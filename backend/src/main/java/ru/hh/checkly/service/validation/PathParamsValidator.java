package ru.hh.checkly.service.validation;

import ru.hh.checkly.exception.RestException;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECOMMENDATION_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECRUITER_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_SURVEY_ID;

public class PathParamsValidator {

  public static void validateRecruiterId(Long recruiterId) {
    if (recruiterId == null || recruiterId < 1) {
      throw new RestException(INVALID_RECRUITER_ID);
    }
  }

  public static void validateRecommendationId(Long recommendationId) {
    if (recommendationId == null || recommendationId < 1) {
      throw new RestException(INVALID_RECOMMENDATION_ID);
    }
  }

  public static void validateSurveyId(Long surveyId) {
    if (surveyId == null || surveyId < 1) {
      throw new RestException(INVALID_SURVEY_ID);
    }
  }

  public static void validateAnswerId(Long answerId) {
    if (answerId == null || answerId < 1) {
      throw new RestException(INVALID_SURVEY_ID);
    }
  }
}
