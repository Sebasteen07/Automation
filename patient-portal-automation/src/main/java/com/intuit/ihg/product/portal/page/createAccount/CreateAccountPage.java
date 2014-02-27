package com.intuit.ihg.product.portal.page.createAccount;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class CreateAccountPage extends BasePageObject {

	public static final String PAGE_NAME = "Beta site Create Account Page";

	@FindBy(name = "firstname")
	private WebElement txtPatientFirstname;

	@FindBy(name = "lastname")
	private WebElement txtLastname;

	@FindBy(name = "birthday")
	private WebElement txtbirthday;

	@FindBy(name = "zipcode")
	private WebElement txtzipcode;

	@FindBy(name = "ssn")
	private WebElement txtssn;

	@FindBy(name = "email")
	private WebElement txtEmail;

	@FindBy(name = "buttons:submit")
	private WebElement btnContinue;
	
	@FindBy(name = "userid")
	private WebElement txtUserIdActivation;
	
	@FindBy(name = "password")
	private WebElement txtUserPasswordActivation;
	
	@FindBy(name ="confirmpassword")
	private WebElement txtUserPasswordConfirmationActivation;
	
	@FindBy(name = "secretquestion")
	private WebElement txtSecretQuestionActivation;
	
	@FindBy(name = "secretanswer")
	private WebElement txtSecretAnswerActivation;
	
	@FindBy(name = "npp")
	private WebElement checkPrivacyInformation;
	
	@FindBy(name = "tou")
	private WebElement checkIntuitTerms;

	// ===========Full page=======================

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:0:input:input")
	private WebElement txtFirstName;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:2:input:input")
	private WebElement txtLastName;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:3:input:input")
	private WebElement txtDoB;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:4:input:input")
	private WebElement txtSSN;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:5:input:input")
	private WebElement chkGender;

	@FindBy(name = "editMailingAddress:border:editForm:inputs:0:input:input")
	private WebElement txtAddress;

	@FindBy(name = "editMailingAddress:border:editForm:inputs:2:input:input")
	private WebElement txtCity;

	@FindBy(name = "editMailingAddress:border:editForm:inputs:3:input:input")
	private WebElement dropDownState;

	@FindBy(name = "editMailingAddress:border:editForm:inputs:4:input:input")
	private WebElement txtZipCode;

	@FindBy(name = "editContactInfo:border:editForm:inputs:0:input:input")
	private WebElement txtHomePhone;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:0:input:input")
	private WebElement txtEmailPage2;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:1:input:input")
	private WebElement txtConfirmEmailPage2;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:2:input:input")
	private WebElement txtUserID;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:4:input:input")
	private WebElement txtPassword;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:5:input:input")
	private WebElement txtConfirmpassword;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:6:input:input")
	private WebElement dropDownSecretQuestion;

	@FindBy(name = "editLoginInfo:border:editForm:inputs:7:input:input")
	private WebElement txtSecretAnswer;

	@FindBy(name = "border:nppack")
	private WebElement chkAgreePatientPrivacyInfo;

	@FindBy(name = "touWrapper:touack")
	private WebElement chkAgreeIntuitTAndC;

	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;

	@FindBy(className = "iframecontainer")
	private WebElement pageContent;
	
	@FindBy(name = "birthday:month")
	private WebElement birthdayMonth;
	
	@FindBy(name = "birthday:day")
	private WebElement birthdayDay;
	
	@FindBy(name = "birthday:year")
	private WebElement birthdayYear;

	public CreateAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * creating a new user in Beta patient portal
	 * 
	 * @param patientFirstName
	 * @param patientLastName
	 * @param email
	 * @param patientPhoneNumber
	 * @param patientDob_Month
	 * @param patientDob_Day
	 * @param patientDob_Year
	 * @param patientZip
	 * @param patientSSN
	 * @param address
	 * @param password
	 * @param Question
	 * @param answer
	 * @param state
	 * @param city
	 * @return
	 */
	public MyPatientPage createAccountPage(String patientFirstName, String patientLastName, String email, String patientPhoneNumber,
					 String patientZip, String patientSSN,
					String address, String password, String Question, String answer, String state, String city) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		patientFirstName = patientFirstName + PortalUtil.createRandomNumber();
		log("patientFirstName" + patientFirstName);
		txtPatientFirstname.sendKeys(patientFirstName);
		patientLastName = patientLastName + PortalUtil.createRandomNumber();
		log("patientLastName" + patientLastName);
		txtLastname.sendKeys(patientLastName);
		
		//Setting Date of birth
		birthdayMonth.sendKeys(PortalConstants.DateOfBirthMonth);
		birthdayDay.sendKeys(PortalConstants.DateOfBirthDay);
		birthdayYear.sendKeys(PortalConstants.DateOfBirthYear);
		
		txtPatientFirstname.click();// This a fix given for chrome browser
						// for dealing date drop down
		log("patientZip" + patientZip);
		txtzipcode.sendKeys(patientZip);
		log("patientSSN" + patientSSN);
		txtssn.sendKeys(patientSSN);
		log("email" + email);
		txtEmail.sendKeys(email);
		btnContinue.click();

		log("I am on the second Page :======");
		txtFirstName.clear();
		txtFirstName.sendKeys(patientFirstName);
		txtLastName.clear();
		txtLastName.sendKeys(patientLastName);
		txtLastName.click();// This a fix given for chrome browser for
					// dealing date drop down
		txtSSN.clear();
		txtSSN.sendKeys(patientSSN);
		chkGender.click();
		txtAddress.sendKeys(address);
		txtCity.sendKeys(city);
		Select selectstate = new Select(dropDownState);
		selectstate.selectByVisibleText(state);
		txtZipCode.clear();
		txtZipCode.sendKeys(patientZip);
		txtHomePhone.sendKeys(patientPhoneNumber);
		txtEmailPage2.clear();
		txtEmailPage2.sendKeys(email);
		txtConfirmEmailPage2.clear();
		txtConfirmEmailPage2.sendKeys(email);
		txtUserID.sendKeys(email);
		txtPassword.sendKeys(password);
		txtConfirmpassword.sendKeys(password);
		/*
		 * Select secretQuestion=new Select(dropDownSecretQuestion);
		 * secretQuestion.selectByVisibleText(Question);
		 */
		chooseProvider(Question);
		txtSecretAnswer.sendKeys(answer);
		chkAgreePatientPrivacyInfo.click();
		chkAgreeIntuitTAndC.click();
		btnSubmit.click();

		return PageFactory.initElements(driver, MyPatientPage.class);
	}

	public void chooseProvider(String pProvider) {
		PortalUtil.setPortalFrame(driver);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='editLoginInfo:border:editForm:inputs:6:input:input']/option"));
		for (WebElement li : list) {
			int count = 1;
			System.out.println("@@@@@@" + li.getText());
			if (li.getText().contains(pProvider)) {
				Select secretQuestion = new Select(dropDownSecretQuestion);
				secretQuestion.selectByIndex(count);
				break;
			}
			count++;
		}

	}

	public MyPatientPage fillInShortPatientCreation(String sPatientFirstName, String sPatientLastName, String sBirthDay, String sZipCode,
					String sSSN, String sEmail) {
		
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 30, txtPatientFirstname);

		txtPatientFirstname.sendKeys(sPatientFirstName);
		txtLastname.sendKeys(sPatientLastName);
		txtbirthday.sendKeys(sBirthDay);
		txtzipcode.sendKeys(sZipCode);
		txtssn.sendKeys(sSSN);
		txtEmail.sendKeys(sEmail);

		btnSubmit.click();

		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	public MyPatientPage fillEmailActivaion(String sPatientLastName, String sBirthDay, String sZipCode,
					String sSSN, String sEmail, String sPassword, String sSecretQuestion, String sSecretAnswer) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 30, txtLastname);

		//txtLastname.sendKeys(sPatientLastName);----> commented by Bala
		txtbirthday.sendKeys(sBirthDay);
		txtzipcode.sendKeys(sZipCode);
		txtssn.sendKeys(sSSN);
		//txtEmail.sendKeys(sEmail);----> commented by Bala

		btnSubmit.click();
		
		log("I am on the second Page :======");
		
		IHGUtil.waitForElement(driver, 60, txtUserIdActivation);
		txtUserIdActivation.sendKeys(sEmail);
		txtUserPasswordActivation.sendKeys(sPassword);
		txtUserPasswordConfirmationActivation.sendKeys(sPassword);
		txtSecretQuestionActivation.sendKeys(sSecretQuestion);
		txtSecretAnswerActivation.sendKeys(sSecretAnswer);
		
		//Accepting license agreements
		
		checkPrivacyInformation.click();
		checkIntuitTerms.click();
		btnSubmit.click();
		
		

		return PageFactory.initElements(driver, MyPatientPage.class);
	}

		/*
		 * 
		 * @FindBy(name="firstname") private WebElement
		 * txtPatientFirstname;
		 * 
		 * @FindBy(name="lastname") private WebElement txtLastname;
		 * 
		 * @FindBy(name="birthday") private WebElement txtbirthday;
		 * 
		 * @FindBy(name="zipcode") private WebElement txtzipcode;
		 * 
		 * @FindBy(name="ssn") private WebElement txtssn;
		 * 
		 * @FindBy(name="email") private WebElement txtEmail;
		 * 
		 * @FindBy(name="buttons:submit") private WebElement btnContinue;
		 */

	

	/*
	 * public void waitForpageContent(WebDriver driver) {
	 * IHGUtil.PrintMethodName(); PortalUtil.setPortalFrame(driver);
	 * IHGUtil.waitForElement(driver, 60, pageContent); }
	 */

}
