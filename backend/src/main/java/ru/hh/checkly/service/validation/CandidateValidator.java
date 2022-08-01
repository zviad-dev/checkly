package ru.hh.checkly.service.validation;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.commons.lang3.StringUtils;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_DTO;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_EMAIL;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_FIRST_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_LAST_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_POSITION_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_EMAIL;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_FIRST_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_LAST_NAME;

import ru.hh.checkly.dto.CandidateWithPositionDto;
import ru.hh.checkly.exception.RestException;

public class CandidateValidator {

  public static void validate(CandidateWithPositionDto candidateDto) {
    if (candidateDto == null) {
      throw new RestException(EMPTY_DTO);
    }

    String firstName = candidateDto.getFirstName();
    String lastName = candidateDto.getLastName();
    String email = candidateDto.getEmail();
    String positionName = candidateDto.getPositionName();

    if (StringUtils.isEmpty(firstName)) {
      throw new RestException(EMPTY_FIRST_NAME);
    }
    /*
    if (!firstName.matches("[а-яёА-ЯЁ]+|[a-zA-Z]+")) {
      throw new RestException(INVALID_FIRST_NAME);
    }
    */

    if (StringUtils.isEmpty(lastName)) {
      throw new RestException(EMPTY_LAST_NAME);
    }
    /*
    if (!lastName.matches("[а-яёА-ЯЁ]+|[a-zA-Z]+")) {
      throw new RestException(INVALID_LAST_NAME);
    }
    */

    if (StringUtils.isEmpty(email)) {
      throw new RestException(EMPTY_EMAIL);
    }

    if (StringUtils.isEmpty(positionName)) {
      throw new RestException(EMPTY_POSITION_NAME);
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
