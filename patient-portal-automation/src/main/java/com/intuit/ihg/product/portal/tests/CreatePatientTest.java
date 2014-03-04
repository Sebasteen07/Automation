package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.createAccount.CreateAccountPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class CreatePatientTest extends BaseTestNGWebDriver {
	
	private String email="";
	private String password="";
	private String url="";
	
	//Getters for getting the email and password value and reusing in other tests
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


	public MyPatientPage createPatient(WebDriver driver,TestcasesData testcasesData) throws Exception {

		log("Test Case: testCreatePatientOnBetaSite");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if (url.isEmpty()) {
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");

		log("URL: " + url);
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 2:Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3:Fill detials in Create Account Page");
		
		//Setting the variables for user in other tests
		email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
		password=testcasesData.getPassword();
		log("email:-" + email);
		MyPatientPage pMyPatientPage = pCreateAccountPage.createAccountPage(testcasesData.getFirstName(),
						testcasesData.getLastName(), email, testcasesData.getPhoneNumber(),
						testcasesData.getZip(), testcasesData.getSSN(),
						testcasesData.getAddress(), testcasesData.getPassword(), testcasesData.getSecretQuestion(),
						testcasesData.getAnswer(), testcasesData.getAddressState(), testcasesData.getAddressCity());

		log("step 5:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6:Logout");
		pMyPatientPage.clickLogout(driver);

		log("step 7:Login as new user");
		loginpage.navigateTo(driver, url);
		pMyPatientPage = loginpage.login(email, testcasesData.getPassword());

		log("step 8:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}

}
