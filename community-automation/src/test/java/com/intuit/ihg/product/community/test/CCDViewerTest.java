package com.intuit.ihg.product.community.test;

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
import com.intuit.ihg.product.community.page.CommunityHomePage;
import com.intuit.ihg.product.community.page.CommunityLoginPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageHealthInformationPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageDetailPage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessageIframeHandlePage;
import com.intuit.ihg.product.community.page.solutions.Messages.MessagePage;
import com.intuit.ihg.product.community.utils.CommunityUtils;
import com.intuit.ihg.product.community.utils.PracticeUrl;

public class CCDViewerTest extends BaseTestNGWebDriver{
	
	/**
	 * @Author:Jakub Calabek 
	 * @Date:3.6.2013
	 * @User Story ID in Rally : TA18722
	 * Signing in the Community Home page, searching for the secret message and checking CCDViewer status
	 */	
	
	@DataProvider(name = "CCDViewer")
	public static Iterator<Object[]> fileDataProviderLogin(ITestContext testContext) throws Exception {

	//Define hashmap
	LinkedHashMap<String, Class<?>> classMap = new LinkedHashMap<String, Class<?>>();
	classMap.put("TestObject", TestObject.class);
	classMap.put("Patient", Patient.class);
	classMap.put("PracticeUrl", PracticeUrl.class);
	
	//Filter is set on DEMO environment 
	Filter filterTestEnvironment = Filter.equalsIgnoreCase(TestObject.TEST_ENV,IHGUtil.getEnvironmentType().toString()); 
	
	//Fetch data from CommunityAcceptanceTestData.csv
	Iterator<Object[]> it = ExcelSheetUtil.getObjectsFromSpreadsheet(LoginTest.class,
			classMap, "CCDViewerTestData.csv", 0, null,filterTestEnvironment);
	
	return it; 
 }
	
	@Test(enabled = false, groups = {"CCDViewerTest"}, dataProvider = "CCDViewer" )
	public void testCCDViewer(TestObject test, Patient patient, PracticeUrl practiceUrl) throws Exception {
		CommunityLoginPage communityloginpage = new CommunityLoginPage(driver);
		
		//Printing Test Method and Test Title
		log(test.getTestMethod());
		log(test.getTestTitle());
		
		//Navigate to login page
		CommunityUtils communityUtils = new CommunityUtils();
		driver.get(communityUtils.AssembleUrlForEnv(practiceUrl.getPractice(), test.getTestEnv()));
		
		//Waiting up to 30 seconds for page to load
		waitForPageTitle("Intuit Health",30);
		
		//Logging into the portal with specified credentials
		log("Loging on with User ID: "+patient.getUserName()+" and Password: "+patient.getPassword());
		
		//Filling up the credentials and clicking on Login
		communityloginpage.LoginToCommunity(patient.getUserName(),patient.getPassword()); 
		CommunityHomePage communityHomePage = new CommunityHomePage(driver);
		
		//Moving to the Messages Page
		communityHomePage.icon_Messages.click();
		
		MessagePage messagePage = new MessagePage(driver);
		//Searching for message with Subject New Health Information Import
		messagePage.clickMessage("New Health Information Import");
		
		//Clicking on Review Health Information
		MessageDetailPage messageDetail = new MessageDetailPage(driver);
		messageDetail.clickReviewHealthInformation();
		
		//Switching to Iframe and Waiting for Inner frame to load
		MessageIframeHandlePage messageIframeHandlePage = new MessageIframeHandlePage(driver);
		messageIframeHandlePage.handleIframe();
		
		MessageHealthInformationPage healthInformationPage = new MessageHealthInformationPage(driver);
		
		//Searching for elements on the CCDViewer site
		assertTrue(healthInformationPage.areElementsLocated(), "CCD message was not displayed properly");
		
		//Switching back to defaul content
		driver.switchTo().defaultContent();
		
		//Signing of the portal 
		communityHomePage.btn_Sign_Out.click(); 

		assertTrue(communityloginpage.btn_Sign_In.isDisplayed(), "Sign In button is not displayed, Login page was not reached");
	}
}

