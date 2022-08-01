package ru.hh.checkly.dto.vk;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VkUsersResponse {
  @JsonProperty("response")
  private List<VkUserInfo> responseList;

  public List<VkUserInfo> getResponseList() {
    return responseList;
  }

}
