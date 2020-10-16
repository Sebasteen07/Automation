// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;

public class JalapenoPrescriptionsPage extends JalapenoMenu {

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
		private WebElement AddradioButton; //For add a New Pharmacy

		@FindBy(how = How.LINK_TEXT, using = "Home")
		private WebElement homeButton; //this is not home button in Jalapeno Menu

		@FindBy(how = How.XPATH, using = "//select[@name='locationContainer:locationDD']")
		private WebElement locationDropdown;

		@FindBy(how = How.XPATH, using = "//select[@name='providerContainer:providerDD']")
		private WebElement providerDropdown;
		
		@FindBy(how = How.XPATH, using ="//input[@name='cccontainer:ccpanel:newccdetails:nameOnCreditCard']")
		private WebElement cardholdername;

		@FindBy(how = How.XPATH, using ="//input[@name='cccontainer:ccpanel:newccdetails:creditCardNumber']")
		private WebElement cardnumber;
		
		@FindBy(how = How.XPATH, using ="//select[@name='cccontainer:ccpanel:newccdetails:creditCardType']")
		private WebElement carddropdown;
		
		@FindBy(how = How.XPATH, using ="//select[@name='cccontainer:ccpanel:newccdetails:expirationMonth']")
		private WebElement monthdd;
		
		@FindBy(how = How.XPATH, using ="//select[@name='cccontainer:ccpanel:newccdetails:expirationYear']")
		private WebElement yeardd;
		
		@FindBy(how = How.XPATH, using ="//input[@name='cccontainer:ccpanel:newccdetails:newccdetailscvv:cvvCode']")
		private WebElement cardcvv;
		
		@FindBy(how = How.XPATH, using ="//input[@name='cccontainer:ccpanel:newccdetails:addressZip']")
		private WebElement cardzip;

		public JalapenoPrescriptionsPage(WebDriver driver) {
				super(driver);
				IHGUtil.PrintMethodName();
		}

		@Override
		public boolean areBasicPageElementsPresent() {
				//TODO
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
				}

				log("Clicking on continue button");
				javascriptClick(continueButton);

				driver.switchTo().defaultContent();
		}

		public JalapenoHomePage fillThePrescription(WebDriver driver, String medication, String dosage, int quantity) throws InterruptedException {

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
		
		public void prescriptionPayment() throws Exception
		{
			PropertyFileLoader testData = new PropertyFileLoader();
			
			driver.switchTo().frame("iframebody");
			String name = "TestPatient CreditCard";
			cardholdername.sendKeys(name);
			
			CreditCard creditCard = new CreditCard(CardType.Mastercard, name);
			cardnumber.sendKeys(creditCard.getCardNumber());
			
			Select cardSelect = new Select(carddropdown);
			cardSelect.selectByIndex(3);
			
			//System.out.println("Expiry month of card is " +testData.getProperty("cardExpiryMonthText"));
			Select monthSelect = new Select(monthdd);
			monthSelect.selectByVisibleText(testData.getProperty("DOBMonthText"));
			
			Select yearSelect = new Select(yeardd);
			yearSelect.selectByValue(creditCard.getExpYear());
			
			System.out.println("Prescription carnumber is ");
			cardcvv.sendKeys(creditCard.getCvvCode());
			
			System.out.println("Prescription car cvv code isis ");
			cardzip.sendKeys(creditCard.getZipCode());
			
		}

}
