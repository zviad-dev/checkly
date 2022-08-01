package ru.hh.checkly.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.SurveyQuestion;
import java.util.Optional;

public class SurveyQuestionDao extends GenericDao {

  @Inject
  public SurveyQuestionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public SurveyQuestion saveSurveyQuestion(SurveyQuestion question) {
    getSession().save(question);
    return question;
  }

  @Deprecated
  public Optional<SurveyQuestion> getSurveyQuestionById(long id) {
    SurveyQuestion surveyQuestion = getSession().get(SurveyQuestion.class, id);
    return Optional.ofNullable(surveyQuestion);
  }

  public Optional<SurveyQuestion> getSurveyQuestion(long questionId, long surveyId) {
    return getSession().createQuery("FROM SurveyQuestion sq WHERE sq.survey.surveyId = :surveyId AND sq.id = :questionId", SurveyQuestion.class)
        .setParameter("questionId", questionId)
        .setParameter("surveyId", surveyId)
        .uniqueResultOptional();
  }

  public void deleteSurveyQuestion(Long questionId) {
    getSession().createQuery("DELETE FROM SurveyQuestion s WHERE s.questionId = :questionId")
        .setParameter("questionId", questionId)
        .executeUpdate();
  }

  public Optional<SurveyQuestion> getSurveyQuestion(Long id) {
    return getSession().createQuery("FROM SurveyQuestion sq "  +
        "WHERE sq.questionId = :id", SurveyQuestion.class)
      .setParameter("id", id)
      .uniqueResultOptional();
  }
}
