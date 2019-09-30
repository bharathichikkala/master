package com.zone.zissa.repos;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Resourcebin;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** The ResourcebinRepository Interface for the Resourcebin database table. */
@Transactional
public interface ResourcebinRepository
    extends JpaRepository<Resourcebin, Integer> {

  /**
   * The findResourceByCategory method.
   *
   * @param category the category
   * @return the list
   */
  List<Resourcebin> findResourceByCategory(Category category);

  /**
   * The findResourcesByCategory method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resourcebin where FK_Category_ID in ?1",
      nativeQuery = true)
  List<Resourcebin> findResourcesByCategory(List<Integer> categoryId);

  /**
   * The findResourcesByCategoryDesc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resourcebin where "
      + "FK_Category_ID in ?1 order by code desc", nativeQuery = true)
  List<Resourcebin> findResourcesByCategoryDesc(List<Integer> categoryId);

  /**
   * The findResourcesByCategoryAsc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(value = "SELECT * FROM resourcebin where "
      + "FK_Category_ID in ?1 order by code asc", nativeQuery = true)
  List<Resourcebin> findResourcesByCategoryAsc(List<Integer> categoryId);

  /**
   * The findResourcesByCategoryDesc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(
      value = "SELECT * FROM resourcebin where "
          + "FK_Category_ID in ?1 order by dispose_reason desc",
      nativeQuery = true)
  List<Resourcebin> findResourcesByCategoryOrderByReasonDesc(
      List<Integer> categoryId);

  /**
   * The findResourcesByCategoryAsc method.
   *
   * @param categoryId the category Id
   * @return the list
   */
  @Query(
      value = "SELECT * FROM resourcebin where "
          + "FK_Category_ID in ?1 order by dispose_reason asc",
      nativeQuery = true)
  List<Resourcebin> findResourcesByCategoryOrderByReasonAsc(
      List<Integer> categoryId);

  /**
   * The findByResourceID method.
   *
   * @param resourceID the resource ID
   * @return Resourcebin
   */
  Optional<Resourcebin> findByResourceID(int resourceID);
}
