package com.zone.zissa.service.impl;

import com.zone.zissa.model.AttrDataType;
import com.zone.zissa.model.Attribute;
import com.zone.zissa.model.AttributeValue;
import com.zone.zissa.model.Category;
import com.zone.zissa.model.CategoryAttribute;
import com.zone.zissa.model.Operation;
import com.zone.zissa.model.Permission;
import com.zone.zissa.model.Resource;
import com.zone.zissa.model.ResourceAttribute;
import com.zone.zissa.model.Resourcebin;
import com.zone.zissa.model.ResourcebinAttribute;
import com.zone.zissa.model.Role;
import com.zone.zissa.model.Status;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.AttributeDataTypeRepository;
import com.zone.zissa.repos.AttributeRepository;
import com.zone.zissa.repos.AttributeValueRepository;
import com.zone.zissa.repos.CategoryAttributeRepository;
import com.zone.zissa.repos.CategoryRepository;
import com.zone.zissa.repos.OperationRepository;
import com.zone.zissa.repos.PermissionRepository;
import com.zone.zissa.repos.ResourceAttributeRepository;
import com.zone.zissa.repos.ResourceRepository;
import com.zone.zissa.repos.ResourcebinAttributeRepository;
import com.zone.zissa.repos.ResourcebinRepository;
import com.zone.zissa.repos.StatusRepository;
import com.zone.zissa.repos.UserRepository;
import com.zone.zissa.response.RestApiMessageConstants;
import com.zone.zissa.service.ExcelDataExportService;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** The Class ExcelData Export ServiceImpl. */
@Service
public class ExcelDataExportServiceImpl implements ExcelDataExportService {

  /** The Constant LOGGER. */
  private static final Logger LOGGER =
      LoggerFactory.getLogger(ExcelDataExportServiceImpl.class);

  /** The res repo. */
  @Autowired
  private ResourceRepository resRepo;

  /** The user repo. */
  @Autowired
  private UserRepository userRepo;

  /** The category repo. */
  @Autowired
  private CategoryRepository categoryRepo;

  /** The attr repo. */
  @Autowired
  private AttributeRepository attrRepo;

  /** The attr val repo. */
  @Autowired
  private AttributeValueRepository attrValRepo;

  /** The attr data repo. */
  @Autowired
  private AttributeDataTypeRepository attrDataRepo;

  /** The res attr repo. */
  @Autowired
  private ResourceAttributeRepository resAttrRepo;

  /** The status repo. */
  @Autowired
  private StatusRepository statusRepo;

  /** The category attr repo. */
  @Autowired
  private CategoryAttributeRepository categoryAttrRepo;

  /** The permission repo. */
  @Autowired
  private PermissionRepository permissionRepo;

  /** The operation repo. */
  @Autowired
  private OperationRepository operationRepo;

  /** The res bin repo. */
  @Autowired
  private ResourcebinRepository resBinRepo;

  /** The res bin attr repo. */
  @Autowired
  private ResourcebinAttributeRepository resBinAttrRepo;

  /** The import excel path. */
  @Value("${import-excelpath}")
  private String importExcelPath;

  /**
   * addResources method.
   *
   * @return Object
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public Object addResources() throws IOException {

    LOGGER.info(
        "Add Resources by Exporting ExcelSheet Data Service implementation");
    Workbook workBook = null;
    workBook = WorkbookFactory.create(new File(importExcelPath));
    for (Sheet sheet : workBook) {
      DataFormatter dataFormatter = new DataFormatter();
      Iterator<Row> rowIterator = sheet.rowIterator();
      List<String> dataTypesList = new ArrayList<>();
      if (rowIterator.hasNext()) {
        this.saveDataTypesList(dataFormatter, dataTypesList, rowIterator);
      }
      Map<Integer, Map<String, String>> attrDataTypeIndexMap =
          new LinkedHashMap<>();
      org.springframework.security.core.userdetails.User user =
          (org.springframework.security.core.userdetails.User) SecurityContextHolder
              .getContext().getAuthentication().getPrincipal();
      Date date = new Date();
      long time = date.getTime();
      Timestamp ts = new Timestamp(time);
      List<String> attrNames = new ArrayList<>();
      if (rowIterator.hasNext()) {
        this.saveAttribute(user, attrNames, dataTypesList, ts,
            attrDataTypeIndexMap, rowIterator, dataFormatter);
      }

      Category categoryObj = this.saveCategory(sheet, ts, user, attrNames);
      int globalCodeValue = 1;
      while (rowIterator.hasNext()) {
        Row row2 = rowIterator.next();
        Iterator<Cell> cellIterator2 = row2.cellIterator();
        cellIterator2.next();
        int lastKey = 0;

        this.saveAttributeValue(attrDataTypeIndexMap, dataFormatter, lastKey,
            cellIterator2);
        Iterator<Cell> cellIterator3 = row2.cellIterator();
        Cell cell = cellIterator3.next();
        String cellValue = dataFormatter.formatCellValue(cell).trim();
        if (cellValue.equals("")) {
          break;
        }
        int localCodeValue = Integer.parseInt(cellValue
            .substring(cellValue.lastIndexOf('/') + 1, cellValue.length()));
        String codePattern = categoryObj.getCode_Pattern();
        int codeValueDiff = localCodeValue - globalCodeValue;
        if (localCodeValue - globalCodeValue > 1) {

          this.saveResourceBin(ts, attrNames, categoryObj, user, localCodeValue,
              codeValueDiff, codePattern);
          Resource resourceObj =
              this.saveResource(user, ts, categoryObj, cellValue);
          this.setResourceAttributes(cellIterator3, dataFormatter,
              attrDataTypeIndexMap, lastKey, resourceObj);
        } else {

          Resource resourceObj =
              this.saveResource(user, ts, categoryObj, cellValue);
          this.setResourceAttributes(cellIterator3, dataFormatter,
              attrDataTypeIndexMap, lastKey, resourceObj);
        }
        globalCodeValue = localCodeValue;
      }
    }

    workBook.close();
    return "SUCCESS";
  }

  /**
   * setResourceAttributes method.
   *
   * @param cellIterator3 the cell iterator 3
   * @param dataFormatter the data formatter
   * @param attrDataTypeIndexMap the attr data type index map
   * @param lastKey the last key
   * @param resourceObj the resource obj
   */
  public void setResourceAttributes(final Iterator<Cell> cellIterator3,
      final DataFormatter dataFormatter,
      final Map<Integer, Map<String, String>> attrDataTypeIndexMap,
      final int lastKey, final Resource resourceObj) {
    LOGGER.info(
        "Set ResourceAttributes Service in ExcelSheet Data Service implementation");
    while (cellIterator3.hasNext()) {
      Cell cell2 = cellIterator3.next();
      String cellValue2 = dataFormatter.formatCellValue(cell2).trim();
      int columnIndex = cell2.getColumnIndex();
      Map<String, String> attrDataMap = attrDataTypeIndexMap.get(columnIndex);
      if (columnIndex > lastKey) {
        break;
      }
      Optional<Attribute> attrObj = Optional.empty();
      for (String s : attrDataMap.keySet()) {
        attrObj = attrRepo.findByName(s);
      }
      ResourceAttribute resourceattrobj = new ResourceAttribute();
      resourceattrobj.setValue(cellValue2);
      if (attrObj.isPresent()) {
        resourceattrobj.setAttribute(attrObj.get());
      }
      resourceattrobj.setResource(resourceObj);
      resAttrRepo.save(resourceattrobj);
    }
  }

  /**
   * saveCategory method.
   *
   * @param sheet the sheet
   * @param ts the ts
   * @param user the user
   * @param attrNames the attr names
   * @return the category
   */
  public Category saveCategory(final Sheet sheet, final Timestamp ts,
      final org.springframework.security.core.userdetails.User user,
      final List<String> attrNames) {
    LOGGER.info(
        "Save Category Service in ExcelSheet Data Service implementation");
    Row row = sheet.getRow(2);
    Iterator<Cell> cellIterator = row.cellIterator();
    String codeValue = cellIterator.next().getStringCellValue().trim();
    codeValue = codeValue.substring(0, codeValue.lastIndexOf('/'));
    Category category = new Category();
    category.setCodePattern(codeValue);
    category.setCreatedDate(ts);
    category.setName(sheet.getSheetName());
    Optional<User> userObj = userRepo.findByUserName(user.getUsername());
    if (userObj.isPresent()) {
      category.setUser(userObj.get());
    }
    Optional<Category> categoryExists =
        categoryRepo.findByName(sheet.getSheetName());
    Category categoryObj = null;
    if (!categoryExists.isPresent()) {
      categoryObj = categoryRepo.save(category);
      Role roleObj = null;
      if (userObj.isPresent()) {
        roleObj = userObj.get().getRole();
      }
      Integer[] operations = {RestApiMessageConstants.OPERATION_VIEW,
          RestApiMessageConstants.OPERATION_ADD,
          RestApiMessageConstants.OPERATION_EDIT,
          RestApiMessageConstants.OPERATION_ALLOCATE,
          RestApiMessageConstants.OPERATION_DISPOSE,
          RestApiMessageConstants.OPERATION_DELETE};
      for (int j = 0; j < operations.length; j++) {
        Integer operationId = operations[j];
        Optional<Operation> operationObj =
            operationRepo.findByOperationID(operationId);
        Permission permissionObj = new Permission();
        permissionObj.setRole(roleObj);
        permissionObj.setCategory(categoryObj);
        if (operationObj.isPresent()) {
          permissionObj.setOperation(operationObj.get());
        }
        permissionRepo.save(permissionObj);
      }
    } else {
      categoryObj = categoryExists.get();
    }
    for (int i = 0; i < attrNames.size(); i++) {
      CategoryAttribute categoryAttrObj = new CategoryAttribute();
      categoryAttrObj.setCategory(categoryObj);
      Optional<Attribute> attribute = attrRepo.findByName(attrNames.get(i));
      if (attribute.isPresent()) {
        categoryAttrObj.setAttribute(attribute.get());
      }
      categoryAttrObj.setDefault(false);
      categoryAttrRepo.save(categoryAttrObj);
    }
    return categoryObj;
  }

  /**
   * saveResourceBin method.
   *
   * @param ts the ts
   * @param attrNames the attr names
   * @param categoryObj the category obj
   * @param user the user
   * @param localCodeValue the local code value
   * @param codeValueDiff the code value diff
   * @param codePattern the code pattern
   */
  public void saveResourceBin(final Timestamp ts, final List<String> attrNames,
      final Category categoryObj,
      final org.springframework.security.core.userdetails.User user,
      final int localCodeValue, final int codeValueDiff,
      final String codePattern) {
    LOGGER.info(
        "Save ResourceBin Service in ExcelSheet Data Service implementation");
    int locValue = localCodeValue;

    for (int i = 0; i < codeValueDiff - 1; i++) {
      --locValue;
      String newLocalCodeValue = locValue + "";
      int localcodelen = newLocalCodeValue.length();
      String finalCodePattern = null;
      switch (localcodelen) {
        case RestApiMessageConstants.VALUE_ONE:
          finalCodePattern = codePattern + "/000" + newLocalCodeValue;
          break;
        case RestApiMessageConstants.VALUE_TWO:
          finalCodePattern = codePattern + "/00" + newLocalCodeValue;
          break;
        case RestApiMessageConstants.VALUE_THREE:
          finalCodePattern = codePattern + "/0" + newLocalCodeValue;
          break;
        case RestApiMessageConstants.VALUE_FOUR:
          finalCodePattern = codePattern + "/" + newLocalCodeValue;
          break;
        default:
          break;
      }

      Resourcebin resBinObj = new Resourcebin();
      resBinObj.setCode(finalCodePattern);
      resBinObj.setCreatedDate(ts);
      resBinObj.setCategory(categoryObj);
      Optional<User> userObj = userRepo.findByUserName(user.getUsername());
      if (userObj.isPresent()) {
        resBinObj.setFKCreateUserID(userObj.get().getUser_ID());
      }
      Optional<Status> statusObj = statusRepo.findBystatusID((byte) 0);
      if (statusObj.isPresent()) {
        resBinObj.setFKStatusID(statusObj.get().getStatus_ID());
      }
      resBinObj.setDisposeReason("Not required");
      Resourcebin resourceBinObj = resBinRepo.save(resBinObj);
      for (int i1 = 0; i1 < attrNames.size(); i1++) {
        ResourcebinAttribute resBinAttrObj = new ResourcebinAttribute();
        resBinAttrObj.setValue("");
        Optional<Attribute> attribute = attrRepo.findByName(attrNames.get(i1));
        if (attribute.isPresent()) {
          resBinAttrObj.setAttribute(attribute.get());
        }
        resBinAttrObj.setResourcebin(resourceBinObj);
        resBinAttrRepo.save(resBinAttrObj);
      }
    }
  }

  /**
   * saveResource method.
   *
   * @param user the user
   * @param ts the ts
   * @param categoryObj the category obj
   * @param cellValue the cell value
   * @return the resource
   */
  public Resource saveResource(
      final org.springframework.security.core.userdetails.User user,
      final Timestamp ts, final Category categoryObj, final String cellValue) {
    LOGGER.info(
        "Save Resource Service in ExcelSheet Data Service implementation");
    Resource resource = new Resource();
    resource.setCategory(categoryObj);
    Optional<User> userObj = userRepo.findByUserName(user.getUsername());
    if (userObj.isPresent()) {
      resource.setUser(userObj.get());
    }
    resource.setCode(cellValue);
    Optional<Status> statusObj = statusRepo.findBystatusID((byte) 0);
    if (statusObj.isPresent()) {
      resource.setStatus(statusObj.get());
    }
    resource.setCreatedDate(ts);
    return resRepo.save(resource);
  }

  /**
   * saveAttribute method.
   *
   * @param user the user
   * @param attrNames the attr names
   * @param dataTypesList the data types list
   * @param ts the ts
   * @param attrDataTypeIndexMap the attr data type index map
   * @param rowIterator the row iterator
   * @param dataFormatter the data formatter
   */
  public void saveAttribute(
      final org.springframework.security.core.userdetails.User user,
      final List<String> attrNames, final List<String> dataTypesList,
      final Timestamp ts,
      final Map<Integer, Map<String, String>> attrDataTypeIndexMap,
      final Iterator<Row> rowIterator, final DataFormatter dataFormatter) {
    LOGGER.info(
        "Save Attribute Service in ExcelSheet Data Service implementation");
    Optional<User> userObj = userRepo.findByUserName(user.getUsername());
    Row row = rowIterator.next();
    Iterator<Cell> cellIterator = row.cellIterator();
    cellIterator.next();
    while (cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      String cellValue = dataFormatter.formatCellValue(cell).trim();
      attrNames.add(cellValue);
    }
    attrNames.removeIf(s -> {
      boolean flag = false;
      if (s.equalsIgnoreCase("")) {
        flag = true;
      }
      return flag;
    });
    Iterator<Cell> cellIteratorobj = row.cellIterator();
    cellIteratorobj.next();
    for (int i = 0; i < attrNames.size() && cellIteratorobj.hasNext(); i++) {
      Map<String, String> attrDatatypeMap = new LinkedHashMap<>();
      attrDatatypeMap.put(attrNames.get(i), dataTypesList.get(i));
      Cell cell = cellIteratorobj.next();
      attrDataTypeIndexMap.put(cell.getColumnIndex(), attrDatatypeMap);
      Attribute attribute = new Attribute();
      String dataType = dataTypesList.get(i).trim();
      if (dataType.contains("-")) {
        dataType = dataType.substring(dataType.indexOf('-') + 1);
      }
      Optional<AttrDataType> attributeDataType =
          attrDataRepo.findByDataTypeNameIgnoreCase(dataType.trim());
      if (attributeDataType.isPresent()) {
        attribute.setAttrDataType(attributeDataType.get());
      }
      attribute.setCreatedDate(ts);
      attribute.setName(attrNames.get(i));
      if (userObj.isPresent()) {
        attribute.setUser(userObj.get());
      }
      Optional<Attribute> attrExists = attrRepo.findByName(attrNames.get(i));
      if (!attrExists.isPresent()) {
        attrRepo.save(attribute);
      }
    }
  }

  /**
   * saveAttributeValue method.
   *
   * @param attrDataTypeIndexMap the attr data type index map
   * @param dataFormatter the data formatter
   * @param lastKey the last key
   * @param cellIterator2 the cell iterator 2
   */
  public void saveAttributeValue(
      final Map<Integer, Map<String, String>> attrDataTypeIndexMap,
      final DataFormatter dataFormatter, int lastKey,
      final Iterator<Cell> cellIterator2) {
    LOGGER.info(
        "Save AttributeValue Service in ExcelSheet Data Service implementation");

    for (Map.Entry<Integer, Map<String, String>> entry : attrDataTypeIndexMap
        .entrySet()) {
      lastKey = entry.getKey();
    }
    while (cellIterator2.hasNext()) {
      Cell cell = cellIterator2.next();
      int columnIndex = cell.getColumnIndex();
      if (columnIndex > lastKey) {
        break;
      }
      String cellValue = dataFormatter.formatCellValue(cell).trim();
      Map<String, String> attrDataMap = attrDataTypeIndexMap.get(columnIndex);

      attrDataMap.forEach((k, value) -> {
        if (value.toLowerCase().indexOf("Dropdown".toLowerCase()) != -1) {
          AttributeValue attrValueObj = new AttributeValue();
          Optional<Attribute> attributeobj = attrRepo.findByName(k);
          attrValueObj.setValue(cellValue);
          attrValueObj.setAttribute(attributeobj.get());
          AttributeValue attrValObj =
              attrValRepo.findAttributeValuesByIdAndValue(
                  attributeobj.get().getAttribute_ID(), cellValue);
          if (attrValObj == null && !cellValue.equals("")) {
            attrValRepo.save(attrValueObj);
          }
        }
      });
    }
  }



  /**
   * saveDataTypesList method.
   *
   * @param dataFormatter the data formatter
   * @param dataTypesList the data types list
   * @param rowIterator the row iterator
   */
  public void saveDataTypesList(final DataFormatter dataFormatter,
      final List<String> dataTypesList, final Iterator<Row> rowIterator) {
    LOGGER.info(
        "Save DataTypesList Service in ExcelSheet Data Service implementation");
    Row row = rowIterator.next();
    Iterator<Cell> cellIterator = row.cellIterator();
    cellIterator.next();
    while (cellIterator.hasNext()) {
      Cell cell = cellIterator.next();
      String cellValue = dataFormatter.formatCellValue(cell).trim();
      dataTypesList.add(cellValue);
    }
    dataTypesList.removeIf(s -> {
      boolean flag = false;
      if (s.equalsIgnoreCase("")) {
        flag = true;
      }
      return flag;
    });
  }
}
