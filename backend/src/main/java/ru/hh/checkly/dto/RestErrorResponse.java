package ru.hh.checkly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.hh.checkly.exception.RestErrorResponseCode;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

public class RestErrorResponse {
  @JsonProperty("reason")
  private final RestErrorResponseCode errorCode;
  @JsonProperty("invalid_parameters")
  @JsonInclude(NON_NULL)
  private String[] inputParameters;

  public RestErrorResponse(RestErrorResponseCode reason) {
    this.errorCode = reason;
  }

  public RestErrorResponse(RestErrorResponseCode reason, String[] inputParameters) {
    this.errorCode = reason;
    this.inputParameters = inputParameters;
  }

  public RestErrorResponseCode getErrorCode() {
    return errorCode;
  }

  public String[] getInputParameters() {
    return inputParameters;
  }
}
