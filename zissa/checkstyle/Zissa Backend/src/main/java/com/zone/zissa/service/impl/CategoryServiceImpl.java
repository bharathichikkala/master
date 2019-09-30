package com.zone.zissa.service.impl;

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryAttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.CategoryService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** The CategoryServiceImpl Class. */
@Service
public class CategoryServiceImpl implements CategoryService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllocationServiceImpl.class);

  /** The attribute repo. */
  @Autowired
  private AttributeRepository attributeRepo;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /** The role repo. */
  @Autowired
  private RoleRepository roleRepo;

  /** The category repo. */
  @Autowired
  private CategoryRepository categoryRepo;

  /** The category attribute repo. */
  @Autowired
  private CategoryAttributeRepository categoryAttributeRepo;

  /** The operation repo. */
  @Autowired
  private OperationRepository operationRepo;

  /** The permission repo. */
  @Autowired
  private PermissionRepository permissionRepo;

  /** The permission service. */
  @Autowired
  private PermissionService permissionService;

  /** The resource repo. */
  @Autowired
  private ResourceRepository resourceRepo;

  /** The resource attribute repo. */
  @Autowired
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The resourcebin repo. */
  @Autowired
  private ResourcebinRepository resourcebinRepo;

  /** The resourcebin attribute repo. */
  @Autowired
  private ResourcebinAttributeRepository resourcebinAttributeRepo;

  /** The default key. */
  private String defaultKey = "isDefault";

  /** The value. */
  private Boolean value = true;

  /** The operation id. */
  private Integer operationId;

  /**
   * Add category service implementation.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  @Override
  public Category addCategory(final String categoryData) throws JSONException {

    LOGGER.info("Add new Category Service implementation");
    Category category = new Category();
    Category categoryObject = null;
    JSONObject object = new JSONObject(categoryData);
    String name = object.getString("name");
    String codePattern = object.getString("code_Pattern");
    int userId = object.getInt("user_ID");
    Optional<User> userObject = userRepo.findByUserID(userId);
    if (userObject.isPresent()) {
      category.setUser(userObject.get());
    }
    category.setName(name);

    category.setCodePattern(codePattern);

    Optional<Category> categoryExists = categoryRepo.findByName(name);
    Optional<Category> patternExists =
        categoryRepo.findByCodePattern(codePattern);
    if (patternExists.isPresent()) {

      throw new ConflictException(RestApiMessageConstants.CODE_PATTERN_EXISTS);
    }
    if (!categoryExists.isPresent()) {
      categoryObject = categoryRepo.save(category);
      this.addCategoryAttributesMethod(object, categoryObject);

      org.springframework.security.core.userdetails.User user =
          (org.springframework.security.core.userdetails.User) SecurityContextHolder
              .getContext().getAuthentication().getPrincipal();
      Optional<User> userObjectByName =
          userRepo.findByUserName(user.getUsername());
      Integer[] operations = {RestApiMessageConstants.OPERATION_VIEW,
          RestApiMessageConstants.OPERATION_ADD,
          RestApiMessageConstants.OPERATION_EDIT,
          RestApiMessageConstants.OPERATION_ALLOCATE,
          RestApiMessageConstants.OPERATION_DISPOSE,
          RestApiMessageConstants.OPERATION_DELETE};
      for (int operationsCount =
          0; operationsCount < operations.length; operationsCount++) {
        operationId = operations[operationsCount];
        Optional<Operation> operationObject =
            operationRepo.findByOperationID(operationId);
        Permission permissionObject = new Permission();
        if (userObjectByName.isPresent()) {
          permissionObject.setRole(userObjectByName.get().getRole());
        }
        permissionObject.setCategory(categoryObject);
        if (operationObject.isPresent()) {
          permissionObject.setOperation(operationObject.get());
        }
        permissionRepo.save(permissionObject);
      }

    } else {
      throw new ConflictException(RestApiMessageConstants.CATEGORY_EXISTS);
    }
    return categoryObject;
  }

  /**
   * addCategoryAttributesMethod.
   *
   * @param object the object
   * @param categoryObject the category object
   * @throws JSONException the JSON exception
   */
  public void addCategoryAttributesMethod(final JSONObject object,
      final Category categoryObject) throws JSONException {
    JSONArray jsonChildObject = object.getJSONArray("categoryAttributes");
    for (int catAttrCount = 0; catAttrCount < jsonChildObject
        .length(); catAttrCount++) {
      CategoryAttribute categoryAttributeObject = new CategoryAttribute();
      JSONObject jsonObject = jsonChildObject.getJSONObject(catAttrCount);
      categoryAttributeObject.setCategory(categoryObject);
      Short attributeId = (short) jsonObject.getInt("attribute_ID");
      int keyValue = jsonObject.getInt(defaultKey);
      boolean attributeDefault = (keyValue > 0) ? value : !value;
      Attribute attributeObject = attributeRepo.getOne(attributeId);
      categoryAttributeObject.setAttribute(attributeObject);
      categoryAttributeObject.setDefault(attributeDefault);
      categoryAttributeRepo.save(categoryAttributeObject);
    }
  }

  /**
   * Get all categories service implementation.
   *
   * @return the list
   */
  @Override
  public List<Category> getAllCategories() {

    LOGGER.info("Get all Categories Service implementation");
    List<Category> categoryList = categoryRepo.findAll();
    categoryList.stream()
        .forEach(category -> category.setCategoryAttributes(null));
    return categoryList;
  }

  /**
   * Get all attributes by categoryid service implementation.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @Override
  public List<CategoryAttribute> getCategoryAttributesByCategoryId(
      final Integer categoryID) {

    LOGGER.info("Get all Attributes by Categoryid Service implementation");

    Optional<Category> categoryExists =
        categoryRepo.findByCategoryID(categoryID);
    Category categoryObject = null;
    if (categoryExists.isPresent()) {
      categoryObject = categoryExists.get();
    }
    List<CategoryAttribute> categoryAttributeList =
        categoryAttributeRepo.findByCategory(categoryObject);
    if (!categoryAttributeList.isEmpty()) {
      for (CategoryAttribute categoryAttribute : categoryAttributeList) {
        List<AttributeValue> list = new ArrayList<>(
            categoryAttribute.getAttribute().getAttributeValues());

        List<AttributeValue> result = list.stream()
            .sorted((o1, o2) -> Integer.valueOf(o1.getAttribute_Value_ID())
                .compareTo(Integer.valueOf(o2.getAttribute_Value_ID())))
            .collect(Collectors.toList());

        Set<AttributeValue> resultSet = new LinkedHashSet<>(result);
        categoryAttribute.getAttribute().setAttributeValues(resultSet);
      }
    }
    if (!categoryExists.isPresent()) {
      throw new NotFoundException(RestApiMessageConstants.CATEGORY_NOT_EXISTS);
    }
    if (categoryAttributeList.isEmpty()) {
      throw new NoContentException(
          RestApiMessageConstants.NO_ATTRIBUTE_FOR_CATEGORY);
    }
    return categoryAttributeList;
  }

  /**
   * Update category service implementation.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  @Override
  public Category updateCategory(final String categoryData)
      throws JSONException {

    LOGGER.info("Update Category Service implementation");
    Category category = new Category();

    JSONObject object = new JSONObject(categoryData);
    String name = object.getString("name");
    int userId = object.getInt("user_ID");
    int categoryId = object.getInt("category_ID");
    Optional<Category> categoryObject =
        categoryRepo.findByCategoryID(categoryId);
    Category categoryObj = null;
    if (!categoryObject.isPresent()) {
      throw new DataNotFoundException(RestApiMessageConstants.UPDATE_CATEGORY);

    } else {
      Optional<Category> categoryObjByName = categoryRepo.findByName(name);
      int categoryObjId = 0;
      if (categoryObjByName.isPresent()) {
        categoryObjId = categoryObjByName.get().getCategory_ID();
      }
      Optional<Category> categoryByName = categoryRepo.findByName(name);
      if (categoryByName.isPresent() && categoryObjId != categoryId) {
        throw new ConflictException(
            RestApiMessageConstants.CATEGORY_NAME_EXISTS);

      } else {
        String codePattern = categoryObject.get().getCode_Pattern();
        category.setCreatedBy(categoryObject.get().getCreatedBy());
        category.setCreatedDate(categoryObject.get().getCreatedDate());
        categoryObj = this.updateCategoryMethod(userId, categoryId, codePattern,
            name, category);
      }
    }
    JSONArray jsonDeleteCategoryObject =
        object.getJSONArray("delete_categoryAttributes");
    JSONArray jsonInsertCategoryObject =
        object.getJSONArray("insert_categoryAttributes");
    JSONArray jsonUpdateCategoryObject =
        object.getJSONArray("update_defaultAttributes");
    if (jsonDeleteCategoryObject.length() != 0) {
      List<Resource> allocatedResource =
          resourceRepo.findResourceByCategoryAndStatus(categoryObj);
      if (allocatedResource.isEmpty()) {
        this.deleteCategoryObjectMethod(jsonDeleteCategoryObject, categoryId);
      } else {
        throw new ConflictException(
            RestApiMessageConstants.CATEGORY_ATTRIBUTES_UPDATION_FAILURE);
      }
    }
    if (jsonInsertCategoryObject.length() != 0) {
      this.insertCategoryObjectMethod(jsonInsertCategoryObject, categoryObj,
          categoryId);
    }
    if (jsonUpdateCategoryObject.length() != 0) {
      this.updateCategoryObjectMethod(jsonUpdateCategoryObject, categoryObj);
    }

    return categoryObj;
  }

  /**
   * updateCategoryMethod.
   *
   * @param userId the user id
   * @param categoryId the category id
   * @param codePattern the code pattern
   * @param name the name
   * @param category the category
   * @return Category
   */
  public Category updateCategoryMethod(final int userId, final int categoryId,
      final String codePattern, final String name, final Category category) {
    Optional<User> userObject = userRepo.findByUserID(userId);
    category.setName(name);
    if (userObject.isPresent()) {
      category.setUser(userObject.get());
    }
    category.setCategoryID(categoryId);
    category.setCodePattern(codePattern);

    return categoryRepo.save(category);
  }

  /**
   * Insert category object method.
   *
   * @param jsonInsertCategoryObject the json insert category object
   * @param catObj the cat obj
   * @param categoryId the category id
   * @throws JSONException the JSON exception
   */
  public void insertCategoryObjectMethod(
      final JSONArray jsonInsertCategoryObject, final Category catObj,
      final int categoryId) throws JSONException {

    for (int insCatCount = 0; insCatCount < jsonInsertCategoryObject
        .length(); insCatCount++) {
      CategoryAttribute categoryAttribute = new CategoryAttribute();
      JSONObject jsonObject =
          jsonInsertCategoryObject.getJSONObject(insCatCount);
      Short attributeId = (short) jsonObject.getInt("attribute_ID");
      Optional<Category> categoryObject =
          categoryRepo.findByCategoryID(categoryId);
      Category category = null;
      if (categoryObject.isPresent()) {
        category = categoryObject.get();
      }
      Attribute attributeObject = attributeRepo.getOne(attributeId);
      int keyValue = jsonObject.getInt(defaultKey);
      boolean attributeDefault = (keyValue > 0) ? value : !value;
      categoryAttribute.setCategory(catObj);
      categoryAttribute.setAttribute(attributeObject);
      categoryAttribute.setDefault(attributeDefault);
      CategoryAttribute categoryAttributeObject =
          categoryAttributeRepo.save(categoryAttribute);
      List<Resource> resourceList =
          resourceRepo.findResourceByCategory(category);
      List<Resourcebin> resourceBinList =
          resourcebinRepo.findResourceByCategory(category);
      if (!resourceList.isEmpty()) {
        this.insertResourceAttrMethod(resourceList, categoryAttributeObject,
            attributeObject);
      }
      if (!resourceBinList.isEmpty()) {
        this.insertResourceBinAttrMethod(resourceBinList,
            categoryAttributeObject, attributeObject);
      }
    }
  }

  /**
   * Update category object method.
   *
   * @param jsonUpdateCategoryObject the json update category object
   * @param catObj the cat obj
   * @throws JSONException the JSON exception
   */
  public void updateCategoryObjectMethod(
      final JSONArray jsonUpdateCategoryObject, final Category catObj)
      throws JSONException {
    for (int updCatCount = 0; updCatCount < jsonUpdateCategoryObject
        .length(); updCatCount++) {
      CategoryAttribute categoryAttrObj = new CategoryAttribute();
      JSONObject jo = jsonUpdateCategoryObject.getJSONObject(updCatCount);
      categoryAttrObj.setCategory(catObj);
      int intError = jo.getInt(defaultKey);
      boolean attributeDefault = (intError > 0) ? value : !value;
      int categoryAttributeId = jo.getInt("category_Attribute_ID");
      Optional<CategoryAttribute> categoryAttr =
          categoryAttributeRepo.findByCategoryAttributeID(categoryAttributeId);
      categoryAttrObj.setDefault(attributeDefault);
      categoryAttrObj.setCategoryAttributeID(categoryAttributeId);
      if (categoryAttr.isPresent()) {
        categoryAttrObj.setAttribute(categoryAttr.get().getAttribute());
      }

      categoryAttributeRepo.save(categoryAttrObj);
    }
  }

  /**
   * Insert Resource Attributes Method.
   *
   * @param resourceList the resource list
   * @param categoryAttributeObject the category attribute object
   * @param attributeObject the attribute object
   */
  public void insertResourceAttrMethod(final List<Resource> resourceList,
      final CategoryAttribute categoryAttributeObject,
      final Attribute attributeObject) {
    for (int insResAttrCount = 0; insResAttrCount < resourceList
        .size(); insResAttrCount++) {
      ResourceAttribute resourceAttributeObject = new ResourceAttribute();
      int dataTypeId = attributeObject.getAttrDataType().getData_Type_ID();
      if (dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_FLOAT
          || dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_CURRENCY) {
        resourceAttributeObject.setValue("0.0");
      } else if (dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_INTEGER) {
        resourceAttributeObject.setValue("0");
      } else {
        resourceAttributeObject.setValue("");
      }
      resourceAttributeObject
          .setAttribute(categoryAttributeObject.getAttribute());
      Optional<Resource> resource = resourceRepo
          .findByResourceID(resourceList.get(insResAttrCount).getResource_ID());
      if (resource.isPresent()) {
        resourceAttributeObject.setResource(resource.get());
      }
      resourceAttributeRepo.save(resourceAttributeObject);
    }
  }

  /**
   * Insert Resourcebin Attributes Method.
   *
   * @param resourceBinList the resource bin list
   * @param categoryAttributeObject the category attribute object
   * @param attributeObject the attribute object
   */
  public void insertResourceBinAttrMethod(
      final List<Resourcebin> resourceBinList,
      final CategoryAttribute categoryAttributeObject,
      final Attribute attributeObject) {
    for (int insResBinCount = 0; insResBinCount < resourceBinList
        .size(); insResBinCount++) {
      ResourcebinAttribute resourceBinAttributeObject =
          new ResourcebinAttribute();
      int dataTypeId = attributeObject.getAttrDataType().getData_Type_ID();
      if (dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_FLOAT
          || dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_CURRENCY) {
        resourceBinAttributeObject.setValue("0.0");
      } else if (dataTypeId == RestApiMessageConstants.DATA_TYPE_ID_INTEGER) {
        resourceBinAttributeObject.setValue("0");
      } else {
        resourceBinAttributeObject.setValue("");
      }
      resourceBinAttributeObject
          .setAttribute(categoryAttributeObject.getAttribute());
      Optional<Resourcebin> resourceBin = resourcebinRepo.findByResourceID(
          resourceBinList.get(insResBinCount).getResource_ID());
      if (resourceBin.isPresent()) {
        resourceBinAttributeObject.setResourcebin(resourceBin.get());
      }
      resourcebinAttributeRepo.save(resourceBinAttributeObject);
    }
  }

  /**
   * Delete category object method.
   *
   * @param jsonDeleteCategoryObject the json delete category object
   * @param categoryId the category id
   * @throws JSONException the JSON exception
   */
  public void deleteCategoryObjectMethod(
      final JSONArray jsonDeleteCategoryObject, final int categoryId)
      throws JSONException {
    for (int delCatCount = 0; delCatCount < jsonDeleteCategoryObject
        .length(); delCatCount++) {
      JSONObject jsonObject =
          jsonDeleteCategoryObject.getJSONObject(delCatCount);
      int categoryAttributeId = jsonObject.getInt("category_Attribute_ID");
      Optional<CategoryAttribute> categoryAttribute =
          categoryAttributeRepo.findByCategoryAttributeID(categoryAttributeId);
      Optional<Category> category = categoryRepo.findByCategoryID(categoryId);
      Category categoryObj = null;
      if (category.isPresent()) {
        categoryObj = category.get();
      }
      short attributeId = 0;
      if (categoryAttribute.isPresent()) {
        attributeId = categoryAttribute.get().getAttribute().getAttribute_ID();
      }
      List<Resource> resourceList =
          resourceRepo.findResourceByCategory(categoryObj);
      List<Resourcebin> resourceBinList =
          resourcebinRepo.findResourceByCategory(categoryObj);
      categoryAttributeRepo.deleteCategoryAttributeId(categoryAttributeId);
      if (!resourceList.isEmpty()) {
        resourceAttributeRepo.deleteResourcesAttributes(attributeId,
            resourceList);
      }
      if (!resourceBinList.isEmpty()) {
        resourcebinAttributeRepo.deleteResourceBinAttributes(attributeId,
            resourceBinList);
      }
    }
  }

  /**
   * Delete category service implementation.
   *
   * @param categoryID the category ID
   * @return ServiceResponse
   */
  @Override
  public ServiceResponse deleteCategory(final Integer categoryID) {

    LOGGER.info("Delete Category Service implementation");
    ServiceResponse response = new ServiceResponse();
    Optional<Category> categoryExists =
        categoryRepo.findByCategoryID(categoryID);
    if (categoryExists.isPresent()) {
      List<Resource> resourceList =
          resourceRepo.findResourceByCategory(categoryExists.get());
      List<Resourcebin> resourceBinList =
          resourcebinRepo.findResourceByCategory(categoryExists.get());
      List<Role> roleCategoryList =
          roleRepo.findByDefaultCategory(categoryExists.get());
      if (resourceList.isEmpty() && resourceBinList.isEmpty()
          && roleCategoryList.isEmpty()) {
        permissionRepo.deleteByCategory(categoryID);
      }
      categoryRepo.deleteById(categoryID);
    } else {
      throw new DataNotFoundException(RestApiMessageConstants.DELETE_CATEGORY);
    }
    return response;
  }

  /**
   * Gets the all operations.
   *
   * @return the list
   */
  @Override
  public List<Operation> getAllOperations() {
    LOGGER.info("Get all operations Service implementation");
    return operationRepo.findAll();
  }

  /**
   * Gets the all categories based on operation.
   *
   * @param operationID the operation ID
   * @return the set
   */
  public Set<Category> getAllCategoriesBasedOnOperation(
      final Integer operationID) {
    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    Set<Category> categoryList = new LinkedHashSet<>();
    Set<Permission> permissionObject =
        permissionService.getAllRolePermissions();
    for (Permission permission : permissionObject) {
      Operation operation = permission.getOperation();
      if (operation.getOperation_ID() == operationID) {
        Category category = permission.getCategory();
        category.setCategoryAttributes(categoryAttributeList);
        categoryList.add(category);
      }
    }
    return categoryList;
  }

  /**
   * Gets the all categories with view permissions.
   *
   * @return the set
   */
  @Override
  public Set<Category> getAllCategoriesWithViewPermissions() {

    LOGGER
        .info("Get all Categories with View Permission Service implementation");
    operationId = RestApiMessageConstants.OPERATION_VIEW;
    return this.getAllCategoriesBasedOnOperation(operationId);
  }

  /**
   * Gets the all categories with add permissions.
   *
   * @return the set
   */
  @Override
  public Set<Category> getAllCategoriesWithAddPermissions() {

    LOGGER
        .info("Get all Categories with Add Permission Service implementation");
    operationId = RestApiMessageConstants.OPERATION_ADD;
    return this.getAllCategoriesBasedOnOperation(operationId);
  }

  /**
   * Gets all categories with dispose permissions service implementation.
   *
   * @return the set
   */
  @Override
  public Set<Category> getAllCategoriesWithDisposePermissions() {

    LOGGER.info(
        "Get all Categories with Dispose Permissions Service implementation");
    operationId = RestApiMessageConstants.OPERATION_DISPOSE;
    return this.getAllCategoriesBasedOnOperation(operationId);
  }

  /**
   * Gets the all categories with allocate permissions.
   *
   * @return the set
   */
  @Override
  public Set<Category> getAllCategoriesWithAllocatePermissions() {
    LOGGER.info(
        "Get all Categories with Allocate Permission Service implementation");
    operationId = RestApiMessageConstants.OPERATION_ALLOCATE;
    return this.getAllCategoriesBasedOnOperation(operationId);
  }

  /**
   * Gets the attribute details by category id list.
   *
   * @param categoryId the category id
   * @return the list
   */
  @Override
  public List<CategoryAttribute> getCategoryAttributesByCategoryIdList(
      final List<Category> categoryId) {
    LOGGER.info("Get all Attributes by Category List Service implementation");

    List<CategoryAttribute> categoryAttributeList =
        categoryAttributeRepo.findByCategoryIn(categoryId);
    if (categoryAttributeList.isEmpty()) {
      throw new NoContentException(
          RestApiMessageConstants.NO_ATTRIBUTE_FOR_CATEGORY);
    }
    return categoryAttributeList;
  }
}
