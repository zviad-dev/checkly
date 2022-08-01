package ru.hh.checkly.dto.template;

import java.util.Set;

public class TemplateDto {

  private Long id;
  private String name;
  private Set<TemplateQuestionDto> questions;

  public TemplateDto(Long id, String name, Set<TemplateQuestionDto> questions) {
    this.id = id;
    this.name = name;
    this.questions = questions;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<TemplateQuestionDto> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<TemplateQuestionDto> questions) {
    this.questions = questions;
  }
}
