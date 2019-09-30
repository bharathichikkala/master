package com.zone.zissa.audit;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Generic class for auditing.
 *
 * @param <U> the generic type
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

  /** The created by. */
  @CreatedBy
  private U createdBy;

  /** The created date. */
  @CreatedDate
  @Temporal(TIMESTAMP)
  private Date createdDate;

  /** The last modified by. */
  @LastModifiedBy
  private U lastModifiedBy;

  /** The last modified date. */
  @LastModifiedDate
  @Temporal(TIMESTAMP)
  private Date lastModifiedDate;

  /**
   * Gets the created by.
   *
   * @return the created by
   */
  public U getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the created by.
   *
   * @param createdUser the new created by
   */
  public void setCreatedBy(final U createdUser) {
    this.createdBy = createdUser;
  }

  /**
   * Gets the created date.
   *
   * @return the created date
   */
  public Date getCreatedDate() {
    return createdDate;
  }

  /**
   * Sets the created date.
   *
   * @param date the new created date
   */
  public void setCreatedDate(final Date date) {
    this.createdDate = date;
  }

  /**
   * Gets the last modified by.
   *
   * @return the last modified by
   */
  public U getLastModifiedBy() {
    return lastModifiedBy;
  }

  /**
   * Sets the last modified by.
   *
   * @param lastModifiedUser the new last modified by
   */
  public void setLastModifiedBy(final U lastModifiedUser) {
    this.lastModifiedBy = lastModifiedUser;
  }

  /**
   * Gets the last modified date.
   *
   * @return the last modified date
   */
  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  /**
   * Sets the last modified date.
   *
   * @param modifiedDate the new last modified date
   */
  public void setLastModifiedDate(final Date modifiedDate) {
    this.lastModifiedDate = modifiedDate;
  }
}
