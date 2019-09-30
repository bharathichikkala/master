package com.zone.zissa.service.impl;

import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The Class DisposedResourceComparator.
 */
@Service
public class DisposedResourceComparator implements Comparator<Resourcebin> {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(DisposedResourceComparator.class);

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
  public int compare(final Resourcebin resource1, final Resourcebin resource2) {
    List<ResourcebinAttribute> resourceArr1 =
        resource1.getResourcebinAttributes();
    List<ResourcebinAttribute> resourceArr2 =
        resource2.getResourcebinAttributes();
    ResourceServiceImpl resourceService = new ResourceServiceImpl();
    ResourcebinAttribute resourceAttribute1 = new ResourcebinAttribute();
    ResourcebinAttribute resourceAttribute2 = null;
    for (ResourcebinAttribute resourceAttribute : resourceArr1) {
      short attributeId = resourceAttribute.getAttribute().getAttribute_ID();
      if (attributeId == attrId) {
        resourceAttribute1 = resourceAttribute;
      }
    }
    for (ResourcebinAttribute resourceAttribute : resourceArr2) {
      short attributeId = resourceAttribute.getAttribute().getAttribute_ID();
      if (attributeId == attrId) {
        resourceAttribute2 = resourceAttribute;
      }
    }

    String dataType =
        resourceAttribute1.getAttribute().getAttrDataType().getData_Type_Name();

    if ("ASC".equalsIgnoreCase(direction)) {

      return this.compareAttributesAsc(dataType, resourceAttribute1,
          resourceAttribute2, resourceService);

    } else {
      return this.compareAttributesDesc(dataType, resourceAttribute1,
          resourceAttribute2, resourceService);
    }
  }

  /**
   * compareAttributesAsc method.
   *
   * @param dataType the data type
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @param resourceService the resource service
   * @return int
   */
  public int compareAttributesAsc(final String dataType,
      final ResourcebinAttribute resourceAttribute1,
      final ResourcebinAttribute resourceAttribute2,
      final ResourceServiceImpl resourceService) {

    switch (dataType) {
      case "String":
        returnValue = resourceAttribute1.getValue().trim()
            .compareToIgnoreCase(resourceAttribute2.getValue().trim());
        break;
      case "Integer":
        returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
        break;
      case "Float":
      case "Currency":
        returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
        break;
      case "Date":
        try {
          returnValue = resourceService.sortAscDisposedResourceDateCase(
              resourceAttribute1, resourceAttribute2);
        } catch (ParseException e) {
          LOGGER.error("ERROR", e);
        }
        break;
      default:
        LOGGER.info("Default");
    }
    return returnValue;
  }

  /**
   * compareAttributesDesc method.
   *
   * @param dataType the data type
   * @param resourceAttribute1 the resource attribute 1
   * @param resourceAttribute2 the resource attribute 2
   * @param resourceService the resource service
   * @return int
   */
  public int compareAttributesDesc(final String dataType,
      final ResourcebinAttribute resourceAttribute1,
      final ResourcebinAttribute resourceAttribute2,
      final ResourceServiceImpl resourceService) {

    switch (dataType) {
      case "String":
        returnValue = -resourceAttribute1.getValue().trim()
            .compareToIgnoreCase(resourceAttribute2.getValue().trim());
        break;
      case "Integer":
        returnValue = Integer.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Integer.valueOf(resourceAttribute2.getValue().trim()));
        returnValue = -returnValue;
        break;
      case "Float":
      case "Currency":
        returnValue = Double.valueOf(resourceAttribute1.getValue().trim())
            .compareTo(Double.valueOf(resourceAttribute2.getValue().trim()));
        returnValue = -returnValue;
        break;
      case "Date":
        try {
          returnValue = resourceService.sortDescDisposedResourceDateCase(
              resourceAttribute1, resourceAttribute2);
        } catch (ParseException e) {
          LOGGER.error("Error", e);
        }
        break;
      default:
        LOGGER.info("Default");
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
    DisposedResourceComparator.direction = sortingDirection;
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
    DisposedResourceComparator.attrId = attrID;
  }
}
