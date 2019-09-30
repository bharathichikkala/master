package com.zone.zissa.repos;

import com.zone.zissa.model.Allocation;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** The AllocationRepository Interface for the allocation database table. */
@Transactional
public interface AllocationRepository
    extends JpaRepository<Allocation, Integer> {

  /**
   * Find allocations by resource.
   *
   * @param resourceId the resource Id
   * @return Allocation
   */
  @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID "
      + "and fk_status_id=1", nativeQuery = true)
  Optional<Allocation> findAllocationsByResource(
      @Param("resource_ID") Integer resourceId);

  /**
   * Find allocation history by resource.
   *
   * @param resourceId the resource Id
   * @return the list
   */
  @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID "
      + "ORDER BY allocation_id desc limit 5", nativeQuery = true)
  List<Allocation> findAllocationHistoryByResource(
      @Param(value = "resource_ID") Integer resourceId);

  /**
   * Find allocations list by resource.
   *
   * @param resourceId the resource Id
   * @return the list
   */
  @Query(value = "SELECT * FROM allocation where fk_resource_id =:resource_ID",
      nativeQuery = true)
  List<Allocation> findAllocationListByResource(
      @Param(value = "resource_ID") Integer resourceId);

  /**
   * Delete by allocation id list.
   *
   * @param allocationList the allocation list
   */
  @Modifying
  @Query(value = "delete FROM allocation where allocation_id in ?1",
      nativeQuery = true)
  void deleteByAllocationIdList(List<Allocation> allocationList);

  /**
   * Find allocation by allocationId.
   *
   * @param allocationId the allocation id
   * @return Allocation
   */
  Optional<Allocation> findByAllocationID(int allocationId);
}
