package com.medfusion.product.jalapeno;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.PropertyFileLoader;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.jalapeno.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage;
import com.medfusion.product.object.maps.jalapeno.page.CreateAccount.JalapenoCreateAccountPage2;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoCreatePatientTest extends BasePageObject {

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

	public JalapenoCreatePatientTest(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public JalapenoHomePage createPatient(WebDriver driver, PropertyFileLoader testData) throws InterruptedException {
		IHGUtil.PrintMethodName();
		int randomize = PortalUtil.createRandomNumber();
		email = testData.getEmail() + randomize;
		password = "";
		firstName = testData.getFirstName() + randomize;
		lastName = testData.getLastName() + randomize;

		JalapenoLoginPage jalapenoLoginPage = new JalapenoLoginPage(driver, testData.getUrl());
		JalapenoCreateAccountPage jalapenoCreateAccountPage = jalapenoLoginPage.clickSignInButton();

		JalapenoCreateAccountPage2 jalapenoCreateAccountPage2 =  jalapenoCreateAccountPage.fillInDataPage1(firstName, lastName, email,
				testData.getDOBMonth(), testData.getDOBDay(), testData.getDOBYear(), true, testData.getZipCode());	
		jalapenoCreateAccountPage2.fillInDataPage2(email, testData.getPassword(), testData.getSecretQuestion(), testData.getSecretAnswer(), testData.getphoneNumer());

		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
}