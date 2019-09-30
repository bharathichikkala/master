package com.zone.zissa.service.impl;

import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.DataToLongException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.AllocationType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Employee;
import com.zone.zissa.model.EmployeeAllocation;
import com.zone.zissa.model.OtherAllocation;
import com.zone.zissa.model.Project;
import com.zone.zissa.model.ProjectAllocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Status;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AllocationTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.EmployeeAllocationRepository;
import com.zone.zissa.repos.EmployeeRepository;
import com.zone.zissa.repos.OtherAllocationRepository;
import com.zone.zissa.repos.ProjectAllocationRepository;
import com.zone.zissa.repos.ProjectRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.StatusRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.ResourceService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/** The ResourceServiceImpl class. */
@Service
public class ResourceServiceImpl implements ResourceService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ResourceServiceImpl.class);

  /** The resource repo. */
  @Autowired
  private ResourceRepository resourceRepo;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /** The category repo. */
  @Autowired
  private CategoryRepository categoryRepo;

  /** The attribute repo. */
  @Autowired
  private AttributeRepository attributeRepo;

  /** The resource attribute repo. */
  @Autowired
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The status repo. */
  @Autowired
  private StatusRepository statusRepo;

  /** The resource bin repo. */
  @Autowired
  private ResourcebinRepository resourceBinRepo;

  /** The resource bin attribute repo. */
  @Autowired
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  /** The permission service. */
  @Autowired
  private PermissionService permissionService;

  /** The allocation repo. */
  @Autowired
  private AllocationRepository allocationRepo;

  /** The employee allocation repo. */
  @Autowired
  private EmployeeAllocationRepository employeeAllocationRepo;

  /** The project allocation repo. */
  @Autowired
  private ProjectAllocationRepository projectAllocationRepo;

  /** The other allocation repo. */
  @Autowired
  private OtherAllocationRepository otherAllocationRepo;

  /** The allocation type repo. */
  @Autowired
  private AllocationTypeRepository allocationTypeRepo;

  /** The employee repo. */
  @Autowired
  private EmployeeRepository employeeRepo;

  /** The project repo. */
  @Autowired
  private ProjectRepository projectRepo;

  /** The resource attrs. */
  private String resourceAttrs = "resourceAttributes";

  /** The user ID. */
  private String userID = "user_ID";

  /** The status ID. */
  private String statusID = "status_ID";

  /** The resource ID. */
  private String resourceID = "resource_ID";

  /** The date format. */
  private String dateFormat = "dd/MM/yyyy";

  /** The allocation date format. */
  private String allocationDateFormat = "yyyy-MM-dd";

  /** The count. */
  private double count = 0;

  /**
   * Gets the count.
   *
   * @return the count
   */
  public double getCount() {
    return count;
  }

  /**
   * Sets the count.
   *
   * @param counts the new count
   */
  public void setCount(final double counts) {
    this.count = counts;
  }

  /** The found. */
  private boolean found = false;

  /** The flag. */
  private boolean flag = true;

  /** The operation id. */
  private int operationId;

  /** The category id. */
  private int categoryId = 0;

  /**
   * Add resources service implementation.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  @Override
  public List<Resource> addResource(@RequestBody final String resourceData)
      throws JSONException {

    LOGGER.info("Add Resources Service implementation");

    List<Resource> addResourceList = new ArrayList<>();
    operationId = 2;
    JSONObject obj = new JSONObject(resourceData);
    categoryId = obj.getInt("category_ID");
    boolean permissionExists =
        permissionService.permissionExists(categoryId, operationId);
    if (permissionExists) {
      JSONArray jsonChildObject = obj.getJSONArray("resource");
      Optional<Category> categoryObj =
          categoryRepo.findByCategoryID(categoryId);
      for (int resCount = 0; resCount < jsonChildObject.length(); resCount++) {
        JSONObject jo = jsonChildObject.getJSONObject(resCount);
        Resource resource = new Resource();
        if (categoryObj.isPresent()) {
          resource.setCategory(categoryObj.get());
        }
        int userId = obj.getInt(userID);
        Optional<User> userObj = userRepo.findByUserID(userId);
        if (userObj.isPresent()) {
          resource.setUser(userObj.get());
        }
        String code = jo.getString("code");
        resource.setCode(code);
        Byte statusId = (byte) jo.getInt(statusID);
        Optional<Status> statusObj = statusRepo.findBystatusID(statusId);
        if (statusObj.isPresent()) {
          resource.setStatus(statusObj.get());
        }
        Resource resourceobj = resourceRepo.save(resource);
        addResourceList.add(resourceobj);
        List<ResourceAttribute> resourceattrlist = new ArrayList<>();
        JSONArray jsonSubChildObject = jo.getJSONArray(resourceAttrs);
        this.setResourceAttributesMethod(jsonSubChildObject, resourceobj,
            resourceattrlist);
        Resource resourceobj2 = addResourceList.get(addResourceList.size() - 1);
        resourceobj2.setResourceAttributes(resourceattrlist);
      }
    } else {

      throw new AccessDeniedException(
          RestApiMessageConstants.RESOURCE_ADD_PERMISSION);
    }
    return addResourceList;
  }

  /**
   * Update resource service implementation.
   *
   * @param resourceData the resource data
   * @return the list
   * @throws JSONException the JSON exception
   */
  @Override
  public List<Resource> updateResource(
      @Valid @RequestBody final String resourceData) throws JSONException {

    LOGGER.info("Update Resources Service implementation");
    List<Resource> updateResourceList = new ArrayList<>();
    operationId = RestApiMessageConstants.OPERATION_EDIT;
    JSONArray jsonArrayObject = new JSONArray(resourceData);
    for (int updResCount = 0; updResCount < jsonArrayObject
        .length(); updResCount++) {
      JSONObject jo = jsonArrayObject.getJSONObject(updResCount);
      Resource resource = new Resource();
      int resourceId = jo.getInt(resourceID);
      Optional<Resource> resRepObj = resourceRepo.findByResourceID(resourceId);
      if (resRepObj.isPresent()) {
        categoryId = resRepObj.get().getCategory().getCategory_ID();
      }
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (permissionExists) {
        int userId = jo.getInt(userID);
        String code = null;
        Category categoryObj = null;
        if (resRepObj.isPresent()) {
          code = resRepObj.get().getCode();
          categoryObj = resRepObj.get().getCategory();
          resource.setCreatedBy(resRepObj.get().getCreatedBy());
          resource.setCreatedDate(resRepObj.get().getCreatedDate());
        }
        Byte statusId = (byte) jo.getInt(statusID);
        Optional<User> userObj = userRepo.findByUserID(userId);
        Optional<Status> statusObj = statusRepo.findBystatusID(statusId);

        resource.setResourceID(resourceId);
        if (userObj.isPresent()) {
          resource.setUser(userObj.get());
        }

        if (statusObj.isPresent()) {
          resource.setStatus(statusObj.get());
        }
        resource.setCategory(categoryObj);
        resource.setCode(code);

        Resource resourceObj = resourceRepo.save(resource);
        updateResourceList.add(resourceObj);
        JSONArray jsonChildObject = jo.getJSONArray(resourceAttrs);
        List<ResourceAttribute> resourceAttrList = new ArrayList<>();
        this.updateResourceAttributesMethod(jsonChildObject, resourceObj,
            resourceAttrList);
        Resource resourceObject =
            updateResourceList.get(updateResourceList.size() - 1);
        resourceObject.setResourceAttributes(resourceAttrList);
      } else {
        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_UPDATE_PERMISSION);
      }
    }
    return updateResourceList;
  }

  /**
   * Restore resource attributes method.
   *
   * @param resourceBinAttributeObj the resource bin attribute obj
   * @param resourceObject the resource object
   */
  public void restoreResourceAttributesMethod(
      final List<ResourcebinAttribute> resourceBinAttributeObj,
      final Resource resourceObject) {

    for (int restoreResCount = 0; restoreResCount < resourceBinAttributeObj
        .size(); restoreResCount++) {
      ResourcebinAttribute resourceBinAttrObject =
          resourceBinAttributeObj.get(restoreResCount);
      ResourceAttribute resourceAttributeObject = new ResourceAttribute();
      resourceAttributeObject.setValue(resourceBinAttrObject.getValue());
      resourceAttributeObject
          .setAttribute(resourceBinAttrObject.getAttribute());
      resourceAttributeObject.setResource(resourceObject);
      resourceAttributeRepo.save(resourceAttributeObject);
    }
  }

  /**
   * Search allocations.
   *
   * @param allocations the allocations
   * @param searchText the search text
   * @return true, if successful
   */
  private boolean searchAllocations(final Set<Allocation> allocations,
      final String searchText) {

    for (Allocation allocation : allocations) {
      byte allocationTypeId =
          allocation.getAllocationType().getAllocation_Type_ID();
      if (allocationTypeId == RestApiMessageConstants.VALUE_ONE && allocation
          .getStatus().getStatus_ID() == RestApiMessageConstants.VALUE_ONE) {
        return allocation.getEmployeeAllocations().getEmployee().getUserName()
            .toLowerCase().contains(searchText.toLowerCase());

      } else if (allocationTypeId == RestApiMessageConstants.VALUE_TWO
          && allocation.getStatus()
              .getStatus_ID() == RestApiMessageConstants.VALUE_ONE) {
        return allocation.getProjectAllocations().getProject().getProjectName()
            .toLowerCase().contains(searchText.toLowerCase());

      } else if (allocationTypeId == RestApiMessageConstants.VALUE_THREE
          && allocation.getStatus()
              .getStatus_ID() == RestApiMessageConstants.VALUE_ONE) {
        return allocation.getOtherAllocations().getAssignee_Name().toLowerCase()
            .contains(searchText.toLowerCase());
      }
    }
    return false;
  }

  /**
   * Search resource attribute.
   *
   * @param resourceAttributeList the resource attribute list
   * @param searchText the search text
   * @return boolean
   */
  private boolean searchResourceAttribute(
      final List<ResourceAttribute> resourceAttributeList,
      final String searchText) {

    for (ResourceAttribute resourceAttribute : resourceAttributeList) {

      found = resourceAttribute.getValue().toLowerCase()
          .contains(searchText.toLowerCase());
      if (found) {
        return true;
      }
    }
    return found;
  }

  /**
   * Search resource bin attribute.
   *
   * @param resourceAttributeList the resource attribute list
   * @param searchText the search text
   * @return boolean
   */
  private boolean searchResourceBinAttribute(
      final List<ResourcebinAttribute> resourceAttributeList,
      final String searchText) {

    for (ResourcebinAttribute resourceAttribute : resourceAttributeList) {

      found = resourceAttribute.getValue().toLowerCase()
          .contains(searchText.toLowerCase());
      if (found) {
        return true;
      }
    }
    return found;
  }

  /**
   * Search resources.
   *
   * @param resourceCd the resource cd
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @return the list
   */
  public List<Resource> searchResources(final List<Resource> resourceCd,
      final int page, final int size, final String searchText) {

    List<Resource> finalResources = null;
    for (Resource resource : resourceCd) {
      Set<CategoryAttribute> categoryAttributes =
          resource.getCategory().getCategoryAttributes();
      List<CategoryAttribute> sortedCategoryAttributeList = categoryAttributes
          .stream()
          .sorted((o1, o2) -> Integer
              .valueOf(o1.getAttribute().getAttribute_ID())
              .compareTo(Integer.valueOf(o2.getAttribute().getAttribute_ID())))
          .collect(Collectors.toList());
      Set<CategoryAttribute> finalSortedCategoryAttributes =
          new LinkedHashSet<>(sortedCategoryAttributeList);
      resource.getCategory()
          .setCategoryAttributes(finalSortedCategoryAttributes);
    }
    Stream<Resource> filteredResources =
        this.filteringResources(resourceCd, searchText);
    finalResources = filteredResources.collect(Collectors.toList());
    this.count = finalResources.size();
    int noOfPages = (int) Math.ceil(this.count / size);
    return this.paginationforResources(page, noOfPages, size, finalResources);
  }

  /**
   * filtering resources method.
   *
   * @param resourceCd the resource cd
   * @param searchText the search text
   * @return the stream
   */
  public Stream<Resource> filteringResources(final List<Resource> resourceCd,
      final String searchText) {
    return resourceCd.stream().filter(resource -> {
      if (resource.getCode().toLowerCase().contains(searchText.toLowerCase())) {
        return flag;
      } else if (resource.getStatus().getStatus_Name().toLowerCase()
          .contains(searchText.toLowerCase())) {
        return flag;
      } else if (searchAllocations(resource.getAllocations(), searchText)) {
        return flag;
      } else if (searchResourceAttribute(resource.getResourceAttributes(),
          searchText)) {
        return flag;
      } else {
        return false;
      }
    });
  }

  /**
   * pagination for Resources method.
   *
   * @param page the page
   * @param noOfPages the no of pages
   * @param size the size
   * @param finalResources the final resources
   * @return the list
   */
  public List<Resource> paginationforResources(final int page,
      final int noOfPages, final int size,
      final List<Resource> finalResources) {

    List<Resource> finalList = null;
    if (page <= noOfPages) {

      int startIndex = page * size;
      int endIndex = startIndex + (size - 1);

      if (startIndex <= finalResources.size() - 1
          && endIndex < finalResources.size()) {
        finalList = finalResources.subList(startIndex, endIndex + 1);
      } else if (startIndex <= finalResources.size() - 1
          && endIndex == finalResources.size()) {
        finalList = finalResources.subList(startIndex, endIndex);
      } else {
        finalList = finalResources.subList(startIndex, finalResources.size());
      }
    }
    return finalList;
  }

  /**
   * Search disposed resources.
   *
   * @param resourceCd the resource cd
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @return the list
   */
  public List<Resourcebin> searchDisposedResources(
      final List<Resourcebin> resourceCd, final int page, final int size,
      final String searchText) {
    List<Resourcebin> finalResources = null;
    for (Resourcebin resourcebin : resourceCd) {
      Set<CategoryAttribute> categoryAttributes =
          resourcebin.getCategory().getCategoryAttributes();
      List<CategoryAttribute> sortedCategoryAttributeList = categoryAttributes
          .stream()
          .sorted((o1, o2) -> Integer
              .valueOf(o1.getAttribute().getAttribute_ID())
              .compareTo(Integer.valueOf(o2.getAttribute().getAttribute_ID())))
          .collect(Collectors.toList());
      Set<CategoryAttribute> finalSortedCategoryAttributes =
          new LinkedHashSet<>(sortedCategoryAttributeList);
      resourcebin.getCategory()
          .setCategoryAttributes(finalSortedCategoryAttributes);
    }
    Stream<Resourcebin> filteredResources =
        resourceCd.stream().filter(resource -> {
          if (resource.getCode().toLowerCase()
              .contains(searchText.toLowerCase())) {
            return flag;
          } else if (resource.getDisposeReason().toLowerCase()
              .contains(searchText.toLowerCase())) {
            return flag;
          } else if (searchResourceBinAttribute(
              resource.getResourcebinAttributes(), searchText)) {
            return flag;
          } else {
            return false;
          }
        });
    finalResources = filteredResources.collect(Collectors.toList());
    this.count = finalResources.size();
    int noOfPages = (int) Math.ceil(this.count / size);
    return this.paginationforDisposedResources(page, noOfPages, size,
        finalResources);
  }

  /**
   * pagination for Disposed Resources method.
   *
   * @param page the page
   * @param noOfPages the no of pages
   * @param size the size
   * @param finalResources the final resources
   * @return the list
   */
  public List<Resourcebin> paginationforDisposedResources(final int page,
      final int noOfPages, final int size,
      final List<Resourcebin> finalResources) {
    List<Resourcebin> finalList = null;

    if (page <= noOfPages) {

      int startIndex = page * size;
      int endIndex = startIndex + (size - 1);

      if (startIndex <= finalResources.size() - 1
          && endIndex < finalResources.size()) {
        finalList = finalResources.subList(startIndex, endIndex + 1);

      } else if (startIndex <= finalResources.size() - 1
          && endIndex == finalResources.size()) {
        finalList = finalResources.subList(startIndex, endIndex);

      } else if (startIndex <= finalResources.size() - 1) {
        finalList = finalResources.subList(startIndex, finalResources.size());
      }
    }
    return finalList;
  }

  /**
   * get All Resources By SearchTerm service implementation.
   *
   * @param categoryID the category ids
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  public List<Resource> getAllResourcesBySearchTerm(
      final List<Integer> categoryID, final int page, final int size,
      final String searchText, final String direction,
      final short attributeId) {
    LOGGER.info("Get all Resources Service implementation");
    List<Resource> resourceCd = null;
    List<Resource> resourceRecords = null;
    List<Resource> resourceSearchList = null;
    int pageSize = size;
    if (size == 0) {
      pageSize = Integer.MAX_VALUE;
    }
    Optional<Attribute> attribute =
        attributeRepo.findByAttributeID(attributeId);
    if (attribute.isPresent()) {
      resourceCd = resourceRepo.findResourcesByCategory(categoryID);
      if (!resourceCd.isEmpty()) {
        resourceRecords =
            sortAscSearchResources(resourceCd, direction, attributeId);
      }
      resourceSearchList =
          searchResources(resourceRecords, page, pageSize, searchText);
    } else {
      if ("desc".equalsIgnoreCase(direction)) {
        resourceCd = resourceRepo.findResourcesByCategoryDesc(categoryID);
      } else {
        resourceCd = resourceRepo.findResourcesByCategoryAsc(categoryID);
      }
      if (!resourceCd.isEmpty()) {
        resourceSearchList =
            searchResources(resourceCd, page, pageSize, searchText);
      } else {
        count = 0;
      }
    }
    return resourceSearchList;
  }

  /**
   * get All Disposed Resources By SearchTerm service implementation.
   *
   * @param categoryID the category ID
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  public List<Resourcebin> getAllDisposedResourcesBySearchTerm(
      final List<Integer> categoryID, final int page, final int size,
      final String searchText, final String direction,
      final short attributeId) {
    LOGGER.info("Get all Disposed Resources Service implementation");
    List<Resourcebin> resourceCd = null;
    List<Resourcebin> resourceRecords = null;
    List<Resourcebin> resourceSearchList = null;
    int pageSize = size;
    if (size == 0) {
      pageSize = Integer.MAX_VALUE;
    }
    Optional<Attribute> attribute =
        attributeRepo.findByAttributeID(attributeId);
    if (attribute.isPresent()) {

      resourceCd = resourceBinRepo.findResourcesByCategory(categoryID);
      if (!resourceCd.isEmpty()) {
        resourceRecords =
            sortAscSearchResourceBin(resourceCd, direction, attributeId);
      }
      resourceSearchList =
          searchDisposedResources(resourceRecords, page, pageSize, searchText);
    } else {
      if ("desc".equalsIgnoreCase(direction)) {
        resourceCd =
            this.getDisposedResourcesByCategoryDesc(attributeId, categoryID);

      } else {

        resourceCd =
            this.getDisposedResourcesByCategoryAsc(attributeId, categoryID);
      }
      if (!resourceCd.isEmpty()) {
        resourceSearchList =
            searchDisposedResources(resourceCd, page, pageSize, searchText);
      } else {
        count = 0;
      }
    }
    return resourceSearchList;
  }

  /**
   * get Disposed Resources By Category Desc method.
   *
   * @param attributeId the attribute id
   * @param categoryID the category ID
   * @return the list
   */
  private List<Resourcebin> getDisposedResourcesByCategoryDesc(
      final short attributeId, final List<Integer> categoryID) {
    List<Resourcebin> resources;

    if (attributeId == -1) {
      resources =
          resourceBinRepo.findResourcesByCategoryOrderByReasonDesc(categoryID);
    } else {
      resources = resourceBinRepo.findResourcesByCategoryDesc(categoryID);
    }
    return resources;
  }

  /**
   * get Disposed Resources By Category Asc method.
   *
   * @param attributeId the attribute id
   * @param categoryID the category ids
   * @return the list
   */
  private List<Resourcebin> getDisposedResourcesByCategoryAsc(
      final short attributeId, final List<Integer> categoryID) {
    List<Resourcebin> resources;

    if (attributeId == -1) {
      resources =
          resourceBinRepo.findResourcesByCategoryOrderByReasonAsc(categoryID);
    } else {
      resources = resourceBinRepo.findResourcesByCategoryAsc(categoryID);
    }
    return resources;
  }

  /**
   * Gets the resources by category id service implementation.
   *
   * @param categoryID the category ids
   * @return the list
   */
  @Override
  public List<Resource> getResourcesByCategoryId(final Integer categoryID) {

    LOGGER.info("Get all Resources by categoryId Service implementation");
    List<Resource> resourceRecords = null;
    List<Resource> resourceList = null;
    operationId = 1;

    Optional<Category> categoryExists =
        categoryRepo.findByCategoryID(categoryID);

    if (!categoryExists.isPresent()) {

      throw new NotFoundException(RestApiMessageConstants.CATEGORY_NOT_EXISTS);

    } else {
      boolean permissionExists =
          permissionService.permissionExists(categoryID, operationId);
      resourceRecords =
          resourceRepo.findResourceByCategory(categoryExists.get());
      if (!resourceRecords.isEmpty()) {
        resourceList = resourceRecords;
        if (permissionExists) {
          this.isResourceExistsMethod(resourceRecords);
        } else {

          throw new AccessDeniedException(
              RestApiMessageConstants.RESOURCE_VIEW_PERMISSION);
        }
      }
    }
    return resourceList;
  }

  /**
   * Checks if is resource exists method.
   *
   * @param resourceRecords the resource records
   */
  public void isResourceExistsMethod(final List<Resource> resourceRecords) {
    if (resourceRecords.isEmpty()) {
      throw new NoContentException(RestApiMessageConstants.RESOURCE_NOT_EXISTS);
    }
  }

  /**
   * Delete resource service implementation.
   *
   * @param resourceId the resource id
   */
  @Override
  public void deleteResource(final Integer resourceId) {

    LOGGER.info("Delete Resource Service implementation");
    operationId = RestApiMessageConstants.OPERATION_DELETE;
    Optional<Resource> resourceExists =
        resourceRepo.findByResourceID(resourceId);
    if (resourceExists.isPresent()) {
      categoryId = resourceExists.get().getCategory().getCategory_ID();
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (permissionExists) {
        this.checkResourceAllocationStatus(resourceExists.get(), resourceId);
      } else {

        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_DELETE_PERMISSION);
      }
    } else {
      throw new DataNotFoundException(RestApiMessageConstants.DELETE_RESOURCE);
    }
  }

  /**
   * Check resource allocation status.
   *
   * @param resourceExists the resource exists
   * @param resourceId the resource id
   */
  public void checkResourceAllocationStatus(final Resource resourceExists,
      final Integer resourceId) {

    byte statusId = resourceExists.getStatus().getStatus_ID();
    if (statusId == 0) {
      List<Allocation> allocationList =
          allocationRepo.findAllocationListByResource(resourceId);
      if (allocationList.isEmpty()) {
        resourceRepo.deleteById(resourceId);
      } else {
        employeeAllocationRepo
            .deleteEmployeeAllocationsByAllocationIdList(allocationList);
        projectAllocationRepo
            .deleteProjectAllocationsByAllocationIdList(allocationList);
        otherAllocationRepo
            .deleteOtherAllocationsByAllocationIdList(allocationList);
        allocationRepo.deleteByAllocationIdList(allocationList);
        resourceRepo.deleteById(resourceId);
      }
    } else {
      throw new ConflictException(
          RestApiMessageConstants.DELETE_ALLOCATED_RESOURCE);
    }
  }

  /**
   * Dispose resources service implementation.
   *
   * @param disposeData the dispose data
   * @return the list
   * @throws JSONException the JSON exception
   */
  @Override
  public List<String> disposeResources(final String disposeData)
      throws JSONException {

    LOGGER.info("Dispose Resources Service implementation");
    List<String> resourceList = null;
    List<String> resourceFailureList = new ArrayList<>();
    operationId = RestApiMessageConstants.OPERATION_DISPOSE;
    JSONArray object = new JSONArray(disposeData);
    JSONArray jsonChildObject = object;
    for (int disposeResCount = 0; disposeResCount < jsonChildObject
        .length(); disposeResCount++) {
      Resourcebin resourceBinObject = new Resourcebin();
      JSONObject jsonObject = jsonChildObject.getJSONObject(disposeResCount);
      Optional<Resource> resourceObject =
          resourceRepo.findByResourceID(jsonObject.getInt(resourceID));
      String disposeReason = jsonObject.getString("reason");
      if (resourceObject.isPresent()) {
        categoryId = resourceObject.get().getCategory().getCategory_ID();
      }
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (disposeReason
          .length() > RestApiMessageConstants.DISPOSE_REASON_LENGTH) {
        throw new DataToLongException(
            RestApiMessageConstants.DISPOSE_RESOURCE_REASON);
      } else {

        if (permissionExists) {
          if (resourceObject.isPresent()) {
            resourceList = this.checkDisposeResourceAllocationStatus(
                resourceObject.get(), resourceBinObject, disposeReason);
          }
          resourceFailureList.addAll(resourceList);

        } else {
          throw new AccessDeniedException(
              RestApiMessageConstants.RESOURCE_DISPOSE_PERMISSION);
        }
      }
    }
    return resourceFailureList;
  }

  /**
   * Check dispose resource allocation status.
   *
   * @param resourceObject the resource object
   * @param resourceBinobject the resource binobject
   * @param disposeReason the dispose reason
   * @return the list
   */
  public List<String> checkDisposeResourceAllocationStatus(
      final Resource resourceObject, final Resourcebin resourceBinobject,
      final String disposeReason) {
    List<String> resourceDisposeFailureList = new ArrayList<>();
    byte statusId = resourceObject.getStatus().getStatus_ID();
    if (statusId == 0) {
      List<Allocation> allocationList = allocationRepo
          .findAllocationListByResource(resourceObject.getResource_ID());

      if (allocationList.isEmpty()) {
        resourceRepo.deleteById(resourceObject.getResource_ID());
      } else {
        employeeAllocationRepo
            .deleteEmployeeAllocationsByAllocationIdList(allocationList);
        projectAllocationRepo
            .deleteProjectAllocationsByAllocationIdList(allocationList);
        otherAllocationRepo
            .deleteOtherAllocationsByAllocationIdList(allocationList);
        allocationRepo.deleteByAllocationIdList(allocationList);
        resourceRepo.deleteById(resourceObject.getResource_ID());
      }
      resourceBinobject.setCode(resourceObject.getCode());
      resourceBinobject.setCreatedDate(resourceObject.getCreatedDate());
      resourceBinobject.setCategory(resourceObject.getCategory());
      resourceBinobject
          .setFKCreateUserID(resourceObject.getUser().getUser_ID());
      resourceBinobject
          .setFKStatusID(resourceObject.getStatus().getStatus_ID());
      resourceBinobject.setDisposeReason(disposeReason);
      Resourcebin resourceBinObj = resourceBinRepo.save(resourceBinobject);
      List<ResourceAttribute> resourceAttributeObject =
          resourceObject.getResourceAttributes();
      for (int resAttrCount = 0; resAttrCount < resourceAttributeObject
          .size(); resAttrCount++) {
        ResourceAttribute resourceObj =
            resourceAttributeObject.get(resAttrCount);
        ResourcebinAttribute resourceBinAttributeObj =
            new ResourcebinAttribute();
        resourceBinAttributeObj.setValue(resourceObj.getValue());
        resourceBinAttributeObj.setAttribute(resourceObj.getAttribute());
        resourceBinAttributeObj.setResourcebin(resourceBinObj);
        resourceBinAttributeRepo.save(resourceBinAttributeObj);
      }

    } else {
      resourceDisposeFailureList.add(resourceObject.getCode());
    }
    return resourceDisposeFailureList;
  }

  /**
   * Restore resources service implementation.
   *
   * @param resourceId the resource id
   */
  @Override
  public void restoreResources(final List<Resourcebin> resourceId) {

    LOGGER.info("Restore Resources Service implementation");
    Date date = new Date();
    long time = date.getTime();
    Timestamp timeStamp = new Timestamp(time);
    operationId = RestApiMessageConstants.OPERATION_DISPOSE;

    for (int resourceBinCount = 0; resourceBinCount < resourceId
        .size(); resourceBinCount++) {
      Resourcebin resourceBin = resourceId.get(resourceBinCount);
      Optional<Resourcebin> resourceExists =
          resourceBinRepo.findByResourceID(resourceBin.getResource_ID());
      if (resourceExists.isPresent()) {
        categoryId = resourceExists.get().getCategory().getCategory_ID();
      }
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (permissionExists) {
        resourceBinRepo.deleteById(resourceBin.getResource_ID());
        Optional<Category> categoryObject = categoryRepo
            .findByCategoryID(resourceBin.getCategory().getCategory_ID());
        Resource resourceObject = new Resource();
        if (categoryObject.isPresent()) {
          resourceObject.setCategory(categoryObject.get());
        }
        resourceObject.setCode(resourceBin.getCode());
        resourceObject.setCreatedDate(timeStamp);
        Optional<Status> statusObject =
            statusRepo.findBystatusID(resourceBin.getFK_Status_ID());
        if (statusObject.isPresent()) {
          resourceObject.setStatus(statusObject.get());
        }
        Optional<User> userObject =
            userRepo.findByUserID(resourceBin.getFK_Create_User_ID());
        if (userObject.isPresent()) {
          resourceObject.setUser(userObject.get());
        }
        Resource saveResource = resourceRepo.save(resourceObject);
        List<ResourcebinAttribute> resourceBinAttributeObject =
            resourceBin.getResourcebinAttributes();
        this.restoreResourceAttributesMethod(resourceBinAttributeObject,
            saveResource);
      } else {
        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_RESTORE_PERMISSION);
      }
    }
  }

  /**
   * Gets the resource service implementation.
   *
   * @param resourceId the resource id
   * @return Resource
   */
  @Override
  public Resource getResource(final Integer resourceId) {

    LOGGER.info("Get Resource Service implementation");
    Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
    operationId = 1;
    Optional<Resource> resourceObject =
        resourceRepo.findByResourceID(resourceId);
    if (!resourceObject.isPresent()) {
      throw new NotFoundException(
          RestApiMessageConstants.RESOURCE_EXISTS_FAILURE);
    } else {
      categoryId = resourceObject.get().getCategory().getCategory_ID();
      Category categoryObject = resourceObject.get().getCategory();
      categoryObject.setCategoryAttributes(categoryAttributeObject);
      resourceObject.get().setCategory(categoryObject);
      List<ResourceAttribute> resourceAttributeList =
          resourceObject.get().getResourceAttributes();
      for (ResourceAttribute resourceAttribute : resourceAttributeList) {
        List<AttributeValue> list = new ArrayList<>(
            resourceAttribute.getAttribute().getAttributeValues());

        List<AttributeValue> result = list.stream()
            .sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
                .compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
            .collect(Collectors.toList());

        Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
        resourceAttribute.getAttribute().setAttributeValues(resultSet);
      }
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (!permissionExists) {
        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_VIEW_PERMISSION);
      }
    }
    return resourceObject.get();
  }

  /**
   * Gets the disposed resources by category service implementation.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @Override
  public List<Resourcebin> getDisposedResourcesByCategory(
      final Integer categoryID) {

    LOGGER.info("Get Disposed Resources by Category Service implementation");
    List<Resourcebin> resourceList = null;
    operationId = RestApiMessageConstants.OPERATION_DISPOSE;
    Optional<Category> categoryExists =
        categoryRepo.findByCategoryID(categoryID);
    if (!categoryExists.isPresent()) {
      throw new NotFoundException(RestApiMessageConstants.CATEGORY_NOT_EXISTS);

    } else {
      boolean permissionExists =
          permissionService.permissionExists(categoryID, operationId);
      List<Resourcebin> resourceRecords = null;
      resourceRecords =
          resourceBinRepo.findResourceByCategory(categoryExists.get());
      if (permissionExists) {
        if (!resourceRecords.isEmpty()) {
          resourceList = resourceRecords;
          this.isResourceBinExistsMethod(resourceList);
        }
      } else {

        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_DISPOSE_PERMISSION);
      }
    }

    return resourceList;
  }

  /**
   * Checks if is resource bin exists method.
   *
   * @param resourceRecords the resource records
   */
  public void isResourceBinExistsMethod(
      final List<Resourcebin> resourceRecords) {

    if (resourceRecords.isEmpty()) {
      throw new NoContentException(
          RestApiMessageConstants.DISPOSE_RESOURCE_FAILURE);
    }
  }

  /**
   * Gets the resources by resource id list service implementation.
   *
   * @param resourceList the resource list
   * @return the list
   */
  @Override
  public List<Resource> getResourcesbyResourceIdList(
      final List<Integer> resourceList) {

    LOGGER.info("Get Resources by ResourceIdList Service implementation");

    Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
    List<Integer> resourceIdList = new ArrayList<>(resourceList);
    List<Resource> resourceObjectList = new ArrayList<>();
    operationId = 1;
    for (Integer value : resourceList) {
      Optional<Resource> resourceObject = resourceRepo.findByResourceID(value);
      if (resourceObject.isPresent()) {
        categoryId = resourceObject.get().getCategory().getCategory_ID();
      }
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (!permissionExists) {
        resourceIdList.remove(value);
      }
    }
    List<Resource> resourceListObject =
        resourceRepo.findResourcesByResourceIdList(resourceIdList);
    if (!resourceListObject.isEmpty()) {
      for (Resource resource : resourceListObject) {
        Category categoryObject = resource.getCategory();
        categoryObject.setCategoryAttributes(categoryAttributeObject);
        resource.setCategory(resource.getCategory());
        List<ResourceAttribute> resourceAttributeList =
            resource.getResourceAttributes();

        List<ResourceAttribute> sortedResourceAttributeList =
            resourceAttributeList.stream()
                .sorted((o1, o2) -> Integer
                    .valueOf(o1.getAttribute().getAttribute_ID()).compareTo(
                        Integer.valueOf(o2.getAttribute().getAttribute_ID())))
                .collect(Collectors.toList());

        resource.setResourceAttributes(sortedResourceAttributeList);
        resourceObjectList.add(resource);
        for (ResourceAttribute resourceAttribute : sortedResourceAttributeList) {
          List<AttributeValue> list = new ArrayList<>(
              resourceAttribute.getAttribute().getAttributeValues());

          List<AttributeValue> sortedAttributeValueList = list.stream()
              .sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
                  .compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
              .collect(Collectors.toList());

          Set<AttributeValue> resultSet =
              new LinkedHashSet<>(sortedAttributeValueList);
          resourceAttribute.getAttribute().setAttributeValues(resultSet);
        }
      }
    } else {
      throw new NotFoundException(
          RestApiMessageConstants.RESOURCE_EXISTS_FAILURE);
    }

    return resourceObjectList;
  }

  /**
   * Gets the disposed resource service implementation.
   *
   * @param resourceId the resource id
   * @return Resourcebin
   */
  @Override
  public Resourcebin getDisposedResource(final Integer resourceId) {
    LOGGER.info("Get Disposed Resource Service implementation");
    Set<CategoryAttribute> categoryAttributeObject = new HashSet<>();
    operationId = RestApiMessageConstants.OPERATION_DELETE;
    Optional<Resourcebin> resourceBinObject =
        resourceBinRepo.findByResourceID(resourceId);
    if (!resourceBinObject.isPresent()) {
      throw new NotFoundException(
          RestApiMessageConstants.RESOURCE_EXISTS_FAILURE);
    } else {
      categoryId = resourceBinObject.get().getCategory().getCategory_ID();
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      Category categoryObject = resourceBinObject.get().getCategory();
      categoryObject.setCategoryAttributes(categoryAttributeObject);
      resourceBinObject.get().setCategory(categoryObject);
      if (!permissionExists) {
        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_DISPOSE_PERMISSION);
      }
    }

    return resourceBinObject.get();
  }

  /**
   * Gets the last resource by category service implementation.
   *
   * @param categoryID the category ID
   * @return the last resource
   */
  @Override
  public Resource getLastResourceByCategory(final Integer categoryID) {

    LOGGER.info("Get Last Resource by categoryId Service implementation");
    List<ResourceAttribute> resourceAttributeObject = new ArrayList<>();
    Optional<Resource> resourceObject =
        resourceRepo.findLastResourceByCategoryId(categoryID);
    if (resourceObject.isPresent()) {
      resourceObject.get().setResourceAttributes(resourceAttributeObject);
      resourceObject.get().setCategory(null);
      return resourceObject.get();
    } else {
      throw new NotFoundException(RestApiMessageConstants.CATEGORY_NOT_EXISTS);
    }
  }

  /**
   * sort Asc Search Resources method.
   *
   * @param resources the resources
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  public List<Resource> sortAscSearchResources(final List<Resource> resources,
      final String direction, final Short attributeId) {
    List<Resource> resourceList = new ArrayList<>();
    List<Resource> otherResourceList = new ArrayList<>();
    ResourceComparator.setAttrId(attributeId);
    ResourceComparator.setDirection(direction);
    for (Resource resourceAttribute : resources) {
      boolean isFound = false;

      for (ResourceAttribute resourceAttribute1 : resourceAttribute
          .getResourceAttributes()) {
        short attributeObjId =
            resourceAttribute1.getAttribute().getAttribute_ID();
        if (attributeObjId == attributeId) {
          resourceList.add(resourceAttribute);
          isFound = true;
          break;
        }
      }
      if (!isFound) {
        otherResourceList.add(resourceAttribute);
      }
    }
    Collections.sort(resourceList, new ResourceComparator());

    resourceList.addAll(otherResourceList);
    return resourceList;
  }

  /**
   * sort Asc Search Resourcebin method.
   *
   * @param resources the resources
   * @param direction the direction
   * @param attributeId the attribute id
   * @return the list
   */
  public List<Resourcebin> sortAscSearchResourceBin(
      final List<Resourcebin> resources, final String direction,
      final Short attributeId) {
    List<Resourcebin> resourceList = new ArrayList<>();
    List<Resourcebin> otherResourceList = new ArrayList<>();
    DisposedResourceComparator.setAttrId(attributeId);
    DisposedResourceComparator.setDirection(direction);
    for (Resourcebin resourceAttribute : resources) {
      boolean isFound = false;

      for (ResourcebinAttribute resourceAttribute1 : resourceAttribute
          .getResourcebinAttributes()) {
        short attributeObjId =
            resourceAttribute1.getAttribute().getAttribute_ID();
        if (attributeObjId == attributeId) {
          resourceList.add(resourceAttribute);
          isFound = true;
          break;
        }
      }
      if (!isFound) {
        otherResourceList.add(resourceAttribute);
      }
    }

    Collections.sort(resourceList, new DisposedResourceComparator());
    resourceList.addAll(otherResourceList);
    return resourceList;
  }

  /**
   * sort Asc Search datecase method.
   *
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @return int
   * @throws ParseException the parse exception
   */
  public int sortAscResourceDateCase(final ResourceAttribute resourceAttribute1,
      final ResourceAttribute resourceAttribute2) throws ParseException {

    int resourceflag;
    String resourceAttributes1 = resourceAttribute1.getValue().trim();
    String resourceAttributes2 = resourceAttribute2.getValue().trim();
    if (resourceAttributes1.isEmpty() && resourceAttributes2.isEmpty()) {
      resourceflag = 0;
    } else if (resourceAttributes1.isEmpty()
        && !resourceAttributes2.isEmpty()) {
      resourceflag = -1;
    } else if (!resourceAttributes1.isEmpty()
        && resourceAttributes2.isEmpty()) {
      resourceflag = 1;
    } else {
      resourceflag =
          new SimpleDateFormat(dateFormat).parse(resourceAttributes1).compareTo(
              new SimpleDateFormat(dateFormat).parse(resourceAttributes2));
    }
    return resourceflag;
  }

  /**
   * sort desc Search datecase method.
   *
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @return int
   * @throws ParseException the parse exception
   */
  public int sortDescResourceDateCase(
      final ResourceAttribute resourceAttribute1,
      final ResourceAttribute resourceAttribute2) throws ParseException {

    int resourceValue;
    String resourceAttributes1 = resourceAttribute1.getValue().trim();
    String resourceAttributes2 = resourceAttribute2.getValue().trim();
    if (resourceAttributes1.isEmpty() && resourceAttributes2.isEmpty()) {
      resourceValue = 0;
    } else if (resourceAttribute1.getValue().trim().isEmpty()
        && !resourceAttribute2.getValue().trim().isEmpty()) {
      resourceValue = 1;
    } else if (!resourceAttribute1.getValue().trim().isEmpty()
        && resourceAttribute2.getValue().trim().isEmpty()) {
      resourceValue = -1;
    } else {

      resourceValue =
          new SimpleDateFormat(dateFormat).parse(resourceAttributes1).compareTo(
              new SimpleDateFormat(dateFormat).parse(resourceAttributes2));
      resourceValue = -resourceValue;
    }
    return resourceValue;
  }

  /**
   * sort Asc Disposed Resource datecase method.
   *
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @return int
   * @throws ParseException the parse exception
   */
  public int sortAscDisposedResourceDateCase(
      final ResourcebinAttribute resourceAttribute1,
      final ResourcebinAttribute resourceAttribute2) throws ParseException {
    int disposedResourceFlag;
    String resourceAttributes1 = resourceAttribute1.getValue().trim();
    String resourceAttributes2 = resourceAttribute2.getValue().trim();
    if (resourceAttributes1.isEmpty() && resourceAttributes2.isEmpty()) {
      disposedResourceFlag = 0;
    } else if (resourceAttributes1.isEmpty()
        && !resourceAttributes2.isEmpty()) {
      disposedResourceFlag = -1;
    } else if (!resourceAttributes1.isEmpty()
        && resourceAttributes2.isEmpty()) {
      disposedResourceFlag = 1;
    } else {
      disposedResourceFlag =
          new SimpleDateFormat(dateFormat).parse(resourceAttributes1).compareTo(
              new SimpleDateFormat(dateFormat).parse(resourceAttributes2));
    }
    return disposedResourceFlag;
  }

  /**
   * sort desc Disposed Resource datecase method.
   *
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @return int
   * @throws ParseException the parse exception
   */
  public int sortDescDisposedResourceDateCase(
      final ResourcebinAttribute resourceAttribute1,
      final ResourcebinAttribute resourceAttribute2) throws ParseException {
    int disposedResourceValue;
    String resourceAttributes1 = resourceAttribute1.getValue().trim();
    String resourceAttributes2 = resourceAttribute2.getValue().trim();
    if (resourceAttributes1.isEmpty() && resourceAttributes2.isEmpty()) {
      disposedResourceValue = 0;
    } else if (resourceAttributes1.isEmpty()
        && !resourceAttributes2.isEmpty()) {
      disposedResourceValue = 1;
    } else if (!resourceAttributes1.isEmpty()
        && resourceAttributes2.isEmpty()) {
      disposedResourceValue = -1;
    } else {

      disposedResourceValue =
          new SimpleDateFormat(dateFormat).parse(resourceAttributes1).compareTo(
              new SimpleDateFormat(dateFormat).parse(resourceAttributes2));
      disposedResourceValue = -disposedResourceValue;
    }
    return disposedResourceValue;
  }

  /**
   * Allocate or Deallocate the Resource.
   *
   * @param allocationData the allocation data
   * @return ServiceResponseBean
   * @throws JSONException the JSON exception
   * @throws ParseException the parse exception
   */
  public ServiceResponse resourceAllocation(final String allocationData)
      throws JSONException, ParseException {
    LOGGER.info("Resource Allocation or DeAllocation Service implementation");
    ServiceResponse response = new ServiceResponse();
    Date toDate = null;
    Date date = new Date();
    operationId = RestApiMessageConstants.OPERATION_ALLOCATE;
    String modifiedDate =
        new SimpleDateFormat(allocationDateFormat).format(date);
    Date parsedDate =
        new SimpleDateFormat(allocationDateFormat).parse(modifiedDate);
    JSONArray jsonArrayObject = new JSONArray(allocationData);
    for (int resAllocCount = 0; resAllocCount < jsonArrayObject
        .length(); resAllocCount++) {
      JSONObject jsonObject = jsonArrayObject.getJSONObject(resAllocCount);
      int resourceId = jsonObject.getInt(resourceID);
      Optional<Resource> resource = resourceRepo.findByResourceID(resourceId);
      if (resource.isPresent()) {
        categoryId = resource.get().getCategory().getCategory_ID();
      }
      Byte statusId = (byte) jsonObject.getInt(statusID);
      int userId = jsonObject.getInt(userID);
      boolean permissionExists =
          permissionService.permissionExists(categoryId, operationId);
      if (permissionExists) {
        if (statusId == 2) {
          toDate = parsedDate;
          int allocationId = jsonObject.getInt("allocation_ID");
          this.saveAllocation(statusId, allocationId, toDate);
          statusId = 0;
          response.setStatus(HttpServletResponse.SC_OK);
          response.setMessage(RestApiMessageConstants.RESOURCE_ALLOCATION);
        } else {
          this.resourceAllocationFailureMethod(resourceId, jsonObject, statusId,
              userId, parsedDate);
        }
        this.saveResourceAllocation(resourceId, statusId);

      } else {
        throw new AccessDeniedException(
            RestApiMessageConstants.RESOURCE_ALLOCATION_PERMISSION);
      }
    }
    return response;
  }

  /**
   * resourceAllocationFailureMethod.
   *
   * @param resourceId the resource id
   * @param jsonObject the json object
   * @param statusId the status id
   * @param userId the user id
   * @param parsedDate the parsed date
   * @throws JSONException the JSON exception
   */
  public void resourceAllocationFailureMethod(final int resourceId,
      final JSONObject jsonObject, final Byte statusId, final int userId,
      final Date parsedDate) throws JSONException {
    Date fromDate = null;
    List<String> resourceList = new ArrayList<>();
    Optional<Resource> resource = resourceRepo.findByResourceID(resourceId);
    byte allocationTypeId = (byte) jsonObject.getInt("allocationtype_ID");
    if (resource.isPresent()) {
      byte resourceStatusId = resource.get().getStatus().getStatus_ID();
      if (resourceStatusId == 1) {
        resourceList.add(resource.get().getCode());
        throw new ConflictException(
            RestApiMessageConstants.ALLOCATION_RESOURCE_FAILURE);
      } else {
        fromDate = parsedDate;
        Allocation allocationObject = this.resourceAllocateAndDeallocateMethod(
            allocationTypeId, resourceId, statusId, userId, fromDate);
        this.allocationMethod(allocationTypeId, jsonObject, allocationObject);
      }
    }
  }

  /**
   * resourceAllocateAndDeallocateMethod.
   *
   * @param allocationTypeId the allocation type id
   * @param resourceId the resource id
   * @param statusId the status id
   * @param userId the user id
   * @param fromDate the from date
   * @return Allocation
   */
  public Allocation resourceAllocateAndDeallocateMethod(
      final byte allocationTypeId, final int resourceId, final Byte statusId,
      final int userId, final Date fromDate) {
    Optional<AllocationType> allocationTypeObject =
        allocationTypeRepo.findByAllocationTypeID(allocationTypeId);
    Allocation allocation = new Allocation();
    if (allocationTypeObject.isPresent()) {
      allocation.setAllocationType(allocationTypeObject.get());
    }
    Optional<Resource> resource = resourceRepo.findByResourceID(resourceId);
    if (resource.isPresent()) {
      allocation.setResource(resource.get());
    }
    Optional<Status> status = statusRepo.findBystatusID(statusId);
    if (status.isPresent()) {
      allocation.setStatus(status.get());
    }
    Optional<User> user = userRepo.findByUserID(userId);
    if (user.isPresent()) {
      allocation.setUser(user.get());
    }
    allocation.setFromDate(fromDate);
    Date allocatedToDate = null;
    allocation.setToDate(allocatedToDate);

    return allocationRepo.save(allocation);
  }

  /**
   * saveAllocation.
   *
   * @param statusId the status id
   * @param allocationId the allocation id
   * @param toDate the to date
   */
  public void saveAllocation(final Byte statusId, final int allocationId,
      final Date toDate) {

    Optional<Status> status = statusRepo.findBystatusID(statusId);
    Optional<Allocation> allocationObject =
        allocationRepo.findByAllocationID(allocationId);
    if (allocationObject.isPresent()) {
      if (status.isPresent()) {
        allocationObject.get().setStatus(status.get());
      }
      allocationObject.get().setToDate(toDate);

      allocationRepo.save(allocationObject.get());
    }
  }

  /**
   * saveResourceAllocation.
   *
   * @param resourceId the resource id
   * @param statusId the status id
   */
  public void saveResourceAllocation(final int resourceId,
      final Byte statusId) {
    Optional<Resource> resourceObject =
        resourceRepo.findByResourceID(resourceId);
    Optional<Status> statusObject = statusRepo.findBystatusID(statusId);
    if (resourceObject.isPresent()) {
      if (statusObject.isPresent()) {
        resourceObject.get().setStatus(statusObject.get());
      }
      resourceRepo.save(resourceObject.get());
    }
  }

  /**
   * Resource Allocation Method for different allocation types.
   *
   * @param allocationTypeId the allocation type id
   * @param jsonObject the json object
   * @param allocationObject the allocation object
   * @throws JSONException the JSON exception
   */
  private void allocationMethod(final byte allocationTypeId,
      final JSONObject jsonObject, final Allocation allocationObject)
      throws JSONException {
    if (allocationTypeId == 1) {
      JSONObject employeeAllocationObject =
          jsonObject.getJSONObject("employeeAllocations");
      Employee employee;
      JSONObject employeeObject =
          employeeAllocationObject.getJSONObject("employee");
      Optional<Employee> employeeExists = employeeRepo
          .findByUserName(employeeObject.getString("sAMAccountName"));
      if (employeeExists.isPresent()) {
        employee = employeeExists.get();
      } else {
        employee = new Employee();
        employee.setUserName(employeeObject.getString("sAMAccountName"));
        employee.setFirstName(employeeObject.getString("firstName"));
        employee.setLastName(employeeObject.getString("lastName"));
        employee.setActiveStatus((byte) 1);
      }
      EmployeeAllocation employeeAllocation = new EmployeeAllocation();
      employeeAllocation.setAllocation(allocationObject);
      employeeAllocation.setEmployee(employee);
      employeeAllocationRepo.save(employeeAllocation);
    } else if (allocationTypeId == 2) {
      JSONObject projectAllocationObject =
          jsonObject.getJSONObject("projectAllocations");
      Project project;
      JSONObject projectObject =
          projectAllocationObject.getJSONObject("project");
      Optional<Project> projectExists =
          projectRepo.findByProjectName(projectObject.getString("code"));
      if (projectExists.isPresent()) {
        project = projectExists.get();
      } else {
        project = new Project();
        project.setProjectName(projectObject.getString("code"));
        project.setActiveStatus((byte) 1);
      }
      ProjectAllocation projectAllocation = new ProjectAllocation();
      projectAllocation.setAllocation(allocationObject);
      projectAllocation.setProject(project);
      projectAllocationRepo.save(projectAllocation);
    } else {
      OtherAllocation otherAllocation = new OtherAllocation();
      JSONObject otherAllocationObject =
          jsonObject.getJSONObject("otherAllocations");
      String assigneeName = otherAllocationObject.getString("assignee_Name");
      otherAllocation.setAllocation(allocationObject);
      otherAllocation.setAssigneeName(assigneeName);
      otherAllocationRepo.save(otherAllocation);
    }
  }

  /**
   * Sets the resource attributes method.
   *
   * @param jsonSubChildObject the json sub child object
   * @param resourceObj the resource obj
   * @param resourceAttrList the resource attr list
   * @throws JSONException the JSON exception
   */
  public void setResourceAttributesMethod(final JSONArray jsonSubChildObject,
      final Resource resourceObj,
      final List<ResourceAttribute> resourceAttrList) throws JSONException {
    for (int resAttrCount = 0; resAttrCount < jsonSubChildObject
        .length(); resAttrCount++) {
      ResourceAttribute resourceAttrObj = new ResourceAttribute();
      JSONObject jsonObj = jsonSubChildObject.getJSONObject(resAttrCount);
      Short attributeId = (short) jsonObj.getInt("attribute_ID");
      Optional<Attribute> attrObj =
          attributeRepo.findByAttributeID(attributeId);
      String value = jsonObj.getString("value");
      resourceAttrObj.setValue(value);
      if (attrObj.isPresent()) {
        resourceAttrObj.setAttribute(attrObj.get());
      }
      resourceAttrObj.setResource(resourceObj);
      ResourceAttribute resourceAttr =
          resourceAttributeRepo.save(resourceAttrObj);
      resourceAttrList.add(resourceAttr);
    }
  }

  /**
   * Update resource attributes method.
   *
   * @param jsonChildObject the json child object
   * @param resourceObj the resource obj
   * @param resourceAttrList the resource attr list
   * @throws JSONException the JSON exception
   */
  public void updateResourceAttributesMethod(final JSONArray jsonChildObject,
      final Resource resourceObj,
      final List<ResourceAttribute> resourceAttrList) throws JSONException {
    for (int updResAttrCount = 0; updResAttrCount < jsonChildObject
        .length(); updResAttrCount++) {
      ResourceAttribute resourceAttrObj = new ResourceAttribute();
      JSONObject obj = jsonChildObject.getJSONObject(updResAttrCount);
      Optional<ResourceAttribute> resAttrObj = resourceAttributeRepo
          .findByResourceAttributeID(obj.getInt("resource_Attribute_ID"));
      Attribute attrObj = null;
      if (resAttrObj.isPresent()) {
        attrObj = resAttrObj.get().getAttribute();
      }
      resourceAttrObj
          .setResourceAttributeID(obj.getInt("resource_Attribute_ID"));
      resourceAttrObj.setResource(resourceObj);
      resourceAttrObj.setValue(obj.getString("value"));
      resourceAttrObj.setAttribute(attrObj);
      ResourceAttribute resAttribute =
          resourceAttributeRepo.save(resourceAttrObj);
      resourceAttrList.add(resAttribute);
    }
  }
}
