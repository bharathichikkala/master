package com.zone.zissa.exception;


/** DataTruncateException class extends CustomException. */
public class DataToLongException extends CustomException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7942123186370063755L;

  /**
   * This is the constructor.
   *
   * @param message the message
   */
  public DataToLongException(final String message) {
    super(message);
  }
}
