package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Recommender;
import javax.inject.Inject;

public class RecommenderDao extends GenericDao {

  @Inject
  public RecommenderDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Long saveRecommender(Recommender recommender) {
    getSession().saveOrUpdate(recommender);
    return recommender.getRecommenderId();
  }

  public int updateRecommender(Long id, Recommender recommender) {
    return getSession().createQuery("update Recommender " +
        "SET recommenderFirstName = :firstName " +
        ", recommenderLastName = :lastName " +
        ", recommenderEmail = :email " +
        " where recommenderId = :id")
      .setParameter("firstName", recommender.getRecommenderFirstName())
      .setParameter("lastName", recommender.getRecommenderLastName())
      .setParameter("email", recommender.getRecommenderEmail())
      .setParameter("id", id)
      .executeUpdate();
  }

  public void deleteRecommender(Recommender recommender) {
    getSession().remove(recommender);
  }
}
