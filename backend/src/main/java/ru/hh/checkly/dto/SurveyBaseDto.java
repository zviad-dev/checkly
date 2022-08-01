package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ru.hh.checkly.entity.SurveyStatus;
import java.time.LocalDateTime;

@JsonTypeName("survey_info")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({ "id", "candidateFirstName", "candidateLastName", "candidateEmail" })
public class SurveyBaseDto {

  private Long id;

  @JsonProperty("position_name")
  private String positionName;

  @JsonProperty("candidate")
  private CandidateDto candidateDto;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("created_at")
  private LocalDateTime creationTime;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("updated_at")
  private LocalDateTime updateTime;

  private SurveyStatus status;

  public SurveyBaseDto() {}

  public SurveyBaseDto(Long id, CandidateDto candidateDto, SurveyStatus status, LocalDateTime creationTime,
                       LocalDateTime updateTime, String positionName) {
    this.id = id;
    this.candidateDto = candidateDto;
    this.status = status;
    this.creationTime = creationTime;
    this.updateTime = updateTime;
    this.positionName = positionName;
  }

  public SurveyStatus getStatus() {
    return status;
  }

  public void setStatus(SurveyStatus status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public CandidateDto getCandidateDto() {
    return candidateDto;
  }

  public void setCandidateDto(CandidateDto candidateDto) {
    this.candidateDto = candidateDto;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
}
