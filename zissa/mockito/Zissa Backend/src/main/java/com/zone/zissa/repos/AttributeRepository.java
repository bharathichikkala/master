package com.zone.zissa.repos;

import com.zone.zissa.model.Attribute;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

/** AttributeDataTypeRepository Interface for attribute database table. */
@Transactional
public interface AttributeRepository extends JpaRepository<Attribute, Short> {

  /**
   * The findByName method.
   *
   * @param name the name
   * @return Attribute
   */
  Optional<Attribute> findByName(String name);

  /**
   * The findByAttributeID method.
   *
   * @param attributeId the attribute id
   * @return Attribute
   */
  Optional<Attribute> findByAttributeID(short attributeId);
}
