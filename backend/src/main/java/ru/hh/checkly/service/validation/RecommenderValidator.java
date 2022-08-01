package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.RecommenderDto;
import ru.hh.checkly.exception.RestException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_BODY;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_EMAIL;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_FIRST_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_LAST_NAME;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_EMAIL;

public class RecommenderValidator {

  public static void validate(RecommenderDto recommenderDto) {
    if (recommenderDto == null) {
      throw new RestException(EMPTY_BODY);
    }

    if (StringUtils.isEmpty(recommenderDto.getFirstName())) {
      throw new RestException(EMPTY_FIRST_NAME);
    }

    if (StringUtils.isEmpty(recommenderDto.getLastName())) {
      throw new RestException(EMPTY_LAST_NAME);
    }

    if (StringUtils.isEmpty(recommenderDto.getEmail())) {
      throw new RestException(EMPTY_EMAIL);
    }

    try {
      InternetAddress address = new InternetAddress(recommenderDto.getEmail());
      address.validate();
    }
    catch (AddressException addressException) {
      throw new RestException(INVALID_EMAIL);
    }
  }
}
