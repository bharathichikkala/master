package com.zone.zissa.exception;

/** ConflictException class extends CustomException. */
public class ConflictException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -7281466302650401387L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public ConflictException(final String message) {

    super(message);
  }
}
