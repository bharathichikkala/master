package com.zone.zissa.controller;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.AllocationService;
import com.zone.zissa.service.impl.ResourceServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** The AllocationMgmtController class. */
@RestController
@RequestMapping("/v1/allocations")
@Api(value = "zissa", tags = {"Allocation-mgmt-controller"})
public class AllocationMgmtController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(AllocationMgmtController.class);

  /** The allocation service impl. */
  @Autowired
  private AllocationService allocationServiceImpl;

  /** The redmine auth token. */
  @Value("${redmine-auth-token}")
  private String redmineAuthToken;

  /** The redmine url. */
  @Value("${redmine-url}")
  private String redmineUrl;

  /** The resource service. */
  @Autowired
  private ResourceServiceImpl resourceService;


  /**
   * Get all allocation details for a resource by resourceid controller.
   *
   * @param resourceID the resource ID
   * @return the all allocation details by resource
   */
  @ApiOperation(value = "View allocation details by resourceid",
      notes = "Return allocation details", response = Allocation.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.NO_CONTENT,
              response = ServiceResponse.class, message = "No Content"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.NOT_FOUND,
              message = "Not Found")})
  @GetMapping("/{resourceID}")
  public ServiceResponse<List<Allocation>> getAllAllocationDetailsByResource(
      @ApiParam(
          value = "resourceid of resource for which "
              + "you need allocation details",
          required = true) @PathVariable final Integer resourceID) {
    LOGGER.debug(
        "Get all Allocation Details by Resource Controller implementation");
    ServiceResponse<List<Allocation>> response = new ServiceResponse<>();

    List<Allocation> allocationList =
        allocationServiceImpl.getAllAllocationDetailsByResource(resourceID);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setMessage(RestApiMessageConstants.GET_ALL_ALLOCATIONS);
    response.setData(allocationList);
    return response;
  }

  /**
   * get Resources By SearchTerm controller.
   *
   * @param categoryID the category ID
   * @param page the page
   * @param size the size
   * @param searchText the search text
   * @param direction the direction
   * @param attrId the attr id
   * @return the resources by search term
   */
  @ApiOperation(
      value = "View list of resource allocation details by categoryid"
          + ",page,size,searchText and direction",
      notes = "Return allocation details", response = Resource.class,
      responseContainer = "List", httpMethod = "GET",
      authorizations = {@Authorization(value = "basicAuth")})
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden")})
  @GetMapping
  public PageServiceResponse<Object> getResourcesBySearchTerm(
      @RequestParam("category_ID") final List<Integer> categoryID,
      @RequestParam("page") final int page,
      @RequestParam("size") final int size,
      @RequestParam("searchText") final String searchText,
      @RequestParam("direction") final String direction,
      @RequestParam(value = "attrid", required = false) final short attrId) {
    LOGGER.debug("Get all Resources allocation details by Searchterm "
        + "Controller implementation");
    PageServiceResponse<Object> response = new PageServiceResponse<>();

    Object resourceSearchList =
        allocationServiceImpl.getAllResourcesBySearchTerm(categoryID, page,
            size, searchText, direction, attrId);
    response.setStatus(HttpServletResponse.SC_OK);
    response.setData(resourceSearchList);
    response.setMessage(RestApiMessageConstants.GET_RESOURCE_WITH_SEARCHTERM);
    response.setTotalRecords(resourceService.getCount());
    resourceService.setCount(0);
    return response;
  }

  /**
   * Get all Projects controller.
   *
   * @return the all projects
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @ApiOperation(value = "View list of projects", notes = "Return all projects",
      response = String.class, httpMethod = "GET")
  @ApiResponses(
      value = {@ApiResponse(code = RestApiMessageConstants.OK, message = "OK"),
          @ApiResponse(code = RestApiMessageConstants.INTERNAL_SERVER_ERROR,
              message = "Internal Server Error"),
          @ApiResponse(code = RestApiMessageConstants.FORBIDDEN,
              message = "Forbidden")})
  @GetMapping("/projects")
  public String getAllProjects() throws IOException {
    LOGGER.debug("Get all redmine projects Controller implementation");
    byte[] encodedBytes =
        Base64.getEncoder().encode(redmineAuthToken.getBytes());
    String authorization = new String(encodedBytes);
    URL url = new URL(redmineUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoOutput(true);
    connection.setInstanceFollowRedirects(false);
    connection.setRequestMethod("GET");
    connection.setRequestProperty("Content-Type", "application/json");
    connection.setRequestProperty("Authorization", "Basic " + authorization);
    connection.connect();
    InputStream inStream = connection.getInputStream();
    String json = null;
    json = streamToString(inStream);
    return json;
  }

  /**
   * streamToString method.
   *
   * @param inputStream the input stream
   * @return String
   */
  private static String streamToString(final InputStream inputStream) {
    return new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
  }

}
