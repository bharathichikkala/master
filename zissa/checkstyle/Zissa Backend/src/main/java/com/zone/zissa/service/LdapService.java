package com.zone.zissa.service;

import com.zone.zissa.model.LdapUser;
import java.util.List;
import javax.naming.NamingException;
import org.springframework.web.bind.annotation.RequestParam;

/** The Interface LdapService. */
public interface LdapService {

  /**
   * Get the all ldap users.
   *
   * @return the list
   * @throws NamingException the naming exception
   */
  List<LdapUser> getAllLdapUsers() throws NamingException;

  /**
   * Get all ldap users based on searchstring.
   *
   * @param searchString the search string
   * @return the list
   * @throws NamingException the naming exception
   */
  List<LdapUser> getAllLdapUsersBySearchString(
      @RequestParam("searchString") String searchString) throws NamingException;
}
