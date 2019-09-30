package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Role;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The RoleRepository Interface for the Role database table. */
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

  /**
   * The findByName method.
   *
   * @param name the name
   * @return Role
   */
  Optional<Role> findByName(String name);

  /**
   * The findByroleID method.
   *
   * @param roleId the role id
   * @return Role
   */
  Optional<Role> findByroleID(int roleId);

  /**
   * The findByDefaultCategory method.
   *
   * @param category the category
   * @return the list
   */
  List<Role> findByDefaultCategory(Category category);
}
