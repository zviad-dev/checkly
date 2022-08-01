package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.dto.statistics.RecruiterStatisticsDto;
import ru.hh.checkly.dto.statistics.StatusCountDto;
import ru.hh.checkly.entity.SurveyStatus;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.hh.checkly.service.StatisticsMapper.toRecruiterStatisticsDto;

public class StatisticsDao extends GenericDao {

  @Inject
  public StatisticsDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public RecruiterStatisticsDto getRecruiterStatistics(Long recruiterId) {
    List<StatusCountDto> resultList = getSession()
      .createQuery("SELECT new ru.hh.checkly.dto.statistics.StatusCountDto (s.status, count(s.status)) " +
        "FROM Recruiter r LEFT JOIN r.surveys s WHERE r.id = :recruiterId GROUP BY s.status", StatusCountDto.class)
      .setParameter("recruiterId", recruiterId)
      .getResultList();
    Map<SurveyStatus, Long> statusToCountMap = new HashMap<>();
    resultList.forEach(dto -> statusToCountMap.put(dto.getStatus(), dto.getCount()));
    return toRecruiterStatisticsDto(statusToCountMap);
  }

}
