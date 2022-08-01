package ru.hh.checkly.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dao.StatisticsDao;
import ru.hh.checkly.dto.statistics.RecruiterStatisticsDto;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECRUITER_ID;
import ru.hh.checkly.exception.RestException;

import javax.inject.Inject;

public class StatisticsService {

  private final RecruiterDao recruiterDao;
  private final StatisticsDao statisticsDao;

  @Inject
  public StatisticsService(RecruiterDao recruiterDao, StatisticsDao statisticsDao) {
    this.recruiterDao = recruiterDao;
    this.statisticsDao = statisticsDao;
  }

  @Transactional
  public RecruiterStatisticsDto getRecruiterStatistics(Long recruiterId) {
    recruiterDao.getRecruiterById(recruiterId).orElseThrow(
      () -> new RestException(INVALID_RECRUITER_ID));
    return statisticsDao.getRecruiterStatistics(recruiterId);
  }
}
