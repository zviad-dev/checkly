package ru.hh.checkly.service.validation;

import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.SurveyQuestionChangeRequestDto;
import ru.hh.checkly.exception.RestException;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class SurveyValidator {
  private SurveyValidator() {
    throw new IllegalStateException();
  }

  public static void checkSurveyPathParams(Long recruiterId, Long surveyId) {
    if (recruiterId == null || recruiterId < 1) {
      throw new RestException(INVALID_RECRUITER_ID);
    }
    if (surveyId == null || surveyId < 1) {
      throw new RestException(INVALID_SURVEY_ID);
    }
  }

  public static void checkSurveyPathParams(Long recruiterId, Long surveyId, Long questionId) {
    checkSurveyPathParams(recruiterId, surveyId);
    if (questionId == null || questionId < 1) {
      throw new RestException(INVALID_QUESTION_ID);
    }
  }

  public static void checkSurveyPathParams(Long recruiterId) {
    if (recruiterId == null || recruiterId < 1) {
      throw new RestException(INVALID_RECRUITER_ID);
    }
  }

  public static void checkSurveyTemplatePathParams(Long recruiterId, Long surveyId, Long templateId) {
    checkSurveyPathParams(recruiterId, surveyId);
    if (templateId == null || templateId < 1) {
      throw new RestException(INVALID_TEMPLATE_ID);
    }
  }

  public static void checkSurveyQuestionChangeRequestDto(SurveyQuestionChangeRequestDto dto) {
    if (dto == null || StringUtils.isEmpty(dto.getQuestion())) {
      throw new RestException(INVALID_QUESTION);
    }
  }

  public static void checkSurveyQuestionsAddRequest(Set<String> questions) {
    if (questions.isEmpty()) {
      throw new RestException(EMPTY_QUESTION);
    }
  }
}
