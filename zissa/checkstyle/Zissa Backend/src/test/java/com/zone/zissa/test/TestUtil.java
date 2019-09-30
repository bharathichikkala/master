package com.zone.zissa.test;

import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * This class converts Object to Json Bytes.
 */
public class TestUtil {

  /**
   * Instantiates a new test util.
   */
  private TestUtil() {
    throw new IllegalAccessError(" Test Utility class");
  }

  /**
   * Convert object to json string.
   *
   * @param object the object
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static String convertObjectToJsonString(Object object)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return mapper.writeValueAsString(object);
  }
}
