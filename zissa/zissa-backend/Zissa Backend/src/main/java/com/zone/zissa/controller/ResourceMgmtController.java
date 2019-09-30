package com.zone.zissa.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.DataToLongException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.ResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The ResourceMgmtController class.
 */
@RestController
@RequestMapping("/v1/resources")
@Api(value = "zissa", description = "Operations pertaining to resource management in zissa")
public class ResourceMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceMgmtController.class);

    @Autowired
    private ResourceService resourceServiceImpl;

    private long count = 0;

    /**
     * add Resource controller.
     *
     * @param resourceData
     * @return List<Resource>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceData", value = "resource Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "add Resource", notes = "Return success response if success, or exception if something wrong", response = Resource.class, responseContainer = "List", httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "BadRequest"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ServiceResponse<List<Resource>> addResource(@Valid @RequestBody List<Resource> resourceData) {
        LOGGER.debug("Add new Attribute Controller implementation");

        ServiceResponse<List<Resource>> response = new ServiceResponse<>();

        try {

            List<Resource> resourceList = resourceServiceImpl.addResource(resourceData);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setData(resourceList);
            response.setMessage("Resources Created Successfully");

        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (NullPointerException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_RESOURCE_ERROR, ex);
            response.setMessage("Failed to add resource");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in adding resource");
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Resources Creation");
            LOGGER.error(RestAPIMessageConstants.ADDING_RESOURCE_ERROR, ex);
            response.setErrorMessage("Error in Resources Creation");
        }

        return response;
    }

    /**
     * Get last resource by categoryid controller.
     *
     * @param category_ID
     * @return Resource
     */
    @ApiOperation(value = "View last resource by categoryid", notes = "Return resource details", response = Resource.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/lastresource/{categoryID}")
    public ServiceResponse<Resource> getLastResourceByCategory(
            @ApiParam(value = "Categoryid of the category for which you need last resource details", required = true) @PathVariable Integer categoryID) {
        LOGGER.debug("Get Last Resource by category Controller implementation");
        ServiceResponse<Resource> response = new ServiceResponse<>();

        try {
            Resource resourceObject = resourceServiceImpl.getLastResourceByCategory(categoryID);
            response.setData(resourceObject);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting Last Resource Details by Category is successfull");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Last Resource Details");
            LOGGER.error("getLastResourceByCategory throw an exception", e);
            response.setErrorMessage("Error  in Getting Last Resource Details");
        }
        return response;
    }

    /**
     * Get all disposed resources by categoryid controller.
     * 
     * @param category_ID
     * @return List<Resourcebin>
     */
    @ApiOperation(value = "View list of disposed resources by categoryid", notes = "Return disposed resources details", response = Resourcebin.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/disposed/{categoryID}")
    public ServiceResponse<List<Resourcebin>> getDisposedResourcesByCategoryId(
            @ApiParam(value = "Categoryid for which you need list of disposed resources", required = true) @PathVariable Integer categoryID) {
        LOGGER.debug("Get Disposed Resources by category Controller implementation");

        ServiceResponse<List<Resourcebin>> response = new ServiceResponse<>();
        try {

            List<Resourcebin> resourceRecords = resourceServiceImpl.getDisposedResourcesByCategory(categoryID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting disposed resources by Category is successfull");
            response.setData(resourceRecords);

        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.notFoundException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.noContentException(ex);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_BY_CATEGORY_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.CATDISPRESC_RETREIVE_FAILURE);
            response.setErrorMessage("Error in Getting DisposedResourcesByCategoryId");

        }
        return response;
    }

    /**
     * Get resource by resource_ID controller.
     *
     * @param resource_ID
     * @return Resource
     */
    @ApiOperation(value = "View resource details by resourceid", notes = "Return resource details", response = Resource.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/resourceobj/{resourceID}")
    public ServiceResponse<Resource> getResource(
            @ApiParam(value = "Resourceid for which you need resource details", required = true) @PathVariable Integer resourceID) {
        LOGGER.debug("Get Resource Controller implementation");

        ServiceResponse<Resource> response = new ServiceResponse<>();
        try {
            Resource resourceObject = resourceServiceImpl.getResource(resourceID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting Resource Details is successfull ");
            response.setData(resourceObject);
        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.notFoundException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GET_RESOURCE_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Resource Details");
            response.setErrorMessage("Error in Getting Resource Details");

        }
        return response;
    }

    /**
     * Get disposed resource by resource_ID controller.
     * 
     * @param resourceID
     * @return Resourcebin
     */
    @ApiOperation(value = "View disposed resource details by resourceid", notes = "Return disposed resource details", response = Resourcebin.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/disposedobj/{resourceID}")
    public ServiceResponse<Resourcebin> getDisposedResource(
            @ApiParam(value = "Resourceid for which you need disposed resource details", required = true) @PathVariable Integer resourceID) {
        LOGGER.debug("Get Disposed Resource Controller implementation");

        ServiceResponse<Resourcebin> response = new ServiceResponse<>();
        try {
            Resourcebin resourcebinObject = resourceServiceImpl.getDisposedResource(resourceID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting Disposed Resource Details is successfull ");
            response.setData(resourcebinObject);

        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.notFoundException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.GET_DISPOSED_RESOURCE_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Disposed Resource Details");
            response.setErrorMessage("Error in Getting Disposed Resource Details");

        }
        return response;
    }

    /**
     * Get resources by resource_ID list controller.
     * 
     * @param resourceIdList
     * @return List<Resource>
     */
    @ApiOperation(value = "View list of resources details by resourceid list", notes = "Return list of resources details", response = Resource.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/resourceobj")
    public ServiceResponse<List<Resource>> getResourcesbyResourceIdList(
            @RequestParam("resource_ID") List<Integer> resourceIdList) {
        LOGGER.debug("Get Resource Controller implementation");
        ServiceResponse<List<Resource>> response = new ServiceResponse<>();
        try {

            List<Resource> resourceObjectList = resourceServiceImpl.getResourcesbyResourceIdList(resourceIdList);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting Resources Details is successfull ");
            response.setData(resourceObjectList);

        } catch (Exception e) {
            LOGGER.error("Getting Resources Details throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Resources Details");
            response.setErrorMessage("Error in Getting Resources Details");

        }
        return response;
    }

    /**
     * Get all resources by category.
     *
     * @param category_ID
     * @return List<Resource>
     */
    @ApiOperation(value = "View list of resources details by categoryid", notes = "Return list of resources", response = Resource.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{categoryID}")
    public ServiceResponse<List<Resource>> getAllResourcesByCategoryId(
            @ApiParam(value = "categoryid for which you need list of resources", required = true) @PathVariable Integer categoryID) {
        LOGGER.debug("Get all Resources by category Controller implementation");

        ServiceResponse<List<Resource>> response = new ServiceResponse<>();
        try {
            List<Resource> resourceRecords = resourceServiceImpl.getResourcesByCategoryId(categoryID);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setData(resourceRecords);
            response.setMessage("Getting all the resources is successfull");
        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALL_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.notFoundException(ex);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALL_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.noContentException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALL_RESOURCE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GET_ALL_RESOURCE_BY_CATEGORY_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.CATRESC_RETREIVE_FAILURE);
            response.setErrorMessage("Error in getting Category related Resources");

        }
        return response;
    }

    /**
     * Delete Resource controller.
     *
     * @param resource_ID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete Resource", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{resourceID}")
    public ServiceResponse deleteResource(
            @ApiParam(value = "Resourceid of the resource which needs to be deleted", required = true) @NotNull @PathVariable Integer resourceID) {
        LOGGER.debug("Delete Resource Controller implementation");

        ServiceResponse response = new ServiceResponse();
        try {
            resourceServiceImpl.deleteResource(resourceID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Resource is deleted successfully");
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (DataNotFoundException ex) {
            LOGGER.error("Resource deleted successful", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.RESC_DELETION_FAILURE);
            LOGGER.error(RestAPIMessageConstants.DELETE_RESOURCE_ERROR, ex);
            response.setErrorMessage("Error in deleting Resources");
        }
        return response;
    }

    /**
     * Update Resources controller.
     *
     * @param resourceData
     * @return List<Resource>
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resourceData", value = "resource Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update Resource", notes = "Return success response if success, or exception if something wrong", response = Resource.class, responseContainer = "List", httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<List<Resource>> updateResource(@RequestBody List<Resource> resourceData) {
        LOGGER.debug("Update Attribute Controller implementation");

        ServiceResponse<List<Resource>> response = new ServiceResponse<>();

        try {

            List<Resource> resourceList = resourceServiceImpl.updateResource(resourceData);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Resources Updated Successfully");
            response.setData(resourceList);

        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (NullPointerException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_RESOURCE_ERROR, ex);
            response.setMessage("Failed to update resource");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in updating resource");
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Resources Updation");
            LOGGER.error(RestAPIMessageConstants.UPDATE_RESOURCE_ERROR, ex);
            response.setErrorMessage("Error in Resources Updation");

        }

        return response;
    }

    /**
     * Dispose Resource controller.
     * 
     * @RequestBody disposeData
     * 
     * @return ServiceResponse
     * @throws JSONException
     */
    @ApiOperation(value = "Dispose Resources", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/dispose")
    public ServiceResponse disposeResources(@RequestBody String disposeData) throws JSONException {
        LOGGER.debug("Dispose Resources Controller implementation");

        ServiceResponse response = new ServiceResponse();
        try {
            resourceServiceImpl.disposeResources(disposeData);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Resource is disposed successfully");
        } catch (DataToLongException ex) {
            LOGGER.error(RestAPIMessageConstants.DISPOSE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.dataToLongException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.DISPOSE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.DISPOSE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.DISPOSE_RESOURCE_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Resource Disposition");
            response.setErrorMessage("Error in Resources Disposition");

        }
        return response;
    }

    /**
     * Restore Resource controller.
     *
     * @param resource_ID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Restore Resources by resourceid list", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/restore")
    public ServiceResponse restoreResources(@RequestParam("resource_ID") List<Resourcebin> resourceID) {
        LOGGER.debug("Restore Resource Controller implementation");

        ServiceResponse response = new ServiceResponse();
        try {
            resourceServiceImpl.restoreResources(resourceID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Resources is restored successfully");
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.RESTORE_RESOURCE_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (Exception e) {
            LOGGER.error(RestAPIMessageConstants.RESTORE_RESOURCE_ERROR, e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Resource Restoration");
            response.setErrorMessage("Error in Resources Restoration");

        }
        return response;
    }

    /**
     * get Resources By SearchTerm controller
     * 
     * @param categoryID
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attributeId
     * @return List<Resource>
     */
    @ApiOperation(value = "View list of resources by categoryid,page,size,searchText and direction", notes = "Return list of resources", response = Resource.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public PageServiceResponse<Object> getResourcesBySearchTerm(@RequestParam("category_ID") List<Integer> categoryID,
            @RequestParam("page") int page, @RequestParam("size") int size,
            @RequestParam("searchText") String searchText, @RequestParam("direction") String direction,
            @RequestParam(value = "attrid", required = false) short attributeId) {
        LOGGER.debug("Get all Resources Controller implementation");

        PageServiceResponse<Object> response = new PageServiceResponse<>();
        try {
            List<Resource> resourceSearchList = resourceServiceImpl.getAllResourcesBySearchTerm(categoryID, page, size,
                    searchText, direction, attributeId);
            response.setMessage("Getting all the resources by Searchterm is successfull");
            response.setTotalRecords(this.count);
            response.setData(resourceSearchList);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in getting Resources");
            response.setErrorMessage("Error in getting Resources");
            LOGGER.error("Getting all the resources by Searchterm throw an exception", e);

        }
        return response;
    }

    /**
     * get Disposed Resources By SearchTerm controller
     * 
     * @param categoryID
     * @param page
     * @param size
     * @param searchText
     * @param direction
     * @param attributeId
     * @return List<Resourcebin>
     */
    @ApiOperation(value = "View list of disposed resources by categoryid,page,size,searchText and direction", notes = "Return list of disposed resources", response = Resourcebin.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/disposed")
    public PageServiceResponse<Object> getDisposedResourcesBySearchTerm(
            @RequestParam("category_ID") List<Integer> categoryID, @RequestParam("page") int page,
            @RequestParam("size") int size, @RequestParam("searchText") String searchText,
            @RequestParam("direction") String direction,
            @RequestParam(value = "attrid", required = false) short attributeId) {
        LOGGER.debug("Get all Disposed Resources Controller implementation");

        PageServiceResponse<Object> response = new PageServiceResponse<>();
        try {
            List<Resourcebin> resourceSearchList = resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryID,
                    page, size, searchText, direction, attributeId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the disposed resources by searchterm is successfull");
            response.setData(resourceSearchList);
            response.setTotalRecords(this.count);

        } catch (Exception e) {
            LOGGER.error("Getting all the disposed resources by Searchterm throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in getting disposed Resources");
            response.setErrorMessage("Error in getting disposed Resources");

        }
        return response;
    }

    /**
     * Allocate or Deallocate Resource controller.
     *
     * @param allocationData
     * @return ServiceResponse
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "allocationData", value = "allocation Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "resource allocation or deallocation", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PostMapping("/allocation")
    public ServiceResponse resourceAllocation(@RequestBody String allocationData) {
        LOGGER.debug("Resource Allocation Management Controller implementation");
        ServiceResponse response = new ServiceResponse();
        try {
            resourceServiceImpl.resourceAllocation(allocationData);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage(RestAPIMessageConstants.RESOURCE_ALLOCATION);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.RESOURCE_ALLOCATION_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (AccessDeniedException ex) {
            LOGGER.error(RestAPIMessageConstants.RESOURCE_ALLOCATION_ERROR, ex);
            return ExceptionHandlerClass.accessDeniedException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.RESOURCE_ALLOCATION_ERROR, ex);
            response.setMessage("Failed to allocate resource");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in allocating resource");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.RESOURCE_ALLOCATION_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.RESOURCE_ALLOCATION_FAILURE);
            response.setErrorMessage(RestAPIMessageConstants.RESOURCE_ALLOCATION_FAILURE);
        }
        return response;
    }
}
