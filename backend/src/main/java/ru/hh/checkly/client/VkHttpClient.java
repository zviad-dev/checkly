package ru.hh.checkly.client;

import ru.hh.checkly.dto.vk.VkTokenResponse;
import ru.hh.nab.common.properties.FileSettings;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;

public class VkHttpClient {

  private final String url;
  private final String redirectUrl;
  private final int clientId;
  private final String clientSecret;
  private final Client client;

  @Inject
  public VkHttpClient(Client client, FileSettings fileSettings) {
    this.client = client;
    this.url = fileSettings.getString("vk.oauth.url");
    this.redirectUrl = fileSettings.getString("vk.redirect.url");
    this.clientId = fileSettings.getInteger("vk.clientId");
    this.clientSecret = fileSettings.getString("vk.clientSecret");
  }

  public VkTokenResponse requestToken(String code, String firstName, String lastName) {
    return client
      .target(url)
      .path("/access_token")
      .queryParam("client_id", clientId)
      .queryParam("client_secret", clientSecret)
      .queryParam("redirect_uri", redirectUrl + String.format("?firstName=%s&lastName=%s", firstName, lastName))
      .queryParam("code", code)
      .request(MediaType.APPLICATION_JSON)
      .get(VkTokenResponse.class);
  }

}
