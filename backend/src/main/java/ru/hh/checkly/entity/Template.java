package ru.hh.checkly.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "survey_template")
public class Template {
  @Id
  @Column(name = "template_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long templateId;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "recruiter_id", nullable = false)
  private Recruiter recruiter;

  @Column(name = "template_name", nullable = false)
  private String templateName;

  @OneToMany(mappedBy = "template", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<TemplateQuestion> questions;

  public Template() {
  }

  public Template(Recruiter recruiter, String templateName, Set<TemplateQuestion> questions  )
  {
    this.recruiter = recruiter;
    this.templateName = templateName;
    this.questions = questions;
  }

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Recruiter getRecruiter() {
    return recruiter;
  }

  public void setRecruiter(Recruiter recruiter) {
    this.recruiter = recruiter;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public Set<TemplateQuestion> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<TemplateQuestion> questions) {
    this.questions = questions;
  }
}
