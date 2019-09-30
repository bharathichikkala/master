package com.zone.zissa.repos;

import com.zone.zissa.model.AttrDataType;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

/** AttributeDataTypeRepository Interface for attribute database table. */
@Transactional
public interface AttributeDataTypeRepository
    extends JpaRepository<AttrDataType, Integer> {

  /**
   * the findByDataTypeNameIgnoreCase method.
   *
   * @param dataTypeName the data type name
   * @return AttrDataType
   */
  Optional<AttrDataType> findByDataTypeNameIgnoreCase(String dataTypeName);

  /**
   * Find AttrDataType by dataTypeId.
   *
   * @param dataTypeId the data type id
   * @return AttrDataType
   */
  Optional<AttrDataType> findByDataTypeID(int dataTypeId);

  /**
   * findAll method.
   *
   * @return AttrDataType
   */
  @Cacheable(value = "AttributeDataTypes")
  List<AttrDataType> findAll();

  /**
   * save method.
   * 
   * @param entity the entity
   * @return AttrDataType
   */
  @CacheEvict(value = "AttributeDataTypes", allEntries = true)
  AttrDataType save(AttrDataType entity);
}
