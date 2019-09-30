package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/** The persistent class for the resourcebin_attribute database table. */
@Entity
@Table(name = "resourcebin_attribute")
public class ResourcebinAttribute implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 470901292719727649L;

  /** The resource attribute ID. */
  @Id
  @Column(unique = true, nullable = false, name = "resource_attribute_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int resourceAttributeID;

  /** The value. */
  @Column(nullable = false, length = RestApiMessageConstants.VALUE_LENGTH)
  private String value;

  /** The attribute. */
  @OneToOne
  @JoinColumn(name = "FK_Attribute_ID", nullable = false)
  private Attribute attribute;

  /** The resourcebin. */
  // bi-directional many-to-one association to Resourcebin
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Resource_ID", nullable = false)
  private Resourcebin resourcebin;

  /**
   * Instantiates a new resourcebin attribute.
   */
  public ResourcebinAttribute() {
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
   * @param resBinAttributeValue the new value
   */
  public void setValue(final String resBinAttributeValue) {
    this.value = resBinAttributeValue;
  }

  /**
   * Gets the resourcebin.
   *
   * @return the resourcebin
   */
  @JsonIgnore
  public Resourcebin getResourcebin() {
    return this.resourcebin;
  }

  /**
   * Sets the resourcebin.
   *
   * @param resourceBinData the new resourcebin
   */
  public void setResourcebin(final Resourcebin resourceBinData) {
    this.resourcebin = resourceBinData;
  }

  /**
   * Gets the attribute.
   *
   * @return the attribute
   */
  public Attribute getAttribute() {
    return attribute;
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
