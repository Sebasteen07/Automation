package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.createAccount.BetaSiteCreateAccountPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class CreatePatientTest extends BaseTestNGWebDriver {
	
	private String email="";
	private String password="";
	
	//Getters for getting the email and password value and reusing in other tests
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}


	public void CreatePatient(WebDriver driver,TestcasesData testcasesData) throws Exception {

		log("Test Case: testCreatePatientOnBetaSite");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());
		log("URL: " + testcasesData.geturl());

		log("step 2:Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		BetaSiteCreateAccountPage pBetaSiteCreateAccountPage = loginpage.signUpIntoBetaSite();

		log("step 3:Fill detials in Create Account Page");
		//Setting the variables for user in other tests
		email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
		password=testcasesData.getPassword();
		log("email:-" + email);
		MyPatientPage pMyPatientPage = pBetaSiteCreateAccountPage.BetaSiteCreateAccountPage(testcasesData.getFirstName(),
						testcasesData.getLastName(), email, testcasesData.getPhoneNumber(),
						testcasesData.getZip(), testcasesData.getSSN(),
						testcasesData.getAddress(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
						testcasesData.getAnswer(), testcasesData.getAddressState(), testcasesData.getAddressCity());

		log("step 5:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6:Logout");
		pMyPatientPage.clickLogout(driver);

		// need to include a piece of code here for deleting cookies so
		// that
		// script works in IE works
		/*
		 * log("Clearing Browser cache");
		 * PhrUtil.DeleteAllBrowsingDataIE(driver);
		 */

		log("step 7:Login as new user");
		loginpage.navigateTo(driver, testcasesData.geturl());
		pMyPatientPage = loginpage.login(email, testcasesData.getPassword());

		log("step 8:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

	}

}
