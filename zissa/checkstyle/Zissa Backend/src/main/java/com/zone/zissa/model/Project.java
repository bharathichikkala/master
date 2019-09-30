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

/** The persistent class for the project database table. */
@Entity
@Table(name = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -8472738521386685504L;

  /** The project ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Project_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int projectID;

  /** The active status. */
  @Column(nullable = false, name = "Active_Status")
  private byte activeStatus;

  /** The project name. */
  @Column(nullable = false,
      length = RestApiMessageConstants.PROJECT_NAME_LENGTH)
  private String projectName;

  /** The project allocations. */
  // bi-directional many-to-one association to ProjectAllocation
  @OneToMany(mappedBy = "project")
  private Set<ProjectAllocation> projectAllocations;

  /**
   * Instantiates a new project.
   */
  public Project() {
    // zero argument constructor
  }

  /**
   * Gets the project ID.
   *
   * @return the project ID
   */
  public int getProject_ID() {
    return this.projectID;
  }

  /**
   * Sets the project ID.
   *
   * @param projectId the new project ID
   */
  public void setProjectID(final int projectId) {
    this.projectID = projectId;
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
   * Gets the project name.
   *
   * @return the project name
   */
  public String getProjectName() {
    return this.projectName;
  }

  /**
   * Sets the project name.
   *
   * @param name the new project name
   */
  public void setProjectName(final String name) {
    this.projectName = name;
  }

  /**
   * Gets the project allocations.
   *
   * @return the project allocations
   */
  @JsonIgnore
  public Set<ProjectAllocation> getProjectAllocations() {
    return projectAllocations;
  }

  /**
   * Sets the project allocations.
   *
   * @param projectAllocationsData the new project allocations
   */
  public void setProjectAllocations(
      final Set<ProjectAllocation> projectAllocationsData) {
    this.projectAllocations = projectAllocationsData;
  }
}
