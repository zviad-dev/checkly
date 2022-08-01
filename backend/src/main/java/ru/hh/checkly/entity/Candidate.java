package ru.hh.checkly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "candidate")
public class Candidate {

  @Id
  @Column(name = "candidate_id")
  private Long candidateId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "candidate_id")
  private Survey survey;

  @Column(name = "candidate_first_name", nullable = false)
  private String candidateFirstName;

  @Column(name = "candidate_last_name", nullable = false)
  private String candidateLastName;

  @Column(name = "candidate_email", nullable = false)
  private String candidateEmail;

  public Candidate() {
  }

  public Candidate(String candidateFirstName, String candidateLastName, String candidateEmail) {
    this.candidateFirstName = candidateFirstName;
    this.candidateLastName = candidateLastName;
    this.candidateEmail = candidateEmail;
  }

  public Long getCandidateId() {
    return candidateId;
  }

  public void setCandidateId(Long candidateId) {
    this.candidateId = candidateId;
  }

  public Survey getSurvey() {
    return survey;
  }

  public void setSurvey(Survey survey) {
    this.survey = survey;
  }

  public String getCandidateFirstName() {
    return candidateFirstName;
  }

  public void setCandidateFirstName(String candidateFirstName) {
    this.candidateFirstName = candidateFirstName;
  }

  public String getCandidateLastName() {
    return candidateLastName;
  }

  public void setCandidateLastName(String candidateLastName) {
    this.candidateLastName = candidateLastName;
  }

  public String getCandidateEmail() {
    return candidateEmail;
  }

  public void setCandidateEmail(String candidateEmail) {
    this.candidateEmail = candidateEmail;
  }
}
