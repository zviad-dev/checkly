package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.entity.Recruiter;

public class RecruiterMapper {
  public static Recruiter toRecruiterEntity(RecruiterDto recruiterDto) {
    Recruiter recruiter = new Recruiter();
    recruiter.setFirstName(recruiterDto.getFirstName());
    recruiter.setLastName(recruiterDto.getLastName());
    recruiter.setEmail(recruiterDto.getEmail());
    recruiter.setPassword(recruiterDto.getPassword());
    recruiter.setCompanyName(recruiterDto.getCompanyName());
    return recruiter;
  }

  public static RecruiterDto toRecruiterDto(Recruiter recruiter) {
    RecruiterDto recruiterDto = new RecruiterDto();
    recruiterDto.setId(recruiter.getId());
    recruiterDto.setFirstName(recruiter.getFirstName());
    recruiterDto.setLastName(recruiter.getLastName());
    recruiterDto.setEmail(recruiter.getEmail());
    recruiterDto.setCompanyName(recruiter.getCompanyName());
    recruiterDto.setRegistrationTime(recruiter.getRegistrationTime());
    return recruiterDto;
  }

}
