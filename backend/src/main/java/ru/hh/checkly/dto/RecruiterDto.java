package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class RecruiterDto {

  private long id;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  private String email;
  @JsonProperty("company_name")
  private String companyName;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String password;

  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonProperty("registration_time")
  private LocalDateTime registrationTime;

  public RecruiterDto() {
  }

  public RecruiterDto(String firstName, String lastName, String email, String companyName, String password, LocalDateTime registrationTime) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.companyName = companyName;
    this.password = password;
    this.registrationTime = registrationTime;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getCompanyName() {
    return companyName;
  }

  public String getPassword() {
    return password;
  }

  public LocalDateTime getRegistrationTime() {
    return registrationTime;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRegistrationTime(LocalDateTime registrationTime) {
    this.registrationTime = registrationTime;
  }
}
