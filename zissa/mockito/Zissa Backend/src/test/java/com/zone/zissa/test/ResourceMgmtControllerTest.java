package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.ResourceMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.AllocationType;
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
import com.zone.zissa.model.Status;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AllocationTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.RoleRepository;
import com.zone.zissa.repos.StatusRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.ResourceService;
import com.zone.zissa.service.impl.PermissionService;
import com.zone.zissa.service.impl.ResourceServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * The Class ResourceMgmtControllerTest.
 */
public class ResourceMgmtControllerTest extends ZissaApplicationTest {
  /** The category. */
  @InjectMocks
  private Category category;

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

  /** The resource. */
  @InjectMocks
  private Resource resource;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The operation. */
  @InjectMocks
  private Operation operation;

  /** The role repo. */
  @Mock
  private RoleRepository roleRepo;

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The category repo. */
  @Mock
  private CategoryRepository categoryRepo;


  /** The resource service. */
  @Mock
  private ResourceService resourceService;


  /** The permission service. */
  @Mock
  private PermissionService permissionService;

  /** The resource service impl. */
  @Mock
  private ResourceServiceImpl resourceServiceImpl;

  /** The resource mgmt controller. */
  @InjectMocks
  private ResourceMgmtController resourceMgmtController;

  /** The resource bin. */
  @InjectMocks
  private Resourcebin resourceBin;

  /** The attribute. */
  @InjectMocks
  private Attribute attribute;

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

  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";

  /** The allocation. */
  @InjectMocks
  private Allocation allocation;


  /** The resource attribute. */
  @InjectMocks
  private ResourceAttribute resourceAttribute;

  /** The status. */
  @InjectMocks
  private Status status;

  /** The resource bin attribute. */
  @InjectMocks
  private ResourcebinAttribute resourceBinAttribute;


  /** The allocation type. */
  @InjectMocks
  private AllocationType allocationType;

  /** The service response. */
  @InjectMocks
  private ServiceResponse serviceResponse;

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


  /**
   * Adds the resource test.
   *
   * @throws JSONException the JSON exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void addResourceTest() throws JSONException {
    JSONObject addResourceData = new JSONObject();
    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setCode("CAT/LAP/0001");
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    resourceList.add(resource);
    when(resourceService.addResource(addResourceData.toJSONString()))
        .thenReturn(resourceList);
    ServiceResponse<List<Resource>> response =
        resourceMgmtController.addResource(addResourceData.toJSONString());
    assertThat(resource.getCode(), is(response.getData().get(0).getCode()));
    assertThat(response.getStatus(), is(201));
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
    List<Resource> resourceList = new ArrayList<>();
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("CAT/DES/0001");
    category = new Category();
    category.setCategoryID(2);
    resource.setCategory(category);
    resourceList.add(resource);
    when(resourceService.updateResource(updateResourceData.toJSONString()))
        .thenReturn(resourceList);
    ServiceResponse<List<Resource>> response = resourceMgmtController
        .updateResource(updateResourceData.toJSONString());
    assertThat(resource.getCode(), is(response.getData().get(0).getCode()));
    assertThat(response.getStatus(), is(200));
    assertThat(RestApiMessageConstants.UPDATE_RESOURCE,
        is(response.getMessage()));
  }

  /**
   * Delete resource test.
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void deleteResourceTest() {
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    ServiceResponse response =
        resourceMgmtController.deleteResource(resource.getResource_ID());
    verify(resourceService, times(1)).deleteResource(resource.getResource_ID());
    assertThat(RestApiMessageConstants.DELETE_RESOURCE,
        is(response.getMessage()));
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

    JSONObject disposeResourceData = new JSONObject();
    disposeResourceData.put("resource_ID", 1);
    disposeResourceData.put("reason", "not required");
    resource = new Resource();
    resource.setResourceID(1);
    category = new Category();
    category.setCategoryID(1);
    resource.setCategory(category);
    ServiceResponse response = resourceMgmtController
        .disposeResources(disposeResourceData.toJSONString());
    verify(resourceService, times(1))
        .disposeResources(disposeResourceData.toJSONString());
    assertThat(response.getStatus(), is(200));
    assertThat(RestApiMessageConstants.DISPOSE_RESOURCE,
        is(response.getMessage()));
  }

  /**
   * Dispose resources failure by allocated resource test.
   *
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void disposeResourcesFailureByAllocatedResourceTest()
      throws Exception {
    JSONObject disposeResourceData = new JSONObject();
    disposeResourceData.put("resource_ID", 1);
    disposeResourceData.put("reason", "not required");
    List<String> resourceAllocationList = new ArrayList<>();
    resourceAllocationList.add("CAT/LAP/0002");
    when(resourceService.disposeResources(disposeResourceData.toJSONString()))
        .thenReturn(resourceAllocationList);
    ServiceResponse response = resourceMgmtController
        .disposeResources(disposeResourceData.toJSONString());
    verify(resourceService, times(1))
        .disposeResources(disposeResourceData.toJSONString());
    assertThat(response.getStatus(), is(409));
    assertThat(RestApiMessageConstants.DISPOSE_ALLOCATED_RESOURCE,
        is(response.getMessage()));
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
    String searchText = "LAP";
    List<Resource> resourceList = new ArrayList<>();
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    resource = new Resource();
    resource.setResourceID(1);
    resource.setCode("CAT/LAP/0001");
    resourceList.add(resource);
    List<Integer> categorylist = new ArrayList<>();
    categorylist.add(1);
    categorylist.add(2);
    when(resourceServiceImpl.getCount()).thenReturn((double) 1);
    when(resourceService.getAllResourcesBySearchTerm(categorylist, page, size,
        searchText, direction, attribute.getAttribute_ID()))
            .thenReturn(resourceList);
    PageServiceResponse<Object> response =
        resourceMgmtController.getResourcesBySearchTerm(categorylist, page,
            size, searchText, direction, attribute.getAttribute_ID());
    assertThat(200, is(response.getStatus()));
    assertThat(1.0, is(response.getTotalRecords()));
    assertThat(RestApiMessageConstants.GET_RESOURCE_BY_SEARCHTERM,
        is(response.getMessage()));
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
    String direction = "ASC";
    String searchText = "LAP";
    List<Resourcebin> DisposedResourceList = new ArrayList<>();
    attribute = new Attribute();
    attribute.setAttributeID((short) 1);
    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);
    resourceBin.setCode("CAT/LAP/0002");
    DisposedResourceList.add(resourceBin);
    List<Integer> categorylist = new ArrayList<>();
    categorylist.add(1);
    categorylist.add(2);
    when(resourceServiceImpl.getCount()).thenReturn((double) 1);
    when(resourceService.getAllDisposedResourcesBySearchTerm(categorylist, page,
        size, searchText, direction, attribute.getAttribute_ID()))
            .thenReturn(DisposedResourceList);
    PageServiceResponse<Object> response =
        resourceMgmtController.getDisposedResourcesBySearchTerm(categorylist,
            page, size, searchText, direction, attribute.getAttribute_ID());
    assertThat(200, is(response.getStatus()));
    assertThat(1.0, is(response.getTotalRecords()));
    assertThat(RestApiMessageConstants.GET_DISPOSE_RESOURCE_BY_SEARCHTERM,
        is(response.getMessage()));
  }

  /**
   * Gets the disposed resource test.
   *
   * @return the disposed resource test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getDisposedResourceTest() throws Exception {

    resourceBin = new Resourcebin();
    resourceBin.setResourceID(1);

    when(resourceService.getDisposedResource(resourceBin.getResource_ID()))
        .thenReturn(resourceBin);
    ServiceResponse<Resourcebin> response = resourceMgmtController
        .getDisposedResource(resourceBin.getResource_ID());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

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
    int categoryId = 1;

    List<Resourcebin> resourcebinList = new ArrayList<>();

    when(resourceBinRepo.findResourceByCategory(category))
        .thenReturn(resourcebinList);
    when(resourceService.getDisposedResourcesByCategory(categoryId))
        .thenReturn(resourcebinList);
    ServiceResponse<List<Resourcebin>> response =
        resourceMgmtController.getDisposedResourcesByCategoryId(categoryId);
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

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

    category = new Category();
    category.setCategoryID(1);

    when(resourceService.getLastResourceByCategory(category.getCategory_ID()))
        .thenReturn(resource);
    ServiceResponse<Resource> response = resourceMgmtController
        .getLastResourceByCategory(category.getCategory_ID());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

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

    List<Resourcebin> resourcebinList = new ArrayList<>();

    resourceService.restoreResources(resourcebinList);
    ServiceResponse response =
        resourceMgmtController.restoreResources(resourcebinList);
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

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
    when(resourceService.resourceAllocation(array.toJSONString()))
        .thenReturn(serviceResponse);
    ServiceResponse response =
        resourceMgmtController.resourceAllocation(array.toJSONString());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

  }

  /**
   * Gets the all permissions by role and category test.
   *
   * @return the all permissions by role and category test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllPermissionsByRoleAndCategoryTest() {

    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    List<Permission> permissionList = new ArrayList<>();

    when(resourceServiceImpl.getAllPermissionsByRoleAndCategory(
        role.getRole_ID(), category.getCategory_ID()))
            .thenReturn(permissionList);

    ServiceResponse<List<Permission>> response =
        resourceMgmtController.getAllPermissionsByRoleAndCategory(
            role.getRole_ID(), category.getCategory_ID());

    assertThat(RestApiMessageConstants.GETTING_PERMISSIONS_BY_ROLE_CATEGORY,
        is(response.getMessage()));
  }

  /**
   * Gets the all categories with allocate permissions test.
   *
   * @return the all categories with allocate permissions test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithAllocatePermissionsTest() {
    Set<Category> categoryList = new HashSet<>();

    when(resourceServiceImpl.getAllCategoriesWithAllocatePermissions())
        .thenReturn(categoryList);

    ServiceResponse<Set<Category>> response =
        resourceMgmtController.getAllCategoriesWithAllocatePermissions();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
  }


  /**
   * Gets the all categories with dispose permissions test.
   *
   * @return the all categories with dispose permissions test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithDisposePermissionsTest() {
    Set<Category> categoryList = new HashSet<>();

    when(resourceServiceImpl.getAllCategoriesWithDisposePermissions())
        .thenReturn(categoryList);

    ServiceResponse<Set<Category>> response =
        resourceMgmtController.getAllCategoriesWithDisposePermissions();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
  }

  /**
   * Gets the all categories with add permissions test.
   *
   * @return the all categories with add permissions test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithAddPermissionsTest() {
    Set<Category> categoryList = new HashSet<>();

    when(resourceServiceImpl.getAllCategoriesWithAddPermissions())
        .thenReturn(categoryList);

    ServiceResponse<Set<Category>> response =
        resourceMgmtController.getAllCategoriesWithAddPermissions();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
  }

  /**
   * Gets the resource test.
   *
   * @return the resource test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getResourceTest() {
    resource = new Resource();
    resource.setResourceID(1);
    when(resourceService.getResource(resource.getResource_ID()))
        .thenReturn(resource);

    ServiceResponse<Resource> response =
        resourceMgmtController.getResource(resource.getResource_ID());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
  }


  /**
   * Gets the all categories with view permissions test.
   *
   * @return the all categories with view permissions test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllCategoriesWithViewPermissionsTest() {
    Set<Category> categoryList = new HashSet<>();

    when(resourceServiceImpl.getAllCategoriesWithViewPermissions())
        .thenReturn(categoryList);

    ServiceResponse<Set<Category>> response =
        resourceMgmtController.getAllCategoriesWithViewPermissions();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
  }

}
