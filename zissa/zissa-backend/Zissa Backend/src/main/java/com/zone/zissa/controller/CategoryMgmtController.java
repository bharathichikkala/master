package com.zone.zissa.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.zone.zissa.exception.ConflictException;
import com.zone.zissa.exception.DataNotFoundException;
import com.zone.zissa.exception.ExceptionHandlerClass;
import com.zone.zissa.exception.NoContentException;
import com.zone.zissa.exception.NotFoundException;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.response.RestAPIMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.svcs.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * The CategoryMgmtController Class.
 */
@RestController
@RequestMapping("/v1/categories")
@Api(value = "zissa", description = "Operations pertaining to categories in zissa")
public class CategoryMgmtController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryMgmtController.class);

    @Autowired
    private CategoryService categoryServiceImpl;

    @Autowired
    private CategoryRepository categoryRepo;

    /**
     * Add category controller.
     *
     * @param categoryData
     * @return Category
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryData", value = "category Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Add Category", notes = "Return success response if success, or exception if something wrong", response = Category.class, httpMethod = "POST", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "BadRequest"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ServiceResponse<Category> addCategory(@RequestBody String categoryData) {
        LOGGER.debug("Add new Category Controller implementation");
        ServiceResponse<Category> response = new ServiceResponse<>();
        try {
            Category category = categoryServiceImpl.addCategory(categoryData);
            response.setData(category);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setMessage("Category Created Successfully");
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_CATEGORY_ERROR, ex);
            response.setMessage("Failed to add category");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in adding category");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.ADDING_CATEGORY_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Category Creation");
            response.setErrorMessage("Failed to add new Category");
        }
        return response;
    }

    /**
     * Update category controller.
     *
     * @param categoryData
     * @return Category
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryData", value = "Category Data", required = true, dataType = "String", paramType = "body") })
    @ApiOperation(value = "Update Category", notes = "Return success response if success, or exception if something wrong", response = Category.class, httpMethod = "PUT", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"), @ApiResponse(code = 400, message = "BadRequest"),
            @ApiResponse(code = 403, message = "Forbidden"), @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @PutMapping
    public ServiceResponse<Category> updateCategory(@RequestBody String categoryData) {
        LOGGER.debug("Update Category Controller implementation");
        ServiceResponse<Category> response = new ServiceResponse<>();
        try {
            Category category = categoryServiceImpl.updateCategory(categoryData);
            response.setData(categoryRepo.findByCategoryID(category.getCategory_ID()));
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Category Updated Successfully");
        } catch (DataNotFoundException ex) {
            LOGGER.error("updating category successfully", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (ConflictException ex) {
            LOGGER.error(RestAPIMessageConstants.CATEGORY_UPDATION_ERROR, ex);
            return ExceptionHandlerClass.conflictException(ex);
        } catch (JSONException | DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.CATEGORY_UPDATION_ERROR, ex);
            response.setMessage("Failed to update category");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setErrorMessage("Error in updating category");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.CATEGORY_UPDATION_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Category updation");
            response.setErrorMessage("Error in Category updation");
        }
        return response;
    }

    /**
     * Get all categories controller.
     *
     * @return List<Category>
     */
    @ApiOperation(value = "View list of all categories", notes = "Return all categories", response = Category.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping
    public ServiceResponse<List<Category>> getAllCategories() {
        LOGGER.debug("Get all Categories Controller implementation");
        ServiceResponse<List<Category>> response = new ServiceResponse<>();
        try {
            List<Category> categoryList = categoryServiceImpl.getAllCategories();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Categories is successfull");
            response.setData(categoryList);
        } catch (Exception ex) {
            LOGGER.error("Getting all Categories throw an exception", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Categories");
            response.setErrorMessage("Failed to get all Categories");
        }
        return response;
    }

    /**
     * Gets all the attributes of a category by categoryid controller.
     *
     * @param category_ID
     * @return List<CategoryAttribute>
     */
    @ApiOperation(value = "View attribute details by categoryID", notes = "Return CategoryAttribute details", response = CategoryAttribute.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/{categoryID}/attributes")
    public ServiceResponse<List<CategoryAttribute>> getCategoryAttributesByCategoryId(
            @ApiParam(value = "categoryID for which need categoryattribute list", required = true) @PathVariable Integer categoryID) {
        LOGGER.debug("Get Attribute Details Controller implementation");
        ServiceResponse<List<CategoryAttribute>> response = new ServiceResponse<>();
        try {
            List<CategoryAttribute> categoryAttributeList = categoryServiceImpl
                    .getCategoryAttributesByCategoryId(categoryID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting the Category related Attributes is successfull");
            response.setData(categoryAttributeList);
        } catch (NotFoundException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.notFoundException(ex);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR, ex);
            return ExceptionHandlerClass.noContentException(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.CATATTR_RETREIVE_FAILURE);
            response.setErrorMessage("Failed to get attribute by id");
        }
        return response;
    }

    /**
     * Gets all the attributes by categoryid list controller.
     *
     * @param category_ID
     * @return List<CategoryAttribute>
     */
    @ApiOperation(value = "View attribute details by categoryid list", notes = "Return CategoryAttribute details", response = CategoryAttribute.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, response = ServiceResponse.class, message = "No Content"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/attributes")
    public ServiceResponse<List<CategoryAttribute>> getCategoryAttributesByCategoryIdList(
            @RequestParam("category_ID") List<Category> categoryID) {
        LOGGER.debug("Get All Attribute Details by Category List Controller implementation");
        ServiceResponse<List<CategoryAttribute>> response = new ServiceResponse<>();
        try {
            List<CategoryAttribute> categoryAttributeList = categoryServiceImpl
                    .getCategoryAttributesByCategoryIdList(categoryID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Category list related Attributes is successfull");
            response.setData(categoryAttributeList);
        } catch (NoContentException ex) {
            LOGGER.error(RestAPIMessageConstants.GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR, ex);
            return ExceptionHandlerClass.noContentException(ex);
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.GET_CATEGORY_ATTRIBUTE_BY_CATEGORY_LIST_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Category List related Attributes");
            response.setErrorMessage("Error in Getting Category List related Attributes");
        }
        return response;
    }

    /**
     * Delete Category controller.
     *
     * @param category_ID
     * 
     * @return ServiceResponse
     */
    @ApiOperation(value = "Delete Category", notes = "Return success response if success, or exception if something wrong", response = ServiceResponse.class, httpMethod = "DELETE", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @DeleteMapping("/{categoryID}")
    public ServiceResponse deleteCategory(@PathVariable Integer categoryID) {
        LOGGER.debug("Delete Category Controller implementation");
        ServiceResponse response = new ServiceResponse();
        try {
            categoryServiceImpl.deleteCategory(categoryID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Category is deleted successfully");
        } catch (DataNotFoundException ex) {
            LOGGER.error("Deleting category successful", ex);
            return ExceptionHandlerClass.dataNotFoundException(ex);
        } catch (DataIntegrityViolationException ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_CATEGORY_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.setMessage(RestAPIMessageConstants.CAT_DELETION_FAILURE);
            response.setErrorMessage("Cannot delete a parent row: a foreign key constraint fails");
        } catch (Exception ex) {
            LOGGER.error(RestAPIMessageConstants.DELETE_CATEGORY_ERROR, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage(RestAPIMessageConstants.CAT_DELETION_FAILURE);
            response.setErrorMessage("Error in Deleting Category");
        }
        return response;
    }

    /**
     * Get all Operations controller.
     *
     * @return List<Operation>
     */
    @ApiOperation(value = "View list of all operations", notes = "Return all operations", response = Operation.class, responseContainer = "List", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/operations")
    public ServiceResponse<List<Operation>> getAllOperations() {
        LOGGER.debug("Get all operations Controller implementation");
        ServiceResponse<List<Operation>> response = new ServiceResponse<>();
        try {
            List<Operation> operationList = categoryServiceImpl.getAllOperations();
            response.setData(operationList);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setMessage("Getting all the Operations is successfull");
        } catch (Exception ex) {
            LOGGER.error("Getting operations list throw an exception", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting operations");
            response.setErrorMessage("Error in Getting operations");
        }
        return response;
    }

    /**
     * Get all categories with view permission for a user controller.
     *
     * @return Set<Category>
     */
    @ApiOperation(value = "View list of categories with view permissions", notes = "Return list of categories", response = Category.class, responseContainer = "Set", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/viewpermissions")
    public ServiceResponse<Set<Category>> getAllCategoriesWithViewPermissions() {
        LOGGER.debug("Get all Categories with view Controller implementation");

        ServiceResponse<Set<Category>> response = new ServiceResponse<>();
        try {
            Set<Category> categoryList = categoryServiceImpl.getAllCategoriesWithViewPermissions();
            response.setMessage("Getting all Categories with View Permission is successfull");
            response.setData(categoryList);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            LOGGER.error("Getting Categories with View Permission throw an exception", e);
            response.setMessage("Failed in Getting Categories with View Permission");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setErrorMessage("Error in Getting Categories with View Permission");

        }
        return response;
    }

    /**
     * Get all categories with add permission for a user controller.
     *
     * @return Set<Category>
     */
    @ApiOperation(value = "View list of categories with add permissions", notes = "Return list of categories", response = Category.class, responseContainer = "Set", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/addpermissions")
    public ServiceResponse<Set<Category>> getAllCategoriesWithAddPermissions() {
        LOGGER.debug("Get all Categories with view Controller implementation");

        ServiceResponse<Set<Category>> response = new ServiceResponse<>();
        try {
            Set<Category> addPermissionCategory = categoryServiceImpl.getAllCategoriesWithAddPermissions();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setData(addPermissionCategory);
            response.setMessage("Getting all Categories with Add Permission is successfull");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Categories with Add Permission");
            response.setErrorMessage("Error in Getting Categories with Add Permission");
            LOGGER.error("Getting Categories with Add Permission throw an exception", e);

        }
        return response;
    }

    /**
     * Get all categories with dispose permission for a user controller.
     *
     * @return Set<Category>
     */
    @ApiOperation(value = "View list of categories with dispose permissions", notes = "Return list of categories", response = Category.class, responseContainer = "Set", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/disposepermissions")
    public ServiceResponse<Set<Category>> getAllCategoriesWithDisposePermissions() {
        LOGGER.debug("Get all Categories with Dispose Controller implementation");

        ServiceResponse<Set<Category>> response = new ServiceResponse<>();
        try {
            Set<Category> disposePermissionCategory = categoryServiceImpl.getAllCategoriesWithDisposePermissions();
            response.setStatus(HttpServletResponse.SC_OK);
            response.setData(disposePermissionCategory);
            response.setMessage("Getting all Categories with Dispose Permission is successfull");

        } catch (Exception e) {
            LOGGER.error("Getting Categories with Dispose Permission throw an exception", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("Failed in Getting Categories with Dispose Permission");
            response.setErrorMessage("Error in Getting Categories with Dispose Permission");

        }
        return response;
    }

    /**
     * Get all categories with allocation permission for a user controller.
     *
     * @return Set<Category>
     */
    @ApiOperation(value = "View list of categories with allocate permissions", notes = "Return list of categories", response = Category.class, responseContainer = "Set", httpMethod = "GET", authorizations = {
            @Authorization(value = "basicAuth") })
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 500, message = "Internal Server Error") })
    @GetMapping("/allocatepermissions")
    public ServiceResponse<Set<Category>> getAllCategoriesWithAllocatePermissions() {
        LOGGER.debug("Get all Categories with Allocate Permissions Controller implementation");

        ServiceResponse<Set<Category>> response = new ServiceResponse<>();
        try {
            Set<Category> categoryList = categoryServiceImpl.getAllCategoriesWithAllocatePermissions();
            response.setMessage("Getting all Categories with Allocate Permission is successfull");
            response.setStatus(HttpServletResponse.SC_OK);
            response.setData(categoryList);

        } catch (Exception e) {
            LOGGER.error("Getting all Categories with Allocate Permission throw an exception", e);
            response.setMessage("Failed in Getting Categories with Allocate Permission");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setErrorMessage("Error in Getting Categories with Allocate Permission");

        }
        return response;
    }
}
