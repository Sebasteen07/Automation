package com.intuit.ihg.product.object.maps.smintegration.page;

import java.util.List;

import com.medfusion.portal.utils.PortalConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
//import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class BetaCreateNewPatientPage extends BasePageObject {

	public String LName = null;
	public String FName = null;

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

	@FindBy(id = "addOption")
	private WebElement preferredProvider;

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

	@FindBy(name = "gender")
	private WebElement radioButtGender;



	public BetaCreateNewPatientPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
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
//	public MyPatientPage BetaSiteCreateAccountPage(String patientFirstName, String patientLastName, String email, String patientPhoneNumber,
//			String patientDob_Month, String patientDob_Day, String patientDob_Year, String patientZip, String patientSSN, String address, String password,
//			String Question, String answer, String state, String city) {
//
//		IHGUtil.PrintMethodName();
//		PortalUtil.setPortalFrame(driver);
//
//		patientFirstName = patientFirstName + PortalUtil.createRandomNumber();
//		log("patientFirstName: " + patientFirstName);
//		txtPatientFirstname.sendKeys(patientFirstName);
//		firstName(patientFirstName);
//		patientLastName = patientLastName + PortalUtil.createRandomNumber();
//		log("patientLastName: " + patientLastName);
//		txtLastname.sendKeys(patientLastName);
//		lastName(patientLastName);
//		radioButtGender.click();
//		setBirthDate();
//		// String birthday = PortalConstants.Month + "/" + patientDob_Day + "/" + patientDob_Year;
//		/* txtbirthday.sendKeys(birthday); */
//		txtPatientFirstname.click();// This a fix given for chrome browser
//		// for dealing date drop down
//		log("patientZip: " + patientZip);
//		txtzipcode.sendKeys(patientZip);
//		/*
//		 * log("patientSSN: " + patientSSN); txtssn.sendKeys(patientSSN);
//		 */
//
//		log("email" + email);
//		txtEmail.sendKeys(email);
//		btnContinue.click();
//
//		log("I am on the second Page :======");
//		/*
//		 * txtFirstName.clear(); txtFirstName.sendKeys(patientFirstName); txtLastName.clear(); txtLastName.sendKeys(patientLastName); txtDoB.clear();
//		 * //setBirthDate(); txtDoB.sendKeys(birthday); txtLastName.click();// This a fix given for chrome browser for // dealing date drop down txtSSN.clear();
//		 * txtSSN.sendKeys(patientSSN); chkGender.click();
//		 */
//		txtAddress.sendKeys(address);
//		txtCity.sendKeys(city);
//		Select selectstate = new Select(dropDownState);
//		selectstate.selectByVisibleText(state);
//		txtZipCode.clear();
//		txtZipCode.sendKeys(patientZip);
//		txtHomePhone.sendKeys(patientPhoneNumber);
//		txtEmailPage2.clear();
//		txtEmailPage2.sendKeys(email);
//		txtConfirmEmailPage2.clear();
//		txtConfirmEmailPage2.sendKeys(email);
//		txtUserID.sendKeys(email);
//		txtPassword.sendKeys(password);
//		txtConfirmpassword.sendKeys(password);
//		/*
//		 * Select secretQuestion=new Select(dropDownSecretQuestion); secretQuestion.selectByVisibleText(Question);
//		 */
//		chooseSecretQuestion(Question);
//		txtSecretAnswer.sendKeys(answer);
//		// chkAgreePatientPrivacyInfo.click();
//		if (driver.getPageSource().contains("Preferred Provider")) {
//			chooseProvider();
//		}
//		chkAgreeIntuitTAndC.click();
//		btnSubmit.click();
//
//		return PageFactory.initElements(driver, MyPatientPage.class);
//	}

	public void chooseSecretQuestion(String question) {
		PortalUtil.setPortalFrame(driver);
		List<WebElement> list = driver.findElements(By.xpath("//select[@name='editLoginInfo:border:editForm:inputs:6:input:input']/option"));
		for (WebElement li : list) {
			int count = 1;
			System.out.println("@@@@@@" + li.getText());
			if (li.getText().contains(question)) {
				Select secretQuestion = new Select(dropDownSecretQuestion);
				secretQuestion.selectByIndex(count);
				break;
			}
			count++;
		}

	}

	public void chooseProvider() {
		IHGUtil.PrintMethodName();
		Select secretQuestion = new Select(preferredProvider);
		secretQuestion.selectByIndex(1);
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

	public String firstName(String name) {
		FName = name;
		return FName;
	}

	public String lastName(String name) {
		LName = name;
		return LName;
	}
}
