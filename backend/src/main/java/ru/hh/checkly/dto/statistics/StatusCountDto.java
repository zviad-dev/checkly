package ru.hh.checkly.dto.statistics;

import ru.hh.checkly.entity.SurveyStatus;

public class StatusCountDto {

  private SurveyStatus status;
  private Long count;

  public StatusCountDto(SurveyStatus status, Long count) {
    this.status = status;
    this.count = count;
  }

  public SurveyStatus getStatus() {
    return status;
  }

  public Long getCount() {
    return count;
  }
}
