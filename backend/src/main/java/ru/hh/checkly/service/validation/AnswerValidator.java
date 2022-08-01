package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.AnswerDto;
import ru.hh.checkly.exception.RestException;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_BODY;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_QUESTION;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_QUESTION_ID;

public class AnswerValidator {

  public static void validateAnswerSet(AnswerDto answerDto) {
    if (answerDto == null) {
      throw new RestException(EMPTY_BODY);
    }

    if (answerDto.getQuestionId() < 0) {
      throw new RestException(INVALID_QUESTION_ID);
    }

    if (StringUtils.isEmpty(answerDto.getQuestion())) {
      throw new RestException(EMPTY_QUESTION);
    }
  }

  public static void validateAnswer(AnswerDto answerDto) {
    if (answerDto == null) {
      throw new RestException(EMPTY_BODY);
    }
  }
}
