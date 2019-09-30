package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.AttributeMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.AttributeService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONObject;

/**
 * The Class AttributeMgmtControllerTest.
 */
public class AttributeMgmtControllerTest extends ZissaApplicationTest {

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

  /** The attribute mgmt controller. */
  @InjectMocks
  private AttributeMgmtController attributeMgmtController;

  /** The attribute service. */
  @Mock
  private AttributeService attributeService;

  /** The resource attr repo. */
  @Mock
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The resource attribute. */
  @InjectMocks
  private ResourceAttribute resourceAttribute;

  /** The resource bin attribute repo. */
  @Mock
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  /** The resource bin attribute. */
  @InjectMocks
  private ResourcebinAttribute resourceBinAttribute;

  /** The attribute value. */
  @InjectMocks
  private AttributeValue attributeValue;

  /** The Constant username. */
  private static final String username = "BathiyaT";


  /** The Constant password. */
  private static final String password = "Zone@789";

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

    when(attributeService.getAllAttributes()).thenReturn(attributeList);

    ServiceResponse<List<Attribute>> response =
        attributeMgmtController.getAllAttributes();
    assertThat(200, is(response.getStatus()));
  }

  /**
   * Adds the attribute test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addAttributeTest() throws JSONException {


    JSONObject addAttributeData = new JSONObject();

    attribute = new Attribute();
    attribute.setName("mobiles");

    when(attributeService.addAttribute(addAttributeData.toJSONString()))
        .thenReturn(attribute);
    ServiceResponse<Attribute> response =
        attributeMgmtController.addAttribute(addAttributeData.toJSONString());
    assertThat(attribute.getName(), is(response.getData().getName()));
  }

  /**
   * Delete attribute test.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteAttributeTest() {
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attributeMgmtController.deleteAttribute(attribute.getAttribute_ID());
    verify(attributeService, times(1))
        .deleteAttribute(attribute.getAttribute_ID());
  }

  /**
   * Gets the all attribute data types test.
   *
   * @return the all attribute data types test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllAttributeDataTypesTest() {
    List<AttrDataType> attrDataTypeList = new ArrayList<AttrDataType>();
    attributeDataType = new AttrDataType();
    attributeDataType.setDataTypeName("String");
    attrDataTypeList.add(attributeDataType);

    when(attributeService.getAllAttributeDataTypes())
        .thenReturn(attrDataTypeList);
    ServiceResponse<List<AttrDataType>> response =
        attributeMgmtController.getAllAttributeDataTypes();
    assertThat(RestApiMessageConstants.GETTING_ATTRIBUTE_DATATYPES,
        is(response.getMessage()));
  }

  /**
   * Gets the attribute details by id test.
   *
   * @return the attribute details by id test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAttributeDetailsByIdTest() {

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    when(attributeService.getAttributeInfoById(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setAttribute(attribute);
    when(resourceAttributeRepo.findByAttribute(attribute))
        .thenReturn(resourceAttributeList);

    List<ResourcebinAttribute> resourceBinAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("Samsung");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttributeList.add(resourceBinAttribute);

    when(resourceBinAttributeRepo.findByAttribute(attribute))
        .thenReturn(resourceBinAttributeList);

    AttrServiceResponse<Attribute> response = attributeMgmtController
        .getAttributeDetailsById(attribute.getAttribute_ID());
    assertThat(200, is(response.getStatus()));

  }

  /**
   * Gets the attribute details by id failure test.
   *
   * @return the attribute details by id failure test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAttributeDetailsByIdFailureTest() {

    when(attributeService.getAttributeInfoById((short) 1)).thenReturn(null);

    AttrServiceResponse<Attribute> response =
        attributeMgmtController.getAttributeDetailsById((short) 1);
    assertThat(404, is(response.getStatus()));
  }



  /**
   * Update attribute test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateAttributeTest() throws JSONException {

    JSONObject attributeData = new JSONObject();
    attribute = new Attribute();
    attribute.setName("mobiles");
    when(attributeService.updateAttribute(attributeData.toJSONString()))
        .thenReturn(attribute);

    ServiceResponse<Attribute> response =
        attributeMgmtController.updateAttribute(attributeData.toJSONString());
    assertThat(RestApiMessageConstants.UPDATE_ATTRIBUTE,
        is(response.getMessage()));
  }

}
