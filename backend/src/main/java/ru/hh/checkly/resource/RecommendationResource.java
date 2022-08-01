package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.RecommenderDto;
import ru.hh.checkly.dto.SurveyBaseDto;
import ru.hh.checkly.exception.RestException;
import ru.hh.checkly.service.RecommendationService;
import ru.hh.checkly.service.security.SecurityContextProvider;
import ru.hh.checkly.service.validation.RecommenderValidator;
import ru.hh.checkly.service.security.Secured;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import static ru.hh.checkly.exception.RestErrorResponseCode.EMPTY_BODY;
import static ru.hh.checkly.service.validation.PathParamsValidator.validateRecruiterId;
import static ru.hh.checkly.service.validation.PathParamsValidator.validateSurveyId;
import static ru.hh.checkly.service.validation.PathParamsValidator.validateRecommendationId;
import static ru.hh.checkly.service.validation.RecommenderValidator.validate;

@Secured
@Path("/recruiters/{recruiterId}/surveys/{surveyId}/recommendations")
@Api(value = "/recruiters/{recruiterId}/surveys/{surveyId}/recommendations", authorizations = @Authorization("Authorization"))
public class RecommendationResource {

  private final RecommendationService recommendationService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public RecommendationResource(RecommendationService recommendationService, SecurityContextProvider securityContextProvider) {
    this.recommendationService = recommendationService;
    this.securityContextProvider = securityContextProvider;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get recommendations", response = SurveyBaseDto.class)
  public SurveyBaseDto getSurveyRecommendations(@PathParam(value = "recruiterId") Long recruiterId,
                                                @PathParam(value = "surveyId") Long surveyId) {
    validateRecruiterId(recruiterId);
    validateSurveyId(surveyId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return recommendationService.getSurveyById(recruiterId, surveyId);
  }

  @GET
  @Path(value = "/{recommendationId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get recommendation", response = SurveyBaseDto.class)
  public SurveyBaseDto getSurveyRecommendation(@PathParam(value = "recruiterId") Long recruiterId,
                                               @PathParam(value = "surveyId") Long surveyId,
                                               @PathParam(value = "recommendationId") Long recommendationId) {
    validateRecruiterId(recruiterId);
    validateSurveyId(surveyId);
    validateRecommendationId(recommendationId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return recommendationService.getRecommendation(recruiterId, surveyId, recommendationId);
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "save recommenders", response = SurveyBaseDto.class)
  public SurveyBaseDto saveRecommenders(@PathParam(value = "recruiterId") Long recruiterId,
                                        @PathParam(value = "surveyId") Long surveyId,
                                        @RequestBody @ApiParam Set<RecommenderDto> recommenderDtoSet) {
    validateRecruiterId(recruiterId);
    validateSurveyId(surveyId);
    if (recommenderDtoSet == null) {
      throw new RestException(EMPTY_BODY);
    }
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    recommenderDtoSet
      .forEach(RecommenderValidator::validate);

    return recommendationService.saveRecommenders(recruiterId, surveyId, recommenderDtoSet);
  }

  @PUT
  @Path(value = "/{recommendationId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "update recommender", response = SurveyBaseDto.class)
  public SurveyBaseDto updateRecommender(@PathParam(value = "recruiterId") Long recruiterId,
                                         @PathParam(value = "surveyId") Long surveyId,
                                         @PathParam(value = "recommendationId") Long recommendationId,
                                         @RequestBody @ApiParam RecommenderDto recommenderDto) {
    validateRecruiterId(recruiterId);
    validateSurveyId(surveyId);
    validateRecommendationId(recommendationId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    validate(recommenderDto);

    return recommendationService.updateRecommender(recruiterId, surveyId, recommendationId, recommenderDto);
  }

  @DELETE
  @Path(value = "/{recommendationId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "delete recommendation")
  public void deleteRecommender(@PathParam(value = "recruiterId") Long recruiterId,
                                   @PathParam(value = "surveyId") Long surveyId,
                                   @PathParam(value = "recommendationId") Long recommendationId) {
    validateRecruiterId(recruiterId);
    validateSurveyId(surveyId);
    validateRecommendationId(recommendationId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    recommendationService.deleteRecommendation(recruiterId, surveyId, recommendationId);
  }
}
