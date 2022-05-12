//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.patientactivation;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.product.practice.api.utils.PracticeConstants;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;


public class PatientActivationPage extends BasePageObject {

	@FindBy(css = "a[href*='activate.newpat']")
	private WebElement addNewPatient;

	// Patient registration page
	@FindBy(css = "input[name='firstname']")
	private WebElement firstName;

	@FindBy(css = "input[name='lastname']")
	private WebElement lastName;

	@FindBy(css = "input[name='member_gender'][value='M']")
	private WebElement male;

	@FindBy(css = "input[name='member_gender'][value='F']")
	private WebElement female;

	@FindBy(css = "input[name='member_emrid']")
	private WebElement patientId;

	@FindBy(css = "input[name='email']")
	private WebElement email;

	@FindBy(css = "input[name='cemail']")
	private WebElement confirmEmail;

	@FindBy(css = "input[name='member_zip']")
	private WebElement zip;

	@FindBy(css = "input[type='submit']")
	private WebElement btnSubmit;

	@FindBy(xpath = "//input[@id='verifyButton']")
	private WebElement btnVerified;
	
	@FindBy(css = "input[onclick*='checkGenKey()']")
	private WebElement btnGenerateKey;

	@FindBy(css = "a[href*='activationCode']")
	private WebElement unlockLink;

	@FindBy(how = How.XPATH, using = "//input[@value='Done']")
	private WebElement btnDone;

	@FindBy(how = How.NAME, using = "birthday")
	private WebElement birthday;

	@FindBy(how = How.NAME, using = "zipcode")
	private WebElement zipcode;

	@FindBy(how = How.NAME, using = "ssn")
	private WebElement ssn;

	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement Submit;

	@FindBy(css = "input[name='member_ssn1']")
	private WebElement SSN1;

	@FindBy(css = "input[name='member_ssn2']")
	private WebElement SSN2;

	@FindBy(css = "input[name='member_ssn3']")
	private WebElement SSN3;

	@FindBy(css = "input[name='member_home_ac']")
	private WebElement Home_No1;

	@FindBy(css = "input[name='member_home_pre']")
	private WebElement Home_No2;

	@FindBy(css = "input[name='member_home_suff']")
	private WebElement Home_No3;

	@FindBy(css = "input[name='member_addr1']")
	private WebElement AddLine1;

	@FindBy(css = "input[name='member_addr2']")
	private WebElement AddLine2;

	@FindBy(css = "input[name='member_city']")
	private WebElement City;

	@FindBy(name = "member_state")
	private WebElement State;

	@FindBy(xpath = ".//*[@id='content']/form/table/tbody/tr[8]/td[2]")
	private WebElement unlockCode;
	
	@FindBy(xpath = "//*[@id=\"submenu\"]/ul/li[2]/a")
	private WebElement failuresLink;
	
	@FindBy(xpath = "//*[@value='Re-send Invitation(s)']")
	private WebElement btnResendInvite;
	
	@FindBy(xpath = "//input[@type='checkbox']")
	private WebElement checkboxSelectAll;
	
	@FindBy(xpath = "//input[@type='submit']")
	private WebElement btnFilterPatients;
	
	@FindBy(how = How.XPATH, using = "//span[@class='feedbackPanelINFO']")
	private WebElement resendSuccessMsg;
	
	@FindBy(how = How.XPATH, using = "//span[@class='feedbackPanelWARNING']")
	private WebElement resendFailureMsg;
	
	static  String firstNameString = "Beta" + IHGUtil.createRandomNumericString();
	
	private String lastNameString = "";
	private String patientIdString = "";
	private String zipCodeString = "";
	private String emailAddressString = "";
	private String unlocklink = "";

	public String getFirstNameString() {
		return firstNameString;
	}

	public String getLastNameString() {
		return lastNameString;
	}

	public String getPatientIdString() {
		return patientIdString;
	}

	public String getZipCodeString() {
		return zipCodeString;
	}

	public String getEmailAddressString() {
		return emailAddressString;
	}

	public String getUnlockLink() {
		return unlocklink;
	}

	public PatientActivationPage(WebDriver driver) {
		super(driver);
	}

	public void clickAddNewPatient() {
		IHGUtil.PrintMethodName();
		addNewPatient.click();
	}

	public void setInitialDetails(String sEmail) throws InterruptedException {
		lastNameString = "Tester";
		emailAddressString = sEmail;
		patientIdString = emailAddressString;

		IHGUtil.PrintMethodName();
		Log4jUtil.log("New Random First Name is " + firstNameString);
		firstName.sendKeys(firstNameString);
		lastName.sendKeys(lastNameString);
		male.click();
		Log4jUtil.log("New Random patientid is " + patientIdString);
		patientId.sendKeys(patientIdString);

		Log4jUtil.log("New Random Email is " + emailAddressString);
		email.sendKeys(emailAddressString);
		confirmEmail.sendKeys(emailAddressString);

		setDOB(JalapenoConstants.DATE_OF_BIRTH_MONTH, JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);

		AddLine1.sendKeys("5501 Dillard Dr");
		City.sendKeys("Cary");
		zip.sendKeys(PracticeConstants.ZIP_CODE);
		Log4jUtil.log("Click on the Register Patient Button");
		clickRegPatient();
		Thread.sleep(2000); //Holding exec for few secs
		Log4jUtil.log("Click on the Verify Button");
		clickVerify();

		IHGUtil.waitForElement(driver, 10, unlockLink);
		unlocklink = unlockLink.getText().trim();
		assertTrue(!unlocklink.isEmpty(), "### ERROR: Couldn't get unlock link");

		Log4jUtil.log("#### The unlock link exists and the link is:" + unlocklink);
		clickDone();

	}


	public String setInitialDetailsAllFields(String firstName, String lastName, String gender, String patientID, String homePhone, String email, String month,
			String day, String year, String address1, String address2, String city, String state, String zipCode) {
		IHGUtil.PrintMethodName();

		log("Patient Name is " + firstName + " " + lastName);
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		if (gender.equals("M"))
			this.male.click();
		else
			this.female.click();

		log("PatientID " + patientID);
		this.patientId.sendKeys(patientID);
		this.Home_No1.sendKeys(homePhone.substring(0, 3));
		this.Home_No2.sendKeys(homePhone.substring(3, 6));
		this.Home_No3.sendKeys(homePhone.substring(6, 10));

		log("Patient email is " + email);
		this.email.sendKeys(email);
		this.confirmEmail.sendKeys(email);

		log("Patient DOB is " + month + "/" + day + "/" + year);
		Select dobMonth = new Select(driver.findElement(By.name("dob_m")));
		dobMonth.selectByValue(month);
		Select dobDay = new Select(driver.findElement(By.name("dob_d")));
		dobDay.selectByVisibleText(day);
		Select dobYear = new Select(driver.findElement(By.name("dob_y")));
		dobYear.selectByVisibleText(year);

		log("Patient ZIP is " + zipCode);
		this.AddLine1.sendKeys(address1);
		this.AddLine2.sendKeys(address2);
		this.City.sendKeys(city);
		Select stateSelect = new Select(State);
		stateSelect.selectByVisibleText(state);
		this.zip.sendKeys(zipCode);

		clickRegPatient();
		clickVerify();

		IHGUtil.waitForElement(driver, 10, unlockLink);
		unlocklink = unlockLink.getText().trim();
		assertNotNull(unlocklink, "### ERROR: Couldn't get unlock link");

		log("#### The unlock link exists and the link is:" + unlocklink);
		clickDone();
		return unlocklink;
	}

	public void setDOB(String month, String day, String year) {

		Select dobMonth = new Select(driver.findElement(By.name("dob_m")));
		dobMonth.selectByVisibleText(month);

		Select dobDay = new Select(driver.findElement(By.name("dob_d")));
		dobDay.selectByVisibleText(day);

		Select dobYear = new Select(driver.findElement(By.name("dob_y")));
		dobYear.selectByVisibleText(year);
	}

	public void clickRegPatient() {
		IHGUtil.PrintMethodName();
		btnSubmit.click();
	}

	public void clickVerify() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, btnVerified);
		btnVerified.click();
	}
	
    public void clickGetKey() {
		IHGUtil.PrintMethodName();
		btnGenerateKey.click();
	}

	public void clickDone() {
		IHGUtil.PrintMethodName();
		btnDone.click();
	}

	public String setFullDetails(String testDataEmail,String testDataLastName,String homePhoneNo,String address1,String address2,String city,String testDataState,String zipCode) {
		firstNameString = "MF" + IHGUtil.createRandomNumericString();
		patientIdString = IHGUtil.createRandomNumericString();
		emailAddressString = IHGUtil.createRandomEmailAddress(testDataEmail);

		IHGUtil.PrintMethodName();
		Log4jUtil.log("First Name is :" + firstNameString);
		firstName.sendKeys(firstNameString);

		lastName.sendKeys(testDataLastName);
		male.click();
		/*
		 * SSN1.sendKeys(testData.getSSN().subSequence(0, 3)); SSN2.sendKeys(testData.getSSN().subSequence(3, 5)); SSN3.sendKeys(testData.getSSN().subSequence(5,
		 * 9));
		 */
		Log4jUtil.log("Patientid is :" + patientIdString);
		patientId.sendKeys(patientIdString);
		Home_No1.sendKeys(homePhoneNo.substring(0, 3));
		Home_No2.sendKeys(homePhoneNo.substring(3, 6));
		Home_No3.sendKeys(homePhoneNo.substring(6, 10));
		Log4jUtil.log("Email is :" + emailAddressString);
		email.sendKeys(emailAddressString);
		confirmEmail.sendKeys(emailAddressString);
		setDOB(JalapenoConstants.DATE_OF_BIRTH_MONTH, JalapenoConstants.DATE_OF_BIRTH_DAY, JalapenoConstants.DATE_OF_BIRTH_YEAR);
		AddLine1.sendKeys(address1);
		AddLine2.sendKeys(address2);
		City.sendKeys(city);
		Select stateSelect = new Select(State);
		stateSelect.selectByVisibleText(testDataState);
		zip.sendKeys(zipCode);

		clickRegPatient();
		IHGUtil.waitForElement(driver, 30, btnVerified);
		clickVerify();

		IHGUtil.waitForElement(driver, 30, unlockLink);
		unlocklink = unlockLink.getText().trim();
		assertTrue(!unlocklink.equals(""), "### ERROR: Couldn't get unlock link");
		String activationCode = unlockCode.getText();
		Log4jUtil.log("Unlock Code :" + activationCode);

		Log4jUtil.log("#### The unlock link exists and the link is :" + unlocklink);
		clickDone();
		return activationCode;
	}

	public boolean checkGuardianUrl(String url) {
		IHGUtil.PrintMethodName();
		return url.contains("guardian");
	}

	public void setInitialDetailsPortal2(int flag, String firstname, String sEmail) throws IOException {
		PropertyFileLoader testData=new PropertyFileLoader();
		lastNameString = "Tester";
		emailAddressString = sEmail;
		patientIdString =  "jalapeno"+IHGUtil.createRandomNumericString();;
		IHGUtil.PrintMethodName();
		log("New Random First Name is " + firstname);
		firstName.sendKeys(firstname);
		lastName.sendKeys(lastNameString);
		male.click();
		log("New Random patientid is " + patientIdString);
		patientId.sendKeys(patientIdString);

		log("New Random Email is " + sEmail);
		email.sendKeys(sEmail);
		confirmEmail.sendKeys(sEmail);

		if(firstname.contains("Dependent"))
		{
			setDOB(testData.getDOBMonthText(), testData.getDOBDay(),testData.getDOBYearUnderage());
		}
		else
		{
			setDOB(testData.getDOBMonthText(), testData.getDOBDay(),testData.getDOBYear());
		}
		zip.sendKeys(PracticeConstants.ZIP);

		clickRegPatient();
		clickVerify();
		
		if(flag==1) {
		IHGUtil.waitForElement(driver, 10, unlockLink);
		unlocklink = unlockLink.getText().trim();
		assertTrue(!unlocklink.isEmpty(), "### ERROR: Couldn't get unlock link");
		log("#### The unlock link exists and the link is:" + unlocklink);
		
		} else
		{
			log("Patient activation link is not present- Auto enrolled");
		}
		clickDone();
		
	}

	public void setInitialDetailsWithPatientId(int flag, String patientEmail,String patientIdString) throws IOException {
		PropertyFileLoader testData=new PropertyFileLoader();
		String firstNameString= "Automation"+ IHGUtil.createRandomNumericString();
		String lastNameString = "Tester" + IHGUtil.createRandomNumericString();
		IHGUtil.PrintMethodName();
		log("New Random First Name is " + firstNameString);
		firstName.sendKeys(firstNameString);
		lastName.sendKeys(lastNameString);
		male.click();
		patientId.sendKeys(patientIdString);
		setDOB(testData.getDOBMonthText(), testData.getDOBDay(),testData.getDOBYear());

		log("New Random Email is " + patientEmail);
		email.sendKeys(patientEmail);
		confirmEmail.sendKeys(patientEmail);
		zip.sendKeys(PracticeConstants.ZIP);

		clickRegPatient();
		clickVerify();
		
		if(flag==1) {
		IHGUtil.waitForElement(driver, 10, unlockLink);
		unlocklink = unlockLink.getText().trim();
		assertTrue(!unlocklink.isEmpty(), "### ERROR: Couldn't get unlock link");
		log("#### The unlock link exists and the link is:" + unlocklink);
		
		} else
		{
			log("Patient activation link is not present- Auto enrolled");
		}
		clickDone();
	}
	
	public void setInitialDetailsFields(String firstName, String lastName, String gender, String patientID, String homePhone, String email, String month,
			String day, String year, String address1, String address2, String city, String state, String zipCode) {
		IHGUtil.PrintMethodName();

		log("Patient Name is " + firstName + " " + lastName);
		this.firstName.sendKeys(firstName);
		this.lastName.sendKeys(lastName);
		if (gender.equals("M"))
			this.male.click();
		else
			this.female.click();

		log("PatientID " + patientID);
		this.patientId.sendKeys(patientID);
		this.Home_No1.sendKeys(homePhone.substring(0, 3));
		this.Home_No2.sendKeys(homePhone.substring(3, 6));
		this.Home_No3.sendKeys(homePhone.substring(6, 10));

		log("Patient email is " + email);
		this.email.sendKeys(email);
		this.confirmEmail.sendKeys(email);

		log("Patient DOB is " + month + "/" + day + "/" + year);
		Select dobMonth = new Select(driver.findElement(By.name("dob_m")));
		dobMonth.selectByValue(month);
		Select dobDay = new Select(driver.findElement(By.name("dob_d")));
		dobDay.selectByVisibleText(day);
		Select dobYear = new Select(driver.findElement(By.name("dob_y")));
		dobYear.selectByVisibleText(year);

		log("Patient ZIP is " + zipCode);
		this.AddLine1.sendKeys(address1);
		this.AddLine2.sendKeys(address2);
		this.City.sendKeys(city);
		Select stateSelect = new Select(State);
		stateSelect.selectByVisibleText(state);
		this.zip.sendKeys(zipCode);

		clickRegPatient();
	}

	public void clickonFailuresLink() {
		failuresLink.click();
		
	}

	public boolean resendFailures() throws Exception {
		log("Set date range to resend failures");
		driver.switchTo().frame("iframebody");
		Select fromMonth = new Select(driver.findElement(By.id("id19")));
		fromMonth.selectByVisibleText(JalapenoConstants.FROM_MONTH);
		Select fromDay = new Select(driver.findElement(By.id("id1a")));
		fromDay.selectByVisibleText(JalapenoConstants.FROM_DAY);
		Select fromYear = new Select(driver.findElement(By.id("id1b")));
		fromYear.selectByVisibleText(JalapenoConstants.FROM_YEAR);
		
		Select toMonth = new Select(driver.findElement(By.id("id20")));
		toMonth.selectByVisibleText(JalapenoConstants.TO_MONTH);
		Select toDay = new Select(driver.findElement(By.id("id21")));
		toDay.selectByVisibleText(JalapenoConstants.TO_DAY );
		Select toYear = new Select(driver.findElement(By.id("id22")));
		toYear.selectByVisibleText(JalapenoConstants.TO_YEAR);
		
		btnFilterPatients.click();
		IHGUtil.waitForElement(driver, 10, checkboxSelectAll);
		checkboxSelectAll.click();
		btnResendInvite.click();
		
		try {
			resendSuccessMsg.isDisplayed();
			log("Invitation failures resent");
			
		} catch (Exception e) {
			// Helpful message about possible issues
			throw new Exception(resendFailureMsg.getText());
		}
		return true;

}
}
