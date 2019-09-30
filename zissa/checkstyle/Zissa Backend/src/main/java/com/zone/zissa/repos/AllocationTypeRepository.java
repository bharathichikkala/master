package com.zone.zissa.repos;

import com.zone.zissa.model.AllocationType;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AllocationTypeRepository Interface for allocationType database table.
 */
@Transactional
public interface AllocationTypeRepository
    extends JpaRepository<AllocationType, Byte> {

  /**
   * Find AllocationType by allocationtypeId.
   *
   * @param allocationtypeId the allocationtype id
   * @return AllocationType
   */
  Optional<AllocationType> findByAllocationTypeID(byte allocationtypeId);

  /**
   * findAll method.
   *
   * @return AllocationType
   */
  @Cacheable(value = "AllocationType")
  List<AllocationType> findAll();

  /**
   * save method.
   *
   * @return AllocationType
   */
  @CacheEvict(value = "AllocationType", allEntries = true)
  AllocationType save(AllocationType entity);
}
