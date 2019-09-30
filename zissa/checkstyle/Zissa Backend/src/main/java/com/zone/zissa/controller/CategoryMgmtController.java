package com.zone.zissa.controller;

import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** The CategoryMgmtController Class. */
@RestController
@RequestMapping("/v1/categories")
@Api(value = "zissa", tags = {"Category-mgmt-controller"})
public class CategoryMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(CategoryMgmtController.class);

  /** The category service impl. */
  @Autowired
  private CategoryService categoryServiceImpl;

  /** The category repo. */
  @Autowired
  private CategoryRepository categoryRepo;

  /**
   * Add category controller.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "categoryData", value = "category Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Add Category",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Category.class, httpMethod = "POST",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(value = {
      @ApiResponse(code = RestApiMessageConstants.CREATED, message = "Created"),
      @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
          message = "BadRequest"),
      @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
          message = "Forbidden"),
      @ApiResponse(code = RestApiMessageConstants.CONFLICT,
          message = "Conflict"),
      @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
          message = "Internal Server Error")})
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ServiceResponse<Category> addCategory(
      @RequestBody final String categoryData) throws JSONException {
    LOGGER.debug("Add new Category Controller implementation");
    ServiceResponse<Category> response = new ServiceResponse<>();

    Category category = categoryServiceImpl.addCategory(categoryData);
    response.setData(category);
    response.setStatus(HttpServletResponse.SC_CREATED);
    response.setMessage(RestApiMessageConstants.ADD_CATEGORY);
    return response;
  }

  /**
   * Update category controller.
   *
   * @param categoryData the category data
   * @return Category
   * @throws JSONException the JSON exception
   */
  @ApiImplicitParams({
      @ApiImplicitParam(name = "categoryData", value = "Category Data",
          required = true, dataType = "String", paramType = "body")})
  @ApiOperation(value = "Update Category",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = Category.class, httpMethod = "PUT",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "Ok"),
          @ApiResponse(code = RestApiMessageConstants.BAD_REQUEST,
              message = "BadRequest"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @PutMapping
  public ServiceResponse<Category> updateCategory(
      @RequestBody final String categoryData) throws JSONException {
    LOGGER.debug("Update Category Controller implementation");
    ServiceResponse<Category> response = new ServiceResponse<>();
    Category category = categoryServiceImpl.updateCategory(categoryData);
    Optional<Category> categoryObj =
        categoryRepo.findByCategoryID(category.getCategory_ID());
    if (categoryObj.isPresent()) {
      response.setData(categoryObj.get());
    }
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.UPDATE_CATEGORY);

    return response;
  }

  /**
   * Get all categories controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View list of all categories",
      notes = "Return all categories", response = Category.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping
  public ServiceResponse<List<Category>> getAllCategories() {
    LOGGER.debug("Get all Categories Controller implementation");
    ServiceResponse<List<Category>> response = new ServiceResponse<>();
    List<Category> categoryList = categoryServiceImpl.getAllCategories();
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_CATEGORY);
    response.setData(categoryList);

    return response;
  }

  /**
   * Gets all the attributes of a category by categoryid controller.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @ApiOperation(value = "View attribute details by categoryID",
      notes = "Return CategoryAttribute details",
      response = CategoryAttribute.class, responseContainer = "List",
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/{categoryID}/attributes")
  public ServiceResponse<List<CategoryAttribute>> getCategoryAttributesByCategoryId(
      @ApiParam(value = "categoryID for which need categoryattribute list",
          required = true) @PathVariable final Integer categoryID) {
    LOGGER.debug("Get Attribute Details Controller implementation");
    ServiceResponse<List<CategoryAttribute>> response = new ServiceResponse<>();
    List<CategoryAttribute> categoryAttributeList =
        categoryServiceImpl.getCategoryAttributesByCategoryId(categoryID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_ATTRIBUTE);
    response.setData(categoryAttributeList);
    return response;
  }

  /**
   * Gets all the attributes by categoryid list controller.
   *
   * @param categoryID the category ID
   * @return the list
   */
  @ApiOperation(value = "View attribute details by categoryid list",
      notes = "Return CategoryAttribute details",
      response = CategoryAttribute.class, responseContainer = "List",
      httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/attributes")
  public ServiceResponse<List<CategoryAttribute>> getCategoryAttributesByCategoryIdList(
      @RequestParam("category_ID") final List<Category> categoryID) {
    LOGGER.debug(
        "Get All Attribute Details by Category List Controller implementation");
    ServiceResponse<List<CategoryAttribute>> response = new ServiceResponse<>();
    List<CategoryAttribute> categoryAttributeList =
        categoryServiceImpl.getCategoryAttributesByCategoryIdList(categoryID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_CATEGORY_LIST_ATTRIBUTE);
    response.setData(categoryAttributeList);
    return response;
  }

  /**
   * Delete Category controller.
   *
   * @param categoryID the category ID
   * @return ServiceResponse
   */
  @ApiOperation(value = "Delete Category",
      notes = "Return success response if success, "
          + "or exception if something wrong",
      response = ServiceResponse.class, httpMethod = "DELETE",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.CONFLICT,
              message = "Conflict"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @DeleteMapping("/{categoryID}")
  public ServiceResponse deleteCategory(
      @PathVariable final Integer categoryID) {
    LOGGER.debug("Delete Category Controller implementation");
    ServiceResponse response = new ServiceResponse();

    categoryServiceImpl.deleteCategory(categoryID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.DELETE_CATEGORY);

    return response;
  }

  /**
   * Get all Operations controller.
   *
   * @return the list
   */
  @ApiOperation(value = "View list of all operations",
      notes = "Return all operations", response = Operation.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error")})
  @GetMapping("/operations")
  public ServiceResponse<List<Operation>> getAllOperations() {
    LOGGER.debug("Get all operations Controller implementation");
    ServiceResponse<List<Operation>> response = new ServiceResponse<>();
    List<Operation> operationList = categoryServiceImpl.getAllOperations();
    response.setData(operationList);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_ALL_OPERATIONS);
    return response;
  }
}
