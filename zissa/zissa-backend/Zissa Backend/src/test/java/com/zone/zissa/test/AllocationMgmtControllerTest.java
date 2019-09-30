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

public class AllocationMgmtControllerTest extends ZissaApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    // for success test cases authentication
    private static final String username = "BathiyaT";
    private static final String password = "Zone@789";

    // for failure test cases authentiation (access denied)
    private static final String f_Username = "AmaliK";
    private static final String f_Password = "Zone@789";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Junit test case for getResource By resourceId
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesByResourceIdTest() throws Exception {
        this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 7)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));

    }

    /**
     * Junit failure test case for getResources By resourceId for Access denied
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesByCategoryIdFailureTest() throws Exception {

        this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 3)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Junit failure test case for getResources By resourceId for not existing
     * resource.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesByCategoryIdFailureTest1() throws Exception {

        this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 13214)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(404));

    }

    /**
     * Junit failure test case for getResources By resourceId with no allocation
     * history.
     * 
     * @throws Exception
     */
    @Test
    @Transactional
    @WithMockUser(username = username, password = password)
    public void getResourcesByCategoryIdFailureTest2() throws Exception {

        this.mockMvc.perform(get("/v1/allocations/{resource_ID}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.status").value(204));
    }

    /**
     * Junit test case for getAllResourcesWithAllocationDetails with existing search
     * term.
     * 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void getAllResourcesWithAllocationDetailsBySearchTermTest() throws Exception {

        this.mockMvc.perform(get("/v1/allocations?category_ID=1,2&page=0&size=20&searchText=s&direction=asc&attrid=0"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").exists()).andExpect(jsonPath("$.status").value(200));
    }

    /**
     * Junit test case for getAllResourcesWithAllocationDetails with not existing
     * search term.
     * 
     * @throws Exception
     */
    @Test
    @WithMockUser(username = username, password = password)
    public void getAllResourcesWithAllocationDetailsBySearchTermTest1() throws Exception {

        this.mockMvc.perform(
                get("/v1/allocations?category_ID=1,2&page=0&size=20&searchText=sdvvcsgdfgdf&direction=asc&attrid=0"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.data").isEmpty()).andExpect(jsonPath("$.status").value(200));
    }
}
