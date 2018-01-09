package com.medfusion.product.object.maps.precheck.page.myInsurance;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.product.object.maps.precheck.page.myAppointmentPreCheck.MyAppointmentPage;

public class TertiaryInsurancePage extends BasePageObject {

	@FindBy(how = How.ID, using = "insuranceNametertiary")
	private WebElement insuranceNameTertiaryInput;
	
	@FindBy(how = How.ID, using = "memberNametertiary")
	private WebElement subscriberNameTertiaryInput;
	
	@FindBy(how = How.ID, using = "memberIdtertiary")
	private WebElement subscriberIDTertiaryInput;
	
	@FindBy(how = How.ID, using = "groupNumbertertiary")
	private WebElement groupNumberTertiaryInput;
	
	@FindBy(how = How.ID, using = "dateIssuedtertiary")
	private WebElement dateIssuedTertiaryInput;
	
	@FindBy(how = How.ID, using = "claimsPhoneNumbertertiary")
	private WebElement claimsPhoneNumberTertiaryInput;
	
	@FindBy(how = How.ID, using = "insuranceConfirmButton")
	private WebElement submitTertiaryInsuranceButton;
	
	public TertiaryInsurancePage(WebDriver driver) {
		super(driver);
	}
	
	
	public void setTertiaryInsuranceName(String insuranceNameTertiaryValue) {
		insuranceNameTertiaryInput.clear();
		insuranceNameTertiaryInput.sendKeys(insuranceNameTertiaryValue);
	}
	
	public void setTertiarySubscriberName(String subscriberNameValue) {
		subscriberNameTertiaryInput.clear();
		subscriberNameTertiaryInput.sendKeys(subscriberNameValue);
	}
	
	public void setTertiarySubscriberID(String subscriberIDValue) {
		subscriberIDTertiaryInput.clear();
		subscriberIDTertiaryInput.sendKeys(subscriberIDValue);
	}
	
	public void setTertiaryGroupNumber(String groupNumberValue) {
		groupNumberTertiaryInput.clear();
		groupNumberTertiaryInput.sendKeys(groupNumberValue);
	}
	
	public void setTertiaryDateIssued(String dateIssuedValue) {
		dateIssuedTertiaryInput.clear();
		String[] date = dateIssuedValue.split(" ");
		String[] date0=date[1].split("/");
		dateIssuedTertiaryInput.sendKeys(date[0]);
		dateIssuedTertiaryInput.sendKeys(date0[0]);
		dateIssuedTertiaryInput.sendKeys(date0[1]);
		
	}
	
	public void setTertiaryClaimsPhoneNumber(String claimsPhoneNumberValue) {
		claimsPhoneNumberTertiaryInput.clear();
		claimsPhoneNumberTertiaryInput.sendKeys(claimsPhoneNumberValue);
	}
	
	public MyAppointmentPage submitTertiaryInsurance() {
		submitTertiaryInsuranceButton.click();
		return PageFactory.initElements(driver, MyAppointmentPage.class);
	}
	
}
