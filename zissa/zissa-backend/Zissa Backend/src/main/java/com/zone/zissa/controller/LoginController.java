package com.zone.zissa.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.ServiceResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The LoginController Class.
 */
@RestController
@Api(value = "zissa", description = "Operations pertaining to loggedin user in zissa")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserRepository userRepo;

    /**
     * Login controller
     * 
     * @return ServiceResponse
     */
    @ApiOperation(value = "View logged in user details", notes = "Return user details", response = User.class, httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 401, message = "Bad Credentials"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/")
    public ServiceResponse<com.zone.zissa.model.User> index() {
        ServiceResponse<com.zone.zissa.model.User> response = new ServiceResponse<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.zone.zissa.model.User userObject = userRepo.findByUserName(user.getUsername());
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Login Successfull");
            response.setData(userObject);
        } catch (Exception ex) {
            LOGGER.error("Login throw an exception", ex);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setMessage("Bad Credentials");
            response.setErrorMessage("Error in login");
        }
        return response;
    }
}
