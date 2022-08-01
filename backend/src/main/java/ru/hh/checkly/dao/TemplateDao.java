package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.Template;

import javax.inject.Inject;
import java.util.Optional;

public class TemplateDao extends GenericDao {

  @Inject
  public TemplateDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public Optional<Template> getTemplateById(Long id) {
    Template template = getSession().get(Template.class, id);
    return Optional.ofNullable(template);
  }

  public Template save(Template template) {
    getSession().save(template);
    return template;
  }

  public Template update(Template template) {
    getSession().update(template);
    return template;
  }

  public void delete(Template template) {
    getSession().delete(template);
  }

}
