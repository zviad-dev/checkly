package ru.hh.checkly.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.RecommendationDao;
import ru.hh.checkly.dao.RecommenderDao;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dao.SurveyDao;
import ru.hh.checkly.dao.SurveyQuestionDao;
import ru.hh.checkly.dto.RecommenderDto;
import ru.hh.checkly.dto.SurveyBaseDto;
import ru.hh.checkly.entity.Answer;
import ru.hh.checkly.entity.Recommendation;
import ru.hh.checkly.entity.RecommendationStatus;
import ru.hh.checkly.entity.Recommender;
import ru.hh.checkly.entity.Recruiter;
import ru.hh.checkly.entity.Survey;
import ru.hh.checkly.entity.SurveyStatus;
import ru.hh.checkly.exception.RestException;
import ru.hh.checkly.service.mapping.RecommenderMapper;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECOMMENDATION_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECOMMENDATION_STATUS;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_STATUS;
import static ru.hh.checkly.exception.RestErrorResponseCode.RECOMMENDER_ALREADY_EXISTS;
import static ru.hh.checkly.service.mapping.RecommendationMapper.toRecommendationWithSurveyInfoDto;
import static ru.hh.checkly.service.mapping.RecommendationMapper.toSurveyDto;

@Service
public class RecommendationService {

  RecommendationDao recommendationDao;
  RecruiterDao recruiterDao;
  SurveyDao surveyDao;
  RecommenderDao recommenderDao;
  SurveyQuestionDao surveyQuestionDao;

  @Inject
  public RecommendationService(RecommendationDao recommendationDao, RecruiterDao recruiterDao,
                               SurveyDao surveyDao, RecommenderDao recommenderDao, SurveyQuestionDao surveyQuestionDao) {
    this.recommendationDao = recommendationDao;
    this.recruiterDao = recruiterDao;
    this.surveyDao = surveyDao;
    this.recommenderDao = recommenderDao;
    this.surveyQuestionDao = surveyQuestionDao;
  }

  @Transactional
  public SurveyBaseDto getSurveyById(Long recruiterId, Long surveyId) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));

    return toSurveyDto(survey);
  }

  @Transactional
  public SurveyBaseDto getRecommendation(Long recruiterId, Long surveyId, Long recommendationId) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    Recommendation recommendation = getSurveyRecommendation(survey, recommendationId);

    return toRecommendationWithSurveyInfoDto(recommendation);
  }

  @Transactional
  public SurveyBaseDto saveRecommenders(Long recruiterId, Long surveyId, Set<RecommenderDto> recommenderDtoSet) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    checkSurveyStatus(survey);

    recommenderDtoSet
      .stream()
      .map(recommenderDto -> {
        Recommender recommender = RecommenderMapper.toRecommender(recommenderDto);
        Recommendation recommendation = new Recommendation(recommender);
        recommender.setRecommendation(recommendation);

        survey.getRecommendations().forEach(q -> {
          if (q.getRecommender().getRecommenderEmail().equals(recommender.getRecommenderEmail())) {
            throw new RestException(RECOMMENDER_ALREADY_EXISTS);
          }
        });
        Set<Answer> answerSet = new HashSet<>();
        survey.getSurveyQuestions().forEach(q -> {
          Answer answ = new Answer();
          answ.setSurveyQuestion(q);
          answ.setRecommendation(recommendation);
          answerSet.add(answ);
        });
        recommendation.setAnswers(answerSet);
        return recommendation;
      })
      .peek(recommendation -> recommendation.setSurvey(survey))
      .peek(recommendation -> recommendation.setStatus(RecommendationStatus.OPEN))
      .forEach(recommendationDao::saveRecommendation);

    surveyDao.refreshSession(survey);
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);

    return toSurveyDto(survey);
  }

  @Transactional
  public SurveyBaseDto updateRecommender(Long recruiterId, Long surveyId, Long recommendationId,
                                         RecommenderDto recommenderDto) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    Recommendation recommendation = getSurveyRecommendation(survey, recommendationId);
    recommenderDao.updateRecommender(recommendation.getRecommender().getRecommenderId(), RecommenderMapper.toRecommender(recommenderDto));

    recommendationDao.refreshSession(recommendation);
    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);

    return toRecommendationWithSurveyInfoDto(recommendation);
  }

  @Transactional
  public void deleteRecommendation(Long recruiterId, Long surveyId, Long recommendationId) {
    Survey survey = surveyDao.getSurveyById(surveyId, recruiterId)
        .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
    Set<Recommendation> recommendationSet = survey.getRecommendations();
    Recommendation recommendation = survey.getRecommendations()
      .stream()
      .filter(r -> r.getRecommendationId().equals(recommendationId))
      .findAny()
      .orElseThrow(() -> new RestException(INVALID_RECOMMENDATION_ID));

    checkSurveyStatus(recommendation.getSurvey());
    if (recommendation.getStatus() != RecommendationStatus.OPEN) {
      throw new RestException(INVALID_RECOMMENDATION_STATUS);
    }

    recommendationSet.remove(recommendation);
    recommendationDao.deleteRecommendation(recommendation);

    survey.setUpdateTime(LocalDateTime.now());
    surveyDao.updateSurvey(survey);
  }

  private static Survey getRecruiterSurvey(Recruiter recruiter, Long surveyId) {
    return recruiter.getSurveys()
      .stream()
      .filter(s -> s.getSurveyId().equals(surveyId))
      .findAny()
      .orElseThrow(() -> new RestException(INVALID_SURVEY_ID));
  }

  private static Recommendation getSurveyRecommendation(Survey survey, Long recommendationId) {
    return survey.getRecommendations()
      .stream()
      .filter(r -> r.getRecommendationId().equals(recommendationId))
      .findAny()
      .orElseThrow(() -> new RestException(INVALID_RECOMMENDATION_ID));
  }

  private static void checkSurveyStatus(Survey survey) {
    if (survey.getStatus() != SurveyStatus.OPEN) {
      throw new RestException(INVALID_SURVEY_STATUS);
    }
  }

}
