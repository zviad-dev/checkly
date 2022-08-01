package ru.hh.checkly.dao;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Survey;

public class SurveyDao extends GenericDao {

  @Inject
  public SurveyDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Set<Survey> getSurveysByRecruiterId(Long recruiterId) {
    return getSession().createQuery("FROM Survey survey " +
            "JOIN FETCH survey.recruiter recruiter " +
            "JOIN FETCH survey.candidate " +
            "WHERE recruiter.id = :recruiterId", Survey.class)
        .setParameter("recruiterId", recruiterId)
        .stream().collect(Collectors.toSet());
  }

  public Optional<Survey> getSurveyById(Long surveyId, Long recruiterId) {
    return getSession().createQuery("FROM Survey survey WHERE survey.recruiter.id = :recruiterId AND survey.surveyId = :surveyId", Survey.class)
        .setParameter("recruiterId", recruiterId)
        .setParameter("surveyId", surveyId)
        .uniqueResultOptional();
  }

  public Survey saveSurvey(Survey survey) {
    getSession().save(survey);
    return survey;
  }

  public Survey updateSurvey(Survey survey) {
    getSession().update(survey);
    return survey;
  }

  public void refreshSession(Survey survey) {
    getSession().refresh(survey);
  }

  public void deleteSurvey(Survey survey) {
    getSession().delete(survey);
  }

  public Optional<Survey> getSurveyById(Long id) {
    return getSession().createQuery("FROM Survey s "  +
        "JOIN FETCH s.recommendations " +
        "JOIN FETCH s.candidate " +
        "JOIN FETCH s.surveyQuestions " +
        "WHERE s.surveyId = :id", Survey.class)
      .setParameter("id", id)
      .uniqueResultOptional();
  }
}
