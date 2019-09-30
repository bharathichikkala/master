package com.zone.zissa.controller;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.ResourceService;
import com.zone.zissa.service.impl.CategoryServiceImpl;
import com.zone.zissa.service.impl.ResourceServiceImpl;
import com.zone.zissa.service.impl.RoleServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/** The ResourceMgmtController class. */
@RestController
@RequestMapping("/v1/resources")
@Api(value = "zissa", tags = {"Resource-mgmt-controller"})
public class ResourceMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ResourceMgmtController.class);

  /** The resource service impl. */
  @Autowired
  private ResourceService resourceServiceImpl;

  /** The resource service. */
  @Autowired
  private ResourceServiceImpl resourceService;

  /** The category service impl. */
  @Autowired
  private CategoryServiceImpl categoryServiceImpl;

  /** The role service impl. */
  @Autowired
  private RoleServiceImpl roleServiceImpl;

  /**
   * add Resource controller.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "resourceData", value = "resource Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "add Resource",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Resource.class, responseContainer = "List",
      httpMethod = "POST",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(value = {
      @ApiResponse(code = RestApiMessageConstants.CREATED, message = "Created"),
      @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
          message = "BadRequest"),
      @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
          message = "Forbidden"),
      @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
          message = "Internal Server Error")})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ServiceResponse<List<Resource>> addResource(
      @Valid @RequestBody final String resourceData) throws JSONException {
    LOGGER.debug("Add new Attribute Controller implementation");

    ServiceResponse<List<Resource>> response = new ServiceResponse<>();

    List<Resource> resourceList = resourceServiceImpl.addResource(resourceData);

    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setData(resourceList);
    response.setMessage(RestApiMessageConstants.ADD_RESOURCE);

    return response;
  }

  /**
   * Get last resource by categoryid controller.
   *
   * @param categoryID the category ID
   * @return Resource
   */
  @ApiOperation(value = "View last resource by categoryid",
      notes = "Return resource details", response = Resource.class,
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/lastresource/{categoryID}")
  public ServiceResponse<Resource> getLastResourceByCategory(@ApiParam(
      value = "Categoryid of the category for which "
          + "you need last resource details",
      required = true) @PathVariable final Integer categoryID) {
    LOGGER.debug("Get Last Resource by category Controller implementation");
    ServiceResponse<Resource> response = new ServiceResponse<>();

    Resource resourceObject =
        resourceServiceImpl.getLastResourceByCategory(categoryID);
    response.setData(resourceObject);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_LAST_RESOURCE_BY_CATEGORY);
    return response;
  }

  /**
   * Get all disposed resources by categoryid controller.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @ApiOperation(value = "View list of disposed resources by categoryid",
      notes = "Return disposed resources details", response = Resourcebin.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/disposed/{categoryID}")
  public ServiceResponse<List<Resourcebin>> getDisposedResourcesByCategoryId(
      @ApiParam(
          value = "Categoryid for which you need list of disposed resources",
          required = true) @PathVariable final Integer categoryID) {
    LOGGER
        .debug("Get Disposed Resources by category Controller implementation");

    ServiceResponse<List<Resourcebin>> response = new ServiceResponse<>();

    List<Resourcebin> resourceRecords =
        resourceServiceImpl.getDisposedResourcesByCategory(categoryID);
    response.setStatus(HttpServletResponse.SC_OK);
    response
        .setMessage(RestApiMessageConstants.GET_DISPOSE_RESOURCE_BY_CATEGORY);
    response.setData(resourceRecords);
    return response;
  }

  /**
   * Get resource by resource_ID controller.
   *
   * @param resourceID the resource ID
   * @return Resource
   */
  @ApiOperation(value = "View resource details by resourceid",
      notes = "Return resource details", response = Resource.class,
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/resourceobj/{resourceID}")
  public ServiceResponse<Resource> getResource(
      @ApiParam(value = "Resourceid for which you need resource details",
          required = true) @PathVariable final Integer resourceID) {
    LOGGER.debug("Get Resource Controller implementation");

    ServiceResponse<Resource> response = new ServiceResponse<>();

    Resource resourceObject = resourceServiceImpl.getResource(resourceID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_RESOURCE);
    response.setData(resourceObject);

    return response;
  }

  /**
   * Get disposed resource by resource_ID controller.
   *
   * @param resourceID the resource ID
   * @return Resourcebin
   */
  @ApiOperation(value = "View disposed resource details by resourceid",
      notes = "Return disposed resource details", response = Resourcebin.class,
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/disposedobj/{resourceID}")
  public ServiceResponse<Resourcebin> getDisposedResource(@ApiParam(
      value = "Resourceid for which you need disposed resource details",
      required = true) @PathVariable final Integer resourceID) {
    LOGGER.debug("Get Disposed Resource Controller implementation");

    ServiceResponse<Resourcebin> response = new ServiceResponse<>();

    Resourcebin resourceBinObject =
        resourceServiceImpl.getDisposedResource(resourceID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_DISPOSE_RESOURCE);
    response.setData(resourceBinObject);
    return response;
  }

  /**
   * Get resources by resource_ID list controller.
   *
   * @param resourceIdList the resource id list
   * @return the list
   */
  @ApiOperation(value = "View list of resources details by resourceid list",
      notes = "Return list of resources details", response = Resource.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/resourceobj")
  public ServiceResponse<List<Resource>> getResourcesbyResourceIdList(
      @RequestParam("resource_ID") final List<Integer> resourceIdList) {
    LOGGER.debug("Get Resource Controller implementation");
    ServiceResponse<List<Resource>> response = new ServiceResponse<>();

    List<Resource> resourceObjectList =
        resourceServiceImpl.getResourcesbyResourceIdList(resourceIdList);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_RESOURCE_DETAILS);
    response.setData(resourceObjectList);
    return response;
  }

  /**
   * Get all resources by category.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @ApiOperation(value = "View list of resources details by categoryid",
      notes = "Return list of resources", response = Resource.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/{categoryID}")
  public ServiceResponse<List<Resource>> getAllResourcesByCategoryId(
      @ApiParam(value = "categoryid for which you need list of resources",
          required = true) @PathVariable final Integer categoryID) {
    LOGGER.debug("Get all Resources by category Controller implementation");

    ServiceResponse<List<Resource>> response = new ServiceResponse<>();

    List<Resource> resourceRecords =
        resourceServiceImpl.getResourcesByCategoryId(categoryID);

    response.setStatus(HttpServletResponse.SC_OK);
    response.setData(resourceRecords);
    response.setMessage(RestApiMessageConstants.GET_ALL_RESOURCE_BY_CATEGORY);
    return response;
  }

  /**
   * Delete Resource controller.
   *
   * @param resourceID the resource ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Delete Resource",
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
  @DeleteMapping("/{resourceID}")
  public ServiceResponse deleteResource(
      @ApiParam(value = "Resourceid of the resource which needs to be deleted",
          required = true) @NotNull @PathVariable final Integer resourceID) {
    LOGGER.debug("Delete Resource Controller implementation");

    ServiceResponse response = new ServiceResponse();

    resourceServiceImpl.deleteResource(resourceID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.DELETE_RESOURCE);
    return response;
  }

  /**
   * Update Resources controller.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "resourceData", value = "resource Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Update Resource",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Resource.class, responseContainer = "List", httpMethod = "PUT",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
              message = "BadRequest"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @PutMapping
  public ServiceResponse<List<Resource>> updateResource(
      @RequestBody final String resourceData) throws JSONException {
    LOGGER.debug("Update Attribute Controller implementation");

    ServiceResponse<List<Resource>> response = new ServiceResponse<>();

    List<Resource> resourceList =
        resourceServiceImpl.updateResource(resourceData);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.UPDATE_RESOURCE);
    response.setData(resourceList);
    return response;
  }

  /**
   * Dispose Resource controller. @RequestBody disposeData
   *
   * @param disposeData the dispose data
   * @return ServiceResponse
   * @throws JSONException the JSON exception
   */
  @ApiOperation(value = "Dispose Resources",
      notes = "Return success response if success,"
          + "or exception if something wrong",
      response = ServiceResponse.class, httpMethod = "DELETE",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @DeleteMapping("/dispose")
  public ServiceResponse disposeResources(@RequestBody final String disposeData)
      throws JSONException {
    LOGGER.debug("Dispose Resources Controller implementation");

    ServiceResponse response = new ServiceResponse();

    List<String> resourceAllocationList =
        resourceServiceImpl.disposeResources(disposeData);
    if (resourceAllocationList.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_OK);
      response.setMessage(RestApiMessageConstants.DISPOSE_RESOURCE);
    } else {
      response.setStatus(HttpServletResponse.SC_CONFLICT);
      response.setMessage(RestApiMessageConstants.DISPOSE_ALLOCATED_RESOURCE);
    }
    return response;
  }

  /**
   * Restore Resource controller.
   *
   * @param resourceID the resource ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Restore Resources by resourceid list",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = ServiceResponse.class, httpMethod = "DELETE",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @DeleteMapping("/restore")
  public ServiceResponse restoreResources(
      @RequestParam("resource_ID") final List<Resourcebin> resourceID) {
    LOGGER.debug("Restore Resource Controller implementation");

    ServiceResponse response = new ServiceResponse();

    resourceServiceImpl.restoreResources(resourceID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.RESTORE_RESOURCE);

    return response;
  }

  /**
   * get Resources By SearchTerm controller.
   *
   * @param categoryID the category ID
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  @ApiOperation(
      value = "View list of resources by categoryid,"
          + "page,size,searchText and direction",
      notes = "Return list of resources", response = Resource.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping
  public PageServiceResponse<Object> getResourcesBySearchTerm(
      @RequestParam("category_ID") final List<Integer> categoryID,
      @RequestParam("page") final int page,
      @RequestParam("size") final int size,
      @RequestParam("searchText") final String searchText,
      @RequestParam("direction") final String direction,
      @RequestParam(value = "attrid",
          required = false) final short attributeId) {
    LOGGER.debug("Get all Resources Controller implementation");

    PageServiceResponse<Object> response = new PageServiceResponse<>();

    List<Resource> resourceSearchList =
        resourceServiceImpl.getAllResourcesBySearchTerm(categoryID, page, size,
            searchText, direction, attributeId);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_RESOURCE_BY_SEARCHTERM);
    response.setData(resourceSearchList);
    response.setTotalRecords(resourceService.getCount());
    resourceService.setCount(0);
    return response;
  }

  /**
   * get Disposed Resources By SearchTerm controller.
   *
   * @param categoryID the category ID
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return response
   */
  @ApiOperation(
      value = "View list of disposed resources by categoryid,"
          + "page,size,searchText and direction",
      notes = "Return list of disposed resources", response = Resourcebin.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/disposed")
  public PageServiceResponse<Object> getDisposedResourcesBySearchTerm(
      @RequestParam("category_ID") final List<Integer> categoryID,
      @RequestParam("page") final int page,
      @RequestParam("size") final int size,
      @RequestParam("searchText") final String searchText,
      @RequestParam("direction") final String direction,
      @RequestParam(value = "attrid",
          required = false) final short attributeId) {
    LOGGER.debug("Get all Disposed Resources Controller implementation");

    PageServiceResponse<Object> response = new PageServiceResponse<>();

    List<Resourcebin> resourceSearchList =
        resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryID,
            page, size, searchText, direction, attributeId);
    response.setStatus(HttpServletResponse.SC_OK);
    response
        .setMessage(RestApiMessageConstants.GET_DISPOSE_RESOURCE_BY_SEARCHTERM);
    response.setData(resourceSearchList);
    response.setTotalRecords(resourceService.getCount());
    resourceService.setCount(0);
    return response;
  }

  /**
   * Allocate or Deallocate Resource controller.
   *
   * @param allocationData the allocation data
   * @return ServiceResponse
   * @throws JSONException the JSON exception
   * @throws ParseException the parse exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "allocationData", value = "allocation Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "resource allocation or deallocation",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = ServiceResponse.class, httpMethod = "POST",
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
  @PostMapping("/allocations")
  public ServiceResponse resourceAllocation(
      @RequestBody final String allocationData)
      throws JSONException, ParseException {
    LOGGER.debug("Resource Allocation Management Controller implementation");
    ServiceResponse response = new ServiceResponse();

    resourceServiceImpl.resourceAllocation(allocationData);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.RESOURCE_ALLOCATION);
    return response;
  }

  /**
   * Get all categories with view permission for a user controller.
   *
   * @return the set
   */
  @ApiOperation(value = "View list of categories with view permissions",
      notes = "Return list of categories", response = Category.class,
      responseContainer = "Set", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden")})
  @GetMapping("viewpermissions")
  public ServiceResponse<Set<Category>> getAllCategoriesWithViewPermissions() {
    LOGGER.debug("Get all Categories with view Controller implementation");

    ServiceResponse<Set<Category>> response = new ServiceResponse<>();
    Set<Category> categoryList =
        resourceService.getAllCategoriesWithViewPermissions();
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_VIEW);
    response.setData(categoryList);
    response.setStatus(HttpServletResponse.SC_OK);
    return response;
  }

  /**
   * Get all categories with add permission for a user controller.
   *
   * @return the set
   */
  @ApiOperation(value = "View list of categories with add permissions",
      notes = "Return list of categories", response = Category.class,
      responseContainer = "Set", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden")})
  @GetMapping("addpermissions")
  public ServiceResponse<Set<Category>> getAllCategoriesWithAddPermissions() {
    LOGGER.debug("Get all Categories with add Controller implementation");

    ServiceResponse<Set<Category>> response = new ServiceResponse<>();
    Set<Category> categoryList =
        resourceService.getAllCategoriesWithAddPermissions();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setData(categoryList);
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_ADD);
    return response;
  }

  /**
   * Get all categories with dispose permission for a user controller.
   *
   * @return the set
   */
  @ApiOperation(value = "View list of categories with dispose permissions",
      notes = "Return list of categories", response = Category.class,
      responseContainer = "Set", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("disposepermissions")
  public ServiceResponse<Set<Category>> getAllCategoriesWithDisposePermissions() {
    LOGGER.debug("Get all Categories with Dispose Controller implementation");

    ServiceResponse<Set<Category>> response = new ServiceResponse<>();
    Set<Category> categoryList =
        resourceService.getAllCategoriesWithDisposePermissions();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setData(categoryList);
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_DISPOSE);
    return response;
  }

  /**
   * Get all categories with allocation permission for a user controller.
   *
   * @return the set
   */
  @ApiOperation(value = "View list of categories with allocate permissions",
      notes = "Return list of categories", response = Category.class,
      responseContainer = "Set", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("allocatepermissions")
  public ServiceResponse<Set<Category>> getAllCategoriesWithAllocatePermissions() {
    LOGGER.debug(
        "Get all Categories with Allocate Permissions Controller implementation");

    ServiceResponse<Set<Category>> response = new ServiceResponse<>();

    Set<Category> categoryList =
        resourceService.getAllCategoriesWithAllocatePermissions();
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_ALLOCATION);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setData(categoryList);
    return response;
  }

  /**
   * Get all permissions based on role and category controller.
   *
   * @param roleID the role ID
   * @param categoryID the category ID
   * @return the list
   */
  @ApiOperation(value = "View list of permissions based on role and category",
      notes = "Return list of permissions", response = Permission.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/permissions")
  public ServiceResponse<List<Permission>> getAllPermissionsByRoleAndCategory(
      @RequestParam("role_ID") final Integer roleID,
      @RequestParam("category_ID") final Integer categoryID) {
    LOGGER.debug(
        "Get all Permissions by role and category Controller implementation");

    ServiceResponse<List<Permission>> response = new ServiceResponse<>();
    List<Permission> permissionObject =
        resourceService.getAllPermissionsByRoleAndCategory(roleID, categoryID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(
        RestApiMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY);
    response.setData(permissionObject);
    return response;
  }
}
