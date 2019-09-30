package com.zone.zissa.exception;

/**
 * CustomException class extends RuntimeException
 */

public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String error;

    /**
     * This is the constructor
     */
    public CustomException(String message, String error) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
