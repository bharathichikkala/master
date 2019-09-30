package com.zone.zissa.repos;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.ProjectAllocation;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** The ProjectAllocationRepository for the Project_Allocation database table. */
@Transactional
public interface ProjectAllocationRepository
    extends JpaRepository<ProjectAllocation, Integer> {

  /**
   * Delete project allocations by allocation id list.
   *
   * @param allocationList the allocation list
   */
  @Modifying
  @Query(value = "delete FROM project_allocation where fk_allocation_id in ?1",
      nativeQuery = true)
  void deleteProjectAllocationsByAllocationIdList(
      @Param("allocation_ID") List<Allocation> allocationList);
}
