package com.zone.zissa.model;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Error class.
 */
public class Error {

  /** The context. */
  private String context;

  /** The code. */
  private String code;

  /** The message. */
  private String message;

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(Error.class);

  /**
   * Gets the context.
   *
   * @return the context
   */
  public String getContext() {
    return context;
  }

  /**
   * Sets the context.
   *
   * @param contextData the new context
   */
  public void setContext(final String contextData, final Throwable th) {
    this.context = contextData;
    try {
      th.printStackTrace(new PrintWriter(context));
    } catch (Exception e) {
      LOGGER.error("Error", e);
      context = th.getLocalizedMessage();
    }

  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * Sets the code.
   *
   * @param codeData the new code
   */
  public void setCode(final String codeData) {
    this.code = codeData;
  }

  /**
   * Gets the message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message.
   *
   * @param errorMessage the new message
   */
  public void setMessage(final String errorMessage) {
    this.message = errorMessage;
  }

}
