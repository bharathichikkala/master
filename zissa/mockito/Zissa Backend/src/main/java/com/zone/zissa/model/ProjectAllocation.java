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

/** The persistent class for the project_allocation database table. */
@Entity
@Table(name = "project_allocation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectAllocation implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 3816786079047344339L;

  /** The project allocation ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Project_Allocation_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int projectAllocationID;

  /** The allocation. */
  // bi-directional many-to-one association to Allocation
  @OneToOne
  @JoinColumn(name = "FK_Allocation_ID", nullable = false)
  private Allocation allocation;

  /** The project. */
  // bi-directional many-to-one association to Project
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Project_ID", nullable = false)
  private Project project;

  /**
   * Instantiates a new project allocation.
   */
  public ProjectAllocation() {
    // zero argument constructor
  }

  /**
   * Gets the project allocation ID.
   *
   * @return the project allocation ID
   */
  public int getProject_Allocation_ID() {
    return this.projectAllocationID;
  }

  /**
   * Sets the project allocation ID.
   *
   * @param projectAllocationId the new project allocation ID
   */
  public void setProjectAllocationID(final int projectAllocationId) {
    this.projectAllocationID = projectAllocationId;
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
   * Gets the project.
   *
   * @return the project
   */
  public Project getProject() {
    return this.project;
  }

  /**
   * Sets the project.
   *
   * @param projectData the new project
   */
  public void setProject(final Project projectData) {
    this.project = projectData;
  }
}
