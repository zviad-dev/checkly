package ru.hh.checkly.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruiterEmailDto {

  @JsonProperty("new_email")
  private String newEmail;
  private String password;

  public String getNewEmail() {
    return newEmail;
  }

  public String getPassword() {
    return password;
  }
}
