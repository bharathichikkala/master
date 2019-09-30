package com.zone.zissa.model;

import com.zone.zissa.audit.Auditable;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** The persistent class for the resourcebin database table. */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Resourcebin extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -5562971486422588326L;

  /** The resource ID. */
  @Id
  @Column(unique = true, nullable = false, name = "resource_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int resourceID;

  /** The code. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH)
  private String code;

  // bi-directional many-to-one association to Category

  /** The category. */
  @ManyToOne
  @JoinColumn(name = "FK_Category_ID", nullable = false)
  private Category category;

  /** The k create user ID. */
  @Column(nullable = false, name = "fk_create_user_id")
  private int fKCreateUserID;

  /** The k status ID. */
  @Column(nullable = false, name = "fk_status_id")
  private byte fKStatusID;

  /** The dispose reason. */
  @Column(nullable = false)
  private String disposeReason;

  /** The resourcebin attributes. */
  // bi-directional many-to-one association to ResourcebinAttribute
  @OneToMany(mappedBy = "resourcebin", cascade = CascadeType.ALL)
  private List<ResourcebinAttribute> resourcebinAttributes;

  /**
   * Instantiates a new resourcebin.
   */
  public Resourcebin() {
    // zero argument constructor
  }

  /**
   * Gets the dispose reason.
   *
   * @return the dispose reason
   */
  public String getDisposeReason() {
    return disposeReason;
  }

  /**
   * Sets the dispose reason.
   *
   * @param reason the new dispose reason
   */
  public void setDisposeReason(final String reason) {
    this.disposeReason = reason;
  }

  /**
   * Gets the resource ID.
   *
   * @return the resource ID
   */
  public int getResource_ID() {
    return this.resourceID;
  }

  /**
   * Gets the code.
   *
   * @return the code
   */
  public String getCode() {
    return this.code;
  }

  /**
   * Sets the resource ID.
   *
   * @param resourceId the new resource ID
   */
  public void setResourceID(final int resourceId) {
    this.resourceID = resourceId;
  }

  /**
   * Sets the code.
   *
   * @param inventoryCode the new code
   */
  public void setCode(final String inventoryCode) {
    this.code = inventoryCode;
  }

  /**
   * Gets the category.
   *
   * @return the category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Sets the category.
   *
   * @param categoryData the new category
   */
  public void setCategory(final Category categoryData) {
    this.category = categoryData;
  }

  /**
   * Gets the fK create user ID.
   *
   * @return the fK create user ID
   */
  public int getFK_Create_User_ID() {
    return this.fKCreateUserID;
  }

  /**
   * Sets the FK create user ID.
   *
   * @param createUserId the new FK create user ID
   */
  public void setFKCreateUserID(final int createUserId) {
    this.fKCreateUserID = createUserId;
  }

  /**
   * Gets the fK status ID.
   *
   * @return the fK status ID
   */
  public byte getFK_Status_ID() {
    return this.fKStatusID;
  }

  /**
   * Sets the FK status ID.
   *
   * @param statusId the new FK status ID
   */
  public void setFKStatusID(final byte statusId) {
    this.fKStatusID = statusId;
  }

  /**
   * Gets the resourcebin attributes.
   *
   * @return the resourcebin attributes
   */
  public List<ResourcebinAttribute> getResourcebinAttributes() {
    return this.resourcebinAttributes;
  }

  /**
   * Sets the resourcebin attributes.
   *
   * @param resourcebinAttributesData the new resourcebin attributes
   */
  public void setResourcebinAttributes(
      final List<ResourcebinAttribute> resourcebinAttributesData) {
    this.resourcebinAttributes = resourcebinAttributesData;
  }
}
