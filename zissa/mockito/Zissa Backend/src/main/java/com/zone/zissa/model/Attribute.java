package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.audit.Auditable;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/** The persistent class for the attribute database table. */
@Entity
@Table(name = "attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners(AuditingEntityListener.class)
public class Attribute extends Auditable<String> implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1256349326584063883L;

  /** The attribute ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Attribute_ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private short attributeID;

  /** The name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Name")
  private String name;

  /** The attr data type. */
  // bi-directional many-to-one association to AttrDataType
  @ManyToOne
  @JoinColumn(name = "FK_Data_Type_ID", nullable = false)
  private AttrDataType attrDataType;

  /** The user. */
  // bi-directional many-to-one association to User
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Create_User_ID", nullable = false)
  private User user;

  /** The attribute values. */
  // bi-directional many-to-one association to AttributeValue
  @OneToMany(mappedBy = "attribute", cascade = CascadeType.ALL)
  private Set<AttributeValue> attributeValues;

  /** The category attributes. */
  @JsonIgnore
  // bi-directional many-to-one association to CategoryAttribute
  @OneToMany(mappedBy = "attribute")
  private Set<CategoryAttribute> categoryAttributes;

  /** The resource attributes. */
  @JsonIgnore
  // bi-directional many-to-one association to ResourceAttribute
  @OneToMany(mappedBy = "attribute")
  private Set<ResourceAttribute> resourceAttributes;

  /**
   * Instantiates a new attribute.
   */
  public Attribute() {
    // zero argument constructor
  }

  /**
   * Gets the attribute ID.
   *
   * @return the attribute ID
   */
  public short getAttribute_ID() {
    return this.attributeID;
  }

  /**
   * Sets the attribute ID.
   *
   * @param attributeId the new attribute ID
   */
  public void setAttributeID(final short attributeId) {
    this.attributeID = attributeId;
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
   * @param attributeName the new name
   */
  public void setName(final String attributeName) {
    this.name = attributeName;
  }

  /**
   * Gets the attr data type.
   *
   * @return the attr data type
   */
  public AttrDataType getAttrDataType() {
    return this.attrDataType;
  }

  /**
   * Sets the attr data type.
   *
   * @param attributeDataType the new attr data type
   */
  public void setAttrDataType(final AttrDataType attributeDataType) {
    this.attrDataType = attributeDataType;
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
   * Gets the attribute values.
   *
   * @return the attribute values
   */
  public Set<AttributeValue> getAttributeValues() {
    return attributeValues;
  }

  /**
   * Sets the attribute values.
   *
   * @param attributeValueData the new attribute values
   */
  public void setAttributeValues(final Set<AttributeValue> attributeValueData) {
    this.attributeValues = attributeValueData;
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
   * @param categoryAttributeData the new category attributes
   */
  public void setCategoryAttributes(
      final Set<CategoryAttribute> categoryAttributeData) {
    this.categoryAttributes = categoryAttributeData;
  }

  /**
   * Gets the resource attributes.
   *
   * @return the resource attributes
   */
  public Set<ResourceAttribute> getResourceAttributes() {
    return this.resourceAttributes;
  }

  /**
   * Sets the resource attributes.
   *
   * @param resourceAttributeData the new resource attributes
   */
  public void setResourceAttributes(
      final Set<ResourceAttribute> resourceAttributeData) {
    this.resourceAttributes = resourceAttributeData;
  }
}
