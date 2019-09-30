package com.zone.zissa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zone.zissa.response.RestApiMessageConstants;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** The persistent class for the attr_data_type database table. */
@Entity
@Table(name = "attr_data_type")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttrDataType implements Serializable {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 6856072067334602314L;

  /** The data type ID. */
  @Id
  @Column(unique = true, nullable = false, name = "Data_Type_ID")
  private int dataTypeID;

  /** The data type name. */
  @Column(nullable = false, length = RestApiMessageConstants.LENGTH,
      name = "Data_Type_Name")
  private String dataTypeName;

  /** The attributes. */
  // bi-directional many-to-one association to Attribute
  @OneToMany(mappedBy = "attrDataType")
  private Set<Attribute> attributes;

  /**
   * Instantiates a new attr data type.
   */
  public AttrDataType() {
    // zero argument constructor
  }

  /**
   * Gets the data type ID.
   *
   * @return the data type ID
   */
  public int getData_Type_ID() {
    return this.dataTypeID;
  }

  /**
   * Sets the data type ID.
   *
   * @param dataTypeId the new data type ID
   */
  public void setDataTypeID(final int dataTypeId) {
    this.dataTypeID = dataTypeId;
  }

  /**
   * Gets the data type name.
   *
   * @return the data type name
   */
  public String getData_Type_Name() {
    return this.dataTypeName;
  }

  /**
   * Sets the data type name.
   *
   * @param dataType the new data type name
   */
  public void setDataTypeName(final String dataType) {
    this.dataTypeName = dataType;
  }

  /**
   * Gets the attributes.
   *
   * @return the attributes
   */
  @JsonIgnore
  public Set<Attribute> getAttributes() {
    return this.attributes;
  }

  /**
   * Sets the attributes.
   *
   * @param attributeData the new attributes
   */
  public void setAttributes(final Set<Attribute> attributeData) {
    this.attributes = attributeData;
  }
}
