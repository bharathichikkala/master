package com.zone.zissa.exception;

/** NotFoundException class extends CustomException. */
public class NotFoundException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3928617166016209937L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public NotFoundException(final String message) {

    super(message);
  }
}
