package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.RoleMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.service.impl.RoleServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * The Class RoleServiceTest.
 */
public class RoleServiceTest extends ZissaApplicationTest {

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The category. */
  @InjectMocks
  private Category category;

  /** The operation. */
  @InjectMocks
  private Operation operation;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The role mgmt controller. */
  @InjectMocks
  private RoleMgmtController roleMgmtController;

  /** The role service impl. */
  @InjectMocks
  private RoleServiceImpl roleServiceImpl;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The role repo. */
  @Mock
  private RoleRepository roleRepo;

  /** The category repo. */
  @Mock
  private CategoryRepository categoryRepo;

  /** The operation repo. */
  @Mock
  private OperationRepository operationRepo;

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The Role mgmt controller. */
  @InjectMocks
  private RoleMgmtController RoleMgmtController;

  /**
   * Inits the.
   */
  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

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

    addRoleData.put("name", "User");
    addRoleData.put("administration_type", "1");
    addRoleData.put("category_ID", "1");

    JSONArray permissionsArray = new JSONArray();
    JSONObject permisssionobject1 = new JSONObject();
    permisssionobject1.put("category_ID", 1);
    JSONArray operationArray1 = new JSONArray();
    operationArray1.add(1);
    operationArray1.add(2);
    operationArray1.add(3);
    operationArray1.add(4);
    operationArray1.add(5);
    permisssionobject1.put("operation", operationArray1);
    permissionsArray.add(permisssionobject1);

    JSONObject permisssionobject2 = new JSONObject();
    permisssionobject2.put("category_ID", 2);
    JSONArray operationArray2 = new JSONArray();
    operationArray2.add(1);
    operationArray2.add(2);
    operationArray2.add(3);
    operationArray2.add(4);
    operationArray2.add(5);
    permisssionobject2.put("operation", operationArray2);
    permissionsArray.add(permisssionobject2);
    addRoleData.put("permissions", permissionsArray);

    role = new Role();
    role.setName("Admin");
    category = new Category();
    category.setName("Laptops");
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    Role roleResponse = roleServiceImpl.addRole(addRoleData.toJSONString());
    assertThat(roleResponse.getName(), is(role.getName()));
  }

  /**
   * Adds the role failure test.
   *
   * @throws JSONException the JSON exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addRoleFailureTest() throws JSONException {

    JSONObject addRoleData = new JSONObject();

    addRoleData.put("name", "Admin");
    addRoleData.put("administration_type", "1");
    addRoleData.put("category_ID", "1");

    JSONArray permissionsArray = new JSONArray();
    JSONObject permisssionobject1 = new JSONObject();
    permisssionobject1.put("category_ID", 1);
    JSONArray operationArray1 = new JSONArray();
    operationArray1.add(1);
    operationArray1.add(2);
    operationArray1.add(3);
    operationArray1.add(4);
    operationArray1.add(5);
    permisssionobject1.put("operation", operationArray1);
    permissionsArray.add(permisssionobject1);

    JSONObject permisssionobject2 = new JSONObject();
    permisssionobject2.put("category_ID", 2);
    JSONArray operationArray2 = new JSONArray();
    operationArray2.add(1);
    operationArray2.add(2);
    operationArray2.add(3);
    operationArray2.add(4);
    operationArray2.add(5);
    permisssionobject2.put("operation", operationArray2);
    permissionsArray.add(permisssionobject2);
    addRoleData.put("permissions", permissionsArray);

    role = new Role();
    role.setName("Admin");
    category = new Category();
    category.setName("Laptops");
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    when(roleServiceImpl.addRole(addRoleData.toJSONString()))
        .thenReturn(roleToReturnFromRepository.get());
  }

  /**
   * Adds the role failure test by invalid data.
   *
   * @throws JSONException the JSON exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addRoleFailureTestByInvalidData() throws JSONException {

    JSONObject addRoleData = new JSONObject();

    addRoleData.put("name", "Admin");
    addRoleData.put("administration_type", "Abc123");
    addRoleData.put("category_ID", "1");

    JSONArray permissionsArray = new JSONArray();
    JSONObject permisssionobject1 = new JSONObject();
    permisssionobject1.put("category_ID", 1);
    JSONArray operationArray1 = new JSONArray();
    operationArray1.add(1);
    operationArray1.add(2);
    operationArray1.add(3);
    operationArray1.add(4);
    operationArray1.add(5);
    permisssionobject1.put("operation", operationArray1);
    permissionsArray.add(permisssionobject1);

    JSONObject permisssionobject2 = new JSONObject();
    permisssionobject2.put("category_ID", 2);
    JSONArray operationArray2 = new JSONArray();
    operationArray2.add(1);
    operationArray2.add(2);
    operationArray2.add(3);
    operationArray2.add(4);
    operationArray2.add(5);
    permisssionobject2.put("operation", operationArray2);
    permissionsArray.add(permisssionobject2);

    addRoleData.put("permissions", permissionsArray);

    role = new Role();
    role.setName("User");
    category = new Category();
    category.setName("Laptops");
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    when(roleServiceImpl.addRole(addRoleData.toJSONString()))
        .thenReturn(roleToReturnFromRepository.get());
  }

  /**
   * Update role test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateRoleTest() throws Exception {

    JSONObject updateRoleData = new JSONObject();

    updateRoleData.put("role_ID", "1");
    updateRoleData.put("name", "Admin");
    updateRoleData.put("administration_type", "1");
    updateRoleData.put("category_ID", "1");

    JSONArray permisssionarray = new JSONArray();

    JSONObject permissisonObject = new JSONObject();
    permissisonObject.put("category_ID", "1");

    JSONObject deleteOperationObject = new JSONObject();

    JSONArray permissionId = new JSONArray();
    permissionId.add(6);
    deleteOperationObject.put("permission_ID", permissionId);

    permissisonObject.put("delete_operation", deleteOperationObject);

    JSONObject insertOperationObject = new JSONObject();

    JSONArray operationId = new JSONArray();

    insertOperationObject.put("operation_ID", operationId);

    permissisonObject.put("insert_operation", insertOperationObject);

    permisssionarray.add(permissisonObject);

    updateRoleData.put("permissions", permisssionarray);

    role = new Role();
    role.setName("Admin");
    role.setRoleID(1);
    category = new Category();
    category.setName("Laptops");
    List<Permission> permissionlist = new ArrayList<>();
    permissionlist.add(permission);
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(permissionRepo.findPermissionByRole(role)).thenReturn(permissionlist);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    Role roleResponse =
        roleServiceImpl.updateRole(updateRoleData.toJSONString());
    assertThat(roleResponse.getName(), is(updateRoleData.get("name")));
  }

  /**
   * Update role failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateRoleFailureTest() throws Exception {

    JSONObject updateRoleData = new JSONObject();

    updateRoleData.put("role_ID", "1");
    updateRoleData.put("name", "Admin");
    updateRoleData.put("administration_type", "1");
    updateRoleData.put("category_ID", "1");

    JSONArray permisssionarray = new JSONArray();

    JSONObject permissisonObject = new JSONObject();
    permissisonObject.put("category_ID", "1");

    JSONObject deleteOperationObject = new JSONObject();

    JSONArray permissionId = new JSONArray();
    permissionId.add(6);
    deleteOperationObject.put("permission_ID", permissionId);

    permissisonObject.put("delete_operation", deleteOperationObject);

    JSONObject insertOperationObject = new JSONObject();

    JSONArray operationId = new JSONArray();

    insertOperationObject.put("operation_ID", operationId);

    permissisonObject.put("insert_operation", insertOperationObject);

    permisssionarray.add(permissisonObject);

    updateRoleData.put("permissions", permisssionarray);

    role = new Role();
    role.setName("Admin");
    role.setRoleID(1);
    category = new Category();
    category.setName("Laptops");
    List<Permission> permissionlist = new ArrayList<>();
    permissionlist.add(permission);
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByroleID(2)).thenReturn(roleToReturnFromRepository);
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(permissionRepo.findPermissionByRole(role)).thenReturn(permissionlist);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    roleServiceImpl.updateRole(updateRoleData.toJSONString());
  }

  /**
   * Update role failure test by invalid data.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateRoleFailureTestByInvalidData() throws Exception {

    JSONObject updateRoleData = new JSONObject();

    updateRoleData.put("role_ID", "1");
    updateRoleData.put("name", "Admin");
    updateRoleData.put("administration_type", "Abc123");
    updateRoleData.put("category_ID", "1");

    JSONArray permisssionarray = new JSONArray();

    JSONObject permissisonObject = new JSONObject();
    permissisonObject.put("category_ID", "1");

    JSONObject deleteOperationObject = new JSONObject();

    JSONArray permissionId = new JSONArray();
    permissionId.add(6);
    deleteOperationObject.put("permission_ID", permissionId);

    permissisonObject.put("delete_operation", deleteOperationObject);

    JSONObject insertOperationObject = new JSONObject();

    JSONArray operationId = new JSONArray();

    insertOperationObject.put("operation_ID", operationId);

    permissisonObject.put("insert_operation", insertOperationObject);

    permisssionarray.add(permissisonObject);

    updateRoleData.put("permissions", permisssionarray);

    role = new Role();
    role.setName("Admin");
    role.setRoleID(1);
    category = new Category();
    category.setName("Laptops");
    List<Permission> permissionlist = new ArrayList<>();
    permissionlist.add(permission);
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByroleID(2)).thenReturn(roleToReturnFromRepository);
    when(roleRepo.findByName(role.getName()))
        .thenReturn(roleToReturnFromRepository);
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(permissionRepo.findPermissionByRole(role)).thenReturn(permissionlist);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    roleServiceImpl.updateRole(updateRoleData.toJSONString());
  }

  /**
   * Update role failure test by existing name.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateRoleFailureTestByExistingName() throws Exception {
    JSONObject updateRoleData = new JSONObject();

    updateRoleData.put("role_ID", "1");
    updateRoleData.put("name", "user");
    updateRoleData.put("administration_type", "1");
    updateRoleData.put("category_ID", "1");

    JSONArray permisssionarray = new JSONArray();

    JSONObject permissisonObject = new JSONObject();
    permissisonObject.put("category_ID", "1");

    JSONObject deleteOperationObject = new JSONObject();

    JSONArray permissionId = new JSONArray();
    permissionId.add(6);

    deleteOperationObject.put("permission_ID", permissionId);

    permissisonObject.put("delete_operation", deleteOperationObject);

    JSONObject insertOperationObject = new JSONObject();

    JSONArray operationId = new JSONArray();

    insertOperationObject.put("operation_ID", operationId);

    permissisonObject.put("insert_operation", insertOperationObject);

    permisssionarray.add(permissisonObject);

    updateRoleData.put("permissions", permisssionarray);

    role = new Role();
    role.setName("user");
    role.setRoleID(2);
    category = new Category();
    category.setName("Laptops");
    List<Permission> permissionlist = new ArrayList<>();
    permissionlist.add(permission);
    when(categoryRepo.findByCategoryID(1)).thenReturn(Optional.of(category));
    when(roleRepo.findByroleID(1)).thenReturn(Optional.of(role));
    when(roleRepo.findByName(role.getName())).thenReturn(Optional.of(role));
    when(roleRepo.save(any(Role.class))).thenReturn(role);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(permissionRepo.findPermissionByRole(role)).thenReturn(permissionlist);
    when(operationRepo.findByOperationID(1)).thenReturn(Optional.of(operation));
    roleServiceImpl.updateRole(updateRoleData.toJSONString());
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
    Optional<Role> roleToReturnFromRepository = Optional.of(role);
    when(roleRepo.findByroleID(1)).thenReturn(roleToReturnFromRepository);
    roleServiceImpl.deleteRole(1);
    verify(roleRepo, times(1)).deleteById(1);
  }

  /**
   * Delete role failure test.
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteRoleFailureTest() {
    roleServiceImpl.deleteRole(0);
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
    when(roleRepo.findAll()).thenReturn(roleList);
    roleServiceImpl.getAllRoles();
    assertThat(roleList.get(0).getName(), is(role.getName()));
    assertThat(1, is(roleList.size()));
  }

  /**
   * Gets the all permissions by role test.
   *
   * @return the all permissions by role test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleTest() throws Exception {
    int id = 1;
    List<Permission> permissionList = new ArrayList<Permission>();
    role = new Role();
    role.setName("Developer");
    role.setRoleID(id);
    category = new Category();
    category.setName("Laptops");
    category.setCategoryID(1);
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);
    role.setPermissions(permissionList);
    when(roleRepo.findByroleID(1)).thenReturn(Optional.of(role));
    Role roleResponse = roleServiceImpl.getAllPermissionsByRole(1);
    assertThat(permissionList.get(0).getPermission_ID(),
        is(roleResponse.getPermissions().get(0).getPermission_ID()));
    assertThat(1, is(roleResponse.getPermissions().size()));
  }

  /**
   * Gets the all permissions by role failure test.
   *
   * @return the all permissions by role failure test
   * @throws Exception the exception
   */
  @Test(expected = NotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleFailureTest() throws Exception {
    int id = 0000;
    role = new Role();
    role.setName("Developer");
    role.setRoleID(id);
    roleServiceImpl.getAllPermissionsByRole(id);
  }

}
