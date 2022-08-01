package ru.hh.checkly.service.security;

import static ru.hh.checkly.exception.RestErrorResponseCode.NO_ACCESS;
import ru.hh.checkly.exception.RestException;

public class SecurityContextProvider {

  private static final ThreadLocal<SecurityContext> globalUserInfo = new ThreadLocal<>();

  public SecurityContextProvider() {

  }

  public void setSecurityContext(SecurityContext securityContext) {
    globalUserInfo.set(securityContext);
  }

  public void checkIsRecruiterWithId(Long recruiterId) {
    if (recruiterId != globalUserInfo.get().getId()) {
      throw new RestException(NO_ACCESS);
    }
  }

}
