package ru.hh.checkly.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "survey")
public class Survey {

  @Id
  @Column(name = "survey_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long surveyId;

  @Column(name = "position_name", nullable = false)
  private String positionName;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "recruiter_id", nullable = false)
  private Recruiter recruiter;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private SurveyStatus status;

  @Column(name = "creation_time")
  @CreationTimestamp
  private LocalDateTime creationTime;

  @Column(name = "update_time")
  @UpdateTimestamp
  private LocalDateTime updateTime;

  @OneToOne(mappedBy = "survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Candidate candidate;

  @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<SurveyQuestion> surveyQuestions;

  @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Recommendation> recommendations;

  public Survey() {
  }

  public Survey(Recruiter recruiter, Candidate candidate, Set<SurveyQuestion> surveyQuestions, Set<Recommendation> recommendations,
                String positionName) {
    this.recruiter = recruiter;
    this.candidate = candidate;
    this.surveyQuestions = surveyQuestions;
    this.recommendations = recommendations;
    this.positionName = positionName;
  }

  public Survey(Candidate candidate, Recruiter recruiter, String positionName) {
    this.candidate = candidate;
    this.recruiter = recruiter;
    this.positionName = positionName;
  }

  public Long getSurveyId() {
    return surveyId;
  }

  public void setSurveyId(Long surveyId) {
    this.surveyId = surveyId;
  }

  public Recruiter getRecruiter() {
    return recruiter;
  }

  public void setRecruiter(Recruiter recruiter) {
    this.recruiter = recruiter;
  }

  public SurveyStatus getStatus() {
    return status;
  }

  public void setStatus(SurveyStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(LocalDateTime creationTime) {
    this.creationTime = creationTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public Candidate getCandidate() {
    return candidate;
  }

  public void setCandidate(Candidate candidate) {
    this.candidate = candidate;
  }

  public Set<SurveyQuestion> getSurveyQuestions() {
    return surveyQuestions;
  }

  public void setSurveyQuestions(Set<SurveyQuestion> surveyQuestions) {
    this.surveyQuestions = surveyQuestions;
  }

  public Set<Recommendation> getRecommendations() {
    return recommendations;
  }

  public void setRecommendations(Set<Recommendation> recommendations) {
    this.recommendations = recommendations;
  }

  public String getPositionName() {
    return positionName;
  }

  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
}
