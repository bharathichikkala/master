package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import com.zone.zissa.controller.LdapMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.service.impl.LdapServiceImpl;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class LdapServiceTest.
 */
public class LdapServiceTest extends ZissaApplicationTest {

  /** The ldap service impl. */
  @Spy
  @InjectMocks
  private LdapServiceImpl ldapServiceImpl;

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

  /** The ldap filter. */
  @Value("${ldap-filter}")
  private String ldapFilter;

  /** The ldap user. */
  @InjectMocks
  private LdapUser ldapUser;

  /** The ldap mgmt controller. */
  @InjectMocks
  private LdapMgmtController ldapMgmtController;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(ldapServiceImpl, "ldapUrl", ldapUrl);
    ReflectionTestUtils.setField(ldapServiceImpl, "ldapUsername", ldapUsername);
    ReflectionTestUtils.setField(ldapServiceImpl, "ldapPassword", ldapPassword);
    ReflectionTestUtils.setField(ldapServiceImpl, "ldapSearchBase",
        ldapSearchBase);
    ReflectionTestUtils.setField(ldapServiceImpl, "ldapFilter", ldapFilter);
  }

  /**
   * Gets the all ldap users test.
   *
   * @return the all ldap users test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  public void getAllLdapUsersTest() throws Exception {
    List<LdapUser> ldapUsers = ldapServiceImpl.getAllLdapUsers();
    assertThat(0, not(ldapUsers.size()));
  }

  /**
   * Gets the all ldap users by string test.
   *
   * @return the all ldap users by string test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  public void getAllLdapUsersByStringTest() throws Exception {
    String searchString = "bathiya";
    List<LdapUser> ldapUsers =
        ldapServiceImpl.getAllLdapUsersBySearchString(searchString);
    assertThat(1, is(ldapUsers.size()));
  }

  /**
   * Gets the all ldap users by string failure test.
   *
   * @return the all ldap users by string failure test
   * @throws Exception the exception
   */
  @Test(expected = NoContentException.class)
  @Transactional
  public void getAllLdapUsersByStringFailureTest() throws Exception {
    String searchString = "ssn";
    ldapServiceImpl.getAllLdapUsersBySearchString(searchString);
  }
}
