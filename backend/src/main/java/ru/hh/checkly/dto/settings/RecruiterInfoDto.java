package ru.hh.checkly.dto.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecruiterInfoDto {

  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("company_name")
  private String companyName;

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getCompanyName() {
    return companyName;
  }
}
