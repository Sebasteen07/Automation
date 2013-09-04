package com.intuit.ihg.product.community.test;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.entities.Patient;
import com.intuit.ihg.common.entities.TestObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.dataprovider.ExcelSheetUtil;
import com.intuit.ihg.common.utils.dataprovider.Filter;
import com.intuit.ihg.common.utils.mail.CheckEmail;
import com.intuit.ihg.common.utils.mail.Gmail;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.ForgotUserId.ForgotUserIdClickForgottenUserIdPage;
import com.intuit.ihg.product.community.page.ForgotUserId.ForgotUserIdEnterEmailAddressPage;
import com.intuit.ihg.product.community.page.ForgotUserId.ForgotUserIdAnswerQuestionPage;
import com.intuit.ihg.product.community.utils.CheckMailCommunity;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.GmailCommunity;
import com.intuit.ihg.product.community.utils.PracticeUrl;

	/**
	 * @Author:Jakub Calabek 
	 * @Date:6.5.2013
	 * @User Story ID in Rally : TA18007
	 * @StepsToReproduce:
	 * Forgot User ID procedure from the Community Login page. Specifying User email account and Secure question to obtain User ID.
	 */	

public class ForgotUserIdTest extends BaseTestNGWebDriver {	
	
	@DataProvider(name = "ForgotUserId")
	public static Iterator<Object[]> fileDataProvider3(ITestContext testContext) throws Exception {

	//Define hashmap
	LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
	classMap.put("TestObject", TestObject.class);
	classMap.put("Patient", Patient.class);
	classMap.put("PracticeUrl", PracticeUrl.class);
	
	
	//Filter is set on DEMO environment 
	Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV,IHGUtil.getEnvironmentType().toString()); 
	
	//Fetch data from CommunityAcceptanceTestData.csv
	Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(ForgotUserIdTest.class,
			classMap, "ForgotUserIdTestData.csv", 0, null,filterTestEnvironment);
	
	return it; 
 }		
	
	@Test(enabled = false, groups = {"AcceptanceTests"}, dataProvider = "ForgotUserId" )
	public void testForgotUserId(TestObject test, Patient patient, PracticeUrl practiceUrl) throws Exception {

		//Printing Test Method and Test Title
		log(test.getTestMethod());
		log(test.getTestTitle());
		
		//Start email search date
		
		log("Logging to Gmail with credentials: "+patient.getGmailUName()+" "+patient.getGmailPassword());
		Date startEmailSearchDate1 = new Date();
		GmailCommunity gmail1 = new GmailCommunity(patient.getGmailUName(),patient.getGmailPassword());
		
		//Community Login Page and Click on Cant access your account
		log("Clicking on the Cant access your account from the Login Page");
		CommunityLoginPage communityloginpage = new CommunityLoginPage(driver);
		CommunityUtils communityUtils = new CommunityUtils();
		
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		waitForPageTitle("Intuit Health",30);
		communityloginpage.link_Cant_Acces_Your_Account.click();
		
		
		ForgotUserIdClickForgottenUserIdPage newUserIdStep1 = new ForgotUserIdClickForgottenUserIdPage(driver);
		waitForPageTitle("Intuit Health",30);
		
		//Click on: Have you forgotten your User ID?
		log("Clicking on Forgotten User ID link");	
		newUserIdStep1.link_forgottenUserId.click();
		
		waitForPageTitle("Intuit Health",30);
		
		//Insert Email Address and click on Continue
		ForgotUserIdEnterEmailAddressPage newUserIdStep2 = new ForgotUserIdEnterEmailAddressPage(driver);
		log("Setting User email "+patient.getGmailUName());
		newUserIdStep2.EmailAddress.sendKeys(patient.getGmailUName());
		
		log("Clicking on Continue");
		newUserIdStep2.btn_Continue.click();
		
		waitForPageTitle("Intuit Health",30);
		
		ForgotUserIdAnswerQuestionPage newUserIdStep3 = new ForgotUserIdAnswerQuestionPage(driver);
		//Security Question, insert Answer and click on Email Me
		log("Secret Question "+patient.getSecAnswer());
		log("Clicking Continue");
		newUserIdStep3.Answer.sendKeys(patient.getSecAnswer());
		newUserIdStep3.btn_EmailMe.click();
		
		boolean foundEmail1 = CheckMailCommunity.validateForgotPasswordTrash(gmail1, startEmailSearchDate1, patient.getGmailUName(), "Your User ID", "");		
		verifyTrue(foundEmail1, "The Forgot User ID email wasn't received.");

	}
	
}
