package com.medfusion.product.patientportal2.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

// TODO to delete? Probably not used anywhere
public class JalapenoCreatePatientTest extends BaseTestNGWebDriver {

	private String email = "";
	private String password = "";
	private String url = "";
	private String firstName = "";
	private String lastName = "";

	// Getters for getting the email and password value and reusing in other
	// tests
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String str) {
		url = str;
	}

	public void setFirstName(String str) {
		firstName = str;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String str) {
		lastName = str;
	}

	public String getLastName() {
		return lastName;
	}

	public void initPatientData(WebDriver driver, PropertyFileLoader testData) {
		IHGUtil.PrintMethodName();

		int randomize = IHGUtil.createRandomNumber();

		// Setting data according to test purpose
		if (email.isEmpty()) {
			email = IHGUtil.createRandomEmailAddress(testData.getEmail());
		}

		if (firstName.isEmpty()) {
			firstName = testData.getFirstName() + randomize;
		}

		if (lastName.isEmpty()) {
			lastName = testData.getLastName() + randomize;
		}

		if (password.isEmpty()) {
			password = testData.getPassword();
		}
	}

	/*
	 * @Deprecated public JalapenoHomePage createPatient(WebDriver driver, PropertyFileLoader testData) throws InterruptedException { IHGUtil.PrintMethodName();
	 * int randomize = IHGUtil.createRandomNumber();
	 * 
	 * //Setting data according to test purpose if(email.isEmpty()) { email = IHGUtil.createRandomEmailAddress(testData.getEmail()); }
	 * 
	 * if(firstName.isEmpty()) { firstName = testData.getFirstName() + randomize; }
	 * 
	 * if(lastName.isEmpty()) { lastName = testData.getLastName() + randomize; }
	 * 
	 * if(password.isEmpty()) { password = testData.getPassword(); }
	 * 
	 * 
	 * JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl()); // catching webdriver exception which started to show up after
	 * selenium 2.45 and firefox 36 updates // try removing the try catch once newrelic is deprecated and fully removed JalapenoCreateAccountPage
	 * jalapenoCreateAccountPage; try { jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton(); } catch (org.openqa.selenium.WebDriverException e){
	 * jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton(); }
	 * 
	 * assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());
	 * 
	 * JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 = jalapenoCreateAccountPage.fillInDataPage1(firstName, lastName, email, testData.getDOBMonth(),
	 * testData.getDOBDay(), testData.getDOBYear(), true, testData.getZipCode());
	 * 
	 * assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements()); jalapenoCreateAccountPage2.fillInDataPage2(null, password,
	 * testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());
	 * 
	 * return PageFactory.initElements(driver, JalapenoHomePage.class); }
	 */
}
