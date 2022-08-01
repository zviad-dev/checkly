package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "questionId" })
public class AnswerDto {

  @JsonProperty("question_id")
  private Long questionId;

  private String question;

  private String answer;

  public AnswerDto() {}

  public AnswerDto(String answer) {
    this.answer = answer;
  }

  public AnswerDto(Long questionId, String question, String answer) {
    this.questionId = questionId;
    this.question = question;
    this.answer = answer;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }
}
