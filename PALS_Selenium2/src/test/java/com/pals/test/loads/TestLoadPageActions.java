package com.pals.test.loads;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pals.page.objects.LoadsPage;
import com.pals.utils.Utils;

public class TestLoadPageActions {

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
	public static void filterLoads() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		loadsPage.goToLoadsPage();
		String loadStatus = "Driver Login";
		Thread.sleep(1000);
		Utils.selectElementByvisibletext(loadsPage.loadStatusFilter, loadStatus);
		Thread.sleep(1000);
		loadsPage.submitFilters();
		Thread.sleep(1000);
		loadsPage.resetFilters();
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
	
	/*@Test(priority = 2)
	public static void viewLoadDetails() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(2000);
		loadsPage.viewLoadDetails();
		Thread.sleep(1000);
		loadsPage.closeLoadDetails();
		Thread.sleep(1000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

	@Test(priority = 3)
	public static void deleteLoadCancel() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(1000);
		loadsPage.deleteLoadDetails();
		Thread.sleep(1000);
		loadsPage.deleteLoadCancel();
		Thread.sleep(1000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

	@Test(priority = 4)
	public static void deleteLoadOK() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(1000);
		loadsPage.deleteLoadDetails();
		Thread.sleep(1000);
		loadsPage.deleteLoadOK();
		Thread.sleep(1000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

	@Test(priority = 5)
	public static void pushLoad() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(5000);
		loadsPage.pushLoad();
		Thread.sleep(1000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
	
	@Test(priority = 6)
	public static void submitUpdatedLoadDetails() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(1000);
		loadsPage.editLoadDetails();
		Thread.sleep(3000);
		String truckNum = "5665448798";
		loadsPage.editLoadData(truckNum);
		Thread.sleep(1000);
		loadsPage.updateLoadDetails();
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
	
	@Test(priority = 7)
	public static void cancelUpdatedLoadDetails() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(1000);
		loadsPage.editLoadDetails();
		Thread.sleep(3000);
		String truckNum = "5665448798";
		loadsPage.editLoadData(truckNum);
		Thread.sleep(1000);
		loadsPage.cancelUpdateLoadDetails();
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}*/
	
	@Test(priority = 2)
	public static void addLoadDetails() throws Exception {
		LoadsPage loadsPage = new LoadsPage(driver);
		Thread.sleep(1000);
		loadsPage.goToAddLoadPage();
		Thread.sleep(3000);
		loadsPage.enterNewLoadData("1236625625", "BABA", "1", "AutoNation Inc", "1596258746");
		Thread.sleep(3000);
		Utils.selectElementByvisibletext(loadsPage.newLoadDriver, "Warren Rina");
		Utils.selectElementByvisibletext(loadsPage.newLoadDealers, "AutoNation Inc");
		loadsPage.addLoadDetails();
		System.out.println("After Class");
		Assert.assertTrue(true);
	}

}
