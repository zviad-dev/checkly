package ru.hh.checkly.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruiterPasswordDto {

  @JsonProperty("old_password")
  private String oldPassword;
  @JsonProperty("new_password")
  private String newPassword;

  public String getOldPassword() {
    return oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }
}
