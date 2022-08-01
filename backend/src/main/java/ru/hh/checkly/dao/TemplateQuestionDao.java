package ru.hh.checkly.dao;

import org.hibernate.SessionFactory;
import ru.hh.checkly.entity.TemplateQuestion;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

public class TemplateQuestionDao extends GenericDao {

  @Inject
  public TemplateQuestionDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public void save(TemplateQuestion templateQuestion) {
    getSession().save(templateQuestion);
  }

  public void delete(TemplateQuestion templateQuestion) {
    getSession().delete(templateQuestion);
  }

  public Set<TemplateQuestion> getTemplateQuestionsById(Long templateId) {
    return getSession().createQuery("FROM TemplateQuestion templateQuestion " +
            "JOIN FETCH templateQuestion.template template " +
            "WHERE template.templateId = :templateId", TemplateQuestion.class)
        .setParameter("templateId", templateId)
        .stream().collect(Collectors.toSet());
  }

}
