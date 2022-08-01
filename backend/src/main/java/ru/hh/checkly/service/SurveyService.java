package ru.hh.checkly.service;

import java.time.LocalDateTime;
import ru.hh.checkly.dao.AnswerDao;
import ru.hh.checkly.dto.CandidateWithPositionDto;
import ru.hh.checkly.entity.Answer;
import ru.hh.checkly.entity.RecommendationStatus;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_QUESTION_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_STATUS;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_TEMPLATE_ID;
import static ru.hh.checkly.service.mapping.CandidateMapper.toCandidateEntity;
import ru.hh.nab.common.properties.FileSettings;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;
import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import static java.nio.charset.StandardCharsets.UTF_8;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import ru.hh.checkly.dao.CandidateDao;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dao.SurveyDao;
import ru.hh.checkly.dao.SurveyQuestionDao;
import ru.hh.checkly.dao.TemplateQuestionDao;
import ru.hh.checkly.dto.MailMessage;
import ru.hh.checkly.dto.SurveyDto;
import ru.hh.checkly.entity.Candidate;
import ru.hh.checkly.entity.Recommendation;
import ru.hh.checkly.entity.Survey;
import ru.hh.checkly.entity.SurveyQuestion;
import ru.hh.checkly.entity.TemplateQuestion;
import static ru.hh.checkly.entity.SurveyStatus.CLOSED;
import static ru.hh.checkly.entity.SurveyStatus.OPEN;
import static ru.hh.checkly.entity.SurveyStatus.PENDING;
import ru.hh.checkly.exception.RestException;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_RECOMMENDATION_LIST;
import static ru.hh.checkly.exception.RestErrorResponseCode.INTERNAL_SERVER_ERROR;
import ru.hh.checkly.service.mapping.SurveyMapper;
import static ru.hh.checkly.service.mapping.SurveyMapper.toSurveyDto;

public class SurveyService {

  final private SurveyDao surveyDao;
  final private RecruiterDao recruiterDao;
  final private CandidateDao candidateDao;
  final private SurveyQuestionDao surveyQuestionDao;
  final private TemplateQuestionDao templateQuestionDao;
  final private AnswerDao answerDao;
  public static final String RECOMMENDATION_TEMPLATE_PATH = "/template-recommendation-letter.html";
  private final MailSenderService mailSenderService;
  private final String host;
  private final String pattern;

  @Inject
  public SurveyService(TemplateQuestionDao templateQuestionDao, CandidateDao candidateDao, SurveyDao surveyDao, AnswerDao answerDao,
                       SurveyQuestionDao surveyQuestionDao, RecruiterDao recruiterDao, MailSenderService mailSenderService,
                       FileSettings fileSettings) throws IOException {
    this.surveyDao = surveyDao;
    this.answerDao = answerDao;
    this.recruiterDao = recruiterDao;
    this.candidateDao = candidateDao;
    this.surveyQuestionDao = surveyQuestionDao;
    this.templateQuestionDao = templateQuestionDao;
    this.host = fileSettings.getString("server.host");
    this.mailSenderService = mailSenderService;
    this.pattern = StreamUtils.copyToString(new ClassPathResource(RECOMMENDATION_TEMPLATE_PATH).getInputStream(), UTF_8);
  }

  @Transactional
  public Set<SurveyDto> getSurveysByRecruiterId(Long recruiterId) {
    return surveyDao.getSurveysByRecruiterId(recruiterId)
        .stream().map(SurveyMapper::toSurveyDto)
        .collect(Collectors.toSet());
  }

  @Transactional
  public SurveyDto getSurveyById(Long surveyId, Long recruiterId) {
    return surveyDao.getSurveyById(surveyId, recruiterId).map(SurveyMapper::toSurveyDto)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
  }

  private Optional<Survey> getSurvey(long surveyId, long recruiterId) {
    return surveyDao.getSurveyById(surveyId, recruiterId);
  }

  @Transactional
  public SurveyDto saveSurvey(CandidateWithPositionDto candidateDto, Long recruiterId) {
    Survey survey = recruiterDao.getRecruiterById(recruiterId)
        .map(recruiter -> {
          Survey temp = new Survey();
          temp.setRecruiter(recruiter);
          temp.setSurveyQuestions(Collections.emptySet());
          temp.setRecommendations(Collections.emptySet());
          temp.setStatus(OPEN);
          temp.setPositionName(candidateDto.getPositionName());
          return temp;
        })
        .map(surveyDao::saveSurvey)
        .orElseThrow(() -> new RestException(INTERNAL_SERVER_ERROR));

    Candidate candidate = toCandidateEntity(candidateDto);

    candidate.setSurvey(survey);
    candidateDao.saveCandidate(candidate);
    survey.setCandidate(candidate);

    return toSurveyDto(survey);
  }

  @Transactional
  public SurveyDto updateCandidate(CandidateWithPositionDto candidateDto, Long surveyId, Long recruiterId) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));

    if (survey.getStatus() != OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }

    Candidate candidate = survey.getCandidate();
    candidate.setCandidateEmail(candidateDto.getEmail());
    candidate.setCandidateFirstName(candidateDto.getFirstName());
    candidate.setCandidateLastName(candidateDto.getLastName());

    candidateDao.updateCandidate(candidate);

    survey.setPositionName(candidateDto.getPositionName());
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);

    return SurveyMapper.toSurveyDto(survey);
  }

  @Transactional
  public SurveyDto addQuestions(Set<String> questions, Long surveyId, Long recruiterId) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));

    if (survey.getStatus() != OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }

    Set<SurveyQuestion> updatedQuestions = new HashSet<>();

    questions.stream().map(SurveyQuestion::new)
        .peek(question -> question.setSurvey(survey))
            .map(surveyQuestionDao::saveSurveyQuestion)
                .forEach(updatedQuestions::add);

    updatedQuestions.forEach(question -> {
      survey.getRecommendations().forEach(recommendation -> {
        Answer answer = new Answer();
        answer.setSurveyQuestion(question);
        answer.setRecommendation(recommendation);
        answerDao.saveAnswer(answer);
      });
    });

    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);
    return toSurveyDto(survey);
  }

  @Transactional
  public SurveyDto addQuestionsFromTemplate(Long templateId, Long surveyId, Long recruiterId) {
    Set<TemplateQuestion> templateQuestions = templateQuestionDao.getTemplateQuestionsById(templateId);

    if (templateQuestions.isEmpty()) {
      throw new RestException(INVALID_TEMPLATE_ID);
    }

    Set<String> questions = new HashSet<>();

    for (TemplateQuestion question: templateQuestions) {
      questions.add(question.getQuestion());
    }

    return addQuestions(questions, surveyId, recruiterId);
  }

  @Transactional
  public void deleteSurvey(Long surveyId, long recruiterId) {
    Survey survey = getSurvey(surveyId, recruiterId).orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    surveyDao.deleteSurvey(survey);
  }

  @Transactional
  public SurveyDto sendSurvey(Long surveyId, long recruiterId) {
    Survey survey = getSurvey(surveyId, recruiterId).orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    if (survey.getStatus() != OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }

    Set<Recommendation> recommendations = survey.getRecommendations();

    if (recommendations.isEmpty()) {
      throw new RestException(EMPTY_RECOMMENDATION_LIST);
    }

    recommendations.forEach(recommendation -> {
      recommendation.setStatus(RecommendationStatus.PENDING);
    });
    survey.setStatus(PENDING);
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);
    survey.getRecommendations().forEach(recommendation -> sendEmailRecommendation(survey.getCandidate(), recommendation));
    return toSurveyDto(survey);
  }

  private void sendEmailRecommendation(Candidate candidate, Recommendation recommendation) {
    String recommendationFormUrl = String.format("http://%s/recommendation/%d", host, recommendation.getRecommendationId());
    String fullName = String.format("%s %s", candidate.getCandidateFirstName(), candidate.getCandidateLastName());
    String title = String.format("Запрос на рекомендацию для %s", fullName);
    String message = new MessageFormat(pattern).format(new Object[] {fullName, recommendationFormUrl});

    MailMessage mailMessage = new MailMessage(title, message,true, recommendation.getRecommender().getRecommenderEmail());
    mailSenderService.sendEmailWithAttachment(mailMessage);
  }

  @Transactional
  public SurveyDto closeSurvey(long surveyId, long recruiterId) {
    Survey survey = getSurvey(surveyId, recruiterId).orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    if (survey.getStatus() == CLOSED) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }
    survey.setStatus(CLOSED);
    survey.getRecommendations().forEach(recommendation -> {
      recommendation.setStatus(RecommendationStatus.CLOSED);
    });
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.saveSurvey(survey);
    return toSurveyDto(survey);
  }

  @Transactional
  public SurveyDto changeSurveyQuestion(Long surveyId, String newQuestion, Long recruiterId, Long questionId) {
    Survey survey = getSurvey(surveyId, recruiterId).orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    if (survey.getStatus() != OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }
    SurveyQuestion question = getSurveyQuestion(questionId, surveyId)
        .orElseThrow(() -> new RestException(INVALID_QUESTION_ID));
    question.setQuestion(newQuestion);
    survey.setUpdateTime(LocalDateTime.now());
    surveyQuestionDao.saveSurveyQuestion(question);
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);
    return SurveyMapper.toSurveyDto(survey);
  }

  @Transactional
  public SurveyDto deleteSurveyQuestion(long surveyId, long recruiterId, long questionId) {
    Survey survey = getSurvey(surveyId, recruiterId).orElseThrow(() -> new RestException(INVALID_SURVEY_ID));

    if (survey.getStatus() != OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }

    SurveyQuestion surveyQuestion = surveyQuestionDao.getSurveyQuestion(questionId, surveyId)
        .orElseThrow(() -> new RestException(INVALID_QUESTION_ID));

    answerDao.deleteAnswersByQuestionId(questionId);
    surveyQuestionDao.deleteSurveyQuestion(questionId);
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);

    return toSurveyDto(survey);
  }

  private Optional<SurveyQuestion> getSurveyQuestion(long questionId, long surveyId) {
    return surveyQuestionDao.getSurveyQuestion(questionId, surveyId);
  }

}
