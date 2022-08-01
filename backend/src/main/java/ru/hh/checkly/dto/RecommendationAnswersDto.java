package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

public class RecommendationAnswersDto {

  @JsonProperty("candidate_first_name")
  private String candidateFirstName;

  @JsonProperty("candidate_last_name")
  private String candidateLastName;

  @JsonProperty("recommender_first_name")
  private String recommenderFirstName;

  @JsonProperty("recommender_last_name")
  private String recommenderLastName;

  @JsonProperty("recommender_email")
  private String recommenderEmail;

  @JsonProperty("answers")
  private Set<AnswerDto> answerDtoSet;

  public RecommendationAnswersDto() {}

  public RecommendationAnswersDto(String candidateFirstName, String candidateLastName, String recommenderFirstName,
                           String recommenderLastName, String recommenderEmail, Set<AnswerDto> answerDtoSet) {
    this.candidateFirstName = candidateFirstName;
    this.candidateLastName = candidateLastName;
    this.recommenderFirstName = recommenderFirstName;
    this.recommenderLastName = recommenderLastName;
    this.recommenderEmail = recommenderEmail;
    this.answerDtoSet = answerDtoSet;
  }

  public String getCandidateFirstName() {
    return candidateFirstName;
  }

  public void setCandidateFirstName(String candidateFirstName) {
    this.candidateFirstName = candidateFirstName;
  }

  public String getCandidateLastName() {
    return candidateLastName;
  }

  public void setCandidateLastName(String candidateLastName) {
    this.candidateLastName = candidateLastName;
  }

  public String getRecommenderFirstName() {
    return recommenderFirstName;
  }

  public void setRecommenderFirstName(String recommenderFirstName) {
    this.recommenderFirstName = recommenderFirstName;
  }

  public String getRecommenderLastName() {
    return recommenderLastName;
  }

  public void setRecommenderLastName(String recommenderLastName) {
    this.recommenderLastName = recommenderLastName;
  }

  public String getRecommenderEmail() {
    return recommenderEmail;
  }

  public void setRecommenderEmail(String recommenderEmail) {
    this.recommenderEmail = recommenderEmail;
  }

  public Set<AnswerDto> getAnswerDtoSet() {
    return answerDtoSet;
  }

  public void setAnswerDtoSet(Set<AnswerDto> answerDtoSet) {
    this.answerDtoSet = answerDtoSet;
  }
}
