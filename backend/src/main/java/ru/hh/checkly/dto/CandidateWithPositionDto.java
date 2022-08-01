package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CandidateWithPositionDto extends CandidateDto {

  @JsonProperty("position_name")
  private String positionName;

  public CandidateWithPositionDto() {

  }

  public CandidateWithPositionDto(String firstName, String lastName, String email, String positionName) {
    super(firstName, lastName, email);
    this.positionName = positionName;
  }

  public CandidateWithPositionDto(long id, String firstName, String lastName, String email, String positionName) {
    super(id, firstName, lastName, email);
    this.positionName = positionName;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
}
