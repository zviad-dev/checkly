package ru.hh.checkly.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.inject.Inject;

public class GenericDao {
  private final SessionFactory sessionFactory;

  @Inject
  protected GenericDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  public Session getSession() {
    return sessionFactory.getCurrentSession();
  }
}
