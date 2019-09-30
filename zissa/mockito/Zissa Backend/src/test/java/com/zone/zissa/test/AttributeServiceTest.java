package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeDataTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.AttributeValueRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.service.impl.AttributeServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * The Class AttributeTest.
 */
public class AttributeServiceTest extends ZissaApplicationTest {

  /** The attribute. */
  @InjectMocks
  private Attribute attribute;

  /** The user. */
  @InjectMocks
  private User user;

  /** The attribute data type. */
  @InjectMocks
  private AttrDataType attributeDataType;

  /** The attr value. */
  @InjectMocks
  private AttributeValue attrValue;

  /** The attr repo. */
  @Mock
  private AttributeRepository attrRepo;

  /** The attr data type repo. */
  @Mock
  private AttributeDataTypeRepository attrDataTypeRepo;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The attribute value repo. */
  @Mock
  private AttributeValueRepository attributeValueRepo;

  /** The resource attr repo. */
  @Mock
  private ResourceAttributeRepository resourceAttrRepo;

  /** The resourcebin attr repo. */
  @Mock
  private ResourcebinAttributeRepository resourcebinAttrRepo;

  /** The attr service impl. */
  @InjectMocks
  private AttributeServiceImpl attrSetviceImpl;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  /** The Constant username. */
  private static final String username = "BathiyaT";


  /** The Constant password. */
  private static final String password = "Zone@789";


  /**
   * Adds the attribute test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addAttributeTest() throws Exception {
    String name = "mobile";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    JSONObject addAttributeData = new JSONObject();

    addAttributeData.put("name", name);
    addAttributeData.put("dropdowncontrolval", dropDownVal);
    addAttributeData.put("attrDataType", attrDataType);
    addAttributeData.put("user_ID", userId);

    JSONArray attributeValueArray = new JSONArray();
    JSONObject valueObeject = new JSONObject();
    valueObeject.put("value", "4.5 inch moiles");
    attributeValueArray.add(valueObeject);

    JSONObject value = new JSONObject();
    value.put("value", "15.5 inch mobiles");
    attributeValueArray.add(value);

    addAttributeData.put("attributeValues", attributeValueArray);

    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByName(attribute.getName()))
        .thenReturn(Optional.of(attribute));
    when(attrDataTypeRepo.findByDataTypeID(1))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(user));

    when(attrSetviceImpl.addAttribute(addAttributeData.toJSONString()))
        .thenReturn(Optional.of(attribute).get());
  }

  /**
   * Adds the attribute failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addAttributeFailureTest() throws Exception {
    String name = "mobile";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    JSONObject addAttributeData = new JSONObject();

    addAttributeData.put("name", name);
    addAttributeData.put("dropdowncontrolval", dropDownVal);
    addAttributeData.put("attrDataType", attrDataType);
    addAttributeData.put("user_ID", userId);

    JSONArray attributeValueArray = new JSONArray();
    JSONObject valueObeject = new JSONObject();
    valueObeject.put("value", "4.5 inch moiles");
    attributeValueArray.add(valueObeject);

    JSONObject value = new JSONObject();
    value.put("value", "15.5 inch mobiles");
    attributeValueArray.add(value);

    addAttributeData.put("attributeValues", attributeValueArray);

    attribute = new Attribute();
    attribute.setName("mobile");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByName(attribute.getName()))
        .thenReturn(Optional.of(attribute));
    when(attrDataTypeRepo.findByDataTypeID(1))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    when(attrSetviceImpl.addAttribute(addAttributeData.toJSONString()));
  }

  /**
   * Adds the attribute failure test by invalid data.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addAttributeFailureTestByInvalidData() throws Exception {
    String name = "mobiles";
    int dropDownVal = 123;
    int attrDataType = 1;
    int userId = 123;
    JSONObject addAttributeData = new JSONObject();

    addAttributeData.put("name", name);
    addAttributeData.put("dropdowncontrolval", dropDownVal);
    addAttributeData.put("attrDataType", attrDataType);
    addAttributeData.put("user_ID", userId);

    JSONArray attributeValueArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "14.5 inch laptop");
    attributeValueArray.add(valueObject);

    JSONObject value = new JSONObject();
    value.put("value", "15.5 inch desktop");
    attributeValueArray.add(value);

    addAttributeData.put("attributeValues", attributeValueArray);
    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(123);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");


    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setAttributeValueID(1);
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);
    when(attributeValueRepo.findByAttributeValueID(1))
        .thenReturn(Optional.of(attrValue));
    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByName(attribute.getName()))
        .thenReturn(Optional.of(attribute));
    when(attrDataTypeRepo.findByDataTypeID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(attributeDataType));

    when(attrSetviceImpl.addAttribute(addAttributeData.toJSONString()));

  }

  /**
   * Adds the attribute by invalid json test.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addAttributeByInvalidJsonTest() throws Exception {
    String name = "mobiles";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    JSONObject addAttributeData = new JSONObject();

    addAttributeData.put("Name", name);
    addAttributeData.put("dropdowncontrolval", dropDownVal);
    addAttributeData.put("attrDataType", attrDataType);
    addAttributeData.put("user_ID", userId);

    JSONArray attributeValueArray = new JSONArray();
    JSONObject valueObeject = new JSONObject();
    valueObeject.put("value", "14.5 inch laptop");
    attributeValueArray.add(valueObeject);

    JSONObject value = new JSONObject();
    value.put("value", "15.5 inch desktop");
    attributeValueArray.add(value);

    addAttributeData.put("attributeValues", attributeValueArray);
    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByName("mobiles")).thenReturn(Optional.of(attribute));
    when(attrDataTypeRepo.findByDataTypeID(1))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    when(attrSetviceImpl.addAttribute(addAttributeData.toJSONString()));
  }

  /**
   * Update attribute test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeTest() throws Exception {
    Integer id = 1;
    String name = "mobiles";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    String attributeValueID = "1";
    String attributeValueID_2 = "2";

    JSONObject AttributeData = new JSONObject();
    AttributeData.put("attribute_ID", id);
    AttributeData.put("name", name);
    AttributeData.put("dropdown", dropDownVal);
    AttributeData.put("data_Type_ID", attrDataType);
    AttributeData.put("user_ID", userId);

    JSONArray deleteAttributrArray = new JSONArray();
    JSONObject AttributeObject = new JSONObject();
    AttributeObject.put("attribute_Value_ID", attributeValueID);
    deleteAttributrArray.add(AttributeObject);

    JSONArray insertAttributeArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "12.5 inch");
    insertAttributeArray.add(valueObject);

    JSONArray attributeArray = new JSONArray();
    JSONObject AttributeValueObject = new JSONObject();
    AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
    AttributeValueObject.put("value", "15.5 inch");
    attributeArray.add(AttributeValueObject);

    AttributeData.put("delete_attributeValues", deleteAttributrArray);
    AttributeData.put("insert_attributeValues", insertAttributeArray);
    AttributeData.put("attributeValues", attributeArray);

    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(attrDataTypeRepo.findByDataTypeID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(1)).thenReturn(Optional.of(user));
    Attribute attrResponse =
        attrSetviceImpl.updateAttribute(AttributeData.toJSONString());

    assertThat(AttributeData.get("name"), is(attrResponse.getName()));


  }


  /**
   * Update attribute test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeWithNoDropDownValueTest() throws Exception {
    Integer id = 1;
    String name = "mobiles";
    int dropDownVal = 0;
    int attrDataType = 1;
    int userId = 1;
    String attributeValueID = "1";
    String attributeValueID_2 = "2";

    JSONObject AttributeData = new JSONObject();
    AttributeData.put("attribute_ID", id);
    AttributeData.put("name", name);
    AttributeData.put("dropdown", dropDownVal);
    AttributeData.put("data_Type_ID", attrDataType);
    AttributeData.put("user_ID", userId);

    JSONArray deleteAttributrArray = new JSONArray();
    JSONObject AttributeObject = new JSONObject();
    AttributeObject.put("attribute_Value_ID", attributeValueID);
    deleteAttributrArray.add(AttributeObject);

    JSONArray insertAttributeArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "12.5 inch");
    insertAttributeArray.add(valueObject);

    JSONArray attributeArray = new JSONArray();
    JSONObject AttributeValueObject = new JSONObject();
    AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
    AttributeValueObject.put("value", "15.5 inch");
    attributeArray.add(AttributeValueObject);

    AttributeData.put("delete_attributeValues", deleteAttributrArray);
    AttributeData.put("insert_attributeValues", insertAttributeArray);
    AttributeData.put("attributeValues", attributeArray);

    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(attrDataTypeRepo.findByDataTypeID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(1)).thenReturn(Optional.of(user));
    Attribute attrResponse =
        attrSetviceImpl.updateAttribute(AttributeData.toJSONString());

    assertThat(AttributeData.get("name"), is(attrResponse.getName()));


  }



  /**
   * Update attribute by existing name test.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeByExistingNameTest() throws Exception {
    Integer id = 1;
    String name = "Models";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    String attributeValueID = "1";
    String attributeValueID_2 = "2";

    JSONObject AttributeData = new JSONObject();
    AttributeData.put("attribute_ID", id);
    AttributeData.put("name", name);
    AttributeData.put("dropdown", dropDownVal);
    AttributeData.put("data_Type_ID", attrDataType);
    AttributeData.put("user_ID", userId);

    JSONArray deleteAttributrArray = new JSONArray();
    JSONObject AttributeObject = new JSONObject();
    AttributeObject.put("attribute_Value_ID", attributeValueID);
    deleteAttributrArray.add(AttributeObject);

    JSONArray insertAttributeArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "12.5 inch");
    insertAttributeArray.add(valueObject);

    JSONArray attributeArray = new JSONArray();
    JSONObject AttributeValueObject = new JSONObject();
    AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
    AttributeValueObject.put("value", "15.5 inch");
    attributeArray.add(AttributeValueObject);

    AttributeData.put("delete_attributeValues", deleteAttributrArray);
    AttributeData.put("insert_attributeValues", insertAttributeArray);
    AttributeData.put("attributeValues", attributeArray);

    attribute = new Attribute();
    attribute.setName("Models");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 2);


    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByAttributeID((short) 1))
        .thenReturn(Optional.of(attribute));
    when(attrRepo.findByName(attribute.getName()))
        .thenReturn(Optional.of(attribute));

    when(attrDataTypeRepo.findByDataTypeID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));
    when(attrSetviceImpl.updateAttribute(AttributeData.toJSONString()));
  }


  /**
   * Update attribute failure test by invalid id.
   *
   * @throws Exception the exception
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeFailureTestByInvalidId() throws Exception {
    Integer id = 123;
    String name = "mobiles";
    int dropDownVal = 1;
    int attrDataType = 1;
    int userId = 1;
    String attributeValueID = "1";
    String attributeValueID_2 = "2";

    JSONObject AttributeData = new JSONObject();
    AttributeData.put("attribute_ID", id);
    AttributeData.put("name", name);
    AttributeData.put("dropdown", dropDownVal);
    AttributeData.put("data_Type_ID", attrDataType);
    AttributeData.put("user_ID", userId);

    JSONArray deleteAttributrArray = new JSONArray();
    JSONObject AttributeObject = new JSONObject();
    AttributeObject.put("attribute_Value_ID", attributeValueID);
    deleteAttributrArray.add(AttributeObject);

    JSONArray insertAttributeArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "12.5 inch");
    insertAttributeArray.add(valueObject);

    JSONArray attributeArray = new JSONArray();
    JSONObject AttributeValueObject = new JSONObject();
    AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
    AttributeValueObject.put("value", "15.5 inch");
    attributeArray.add(AttributeValueObject);

    AttributeData.put("delete_attributeValues", deleteAttributrArray);
    AttributeData.put("insert_attributeValues", insertAttributeArray);
    AttributeData.put("attributeValues", attributeArray);

    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 1);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1);
    attributeDataType.setDataTypeName("String");

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attrValue = new AttributeValue();
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);

    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(attrDataTypeRepo.findByDataTypeID(attributeDataType.getData_Type_ID()))
        .thenReturn(Optional.of(attributeDataType));

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));
    when(attrSetviceImpl.updateAttribute(AttributeData.toJSONString()));
  }

  /**
   * Gets the all attributes test.
   *
   * @return the all attributes test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllAttributesTest() throws Exception {
    List<Attribute> attributeList = new ArrayList<Attribute>();
    attribute = new Attribute();
    attribute.setName("model");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attributeList.add(attribute);
    when(attrRepo.findAll()).thenReturn(attributeList);
    List<Attribute> list = attrSetviceImpl.getAllAttributes();
    assertThat(attributeList.get(0).getName(), is(list.get(0).getName()));
    assertThat(1, is(list.size()));
  }

  /**
   * Gets the all attribute data types test.
   *
   * @return the all attribute data types test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllAttributeDataTypesTest() throws Exception {

    List<AttrDataType> attrDataTypeList = new ArrayList<AttrDataType>();
    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeName("String");
    attrDataTypeList.add(attributeDataType);
    when(attrDataTypeRepo.findAll()).thenReturn(attrDataTypeList);
    List<AttrDataType> list = attrSetviceImpl.getAllAttributeDataTypes();
    assertThat(attrDataTypeList.get(0).getData_Type_Name(),
        is(list.get(0).getData_Type_Name()));
    assertThat(1, is(list.size()));
  }

  /**
   * Gets the attribute info by id test.
   *
   * @return the attribute info by id test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAttributeInfoByIdTest() throws Exception {
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setName("model");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));
    Attribute attrResponse = attrSetviceImpl.getAttributeInfoById((short) 1);
    assertThat(attribute.getAttribute_ID(), is(attrResponse.getAttribute_ID()));

  }

  /**
   * Delete attribute test.
   *
   * @throws Exception the exception
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void deleteAttributeTest() throws Exception {
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));
    attrSetviceImpl.deleteAttribute((short) 1);
    verify(attrRepo, times(1)).deleteById((short) 1);

  }

  /**
   * Delete attribute failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteAttributeFailureTest() throws Exception {
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attrRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));
    attrSetviceImpl.deleteAttribute((short) 0);
    verify(attrRepo, times(1)).deleteById((short) 0);
  }

  /**
   * Update attribute failure test by invalid data.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeFailureTestByInvalidData() throws Exception {
    Integer id = 1;
    String name = "mobiles";
    int dropDownVal = 1;
    int attrDataType = 100;
    int userId = 1;
    String attributeValueID = "26";
    String attributeValueID_2 = "27";

    JSONObject AttributeData = new JSONObject();
    AttributeData.put("attribute_ID", id);
    AttributeData.put("name", name);
    AttributeData.put("dropdown", dropDownVal);
    AttributeData.put("data_Type_ID", attrDataType);
    AttributeData.put("user_ID", userId);

    JSONArray deleteAttributrArray = new JSONArray();
    JSONObject AttributeObject = new JSONObject();
    AttributeObject.put("attribute_Value_ID", attributeValueID);
    deleteAttributrArray.add(AttributeObject);

    JSONArray insertAttributeArray = new JSONArray();
    JSONObject valueObject = new JSONObject();
    valueObject.put("value", "12.5 inch");
    insertAttributeArray.add(valueObject);

    JSONArray attributeArray = new JSONArray();
    JSONObject AttributeValueObject = new JSONObject();
    AttributeValueObject.put("attribute_Value_ID", attributeValueID_2);
    AttributeValueObject.put("value", "15.5 inch");
    attributeArray.add(AttributeValueObject);

    AttributeData.put("delete_attributeValues", deleteAttributrArray);
    AttributeData.put("insert_attributeValues", insertAttributeArray);
    AttributeData.put("attributeValues", attributeArray);

    attribute = new Attribute();
    attribute.setName("mobiles");
    attribute.setAttrDataType(attributeDataType);
    attribute.setUser(user);
    attribute.setAttributeID((short) 0);

    Optional<Attribute> attributeToReturnFromRepository =
        Optional.of(attribute);

    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeID(1000);
    attributeDataType.setDataTypeName("String");

    Optional<AttrDataType> attrDataTypeToReturnFromRepository =
        Optional.of(attributeDataType);

    user = new User();
    user.setUserID(1);
    user.setActiveStatus(1);
    user.setEmail("bathiyat@zone24x7.com");
    user.setFirstName("Bathiya");
    user.setLastName("Tennakoon");
    user.setUserName("BathiyaT");
    Optional<User> userToReturnFromRepository = Optional.of(user);

    Set<AttributeValue> attributeValueList = new HashSet<>();

    attrValue = new AttributeValue();
    attrValue.setAttributeValueID(27);
    attrValue.setValue("4.5 inch moiles");
    attributeValueList.add(attrValue);


    attribute.setAttributeValues(attributeValueList);

    when(attributeValueRepo.save(attrValue)).thenReturn(attrValue);
    when(attributeValueRepo.findAttributeValueByAttribute(attribute))
        .thenReturn(attributeValueList);

    when(attrRepo.save(any(Attribute.class))).thenReturn(attribute);

    when(attrRepo.findByAttributeID((short) 1))
        .thenReturn(attributeToReturnFromRepository);
    when(attrRepo.findByName(attribute.getName()))
        .thenReturn(Optional.of(attribute));

    when(attrDataTypeRepo.findByDataTypeID(100))
        .thenReturn(attrDataTypeToReturnFromRepository);
    when(attributeValueRepo.findByAttributeValueID(26))
        .thenReturn(Optional.of(attrValue));

    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);
    when(attrSetviceImpl.updateAttribute(AttributeData.toJSONString()))
        .thenThrow(new ConflictException("Attribue already exists"));

  }

}
