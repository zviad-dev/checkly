package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.SurveyQuestionDto;
import ru.hh.checkly.entity.SurveyQuestion;

public class SurveyQuestionMapper {

  private SurveyQuestionMapper() {
      throw new IllegalStateException();
  }

  public static SurveyQuestionDto toSurveyQuestionDto(SurveyQuestion question) {
    return new SurveyQuestionDto(question.getQuestionId(), question.getQuestion());
  }
}
