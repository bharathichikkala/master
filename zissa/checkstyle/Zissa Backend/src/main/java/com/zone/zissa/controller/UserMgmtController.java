package com.zone.zissa.controller;

import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import java.util.Optional;
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

/** The UserMgmtController Class. */
@RestController
@RequestMapping("/v1/users")
@Api(value = "zissa", tags = {"User-mgmt-controller"})
public class UserMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(UserMgmtController.class);

  /** The user service impl. */
  @Autowired
  private UserService userServiceImpl;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /**
   * Add User controller.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "userData", value = "user Data",
      required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Add User",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = User.class, httpMethod = "POST",
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
  public ServiceResponse<User> addUser(@RequestBody final String userData)
      throws JSONException {

    LOGGER.debug("Add new User Controller implementation");
    ServiceResponse<User> response = new ServiceResponse<>();

    User user = userServiceImpl.addUser(userData);
    response.setStatus(HttpServletResponse.SC_CREATED);
    Optional<User> userObject = userRepo.findByUserName(user.getUserName());
    if (userObject.isPresent()) {
      response.setData(userObject.get());
    }
    response.setMessage(RestApiMessageConstants.ADD_USER);
    return response;
  }

  /**
   * Update User controller.
   *
   * @param userData the user data
   * @return User
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({@ApiImplicitParam(name = "userData", value = "user Data",
      required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Update User",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = User.class, httpMethod = "PUT",
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
  public ServiceResponse<User> updateUser(@RequestBody final String userData)
      throws JSONException {
    LOGGER.debug("Update User Controller implementation");
    ServiceResponse<User> response = new ServiceResponse<>();

    User user = userServiceImpl.updateUser(userData);
    Optional<User> userObject = userRepo.findByUserName(user.getUserName());
    if (userObject.isPresent()) {
      response.setData(userObject.get());
    }
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.UPDATE_USER);
    return response;
  }

  /**
   * Get all Users controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View a list of users", notes = "Return all users",
      response = User.class, responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping
  public ServiceResponse<List<User>> getAllUsers() {
    LOGGER.debug("Get all Users Controller implementation");
    ServiceResponse<List<User>> response = new ServiceResponse<>();

    List<User> userList = userServiceImpl.getAllUsers();
    response.setData(userList);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GETTING_USER);
    return response;
  }

  /**
   * Delete User controller.
   *
   * @param userID the user ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Delete User",
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
  @DeleteMapping("/{userID}")
  public ServiceResponse deleteUser(
      @ApiParam(value = "Userid of the user which needs to be deleted",
          required = true) @PathVariable final Integer userID) {
    LOGGER.debug("Delete User Controller implementation");
    ServiceResponse response = new ServiceResponse();

    userServiceImpl.deleteUser(userID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.DELETE_USER);
    return response;
  }
}
