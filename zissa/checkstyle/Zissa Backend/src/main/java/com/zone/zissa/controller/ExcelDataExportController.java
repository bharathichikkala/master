package com.zone.zissa.controller;

import com.zone.zissa.service.ExcelDataExportService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** The Class ExcelDataExportController. */
@RestController
public class ExcelDataExportController {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ExcelDataExportController.class);

  /** The excel data export service impl. */
  @Autowired
  private ExcelDataExportService excelDataExportServiceImpl;

  /**
   * Adds the resource.
   *
   * @return the object
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @GetMapping("/v1/exportexceldata")
  public Object addResource() throws IOException {
    LOGGER.debug("Add New Excelexportdata Controller implementation");
    return excelDataExportServiceImpl.addResources();
  }
}
