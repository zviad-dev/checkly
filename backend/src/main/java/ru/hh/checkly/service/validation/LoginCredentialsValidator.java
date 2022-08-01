package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.Credentials;
import ru.hh.checkly.exception.RestErrorResponseCode;
import ru.hh.checkly.exception.RestException;

public class LoginCredentialsValidator {

  public static void validate(Credentials credentials) {
    if (credentials == null ||
      StringUtils.isEmpty(credentials.getEmail()) ||
      StringUtils.isEmpty(credentials.getPassword())
    ) {
      throw new RestException(RestErrorResponseCode.INVALID_USER_OR_PASSWORD);
    }
  }

}
