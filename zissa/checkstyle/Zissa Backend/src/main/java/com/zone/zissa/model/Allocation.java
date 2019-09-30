package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.audit.Auditable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** The persistent class for the allocation database table. */
@Entity
@Table(name = "allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Allocation extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3507184272115115419L;

  /** The allocation ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Allocation_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int allocationID;

  /** The from date. */
  @Temporal(TemporalType.DATE)
  @Column(nullable = false, name = "From_Date")
  private Date fromDate;

  /** The to date. */
  @Temporal(TemporalType.DATE)
  @Column(name = "To_Date")
  private Date toDate;

  /** The allocation type. */
  // bi-directional many-to-one association to AllocationType
  @ManyToOne
  @JoinColumn(name = "FK_Allocation_Type_ID", nullable = false)
  private AllocationType allocationType;

  /** The resource. */
  // bi-directional many-to-one association to Resource
  @ManyToOne
  @JoinColumn(name = "FK_Resource_ID", nullable = false)
  private Resource resource;

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

  /** The employee allocations. */
  // bi-directional many-to-one association to EmployeeAllocation
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
  private EmployeeAllocation employeeAllocations;

  /** The other allocations. */
  // bi-directional many-to-one association to OtherAllocation
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
  private OtherAllocation otherAllocations;

  /** The project allocations. */
  // bi-directional many-to-one association to ProjectAllocation
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "allocation")
  private ProjectAllocation projectAllocations;

  /**
   * Instantiates a new allocation.
   */
  public Allocation() {
    // zero argument constructor
  }

  /**
   * Gets the allocation ID.
   *
   * @return the allocation ID
   */
  public int getAllocation_ID() {
    return this.allocationID;
  }

  /**
   * Sets the allocation ID.
   *
   * @param allocationId the new allocation ID
   */
  public void setAllocationID(final int allocationId) {
    this.allocationID = allocationId;
  }

  /**
   * Gets the from date.
   *
   * @return the from date
   */
  public Date getFrom_Date() {
    return this.fromDate;
  }

  /**
   * Sets the from date.
   *
   * @param date the new from date
   */
  public void setFromDate(final Date date) {
    this.fromDate = date;
  }

  /**
   * Gets the to date.
   *
   * @return the to date
   */
  public Date getTo_Date() {
    return this.toDate;
  }

  /**
   * Sets the to date.
   *
   * @param date the new to date
   */
  public void setToDate(final Date date) {
    this.toDate = date;
  }

  /**
   * Gets the allocation type.
   *
   * @return the allocation type
   */
  public AllocationType getAllocationType() {
    return this.allocationType;
  }

  /**
   * Sets the allocation type.
   *
   * @param allocationTypeData the new allocation type
   */
  public void setAllocationType(final AllocationType allocationTypeData) {
    this.allocationType = allocationTypeData;
  }

  /**
   * Gets the resource.
   *
   * @return the resource
   */
  @JsonIgnore
  public Resource getResource() {
    return this.resource;
  }

  /**
   * Sets the resource.
   *
   * @param resourceData the new resource
   */
  public void setResource(final Resource resourceData) {
    this.resource = resourceData;
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
   * @param statusObject the new status
   */
  public void setStatus(final Status statusObject) {
    this.status = statusObject;
  }

  /**
   * Gets the user.
   *
   * @return the user
   */
  @JsonIgnore
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
   * Gets the employee allocations.
   *
   * @return the employee allocations
   */
  public EmployeeAllocation getEmployeeAllocations() {
    return employeeAllocations;
  }

  /**
   * Sets the employee allocations.
   *
   * @param employeeAllocation the new employee allocations
   */
  public void setEmployeeAllocations(
      final EmployeeAllocation employeeAllocation) {
    this.employeeAllocations = employeeAllocation;
  }

  /**
   * Gets the other allocations.
   *
   * @return the other allocations
   */
  public OtherAllocation getOtherAllocations() {
    return otherAllocations;
  }

  /**
   * Sets the other allocations.
   *
   * @param otherAllocation the new other allocations
   */
  public void setOtherAllocations(final OtherAllocation otherAllocation) {
    this.otherAllocations = otherAllocation;
  }

  /**
   * Gets the project allocations.
   *
   * @return the project allocations
   */
  public ProjectAllocation getProjectAllocations() {
    return projectAllocations;
  }

  /**
   * Sets the project allocations.
   *
   * @param projectAllocation the new project allocations
   */
  public void setProjectAllocations(final ProjectAllocation projectAllocation) {
    this.projectAllocations = projectAllocation;
  }
}
