package com.zone.zissa.exception;

/** DataNotFoundException class extends CustomException. */
public class DataNotFoundException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1035538289232487777L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public DataNotFoundException(final String message) {

    super(message);
  }
}
