//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.AppointmentType;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class ManageAppointmentType extends PSS2MenuPage {

	@FindBy(how = How.ID, using = "search-appointmenttype")
	private WebElement searchAppointment;

	@FindAll({@FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr")})
	private List<WebElement> appointmentTypeList;

	@FindAll({@FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr/td/span/a")})
	private List<WebElement> appointmentTypeNameList;

	@FindBy(how = How.XPATH, using = "//table[@class=\"table table-hover \"]/tbody/tr/td[3]/a")
	private WebElement aptTypeClose;

	@FindBy(how = How.ID, using = "name")
	private WebElement aptTypeName;

	@FindBy(how = How.ID, using = "extAppointmentTypeId")
	private WebElement aptTypeID;

	@FindBy(how = How.ID, using = "appointmentType.displayNamesEN")
	private WebElement aptTypeDisplayName;

	@FindBy(how = How.ID, using = "categoryName")
	private WebElement aptTypeCategoryName;
	
	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td/span")
	private WebElement aptTypeLink;

	@FindBy(how = How.XPATH, using = "//label[normalize-space()='Prevent Scheduling appointment type within (Days)']")
	private WebElement prevSchedSettingLabel;
	
	@FindBy(how = How.XPATH, using = "//input[@id='appointmentType.preventScheduling']")
	private WebElement prevSchedSettingAdmin;
	
	@FindBy(how = How.XPATH, using = "//form[@role='form']//fieldset//div//div//button[@type='submit'][normalize-space()='Save']")
	private WebElement aptTypeSettingSaveBtn;
	
	@FindBy(how = How.XPATH, using = "//header/h4")
	private WebElement editAptTypeHeading;	
	
	@FindBy(how = How.XPATH, using = "//div[@id='toast-container']")
	private WebElement aptTypeSaveMsg;	
	
	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td/span/span/a")
	private WebElement selectAppointment;	
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[4]/a")
	private WebElement goConfiguration;	
	
	@FindBy(how = How.XPATH, using = "//*[@name='apptTimeMark']")
	private WebElement timeMarkOption;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabX33']/form/fieldset[2]/div/div/button[1]")
	private WebElement saveConfig;
	
	@FindBy(how = How.XPATH, using = "//label[@for='allowSameDayAppts']//input")
	private WebElement acceptToggle;

	@FindBy(how = How.XPATH, using = "//*[@name='apptTypeReservedReason']")
	private WebElement reservefor;

	@FindBy(how = How.XPATH, using = "//div[@class='col-md-12']//label[@for='allowSameDayAppts']/i")
	private WebElement acceptToggleclick;

	public ManageAppointmentType(WebDriver driver) {
		super(driver);
	}
	
	CommonMethods commonMethods = new CommonMethods(driver);
	
	public void searchByAptTypeName(String appointmentName) {
		searchAppointment.sendKeys(appointmentName);
	}


	public List<WebElement> aptNameList() {
		IHGUtil.waitForElement(driver, 120, searchAppointment);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		List<WebElement> aptNameList = driver.findElements(By.xpath("//*[@class=\"table table-hover \"]/tbody/tr/td/span/a"));
		return aptNameList;
	}
	
	public void prevSchedSettings(String aptType, int i) throws InterruptedException {
		
		commonMethods.highlightElement(searchAppointment);
		searchAppointment.sendKeys(aptType);
		commonMethods.highlightElement(aptTypeLink);
		aptTypeLink.click();
		IHGUtil.waitForElement(driver, 10, editAptTypeHeading);
		commonMethods.highlightElement(editAptTypeHeading);
		scrollAndWait(0, 800, 1000);
		commonMethods.highlightElement(prevSchedSettingLabel);
		log("PrevSched Setting Label- "+prevSchedSettingLabel.getText());
		
		commonMethods.highlightElement(prevSchedSettingAdmin);
		prevSchedSettingAdmin.clear();
		prevSchedSettingAdmin.sendKeys(Integer.toString(i));
		commonMethods.highlightElement(aptTypeSettingSaveBtn);
		aptTypeSettingSaveBtn.click();
		IHGUtil.waitForElement(driver, 1, aptTypeSaveMsg);
		
		String appointmentTypeSavedMsg=aptTypeSaveMsg.getText();
		
		log("Appointment Type Saved Message- "+appointmentTypeSavedMsg);
		log("Length of Saved Message- "+appointmentTypeSavedMsg.length());
		
	}
	
	public void selectAppointment(String appointment) throws InterruptedException {
		searchByAptTypeName(appointment);
		IHGUtil.waitForElement(driver, 60, selectAppointment);
		selectAppointment.click();
		log("clicked on Appointment");
	} 
	public void gotoConfiguration()
	{
		log("Click on the Configuration");
		goConfiguration.click();
		log("Clicked on the Configuration");

	}
	
	public void timeMark(String timeMarkValue) {
		Select selectOptions = new Select(timeMarkOption);
		selectOptions.selectByValue(timeMarkValue);
		timeMarkOption.click();
		saveConfig.click();
	}
	
	public boolean acceptForStatus() {
		boolean bool = acceptToggle.isSelected();
		log("Status of Accept for the Same Day -" + bool);
		return bool;
	}

	public void notReserve() {
		Select objSelect = new Select(reservefor);
		objSelect.selectByVisibleText("Not Reserved");
		reservefor.click();
		saveConfig.click();
	}

	public void clickAcceptSameDay() {
		acceptToggleclick.click();
		saveConfig.click();

	}

}
