package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Recruiter;

import javax.inject.Inject;
import java.util.Optional;

public class RecruiterDao extends GenericDao {

  @Inject
  public RecruiterDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Optional<Recruiter> getRecruiterById(Long id) {
    Recruiter recruiter = getSession().get(Recruiter.class, id);
    return Optional.ofNullable(recruiter);
  }

  public Optional<Recruiter> getRecruiterByEmailAndPassword(String email, String password) {
    return getSession().createQuery("FROM Recruiter r WHERE r.email = :email AND r.password = :password", Recruiter.class)
      .setParameter("email", email)
      .setParameter("password", password)
      .uniqueResultOptional();
  }

  public Optional<Recruiter> getRecruiterByEmail(String email) {
    return getSession().createQuery("FROM Recruiter r WHERE r.email = :email", Recruiter.class)
      .setParameter("email", email)
      .uniqueResultOptional();
  }

  public Recruiter save(Recruiter recruiter) {
    getSession().save(recruiter);
    return recruiter;
  }

  public Recruiter update(Recruiter recruiter) {
    getSession().update(recruiter);
    return recruiter;
  }
}
