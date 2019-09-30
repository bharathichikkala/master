package com.zone.zissa.response;

/**
 * The ServiceResponse class.
 *
 * @param <T> the generic type
 */
public class ServiceResponse<T> {

  /** The status. */
  private int status;

  /** The message. */
  private String message;

  /** The data. */
  private T data;

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
   * @param responseMessage the new message
   */
  public void setMessage(final String responseMessage) {
    this.message = responseMessage;
  }

  /**
   * Gets the status.
   *
   * @return the status
   */
  public int getStatus() {
    return status;
  }

  /**
   * Sets the status.
   *
   * @param responseStatus the new status
   */
  public void setStatus(final int responseStatus) {
    this.status = responseStatus;
  }

  /**
   * Gets the data.
   *
   * @return the data
   */
  public T getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param responseData the new data
   */
  public void setData(final T responseData) {
    this.data = responseData;
  }
}
