package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** CategoryAttributeRepository for category_attribute database table. */
@Transactional
public interface CategoryAttributeRepository
    extends JpaRepository<CategoryAttribute, Integer> {

  /**
   * The getAllAttributeValue method.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<CategoryAttribute> findByCategory(Category categoryId);

  /**
   * The getAllAttributeValue by category list method.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<CategoryAttribute> findByCategoryIn(List<Category> categoryId);

  /**
   * The deleteCategoryAttributeId method.
   *
   * @param categoryAttributeId the category attribute Id
   */
  @Modifying
  @Query(value = "delete from category_attribute where "
      + "Category_Attribute_ID=:category_Attribute_ID", nativeQuery = true)
  void deleteCategoryAttributeId(
      @Param("category_Attribute_ID") Integer categoryAttributeId);

  /**
   * The findByCategoryAttributeID method.
   *
   * @param categoryAttributeId the category attribute id
   * @return CategoryAttribute
   */
  Optional<CategoryAttribute> findByCategoryAttributeID(
      int categoryAttributeId);
}
