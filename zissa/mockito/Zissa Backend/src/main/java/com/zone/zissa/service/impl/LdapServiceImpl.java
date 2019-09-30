package com.zone.zissa.service.impl;

import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.service.LdapService;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/** The LdapServiceImpl class. */
@Profile("ldap")
@Service
public class LdapServiceImpl implements LdapService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(LdapServiceImpl.class);

  /** The ldap url. */
  @Value("${ldap.urls}")
  private String ldapUrl;

  /** The ldap username. */
  @Value("${ldap.username}")
  private String ldapUsername;

  /** The ldap password. */
  @Value("${ldap.password}")
  private String ldapPassword;

  /** The ldap search base. */
  @Value("${ldap.searchbase}")
  private String ldapSearchBase;

  /** The ldap filter base. */
  @Value("${ldap-filter}")
  private String ldapFilter;

  /**
   * Get all ldap users service implementation.
   *
   * @return the list
   * @throws NamingException the naming exception
   */
  @Override
  public List<LdapUser> getAllLdapUsers() throws NamingException {

    LOGGER.info("Getting all Ldap Users Service implementation");
    Hashtable<String, String> env = new Hashtable<>();
    SearchControls searchCtrls = new SearchControls();
    searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    env.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, ldapUrl);
    env.put(Context.REFERRAL, "follow");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
    env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
    DirContext context = new InitialDirContext(env);
    String filter = ldapFilter;
    String searchBase = ldapSearchBase;
    NamingEnumeration answer = context.search(searchBase, filter, searchCtrls);
    List<LdapUser> userList = null;
    userList = new ArrayList<>();
    LdapUser user = null;
    while (answer.hasMoreElements()) {
      SearchResult sr = (SearchResult) answer.next();
      user = new LdapUser();
      user.setName(sr.getName());
      userList.add(user);
      Attributes attrs = sr.getAttributes();
      if (attrs != null) {
        this.ifAttrsNotNull(user, attrs);
      }
    }
    return userList;
  }

  /**
   * If attrs not null.
   *
   * @param user the user
   * @param attrs the attrs
   * @throws NamingException the naming exception
   */
  public void ifAttrsNotNull(final LdapUser user, final Attributes attrs)
      throws NamingException {
    LOGGER.info(
        "Attributes Not Null method in Ldap Users Service implementation");
    NamingEnumeration ae = attrs.getAll();
    while (ae.hasMore()) {
      Attribute attribute = (Attribute) ae.next();
      NamingEnumeration e = attribute.getAll();
      if (e.hasMore()) {
        if ("sAMAccountName".equals(attribute.getID())) {
          user.setsAMAccountName((String) e.next());
        } else if ("name".equals(attribute.getID())) {
          user.setName((String) (e.next()));
        } else if ("mail".equals(attribute.getID())) {
          user.setEmail((String) (e.next()));
        } else if ("givenName".equals(attribute.getID())) {
          user.setFirstName((String) (e.next()));
        } else if ("sn".equals(attribute.getID())) {
          user.setLastName((String) (e.next()));
        }
      }
    }
  }

  /**
   * Get all ldap users based on search string service implementation.
   *
   * @param searchString the search string
   * @return the list
   * @throws NamingException the naming exception
   */
  @Override
  public List<LdapUser> getAllLdapUsersBySearchString(final String searchString)
      throws NamingException {

    LOGGER
        .info("Getting all Ldap Users by search string Service implementation");
    List<LdapUser> userList = this.getAllLdapUsers();
    List<LdapUser> filteredUserList = new ArrayList<>();
    for (LdapUser object : userList) {
      if (object.getFirstName().toLowerCase()
          .startsWith(searchString.toLowerCase())) {
        filteredUserList.add(object);
      }
    }
    if (filteredUserList.isEmpty()) {
      throw new NoContentException(
          RestApiMessageConstants.LDAP_USER_NOT_EXISTS);
    }
    return filteredUserList;
  }
}


/** The LocalLdapServiceImpl class. */
@Profile("localldap")
@Service
class LocalLdapServiceImpl implements LdapService {
  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(LdapServiceImpl.class);

  /** The ldap url. */
  @Value("${ldap.urls}")
  private String ldapUrl;

  /** The ldap username. */
  @Value("${ldap.username}")
  private String ldapUsername;

  /** The ldap password. */
  @Value("${ldap.password}")
  private String ldapPassword;

  /** The ldap search base. */
  @Value("${ldap.searchbase}")
  private String ldapSearchBase;

  /** The ldap filter base. */
  @Value("${ldap-filter}")
  private String ldapFilter;

  /**
   * Get all ldap users service implementation.
   *
   * @return the list
   * @throws NamingException the NamingException
   */
  @Override
  public List<LdapUser> getAllLdapUsers() throws NamingException {

    LOGGER.info("Getting all Ldap Users Service implementation");
    Hashtable<String, String> env = new Hashtable<>();
    SearchControls searchCtrls = new SearchControls();
    searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
    env.put(Context.INITIAL_CONTEXT_FACTORY,
        "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, ldapUrl);
    env.put(Context.REFERRAL, "follow");
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
    env.put(Context.SECURITY_CREDENTIALS, ldapPassword);
    String filter = ldapFilter;
    String searchBase = ldapSearchBase;
    LdapUser user = null;
    List<LdapUser> userList = null;
    DirContext context = new InitialDirContext(env);
    NamingEnumeration answer = context.search(searchBase, filter, searchCtrls);
    userList = new ArrayList<>();
    while (answer.hasMoreElements()) {
      SearchResult sr = (SearchResult) answer.next();
      user = new LdapUser();
      user.setName(sr.getName());
      userList.add(user);
      Attributes attrs = sr.getAttributes();
      if (attrs != null) {
        this.ifAttrsNotNull(user, attrs);
      }
    }
    return userList;
  }


  /**
   * ifAttrsNotNull method.
   * 
   * @param user the user
   * @param attrs the attrs
   * @throws NamingException the NamingException
   */
  public void ifAttrsNotNull(final LdapUser user, final Attributes attrs)
      throws NamingException {
    LOGGER.info(
        "Attributes Not Null method in Ldap Users Service implementation");
    NamingEnumeration ae = attrs.getAll();
    while (ae.hasMore()) {
      Attribute attr = (Attribute) ae.next();
      NamingEnumeration e = attr.getAll();
      String name = "";
      if (e.hasMore()) {
        if ("uid".equals(attr.getID())) {
          name = (String) e.next();
          user.setsAMAccountName(name);
          user.setEmail(name.toLowerCase() + "@zone24x7.com");
        } else if ("displayName".equals(attr.getID())) {
          user.setName((String) (e.next()));
        } else if ("givenName".equals(attr.getID())) {
          user.setFirstName((String) (e.next()));
        } else if ("sn".equals(attr.getID())) {
          user.setLastName((String) (e.next()));
        }
      }
    }
  }

  /**
   * Get all ldap users based on search string service implementation.
   *
   * @param searchString the searchString
   * @return the list
   * @throws NamingException the NamingException
   */
  @Override
  public List<LdapUser> getAllLdapUsersBySearchString(final String searchString)
      throws NamingException {

    LOGGER
        .info("Getting all Ldap Users by search string Service implementation");
    List<LdapUser> userList = this.getAllLdapUsers();
    List<LdapUser> filteredUserList = new ArrayList<>();
    for (LdapUser object : userList) {
      if (object.getFirstName().toLowerCase()
          .startsWith(searchString.toLowerCase())) {
        filteredUserList.add(object);
      }
    }
    if (filteredUserList.isEmpty()) {
      throw new NoContentException(
          RestApiMessageConstants.LDAP_USER_NOT_EXISTS);
    }
    return filteredUserList;
  }
}
