package com.zone.zissa.repos;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** ResourceAttributeRepository for ResourceAttribute database table. */
@Transactional
public interface ResourceAttributeRepository
    extends JpaRepository<ResourceAttribute, Integer> {

  /**
   * Find by attribute.
   *
   * @param attribute the attribute
   * @return the list
   */
  List<ResourceAttribute> findByAttribute(Attribute attribute);

  /**
   * Delete resources attributes.
   *
   * @param attributeId the attribute Id
   * @param resourceIdList the resource id list
   */
  @Modifying
  @Query(value = "Delete FROM resource_attribute where "
      + "fk_attribute_id = ?1 and fk_resource_id in ?2", nativeQuery = true)
  void deleteResourcesAttributes(short attributeId,
      List<Resource> resourceIdList);

  /**
   * Find resource attributes by value and id.
   *
   * @param attribute the attribute
   * @param value the value
   * @return the list
   */
  List<ResourceAttribute> findByAttributeAndValue(Attribute attribute,
      String value);

  /**
   * Find resource attributes by id.
   *
   * @param resourceAttributeID the resource attribute ID
   * @return ResourceAttribute
   */
  Optional<ResourceAttribute> findByResourceAttributeID(
      int resourceAttributeID);

  /**
   * Update resources attribute Value with new value.
   *
   * @param newValue the new value
   * @param attributeId the attribute Id
   * @param oldValue the old value
   */
  @Modifying
  @Query(value = "update resource_attribute SET value = ?1 where"
      + " fk_attribute_id = ?2 and value = ?3", nativeQuery = true)
  void updateResourcesAttributes(String newValue, short attributeId,
      String oldValue);
}
