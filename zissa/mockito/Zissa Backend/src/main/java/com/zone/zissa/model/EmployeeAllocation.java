package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** The persistent class for the employee_allocation database table. */
@Entity
@Table(name = "employee_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeAllocation implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5168486443195211934L;

  /** The employee allocation ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Employee_Allocation_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeAllocationID;

  /** The allocation. */
  // bi-directional many-to-one association to Allocation
  @OneToOne
  @JoinColumn(name = "FK_Allocation_ID", nullable = false)
  private Allocation allocation;

  /** The employee. */
  // bi-directional many-to-one association to Employee
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Employee_ID", nullable = false)
  private Employee employee;

  /**
   * Instantiates a new employee allocation.
   */
  public EmployeeAllocation() {
    // zero argument constructor
  }

  /**
   * Gets the employee allocation ID.
   *
   * @return the employee allocation ID
   */
  public int getEmployee_Allocation_ID() {
    return this.employeeAllocationID;
  }

  /**
   * Sets the employee allocation ID.
   *
   * @param employeeAllocationId the new employee allocation ID
   */
  public void setEmployeeAllocationID(final int employeeAllocationId) {
    this.employeeAllocationID = employeeAllocationId;
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

  /**
   * Gets the employee.
   *
   * @return the employee
   */
  public Employee getEmployee() {
    return this.employee;
  }

  /**
   * Sets the employee.
   *
   * @param employeeData the new employee
   */
  public void setEmployee(final Employee employeeData) {
    this.employee = employeeData;
  }
}
