package com.medfusion.product.object.maps.precheck.page.myInsurance;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyAppointmentPage;

public class PrimaryInsurancePage extends BasePageObject  {

	@FindBy(how = How.ID, using = "insuranceNameprimary")
	private WebElement insuranceNameprimaryInput;
	
	@FindBy(how = How.ID, using = "memberNameprimary")
	private WebElement subscriberNameInput;
	
	@FindBy(how = How.ID, using = "memberIdprimary")
	private WebElement subscriberIDInput;
	
	@FindBy(how = How.ID, using = "groupNumberprimary")
	private WebElement groupNumberInput;
	
	@FindBy(how = How.ID, using = "dateIssuedprimary")
	private WebElement dateIssuedInput;
	
	@FindBy(how = How.ID, using = "claimsPhoneNumberprimary")
	private WebElement claimsPhoneNumberInput;
	
	@FindBy(how = How.ID, using = "insuranceConfirmButton")
	private WebElement submitPrimaryInsuranceButton;
	
	@FindBy(how = How.XPATH, using = "//*[text() = 'Secondary']")
	private WebElement secondaryInsuranceTab;
	
	public PrimaryInsurancePage(WebDriver driver) {
		super(driver);
	}

	public void setPrimaryInsuranceName(String insuranceNameValue) {
		insuranceNameprimaryInput.clear();
		insuranceNameprimaryInput.sendKeys(insuranceNameValue);
	}
	
	public void setPrimarySubscriberName(String subscriberNameValue) {
		subscriberNameInput.clear();
		subscriberNameInput.sendKeys(subscriberNameValue);
	}
	
	public void setPrimarySubscriberID(String subscriberIDValue) {
		subscriberIDInput.clear();
		subscriberIDInput.sendKeys(subscriberIDValue);
	}
	
	public void setPrimaryGroupNumber(String groupNumberValue) {
		groupNumberInput.clear();
		groupNumberInput.sendKeys(groupNumberValue);
	}
	
	public void setPrimaryDateIssued(String dateValue) {
		dateIssuedInput.clear();
		dateIssuedInput.sendKeys(dateValue);
	}
	
	public void setClaimsPhoneNumberInput(String claimsPhoneNumberValue) {
		claimsPhoneNumberInput.clear();
		claimsPhoneNumberInput.sendKeys(claimsPhoneNumberValue);
	}
	
	public MyAppointmentPage submitPrimaryInsurance() {
		submitPrimaryInsuranceButton.click();
		return PageFactory.initElements(driver, MyAppointmentPage.class);
	}
	
	public SecondaryInsurancePage gotoSecondaryInsurance() {
		secondaryInsuranceTab.click();
		return PageFactory.initElements(driver, SecondaryInsurancePage.class);
	}
}