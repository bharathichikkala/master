package com.zone.zissa.service;

import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.json.JSONException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/** The Interface AttributeService. */
public interface AttributeService {

  /**
   * Adds the attribute.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  Attribute addAttribute(@Valid @RequestBody String attributeData)
      throws JSONException;

  /**
   * Update attribute.
   *
   * @param attributeData the attribute data
   * @return Attribute
   * @throws JSONException the JSON exception
   */
  Attribute updateAttribute(@Valid @RequestBody String attributeData)
      throws JSONException;

  /**
   * Delete attribute.
   *
   * @param attributeId the attribute id
   */
  void deleteAttribute(@NotNull @PathVariable Short attributeId);

  /**
   * Get the all attributes.
   *
   * @return the list
   */
  List<Attribute> getAllAttributes();

  /**
   * Get all attribute datatypes.
   *
   * @return the list
   */
  List<AttrDataType> getAllAttributeDataTypes();

  /**
   * Gets the attribute info by id.
   *
   * @param attributeId the attribute id
   * @return Attribute
   */
  Attribute getAttributeInfoById(@NotNull @PathVariable Short attributeId);
}
