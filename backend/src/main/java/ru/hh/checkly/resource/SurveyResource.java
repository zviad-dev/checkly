package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import java.util.Set;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.CandidateWithPositionDto;
import ru.hh.checkly.dto.SurveyDto;
import ru.hh.checkly.dto.SurveyQuestionChangeRequestDto;
import ru.hh.checkly.service.SurveyService;
import ru.hh.checkly.service.security.Secured;
import ru.hh.checkly.service.security.SecurityContextProvider;
import static ru.hh.checkly.service.validation.CandidateValidator.validate;
import static ru.hh.checkly.service.validation.SurveyValidator.checkSurveyPathParams;
import static ru.hh.checkly.service.validation.SurveyValidator.checkSurveyQuestionChangeRequestDto;
import static ru.hh.checkly.service.validation.SurveyValidator.checkSurveyQuestionsAddRequest;
import static ru.hh.checkly.service.validation.SurveyValidator.checkSurveyTemplatePathParams;

@Secured
@Path("/recruiters/{recruiterId}/surveys/")
@Api(value = "/recruiters/{recruiterId}/surveys", authorizations = @Authorization("Authorization"))
public class SurveyResource {

  public static final String RECRUITER_ID_PARAM = "recruiterId";
  public static final String SURVEY_ID_PARAM = "surveyId";
  public static final String QUESTION_ID_PARAM = "questionId";
  public static final String TEMPLATE_ID_PARAM = "templateId";
  private final SurveyService surveyService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public SurveyResource(SurveyService surveyService, SecurityContextProvider securityContextProvider) {
    this.surveyService = surveyService;
    this.securityContextProvider = securityContextProvider;
  }

  @GET
  @ApiOperation(value = "get surveys", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public Set<SurveyDto> getSurveys(@PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyPathParams(recruiterId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.getSurveysByRecruiterId(recruiterId);
  }

  @GET
  @Path("/{surveyId}")
  @ApiOperation(value = "get survey by id", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public SurveyDto getSurvey(@PathParam(SURVEY_ID_PARAM) Long surveyId,
                             @PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.getSurveyById(surveyId, recruiterId);
  }

  @POST
  @ApiOperation(value = "create survey", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public SurveyDto createSurvey(@RequestBody @ApiParam CandidateWithPositionDto candidateDto,
                                @PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyPathParams(recruiterId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    validate(candidateDto);

    return surveyService.saveSurvey(candidateDto, recruiterId);
  }

  @PUT
  @Path("/{surveyId}")
  @ApiOperation(value = "change candidate info", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public SurveyDto changeCandidate(@RequestBody @ApiParam CandidateWithPositionDto candidateDto,
                                   @PathParam(SURVEY_ID_PARAM) Long surveyId,
                                   @PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    validate(candidateDto);

    return surveyService.updateCandidate(candidateDto, surveyId, recruiterId);
  }

  @POST
  @Path("/{surveyId}/questions")
  @ApiOperation(value = "add questions", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public SurveyDto addQuestions(@RequestBody @ApiParam Set<String> questions,
                                @PathParam(SURVEY_ID_PARAM) Long surveyId,
                                @PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    checkSurveyQuestionsAddRequest(questions);

    return surveyService.addQuestions(questions, surveyId, recruiterId);
  }

  @POST
  @Path("/{surveyId}/questions/from-template/{templateId}")
  @ApiOperation(value = "add questions from template", response = SurveyDto.class)
  @Produces(MediaType.APPLICATION_JSON)
  public SurveyDto addQuestionsFromTemplate(@PathParam(SURVEY_ID_PARAM) Long surveyId,
                                            @PathParam(TEMPLATE_ID_PARAM) Long templateId,
                                            @PathParam(RECRUITER_ID_PARAM) Long recruiterId) {
    checkSurveyTemplatePathParams(recruiterId, surveyId, templateId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.addQuestionsFromTemplate(templateId, surveyId, recruiterId);
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{surveyId}/questions/{questionId}")
  @ApiOperation(value = "change question", response = SurveyDto.class)
  public SurveyDto changeQuestion(@RequestBody @ApiParam SurveyQuestionChangeRequestDto changeDto,
                                          @PathParam(RECRUITER_ID_PARAM) Long recruiterId,
                                          @PathParam(SURVEY_ID_PARAM) Long surveyId,
                                          @PathParam(QUESTION_ID_PARAM) Long questionId) {
    checkSurveyPathParams(recruiterId, surveyId, questionId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    checkSurveyQuestionChangeRequestDto(changeDto);

    return surveyService.changeSurveyQuestion(surveyId, changeDto.getQuestion(), recruiterId, questionId);
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{surveyId}/questions/{questionId}")
  @ApiOperation(value = "delete question", response = SurveyDto.class)
  public SurveyDto deleteQuestion(@PathParam(RECRUITER_ID_PARAM) Long recruiterId,
                             @PathParam(SURVEY_ID_PARAM) Long surveyId,
                             @PathParam(QUESTION_ID_PARAM) Long questionId) {
    checkSurveyPathParams(recruiterId, surveyId, questionId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.deleteSurveyQuestion(surveyId, recruiterId, questionId);
  }

  @PUT
  @Path("/{surveyId}/send")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "send email to recommenders", response = SurveyDto.class)
  public SurveyDto sendSurvey(@PathParam(RECRUITER_ID_PARAM) Long recruiterId,
                              @PathParam(SURVEY_ID_PARAM) Long surveyId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.sendSurvey(surveyId, recruiterId);
  }

  @PUT
  @Path("/{surveyId}/close")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "close survey", response = SurveyDto.class)
  public SurveyDto closeSurvey(@PathParam(RECRUITER_ID_PARAM) Long recruiterId,
                               @PathParam(SURVEY_ID_PARAM) Long surveyId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return surveyService.closeSurvey(surveyId, recruiterId);
  }

  @DELETE
  @Path("/{surveyId}")
  @ApiOperation(value = "delete survey")
  public void deleteSurvey(@PathParam(RECRUITER_ID_PARAM) Long recruiterId,
                           @PathParam(SURVEY_ID_PARAM) Long surveyId) {
    checkSurveyPathParams(recruiterId, surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    surveyService.deleteSurvey(surveyId, recruiterId);
  }

}
