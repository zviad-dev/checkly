package ru.hh.checkly.service.security;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
  private final AuthenticationService authenticationService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public AuthenticationFilter(AuthenticationService authenticationService, SecurityContextProvider securityContextProvider) {
    this.authenticationService = authenticationService;
    this.securityContextProvider = securityContextProvider;
  }

  @Override
  public void filter(ContainerRequestContext requestContext) {

    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    authenticationService.authenticate(authorizationHeader);

    SecurityContext securityContext = new SecurityContext(authenticationService.getUserInfo(authorizationHeader).getId());
    securityContextProvider.setSecurityContext(securityContext);
  }

}
