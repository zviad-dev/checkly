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
import javax.persistence.Table;

@Entity
@Table(name = "answer")
public class Answer {
  @Id
  @Column(name = "answer_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long answerId;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "question_id", nullable = false)
  private SurveyQuestion surveyQuestion;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
  @JoinColumn(name = "recommendation_id", nullable = false)
  private Recommendation recommendation;

  @Column(name = "answer")
  private String answer;

  public Answer() {
  }

  public Answer(String answer) {
    this.answer = answer;
  }

  public Long getAnswerId() {
    return answerId;
  }

  public void setAnswerId(Long answerId) {
    this.answerId = answerId;
  }

  public SurveyQuestion getSurveyQuestion() {
    return surveyQuestion;
  }

  public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
    this.surveyQuestion = surveyQuestion;
  }

  public Recommendation getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(Recommendation recommendation) {
    this.recommendation = recommendation;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }
}
