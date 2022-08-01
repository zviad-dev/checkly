package ru.hh.checkly.exception;

import javax.ws.rs.core.Response;

public enum RestErrorResponseCode {
  INTERNAL_SERVER_ERROR(Response.Status.INTERNAL_SERVER_ERROR),
  INVALID_USER_OR_PASSWORD(Response.Status.FORBIDDEN),
  AUTHORIZATION_TOKEN_IS_NOT_VALID(Response.Status.UNAUTHORIZED),
  VK_AUTHORIZATION_ERROR(Response.Status.FORBIDDEN),
  VK_CREDENTIALS_ERROR(Response.Status.FORBIDDEN),
  NO_ACCESS(Response.Status.UNAUTHORIZED),

  // NOT_FOUND
  RECRUITER_NOT_FOUND(Response.Status.NOT_FOUND),
  // BAD_REQUEST
  EMPTY_BODY(Response.Status.BAD_REQUEST),
  EMPTY_TEMPLATE_NAME(Response.Status.BAD_REQUEST),
  EMPTY_FIRST_NAME(Response.Status.BAD_REQUEST),
  EMPTY_POSITION_NAME(Response.Status.BAD_REQUEST),
  EMPTY_COMPANY_NAME(Response.Status.BAD_REQUEST),
  EMPTY_PASSWORD(Response.Status.BAD_REQUEST),
  EMPTY_LAST_NAME(Response.Status.BAD_REQUEST),
  EMPTY_EMAIL(Response.Status.BAD_REQUEST),
  EMPTY_QUESTION(Response.Status.BAD_REQUEST),
  EMPTY_DTO(Response.Status.BAD_REQUEST),
  EMAIL_ALREADY_REGISTERED(Response.Status.BAD_REQUEST),
  INVALID_RECRUITER_ID(Response.Status.BAD_REQUEST),
  INVALID_TEMPLATE_ID(Response.Status.BAD_REQUEST),
  INVALID_QUESTION_ID(Response.Status.BAD_REQUEST),
  INVALID_SURVEY_ID(Response.Status.BAD_REQUEST),
  INVALID_RECOMMENDATION_STATUS(Response.Status.BAD_REQUEST),
  INVALID_EMAIL(Response.Status.BAD_REQUEST),
  INVALID_RECOMMENDATION_ID(Response.Status.BAD_REQUEST),
  INVALID_FIRST_NAME(Response.Status.BAD_REQUEST),
  INVALID_LAST_NAME(Response.Status.BAD_REQUEST),
  INVALID_QUESTION(Response.Status.BAD_REQUEST),
  SHORTER_THAN_8_SIGNS_PASSWORD(Response.Status.BAD_REQUEST),
  OLD_AND_NEW_PASSWORDS_ARE_THE_SAME(Response.Status.BAD_REQUEST),
  INVALID_SURVEY_STATUS(Response.Status.BAD_REQUEST),
  EMPTY_RECOMMENDATION_LIST(Response.Status.BAD_REQUEST),
  RECOMMENDER_ALREADY_EXISTS(Response.Status.BAD_REQUEST),
  INVALID_ANSWER(Response.Status.BAD_REQUEST),
  INVALID_PASSWORD(Response.Status.BAD_REQUEST);

  private final Response.Status status;

  RestErrorResponseCode(Response.Status status) {
    this.status = status;
  }

  public Response.Status getStatus() {
    return status;
  }

}
