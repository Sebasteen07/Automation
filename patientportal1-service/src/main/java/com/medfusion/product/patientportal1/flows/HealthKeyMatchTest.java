package com.medfusion.product.patientportal1.flows;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountExistingUserPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountHealthKeyPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreateAccountPage;
import com.medfusion.product.object.maps.patientportal1.page.createAccount.CreatePatientVerifyPhonePage;
import com.medfusion.product.patientportal1.utils.TestcasesData;

public class HealthKeyMatchTest extends BaseTestNGWebDriver {

	private String url="";
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String str) {
		url = str;
	}


	public MyPatientPage healthKey66SamePracticeMatch(WebDriver driver,TestcasesData testcasesData) throws Exception {

		log("Test Case: testCreatePatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		log("step 2: Click Create An Account");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3: Fill detials in Create Account Page");	
		log("email:-" + testcasesData.getEmail());
		CreateAccountExistingUserPage pCreateAccountExistingUserPage = pCreateAccountPage.tryCreateExistingUser(testcasesData.getFirstName(), 
																			testcasesData.getLastName(), testcasesData.getZip(), testcasesData.getSSN(), 
																			testcasesData.getEmail(), testcasesData.getDob_Month(), testcasesData.getDob_Day(),
																			testcasesData.getDob_Year());
		
		assertTrue(pCreateAccountExistingUserPage.existingPageVerify(driver));
		
		log("step 4: Log in with existing account");	
		MyPatientPage pMyPatientPage = pCreateAccountExistingUserPage.logIn(testcasesData.getEmail(), testcasesData.getPassword());
		
		log("step 5: Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6: Logout");
		pMyPatientPage.clickLogout(driver);
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	public MyPatientPage healthKey66DifferentPracticeMatch(WebDriver driver,TestcasesData testcasesData, String email, String firstName, String lastName) throws Exception {

		log("Test Case: testCreatePatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		log("step 2: Click Create An Account");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3: Fill detials in Create Account Page");
		log("email:-" + email);
		CreateAccountHealthKeyPage pCreateAccountHelthKeyPage = pCreateAccountPage.tryCreateExistingUserDiffPrac(firstName, 
				lastName, testcasesData.getZip(), testcasesData.getSSN(), 
				email, testcasesData.getDob_Month(), testcasesData.getDob_Day(),
				testcasesData.getDob_Year());
		
		assertTrue(pCreateAccountHelthKeyPage.existingPageVerify(driver));
		
		log("step 4: Log in with existing account");	
		MyPatientPage pMyPatientPage = pCreateAccountHelthKeyPage.logIn(email, testcasesData.getPassword());
		
		log("step 5: Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6: Logout");
		pMyPatientPage.clickLogout(driver);
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
		
	
	public MyPatientPage healthKey56SamePracticeMatch(WebDriver driver,TestcasesData testcasesData) throws Exception {
		
		log("Test Case: testCreatePatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		log("step 2: Click Create An Account");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3: Fill detials in Create Account Page");	
		log("email:-" + testcasesData.getEmail());
		CreateAccountExistingUserPage pCreateAccountExistingUserPage = pCreateAccountPage.tryCreateExistingUser(testcasesData.getFirstName(), 
																			"tester", testcasesData.getZip(), testcasesData.getSSN(), 
																			testcasesData.getEmail(), testcasesData.getDob_Month(), testcasesData.getDob_Day(),
																			testcasesData.getDob_Year());
		
		log("step 4: Verify the patient by phone");	
		CreatePatientVerifyPhonePage pCreatePatientVerifyPhonePage = pCreateAccountExistingUserPage.clickVerifyPhone(driver);
		pCreatePatientVerifyPhonePage.fillPhone(driver, testcasesData.getPhoneNumber());
		
		assertTrue(pCreateAccountExistingUserPage.existingPageVerify(driver));
		
		log("step 5: Log in with existing account");	
		MyPatientPage pMyPatientPage = pCreateAccountExistingUserPage.logIn(testcasesData.getEmail(), testcasesData.getPassword());
		
		log("step 6: Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 7: Logout");
		pMyPatientPage.clickLogout(driver);
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	public MyPatientPage healthKey56DifferentPracticeMatch(WebDriver driver,TestcasesData testcasesData, String email, String firstName, String lastName) throws Exception {

		log("Test Case: testCreatePatient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		log("step 2: Click Create An Account");
		PortalLoginPage loginpage = new PortalLoginPage(driver, url);
		CreateAccountPage pCreateAccountPage = loginpage.signUp();

		log("step 3: Fill detials in Create Account Page");
		log("email:-" + email);
		CreateAccountHealthKeyPage pCreateAccountHelthKeyPage = pCreateAccountPage.tryCreateExistingUserDiffPrac(firstName, 
				lastName, testcasesData.getZip(), testcasesData.getSSN(), 
				email, testcasesData.getDob_Month(), testcasesData.getDob_Day(),
				testcasesData.getDob_Year());
		
		log("step 4: Verify the patient by phone");	
		CreatePatientVerifyPhonePage pCreatePatientVerifyPhonePage = pCreateAccountHelthKeyPage.clickVerifyPhone(driver);
		pCreatePatientVerifyPhonePage.fillPhone(driver, testcasesData.getPhoneNumber());
		
		assertTrue(pCreateAccountHelthKeyPage.existingPageVerify(driver));
		
		log("step 4: Log in with existing account");	
		MyPatientPage pMyPatientPage = pCreateAccountHelthKeyPage.logIn(email, testcasesData.getPassword());
		
		log("step 5: Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6: Logout");
		pMyPatientPage.clickLogout(driver);
		
		return PageFactory.initElements(driver, MyPatientPage.class);
	}
}
