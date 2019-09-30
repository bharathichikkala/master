package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.exception.AccessDeniedException;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AllocationRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.service.AllocationService;
import com.zone.zissa.service.impl.AllocationServiceImpl;
import com.zone.zissa.service.impl.PermissionService;
import com.zone.zissa.service.impl.ResourceServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AllocationServiceTest.
 */
public class AllocationServiceTest extends ZissaApplicationTest {

  /** The category. */
  @InjectMocks
  private Category category;

  /** The user. */
  @InjectMocks
  private User user;

  /** The role. */
  @InjectMocks
  private Role role;

  /** The attribute. */
  @InjectMocks
  private Attribute attribute;

  /** The resource. */
  @InjectMocks
  private Resource resource;

  /** The allocation. */
  @InjectMocks
  private Allocation allocation;

  /** The permission. */
  @InjectMocks
  private Permission permission;

  /** The resource attribute. */
  @InjectMocks
  private ResourceAttribute resourceAttribute;

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

  /** The permission repo. */
  @Mock
  private PermissionRepository permissionRepo;

  /** The allocation service impl. */
  @InjectMocks
  private AllocationServiceImpl allocationServiceImpl;

  /** The permission service. */
  @Mock
  private PermissionService permissionService;

  /** The resource service. */
  @Mock
  private ResourceServiceImpl resourceService;

  /** The allocation service. */
  @Mock
  private AllocationService allocationService;

  /** The page service response. */
  @InjectMocks
  private PageServiceResponse<Object> pageServiceResponse;


  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Gets the allocation details by resource id test.
   *
   * @return the allocation details by resource id test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdTest() throws Exception {
    Integer operationId = 1;

    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);


    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);

    when(permissionRepo.findByRole(user.getRole())).thenReturn(permissionList);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<Allocation> allocationList = new ArrayList<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setResource(resource);
    allocationList.add(allocation);
    when(allocationRepo
        .findAllocationHistoryByResource(resource.getResource_ID()))
            .thenReturn(allocationList);
    List<Allocation> allocationResponse = allocationServiceImpl
        .getAllAllocationDetailsByResource(resource.getResource_ID());
    assertThat(1, is(allocationResponse.size()));


  }

  /**
   * Gets the allocation details by resource id failure test.
   *
   * @return the allocation details by resource id failure test
   * @throws Exception the exception
   */
  @Test(expected = AccessDeniedException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdFailureTest() throws Exception {
    Integer operationId = 1;

    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);


    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);

    when(permissionRepo.findByRole(user.getRole())).thenReturn(permissionList);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(false);

    List<Allocation> allocationList = new ArrayList<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setResource(resource);
    allocationList.add(allocation);
    when(allocationRepo
        .findAllocationHistoryByResource(resource.getResource_ID()))
            .thenReturn(allocationList);
    when(allocationServiceImpl
        .getAllAllocationDetailsByResource(resource.getResource_ID()));
  }

  /**
   * Gets the allocation details by resource id by not existing resource test.
   *
   * @return the allocation details by resource id by not existing resource test
   * @throws Exception the exception
   */
  @Test(expected = NotFoundException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdByNotExistingResourceTest()
      throws Exception {
    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);

    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.empty());
    when(allocationServiceImpl
        .getAllAllocationDetailsByResource(resource.getResource_ID()));

  }

  /**
   * Gets the allocations by resource id with no allocation history test.
   *
   * @return the allocations by resource id with no allocation history test
   * @throws Exception the exception
   */
  @Test(expected = NoContentException.class)
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationsByResourceIdWithNoAllocationHistoryTest()
      throws Exception {

    Integer operationId = 1;

    category = new Category();
    category.setCategoryID(1);

    role = new Role();
    role.setRoleID(1);

    user = new User();
    user.setUserID(1);
    user.setUserName("Bathiyat");
    user.setRole(role);

    resource = new Resource();
    resource.setResourceID(1);
    resource.setCategory(category);
    resource.setUser(user);


    when(resourceRepo.findByResourceID(resource.getResource_ID()))
        .thenReturn(Optional.of(resource));

    when(userRepo.findByUserName(user.getUserName()))
        .thenReturn(Optional.of(user));

    Set<Permission> permissionList = new HashSet<>();
    permission = new Permission();
    permission.setPermissionID(1);

    when(permissionRepo.findByRole(user.getRole())).thenReturn(permissionList);

    when(permissionService
        .permissionExists(resource.getCategory().getCategory_ID(), operationId))
            .thenReturn(true);

    List<Allocation> allocationList = new ArrayList<>();
    when(allocationRepo
        .findAllocationHistoryByResource(resource.getResource_ID()))
            .thenReturn(allocationList);

    when(allocationServiceImpl
        .getAllAllocationDetailsByResource(resource.getResource_ID()));
  }



  /**
   * Gets the all resources with allocation details by search term test.
   *
   * @return the all resources with allocation details by search term test
   * @throws Exception the exception
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void getAllResourcesWithAllocationDetailsBySearchTermTest()
      throws Exception {
    String direction = "ASC";
    int page = 0;
    int size = 1;
    String searchText = "s";
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

    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.of(attribute));

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    allocationServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());

  }

  /**
   * Gets the all resources by search term test.
   *
   * @return the all resources by search term test
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void getAllResourcesBySearchTermTest() {

    String direction = "DESC";
    int page = 0;
    int size = 1;
    String searchText = "s";

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

    List<Integer> categoryId = new ArrayList<>();
    categoryId.add(1);

    when(attributeRepo.findByAttributeID(attribute.getAttribute_ID()))
        .thenReturn(Optional.empty());

    when(resourceRepo.findResourcesByCategory(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryDesc(categoryId))
        .thenReturn(resourceList);

    when(resourceRepo.findResourcesByCategoryAsc(categoryId))
        .thenReturn(resourceList);

    allocationServiceImpl.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attribute.getAttribute_ID());

  }


}
