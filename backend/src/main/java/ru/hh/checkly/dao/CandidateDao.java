package ru.hh.checkly.dao;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Candidate;

public class CandidateDao extends GenericDao {

  @Inject
  public CandidateDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Candidate saveCandidate(Candidate candidate) {
    getSession().save(candidate);
    return candidate;
  }

  public Candidate updateCandidate(Candidate candidate) {
    getSession().update(candidate);
    return candidate;
  }

}
