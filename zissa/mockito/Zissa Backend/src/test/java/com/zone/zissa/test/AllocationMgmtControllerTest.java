package com.zone.zissa.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import com.zone.zissa.controller.AllocationMgmtController;
import com.zone.zissa.core.ZissaApplicationTest;
import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.Resource;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.ServiceResponse;
import com.zone.zissa.service.AllocationService;
import com.zone.zissa.service.impl.ResourceServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class AllocationMgmtControllerTest.
 */
public class AllocationMgmtControllerTest extends ZissaApplicationTest {

  /** The resource. */
  @InjectMocks
  private Resource resource;

  /** The allocation. */
  @InjectMocks
  private Allocation allocation;

  /** The allocation service. */
  @Mock
  private AllocationService allocationService;

  /** The resource service impl. */
  @Mock
  private ResourceServiceImpl resourceServiceImpl;

  /** The allocation mgmt controller. */
  @InjectMocks
  private AllocationMgmtController allocationMgmtController;

  /** The Constant username. */
  private static final String username = "BathiyaT";

  /** The Constant password. */
  private static final String password = "Zone@789";

  /**
   * Gets the all allocation details by resource test.
   *
   * @return the all allocation details by resource test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getAllAllocationDetailsByResourceTest() {
    resource = new Resource();
    resource.setResourceID(1);

    List<Allocation> allocationList = new ArrayList<>();
    allocation = new Allocation();
    allocation.setAllocationID(1);
    allocation.setResource(resource);
    allocationList.add(allocation);

    when(allocationService
        .getAllAllocationDetailsByResource(resource.getResource_ID()))
            .thenReturn(allocationList);
    ServiceResponse<List<Allocation>> response = allocationMgmtController
        .getAllAllocationDetailsByResource(resource.getResource_ID());

    assertThat(200, is(response.getStatus()));
  }


  /**
   * Gets the resources by search term test.
   *
   * @return the resources by search term test
   */
  @Test
  @Transactional
  @WithMockUser(username = username, password = password)
  public void getResourcesBySearchTermTest() {
    List<Integer> categoryId = null;
    int page = 0;
    int size = 1;
    String searchText = "Bathiyat";
    String direction = "ASC";
    short attrId = 0;
    when(resourceServiceImpl.getCount()).thenReturn((double) 0);
    List<Resource> resourceSearchList = new ArrayList<>();
    when(allocationService.getAllResourcesBySearchTerm(categoryId, page, size,
        searchText, direction, attrId)).thenReturn(resourceSearchList);
    PageServiceResponse<Object> response =
        allocationMgmtController.getResourcesBySearchTerm(categoryId, page,
            size, searchText, direction, attrId);

    assertThat(200, is(response.getStatus()));
  }

}
