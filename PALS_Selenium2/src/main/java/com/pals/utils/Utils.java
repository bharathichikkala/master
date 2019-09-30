package com.pals.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;
import com.pals.page.objects.BaseClass;

public class Utils extends BaseClass {
	public Utils(WebDriver driver) {
		super(driver);
	}

	public static WebDriver driver = BaseClass.driver;

	public static WebDriver getbrowser(String browser) {
		switch (browser) {
		case "firefox": {
			try {
				Path resourceDirectory = Paths.get("src/test/resources");
				System.setProperty("webdriver.gecko.driver", resourceDirectory+"\\"+Constant.FirefoxDriverPath);
				driver = new FirefoxDriver();
				Log.info("New driver instantiated");
				driver.manage().window().maximize();
				Log.info("Implicit wait applied on the driver for 10 seconds");
				driver.get(Constant.getApplicationUrl());
				Log.info("Web application launched successfully");

			} catch (Exception e) {
				Log.error("Class Utils | Method OpenBrowser | Exception desc : " + e.getMessage());
			}
		}
			break;
		case "chrome": {
			try {
				
				Path resourceDirectory = Paths.get("src/test/resources");
				System.setProperty("webdriver.chrome.driver", resourceDirectory+"\\"+Constant.ChromeDriverPath);
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new ChromeDriver(capability);
				Log.info("New driver instantiated");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				Log.info("Implicit wait applied on the driver for 10 seconds");
				System.out.println(Constant.getApplicationUrl());
				driver.get(Constant.getApplicationUrl());
				Log.info("Web application launched successfully");

			} catch (Exception e) {
				Log.error("Class Utils | Method OpenBrowser | Exception desc : " + e.getMessage());
			}

		}
			break;
		case "IE": {
			try {
				System.setProperty("webdriver.ie.driver", Constant.IEDriverPath);
				driver = new InternetExplorerDriver();
				Log.info("New driver instantiated");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				Log.info("Implicit wait applied on the driver for 10 seconds");
				driver.get(Constant.getApplicationUrl());
				driver.navigate().to("javascript:document.getElementById('overridelink').click()");
				Log.info("Web application launched successfully");

			} catch (Exception e) {
				Log.error("Class Utils | Method OpenBrowser | Exception desc : " + e.getMessage());
			}
		}
			break;
		default:
			System.out.println("Incorrect browser defined");
		}
		return driver;
	}
	
	public String getFilePath(String fileName){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("somefile").getFile());
		return file.getAbsolutePath();
	}

	public static void takeScreenshot(String screenshotName) throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(Constant.screenshotPath + screenshotName + ".png"));
	}

	public static void switchToNewWindow() {
		Set s = driver.getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		driver.switchTo().window(w2);
	}

	public static void switchToOldWindow() {
		Set s = driver.getWindowHandles();
		Iterator itr = s.iterator();
		String w1 = (String) itr.next();
		String w2 = (String) itr.next();
		driver.switchTo().window(w1);
	}

	public static void switchToParentWindow() {
		driver.switchTo().defaultContent();
	}

	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;
		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");
			value = value.substring(posi + 1);
			return value;
		} catch (Exception e) {
			Log.error("Class Utils | Method getTestCaseName | Exception desc : " + e.getMessage());
			throw (e);
		}
	}

	public static void waitForElementToBeClickable(Predicate<WebDriver> expectedConditions) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		// wait.until(expectedConditions);
	}

	public static void waitForElement(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitMyTime(int i) {
		driver.manage().timeouts().implicitlyWait(i, TimeUnit.SECONDS);

	}

	public static void highlightelement(WebElement element) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: solid red; border: 5px solid blue;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");

		}

	}

	public static void takeScreenshotOfWebelement(WebDriver driver, WebElement element, String screenshotName)
			throws Exception {
		File v = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage bi = ImageIO.read(v);
		org.openqa.selenium.Point p = element.getLocation();
		int n = element.getSize().getWidth();
		int m = element.getSize().getHeight();
		BufferedImage d = bi.getSubimage(p.getX(), p.getY(), n, m);
		ImageIO.write(d, "png", v);

		FileUtils.copyFile(v, new File(Constant.screenshotPath + screenshotName + ".png"));
	}

	public static void setWindowSize(int Dimension1, int dimension2) {
		driver.manage().window().setSize(new Dimension(Dimension1, dimension2));

	}

	public static void pressKeyDown(WebElement element) {
		element.sendKeys(Keys.DOWN);
	}

	public void pressKeyEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	public static void pressKeyUp(WebElement element) {
		element.sendKeys(Keys.UP);
	}

	public static void moveToTab(WebElement element) {
		element.sendKeys(Keys.chord(Keys.ALT, Keys.TAB));
	}

	public static void clickAllLinksInPage(String NameOfScreenshot) throws Exception {

		List<WebElement> Links = driver.findElements(By.tagName("a"));
		System.out.println("Total number of links  :" + Links.size());

		for (int p = 0; p < Links.size(); p++) {
			System.out.println("Elements present the body :" + Links.get(p).getText());
			Links.get(p).click();
			Thread.sleep(3000);
			System.out.println("Url of the page  " + p + ")" + driver.getCurrentUrl());
			takeScreenshot(NameOfScreenshot + "_" + p);
			navigate_back();
			Thread.sleep(2000);
		}
	}

	public static void navigate_forward() {
		driver.navigate().forward();
	}

	public static void clearTextField(WebElement element) {
		element.clear();

	}

	public static void clickWebelement(WebElement element) {
		try {
			boolean elementIsClickable = element.isEnabled();
			while (elementIsClickable) {
				element.click();
			}

		} catch (Exception e) {
			System.out.println("Element is not enabled");
			e.printStackTrace();
		}
	}

	public static void clickMultipleElements(WebElement someElement, WebElement someOtherElement) {
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.CONTROL).click(someElement).click(someOtherElement).keyUp(Keys.CONTROL).build().perform();
	}

	public static void dragAndDrop(WebElement fromWebElement, WebElement toWebElement) {
		Actions builder = new Actions(driver);
		builder.dragAndDrop(fromWebElement, toWebElement);
	}

	public static void dragAndDrop_Method2(WebElement fromWebElement, WebElement toWebElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(fromWebElement).moveToElement(toWebElement).release(toWebElement)
				.build();
		dragAndDrop.perform();
	}

	public static void dragAndDrop_Method3(WebElement fromWebElement, WebElement toWebElement)
			throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.clickAndHold(fromWebElement).moveToElement(toWebElement).perform();
		Thread.sleep(2000);
		builder.release(toWebElement).build().perform();
	}

	public static void hoverWebelement(WebElement HovertoWebElement) throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.moveToElement(HovertoWebElement).perform();
		Thread.sleep(2000);

	}

	public static void doubleClickWebelement(WebElement doubleclickonWebElement) throws InterruptedException {
		Actions builder = new Actions(driver);
		builder.doubleClick(doubleclickonWebElement).perform();
		Thread.sleep(2000);

	}

	public static String getToolTip(WebElement toolTipofWebElement) throws InterruptedException {
		String tooltip = toolTipofWebElement.getAttribute("title");
		System.out.println("Tool text : " + tooltip);
		return tooltip;
	}

	public static void selectElementByvisibletext(WebElement element, String Name) {
		Select selectitem = new Select(element);
		selectitem.selectByVisibleText(Name);
	}

	public static void selectElementByValueMethod(WebElement element, String value) {
		Select selectitem = new Select(element);
		selectitem.selectByValue(value);
	}

	public static void selectElementByIndexMethod(WebElement element, int index) {
		Select selectitem = new Select(element);
		selectitem.selectByIndex(index);
	}

	public static void navigate_back() {
		driver.navigate().back();
	}

	public static void refresh() {
		driver.navigate().refresh();
	}

	public static void clickCheckboxFromList(String xpathOfElement, String valueToSelect) {

		List<WebElement> lst = driver.findElements(By.xpath(xpathOfElement));
		for (int i = 0; i < lst.size(); i++) {
			List<WebElement> dr = lst.get(i).findElements(By.tagName("label"));
			for (WebElement f : dr) {
				System.out.println("value in the list : " + f.getText());
				if (valueToSelect.equals(f.getText())) {
					f.click();
					break;
				}
			}
		}
	}

	public static void downloadFile(String href, String fileName) throws Exception {
		URL url = null;
		URLConnection con = null;
		int i;
		url = new URL(href);
		con = url.openConnection();
		File file = new File(".//OutputData//" + fileName);
		BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		while ((i = bis.read()) != -1) {
			bos.write(i);
		}
		bos.flush();
		bis.close();
	}

	public static boolean checkAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static boolean acceptAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			a.accept();
			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static boolean dismissAlert() {
		try {
			Alert a = driver.switchTo().alert();
			String str = a.getText();
			System.out.println(str);

			a.dismiss();
			return true;

		} catch (Exception e) {

			System.out.println("no alert ");
			return false;

		}
	}

	public static void scrolltoElement(WebElement ScrolltoThisElement) {
		Coordinates coordinate = ((Locatable) ScrolltoThisElement).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	public static void checkbox_Checking(WebElement checkbox) {
		boolean checkstatus;
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			System.out.println("Checkbox is already checked");
		} else {
			checkbox.click();
			System.out.println("Checked the checkbox");
		}
	}

	public static void radiobutton_Select(WebElement Radio) {
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			System.out.println("RadioButton is already checked");
		} else {
			Radio.click();
			System.out.println("Selected the Radiobutton");
		}
	}

	// Unchecking
	public static void checkbox_Unchecking(WebElement checkbox) {
		boolean checkstatus;
		checkstatus = checkbox.isSelected();
		if (checkstatus == true) {
			checkbox.click();
			System.out.println("Checkbox is unchecked");
		} else {
			System.out.println("Checkbox is already unchecked");
		}
	}

	public static void radioButton_Deselect(WebElement Radio) {
		boolean checkstatus;
		checkstatus = Radio.isSelected();
		if (checkstatus == true) {
			Radio.click();
			System.out.println("Radio Button is deselected");
		} else {
			System.out.println("Radio Button is already Deselected");
		}
	}

	public static void passThisStep(String reasonForPass) {
		Assert.assertTrue(reasonForPass, true);
	}

	public static void failThisStep(String reasonForFailing) {
		Assert.fail(reasonForFailing);
	}

	public static void datepicker_setDateAndTime(String TypeMonth, String TypeYear, String Date, String setHour,
			String setMinute, String setSeconds, String setHourShift) {
		List<WebElement> date = driver.findElements(By.xpath(".//*[@id='ui-datepicker-div']"));
		System.out.println("number of datepickers present : " + date.size());
		for (int i = 0; i < date.size(); i++) {
			System.out.println("element present in the Date picker box " + date.get(i).getText());
			Select month = new Select(date.get(i).findElement(
					By.xpath("//select[@class='form-control datetime-ui-datepicker-month input-width-20']")));
			month.selectByVisibleText(TypeMonth);
			Select year = new Select(date.get(i).findElement(
					By.xpath("//select[@class='form-control datetime-ui-datepicker-year input-width-20']")));
			year.selectByVisibleText(TypeYear);

			driver.findElement(By.linkText(Date)).click();

			date.get(i).findElement(By.xpath("//input[contains(@id,'hour')]")).clear();
			date.get(i).findElement(By.xpath("//input[contains(@id,'minute')]")).clear();
			date.get(i).findElement(By.xpath("//input[contains(@id,'second')]")).clear();

			date.get(i).findElement(By.xpath("//input[contains(@id,'hour')]")).sendKeys(setHour);
			date.get(i).findElement(By.xpath("//input[contains(@id,'minute')]")).sendKeys(setMinute);
			date.get(i).findElement(By.xpath("//input[contains(@id,'second')]")).sendKeys(setSeconds);
			Select hourshift = new Select(date.get(i).findElement(By.xpath("//select[@class='hourShift']")));
			hourshift.selectByVisibleText(setHourShift);
			driver.findElement(By.xpath(".//*[@id='ui-datepicker-div']/div[4]/button")).click();

		}

	}

	public static boolean isClickable(WebElement webe) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(webe));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
