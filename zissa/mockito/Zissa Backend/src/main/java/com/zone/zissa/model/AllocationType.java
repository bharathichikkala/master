package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the allocation_type database table. */
@Entity
@Table(name = "allocation_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllocationType implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6925562635912658444L;

  /** The allocation type ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Allocation_Type_ID")
  private byte allocationTypeID;

  /** The allocation name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Allocation_Name")
  private String allocationName;

  /** The allocations. */
  // bi-directional many-to-one association to Allocation
  @OneToMany(mappedBy = "allocationType")
  private Set<Allocation> allocations;

  /**
   * Instantiates a new allocation type.
   */
  public AllocationType() {
    // zero argument constructor
  }

  /**
   * Gets the allocation type ID.
   *
   * @return the allocation type ID
   */
  public byte getAllocation_Type_ID() {
    return this.allocationTypeID;
  }

  /**
   * Sets the allocation type ID.
   *
   * @param allocationTypeId the new allocation type ID
   */
  public void setAllocationTypeID(final byte allocationTypeId) {
    this.allocationTypeID = allocationTypeId;
  }

  /**
   * Gets the allocation name.
   *
   * @return the allocation name
   */
  public String getAllocation_Name() {
    return this.allocationName;
  }

  /**
   * Sets the allocation name.
   *
   * @param name the new allocation name
   */
  public void setAllocationName(final String name) {
    this.allocationName = name;
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
}
