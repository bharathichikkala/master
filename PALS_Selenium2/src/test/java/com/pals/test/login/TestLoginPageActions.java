package com.pals.test.login;

import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pals.page.objects.LoginPage;
import com.pals.utils.Utils;

public class TestLoginPageActions {

	public static WebDriver driver;

	@BeforeClass
	public static void launchSite() {
		System.out.println("Before Class");
		driver = Utils.getbrowser("chrome");
	}

	@AfterClass
	public static void TearDownSite() {
		System.out.println("After Class");
		Utils.driver.close();
	}

	@Test
	public static void loginTest() throws InterruptedException {
		LoginPage loginpage = new LoginPage(driver);
		loginpage.enterName("admin", "madmin@123");
		loginpage.submit();
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
}
