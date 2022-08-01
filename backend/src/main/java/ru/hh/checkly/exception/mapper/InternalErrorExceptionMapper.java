package ru.hh.checkly.exception.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hh.checkly.dto.RestErrorResponse;
import ru.hh.checkly.exception.RestErrorResponseCode;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static ru.hh.checkly.exception.RestErrorResponseCode.INTERNAL_SERVER_ERROR;

@Provider
public class InternalErrorExceptionMapper implements ExceptionMapper<Exception> {
  private static final RestErrorResponseCode INTERNAL_ERROR_RESPONSE_CODE = INTERNAL_SERVER_ERROR;
  private static final Logger LOGGER = LoggerFactory.getLogger(InternalErrorExceptionMapper.class);

  @Override
  public Response toResponse(Exception ex) {
    LOGGER.error("Internal error", ex);
    return Response.status(INTERNAL_ERROR_RESPONSE_CODE.getStatus()).
        entity(new RestErrorResponse(INTERNAL_ERROR_RESPONSE_CODE)).
        type("application/json").
        build();
  }
}
