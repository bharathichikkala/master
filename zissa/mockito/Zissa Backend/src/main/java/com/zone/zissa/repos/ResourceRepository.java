package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Resource;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** The ResourceRepository Interface for the Resource database table. */
@Transactional
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

  /**
   * The findResourceByCategory method.
   *
   * @param category the category
   * @return the list
   */
  List<Resource> findResourceByCategory(Category category);

  /**
   * The findResourcesByResourceIdList method.
   *
   * @param resourceIdList the resource id list
   * @return the list
   */
  @Query(value = "SELECT * FROM resource where Resource_ID in ?1",
      nativeQuery = true)
  List<Resource> findResourcesByResourceIdList(
      @Param("resource_ID") List<Integer> resourceIdList);

  /**
   * The findResourcesByCategory method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resource where FK_Category_ID in ?1",
      nativeQuery = true)
  List<Resource> findResourcesByCategory(List<Integer> categoryId);

  /**
   * The findLastResourceByCategoryId method.
   *
   * @param categoryId the category Id
   * @return Resource
   */
  @Query(value = "SELECT Resource_ID,code,FK_Category_ID,Created_Date,"
      + "FK_Create_User_ID,FK_Status_ID,"
      + "created_by,last_modified_by,last_modified_date "
      + "FROM resource where FK_Category_ID =:category_ID UNION ALL "
      + "SELECT Resource_ID,code,FK_Category_ID,"
      + "Created_Date,FK_Create_User_ID,"
      + "FK_Status_ID,created_by,last_modified_by,last_modified_date "
      + "FROM resourcebin where fk_category_id =:category_ID "
      + "ORDER BY Code DESC LIMIT 1", nativeQuery = true)
  Optional<Resource> findLastResourceByCategoryId(
      @Param("category_ID") Integer categoryId);

  /**
   * The findResourcesByCategoryDesc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resource where "
      + "FK_Category_ID in ?1 order by code desc", nativeQuery = true)
  List<Resource> findResourcesByCategoryDesc(List<Integer> categoryId);

  /**
   * The findResourcesByCategoryAsc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resource where "
      + "FK_Category_ID in ?1 order by code asc", nativeQuery = true)
  List<Resource> findResourcesByCategoryAsc(List<Integer> categoryId);

  /**
   * The findByResourceID method.
   *
   * @param resourceId the resource id
   * @return Resource
   */
  Optional<Resource> findByResourceID(int resourceId);

  /**
   * The findResourceByCategoryAndStatus method.
   *
   * @param category the category
   * @return Resource
   */
  @Query(value = "SELECT * FROM resource where "
      + "FK_Category_ID =:category and FK_Status_ID=1", nativeQuery = true)
  List<Resource> findResourceByCategoryAndStatus(Category category);
}
