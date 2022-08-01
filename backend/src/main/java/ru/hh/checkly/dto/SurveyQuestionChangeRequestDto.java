package ru.hh.checkly.dto;

public class SurveyQuestionChangeRequestDto {

  private String question;

  public SurveyQuestionChangeRequestDto() {
  }

  public SurveyQuestionChangeRequestDto(String question) {
    this.question = question;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }
}
