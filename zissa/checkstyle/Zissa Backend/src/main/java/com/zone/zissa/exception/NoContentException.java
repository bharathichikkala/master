package com.zone.zissa.exception;

/** NoContentException class extends CustomException. */
public class NoContentException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6113075970147444974L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public NoContentException(final String message) {

    super(message);
  }
}
