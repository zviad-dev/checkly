package ru.hh.checkly.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Answer;
import java.util.Optional;

public class AnswerDao extends GenericDao {

  @Inject
  public AnswerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public void deleteAnswersByQuestionId(Long questionId) {
    getSession().createQuery("DELETE FROM Answer answer WHERE surveyQuestion.questionId = :questionId")
      .setParameter("questionId", questionId)
      .executeUpdate();
  }

  public Optional<Answer> getAnswer(Long questionId, Long recommendationId) {
    return getSession().createNativeQuery("SELECT * FROM answer a "  +
        "WHERE a.question_id = :questionId AND a.recommendation_id = :recommendationId", Answer.class)
      .setParameter("questionId", questionId)
      .setParameter("recommendationId", recommendationId)
      .uniqueResultOptional();
  }

  public Long saveAnswer(Answer answer) {
    getSession().saveOrUpdate(answer);
    return answer.getAnswerId();
  }
}
