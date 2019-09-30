package com.zone.zissa.controller;

import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.AttributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** The AttributeMgmtController class. */
@RestController
@RequestMapping("/v1/attributes")
@Api(value = "zissa", tags = {"Attribute-mgmt-controller"})
public class AttributeMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AttributeMgmtController.class);

  /** The attribute service impl. */
  @Autowired
  private AttributeService attributeServiceImpl;

  /** The resource attribute repo. */
  @Autowired
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The resource bin attribute repo. */
  @Autowired
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  /**
   * add Attribute controller.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "attributeData", value = "attribute Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Add Attribute",
      notes = "Return success response if success,"
          + " or exception if something wrong",
      response = Attribute.class, httpMethod = "POST",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
              message = "Bad Request")})
  @PostMapping
  public ServiceResponse<Attribute> addAttribute(
      @Valid @RequestBody final String attributeData) throws JSONException {
    LOGGER.debug("Add new Attribute Controller implementation");
    ServiceResponse<Attribute> response = new ServiceResponse<>();
    Attribute attribute = attributeServiceImpl.addAttribute(attributeData);
    response.setData(attribute);
    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setMessage(RestApiMessageConstants.ADD_ATTRIBUTE);
    return response;
  }

  /**
   * Delete Attribute controller.
   *
   * @param attributeID the attribute ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Delete Attribute",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = ServiceResponse.class, httpMethod = "DELETE",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @DeleteMapping("/{attributeID}")
  public ServiceResponse deleteAttribute(@ApiParam(
      value = "attributeID of the attribute which needs to be deleted",
      required = true) @NotNull @PathVariable final Short attributeID) {
    LOGGER.debug("Delete Attribute Controller implementation");

    ServiceResponse response = new ServiceResponse();
    attributeServiceImpl.deleteAttribute(attributeID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.DELETE_ATTRIBUTE);
    return response;
  }

  /**
   * Get the all attributes controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View list of all attributes",
      notes = "Return all attributes", response = Attribute.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping
  public ServiceResponse<List<Attribute>> getAllAttributes() {
    LOGGER.debug("Get all Attributes Controller implementation");
    ServiceResponse<List<Attribute>> response = new ServiceResponse<>();

    List<Attribute> allAttributes = attributeServiceImpl.getAllAttributes();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GETTING_ATTRIBUTES);
    response.setData(allAttributes);
    return response;
  }

  /**
   * Get all attribute datatypes controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View list of all attribute datatypes",
      notes = "Return all attribute datatypes", response = AttrDataType.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/attributeDataTypes")
  public ServiceResponse<List<AttrDataType>> getAllAttributeDataTypes() {
    LOGGER.debug("Get all Attribute DataTypes Controller implementation");

    ServiceResponse<List<AttrDataType>> response = new ServiceResponse<>();

    List<AttrDataType> attributeDataType =
        attributeServiceImpl.getAllAttributeDataTypes();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GETTING_ATTRIBUTE_DATATYPES);
    response.setData(attributeDataType);
    return response;
  }

  /**
   * Gets the attribute details by id controller.
   *
   * @param attributeID the attribute ID
   * @return Attribute
   */
  @ApiOperation(value = "View attribute details by attributeid",
      notes = "Return attribute details", response = Attribute.class,
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/{attributeID}")
  public AttrServiceResponse<Attribute> getAttributeDetailsById(
      @ApiParam(value = "attributeID for which you need attribute details",
          required = true) @NotNull @PathVariable final Short attributeID) {
    LOGGER.debug("Get Attribute Details Controller implementation");
    AttrServiceResponse<Attribute> response = new AttrServiceResponse<>();
    Attribute attributeObject =
        attributeServiceImpl.getAttributeInfoById(attributeID);
    List<ResourceAttribute> resourceAttributeList =
        resourceAttributeRepo.findByAttribute(attributeObject);
    List<ResourcebinAttribute> resourceBinAttributeList =
        resourceBinAttributeRepo.findByAttribute(attributeObject);
    if ((attributeObject != null) && (resourceAttributeList.isEmpty()
        && resourceBinAttributeList.isEmpty())) {
      return getAttributes(attributeObject, false);
    } else if (attributeObject != null) {
      return getAttributes(attributeObject, true);
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.setMessage(RestApiMessageConstants.ATTRIBUTE_NOT_EXISTS);
      return response;
    }
  }

  /**
   * getAttributes method.
   *
   * @param attributeObject the attribute object
   * @param value the value
   * @return the attributes
   */
  public AttrServiceResponse<Attribute> getAttributes(
      final Attribute attributeObject, final Boolean value) {

    AttrServiceResponse<Attribute> response = new AttrServiceResponse<>();

    List<AttributeValue> list =
        new ArrayList<>(attributeObject.getAttributeValues());

    List<AttributeValue> result = list.stream()
        .sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
            .compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
        .collect(Collectors.toList());

    Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
    attributeObject.setAttributeValues(resultSet);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_ATTRIBUTE_BY_ID);
    response.setInuse(value);
    response.setData(attributeObject);

    return response;
  }

  /**
   * Update attribute controller.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "attributeData", value = "attribute Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Update Attribute",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Attribute.class, httpMethod = "PUT",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
              message = "BadRequest"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @PutMapping
  public ServiceResponse<Attribute> updateAttribute(
      @RequestBody final String attributeData) throws JSONException {
    LOGGER.debug("Update Attribute Controller implementation");
    ServiceResponse<Attribute> response = new ServiceResponse<>();

    Attribute attribute = attributeServiceImpl.updateAttribute(attributeData);
    response.setData(attribute);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.UPDATE_ATTRIBUTE);
    return response;
  }
}
