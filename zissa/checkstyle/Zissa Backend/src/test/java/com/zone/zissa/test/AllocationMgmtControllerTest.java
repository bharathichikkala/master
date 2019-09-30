package com.zone.zissa.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.zone.zissa.core.ZissaApplicationTest;

// TODO: Auto-generated Javadoc
/**
 * The Class AllocationMgmtControllerTest.
 */
public class AllocationMgmtControllerTest extends ZissaApplicationTest {

  /** The wac. */
  @Autowired
  private WebApplicationContext wac;

  /** The mock mvc. */
  private MockMvc mockMvc;

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
   * Setup.
   */
  @Before
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  /**
   * Junit test case for get resource allocation details By resourceId.
   *
   * @return the allocation details by resource id test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdTest() throws Exception {
    this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 7))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.data").exists())
        .andExpect(jsonPath("$.status").value(200));
  }

  /**
   * Junit failure test case for get allocation details By resourceId for Access denied.
   *
   * @return the allocation details by resource id failure test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdFailureTest() throws Exception {

    this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 3))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value(403));
  }

  /**
   * Junit failure test case for get Allocation details By resourceId by giving not existing resource.
   *
   * @return the allocation details by resource id by not existing resource test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationDetailsByResourceIdByNotExistingResourceTest()
      throws Exception {

    this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 13214))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value(404));
  }

  /**
   * Junit failure test case for get allocation details By resourceId with no allocation history.
   *
   * @return the allocations by resource id with no allocation history test
   * @throws Exception the exception
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllocationsByResourceIdWithNoAllocationHistoryTest()
      throws Exception {

    this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 1))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.status").value(204));
  }

  /**
   * Junit test case for getAllResourcesWithAllocationDetails with existing search term.
   *
   * @return the all resources with allocation details by search term test
   * @throws Exception the exception
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void getAllResourcesWithAllocationDetailsBySearchTermTest()
      throws Exception {

    this.mockMvc.perform(get(
        "/v1/allocations?category_ID=1,2&page=0&size=20&searchText=s&direction=asc&attrid=0"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.data").exists())
        .andExpect(jsonPath("$.status").value(200));
  }

  /**
   * Junit test case for getAllResourcesWithAllocationDetails with not existing search term.
   *
   * @return the all resources with allocation details by invalid search term test
   * @throws Exception the exception
   */
  @Test
  @WithMockUser(username = username, password = password)
  public void getAllResourcesWithAllocationDetailsByInvalidSearchTermTest()
      throws Exception {

    this.mockMvc.perform(get(
        "/v1/allocations?category_ID=1,2&page=0&size=20&searchText=sdvvcsgdfgdf&direction=asc&attrid=0"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(jsonPath("$.data").isEmpty())
        .andExpect(jsonPath("$.status").value(200));
  }
}
