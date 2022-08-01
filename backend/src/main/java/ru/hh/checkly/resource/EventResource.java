package ru.hh.checkly.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.EventDto;
import ru.hh.checkly.dto.SurveyBaseDto;
import ru.hh.checkly.dto.ViewEventDto;
import ru.hh.checkly.service.EventService;
import ru.hh.checkly.service.NotificationService;
import ru.hh.checkly.service.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

import static ru.hh.checkly.service.validation.PathParamsValidator.validateRecruiterId;

@Secured
@Path("/recruiters/{recruiterId}/events")
public class EventResource {

  private final EventService eventService;
  private final NotificationService notificationService;

  @Inject
  public EventResource(EventService eventService, NotificationService notificationService) {
    this.eventService = eventService;
    this.notificationService = notificationService;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get all events", response = EventDto[].class)
  public List<EventDto> getAllEvents(@PathParam(value = "recruiterId") Long recruiterId) {
    validateRecruiterId(recruiterId);
    return eventService.getAll(recruiterId);
  }

  @POST
  @Path(value = "/view")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "get all events", response = SurveyBaseDto.class)
  public void viewEvents(@PathParam(value = "recruiterId") Long recruiterId,
                         @RequestBody @ApiParam ViewEventDto dto) {
    validateRecruiterId(recruiterId);
    eventService.viewEvents(recruiterId, Optional.ofNullable(dto).map(ViewEventDto::getIds).orElse(null));
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "delete events")
  public void deleteEvents(@PathParam(value = "recruiterId") Long recruiterId) {
    validateRecruiterId(recruiterId);
    eventService.deleteAllByPersonId(recruiterId);
  }
}
