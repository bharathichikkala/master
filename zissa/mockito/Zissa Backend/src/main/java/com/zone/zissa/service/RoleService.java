package com.zone.zissa.service;

import com.zone.zissa.model.Role;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/** The Interface RoleService. */
public interface RoleService {

  /**
   * Adds the Role.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  Role addRole(@Valid @RequestBody String roleData) throws JSONException;

  /**
   * Update Role.
   *
   * @param roleData the role data
   * @return Role
   * @throws JSONException the JSON exception
   */
  Role updateRole(@Valid @RequestBody String roleData) throws JSONException;

  /**
   * Delete Role.
   *
   * @param roleId the role id
   */
  void deleteRole(@NotNull @PathVariable Integer roleId);

  /**
   * Gets all roles.
   *
   * @return the list
   */
  List<Role> getAllRoles();

  /**
   * Gets the all permissions by role.
   *
   * @param roleId the role id
   * @return Role
   */
  Role getAllPermissionsByRole(@RequestParam("role_ID") Integer roleId);

}
