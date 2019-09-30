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

/** The persistent class for the operation database table. */
@Entity
@Table(name = "operation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Operation implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -3139878071764537010L;

  /** The operation ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Operation_ID")
  private int operationID;

  /** The name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH)
  private String name;

  /** The permissions. */
  // bi-directional many-to-one association to Permission
  @OneToMany(mappedBy = "operation")
  private Set<Permission> permissions;

  /**
   * Instantiates a new operation.
   */
  public Operation() {
    // zero argument constructor
  }

  /**
   * Gets the operation ID.
   *
   * @return the operation ID
   */
  public int getOperation_ID() {
    return operationID;
  }

  /**
   * Sets the operation ID.
   *
   * @param operationId the new operation ID
   */
  public void setOperationID(final int operationId) {
    this.operationID = operationId;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name.
   *
   * @param operationName the new name
   */
  public void setName(final String operationName) {
    this.name = operationName;
  }

  /**
   * Gets the permissions.
   *
   * @return the permissions
   */
  @JsonIgnore
  public Set<Permission> getPermissions() {
    return this.permissions;
  }

  /**
   * Sets the permissions.
   *
   * @param permissionsData the new permissions
   */
  public void setPermissions(final Set<Permission> permissionsData) {
    this.permissions = permissionsData;
  }
}
