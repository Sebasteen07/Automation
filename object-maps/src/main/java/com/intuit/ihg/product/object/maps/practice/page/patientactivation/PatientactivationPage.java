package com.intuit.ihg.product.object.maps.practice.page.patientactivation;
import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ifs.csscat.report.model.testng.Test;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.practice.utils.PracticeConstants;

public class PatientactivationPage extends BasePageObject{
	
	public PatientactivationPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css="a[href*='activate.newpat']")
	private WebElement addNewPatient;
	
	//Patient registration page
	@FindBy(css="input[name='firstname']")
	private WebElement firstName;

	@FindBy(css="input[name='lastname']")
	private WebElement lastName;
	
	@FindBy(css="input[name='member_gender'][value='M']")
	private WebElement male;
	
	@FindBy(css="input[name='member_gender'][value='F']")
	private WebElement female;
	
	@FindBy(css="input[name='member_emrid']")
	private WebElement patientId;
	
	@FindBy(css="input[name='email']")
	private WebElement email;
	
	@FindBy(css="input[name='cemail']")
	private WebElement confirmEmail;
	
	@FindBy(css="input[name='member_zip']")
	private WebElement zip;
	
	@FindBy(css="input[type='submit']")
	private WebElement btnSubmit;
	
	@FindBy(css="input[onclick*='checkVerified()']")
	private WebElement btnVerified;
	
	@FindBy(css="input[onclick*='checkGenKey()']")
	private WebElement btnGenerateKey;
	
	@FindBy(css="a[href*='home.unlock']")
	private WebElement unlockLink;
	
	@FindBy(css="input[onclick*='returnToCaller()']")
	private WebElement btnDone;
	
	@FindBy( how = How.NAME, using="birthday")
	private WebElement birthday;
	
	@FindBy( how = How.NAME, using="zipcode")
	private WebElement zipcode;
	
	@FindBy( how = How.NAME, using="ssn")
	private WebElement ssn;

	@FindBy( how = How.NAME, using="buttons:submit")
	private WebElement Submit;
	
	String unlocklink ="";
	
	public void clickAddNewPatient() {
	
	IHGUtil.PrintMethodName();
	
	addNewPatient.click();
	}
	
	private String firstNameString="";
	private String lastNameString="";
	private String patientIdString="";
	private String zipCodeString="";
	private String emailAddressString="";
	
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
	
	
	public void setinitialdetails(String sEmail)
	{
		firstNameString="Beta" + IHGUtil.createRandomNumericString();
		lastNameString="Tester";
		patientIdString=IHGUtil.createRandomNumericString();
		zipCodeString=PracticeConstants.Zipcode;
		emailAddressString=IHGUtil.createRandomEmailAddress(sEmail);
		
		IHGUtil.PrintMethodName();
		Log4jUtil.log("New Random First Name is"+firstNameString);
		firstName.sendKeys(firstNameString);
		lastName.sendKeys(lastNameString);
		male.click();
		Log4jUtil.log("New Random patientid is"+patientIdString);
		patientId.sendKeys(patientIdString);
		
		Log4jUtil.log("New Random Email is"+emailAddressString);
		email.sendKeys(emailAddressString);
		confirmEmail.sendKeys(emailAddressString);
		setDOB(PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);
		zip.sendKeys(zipCodeString);
		clickregpatient();
		clickverify();
		
//		clickgetkey deprecated after US8665 (remove comment to test where that change is not yet deployed) --by JakubO
//		clickgetkey();
		
		IHGUtil.waitForElement(driver, 10, unlockLink);
		unlocklink=unlockLink.getText().trim();
		Assert.assertTrue("### ERROR: Couldn't get unlock link", unlocklink!="");
		
		Log4jUtil.log("#### The unlock link exists and the link is:"+unlocklink);
		clickdone();
		
		/*driver.get(unlocklink);
		maxWindow();
		IHGUtil.setFrame(driver, "iframebody");
		birthday.sendKeys(PracticeConstants.DOB);
		zipcode.sendKeys(PracticeConstants.Zipcode);
		Submit.click();*/
		
		
		
	}
	
	public void setDOB( String month, String day, String year ) {
		
		Select dobMonth = new Select( driver.findElement(By.name("dob_m")));
		dobMonth.selectByVisibleText( month );
		
		Select dobDay = new Select( driver.findElement(By.name("dob_d")));
		dobDay.selectByVisibleText( day );
		
		Select dobYear = new Select( driver.findElement(By.name("dob_y")));
		dobYear.selectByVisibleText( year );
	}
	
	public void clickregpatient()
	{
		IHGUtil.PrintMethodName();
		btnSubmit.click();
	}
	
	public void clickverify()
	{
		IHGUtil.PrintMethodName();
		btnVerified.click();
	}
	public void clickgetkey()
	{
		IHGUtil.PrintMethodName();
		btnGenerateKey.click();
	}

	public void clickdone()
	{
		IHGUtil.PrintMethodName();
		btnDone.click();
	}
	
	public String getUnlockLink() {
		return unlocklink;
	}
	
	
	}
