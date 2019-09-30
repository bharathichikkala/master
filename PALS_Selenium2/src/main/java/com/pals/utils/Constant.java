package com.pals.utils;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.pals.page.objects.BaseClass;

import jxl.Sheet;
import jxl.Workbook;


public class Constant extends BaseClass {
	public static FileInputStream Data;
	public static FileInputStream Data1;
	public static Workbook wb;
	public static Sheet sh;
	public static Workbook wb1;
	public static Sheet sh1;
	public static final String screenshotPath = "";
	public static final String ChromeDriverPath = "chromedriver.exe";
	public static final String IEDriverPath = "IEDriverServer.exe";
	public static final String FirefoxDriverPath = "geckodriver.exe";

	public Constant(WebDriver driver) {
		super(driver);
	}

	public static String URL;
	public static String URLDashboard;
	public static final String TestDataPath = "TestData_PALS_Dashboard.xls";

	
	public static String getApplicationUrl() throws Exception {
		Path resourceDirectory = Paths.get("src/test/resources");
		Data = new FileInputStream(resourceDirectory+"//"+Constant.TestDataPath);
		wb = Workbook.getWorkbook(Data);
		sh = wb.getSheet("Setup");
		URL = sh.getCell(1, 1).getContents();
		return URL;
	}
	public static String getDataFromXLDocument(String sheetName,Integer cellColumnNum,Integer cellRowNum) throws Exception{
		Path resourceDirectory = Paths.get("src/test/resources");
		Data = new FileInputStream(resourceDirectory+"//"+Constant.TestDataPath);
		wb = Workbook.getWorkbook(Data);
		sh = wb.getSheet(sheetName);
		return sh.getCell(cellColumnNum,cellRowNum).getContents();
		}
	public static void clickCheckboxFromList(String xpathOfRecipientList,
			String valueToSelect) {

		List<WebElement> lst = driver.findElements(By
				.xpath(xpathOfRecipientList));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				System.out.println(f.getText());
				if (valueToSelect.equals(f.getText())) {
					f.click();
					break;
				}
			}
		}
	}
}