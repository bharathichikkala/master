package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Role;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** The PermissionRepository Interface for the Permission database table. */
@Transactional
public interface PermissionRepository
    extends JpaRepository<Permission, Integer> {

  /**
   * The findByRole method.
   *
   * @param role the role
   * @return the set
   */
  Set<Permission> findByRole(Role role);

  /**
   * Delete by category.
   *
   * @param categoryId the category Id
   */
  @Modifying
  @Query(value = "delete from permission where fk_category_id =:category_ID",
      nativeQuery = true)
  void deleteByCategory(@Param("category_ID") Integer categoryId);

  /**
   * The deletePermission method.
   *
   * @param permissionId the permission Id
   */
  @Modifying
  @Query(value = "delete from permission where Permission_ID =:permission_ID",
      nativeQuery = true)
  void deletePermission(@Param("permission_ID") Integer permissionId);

  /**
   * The findPermissionByRole method.
   *
   * @param role the role
   * @return the list
   */
  List<Permission> findPermissionByRole(Role role);

  /**
   * The findPermissionByRoleAndCategory method.
   *
   * @param role the role
   * @param category the category
   * @return the list
   */
  List<Permission> findPermissionByRoleAndCategory(Role role,
      Category category);
}
