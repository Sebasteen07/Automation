package com.intuit.ihg.product.mu2.test;

import java.io.File;
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
import com.intuit.ihg.product.object.maps.portal.page.newRxRenewalpage.NewRxRenewalPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep3Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.apptRequest.AppointmentRequestStep4Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffHistoryPage;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep1Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep2Page;
import com.intuit.ihg.product.object.maps.portal.page.solutions.askstaff.AskAStaffStep3Page;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

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
		Thread.sleep(9000);
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
	

	 @Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMU2PullAPI2() throws Exception {
		 
		log("Test Case (MU2PullAPI2): Appointment/Rx/Ask A Staff/Secure Message events verification in Pull Events");
			
		log("Test case Environment: "+IHGUtil.getEnvironmentType());
		log("Execution Browser: " +TestConfig.getBrowserType());
			
		long timestamp=System.currentTimeMillis();
		log("TIME STAMP for MU2 Pull API SinceTime: "+Long.toString(timestamp));
			
		
		String ActionTimestamp = null;
		APITestData apitestData = new APITestData();
		APIData testData = new APIData(apitestData);
			
		log("Step 1: LogIn");
		log("Practice URL: "+testData.getPortalURL());
		PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getPortalURL());
		MyPatientPage pMyPatientPage = loginpage.login(testData.getPortalUserName(), testData.getPortalPassword());
		
		log("====== Appointment related events generation Started ======");
		
		log("Step 2: Click on Appointment Button on My Patient Page");
		AppointmentRequestStep1Page apptRequestStep1 = pMyPatientPage.clickAppointmentRequestTab();

		log("Step 3: Complete Appointment Request Step1 Page  ");
		AppointmentRequestStep2Page apptRequestStep2 = apptRequestStep1.requestAppointment(null,null,null,null);

		log("Step 4: Complete Appointment Request Step2 Page  ");
		AppointmentRequestStep3Page apptRequestStep3 = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame,
				PortalConstants.PreferredDay, PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason,
				PortalConstants.WhichIsMoreImportant, "1234567890");
	    
		log("Step 5: Complete Appointment Request Step3 Page  ");
		AppointmentRequestStep4Page apptRequestStep4 = apptRequestStep3.clickSubmit();

		log("Step 6: Complete Appointment Request Step4 Page  ");
		pMyPatientPage = apptRequestStep4.clickBackToMyPatientPage();
		
		log("====== Appointment request event generated successfully ======");
		
		log("====== Rx related events generation Started ======");

		log("Step 7: Click on PrescriptionRenewal Link ");
		NewRxRenewalPage newRxRenewalPage = pMyPatientPage.clickPrescriptionRenewal();
		
		log("Step 8: Set Medication Fields in RxRenewal Page");
		newRxRenewalPage.setMedicationDetails();

		log("Step 9: Set Pharmacy Fields in RxRenewal Page");
		newRxRenewalPage.setPharmacyFields();
		
		log("Step 10: Verify RxRenewal Confirmation Message");
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 5, newRxRenewalPage.renewalConfirmationmessage);
		verifyEquals(newRxRenewalPage.renewalConfirmationmessage.getText(), PortalConstants.RenewalConfirmation);
		
		log("====== Rx Renewal event generated successfully ======");
		
		pMyPatientPage = apptRequestStep4.clickBackToMyPatientPage();
		
		log("====== Ask a staff event generation Started ======");
		
		log("Step 11: Click Ask A Staff");
		AskAStaffStep1Page askStaff1 = pMyPatientPage.clickAskAStaffLink();

		log("Step 12: Complete Step 1 of Ask A Staff");
		AskAStaffStep2Page askStaff2 = askStaff1.askQuestion("Test", "This is generated from the AMDCAskQuestion automation test case.");

		log("Step 13: Complete Step 2 of Ask A Staff");
		AskAStaffStep3Page askStaff3 = askStaff2.submitUnpaidQuestion();

		log("Step 14: Validate entry is on Ask A Staff History page");
		AskAStaffHistoryPage aasHistory = askStaff3.clickAskAStaffHistory();
		verifyTrue(aasHistory.isAskAStaffOnHistoryPage(Long.toString(askStaff1.getCreatedTimeStamp())), "Expected to see a subject containing "
						+ askStaff1.getCreatedTimeStamp() + " on the Ask A Staff History page. None were found.");
		
		log("====== Ask a question event generated successfully ======");

		log("Step 15: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
		log("====== AMDC secure Message event generation Started ======");
		
		log("Step 16: Login with other patient");
		loginpage = new PortalLoginPage(driver, testData.getPortalURL());
		pMyPatientPage = loginpage.login(testData.getPortalUserName2(), testData.getPortalPassword());
		
		MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
		
		log("Step 17: Go to message inbox page");
		MessagePage msg = inboxPage.openMessageInInbox("secure message without attachment");

		Thread.sleep(9000);
		log("Step 18: Validate message loads and is the right message");
		assertTrue(msg.isSubjectLocated("secure message without attachment"));
		
		log("Step 19: Reply to the message");
		msg.replyToMessage("REply",null);
	
		log("====== AMDC secure Message event generated successfully ======");
		
		log("Step 20: Logout of Patient Portal");
		pMyPatientPage.logout(driver);
		
		log("Step 21: Waiting for Events sync in DWH");
		Thread.sleep(420000);		
						
		// Setup oauth Client
		log("Step 22: Setup Oauth client 2.O"); 
		RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
		
		
		// Build new Rest URL with epoch milliseconds 
		log("Original PULL API URl: "+testData.getPullAPIURL());
		  
		String restPullUrl=new StringBuilder(testData.getPullAPIURL()).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
		log("Updated PULL API URL: "+restPullUrl);
		
		log("Step 23: Send Pull API HTTP GET Request");
		RestUtils.setupHttpGetRequest(restPullUrl, testData.getResponsePath());
		
		String intuitPatientID1=testData.getIntuitPatientID();
		log("First practicePatientID: "+intuitPatientID1);
		
		String intuitPatientID2=testData.getPatientID();
		log("Second practicePatientID: "+intuitPatientID2);
		
		
		// verify 'Transmit' event of Secure Message (Ask a Staff) in response XML
		log("Verification of Ask a Staff 'Transmit' event present in Pull API response");
		ActionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID1);
		Assert.assertNotNull(ActionTimestamp, "'Transmit' event of Secure Message (Ask a Staff) is not found in Response XML");
		
		// verify "Transmit" event of Appointment Request in response XML
		log("Verification of Appointment Request 'Transmit' present event in Pull API response");
		ActionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.APPOINTMENT_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID1);
		Assert.assertNotNull(ActionTimestamp, "'Transmit' event of Appointment Request is not found in Response XML");
		
		// verify "Transmit" event of Rx Renewal Request in response XML
		log("Verification of Rx Request 'Transmit' event in Pull API response xml");
		ActionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.PRESCRIPTION_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID1);
		Assert.assertNotNull(ActionTimestamp, "'Transmit' present event of Rx Renewal Request is not found in Response XML");
			
		//again Login to patient portal and Verify  event in Account activity
		
		
			// verify "View" event of Secure Message Request in response XML
		 log("Verification of Secure Message without attachment 'View' event present in Pull API response");
		 ActionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.VIEW_ACTION,timestamp,intuitPatientID2);
		 Assert.assertNotNull(ActionTimestamp, "'View' event of Secure Message is not found in Response XML");

				// verify "Transmit" event of Secure Message Request in response XML
		 log("Verification of Secure Message without attachment 'Transmit' event present in Pull API response");
		 ActionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID2);
		 Assert.assertNotNull(ActionTimestamp, "'Transmit' event of Secure Message is not found in Response XML");
		
		  log("Step 24: Logout of Patient Portal");
		  pMyPatientPage.logout(driver);
			 
			 
	}		 
	  @Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	  public void testMU2PullAPI3() throws Exception {
		    log("Test Case (testMU2PullAPI3): Secure Message related events verification in Pull Events");
			
			log("Test case Environment: "+IHGUtil.getEnvironmentType());
			log("Execution Browser: " +TestConfig.getBrowserType());
			
			long timestamp=System.currentTimeMillis();
			String actionTimestamp=null;
			log("TIME STAMP for MU2 Pull API SinceTime: "+Long.toString(timestamp));
			
			APITestData apitestData = new APITestData();
			APIData testData = new APIData(apitestData);
			
			File file = new File(testData.getImagePath());
			String filepath=file.getAbsolutePath().toString();
			log("Upload File Path: " +filepath);
			
			log("Step 1: LogIn");
			log("Practice URL: "+testData.getPortalURL());
			PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getPortalURL());
			MyPatientPage pMyPatientPage = loginpage.login(testData.getPortalUserName(), testData.getPortalPassword());
			
			log("Step 2: Go to Inbox");
			MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
			assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");
			
			log("====== AMDC secure Message with attachment event generation Started ======");
			
			MessagePage msg = inboxPage.openMessageInInbox("secure message with attachment");
			
			Thread.sleep(9000);
			log("Step 3: Validate message loads and is the right message");
			assertTrue(msg.isSubjectLocated("secure message with attachment"));
			
			log("Step 4: Reply to the message");
			msg.replyToMessage("REply",filepath);
			
			log("====== AMDC secure Message with attachment event generated successfully ======");
			
			log("Step 5: Logout of Patient Portal");
			pMyPatientPage.logout(driver);
			
			log("Step 6: Waiting for Events sync in DWH");
			Thread.sleep(420000);

			log("Step 7: Setup Oauth client 2.O"); 
			RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
						
			// Build new Rest URL with epoch milliseconds 
			log("Original PULL API URl: "+testData.getPullAPIURL());
			  
			String restPullUrl=new StringBuilder(testData.getPullAPIURL()).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
			log("Updated PULL API URL: "+restPullUrl);
			
			log("Step 8: Send Pull API HTTP GET Request");
			RestUtils.setupHttpGetRequest(restPullUrl, testData.getResponsePath());
			
			String intuitPatientID=testData.getIntuitPatientID();
			log("practicePatientID: "+intuitPatientID);
			
			// verify "Transmit" event of Appointment Request in response XML
			log("Step 9: Verification of Secure Message with attachement 'View' event present in Pull API response");
			actionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.VIEW_ACTION,timestamp,intuitPatientID);
			Assert.assertNotNull(actionTimestamp, "'View' event of Secure Message (AMDC) is not found in Response XML");
			
			// verify "Transmit" event of Prescription Renewal Request in response XML
			log("Step 10: Verification of Secure Message with attachment 'Transmit' event present in Pull API response");
			actionTimestamp=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.TRANSMIT_RESOURCE_TYPE,MU2Constants.TRANSMIT_ACTION,timestamp,intuitPatientID);
			Assert.assertNotNull(actionTimestamp, "'Transmit' event of Secure Message (AMDC) is not found in Response XML");
			
			 
	}
	  
	  
	  @Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
		public void testMU2PullAPI4() throws Exception {
			log("Test Case (testMU2PullAPI4): Non Consolidated CCD related events verification in Pull Events");
		
			log("Test case Environment: "+IHGUtil.getEnvironmentType());
			log("Execution Browser: " +TestConfig.getBrowserType());
			
			long timestamp=System.currentTimeMillis();
			log("TIME STAMP for MU2 Pull API SinceTime: "+Long.toString(timestamp));
			
			String viewEventTime = null;
			String downloadEventTime = null;
			APITestData apitestData = new APITestData();
			APIData testData = new APIData(apitestData);
			
			log("Step 1: LogIn");
			log("Practice URL: "+testData.getPortalURL());
			PortalLoginPage loginpage = new PortalLoginPage(driver, testData.getPortalURL());
			MyPatientPage pMyPatientPage = loginpage.login(testData.getPortalUserName2(), testData.getPortalPassword());
			
			log("Step 2: Go to Inbox");
			MessageCenterInboxPage inboxPage = pMyPatientPage.clickViewAllMessagesInMessageCenter();
			assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

			log("Step 3: Find message in Inbox");
			MessagePage pMessageCenterInboxPage = inboxPage.openMessageInInbox(IntegrationConstants.CCD_MESSAGE_SUBJECT);

			log("Step 4: Validate message subject");
			Thread.sleep(9000);
			assertTrue(pMessageCenterInboxPage.isSubjectLocated("New Health Information Import"));
			
			log("Non Consolidated CCD related events generation Started ======");

			log("Step 5: Click on link ReviewHealthInformation");
			pMessageCenterInboxPage.clickBtnReviewHealthInformation();
			
			log("Step 6: Click on PDF download Link");
			pMessageCenterInboxPage.clickOnPDF();

			log("Step 7: Close the CCD");
			pMessageCenterInboxPage.verifyCCDViewerAndClose();
			
			log("Non Consolidated CCD - VD events generated successfully");
			
			log("Step 8: Logout of Patient Portal");
			pMyPatientPage.logout(driver);
			
			log("Step 9: Waiting for Events sync in DWH");
			Thread.sleep(420000);		
							
			// Setup oauth Client
			log("Step 10: Setup Oauth client 2.O"); 
			RestUtils.oauthSetup(testData.getOauthKeyStore(),testData.getOauthProperty(), testData.getOauthAppToken(), testData.getOauthUsername(), testData.getOauthPassword());
			
			
			// Build new Rest URL with epoch milliseconds 
			log("Original PULL API URl:"+testData.getPullAPIURL());
			  
			String restPullUrl=new StringBuilder(testData.getPullAPIURL()).append("&sinceTime=").append(timestamp).append("&maxEvents=40").toString();
			log("Updated PULL API URL: "+restPullUrl);
			
			log("Step 11: Send Pull API HTTP GET Request");
			RestUtils.setupHttpGetRequest(restPullUrl, testData.getResponsePath());

			String intuitPatientID=testData.getPatientID();
			log("Second practicePatientID: "+intuitPatientID);
			
			log("Step 12: Verification of CCD 'View' event present in Pull API response");	
			String ActionTimestamp1= UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.VIEW_ACTION, timestamp,intuitPatientID);
			Assert.assertNotNull(ActionTimestamp1, "'View' Event is not found in Response XML");
			viewEventTime=UtilityFunctions.generateDate(ActionTimestamp1);
			log("CCD 'View' event portal Time"+viewEventTime);
			
			// verify "Download" event in response XML and return Action Timestamp
			log("Step 13: Verification of CCD 'Download' event present in Pull API response");
			String ActionTimestamp2=UtilityFunctions.FindEventInResonseXML(testData.getResponsePath(),MU2Constants.EVENT,MU2Constants.RESOURCE_TYPE,MU2Constants.DOWNLOAD_ACTION,timestamp,intuitPatientID);
			Assert.assertNotNull(ActionTimestamp2, "'Download' Event is not found in Response XML");
			downloadEventTime=UtilityFunctions.generateDate(ActionTimestamp2);
			log("CCD 'Download' event portal Time"+downloadEventTime);
			
			//again Login to patient portal and Verify  event in Account activity
			
			log("Verification of event in Account activity");
			
			log("Step 14: LogIn");
			loginpage = new PortalLoginPage(driver, testData.getPortalURL());
			pMyPatientPage = loginpage.login(testData.getPortalUserName2(), testData.getPortalPassword());

			log("Step 15: Click on myaccountLink on MyPatientPage");
			MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

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
			
			 WebElement closeViewer = driver.findElement(By.linkText("Close Viewer"));
			 closeViewer.click();
				 
			 log("Step 16: Logout of Patient Portal");
			 pMyAccountPage.logout(driver);
			
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
   	 
  /* 	log("Step 8: Compare actual Push API response with sample expected xml and ensure both are same ");
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
         	
    
	
	

	
	

	

