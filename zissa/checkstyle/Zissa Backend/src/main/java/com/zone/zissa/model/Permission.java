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
import javax.persistence.Table;

/** The persistent class for the permission database table. */
@Entity
@Table(name = "permission")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Permission implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -4013040327187212394L;

  /** The permission ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Permission_ID")
  private int permissionID;

  /** The category. */
  // bi-directional many-to-one association to Category
  @ManyToOne
  @JoinColumn(name = "FK_Category_ID", nullable = false)
  private Category category;

  /** The operation. */
  // bi-directional many-to-one association to Operation
  @ManyToOne
  @JoinColumn(name = "FK_Operation_ID", nullable = false)
  private Operation operation;

  /** The role. */
  // bi-directional many-to-one association to Role
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Role_ID", nullable = false)
  private Role role;

  /**
   * Instantiates a new permission.
   */
  public Permission() {
    // zero argument constructor
  }

  /**
   * Gets the permission ID.
   *
   * @return the permission ID
   */
  public int getPermission_ID() {
    return this.permissionID;
  }

  /**
   * Sets the permission ID.
   *
   * @param permissionId the new permission ID
   */
  public void setPermissionID(final int permissionId) {
    this.permissionID = permissionId;
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
   * Gets the operation.
   *
   * @return the operation
   */
  public Operation getOperation() {
    return this.operation;
  }

  /**
   * Sets the operation.
   *
   * @param operationData the new operation
   */
  public void setOperation(final Operation operationData) {
    this.operation = operationData;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  @JsonIgnore
  public Role getRole() {
    return this.role;
  }

  /**
   * Sets the role.
   *
   * @param roleData the new role
   */
  public void setRole(final Role roleData) {
    this.role = roleData;
  }
}
