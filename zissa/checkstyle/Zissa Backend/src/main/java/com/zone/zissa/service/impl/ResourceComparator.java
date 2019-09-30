package com.zone.zissa.service.impl;

import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.response.RestApiMessageConstants;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class ResourceComparator.
 */
@Component
public class ResourceComparator implements Comparator<Resource> {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ResourceComparator.class);

  /** The direction. */
  private static String direction;

  /** The attr id. */
  private static Short attrId;

  /** The return value. */
  private int returnValue = 0;

  /**
   * compare method.
   *
   * @param resource1 the resource 1
   * @param resource2 the resource 2
   * @return the list
   */
  @Override
  public int compare(final Resource resource1, final Resource resource2) {

    List<ResourceAttribute> resourceArr1 = resource1.getResourceAttributes();
    List<ResourceAttribute> resourceArr2 = resource2.getResourceAttributes();
    ResourceServiceImpl resourceServ = new ResourceServiceImpl();
    ResourceAttribute resourceAttribute1 = new ResourceAttribute();
    ResourceAttribute resourceAttribute2 = null;
    for (ResourceAttribute resourceAttribute : resourceArr1) {
      short attributeId = resourceAttribute.getAttribute().getAttribute_ID();
      if (attributeId == getAttrId()) {
        resourceAttribute1 = resourceAttribute;
      }
    }
    for (ResourceAttribute resourceAttribute : resourceArr2) {
      short attributeId = resourceAttribute.getAttribute().getAttribute_ID();
      if (attributeId == getAttrId()) {
        resourceAttribute2 = resourceAttribute;
      }
    }

    int dataType =
        resourceAttribute1.getAttribute().getAttrDataType().getData_Type_ID();

    if ("ASC".equalsIgnoreCase(getDirection())) {

      return this.compareAttributesAsc(dataType, resourceAttribute1,
          resourceAttribute2, resourceServ);
    } else {

      return this.compareAttributesDesc(dataType, resourceAttribute1,
          resourceAttribute2, resourceServ);
    }
  }

  /**
   * compareAttributesAsc method.
   *
   * @param dataType the data type
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @param resourceServ the resource serv
   * @return int
   */
  public int compareAttributesAsc(final int dataType,
      final ResourceAttribute resourceAttribute1,
      final ResourceAttribute resourceAttribute2,
      final ResourceServiceImpl resourceServ) {

    switch (dataType) {
      case RestApiMessageConstants.VALUE_ONE:
        returnValue = resourceAttribute1.getValue().trim()
            .compareToIgnoreCase(resourceAttribute2.getValue().trim());
        break;
      case RestApiMessageConstants.VALUE_TWO:
        returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
        break;
      case RestApiMessageConstants.VALUE_THREE:
      case RestApiMessageConstants.VALUE_FOUR:
        returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
        break;
      case RestApiMessageConstants.VALUE_FIVE:
        try {
          returnValue = resourceServ.sortAscResourceDateCase(resourceAttribute1,
              resourceAttribute2);
        } catch (ParseException e) {
          LOGGER.error("ERROR", e);
        }
        break;
      default:
        LOGGER.info("Default Case");
    }
    return returnValue;
  }

  /**
   * compareAttributesDesc method.
   *
   * @param dataType the data type
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @param resourceServ the resource serv
   * @return int
   */
  public int compareAttributesDesc(final int dataType,
      final ResourceAttribute resourceAttribute1,
      final ResourceAttribute resourceAttribute2,
      final ResourceServiceImpl resourceServ) {

    switch (dataType) {
      case RestApiMessageConstants.VALUE_ONE:
        returnValue = -resourceAttribute1.getValue().trim()
            .compareToIgnoreCase(resourceAttribute2.getValue().trim());
        break;
      case RestApiMessageConstants.VALUE_TWO:
        returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
        returnValue = -returnValue;
        break;
      case RestApiMessageConstants.VALUE_THREE:
      case RestApiMessageConstants.VALUE_FOUR:
        returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
        returnValue = -returnValue;
        break;

      case RestApiMessageConstants.VALUE_FIVE:
        try {
          returnValue = resourceServ
              .sortDescResourceDateCase(resourceAttribute1, resourceAttribute2);
        } catch (ParseException e) {
          LOGGER.error("ERROR", e);
        }
        break;
      default:
        LOGGER.info("Default case");
    }
    return returnValue;
  }

  /**
   * Gets the direction.
   *
   * @return the direction
   */
  public static String getDirection() {
    return direction;
  }

  /**
   * Sets the direction.
   *
   * @param sortingDirection the new direction
   */
  public static void setDirection(final String sortingDirection) {
    ResourceComparator.direction = sortingDirection;
  }

  /**
   * Gets the attr id.
   *
   * @return the attr id
   */

  public static Short getAttrId() {
    return attrId;
  }

  /**
   * Sets the attr id.
   *
   * @param attrID the new attr id
   */
  public static void setAttrId(final Short attrID) {
    ResourceComparator.attrId = attrID;
  }
}
