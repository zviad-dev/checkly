package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import ru.hh.checkly.dto.Credentials;
import ru.hh.checkly.dto.LoginResponseDto;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.service.security.AuthenticationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static ru.hh.checkly.service.validation.LoginCredentialsValidator.validate;

@Path("/login")
@Api(value = "/login")
public class AuthenticationResource {

  private final AuthenticationService authenticationService;

  @Inject
  public AuthenticationResource(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "authenticate user", response = String.class)
  public LoginResponseDto authenticateUser(@ApiParam Credentials credentials) {
    validate(credentials);
    return authenticationService.getLoginResponse(credentials);
  }

  @GET
  @Path("/self")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get user info", response = RecruiterDto.class, authorizations = @Authorization("Authorization"))
  public RecruiterDto getUserInfo(@HeaderParam(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    return authenticationService.getUserInfo(authorizationHeader);
  }

}
