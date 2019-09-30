package com.pals.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelData {
	public File inputWorkbook;
	public Sheet sheet;

	public ReadExcelData(String fileName, String sheetName) throws BiffException, IOException {
		Path resourceDirectory = Paths.get("src/test/resources");
		FileInputStream file = new FileInputStream(resourceDirectory+"//"+fileName);
		Workbook fwb = Workbook.getWorkbook(file);
		sheet = fwb.getSheet(sheetName);
	}

	public String readData(int i, int j) throws BiffException, IOException {
		Cell cell = sheet.getCell(i, j);
		return cell.getContents();
	}

}