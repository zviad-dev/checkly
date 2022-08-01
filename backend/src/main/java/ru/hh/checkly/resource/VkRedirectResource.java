package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ru.hh.checkly.service.VkAuthService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import static ru.hh.checkly.service.validation.VkLoginValidator.validate;

@Path("/vk")
@Api(value = "/vk")
public class VkRedirectResource {

  private final VkAuthService vkAuthService;

  @Inject
  public VkRedirectResource(VkAuthService vkAuthService) {
    this.vkAuthService = vkAuthService;
  }

  @GET
  @ApiOperation(value = "check user details", response = String.class)
  public String checkUserDetails(@QueryParam("code") String code,
                                 @QueryParam("firstName") String firstName,
                                 @QueryParam("lastName") String lastName) {

    validate(code, firstName, lastName);
    return vkAuthService.getToken(code, firstName, lastName);
  }

}
