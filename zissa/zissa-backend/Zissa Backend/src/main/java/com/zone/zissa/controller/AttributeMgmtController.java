package com.zone.zissa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.AttributeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The AttributeMgmtController class.
 */
@RestController
@RequestMapping("/v1/attributes")
@Api(value = "zissa", description = "Operations pertaining to attributes in zissa")
public class AttributeMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeMgmtController.class);

    @Autowired
    private AttributeService attributeServiceImpl;

    @Autowired
    private ResourceAttributeRepository resourceAttributeRepo;

    @Autowired
    private ResourcebinAttributeRepository resourceBinAttributeRepo;

    /**
     * add Attribute controller.
     *
     * @param attributeData
     * @return Attribute
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attributeData", value = "attribute Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Add Attribute", notes = "Return success response if success, or exception if something wrong", response = Attribute.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @PostMapping
    public ServiceResponse<Attribute> addAttribute(@Valid @RequestBody String attributeData) {
        LOGGER.debug("Add new Attribute Controller implementation");
        ServiceResponse<Attribute> response = new ServiceResponse<>();

        try {
            Attribute attribute = attributeServiceImpl.addAttribute(attributeData);

            response.setData(attribute);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setMessage("Attribute Created Successfully");
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            response.setMessage("Failed to add attribute");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in adding attribute");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_ATTRIBUTE_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Attribute Creation");
            response.setErrorMessage("Failed to add new Attribute");
        }
        return response;
    }

    /**
     * Delete Attribute controller.
     *
     * @param attributeID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete Attribute", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{attributeID}")
    public ServiceResponse deleteAttribute(
            @ApiParam(value = "attributeID of the attribute which needs to be deleted", required = true) @NotNull @PathVariable Short attributeID) {
        LOGGER.debug("Delete Attribute Controller implementation");

        ServiceResponse response = new ServiceResponse();

        try {
            attributeServiceImpl.deleteAttribute(attributeID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Attribute is deleted successfully");
        } catch (DataNotFoundException ex) {
            LOGGER.error("Attribute is deleted successfully", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (DataIntegrityViolationException e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_DELETION_ERROR, e);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setMessage(RestAPIMessageConstants.ATTR_DELETION_FAILURE);
            response.setErrorMessage("Cannot delete a parent row: a foreign key constraint fails");
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_DELETION_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.ATTR_DELETION_FAILURE);
            response.setErrorMessage("Error in Deleting Attribute");
        }
        return response;
    }

    /**
     * Get the all attributes controller.
     *
     * @return List<Attribute>
     */
    @ApiOperation(value = "View list of all attributes", notes = "Return all attributes", response = Attribute.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public ServiceResponse<List<Attribute>> getAllAttributes() {
        LOGGER.debug("Get all Attributes Controller implementation");
        ServiceResponse<List<Attribute>> response = new ServiceResponse<>();
        try {
            List<Attribute> allAttributes = attributeServiceImpl.getAllAttributes();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Attributes is successfull");
            response.setData(allAttributes);
        } catch (Exception e) {
            LOGGER.error("Getting all attribute throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in getting Attributes");
            response.setErrorMessage("Failed to get all Attributes");
        }
        return response;
    }

    /**
     * Get all attribute datatypes controller.
     *
     * @return List<AttrDataType>
     */
    @ApiOperation(value = "View list of all attribute datatypes", notes = "Return all attribute datatypes", response = AttrDataType.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/attributeDataTypes")
    public ServiceResponse<List<AttrDataType>> getAllAttributeDataTypes() {
        LOGGER.debug("Get all Attribute DataTypes Controller implementation");

        ServiceResponse<List<AttrDataType>> response = new ServiceResponse<>();
        try {

            List<AttrDataType> attributeDataType = attributeServiceImpl.getAllAttributeDataTypes();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Attribute DataTypes is successfull");
            response.setData(attributeDataType);

        } catch (Exception e) {
            LOGGER.error("Getting Attribute DataTypes throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Attribute DataTypes");
            response.setErrorMessage("Failed to get all Attribute DataTypes");

        }
        return response;
    }

    /**
     * Gets the attribute details by id controller.
     *
     * @param attributeID
     * @return Attribute
     */
    @ApiOperation(value = "View attribute details by attributeid", notes = "Return attribute details", response = Attribute.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{attributeID}")
    public AttrServiceResponse<Attribute> getAttributeDetailsById(
            @ApiParam(value = "attributeID for which you need attribute details", required = true) @NotNull @PathVariable Short attributeID) {
        LOGGER.debug("Get Attribute Details Controller implementation");

        AttrServiceResponse<Attribute> response = new AttrServiceResponse<>();
        try {

            Attribute attributeObject = attributeServiceImpl.getAttributeInfoById(attributeID);

            if (attributeObject != null) {
                List<ResourceAttribute> resourceAttributeList = resourceAttributeRepo.findByAttribute(attributeObject);
                List<ResourcebinAttribute> resourceBinAttributeList = resourceBinAttributeRepo
                        .findByAttribute(attributeObject);
                if (resourceAttributeList.isEmpty() && resourceBinAttributeList.isEmpty()) {
                    response.setInuse(false);
                } else {
                    response.setInuse(true);
                }
                List<AttributeValue> list = new ArrayList<>(attributeObject.getAttributeValues());
                Collections.sort(list, new Comparator<AttributeValue>() {

                    @Override
                    public int compare(AttributeValue value1, AttributeValue value2) {

                        return Integer.valueOf(value1.getAttribute_Value_ID())
                                .compareTo(Integer.valueOf(value2.getAttribute_Value_ID()));
                    }
                });
                Set<AttributeValue> resultSet = new LinkedHashSet<>(list);
                attributeObject.setAttributeValues(resultSet);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setMessage("Getting Attribute Details is successfull ");
                response.setData(attributeObject);
            } else {
                response.setMessage(RestAPIMessageConstants.GETTING_ATTR_FAILURE);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.setErrorMessage(RestAPIMessageConstants.ATTR_EXISTS_FAILURE);
            }

        } catch (Exception e) {
            LOGGER.error("getAttributeDetailsById throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.GETTING_ATTR_FAILURE);
            response.setErrorMessage("Failed to get Attribute Details");

        }
        return response;
    }

    /**
     * Update attribute controller.
     *
     * @param attributeData
     * @return Attribute
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attributeData", value = "attribute Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update Attribute", notes = "Return success response if success, or exception if something wrong", response = Attribute.class, httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<Attribute> updateAttribute(@RequestBody String attributeData) {
        LOGGER.debug("Update Attribute Controller implementation");
        ServiceResponse<Attribute> response = new ServiceResponse<>();

        try {

            Attribute attribute = attributeServiceImpl.updateAttribute(attributeData);
            response.setData(attribute);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.ATTR_UPDATION);

        } catch (DataNotFoundException ex) {
            LOGGER.error("updating attribute successfully", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, ex);
            response.setMessage("Failed to update attribute");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in update attribute");
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.ATTRIBUTE_UPDATION_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.ATTR_UPDATION_FAILURE);
            response.setErrorMessage("Error in updating Attribute");
        }

        return response;
    }
}
