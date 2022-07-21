// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.pss2.page.PSS2MenuPage;
import com.medfusion.product.object.maps.pss2.page.util.CommonMethods;

public class AdminAppointment extends SettingsTab {

	@FindBy(how = How.ID, using = "cancelappointment")
	private WebElement cancelAppointment;

	@FindBy(how = How.XPATH, using = "//input[@id=\"showCancelReason\"]")
	private WebElement showCancelReschedReason;

	@FindBy(how = How.XPATH, using = "//input[@id=\"showCancelReasonFromPM\"]")
	private WebElement showCancelReasonPM;

	@FindBy(how = How.XPATH, using = "//form[5]/div[2]/div[1]/div[1]/label[2]")
	private WebElement showCancelReschedReasonLabel;

	@FindBy(how = How.XPATH, using = "//label[normalize-space()='Show cancellation reason From PM']")
	private WebElement showCancelReasonPMLabel;

	@FindBy(how = How.ID, using = "blockpatientmonths")
	private WebElement blockPatientMonths;

	@FindBy(how = How.ID, using = "slotcount")
	private WebElement slotCount;

	@FindBy(how = How.ID, using = "maxappt")
	private WebElement maxAppt;

	@FindBy(how = How.ID, using = "maxslotmonths")
	private WebElement maxSlotMonths;

	@FindBy(how = How.ID, using = "majorage")
	private WebElement majorAge;

	@FindBy(how = How.ID, using = "showproviderimage")
	private WebElement showProviderImage;

	@FindBy(how = How.XPATH, using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[5]/div[1]/form[1]/div[3]/div[1]/div[1]/label[1]/i[1]")
	private WebElement allowPCP;

	@FindBy(how = How.XPATH, using = "//*[@id='appt']/div/form[1]/div[3]/div/div/label[1]/i")
	private WebElement allowPCP1;

	@FindBy(how = How.XPATH, using = "//input[@id='allowpcp']")
	private WebElement allowPCPToggle;

	@FindBy(how = How.ID, using = "searchlocation")
	private WebElement searchLocation;
	
	@FindBy(how = How.XPATH, using = "//*[@id='searchlocation']")
	private WebElement searchLocationStatus;
	
	@FindBy(how = How.XPATH, using = "//label[@for='searchlocation']/i")
	private WebElement searchLocationToggle;

	@FindBy(how = How.XPATH, using = "//*[@id='appt']/form[1]/div[4]/div/div/label[1]/i")
	private WebElement nextAvailable;

	@FindBy(how = How.XPATH, using = "//*[@id='nextavailable']")
	private WebElement nextAvailableStatus;

	@FindBy(how = How.ID, using = "pastapptmonths")
	private WebElement pastApptMonths;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Primary Care Provider')]/preceding-sibling::input")
	private WebElement radioOptionPCP;

	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Responsible Care Provider')]/preceding-sibling::input")
	private WebElement radioOptionRCP;

	@FindBy(how = How.XPATH, using = "//body/app[1]/layout[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[5]/div[1]/form[2]/div[1]/div[2]/div[1]/div[1]/label[1]/i[1]")
	private WebElement allowFCT;

	@FindBy(how = How.ID, using = "forceCareteamDuration")
	private WebElement fCTAvailibilityDuration;

	@FindBy(how = How.ID, using = "pcpAvailabilityDuration")
	private WebElement pcpAvailabilityDuration;

	@FindBy(how = How.XPATH, using = "//*[text()='Resource Configuration']/parent::*/parent::*//button[contains(text(),'Save')]")
	private WebElement buttonSaveResourceConfg;

	@FindBy(how = How.XPATH, using = "//*[@id='appt']/form/fieldset/div/div/button")
	private WebElement buttonSave;

	@FindBy(how = How.XPATH, using = "//*[@id='appt']/div/form/fieldset/div/div/button")
	private WebElement pcpbuttonSave;

	public AdminAppointment(WebDriver driver) {
		super(driver);
	}

	CommonMethods commonMethods = new CommonMethods(driver);

	public void clearAll() {
		// 01:00 1 1 5 1 18
		cancelAppointment.clear();
		cancelAppointment.sendKeys("01:00");
		blockPatientMonths.clear();
		blockPatientMonths.sendKeys("1");
		slotCount.clear();
		slotCount.sendKeys("5");
		maxAppt.clear();
		maxAppt.sendKeys("1");
		maxSlotMonths.clear();
		maxSlotMonths.sendKeys("1");

		majorAge.clear();
		majorAge.sendKeys("18");
		log("----------------------------------------------------------------------------------");
		log("showProviderImage =" + showProviderImage.isEnabled());
		log("allowPCP =" + allowPCP.isEnabled());
		log("searchLocation =" + searchLocation.isEnabled());
		log("pastApptMonths text =" + pastApptMonths.getText());
		log("----------------------------------------------------------------------------------");

		if (showProviderImage.isEnabled() && showProviderImage.isDisplayed()) {
			showProviderImage.click();
		}
		if (allowPCP.isEnabled() && allowPCP.isDisplayed()) {
			allowPCP.click();
		}
		if (!searchLocation.isEnabled() && !searchLocation.isDisplayed()) {
			searchLocation.click();
		}
		pastApptMonths.clear();

		IHGUtil.waitForElement(driver, 60, buttonSave);
		buttonSave.click();
	}

	public void toggleCancelReason() throws InterruptedException {
		pageDown();
		log("Verify the Cancel Settings in ADMIN TAB");

		boolean cancel1 = isShowCancellationRescheduleReason();
		log("verifying the settings of Cancel/Reschedule Reason : " + cancel1);
		Thread.sleep(1000);
		if (cancel1) {
			log("Cancel/Reschedule Reason ALREADY turned ON..");
			boolean cancel2 = isShowCancellationReasonPM();
			log("verifying the settings of Cancel Reason from PM : " + cancel2);
			if (cancel2) {

				log("cancel 1 - ON and Cance2 - ON");

			} else {

				log("cancel 1 - ON and Cance2 - OFF");
			}

		} else {

			log("Cancel/Reschedule reason setting is OFF-Defaults pop up message will display");
			log("cancel 1 - OFF and Cancel2 - OFF");
		}

	}

	public void updateCancelAppointmentSettings(String cancelHoursBefore) throws InterruptedException {
		commonMethods.highlightElement(cancelAppointment);
		cancelAppointment.sendKeys(cancelHoursBefore);
		commonMethods.highlightElement(buttonSave);
		Thread.sleep(3000);
		buttonSave.click();
		Thread.sleep(1000);
	}

	public boolean toggleAllowPCPONOF() throws InterruptedException {
		pageDown();
		return allowPCPToggle.isSelected();
	}

	public void pcptoggleclick() throws InterruptedException {
		javascriptClick(allowPCP1);
		log("Clicked on PCP ON....................");
		pcpbuttonSave.click();
		Thread.sleep(5000);
		log("Clicked on Save Button...............");
	}

	public Boolean isShowCancellationRescheduleReason() {

		return showCancelReschedReason.isSelected();

	}

	public Boolean isShowCancellationReasonPM() {

		return showCancelReasonPM.isSelected();
	}

	public void blockPatientsAsPerMonth(String lastSeenMonth) {
		blockPatientMonths.clear();
		blockPatientMonths.sendKeys(lastSeenMonth);
		buttonSave.click();
	}

	public void slotcountToBeDisplayed(String NumberOfSlots) {
		slotCount.click();
		slotCount.clear();
		slotCount.sendKeys(NumberOfSlots);
		buttonSave.click();
	}

	public void maxAppointments(String maxAp) {
		maxAppt.click();
		maxAppt.clear();
		maxAppt.sendKeys(maxAp);
		buttonSave.click();
	}

	public void maxSlotMonthsToBeDisplayed(String maxMonths) {
		maxSlotMonths.clear();
		maxSlotMonths.sendKeys(maxMonths);
		buttonSave.click();
	}

	public void majorAgeToSet(String majotAgeInYears) {
		majorAge.clear();
		majorAge.sendKeys(majotAgeInYears);
		buttonSave.click();
	}

	public void displayProviderImages() {
		showProviderImage.click();
		buttonSave.click();
	}

	public void toggleSearchLocation() {
		log("is search location Enabled" + searchLocation.isEnabled());
		if (!searchLocation.isEnabled()) {
			searchLocation.click();
		}
		buttonSave.click();
	}
	
	public boolean toggleLocationStatus() throws InterruptedException {
		Thread.sleep(3000);
		commonMethods.highlightElement(searchLocationStatus);
		return searchLocationStatus.isSelected();
	}
	
	public void toggleSearchLocationClick() throws InterruptedException {
		log("is search location selected " + toggleLocationStatus());
		if (toggleLocationStatus() == false) {
			searchLocationToggle.click();
			commonMethods.highlightElement(buttonSave);
			buttonSave.click();
			log("Clicked on search location");
		}
	}

	public Boolean toggleNextAvailableStatus() {
		log("Status of NextAvailable Button is  " + nextAvailableStatus.isSelected());
		return nextAvailableStatus.isSelected();
	}

	public void toggleNextavailableClick() throws InterruptedException {
		javascriptClick(nextAvailable);
		log("Clicked on nextAvailable");
		IHGUtil.waitForElement(driver, 10, buttonSave);
		javascriptClick(buttonSave);
	}

	public void togglePastApptMonths() {
		pastApptMonths.click();
		buttonSave.click();
	}

	public void pageDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void toggleAllowPCP() throws InterruptedException {
		pageDown();
		log("Inside toggleAllowPCP method about to turn ON ENABLE CARE TEAMS.");
		String background_color = allowPCP.getCssValue("background-color");
		log("verifying the color of ENABLE CARE TEAM : " + background_color);
		Thread.sleep(1000);
		if (background_color.equals("rgba(93, 143, 194, 1)")) {
			log("ENABLE CARE TEAMS is ALREADY turned ON..");
			Thread.sleep(1000);
			toggleAllowFCT();
		} else if (allowPCP.isEnabled() && (background_color.equals("rgba(0, 0, 0, 0)"))) {
			javascriptClick(allowPCP);
			log("ENABLE CARE TEAMS turned ON.");
			toggleAllowFCT();
		} else {
			log("CARE TEAMs is NOT turned ON.");
		}
	}

	public void toggleAllowFCT() throws InterruptedException {
		log("Inside toggleallowFCT method about to turn ON FORCE CARE TEAMS.");
		Thread.sleep(2000);
		String background_color = allowFCT.getCssValue("background-color");
		log("verifying the color of FORCE CARE TEAM : " + background_color);
		if ((background_color.equals("rgba(93, 143, 194, 1)"))) {
			log("Inside toggleallowFCT but FCT is Aleady Turned ON.");
		} else if (allowFCT.isEnabled() && (background_color.equals("rgba(0, 0, 0, 0)"))) {
			javascriptClick(allowFCT);
			log("FORCE CARE TEAMS turned ON.");
		} else {
			log("Inside toggleAllowFCT but FORCE CARE TEAMS NOT turned ON.");
		}
	}

	public void chooseRCPorPCP(String selectRCPorPCP) throws InterruptedException {
		if (selectRCPorPCP.equalsIgnoreCase("RCP")) {
			selectResponsibleCareProvider();
		} else if (selectRCPorPCP.equalsIgnoreCase("PCP")) {
			selectPrimaryCareProvider();
		} else {
			log("None of the CheckBox is selected");
		}
	}

	public void selectResponsibleCareProvider() throws InterruptedException {
		log("Inside Responsible care Provider method.");
		if (radioOptionRCP.isSelected() == false) {
			javascriptClick(radioOptionRCP);
		}
		radioOptionRCP.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		saveSlotSettingsResourceConfg();
		log("RCP Selected.");
	}

	public void selectPrimaryCareProvider() throws InterruptedException {
		log("Inside Primary care Provider method.");
		if (radioOptionPCP.isSelected() == false) {
			javascriptClick(radioOptionPCP);
		}
		radioOptionPCP.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		saveSlotSettingsResourceConfg();
		log("PCP Selected.");
	}

	public void pCPAvailabilityDuration(String PCPAvailabilityDuration) throws InterruptedException {
		log("Inside method(pCPAvailabilityDuration)");
		if (pcpAvailabilityDuration.isEnabled()) {
			javascriptClick(pcpAvailabilityDuration);
			Thread.sleep(1000);
			pcpAvailabilityDuration.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			pcpAvailabilityDuration.sendKeys(Keys.chord(Keys.DELETE));
			pcpAvailabilityDuration.sendKeys(PCPAvailabilityDuration);
			Thread.sleep(1000);
			pcpAvailabilityDuration.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			log("PCP text field updated :" + PCPAvailabilityDuration);
			saveSlotSettingsResourceConfg();
			Thread.sleep(1000);
		}
	}

	public void forceCareteamDuration(String forceCareTeamDuration) throws InterruptedException {
		log("Inside method(forceCareteamDuration)");
		if (fCTAvailibilityDuration.isEnabled() && (forceCareTeamDuration != "0")) {
			javascriptClick(fCTAvailibilityDuration);
			Thread.sleep(1000);
			fCTAvailibilityDuration.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			fCTAvailibilityDuration.sendKeys(Keys.chord(Keys.DELETE));
			Thread.sleep(1000);
			fCTAvailibilityDuration.sendKeys(forceCareTeamDuration);
			Thread.sleep(1000);
			pcpAvailabilityDuration.sendKeys(Keys.ENTER);
			Thread.sleep(1000);
			log("FCT text field updated :" + forceCareTeamDuration);
			saveSlotSettingsResourceConfg();
			Thread.sleep(1000);
		}
	}

	public PSS2MenuPage saveSlotCancelReasonSetting() {
		javascriptClick(buttonSave);
		return PageFactory.initElements(driver, PSS2MenuPage.class);
	}

	public void saveSlotSettings() {
		javascriptClick(buttonSave);
	}

	public void saveSlotSettingsResourceConfg() {
		javascriptClick(buttonSaveResourceConfg);
	}
}