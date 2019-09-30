package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.audit.Auditable;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** The persistent class for the resource database table. */
@Entity
@Table(name = "resource")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Resource extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 969545852807168608L;

  /** The resource ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Resource_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int resourceID;

  /** The code. */
  @Column(unique = true, nullable = false,
      length = RestApiMessageConstants.LENGTH)
  private String code;

  /** The allocations. */
  // bi-directional many-to-one association to Allocation
  @OneToMany(mappedBy = "resource")
  private Set<Allocation> allocations;

  /** The category. */
  // bi-directional many-to-one association to Category
  @ManyToOne
  @JoinColumn(name = "FK_Category_ID", nullable = false)
  private Category category;

  /** The status. */
  // bi-directional many-to-one association to Status
  @ManyToOne
  @JoinColumn(name = "FK_Status_ID", nullable = false)
  private Status status;

  /** The user. */
  // bi-directional many-to-one association to User
  @ManyToOne
  @JoinColumn(name = "FK_Create_User_ID", nullable = false)
  private User user;

  /** The resource attributes. */
  // bi-directional many-to-one association to ResourceAttribute
  @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
  private List<ResourceAttribute> resourceAttributes;

  /**
   * Instantiates a new resource.
   */
  public Resource() {
    // zero argument constructor
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
   * Gets the allocations.
   *
   * @return the allocations
   */
  public Set<Allocation> getAllocations() {
    return this.allocations;
  }

  /**
   * Sets the allocations.
   *
   * @param allocationsData the new allocations
   */
  public void setAllocations(final Set<Allocation> allocationsData) {
    this.allocations = allocationsData;
  }

  /**
   * Gets the category.
   *
   * @return the category
   */
  public Category getCategory() {
    return this.category;
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
   * Gets the status.
   *
   * @return the status
   */
  public Status getStatus() {
    return this.status;
  }

  /**
   * Sets the status.
   *
   * @param statusData the new status
   */
  public void setStatus(final Status statusData) {
    this.status = statusData;
  }

  /**
   * Gets the user.
   *
   * @return the user
   */
  public User getUser() {
    return this.user;
  }

  /**
   * Sets the user.
   *
   * @param userData the new user
   */
  public void setUser(final User userData) {
    this.user = userData;
  }

  /**
   * Gets the resource attributes.
   *
   * @return the resource attributes
   */
  public List<ResourceAttribute> getResourceAttributes() {
    return this.resourceAttributes;
  }

  /**
   * Sets the resource attributes.
   *
   * @param resourceAttributesData the new resource attributes
   */
  public void setResourceAttributes(
      final List<ResourceAttribute> resourceAttributesData) {
    this.resourceAttributes = resourceAttributesData;
  }
}
