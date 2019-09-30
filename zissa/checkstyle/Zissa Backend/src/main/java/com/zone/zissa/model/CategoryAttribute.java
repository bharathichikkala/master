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

/** The persistent class for the category_attribute database table. */
@Entity
@Table(name = "category_attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryAttribute implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 9130125389871446933L;

  /** The category attribute ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false, name = "Category_Attribute_ID")
  private int categoryAttributeID;

  /** The attribute. */
  // bi-directional many-to-one association to Attribute
  @ManyToOne
  @JoinColumn(name = "FK_Attribute_ID", nullable = false)
  private Attribute attribute;

  /** The category. */
  // bi-directional many-to-one association to Category
  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinColumn(name = "FK_Category_ID", nullable = false)
  private Category category;

  /** The is default. */
  @Column(nullable = false)
  private boolean isDefault;

  /**
   * Instantiates a new category attribute.
   */
  public CategoryAttribute() {
    // zero argument constructor
  }

  /**
   * Checks if is default.
   *
   * @return true, if is default
   */
  public boolean isDefault() {
    return isDefault;
  }

  /**
   * Sets the default.
   *
   * @param defaultValue the new default
   */
  public void setDefault(final boolean defaultValue) {
    this.isDefault = defaultValue;
  }

  /**
   * Gets the category attribute ID.
   *
   * @return the category attribute ID
   */
  public int getCategory_Attribute_ID() {
    return this.categoryAttributeID;
  }

  /**
   * Sets the category attribute ID.
   *
   * @param categoryAttributeId the new category attribute ID
   */
  public void setCategoryAttributeID(final int categoryAttributeId) {
    this.categoryAttributeID = categoryAttributeId;
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
   * Gets the category.
   *
   * @return the category
   */
  @JsonIgnore
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
}
