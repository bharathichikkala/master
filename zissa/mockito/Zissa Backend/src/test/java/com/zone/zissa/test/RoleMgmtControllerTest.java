package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.RoleMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Role;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONObject;

/**
 * The Class RoleMgmtControllerTest.
 */
public class RoleMgmtControllerTest extends ZissaApplicationTest {

  /** The role. */
  @InjectMocks
  private Role role;

  /** The role mgmt controller. */
  @InjectMocks
  private RoleMgmtController roleMgmtController;

  /** The role service. */
  @Mock
  private RoleService roleService;

  /** The Constant username. */
  private static final String username = "BathiyaT";


  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Adds the role test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addRoleTest() throws JSONException {

    JSONObject addRoleData = new JSONObject();
    role = new Role();
    role.setName("Admin");
    when(roleService.addRole(addRoleData.toJSONString())).thenReturn(role);
    ServiceResponse<Role> response =
        roleMgmtController.addRole(addRoleData.toJSONString());
    assertThat(role.getName(), is(response.getData().getName()));
    assertThat(response.getStatus(), is(201));
  }

  /**
   * Update role test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateRoleTest() throws JSONException {

    JSONObject updateRoleData = new JSONObject();
    role = new Role();
    role.setName("Admin");
    when(roleService.updateRole(updateRoleData.toJSONString()))
        .thenReturn(role);
    ServiceResponse<Role> response =
        roleMgmtController.updateRole(updateRoleData.toJSONString());
    assertThat(role.getName(), is(response.getData().getName()));
    assertThat(RestApiMessageConstants.UPDATE_ROLE, is(response.getMessage()));
  }

  /**
   * Delete role test.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteRoleTest() {
    role = new Role();
    role.setRoleID(1);
    ServiceResponse response = roleMgmtController.deleteRole(role.getRole_ID());
    verify(roleService, times(1)).deleteRole(role.getRole_ID());
    assertThat(RestApiMessageConstants.DELETE_ROLE, is(response.getMessage()));
  }

  /**
   * Gets the all roles test.
   *
   * @return the all roles test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllRolesTest() throws Exception {

    List<Role> roleList = new ArrayList<Role>();
    role = new Role();
    role.setName("Developer");
    roleList.add(role);
    when(roleService.getAllRoles()).thenReturn(roleList);
    ServiceResponse<List<Role>> response = roleMgmtController.getAllRoles();
    assertThat(200, is(response.getStatus()));
    assertThat(1, is(response.getData().size()));
  }

  /**
   * Gets the all permissions by role test.
   *
   * @return the all permissions by role test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleTest() {
    role = new Role();
    role.setRoleID(1);
    when(roleService.getAllPermissionsByRole(1)).thenReturn(role);
    ServiceResponse response =
        roleMgmtController.getAllPermissionsByRole(role.getRole_ID());
    assertThat(200, is(response.getStatus()));
    assertThat(RestApiMessageConstants.GETTING_ALL_PERMISSIONS_BY_ROLE,
        is(response.getMessage()));
  }
}
