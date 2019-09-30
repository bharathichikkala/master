package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.audit.Auditable;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
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

/** The persistent class for the user database table. */
@Entity
@Table(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class User extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 7817027843116573795L;

  /** The user ID. */
  @Id
  @Column(unique = true, nullable = false, name = "User_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int userID;

  /** The active status. */
  @Column(nullable = false, name = "Active_Status")
  private int activeStatus;

  /** The email. */
  @Column(nullable = false, length = RestApiMessageConstants.EMAIL_LENGTH)
  private String email;

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

  /** The allocations. */
  @JsonIgnore
  // bi-directional many-to-one association to Allocation
  @OneToMany(mappedBy = "user")
  private Set<Allocation> allocations;

  /** The attributes. */
  @JsonIgnore
  // bi-directional many-to-one association to Attribute
  @OneToMany(mappedBy = "user")
  private Set<Attribute> attributes;

  /** The categories. */
  @JsonIgnore
  // bi-directional many-to-one association to Category
  @OneToMany(mappedBy = "user")
  private Set<Category> categories;

  /** The resources. */
  @JsonIgnore
  // bi-directional many-to-one association to Resource
  @OneToMany(mappedBy = "user")
  private List<Resource> resources;

  /** The role. */
  // bi-directional many-to-one association to Role
  @ManyToOne
  @JoinColumn(name = "FK_Role_ID", nullable = false)
  private Role role;

  /**
   * Instantiates a new user.
   */
  public User() {
    // zero argument constructor
  }

  /**
   * Gets the user ID.
   *
   * @return the user ID
   */
  public int getUser_ID() {
    return this.userID;
  }

  /**
   * Sets the user ID.
   *
   * @param userId the new user ID
   */
  public void setUserID(final int userId) {
    this.userID = userId;
  }

  /**
   * Gets the active status.
   *
   * @return the active status
   */
  public int getActive_Status() {
    return activeStatus;
  }

  /**
   * Sets the active status.
   *
   * @param statusId the new active status
   */
  public void setActiveStatus(final int statusId) {
    this.activeStatus = statusId;
  }

  /**
   * Gets the email.
   *
   * @return the email
   */
  public String getEmail() {
    return this.email;
  }

  /**
   * Sets the email.
   *
   * @param emailId the new email
   */
  public void setEmail(final String emailId) {
    this.email = emailId;
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
   * Sets the first name.
   *
   * @param userFirstName the new first name
   */
  public void setFirstName(final String userFirstName) {
    this.firstName = userFirstName;
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
   * Sets the last name.
   *
   * @param userLastName the new last name
   */
  public void setLastName(final String userLastName) {
    this.lastName = userLastName;
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
   * Sets the user name.
   *
   * @param name the new user name
   */
  public void setUserName(final String name) {
    this.userName = name;
  }

  /**
   * Gets the allocations.
   *
   * @return the allocations
   */
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

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  public Set<Attribute> getAttributes() {
    return this.attributes;
  }

  /**
   * Sets the attributes.
   *
   * @param attributesData the new attributes
   */
  public void setAttributes(final Set<Attribute> attributesData) {
    this.attributes = attributesData;
  }

  /**
   * Gets the categories.
   *
   * @return the categories
   */
  public Set<Category> getCategories() {
    return this.categories;
  }

  /**
   * Sets the categories.
   *
   * @param categoriesData the new categories
   */
  public void setCategories(final Set<Category> categoriesData) {
    this.categories = categoriesData;
  }

  /**
   * Gets the resources.
   *
   * @return the resources
   */
  public List<Resource> getResources() {
    return this.resources;
  }

  /**
   * Sets the resources.
   *
   * @param resourcesData the new resources
   */
  public void setResources(final List<Resource> resourcesData) {
    this.resources = resourcesData;
  }

  /**
   * Gets the role.
   *
   * @return the role
   */
  public Role getRole() {
    return this.role;
  }

  /**
   * Sets the role.
   *
   * @param rolesData the new role
   */
  public void setRole(final Role rolesData) {
    this.role = rolesData;
  }
}
