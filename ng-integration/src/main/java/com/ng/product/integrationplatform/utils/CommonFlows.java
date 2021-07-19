//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.utils;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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

	public static void verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyFileLoader propertyLoaderObj,
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

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ entityidentifier + "')");

		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getOAuthUsername(),
				propertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		Log4jUtil.log("Step Begins: Do get processing status call and verify the processing time of " + type
				+ " to be received");

//		String processingUrl=propertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, propertyLoaderObj.getResponsePath());
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

	public static void verifyCCDProcessingStatus(PropertyFileLoader propertyLoaderObj, String personId,
			String practiceId, String integrationID, int ccdaType) throws Throwable {

		String ccdaRequestsQuery = null, requestId = null;
		if (ccdaType == 3) {
			ccdaRequestsQuery = "select top 1 processing_status from pxp_ccda_requests where person_id ='"
					+ personId.trim() + "' and type ='" + ccdaType + "' and practice_id='" + practiceId.trim()
					+ "' order by create_timestamp desc";
			requestId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select top 1 request_id from pxp_ccda_requests where person_id ='" + personId.trim()
							+ "' and type ='" + ccdaType + "' and practice_id='" + practiceId.trim()
							+ "' order by create_timestamp desc");
		} else {
			ccdaRequestsQuery = "select processing_status from pxp_ccda_requests where person_id ='" + personId.trim()
					+ "' and type ='" + ccdaType + "' and practice_id='" + practiceId.trim() + "'";
			requestId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select request_id from pxp_ccda_requests where person_id ='" + personId.trim() + "' and type ='"
							+ ccdaType + "' and practice_id='" + practiceId.trim() + "'");
		}

		String ccdaDocumentQuery = "select delete_ind from pxp_documents where request_id ='" + requestId.trim() + "'";
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);

		if (processingStatus.equals("1")) {
			Log4jUtil.log(
					"Processing status is " + processingStatus + " i.e. CCD is requested by patient from Portal.");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processingStatus.equals("2")) {
					Log4jUtil.log("Processing status is " + processingStatus + " i.e. CCD is generated from Rosetta.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "2");
		}
		if ((processingStatus.equals("2")) || (processingStatus.equals("4"))) {
			if (processingStatus.equals("2")) {
				Log4jUtil.log("Processing status is " + processingStatus + " i.e. CCD is generated from Rosetta.");
				String documentStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaDocumentQuery);
				if (!(documentStatus.equals("0")) || !(documentStatus.equals("false"))) {
					for (int i = 0; i < arg_timeOut; i++) {
						documentStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaDocumentQuery);
						if (documentStatus.equals("0") || documentStatus.equals("false")) {
							Log4jUtil.log("Document is present in document table");
							break;
						} else {
							if (i == arg_timeOut - 1)
								Thread.sleep(1000);
						}
					}
				}
				CommonUtils.VerifyTwoValues(documentStatus, "equals", "false");
			}

			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processingStatus.equals("4")) {
					Log4jUtil.log("Step End: Processing status is " + processingStatus
							+ " i.e. CCD has been posted to MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
//		CommonUtils.VerifyTwoValues(processingStatus,"equals","4");

			verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, requestId, integrationID,
					"CCD");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", ccdaRequestsQuery);
				if (processingStatus.equals("6")) {
					Log4jUtil.log("Step End: Processing status is " + processingStatus
							+ " i.e. RSDK has posted CCD to portal");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		} else if (processingStatus.equals("6")) {
			Log4jUtil
					.log("Step End: Processing status is " + processingStatus + " i.e. RSDK has posted CCD to portal");
		} else if (processingStatus.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. Failed to generate CCD from Rosetta");
		} else if (processingStatus.equals("5")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processingStatus + " i.e. Failed to post CCD to MF Agent");
		} else if (processingStatus.equals("7")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processingStatus + " i.e. Failed to deliver CCD to Portal");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "6");
	}

	public static void IsCCDReceived(WebDriver driver, String url, String username, String password, String personType,
			String practiceName) throws InterruptedException {
		Log4jUtil.log("Step Begins: Login to Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
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

		Log4jUtil.log("Step Begins: Validate message subject and send date");
		Thread.sleep(1000);
		Log4jUtil.log("Message Date" + IHGUtil.getEstTiming());
		assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
		Log4jUtil.log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

		messagesPage.OpenMessage(driver, "You have a new health data summary");

		Log4jUtil.log("Step Begins: Click on link View health data");
		NGCcdViewerPage ngCcdPage = messagesPage.findNGCcdMessage(driver);

		Log4jUtil.log("Step Begins: Verify all elements are present in CCD Viewer and click Close Viewer");
		ngCcdPage.verifyCCDPageElementsPresent();
		if (personType.equalsIgnoreCase("HavingSensitiveEncounterONDemand"))
			ngCcdPage.verifySensitiveONDemandCCDElementsContent(driver);
		else if (personType.equalsIgnoreCase("HavingSensitiveEncounterMSU"))
			ngCcdPage.verifySensitiveMSUCCDElementsContent(driver);
		else if (personType.equalsIgnoreCase("EncounterHavingALLData"))
			ngCcdPage.verifyCCDElementsContent(driver, "guaifenesin", "Lipitor 10 mg tablet", "Anemia",
					"Panel Description: Glucose [Mass/volume] in Serum or Plasma", "Chest pain, unspecified");
		else if (personType.equalsIgnoreCase("HavingUnSignedOffResult"))
			ngCcdPage.verifyCCDElementsContent(driver, "guaifenesin", "Lipitor 10 mg tablet", "Anemia", "",
					"Chest pain, unspecified");

		messagesPage = ngCcdPage.closeCcd(driver);

		Log4jUtil.log("Step Begins: Archive the CCDA message");
		messagesPage.archiveMessage();

		Log4jUtil.log("Step Begins: Logout");
		homePage.LogoutfromNGMFPortal();
		Log4jUtil.log("CCD is received to patient");
	}

	public static void requestCCD(WebDriver driver, String url, String username, String password, String personType,
			String practiceName) throws InterruptedException {
		Log4jUtil.log("Step Begins: Login to Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
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

	public static void deactivatePatient(WebDriver driver, String url, String usermame, String password, String fName,
			String lName) throws Exception {
		Log4jUtil.log("Step Begins: Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
		PracticeHomePage practiceHome = practiceLogin.login(usermame, password);
		Log4jUtil.log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		Log4jUtil.log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(fName, lName);
		patientSearchPage.clickOnPatient(fName, lName);
		Log4jUtil.log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		Log4jUtil.log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(fName, lName);
	}

	public static void deletePatient(WebDriver driver, String url, String usermame, String password, String fName,
			String lName) throws Exception {
		Log4jUtil.log("Step Begins: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, url);
		PracticeHomePage practiceHome = practiceLogin.login(usermame, password);
		Log4jUtil.log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		Log4jUtil.log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(fName, lName);
		patientSearchPage.clickOnPatient(fName, lName);
		Log4jUtil.log("Step Begins: Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(fName, lName);
	}

	public static String addDataToCCD(String locationName, String providerName, String personId, String practiceId)
			throws Throwable {
		Log4jUtil.log("Step Begins: Adding Test data to patient CCD " + personId);
		
		Log4jUtil.log("Step Begins: Add Encounter to patient chart");
		String encounterId = NGAPIFlows.addEncounter(locationName, providerName, personId);

		Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		NGAPIFlows.addDiagnosis(practiceId, personId, encounterId);

		Log4jUtil.log("Step Begins: Add Medication to created encounter");
		NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", personId, encounterId, "R07.9",
				286939);

		Log4jUtil.log("Step Begins: Add allergy to created encounter");
		NGAPIFlows.addAllergy(locationName, providerName, personId, encounterId, "1000", 2);

		Log4jUtil.log("Step Begins: Add Problem to patient chart");
		NGAPIFlows.addProblem(locationName, providerName, personId, "420543008", "55561003", "Active");

		Log4jUtil.log("Step Begins: Add Immunization to created encounter");
		NGAPIFlows.addNewImmunizationsOrder(locationName, providerName, personId, encounterId);

		Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
		String labOrderId = NGAPIFlows.addLabOrder(locationName, providerName, personId, encounterId);
		NGAPIFlows.addLabOrderTest(personId, labOrderId, "NG001032");
		String obsPanelId = NGAPIFlows.addObsPanel(personId, labOrderId);
		NGAPIFlows.addLabResult(personId, obsPanelId, "75325-1");
		NGAPIFlows.updateLabOrder(locationName, providerName, personId, labOrderId);
		Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter " + encounterId);
		return encounterId;
	}

	public static void verifyMessageProcessingStatus(PropertyFileLoader propertyLoaderObj, String personId,
			String practiceId, String commId, String integrationID, String messageType) throws Throwable {
		String messageProcessingStatusQuery = "select processing_status from ngweb_comm_recpts where comm_id ='"
				+ commId + "'";
		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + commId
				+ "'";
		String messageDeliveryStatusQuery = "select status from  message_delivery where message_groupid ='" + commId
				+ "'";
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
		String deliveryStatusATMF = "";

		if (processingStatus.equals("1")) {
			Log4jUtil.log("Processing status is " + processingStatus
					+ " i.e. New - Message is created by patient and posted to MF agent.");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processingStatus.equals("2")) {
					Log4jUtil.log("Processing status is " + processingStatus
							+ " i.e. InProgress - MF agent posted message to RSDK");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "2");
		}

		if (processingStatus.equals("2")) {
			Thread.sleep(40000);
			String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);
			deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);
			assertNotNull(deliveryStatusATMF);
			verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, messageID, integrationID,
					"Message");
			deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryStatusQuery);

			if (messageType.equalsIgnoreCase("ReadReceiptRequested"))
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "READ_SENT");
			else if (messageType.equalsIgnoreCase("UnReadRequested"))
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "UNREAD_SENT");
			else
				CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "SENT");

			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processingStatus.equals("3")) {
					Log4jUtil.log("Step End: Processing status is " + processingStatus
							+ " i.e. Delivered - Message is sent to Portal successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "3");
		}

		if (processingStatus.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. Delivered - Message is sent to Portal successfully");
		} else if (processingStatus.equals("4")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processingStatus + " i.e. Failed to send message to Portal");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "3");
	}

	public static String verifyMessageINInbox(PropertyFileLoader propertyLoaderObj, WebDriver driver, String url,
			String username, String password, String subject, String body, String commId, String messageID,
			String integrationID, String messageType, String attachmentName) throws Throwable {
		long timestamp = System.currentTimeMillis();
		String replyMessageID = "";
		Log4jUtil.log("Step Begins: Login to Patient Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, password);

		Log4jUtil.log("Step Begins: Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		Log4jUtil.log("Log the message read time ");
		long epoch = System.currentTimeMillis() / 1000;
		String readdatetimestamp = RestUtils.readTime(epoch);
		Log4jUtil.log("Message Read Time:" + readdatetimestamp);

		Log4jUtil.log("Step Begins: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, subject));
		messagesPage.verifyMessageContent(driver, subject, body);

		if (messageType.equalsIgnoreCase("SentByPracticeUser")) {
			String userId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select sender_id from ngweb_communications where comm_id ='" + commId + "'");
			String userFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select first_name from user_mstr where user_id='" + userId + "'");
			String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select last_name from user_mstr where user_id='" + userId + "'");
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}
		if (messageType.equalsIgnoreCase("SentByAlias")) {
			String userFirstName = "Alias";
			String userLastName = "Routing";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}
		if (messageType.equalsIgnoreCase("SentByOnlineProfile")) {
			String userFirstName = "Online";
			String userLastName = "Profile";
			messagesPage.verifySenderInfo(driver, userFirstName, userLastName);
		}

		if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {
			String userId = DBUtils.executeQueryOnDB("NGCoreDB",
					"select sender_id from ngweb_communications where comm_id ='" + commId + "'");
			String userFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select first_name from user_mstr where user_id='" + userId + "'");
			String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
					"select last_name from user_mstr where user_id='" + userId + "'");
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
					propertyLoaderObj.getProperty("get.read.receipt").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					propertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(propertyLoaderObj.getResponsePath(), messageID, readdatetimestamp);

			verifyReadReceiptReceived(commId, readdatetimestamp);
			verifyReadReceiptMessageReceived(commId, subject);
		} else if (messageType.equalsIgnoreCase("UnReadNotificationRequested")) {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + commId + "'");
			CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "NOTIFIED_FAILURE");
		} else if (messageType.equalsIgnoreCase("BulkCannotReply")) {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + commId + "'");
			Log4jUtil.log("Message Status at MF agent " + deliveryStatusATMF);
		} else if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {

			Log4jUtil.log("Step Begins: Wait 1 min, so the message can be processed");
			Thread.sleep(60000);

			Log4jUtil.log("Getting messages since timestamp: " + since);
			RestUtils.setupHttpGetRequest(
					propertyLoaderObj.getProperty("get.read.receipt").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					propertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate the message id and read time in response");
			RestUtils.isReadCommunicationMessage(propertyLoaderObj.getResponsePath(), messageID, readdatetimestamp);

			verifyReadReceiptReceived(commId, readdatetimestamp);
			verifyReadReceiptMessageReceived(commId, subject);
		} else {
			String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
					"select status from  message_delivery where message_groupid ='" + commId + "'");
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
					propertyLoaderObj.getProperty("get.inbound.message").replaceAll("integrationID", integrationID)
							+ "?since=" + since + ",0",
					propertyLoaderObj.getResponsePath());

			Log4jUtil.log("Step Begins: Validate message reply");
			replyMessageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(),
					"Re: " + subject, IntegrationConstants.MESSAGE_REPLY);
		}

		if (messageType.equalsIgnoreCase("BulkSentByPracticeUserReadReceiptRequested")) {
			replyMessageID = ReplyToMessage(propertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}
		if (messageType.equalsIgnoreCase("SentByPracticeUser")) {
			replyMessageID = ReplyToMessage(propertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}

		if (messageType.equalsIgnoreCase("SentByAlias")) {
			replyMessageID = ReplyToMessage(propertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}
		if (messageType.equalsIgnoreCase("SentByOnlineProfile")) {
			replyMessageID = ReplyToMessage(propertyLoaderObj, driver, messagesPage, timestamp, integrationID, subject);
		}

		Log4jUtil.log("Logging out");
		homePage.LogoutfromNGMFPortal();
		return replyMessageID;
	}

	public static String ReplyToMessage(PropertyFileLoader propertyLoaderObj, WebDriver driver,
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
				propertyLoaderObj.getProperty("get.inbound.message").replaceAll("integrationID", integrationID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		replyMessageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), "Re: " + subject,
				IntegrationConstants.MESSAGE_REPLY);
		return replyMessageID;
	}

	public static void verifyReadReceiptReceived(String commId, String actualReadDateTimestamp) throws Throwable {
		String messageProcessingStatusQuery = "select processing_status from ngweb_comm_recpts where comm_id ='"
				+ commId + "'";
		String messageDeliveryStatusQuery = "select status from  message_delivery where message_groupid ='" + commId
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

		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
		if (processingStatus.equals("3")) {
			Log4jUtil.log("Processing status is " + processingStatus + " i.e. Message is delivered to Patient");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", messageProcessingStatusQuery);
				if (processingStatus.equals("5")) {
					Log4jUtil.log("Processing status is " + processingStatus
							+ " i.e. Read Receipt is received by Practice User");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "5");
		}

		if (processingStatus.equals("4")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processingStatus + " i.e. Failed to send message to Portal");
		} else if (processingStatus.equals("5")) {
			Log4jUtil.log(
					"Processing status is " + processingStatus + " i.e. Read Receipt is received by Practice User");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "5");

		String messageReadTimeStamp = DBUtils.executeQueryOnDB("NGCoreDB",
				"select read_timestamp from ngweb_comm_recpts where comm_id ='" + commId + "'");

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

	public static void verifyReadReceiptMessageReceived(String commId, String subject) throws Throwable {
		DBUtils.executeQueryOnDB("NGCoreDB",
				"select comm_id from ngweb_communications where parent_id ='" + commId + "'");

		String ReadReceiptSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where parent_id ='" + commId + "'");

		subject = "(Read receipt) RE: " + subject;
		CommonUtils.VerifyTwoValues(ReadReceiptSubject, "equals", subject);
		String ReadReceiptBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where parent_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(ReadReceiptBody, "contains", "This message was read by");
	}

	public static void verifyMessageReceivedAtNGCore(PropertyFileLoader propertyLoaderObj, String commId,
			String subject, String body, String commCategory) throws Throwable {
		String actualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where comm_id ='" + commId + "'");
		if (actualSubject.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				actualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
						"select subject from ngweb_communications where comm_id ='" + commId + "'");
				if (!actualSubject.isEmpty()) {
					Log4jUtil.log("Message deilvered to NG Core");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(actualSubject, "equals", subject);
		String actualBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualBody.replace("\r", "").replace("\n", ""), "equals", body);
		String senderType = DBUtils.executeQueryOnDB("NGCoreDB",
				"select sender_type from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(senderType, "equals", "2");
		String bulk = DBUtils.executeQueryOnDB("NGCoreDB",
				"select isBulk from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(bulk, "equals", "false");
		String actualCommCategory = DBUtils.executeQueryOnDB("NGCoreDB",
				"select comm_category from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualCommCategory, "equals", commCategory);
	}

	public static void verifyReplyReceivedAtNGCore(String commId, String parentId, String rootThreadID, String subject,
			String body) throws Throwable {
		String actualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
				"select subject from ngweb_communications where comm_id ='" + commId + "'");
		if (actualSubject.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				actualSubject = DBUtils.executeQueryOnDB("NGCoreDB",
						"select subject from ngweb_communications where comm_id ='" + commId + "'");
				if (!actualSubject.isEmpty()) {
					Log4jUtil.log("Message deilvered to NG Core");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(actualSubject, "equals", subject);
		String actualBody = DBUtils.executeQueryOnDB("NGCoreDB",
				"select body from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualBody.replace("\r", "").replace("\n", ""), "equals", body);
		String senderType = DBUtils.executeQueryOnDB("NGCoreDB",
				"select sender_type from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(senderType, "equals", "2");
		String bulk = DBUtils.executeQueryOnDB("NGCoreDB",
				"select isBulk from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(bulk, "equals", "false");

		String actualParentId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select parent_id from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualParentId, "equals", parentId);
		String actualRootThreadID = DBUtils.executeQueryOnDB("NGCoreDB",
				"select root_thread_id from ngweb_communications where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualRootThreadID, "equals", rootThreadID);
	}

	public static void verifyAppointmentRequestReceived(String appointmentId, String reason, String startTime,
			String endTime, String days, String practiceId) throws Throwable {
		String apptProcessingStatusQuery = "Select processing_status from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String apptStatusQuery = "Select appointment_status_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String requestReasonQuery = "Select requested_reason from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String startTimeQuery = "Select requested_starttime1 from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String endTimeQuery = "Select requested_endtime1 from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String daysQuery = "Select requested_days1 from NGWEB_APPOINTMENT_REQ where appointment_id ='" + appointmentId
				+ "'";
		String practiceIDinApptTable = "Select nx_practice_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String practiceIdQuery = "select nx_practice_id from nxmd_practices where practice_id ='" + practiceId + "'";

		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
		if (processingStatus.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (processingStatus.equalsIgnoreCase("0")) {
					Log4jUtil.log("Step End: Appointment request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processingStatus, "equals", "0");
		String apptStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptStatusQuery);
		CommonUtils.VerifyTwoValues(apptStatus, "equals", "1");
		String requestReason = DBUtils.executeQueryOnDB("NGCoreDB", requestReasonQuery);
		CommonUtils.VerifyTwoValues(requestReason, "contains", reason);
		String actualStartTime = DBUtils.executeQueryOnDB("NGCoreDB", startTimeQuery);
		CommonUtils.VerifyTwoValues(actualStartTime, "equals", startTime);
		String actualEndTime = DBUtils.executeQueryOnDB("NGCoreDB", endTimeQuery);
		CommonUtils.VerifyTwoValues(actualEndTime, "equals", endTime);
		String actualDays = DBUtils.executeQueryOnDB("NGCoreDB", daysQuery);
		CommonUtils.VerifyTwoValues(actualDays, "equals", days);
		String expectedPracticeID = DBUtils.executeQueryOnDB("NGCoreDB", practiceIdQuery);
		String actualPracticeIDinDB = DBUtils.executeQueryOnDB("NGCoreDB", practiceIDinApptTable);
		CommonUtils.VerifyTwoValues(actualPracticeIDinDB, "equals", expectedPracticeID);
		Log4jUtil.log("Step End: The appointment request is reached to EPM/EHR Inbox");
	}

	public static void verifyAppointmentProcessingStatus(PropertyFileLoader propertyLoaderObj, String appointmentId,
			String integrationID) throws Throwable {
		String apptProcessingStatusQuery = "Select processing_status from NGWEB_APPOINTMENT_REQ where appointment_id='"
				+ appointmentId + "'";
		String apptResponseQuery = "Select epm_appt_id from NGWEB_APPOINTMENT_RESP where appointment_id ='"
				+ appointmentId + "'";
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);

		if (processingStatus.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if ((processingStatus.equals("0")) || (processingStatus.equals("1"))
						|| (processingStatus.equals("2"))) {
					Log4jUtil.log("Step End: Appointment request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		if (processingStatus.equals("0")) {
			Log4jUtil.log("Processing status is " + processingStatus
					+ " i.e. appointment request is reached to EPM/EHR Inbox from Portal.");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (processingStatus.equals("1")) {
					Log4jUtil.log("Processing status is " + processingStatus
							+ " i.e. appointment response is picked up by MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "1");
		}

		String apptResponse = DBUtils.executeQueryOnDB("NGCoreDB", apptResponseQuery);
		if (apptResponse.isEmpty()) {
			for (int i = 0; i < arg_timeOut; i++) {
				apptResponse = DBUtils.executeQueryOnDB("NGCoreDB", apptResponseQuery);
				if (!apptResponse.isEmpty()) {
					Log4jUtil.log("Step End: Appointment is posted successfully to MF agent");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, apptResponse, integrationID,
				"Appointment");

		if (processingStatus.equals("1")) {
			Log4jUtil.log(
					"Processing status is " + processingStatus + " i.e. appointment response is sent to MF agent");
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", apptProcessingStatusQuery);
				if (processingStatus.equals("2")) {
					Log4jUtil.log("Processing status is " + processingStatus
							+ " i.e. appointment is sent successfully to Portal");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "2");
		} else if (processingStatus.equals("2")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. appointment is sent successfully to Portal");
		} else if (processingStatus.equals("3")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. Failed to post appointment to Portal");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "2");
	}

	public static void verifyAppointmentBookedResponseCaptured(String appointmentId, String response)
			throws Throwable {
		String apptStatusQuery = "Select appointment_status_id from NGWEB_APPOINTMENT_REQ where appointment_id ='"
				+ appointmentId + "'";
		String apptResponseQuery = "Select message from NGWEB_APPOINTMENT_RESP where appointment_id ='" + appointmentId
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
		CommonUtils.VerifyTwoValues(expectedApptResponse, "equals", response);
		Log4jUtil.log("Step End: The appointment response is saved to NG_Web_Response Table");
	}

	public static void verifyAppointmentReceivedinPortal(PropertyFileLoader propertyLoaderObj, WebDriver driver,
			String url, String username, String appointmentDate, String appointmentTime, String body)
			throws InterruptedException {
		Log4jUtil.log("Step Begins: Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		if (!body.isEmpty()) {
			JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

			Log4jUtil.log("Step Begins: Validate message loads and is the right message");
			assertTrue(messagesPage.isMessageDisplayed(driver, propertyLoaderObj.getProperty("appointment.subject")));
			messagesPage.verifyMessageContent(driver, propertyLoaderObj.getProperty("appointment.subject"), body);
			messagesPage.backToHomePage(driver);
		}

		Log4jUtil.log("Step Begins: Click appointment request");
		homePage.clickOnAppointmentV3(driver);
		JalapenoAppointmentsPage appointmentsPage = PageFactory.initElements(driver, JalapenoAppointmentsPage.class);

		Thread.sleep(5000);
		Log4jUtil.log("Step Begins: Verify booked appointment received in Portal");
		Boolean appointmentStatus = appointmentsPage.verifyAppointment(appointmentDate, appointmentTime,
				propertyLoaderObj.getProperty("epm.provider.name"));
		assertTrue(appointmentStatus, "Booked Appointment didnot receive by Patient");

		driver.navigate().back();
		homePage = PageFactory.initElements(driver, JalapenoHomePage.class);
		homePage.LogoutfromNGMFPortal();
		Log4jUtil.log("Step End: Booked Appointment is received by Patient");
	}

	public static void verifyAppointmentDeletedinPortal(PropertyFileLoader propertyLoaderObj, WebDriver driver,
			String url, String username, String appointmentDate, String appointmentTime) throws InterruptedException {
		Log4jUtil.log("Step Begins: Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		Log4jUtil.log("Step Begins: Click appointment request");
		homePage.clickOnAppointmentV3(driver);
		JalapenoAppointmentsPage appointmentsPage = PageFactory.initElements(driver, JalapenoAppointmentsPage.class);

		Log4jUtil.log("Step Begins: Verify appointment is deleted");
		appointmentsPage.verifyAppointmentisDeleted(appointmentDate, appointmentTime);
		Log4jUtil.log("Booked Appointment is deleted successfully");
	}

	public static void verifyPrescriptionRenewalRequestReceived(String renewalRequestId, String reason,
			String practiceId) throws Throwable {
		String processingStatusQuery = "Select processing_status from nxmd_med_renewals where id ='" + renewalRequestId
				+ "'";
		String requestReasonQuery = "Select request_reason from nxmd_med_renewals where id ='" + renewalRequestId
				+ "'";
		String practiceIDinPrescriptionTable = "Select nx_practice_id from nxmd_med_renewals where id ='"
				+ renewalRequestId + "'";
		String practiceIdQuery = "select nx_practice_id from nxmd_practices where practice_id ='" + practiceId + "'";

		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", processingStatusQuery);
		if (processingStatus.isEmpty() || processingStatus.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", processingStatusQuery);
				if (processingStatus.equalsIgnoreCase("1")) {
					Log4jUtil.log("Step End: Prescription Renewal request is reached to EPM/EHR Inbox from Portal.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processingStatus, "equals", "1");

		String requestReason = DBUtils.executeQueryOnDB("NGCoreDB", requestReasonQuery);
		CommonUtils.VerifyTwoValues(requestReason, "contains", reason);

		String expectedPracticeID = DBUtils.executeQueryOnDB("NGCoreDB", practiceIdQuery);
		String actualPracticeIDinDB = DBUtils.executeQueryOnDB("NGCoreDB", practiceIDinPrescriptionTable);
		CommonUtils.VerifyTwoValues(actualPracticeIDinDB, "equals", expectedPracticeID);
		Log4jUtil.log("Step End: The Prescription Renewal request is reached to EPM/EHR Inbox");
	}

	public static void verifyPaymentReachedtoMFAgent(String comment, String actualType, String actualPatientNumber,
			String actualAccountNumber, String actualStatus, String actualAmount, String actualCardType)
			throws Throwable {
		Log4jUtil.log("Step Begins: Verify payment details at MF agent");
		String typeQuery = "select type from payment where comment = '" + comment + "'";
		String patientNumberQuery = "select patient_number from payment where comment = '" + comment + "'";
		String accountNumberQuery = "select account_number from payment where comment = '" + comment + "'";
		String statusQuery = "select status from payment where comment = '" + comment + "'";
		String amountQuery = "select amount from payment where comment = '" + comment + "'";
		String cardTypeQuery = "select card_type from payment where comment = '" + comment + "'";

		String type = DBUtils.executeQueryOnDB("MFAgentDB", typeQuery);
		if (type.isEmpty() || type.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				type = DBUtils.executeQueryOnDB("MFAgentDB", typeQuery);
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
			String patientNumber = DBUtils.executeQueryOnDB("MFAgentDB", patientNumberQuery);
			CommonUtils.VerifyTwoValues(patientNumber, "equals", actualPatientNumber);
		}
		String accountNumber = DBUtils.executeQueryOnDB("MFAgentDB", accountNumberQuery);
		CommonUtils.VerifyTwoValues(accountNumber, "equals", actualAccountNumber);
		String status = DBUtils.executeQueryOnDB("MFAgentDB", statusQuery);
		CommonUtils.VerifyTwoValues(status, "equals", actualStatus);
		String amount = DBUtils.executeQueryOnDB("MFAgentDB", amountQuery);
		CommonUtils.VerifyTwoValues(amount, "equals", actualAmount);
		String cardType = DBUtils.executeQueryOnDB("MFAgentDB", cardTypeQuery);
		CommonUtils.VerifyTwoValues(cardType, "equals", actualCardType);
		Log4jUtil.log("Step End: The payment details are verified at MF agent");
	}

	public static void verifyPaymentPostedtoNG(String comment, String actualSourceID, String actualPersonID,
			String actualAmount, String actualTrackingDesc, String actualPracticeID) throws Throwable {
		Log4jUtil.log("Step Begins: Verify payment is posted to NG");
		String sourceIDQuery = "select source_id from transactions where transaction_notes = '" + comment + "'";
		String personIDQuery = "select person_id from transactions where transaction_notes = '" + comment + "'";
		String amountQuery = "select tran_amt from transactions where transaction_notes = '" + comment + "'";
		String trackingDescQuery = "select tracking_desc_40 from transactions where transaction_notes = '" + comment
				+ "'";
		String practiceIDQuery = "select practice_id from transactions where transaction_notes = '" + comment + "'";

		String personId = DBUtils.executeQueryOnDB("NGCoreDB", personIDQuery);
		if (personId.isEmpty() || personId.equals("NULL")) {
			for (int i = 0; i < arg_timeOut; i++) {
				personId = DBUtils.executeQueryOnDB("NGCoreDB", personIDQuery);
				if (personId.equalsIgnoreCase(actualPersonID)) {
					Log4jUtil.log("Payment is posted to NG");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(personId, "equals", actualPersonID);
		}
		CommonUtils.VerifyTwoValues(personId, "equals", actualPersonID);

		String sourceID = DBUtils.executeQueryOnDB("NGCoreDB", sourceIDQuery);
		CommonUtils.VerifyTwoValues(sourceID, "equals", actualSourceID);
		String amount = DBUtils.executeQueryOnDB("NGCoreDB", amountQuery);
		CommonUtils.VerifyTwoValues(amount, "equals", actualAmount);
		String trackingDesc = DBUtils.executeQueryOnDB("NGCoreDB", trackingDescQuery);
		CommonUtils.VerifyTwoValues(trackingDesc, "equals", actualTrackingDesc);
		String practiceID = DBUtils.executeQueryOnDB("NGCoreDB", practiceIDQuery);
		CommonUtils.VerifyTwoValues(practiceID, "equals", actualPracticeID);
		Log4jUtil.log("Step End: The payment is posted to NG successfully");
	}

	public static void verifyAttachmentReceivedInMessageAtNGCore(PropertyFileLoader propertyLoaderObj, String commId,
			String format, String attachmentName) throws Throwable {
		Log4jUtil.log("Step Begins: Verify attachment is received in message");
		String actualFormat = DBUtils.executeQueryOnDB("NGCoreDB",
				"select format from pxp_comm_attachment_data where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualFormat, "equals", format);

		String actualAttachmentData = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_data from pxp_comm_attachment_data where comm_id ='" + commId + "'");
		assertTrue((!actualAttachmentData.isEmpty()), "Attachment is received");

		String attachmentIdPxp = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_id from pxp_comm_attachment_data where comm_id ='" + commId + "'");
		String attachmentIdNG = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_id from ngweb_comm_attachments where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(attachmentIdPxp.toUpperCase(), "equals", attachmentIdNG.toUpperCase());

		String actualAttachmentName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_name from ngweb_comm_attachments where comm_id ='" + commId + "'");
		CommonUtils.VerifyTwoValues(actualAttachmentName, "equals", attachmentName);
		Log4jUtil.log("Step End: The attachment is received in message");
	}

	public static void verifyMultipleAttachmentsReceivedInMessageAtNGCore(PropertyFileLoader propertyLoaderObj,
			String commId, String format, String attachmentName) throws Throwable {
		Log4jUtil.log("Step Begins: Verify attachment is received in message");
		String actualattachmentId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_id from ngweb_comm_attachments where comm_id ='" + commId
						+ "' and attachment_name ='" + attachmentName + "'");

		String actualFormat = DBUtils.executeQueryOnDB("NGCoreDB",
				"select format from pxp_comm_attachment_data where comm_id ='" + commId + "' and attachment_id ='"
						+ actualattachmentId + "'");
		CommonUtils.VerifyTwoValues(actualFormat, "equals", format);

		String actualAttachmentData = DBUtils.executeQueryOnDB("NGCoreDB",
				"select attachment_data from pxp_comm_attachment_data where comm_id ='" + commId
						+ "' and attachment_id ='" + actualattachmentId + "'");
		assertTrue((!actualAttachmentData.isEmpty()), "Attachment is received");
		Log4jUtil.log("Step End: The attachment is received in message");
	}

	public static void verifyDocumentInsertedIntoTables(String requestId, String comments) throws Throwable {
		String documentContentQuery = "select content from pxp_documents where request_id ='" + requestId + "'";
		String documentRequestsQuery = "select name from pxp_document_requests where request_id ='" + requestId + "'";
		String documentHistoryQuery = "select emr_doc_id from ngweb_document_history where comments ='" + comments
				+ "'";

		String documentContent = DBUtils.executeQueryOnDB("NGCoreDB", documentContentQuery);
		String documentRequests = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);
		String documentHistory = DBUtils.executeQueryOnDB("NGCoreDB", documentHistoryQuery);

		if ((documentContent.isEmpty() && documentRequests.isEmpty() && documentHistory.isEmpty())
				|| ((documentContent == null) && (documentRequests == null) && (documentHistory == null)))
			Log4jUtil.log("Document is not added into tables");
		else
			Log4jUtil.log("Document is added into tables successfully");
	}

	public static void verifyDocumentProcessingStatus(PropertyFileLoader propertyLoaderObj, String requestId,
			String practiceId, String integrationID) throws Throwable {

		String documentRequestsQuery = "select status from pxp_document_requests where request_id ='" + requestId
				+ "' and practice_id='" + practiceId.trim() + "'";
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);

		if ((processingStatus.equals("2")) || (processingStatus.equals("4"))) {
			if (processingStatus.equals("2")) {
				Log4jUtil.log("Processing status is " + processingStatus + " i.e. Document is added into table.");
				for (int i = 0; i < arg_timeOut; i++) {
					processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);
					if (processingStatus.equals("4")) {
						Log4jUtil.log("Step End: Processing status is " + processingStatus
								+ " i.e. EHR Document has been posted to MF agent");
						break;
					} else {
						if (i == arg_timeOut - 1)
							Thread.sleep(1000);
					}
				}
				CommonUtils.VerifyTwoValues(processingStatus, "equals", "4");
			}
			verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, requestId, integrationID,
					"EHR Document");

			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);
				if (processingStatus.equals("6")) {
					Log4jUtil.log("Step End: Processing status is " + processingStatus
							+ " i.e. RSDK has posted EHR Document to portal");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		} else if (processingStatus.equals("6")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. RSDK has posted EHR Document to portal");
		} else if (processingStatus.equals("3")) {
			Log4jUtil.log(
					"Step End: Processing status is " + processingStatus + " i.e. Failed to generate EHR Document");
		} else if (processingStatus.equals("5")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. Failed to post EHR Document to MF Agent");
		} else if (processingStatus.equals("7")) {
			Log4jUtil.log("Step End: Processing status is " + processingStatus
					+ " i.e. Failed to deliver EHR Document to Portal");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "6");
	}

	public static void verifyPatientDocumentReceivedINInbox(PropertyFileLoader propertyLoaderObj, WebDriver driver,
			String url, String username, String password, String subject, String body, String attachmentName)
			throws Throwable {
		Log4jUtil.log("Step Begins: Login to Patient Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, password);

		Log4jUtil.log("Step Begins: Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
		Log4jUtil.log("Step Begins: Find message in Inbox with message subject " + subject);

		Log4jUtil.log("Step Begins: Validate message loads and is the right message");
		assertTrue(messagesPage.isMessageDisplayed(driver, subject));

		messagesPage.verifyMessageContent(driver, subject, body);
		Log4jUtil.log("Step Begins:Verify attachment in message");
		messagesPage.verifyMessageAttachment(driver, attachmentName);

		Log4jUtil.log("Logging out");
		homePage.LogoutfromNGMFPortal();
	}

	public static void verifyIMHState(String documentId) throws Throwable {
		Log4jUtil.log("Step Begins: Verify current status of IMH");
		String currentStateQuery = "select current_state from ngweb_imh_question_state where id = '" + documentId + "'";
		String currentState = DBUtils.executeQueryOnDB("NGCoreDB", currentStateQuery);

		if (currentState.equals("1")) {
			for (int i = 0; i < arg_timeOut; i++) {
				currentState = DBUtils.executeQueryOnDB("NGCoreDB", currentStateQuery);
				if (currentState.equals("2")) {
					Log4jUtil.log("IMH response is received to NG");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(currentState, "equals", "2");
		}
		CommonUtils.VerifyTwoValues(currentState, "equals", "2");
	}

	public static void verifyDocumentReadStatus(String requestId) throws Throwable {
		Log4jUtil.log("Step Begins: Verify read status of document");
		String documentRequestsQuery = "select status from pxp_document_requests where request_id ='" + requestId + "'";
		String processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);

		if (processingStatus.equals("6")) {
			for (int i = 0; i < arg_timeOut; i++) {
				processingStatus = DBUtils.executeQueryOnDB("NGCoreDB", documentRequestsQuery);
				if (processingStatus.equals("8")) {
					Log4jUtil.log("Document is read by patient");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
			CommonUtils.VerifyTwoValues(processingStatus, "equals", "8");
		}
		CommonUtils.VerifyTwoValues(processingStatus, "equals", "8");
	}

	public static String getPAMLastName(String response) {
		JsonObject jObj = new JsonParser().parse(response).getAsJsonObject();
		jObj.remove("URL");

		String namevalue = CommonUtils.getResponseKeyValue(jObj.toString(), "name");
		namevalue = namevalue.substring(1, namevalue.length() - 1);
		String lastName = CommonUtils.getResponseKeyValue(namevalue, "family");
		Log4jUtil.log("LastName is " + lastName);

		return lastName;
	}
}
