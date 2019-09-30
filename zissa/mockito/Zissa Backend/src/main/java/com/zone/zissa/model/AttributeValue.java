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

/** The persistent class for the attribute_value database table. */
@Entity
@Table(name = "attribute_value")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeValue implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 8786905427874016085L;

  /** The attribute value ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Attribute_Value_ID")
  private int attributeValueID;

  /** The value. */
  @Column(nullable = false, length = RestApiMessageConstants.VALUE_LENGTH,
      name = "Value")
  private String value;

  // bi-directional many-to-one association to Attribute

  /** The attribute. */
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Attribute_ID", nullable = false)
  private Attribute attribute;

  /**
   * Instantiates a new attribute value.
   */
  public AttributeValue() {
    // zero argument constructor
  }

  /**
   * Gets the attribute value ID.
   *
   * @return the attribute value ID
   */
  public int getAttribute_Value_ID() {
    return this.attributeValueID;
  }

  /**
   * Sets the attribute value ID.
   *
   * @param attributeValueId the new attribute value ID
   */
  public void setAttributeValueID(final int attributeValueId) {
    this.attributeValueID = attributeValueId;
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
   * @param attrValue the new value
   */
  public void setValue(final String attrValue) {
    this.value = attrValue;
  }

  /**
   * Gets the attribute.
   *
   * @return the attribute
   */
  @JsonIgnore
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
}
