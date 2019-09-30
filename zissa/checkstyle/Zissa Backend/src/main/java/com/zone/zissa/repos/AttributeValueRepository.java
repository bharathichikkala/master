package com.zone.zissa.repos;

import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** AttributeValueRepository Interface for attribute_Value database table. */
@Transactional
public interface AttributeValueRepository
    extends JpaRepository<AttributeValue, Integer> {

  /**
   * The deleteAttributeValue method.
   *
   * @param attributeId the attribute Id
   */
  @Modifying
  @Query(value = "delete from attribute_value where "
      + "FK_Attribute_ID =:attribute_ID ", nativeQuery = true)
  void deleteAttributeValue(@Param("attribute_ID") Short attributeId);

  /**
   * The deleteAttributeValueID method.
   *
   * @param attributeValueId the attribute value Id
   */
  @Modifying
  @Query(value = "delete from attribute_value where"
      + " Attribute_Value_ID =:attribute_Value_ID ", nativeQuery = true)
  void deleteAttributeValueID(
      @Param("attribute_Value_ID") Integer attributeValueId);

  /**
   * The findAttributeValueByAttribute method.
   *
   * @param attribute the attribute
   * @return the set
   */
  Set<AttributeValue> findAttributeValueByAttribute(Attribute attribute);

  /**
   * The findByAttributeValueID method.
   *
   * @param attributeValueId the attribute value id
   * @return AttributeValue
   */
  Optional<AttributeValue> findByAttributeValueID(int attributeValueId);

  /**
   * The findAttributeValuesByIdAndValue method.
   *
   * @param attributeID the attribute ID
   * @param value the value
   * @return the AttributeValue
   */
  @Query(
      value = "SELECT * FROM zissa.attribute_value where "
          + "FK_Attribute_ID =:attribute_ID and Value =:value",
      nativeQuery = true)
  AttributeValue findAttributeValuesByIdAndValue(
      @Param("attribute_ID") Short attributeID, @Param("value") String value);
}
