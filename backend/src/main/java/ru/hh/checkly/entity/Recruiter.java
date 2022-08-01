package ru.hh.checkly.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "recruiter")
@PrimaryKeyJoinColumn(name = "recruiter_id")
public class Recruiter extends Person {

  @Column(name = "company_name", nullable = false)
  private String companyName;

  @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Template> templates;

  @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Survey> surveys;

  public Recruiter() {
  }

  public Recruiter(String companyName) {
    this.companyName = companyName;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Set<Template> getTemplates() {
    return templates;
  }

  public void setTemplates(Set<Template> templates) {
    this.templates = templates;
  }

  public Set<Survey> getSurveys() {
    return surveys;
  }

  public void setSurveys(Set<Survey> surveys) {
    this.surveys = surveys;
  }
}
