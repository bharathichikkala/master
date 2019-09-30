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

/** The persistent class for the category database table. */
@Entity
@Table(name = "category")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Category extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8078340836909885997L;

  /** The category ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Category_ID")
  private int categoryID;

  /** The code pattern. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Code_Pattern")
  private String codePattern;

  /** The name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Name")
  private String name;

  // bi-directional many-to-one association to User\

  /** The user. */
  @ManyToOne
  @JoinColumn(name = "FK_Create_User_ID")
  private User user;

  // bi-directional many-to-one association to CategoryAttribute

  /** The category attributes. */
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private Set<CategoryAttribute> categoryAttributes;

  /** The permissions. */
  // bi-directional many-to-one association to Permission
  @JsonIgnore
  @OneToMany(mappedBy = "category")
  private List<Permission> permissions;

  /** The resources. */
  @JsonIgnore
  // bi-directional many-to-one association to Resource
  @OneToMany(mappedBy = "category")
  private List<Resource> resources;

  /**
   * Instantiates a new category.
   */
  public Category() {
    // zero argument constructor
  }

  /**
   * Gets the category ID.
   *
   * @return the category ID
   */
  public int getCategory_ID() {
    return this.categoryID;
  }

  /**
   * Sets the category ID.
   *
   * @param categoryId the new category ID
   */
  public void setCategoryID(final int categoryId) {
    this.categoryID = categoryId;
  }

  /**
   * Gets the code pattern.
   *
   * @return the code pattern
   */
  public String getCode_Pattern() {
    return this.codePattern;
  }

  /**
   * Sets the code pattern.
   *
   * @param catCodePattern the new code pattern
   */
  public void setCodePattern(final String catCodePattern) {
    this.codePattern = catCodePattern;
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
   * @param categoryName the new name
   */
  public void setName(final String categoryName) {
    this.name = categoryName;
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
   * Gets the category attributes.
   *
   * @return the category attributes
   */
  public Set<CategoryAttribute> getCategoryAttributes() {
    return this.categoryAttributes;
  }

  /**
   * Sets the category attributes.
   *
   * @param categoryAttributesData the new category attributes
   */
  public void setCategoryAttributes(
      final Set<CategoryAttribute> categoryAttributesData) {
    this.categoryAttributes = categoryAttributesData;
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
}
