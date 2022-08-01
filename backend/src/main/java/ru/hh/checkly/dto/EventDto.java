package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ru.hh.checkly.entity.Recommendation;
import ru.hh.checkly.entity.Recommender;
import ru.hh.checkly.entity.Survey;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDto {
  private final UUID id;
  private final String message;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private final LocalDateTime created;
  private final boolean viewed;
  private Long surveyId;
  private Long recommendationId;
  private String recommenderFio;
  private String candidateFio;
  private String positionName;

  public EventDto(UUID id, String message, LocalDateTime created, boolean viewed, Recommendation recommendation) {
    this.id = id;
    this.message = message;
    this.created = created;
    this.viewed = viewed;
    if (recommendation != null) {
      Survey survey = recommendation.getSurvey();
      this.surveyId = recommendation.getSurvey().getSurveyId();
      this.recommendationId = recommendation.getRecommendationId();

      Recommender recommender = recommendation.getRecommender();
      this.recommenderFio = String.format("%s %s", recommender.getRecommenderLastName(), recommender.getRecommenderFirstName());
      this.candidateFio = String.format("%s %s", survey.getCandidate().getCandidateLastName(), survey.getCandidate().getCandidateFirstName());
      this.positionName = recommendation.getSurvey().getPositionName();
    }
  }

  public UUID getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public boolean isViewed() {
    return viewed;
  }

  public Long getSurveyId() {
    return surveyId;
  }

  public Long getRecommendationId() {
    return recommendationId;
  }

  public String getRecommenderFio() {
    return recommenderFio;
  }

  public String getCandidateFio() {
    return candidateFio;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setSurveyId(Long surveyId) {
    this.surveyId = surveyId;
  }
}
