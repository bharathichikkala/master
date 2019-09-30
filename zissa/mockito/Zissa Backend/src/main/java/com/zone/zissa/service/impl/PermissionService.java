package com.zone.zissa.service.impl;

import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.UserRepository;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** The PermissionService class. */
@Component
public class PermissionService {
  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(PermissionService.class);

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /** The permission repo. */
  @Autowired
  private PermissionRepository permissionRepo;

  /**
   * Permission exists Method.
   *
   * @param categoryId the category id
   * @param operationId the operation id
   * @return true, if successfull
   */
  public boolean permissionExists(final Integer categoryId,
      final Integer operationId) {
    LOGGER.info("Permission Exists Service Implementation");
    boolean operationExists = false;
    org.springframework.security.core.userdetails.User user =
        (org.springframework.security.core.userdetails.User) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
    Optional<User> userObject = userRepo.findByUserName(user.getUsername());
    Set<Permission> permissionObject = null;
    if (userObject.isPresent()) {
      permissionObject = permissionRepo.findByRole(userObject.get().getRole());
      if (!permissionObject.isEmpty()) {
        for (Permission permission : permissionObject) {
          if (permission.getCategory().getCategory_ID() == categoryId
              && permission.getOperation().getOperation_ID() == operationId) {
            operationExists = true;
          }
        }
      }
    }
    return operationExists;
  }

  /**
   * Gets all permissions.
   *
   * @return all permissions
   */
  public Set<Permission> getAllRolePermissions() {
    LOGGER.info("Get All Role Permissions Service Implementation");
    org.springframework.security.core.userdetails.User user =
        (org.springframework.security.core.userdetails.User) SecurityContextHolder
            .getContext().getAuthentication().getPrincipal();
    Optional<User> userObject = userRepo.findByUserName(user.getUsername());
    Role role = null;
    if (userObject.isPresent()) {
      role = userObject.get().getRole();
    }
    Set<Permission> permission = null;
    Set<Permission> permissionObject = permissionRepo.findByRole(role);
    if (!permissionObject.isEmpty()) {
      permission = permissionObject;
    }
    return permission;
  }
}
