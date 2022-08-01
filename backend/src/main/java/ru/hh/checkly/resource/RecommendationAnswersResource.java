package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.AnswerDto;
import ru.hh.checkly.dto.RecommendationAnswersDto;
import ru.hh.checkly.service.RecommendationAnswersService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static ru.hh.checkly.service.validation.AnswerValidator.validateAnswer;
import static ru.hh.checkly.service.validation.PathParamsValidator.validateAnswerId;
import static ru.hh.checkly.service.validation.PathParamsValidator.validateRecommendationId;

@Path("/recommendations/{recommendationId}/answers")
@Api(value = "/recommendations/{recommendationId}/answers", authorizations = @Authorization("Authorization"))
public class RecommendationAnswersResource {

  private final RecommendationAnswersService recommendationAnswersService;

  @Inject
  public RecommendationAnswersResource(RecommendationAnswersService recommendationAnswersService) {
    this.recommendationAnswersService = recommendationAnswersService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get recommendation answers", response = RecommendationAnswersDto.class)
  public RecommendationAnswersDto getRecommendationAnswers(@PathParam(value = "recommendationId") Long recommendationId) {
    validateRecommendationId(recommendationId);

    return recommendationAnswersService.getRecommendation(recommendationId);
  }

  @PUT
  @Path("/{answerId}")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "save recommendation answer", response = RecommendationAnswersDto.class)
  public RecommendationAnswersDto saveAnswers(@PathParam(value = "recommendationId") Long recommendationId,
                                              @PathParam(value = "answerId") Long answerId,
                                              @RequestBody @ApiParam AnswerDto answerDto) {
    validateRecommendationId(recommendationId);
    validateAnswerId(answerId);
    validateAnswer(answerDto);

    return recommendationAnswersService.saveRecommendationAnswer(recommendationId, answerId, answerDto);
  }

  @POST
  @Path("/close")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "close recommendation", response = Response.class)
  public Response saveAnswers(@PathParam(value = "recommendationId") Long recommendationId) {
    validateRecommendationId(recommendationId);
    recommendationAnswersService.closeRecommendation(recommendationId);

    return Response.status(Response.Status.OK).build();
  }
}
