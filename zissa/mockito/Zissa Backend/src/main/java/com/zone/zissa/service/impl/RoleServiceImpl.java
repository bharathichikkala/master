package com.zone.zissa.service.impl;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.service.RoleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The RoleServiceImpl class. */
@Service
public class RoleServiceImpl implements RoleService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(RoleServiceImpl.class);

  /** The role repo. */
  @Autowired
  private RoleRepository roleRepo;

  /** The category repo. */
  @Autowired
  private CategoryRepository categoryRepo;

  /** The permission repo. */
  @Autowired
  private PermissionRepository permissionRepo;

  /** The operation repo. */
  @Autowired
  private OperationRepository operationRepo;

  /** The categoryid. */
  private String categoryid = "category_ID";

  /**
   * Add role service implementation.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  @Override
  public Role addRole(final String roleData) throws JSONException {

    LOGGER.info("Add new Role Service implementation");
    Role role = new Role();
    List<Permission> permissionList = new ArrayList<>();
    Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
    Role roleObject = null;
    JSONObject jsonObject = new JSONObject(roleData);
    String name = jsonObject.getString("name");
    int adminType = jsonObject.getInt("administration_type");
    int defaultCategoryId = jsonObject.getInt(categoryid);
    role.setName(name);
    role.setAdministration(adminType);
    Optional<Category> category =
        categoryRepo.findByCategoryID(defaultCategoryId);
    if (category.isPresent()) {
      role.setDefaultCategory(category.get());
    }
    Optional<Role> roleExists = roleRepo.findByName(name);
    if (!roleExists.isPresent()) {
      roleObject = roleRepo.save(role);
      JSONArray jsonChildObject = jsonObject.getJSONArray("permissions");
      for (int permCount = 0; permCount < jsonChildObject
          .length(); permCount++) {
        JSONObject json = jsonChildObject.getJSONObject(permCount);
        Integer categoryId = json.getInt(categoryid);
        Optional<Category> categoryObject =
            categoryRepo.findByCategoryID(categoryId);
        Category categoryObjById = null;
        if (categoryObject.isPresent()) {
          categoryObjById = categoryObject.get();
          categoryObjById.setCategoryAttributes(categoryAttributeObject);
        }
        JSONArray list = json.getJSONArray("operation");
        this.setPermissionsList(list, roleObject, categoryObject,
            permissionList);
        roleObject.setPermissions(permissionList);
      }
    } else {
      throw new ConflictException(RestApiMessageConstants.ROLE_EXISTS);
    }
    return roleObject;
  }

  /**
   * setPermissionsList method.
   *
   * @param list the list
   * @param roleObject the role object
   * @param categoryObject the category object
   * @param permissionList the permission list
   * @throws JSONException the JSON exception
   */
  public void setPermissionsList(final JSONArray list, final Role roleObject,
      final Optional<Category> categoryObject,
      final List<Permission> permissionList) throws JSONException {
    LOGGER.info("Set Permission List Service implementation");
    for (int permCount = 0; permCount < list.length(); permCount++) {
      Permission permissionObject = new Permission();
      permissionObject.setRole(roleObject);
      Category category = null;
      if (categoryObject.isPresent()) {
        category = categoryObject.get();
      }
      permissionObject.setCategory(category);
      Integer operationId = list.getInt(permCount);
      Optional<Operation> operationObject =
          operationRepo.findByOperationID(operationId);
      if (operationObject.isPresent()) {
        permissionObject.setOperation(operationObject.get());
      }
      Permission addPermissions = permissionRepo.save(permissionObject);
      permissionList.add(addPermissions);
    }
  }

  /**
   * Get all roles service implementation.
   *
   * @return the list
   */
  @Override
  public List<Role> getAllRoles() {

    LOGGER.info("Get all Roles Service implementation");
    List<Role> rolesList = roleRepo.findAll();
    List<Role> templateSet = new ArrayList<>();
    for (Role reportTemplate : rolesList) {
      Role newReportTemplate = new Role();
      newReportTemplate.setRoleID(reportTemplate.getRole_ID());
      newReportTemplate.setName(reportTemplate.getName());
      newReportTemplate.setAdministration(reportTemplate.getAdministration());
      templateSet.add(newReportTemplate);
    }
    return templateSet;
  }

  /**
   * Get all permissions by role service implementation.
   *
   * @param roleId the role id
   * @return role
   */
  @Override
  public Role getAllPermissionsByRole(final Integer roleId) {
    LOGGER
        .info("Get Role Permissions Details by role_ID Service implementation");
    Optional<Role> roleObject = roleRepo.findByroleID(roleId);
    if (roleObject.isPresent()) {
      List<Permission> permissionObject = roleObject.get().getPermissions();
      permissionObject.stream().map(Permission::getCategory)
          .forEachOrdered(categoryObject -> {
            categoryObject.setUser(null);
            categoryObject.setCategoryAttributes(null);
          });
    } else {
      throw new NotFoundException(RestApiMessageConstants.ROLE_NOT_EXISTS);
    }
    return roleObject.get();
  }

  /**
   * Update role service implementation.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  @Override
  public Role updateRole(final String roleData) throws JSONException {

    LOGGER.info("Update Role Service implementation");
    Role role = new Role();
    Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
    List<Permission> permissionList = new ArrayList<>();
    JSONObject jsonObject = new JSONObject(roleData);
    String name = jsonObject.getString("name");
    int roleId = jsonObject.getInt("role_ID");
    int adminType = jsonObject.getInt("administration_type");
    int defaultCategoryId = jsonObject.getInt(categoryid);
    role.setAdministration(adminType);
    role.setName(name);
    role.setRoleID(roleId);
    Optional<Category> category =
        categoryRepo.findByCategoryID(defaultCategoryId);
    Category categoryObj = null;
    if (category.isPresent()) {
      categoryObj = category.get();
    }
    role.setDefaultCategory(categoryObj);
    Optional<Role> roleExists = roleRepo.findByroleID(roleId);
    Role roleObject = null;
    if (!roleExists.isPresent()) {
      throw new DataNotFoundException(RestApiMessageConstants.UPDATE_ROLE);

    } else {
      role.setCreatedBy(roleExists.get().getCreatedBy());
      role.setCreatedDate(roleExists.get().getCreatedDate());
      roleObject = updateRoleMethod(role, name, roleId);
    }
    JSONArray jsonPermissionObject = (JSONArray) jsonObject.get("permissions");
    for (int updPermCount = 0; updPermCount < jsonPermissionObject
        .length(); updPermCount++) {

      JSONObject jsonObj = jsonPermissionObject.getJSONObject(updPermCount);
      int categoryId = jsonObj.getInt(categoryid);
      Optional<Category> categoryObject =
          categoryRepo.findByCategoryID(categoryId);
      Category categoryObjById = null;
      if (categoryObject.isPresent()) {
        categoryObjById = categoryObject.get();
        categoryObjById.setCategoryAttributes(categoryAttributeObject);
      }
      JSONObject deleteObject = jsonObj.getJSONObject("delete_operation");
      JSONObject insertObject = jsonObj.getJSONObject("insert_operation");

      if (deleteObject.length() != 0) {

        this.deletePermissions(deleteObject);
      }
      if (insertObject.length() != 0) {

        JSONArray list = insertObject.getJSONArray("operation_ID");
        this.setPermissionsList(list, roleObject, categoryObject,
            permissionList);
      }
      List<Permission> permissionObject =
          permissionRepo.findPermissionByRole(roleObject);
      List<Permission> newPermissionObject = new ArrayList<>();
      if (!permissionObject.isEmpty()) {
        for (Permission permission : permissionObject) {
          newPermissionObject.add(permission);
        }
      }
      roleObject.setPermissions(newPermissionObject);
    }
    return roleObject;
  }

  /**
   * updateRoleMethod.
   *
   * @param role the role
   * @param name the name
   * @param roleId the role id
   * @return Role
   */
  private Role updateRoleMethod(final Role role, final String name,
      final int roleId) {
    LOGGER.info("Update Role Method Service implementation");
    Role roleObject = null;
    Optional<Role> roleObjectByName = roleRepo.findByName(name);
    int roleObjById = 0;
    if (roleObjectByName.isPresent()) {
      roleObjById = roleObjectByName.get().getRole_ID();
    }
    Optional<Role> roleByName = roleRepo.findByName(name);
    if (roleByName.isPresent() && roleObjById != roleId) {
      throw new ConflictException(RestApiMessageConstants.ROLE_NAME_EXISTS);
    } else {
      roleObject = roleRepo.save(role);
    }
    return roleObject;
  }

  /**
   * deletePermissions method.
   *
   * @param deleteObject the delete object
   * @throws JSONException the JSON exception
   */
  public void deletePermissions(final JSONObject deleteObject)
      throws JSONException {
    LOGGER.info("Delete Permissions Service implementation");
    JSONArray list = deleteObject.getJSONArray("permission_ID");
    for (int delPermCount = 0; delPermCount < list.length(); delPermCount++) {
      Integer permissionId = (Integer) list.get(delPermCount);
      permissionRepo.deletePermission(permissionId);
    }
  }

  /**
   * Delete role service implementation.
   *
   * @param roleId the role id
   */
  @Override
  public void deleteRole(final Integer roleId) {

    LOGGER.info("Delete Role Service implementation");
    Optional<Role> roleExists = roleRepo.findByroleID(roleId);
    if (roleExists.isPresent()) {
      roleRepo.deleteById(roleId);

    } else {
      throw new DataNotFoundException(RestApiMessageConstants.DELETE_ROLE);
    }
  }

}
