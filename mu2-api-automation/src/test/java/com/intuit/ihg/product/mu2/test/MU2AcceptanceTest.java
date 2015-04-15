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
	public void testMU2PullAPI1() throws Exception {
		log("Test Case (MU2PullAPI1): Consolidated CCD related events verification in Pull Events");
	
		log("Test case Environment: "+IHGUtil.getEnvironmentType());
		log("Execution Browser: " +TestConfig.getBrowserType());
		
		long timestamp=System.currentTimeMillis();
		log("TIME STAMP for MU2 Pull API SinceTime: "+Long.toString(timestamp));
		
		String eventTime = null;
		String ActionTimestamp = null;
		List<String> portalTime=new ArrayList<String>();
		APITestData apitestData = new APITestData();
		APIData testData = new APIData(apitestData);
		
		log("Step 1: LogIn");
		log("Practice URL: "+testData.getPortalURL());
		PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage pMyPatientPage = loginpage.login(testData.getPortalUserName(), testData.getPortalPassword());
		
		log("Step 2: Go to Inbox");
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("Step 3: Find message in Inbox");
		MessagePage pMessageCenterInboxPage = inboxPage.openMessageInInbox(IntegrationConstants.CCD_MESSAGE_SUBJECT);

		log("Step 4: Validate message subject");
		Thread.sleep(1000);
		assertTrue(pMessageCenterInboxPage.isSubjectLocated("New Health Information Import"));
		
		log("====== Consolidated CCD related events generation Started ======");

		log("Step 5: Click on link ReviewHealthInformation");
		pMessageCenterInboxPage.clickBtnReviewHealthInformation();
		
		log("Step 6: Click on PDF download Link");
		pMessageCenterInboxPage.clickOnPDF();
		
		log("Step 7: Click on Send Information link");
		String Email=testData.getEmail();
		pMessageCenterInboxPage.generateTransmitEvent(Email);
		
		log("Step 8: Close the CCD");
		pMessageCenterInboxPage.verifyCCDViewerAndClose();
		
		log("====== Consolidated CCD - VDT events generated successfully ======");
		
		log("Step 9: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
		log("Step 10: Waiting for Events sync in DWH");
		Thread.sleep(420000);	
		
		log("Step 11: Setup Oauth client 2.O"); 
		RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
				
		// Build new Rest URL with epoch milliseconds 
		log("Original PULL API URl: "+testData.getPullAPIURL());
		  
		String restPullUrl=new StringBuilder(testData.getPullAPIURL()).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
		log("Updated PULL API URL: "+restPullUrl);
		
		log("Step 12: Send Pull API HTTP GET Request");
		RestUtils.setupHttpGetRequest(restPullUrl, testData.getResponsePath());
		
		String intuitPatientID1=testData.getIntuitPatientID();
		log("First practicePatientID: "+intuitPatientID1);
		
		List<String> list=UtilityFunctions.eventList();
		for(int i=0;i<list.size();i++)
		{
		// verify "View" event in response XML and return Action Timestamp
		log("Verification of CCD '"+list.get(i)+"' event present in Pull API response xml");	
		ActionTimestamp= UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,list.get(i), timestamp,intuitPatientID1);
		Assert.assertNotNull(ActionTimestamp, "'"+list.get(i)+"' Event is not found in Response XML");
		eventTime=UtilityFunctions.generateDate(ActionTimestamp);
		portalTime.add(eventTime);
		log("CCD '"+list.get(i)+"' event portal Time: "+eventTime);
		}
		
        log("Verification of event in Account activity");
		
		log("Step 13: LogIn");
		PortalLoginPage ploginpage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage myPatientPage = ploginpage.login(testData.getPortalUserName(), testData.getPortalPassword());

		log("Step 14: Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = myPatientPage.clickMyAccountLink();

		ViewAccountActivityPage viewAccountActivity = pMyAccountPage.addAccountActivityLink();
		viewAccountActivity.clickOnViewAccountActivity();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@title='Activity Log']")));
				
		List<Object> viewList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_VIEWED,portalTime.get(0))));
		if(!viewList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) viewList.get(1)).booleanValue());
			
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Viewed event not present");	
		}
				
		List<Object> downloadList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_DOWNLOADED,portalTime.get(1))));
		if(!downloadList.isEmpty())
		{
			BaseTestSoftAssert.assertTrue(((Boolean) downloadList.get(1)).booleanValue());
		}
		else
		{
			BaseTestSoftAssert.assertTrue(false,"Health Information Downloaded event not present");	
		}
		
		List<Object> transmitList=IHGUtil.searchResultsSubstring(driver,"//table[@id='activityTable']/tbody",new ArrayList<String>(Arrays.asList(MU2Constants.ACCOUNT_ACTIVITY_TRANSMITTED,portalTime.get(2))));
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
			 
		log("Step 15: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
	}
}
         	
    
	
	

	
	

	

