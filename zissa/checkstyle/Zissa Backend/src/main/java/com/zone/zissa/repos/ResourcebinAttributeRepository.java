package com.zone.zissa.repos;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/** ResourcebinAttributeRepository for ResourcebinAttribute database table. */
@Transactional
public interface ResourcebinAttributeRepository
    extends JpaRepository<ResourcebinAttribute, Integer> {

  /**
   * Find by attribute.
   *
   * @param attribute the attribute
   * @return the list
   */
  List<ResourcebinAttribute> findByAttribute(Attribute attribute);

  /**
   * Delete resource bin attributes.
   *
   * @param attributeId the attribute Id
   * @param resourcebinIdList the resourcebin id list
   */
  @Modifying
  @Query(
      value = "Delete FROM resourcebin_attribute where"
          + " fk_attribute_id = ?1 and fk_resource_id in ?2",
      nativeQuery = true)
  void deleteResourceBinAttributes(short attributeId,
      List<Resourcebin> resourcebinIdList);

  /**
   * Find resource bin attributes by value and id.
   *
   * @param attribute the attribute
   * @param value the value
   * @return the list
   */
  List<ResourcebinAttribute> findByAttributeAndValue(Attribute attribute,
      String value);

  /**
   * Update resources bin attribute Value with new value.
   *
   * @param newValue the new value
   * @param attributeId the attribute Id
   * @param oldValue the old value
   */
  @Modifying
  @Query(value = "update resourcebin_attribute SET value = ?1 "
      + "where fk_attribute_id = ?2 and value = ?3", nativeQuery = true)
  void updateResourceBinAttributes(String newValue, short attributeId,
      String oldValue);
}
