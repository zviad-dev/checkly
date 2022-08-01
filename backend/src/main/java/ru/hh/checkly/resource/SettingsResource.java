package ru.hh.checkly.resource;

import ru.hh.checkly.dto.RecruiterDto;
import ru.hh.checkly.dto.settings.RecruiterEmailDto;
import ru.hh.checkly.dto.settings.RecruiterInfoDto;
import ru.hh.checkly.dto.settings.RecruiterPasswordDto;
import ru.hh.checkly.service.SettingsService;
import ru.hh.checkly.service.security.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.hh.checkly.service.security.SecurityContextProvider;
import static ru.hh.checkly.service.validation.SettingsValidator.validate;

@Path("/recruiter")
public class SettingsResource {

  private final SettingsService settingsService;
  private final SecurityContextProvider securityContextProvider;

  @Inject
  public SettingsResource(SettingsService settingsService, SecurityContextProvider securityContextProvider) {
    this.settingsService = settingsService;
    this.securityContextProvider = securityContextProvider;
  }

  @PUT
  @Path("/{recruiterId}/settings/info")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RecruiterDto updateInfo(@PathParam("recruiterId") Long recruiterId, RecruiterInfoDto recruiterInfoDto) {
    validate(recruiterId, recruiterInfoDto);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return settingsService.updateInfo(recruiterId, recruiterInfoDto);
  }

  @PUT
  @Path("/{recruiterId}/settings/email")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public RecruiterDto updateEmail(@PathParam("recruiterId") Long recruiterId, RecruiterEmailDto recruiterEmailDto) {
    validate(recruiterId, recruiterEmailDto);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return settingsService.updateEmail(recruiterId, recruiterEmailDto);
  }

  @PUT
  @Path("/{recruiterId}/settings/password")
  @Secured
  @Consumes(MediaType.APPLICATION_JSON)
  public String updatePassword(@PathParam("recruiterId") Long recruiterId, RecruiterPasswordDto recruiterPasswordDto) {
    validate(recruiterId, recruiterPasswordDto);
    securityContextProvider.checkIsRecruiterWithId(recruiterId);

    return settingsService.updatePassword(recruiterId, recruiterPasswordDto);
  }


}

