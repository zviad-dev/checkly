package ru.hh.checkly.exception;

public class RestException extends RuntimeException {
  private final RestErrorResponseCode type;
  private String[] inputParameters;

  public RestException(RestErrorResponseCode type) {
    this.type = type;
  }

  public RestException(RestErrorResponseCode type, String... parameters) {
    this(type);
    this.inputParameters = parameters;
  }

  public RestErrorResponseCode getType() {
    return type;
  }

  public String[] getInputParameters() {
    return inputParameters;
  }
}
