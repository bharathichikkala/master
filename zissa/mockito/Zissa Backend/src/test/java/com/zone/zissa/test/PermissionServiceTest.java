package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.service.impl.PermissionService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class PermissionServiceTest.
 */
public class PermissionServiceTest extends ZissaApplicationTest {

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The category. */
  @InjectMocks
  private Category category;

  /** The operation. */
  @InjectMocks
  private Operation operation;

  /** The permission service. */
  @InjectMocks
  private PermissionService permissionService;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Permission exists test.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void permissionExistsTest() {
    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("BathiyaT");
    user.setRole(role);

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("View");


    category = new Category();
    category.setCategoryID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setRole(role);
    permission.setOperation(operation);
    permission.setCategory(category);
    permissionList.add(permission);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    when(permissionRepo.findByRole(user.getRole())).thenReturn(permissionList);

    boolean permissionExists = permissionService.permissionExists(
        category.getCategory_ID(), operation.getOperation_ID());
    assertThat(true, is(permissionExists));
  }

  /**
   * Gets the all role permissions test.
   *
   * @return the all role permissions test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllRolePermissionsTest() {
    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("View");


    category = new Category();
    category.setCategoryID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setRole(role);
    permission.setOperation(operation);
    permission.setCategory(category);
    permissionList.add(permission);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    when(permissionRepo.findByRole(user.getRole())).thenReturn(permissionList);

    when(permissionService.getAllRolePermissions()).thenReturn(permissionList);
  }

}
