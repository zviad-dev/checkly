package ru.hh.checkly.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.dto.settings.RecruiterEmailDto;
import ru.hh.checkly.dto.settings.RecruiterInfoDto;
import ru.hh.checkly.dto.settings.RecruiterPasswordDto;
import ru.hh.checkly.entity.Recruiter;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMAIL_ALREADY_REGISTERED;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_RECRUITER_ID;
import static ru.hh.checkly.exception.RestErrorResponseCode.INVALID_USER_OR_PASSWORD;
import ru.hh.checkly.exception.RestException;
import ru.hh.checkly.service.security.AuthenticationService;

import javax.inject.Inject;

import static ru.hh.checkly.service.mapping.RecruiterMapper.toRecruiterDto;

public class SettingsService {

  private final RecruiterDao recruiterDao;
  private final AuthenticationService authenticationService;

  @Inject
  public SettingsService(RecruiterDao recruiterDao, AuthenticationService authenticationService) {
    this.recruiterDao = recruiterDao;
    this.authenticationService = authenticationService;
  }

  @Transactional
  public RecruiterDto updateInfo(Long id, RecruiterInfoDto recruiterInfoDto) {
    Recruiter recruiter = recruiterDao.getRecruiterById(id)
      .orElseThrow(() -> new RestException(INVALID_RECRUITER_ID));
    recruiter.setFirstName(recruiterInfoDto.getFirstName());
    recruiter.setLastName(recruiterInfoDto.getLastName());
    recruiter.setCompanyName(recruiterInfoDto.getCompanyName());
    return toRecruiterDto(recruiterDao.update(recruiter));
  }

  @Transactional
  public RecruiterDto updateEmail(Long id, RecruiterEmailDto recruiterEmailDto) {
    if (recruiterDao.getRecruiterByEmail(recruiterEmailDto.getNewEmail()).isPresent()) {
      throw new RestException(EMAIL_ALREADY_REGISTERED);
    }
    Recruiter recruiter = recruiterDao.getRecruiterById(id)
      .orElseThrow(() -> new RestException(INVALID_RECRUITER_ID));
    if (!recruiter.getPassword().equals(recruiterEmailDto.getPassword())) {
      throw new RestException(INVALID_USER_OR_PASSWORD);
    }
    recruiter.setEmail(recruiterEmailDto.getNewEmail());
    return toRecruiterDto(recruiterDao.update(recruiter));
  }

  @Transactional
  public String updatePassword(Long id, RecruiterPasswordDto recruiterPasswordDto) {
    Recruiter recruiter = recruiterDao.getRecruiterById(id)
      .orElseThrow(() -> new RestException(INVALID_RECRUITER_ID));
    if (!recruiter.getPassword().equals(recruiterPasswordDto.getOldPassword())) {
      throw new RestException(INVALID_USER_OR_PASSWORD);
    }
    recruiter.setPassword(recruiterPasswordDto.getNewPassword());
    return authenticationService.issueToken(recruiterDao.update(recruiter).getId());
  }
}
