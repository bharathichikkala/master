package com.zone.zissa.service.impl;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeDataTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.AttributeValueRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.service.AttributeService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The AttributeServiceImpl class. */
@Service
public class AttributeServiceImpl implements AttributeService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AttributeServiceImpl.class);

  /** The attribute repo. */
  @Autowired
  private AttributeRepository attributeRepo;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /** The attribute data repo. */
  @Autowired
  private AttributeDataTypeRepository attributeDataRepo;

  /** The attribute value repo. */
  @Autowired
  private AttributeValueRepository attributeValueRepo;

  /** The resource attribute repo. */
  @Autowired
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The resource bin attribute repo. */
  @Autowired
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  /** The attribute value. */
  private String attributeValue = "value";

  /** The attribute value id. */
  private String attributeValueId = "attribute_Value_ID";

  /**
   * Add attribute service implementation.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  @Override
  public Attribute addAttribute(final String attributeData)
      throws JSONException {
    LOGGER.info("Add new Attribute Service implementation");

    Attribute attribute = new Attribute();
    Attribute addAttribute = null;
    Set<AttributeValue> attributeValueList = new HashSet<>();
    JSONObject jsonObject = new JSONObject(attributeData);
    String name = jsonObject.getString("name");
    int dataTypeId = jsonObject.getInt("attrDataType");
    int dropDownValue = jsonObject.getInt("dropdowncontrolval");
    int userId = jsonObject.getInt("user_ID");
    Optional<User> userObject = userRepo.findByUserID(userId);

    Optional<AttrDataType> attributeDataTypeObject =
        attributeDataRepo.findByDataTypeID(dataTypeId);
    if (userObject.isPresent()) {
      attribute.setUser(userObject.get());
    }
    if (attributeDataTypeObject.isPresent()) {
      attribute.setAttrDataType(attributeDataTypeObject.get());
    }
    attribute.setName(name);

    Optional<Attribute> attributeExists = attributeRepo.findByName(name);
    if (!attributeExists.isPresent()) {
      addAttribute = attributeRepo.save(attribute);
      if (dropDownValue == 1) {
        this.addAttributeValuesMethod(jsonObject, addAttribute,
            attributeValueList);
      }
    } else {
      throw new ConflictException(RestApiMessageConstants.ATTRIBUTE_EXISTS);
    }

    return addAttribute;
  }

  /**
   * addAttributeValuesMethod method.
   *
   * @param jsonObject the json object
   * @param attributeObject the attribute object
   * @param attributeValueList the attribute value list
   * @throws JSONException the JSON exception
   */
  public void addAttributeValuesMethod(final JSONObject jsonObject,
      final Attribute attributeObject,
      final Set<AttributeValue> attributeValueList) throws JSONException {
    JSONArray jsonChildObject = jsonObject.getJSONArray("attributeValues");
    for (int attribValsCount = 0; attribValsCount < jsonChildObject
        .length(); attribValsCount++) {
      AttributeValue attributeValueObject = new AttributeValue();
      JSONObject jsonValue = jsonChildObject.getJSONObject(attribValsCount);
      attributeValueObject.setValue(jsonValue.getString(attributeValue));
      attributeValueObject.setAttribute(attributeObject);
      AttributeValue addAttributeValue =
          attributeValueRepo.save(attributeValueObject);
      attributeValueList.add(addAttributeValue);
    }
    attributeObject.setAttributeValues(attributeValueList);
  }

  /**
   * Update attribute service implementation.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  @Override
  public Attribute updateAttribute(final String attributeData)
      throws JSONException {
    LOGGER.info("Update Attribute Service implementation");
    JSONObject jsonObject = new JSONObject(attributeData);
    int userId = jsonObject.getInt("user_ID");
    Optional<User> userObject = userRepo.findByUserID(userId);
    int dataTypeId = jsonObject.getInt("data_Type_ID");
    Optional<AttrDataType> attributeDataTypeObject =
        attributeDataRepo.findByDataTypeID(dataTypeId);
    Attribute attribute = new Attribute();
    if (userObject.isPresent()) {
      attribute.setUser(userObject.get());
    }
    if (attributeDataTypeObject.isPresent()) {
      attribute.setAttrDataType(attributeDataTypeObject.get());
    }
    String name = jsonObject.getString("name");
    attribute.setName(name);
    Short attributeId = ((Integer) jsonObject.get("attribute_ID")).shortValue();
    attribute.setAttributeID(attributeId);
    Attribute updateAttribute = null;
    Optional<Attribute> attributeObject =
        attributeRepo.findByAttributeID(attributeId);
    if (!attributeObject.isPresent()) {
      throw new DataNotFoundException(RestApiMessageConstants.UPDATE_ATTRIBUTE);

    } else {
      attribute.setCreatedDate(attributeObject.get().getCreatedDate());
      attribute.setCreatedBy(attributeObject.get().getCreatedBy());
      updateAttribute =
          this.updateAttributeMethod(name, attributeId, attribute);
    }
    int dropDownValue = jsonObject.getInt("dropdown");
    if (dropDownValue == 1) {

      JSONArray jsonDeleteAttributeObject =
          jsonObject.getJSONArray("delete_attributeValues");
      JSONArray jsonInsertAttributeObject =
          jsonObject.getJSONArray("insert_attributeValues");
      JSONArray jsonChildObject = jsonObject.getJSONArray("attributeValues");
      if (jsonInsertAttributeObject.length() != 0) {
        this.insertAttributeMethod(jsonInsertAttributeObject, updateAttribute);
      }
      this.updateAttributeValuesMethod(jsonChildObject, attributeId,
          updateAttribute);
      if (jsonDeleteAttributeObject.length() != 0) {
        List<String> deleteAttributeFailureList =
            this.deleteAttributeMethod(jsonDeleteAttributeObject, attributeId);
        return this.checkDeleteAttributeFailureListIsNotEmpty(
            deleteAttributeFailureList, updateAttribute, attributeObject);
      }
    } else {
      attributeValueRepo.deleteAttributeValue(attributeId);
    }
    Set<AttributeValue> saveAttributeValue =
        attributeValueRepo.findAttributeValueByAttribute(attributeObject.get());
    if (!saveAttributeValue.isEmpty()) {
      updateAttribute.setAttributeValues(saveAttributeValue);
    }
    return updateAttribute;
  }

  /**
   * updateAttributeMethod.
   *
   * @param name the name
   * @param attributeId the attribute id
   * @param attribute the attribute
   * @return Attribute
   */
  public Attribute updateAttributeMethod(final String name,
      final Short attributeId, final Attribute attribute) {
    Attribute updateAttribute = null;
    Optional<Attribute> attributeObj = attributeRepo.findByName(name);
    int attributeIdByName = 0;
    if (attributeObj.isPresent()) {
      attributeIdByName = attributeObj.get().getAttribute_ID();
    }
    Optional<Attribute> attributeByName = attributeRepo.findByName(name);
    if (attributeByName.isPresent() && attributeIdByName != attributeId) {
      throw new ConflictException(
          RestApiMessageConstants.ATTRIBUTE_NAME_EXISTS);
    } else {
      updateAttribute = attributeRepo.save(attribute);
    }
    return updateAttribute;
  }

  /**
   * checkDeleteAttributeFailureListIsNotEmpty method.
   *
   * @param deleteAttributeFailureList the delete attribute failure list
   * @param updateAttribute the update attribute
   * @param attributeObject the attribute object
   * @return Attribute
   */
  public Attribute checkDeleteAttributeFailureListIsNotEmpty(
      final List<String> deleteAttributeFailureList,
      final Attribute updateAttribute,
      final Optional<Attribute> attributeObject) {
    if (!deleteAttributeFailureList.isEmpty()) {
      throw new ConflictException(
          RestApiMessageConstants.ATTRIBUTE_UPDATE_CONFLICT);

    } else {
      Attribute attribute = null;
      if (attributeObject.isPresent()) {
        attribute = attributeObject.get();
      }
      Set<AttributeValue> attributeValueObject =
          attributeValueRepo.findAttributeValueByAttribute(attribute);
      if (!attributeValueObject.isEmpty()) {
        updateAttribute.setAttributeValues(attributeValueObject);
      }
    }
    return updateAttribute;
  }

  /**
   * Update AttributeValues Method.
   *
   * @param jsonChildObject the json child object
   * @param attributeId the attribute id
   * @param updateAttribute the update attribute
   * @throws JSONException the JSON exception
   */
  public void updateAttributeValuesMethod(final JSONArray jsonChildObject,
      final Short attributeId, final Attribute updateAttribute)
      throws JSONException {
    for (int updateAttrsCount = 0; updateAttrsCount < jsonChildObject
        .length(); updateAttrsCount++) {
      AttributeValue attributeValueObject = new AttributeValue();
      JSONObject json = (JSONObject) jsonChildObject.get(updateAttrsCount);
      attributeValueObject.setValue(json.getString(attributeValue));

      Optional<AttributeValue> attributeValueObj = attributeValueRepo
          .findByAttributeValueID(json.getInt(attributeValueId));
      String attributeDropDownOldValue = null;
      if (attributeValueObj.isPresent()) {
        attributeDropDownOldValue = attributeValueObj.get().getValue();
      }
      String attributeDropDownNewValue = json.getString(attributeValue);
      if (!attributeDropDownNewValue.equals(attributeDropDownOldValue)) {
        resourceAttributeRepo.updateResourcesAttributes(
            attributeDropDownNewValue, attributeId, attributeDropDownOldValue);
        resourceBinAttributeRepo.updateResourceBinAttributes(
            attributeDropDownNewValue, attributeId, attributeDropDownOldValue);
      }
      attributeValueObject.setAttributeValueID(json.getInt(attributeValueId));
      attributeValueObject.setAttribute(updateAttribute);
      attributeValueRepo.save(attributeValueObject);
    }
  }

  /**
   * Insert attribute method.
   *
   * @param jsonInsertAttributeObject the json insert attribute object
   * @param attributeObject the attribute object
   * @throws JSONException the JSON exception
   */
  public void insertAttributeMethod(final JSONArray jsonInsertAttributeObject,
      final Attribute attributeObject) throws JSONException {
    for (int insAttrCount = 0; insAttrCount < jsonInsertAttributeObject
        .length(); insAttrCount++) {
      AttributeValue attributeValueObject = new AttributeValue();
      JSONObject jsonObject =
          jsonInsertAttributeObject.getJSONObject(insAttrCount);
      attributeValueObject.setValue(jsonObject.getString(attributeValue));
      attributeValueObject.setAttribute(attributeObject);
      attributeValueRepo.save(attributeValueObject);
    }
  }

  /**
   * Delete attribute method.
   *
   * @param jsonDeleteAttributeObject the json delete attribute object
   * @param attributeId the attribute id
   * @return list
   * @throws JSONException the JSON exception
   */
  public List<String> deleteAttributeMethod(
      final JSONArray jsonDeleteAttributeObject, final Short attributeId)
      throws JSONException {
    List<String> deleteAttributeFailureList = new ArrayList<>();
    for (int delAttrCount = 0; delAttrCount < jsonDeleteAttributeObject
        .length(); delAttrCount++) {
      JSONObject jsonObject =
          jsonDeleteAttributeObject.getJSONObject(delAttrCount);
      int attributeValueById = jsonObject.getInt(attributeValueId);
      Optional<AttributeValue> attributeValueObject =
          attributeValueRepo.findByAttributeValueID(attributeValueById);
      String getAttributeValue = null;
      if (attributeValueObject.isPresent()) {
        getAttributeValue = attributeValueObject.get().getValue();
      }
      Optional<Attribute> attributeObject =
          attributeRepo.findByAttributeID(attributeId);
      Attribute attribute = null;
      if (attributeObject.isPresent()) {
        attribute = attributeObject.get();
      }
      List<ResourceAttribute> resourceAttributeList = resourceAttributeRepo
          .findByAttributeAndValue(attribute, getAttributeValue);
      List<ResourcebinAttribute> resourceBinAttributeList =
          resourceBinAttributeRepo.findByAttributeAndValue(attribute,
              getAttributeValue);
      if (resourceAttributeList.isEmpty()
          && resourceBinAttributeList.isEmpty()) {
        attributeValueRepo.deleteAttributeValueID(attributeValueById);
      } else {
        deleteAttributeFailureList.add(getAttributeValue);
      }
    }
    return deleteAttributeFailureList;
  }

  /**
   * Delete attribute service implementation.
   *
   * @param attributeId the attribute id
   */
  @Override
  public void deleteAttribute(final Short attributeId) {

    LOGGER.info("Delete Attribute Service implementation");
    Optional<Attribute> attributeExists =
        attributeRepo.findByAttributeID(attributeId);
    if (attributeExists.isPresent()) {
      attributeRepo.deleteById(attributeId);
    } else {
      throw new DataNotFoundException(RestApiMessageConstants.DELETE_ATTRIBUTE);
    }
  }

  /**
   * Get all attributes service implementation.
   *
   * @return the list
   */
  @Override
  public List<Attribute> getAllAttributes() {

    LOGGER.info("Get all Attributes Service implementation");
    return attributeRepo.findAll();
  }

  /**
   * Gets the attribute info by id service implementation.
   *
   * @param attributeId the attribute id
   * @return Attribute
   */
  @Override
  public Attribute getAttributeInfoById(final Short attributeId) {

    LOGGER.info("Get Attribute Details by AttributeId Service implementation");
    Optional<Attribute> attribute =
        attributeRepo.findByAttributeID(attributeId);
    Attribute attributeObject = null;
    if (attribute.isPresent()) {
      attributeObject = attribute.get();
    }
    return attributeObject;
  }

  /**
   * Gets all attribute data types.
   *
   * @return the list
   */
  @Override
  public List<AttrDataType> getAllAttributeDataTypes() {

    LOGGER.info("Get all Attribute DataTypes Service implementation");
    return attributeDataRepo.findAll();
  }
}
