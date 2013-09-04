package com.intuit.ihg.product.community.page.CreateAnAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class CreateAnAccountPatientPage extends BasePageObject{

	@FindBy(how = How.NAME, using = "inputs:0:input:input")
	public WebElement firstname;

	@FindBy(how = How.NAME, using = "inputs:1:input:input")
	public WebElement lastname;

	@FindBy(how = How.NAME, using = "inputs:2:input:input")
	public WebElement email;

	@FindBy(css = "*[mask='confirmemail']")
	public WebElement confirmEmail;

	@FindBy(css = "*[mask='phone']")
	public WebElement phonenumber;
	
	@FindBy(name = "inputs:4:input:input:phonenumberwrapperwrapper:_body:phonenumberwrapper:phoneType")
	public WebElement phonetype;

	// @FindBy( how = How.NAME, using="inputs:3:input:input")
	// public WebElement dob; // date of birth

	// @FindBy( how = How.NAME, using="inputs:3:input:input:month")

	@FindBy(name = "inputs:5:input:input:month")
	public WebElement input_dob_month; // date of birth - month

	@FindBy(css = "select[name*='month']")
	public WebElement select_dob_month; // date of birth - month

	// @FindBy( how = How.NAME, using="inputs:3:input:input:day")

	@FindBy(name = "inputs:5:input:input:day")
	public WebElement input_dob_day; // date of birth - day

	@FindBy(css = "select[name*='day']")
	public WebElement select_dob_day; // date of birth - day

	// @FindBy( how = How.NAME, using="inputs:3:input:input:year")

	@FindBy(name = "inputs:5:input:input:year")
	public WebElement input_dob_year; // date of birth - year

	@FindBy(css = "*[mask*='zip']")
	public WebElement zipcode;

	@FindBy(css = "*[mask*='ssn']")
	public WebElement ssn;

	@FindBy(how = How.NAME, using = "buttons:submit")
	public WebElement submit;
	
	public CreateAnAccountPatientPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public String generateEmail(String sEnvironment, String sRandomNumber) {
		String email="";
		
		if (sEnvironment.equalsIgnoreCase("DEV3")) {
			email="com.test.ihg+DEV3NEW"+sRandomNumber+"@gmail.com";
		}
		
		else if (sEnvironment.equalsIgnoreCase("DEMO")) {
			email="com.test.ihg+DEMONEW"+sRandomNumber+"@gmail.com";
		}
		
		else if (sEnvironment.equalsIgnoreCase("PROD")) {
			email="com.test.ihg.prod+PRODNEW"+sRandomNumber+"@gmail.com";
		}
		
		else if (sEnvironment.equalsIgnoreCase("P10INT")) {
			email="com.test.ihg+P10INTNEW"+sRandomNumber+"@gmail.com";
		}
		
		return email;
	}
	
	public CreateAnAccountPasswordPage clickSubmit(){
		submit.click();
		return PageFactory.initElements(driver, CreateAnAccountPasswordPage.class);
	}
}
