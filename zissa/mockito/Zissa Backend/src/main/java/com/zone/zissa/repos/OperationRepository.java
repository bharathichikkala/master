package com.zone.zissa.repos;

import com.zone.zissa.model.Operation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/** The OperationRepository Interface for the Operation database table. */
@Transactional
public interface OperationRepository extends JpaRepository<Operation, Integer> {
  /**
   * The findByOperationID method.
   *
   * @param operationId the operation id
   * @return Operation
   */
  Optional<Operation> findByOperationID(Integer operationId);

  /**
   * The findAll method.
   *
   * @return Operation
   */
  @Cacheable(value = "Operation")
  List<Operation> findAll();

  /**
   * the save method.
   * 
   * @param entity the entity
   * @return Operation
   */
  @CacheEvict(value = "Operation", allEntries = true)
  Operation save(Operation entity);
}
