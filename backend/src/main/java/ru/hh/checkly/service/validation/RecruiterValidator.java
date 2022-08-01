package ru.hh.checkly.service.validation;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.exception.RestException;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class RecruiterValidator {

  public static void validate(RecruiterDto recruiterDto) {
    if (recruiterDto == null) {
      throw new RestException(EMPTY_BODY);
    }

    String firstName = recruiterDto.getFirstName();
    String lastName = recruiterDto.getLastName();
    String email = recruiterDto.getEmail();
    String password = recruiterDto.getPassword();

    if (StringUtils.isEmpty(firstName)) {
      throw new RestException(EMPTY_FIRST_NAME);
    }

    if (!firstName.matches("[а-яёА-ЯЁ]+|[a-zA-Z]+")) {
      throw new RestException(INVALID_FIRST_NAME);
    }

    if (StringUtils.isEmpty(lastName)) {
      throw new RestException(EMPTY_LAST_NAME);
    }

    if (!lastName.matches("[а-яёА-ЯЁ]+|[a-zA-Z]+")) {
      throw new RestException(INVALID_LAST_NAME);
    }

    if (StringUtils.isEmpty(email)) {
      throw new RestException(EMPTY_EMAIL);
    }

    if (StringUtils.isEmpty(recruiterDto.getCompanyName())) {
      throw new RestException(EMPTY_COMPANY_NAME);
    }

    if (StringUtils.isEmpty(password)) {
      throw new RestException(EMPTY_PASSWORD);
    }

    if(!password.matches("[a-zA-Z0-9]+")) {
      throw new RestException(INVALID_PASSWORD);
    }

    if (password.length() < 8) {
      throw new RestException(SHORTER_THAN_8_SIGNS_PASSWORD);
    }

    try {
      InternetAddress address = new InternetAddress(email);
      address.validate();
    }
    catch (AddressException addressException) {
      throw new RestException(INVALID_EMAIL);
    }
  }
}
