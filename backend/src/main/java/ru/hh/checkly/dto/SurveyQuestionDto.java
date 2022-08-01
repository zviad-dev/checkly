package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SurveyQuestionDto {

  @JsonProperty("id")
  private long id;

  @JsonProperty("question")
  private String question;

  public SurveyQuestionDto(long id, String question) {
    this.id = id;
    this.question = question;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

}
