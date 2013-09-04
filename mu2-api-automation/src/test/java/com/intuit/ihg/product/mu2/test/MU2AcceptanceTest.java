package com.intuit.ihg.product.mu2.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mu2.utils.APIData;
import com.intuit.ihg.product.mu2.utils.APITestData;
import com.intuit.ihg.product.mu2.utils.MU2Constants;
import com.intuit.ihg.product.mu2.utils.UtilityFunctions;

import com.intuit.ihg.product.mu2.utils.TrackingDBHandler;

import com.intuit.ihg.product.mu2.utils.XMLHandler;

public class MU2AcceptanceTest extends BaseTestNGWebDriver { 
		
	@Override
	@BeforeMethod (alwaysRun = true)
	public void testSetup() throws Exception {
		
	}
	
	
	@Override
	@AfterMethod (alwaysRun = true)
	public void postTestCase(ITestResult _result) {
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMU2PullAPI() throws Exception {
		log("Test Case: MU2 Pull API");
	
		log("Test case Environment "+IHGUtil.getEnvironmentType());
		
		APITestData apitestData = new APITestData();
		APIData testData = new APIData(apitestData);
		
		log("Step 1: Setup Oauth client"); 
		UtilityFunctions.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(),testData.getResponsePath());
				
		log("Step 2: Send Pull API HTTP GET Request");
		UtilityFunctions.setupHttpGetRequest(UtilityFunctions.getURL(MU2Constants.PROTOCOL,testData.getHost(), MU2Constants.PORT, testData.getPullAPIURL()), testData.getResponsePath());
				
		log("Step 3: Assert for 'View' event present in Pull API response xml");	
		assertTrue(UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.VIEW_ACTION)," 'View' Event is not present in Pull API response ");
		
		log("Step 4: Assert for 'Download' event present in Pull API response xml");
		assertTrue(UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.DOWNLOAD_ACTION)," 'Download' Event is not present in Pull API response");
		
		log("Step 5: Assert for 'Transmit' event present in Pull API response xml");
		assertTrue(UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION)," 'Transmit' Event is not present in Pull API response");
	
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
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
         	
    
	
	

	
	

	

