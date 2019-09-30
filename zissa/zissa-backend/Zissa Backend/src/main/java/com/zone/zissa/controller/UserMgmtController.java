package com.zone.zissa.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The UserMgmtController Class.
 */
@RestController
@RequestMapping("/v1/users")
@Api(value = "zissa", description = "Operations pertaining to users in zissa")
public class UserMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMgmtController.class);

    @Autowired
    private UserService userServiceImpl;

    @Autowired
    private UserRepository userRepo;

    /**
     * Add User controller.
     *
     * @param userData
     * @return User
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userData", value = "user Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Add User", notes = "Return success response if success, or exception if something wrong", response = User.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "BadRequest"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ServiceResponse<User> addUser(@RequestBody String userData) {

        LOGGER.debug("Add new User Controller implementation");
        ServiceResponse<User> response = new ServiceResponse<>();
        try {
            User user = userServiceImpl.addUser(userData);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setData(userRepo.findByUserName(user.getUserName()));
            response.setMessage("User Created Successfully");
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_USER_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_USER_ERROR, ex);
            response.setMessage("Failed to add user");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in adding user");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_USER_ERROR, ex);
            response.setMessage("Failed in User Creation");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setErrorMessage("Error in User Creation");
        }

        return response;
    }

    /**
     * Update User controller.
     *
     * @param userData
     * @return User
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userData", value = "user Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update User", notes = "Return success response if success, or exception if something wrong", response = User.class, httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<User> updateUser(@RequestBody String userData) {
        LOGGER.debug("Update User Controller implementation");
        ServiceResponse<User> response = new ServiceResponse<>();
        try {
            User user = userServiceImpl.updateUser(userData);
            response.setData(userRepo.findByUserName(user.getUserName()));
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("User Updated Successfully");
        } catch (DataNotFoundException ex) {
            LOGGER.error("updating user successfully", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_USER_ERROR, ex);
            response.setMessage("Failed to update user");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in updating user");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.UPDATE_USER_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in User Updation");
            response.setErrorMessage("Error in User Updation");
        }

        return response;
    }

    /**
     * Get all Users controller.
     *
     * @return List<User>
     */
    @ApiOperation(value = "View a list of users", notes = "Return all users", response = User.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public ServiceResponse<List<User>> getAllUsers() {
        LOGGER.debug("Get all Users Controller implementation");
        ServiceResponse<List<User>> response = new ServiceResponse<>();
        try {
            List<User> userList = userServiceImpl.getAllUsers();
            response.setData(userList);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Users is successfull");
        } catch (Exception ex) {
            LOGGER.error("get all users throw an exception", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting User");
            response.setErrorMessage("Error in Getting User");
        }
        return response;
    }

    /**
     * Delete User controller.
     *
     * @param user_ID
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete User", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{userID}")
    public ServiceResponse deleteUser(
            @ApiParam(value = "Userid of the user which needs to be deleted", required = true) @PathVariable Integer userID) {
        LOGGER.debug("Delete User Controller implementation");
        ServiceResponse response = new ServiceResponse();
        try {
            userServiceImpl.deleteUser(userID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("User is deleted successfully");
        } catch (DataNotFoundException ex) {
            LOGGER.error("deleting user successfully", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_USER_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setMessage(RestAPIMessageConstants.USER_DELETION_FAILURE);
            response.setErrorMessage("Cannot delete a parent row: a foreign key constraint fails");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_USER_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.USER_DELETION_FAILURE);
            response.setErrorMessage("Error in Deleting User");

        }
        return response;
    }
}
