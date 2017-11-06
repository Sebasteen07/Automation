package com.medfusion.product.object.maps.patientportal1.page.createAccount;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGConstants;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CreateAccountPage extends BasePageObject {

	public static final String PAGE_NAME = "Beta site Create Account Page";

	@FindBy(name = "firstname")
	private WebElement txtPatientFirstname;

	@FindBy(name = "lastname")
	private WebElement txtLastname;

	@FindBy(name = "gender")
	private WebElement radioButtGender;

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

	@FindBy(name = "confirmpassword")
	private WebElement txtUserPasswordConfirmationActivation;

	@FindBy(name = "secretquestion")
	private WebElement txtSecretQuestionActivation;

	@FindBy(name = "secretanswer")
	private WebElement txtSecretAnswerActivation;

	@FindBy(name = "npp")
	private WebElement checkPrivacyInformation;

	@FindBy(name = "tou")
	private WebElement checkIntuitTerms;

	@FindBy(name = "addOption")
	private WebElement prefferedProvider;



	// ===========Full page=======================

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:0:input:input")
	private WebElement txtFirstName;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:2:input:input")
	private WebElement txtLastName;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:3:input:input")
	private WebElement txtDoB;

	@FindBy(name = "editPersonalInfo:border:editForm:inputs:4:input:input")
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

	@FindBy(name = "cont:questioninput")
	private WebElement txtActivationCode;

	@FindBy(xpath = "//select[@fieldid='preferences.preferredLocation']")
	private WebElement prefferedLocation;

	@FindBy(name = "buttons:submit")
	private WebElement btnActivate;


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
	 * @param patientZip
	 * @param address
	 * @param password
	 * @param Question
	 * @param answer
	 * @param state
	 * @param city
	 * @return
	 */
	public MyPatientPage createAccountPage(String patientFirstName, String patientLastName, String email, String patientPhoneNumber, String patientZip,
			String address, String password, String Question, String answer, String state, String city) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		driver.manage().timeouts().implicitlyWait(PortalConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);

		log("patientFirstName: " + patientFirstName);
		txtPatientFirstname.sendKeys(patientFirstName);
		log("patientLastName: " + patientLastName);
		txtLastname.sendKeys(patientLastName);
		radioButtGender.click();
		setBirthDate();

		txtPatientFirstname.click();
		log("PatientZip: " + patientZip);
		txtzipcode.sendKeys(patientZip);
		log("Email: " + email);
		txtEmail.sendKeys(email);
		btnContinue.click();

		log("I am on the second Page :======");
		txtFirstName.clear();
		txtFirstName.sendKeys(patientFirstName);
		txtLastName.clear();
		txtLastName.sendKeys(patientLastName);
		txtLastName.click();
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
		log("User ID: " + email);
		log("Password: " + password);
		txtUserID.sendKeys(email);
		txtPassword.sendKeys(password);
		txtConfirmpassword.sendKeys(password);
		chooseSecretQuestion(Question);
		txtSecretAnswer.sendKeys(answer);

		selectLocationIfNeeded();

		/*
		 * Note that after changes, selecting a provider is currently disabled on testpractices across all environments by default chooseProvider();
		 */
		if (chkAgreePatientPrivacyInfo.isDisplayed()) {
		    log("Found it");
		    javascriptClick(chkAgreePatientPrivacyInfo);
		}

		javascriptClick(chkAgreeIntuitTAndC);
		javascriptClick(btnSubmit);

		log("Clicked submit, returning");
		return PageFactory.initElements(driver, MyPatientPage.class);
	}

	/**
	 * try to create existing user in same practice
	 * 
	 * @param patientFirstName
	 * @param patientLastName
	 * @param gender
	 * @param patientZip
	 * @param patientSSN
	 * @param email
	 * @return
	 */
	public CreateAccountExistingUserPage tryCreateExistingUser(String patientFirstName, String patientLastName, String patientZip, String patientSSN,
			String email, String bMonth, String bDay, String bYear) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		log("patientFirstName: " + patientFirstName);
		txtPatientFirstname.sendKeys(patientFirstName);
		log("patientLastName: " + patientLastName);
		txtLastname.sendKeys(patientLastName);
		radioButtGender.click();
		setBirthDate(bMonth, bDay, bYear);

		txtPatientFirstname.click();
		log("PatientZip: " + patientZip);
		txtzipcode.sendKeys(patientZip);
		log("Email: " + email);
		txtEmail.sendKeys(email);
		btnContinue.click();

		return PageFactory.initElements(driver, CreateAccountExistingUserPage.class);
	}

	/**
	 * try to create existing user in different practice
	 * 
	 * @param patientFirstName
	 * @param patientLastName
	 * @param gender
	 * @param patientZip
	 * @param patientSSN
	 * @param email
	 * @return
	 */
	public CreateAccountHealthKeyPage tryCreateExistingUserDiffPrac(String patientFirstName, String patientLastName, String patientZip, String patientSSN,
			String email, String bMonth, String bDay, String bYear) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		log("patientFirstName: " + patientFirstName);
		txtPatientFirstname.sendKeys(patientFirstName);
		log("patientLastName: " + patientLastName);
		txtLastname.sendKeys(patientLastName);
		radioButtGender.click();
		setBirthDate(bMonth, bDay, bYear);

		txtPatientFirstname.click();
		log("PatientZip: " + patientZip);
		txtzipcode.sendKeys(patientZip);
		log("PatientSSN: " + patientSSN);
		txtssn.sendKeys(patientSSN);
		log("Email: " + email);
		txtEmail.sendKeys(email);
		btnContinue.click();

		return PageFactory.initElements(driver, CreateAccountHealthKeyPage.class);
	}


	private void setBirthDate(String Month, String Day, String Year) {
		Select birthdaySelect = new Select(birthdayMonth);
		birthdaySelect.selectByVisibleText(Month);
		birthdayDay.sendKeys(Day);
		birthdayYear.sendKeys(Year);
	}

	private void setBirthDate() {
		setBirthDate(PortalConstants.DateOfBirthMonth, PortalConstants.DateOfBirthDay, PortalConstants.DateOfBirthYear);
	}

	/**
	 * Chooses secret question. This method used to have a different name (choose provider) which did not make sense
	 */
	public void chooseSecretQuestion(String pProvider) {
		PortalUtil.setPortalFrame(driver);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='editLoginInfo:border:editForm:inputs:6:input:input']/option"));
		for (WebElement li : list) {
			int count = 1;
			if (li.getText().contains(pProvider)) {
				Select secretQuestion = new Select(dropDownSecretQuestion);
				secretQuestion.selectByIndex(count);
				break;
			}
			count++;
		}
	}

	public void selectLocationIfNeeded() {
		if (IHGUtil.exists(driver, 1, prefferedLocation)) {
			new Select(prefferedLocation).selectByIndex(1);
		}
	}

	/**
	 * @brief If the option to select a preffered provider is displayed then this method does so
	 * @author Adam
	 */
	public void chooseProvider() {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


		// check if there is preffered provider option
		if (driver.findElements(By.name("addOption")).size() > 0) {
			Select provider = new Select(prefferedProvider); // and if so, select the first one
			provider.selectByIndex(1);
		}

		driver.manage().timeouts().implicitlyWait(IHGConstants.SELENIUM_IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
	}

	public MyPatientPage fillInShortPatientCreation(String sPatientFirstName, String sPatientLastName, String sBirthDay, String sZipCode, String sSSN,
			String sEmail) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 30, txtPatientFirstname);

		txtPatientFirstname.sendKeys(sPatientFirstName);
		txtLastname.sendKeys(sPatientLastName);

		radioButtGender.click();

		setBirthDate();
		// txtbirthday.sendKeys(sBirthDay);
		txtzipcode.sendKeys(sZipCode);
		txtssn.sendKeys(sSSN);

		btnSubmit.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}

	public MyPatientPage fillPatientActivaion(String sZipCode, String sEmail, String sPassword, String sSecretQuestion, String sSecretAnswer) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		setBirthDate();
		txtzipcode.sendKeys(sZipCode);

		btnSubmit.click();

		log("I am on the second Page :======");

		IHGUtil.waitForElement(driver, 60, txtUserIdActivation);
		txtUserIdActivation.sendKeys(sEmail);
		txtUserPasswordActivation.sendKeys(sPassword);
		txtUserPasswordConfirmationActivation.sendKeys(sPassword);
		Select questionSelect = new Select(txtSecretQuestionActivation);
		questionSelect.selectByVisibleText(sSecretQuestion);
		txtSecretAnswerActivation.sendKeys(sSecretAnswer);

		log("Accepting license agreements");
		chooseProvider();
		selectLocationIfNeeded();
		checkPrivacyInformation.click();
		checkIntuitTerms.click();
		btnSubmit.click();

		return PageFactory.initElements(driver, MyPatientPage.class);
	}
}
