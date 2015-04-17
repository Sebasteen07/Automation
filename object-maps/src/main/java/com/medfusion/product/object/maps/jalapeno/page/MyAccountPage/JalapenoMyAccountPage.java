package com.medfusion.product.object.maps.jalapeno.page.MyAccountPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

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
	
	@FindBy(how = How.ID, using = "id10")
	private WebElement address1Textbox;
	
	@FindBy(how = How.ID, using = "id12")
	private WebElement cityTextbox;
	
	@FindBy(how = How.ID, using = "id14")
	private WebElement zipCodeTextbox;
		
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
		
		if(savedAddressLine1.isEmpty() || savedCity.isEmpty() || savedZipCode.isEmpty()) {
			log("One of the address information if missing");
			return false;
		}
		
		if(!line1.equals(savedAddressLine1)) {
			log("Line 1 is incorrect: " + savedAddressLine1);
			return false;
		}
		if(!city.equals(savedCity)) {
			log("City is incorrect: " + savedCity);
			return false;
		}
		if(!zipCode.equals(savedZipCode)) {
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
		
		log("Finding Address Line 1 textbox");
		IHGUtil.setFrame(driver, "iframebody");
		log("I am in iframe");
		
		String savedZipCode = zipCodeTextbox.getAttribute("value");
		
		if(savedZipCode.isEmpty()) {
			log("ZipCode is missing");
			return false;
		}
		
		log("ZipCode value: " + savedZipCode);

		log("Going out of frame");
		IHGUtil.setDefaultFrame(driver);
			
		return true;	
	}
}
