package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ru.hh.checkly.entity.RecommendationStatus;
import java.util.Set;

@JsonPropertyOrder({ "id", "firstName", "lastName", "email" })
public class RecommendationDto {

  private Long id;

  private RecommendationStatus status;

  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;

  private String email;

  @JsonProperty("answers")
  private Set<AnswerDto> answerDtoSet;

  public RecommendationDto() {}

  public RecommendationDto(Long id, String firstName, String lastName,
                               String email, RecommendationStatus status,
                               Set<AnswerDto> answerDtoSet) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.status = status;
    this.answerDtoSet = answerDtoSet;
  }

  public RecommendationStatus getStatus() {
    return status;
  }

  public void setStatus(RecommendationStatus status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<AnswerDto> getAnswerDtoSet() {
    return answerDtoSet;
  }

  public void setAnswerDtoSet(Set<AnswerDto> answerDtoSet) {
    this.answerDtoSet = answerDtoSet;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
