//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.utils;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentsPage.JalapenoAppointmentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.NGCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.ng.product.integrationplatform.flows.NGAPIFlows;

public class CommonFlows {

	static int arg_timeOut = 1800;

	public static void verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyFileLoader PropertyLoaderObj,
			String entityidentifier, String integrationID, String type) throws Throwable {

		entityidentifier = entityidentifier.trim().replace("\t", "");
		Log4jUtil.log("Step Begins: Verify the status of MF agent job for " + type);
		DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus_entity where entityidentifier ='" + entityidentifier + "'");
		String jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");

		if (jobStatus1.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
						"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
								+ entityidentifier + "')");
				if ((jobStatus1.equalsIgnoreCase("Pending")) || (jobStatus1.equalsIgnoreCase("COMPLETED"))) {
					Log4jUtil.log("Step End: Request is sent to RSDK successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		Log4jUtil.log("Status of MF agent job " + jobStatus1);
		if (!jobStatus1.isEmpty()) {
			if (jobStatus1.equalsIgnoreCase("Pending"))
				Log4jUtil.log("Step End: Request is sent to RSDK and inprocess with job status " + jobStatus1);
			else if (jobStatus1.equalsIgnoreCase("COMPLETED"))
				Log4jUtil.log(type + " is received");
		} else {
			Log4jUtil.log("Please check Bad request or MF agent is not working");
		}

		String jobID = DBUtils.executeQueryOnDB("MFAgentDB",
				"select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");

		String messageID = DBUtils.executeQueryOnDB("MFAgentDB",
				"select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");

		Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(),
				PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),
				PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		Log4jUtil.log("Step Begins: Do get processing status call and verify the processing time of " + type
				+ " to be received");

//		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
//		assertTrue(completed, "Message processing was not completed in time");

		Thread.sleep(60000);
		Log4jUtil.log("Step Begins: Verify the processing status of MF agent job after the request reached to RSDK");
		String ccdaStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus_entity where entityidentifier ='" + entityidentifier + "'");

		if (ccdaStatus.equalsIgnoreCase("Pending")) {
			for (int i = 0; i < arg_timeOut; i++) {
				ccdaStatus = DBUtils.executeQueryOnDB("MFAgentDB",
						"select status from processingstatus_entity where entityidentifier ='" + entityidentifier
								+ "'");
				if (ccdaStatus.equalsIgnoreCase("COMPLETED")) {
					Log4jUtil.log("Step End: Request is sent to RSDK successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(ccdaStatus, "equals", "COMPLETED");

		String jobStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");
		CommonUtils.VerifyTwoValues(jobStatus, "equals", "COMPLETED");
		Log4jUtil.log("Step End: The processing status of MF agent job is " + jobStatus + " Request is sent to RSDK");
	}

	public static void verifyCCDProcessingStatus(PropertyFileLoader PropertyLoaderObj, String person_id,
			String practice_id, String integrationID, int ccdaType) throws Throwable {

		String ccdaRequestsQuery = "select processing_status from pxp_ccda_requests where person_id ='"
				+ person_id.trim() + "' and type ='" + ccdaType + "' and practice_id='" + practice_id.trim() + "'";
		String request_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select request_id from pxp_ccda_requests where person_id ='" + person_id.trim() + "' and type ='"
						+ ccdaType + "' and practice_id='" + practice_id.trim() + "'");
		String ccdaDocumentQuery = "select delete_ind from pxp_documents where request_id ='" + request_id.trim() + "'";
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);

		if (processing_status.equals("1")) {
			Log4jUtil.log(
					"Processing status is " + processing_status + " i.e. CCD is requested by patient from Portal.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processing_status.equals("2")) {
					Log4jUtil.log("Processing status is " + processing_status + " i.e. CCD is generated from Rosetta.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "2");
		}
		if ((processing_status.equals("2")) || (processing_status.equals("4"))) {
			if (processing_status.equals("2")) {
				Log4jUtil.log("Processing status is " + processing_status + " i.e. CCD is generated from Rosetta.");
				String document_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaDocumentQuery);
				if (!(document_status.equals("0")) || !(document_status.equals("false"))) {
					for (int i = 0; i < arg_timeOut; i++) {
						document_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaDocumentQuery);
						if (document_status.equals("0") || document_status.equals("false")) {
							Log4jUtil.log("Document is present in document table");
							break;
						} else {
							if (i == arg_timeOut - 1)
								Thread.sleep(1000);
						}
					}
				}
				CommonUtils.VerifyTwoValues(document_status, "equals", "false");
			}

			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processing_status.equals("4")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. CCD has been posted to MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
//		CommonUtils.VerifyTwoValues(processing_status,"equals","4");

			verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj, request_id, integrationID,
					"CCD");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processing_status.equals("6")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. RSDK has posted CCD to portal");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		} else if (processing_status.equals("6")) {
			Log4jUtil
					.log("Step End: Processing status is " + processing_status + " i.e. RSDK has posted CCD to portal");
		} else if (processing_status.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processing_status
					+ " i.e. Failed to generate CCD from Rosetta");
		} else if (processing_status.equals("5")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processing_status + " i.e. Failed to post CCD to MF Agent");
		} else if (processing_status.equals("7")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processing_status + " i.e. Failed to deliver CCD to Portal");
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "6");
	}

	public static void IsCCDReceived(WebDriver driver, String URL, String username, String password, String personType,
			String practiceName) throws InterruptedException {
		Log4jUtil.log("Step Begins: Login to Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, URL);
		JalapenoHomePage homePage = loginPage.login(username, password);

		if (personType.equalsIgnoreCase("Dependent") || personType.equalsIgnoreCase("TrustedRepresentative")) {
			homePage.faChangePatient();
			Thread.sleep(10000);
		} else if (personType.equalsIgnoreCase("MultiPractice")) {
			homePage.switchToPractice(practiceName);
			Thread.sleep(40000);
		}

		Log4jUtil.log("Step Begins: Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

		Log4jUtil.log("Step Begins: Validate message subject and send date");
		Thread.sleep(1000);
		Log4jUtil.log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		Log4jUtil.log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		messagesPage.OpenMessage(driver, "You have a new health data summary");

		Log4jUtil.log("Step Begins: Click on link View health data");
		NGCcdViewerPage ngCcdPage = messagesPage.findNGCcdMessage(driver);

		Log4jUtil.log("Step Begins: Verify if CCD Viewer is loaded");
		assertTrue(ngCcdPage.areBasicPageElementsPresent());

		Log4jUtil.log("Step Begins: Verify all elements are present in CCD Viewer and click Close Viewer");
		ngCcdPage.verifyCCDPageElementsPresent();
		if (personType.equalsIgnoreCase("HavingSensitiveEncounterONDemand"))
			ngCcdPage.verifySensitiveONDemandCCDElementsContent(driver);
		else if (personType.equalsIgnoreCase("HavingSensitiveEncounterMSU"))
			ngCcdPage.verifySensitiveMSUCCDElementsContent(driver);
		else if (personType.equalsIgnoreCase("EncounterHavingALLData"))
			ngCcdPage.verifyCCDElementsContent(driver, "guaifenesin", "Lipitor 10 mg tablet", "Chest pain, unspecified",
					"Panel Description: Glucose [Mass/volume] in Serum or Plasma", "Chest pain, unspecified");
		else if (personType.equalsIgnoreCase("HavingUnSignedOffResult"))
			ngCcdPage.verifyCCDElementsContent(driver, "guaifenesin", "Lipitor 10 mg tablet", "Chest pain, unspecified",
					"", "Chest pain, unspecified");

		messagesPage = ngCcdPage.closeCcd(driver);

		Log4jUtil.log("Step Begins: Archive the CCDA message");
		messagesPage.archiveMessage();

		Log4jUtil.log("Step Begins: Logout");
		homePage.LogoutfromNGMFPortal();
		Log4jUtil.log("CCD is received to patient");
	}

	public static void requestCCD(WebDriver driver, String URL, String username, String password, String personType,
			String practiceName) throws InterruptedException {
		Log4jUtil.log("Step Begins: Login to Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, URL);
		JalapenoHomePage homePage = loginPage.login(username, password);

		if (personType.equalsIgnoreCase("Dependent") || personType.equalsIgnoreCase("TrustedRepresentative")) {
			homePage.faChangePatient();
			Thread.sleep(10000);
		} else if (personType.equalsIgnoreCase("MultiPractice")) {
			homePage.switchToPractice(practiceName);
			Thread.sleep(40000);
		}

		Log4jUtil.log("Step Begins: Go to  Health Record Summaries");
		MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);
		assertTrue(MedicalRecordSummariesPageObject.areBasicPageElementsPresent(),
				"Failed to Load Health Record Summaries ");

		Log4jUtil.log("Step Begins: Click on Request Health Record");
		MedicalRecordSummariesPageObject.selectHealthRecordRequestButton();
		Thread.sleep(6000);

		Log4jUtil.log("Step Begins: Request complete Health Record");
		MedicalRecordSummariesPageObject.requestCompleteRecord();

		Log4jUtil.log("Step Begins: Close the onDemand PopUp");
		MedicalRecordSummariesPageObject.closeOnDemandPopUpButton();

		Log4jUtil.log("Step Begins: Logout");
		homePage.LogoutfromNGMFPortal();
		Log4jUtil.log("ON Demand CCD is requested by patient successfully");
		}
         
	public static void deactivatePatient(WebDriver driver, String URL, String Usermame, String Password, String FName,
			String LName) throws Exception {
		Log4jUtil.log("Step Begins: Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, URL);
		PracticeHomePage practiceHome = practiceLogin.login(Usermame, Password);
		Log4jUtil.log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		Log4jUtil.log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(FName, LName);
		patientSearchPage.clickOnPatient(FName, LName);
		Log4jUtil.log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		Log4jUtil.log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(FName, LName);
		}      
   
	public static void deletePatient(WebDriver driver, String URL, String Usermame, String Password, String FName,
			String LName) throws Exception {
		Log4jUtil.log("Step Begins: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, URL);
		PracticeHomePage practiceHome = practiceLogin.login(Usermame, Password);
		Log4jUtil.log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		Log4jUtil.log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(FName, LName);
		patientSearchPage.clickOnPatient(FName, LName);
		Log4jUtil.log("Step Begins: Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(FName, LName);
	}

	public static String addDataToCCD(String locationName, String providerName, String person_id, String practiceId)
			throws Throwable {
		Log4jUtil.log("Step Begins: Adding Test data to patient CCD " + person_id);
		Log4jUtil.log("Step Begins: Add Chart to patient");
//		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		Log4jUtil.log("Step Begins: Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, person_id);

		Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);

		Log4jUtil.log("Step Begins: Add Medication to created encounter");
		String medication_id = NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", person_id,
				encounter_id, "R07.9", 286939);

		Log4jUtil.log("Step Begins: Add allergy to created encounter");
		String allergy_id = NGAPIFlows.addAllergy(locationName, providerName, person_id, encounter_id, "1000", 2);

		Log4jUtil.log("Step Begins: Add Problem to patient chart");
		String problem_id = NGAPIFlows.addProblem(locationName, providerName, person_id, "420543008", "55561003",
				"Active");

		Log4jUtil.log("Step Begins: Add Immunization to created encounter");
		String immunization_id = NGAPIFlows.addNewImmunizationsOrder(locationName, providerName, person_id,
				encounter_id);

		Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
		String labOrder_id = NGAPIFlows.addLabOrder(locationName, providerName, person_id, encounter_id);
		String labOrderTest_id = NGAPIFlows.addLabOrderTest(person_id, labOrder_id, "NG001032");
		String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
		String labResult_id = NGAPIFlows.addLabResult(person_id, ObsPanel_id, "75325-1");
		String updatelabOrder_id = NGAPIFlows.updateLabOrder(locationName, providerName, person_id, labOrder_id);
		Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter " + encounter_id);
		return encounter_id;
	}

	public static void verifyMessageProcessingStatus(PropertyFileLoader PropertyLoaderObj, String person_id,
			String practice_id, String comm_id, String integrationID, String messageType) throws Throwable {
		String messageProcessingStatusQuery = "select processing_status from ngweb_comm_recpts where comm_id ='"
				+ comm_id + "'";
		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageDeliveryStatusQuery = "select status from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
		String deliveryStatusATMF = "";

		if (processing_status.equals("1")) {
			Log4jUtil.log("Processing status is " + processing_status
					+ " i.e. New - Message is created by patient and posted to MF agent.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processing_status.equals("2")) {
					Log4jUtil.log("Processing status is " + processing_status
							+ " i.e. InProgress - MF agent posted message to RSDK");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "2");
		}

		if (processing_status.equals("2")) {
			Thread.sleep(40000);
			String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);
			deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);
			assertNotNull(deliveryStatusATMF);
			verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj, messageID, integrationID,
					"Message");
			deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);

			if (messageType.equalsIgnoreCase("ReadReceiptRequested"))
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "READ_SENT");
			else if (messageType.equalsIgnoreCase("UnReadRequested"))
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "UNREAD_SENT");
			else
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "SENT");

			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processing_status.equals("3")) {
					Log4jUtil.log("Step End: Processing status is " + processing_status
							+ " i.e. Delivered - Message is sent to Portal successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "3");
		}

		if (processing_status.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processing_status
					+ " i.e. Delivered - Message is sent to Portal successfully");
		} else if (processing_status.equals("4")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processing_status + " i.e. Failed to send message to Portal");
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "3");
	}

	public static String verifyMessageINInbox(PropertyFileLoader PropertyLoaderObj, WebDriver driver, String URL,
			String username, String password, String subject, String body, String comm_id, String messageID,
			String integrationID, String messageType, String attachmentName) throws Throwable {
		long timestamp = System.currentTimeMillis();
		String replyMessageID = "";
		Log4jUtil.log("Step Begins: Login to Patient Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, URL);
		JalapenoHomePage homePage = loginPage.login(username, password);
		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));
		Log4jUtil.log("Step Begins: Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");
		Log4jUtil.log("Step Begins: Find message in Inbox with message subject " + subject);

		Log4jUtil.log("Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;

		String readdatetimestamp = RestUtils.readTime(epoch);
		Log4jUtil.log("Message Read Time:" + readdatetimestamp);

		Log4jUtil.log("Step Begins: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, subject));

		messagesPage.verifyMessageContent(driver, subject, body);

		if (messageType.equalsIgnoreCase("SentByPracticeUser")) {
			String userId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select sender_id from ngweb_communications where comm_id ='" + comm_id + "'");
			String userFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select first_name from user_mstr where user_id='" + userId + "'");
			String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select last_name from user_mstr where user_id='" + userId + "'");
			String ExpectedSenderName = userLastName + ", " + userFirstName + "Dr";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}
		if (messageType.equalsIgnoreCase("SentByAlias")) {
			String userFirstName = "Alias";
			String userLastName = "Routing";
			String ExpectedSenderName = "Routing, Alias Staff";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}
		if (messageType.equalsIgnoreCase("SentByOnlineProfile")) {
			String userFirstName = "Online";
			String userLastName = "Profile";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}

		if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {
			String userId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select sender_id from ngweb_communications where comm_id ='" + comm_id + "'");
			String userFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select first_name from user_mstr where user_id='" + userId + "'");
			String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select last_name from user_mstr where user_id='" + userId + "'");
			String ExpectedSenderName = userLastName + ", " + userFirstName + "Dr";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}

		if (messageType.equalsIgnoreCase("CannotReply")) {
			Boolean replyStatus = messagesPage.verifyReplyButton(driver);
			assertTrue(replyStatus, "Reply Button is not displayed as expected");
		}

		if (messageType.equalsIgnoreCase("BulkCannotReply")) {
			Boolean replyStatus = messagesPage.verifyReplyButton(driver);
			assertTrue(replyStatus, "Reply Button is not displayed as expected");
		}

		if (messageType.equalsIgnoreCase("messageWithPE")) {
			messagesPage.verifyMessageAttachment(driver, attachmentName);
		}

		Log4jUtil.log("Step Begins: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		if (messageType.equalsIgnoreCase("ReadReceiptRequested")) {
			Log4jUtil.log("Step Begins: Wait 1 min, so the message can be processed");
			Thread.sleep(60000);

			Log4jUtil.log("Getting messages since timestamp: " + since);
			RestUtils.setupHttpGetRequest(
					PropertyLoaderObj.getProperty("GetReadReceipt").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					PropertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(PropertyLoaderObj.getResponsePath(), messageID, readdatetimestamp);

			verifyReadReceiptReceived(comm_id, readdatetimestamp);
			verifyReadReceiptMessageReceived(comm_id, subject);
		} else if (messageType.equalsIgnoreCase("UnReadNotificationRequested")) {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + comm_id + "'");
			CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "NOTIFIED_FAILURE");
		} else if (messageType.equalsIgnoreCase("BulkCannotReply")) {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + comm_id + "'");
			Log4jUtil.log("Message Status at MF agent " + deliveryStatusATMF);
		} else if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {

			Log4jUtil.log("Step Begins: Wait 1 min, so the message can be processed");
			Thread.sleep(60000);

			Log4jUtil.log("Getting messages since timestamp: " + since);
			RestUtils.setupHttpGetRequest(
					PropertyLoaderObj.getProperty("GetReadReceipt").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					PropertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(PropertyLoaderObj.getResponsePath(), messageID, readdatetimestamp);

			verifyReadReceiptReceived(comm_id, readdatetimestamp);
			verifyReadReceiptMessageReceived(comm_id, subject);
		} else {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + comm_id + "'");
			CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "SENT");
		}

		if (messageType.equalsIgnoreCase("SendReply")) {
			Log4jUtil.log("Step Begins: Reply to the message");
			Boolean replyStatus = messagesPage.replyToMessage(driver);
			assertTrue(replyStatus, "Message sent to Practice User");

			Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
			Thread.sleep(60000);

			Log4jUtil.log("Step Begins: Do a GET and get the message");
			RestUtils.setupHttpGetRequest(
					PropertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					PropertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate message reply");
			replyMessageID = RestUtils.isReplyPresentReturnMessageID(PropertyLoaderObj.getResponsePath(),
					"Re: " + subject, IntegrationConstants.MESSAGE_REPLY);
		}

		if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {
			replyMessageID = ReplyToMessage(PropertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}
		if (messageType.equalsIgnoreCase("SentByPracticeUser")) {
			replyMessageID = ReplyToMessage(PropertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}

		if (messageType.equalsIgnoreCase("SentByAlias")) {
			replyMessageID = ReplyToMessage(PropertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}
		if (messageType.equalsIgnoreCase("SentByOnlineProfile")) {
			replyMessageID = ReplyToMessage(PropertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}

		Log4jUtil.log("Logging out");
		homePage.LogoutfromNGMFPortal();
		return replyMessageID;
	}

	public static String ReplyToMessage(PropertyFileLoader PropertyLoaderObj, WebDriver driver,
			JalapenoMessagesPage messagesPage, Long timestamp, String integrationID, String subject) throws Throwable {
		Long since = timestamp / 1000L - 60 * 24;
		String replyMessageID = "";
		Log4jUtil.log("Step Begins: Reply to the message");
		Boolean replyStatus = messagesPage.replyToMessage(driver);
		assertTrue(replyStatus, "Message sent to Practice User");

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				PropertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationID)
						+ "?since=" + since + ",0",
				PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		replyMessageID = RestUtils.isReplyPresentReturnMessageID(PropertyLoaderObj.getResponsePath(), "Re: " + subject,
				IntegrationConstants.MESSAGE_REPLY);
		return replyMessageID;
	}

	public static void verifyReadReceiptReceived(String comm_id, String actualReadDateTimestamp) throws Throwable {
		String messageProcessingStatusQuery = "select processing_status from ngweb_comm_recpts where comm_id ='"
				+ comm_id + "'";
		String messageDeliveryStatusQuery = "select status from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);

		if (deliveryStatusATMF.equalsIgnoreCase("READ_SENT")) {
			Log4jUtil.log("Waiting for the Read Receipt notification to be sent");
			for (int i = 0; i < arg_timeOut; i++) {
				deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);
				if (deliveryStatusATMF.equalsIgnoreCase("NOTIFIED")) {
					Log4jUtil.log(
							"Step End: RSDK sent the Read Receipt to MF agent and MF agent sent to NG successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "NOTIFIED");
		} else if (deliveryStatusATMF.equalsIgnoreCase("NOTIFIED"))
			Log4jUtil.log("RSDK sent the Read Receipt to MF agent and MF agent sent to NG successfully");
		else if (deliveryStatusATMF.equalsIgnoreCase("NOTIFIED_FAILURE"))
			Log4jUtil.log("Failed to send Read Receipt notification by RSDK");

		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
		if (processing_status.equals("3")) {
			Log4jUtil.log("Processing status is " + processing_status + " i.e. Message is delivered to Patient");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processing_status.equals("5")) {
					Log4jUtil.log("Processing status is " + processing_status
							+ " i.e. Read Receipt is received by Practice User");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "5");
		}

		if (processing_status.equals("4")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processing_status + " i.e. Failed to send message to Portal");
		} else if (processing_status.equals("5")) {
			Log4jUtil.log(
					"Processing status is " + processing_status + " i.e. Read Receipt is received by Practice User");
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "5");

		String messageReadTimeStamp = DBUtils.executeQueryOnDB("NGCoreDB",
				"select read_timestamp from ngweb_comm_recpts where comm_id ='" + comm_id + "'");

		Boolean ReadTimeStampStatus = false;
		actualReadDateTimestamp = actualReadDateTimestamp.replace("T", " ");
		actualReadDateTimestamp = actualReadDateTimestamp.substring(0, actualReadDateTimestamp.lastIndexOf(":") + 2);
		Log4jUtil.log("Actual Read DateTimestamp " + actualReadDateTimestamp);
		if (messageReadTimeStamp.contains(actualReadDateTimestamp)) {
			ReadTimeStampStatus = true;
			Log4jUtil.log("Read TimeStamp is added to ngweb_comm_recpts table " + actualReadDateTimestamp);
		}
		assertTrue(ReadTimeStampStatus, "Read TimeStamp is not added to ngweb_comm_recpts table");
	}

	public static void verifyReadReceiptMessageReceived(String comm_id, String subject) throws Throwable {
		String ReadReceiptCommID = DBUtils.executeQueryOnDB("NGCoreDB",
				"select comm_id from ngweb_communications where parent_id ='" + comm_id + "'");

		String ReadReceiptSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where parent_id ='" + comm_id + "'");

		subject = "(Read receipt) RE: " + subject;
		CommonUtils.VerifyTwoValues(ReadReceiptSubject, "equals", subject);
		String ReadReceiptBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where parent_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(ReadReceiptBody, "contains", "This message was read by");
	}

	public static void verifyMessageReceivedAtNGCore(PropertyFileLoader PropertyLoaderObj, String comm_id,
			String subject, String body, String comm_category) throws Throwable {
		String ActualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where comm_id ='" + comm_id + "'");
		if (ActualSubject.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				ActualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
						"select subject from ngweb_communications where comm_id ='" + comm_id + "'");
				if (!ActualSubject.isEmpty()) {
					Log4jUtil.log("Message deilvered to NG Core");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(ActualSubject, "equals", subject);
		String ActualBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(ActualBody.replace("\r", "").replace("\n", ""), "equals", body);
		String senderType = DBUtils.executeQueryOnDB("NGCoreDB",
				"select sender_type from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(senderType, "equals", "2");
		String bulk = DBUtils.executeQueryOnDB("NGCoreDB",
				"select isBulk from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(bulk, "equals", "false");
		String actualCommCategory = DBUtils.executeQueryOnDB("NGCoreDB",
				"select comm_category from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(actualCommCategory, "equals", comm_category);
	}

	public static void verifyReplyReceivedAtNGCore(String comm_id, String parentId, String rootThreadID, String subject,
			String body) throws Throwable {
		String ActualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where comm_id ='" + comm_id + "'");
		if (ActualSubject.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				ActualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
						"select subject from ngweb_communications where comm_id ='" + comm_id + "'");
				if (!ActualSubject.isEmpty()) {
					Log4jUtil.log("Message deilvered to NG Core");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(ActualSubject, "equals", subject);
		String ActualBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(ActualBody.replace("\r", "").replace("\n", ""), "equals", body);
		String senderType = DBUtils.executeQueryOnDB("NGCoreDB",
				"select sender_type from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(senderType, "equals", "2");
		String bulk = DBUtils.executeQueryOnDB("NGCoreDB",
				"select isBulk from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(bulk, "equals", "false");

		String ActualParentId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select parent_id from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(ActualParentId, "equals", parentId);
		String ActualRootThreadID = DBUtils.executeQueryOnDB("NGCoreDB",
				"select root_thread_id from ngweb_communications where comm_id ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(ActualRootThreadID, "equals", rootThreadID);
	}

	public static void verifyAppointmentRequestReceived(String appointment_id, String Reason, String StartTime,
			String EndTime, String Days, String practiceId) throws Throwable {
		String apptProcessingStatusQuery = "Select processing_status from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String apptStatusQuery = "Select appointment_status_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String requestReasonQuery = "Select requested_reason from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String startTimeQuery = "Select requested_starttime1 from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String endTimeQuery = "Select requested_endtime1 from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String daysQuery = "Select requested_days1 from NGWEB_APPOINTMENT_REQ where appointment_id ='" + appointment_id
				+ "'";
		String PracticeIDinApptTable = "Select nx_practice_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String practiceIdQuery = "select nx_practice_id from nxmd_practices where practice_id ='" + practiceId + "'";

		String ProcessingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
		if (ProcessingStatus.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				ProcessingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (ProcessingStatus.equalsIgnoreCase("0")) {
					Log4jUtil.log("Step End: Appointment request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(ProcessingStatus, "equals", "0");
		String apptStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptStatusQuery);
		CommonUtils.VerifyTwoValues(apptStatus, "equals", "1");
		String requestReason = DBUtils.executeQueryOnDB("NGCoreDB", requestReasonQuery);
		CommonUtils.VerifyTwoValues(requestReason, "contains", Reason);
		String startTime = DBUtils.executeQueryOnDB("NGCoreDB", startTimeQuery);
		CommonUtils.VerifyTwoValues(startTime, "equals", StartTime);
		String endTime = DBUtils.executeQueryOnDB("NGCoreDB", endTimeQuery);
		CommonUtils.VerifyTwoValues(endTime, "equals", EndTime);
		String days = DBUtils.executeQueryOnDB("NGCoreDB", daysQuery);
		CommonUtils.VerifyTwoValues(days, "equals", Days);
		String ExpectedPracticeID = DBUtils.executeQueryOnDB("NGCoreDB", practiceIdQuery);
		String ActualPracticeIDinDB = DBUtils.executeQueryOnDB("NGCoreDB", PracticeIDinApptTable);
		CommonUtils.VerifyTwoValues(ActualPracticeIDinDB, "equals", ExpectedPracticeID);
		Log4jUtil.log("Step End: The appointment request is reached to EPM/EHR Inbox");
	}

	public static void verifyAppointmentProcessingStatus(PropertyFileLoader PropertyLoaderObj, String appointment_id,
			String integrationID) throws Throwable {
		String apptProcessingStatusQuery = "Select processing_status from NGWEB_APPOINTMENT_REQ where appointment_id='"
				+ appointment_id + "'";
		String apptResponseQuery = "Select epm_appt_id from NGWEB_APPOINTMENT_RESP where appointment_id ='"
				+ appointment_id + "'";
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);

		if (processing_status.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if ((processing_status.equals("0")) || (processing_status.equals("1"))
						|| (processing_status.equals("2"))) {
					Log4jUtil.log("Step End: Appointment request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		if (processing_status.equals("0")) {
			Log4jUtil.log("Processing status is " + processing_status
					+ " i.e. appointment request is reached to EPM/EHR Inbox from Portal.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (processing_status.equals("1")) {
					Log4jUtil.log("Processing status is " + processing_status
							+ " i.e. appointment response is picked up by MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "1");
		}

		String appt_Response = DBUtils.executeQueryOnDB("NGCoreDB", apptResponseQuery);
		if (appt_Response.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				appt_Response = DBUtils.executeQueryOnDB("NGCoreDB", apptResponseQuery);
				if (!appt_Response.isEmpty()) {
					Log4jUtil.log("Step End: Appointment is posted successfully to MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj, appt_Response, integrationID,
				"Appointment");

		if (processing_status.equals("1")) {
			Log4jUtil.log(
					"Processing status is " + processing_status + " i.e. appointment response is sent to MF agent");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (processing_status.equals("2")) {
					Log4jUtil.log("Processing status is " + processing_status
							+ " i.e. appointment is sent successfully to Portal");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processing_status, "equals", "2");
		} else if (processing_status.equals("2")) {
			Log4jUtil.log("Step End: Processing status is " + processing_status
					+ " i.e. appointment is sent successfully to Portal");
		} else if (processing_status.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processing_status
					+ " i.e. Failed to post appointment to Portal");
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "2");
	}

	public static void verifyAppointmentBookedResponseCaptured(String appointment_id, String Response)
			throws Throwable {
		String apptStatusQuery = "Select appointment_status_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointment_id + "'";
		String apptResponseQuery = "Select message from NGWEB_APPOINTMENT_RESP where appointment_id ='" + appointment_id
				+ "'";
		String apptStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptStatusQuery);

		if (apptStatus.equals("1")) {
			Log4jUtil.log("Appointment status is " + apptStatus + " i.e. appointment status is Pending");
			for (int i = 0; i < arg_timeOut; i++) {
				apptStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptStatusQuery);
				if (apptStatus.equals("2")) {
					Log4jUtil.log("Appointment status is " + apptStatus + " i.e. appointment status is Booked");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(apptStatus, "equals", "2");
		}

		CommonUtils.VerifyTwoValues(apptStatus, "equals", "2");
		String expectedApptResponse = DBUtils.executeQueryOnDB("NGCoreDB", apptResponseQuery);
		CommonUtils.VerifyTwoValues(expectedApptResponse, "equals", Response);
		Log4jUtil.log("Step End: The appointment response is saved to NG_Web_Response Table");
	}

	public static void verifyAppointmentReceivedinPortal(PropertyFileLoader PropertyLoaderObj, WebDriver driver,
			String url, String username, String appointmentDate, String appointmentTime, String body)
			throws InterruptedException {
		Log4jUtil.log("Step Begins: Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());

		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		if (!body.isEmpty()) {
			JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

			Log4jUtil.log("Step Begins: Validate message loads and is the right message");
			assertTrue(messagesPage.isMessageDisplayed(driver, PropertyLoaderObj.getProperty("AppointmentSubject")));
			messagesPage.verifyMessageContent(driver, PropertyLoaderObj.getProperty("AppointmentSubject"), body);
			messagesPage.backToHomePage(driver);
		}

		Log4jUtil.log("Step Begins: Click appointment request");
		homePage.clickOnAppointmentV3(driver);
		JalapenoAppointmentsPage appointmentsPage = PageFactory.initElements(driver, JalapenoAppointmentsPage.class);

		Thread.sleep(5000);
		Log4jUtil.log("Step Begins: Verify booked appointment received in Portal");
		Boolean appointmentStatus = appointmentsPage.verifyAppointment(appointmentDate, appointmentTime,
				PropertyLoaderObj.getProperty("ResourceName"));
		assertTrue(appointmentStatus, "Booked Appointment didnot receive by Patient");

		driver.navigate().back();
		homePage = PageFactory.initElements(driver, JalapenoHomePage.class);
		homePage.LogoutfromNGMFPortal();
		Log4jUtil.log("Step End: Booked Appointment is received by Patient");
	}

	public static void verifyAppointmentDeletedinPortal(PropertyFileLoader PropertyLoaderObj, WebDriver driver,
			String url, String username, String appointmentDate, String appointmentTime) throws InterruptedException {
		Log4jUtil.log("Step Begins: Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());

		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		Log4jUtil.log("Step Begins: Click appointment request");
		homePage.clickOnAppointmentV3(driver);
		JalapenoAppointmentsPage appointmentsPage = PageFactory.initElements(driver, JalapenoAppointmentsPage.class);

		Log4jUtil.log("Step Begins: Verify appointment is deleted");
		appointmentsPage.verifyAppointmentisDeleted(appointmentDate, appointmentTime);
		Log4jUtil.log("Booked Appointment is deleted successfully");
	}

	public static void verifyPrescriptionRenewalRequestReceived(String renewalrequest_id, String Reason,
			String practiceId) throws Throwable {
		String ProcessingStatusQuery = "Select processing_status from nxmd_med_renewals where id ='" + renewalrequest_id
				+ "'";
		String requestReasonQuery = "Select request_reason from nxmd_med_renewals where id ='" + renewalrequest_id
				+ "'";
		String PracticeIDinPrescriptionTable = "Select nx_practice_id from nxmd_med_renewals where id ='"
				+ renewalrequest_id + "'";
		String practiceIdQuery = "select nx_practice_id from nxmd_practices where practice_id ='" + practiceId + "'";

		String ProcessingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ProcessingStatusQuery);
		if (ProcessingStatus.isEmpty() || ProcessingStatus.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				ProcessingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ProcessingStatusQuery);
				if (ProcessingStatus.equalsIgnoreCase("1")) {
					Log4jUtil.log("Step End: Prescription Renewal request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(ProcessingStatus, "equals", "1");

		String requestReason = DBUtils.executeQueryOnDB("NGCoreDB", requestReasonQuery);
		CommonUtils.VerifyTwoValues(requestReason, "contains", Reason);

		String ExpectedPracticeID = DBUtils.executeQueryOnDB("NGCoreDB", practiceIdQuery);
		String ActualPracticeIDinDB = DBUtils.executeQueryOnDB("NGCoreDB", PracticeIDinPrescriptionTable);
		CommonUtils.VerifyTwoValues(ActualPracticeIDinDB, "equals", ExpectedPracticeID);
		Log4jUtil.log("Step End: The Prescription Renewal request is reached to EPM/EHR Inbox");
	}

	public static void verifyPaymentReachedtoMFAgent(String comment, String actualType, String actualPatientNumber,
			String actualAccountNumber, String actualStatus, String actualAmount, String actualCardType)
			throws Throwable {
		Log4jUtil.log("Step Begins: Verify payment details at MF agent");
		String TypeQuery = "select type from payment where comment = '" + comment + "'";
		String PatientNumberQuery = "select patient_number from payment where comment = '" + comment + "'";
		String AccountNumberQuery = "select account_number from payment where comment = '" + comment + "'";
		String StatusQuery = "select status from payment where comment = '" + comment + "'";
		String AmountQuery = "select amount from payment where comment = '" + comment + "'";
		String CardTypeQuery = "select card_type from payment where comment = '" + comment + "'";

		String type = DBUtils.executeQueryOnDB("MFAgentDB", TypeQuery);
		if (type.isEmpty() || type.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				type = DBUtils.executeQueryOnDB("MFAgentDB", TypeQuery);
				if (type.equalsIgnoreCase(actualType)) {
					Log4jUtil.log("Payment is received by MF agent successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(type, "equals", actualType);
		}
		CommonUtils.VerifyTwoValues(type, "equals", actualType);

		if (!actualPatientNumber.isEmpty()) {
			String PatientNumber = DBUtils.executeQueryOnDB("MFAgentDB", PatientNumberQuery);
			CommonUtils.VerifyTwoValues(PatientNumber, "equals", actualPatientNumber);
		}
		String AccountNumber = DBUtils.executeQueryOnDB("MFAgentDB", AccountNumberQuery);
		CommonUtils.VerifyTwoValues(AccountNumber, "equals", actualAccountNumber);
		String Status = DBUtils.executeQueryOnDB("MFAgentDB", StatusQuery);
		CommonUtils.VerifyTwoValues(Status, "equals", actualStatus);
		String Amount = DBUtils.executeQueryOnDB("MFAgentDB", AmountQuery);
		CommonUtils.VerifyTwoValues(Amount, "equals", actualAmount);
		String CardType = DBUtils.executeQueryOnDB("MFAgentDB", CardTypeQuery);
		CommonUtils.VerifyTwoValues(CardType, "equals", actualCardType);
		Log4jUtil.log("Step End: The payment details are verified at MF agent");
	}

	public static void verifyPaymentPostedtoNG(String comment, String actualSourceID, String actualPersonID,
			String actualAmount, String actualTrackingDesc, String actualPracticeID) throws Throwable {
		Log4jUtil.log("Step Begins: Verify payment is posted to NG");
		String SourceIDQuery = "select source_id from transactions where transaction_notes = '" + comment + "'";
		String PersonIDQuery = "select person_id from transactions where transaction_notes = '" + comment + "'";
		String AmountQuery = "select tran_amt from transactions where transaction_notes = '" + comment + "'";
		String TrackingDescQuery = "select tracking_desc_40 from transactions where transaction_notes = '" + comment
				+ "'";
		String PracticeIDQuery = "select practice_id from transactions where transaction_notes = '" + comment + "'";

		String person_id = DBUtils.executeQueryOnDB("NGCoreDB", PersonIDQuery);
		if (person_id.isEmpty() || person_id.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				person_id = DBUtils.executeQueryOnDB("NGCoreDB", PersonIDQuery);
				if (person_id.equalsIgnoreCase(actualPersonID)) {
					Log4jUtil.log("Payment is posted to NG");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(person_id, "equals", actualPersonID);
		}
		CommonUtils.VerifyTwoValues(person_id, "equals", actualPersonID);

		String SourceID = DBUtils.executeQueryOnDB("NGCoreDB", SourceIDQuery);
		CommonUtils.VerifyTwoValues(SourceID, "equals", actualSourceID);
		String Amount = DBUtils.executeQueryOnDB("NGCoreDB", AmountQuery);
		CommonUtils.VerifyTwoValues(Amount, "equals", actualAmount);
		String TrackingDesc = DBUtils.executeQueryOnDB("NGCoreDB", TrackingDescQuery);
		CommonUtils.VerifyTwoValues(TrackingDesc, "equals", actualTrackingDesc);
		String PracticeID = DBUtils.executeQueryOnDB("NGCoreDB", PracticeIDQuery);
		CommonUtils.VerifyTwoValues(PracticeID, "equals", actualPracticeID);
		Log4jUtil.log("Step End: The payment is posted to NG successfully");
	}
}
