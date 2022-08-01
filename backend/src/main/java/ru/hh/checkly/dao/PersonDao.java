package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import javax.inject.Inject;

public class PersonDao extends GenericDao {

  @Inject
  public PersonDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public boolean checkIfPersonExists(String email, String password) {
    return (boolean) getSession()
        .createNativeQuery("SELECT EXISTS (SELECT 1 FROM person WHERE email = :email AND password = :password)")
        .setParameter("email", email)
        .setParameter("password", password)
        .getSingleResult();
  }

  public boolean checkIfPersonEmailExists(String email) {
    return (boolean) getSession()
        .createNativeQuery("SELECT EXISTS (SELECT 1 FROM person WHERE email = :email)")
        .setParameter("email", email)
        .getSingleResult();
  }
}
