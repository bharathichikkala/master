package com.zone.zissa.controller;

import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.LdapService;
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

/** The LdapMgmtController Class. */
@RestController
@RequestMapping("/v1/ldapusers")
public class LdapMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(LdapMgmtController.class);

  /** The ldap service impl. */
  @Autowired
  private LdapService ldapServiceImpl;

  /**
   * Get all ldap users controller.
   *
   * @return the list of ldap users
   * @throws NamingException the naming exception
   */
  @GetMapping
  public ServiceResponse<List<LdapUser>> getAllLdapUsers()
      throws NamingException {
    LOGGER.debug("Get all Ldap Users Controller implementation");
    ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
    List<LdapUser> ldapUsers = ldapServiceImpl.getAllLdapUsers();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GETTING_LDAP_USERS);
    response.setData(ldapUsers);
    return response;
  }

  /**
   * Get all ldap users based on search string controller.
   *
   * @param searchString the search string
   * @return the list
   * @throws NamingException the naming exception
   */
  @GetMapping("/search")
  public ServiceResponse<List<LdapUser>> getAllLdapUsersBySearchString(
      @RequestParam("searchString") final String searchString)
      throws NamingException {
    LOGGER.debug("Get all Ldap Users Controller implementation");
    ServiceResponse<List<LdapUser>> response = new ServiceResponse<>();
    List<LdapUser> filteredUserList =
        ldapServiceImpl.getAllLdapUsersBySearchString(searchString);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.LDAP_USER_BY_SEARCH_STRING);
    response.setData(filteredUserList);
    return response;
  }
}
