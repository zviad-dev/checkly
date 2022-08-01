package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.template.TemplateNameDto;
import ru.hh.checkly.dto.template.TemplateQuestionDtoShort;
import ru.hh.checkly.exception.RestException;

import java.util.List;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class TemplateValidator {

  public static void validateRecruiterId(Long recruiterId) {
    if (recruiterId == null || recruiterId < 1) {
      throw new RestException(INVALID_RECRUITER_ID);
    }
  }

  public static void validateTemplateId(Long templateId) {
    if (templateId == null || templateId < 1) {
      throw new RestException(INVALID_TEMPLATE_ID);
    }
  }

  public static void validateQuestionId(Long questionId) {
    if (questionId == null || questionId < 1) {
      throw new RestException(INVALID_QUESTION_ID);
    }
  }

  public static void validateTemplateName(TemplateNameDto templateNameDto) {
    if (templateNameDto == null) {
      throw new RestException(EMPTY_BODY);
    }
    if (StringUtils.isEmpty(templateNameDto.getName())) {
      throw new RestException(EMPTY_TEMPLATE_NAME);
    }
  }

  public static void validateQuestion (TemplateQuestionDtoShort templateQuestionDtoShort) {
    if (templateQuestionDtoShort == null) {
      throw new RestException(EMPTY_BODY);
    }
    if (StringUtils.isEmpty(templateQuestionDtoShort.getQuestion())) {
      throw new RestException(EMPTY_QUESTION);
    }
  }

  public static void validateQuestions(List<String> questions) {
    if (questions == null) {
      throw new RestException(EMPTY_BODY);
    }

    if (!questions.stream().allMatch(StringUtils::isNotEmpty))  {
      throw new RestException(EMPTY_QUESTION);
    }
  }

}
