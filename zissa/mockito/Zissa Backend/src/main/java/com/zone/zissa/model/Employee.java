package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the employee database table. */
@Entity
@Table(name = "employee")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5623708864708424564L;

  /** The employee ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Employee_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int employeeID;

  /** The active status. */
  @Column(nullable = false, name = "Active_Status")
  private byte activeStatus;

  /** The first name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "First_Name")
  private String firstName;

  /** The last name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Last_Name")
  private String lastName;

  /** The user name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "User_Name")
  private String userName;

  /** The employee allocations. */
  // bi-directional many-to-one association to EmployeeAllocation
  @OneToMany(mappedBy = "employee")
  private Set<EmployeeAllocation> employeeAllocations;

  /**
   * Instantiates a new employee.
   */
  public Employee() {
    // zero argument constructor
  }

  /**
   * Gets the employee ID.
   *
   * @return the employee ID
   */
  public int getEmployee_ID() {
    return this.employeeID;
  }

  /**
   * Sets the employee ID.
   *
   * @param employeeId the new employee ID
   */
  public void setEmployeeID(final int employeeId) {
    this.employeeID = employeeId;
  }

  /**
   * Gets the active status.
   *
   * @return the active status
   */
  public byte getActive_Status() {
    return this.activeStatus;
  }

  /**
   * Sets the active status.
   *
   * @param status the new active status
   */
  public void setActiveStatus(final byte status) {
    this.activeStatus = status;
  }

  /**
   * Gets the first name.
   *
   * @return the first name
   */
  public String getFirst_Name() {
    return this.firstName;
  }

  /**
   * Gets the last name.
   *
   * @return the last name
   */
  public String getLast_Name() {
    return this.lastName;
  }

  /**
   * Gets the user name.
   *
   * @return the user name
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * Sets the last name.
   *
   * @param empLastName the new last name
   */
  public void setLastName(final String empLastName) {
    this.lastName = empLastName;
  }

  /**
   * Sets the first name.
   *
   * @param empFirstName the new first name
   */
  public void setFirstName(final String empFirstName) {
    this.firstName = empFirstName;
  }

  /**
   * Gets the employee allocations.
   *
   * @return the employee allocations
   */
  @JsonIgnore
  public Set<EmployeeAllocation> getEmployeeAllocations() {
    return employeeAllocations;
  }

  /**
   * Sets the employee allocations.
   *
   * @param employeeAllocationsData the new employee allocations
   */
  public void setEmployeeAllocations(
      final Set<EmployeeAllocation> employeeAllocationsData) {
    this.employeeAllocations = employeeAllocationsData;
  }

  /**
   * Sets the user name.
   *
   * @param empUserName the new user name
   */
  public void setUserName(final String empUserName) {
    this.userName = empUserName;
  }
}
