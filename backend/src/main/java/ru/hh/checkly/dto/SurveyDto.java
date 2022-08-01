package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.Set;
import ru.hh.checkly.entity.SurveyStatus;

public class SurveyDto {

  private long id;

  @JsonProperty("position_name")
  private String positionName;

  @JsonProperty("candidate")
  CandidateDto candidateDto;

  @JsonProperty("status")
  private SurveyStatus status;

  @JsonProperty("created_at")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonProperty("updated_at")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  @JsonProperty("questions")
  private Set<SurveyQuestionDto> questions;

  @JsonProperty("recommenders")
  private Set<RecommenderDto> recommenders;

  public SurveyDto() {

  }

  public SurveyDto(long id, CandidateDto candidateDto,
                   SurveyStatus status, LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   Set<SurveyQuestionDto> questions,
                   Set<RecommenderDto> recommenders, String positionName) {
    this.id = id;
    this.candidateDto = candidateDto;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.questions = questions;
    this.recommenders = recommenders;
    this.positionName = positionName;
  }

  public long getId() {
    return id;
  }

  public CandidateDto getCandidateDto() {
    return candidateDto;
  }

  public SurveyStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Set<SurveyQuestionDto> getQuestions() {
    return questions;
  }

  public Set<RecommenderDto> getRecommenders() {
    return recommenders;
  }
}