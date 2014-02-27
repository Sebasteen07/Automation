package com.intuit.ihg.product.portal.page.createAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class CreateAccountPageOnBetaSite extends BasePageObject {
	
	public static final String PAGE_NAME = "Create Account Page";
	
	@FindBy(how = How.NAME, using = "inputs:0:input:input")
	private WebElement txtPatientFirstname;

	@FindBy(how = How.NAME, using = "inputs:1:input:input")
	private WebElement txtLastname;

	@FindBy(how = How.NAME, using = "inputs:2:input:input")
	private WebElement txtEmail;

	@FindBy(css = "*[mask='confirmemail']")
	private WebElement txtConfirmEmail;

	@FindBy(css = "*[mask='phone']")
	private WebElement txtPhonenumber;
	
	@FindBy(css = "select[name*='phoneType']")
	private WebElement DropDownPhonetype;
	
	@FindBy(css = "select[name*='month']")
	private WebElement DropDown_dob_month; 
	
	@FindBy(css = "input[name*='day']")
	private WebElement txtDob_day; // date of birth - day

	@FindBy(css = "input[name*='year']")
	private WebElement txtDob_year; // date of birth - year

	@FindBy(css = "*[mask*='zip']")
	private WebElement txtzipcode;

	@FindBy(css = "*[mask*='ssn']")
	private WebElement txtssn;

	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement btnsubmit;
	
	public String emailAddress=null;
	
	public CreateAccountPageOnBetaSite(WebDriver driver)
	{
		super(driver);
	}
	
	public String storeEmail(String email)
	{   
		emailAddress=email;
		return emailAddress;
	}
	
	public CreateAccountPasswordPage createAccount( String patientFirstName,String patientLastName,String email,String patientEmail,String patientPhoneNumber,
	 String patientPhoneType, String patientDob_Month, String patientDob_Day,String patientDob_Year,String patientZip,
	 String patientSSN)
	{
		patientFirstName=patientFirstName+PortalUtil.createRandomNumber();
		log("patientFirstName"+patientFirstName);
		txtPatientFirstname.sendKeys(patientFirstName);
		patientLastName=patientLastName+PortalUtil.createRandomNumber();
		log("patientLastName"+patientLastName);
		txtLastname.sendKeys(patientLastName);
		log("email"+email);
		txtEmail.sendKeys(email);
		log("txtConfirmEmail"+email);
		txtConfirmEmail.sendKeys(email);	
		txtPhonenumber.clear();
		log("patientPhoneNumber"+patientPhoneNumber);
		txtPhonenumber.sendKeys(patientPhoneNumber);
		Select Phonetype=new Select(DropDownPhonetype);
		log("patientPhoneType"+patientPhoneType);
		Phonetype.selectByVisibleText(patientPhoneType);
		//Phonetype.selectByValue(patientPhoneType);
		Select dob_month=new Select(DropDown_dob_month);
		log("patientDob_Month"+patientDob_Month);
		dob_month.selectByVisibleText(patientDob_Month);
		//dob_month.selectByValue(patientDob_Month);
		log("patientDob_Day"+patientDob_Day);
		txtDob_day.sendKeys(patientDob_Day);
		log("patientDob_Year"+patientDob_Year);
		txtDob_year.sendKeys(patientDob_Year);
		log("patientZip"+patientZip);
		txtzipcode.sendKeys(patientZip);
		log("patientSSN"+patientSSN);
		txtssn.sendKeys(patientSSN);
		btnsubmit.click();
		return PageFactory.initElements(driver, CreateAccountPasswordPage.class);
		
	}
	

}
