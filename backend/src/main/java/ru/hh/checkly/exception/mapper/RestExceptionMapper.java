package ru.hh.checkly.exception.mapper;

import ru.hh.checkly.dto.RestErrorResponse;
import ru.hh.checkly.exception.RestErrorResponseCode;
import ru.hh.checkly.exception.RestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RestExceptionMapper implements ExceptionMapper<RestException> {

  @Override
  public Response toResponse(RestException ex) {
    RestErrorResponseCode errorResponseType = ex.getType();
    return Response.status(errorResponseType.getStatus()).
        entity(new RestErrorResponse(errorResponseType, ex.getInputParameters())).
        type("application/json").
        build();
  }
}
