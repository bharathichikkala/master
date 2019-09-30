package com.pals.test.cars;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pals.page.objects.CarsPage;
import com.pals.page.objects.LoginPage;
import com.pals.utils.Constant;
import com.pals.utils.ReadExcelData;
import com.pals.utils.Utils;
import com.sun.corba.se.impl.javax.rmi.CORBA.Util;
import com.sun.org.apache.regexp.internal.REDebugCompiler;

public class TestCarsPageActions {
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
	@Test
	public static void addcarTest() throws Exception {
		CarsPage carspage=new CarsPage(driver);
		carspage.sidebarclick();
		Thread.sleep(2000);
		carspage.addcar();
		Thread.sleep(1000);
        String loadnum=Constant.getDataFromXLDocument("AddCar",1,1);
        String dealername=Constant.getDataFromXLDocument("AddCar",2,1);
		Utils.selectElementByvisibletext(carspage.SelectLoadNo,loadnum);
		Utils.selectElementByvisibletext(carspage.SelectDealerName,dealername);
		String vinNo=Constant.getDataFromXLDocument("AddCar",3,1);
		String loadSeq=Constant.getDataFromXLDocument("AddCar",4,1);
		String yardId=Constant.getDataFromXLDocument("AddCar",5,1);
		String colorDesc=Constant.getDataFromXLDocument("AddCar",6,1);
		carspage.enterData(vinNo,loadSeq,yardId,colorDesc);
		carspage.submitcar();
		Thread.sleep(2000);
		System.out.println("After Class");
		Assert.assertTrue(true);
	}
}