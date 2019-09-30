package com.pals.test.cars;

import java.io.IOException;

import org.junit.AfterClass;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pals.page.objects.CarsPage;
import com.pals.utils.Constant;
import com.pals.utils.Utils;

import jxl.read.biff.BiffException;

public class TestCarsDetailsPage {
	public static WebDriver driver;

	@BeforeClass
	public static void launchSite() {
		System.out.println("Before Class");
		driver = Utils.driver;
	}

	@AfterClass
	public static void TearDownSite() {
		System.out.println("After Class");
		Utils.driver.close();
	}

	@Test(priority = 1)
	public static void filtercardetailsTest() throws Exception {
		CarsPage carspage = new CarsPage(driver);
		carspage.sidebarclick();
		String dealername = Constant.getDataFromXLDocument("CarDetails", 1, 1);
		Thread.sleep(1000);
		Utils.selectElementByvisibletext(carspage.SelectFilterDealer,
				dealername);
		Thread.sleep(1000);
		carspage.filtersubmit();
		Thread.sleep(1000);
		carspage.filterreset();
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

	@Test(priority = 2)
	public static void cardeatilsView() throws InterruptedException,
			BiffException, IOException {
		CarsPage carspage = new CarsPage(driver);
		Thread.sleep(1000);
		carspage.cardetailsview();
		Thread.sleep(1000);
		carspage.carpopupclose();
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

	@Test(priority = 3)
	public static void cardetailsEdit() throws Exception {
		CarsPage carspage = new CarsPage(driver);
		Thread.sleep(1000);
		carspage.cardetailsedit();
		Thread.sleep(1000);
		String colorDesc = Constant.getDataFromXLDocument("CarDetails", 2, 1);
		String vinDesc = Constant.getDataFromXLDocument("CarDetails", 3, 1);
		String lotBay = Constant.getDataFromXLDocument("CarDetails", 4, 1);
		carspage.CarEditData(colorDesc, vinDesc, lotBay);
		Thread.sleep(1000);
		carspage.submitcar();
		String statusmsg=carspage.CarUpdateStatusMessage.getText();
		String msg = Constant.getDataFromXLDocument("StatusMessage",1, 1);
		Assert.assertEquals(statusmsg,msg);
		String searchkey = Constant.getDataFromXLDocument("CarDetails", 5, 1);
		Thread.sleep(5000);
		carspage.SearchFilter.sendKeys(searchkey);
		Thread.sleep(2000);
		carspage.SearchFilter.sendKeys(Keys.BACK_SPACE);
		carspage.SearchFilter.sendKeys(Keys.BACK_SPACE);
		carspage.SearchFilter.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
	@Test(priority = 4)
	public static void cardetailsDelete() throws Exception {
		CarsPage carspage = new CarsPage(driver);
		Thread.sleep(1000);
		carspage.cardelete();
		Thread.sleep(5000);
        carspage.cardeleteok();
        String statusmsg=carspage.CarDeleteStatusMessage.getText();
        String msg = Constant.getDataFromXLDocument("StatusMessage",1,2);
        Assert.assertEquals(statusmsg,msg);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
}