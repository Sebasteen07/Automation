package com.medfusion.product.object.maps.precheck.page.patient;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class DemographicsPage extends BasePageObject {

	@FindBy(how = How.ID, using = "demographicsConfirmButton")
	private WebElement demographicsConfirmButton;
	
	@FindBy(how = How.ID, using = "firstName")
	private WebElement firstNameInput;
	
	@FindBy(how = How.ID, using = "middleName")
	private WebElement middleNameInput;
	
	@FindBy(how = How.ID, using = "lastName")
	private WebElement lastNameInput;
	
	@FindBy(how = How.ID, using = "dob")
	private WebElement dobInput;
	
	@FindBy(how = How.ID, using = "street")
	private WebElement streetInput;

	@FindBy(how = How.ID, using = "street2")
	private WebElement street2Input;
	
	@FindBy(how = How.ID, using = "city")
	private WebElement cityInput;
	
	@FindBy(how = How.ID, using = "state")
	private WebElement stateInput;
	
	@FindBy(how = How.ID, using = "zip")
	private WebElement zipInput;
	
	@FindBy(how = How.ID, using = "phone")
	private WebElement phoneInput;
	
	@FindBy(how = How.ID, using = "email")
	private WebElement emailInput;
	
	@FindBy(how = How.ID, using = "preferredPharmacyName")
	private WebElement preferredPharmacyNameInput;
	
	@FindBy(how = How.ID, using = "preferredPharmacyPhoneNumber")
	private WebElement preferredPharmacyPhoneNumber;
	
	
	public DemographicsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void fillInDemogprahicsData() {
		
	}
	
	public PatientHomePage clickConfirmDemographics() {
		demographicsConfirmButton.click();
		return PageFactory.initElements(driver, PatientHomePage.class);
	}
	
	

}
