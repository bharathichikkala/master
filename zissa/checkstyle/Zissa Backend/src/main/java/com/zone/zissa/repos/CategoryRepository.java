package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/** The CategoryRepository Interface for the Category database table. */
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {

  /**
   * The findByName method.
   *
   * @param name the name
   * @return Category
   */
  Optional<Category> findByName(String name);

  /**
   * The findByCategoryID method.
   *
   * @param categoryID the category ID
   * @return Category
   */
  Optional<Category> findByCategoryID(Integer categoryID);

  /**
   * The findByCodePattern method.
   *
   * @param codePattern the code pattern
   * @return Category
   */
  Optional<Category> findByCodePattern(String codePattern);
}
