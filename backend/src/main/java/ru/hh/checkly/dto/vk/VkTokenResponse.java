package ru.hh.checkly.dto.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkTokenResponse {

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("user_id")
  private int userId;

  private String email;

  public int getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getAccessToken() {
    return accessToken;
  }

}
