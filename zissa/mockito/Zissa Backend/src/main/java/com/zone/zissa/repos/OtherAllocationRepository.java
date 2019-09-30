package com.zone.zissa.repos;

import com.zone.zissa.model.Allocation;
import com.zone.zissa.model.OtherAllocation;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** OtherAllocationRepository Interface for OtherAllocation database table. */
@Transactional
public interface OtherAllocationRepository
    extends JpaRepository<OtherAllocation, Integer> {
  /**
   * Delete other allocations by allocation id list.
   *
   * @param allocationList the allocation list
   */
  @Modifying
  @Query(value = "delete FROM other_allocation where fk_allocation_id in ?1",
      nativeQuery = true)
  void deleteOtherAllocationsByAllocationIdList(
      @Param("allocation_ID") List<Allocation> allocationList);
}
