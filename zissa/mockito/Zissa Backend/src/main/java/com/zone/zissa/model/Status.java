package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the status database table. */
@Entity
@Table(name = "status")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Status implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3254922699320644773L;

  /** The status ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Status_ID")
  private byte statusID;

  /** The status name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Status_Name")
  private String statusName;

  /** The allocations. */
  // bi-directional many-to-one association to Allocation
  @OneToMany(mappedBy = "status")
  private Set<Allocation> allocations;

  /** The resources. */
  // bi-directional many-to-one association to Resource
  @OneToMany(mappedBy = "status")
  private List<Resource> resources;

  /**
   * Instantiates a new status.
   */
  public Status() {
    // zero argument constructor
  }

  /**
   * Gets the status ID.
   *
   * @return the status ID
   */
  public byte getStatus_ID() {
    return this.statusID;
  }

  /**
   * Sets the status ID.
   *
   * @param statusId the new status ID
   */
  public void setStatusID(final byte statusId) {
    this.statusID = statusId;
  }

  /**
   * Gets the status name.
   *
   * @return the status name
   */
  public String getStatus_Name() {
    return this.statusName;
  }

  /**
   * Sets the status name.
   *
   * @param name the new status name
   */
  public void setStatusName(final String name) {
    this.statusName = name;
  }

  /**
   * Gets the allocations.
   *
   * @return the allocations
   */
  @JsonIgnore
  public Set<Allocation> getAllocations() {
    return this.allocations;
  }

  /**
   * Sets the allocations.
   *
   * @param allocationData the new allocations
   */
  public void setAllocations(final Set<Allocation> allocationData) {
    this.allocations = allocationData;
  }

  /**
   * Gets the resources.
   *
   * @return the resources
   */
  @JsonIgnore
  public List<Resource> getResources() {
    return this.resources;
  }

  /**
   * Sets the resources.
   *
   * @param resourcesData the new resources
   */
  public void setResources(final List<Resource> resourcesData) {
    this.resources = resourcesData;
  }
}
