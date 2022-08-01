package ru.hh.checkly.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hh.checkly.dao.RecruiterDao;
import ru.hh.checkly.dao.TemplateDao;
import ru.hh.checkly.dao.TemplateQuestionDao;
import ru.hh.checkly.dto.template.TemplateDto;
import ru.hh.checkly.entity.Recruiter;
import ru.hh.checkly.entity.Template;
import ru.hh.checkly.entity.TemplateQuestion;
import ru.hh.checkly.exception.RestException;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.hh.checkly.exception.RestErrorResponseCode.*;
import ru.hh.checkly.service.mapping.TemplateMapper;

@Service
public class TemplateService {

  private final TemplateDao templateDao;
  private final RecruiterDao recruiterDao;
  private final TemplateQuestionDao templateQuestionDao;

  @Inject
  public TemplateService(TemplateDao templateDao, RecruiterDao recruiterDao, TemplateQuestionDao templateQuestionDao) {
    this.templateDao = templateDao;
    this.recruiterDao = recruiterDao;
    this.templateQuestionDao = templateQuestionDao;
  }

  @Transactional
  public TemplateDto getTemplateById(Long recruiterId, Long templateId) {
    return templateDao.getTemplateById(templateId)
        .filter(template -> Objects.equals(template.getRecruiter().getId(), recruiterId))
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public Set<TemplateDto> getTemplates(Long recruiterId) {
    return recruiterDao.getRecruiterById(recruiterId)
        .map(Recruiter::getTemplates)
        .map(templates -> templates.stream()
            .map(TemplateMapper::toTemplateDto)
            .collect(Collectors.toSet()))
        .orElseThrow(() -> new RestException(INVALID_RECRUITER_ID));
  }

  @Transactional
  public TemplateDto saveTemplate(Long recruiterId, String templateName) {
    return recruiterDao.getRecruiterById(recruiterId)
        .map(recruiter -> new Template(recruiter,templateName,Collections.emptySet()))
        .map(templateDao::save)
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public TemplateDto updateTemplateName(Long recruiterId, Long templateId, String name) {
    return templateDao.getTemplateById(templateId)
        .filter(template -> Objects.equals(template.getRecruiter().getId(), recruiterId))
        .map(template -> {
          template.setTemplateName(name);
          return template;
        })
        .map(templateDao::update)
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public TemplateDto addTemplateQuestions(Long recruiterId, Long templateId, List<String> questions) {
    return templateDao.getTemplateById(templateId)
        .filter(template -> Objects.equals(template.getRecruiter().getId(), recruiterId))
        .map(template -> {
          template.getQuestions().addAll(questions.stream()
              .map(TemplateQuestion::new)
              .peek(templateQuestion -> templateQuestion.setTemplate(template))
              .peek(templateQuestionDao::save)
              .collect(Collectors.toSet()));
          return template;
        })
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public TemplateDto updateQuestion(Long recruiterId, Long templateId, Long questionId, String question) {
    return templateDao.getTemplateById(templateId)
        .filter(template -> Objects.equals(template.getRecruiter().getId(), recruiterId))
        .map(template -> {
          template.getQuestions().stream()
              .filter(templateQuestion -> Objects.equals(templateQuestion.getQuestionId(), questionId))
              .peek(templateQuestion -> templateQuestion.setQuestion(question))
              .findAny()
              .orElseThrow(() -> new RestException(INVALID_QUESTION_ID));
          return template;
        })
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public TemplateDto deleteQuestion(Long recruiterId, Long templateId, Long questionId) {
    return templateDao.getTemplateById(templateId)
        .filter(template -> Objects.equals(template.getRecruiter().getId(), recruiterId))
        .map(template -> {
          template.getQuestions().remove(template.getQuestions().stream()
              .filter(templateQuestion -> Objects.equals(templateQuestion.getQuestionId(), questionId))
              .peek(templateQuestionDao::delete)
              .findAny()
              .orElseThrow(() -> new RestException(INVALID_QUESTION_ID)));
          return template;
        })
        .map(TemplateMapper::toTemplateDto)
        .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID));
  }

  @Transactional
  public void deleteTemplate(Long recruiterId, Long templateId) {
    recruiterDao.getRecruiterById(recruiterId)
        .map(recruiter -> {
          recruiter.getTemplates().remove(recruiter.getTemplates().stream()
              .filter(template -> Objects.equals(template.getTemplateId(), templateId))
              .peek(templateDao::delete)
              .findAny()
              .orElseThrow(() -> new RestException(INVALID_TEMPLATE_ID)));
          return recruiter;
        }).orElseThrow(() -> new RestException(INVALID_RECRUITER_ID));
  }

}
