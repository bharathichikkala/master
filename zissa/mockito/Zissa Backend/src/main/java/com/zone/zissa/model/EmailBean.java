package com.zone.zissa.model;

/**
 * Email Notifications Class.
 */
public class EmailBean {

  /** The to. */
  private String to;

  /** The from. */
  private String from;

  /** The subject. */
  private String subject;

  /** The message. */
  private String message;

  /** The attachment. */
  private String attachment;

  /** The recipients. */
  private String[] recipients;

  /**
   * Gets the to.
   *
   * @return the to
   */
  public String getTo() {
    return to;
  }

  /**
   * Sets the to.
   *
   * @param toData the new to
   */
  public void setTo(final String toData) {
    this.to = toData;
  }

  /**
   * Gets the subject.
   *
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * Sets the subject.
   *
   * @param subjectData the new subject
   */
  public void setSubject(final String subjectData) {
    this.subject = subjectData;
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
   * @param messageData the new message
   */
  public void setMessage(final String messageData) {
    this.message = messageData;
  }

  /**
   * Gets the attachment.
   *
   * @return the attachment
   */
  public String getAttachment() {
    return attachment;
  }

  /**
   * Sets the attachment.
   *
   * @param attachmentData the new attachment
   */
  public void setAttachment(final String attachmentData) {
    this.attachment = attachmentData;
  }

  /**
   * Gets the from.
   *
   * @return the from
   */
  public String getFrom() {
    return from;
  }

  /**
   * Sets the from.
   *
   * @param fromData the new from
   */
  public void setFrom(final String fromData) {
    this.from = fromData;
  }

  /**
   * Gets the recipients.
   *
   * @return the recipients
   */
  public String[] getRecipients() {
    return recipients;
  }

  /**
   * Sets the recipients.
   *
   * @param recipientsData the new recipients
   */
  public void setRecipients(final String[] recipientsData) {
    this.recipients = recipientsData;
  }
}
