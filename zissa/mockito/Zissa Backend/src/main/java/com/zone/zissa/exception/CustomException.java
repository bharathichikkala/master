package com.zone.zissa.exception;

/**
 * CustomException class extends RuntimeException.
 */
public class CustomException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6124642297250089254L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public CustomException(final String message) {
    super(message);
  }
}
