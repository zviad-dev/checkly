package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.CandidateDto;
import ru.hh.checkly.entity.Candidate;

public class CandidateMapper {

  public static Candidate toCandidateEntity(CandidateDto candidateDto) {
    return new Candidate(candidateDto.getFirstName(),
        candidateDto.getLastName(),
        candidateDto.getEmail());
  }

  public static CandidateDto toCandidateDto(Candidate candidate) {
    return new CandidateDto(candidate.getCandidateFirstName(),
      candidate.getCandidateLastName(),
      candidate.getCandidateEmail());
  }
}
