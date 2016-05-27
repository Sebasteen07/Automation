package com.medfusion.product.object.maps.patientportal2.page.MyAccountPage;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.IHGUtil.Gender;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class JalapenoMyAccountPage extends BasePageObject {

	@FindBy(how = How.LINK_TEXT, using = "Profile")
	private WebElement profileTab;
	
	@FindBy(how = How.LINK_TEXT, using = "E-mail")
	private WebElement emailTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Password & User ID")
	private WebElement passwordAndIdTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Preferences")
	private WebElement preferencesTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Wallet")
	private WebElement walletTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Family")
	private WebElement familyTab;
	
	@FindBy(how = How.LINK_TEXT, using = "Account Activity")
	private WebElement accountActivityTab;

	@FindBy(how = How.XPATH, using = "//input[@fieldid='address.address1']")
	private WebElement address1Textbox;
	
	@FindBy(how = How.XPATH, using = "//input[@fieldid='address.city']")
	private WebElement cityTextbox;
	
	@FindBy(how = How.XPATH, using = "//input[@fieldid='address.zip']")
	private WebElement zipCodeTextbox;

	@FindBy(how = How.XPATH, using = "//td[label[.='Male']]/input")
	private WebElement maleRadioButton;
		
	public JalapenoMyAccountPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}

	public boolean checkForAddress(WebDriver driver, String line1, String city, String zipCode) {

		log("Finding Address Line 1 textbox");
		IHGUtil.setFrame(driver, "iframebody");
		log("I am in iframe");

		String savedAddressLine1 = address1Textbox.getAttribute("value");
		String savedCity = cityTextbox.getAttribute("value");
		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (savedAddressLine1.isEmpty() || savedCity.isEmpty() || savedZipCode.isEmpty()) {
			log("One of the address information if missing");
			return false;
		}

		if (!line1.equals(savedAddressLine1)) {
			log("Line 1 is incorrect: " + savedAddressLine1);
			return false;
		}
		if (!city.equals(savedCity)) {
			log("City is incorrect: " + savedCity);
			return false;
		}
		if (!zipCode.equals(savedZipCode)) {
			log("ZipCode is incorrect: " + savedZipCode);
			return false;
		}

		log("Everything is saved, values are as follows");

		log("Address line 1 value: " + savedAddressLine1);
		log("City value: " + savedCity);
		log("ZipCode value: " + savedZipCode);

		log("Going out of frame");
		IHGUtil.setDefaultFrame(driver);

		return true;
	}

	public boolean checkForAddress(WebDriver driver, String zipCode) {

		log("Finding ZipCode textbox");
		IHGUtil.setFrame(driver, "iframebody");
		log("I am in iframe");

		String savedZipCode = zipCodeTextbox.getAttribute("value");

		if (savedZipCode.isEmpty()) {
			log("ZipCode is missing");
			return false;
		}

		log("ZipCode value: " + savedZipCode);

		log("Going out of frame");
		IHGUtil.setDefaultFrame(driver);

		return true;
	}

	public Gender getGender() {
		IHGUtil.setFrame(driver, "iframebody");
		Gender result = maleRadioButton.isSelected() ? Gender.MALE : Gender.MALE;
		IHGUtil.setDefaultFrame(driver);
		return result;
	}

	public JalapenoHomePage returnToHomePage(WebDriver driver) {
		log("Return to dashboard");
		driver.findElement(By.id("home")).click();

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
}
