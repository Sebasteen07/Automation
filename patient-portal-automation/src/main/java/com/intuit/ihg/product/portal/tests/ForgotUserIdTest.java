package com.intuit.ihg.product.portal.tests;

import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.mail.CheckEmail;
import com.intuit.ihg.common.utils.mail.Gmail;
import com.intuit.ihg.common.utils.monitoring.PerformanceReporter;
import com.intuit.ihg.product.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdConfirmationPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdEnterEmailPage;
import com.intuit.ihg.product.portal.page.forgotuserid.ForgotUserIdSecretAnswerPage;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class ForgotUserIdTest extends BaseTestNGWebDriver {
	
	private Boolean caseInsensitiveEmail = false;
	private String email = "";
	private String url = "";
	
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String str) {
		url = str;
	}
	
	public void setCaseInsensitiveEmail(Boolean value) {
		caseInsensitiveEmail = value;
	}


	public void forgotUserIdTest(WebDriver driver,TestcasesData testcasesData) throws Exception {

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		/* Not very elegant way of changing URL the method uses 
		 * If someone sets the URL in the object before calling this method then the set URL is be used
		 * otherwise (the default way) the URL from testcasesData.geturl() is used
		 */
		if ( url.isEmpty() ){
			url = testcasesData.geturl();
		}
		
		// If someone sets caseInsensitive to true, the test mix the cases of email
		if ( caseInsensitiveEmail == false ) {
			email = testcasesData.getEmail();
		} else {
			email = mixCase(testcasesData.getEmail());
		}
			
		log("step 1: Get Data from Excel");
		log("URL: " + url);

		// Some other data setup for email searching:
		Date startEmailSearchDate = new Date();
		Gmail gmail = new Gmail(testcasesData.getEmail(), testcasesData.getPassword());

		log("step 2: Navigate to login page");
		PortalLoginPage loginPage = new PortalLoginPage(driver, testcasesData.geturl());

		log("step 3: Click forgot user id link");
		ForgotUserIdEnterEmailPage step1 = loginPage.forgotUserId();
		assertTrue(step1.isPageLoaded(), "There was an error loading the first page in the Forgot UserId workflow.");
		PerformanceReporter.getPageLoadDuration(driver, ForgotUserIdEnterEmailPage.PAGE_NAME);

		log("step 4: Enter patient email");		
		ForgotUserIdSecretAnswerPage step2 = step1.enterEmail(email);

		log("Enter Patient's DOB");
		step2.selectDOB(testcasesData.getDob_Day(), testcasesData.getDob_Month(), testcasesData.getDob_Year());
		
		log("step 5: Answer patient security question");
		ForgotUserIdConfirmationPage step3 = step2.answerSecurityQuestion(testcasesData.getAnswer());
		assertTrue(step3.confirmationPageLoaded(), "There was an error loading the confirmation page in the Forgot UserId workflow");
		PerformanceReporter.getPageLoadDuration(driver, ForgotUserIdConfirmationPage.PAGE_NAME);

		log("step 6: Access Gmail and check for received email");
		int count = 1;
		boolean flag = false;
		do {
			boolean foundEmail = CheckEmail.validateForgotUserID(gmail, startEmailSearchDate, testcasesData.getEmail(), "Your User ID for",
					testcasesData.getUsername());
			if (foundEmail) {
				assertTrue(foundEmail, "The Forgot User ID email wasn't received.");
				System.out.println("The User ID email receiced In between :" + count * 60 + "seconds");
				flag = true;
				break;
			} else {
				Thread.sleep(60000);
				count++;
			}

		} while (count < 21);
		if (!flag) {
			log("The User ID email wasn't received even after Five minutes of wait");
		}
	}
	
	
	// This method takes inputString and mix cases of its chars
	// like "camel" to "CaMeL"
	public String mixCase( String inputStr ){
		StringBuilder outputStr = new StringBuilder();
		
		for ( int i = 0; i < inputStr.length(); i++ ){
			char mixCase =  (( i % 2 ) == 1 ) ? Character.toUpperCase(( inputStr.charAt( i ))) : Character.toLowerCase(( inputStr.charAt( i ))); 
			outputStr.append( mixCase );			
		}
		
		return outputStr.toString();
	}

}
