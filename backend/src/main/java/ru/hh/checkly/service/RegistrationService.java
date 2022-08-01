package ru.hh.checkly.service;

import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.PersonDao;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.entity.Recruiter;
import ru.hh.checkly.exception.RestException;

import javax.inject.Inject;

import static ru.hh.checkly.exception.RestErrorResponseCode.EMAIL_ALREADY_REGISTERED;
import ru.hh.checkly.service.mapping.RecruiterMapper;

public class RegistrationService {
  private final PersonDao personDao;
  private final RecruiterDao recruiterDao;

  @Inject
  public RegistrationService(PersonDao personDao, RecruiterDao recruiterDao) {
    this.recruiterDao = recruiterDao;
    this.personDao = personDao;
  }

  @Transactional
  public RecruiterDto saveRecruiter(RecruiterDto recruiterDto) {
    Recruiter recruiter = RecruiterMapper.toRecruiterEntity(recruiterDto);

    if (!personDao.checkIfPersonEmailExists(recruiter.getEmail())) {
      return RecruiterMapper.toRecruiterDto(recruiterDao.save(recruiter));
    }
    throw new RestException(EMAIL_ALREADY_REGISTERED);
  }
}
