package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.CategoryMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.AttrDataType;
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
import com.zone.zissa.service.impl.CategoryServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * The Class CategoryServiceTest.
 */
public class CategoryServiceTest extends ZissaApplicationTest {

  /** The category. */
  @InjectMocks
  private Category category;

  /** The attribute. */
  @InjectMocks
  private Attribute attribute;

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The category attribute. */
  @InjectMocks
  private CategoryAttribute categoryAttribute;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The resource. */
  @InjectMocks
  private Resource resource;

  /** The resource attribute. */
  @InjectMocks
  private ResourceAttribute resourceAttribute;

  /** The attribute value. */
  @InjectMocks
  private AttributeValue attributeValue;

  /** The operation. */
  @InjectMocks
  private Operation operation;

  /** The resource bin. */
  @InjectMocks
  private Resourcebin resourceBin;

  /** The resource bin attribute. */
  @InjectMocks
  private ResourcebinAttribute resourceBinAttribute;

  /** The category service impl. */
  @InjectMocks
  private CategoryServiceImpl categoryServiceImpl;

  /** The attr data type. */
  @InjectMocks
  private AttrDataType attrDataType;

  /** The category repo. */
  @Mock
  private CategoryRepository categoryRepo;

  /** The operation repo. */
  @Mock
  private OperationRepository operationRepo;

  /** The category attribute repo. */
  @Mock
  private CategoryAttributeRepository categoryAttributeRepo;

  /** The resource repo. */
  @Mock
  private ResourceRepository resourceRepo;

  /** The role repo. */
  @Mock
  private RoleRepository roleRepo;

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The resource bin repo. */
  @Mock
  private ResourcebinRepository resourceBinRepo;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The attribute repo. */
  @Mock
  private AttributeRepository attributeRepo;

  /** The resource attribute repo. */
  @Mock
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The resource bin attribute repo. */
  @Mock
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  /** The category mgmt controller. */
  @InjectMocks
  private CategoryMgmtController categoryMgmtController;


  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";



  /**
   * Gets the all categories test.
   *
   * @return the all categories test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllCategoriesTest() throws Exception {
    List<Category> categoryList = new ArrayList<Category>();
    Set<CategoryAttribute> categoryAttribute = new HashSet<>();
    List<Permission> permission = new ArrayList<Permission>();
    List<Resource> resource = new ArrayList<Resource>();
    user = new User();
    user.setUserID(1);
    category = new Category();
    category.setCategoryID(1);
    category.setCodePattern("CAT/LAP");
    category.setName("laptop");
    category.setUser(user);
    category.setCategoryAttributes(categoryAttribute);
    category.setPermissions(permission);
    category.setResources(resource);
    categoryList.add(category);
    when(categoryRepo.findAll()).thenReturn(categoryList);
    List<Category> list = categoryServiceImpl.getAllCategories();
    assertThat(categoryList.get(0).getName(), is(list.get(0).getName()));
    assertThat(1, is(list.size()));
  }

  /**
   * Gets the category attribute by id test.
   *
   * @return the category attribute by id test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getCategoryAttributeByIdTest() throws Exception {

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);

    category = new Category();
    category.setCategoryID(1);

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    categoryAttrList.add(categoryAttribute);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));
    when(categoryAttributeRepo.findByCategory(category))
        .thenReturn(categoryAttrList);
    List<CategoryAttribute> list = categoryServiceImpl
        .getCategoryAttributesByCategoryId(category.getCategory_ID());
    assertThat(categoryAttrList.get(0).getCategory_Attribute_ID(),
        is(list.get(0).getCategory_Attribute_ID()));
    assertThat(1, is(categoryAttrList.size()));
  }


  /**
   * Gets the category attribute by id failure test.
   *
   * @return the category attribute by id failure test
   * @throws Exception the exception
   */
  @Test(expected = NoContentException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getCategoryAttributeByIdFailureTest() throws Exception {
    Integer categoryId = 1;

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();

    when(categoryRepo.findByCategoryID(categoryId))
        .thenReturn(Optional.of(category));
    when(categoryAttributeRepo.findByCategory(category))
        .thenReturn(categoryAttrList);
    categoryServiceImpl.getCategoryAttributesByCategoryId(categoryId);
  }


  /**
   * Gets the category attributes by category id list test.
   *
   * @return the category attributes by category id list test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getCategoryAttributesByCategoryIdListTest() throws Exception {

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    category = new Category();
    category.setCategoryID(1);

    List<Category> categoryId = new ArrayList<>();
    categoryId.add(category);

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    categoryAttrList.add(categoryAttribute);

    when(categoryAttributeRepo.findByCategoryIn(categoryId))
        .thenReturn(categoryAttrList);
    List<CategoryAttribute> list =
        categoryServiceImpl.getCategoryAttributesByCategoryIdList(categoryId);
    assertThat(1, is(list.size()));

  }

  /**
   * Gets the category attributes by category id list failure test.
   *
   * @return the category attributes by category id list failure test
   * @throws Exception the exception
   */
  @Test(expected = NoContentException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getCategoryAttributesByCategoryIdListFailureTest()
      throws Exception {
    List<Category> categoryId = new ArrayList<>();

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();
    when(categoryAttributeRepo.findByCategoryIn(categoryId))
        .thenReturn(categoryAttrList);
    categoryServiceImpl.getCategoryAttributesByCategoryIdList(categoryId);
  }

  /**
   * Gets the all operations test.
   *
   * @return the all operations test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllOperationsTest() throws Exception {
    List<Operation> operationList = new ArrayList<>();
    operation.setOperationID(1);
    operation.setName("Add");
    operationList.add(operation);
    when(operationRepo.findAll()).thenReturn(operationList);
    List<Operation> list = categoryServiceImpl.getAllOperations();
    assertThat(1, is(list.size()));
  }

  /**
   * Delete category test.
   *
   * @throws Exception the exception
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void deleteCategoryTest() throws Exception {
    category = new Category();
    category.setCategoryID(1);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);
    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setCategory(category);
    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourceBinList);
    List<Role> roleList = new ArrayList<>();
    role = new Role();
    role.setRoleID(1);
    when(roleRepo.findByDefaultCategory(category)).thenReturn(roleList);
    permissionRepo.deleteByCategory(category.getCategory_ID());
    categoryServiceImpl.deleteCategory(category.getCategory_ID());
    verify(categoryRepo, times(1)).deleteById(category.getCategory_ID());

  }

  /**
   * Delete category failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteCategoryFailureTest() throws Exception {
    Integer categoryId = 1;
    when(categoryRepo.findByCategoryID(categoryId))
        .thenReturn(Optional.empty());
    categoryServiceImpl.deleteCategory(categoryId);
  }

  /**
   * Adds the category test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addCategoryTest() throws Exception {
    JSONObject addCategoryData = new JSONObject();

    addCategoryData.put("name", "sampleCategory");
    addCategoryData.put("code_Pattern", "CAT/MON");
    addCategoryData.put("user_ID", 1);

    JSONArray categoryAttributesArray = new JSONArray();

    JSONObject AttributeObject1 = new JSONObject();
    AttributeObject1.put("attribute_ID", 1);
    AttributeObject1.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject1);

    JSONObject AttributeObject2 = new JSONObject();
    AttributeObject2.put("attribute_ID", 2);
    AttributeObject2.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject2);

    JSONObject AttributeObject3 = new JSONObject();
    AttributeObject3.put("attribute_ID", 3);
    AttributeObject3.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject3);

    JSONObject AttributeObject4 = new JSONObject();
    AttributeObject4.put("attribute_ID", 4);
    AttributeObject4.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject4);

    addCategoryData.put("categoryAttributes", categoryAttributesArray);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bhatiyat");
    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    category = new Category();
    category.setCategoryID(1);
    category.setName("Laptop");
    category.setCodePattern("CAT/DES");
    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.findByCodePattern(category.getCode_Pattern()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("Add");
    when(operationRepo.findByOperationID(operation.getOperation_ID()))
        .thenReturn(Optional.of(operation));

    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    Category categoryResponse =
        categoryServiceImpl.addCategory(addCategoryData.toJSONString());
    assertThat(category.getName(), is(categoryResponse.getName()));

  }

  /**
   * Adds the category failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addCategoryFailureTest() throws Exception {
    JSONObject addCategoryData = new JSONObject();

    addCategoryData.put("name", "Laptops");
    addCategoryData.put("code_Pattern", "CMB/LAP");
    addCategoryData.put("user_ID", 1);

    JSONArray categoryAttributesArray = new JSONArray();

    JSONObject AttributeObject1 = new JSONObject();
    AttributeObject1.put("attribute_ID", 1);
    AttributeObject1.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject1);

    JSONObject AttributeObject2 = new JSONObject();
    AttributeObject2.put("attribute_ID", 2);
    AttributeObject2.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject2);

    JSONObject AttributeObject3 = new JSONObject();
    AttributeObject3.put("attribute_ID", 3);
    AttributeObject3.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject3);

    JSONObject AttributeObject4 = new JSONObject();
    AttributeObject4.put("attribute_ID", 4);
    AttributeObject4.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject4);

    addCategoryData.put("categoryAttributes", categoryAttributesArray);


    user = new User();
    user.setUserID(1);
    user.setUserName("Bhatiyat");
    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    category = new Category();
    category.setCategoryID(1);
    category.setName("Laptops");
    category.setCodePattern("CMB/LAP");
    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.findByCodePattern(category.getCode_Pattern()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("Add");
    when(operationRepo.findByOperationID(operation.getOperation_ID()))
        .thenReturn(Optional.of(operation));

    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(categoryServiceImpl.addCategory(addCategoryData.toJSONString()));

  }

  /**
   * Adds the category by invalid json test.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addCategoryByInvalidJsonTest() throws Exception {

    JSONObject addCategoryData = new JSONObject();

    addCategoryData.put("Name", "sampleCategory");
    addCategoryData.put("code_Pattern", "CAT/MON");
    addCategoryData.put("user_ID", 1);

    JSONArray categoryAttributesArray = new JSONArray();

    JSONObject AttributeObject1 = new JSONObject();
    AttributeObject1.put("attribute_ID", 1);
    AttributeObject1.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject1);

    JSONObject AttributeObject2 = new JSONObject();
    AttributeObject2.put("attribute_ID", 2);
    AttributeObject2.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject2);

    JSONObject AttributeObject3 = new JSONObject();
    AttributeObject3.put("attribute_ID", 3);
    AttributeObject3.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject3);

    JSONObject AttributeObject4 = new JSONObject();
    AttributeObject4.put("attribute_ID", 4);
    AttributeObject4.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject4);

    addCategoryData.put("categoryAttributes", categoryAttributesArray);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bhatiyat");
    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    category = new Category();
    category.setCategoryID(1);
    category.setName("Desktop");
    category.setCodePattern("CAT/DES");
    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.findByCodePattern(category.getCode_Pattern()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("Add");
    when(operationRepo.findByOperationID(operation.getOperation_ID()))
        .thenReturn(Optional.of(operation));

    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(categoryServiceImpl.addCategory(addCategoryData.toJSONString()))
        .thenReturn(category);
  }


  /**
   * Update category test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateCategoryTest() throws Exception {

    JSONObject updateCategoryData = new JSONObject();
    updateCategoryData.put("category_ID", 1);
    updateCategoryData.put("name", "Laptops");
    updateCategoryData.put("user_ID", 1);

    JSONArray deleteArray = new JSONArray();
    JSONObject deleteObject = new JSONObject();
    deleteObject.put("category_Attribute_ID", 2);
    deleteArray.add(deleteObject);


    JSONArray insertArray = new JSONArray();
    JSONObject insertObject = new JSONObject();
    insertObject.put("attribute_ID", 1);
    insertObject.put("category_Attribute_ID", 2);
    insertObject.put("isDefault", 1);
    insertArray.add(insertObject);

    JSONArray updateArray = new JSONArray();
    JSONObject updateObject = new JSONObject();
    updateObject.put("category_Attribute_ID", 1);
    updateObject.put("isDefault", 0);
    updateArray.add(updateObject);

    updateCategoryData.put("delete_categoryAttributes", deleteArray);
    updateCategoryData.put("insert_categoryAttributes", insertArray);
    updateCategoryData.put("update_defaultAttributes", updateArray);

    attrDataType = new AttrDataType();
    attrDataType.setDataTypeID(1);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttrDataType(attrDataType);

    category = new Category();
    category.setCategoryID(1);
    category.setName("Desktop");
    category.setCodePattern("CAT/DES");
    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resourceList.add(resource);

    List<Resource> list = new ArrayList<>();
    when(resourceRepo.findResourceByCategoryAndStatus(category))
        .thenReturn(list);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.findByCategoryAttributeID(
        categoryAttribute.getCategory_Attribute_ID()))
            .thenReturn(Optional.of(categoryAttribute));

    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setCategory(category);
    resourceBinList.add(resourceBin);

    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourceBinList);

    categoryAttributeRepo.deleteCategoryAttributeId(
        categoryAttribute.getCategory_Attribute_ID());

    resourceAttributeRepo.deleteResourcesAttributes(attribute.getAttribute_ID(),
        resourceList);
    resourceBinAttributeRepo.deleteResourceBinAttributes(
        attribute.getAttribute_ID(), resourceBinList);

    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);
    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setResource(resource);
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    when(resourceBinRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resourceBin));

    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourcebin(resourceBin);
    when(resourceBinAttributeRepo.save(any(ResourcebinAttribute.class)))
        .thenReturn(resourceBinAttribute);

    Category categoryResponse =
        categoryServiceImpl.updateCategory(updateCategoryData.toJSONString());
    assertThat(category.getName(), is(categoryResponse.getName()));
  }

  /**
   * Update category by invalid json test.
   *
   * @throws Exception the exception
   */
  @Test(expected = JSONException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateCategoryByInvalidJsonTest() throws Exception {

    JSONObject updateCategoryData = new JSONObject();
    updateCategoryData.put("Category_ID", 1);
    updateCategoryData.put("name", "Laptops");
    updateCategoryData.put("user_ID", 1);

    JSONArray deleteArray = new JSONArray();

    JSONArray insertArray = new JSONArray();

    JSONArray updateArray = new JSONArray();
    JSONObject updateObject = new JSONObject();
    updateObject.put("category_Attribute_ID", 1);
    updateObject.put("isDefault", 0);
    updateArray.add(updateObject);

    updateCategoryData.put("delete_categoryAttributes", deleteArray);
    updateCategoryData.put("insert_categoryAttributes", insertArray);
    updateCategoryData.put("update_defaultAttributes", updateArray);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    category = new Category();
    category.setCategoryID(1);
    category.setName("Desktop");
    category.setCodePattern("CAT/DES");
    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    when(resourceRepo.findResourceByCategoryAndStatus(category))
        .thenReturn(resourceList);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.findByCategoryAttributeID(
        categoryAttribute.getCategory_Attribute_ID()))
            .thenReturn(Optional.of(categoryAttribute));

    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setCategory(category);
    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourceBinList);

    categoryAttributeRepo.deleteCategoryAttributeId(
        categoryAttribute.getCategory_Attribute_ID());

    resourceAttributeRepo.deleteResourcesAttributes(attribute.getAttribute_ID(),
        resourceList);
    resourceBinAttributeRepo.deleteResourceBinAttributes(
        attribute.getAttribute_ID(), resourceBinList);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);
    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setResource(resource);
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    when(resourceBinRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resourceBin));

    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourcebin(resourceBin);
    when(resourceBinAttributeRepo.save(any(ResourcebinAttribute.class)))
        .thenReturn(resourceBinAttribute);

    when(categoryServiceImpl.updateCategory(updateCategoryData.toJSONString()))
        .thenReturn(category);
  }

  /**
   * Update category by existing name test.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateCategoryByExistingNameTest() throws Exception {

    JSONObject updateCategoryData = new JSONObject();
    updateCategoryData.put("category_ID", 1);
    updateCategoryData.put("name", "Office Equipments");
    updateCategoryData.put("user_ID", 1);

    JSONArray deleteArray = new JSONArray();

    JSONArray insertArray = new JSONArray();

    JSONArray updateArray = new JSONArray();
    JSONObject updateObject = new JSONObject();
    updateObject.put("category_Attribute_ID", 1);
    updateObject.put("isDefault", 0);
    updateArray.add(updateObject);

    updateCategoryData.put("delete_categoryAttributes", deleteArray);
    updateCategoryData.put("insert_categoryAttributes", insertArray);
    updateCategoryData.put("update_defaultAttributes", updateArray);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    category = new Category();
    category.setCategoryID(1);
    category.setName("Office Equipments");
    category.setCodePattern("CAT/DES");
    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    Category categoryObj = new Category();
    categoryObj.setCategoryID(2);
    categoryObj.setName("Office Equipments");
    when(categoryRepo.findByName(categoryObj.getName()))
        .thenReturn(Optional.of(categoryObj));

    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    when(resourceRepo.findResourceByCategoryAndStatus(category))
        .thenReturn(resourceList);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.findByCategoryAttributeID(
        categoryAttribute.getCategory_Attribute_ID()))
            .thenReturn(Optional.of(categoryAttribute));

    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setCategory(category);
    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourceBinList);

    categoryAttributeRepo.deleteCategoryAttributeId(
        categoryAttribute.getCategory_Attribute_ID());

    resourceAttributeRepo.deleteResourcesAttributes(attribute.getAttribute_ID(),
        resourceList);
    resourceBinAttributeRepo.deleteResourceBinAttributes(
        attribute.getAttribute_ID(), resourceBinList);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);
    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setResource(resource);
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    when(resourceBinRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resourceBin));

    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourcebin(resourceBin);
    when(resourceBinAttributeRepo.save(any(ResourcebinAttribute.class)))
        .thenReturn(resourceBinAttribute);

    when(categoryServiceImpl.updateCategory(updateCategoryData.toJSONString()));



  }

  /**
   * Update category failure test.
   *
   * @throws Exception the exception
   */
  @Test(expected = ConflictException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateCategoryFailureTest() throws Exception {

    JSONObject updateCategoryData = new JSONObject();
    updateCategoryData.put("category_ID", 1);
    updateCategoryData.put("name", "Office Equipments");
    updateCategoryData.put("user_ID", 1);

    JSONArray deleteArray = new JSONArray();
    JSONObject deleteObject = new JSONObject();
    deleteObject.put("category_Attribute_ID", 2);
    deleteArray.add(deleteObject);

    JSONArray insertArray = new JSONArray();

    JSONArray updateArray = new JSONArray();
    JSONObject updateObject = new JSONObject();
    updateObject.put("category_Attribute_ID", 1);
    updateObject.put("isDefault", 0);
    updateArray.add(updateObject);

    updateCategoryData.put("delete_categoryAttributes", deleteArray);
    updateCategoryData.put("insert_categoryAttributes", insertArray);
    updateCategoryData.put("update_defaultAttributes", updateArray);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    Set<Allocation> allocationList = new HashSet<>();
    Allocation allocation = new Allocation();
    allocation.setAllocationID(1);
    allocationList.add(allocation);
    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setAllocations(allocationList);
    resourceList.add(resource);

    category = new Category();
    category.setCategoryID(1);
    category.setName("Office Equipments");
    category.setCodePattern("CAT/DES");
    category.setResources(resourceList);
    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    when(resourceRepo.findResourceByCategoryAndStatus(category))
        .thenReturn(resourceList);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.findByCategoryAttributeID(
        categoryAttribute.getCategory_Attribute_ID()))
            .thenReturn(Optional.of(categoryAttribute));

    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setCategory(category);
    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourceBinList);

    categoryAttributeRepo.deleteCategoryAttributeId(
        categoryAttribute.getCategory_Attribute_ID());

    resourceAttributeRepo.deleteResourcesAttributes(attribute.getAttribute_ID(),
        resourceList);
    resourceBinAttributeRepo.deleteResourceBinAttributes(
        attribute.getAttribute_ID(), resourceBinList);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);
    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setResource(resource);
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    when(resourceBinRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resourceBin));

    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourcebin(resourceBin);
    when(resourceBinAttributeRepo.save(any(ResourcebinAttribute.class)))
        .thenReturn(resourceBinAttribute);

    when(categoryServiceImpl.updateCategory(updateCategoryData.toJSONString()));
  }


  /**
   * Adds the category failute test by invalid user.
   *
   * @throws Exception the exception
   */
  @Test(expected = Exception.class)
  @Transactional
  public void addCategoryFailuteTestByInvalidUser() throws Exception {

    JSONObject addCategoryData = new JSONObject();

    addCategoryData.put("name", "sampleCategory");
    addCategoryData.put("code_Pattern", "CAT/MON");
    addCategoryData.put("user_ID", 000);

    JSONArray categoryAttributesArray = new JSONArray();

    JSONObject AttributeObject1 = new JSONObject();
    AttributeObject1.put("attribute_ID", 1);
    AttributeObject1.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject1);

    JSONObject AttributeObject2 = new JSONObject();
    AttributeObject2.put("attribute_ID", 2);
    AttributeObject2.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject2);

    JSONObject AttributeObject3 = new JSONObject();
    AttributeObject3.put("attribute_ID", 3);
    AttributeObject3.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject3);

    JSONObject AttributeObject4 = new JSONObject();
    AttributeObject4.put("attribute_ID", 4);
    AttributeObject4.put("isDefault", 0);
    categoryAttributesArray.add(AttributeObject4);

    addCategoryData.put("categoryAttributes", categoryAttributesArray);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bhatiyat");
    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(Optional.of(user));

    category = new Category();
    category.setCategoryID(1);
    category.setName("Laptops");
    category.setCodePattern("CMB/LAP");
    when(categoryRepo.findByName(category.getName()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.findByCodePattern(category.getCode_Pattern()))
        .thenReturn(Optional.of(category));
    when(categoryRepo.save(any(Category.class))).thenReturn(category);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    when(attributeRepo.getOne(attribute.getAttribute_ID()))
        .thenReturn(attribute);

    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    when(categoryAttributeRepo.save(any(CategoryAttribute.class)))
        .thenReturn(categoryAttribute);

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("Add");
    when(operationRepo.findByOperationID(operation.getOperation_ID()))
        .thenReturn(Optional.of(operation));

    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    when(permissionRepo.save(any(Permission.class))).thenReturn(permission);
    when(categoryServiceImpl.addCategory(addCategoryData.toJSONString()))
        .thenThrow(new Exception("invalid data"));

  }



  /**
   * Gets the category attributes by invalid id test.
   *
   * @return the category attributes by invalid id test
   * @throws Exception the exception
   */
  @Test(expected = NotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getCategoryAttributesByInvalidIdTest() throws Exception {
    Integer categoryId = 879;
    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);

    category = new Category();
    category.setCategoryID(1);

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttribute.setAttribute(attribute);
    categoryAttrList.add(categoryAttribute);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));
    when(categoryAttributeRepo.findByCategory(category))
        .thenReturn(categoryAttrList);
    categoryServiceImpl.getCategoryAttributesByCategoryId(categoryId);
  }

  /**
   * Update category by invalid data test.
   *
   * @throws JSONException the JSON exception
   */
  @Test(expected = DataNotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void updateCategoryByInvalidDataTest() throws JSONException {

    JSONObject updateCategoryData = new JSONObject();
    updateCategoryData.put("category_ID", 1);
    updateCategoryData.put("name", "Laptops");
    updateCategoryData.put("user_ID", 1);

    JSONArray deleteArray = new JSONArray();


    JSONArray insertArray = new JSONArray();

    JSONArray updateArray = new JSONArray();
    JSONObject updateObject = new JSONObject();
    updateObject.put("category_Attribute_ID", 1);
    updateObject.put("isDefault", 0);
    updateArray.add(updateObject);

    updateCategoryData.put("delete_categoryAttributes", deleteArray);
    updateCategoryData.put("insert_categoryAttributes", insertArray);
    updateCategoryData.put("update_defaultAttributes", updateArray);

    category = new Category();
    category.setCategoryID(1);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.empty());
    when(categoryServiceImpl.updateCategory(updateCategoryData.toJSONString()));

  }

}
