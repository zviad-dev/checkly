package ru.hh.checkly.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import ru.hh.checkly.dto.statistics.RecruiterStatisticsDto;
import ru.hh.checkly.service.StatisticsService;
import ru.hh.checkly.service.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.hh.checkly.service.security.SecurityContextProvider;
import static ru.hh.checkly.service.validation.TemplateValidator.validateRecruiterId;

@Secured
@Path("recruiters/{recruiterId}/surveys")
@Api(value = "/recruiters/{recruiterId}/surveys", authorizations = @Authorization("Authorization"))
public class StatisticsResource {

  private final StatisticsService statisticsService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public StatisticsResource(StatisticsService statisticsService, SecurityContextProvider securityContextProvider) {
    this.statisticsService = statisticsService;
    this.securityContextProvider = securityContextProvider;
  }

  @GET
  @Path("/statistics")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get recruiter statistics", response = RecruiterStatisticsDto.class)
  public RecruiterStatisticsDto getRecruiterStatistics(@PathParam("recruiterId") Long recruiterId) {
    validateRecruiterId(recruiterId);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);
    return statisticsService.getRecruiterStatistics(recruiterId);
  }

}
