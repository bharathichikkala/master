package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.LdapMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.LdapUser;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.LdapService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class LdapMgmtControllerTest.
 */
public class LdapMgmtControllerTest extends ZissaApplicationTest {

  /** The ldap user. */
  @InjectMocks
  private LdapUser ldapUser;

  /** The ldap mgmt controller. */
  @InjectMocks
  private LdapMgmtController ldapMgmtController;

  /** The ldap service. */
  @Mock
  private LdapService ldapService;

  /** The Constant username. */
  private static final String username = "BathiyaT";


  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Gets the all ldap users test.
   *
   * @return the all ldap users test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  public void getAllLdapUsersTest() throws Exception {
    List<LdapUser> ldapUserList = new ArrayList<LdapUser>();
    ldapUser = new LdapUser();
    ldapUser.setEmail("bathiyat@zone24x7.com");
    ldapUserList.add(ldapUser);
    when(ldapService.getAllLdapUsers()).thenReturn(ldapUserList);
    ServiceResponse<List<LdapUser>> response =
        ldapMgmtController.getAllLdapUsers();
    assertThat(200, is(response.getStatus()));
    assertThat(RestApiMessageConstants.GETTING_LDAP_USERS,
        is(response.getMessage()));
    assertThat(1, is(response.getData().size()));
  }

  /**
   * Gets the all ldap users by search string test.
   *
   * @return the all ldap users by search string test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  public void getAllLdapUsersBySearchStringTest() throws Exception {
    List<LdapUser> ldapUserList = new ArrayList<LdapUser>();
    ldapUser = new LdapUser();
    ldapUser.setEmail("bathiyat@zone24x7.com");
    String searchText = "bathiya";
    ldapUserList.add(ldapUser);
    when(ldapService.getAllLdapUsersBySearchString(searchText))
        .thenReturn(ldapUserList);
    ServiceResponse<List<LdapUser>> response =
        ldapMgmtController.getAllLdapUsersBySearchString(searchText);
    assertThat(200, is(response.getStatus()));
    assertThat(RestApiMessageConstants.LDAP_USER_BY_SEARCH_STRING,
        is(response.getMessage()));
    assertThat(1, is(response.getData().size()));
  }
}
