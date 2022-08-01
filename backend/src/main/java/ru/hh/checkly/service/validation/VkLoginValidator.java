package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.exception.RestException;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class VkLoginValidator {

  public static void validate(String code, String firstName, String lastName) {

    if (StringUtils.isEmpty(code)) {
      throw new RestException(VK_AUTHORIZATION_ERROR);
    }
    if (StringUtils.isEmpty(firstName)) {
      throw new RestException(EMPTY_FIRST_NAME);
    }
    if (StringUtils.isEmpty(lastName)) {
      throw new RestException(EMPTY_LAST_NAME);
    }

  }

}
