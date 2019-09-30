package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.ResourceMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.DataToLongException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.AllocationType;
import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Employee;
import com.zone.zissa.model.EmployeeAllocation;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.OtherAllocation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Project;
import com.zone.zissa.model.ProjectAllocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.Status;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AllocationTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.EmployeeAllocationRepository;
import com.zone.zissa.repos.EmployeeRepository;
import com.zone.zissa.repos.OtherAllocationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ProjectAllocationRepository;
import com.zone.zissa.repos.ProjectRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.StatusRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.ResourceService;
import com.zone.zissa.service.impl.PermissionService;
import com.zone.zissa.service.impl.ResourceServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * The Class ResourceServiceTest.
 */
public class ResourceServiceTest extends ZissaApplicationTest {

  /** The category. */
  @InjectMocks
  private Category category;
  @InjectMocks
  private AttrDataType attrDataType;

  /** The category attribute. */
  @InjectMocks
  private CategoryAttribute categoryAttribute;

  /** The attribute value. */
  @InjectMocks
  private AttributeValue attributeValue;

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The attribute. */
  @InjectMocks
  private Attribute attribute;
  /** The attribute. */
  @InjectMocks
  private Attribute attribute1;

  /** The resource. */
  @InjectMocks
  private Resource resource;
  /** The resource. */
  @InjectMocks
  private Resource resource1;


  /** The allocation. */
  @InjectMocks
  private Allocation allocation;
  /** The allocation. */
  @InjectMocks
  private Allocation allocation1;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The operation. */
  @InjectMocks
  private Operation operation;

  /** The resource attribute. */
  @InjectMocks
  private ResourceAttribute resourceAttribute;

  /** The status. */
  @InjectMocks
  private Status status;
  /** The status. */
  @InjectMocks
  private Status status1;

  /** The resource bin attribute. */
  @InjectMocks
  private ResourcebinAttribute resourceBinAttribute;

  /** The resource bin. */
  @InjectMocks
  private Resourcebin resourceBin;
  @InjectMocks
  private Resourcebin resourceBin1;

  /** The allocation type. */
  @InjectMocks
  private AllocationType allocationType;

  /** The allocation type. */
  @InjectMocks
  private AllocationType allocationType1;
  /** The service response. */
  @InjectMocks
  private ServiceResponse serviceResponse;

  @InjectMocks
  private Project project;
  @InjectMocks
  private Employee employee;
  @InjectMocks
  private EmployeeAllocation employeeAllocation;
  @InjectMocks
  private ProjectAllocation projectAllocation;
  @InjectMocks
  private OtherAllocation otherAllocation;
  @Mock
  private ProjectRepository projectRepo;



  /** The resource repo. */
  @Mock
  private ResourceRepository resourceRepo;

  /** The attribute repo. */
  @Mock
  private AttributeRepository attributeRepo;

  /** The allocation repo. */
  @Mock
  private AllocationRepository allocationRepo;

  /** The user repo. */
  @Mock
  private UserRepository userRepo;

  /** The role repo. */
  @Mock
  private RoleRepository roleRepo;

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The category repo. */
  @Mock
  private CategoryRepository categoryRepo;

  /** The status repo. */
  @Mock
  private StatusRepository statusRepo;

  /** The resource bin repo. */
  @Mock
  private ResourcebinRepository resourceBinRepo;

  /** The resource attribute repo. */
  @Mock
  private ResourceAttributeRepository resourceAttributeRepo;

  /** The allocation type repo. */
  @Mock
  private AllocationTypeRepository allocationTypeRepo;

  /** The project allocation repo. */
  @Mock
  private ProjectAllocationRepository projectAllocationRepo;


  /** The employee allocation repo. */
  @Mock
  private EmployeeAllocationRepository employeeAllocationRepo;

  /** The projectrepo. */
  @Mock
  private ProjectRepository projectrepo;

  /** The employee repo. */
  @Mock
  private EmployeeRepository employeeRepo;

  /** The resource service impl. */
  @InjectMocks
  private ResourceServiceImpl resourceServiceImpl;

  /** The permission service. */
  @Mock
  private PermissionService permissionService;

  /** The other allocation repo. */
  @Mock
  private OtherAllocationRepository otherAllocationRepo;


  /** The resource controller. */
  @InjectMocks
  private ResourceMgmtController resourceController;

  /** The resource service. */
  @Mock
  private ResourceService resourceService;

  /** The resource bin attribute repo. */
  @Mock
  private ResourcebinAttributeRepository resourceBinAttributeRepo;

  @InjectMocks
  private ResourceMgmtController resourceMgmtController;


  /** The Constant username. */
  // for success test cases authentication
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";


  /** The Constant f_Username. */
  // for failure test cases authentiation (access denied)
  private static final String f_Username = "AmaliK";

  /** The Constant f_Password. */
  private static final String f_Password = "Zone@789";

  /**
   * Adds the resource test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void addResourceTest() throws Exception {

    JSONObject addResourceData = new JSONObject();

    addResourceData.put("category_ID", 1);
    addResourceData.put("user_ID", 1);

    JSONArray array = new JSONArray();
    JSONObject userData1 = new JSONObject();
    userData1.put("code", "CMB/LAP/0004");
    userData1.put("status_ID", 0);

    JSONArray array1 = new JSONArray();

    JSONObject resAttr = new JSONObject();
    resAttr.put("attribute_ID", 2);
    resAttr.put("value", "dell");
    array1.add(resAttr);
    userData1.put("resourceAttributes", array1);
    array.add(userData1);

    JSONArray arrayNext = new JSONArray();
    JSONObject userData = new JSONObject();
    userData.put("code", "CMB/LAP/0005");
    userData.put("status_ID", 0);
    arrayNext.add(userData);
    JSONArray array3 = new JSONArray();

    JSONObject resAttr2 = new JSONObject();
    resAttr2.put("attribute_ID", 2);
    resAttr2.put("value", "SAMSUNG");
    array3.add(resAttr2);
    userData.put("resourceAttributes", array3);
    array.add(userData);

    addResourceData.put("resource", array);

    Integer operationId = 2;

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);


    category = new Category();
    category.setCategoryID(1);
    Optional<Category> categoryToReturnFromRepository = Optional.of(category);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);
    Optional<User> userToReturnFromRepository = Optional.of(user);


    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");
    status.setResources(resourceList);
    Optional<Status> statusToReturnFromRepository = Optional.of(status);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(categoryToReturnFromRepository);

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(userToReturnFromRepository);
    when(statusRepo.findBystatusID(status.getStatus_ID()))
        .thenReturn(statusToReturnFromRepository);
    when(resourceRepo.save(any(Resource.class))).thenReturn(resource);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    when(resourceServiceImpl.addResource(addResourceData.toJSONString()))
        .thenReturn(resourceList);
  }


  /**
   * Adds the resource access denied test.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void addResourceAccessDeniedTest() throws Exception {

    JSONObject addResourceData = new JSONObject();

    addResourceData.put("category_ID", 2);
    addResourceData.put("user_ID", 1);

    JSONArray array = new JSONArray();
    JSONObject userData1 = new JSONObject();
    userData1.put("code", "CMB/OEQ/0001");
    userData1.put("status_ID", 0);

    JSONArray array1 = new JSONArray();

    JSONObject resAttr = new JSONObject();
    resAttr.put("attribute_ID", 2);
    resAttr.put("value", "Dell");
    array1.add(resAttr);
    userData1.put("resourceAttributes", array1);
    array.add(userData1);

    addResourceData.put("resource", array);

    Integer operationId = 2;
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);


    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");
    status.setResources(resourceList);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceServiceImpl.addResource(addResourceData.toJSONString()));
  }

  /**
   * Update resource test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void updateResourceTest() throws Exception {

    JSONObject updateResourceData = new JSONObject();

    updateResourceData.put("resource_ID", 1);
    updateResourceData.put("user_ID", 1);
    updateResourceData.put("status_ID", 0);

    JSONArray array = new JSONArray();
    JSONArray array1 = new JSONArray();

    JSONObject resAttr = new JSONObject();
    resAttr.put("resource_Attribute_ID", 1);
    resAttr.put("value", "Dell");
    array1.add(resAttr);

    updateResourceData.put("resourceAttributes", array1);
    array.add(updateResourceData);

    Integer operationId = 3;
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);


    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);
    Optional<User> userToReturnFromRepository = Optional.of(user);


    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");
    status.setResources(resourceList);
    Optional<Status> statusToReturnFromRepository = Optional.of(status);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(resourceToReturnFromRepository);

    when(userRepo.findByUserID(user.getUser_ID()))
        .thenReturn(userToReturnFromRepository);
    when(statusRepo.findBystatusID(status.getStatus_ID()))
        .thenReturn(statusToReturnFromRepository);
    when(resourceRepo.save(any(Resource.class))).thenReturn(resource);
    when(resourceAttributeRepo.findByResourceAttributeID(
        resourceAttribute.getResource_Attribute_ID()))
            .thenReturn(Optional.of(resourceAttribute));
    when(resourceAttributeRepo.save(any(ResourceAttribute.class)))
        .thenReturn(resourceAttribute);

    List<Resource> resourceResponse =
        resourceServiceImpl.updateResource(array.toJSONString());
    assertThat(resource.getResource_ID(),
        is(resourceResponse.get(0).getResource_ID()));

  }


  /**
   * Update resource access denied test.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = f_Username, password = f_Password)
  public void updateResourceAccessDeniedTest() throws Exception {
    JSONObject updateResourceData = new JSONObject();

    updateResourceData.put("resource_ID", 1);
    updateResourceData.put("user_ID", 1);
    updateResourceData.put("status_ID", 0);

    JSONArray array = new JSONArray();
    JSONArray array1 = new JSONArray();

    JSONObject resAttr = new JSONObject();
    resAttr.put("resource_Attribute_ID", 1);
    resAttr.put("value", "Samsung");
    array1.add(resAttr);

    updateResourceData.put("resourceAttributes", array1);
    array.add(updateResourceData);

    Integer operationId = 3;
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);


    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");
    status.setResources(resourceList);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);

    when(resourceServiceImpl.updateResource(array.toJSONString()));
  }


  /**
   * Gets the all categories with view permissions test.
   *
   * @return the all categories with view permissions test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithViewPermissionsTest() throws Exception {

    operation = new Operation();
    operation.setOperationID(1);
    operation.setName("View");

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resourceList.add(resource);

    Set<Category> categoryList = new HashSet<>();
    category = new Category();
    category.setCategoryID(1);
    category.setResources(resourceList);
    categoryList.add(category);

    role = new Role();
    role.setRoleID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(permissionService.getAllRolePermissions()).thenReturn(permissionList);
    Set<Category> categoryResponse =
        resourceServiceImpl.getAllCategoriesWithViewPermissions();

    assertThat(1, is(categoryResponse.size()));
  }

  /**
   * Gets the all categories with add permissions test.
   *
   * @return the all categories with add permissions test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithAddPermissionsTest() throws Exception {
    operation = new Operation();
    operation.setOperationID(2);
    operation.setName("Add");

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resourceList.add(resource);

    Set<Category> categoryList = new HashSet<>();
    category = new Category();
    category.setCategoryID(1);
    category.setResources(resourceList);
    categoryList.add(category);

    role = new Role();
    role.setRoleID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(permissionService.getAllRolePermissions()).thenReturn(permissionList);
    Set<Category> categoryResponse =
        resourceServiceImpl.getAllCategoriesWithAddPermissions();

    assertThat(1, is(categoryResponse.size()));
  }

  /**
   * Gets the all categories with dispose permissions test.
   *
   * @return the all categories with dispose permissions test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithDisposePermissionsTest() throws Exception {
    operation = new Operation();
    operation.setOperationID(5);
    operation.setName("Dispose");

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resourceList.add(resource);

    Set<Category> categoryList = new HashSet<>();
    category = new Category();
    category.setCategoryID(1);
    category.setResources(resourceList);
    categoryList.add(category);

    role = new Role();
    role.setRoleID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(permissionService.getAllRolePermissions()).thenReturn(permissionList);
    Set<Category> categoryResponse =
        resourceServiceImpl.getAllCategoriesWithDisposePermissions();

    assertThat(1, is(categoryResponse.size()));
  }

  /**
   * Gets the all categories with allocate permissions test.
   *
   * @return the all categories with allocate permissions test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithAllocatePermissionsTest() throws Exception {
    operation = new Operation();
    operation.setOperationID(4);
    operation.setName("Allocate");

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resourceList.add(resource);

    Set<Category> categoryList = new HashSet<>();
    category = new Category();
    category.setCategoryID(1);
    category.setResources(resourceList);
    categoryList.add(category);

    role = new Role();
    role.setRoleID(1);

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setOperation(operation);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(permissionService.getAllRolePermissions()).thenReturn(permissionList);
    Set<Category> categoryResponse =
        resourceServiceImpl.getAllCategoriesWithAllocatePermissions();

    assertThat(1, is(categoryResponse.size()));
  }

  /**
   * Gets the all permissions by role and category test.
   *
   * @return the all permissions by role and category test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleAndCategoryTest() throws Exception {
    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    List<Permission> permissionList = new ArrayList<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(permissionRepo.findPermissionByRoleAndCategory(role, category))
        .thenReturn(permissionList);

    when(resourceServiceImpl.getAllPermissionsByRoleAndCategory(
        role.getRole_ID(), category.getCategory_ID()))
            .thenReturn(permissionList);

  }


  /**
   * Gets the all permissions by role and category failure test.
   *
   * @return the all permissions by role and category failure test
   * @throws Exception the exception
   */

  @Test(expected = NoContentException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleAndCategoryFailureTest() throws Exception {

    category = new Category();
    category.setCategoryID(1);
    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    role = new Role();
    role.setRoleID(1);
    Optional<Role> roleToReturnFromRepository = Optional.of(role);

    List<Permission> permissionList = new ArrayList<>();


    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(categoryToReturnFromRepository);
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(roleToReturnFromRepository);

    when(permissionRepo.findPermissionByRoleAndCategory(role, category))
        .thenReturn(permissionList);

    when(resourceServiceImpl.getAllPermissionsByRoleAndCategory(
        role.getRole_ID(), category.getCategory_ID()));

  }


  /**
   * Gets the all permissions by role and category failure by unavailable category test.
   *
   * @return the all permissions by role and category failure by unavailable category test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleAndCategoryFailureByUnavailableCategoryTest()
      throws Exception {
    category = new Category();
    category.setCategoryID(15);

    role = new Role();
    role.setRoleID(1);

    List<Permission> permissionList = new ArrayList<>();
    permission = new Permission();
    permission.setPermissionID(1);
    permission.setRole(role);
    permission.setCategory(category);
    permissionList.add(permission);

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.empty());
    when(roleRepo.findByroleID(role.getRole_ID()))
        .thenReturn(Optional.of(role));

    when(permissionRepo.findPermissionByRoleAndCategory(role, category))
        .thenReturn(permissionList);

    when(resourceServiceImpl.getAllPermissionsByRoleAndCategory(
        role.getRole_ID(), category.getCategory_ID()));

  }



  /**
   * Gets the resources by search term test.
   *
   * @return the resources by search term test
   * @throws Exception the exception
   */

  @Test

  @WithMockUser(username = username, password = password)
  public void getResourcesBySearchTermTest() throws Exception {

    int page = 0;
    int size = 1;
    String direction = "ASC";
    String searchText = "Samsung";

    String direction1 = "DESC";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    Optional<Attribute> attributeToReturnRepo = Optional.of(attribute);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 1);
    status.setStatusName("Allocated");

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    otherAllocation = new OtherAllocation();
    otherAllocation.setAssigneeName("test");

    Set<Allocation> allocationList = new HashSet<>();

    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocation.setOtherAllocations(otherAllocation);

    allocationList.add(allocation);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);
    resourceList.add(resource);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(attributeToReturnRepo);

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    resourceServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());
  }

  @Test

  @WithMockUser(username = username, password = password)
  public void getResourcesBySearchTermDescTest() throws Exception {

    int page = 0;
    int size = 1;
    String direction = "DESC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);



    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 1);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);

    allocationType1 = new AllocationType();
    allocationType1.setAllocationTypeID((byte) 2);

    employee = new Employee();
    employee.setUserName("BathiyaT");

    employeeAllocation = new EmployeeAllocation();
    employeeAllocation.setEmployee(employee);

    project = new Project();
    project.setProjectName("zissa");

    projectAllocation = new ProjectAllocation();
    projectAllocation.setProject(project);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocation.setEmployeeAllocations(employeeAllocation);
    allocationList.add(allocation);

    Set<Allocation> allocationList1 = new HashSet<>();
    allocation1 = new Allocation();
    allocation1 = new Allocation();
    allocation1.setAllocationType(allocationType1);
    allocation1.setResource(resource1);
    allocation1.setStatus(status);
    allocation1.setUser(user);
    allocation1.setProjectAllocations(projectAllocation);
    allocationList1.add(allocation1);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung"); // resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);
    resourceList.add(resource);


    resource1 = new Resource();
    resource1.setResourceID(1);
    resource1.setCode("RES/0002");
    resource1.setStatus(status);
    resource1.setCategory(category);
    resource1.setUser(user);
    resource1.setResourceAttributes(resourceAttributeList);
    resource1.setAllocations(allocationList1);
    resourceList.add(resource1);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);
    //
    // when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
    // .thenReturn(Optional.of(attribute));

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    resourceServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());
  }


  /**
   * Gets the disposed resources by search term test.
   *
   * @return the disposed resources by search term test
   * @throws Exception the exception
   */

  @Test

  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesBySearchTermTest() throws Exception {

    int page = 0;
    int size = 1;
    int pageSize = size;
    String direction = "ASC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);


    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attributeValueList.add(attributeValue);

    role = new Role();
    role.setRoleID(1);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationList.add(allocation);

    List<ResourcebinAttribute> resourceBinAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);

    resourceBinAttribute.setValue("Samsung");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttributeList.add(resourceBinAttribute);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCode("RES/0002");
    resourceBin.setDisposeReason("Not Required");
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(1);
    resourceBin.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin);



    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(resourceBinRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceBinList);
    when(resourceBinRepo.findResourcesByCategoryOrderByReasonDesc(categoryId))
        .thenReturn(resourceBinList);
    when(resourceBinRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceBinList);
    when(resourceBinRepo.findResourcesByCategoryOrderByReasonAsc(categoryId))
        .thenReturn(resourceBinList);
    resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryId, page,
        size, searchText, direction, attribute.getAttribute_ID());
  }



  /**
   * Resource allocation test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject allocateResource = new JSONObject();
    allocateResource.put("allocationtype_ID", 3);
    allocateResource.put("resource_ID", 1);
    allocateResource.put("status_ID", 1);
    allocateResource.put("user_ID", 1);

    JSONObject otherAllocations = new JSONObject();
    otherAllocations.put("assignee_Name", "Nikhil");

    allocateResource.put("otherAllocations", otherAllocations);

    array.add(allocateResource);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(1))
        .thenReturn(resourceToReturnFromRepository);

    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 3))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenReturn(serviceResponse);



  }

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationForEmployeeTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject allocateResource = new JSONObject();
    allocateResource.put("allocationtype_ID", 1);
    allocateResource.put("resource_ID", 1);
    allocateResource.put("status_ID", 1);
    allocateResource.put("user_ID", 1);


    JSONObject employeeAllocations = new JSONObject();
    JSONObject employeeObj = new JSONObject();

    employeeObj.put("sAMAccountName", "BathiyaT");
    employeeObj.put("name", "Bathiya Tennakoon");
    employeeAllocations.put("employee", employeeObj);

    allocateResource.put("employeeAllocations", employeeAllocations);

    array.add(allocateResource);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(1))
        .thenReturn(resourceToReturnFromRepository);

    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);

    employee = new Employee();
    employee.setUserName("BathiyaT");
    Optional<Employee> employeeToReturnFromRepository = Optional.of(employee);
    when(employeeRepo.findByUserName("BathiyaT"))
        .thenReturn(employeeToReturnFromRepository);
    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 1))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenReturn(serviceResponse);



  }

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationForProjectTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject allocateResource = new JSONObject();
    allocateResource.put("allocationtype_ID", 2);
    allocateResource.put("resource_ID", 1);
    allocateResource.put("status_ID", 1);
    allocateResource.put("user_ID", 1);



    JSONObject projectAllocations = new JSONObject();
    JSONObject projectObj = new JSONObject();
    projectObj.put("code", "rest");

    projectObj.put("projectName", "Zissa");
    projectAllocations.put("project", projectObj);

    allocateResource.put("projectAllocations", projectAllocations);

    array.add(allocateResource);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);


    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(1))
        .thenReturn(resourceToReturnFromRepository);

    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);

    project = new Project();
    project.setProjectName("Zissa");
    project.setActiveStatus((byte) 1);
    Optional<Project> projectToReturnFromRepository = Optional.of(project);
    when(projectRepo.findByProjectName("zissa"))
        .thenReturn(projectToReturnFromRepository);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 2);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 2))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenReturn(serviceResponse);



  }

  /**
   * Resource allocation failure test.
   *
   * @throws Exception the exception
   */

  @Test(expected = ConflictException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationFailureTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject allocateResource = new JSONObject();
    allocateResource.put("allocationtype_ID", 3);
    allocateResource.put("resource_ID", 7);
    allocateResource.put("status_ID", 1);
    allocateResource.put("user_ID", 1);

    JSONObject otherAllocations = new JSONObject();
    otherAllocations.put("assignee_Name", "BathiyaT");
    allocateResource.put("otherAllocations", otherAllocations);

    array.add(allocateResource);

    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);
    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(7);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);
    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 3))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenThrow(new ConflictException("already allocated"));
  }

  /**
   * Resource allocation for existing one test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationForExistingOneTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject obj = new JSONObject();
    obj.put("allocation_ID", 1);
    obj.put("allocationtype_ID", 3);
    obj.put("resource_ID", 7);
    obj.put("status_ID", 2);
    obj.put("user_ID", 1);

    JSONObject otherAllocations = new JSONObject();
    otherAllocations.put("assignee_Name", "AmaliK");

    obj.put("otherAllocations", otherAllocations);
    array.add(obj);
    status = new Status();
    status.setStatusID((byte) 2);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 2))
        .thenReturn(statusToReturnFromRepository);
    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(7);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);
    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 3))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenReturn(serviceResponse);
  }

  /**
   * Resource allocation failure test access denied.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationFailureTestAccessDenied() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject allocateResource = new JSONObject();
    allocateResource.put("allocationtype_ID", 3);
    allocateResource.put("resource_ID", 7);
    allocateResource.put("status_ID", 2);
    allocateResource.put("user_ID", 1);

    JSONObject otherAllocations = new JSONObject();
    otherAllocations.put("assignee_Name", "");
    allocateResource.put("otherAllocations", otherAllocations);

    array.add(allocateResource);
    System.out.println(array.toString());
    status = new Status();
    status.setStatusID((byte) 2);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 2))
        .thenReturn(statusToReturnFromRepository);
    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(7);

    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);
    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();

    allocationType.setAllocationTypeID((byte) 3);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 3))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceServiceImpl.resourceAllocation(array.toJSONString()))
        .thenThrow(new AccessDeniedException("access denied"));
  }

  /**
   * Resource allocation failure test by invalid id.
   *
   * @throws Exception the exception
   */

  @Test(expected = JSONException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void resourceAllocationFailureTestByInvalidId() throws Exception {

    JSONObject obj = new JSONObject();
    obj.put("allocationtype_ID", 3);
    obj.put("resource_ID", 123456);
    obj.put("status_ID", 1);
    obj.put("user_ID", 1);

    JSONObject otherAllocations = new JSONObject();
    otherAllocations.put("assignee_Name", "Nikhil");
    obj.put("otherAllocations", otherAllocations);

    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);
    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);
    Integer operationId = 4;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(7);

    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);
    Set<Allocation> allocationValueList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationValueList.add(allocation);


    Optional<Allocation> allocationToReturnFromRepository =
        Optional.of(allocation);
    when(allocationRepo.findByAllocationID(1))
        .thenReturn(allocationToReturnFromRepository);

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Optional<AllocationType> allocationTypeToReturnFromRepository =
        Optional.of(allocationType);
    when(allocationTypeRepo.findByAllocationTypeID((byte) 3))
        .thenReturn(allocationTypeToReturnFromRepository);

    allocation.setAllocationType(allocationType);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceServiceImpl.resourceAllocation(obj.toJSONString()))
        .thenThrow(new JSONException("bad request"));

  }

  /**
   * Delete resource test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void deleteResourceTest() throws Exception {
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    status = new Status();
    status.setStatusID((byte) 0);
    resource.setStatus(status);
    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);
    List<Allocation> allocationList = new ArrayList<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setAllocationType(allocationType);
    allocationList.add(allocation);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(allocationRepo.findAllocationListByResource(1))
        .thenReturn(allocationList);
    when(permissionService.permissionExists(category.getCategory_ID(), 6))
        .thenReturn(true);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(resourceToReturnFromRepository);
    resourceServiceImpl.deleteResource(1);
    verify(resourceRepo, times(1)).deleteById(1);
  }

  /**
   * Delete resource failure test.
   *
   * @throws Exception the exception
   */

  @Test(expected = DataNotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void deleteResourceFailureTest() throws Exception {
    resource = new Resource();
    resource.setResourceID(1);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(resourceToReturnFromRepository);
    resourceServiceImpl.deleteResource(2);
  }

  /**
   * Delete resource access denied test.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void deleteResourceAccessDeniedTest() throws Exception {
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(permissionService.permissionExists(category.getCategory_ID(), 6))
        .thenReturn(false);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(resourceToReturnFromRepository);
    resourceServiceImpl.deleteResource(resource.getResource_ID());

  }

  /**
   * Delete resource by allocated resource test.
   *
   * @throws Exception the exception
   */

  @Test(expected = ConflictException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void deleteResourceByAllocatedResourceTest() throws Exception {
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    status = new Status();
    status.setStatusID((byte) 1);
    resource.setStatus(status);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(permissionService.permissionExists(category.getCategory_ID(), 6))
        .thenReturn(true);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(resourceToReturnFromRepository);
    resourceServiceImpl.deleteResource(1);
  }

  /**
   * Gets the resource test.
   *
   * @return the resource test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getResourceTest() throws Exception {
    Integer operationId = 1;

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);


    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);

    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    Resource resourceResponse =
        resourceServiceImpl.getResource(resource.getResource_ID());
    assertThat(resource.getResource_ID(),
        is(resourceResponse.getResource_ID()));

  }


  /**
   * Gets the resource failure test.
   *
   * @return the resource failure test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getResourceFailureTest() throws Exception {

    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.empty());

    when(resourceServiceImpl.getResource(resource.getResource_ID()));

  }

  /**
   * Gets the resource failure test by access denied.
   *
   * @return the resource failure test by access denied
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getResourceFailureTestByAccessDenied() throws Exception {

    Integer operationId = 1;

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);


    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resourceList.add(resource);

    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceServiceImpl.getResource(resource.getResource_ID()));
  }

  /**
   * Restore resources test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void restoreResourcesTest() throws Exception {
    Integer operationId = 5;
    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    category = new Category();
    category.setCategoryID(1);

    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    resource = new Resource();
    resource.setResourceID(7);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");

    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);

    when(resourceBinRepo.findByResourceID(1))
        .thenReturn(resourcebinToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<ResourcebinAttribute> resourceAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("mobile");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttribute.setResourcebin(resourceBin);
    resourceAttributeList.add(resourceBinAttribute);

    resourceBin.setResourcebinAttributes(resourceAttributeList);

    resourceBinRepo.deleteById(resourceBin.getResource_ID());
    when(resourceRepo.save(resource)).thenReturn(resource);
    when(resourceAttributeRepo.save(resourceAttribute))
        .thenReturn(resourceAttribute);
    resourceServiceImpl.restoreResources(resourcebinList);

  }

  /**
   * Restore resources failure test.
   *
   * @throws Exception the exception
   */

  @Test(expected = Exception.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void restoreResourcesFailureTest() throws Exception {
    Integer operationId = 5;
    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    category = new Category();
    category.setCategoryID(1);

    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    resource = new Resource();
    resource.setResourceID(7);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");

    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);

    when(resourceBinRepo.findByResourceID(1234))
        .thenReturn(resourcebinToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<ResourcebinAttribute> resourceAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("mobile");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttribute.setResourcebin(resourceBin);
    resourceAttributeList.add(resourceBinAttribute);

    resourceBin.setResourcebinAttributes(resourceAttributeList);

    resourceBinRepo.deleteById(resourceBin.getResource_ID());
    when(resourceRepo.save(resource)).thenReturn(resource);
    when(resourceAttributeRepo.save(resourceAttribute))
        .thenReturn(resourceAttribute);
    resourceServiceImpl.restoreResources(resourcebinList);

  }

  /**
   * Restore resources access denied test.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void restoreResourcesAccessDeniedTest() throws Exception {
    Integer operationId = 5;
    status = new Status();
    status.setStatusID((byte) 1);

    Optional<Status> statusToReturnFromRepository = Optional.of(status);
    when(statusRepo.findBystatusID((byte) 1))
        .thenReturn(statusToReturnFromRepository);

    category = new Category();
    category.setCategoryID(1);

    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);

    Optional<User> userToReturnFromRepository = Optional.of(user);
    when(userRepo.findByUserID(1)).thenReturn(userToReturnFromRepository);

    resource = new Resource();
    resource.setResourceID(7);
    resource.setCategory(category);
    resource.setStatus(status);
    resource.setUser(user);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findByResourceID(7))
        .thenReturn(resourceToReturnFromRepository);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");

    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);

    when(resourceBinRepo.findByResourceID(1))
        .thenReturn(resourcebinToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);

    List<ResourcebinAttribute> resourceAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("mobile");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttribute.setResourcebin(resourceBin);
    resourceAttributeList.add(resourceBinAttribute);

    resourceBin.setResourcebinAttributes(resourceAttributeList);

    resourceBinRepo.deleteById(resourceBin.getResource_ID());
    when(resourceRepo.save(resource)).thenReturn(resource);
    when(resourceAttributeRepo.save(resourceAttribute))
        .thenReturn(resourceAttribute);
    resourceServiceImpl.restoreResources(resourcebinList);

  }

  /**
   * Gets the resourcesby resource id list test.
   *
   * @return the resourcesby resource id list test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getResourcesbyResourceIdListTest() throws Exception {
    Integer operationId = 1;
    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationList.add(allocation);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);
    resourceList.add(resource);

    List<Integer> resourceObjList = new ArrayList<>();
    resourceObjList.add(1);
    List<Integer> resourceIdList = new ArrayList<>(resourceObjList);

    when(resourceRepo.findByResourceID(1)).thenReturn(Optional.of(resource));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceRepo.findResourcesByResourceIdList(resourceIdList))
        .thenReturn(resourceList);
    when(resourceServiceImpl.getResourcesbyResourceIdList(resourceObjList))
        .thenReturn(resourceList);
  }


  /**
   * Gets the resourcesby resource id list failure test.
   *
   * @return the resourcesby resource id list failure test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getResourcesbyResourceIdListFailureTest() throws Exception {
    Integer operationId = 1;
    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 3);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocationList.add(allocation);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);

    List<Integer> resourceObjList = new ArrayList<>();
    resourceObjList.add(1);
    List<Integer> resourceIdList = new ArrayList<>(resourceObjList);

    when(resourceRepo.findByResourceID(1)).thenReturn(Optional.of(resource));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceRepo.findResourcesByResourceIdList(resourceIdList))
        .thenReturn(resourceList);

    when(resourceServiceImpl.getResourcesbyResourceIdList(resourceObjList));
  }


  /**
   * Gets the all resources by category id test.
   *
   * @return the all resources by category id test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllResourcesByCategoryIdTest() throws Exception {

    Integer operationId = 1;

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resourceList.add(resource);


    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    when(
        resourceServiceImpl.getResourcesByCategoryId(category.getCategory_ID()))
            .thenReturn(resourceList);
  }

  /**
   * Gets the all resources by category id failure test.
   *
   * @return the all resources by category id failure test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllResourcesByCategoryIdFailureTest() throws Exception {

    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.empty());

    when(resourceServiceImpl
        .getResourcesByCategoryId(category.getCategory_ID()));

  }

  /**
   * Gets the all resources by category id failure test access denied.
   *
   * @return the all resources by category id failure test access denied
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getAllResourcesByCategoryIdFailureTestAccessDenied()
      throws Exception {

    Integer operationId = 1;

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resourceList.add(resource);


    when(categoryRepo.findByCategoryID(category.getCategory_ID()))
        .thenReturn(Optional.of(category));

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceRepo.findResourceByCategory(category))
        .thenReturn(resourceList);

    when(resourceServiceImpl
        .getResourcesByCategoryId(category.getCategory_ID()));

  }

  /**
   * Dispose resources test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void disposeResourcesTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject resourceData = new JSONObject();
    resourceData.put("resource_ID", 1);
    resourceData.put("reason", "not required");
    array.add(resourceData);
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    status = new Status();
    status.setStatusID((byte) 0);
    user = new User();
    user.setUserID(1);
    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("Samsung");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);
    resource.setStatus(status);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);

    ResourcebinAttribute resourceBinAttributeObj = new ResourcebinAttribute();
    resourceBinAttributeObj.setResourceAttributeID(1);
    resourceBinAttributeObj.setAttribute(attribute);
    resourceBinAttributeObj.setValue("Samsung");

    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);
    List<Allocation> allocationList = new ArrayList<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setAllocationType(allocationType);
    allocationList.add(allocation);

    when(allocationRepo.findAllocationListByResource(1))
        .thenReturn(allocationList);
    when(permissionService.permissionExists(category.getCategory_ID(), 5))
        .thenReturn(true);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(resourceToReturnFromRepository);
    when(resourceBinAttributeRepo.save(any(ResourcebinAttribute.class)))
        .thenReturn(resourceBinAttributeObj);
    resourceServiceImpl.disposeResources(array.toJSONString());
    verify(resourceRepo, times(1)).deleteById(1);
    verify(resourceBinAttributeRepo, times(1))
        .save(any(ResourcebinAttribute.class));
  }

  /**
   * Dispose resources failure test.
   *
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void disposeResourcesFailureTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject resourceData = new JSONObject();
    resourceData.put("resource_ID", 1);
    resourceData.put("reason", "not required");
    array.add(resourceData);
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    status = new Status();
    status.setStatusID((byte) 0);
    resource.setCategory(category);
    when(permissionService.permissionExists(category.getCategory_ID(), 5))
        .thenReturn(false);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(Optional.of(resource));
    resourceServiceImpl.disposeResources(array.toJSONString());
  }

  /**
   * Dispose resources by allocate resource test.
   *
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void disposeResourcesByAllocateResourceTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject resourceData = new JSONObject();
    resourceData.put("resource_ID", 1);
    resourceData.put("reason", "not required");
    array.add(resourceData);
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("CAT/LAP");
    Allocation allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setFromDate(new Date());
    List<String> resourceAllocationList = new ArrayList<>();
    resourceAllocationList.add(resource.getCode());
    when(resourceService.disposeResources(array.toJSONString()))
        .thenReturn(resourceAllocationList);
    ServiceResponse disposeResponse =
        resourceController.disposeResources(array.toJSONString());
    assertThat(disposeResponse.getStatus(), is(409));
  }

  /**
   * Dispose resources failure by reason length test.
   *
   * @throws Exception the exception
   */

  @Test(expected = DataToLongException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void disposeResourcesFailureByReasonLengthTest() throws Exception {

    JSONArray array = new JSONArray();
    JSONObject resourceData = new JSONObject();
    resourceData.put("resource_ID", 1);
    resourceData.put("reason",
        "asdjfhdhfshdfsdfhjsdh jfhsjdhfjh sdbfsdjfhjsdhr sdbfsdbfsbdf bfsdhfhf dfhdfhdfh sdgsdghdgjhfg"
            + "dfghdfhghhfghdfhgdhfghdfhghfgfhghghfghfhgfhghfhgfhgfhghfghfhgfhghfghgfgfd fgdfgfgdf gfdgdfgf"
            + "gdgg sdfgdfgdf fdgdfgfgdf gfgdffgd dfgdfgdfd gdfgdfgdf dfgdfgdfg dfgdfg gdgdfgdfgdgdgdfgdf");
    array.add(resourceData);
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    status = new Status();
    status.setStatusID((byte) 0);
    resource.setCategory(category);
    when(permissionService.permissionExists(category.getCategory_ID(), 5))
        .thenReturn(false);
    when(resourceRepo.findByResourceID((short) 1))
        .thenReturn(Optional.of(resource));
    resourceServiceImpl.disposeResources(array.toJSONString());
  }


  /**
   * Gets the last resource by category test.
   *
   * @return the last resource by category test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getLastResourceByCategoryTest() throws Exception {
    int categoryID = 1;
    category = new Category();
    category.setCategoryID(1);
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("mobile");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setResourceAttributes(resourceAttributeList);

    Optional<Resource> resourceToReturnFromRepository = Optional.of(resource);
    when(resourceRepo.findLastResourceByCategoryId(categoryID))
        .thenReturn(resourceToReturnFromRepository);
    resourceServiceImpl.getLastResourceByCategory(categoryID);

  }

  /**
   * Gets the last resource by category failure test.
   *
   * @return the last resource by category failure test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getLastResourceByCategoryFailureTest() throws Exception {
    int categoryID = 00;

    when(resourceRepo.findLastResourceByCategoryId(categoryID))
        .thenThrow(new NotFoundException("category not exists"));
    resourceServiceImpl.getLastResourceByCategory(categoryID);
  }

  /**
   * Gets the disposed resources by category id test.
   *
   * @return the disposed resources by category id test
   * @throws Exception the exception
   */

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesByCategoryIdTest() throws Exception {
    int categoryID = 1;
    Integer operationId = 5;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);


    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourcebinList);
    when(resourceServiceImpl.getDisposedResourcesByCategory(categoryID))
        .thenReturn(resourcebinList);


  }

  /**
   * Gets the disposed resources by category id failure test.
   *
   * @return the disposed resources by category id failure test
   * @throws Exception the exception
   */

  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesByCategoryIdFailureTest() throws Exception {
    int categoryID = 000;
    Integer operationId = 5;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);


    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourcebinList);
    when(resourceServiceImpl.getDisposedResourcesByCategory(categoryID))
        .thenThrow(new NotFoundException("category does not exists"));

  }

  /**
   * Gets the disposed resources by category id test access denied.
   *
   * @return the disposed resources by category id test access denied
   * @throws Exception the exception
   */

  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesByCategoryIdTestAccessDenied()
      throws Exception {
    int categoryID = 1;
    Integer operationId = 5;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    Optional<Category> categoryToReturnFromRepository = Optional.of(category);
    when(categoryRepo.findByCategoryID(1))
        .thenReturn(categoryToReturnFromRepository);
    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);


    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourcebinList);
    when(resourceServiceImpl.getDisposedResourcesByCategory(categoryID))
        .thenThrow(new AccessDeniedException("permission not exists"));
  }

  @Test

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourceTest() throws Exception {
    int resourceId = 1;
    Integer operationId = 6;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);
    when(permissionService.permissionExists(
        resourceBin.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceBinRepo.findByResourceID(resourceId))
        .thenReturn(resourcebinToReturnFromRepository);
    resourceServiceImpl.getDisposedResource(resourceBin.getResource_ID());

  }

  /**
   * Junit Failure test case for getDisposedResource for Not Found.
   *
   * @return the disposed resource failure test
   * @throws Exception the exception
   */


  @Test(expected = NotFoundException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourceFailureTest() throws Exception {

    int resourceId = 0;
    Integer operationId = 6;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);
    when(permissionService.permissionExists(
        resourceBin.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);
    when(resourceBinRepo.findByResourceID(resourceId))
        .thenReturn(resourcebinToReturnFromRepository);
    when(resourceServiceImpl.getDisposedResource(resourceBin.getResource_ID()))
        .thenThrow(new NotFoundException("id does not exists"));
  }



  /**
   * Junit Failure test case for getDisposedResource for Access Denied.
   *
   * @return the disposed resource failure test access denied
   * @throws Exception the exception
   */
  @Test(expected = AccessDeniedException.class)

  @Transactional

  @WithMockUser(username = username, password = password)
  public void getDisposedResourceFailureTestAccessDenied() throws Exception {
    int resourceId = 1;
    Integer operationId = 6;
    category = new Category();
    category.setCategoryID(1);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);

    List<Resourcebin> resourcebinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(user.getUser_ID());
    resourceBin.setFKStatusID(status.getStatus_ID());
    resourceBin.setDisposeReason("not in use");
    resourcebinList.add(resourceBin);

    Optional<Resourcebin> resourcebinToReturnFromRepository =
        Optional.of(resourceBin);
    when(permissionService.permissionExists(
        resourceBin.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);
    when(resourceBinRepo.findByResourceID(resourceId))
        .thenReturn(resourcebinToReturnFromRepository);
    when(resourceServiceImpl.getDisposedResource(resourceBin.getResource_ID()))
        .thenThrow(new AccessDeniedException("permission does not exists"));

  }


  @Test

  @WithMockUser(username = username, password = password)
  public void getResourcesBySearchTermDateCaseTest() throws Exception {

    int page = 0;
    int size = 1;
    String direction = "ASC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attrDataType = new AttrDataType();
    attrDataType.setDataTypeID(5);


    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    attribute.setAttrDataType(attrDataType);
    Optional<Attribute> attributeToReturnRepo = Optional.of(attribute);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 1);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);

    allocationType1 = new AllocationType();
    allocationType1.setAllocationTypeID((byte) 2);

    employee = new Employee();
    employee.setUserName("BathiyaT");

    employeeAllocation = new EmployeeAllocation();
    employeeAllocation.setEmployee(employee);

    project = new Project();
    project.setProjectName("zissa");

    projectAllocation = new ProjectAllocation();
    projectAllocation.setProject(project);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocation.setEmployeeAllocations(employeeAllocation);
    allocationList.add(allocation);
    Set<Allocation> allocationList1 = new HashSet<>();
    allocation1 = new Allocation();
    allocation1 = new Allocation();
    allocation1.setAllocationType(allocationType1);
    allocation1.setResource(resource1);
    allocation1.setStatus(status);
    allocation1.setUser(user);
    allocation1.setProjectAllocations(projectAllocation);
    allocationList1.add(allocation1);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("23/07/2019");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);
    resourceList.add(resource);


    resource1 = new Resource();
    resource1.setResourceID(1);
    resource1.setCode("RES/0002");
    resource1.setStatus(status);
    resource1.setCategory(category);
    resource1.setUser(user);
    resource1.setResourceAttributes(resourceAttributeList);
    resource1.setAllocations(allocationList1);
    resourceList.add(resource1);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(attributeToReturnRepo);

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    resourceServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());
  }

  @Test

  @WithMockUser(username = username, password = password)
  public void getResourcesBySearchTermDescDateCaseTest() throws Exception {

    int page = 0;
    int size = 1;
    String direction = "DESC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attrDataType = new AttrDataType();
    attrDataType.setDataTypeID(5);

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    attribute.setAttrDataType(attrDataType);
    Optional<Attribute> attributeToReturnRepo = Optional.of(attribute);



    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 1);
    status.setStatusName("Available");


    allocationType = new AllocationType();
    allocationType.setAllocationTypeID((byte) 1);

    allocationType1 = new AllocationType();
    allocationType1.setAllocationTypeID((byte) 2);

    employee = new Employee();
    employee.setUserName("BathiyaT");

    employeeAllocation = new EmployeeAllocation();
    employeeAllocation.setEmployee(employee);

    project = new Project();
    project.setProjectName("zissa");

    projectAllocation = new ProjectAllocation();
    projectAllocation.setProject(project);

    Set<Allocation> allocationList = new HashSet<>();
    allocation = new Allocation();
    allocation.setAllocationType(allocationType);
    allocation.setResource(resource);
    allocation.setStatus(status);
    allocation.setUser(user);
    allocation.setEmployeeAllocations(employeeAllocation);
    allocationList.add(allocation);

    Set<Allocation> allocationList1 = new HashSet<>();
    allocation1 = new Allocation();
    allocation1 = new Allocation();
    allocation1.setAllocationType(allocationType1);
    allocation1.setResource(resource1);
    allocation1.setStatus(status);
    allocation1.setUser(user);
    allocation1.setProjectAllocations(projectAllocation);
    allocationList1.add(allocation1);

    List<ResourceAttribute> resourceAttributeList = new ArrayList<>();
    resourceAttribute = new ResourceAttribute();
    resourceAttribute.setResourceAttributeID(1);
    resourceAttribute.setValue("23/07/2019");
    resourceAttribute.setAttribute(attribute);
    resourceAttributeList.add(resourceAttribute);

    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("RES/0001");
    resource.setStatus(status);
    resource.setCategory(category);
    resource.setUser(user);
    resource.setResourceAttributes(resourceAttributeList);
    resource.setAllocations(allocationList);
    resourceList.add(resource);

    resource1 = new Resource();
    resource1.setResourceID(1);
    resource1.setCode("RES/0002");
    resource1.setStatus(status);
    resource1.setCategory(category);
    resource1.setUser(user);
    resource1.setResourceAttributes(resourceAttributeList);
    resource1.setAllocations(allocationList1);
    resourceList.add(resource1);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(attributeToReturnRepo);

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    resourceServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesBySearchTermDescTest() throws Exception {

    int page = 0;
    int size = 1;
    int pageSize = size;
    String direction = "DESC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);


    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);



    role = new Role();
    role.setRoleID(1);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    // allocationType = new AllocationType();
    // allocationType.setAllocationTypeID((byte) 3);
    //
    // Set<Allocation> allocationList = new HashSet<>();
    // allocation = new Allocation();
    // allocation.setAllocationType(allocationType);
    // allocation.setResource(resource);
    // allocation.setStatus(status);
    // allocation.setUser(user);
    // allocationList.add(allocation);

    List<ResourcebinAttribute> resourceBinAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);

    resourceBinAttribute.setValue("Samsung");
    // resourceBinAttribute.setAttribute(attribute);
    resourceBinAttributeList.add(resourceBinAttribute);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCode("RES/0002");
    resourceBin.setDisposeReason("Not Required");
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(1);
    resourceBin.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin);



    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    // when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
    // .thenReturn(Optional.of(attribute));

    when(resourceBinRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceBinList);
     when(resourceBinRepo.findResourcesByCategoryOrderByReasonDesc(categoryId))
     .thenReturn(resourceBinList);
    when(resourceBinRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceBinList);
     when(resourceBinRepo.findResourcesByCategoryOrderByReasonAsc(categoryId))
     .thenReturn(resourceBinList);
    resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryId, page,
        size, searchText, direction, attribute.getAttribute_ID());



  }

  @Test
  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesBySearchTermDateCaseSortAscTest()
      throws Exception {
    int page = 0;
    int size = 1;
    String direction = "ASC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attrDataType = new AttrDataType();
    attrDataType.setDataTypeID(5);
    attrDataType.setDataTypeName("Date");

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    attribute.setAttrDataType(attrDataType);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");

    List<ResourcebinAttribute> resourceBinAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("24/07/2019");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttributeList.add(resourceBinAttribute);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCode("RES/0003");
    resourceBin.setDisposeReason("Not Required");
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(1);
    resourceBin.setFKStatusID((byte) 0);
    resourceBin.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin);

    resourceBin1 = new Resourcebin();
    resourceBin1.setResourceID(1);
    resourceBin1.setCode("RES/0003");
    resourceBin1.setDisposeReason("Not Required");
    resourceBin1.setCategory(category);
    resourceBin1.setFKCreateUserID(1);
    resourceBin1.setFKStatusID((byte) 0);
    resourceBin1.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin1);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);


    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(resourceBinRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceBinList);

    resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryId, page,
        size, searchText, direction, attribute.getAttribute_ID());


  }

  @Test
  @WithMockUser(username = username, password = password)
  public void getDisposedResourcesBySearchTermDateCaseSortDescscTest()
      throws Exception {
    int page = 0;
    int size = 1;
    String direction = "DESC";
    String searchText = "Samsung";

    Set<CategoryAttribute> categoryAttributeList = new HashSet<>();
    categoryAttribute = new CategoryAttribute();
    categoryAttribute.setCategoryAttributeID(1);
    categoryAttribute.setCategory(category);
    categoryAttributeList.add(categoryAttribute);

    category = new Category();
    category.setCategoryID(1);
    category.setCategoryAttributes(categoryAttributeList);

    Set<AttributeValue> attributeValueList = new HashSet<>();
    attributeValue = new AttributeValue();
    attributeValue.setValue("4 inch");
    attributeValueList.add(attributeValue);

    attrDataType = new AttrDataType();
    attrDataType.setDataTypeID(5);
    attrDataType.setDataTypeName("Date");

    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    attribute.setAttributeValues(attributeValueList);
    attribute.setAttrDataType(attrDataType);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    status = new Status();
    status.setStatusID((byte) 0);
    status.setStatusName("Available");

    List<ResourcebinAttribute> resourceBinAttributeList = new ArrayList<>();
    resourceBinAttribute = new ResourcebinAttribute();
    resourceBinAttribute.setResourceAttributeID(1);
    resourceBinAttribute.setValue("24/07/2019");
    resourceBinAttribute.setAttribute(attribute);
    resourceBinAttributeList.add(resourceBinAttribute);

    List<Resourcebin> resourceBinList = new ArrayList<>();
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCode("RES/0003");
    resourceBin.setDisposeReason("Not Required");
    resourceBin.setCategory(category);
    resourceBin.setFKCreateUserID(1);
    resourceBin.setFKStatusID((byte) 0);
    resourceBin.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin);

    resourceBin1 = new Resourcebin();
    resourceBin1.setResourceID(1);
    resourceBin1.setCode("RES/0003");
    resourceBin1.setDisposeReason("Not Required");
    resourceBin1.setCategory(category);
    resourceBin1.setFKCreateUserID(1);
    resourceBin1.setFKStatusID((byte) 0);
    resourceBin1.setResourcebinAttributes(resourceBinAttributeList);
    resourceBinList.add(resourceBin1);


    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);


    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(resourceBinRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceBinList);

    when(resourceBinRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceBinList);

    resourceServiceImpl.getAllDisposedResourcesBySearchTerm(categoryId, page,
        size, searchText, direction, attribute.getAttribute_ID());


  }


}
