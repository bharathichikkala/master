package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.CategoryMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
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
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import net.minidev.json.JSONObject;

/**
 * The Class categorymgmtControllerTest.
 */
public class categorymgmtControllerTest extends ZissaApplicationTest {

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

  /** The category service. */
  @Mock
  private CategoryService categoryService;

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

    when(categoryService.getAllCategories()).thenReturn(categoryList);
    ServiceResponse<List<Category>> response =
        categoryMgmtController.getAllCategories();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
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
    when(categoryService.getAllOperations()).thenReturn(operationList);
    ServiceResponse<List<Operation>> response =
        categoryMgmtController.getAllOperations();
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
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
    category = new Category();
    category.setCategoryID(1);

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();

    when(categoryService
        .getCategoryAttributesByCategoryId(category.getCategory_ID()))
            .thenReturn(categoryAttrList);
    ServiceResponse<List<CategoryAttribute>> response = categoryMgmtController
        .getCategoryAttributesByCategoryId(category.getCategory_ID());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

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

    category = new Category();
    category.setCategoryID(1);

    List<Category> categoryId = new ArrayList<>();

    List<CategoryAttribute> categoryAttrList = new ArrayList<>();

    when(categoryAttributeRepo.findByCategoryIn(categoryId))
        .thenReturn(categoryAttrList);
    when(categoryService.getCategoryAttributesByCategoryIdList(categoryId))
        .thenReturn(categoryAttrList);
    ServiceResponse<List<CategoryAttribute>> response = categoryMgmtController
        .getCategoryAttributesByCategoryIdList(categoryId);

    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));
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

    List<Role> roleList = new ArrayList<>();
    role = new Role();
    role.setRoleID(1);
    when(roleRepo.findByDefaultCategory(category)).thenReturn(roleList);
    permissionRepo.deleteByCategory(category.getCategory_ID());
    categoryMgmtController.deleteCategory(category.getCategory_ID());
    verify(categoryService, times(1)).deleteCategory(category.getCategory_ID());

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

    category = new Category();
    category.setCategoryID(1);
    category.setName("Laptop");
    category.setCodePattern("CAT/DES");

    when(categoryService.addCategory(addCategoryData.toJSONString()))
        .thenReturn(category);
    ServiceResponse<Category> response =
        categoryMgmtController.addCategory(addCategoryData.toJSONString());
    assertThat(HttpServletResponse.SC_CREATED, is(response.getStatus()));
    assertThat(category.getName(), is(response.getData().getName()));

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

    category = new Category();
    category.setCategoryID(1);
    category.setName("Desktop");
    category.setCodePattern("CAT/DES");

    when(categoryService.updateCategory(updateCategoryData.toJSONString()))
        .thenReturn(category);
    ServiceResponse<Category> response = categoryMgmtController
        .updateCategory(updateCategoryData.toJSONString());
    assertThat(HttpServletResponse.SC_OK, is(response.getStatus()));

  }

}
