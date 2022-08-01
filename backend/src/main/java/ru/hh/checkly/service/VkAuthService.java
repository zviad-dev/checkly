package ru.hh.checkly.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import ru.hh.checkly.client.VkHttpClient;
import ru.hh.checkly.dto.vk.VkTokenResponse;
import ru.hh.checkly.dto.vk.VkUsersResponse;
import ru.hh.checkly.exception.RestException;

import javax.inject.Inject;
import java.io.IOException;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class VkAuthService {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final VkApiClient vkApiClient;
  private final VkHttpClient vkHttpClient;

  @Inject
  public VkAuthService(VkHttpClient vkHttpClient) {
    this.vkApiClient = new VkApiClient(new HttpTransportClient());
    this.vkHttpClient = vkHttpClient;
  }

  public String getToken(String code, String firstName, String lastName) {

    VkTokenResponse vkTokenResponse = vkHttpClient.requestToken(code, firstName, lastName);
    if (checkUserDetails(vkTokenResponse, firstName, lastName)) {
      return "VK credentials check success";
    } else {
      throw new RestException(VK_CREDENTIALS_ERROR);
    }

  }

  private boolean checkUserDetails(VkTokenResponse vkTokenResponse, String firstName, String lastName) {
    UserActor actor = new UserActor(vkTokenResponse.getUserId(), vkTokenResponse.getAccessToken());
    try {
      String response = vkApiClient.users().get(actor).executeAsString();
      VkUsersResponse vkUsersResponse = objectMapper.readValue(response, VkUsersResponse.class);
      String vkFirstName = vkUsersResponse.getResponseList().get(0).getFirstName();
      String vkLastName = vkUsersResponse.getResponseList().get(0).getLastName();
      return firstName.equals(vkFirstName) && lastName.equals(vkLastName);
    } catch (ClientException e) {
      throw new RestException(VK_AUTHORIZATION_ERROR);
    } catch (IOException e) {
      throw new RestException(INTERNAL_SERVER_ERROR);
    }
  }

}
