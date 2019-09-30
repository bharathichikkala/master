package com.zone.zissa.exception;

/** AccessDeniedException class extends CustomException. */
public class AccessDeniedException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3174665973943209527L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public AccessDeniedException(final String message) {

    super(message);
  }
}
