package ru.hh.checkly.service.validation;

import org.apache.commons.lang3.StringUtils;
import ru.hh.checkly.dto.settings.RecruiterEmailDto;
import ru.hh.checkly.dto.settings.RecruiterInfoDto;
import ru.hh.checkly.dto.settings.RecruiterPasswordDto;
import ru.hh.checkly.exception.RestErrorResponseCode;
import ru.hh.checkly.exception.RestException;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;

public class SettingsValidator {

  public static void validate(Long id, RecruiterInfoDto recruiterInfoDto) {
    validateId(id);
    if (recruiterInfoDto == null) {
      throw new RestException(RestErrorResponseCode.EMPTY_BODY);
    }
    if (StringUtils.isEmpty(recruiterInfoDto.getFirstName())) {
      throw new RestException(EMPTY_FIRST_NAME);
    }
    if (StringUtils.isEmpty(recruiterInfoDto.getLastName())) {
      throw new RestException(EMPTY_LAST_NAME);
    }
    if (StringUtils.isEmpty(recruiterInfoDto.getCompanyName())) {
      throw new RestException(EMPTY_COMPANY_NAME);
    }
  }

  public static void validate(Long id, RecruiterEmailDto recruiterEmailDto) {
    validateId(id);
    if (recruiterEmailDto == null) {
      throw new RestException(RestErrorResponseCode.EMPTY_BODY);
    }
    if (StringUtils.isEmpty(recruiterEmailDto.getNewEmail())) {
      throw new RestException(EMPTY_EMAIL);
    }
    if (StringUtils.isEmpty(recruiterEmailDto.getPassword())) {
      throw new RestException(EMPTY_PASSWORD);
    }
  }

  public static void validate(Long id, RecruiterPasswordDto recruiterPasswordDto) {
    validateId(id);
    if (recruiterPasswordDto == null) {
      throw new RestException(RestErrorResponseCode.EMPTY_BODY);
    }
    if (StringUtils.isEmpty(recruiterPasswordDto.getNewPassword())) {
      throw new RestException(EMPTY_PASSWORD);
    }
    if (StringUtils.isEmpty(recruiterPasswordDto.getOldPassword())) {
      throw new RestException(EMPTY_PASSWORD);
    }
    if (recruiterPasswordDto.getNewPassword().length() < 8) {
      throw new RestException(SHORTER_THAN_8_SIGNS_PASSWORD);
    }
    if (recruiterPasswordDto.getNewPassword().equals(recruiterPasswordDto.getOldPassword())) {
      throw new RestException(OLD_AND_NEW_PASSWORDS_ARE_THE_SAME);
    }
  }

  private static void validateId(Long id) {
    if (id == null || id < 1) {
      throw new RestException(RestErrorResponseCode.INVALID_RECRUITER_ID);
    }
  }

}
