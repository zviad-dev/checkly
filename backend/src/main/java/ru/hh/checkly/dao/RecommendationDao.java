package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Recommendation;
import javax.inject.Inject;
import java.util.Optional;

public class RecommendationDao extends GenericDao {

  @Inject
  public RecommendationDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public void refreshSession(Recommendation recommendation) {
    getSession().refresh(recommendation);
  }

  public Optional<Recommendation> getRecommendationById(Long id) {
    return getSession().createQuery("FROM Recommendation r "  +
        "JOIN FETCH r.survey " +
        "WHERE r.recommendationId = :id", Recommendation.class)
      .setParameter("id", id)
      .uniqueResultOptional();
  }

    public Long saveRecommendation(Recommendation recommendation) {
    getSession().saveOrUpdate(recommendation);
    return recommendation.getRecommendationId();
  }

  public void deleteRecommendation(Recommendation recommendation) {
    getSession().remove(recommendation);
  }
}
