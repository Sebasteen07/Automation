// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.Resource;

import java.util.List;

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

public class ManageResource extends PSS2MenuPage {

	@FindBy(how = How.CSS, using = ".table.table-hover ")
	private WebElement resourceTable;

	@FindBy(how = How.ID, using = "search-resource")
	private WebElement searchResource;

	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td[2]/span/a")
	private WebElement searchedResourceName;

	@FindAll({ @FindBy(xpath = "//*[@class=\"table table-hover \"]/tbody/tr") })
	private List<WebElement> locationRowList;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'General')]")
	private WebElement editGeneralTab;

	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[2]/a")
	private WebElement editLocationTab;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Specialty')]")
	private WebElement editSpecialityTab;

	@FindBy(how = How.XPATH, using = "//*[@id='tabs3']/li[4]/a")
	private WebElement editAptTypeTab;

	@FindBy(how = How.NAME, using = "slotSize")
	private WebElement slotSizeValue;

	@FindBy(how = How.ID, using = "status")
	private WebElement resourceActiveState;

	@FindBy(how = How.ID, using = "sharePatients")
	private WebElement resourceSharePatients;

	@FindBy(how = How.XPATH, using = "//*[@id='tab13']/form/fieldset[1]/div[10]/div/label[1]/i")
	private WebElement resoureceSharePatientToggle;

	@FindBy(how = How.ID, using = "search-specialty")
	private WebElement resourceSearchSpeciality;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab33\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceSpecilaityEnabled;

	@FindBy(how = How.ID, using = "search-appointmenttype")
	private WebElement resourceSearchApt;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab43\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceAptEnabled;

	@FindBy(how = How.ID, using = "acceptComment")
	private WebElement resourceAcceptsComments;

	@FindBy(how = How.ID, using = "isageRule")
	private WebElement resourceAgeRuleChecked;

	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	private WebElement resourceSave;
	
	@FindBy(how = How.XPATH, using = "//a[normalize-space()='Location']")
	private WebElement resourceLocationTab;	

	@FindBy(how = How.XPATH, using = "//button[@type=\"button\"]")
	private WebElement resourceCancel;

	@FindBy(how = How.ID, using = "search-location")
	private WebElement resourceSearchLocation;

	@FindBy(how = How.XPATH, using = "//*[@id=\"tab23\"]/table/tbody/tr[1]/td[3]/div/label/input")
	private WebElement resourceSearchedLocation;

	@FindBy(how = How.XPATH, using = "//table/tbody/tr/td/a/span")
	private WebElement resourceAppTypeclick;

	@FindBy(how = How.XPATH, using = "//input[@id='leadTimedays']")
	private WebElement leadDay;

	@FindBy(how = How.XPATH, using = "//input[@id='leadTimehours']")
	private WebElement leadHour;

	@FindBy(how = How.XPATH, using = "//input[@id='leadTimemins']")
	private WebElement leadMinut;

	@FindBy(how = How.XPATH, using = "//*[@name='apptTypeReservedReason']")
	private WebElement reservefor;

	@FindBy(how = How.XPATH, using = "//*[@id='tab43']/div/form/fieldset[3]/div/div/button[1]")
	private WebElement appointmenttypeSave;

	@FindBy(how = How.ID, using = "maxPerDay")
	private WebElement maxPerDay;


	@FindBy(how = How.XPATH, using = "//label[@for='allowSameDayAppts']//input")
	private WebElement acceptToggle;

	@FindBy(how = How.XPATH, using = "//div[@class='col-md-12']//label[@for='allowSameDayAppts']/i")
	private WebElement acceptToggleclick;

	@FindBy(how = How.XPATH, using = "//strong[contains(text(),'Age Rule')]")
	private WebElement ageRuleCheckbox;

	@FindBy(how = How.XPATH, using = "//input[@id='resourceappointmenttypeisageRule']")
	private WebElement ageRuleCheckboxStatus;

	@FindBy(how = How.XPATH, using = "//*[@id='tab43']/div/form/div[2]/div[2]/select")
	private WebElement ageruleDropFirst;

	@FindBy(how = How.XPATH, using = "//*[@id='tab43']/div/form/div[2]/div[5]/select")
	private WebElement ageruleDropSecond;

	@FindBy(how = How.XPATH, using = "//*[@id='tab43']/div/form/div[2]/div[4]/select")
	private WebElement ageruleAnd;

	@FindBy(how = How.XPATH, using = "//input[@id='line1' and @name='leftVal']")
	private WebElement sendMonthFirst;

	@FindBy(how = How.XPATH, using = "//input[@id='line1' and @name='rightVal']")
	private WebElement sendMonthsecond;

	@FindBy(how = How.XPATH, using = "//*[@name='apptTimeMark']")
	private WebElement timeMarkOption;

	@FindBy(how = How.XPATH, using = "//label[@for='lastQuestRequired']//i")
	private WebElement lastQuestionRequiredToggleClick;

	@FindBy(how = How.XPATH, using = "//label[@for='lastQuestRequired']//input")
	private WebElement lastQuestionRequiredTogglebtn;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary'][normalize-space()='Save']")
	private WebElement bookAptSaveBtn;

	@FindBy(how = How.XPATH, using = "//label[@for='acceptComment']//input")
	private WebElement lastQuestionEnableTogglebtn;

	@FindBy(how = How.XPATH, using = "//label[@for='acceptComment']//i")
	private WebElement lastQuestionEnableToggleClick;

	@FindBy(how = How.XPATH, using = "//a[@title='Back']//*[local-name()='svg']")
	private WebElement backArrow;
	
	@FindAll({ @FindBy(xpath = "//div[@id='tab23']/table/tbody/tr/td[3]/div/div/label/input") })
	private List<WebElement> locationToggleStatus;

	@FindAll({ @FindBy(xpath = "//div[@id='tab23']/table/tbody/tr/td[3]/div/div/label/i") })
	private List<WebElement> locationToggleClick;
	
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

	@FindBy(how = How.XPATH, using = "//*[@name='slotCount']")
	private WebElement slotCount;
	
	@FindBy(how = How.XPATH, using = "//input[@id='isContiguous']")
	private WebElement preventBacktoBackToggle;
	
	@FindBy(how = How.XPATH, using = "//*[@id='tab43']/div/form/fieldset[1]/div[11]/div/label[1]/i")
	private WebElement preventBackToBackToggleclick;

	
	public ManageResource(WebDriver driver) {
		super(driver);
	}

	public void searchResource(String resourceName){
		IHGUtil.waitForElement(driver, 6, searchResource);
		log("Enter the resource in search box "+resourceName);
		searchResource.sendKeys(resourceName);
	}

	public void resourceSearchApt(String resourceName) {
		resourceSearchApt.sendKeys(resourceName);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public void selectResource(String resourceName) throws InterruptedException {
		searchResource(resourceName);
		IHGUtil.waitForElement(driver, 60, searchedResourceName);
		searchedResourceName.click();
		log("clicked on Resource  ");
	} 
 
	public void clickLocation() throws InterruptedException {
		commonMethods.highlightElement(resourceLocationTab);
		resourceLocationTab.click();
		log("Clicked On Location ");
	}

	public void offAllLocationToggle() throws InterruptedException {
		log("The size of toggle button in webpage is" + locationToggleStatus.size());
		for (int t = 1; t < locationToggleClick.size(); t++) {
			WebElement locationchk = driver
					.findElement(By.xpath("//div[@id='tab23']/table/tbody/tr[" + t + "]/td[3]/div/div/label/i"));
			WebElement locationchkStatus = driver
					.findElement(By.xpath("//div[@id='tab23']/table/tbody/tr[" + t + "]/td[3]/div/div/label/input"));
			if (locationchkStatus.isSelected() == true) {
				log("the Value of t is " + t);
				commonMethods.highlightElement(locationchk);
				locationchk.click();
				log("The Value of the Toggle status after turn off is " + locationchkStatus.isSelected());
				log("Clicked on The True Value");
			}
		}
	}

	public void selectAppointmenttype(String ApptypeName) {
		editAptTypeTab.click();
		resourceSearchApt(ApptypeName);
		IHGUtil.waitForElement(driver, 60, searchedResourceName);
		resourceAppTypeclick.click();
		log("clicked on Appointment type  ");
	}

	public Boolean isSharedPatientTrueForResource() {
		return Boolean.valueOf(resourceSharePatients.getAttribute("ng-reflect-model"));
	}

	public void clickShareToggle() {
		resoureceSharePatientToggle.click();
		log("Clicked on SharePatient Toggle button");
		resourceSave.click();
	}

	public Boolean isResourceActive() {
		return Boolean.valueOf(resourceActiveState.getAttribute("ng-reflect-model"));
	}

	public Boolean isResourceAcceptComments() {
		return Boolean.valueOf(resourceAcceptsComments.getAttribute("ng-reflect-model"));
	}

	public Boolean isResourceAgeRuleChecked() {
		return Boolean.valueOf(resourceAgeRuleChecked.getAttribute("ng-reflect-model"));
	}

	public String isResourceLocationEnabled() {
		String enabledValue = resourceSearchedLocation.getAttribute("ng-reflect-model");
		log("Location Enabled for the resource ? " + enabledValue);
		return enabledValue;
	}

	public void clickappointmenttype() {
		editAptTypeTab.click();
	}

	public int getDay() {
		String leadtimeDay = leadDay.getAttribute("value");
		int leadday = Integer.parseInt(leadtimeDay);
		return leadday;

	}

	public int getHour() {
		String leadtimeHour = leadHour.getAttribute("value");
		int ledhour = Integer.parseInt(leadtimeHour);
		return ledhour;

	}

	public int getMinut() {
		String leadtimeMinut = leadMinut.getAttribute("value");
		int laeahour = Integer.parseInt(leadtimeMinut);
		return laeahour;

	}

	public void reserveFor() {
		Select objSelect = new Select(reservefor);
		objSelect.selectByVisibleText("Same Day");
		reservefor.click();
		appointmenttypeSave.click();
	}

	public void notReserve() {
		Select objSelect = new Select(reservefor);
		objSelect.selectByVisibleText("Not Reserved");
		reservefor.click();
		appointmenttypeSave.click();
	}

	public void maxperDay(String maxvalue) {
		maxPerDay.clear();
		maxPerDay.sendKeys("0");
		appointmenttypeSave.click();
		maxPerDay.clear();
		maxPerDay.sendKeys(maxvalue);
		appointmenttypeSave.click();

	}

	public boolean acceptforStatus() {
		boolean bool = acceptToggle.isSelected();
		log("Status of Accept for the Same Day -" + bool);
		return bool;
	}

	public void clickAcceptSameDay() {
		acceptToggleclick.click();
		appointmenttypeSave.click();
		log("clicked on accceptfor sameday");
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
		appointmenttypeSave.click();
	}

	public void disableLastQuestionRequired() throws InterruptedException {
		IHGUtil.waitForElement(driver, 1, lastQuestionRequiredToggleClick);
		commonMethods.highlightElement(lastQuestionRequiredToggleClick);
		jse.executeScript("arguments[0].click();", lastQuestionRequiredTogglebtn);
		log("Status of Last Question Required after TURN OFF- " + lastQuestionRequiredTogglebtn.isSelected());
		while (lastQuestionRequiredTogglebtn.isSelected() == true) {
			jse.executeScript("arguments[0].click();", lastQuestionRequiredTogglebtn);
		}
		pageDown(1000);
		IHGUtil.waitForElement(driver, 3, bookAptSaveBtn);
		commonMethods.highlightElement(bookAptSaveBtn);
		bookAptSaveBtn.click();
		log("TURN OFF the Last Question Required toggle button as it was enable previously");
	}

	public void enableLastQuestionRequired() throws InterruptedException {

		IHGUtil.waitForElement(driver, 1, lastQuestionRequiredToggleClick);
		pageDown(800);

		commonMethods.highlightElement(lastQuestionRequiredToggleClick);
		jse.executeScript("arguments[0].click();", lastQuestionRequiredTogglebtn);
		log("Status of Last Question Required after TURN ON- " + lastQuestionRequiredTogglebtn.isSelected());

		while (lastQuestionRequiredTogglebtn.isSelected() == false) {
			jse.executeScript("arguments[0].click();", lastQuestionRequiredTogglebtn);
		}
		pageDown(1000);
		IHGUtil.waitForElement(driver, 3, bookAptSaveBtn);
		commonMethods.highlightElement(bookAptSaveBtn);
		bookAptSaveBtn.click();
		log("TURN ON the Last Question Required toggle button as it was disabled previously");
	}

	public void enableLastQuestion() throws InterruptedException {

		IHGUtil.waitForElement(driver, 1, lastQuestionEnableToggleClick);
		pageDown(800);

		commonMethods.highlightElement(lastQuestionEnableToggleClick);
		jse.executeScript("arguments[0].click();", lastQuestionEnableTogglebtn);
		log("Status of Last Question Enable after TURN ON- " + lastQuestionEnableTogglebtn.isSelected());

		while (lastQuestionEnableTogglebtn.isSelected() == false) {
			jse.executeScript("arguments[0].click();", lastQuestionEnableTogglebtn);
		}
		log("TURN ON the Last Question Enable toggle button as it was disabled previously");
	}

	public void pageDown(int d) throws InterruptedException {
		Thread.sleep(1000);
		// This will scroll down the page by 800 pixel vertical
		jse.executeScript("window.scrollBy(0," + d + ")");
		Thread.sleep(1000);
	}

	public void pageUp(int t) throws InterruptedException {
		Thread.sleep(1000);
		// This will scroll up the page by 600 pixel vertical
		jse.executeScript("window.scrollBy(" + t + ",0)");
		Thread.sleep(1000);
	}

	public void clickBackArraow() throws InterruptedException {
		pageUp(1500);
		commonMethods.highlightElement(backArrow);
		backArrow.click();
	}

	public void clickGeneralTab() throws InterruptedException {
		commonMethods.highlightElement(editGeneralTab);
		editGeneralTab.click();
		pageDown(1500);
		commonMethods.highlightElement(resourceSave);
		resourceSave.click();
	}

	public boolean lastQuestionRequiredStatus() {

		commonMethods.highlightElement(lastQuestionRequiredToggleClick);
		boolean bool = lastQuestionRequiredTogglebtn.isSelected();
		return bool;
	}

	public boolean lastQuestionEnableStatus() {
		commonMethods.highlightElement(lastQuestionEnableToggleClick);
		boolean bool = lastQuestionEnableTogglebtn.isSelected();
		return bool;
	}

	public void timeMark(String timeMarkValue) {
		Select selectOptions = new Select(timeMarkOption);
		selectOptions.selectByValue(timeMarkValue);
		timeMarkOption.click();
		appointmenttypeSave.click();

	}
	public void excludeBtn(String firstValue,String secondValue)
	{
		excludeSlotBtn.click();
		Select before = new Select(excludeSlotBeforeAfterStart);
		Select and = new Select(excludeSlotCondition);
		Select after = new Select(excludeSlotBeforeAfterEnd);
		before.selectByVisibleText("After");
		startTime.clear();
		startTime.sendKeys(firstValue);
		and.selectByIndex(1);;
		after.selectByVisibleText("Before");
		endTime.clear();
		endTime.sendKeys(secondValue);
		log("SuccessFully Sent the Values in Exclude Slot textfield");
		addExcludeSlotBtn.click();
		log("Clicked on yes ");
		appointmenttypeSave.click();
	}

	public void slotCount(String slotValue) {
		Select selectOptions = new Select(slotCount);
		selectOptions.selectByVisibleText(slotValue);
		slotCount.click();
		commonMethods.highlightElement(appointmenttypeSave);
		appointmenttypeSave.click();
	}
	public String getslotSize()
	{
		commonMethods.highlightElement(slotSizeValue);
		Select selectOptions = new Select(slotSizeValue);
		WebElement slotSizeValue=selectOptions.getFirstSelectedOption();
		String selectedValue=slotSizeValue.getText();
		return selectedValue;
	}

	public void setLeadDay(String leadtimeDay) {
		leadDay.clear();
		leadDay.sendKeys(leadtimeDay);
		appointmenttypeSave.click();

	}
	
	public boolean preventBackToBack() {
		boolean bool = preventBacktoBackToggle.isSelected();
		log("Status of Prevent for the Same Day -" + bool);
		return bool;
	}

	public void clickBackToBack() {
		preventBackToBackToggleclick.click();
		appointmenttypeSave.click();
	}
}