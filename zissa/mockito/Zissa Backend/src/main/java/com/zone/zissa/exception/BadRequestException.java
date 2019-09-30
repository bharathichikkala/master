package com.zone.zissa.exception;

/** BadRequestException class extends CustomException. */
public class BadRequestException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3054369808197526485L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public BadRequestException(final String message) {
    super(message);
  }
}
