package ru.hh.checkly.dto.statistics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruiterStatisticsDto {

  @JsonProperty("total_surveys")
  private Long totalSurveys;
  @JsonProperty("open_surveys")
  private Long openSurveys;
  @JsonProperty("pending_surveys")
  private Long pendingSurveys;
  @JsonProperty("finished_surveys")
  private Long finishedSurveys;
  @JsonProperty("closed_surveys")
  private Long closedSurveys;

  public void setTotalSurveys(Long totalSurveys) {
    this.totalSurveys = totalSurveys;
  }

  public void setOpenSurveys(Long openSurveys) {
    this.openSurveys = openSurveys;
  }

  public void setPendingSurveys(Long pendingSurveys) {
    this.pendingSurveys = pendingSurveys;
  }

  public void setFinishedSurveys(Long finishedSurveys) {
    this.finishedSurveys = finishedSurveys;
  }

  public void setClosedSurveys(Long closedSurveys) {
    this.closedSurveys = closedSurveys;
  }
}
