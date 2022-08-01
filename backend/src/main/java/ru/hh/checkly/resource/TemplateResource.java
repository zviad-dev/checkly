package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.template.TemplateDto;
import ru.hh.checkly.dto.template.TemplateNameDto;
import ru.hh.checkly.dto.template.TemplateQuestionDtoShort;
import ru.hh.checkly.service.TemplateService;
import ru.hh.checkly.service.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

import ru.hh.checkly.service.security.SecurityContextProvider;
import static ru.hh.checkly.service.validation.TemplateValidator.*;

@Secured
@Path("recruiters/{recruiterId}")
@Api(value = "/recruiters/{recruiterId}", authorizations = @Authorization("Authorization"))
public class TemplateResource {

  private final TemplateService templateService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public TemplateResource(TemplateService templateService, SecurityContextProvider securityContextProvider) {
    this.templateService = templateService;
    this.securityContextProvider = securityContextProvider;
  }

  @GET
  @Path("/templates/{templateId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get template by id", response = TemplateDto.class)
  public TemplateDto getTemplate(@PathParam(value = "recruiterId") Long recruiterId,
                                 @PathParam(value = "templateId") Long templateId) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return templateService.getTemplateById(recruiterId, templateId);
  }

  @GET
  @Path("/templates")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get templates", response = TemplateDto.class, responseContainer = "Set")
  public Set<TemplateDto> getTemplates(@PathParam(value = "recruiterId") Long recruiterId) {
    validateRecruiterId(recruiterId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return templateService.getTemplates(recruiterId);
  }

  @POST
  @Path("/templates")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "save template", response = TemplateDto.class)
  public TemplateDto saveTemplate(@PathParam(value = "recruiterId") Long recruiterId,
                                  @RequestBody @ApiParam TemplateNameDto templateNameDto) {
    validateRecruiterId(recruiterId);
    validateTemplateName(templateNameDto);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return templateService.saveTemplate(recruiterId, templateNameDto.getName());
  }

  @POST
  @Path("/templates/{templateId}/questions")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "add question to template", response = TemplateDto.class)
  public TemplateDto addTemplateQuestions(@PathParam(value = "recruiterId") Long recruiterId,
                                          @PathParam(value = "templateId") Long templateId,
                                          @RequestBody @ApiParam List<String> questions) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    validateQuestions(questions);

    return templateService.addTemplateQuestions(recruiterId, templateId, questions);
  }

  @PUT
  @Path("/templates/{templateId}/name")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "update template name", response = TemplateDto.class)
  public TemplateDto updateTemplateName(@PathParam(value = "recruiterId") Long recruiterId,
                                        @PathParam(value = "templateId") Long templateId,
                                        @RequestBody @ApiParam TemplateNameDto templateNameDto) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    validateTemplateName(templateNameDto);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return templateService.updateTemplateName(recruiterId, templateId, templateNameDto.getName());
  }

  @PUT
  @Path("/templates/{templateId}/questions/{questionId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "update question", response = TemplateDto.class)
  public TemplateDto updateQuestion(@PathParam(value = "recruiterId") Long recruiterId,
                                    @PathParam(value = "templateId") Long templateId,
                                    @PathParam(value = "questionId") Long questionId,
                                    @RequestBody @ApiParam TemplateQuestionDtoShort templateQuestionDtoShort) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    validateQuestionId(questionId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    validateQuestion(templateQuestionDtoShort);

    return templateService.updateQuestion(recruiterId, templateId, questionId, templateQuestionDtoShort.getQuestion());
  }

  @DELETE
  @Path("/templates/{templateId}/questions/{questionId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "delete question")
  public TemplateDto deleteQuestion(@PathParam(value = "recruiterId") Long recruiterId,
                                    @PathParam(value = "templateId") Long templateId,
                                    @PathParam(value = "questionId") Long questionId) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    validateQuestionId(questionId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return templateService.deleteQuestion(recruiterId, templateId, questionId);
  }

  @DELETE
  @Path("/templates/{templateId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "delete template")
  public Response deleteTemplate(@PathParam(value = "recruiterId") Long recruiterId,
                                 @PathParam(value = "templateId") Long templateId) {
    validateRecruiterId(recruiterId);
    validateTemplateId(templateId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    templateService.deleteTemplate(recruiterId, templateId);
    return Response.status(Response.Status.OK).build();
  }

}
