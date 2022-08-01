package ru.hh.checkly.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.AnswerDao;
import ru.hh.checkly.dao.RecommendationDao;
import ru.hh.checkly.dao.SurveyDao;
import ru.hh.checkly.dao.SurveyQuestionDao;
import ru.hh.checkly.dto.AnswerDto;
import ru.hh.checkly.dto.MailMessage;
import ru.hh.checkly.dto.RecommendationAnswersDto;
import ru.hh.checkly.entity.*;
import ru.hh.checkly.exception.RestException;
import ru.hh.nab.common.properties.FileSettings;

import javax.inject.Inject;
import java.time.LocalDateTime;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_ANSWER;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_QUESTION_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECOMMENDATION_STATUS;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_STATUS;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;
import static ru.hh.checkly.service.mapping.RecommendationMapper.toRecommendationAnswersDto;

@Service
public class RecommendationAnswersService {

  RecommendationDao recommendationDao;
  SurveyQuestionDao surveyQuestionDao;
  AnswerDao answerDao;
  SurveyDao surveyDao;
  MailSenderService mailSenderService;
  EventService eventService;
  private final String host;

  @Inject
  public RecommendationAnswersService(RecommendationDao recommendationDao, SurveyQuestionDao surveyQuestionDao,
                                      AnswerDao answerDao, SurveyDao surveyDao, MailSenderService mailSenderService,
                                      FileSettings fileSettings, EventService eventService) {
    this.recommendationDao = recommendationDao;
    this.surveyQuestionDao = surveyQuestionDao;
    this.answerDao = answerDao;
    this.surveyDao = surveyDao;
    this.mailSenderService = mailSenderService;
    this.eventService = eventService;
    this.host = fileSettings.getString("server.host");
  }

  @Transactional
  public RecommendationAnswersDto getRecommendation(Long recommendationId) {
    Recommendation recommendation = recommendationDao.getRecommendationById(recommendationId)
      .orElseThrow(() -> new RestException(INVALID_RECOMMENDATION_ID));
    checkRecommendationStatus(recommendation);

    return toRecommendationAnswersDto(recommendation);
  }

  @Transactional
  public RecommendationAnswersDto saveRecommendationAnswer(Long recommendationId, Long questionId, AnswerDto answerDto) {
    Recommendation recommendation = recommendationDao.getRecommendationById(recommendationId)
      .orElseThrow(() -> new RestException(INVALID_RECOMMENDATION_ID));
    checkRecommendationStatus(recommendation);
    Survey survey = recommendation.getSurvey();
    /*
     * Проверяем, содержит ли опрос, связанный с рекомендацией, вопрос с заданным id
     */
    if (survey.getSurveyQuestions()
      .stream()
      .noneMatch(question -> question.getQuestionId().equals(questionId))) {
      throw new RestException(INVALID_QUESTION_ID);
    }
    Answer answer = answerDao.getAnswer(questionId, recommendationId)
      .orElseThrow(() -> new RestException(INVALID_ANSWER));
    answer.setAnswer(answerDto.getAnswer());
    answerDao.saveAnswer(answer);
    recommendationDao.saveRecommendation(recommendation);

    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);

    return toRecommendationAnswersDto(recommendation);
  }

  @Transactional
  public void closeRecommendation(Long recommendationId) {
    Recommendation recommendation = recommendationDao.getRecommendationById(recommendationId)
      .orElseThrow(() -> new RestException(INVALID_RECOMMENDATION_ID));
    checkRecommendationStatus(recommendation);
    recommendation.setStatus(RecommendationStatus.CLOSED);
    recommendationDao.saveRecommendation(recommendation);
    sendRecommendationFormEmailToRecruiter(recommendation);

    Survey survey = recommendation.getSurvey();
    survey.setUpdateTime(LocalDateTime.now());
    if (survey.getRecommendations()
      .stream()
      .allMatch(r -> r.getStatus() == RecommendationStatus.CLOSED)) {
      survey.setStatus(SurveyStatus.FINISHED);

      eventService.createForSurvey(
          String.format("Опрос по вакансии '%s' завершен", survey.getPositionName()),
          survey.getRecruiter(),
          survey
      );
    }
    surveyDao.saveSurvey(survey);

    eventService.create(
        String.format("Получена рекомендация по вакансии '%s'", recommendation.getSurvey().getPositionName()),
        survey.getRecruiter(),
        recommendation
    );
  }

  public void sendRecommendationFormEmailToRecruiter(Recommendation recommendation) {
    Recruiter recruiter = recommendation.getSurvey().getRecruiter();
    String recommendationFormUrl =
      String.format("http://%S/recruiters/%s/surveys/%s/recommendations/%s",
        host, recruiter.getId(), recommendation.getSurvey().getSurveyId(), recommendation.getRecommendationId());

    String title = "Получен ответ по анкете";
    String body = String.format("Здравствуйте, %s!" +
        "%nПолучена рекомендация по анкете: %s" +
        "%nС уважением," +
        "%nКоманда Checkly",
      recruiter.getFirstName(),
      recommendationFormUrl);

    MailMessage mailMessage = new MailMessage(title, body, recruiter.getEmail());
    mailSenderService.sendEmailWithAttachment(mailMessage);
  }

  private static void checkRecommendationStatus(Recommendation recommendation) {
    if (recommendation.getSurvey().getStatus() != SurveyStatus.PENDING) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }
    if (recommendation.getStatus() != RecommendationStatus.PENDING) {
      throw new RestException(INVALID_RECOMMENDATION_STATUS);
    }
  }

}
