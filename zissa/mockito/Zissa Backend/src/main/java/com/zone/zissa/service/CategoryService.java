package com.zone.zissa.service;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.response.ServiceResponse;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/** The Interface CategoryService. */
public interface CategoryService {

  /**
   * Adds the category.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  Category addCategory(@Valid @RequestBody String categoryData)
      throws JSONException;

  /**
   * Update category.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  Category updateCategory(@Valid @RequestBody String categoryData)
      throws JSONException;

  /**
   * Get all the categories.
   *
   * @return the list
   */
  List<Category> getAllCategories();

  /**
   * Get all the attributes info by categoryid.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<CategoryAttribute> getCategoryAttributesByCategoryId(
      @PathVariable Integer categoryId);

  /**
   * Delete Category.
   *
   * @param categoryId the category id
   * @return ServiceResponse
   */
  ServiceResponse deleteCategory(@NotNull @PathVariable Integer categoryId);

  /**
   * Gets the all operations.
   *
   * @return the list
   */
  List<Operation> getAllOperations();


  /**
   * Gets the attribute details by category id list.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<CategoryAttribute> getCategoryAttributesByCategoryIdList(
      List<Category> categoryId);

}
