package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.hh.checkly.entity.SurveyStatus;
import java.time.LocalDateTime;

@JsonTypeName("survey_info")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
public class SurveyRecommendationDto extends SurveyBaseDto {

  @JsonProperty("recommendation")
  private RecommendationDto recommendationDto;

  public SurveyRecommendationDto() {}

  public SurveyRecommendationDto(Long id, CandidateDto candidateDto, SurveyStatus status, LocalDateTime creationTime,
                                 LocalDateTime updateTime, RecommendationDto recommendationDto, String positionName) {
    super(id, candidateDto, status, creationTime, updateTime, positionName);
    this.recommendationDto = recommendationDto;
  }

  public RecommendationDto getRecommendationDto() {
    return recommendationDto;
  }

  public void setRecommendationDto(RecommendationDto recommendationDto) {
    this.recommendationDto = recommendationDto;
  }
}
