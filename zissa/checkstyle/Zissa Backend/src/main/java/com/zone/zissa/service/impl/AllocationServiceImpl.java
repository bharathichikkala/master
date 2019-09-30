package com.zone.zissa.service.impl;

import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Resource;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.service.AllocationService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** The AllocationServiceImpl class. */
@Service
public class AllocationServiceImpl implements AllocationService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllocationServiceImpl.class);

  /** The resource repo. */
  @Autowired
  private ResourceRepository resourceRepo;

  /** The attribute repo. */
  @Autowired
  private AttributeRepository attributeRepo;

  /** The allocation repo. */
  @Autowired
  private AllocationRepository allocationRepo;

  /** The permission service. */
  @Autowired
  private PermissionService permissionService;

  /** The resource service. */
  @Autowired
  private ResourceServiceImpl resourceService;

  /**
   * Gets the all allocation history details by resource service implementation.
   *
   * @param resourceId the resource id
   * @return the list
   */
  @Override
  public List<Allocation> getAllAllocationDetailsByResource(
      final Integer resourceId) {
    LOGGER
        .info("Get all allocation details by Resource Service implementation");
    List<Allocation> allocationList = null;
    int operationId = 1;
    Optional<Resource> resourceExists =
        resourceRepo.findByResourceID(resourceId);

    if (resourceExists.isPresent()) {
      boolean permissionExists = permissionService.permissionExists(
          resourceExists.get().getCategory().getCategory_ID(), operationId);
      if (permissionExists) {
        allocationList =
            allocationRepo.findAllocationHistoryByResource(resourceId);
        if (allocationList.isEmpty()) {

          throw new NoContentException(
              RestApiMessageConstants.NO_ALLOCATION_CONTENT);
        }
      } else {
        throw new AccessDeniedException(
            RestApiMessageConstants.ALLOCATION_ACCESS_DENIED);
      }
    } else {
      throw new NotFoundException(RestApiMessageConstants.RESOURCE_NOT_FOUND);
    }
    return allocationList;
  }

  /**
   * get All Resources By SearchTerm service implementation.
   *
   * @param categoryId the category id
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attrid the attrid
   * @return the object
   */
  @Override
  public Object getAllResourcesBySearchTerm(final List<Integer> categoryId,
      final int page, final int size, final String searchText,
      final String direction, final short attrid) {
    LOGGER.info(
        "Get all Resources with allocation details Service implementation");
    int pageSize = size;
    List<Resource> resource = null;
    List<Resource> resourceList = null;
    List<Resource> resourceSearchList = null;
    if (size == 0) {
      pageSize = Integer.MAX_VALUE;
    }
    Optional<Attribute> attribute = attributeRepo.findByAttributeID(attrid);
    if (attribute.isPresent()) {
      resource = resourceRepo.findResourcesByCategory(categoryId);
      if (!resource.isEmpty()) {
        resourceList =
            resourceService.sortAscSearchResources(resource, direction, attrid);
      }
      resourceSearchList = resourceService.searchResources(resourceList, page,
          pageSize, searchText);
    } else {
      if ("desc".equalsIgnoreCase(direction)) {
        resource = resourceRepo.findResourcesByCategoryDesc(categoryId);
      } else {
        resource = resourceRepo.findResourcesByCategoryAsc(categoryId);
      }
      if (!resource.isEmpty()) {
        resourceSearchList = resourceService.searchResources(resource, page,
            pageSize, searchText);
      }
    }
    return resourceSearchList;
  }
}
