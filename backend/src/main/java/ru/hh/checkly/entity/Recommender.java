package ru.hh.checkly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "recommender")
public class Recommender {

  @Id
  @Column(name = "recommender_id")
  private Long recommenderId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "recommender_id")
  private Recommendation recommendation;

  @Column(name = "recommender_first_name", nullable = false)
  private String recommenderFirstName;

  @Column(name = "recommender_last_name", nullable = false)
  private String recommenderLastName;

  @Column(name = "recommender_email", nullable = false)
  private String recommenderEmail;

  public Recommender() {
  }

  public Recommender(String recommenderFirstName, String recommenderLastName, String recommenderEmail) {
    this.recommenderFirstName = recommenderFirstName;
    this.recommenderLastName = recommenderLastName;
    this.recommenderEmail = recommenderEmail;
  }

  public Long getRecommenderId() {
    return recommenderId;
  }

  public void setRecommenderId(Long recommenderId) {
    this.recommenderId = recommenderId;
  }

  public Recommendation getRecommendation() {
    return recommendation;
  }

  public void setRecommendation(Recommendation recommendation) {
    this.recommendation = recommendation;
  }

  public String getRecommenderFirstName() {
    return recommenderFirstName;
  }

  public void setRecommenderFirstName(String recommenderFirstName) {
    this.recommenderFirstName = recommenderFirstName;
  }

  public String getRecommenderLastName() {
    return recommenderLastName;
  }

  public void setRecommenderLastName(String recommenderLastName) {
    this.recommenderLastName = recommenderLastName;
  }

  public String getRecommenderEmail() {
    return recommenderEmail;
  }

  public void setRecommenderEmail(String recommenderEmail) {
    this.recommenderEmail = recommenderEmail;
  }
}
