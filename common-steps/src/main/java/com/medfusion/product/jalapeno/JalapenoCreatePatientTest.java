package com.medfusion.product.jalapeno;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage2;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

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

	public JalapenoHomePage createPatient(WebDriver driver, PropertyFileLoader testData) throws InterruptedException {
		IHGUtil.PrintMethodName();
		int randomize = PortalUtil.createRandomNumber();
		
		//Setting data according to test purpose
		if(email.isEmpty()) {
			email = IHGUtil.createRandomEmailAddress(testData.getEmail());
		}
		
		if(firstName.isEmpty()) {
			firstName = testData.getFirstName() + randomize;
		}
		
		if(lastName.isEmpty()) {
			lastName = testData.getLastName() + randomize;
		}
		
		if(password.isEmpty()) {
			password = testData.getPassword();
		}


		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();
		
		assertTrue(jalapenoCreateAccountPage.assessCreateAccountPageElements());

		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 =  jalapenoCreateAccountPage.fillInDataPage1(firstName, lastName, email,
				testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear(), true, testData.getZipCode());	
		
		assertTrue(jalapenoCreateAccountPage2.assessCreateAccountPage2Elements());
		jalapenoCreateAccountPage2.fillInDataPage2(null, password, testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
}