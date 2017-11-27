package com.medfusion.product.object.maps.precheck.page.myInsurance;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class SecondaryInsurancePage  extends BasePageObject  {

	@FindBy(how = How.ID, using = "insuranceNamesecondary")
	private WebElement insuranceNameSecondaryInput;
	
	@FindBy(how = How.ID, using = "memberNamesecondary")
	private WebElement subscriberNameInput;
	
	@FindBy(how = How.ID, using = "memberIdsecondary")
	private WebElement subscriberIDInput;
	
	@FindBy(how = How.ID, using = "groupNumbersecondary")
	private WebElement groupNumberInput;
	
	@FindBy(how = How.ID, using = "dateIssuedsecondary")
	private WebElement dateIssuedInput;
	
	@FindBy(how = How.ID, using = "claimsPhoneNumbersecondary")
	private WebElement claimsPhoneNumberInput;
	
	@FindBy(how = How.ID, using = "insuranceConfirmButton")
	private WebElement submitSecondaryInsuranceButton;
	
	@FindBy(how = How.XPATH, using = "//*[text() = 'Tertiary']")
	private WebElement tertiaryInsuranceTab;
	
	public SecondaryInsurancePage(WebDriver driver) {
		super(driver);
	}

	public void setSecondaryInsuranceName(String insuranceNameSecondaryValue) {
		insuranceNameSecondaryInput.clear();
		insuranceNameSecondaryInput.sendKeys(insuranceNameSecondaryValue);
	}
	
	public void setSecondarySubscriberName(String subscriberNameValue) {
		subscriberNameInput.clear();
		subscriberNameInput.sendKeys(subscriberNameValue);
	}
	
	public void setSecondarySubscriberID(String subscriberIDValue) {
		subscriberIDInput.clear();
		subscriberIDInput.sendKeys(subscriberIDValue);
	}
	
	public void setSecondaryGroupNumber(String groupNumberValue) {
		groupNumberInput.clear();
		groupNumberInput.sendKeys(groupNumberValue);
	}
	
	public void setSecondaryDateIssued(String dateIssuedValue) {
		dateIssuedInput.clear();
		String[] date = dateIssuedValue.split(" ");
		String[] date0=date[1].split("/");
		dateIssuedInput.sendKeys(date[0]);
		dateIssuedInput.sendKeys(date0[0]);
		dateIssuedInput.sendKeys(date0[1]);
	}
	
	public void setClaimsPhoneNumber(String claimsPhoneNumberValue) {
		claimsPhoneNumberInput.clear();
		claimsPhoneNumberInput.sendKeys(claimsPhoneNumberValue);
	}
	
	public void submitSecondaryInsurance() {
		submitSecondaryInsuranceButton.click();
	}
	
	public TertiaryInsurancePage gotoTertiaryInsurance() {
		tertiaryInsuranceTab.click();
		return PageFactory.initElements(driver, TertiaryInsurancePage.class);
	}
}
