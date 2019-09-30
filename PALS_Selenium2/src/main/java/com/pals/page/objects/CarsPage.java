package com.pals.page.objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CarsPage extends BaseClass {
	@FindBy(xpath = ".//*[@id='al-sidebar-list']/ba-menu-item[4]/li/a")
	private WebElement Carsbar;
	@FindBy(xpath = ".//*[@id='page-wrapper']/div[1]/div/a")
	private WebElement AddCarButton;
	@FindBy(xpath = ".//*[@id='DataTables_Table_0_wrapper']/form/div[1]/div[1]/div/div/select")
	public WebElement SelectLoadNo;
	@FindBy(xpath = ".//*[@id='dealerCd']")
	public WebElement SelectDealerName;
	@FindBy(xpath = ".//*[@id='vin']")
	private WebElement vinno;
	@FindBy(xpath = ".//*[@id='loadSeq']")
	private WebElement loadSeq;
	@FindBy(xpath = ".//*[@id='yardId']")
	private WebElement yardId;
	@FindBy(xpath = ".//*[@id='colorDesc']")
	private WebElement colorDesc;
	@FindBy(xpath = ".//*[@id='vin_submit']")
	private WebElement SubmitcarButtton;
	@FindBy(xpath = ".//*[@id='select2-9tn1-container']")
	public WebElement SelectVinNo;
	@FindBy(xpath = ".//*[@id='dealerName']")
	public WebElement SelectFilterDealer;
	@FindBy(xpath = ".//*[@id='filter-submit']")
	private WebElement FilterSubmitButton;
	@FindBy(xpath = ".//*[@id='filter-reset']")
	private WebElement FilterResetButton;
	@FindBy(xpath = ".//*[@id='palsvins']/tbody/tr[1]/td[4]/button[1]")
	private WebElement CarDetailsViewButton;
	@FindBy(xpath = ".//*[@id='page-wrapper']/modal/div/div/div/div[1]/button")
	private WebElement CarPopUpCloseButton;
	@FindBy(xpath = ".//*[@id='palsvins']/tbody/tr[6]/td[4]/a/i")
	private WebElement CarDetailsEditButton;
	@FindBy(xpath = ".//*[@id='update_vinDesc']")
	public WebElement Update_VinDesc;
	@FindBy(xpath = ".//*[@id='update_parkingSpot']")
	public WebElement Update_ParkingSpot;
	@FindBy(xpath = ".//*[@id='palsDealers_filter']/label/input")
	public WebElement SearchFilter;
	@FindBy(xpath = ".//*[@id='palsvins']/tbody/tr[1]/td[4]/button[2]")
	public WebElement CarDeleteButton;
	@FindBy(xpath = ".//*[@id='ng2-popup-overlay']/div/ng2-message-popup/div/div[3]/button[1]")
	public WebElement CarDeleteConfirmButton;
	@FindBy(xpath = ".//*[@id='flashMessages']/div/p")
	public WebElement CarDeleteStatusMessage;
	@FindBy(xpath = ".//*[@id='flashMessages']/div/p")
	public WebElement CarUpdateStatusMessage;
	
	
	public CarsPage(WebDriver driver) {
		super(driver);
	}
	public void sidebarclick() {
		Carsbar.click();
	}

	public void addcar() {
		AddCarButton.click();
	}

	public void submitcar() {
		SubmitcarButtton.click();
	}

	public void filtersubmit() {
		FilterSubmitButton.click();
	}

	public void filterreset() {
		FilterResetButton.click();
	}

	public void cardetailsview() {
		CarDetailsViewButton.click();
	}

	public void carpopupclose() {
		CarPopUpCloseButton.click();
	}
	public void cardetailsedit() {
		CarDetailsEditButton.click();
	}
	public void cardelete()
	{
		CarDeleteButton.click();
	}
	public void cardeleteok()
	{
		CarDeleteConfirmButton.click();
	}
	public void enterData(String vinno, String loadSeq, String yardId,
			String colorDesc) {
		this.vinno.clear();
		this.vinno.sendKeys(vinno);
		this.loadSeq.sendKeys(loadSeq);
		this.yardId.sendKeys(yardId);
		this.colorDesc.sendKeys(colorDesc);
	}
	public void CarEditData(String colorDesc,String vinDesc, String lotBay) {
		this.Update_VinDesc.clear();
		this.Update_VinDesc.sendKeys(vinDesc);
		this.colorDesc.clear();
		this.colorDesc.sendKeys(colorDesc);
		this.Update_ParkingSpot.clear();
		this.Update_ParkingSpot.sendKeys(lotBay);
	}
}