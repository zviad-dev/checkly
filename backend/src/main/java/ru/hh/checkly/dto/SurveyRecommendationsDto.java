package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import ru.hh.checkly.entity.SurveyStatus;
import java.time.LocalDateTime;
import java.util.Set;

@JsonTypeName("survey_info")
@JsonTypeInfo(include= JsonTypeInfo.As.WRAPPER_OBJECT,use= JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({ "id", "candidateFirstName", "candidateLastName", "candidateEmail" })
public class SurveyRecommendationsDto extends SurveyBaseDto {

  @JsonProperty("recommendations")
  private Set<RecommendationDto> recommendationDtoSet;

  public SurveyRecommendationsDto() {}

  public SurveyRecommendationsDto(Long id, CandidateDto candidateDto, SurveyStatus status, LocalDateTime creationTime,
                                 LocalDateTime updateTime, Set<RecommendationDto> recommendationDtoSet, String positionName) {
    super(id, candidateDto, status, creationTime, updateTime, positionName);
    this.recommendationDtoSet = recommendationDtoSet;
  }

  public Set<RecommendationDto> getRecommendationDtoSet() {
    return recommendationDtoSet;
  }

  public void setRecommendationDtoSet(Set<RecommendationDto> recommendationDtoSet) {
    this.recommendationDtoSet = recommendationDtoSet;
  }
}
