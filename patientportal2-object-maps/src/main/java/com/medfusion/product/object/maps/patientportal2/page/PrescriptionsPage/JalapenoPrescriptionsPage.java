// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;

public class JalapenoPrescriptionsPage extends JalapenoMenu {

	private long createdTs;

	@FindBy(how = How.XPATH, using = "//input[@value='Continue']")
	private WebElement continueButton;

	@FindBy(how = How.XPATH, using = "//div[@id='medForm']/div[1]/div/div[2]/input")
	private WebElement medicationName;

	@FindBy(how = How.XPATH, using = "//div[@id='medForm']/div[2]/div/div[2]/input")
	private WebElement dosage;

	@FindBy(how = How.XPATH, using = "//div[@id='medForm']/div[3]/div/div[2]/input")
	private WebElement quantity;

	@FindBy(how = How.XPATH, using = "//div[@class='new-pharmacy-container']/div[1]/div/div[2]/input")
	private WebElement pharmacyName;

	@FindBy(how = How.XPATH, using = "//div[@class='new-pharmacy-container']/div[2]/div/div[2]/input")
	private WebElement pharmacyPhone;

	@FindBy(how = How.XPATH, using = "//input[@value='Submit Request']")
	private WebElement submitButton;

	@FindBy(how = How.XPATH, using = "//input[@type='radio']")
	private WebElement radioButton;

	@FindBy(how = How.XPATH, using = "//div[@class='fieldWrapper switchLabelAndField']//input")
	private WebElement AddradioButton; // For add a New Pharmacy

	@FindBy(how = How.LINK_TEXT, using = "Home")
	public WebElement homeButton; // this is not home button in Jalapeno Menu

	@FindBy(how = How.XPATH, using = "//select[@name='locationContainer:locationDD']")
	private WebElement locationDropdown;

	@FindBy(how = How.XPATH, using = "//select[@name='providerContainer:providerDD']")
	private WebElement providerDropdown;

	@FindBy(how = How.XPATH, using = "//input[@name='cccontainer:ccpanel:newccdetails:nameOnCreditCard']")
	private WebElement cardholdername;

	@FindBy(how = How.XPATH, using = "//input[@name='cccontainer:ccpanel:newccdetails:creditCardNumber']")
	private WebElement cardnumber;

	@FindBy(how = How.XPATH, using = "//select[@name='cccontainer:ccpanel:newccdetails:creditCardType']")
	private WebElement carddropdown;

	@FindBy(how = How.XPATH, using = "//select[@name='cccontainer:ccpanel:newccdetails:expirationMonth']")
	private WebElement monthdd;

	@FindBy(how = How.XPATH, using = "//select[@name='cccontainer:ccpanel:newccdetails:expirationYear']")
	private WebElement yeardd;

	@FindBy(how = How.XPATH, using = "//input[@name='cccontainer:ccpanel:newccdetails:newccdetailscvv:cvvCode']")
	private WebElement cardcvv;

	@FindBy(how = How.XPATH, using = "//input[@name='cccontainer:ccpanel:newccdetails:addressZip']")
	private WebElement cardzip;

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Add Another Medication')]")
	private WebElement addAnotherMedicationBtn;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:addMedPanel:medForm:addEditMedicationNameWrapper:_body:addEditMedicationName')]")
	private WebElement medicationNameField;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:addMedPanel:medForm:addEditMedicationDosageWrapper:_body:addEditMedicationDosage')]")
	private WebElement medicationDosage;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:addMedPanel:medForm:addEditMedicationQuantityWrapper:_body:addEditMedicationQuantity')]")
	private WebElement medicationQuantity;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:addMedPanel:medForm:addEditMedicationRefillsWrapper:_body:addEditMedicationRefills')]")
	private WebElement numberOfRefills;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:addMedPanel:medForm:addEditMedicationRxNumWrapper:_body:addEditMedicationRxNum')]")
	private WebElement prescriptionNumber;

	@FindBy(how = How.XPATH, using = "//*[contains(@name,'0:additionalInfoForAddWrapper:_body:additionalInfoForAdd')]")
	private WebElement additionalInformation;

	@FindBy(how = How.XPATH, using = "//span[contains(text(),'Add a Pharmacy')]/preceding-sibling::input[@name='pharmacyPanel:radioGroup']")
	private WebElement addNewPharamcyRadioBtn;

	@FindBy(how = How.XPATH, using = "//*[@class='feedback']/following::*[contains(text(),'Prescription Renewa')]")
	public WebElement renewalConfirmationmessage;

	@FindAll({ @FindBy(how = How.XPATH, using = "//*[@id='medicationForm']/div[1]/div") })
	public List<WebElement> Medicationlist;

	@FindBy(how = How.XPATH, using = "//select[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList:select']")
	private WebElement chooseOneDrpdown;

	@FindAll({
			@FindBy(how = How.XPATH, using = "//select[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList:select']/optgroup[2]/option") })
	public List<WebElement> optionFromOtherPharmacy;

	@FindBy(how = How.XPATH, using = "//input[@name='pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList']")
	private WebElement chooseFromAList;

	@FindBy(how = How.XPATH, using = "//div[@class='wicket-aa-container']/div/ul/li")
	private WebElement textValueFromChooseFromAList;

	@FindBy(how = How.XPATH, using = "(//div[@id='medicationForm']//input[@type='checkbox'])[1]")
	public WebElement selectFirstMedication;

	@FindBy(how = How.NAME, using = "pharmacyPanel:radioGroup:pharmacySearchContainer:pharmacySearchList")
	public WebElement PharmacyDropDown;

	@FindBy(how = How.XPATH, using = "(//*[contains(@name,'summaryAdditionalInfo')])[1]")
	public WebElement addAdditionalInfo;

	@FindBy(how = How.XPATH, using = "//input[@name='pharmacyPanel:radioGroup']")
	public WebElement PharmacyRadioButton;

	@FindBy(how = How.XPATH, using = "//*[@id='rxrenewalform']/div[3]/div[2]/div")
	public WebElement providerLocation;

	@FindBy(how = How.XPATH, using = "//*[@id=\"rxrenewalform\"]/div[4]/div[2]/div")
	public WebElement practiceProvider;

	public JalapenoPrescriptionsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		createdTs = System.currentTimeMillis();

	}

	public void clickContinueButton(WebDriver driver) {
		driver.switchTo().frame("iframebody");

		log("Checking if there're location options");
		if (IHGUtil.exists(driver, 2, locationDropdown)) {
			log("Selecting location");
			Select locationSelect = new Select(locationDropdown);
			locationSelect.selectByIndex(1);

			log("Selecting provider");
			try {
				Select providerSelect = new Select(providerDropdown);
				providerSelect.selectByIndex(1);

			} catch (StaleElementReferenceException ex) {
				log("Dont know what's going on here");
			}

			log("Clicking on continue button");
			javascriptClick(continueButton);

			driver.switchTo().defaultContent();
		}
	}

	public JalapenoHomePage fillThePrescription(WebDriver driver, String medication, String dosage, int quantity)
			throws InterruptedException {

		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");

		log("Insert medication info");
		this.medicationName.sendKeys(medication);
		this.dosage.sendKeys(dosage);
		this.quantity.sendKeys(Integer.toString(quantity));
		log("Insert pharmacy information");
		Thread.sleep(1000);
		AddradioButton.click();// Clicking on Add a pharmacy radio button
		Thread.sleep(1000);
		pharmacyName.sendKeys("PharmacyName");
		pharmacyPhone.sendKeys("3216549870");
		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		javascriptClick(continueButton);
		Thread.sleep(1000);

		log("Click on Submit button");
		wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		javascriptClick(submitButton);

		log("Return to Home Dashboard");
		wait.until(ExpectedConditions.elementToBeClickable(homeButton));
		javascriptClick(homeButton);

		driver.switchTo().defaultContent();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public void prescriptionPayment() throws Exception {
		PropertyFileLoader testData = new PropertyFileLoader();

		driver.switchTo().frame("iframebody");
		String name = "TestPatient CreditCard";
		cardholdername.sendKeys(name);

		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
		cardnumber.sendKeys(creditCard.getCardNumber());

		Select cardSelect = new Select(carddropdown);
		cardSelect.selectByIndex(3);

		Select monthSelect = new Select(monthdd);
		monthSelect.selectByVisibleText(testData.getProperty("dob.month.text"));

		Select yearSelect = new Select(yeardd);
		yearSelect.selectByValue(creditCard.getExpYear());

		cardcvv.sendKeys(creditCard.getCvvCode());
		cardzip.sendKeys(creditCard.getZipCode());

	}

	public void fillThePrescriptionforExisitngUser() throws InterruptedException {

		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");
		jse.executeScript("window.scrollBy(0,650)", "");
		String env = IHGUtil.getEnvironmentType().toString();
		if (env.equals("DEV3") || env.equals("DEMO")) {
			addAnotherMedicationBtn.click();
		} else {
			log("No Add another Medication button");
		}
		jse.executeScript("window.scrollBy(0,350)", "");
		IHGUtil.waitForElement(driver, 20, medicationNameField);
		log("Step 4: Insert Medication Details");
		medicationNameField.sendKeys(JalapenoConstants.MEDICATION_NAME + "" + createdTs);
		medicationDosage.sendKeys(JalapenoConstants.DOSAGE);
		medicationQuantity.sendKeys(JalapenoConstants.QUANTITY);
		numberOfRefills.sendKeys(JalapenoConstants.NO_OF_REFILLS);
		prescriptionNumber.sendKeys(JalapenoConstants.PRESCRIPTION_NO);
		additionalInformation.sendKeys(JalapenoConstants.ADDITIONAL_INFO);

		log("Step 5: Insert Pharmacy Details");
		jse.executeScript("window.scrollBy(0,200)", "");
		Thread.sleep(2000);
		// Clicking on Add a pharmacy radio button to add new Pharmacy
		jse.executeScript("window.scrollBy(0,400)", "");
		addNewPharamcyRadioBtn.click();
		pharmacyName.sendKeys("PharmacyName");
		pharmacyPhone.sendKeys("3216549870");
		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		javascriptClick(continueButton);
		Thread.sleep(2000);

		log("Click on Submit button");
		wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		javascriptClick(submitButton);

	}

	public long getCreatedTs() {
		IHGUtil.PrintMethodName();
		return createdTs;
	}

	public void validatemedication(String productName) {
		driver.switchTo().frame("iframe");

		for (int i = 1; i < Medicationlist.size(); i++) {
			String medicationName = driver.findElement(By.xpath("//*[@id='medicationForm']/div[1]/div[" + i + "]"))
					.getText();
			if (medicationName.contains(productName)) {
				log("Medication POSTED is visible on portal");
				break;
			} else {
				continue;
			}
		}
		driver.switchTo().defaultContent();
	}

	public void validateDeletedMedication(String productName) {
		driver.switchTo().frame("iframe");
		for (int i = 1; i < Medicationlist.size(); i++) {
			String medicationName = driver.findElement(By.xpath("//*[@id='medicationForm']/div[1]/div[" + i + "]"))
					.getText();
			if (medicationName.contains(productName)) {
				Log4jUtil.log("Deleted medications is still visible on the Prescription page");
				assertTrue(!medicationName.contains(productName));
				break;
			} else {
				continue;
			}
		}

		driver.switchTo().defaultContent();
	}

	public void clickOnChooseOneDrpdown() {
		log("Click on choose one Dropdown");
		driver.switchTo().frame("iframebody");
		chooseOneDrpdown.click();
	}

	public void verifyPharmacy(String pharmacy) {
		List<WebElement> otherPharamacies = optionFromOtherPharmacy;
		java.util.Iterator<WebElement> itr = otherPharamacies.iterator();
		while (itr.hasNext()) {
			WebElement ele = itr.next();
			if (ele.getText().equalsIgnoreCase(pharmacy)) {
				assertEquals(ele.getText(), pharmacy);
				log("Pharamacy is visible on Portal");
				break;
			} else {
				log("Pharamacy is not visible on Portal");
			}
		}
	}

	public void verifyPharamcy(String pharmacy, String sendPharmacyFirstWord, String env) throws InterruptedException {
		if(!env.equalsIgnoreCase("PROD")) {
			driver.switchTo().frame("iframebody");
		}
		chooseFromAList.sendKeys(sendPharmacyFirstWord);
		Thread.sleep(5000);
		log("Get text value from Choose from a list textbox");
		String textValue = textValueFromChooseFromAList.getText();
		if (textValue.equalsIgnoreCase(pharmacy)) {
			assertEquals(textValue, pharmacy);
			log("Pharamacy is visible on Portal");
		} else {
			log("Pharamacy is not visible on Portal");
		}
	}

	public void SelectProviderLocationclickContinueButton(WebDriver driver, String locationName, String ProviderName)
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframebody");

		log("Checking if there're location options");
		if (IHGUtil.exists(driver, 2, locationDropdown)) {
			log("Selecting location");
			Select locationSelect = new Select(locationDropdown);
			locationSelect.selectByVisibleText(locationName);
			Thread.sleep(3000);

			log("Selecting provider");
			try {
				Select providerSelect = new Select(providerDropdown);
				providerSelect.selectByVisibleText(ProviderName);
			} catch (StaleElementReferenceException ex) {
				log("Dont know what's going on here");
			}
			Thread.sleep(5000);

			log("Clicking on continue button");
			javascriptClick(continueButton);

			driver.switchTo().defaultContent();
		}
	}

	public JalapenoHomePage requestForPrescriptionRenewal(WebDriver driver, String prescritonRenewalRequestReason,
			String medicationToRenew) throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");

		log("Select medication to renew");
		driver.findElement(
				By.xpath("//*[contains(text(),'" + medicationToRenew + "')]//parent::div//input[@type='checkbox']"))
				.click();
		log("Medication is selected");

		log("Add comments");
		driver.findElement(By.xpath("//*[contains(text(),'" + medicationToRenew
				+ "')]//parent::div//parent::div//textarea[contains(@name,'summaryAdditionalInfo')]"))
				.sendKeys(prescritonRenewalRequestReason);

		log("Insert pharmacy information");
		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(PharmacyRadioButton));
		PharmacyRadioButton.click();

		try {
			wait.until(ExpectedConditions.elementToBeClickable(PharmacyDropDown));

			Select providerSelect = new Select(PharmacyDropDown);
			providerSelect.selectByIndex(1);
		} catch (Exception e) {
			log(e.getMessage());
		}

		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		javascriptClick(continueButton);
		Thread.sleep(1000);

		log("Click on Submit button");
		wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		javascriptClick(submitButton);

		log("Return to Home Dashboard");
		wait.until(ExpectedConditions.elementToBeClickable(homeButton));
		javascriptClick(homeButton);

		driver.switchTo().defaultContent();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public JalapenoHomePage requestForMultiplePrescriptionRenewal(WebDriver driver,
			String prescritonRenewalRequestReason, String medicationToRenew, String SecondMedicationToRenew)
			throws InterruptedException {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");

		log("Select medication to renew");
		driver.findElement(
				By.xpath("//*[contains(text(),'" + medicationToRenew + "')]//parent::div//input[@type='checkbox']"))
				.click();
		log("Medication is selected");

		log("Add comments");
		driver.findElement(By.xpath("//*[contains(text(),'" + medicationToRenew
				+ "')]//parent::div//parent::div//textarea[contains(@name,'summaryAdditionalInfo')]"))
				.sendKeys(prescritonRenewalRequestReason);

		Thread.sleep(5000);
		log("Select second medication to renew");
		driver.findElement(By
				.xpath("//*[contains(text(),'" + SecondMedicationToRenew + "')]//parent::div//input[@type='checkbox']"))
				.click();
		log("Medication is selected");

		log("Add comments");
		driver.findElement(By.xpath("//*[contains(text(),'" + SecondMedicationToRenew
				+ "')]//parent::div//parent::div//textarea[contains(@name,'summaryAdditionalInfo')]"))
				.sendKeys(prescritonRenewalRequestReason);

		log("Insert pharmacy information");
		Thread.sleep(1000);

		wait.until(ExpectedConditions.elementToBeClickable(PharmacyRadioButton));
		PharmacyRadioButton.click();

		try {
			wait.until(ExpectedConditions.elementToBeClickable(PharmacyDropDown));

			Select providerSelect = new Select(PharmacyDropDown);
			providerSelect.selectByIndex(1);
		} catch (Exception e) {
			log(e.getMessage());
		}

		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		javascriptClick(continueButton);
		Thread.sleep(1000);

		log("Click on Submit button");
		wait.until(ExpectedConditions.elementToBeClickable(submitButton));
		javascriptClick(submitButton);

		log("Return to Home Dashboard");
		wait.until(ExpectedConditions.elementToBeClickable(homeButton));
		javascriptClick(homeButton);

		driver.switchTo().defaultContent();
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

	public void verifyDeletedPharmacy(String pharmacy) {
		List<WebElement> otherPharamacies = optionFromOtherPharmacy;
		java.util.Iterator<WebElement> itr = otherPharamacies.iterator();
		while (itr.hasNext()) {
			WebElement ele = itr.next();
			if (ele.getText().contains(pharmacy)) {
				log("Deleted Pharmacy is visible on the Portal");
				assertTrue(!ele.getText().contains(pharmacy));
				break;
			} else {
				continue;
			}
		}
	}

	public void verifyDeletedPharamcy(String pharmacy, String sendPharmacyFirstWord, String env) throws InterruptedException {
		if(!env.equalsIgnoreCase("PROD")) {
			driver.switchTo().frame("iframebody");
		}
		String textValue = "";
		chooseFromAList.sendKeys(sendPharmacyFirstWord);
		Thread.sleep(5000);
		try {
			log("Trying to get text value from Choose from a list textbox");
			textValue = textValueFromChooseFromAList.getText();
		} catch (Exception e) {
			log(e.getMessage());
		}
		if (textValue.contains(pharmacy)) {
			log("Deleted Pharmacy is visible on the Portal");
			assertTrue(!textValue.contains(pharmacy));
		} else {
			log("Pharamacy is not visible on Portal");
		}
	}

	public String getPracticeProvider(WebDriver driver) {
		IHGUtil.PrintMethodName();
		String env = IHGUtil.getEnvironmentType().toString();
		if (env.equals("DEV3") || env.equals("DEMO")) {
			driver.switchTo().defaultContent();
			driver.switchTo().frame("iframebody");
		} else {
			log("getting Practice Provider");
		}
		String PracticeProvider = practiceProvider.getText();
		return PracticeProvider;
	}

	public String getPracticeLocation(WebDriver driver) {
		IHGUtil.PrintMethodName();
		String env = IHGUtil.getEnvironmentType().toString();
		if (env.equals("DEV3") || env.equals("DEMO")) {
			driver.switchTo().frame("iframebody");
		} else {
			log("getting Practice Location");
		}
		String ProviderLocation = providerLocation.getText();
		return ProviderLocation;
	}
}
