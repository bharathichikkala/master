package com.zone.zissa.service;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.response.ServiceResponse;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/** The Interface ResourceService. */
public interface ResourceService {

  /**
   * Adds the Resource.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  List<Resource> addResource(@Valid @RequestBody String resourceData)
      throws JSONException;

  /**
   * Gets all resources by category id.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<Resource> getResourcesByCategoryId(
      @RequestParam("category_ID") Integer categoryId);

  /**
   * Delete Resource.
   *
   * @param resourceId the resource id
   */
  void deleteResource(@NotNull @PathVariable Integer resourceId);

  /**
   * Update Resource.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  List<Resource> updateResource(@Valid @RequestBody String resourceData)
      throws JSONException;

  /**
   * Dispose resources.
   *
   * @param disposeData the dispose data
   * @return the list
   * @throws JSONException the JSON exception
   */
  List<String> disposeResources(String disposeData) throws JSONException;

  /**
   * Restore resources.
   *
   * @param resourceId the resource id
   */
  void restoreResources(List<Resourcebin> resourceId);

  /**
   * Gets the resource.
   *
   * @param resourceId the resource id
   * @return Resource
   */
  Resource getResource(@PathVariable Integer resourceId);

  /**
   * Gets the disposed resources by category.
   *
   * @param categoryId the category id
   * @return the list
   */
  List<Resourcebin> getDisposedResourcesByCategory(Integer categoryId);

  /**
   * Gets the resources by resource id list.
   *
   * @param resourceIdList the resource id list
   * @return the list
   */
  List<Resource> getResourcesbyResourceIdList(List<Integer> resourceIdList);

  /**
   * Gets the dipsosed resource.
   *
   * @param resourceId the resource id
   * @return Resourcebin
   */
  Resourcebin getDisposedResource(Integer resourceId);

  /**
   * Gets the last resource by category.
   *
   * @param categoryId the category id
   * @return Resource
   */
  Resource getLastResourceByCategory(Integer categoryId);

  /**
   * Gets the all resources by search term.
   *
   * @param categoryId the category id
   * @param page the page
   * @param size the size
   * @param serachText the serach text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  List<Resource> getAllResourcesBySearchTerm(List<Integer> categoryId, int page,
      int size, String serachText, String direction, short attributeId);

  /**
   * Gets the all disposed resources by search term.
   *
   * @param categoryId the category id
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  List<Resourcebin> getAllDisposedResourcesBySearchTerm(
      List<Integer> categoryId, int page, int size, String searchText,
      String direction, short attributeId);

  /**
   * Allocate or Deallocate the Resource.
   *
   * @param allocationData the allocation data
   * @return ServiceResponseBean
   * @throws JSONException the JSON exception
   * @throws ParseException the parse exception
   */
  ServiceResponse resourceAllocation(String allocationData)
      throws JSONException, ParseException;


  /**
   * Gets the all categories with view permissions.
   *
   * @return the set
   */
  Set<Category> getAllCategoriesWithViewPermissions();

  /**
   * Gets the all categories with add permissions.
   *
   * @return the set
   */
  Set<Category> getAllCategoriesWithAddPermissions();

  /**
   * Gets the all categories with dispose permissions.
   *
   * @return the set
   */
  Set<Category> getAllCategoriesWithDisposePermissions();

  /**
   * Gets the all categories with allocate permissions.
   *
   * @return the set
   */
  Set<Category> getAllCategoriesWithAllocatePermissions();

  /**
   * Gets the all permissions by role and category.
   *
   * @param roleId the role id
   * @param categoryId the category id
   * @return the list
   */
  List<Permission> getAllPermissionsByRoleAndCategory(Integer roleId,
      Integer categoryId);

}
