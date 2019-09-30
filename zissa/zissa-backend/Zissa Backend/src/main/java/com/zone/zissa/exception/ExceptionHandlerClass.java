package com.zone.zissa.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zone.zissa.response.ServiceResponse;

/**
 * Controller responsible for handling Exceptions
 */
@ControllerAdvice
public class ExceptionHandlerClass {

    /**
     * this is constructor
     */
    private ExceptionHandlerClass() {

    }

    /**
     * Responsible for handling BadRequestException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(BadRequestException.class)
    public static ServiceResponse badRequestException(CustomException ex) {

        int status = HttpServletResponse.SC_BAD_REQUEST;
        return handle(ex, status);

    }

    /**
     * Responsible for handling AccessDeniedException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(AccessDeniedException.class)
    public static ServiceResponse accessDeniedException(CustomException ex) {

        int status = HttpServletResponse.SC_FORBIDDEN;
        return handle(ex, status);
    }

    /**
     * Responsible for handling NotFoundException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(NotFoundException.class)
    public static ServiceResponse notFoundException(CustomException ex) {

        int status = HttpServletResponse.SC_NOT_FOUND;
        return handle(ex, status);

    }

    /**
     * Responsible for handling ConflictException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(ConflictException.class)
    public static ServiceResponse conflictException(CustomException ex) {

        int status = HttpServletResponse.SC_CONFLICT;
        return handle(ex, status);

    }

    /**
     * Responsible for handling NoContentException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(NoContentException.class)
    public static ServiceResponse noContentException(CustomException ex) {

        int status = HttpServletResponse.SC_NO_CONTENT;
        return handle(ex, status);

    }

    /**
     * Responsible for handling SuccessException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(DataNotFoundException.class)
    public static ServiceResponse dataNotFoundException(CustomException ex) {

        int status = HttpServletResponse.SC_OK;
        return handle(ex, status);

    }

    /**
     * Responsible for handling SuccessException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(DataToLongException.class)
    public static ServiceResponse dataToLongException(CustomException ex) {

        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        return handle(ex, status);

    }

    /**
     * handle method
     * 
     * @param ex
     * @param status
     *
     * @return ServiceResponse
     */
    private static ServiceResponse handle(CustomException ex, int status) {
        ServiceResponse response = new ServiceResponse<>();

        response.setStatus(status);
        response.setMessage(ex.getMessage());
        response.setErrorMessage(ex.getError());

        return response;
    }

}
