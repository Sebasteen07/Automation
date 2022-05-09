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
	
	@FindBy(how = How.XPATH, using = "//input[@id='leadTimedays']")
	private WebElement leadDay;
	
	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Age Rule')]")
	private WebElement ageRuleCheckbox;

	@FindBy(how = How.XPATH, using = "//input[@id='myonoffswitch']")
	private WebElement ageRuleCheckboxStatus;

	@FindBy(how = How.XPATH, using = "//select[@name='leftToken']")
	private WebElement ageruleDropFirst;

	@FindBy(how = How.XPATH, using = "//select[@name='rightToken']")
	private WebElement ageruleDropSecond;

	@FindBy(how = How.XPATH, using = "//select[@name='condition']")
	private WebElement ageruleAnd;

	@FindBy(how = How.XPATH, using = "//input[@id='line1' and @name='leftVal']")
	private WebElement sendMonthFirst;

	@FindBy(how = How.XPATH, using = "//input[@id='line1' and @name='rightVal']")
	private WebElement sendMonthsecond;

	@FindBy(how = How.XPATH, using = "//*[contains(text(),'Max Per Day')]")
	private WebElement maxPerDay;

	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/a")
	private WebElement settingBtn;

	@FindBy(how = How.XPATH, using = "//*[@id='app']/nav[2]/ul[2]/li[4]/ul")
	private WebElement logoutBtn;
	
	@FindBy(how = How.XPATH, using = "//a[@title='Add Slots']")
	private WebElement excludeSlotBtn;
	
	@FindBy(how = How.XPATH, using = "//select[@name='beforeAfterStart']")
	private WebElement excludeSlotBeforeAfterStart;

	@FindBy(how = How.XPATH, using = "//select[@name='beforeAfterEnd']")
	private WebElement excludeSlotBeforeAfterEnd;

	@FindBy(how = How.XPATH, using = "//select[@name='condition']")
	private WebElement excludeSlotCondition;

	@FindBy(how = How.XPATH, using = "//input[@name='startTime']")
	private WebElement startTime;

	@FindBy(how = How.XPATH, using = "//input[@name='endTime']")
	private WebElement endTime;
	
	@FindBy(how = How.XPATH, using = "//a[@title='Add Exclude Slot']")
	private WebElement addExcludeSlotBtn;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tabX33']/form/fieldset[2]/div/div/button[1]")
	private WebElement appointmenttypeSave;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tabX33\"]/form/div[2]/table/tbody/tr[1]/td[2]/a")
	private WebElement excludeSlotCheckBox;	
	
	@FindBy(how = How.XPATH, using = "//input[@id='appointmentstacking']")
	private WebElement overBookingToggle;
	
	@FindBy(how = How.XPATH, using = "//input[@id='appointmentstacking']/following-sibling::i")
	private WebElement overBookingToggleClick;

	public ManageAppointmentType(WebDriver driver) {
		super(driver);
	}
	
	CommonMethods commonMethods = new CommonMethods(driver);
	
	public void searchByAptTypeName(String appointmentName) {
		searchAppointment.clear();
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
	
	public void resetPrevSchedSettings(String aptType) throws InterruptedException {
		
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
		prevSchedSettingAdmin.sendKeys("0");
		commonMethods.highlightElement(aptTypeSettingSaveBtn);
		aptTypeSettingSaveBtn.click();
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
	
	public void timeMark(String timeMarkValue) throws InterruptedException {
		Select selectOptions = new Select(timeMarkOption);
		selectOptions.selectByValue(timeMarkValue);
		timeMarkOption.click();
		scrollAndWait(0, 1000, 100);
		saveConfig.click();
	}
	
	public boolean acceptForStatus() {
		boolean bool = acceptToggle.isSelected();
		log("Status of Accept for the Same Day -" + bool);
		return bool;
	}

	public void notReserve() throws InterruptedException {
		Select objSelect = new Select(reservefor);
		objSelect.selectByVisibleText("Not Reserved");
		log("Not Reserved .............");
		reservefor.click();
		scrollAndWait(0, 1000, 100);
		saveConfig.click();
		log("Saved .............");
	}

	public void clickAcceptSameDay() {
		acceptToggleclick.click();
		saveConfig.click();

	}
	public void reserveForSameDay() throws InterruptedException {
		Select objSelect = new Select(reservefor);
		objSelect.selectByVisibleText("Same Day");
		reservefor.click();
		scrollAndWait(0, 800, 1000);
		saveConfig.click();
	}
	

	public void leadTime(String leadValue) throws InterruptedException {
		leadDay.clear();
		leadDay.sendKeys(leadValue);
		scrollAndWait(0, 1000, 100);
		saveConfig.click();
	}

	public boolean checkBoxStatus() {
		log("Status of the age rule checkbox  " + ageRuleCheckboxStatus.isSelected());
		return ageRuleCheckboxStatus.isSelected();
	}

	public void ageRule() {
		if (checkBoxStatus() == false) {
			ageRuleCheckbox.click();
			log("Clicked on Checkbox of Age Rule ");
		} else {
			log("Not clicked on Age Rule Check Box going to send value in textfield");
		}
	}

	public void resetAgeRule() throws InterruptedException {
		ageRuleCheckbox.click();
		log("Clicked On Age Rule");
		saveConfig.click();
	}

	public void ageRuleparameter(String ageStartMonth, String ageEndMonths) {
		Select select = new Select(ageruleDropFirst);
		Select and = new Select(ageruleAnd);
		Select select1 = new Select(ageruleDropSecond);
		select.selectByVisibleText(">");
		sendMonthFirst.clear();
		sendMonthFirst.sendKeys(ageStartMonth);
		and.selectByIndex(1);
		select1.selectByVisibleText("<");
		sendMonthsecond.clear();
		sendMonthsecond.sendKeys(ageEndMonths);
		log("SuccessFully Sent the Values in ageRule textfield");
		saveConfig.click();
	}

	public int maxPerDaySize() {
		List<WebElement> aptNameList = driver.findElements(By.xpath("//*[contains(text(),'Max Per Day')]"));
		int size = aptNameList.size();
		return size;
	}

	public boolean maxPerDayStatus() {
		if (maxPerDaySize() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void logout() throws InterruptedException {
		settingBtn.click();
		logoutBtn.click();
	}
	
	public void excludeBtnWithTwoValues(String firstValue,String secondValue)
	{
		excludeSlotBtn.click();
		Select before = new Select(excludeSlotBeforeAfterStart);
		before.selectByVisibleText("Before");
		startTime.clear();
		startTime.sendKeys(firstValue);
		log("SuccessFully Sent the First Value in Exclude Slot textfield");
		addExcludeSlotBtn.click();
		log("Clicked on yes ");
		excludeSlotBtn.click();
		before.selectByVisibleText("After");
		startTime.clear();
		startTime.sendKeys(secondValue);
		log("SuccessFully Sent the Second Value in Exclude Slot textfield");
		addExcludeSlotBtn.click();
		log("Clicked on yes ");
		appointmenttypeSave.click();
	}
	
	public void resetExcludeBtn() {
		excludeSlotCheckBox.click();
		log("Exclude Btn reset");
		appointmenttypeSave.click();	
	}
	public boolean overBookingStatus() {
		boolean bool = overBookingToggle.isSelected();
		log("Status of OverBooking -" + bool);
		return bool;
	}

	public void overBookingClick() {
		overBookingToggleClick.click();
		appointmenttypeSave.click();
	}
}
