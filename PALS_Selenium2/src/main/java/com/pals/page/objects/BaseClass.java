package com.pals.page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BaseClass {

	public static WebDriver driver;
	public static boolean bresult;

	public BaseClass(WebDriver driver) {
		BaseClass.driver = driver;
		BaseClass.bresult = true;
		PageFactory.initElements(driver, this);
	}
}
