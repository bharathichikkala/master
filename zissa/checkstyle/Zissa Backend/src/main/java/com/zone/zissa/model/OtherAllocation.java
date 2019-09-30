package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** The persistent class for the other_allocation database table. */
@Entity
@Table(name = "other_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtherAllocation implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1348776691193961558L;

  /** The other allocation ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Other_Allocation_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int otherAllocationID;

  /** The assignee name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Assignee_Name")
  private String assigneeName;

  /** The allocation. */
  // bi-directional many-to-one association to Allocation
  @OneToOne
  @JoinColumn(name = "FK_Allocation_ID", nullable = false)
  private Allocation allocation;

  /**
   * Instantiates a new other allocation.
   */
  public OtherAllocation() {
    // zero argument constructor
  }

  /**
   * Gets the other allocation ID.
   *
   * @return the other allocation ID
   */
  public int getOther_Allocation_ID() {
    return this.otherAllocationID;
  }

  /**
   * Sets the other allocation ID.
   *
   * @param otherAllocationId the new other allocation ID
   */
  public void setOtherAllocationID(final int otherAllocationId) {
    this.otherAllocationID = otherAllocationId;
  }

  /**
   * Gets the assignee name.
   *
   * @return the assignee name
   */
  public String getAssignee_Name() {
    return this.assigneeName;
  }

  /**
   * Sets the assignee name.
   *
   * @param name the new assignee name
   */
  public void setAssigneeName(final String name) {
    this.assigneeName = name;
  }

  /**
   * Gets the allocation.
   *
   * @return the allocation
   */
  @JsonIgnore
  public Allocation getAllocation() {
    return this.allocation;
  }

  /**
   * Sets the allocation.
   *
   * @param allocationData the new allocation
   */
  public void setAllocation(final Allocation allocationData) {
    this.allocation = allocationData;
  }
}
