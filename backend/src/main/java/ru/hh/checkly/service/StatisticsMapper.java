package ru.hh.checkly.service;

import ru.hh.checkly.dto.statistics.RecruiterStatisticsDto;
import ru.hh.checkly.entity.SurveyStatus;

import java.util.Map;
import java.util.Objects;

public class StatisticsMapper {

  public static RecruiterStatisticsDto toRecruiterStatisticsDto(Map<SurveyStatus, Long> statusToCountMap) {
    RecruiterStatisticsDto recruiterStatisticsDto = new RecruiterStatisticsDto();
    recruiterStatisticsDto.setOpenSurveys(statusToCountMap.get(SurveyStatus.OPEN));
    recruiterStatisticsDto.setPendingSurveys(statusToCountMap.get(SurveyStatus.PENDING));
    recruiterStatisticsDto.setClosedSurveys(statusToCountMap.get(SurveyStatus.CLOSED));
    recruiterStatisticsDto.setFinishedSurveys(statusToCountMap.get(SurveyStatus.FINISHED));
    recruiterStatisticsDto.setTotalSurveys(
      statusToCountMap
        .values()
        .stream()
        .filter(Objects::nonNull)
        .mapToLong(Long::longValue)
        .sum());
    return recruiterStatisticsDto;
  }

}
