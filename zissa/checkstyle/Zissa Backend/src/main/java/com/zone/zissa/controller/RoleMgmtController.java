package com.zone.zissa.controller;

import com.zone.zissa.model.Role;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** The RoleMgmtController Class. */
@RestController
@RequestMapping("/v1/roles")
@Api(value = "zissa", tags = {"Role-mgmt-controller"})
public class RoleMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(RoleMgmtController.class);

  /** The role service impl. */
  @Autowired
  private RoleService roleServiceImpl;

  /**
   * Add role controller.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "roleData", value = "role Data",
      required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Add Role",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Role.class, httpMethod = "POST",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(value = {
      @ApiResponse(code = RestApiMessageConstants.CREATED, message = "Created"),
      @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
          message = "BadRequest"),
      @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
          message = "Forbidden"),
      @ApiResponse(code = RestApiMessageConstants.CONFLICT,
          message = "Conflict"),
      @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
          message = "Internal Server Error")})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ServiceResponse<Role> addRole(@RequestBody final String roleData)
      throws JSONException {
    LOGGER.debug("Add new Role Controller implementation");

    ServiceResponse<Role> response = new ServiceResponse<>();
    Role roleObject = roleServiceImpl.addRole(roleData);
    response.setData(roleObject);
    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setMessage(RestApiMessageConstants.ADD_ROLE);

    return response;
  }

  /**
   * Update Role controller.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "roleData", value = "role Data",
      required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Update Role",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Role.class, httpMethod = "PUT",
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
  public ServiceResponse<Role> updateRole(@RequestBody final String roleData)
      throws JSONException {
    LOGGER.debug("Update Role Controller implementation");

    ServiceResponse<Role> response = new ServiceResponse<>();

    Role roleObject = roleServiceImpl.updateRole(roleData);
    response.setData(roleObject);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.UPDATE_ROLE);
    return response;
  }

  /**
   * Delete Role controller.
   *
   * @param roleID the role ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Delete Role",
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
  @DeleteMapping("/{roleID}")
  public ServiceResponse deleteRole(
      @ApiParam(value = "Roleid of the user which needs to be deleted",
          required = true) @PathVariable final Integer roleID) {
    LOGGER.debug("Delete Role Controller implementation");

    ServiceResponse response = new ServiceResponse();

    roleServiceImpl.deleteRole(roleID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.DELETE_ROLE);
    return response;
  }

  /**
   * Get all Roles controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View a list of roles", notes = "Return all roles",
      response = Role.class, responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping
  public ServiceResponse<List<Role>> getAllRoles() {
    LOGGER.debug("Get all Roles Controller implementation");
    ServiceResponse<List<Role>> response = new ServiceResponse<>();

    List<Role> templateSet = roleServiceImpl.getAllRoles();
    response.setData(templateSet);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GETTING_ROLES);
    return response;
  }

  /**
   * Get all Role related permissions controller.
   *
   * @param roleId the role id
   * @return Role
   */
  @ApiOperation(value = "View list of permissions for a role",
      notes = "Return all permissions by role", response = Role.class,
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
  @GetMapping("{roleId}/permissions")
  public ServiceResponse<Role> getAllPermissionsByRole(
      @ApiParam(value = "Roleid for which need to list permissions",
          required = true) @PathVariable final Integer roleId) {
    LOGGER.debug("Get all Permissions by Role Controller implementation");

    ServiceResponse<Role> response = new ServiceResponse<>();

    Role roleObject = roleServiceImpl.getAllPermissionsByRole(roleId);
    response.setStatus(HttpServletResponse.SC_OK);
    response
        .setMessage(RestApiMessageConstants.GETTING_ALL_PERMISSIONS_BY_ROLE);
    response.setData(roleObject);
    return response;
  }
}
