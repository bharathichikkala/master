package com.pals.page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseClass {
	@FindBy(id = "inputEmail3")
	private WebElement userName;
	@FindBy(id = "inputPassword3")
	private WebElement passWord;
	@FindBy(xpath = "html/body/app/main/ng-component/div/div/form/div[3]/div/button")
	private WebElement submitButton;

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void enterName(String userName, String passWord) {
		this.userName.clear();
		this.userName.sendKeys(userName);
		this.passWord.sendKeys(passWord);
	}

	public void submit() {
		submitButton.click();
	}
}
