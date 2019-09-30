package com.pals.page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pals.utils.Utils;

public class LoadsPage extends BaseClass {

	public LoadsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = ".//*[@id='al-sidebar-list']/ba-menu-item[2]/li/a")
	private WebElement loadsMenuItem;

	@FindBy(xpath = ".//*[@id='loads-page']/div[1]/div/a")
	private WebElement addLoadButton;

	@FindBy(xpath = ".//*[@id='loadNum']")
	private WebElement newLoadNum;

	@FindBy(xpath = ".//*[@id='select2-b3vc-container']")
	public WebElement newLoadDriver;

	@FindBy(xpath = ".//*[@id='driverEmail']")
	private WebElement newLoadDriverEmail;

	@FindBy(xpath = ".//*[@id='skidDrops']")
	private WebElement newLoadSkidDrops;

	@FindBy(xpath = ".//*[@id='dealersList']/span/span[1]/span")
	public WebElement newLoadDealers;

	@FindBy(xpath = ".//*[@id='dealerAddress']")
	private WebElement newLoadDealerAddress;

	@FindBy(xpath = ".//*[@id='trkNum']")
	private WebElement newLoadTruckNum;

	@FindBy(xpath = ".//*[@id='load_high_value']")
	private WebElement newLoadHighValue;

	@FindBy(xpath = ".//*[@id='load_high_priority']")
	private WebElement newLoadHighPriority;

	@FindBy(xpath = ".//*[@id='load_submit']")
	private WebElement submitUpdateLoadDetails;

	@FindBy(xpath = ".//*[@id='DataTables_Table_0_wrapper']/form/div/div[8]/div[1]/button[2]")
	private WebElement cancelUpdateLoadDetails;

	@FindBy(xpath = ".//*[@id='startDate']")
	public WebElement startDateFilter;

	@FindBy(xpath = ".//*[@id='endDate']")
	public WebElement endDateFilter;

	@FindBy(xpath = ".//*[@id='load-status']")
	public WebElement loadStatusFilter;

	@FindBy(xpath = ".//*[@id='load_high_value']")
	private WebElement loadHighValueFilter;

	@FindBy(xpath = ".//*[@id='select2-5vb5-container']")
	private WebElement vinNumFilter;

	@FindBy(xpath = ".//*[@id='select2-zanm-container']")
	public WebElement loadNumFilter;

	@FindBy(xpath = ".//*[@id='empID']")
	private WebElement driverFilter;

	@FindBy(xpath = ".//*[@id='load_high_priority']")
	public WebElement loadHighPriorityFilter;

	@FindBy(xpath = ".//*[@id='filter-submit']")
	private WebElement submitFiltersButton;

	@FindBy(xpath = ".//*[@id='filter-reset']")
	private WebElement resetFilterButton;

	@FindBy(xpath = ".//*[@id='palsDealers_filter']/label/input")
	public WebElement searchLoadButton;

	@FindBy(xpath = ".//*[@id='palsloads']/tbody/tr[1]/td[7]/button")
	private WebElement viewLoadDetailsButton;

	@FindBy(xpath = ".//*[@id='page-wrapper']/modal/div/div/div/div[1]/button")
	private WebElement viewLoadDetailsCloseButton;

	@FindBy(xpath = ".//*[@id='palsloads']/tbody/tr[1]/td[7]/span[1]/a/i")
	private WebElement editLoadDetailsButton;

	@FindBy(xpath = ".//*[@id='select2-2bbx-container']")
	private WebElement updateLoadDriver;

	@FindBy(xpath = ".//*[@id='email']")
	private WebElement updateLoadDriverEmail;

	@FindBy(xpath = ".//*[@id='skidDrops']")
	private WebElement upadateLoadSkidDrops;

	@FindBy(xpath = ".//*[@id='document_updateDealerOptions']/span/span[1]/span/ul")
	private WebElement updateLoadDealers;

	@FindBy(xpath = ".//*[@id='dealerAddress']")
	private WebElement updateLoadDealerAddress;

	@FindBy(xpath = ".//*[@id='trkNum']")
	public WebElement updateLoadTruckNum;

	@FindBy(xpath = ".//*[@id='load_high_value']")
	private WebElement updateLoadHighValue;

	@FindBy(xpath = ".//*[@id='load_high_priority']")
	private WebElement updateLoadHighPriority;

	@FindBy(xpath = ".//*[@id='load_submit']")
	private WebElement submitNewLoadDetails;

	@FindBy(xpath = ".//*[@id='DataTables_Table_0_wrapper']/form/div/div[8]/div[1]/button[2]")
	private WebElement cancelNewLoadDetails;

	@FindBy(xpath = ".//*[@id='palsloads']/tbody/tr[1]/td[7]/span[2]/button")
	public WebElement deleteLoadButton;

	@FindBy(xpath = ".//*[@id='ng2-popup-overlay']/div/ng2-message-popup/div/div[3]/button[1]")
	public WebElement deleteLoadOKButton;

	@FindBy(xpath = ".//*[@id='ng2-popup-overlay']/div/ng2-message-popup/div/div[3]/button[2]")
	public WebElement deleteLoadCancelButton;
	
	@FindBy(xpath = ".//*[@id='palsloads']/tbody/tr[1]/td[7]/span[3]/button")
	public WebElement pushLoadButton;
	
	@FindBy(xpath = ".//*[@id='palsDealers_filter']/label/input")
	public WebElement SearchFilter;
	
	public void goToLoadsPage() {
		loadsMenuItem.click();
	}

	public void goToAddLoadPage() {
		addLoadButton.click();
	}

	public void addLoadDetails() {
		submitNewLoadDetails.click();
	}

	public void submitFilters() {
		submitFiltersButton.click();
	}
	
	public void submitLoadDetails() {
		submitUpdateLoadDetails.click();
	}

	public void resetFilters() {
		resetFilterButton.click();
	}

	public void viewLoadDetails() {
		viewLoadDetailsButton.click();
	}

	public void closeLoadDetails() {
		viewLoadDetailsCloseButton.click();
	}

	public void editLoadDetails() {
		editLoadDetailsButton.click();
	}
	
	public void updateLoadDetails() {
		submitUpdateLoadDetails.click();
	}
	
	public void cancelUpdateLoadDetails() {
		cancelUpdateLoadDetails.click();
	}

	public void deleteLoadDetails() {
		deleteLoadButton.click();
	}

	public void deleteLoadOK() {
		deleteLoadOKButton.click();
	}
	
	public void deleteLoadCancel() {
		deleteLoadCancelButton.click();
	}
	
	public void pushLoad() {
		pushLoadButton.click();
	}

	public void enterNewLoadData(String loadNum, String driver, String skidDrops, String dealers, String truckNum) {
		this.newLoadNum.clear();
		this.newLoadNum.sendKeys(loadNum);
		this.newLoadSkidDrops.sendKeys(skidDrops);
		this.newLoadTruckNum.sendKeys(truckNum);
		
	}

	public void editLoadData(String truckNum) {
		this.updateLoadTruckNum.clear();
		this.updateLoadTruckNum.sendKeys(truckNum);
	}
}