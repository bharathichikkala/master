package com.zone.zissa.repos;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.EmployeeAllocation;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** EmployeeAllocationRepository for Employee_Allocation database table. */
@Transactional
public interface EmployeeAllocationRepository
    extends JpaRepository<EmployeeAllocation, Integer> {

  /**
   * The findByAllocation method.
   *
   * @param allocation the allocation
   * @return EmployeeAllocation
   */
  Optional<EmployeeAllocation> findByAllocation(Allocation allocation);

  /**
   * Delete employee allocations by allocation id list.
   *
   * @param allocationList the allocation list
   */
  @Modifying
  @Query(value = "delete FROM employee_allocation where fk_allocation_id in ?1",
      nativeQuery = true)
  void deleteEmployeeAllocationsByAllocationIdList(
      @Param("allocation_ID") List<Allocation> allocationList);
}
