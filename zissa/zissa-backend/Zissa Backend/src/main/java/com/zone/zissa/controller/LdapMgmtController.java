package com.zone.zissa.controller;

import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.LdapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * The LdapMgmtController Class.
 */
@RestController
@RequestMapping("/v1/ldapusers")
@Api(value = "zissa", description = "Operations pertaining to ldap in zissa")
public class LdapMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapMgmtController.class);

    @Autowired
    private LdapService ldapServiceImpl;

    /**
     * Get all ldap users controller.
     *
     * @return List<LdapUser>
     */
    @ApiOperation(value = "View list of ldapusers", notes = "Return all ldapusers", response = LdapUser.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @GetMapping
    public ServiceResponse<List<LdapUser>> getAllLdapUsers() {
        LOGGER.debug("Get all Ldap Users Controller implementation");
        ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
        try {
            List<LdapUser> ldapUsers = ldapServiceImpl.getAllLdapUsers();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Ldap Users is Successful");
            response.setData(ldapUsers);
        } catch (NamingException ex) {
            LOGGER.error("getting all ldap users throw an exception", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed  in Getting all the Ldap Users");
            response.setErrorMessage("Error in Getting all the Ldap Users");
        }
        return response;
    }

    /**
     * Get all ldap users based on search string controller.
     * 
     * @param searchString
     * @return List<LdapUser>
     */
    @ApiOperation(value = "View ldapusers based on search string", notes = "Return ldapusers by search string", response = LdapUser.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @GetMapping("/search")
    public ServiceResponse<List<LdapUser>> getAllLdapUsersBySearchString(
            @RequestParam("searchString") String searchString) {
        LOGGER.debug("Get all Ldap Users Controller implementation");
        ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
        try {
            List<LdapUser> filteredUserList = ldapServiceImpl.getAllLdapUsersBySearchString(searchString);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Ldap Users by Search string is Successful");
            response.setData(filteredUserList);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.LDAP_SEARCH_ERROR, ex);
            return ExceptionHandlerClass.noContentException(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.LDAP_SEARCH_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting all the Ldap Users by Search string");
            response.setErrorMessage("Error in Getting all the Ldap Users by Search string");
        }
        return response;
    }
}
