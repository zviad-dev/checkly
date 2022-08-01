package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDto {
  private final String token;
  @JsonProperty("recruiter_info")
  private final RecruiterDto recruiterDto;

  public LoginResponseDto(String token, RecruiterDto recruiterDto) {
    this.token = token;
    this.recruiterDto = recruiterDto;
  }

  public String getToken() {
    return token;
  }

  public RecruiterDto getRecruiterDto() {
    return recruiterDto;
  }
}
