package com.medfusion.product.object.maps.pss2.page.settings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.medfusion.common.utils.IHGUtil;

public class AdminAppointment extends SettingsTab {

	@FindBy(how = How.ID, using = "cancelappointment")
	private WebElement cancelAppointment;

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

	// @FindBy(how = How.ID, using = "allowpcp")
	// private WebElement allowPCP;


	@FindBy(how = How.XPATH, using = "//label[contains(text(),'Primary Care Provider')]")
	private WebElement allowPCP;

	@FindBy(how = How.ID, using = "searchlocation")
	private WebElement searchLocation;

	@FindBy(how = How.ID, using = "nextAvailable")
	private WebElement nextavailable;

	@FindBy(how = How.ID, using = "pastapptmonths")
	private WebElement pastApptMonths;

	@FindBy(how = How.ID, using = "radior0")
	private WebElement radioOption0;

	@FindBy(how = How.ID, using = "radior1")
	private WebElement radioOption1;

	@FindBy(how = How.XPATH, using = "//*[@id=\"appt\"]/form/fieldset/div/div/button")
	private WebElement buttonSave;

	@FindBy(how = How.XPATH, using = "//div[@id='appt']//div//form[@class='ng-untouched ng-pristine ng-valid']//button[@class='btn btn-primary']")
	private WebElement careTeamConfgSave;


	public AdminAppointment(WebDriver driver) {
		super(driver);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		return true;
	}

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
		// if (!nextavailable.isEnabled()) {
		// nextavailable.click();
		// }
		pastApptMonths.clear();

		IHGUtil.waitForElement(driver, 60, buttonSave);
		buttonSave.click();
	}

	public void updateCancelAppointmentSettings(String cancelHoursBefore) {
		cancelAppointment.sendKeys(cancelHoursBefore);
		buttonSave.click();
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

	public void toggleAllowPCP() throws InterruptedException {

		jse.executeScript("window.scrollBy(0,2000)");
		Thread.sleep(2000);
		allowPCP.click();
		log("Clicked on allow PCP");
		Thread.sleep(200);
		jse.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", careTeamConfgSave);
		Thread.sleep(1000);
		careTeamConfgSave.click();
	}

	public void toggleSearchLocation() {
		log("is search location Enabled" + searchLocation.isEnabled());
		if (!searchLocation.isEnabled()) {
			searchLocation.click();
		}
		buttonSave.click();
	}

	public void toggleNextavailable() {
		nextavailable.click();
		buttonSave.click();
	}

	public void togglePastApptMonths() {
		pastApptMonths.click();
		buttonSave.click();
	}

	public void selectAppointmentComments() {
		if (radioOption0.isSelected() == false) {
			radioOption0.click();
			buttonSave.click();
		}
	}

	public void selectChiefComplaint() {
		if (radioOption1.isSelected() == false) {
			radioOption1.click();
			buttonSave.click();
		}
	}

	public void saveSlotSettings() {
		javascriptClick(buttonSave);
	}
}
