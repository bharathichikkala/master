package com.zone.zissa.service;

import com.zone.zissa.model.Allocation;
import java.util.List;

/** The Interface AllocationService. */
public interface AllocationService {

  /**
   * Gets the all allocation details by resource.
   *
   * @param resourceId the resource id
   * @return the list
   */
  List<Allocation> getAllAllocationDetailsByResource(Integer resourceId);

  /**
   * get All Resources By SearchTerm.
   *
   * @param categoryId the category id
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attrId the attr id
   * @return the object
   */
  Object getAllResourcesBySearchTerm(List<Integer> categoryId, int page,
      int size, String searchText, String direction, short attrId);
}
