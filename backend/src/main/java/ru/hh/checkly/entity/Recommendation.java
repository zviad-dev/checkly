package ru.hh.checkly.entity;

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
import java.util.Set;

@Entity
@Table(name = "recommendation")
public class Recommendation {

  @Id
  @Column(name = "recommendation_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long recommendationId;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private RecommendationStatus status;

  @OneToOne(mappedBy = "recommendation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private Recommender recommender;

  @OneToMany(mappedBy = "recommendation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<Answer> answers;

  public Recommendation() {
  }

  public Recommendation(Recommender recommender) {
    this.recommender = recommender;
  }

  public Long getRecommendationId() {
    return recommendationId;
  }

  public void setRecommendationId(Long recommendationId) {
    this.recommendationId = recommendationId;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  public RecommendationStatus getStatus() {
    return status;
  }

  public void setStatus(RecommendationStatus status) {
    this.status = status;
  }

  public Recommender getRecommender() {
    return recommender;
  }

  public void setRecommender(Recommender recommender) {
    this.recommender = recommender;
  }

  public Set<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(Set<Answer> answers) {
    this.answers = answers;
  }
}
