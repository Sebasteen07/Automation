// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage;

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
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;

import junit.framework.Assert;

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

	public JalapenoPrescriptionsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		createdTs = System.currentTimeMillis();

	}

	@Override
	public boolean areBasicPageElementsPresent() {
		log("Method areBasicPageElementsPresent() is not implemented, so it is considered that all expected elements are present.");
		return true;
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
		monthSelect.selectByVisibleText(testData.getProperty("DOBMonthText"));

		Select yearSelect = new Select(yeardd);
		yearSelect.selectByValue(creditCard.getExpYear());

		cardcvv.sendKeys(creditCard.getCvvCode());
		cardzip.sendKeys(creditCard.getZipCode());

	}

	public void fillThePrescriptionforExisitngUser() throws InterruptedException {

		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");
		jse.executeScript("window.scrollBy(0,650)", "");
		addAnotherMedicationBtn.click();
		jse.executeScript("window.scrollBy(0,350)", "");
		IHGUtil.waitForElement(driver, 10, medicationNameField);
		log("Step 5: Insert Medication Details");
		medicationNameField.sendKeys(PortalConstants.MedicationName + "" + createdTs);
		medicationDosage.sendKeys(PortalConstants.Dosage);
		medicationQuantity.sendKeys(PortalConstants.Quantity);
		numberOfRefills.sendKeys(PortalConstants.No_Of_Refills);
		prescriptionNumber.sendKeys(PortalConstants.Prescription_No);
		additionalInformation.sendKeys(PortalConstants.Additional_Info);

		log("Step 6: Insert Pharmacy Details");
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
				Assert.assertTrue(!medicationName.contains(productName));
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
				Assert.assertEquals(ele.getText(), pharmacy);
				log("Pharamacy is visible on Portal");
				break;
			} else {
				log("Pharamacy is not visible on Portal");
			}
		}
	}

	public void verifyPharamcy(String pharmacy, String sendPharmacyFirstWord) throws InterruptedException {
		driver.switchTo().frame("iframebody");
		chooseFromAList.sendKeys(sendPharmacyFirstWord);
		Thread.sleep(5000);
		log("Get text value from Choose from a list textbox");
		String textValue = textValueFromChooseFromAList.getText();
		if (textValue.equalsIgnoreCase(pharmacy)) {
			Assert.assertEquals(textValue, pharmacy);
			log("Pharamacy is visible on Portal");
		} else {
			log("Pharamacy is not visible on Portal");
		}
	}
}
