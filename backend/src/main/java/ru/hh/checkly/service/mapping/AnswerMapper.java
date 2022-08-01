package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.AnswerDto;
import ru.hh.checkly.entity.Answer;

public class AnswerMapper {

  public static Answer toAnswer(AnswerDto answerDto) {
    return new Answer(answerDto.getAnswer());
  }

  public static AnswerDto toAnswerDto(Answer answer) {
    return new AnswerDto(answer.getSurveyQuestion().getQuestionId(), answer.getSurveyQuestion().getQuestion(),
      answer.getAnswer());
  }
}
