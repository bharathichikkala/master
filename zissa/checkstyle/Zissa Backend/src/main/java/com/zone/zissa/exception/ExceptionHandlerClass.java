package com.zone.zissa.exception;

import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.ServiceResponse;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ldap.NamingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller responsible for handling Exceptions.
 */
@ControllerAdvice
public final class ExceptionHandlerClass {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ExceptionHandlerClass.class);

  /** The exception message. */
  private String exceptionMessage = "Internal Server Error";

  /**
   * this is constructor.
   */
  private ExceptionHandlerClass() {
    /** No implementation */
  }


  /**
   * Handle.
   *
   * @param ex the ex
   * @return the service response
   */
  @ExceptionHandler(JSONException.class)
  public @ResponseBody ServiceResponse handle(final JSONException ex) {

    int status = HttpServletResponse.SC_BAD_REQUEST;
    String message = "JSON parse error";
    LOGGER.error(message);
    return handleException(status, message);
  }

  /**
   * Responsible for handling DataIntegrityViolationException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseBody
  ServiceResponse handle(final DataIntegrityViolationException ex) {

    int status = HttpServletResponse.SC_CONFLICT;
    String message =
        "Cannot delete a parent row: a foreign key constraint fails";
    LOGGER.error(message);
    return handleException(status, message);
  }

  /**
   * Responsible for handling Exception globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(Exception.class)
  public @ResponseBody ServiceResponse handle(final Exception ex) {

    int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    String message = exceptionMessage;
    LOGGER.error(message);
    return handleException(status, message);
  }

  /**
   * Responsible for handling IOException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(IOException.class)
  public @ResponseBody ServiceResponse handle(final IOException ex) {

    int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    String message = exceptionMessage;
    LOGGER.error(message);
    return handleException(status, message);
  }

  /**
   * Responsible for handling NamingException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(NamingException.class)
  public @ResponseBody ServiceResponse handle(final NamingException ex) {

    int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    String message = exceptionMessage;
    LOGGER.error(message);
    return handleException(status, message);
  }

  /**
   * Responsible for handling BadRequestException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(BadRequestException.class)
  public @ResponseBody ServiceResponse handle(final BadRequestException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_BAD_REQUEST;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling AccessDeniedException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(AccessDeniedException.class)
  public @ResponseBody ServiceResponse handle(final AccessDeniedException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_FORBIDDEN;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling NotFoundException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(NotFoundException.class)
  public @ResponseBody ServiceResponse handle(final NotFoundException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_NOT_FOUND;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling NoContentException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(NoContentException.class)
  public @ResponseBody ServiceResponse handle(final NoContentException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_NO_CONTENT;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling SuccessException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(DataNotFoundException.class)
  public @ResponseBody ServiceResponse handle(final DataNotFoundException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_OK;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling SuccessException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(DataToLongException.class)
  public ServiceResponse handle(final DataToLongException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    return handleResponse(ex, status);
  }

  /**
   * Responsible for handling ConflictException globally.
   *
   * @param ex the ex
   * @return ServiceResponse
   */
  @ExceptionHandler(ConflictException.class)
  public @ResponseBody ServiceResponse handle(final ConflictException ex) {
    LOGGER.error(ex.getMessage());
    int status = HttpServletResponse.SC_CONFLICT;
    return handleResponse(ex, status);
  }

  /**
   * handleException Method.
   *
   * @param status the status
   * @param message the message
   * @return ServiceResponse
   */
  private ServiceResponse handleException(final int status,
      final String message) {
    ServiceResponse response = new ServiceResponse();
    response.setStatus(status);
    response.setMessage(message);
    return response;
  }

  /**
   * handleAttributeResponse method.
   *
   * @param status the status
   * @param message the message
   * @return AttrServiceResponse
   */
  public AttrServiceResponse handleAttributeResponse(final int status,
      final String message) {
    AttrServiceResponse response = new AttrServiceResponse();

    response.setStatus(status);
    response.setMessage(message);

    return response;
  }

  /**
   * handlePageServiceResponse method.
   *
   * @param status the status
   * @param message the message
   * @return PageServiceResponse
   */
  public PageServiceResponse handlePageServiceResponse(final int status,
      final String message) {
    PageServiceResponse response = new PageServiceResponse();

    response.setStatus(status);
    response.setMessage(message);

    return response;
  }


  /**
   * Handle response.
   *
   * @param ex the ex
   * @param status the status
   * @return the service response
   */
  private ServiceResponse handleResponse(final CustomException ex,
      final int status) {
    ServiceResponse response = new ServiceResponse();

    response.setStatus(status);
    response.setMessage(ex.getMessage());

    return response;
  }
}
