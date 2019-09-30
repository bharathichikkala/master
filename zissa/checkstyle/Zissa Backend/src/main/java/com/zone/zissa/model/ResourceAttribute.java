package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
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

/** The persistent class for the resource_attribute database table. */
@Entity
@Table(name = "resource_attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceAttribute implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 2683688033736722149L;

  /** The resource attribute ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Resource_Attribute_ID")
  private int resourceAttributeID;

  /** The value. */
  @Column(nullable = false, length = RestApiMessageConstants.VALUE_LENGTH)
  private String value;

  /** The attribute. */
  // bi-directional many-to-one association to Attribute
  @ManyToOne
  @JoinColumn(name = "FK_Attribute_ID", nullable = false)
  private Attribute attribute;

  /** The resource. */
  // bi-directional many-to-one association to Resource
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Resource_ID", nullable = false)
  private Resource resource;

  /**
   * Instantiates a new resource attribute.
   */
  public ResourceAttribute() {
    // zero argument constructor
  }

  /**
   * Gets the resource attribute ID.
   *
   * @return the resource attribute ID
   */
  public int getResource_Attribute_ID() {
    return this.resourceAttributeID;
  }

  /**
   * Sets the resource attribute ID.
   *
   * @param resourceAttributeId the new resource attribute ID
   */
  public void setResourceAttributeID(final int resourceAttributeId) {
    this.resourceAttributeID = resourceAttributeId;
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Sets the value.
   *
   * @param resAttributeValue the new value
   */
  public void setValue(final String resAttributeValue) {
    this.value = resAttributeValue;
  }

  /**
   * Gets the attribute.
   *
   * @return the attribute
   */
  public Attribute getAttribute() {
    return this.attribute;
  }

  /**
   * Sets the attribute.
   *
   * @param attributeData the new attribute
   */
  public void setAttribute(final Attribute attributeData) {
    this.attribute = attributeData;
  }

  /**
   * Gets the resource.
   *
   * @return the resource
   */
  @JsonIgnore
  public Resource getResource() {
    return this.resource;
  }

  /**
   * Sets the resource.
   *
   * @param resourceData the new resource
   */
  public void setResource(final Resource resourceData) {
    this.resource = resourceData;
  }
}
