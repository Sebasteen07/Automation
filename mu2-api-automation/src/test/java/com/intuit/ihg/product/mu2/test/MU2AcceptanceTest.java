package com.intuit.ihg.product.mu2.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.intuit.ihg.product.mu2.utils.APIData;
import com.intuit.ihg.product.mu2.utils.APITestData;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.intuit.ihg.product.mu2.utils.UtilityFunctions;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessageCenterInboxPage;
import com.intuit.ihg.product.object.maps.portal.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.AccountActivity.ViewAccountActivityPage;

public class MU2AcceptanceTest extends BaseTestNGWebDriver { 

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMU2PullAPI() throws Exception {
		log("Test Case: MU2 Pull API and VDT - Account Activity verification");
	
		log("Test case Environment "+IHGUtil.getEnvironmentType());
		log("Execution Browser: " +TestConfig.getBrowserType());
		
		long timestamp=System.currentTimeMillis();
		log("TIME STAMP for MU2 Pull API SinceTime"+Long.toString(timestamp));
		
		String viewEventTime = null,downloadEventTime = null,transmitEventTime = null;
		APITestData apitestData = new APITestData();
		APIData testData = new APIData(apitestData);
		
		
		//Generate and Post CCD
		//String ccd=RestUtils.prepareCCD(testData.getCCDPath());
		//System.out.println("Genrated XML:"+ccd);
		
		//log("step 1: Setup Oauth client"); 
		//RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
		
		//log("step 1:step Do Message Post Request");
		//RestUtils.setupHttpPostRequest(testData.getRestUrl(), ccd, testData.getResponsePath());
		
		// Generate CCD-VDT events through patient portal
		log("step 1:LogIn");
		log("Practice URL"+testData.getPortalURL());
		PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage pMyPatientPage = loginpage.login(testData.getPortalUserName(), testData.getPortalPassword());
		
		log("step 2: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 3: Find message in Inbox");
		MessagePage pMessageCenterInboxPage = inboxPage.openMessageInInbox(IntegrationConstants.CCD_MESSAGE_SUBJECT);

		log("step 4: Validate message subject");
		Thread.sleep(1000);
		assertEquals(pMessageCenterInboxPage.getPracticeReplyMessageTitle(),
				IntegrationConstants.CCD_MESSAGE_SUBJECT,
		"### Assertion failed for Message subject");

		log("step 5: Click on link ReviewHealthInformation");
		pMessageCenterInboxPage.clickBtnReviewHealthInformation();
		
		log("step 6: Click on PDF download Link");
		pMessageCenterInboxPage.clickOnPDF();
		
		log("step 7: Click on Send Information link");
		String Email=testData.getEmail();
		pMessageCenterInboxPage.generateTransmitEvent(Email);
		
		log("step 8: Close the CCD");
		pMessageCenterInboxPage.verifyCCDViewerAndClose();
		
		log("step 9: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
		//Wait according to environment
		log("step 10: Waiting for Events sync in DWH");
		if((IHGUtil.getEnvironmentType().toString().equals("DEV3")) || IHGUtil.getEnvironmentType().toString().equals("PROD")){
			Thread.sleep(900000);
		}
		else {
			Thread.sleep(600000*3);
		}		
						
		// Setup oauth Client
		log("step 11: Setup Oauth client"); 
		RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
		
		
		// Build new Rest URL with epoch milliseconds 
		log("Original PULL API URl:"+testData.getPullAPIURL());
		  
		String restPullUrl=new StringBuilder(testData.getPullAPIURL()).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
		log("Updated PULL API URL"+restPullUrl);
		
		log("Step 12: Send Pull API HTTP GET Request");
		RestUtils.setupHttpGetRequest(restPullUrl, testData.getResponsePath());
		
		String intuitPatientID=testData.getIntuitPatientID();
		log("practicePatientID:"+intuitPatientID);

		// verify "View" event in response XML and return Action Timestamp
		log("Step 13: Assert for 'View' event present in Pull API response xml");	
		String ActionTimestamp1= UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.VIEW_ACTION, timestamp,intuitPatientID);
		Assert.assertNotNull(ActionTimestamp1, "'View' Event is not found in Response XML");
		viewEventTime=UtilityFunctions.generateDate(ActionTimestamp1);
		log("View event portal Time"+viewEventTime);
		
		// verify "Download" event in response XML and return Action Timestamp
		log("Step 14: Assert for 'Download' event present in Pull API response xml");
		String ActionTimestamp2=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.DOWNLOAD_ACTION,timestamp,intuitPatientID);
		Assert.assertNotNull(ActionTimestamp2, "'Download' Event is not found in Response XML");
		downloadEventTime=UtilityFunctions.generateDate(ActionTimestamp2);
		log("Download event portal Time"+downloadEventTime);
		
		// verify "Transmit" event in response XML and return Action Timestamp
		log("Step 15: Assert for 'Transmit' event present in Pull API response xml");
		String ActionTimestamp3=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID);
		Assert.assertNotNull(ActionTimestamp3, "'Transmit' Event is not found in Response XML");
		transmitEventTime=UtilityFunctions.generateDate(ActionTimestamp3);
		log("Transmit event portal Time"+transmitEventTime);
		
		
		//again Login to patient portal and Verify  event in Account activity
		log("step 16:LogIn");
		PortalLoginPage ploginpage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage myPatientPage = ploginpage.login(testData.getPortalUserName(), testData.getPortalPassword());

		log("step 17:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = myPatientPage.clickMyAccountLink();

		ViewAccountActivityPage viewAccountActivity = pMyAccountPage.addAccountActivityLink();
		viewAccountActivity.clickOnViewAccountActivity();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Activity Log']")));
				
		List<Object> viewList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_VIEWED,viewEventTime)));
		if(!viewList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) viewList.get(1)).booleanValue());
			
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Viewed event not present");	
		}
				
		List<Object> downloadList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_DOWNLOADED,downloadEventTime)));
		if(!downloadList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) downloadList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Downloaded event not present");	
		}
		
		List<Object> transmitList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_TRANSMITTED,transmitEventTime)));
		if(!transmitList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) transmitList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Transmitted event not present");	
		}
	
			 WebElement closeViewer = driver.findElement(By.linkText("Close Viewer"));
			 closeViewer.click();
	}
	
	
	//@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
    public void testMU2PushAPI() throws Exception{
		log("Test Case: MU2 Push API");
		
		log("Test case Environment "+IHGUtil.getEnvironmentType());
		
		APITestData apitestData = new APITestData();
		APIData testData = new APIData(apitestData);
		UtilityFunctions.emptyFile(testData.getPushResponsePath());
	
		
		
		log(" Step 1: Send Push API HTTP GET Request");
    	UtilityFunctions.setupHttpGetRequest(UtilityFunctions.getURL( MU2Constants.PROTOCOL,testData.getPushHost(), MU2Constants.PORT,testData.getPushAPIURL(),testData.getPushSubjectID()),testData.getPushResponsePath());
    	    		
    	log("Step 2: Assert for 'View' event present in Push API response xml for Patient1");		
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.VIEW_ACTION),"Push API event 'View' is not present for Patient1");
    	
    	log("Step 3: Assert for 'Download' event present in Push API response xml for Patient1");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.DOWNLOAD_ACTION),"Push API event 'Download' is not present for Patient1");
    	
    	log("Step 4: Assert for 'Transmit' event present in Push API response xml for Patient1");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.TRANSMIT_ACTION),"Push API event 'Transmit' is not present for Patient1");
    		
    	log("Step 5: Assert for 'View' event present in Push API response xml for Patient2");		
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient2FN(),testData.getPatient2LN(),MU2Constants.VIEW_ACTION),"Push API event 'View' is not present for Patient2");
    		
    	log("Step 6: Assert for 'Download' event present in Push API response xml for Patient2");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient2FN(),testData.getPatient2LN(),MU2Constants.DOWNLOAD_ACTION),"Push API event 'Download' is not present for Patient2");
    	
    	log("Step 7: Assert for 'Transmit' event present in Push API response xml for Patient2");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient2FN(),testData.getPatient2LN(),MU2Constants.TRANSMIT_ACTION),"Push API event 'Transmit' is not present for Patient2");
    	
    	//Uncomment below code once DE7020 fixed
    	 /*
    	log("Step 8: Compare actual Push API response with sample expected xml and ensure both are same ");
       	assertTrue(XMLHandler.xmlComparison(testData.getPushResponsePath(),testData.getPushExpectedXMLPath()),"Push API response xml and expected xml are not same");
       	      	
       	
       log("Get latest SubjectID from Tracking DB and create new Push API URL and assert for VDT events in UserActivityReport");
        TrackingDBHandler.establishConnection(testData.getDBHost(),testData.getDBName(),testData.getDBUser(),testData.getDBPassword(),testData.getDBSID());
       	
        log(" Step 1A: Get latest subjectID from DB");
       	String subjectID = TrackingDBHandler.getLatetSubjectIDFromDB(MU2Constants.PUSH_API_MSG_TYPE,MU2Constants.PUSH_API_PROCESSING_STATUS_TYPE,testData.getPushPracticeID());//(MU2Constants.PUSH_API_MSG_TYPE,MU2Constants.PUSH_API_PRACTICEID);//testData.getPushPracticeID());
    	
       	log("Closing DB connection");
       	TrackingDBHandler.closeConnection();  
       
    	log(" Step 2A: Send Push API HTTP GET Request for the latest SubjectID");
    	UtilityFunctions.setupHttpGetRequest(UtilityFunctions.getURL( MU2Constants.PROTOCOL,testData.getPushHost(), MU2Constants.PORT,testData.getPushAPIURL(),subjectID),testData.getPushResponsePath());
    	 	
    
    	log("Step 3A: Assert for 'View' event present in Push API response xml for Patient1");	
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.VIEW_ACTION),"Push API event 'View' is not present for Patient1");
    	
    	log("Step 4A: Assert for 'Download' event present in Push API response xml for Patient1");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.DOWNLOAD_ACTION),"Push API event 'Download' is not present for Patient1");
    	
    	log("Step 5A: Assert for 'Transmit' event present in Push API response xml for Patient1");
    	assertTrue(UtilityFunctions.findPushEventsInResonseXML(testData.getPushResponsePath(), testData.getPatient1FN(),testData.getPatient1LN(),MU2Constants.TRANSMIT_ACTION),"Push API event 'Transmit' is not present for Patient1");
    */
         	
    }
	
}
         	
    
	
	

	
	

	

