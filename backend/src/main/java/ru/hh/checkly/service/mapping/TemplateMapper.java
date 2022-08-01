package ru.hh.checkly.service.mapping;

import ru.hh.checkly.dto.template.TemplateDto;
import ru.hh.checkly.dto.template.TemplateQuestionDto;
import ru.hh.checkly.entity.Template;

import java.util.stream.Collectors;

public class TemplateMapper {

  public static TemplateDto toTemplateDto(Template template) {
    return new TemplateDto(template.getTemplateId(),
        template.getTemplateName(),
        template.getQuestions()
            .stream()
            .map(templateQuestion -> new TemplateQuestionDto(templateQuestion.getQuestionId(), templateQuestion.getQuestion()))
            .collect(Collectors.toSet()));
  }
}
