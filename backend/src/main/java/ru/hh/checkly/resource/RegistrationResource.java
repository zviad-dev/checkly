package ru.hh.checkly.resource;

import javax.ws.rs.Produces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.service.RegistrationService;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import static ru.hh.checkly.service.validation.RecruiterValidator.validate;

@Path("/registration")
@Api(value = "/registration")
public class RegistrationResource {
  private final RegistrationService registrationService;

  @Inject
  public RegistrationResource(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "registration", response = RecruiterDto.class)
  public RecruiterDto registration (@RequestBody @ApiParam RecruiterDto recruiterDto) {
    validate(recruiterDto);
    return registrationService.saveRecruiter(recruiterDto);
  }
}
