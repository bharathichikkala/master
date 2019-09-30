package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.audit.Auditable;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** The persistent class for the role database table. */
@Entity
@Table(name = "role")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Role extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8471348817865172652L;

  /** The role ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Role_ID")
  private int roleID;

  /** The name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH)
  private String name;

  /** The administration. */
  @Column(nullable = false)
  private int administration;

  /** The default category. */
  // bi-directional many-to-one association to AllocationType
  @ManyToOne
  @JoinColumn(name = "FK_Default_Category", nullable = false)
  private Category defaultCategory;

  // bi-directional many-to-one association to Permission

  /** The permissions. */
  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private List<Permission> permissions;

  /** The users. */
  // bi-directional many-to-one association to User
  @OneToMany(mappedBy = "role")
  private Set<User> users;

  /**
   * Instantiates a new role.
   */
  public Role() {
    // zero argument constructor
  }

  /**
   * Gets the role ID.
   *
   * @return the role ID
   */
  public int getRole_ID() {
    return this.roleID;
  }

  /**
   * Sets the role ID.
   *
   * @param roleId the new role ID
   */
  public void setRoleID(final int roleId) {
    this.roleID = roleId;
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
   * @param roleName the new name
   */
  public void setName(final String roleName) {
    this.name = roleName;
  }

  /**
   * Gets the administration.
   *
   * @return the administration
   */
  public int getAdministration() {
    return administration;
  }

  /**
   * Sets the administration.
   *
   * @param value the new administration
   */
  public void setAdministration(final int value) {
    this.administration = value;
  }

  /**
   * Gets the permissions.
   *
   * @return the permissions
   */
  public List<Permission> getPermissions() {
    return this.permissions;
  }

  /**
   * Sets the permissions.
   *
   * @param permissionsData the new permissions
   */
  public void setPermissions(final List<Permission> permissionsData) {
    this.permissions = permissionsData;
  }

  /**
   * Gets the users.
   *
   * @return the users
   */
  @JsonIgnore
  public Set<User> getUsers() {
    return this.users;
  }

  /**
   * Sets the users.
   *
   * @param usersData the new users
   */
  public void setUsers(final Set<User> usersData) {
    this.users = usersData;
  }

  /**
   * Gets the default category.
   *
   * @return the default category
   */
  public Category getDefaultCategory() {
    return defaultCategory;
  }

  /**
   * Sets the default category.
   *
   * @param defaultCategoryData the new default category
   */
  public void setDefaultCategory(final Category defaultCategoryData) {
    this.defaultCategory = defaultCategoryData;
  }
}
