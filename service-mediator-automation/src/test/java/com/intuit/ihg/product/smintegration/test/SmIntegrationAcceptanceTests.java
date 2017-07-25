package com.intuit.ihg.product.smintegration.test;

import java.util.HashMap;
import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.WebDriverFactory;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.PhrLoginPage;
import com.intuit.ihg.product.object.maps.phr.page.messages.PhrMessagesPage;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.object.maps.patientportal1.page.PortalLoginPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessageCenterInboxPage;
import com.medfusion.product.object.maps.patientportal1.page.inbox.MessagePage;
import com.intuit.ihg.product.object.maps.smintegration.page.AppointmentReqtStep2Page;
import com.intuit.ihg.product.object.maps.smintegration.page.AppointmentReqtStep3Page;
import com.intuit.ihg.product.object.maps.smintegration.page.AppointmentRequestFirstPage;
import com.intuit.ihg.product.object.maps.smintegration.page.BetaCreateNewPatientPage;
import com.medfusion.product.patientportal1.utils.PortalConstants;
import com.medfusion.product.patientportal1.utils.PortalUtil;
import com.intuit.ihg.product.smintegration.utils.SmIntegration;
import com.intuit.ihg.product.smintegration.utils.SmIntegrationConstants;
import com.intuit.ihg.product.smintegration.utils.SmIntegrationTestData;
import com.intuit.ihg.product.smintegration.utils.SmIntegrationUtil;

/**
 * @author bkrishnankutty
 * @Date 6/Aug/2013
 * @Description :-
 * @Note :
 */

public class SmIntegrationAcceptanceTests extends BaseTestNGWebDriver {

	/**
	 * @Author:-bbinisha
	 * @Date:-23/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List steps : Login to patient portal Click on Appointment Request tab Create a new appointment Check the Integration Status id in DB
	 *                    Check the ppia_status and state Get ReplyToMessage ID (i.e ppia_id) from the outbound xml and update same in inbound xml Update
	 *                    patient,Provider and practice details in xml and do inbound Check the message in patient portal
	 *                    ============================================================= Doesn't work in P10, QA3 and PROD
	 *                    =============================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testApptRequestOutboundAndInbound() throws Exception {
		String ppia_id = null;
		log("+++++++++++++ Test run+++++++++++");

		log("Test Case: testAppointmentRequest");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		SmIntegration smIntegration = new SmIntegration();
		SmIntegrationTestData testData = new SmIntegrationTestData(smIntegration);

		String userName = "";
		String password = "";
		String patientID = "";

		if (IHGUtil.getEnvironmentType().toString().equals("DEV3")) {

			userName = testData.getARUserName();
			password = testData.getARPassword();
			patientID = testData.getARPatient();

		} else {
			userName = testData.getUserName();
			password = testData.getPassword();
			patientID = testData.getPatientID();
		}
		log("Patient ID *****" + patientID);
		log("step 2: LogIn with " + userName + " and " + password);
		PortalLoginPage loginPage = new PortalLoginPage(driver, testData.getUrl());
		MyPatientPage myPatientPage = loginPage.login(userName, password);

		log("step 3: Click on Appointment Button on My Patient Page");
		myPatientPage.clickAppointmentRequestTab();
		
		log("step 4: Complete Appointment Request Step1 Page  ");
		AppointmentRequestFirstPage apptRequest = new AppointmentRequestFirstPage(driver);
		apptRequest.clickContinueBtn();

		AppointmentReqtStep2Page apptRequestStep2 = new AppointmentReqtStep2Page(driver);
		Thread.sleep(10000);
		log("step 5: Complete Appointment Request Step2 Page  ");
		String appointmentReason = apptRequestStep2.fillInForm(PortalConstants.PreferredTimeFrame, PortalConstants.PreferredDay,
				PortalConstants.ChoosePreferredTime, PortalConstants.ApptReason, PortalConstants.WhichIsMoreImportant, "9847562145");

		log("Reason for visit is ************** :" + appointmentReason);

		AppointmentReqtStep3Page apptRequestStep3 = new AppointmentReqtStep3Page(driver);

		log("step 6: Complete Appointment Request Step3 Page  ");
		apptRequestStep3.clickSubmit();

		myPatientPage.logout(driver);
		driver.close();

		if (IHGUtil.getEnvironmentType().toString().equals("PROD")) {
			log("PROD-DB Environment is not stable.");
		} else {
			String vAppointmentReason = appointmentReason.toString();

			log(" Step 7 : Getting the Appointment request id from pgDB");
			String appointment_request_id = SmIntegrationUtil.getAppointmentRequestid(vAppointmentReason, testData.getPGDBName(), testData.getDBEnv(),
					testData.getDBUserName(), testData.getDBPassword());

			log("Waiting for DB (integrationStatus) to get update.");
			Thread.sleep(480000);

			int integrationId = Integer.parseInt(testData.getIntegrationID().toString());

			log(" Step 8 : Getting the integration status id from pgDB");
			String integrationStatus = SmIntegrationUtil.getintegrationStatusIDFromDB(integrationId, testData.getPGDBName(), testData.getDBEnv(),
					testData.getDBUserName(), testData.getDBPassword());

			log("Step 9 : Verify whether the integration status id");
			BaseTestSoftAssert.verifyEquals(integrationStatus, SmIntegrationConstants.INTEGRATIONSTATUS_ID, "Failure***Integration Status ID is not as expected");

			if (IHGUtil.getEnvironmentType().toString().equals("DEV3") || IHGUtil.getEnvironmentType().toString().equals("QA1")) {


				HashMap<String, String> dataMap = null;
				if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
					log(" Step 10 : Getting the ppia status and state value from pgDB");
					dataMap = SmIntegrationUtil.getPartnerIntegrationIDFromDB(appointment_request_id, testData.getSMDBName(), testData.getDBEnv(),
							testData.getdBUserNameSM(), testData.getDBPasswordSM());

					log("Step 11 : Verify whether the ppia status id");
					BaseTestSoftAssert.verifyEquals(dataMap.get("Status"), SmIntegrationConstants.PPIA_STATUS_VALUE1, "Failure***PPIA Status ID is not as expected");

				} else {
					log(" Step 10 : Getting the ppia status and state value from pgDB");
					dataMap = SmIntegrationUtil.getPartnerIntegrationIDFromDB(appointment_request_id, testData.getSMDBName(), testData.getDBEnv(),
							testData.getDBUserName(), testData.getDBPassword());

					log("Step 11 : Verify whether the ppia status id");
					BaseTestSoftAssert.verifyEquals(dataMap.get("Status"), SmIntegrationConstants.PPIA_STATUS_VALUE1, "Failure***PPIA Status ID is not as expected");
					log("Step 12 : Verify whether the ppia state id");
				}
				if (dataMap.get("State").contains(SmIntegrationConstants.PPIASTATE_VALUE1) || dataMap.get("State").contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
					BaseTestSoftAssert.verifyTrue(true);
				} else {
					verifyTrue(false, "Failure*** State Value is not as expected");
				}

				ppia_id = dataMap.get(SmIntegrationConstants.PPIA_ID_COLUMN);

				String PPIAL_RESPONSE = "";
				if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
					PPIAL_RESPONSE = SmIntegrationUtil.getPPIAReqXMLFromActivityLog(ppia_id, testData.getSMDBName(), testData.getDBEnv(), testData.getdBUserNameSM(),
							testData.getDBPasswordSM());
				} else {
					PPIAL_RESPONSE = SmIntegrationUtil.getPPIAReqXMLFromActivityLog(ppia_id, testData.getSMDBName(), testData.getDBEnv(), testData.getDBUserName(),
							testData.getDBPassword());
				}
				log("Step 13 : Verify whether the ppia_id with error message");
				if (PPIAL_RESPONSE.contains(SmIntegrationConstants.PPIAL_RESPONSE_VALUE)) {
					BaseTestSoftAssert.verifyTrue(true);
				} else {
					verifyTrue(false, "Expected Error Not Found");

				}
			}
			if (IHGUtil.getEnvironmentType().toString().equals("DEMO")) {

				log(" Step 10 : Getting the ppia status and state value from pgDB");
				HashMap<String, String> dataMap = SmIntegrationUtil.getPartnerIntegrationIDFromDB(appointment_request_id, testData.getSMDBName(), testData.getDBEnv(),
						testData.getDBUserName(), testData.getDBPassword());

				log("Step 11 : Verify whether the ppia status id");
				BaseTestSoftAssert.verifyEquals(dataMap.get("Status"), SmIntegrationConstants.PPIASTATUS_VALUE, "Failure***PPIA Status ID is not as expected");

				log("Step 12 : Verify whether the ppia state id");

				if (dataMap.get("State").contains(SmIntegrationConstants.PPIASTATE_VALUE1) || dataMap.get("State").contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
					BaseTestSoftAssert.verifyTrue(true);
				} else {
					verifyTrue(false, "Failure*** State Value is not as expected");
				}
			}
			Thread.sleep(5000);
			HashMap<String, String> appointmentReq = null;
			if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
				appointmentReq = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.APPOINTTMENT_REQUEST_OUTBOUND_PPIA_PPI_ID, testData.getSMDBName(),
						testData.getDBEnv(), testData.getdBUserNameSM(), testData.getDBPasswordSM());
			} else {
				appointmentReq = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.APPOINTTMENT_REQUEST_OUTBOUND_PPIA_PPI_ID, testData.getSMDBName(),
						testData.getDBEnv(), testData.getDBUserName(), testData.getDBPassword());
			}
			String appointmentReq_xml = appointmentReq.get("ppia_request_xml");
			
			SmIntegrationUtil util = new SmIntegrationUtil(driver);

			log("Step 13 : Write the DB xml to below path");
			util.writeStringToXml(SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "dbresponse.xml", appointmentReq_xml);

			String reasonForVisit =
					SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.APPOINTMENT_REQUEST + "dbresponse.xml", "appointmentRequest", "reasonForVisit");

			log("Verify whether the Request xml from SM DB contains the appointment Reason provided in the outbound.");
			// verifyEquals(appointmentReason, reasonForVisit ,"The Appointment reason in xml and the filled form is different." );

			if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
				ppia_id = SmIntegrationUtil.getPPIDFromSM(testData.getSMDBName(), testData.getdBUserNameSM(), testData.getDBPasswordSM(), testData.getDBEnv());
			} else {
				ppia_id = SmIntegrationUtil.getPPIDFromSM(testData.getSMDBName(), testData.getDBUserName(), testData.getDBPassword(), testData.getDBEnv());
			}
			log("PPIA_ID**************" + ppia_id);

			log("##############################################################################################");

			log("========== In bound testcase happens from here======================");

			log("Test Case: testAppointmentRequestInBound");

			log("Path for Appointment request Inbound XML" + SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "Connecthub-Inbound-AppointmentRequestProcessed.xml");
			String inputxmlFilePath = SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "Connecthub-Inbound-AppointmentRequestProcessed.xml";

			Thread.sleep(3000);
			log("File to string Conversation");
			String inputxml = util.fileToString(inputxmlFilePath);

			log("Updating the xml with patient,practice and provider details");
			
			
			log("Updating the xml with All Script ID");
			String allScript_New_Value = IHGUtil.createRandomNumericString();
			
			log("ALLSCRIPT_ID*******" + allScript_New_Value);
			String updatedReqXmlInString =
					util.updateXML(inputxml, SmIntegrationConstants.TAG_ALLSCRIPT_ID, SmIntegrationConstants.ALLSCRIPT_OLD_VALUE, allScript_New_Value);


			log("Updating the xml with Intuit Patient ID");
			updatedReqXmlInString =
					util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PATIENT_ID, SmIntegrationConstants.REQUESTORPATIENTID_OLDVALUE, patientID);

			log("###################updatedReqXmlInString" + updatedReqXmlInString);

			log("Updating the xml with Intuit Practice ID");
			updatedReqXmlInString =
					util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SITEID, SmIntegrationConstants.SITEID_OLDVALUE, testData.getPracticeID());

			log("Updating the xml with Intuit Provider ID");
			updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
					testData.getIntuitProviderID());

			log("Updating the xml with Intuit Provider ID");
			updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
					testData.getIntuitProviderID());

			log(" Updating the xml with 'Reply to Message ID");
			updatedReqXmlInString =
					util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_REPLY_TO_MESSAGE_ID, SmIntegrationConstants.REPLY_TO_MESSAGE_VALUE, ppia_id);
			
			int rndnumber = IHGUtil.createRandomNumber();
			log("Random Number" + rndnumber);
			String emailSubject = SmIntegrationConstants.SUBJECT_NEW_APPOINTMENT + " " + rndnumber;
			log("Email Subject#######################################" + emailSubject);

			String emailContent = SmIntegrationConstants.APPOINT_EMAIL_NEW_CONTENT + rndnumber;
			log("Email Subject#######################################" + emailContent);

			log("Updating Email Subject and Content");
			updatedReqXmlInString =
					util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SUBJECT, SmIntegrationConstants.SUBJECT_OLD_APPOINTMENT, emailSubject);
			updatedReqXmlInString =
					util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_EMAILCONTENT, SmIntegrationConstants.APPOINT_EMAIL_OLD_CONTENT, emailContent);
			
			log("updatedReqXmlInString >>>>>>>>>>>>>>>>>>>>>>>>> Before "+updatedReqXmlInString);
			String new_ID= UUID.randomUUID().toString();
			String old_ID = "0906b2b2-e690-459f-82f0-20110426T11:04PM";
			updatedReqXmlInString = util.updateXML(updatedReqXmlInString, "ID", old_ID, new_ID);
			
			log("Updated XML***************" + updatedReqXmlInString);

			log("Posting the request xml and getting the reponse");
			String responseXml = util.postXMLRequest(testData.getEndPointUrl(), updatedReqXmlInString);

			log("Converting the response string to an xml");
			util.writeStringToXml(SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "response.xml", responseXml);

			Thread.sleep(30000);
			log("Step 2 :Getting the ppia status and state value from pgDB");
			HashMap<String, String> dataMapIn = null;
			if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
				dataMapIn = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.APPOINTMENT_REQUEST_INBOUND_PPIA_PPI_ID, testData.getSMDBName(),
						testData.getDBEnv(), testData.getdBUserNameSM(), testData.getDBPasswordSM());
			} else {
				dataMapIn = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.APPOINTMENT_REQUEST_INBOUND_PPIA_PPI_ID, testData.getSMDBName(),
						testData.getDBEnv(), testData.getDBUserName(), testData.getDBPassword());
			}
			if (dataMapIn.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIASTATE_VALUE1)
					&& dataMapIn.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
				BaseTestSoftAssert.verifyTrue(true);
			} else {
				verifyTrue(false, "Failure*** State and Status Values are not as expected");
			}

			log("Step 3 : Write the DB xml to below path");
			util.writeStringToXml(SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "dbresponse.xml", dataMapIn.get(SmIntegrationConstants.PPIA_REQUEST_XML));


			log("Step 4 : get the externalPatientRef value to query the DB");
			String emailSubject_Db_Response =
					SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "dbresponse.xml", "generalMessage", "messageSubject");
			String emailContent_Db_Response =
					SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.APPOINTMENT_REQUEST_PATH + "dbresponse.xml", "generalMessage", "messageBody");

			log("emailSubject from Db_Response####################################" + emailSubject_Db_Response);
			log("emailContent from Db_Response####################################" + emailContent_Db_Response);

			verifyEquals(emailSubject_Db_Response.trim(), emailSubject_Db_Response.trim());
			verifyEquals(emailContent_Db_Response.trim(), emailContent.trim());

			WebDriver driver2 = WebDriverFactory.getWebDriver();
			Thread.sleep(5000);
			log("Log into pateint portal for checking the mail");

			log("step 18: login to patient portal (again)");
			PortalLoginPage login = new PortalLoginPage(driver2, testData.getUrl());
			// MyPatientPage home = login.login(testData.getUserName(), testData.getPassword());
			MyPatientPage home = login.login(userName, password);
			Thread.sleep(8000);
			log("step 19: Go to Inbox");
			MessageCenterInboxPage inboxPage = home.clickViewAllMessagesInMessageCenter();
			Thread.sleep(8000);

			log("step 20: Find message in Inbox");
			MessagePage message = inboxPage.openMessageInInbox(emailSubject_Db_Response);


			log("step 21: Validate message loads and is the right message");
			String actualSubject = message.getPracticeReplyMessageTitle();
			verifyTrue(message.getPracticeReplyMessageTitle().trim().equalsIgnoreCase(emailSubject_Db_Response),
					"Expected subject containting [" + emailSubject_Db_Response + "but actual subject was [" + actualSubject + "]");


			Thread.sleep(10000);
			log("step 23: Logout of Patient Portal");
			home.logout(driver2);


		}

	}


	/**
	 * @Author:- bbinisha,Kiran_GT
	 * @Date:-23/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List steps : Login to patient portal Click on Create Account button Create a new new patient Check the PGDB Integration ID Status Check
	 *                    ppia_status and state in SM DB Check the ppia_response_xml ====================== ====================================== Doesn't work in
	 *                    DEMO,PROD,P10 and QA3 since env is not ready ========== ===================================================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRegistrationOutBoundAndInBound() throws Exception {

		String actualMemberID = "";

		log("Test Case: testPatientRegistrationOutBoundAndInBound");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		SmIntegration smIntegration = new SmIntegration();
		SmIntegrationTestData testcasesData = new SmIntegrationTestData(smIntegration);
		SmIntegrationUtil util = new SmIntegrationUtil(driver);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());
		log("URL: " + testcasesData.getUrl());

		log("step 2:Click Sign-UP");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.getUrl());
		loginpage.signUp();

		BetaCreateNewPatientPage createNewPatientPage = new BetaCreateNewPatientPage(driver);
		log("step 3:Fill detials in Create Account Page");
		// Setting the variables for user in other tests
		String email = PortalUtil.createRandomEmailAddress(testcasesData.getEmail());
		log("email:-" + email);
		MyPatientPage pMyPatientPage = createNewPatientPage.BetaSiteCreateAccountPage(testcasesData.getFirstName(), testcasesData.getLastName(), email,
				testcasesData.getPhoneNumber(), testcasesData.getDobMonth(), testcasesData.getDobDay(), testcasesData.getDobYear(), testcasesData.getZip(),
				testcasesData.getSSN(), testcasesData.getAddress(), testcasesData.getPassword(), testcasesData.getSecretQuestion(), testcasesData.getAnswer(),
				testcasesData.getAddressState(), testcasesData.getAddressCity());

		log("step 5:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		log("step 6:Logout");
		pMyPatientPage.clickLogout(driver);

		log("step 7:Login as new user");
		loginpage.navigateTo(driver, testcasesData.getUrl());
		pMyPatientPage = loginpage.login(email, testcasesData.getPassword());

		log("step 8:Assert Webelements in MyPatientPage");
		assertTrue(pMyPatientPage.isViewallmessagesButtonPresent(driver));

		actualMemberID = SmIntegrationUtil.getMemberIDFromDB(email, testcasesData.getPGDBName(), testcasesData.getDBEnv(), testcasesData.getDBUserName(),
				testcasesData.getDBPassword());

		log("MEMBER ID*****************" + actualMemberID);

		log("Step 9: Verify the member ID from DB");
		BaseTestSoftAssert.verifyFalse(actualMemberID.equals(null), "Member id is not retrieved from DB");

		int integrationId = Integer.parseInt(testcasesData.getIntegrationID().toString());
		Thread.sleep(600000);

		String integrationStatusID = SmIntegrationUtil.getintegrationStatusIDFromDB(integrationId, testcasesData.getPGDBName(), testcasesData.getDBEnv(),
				testcasesData.getDBUserName(), testcasesData.getDBPassword());


		log("Step 10 : Verify whether the integration status id");
		if (integrationStatusID.contains(SmIntegrationConstants.INTEGRATIONSTATUS_ID)) {
		}
		BaseTestSoftAssert.verifyEquals(integrationStatusID, SmIntegrationConstants.INTEGRATIONSTATUS_ID, "Failure***Integration Status ID is not as expected");

		if (IHGUtil.getEnvironmentType().toString().equals("DEV3") || IHGUtil.getEnvironmentType().toString().equals("QA1")) {

			log(" Step 11 : Getting the ppia status and state value from pgDB");
			HashMap<String, String> dataMap = null;
			if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
				dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PPIA_PPI_ID_OUTBOUND, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
						testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
			} else {
				dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PPIA_PPI_ID_OUTBOUND, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
						testcasesData.getDBUserName(), testcasesData.getDBPassword());
			}
			log("Step 12 : Verify whether the ppia status id");
			BaseTestSoftAssert.verifyEquals(dataMap.get(SmIntegrationConstants.PPIA_STATUS), SmIntegrationConstants.PPIA_STATUS_VALUE1,
					"Failure***Integration Status ID is not as expected");

			log("Step 13 : Verify whether the ppia state id");
			if (dataMap.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIA_STATUS_VALUE1)
					&& dataMap.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE1)) {
				// BaseTestSoftAssert.verifyTrue(true);
				BaseTestSoftAssert.verifyEquals(dataMap.get(SmIntegrationConstants.PPIA_STATE), SmIntegrationConstants.PPIASTATE_VALUE1,
						"Failure***Integration Status ID is not as expected");
			} else {
				verifyTrue(false, "Failure*** State Value is not as expected");
			}

			String ppia_id = dataMap.get(SmIntegrationConstants.PPIA_ID_COLUMN);

			String PPIAL_RESPONSE = "";
			if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
				PPIAL_RESPONSE = SmIntegrationUtil.getPPIAReqXMLFromActivityLog(ppia_id, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
						testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
			} else {
				PPIAL_RESPONSE = SmIntegrationUtil.getPPIAReqXMLFromActivityLog(ppia_id, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
						testcasesData.getDBUserName(), testcasesData.getDBPassword());
			}
			log("Step 15 : Verify whether the ppia_id with error message");
			if (PPIAL_RESPONSE.contains(SmIntegrationConstants.PPIAL_RESPONSE_VALUE)) {
				BaseTestSoftAssert.verifyTrue(true);
			} else {
				verifyTrue(false, "Expected Error Not Found");

			}

			log("Step 16 : Verify the memberid value in PPIA_Request_Xml");
			if (dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML).contains(actualMemberID)) {
				BaseTestSoftAssert.verifyTrue(true);
			} else {
				verifyTrue(false, "Member id not Found");
			}

			// log("Step 15 : Verify the email value in PPIA_Request_Xml");
			// String request_xml = dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML);
		}

		if (IHGUtil.getEnvironmentType().toString().equals("DEMO")) {


			log(" Step 11 : Getting the ppia status and state value from pgDB");
			HashMap<String, String> dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PPIA_PPI_ID_OUTBOUND, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getDBUserName(), testcasesData.getDBPassword());

			log("Step 12 : Verify whether the ppia status id");
			BaseTestSoftAssert.verifyEquals(dataMap.get(SmIntegrationConstants.PPIA_STATUS), SmIntegrationConstants.PPIASTATUS_VALUE,
					"Failure***Integration Status ID is not as expected");

			log("Step 13 : Verify whether the ppia state id");
			if (dataMap.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIASTATE_VALUE1)
					&& dataMap.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
				BaseTestSoftAssert.verifyTrue(true);
			} else {
				verifyTrue(false, "Failure*** State Value is not as expected");
			}

			// log("Step 14 : Verify the email value in PPIA_Request_Xml");
			// String request_xml = dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML);
			// String response = SmIntegrationUtil.getPPIAXMLvalue(request_xml, question);
			// verifyTrue(request_xml.contains(email), "The email value in 'ppia_request_xml' is not as expected");

			log("Step 14 : Verify the memberid value in PPIA_Request_Xml");
			if (dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML).contains(actualMemberID)) {
				BaseTestSoftAssert.verifyTrue(true);
			} else {
				verifyTrue(false, "Member id not Found");
			}
		}


		if (IHGUtil.getEnvironmentType().toString().equals("P10INT")) {
			log("P10 Environment is not ready");

		}

		if (IHGUtil.getEnvironmentType().toString().equals("PROD")) {
			log("PROD Environment is not ready");

		}

		if (IHGUtil.getEnvironmentType().toString().equals("QA3")) {
			log("QA3 Environment is not in scope");

		}

		log("##############################################################################################");

		log("========== In bound testcase happens from here======================");

		log("Test Case: testPatientRegistrationInBound");

		log("Path for Registration Inbound XML" + SmIntegrationConstants.PATIENT_REGISTRATION_REQUEST + "Connecthub-Inbound-RegistrationRequestProcessed.xml");

		String inputxmlFilePath = SmIntegrationConstants.PATIENT_REGISTRATION_REQUEST + "Connecthub-Inbound-RegistrationRequestProcessed.xml";

		log("File to string Conversation");
		String inputxml = util.fileToString(inputxmlFilePath);

		log("Updating the xml with patient,practice and provider details");

		log("Updating the xml with ALLSCRIPTPATIENTID");
		String updatedReqXmlInString = util.updateXML(inputxml, SmIntegrationConstants.TAG_ALLSCRIPTPATIENTID, SmIntegrationConstants.ALLSCRIPTPATIENTID_OLDVALUE,
				IHGUtil.createRandomNumericString());

		log("Updating the xml with REQUESTORPATIENTID");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_REQUESTORPATIENTID,
				SmIntegrationConstants.REQUESTORPATIENTID_OLDVALUE, actualMemberID);

		log("Updating the xml with MEMBERID");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_MEMBERID, SmIntegrationConstants.MEMBERID_OLDVALUE, actualMemberID);

		log("Updating the xml with PROVIDERID");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
				testcasesData.getPracticeProviderID());

		log("Updating the xml with Desitination Site ID");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SITEID, SmIntegrationConstants.SITEID_OLDVALUE, testcasesData.getPracticeID());

		log("Request XML #########################\n" + updatedReqXmlInString);

		log("Posting the request xml and getting the reponse");
		String responseXml = util.postXMLRequest(testcasesData.getEndPointUrl(), updatedReqXmlInString);

		log("Converting the response string to an xml");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_REGISTRATION_RESPONSE + "response.xml", responseXml);

		Thread.sleep(150000);
		log(" Step 11 : Getting the ppia status and state value from pgDB");
		HashMap<String, String> dataMap = null;
		if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PPIA_PPI_ID_INBOUND, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
					testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
		} else {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PPIA_PPI_ID_INBOUND, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
					testcasesData.getDBUserName(), testcasesData.getDBPassword());
		}

		log("Step 12 : Verify whether the ppia state id :- At present omitted");
		/*
		 * if(dataMap.get(SmIntegrationConstants.PPIA_STATE).contains( SmIntegrationConstants .PPIASTATE_VALUE1)||dataMap.get(SmIntegrationConstants
		 * .PPIA_STATUS).contains( SmIntegrationConstants.PPIASTATE_VALUE2)) { BaseTestSoftAssert.verifyTrue(true); } else {
		 * verifyTrue(false,"Failure*** State Value is not as expected"); }
		 */
		Thread.sleep(30000);
		log("Step 12 : Verify the member ID in the DB xml");
		// verifyTrue(dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML).contains(util.xmlTagWithValue(SmIntegrationConstants.PPIA_REQUEST_XML,"practicePatientId",
		// actualMemberID)));//this step need to be revisted

		log("Step 13 : Write the DB xml to below path");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_REGISTRATION_RESPONSE + "dbresponse.xml", dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML));

		log("Step 16 : get the externalPatientRef value to query the DB");
		String externalPatientRef = SmIntegrationUtil.findAttributeOfChildNode(SmIntegrationConstants.PATIENT_REGISTRATION_RESPONSE + "dbresponse.xml",
				"patientAdminRequest", "memberEntity", "externalPatientRef");

		log("externalPatientRef================" + externalPatientRef);
		Thread.sleep(30000);
		log("Step 17 : Once the request is success in SM check portal db for external id for our patient");
		String query = "Select * from externalpracticemembermap where externalid='" + externalPatientRef + "';";
		log("query+++++++++++++++++" + query);
		assertTrue(SmIntegrationUtil.getRowCount(testcasesData.getPGDBName(), query, testcasesData.getDBEnv(), testcasesData.getDBUserName(),
				testcasesData.getDBPassword()));
	}

	/**
	 * @Author:- Shanthala
	 * @Date:-28/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List steps : Automation steps listed below Login to PHR Click on 'View All Messages' Check the PGDB Integration ID Status Check the
	 *                    ppia_response_xml ====== ====================================================== Doesn't work in DEMO,PROD,P10 and QA3 since env is not
	 *                    ready ================================================== ===========
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthReminderAndReadReceiptInboundAndOutbound() throws Exception {
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("Test Case: testHealthReminderAndReadReceiptInboundAndOutbound");

		log("step 1: Get Data from Excel");
		SmIntegration smIntegration = new SmIntegration();
		SmIntegrationTestData testcasesData = new SmIntegrationTestData(smIntegration);
		SmIntegrationUtil util = new SmIntegrationUtil(driver);

		log("Path for Health Reminder Inbound XML" + SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "Connecthub-Inbound-HealthReminder.xml");

		String inputxmlFilePath = SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "Connecthub-Inbound-HealthReminder.xml";

		log("step 2:File to string Conversation");
		String inputxml = util.fileToString(inputxmlFilePath);

		log("step 3: Updating the xml with patient,practice and provider details");
		log("Updating Input xml with Patient ID");
		String updatedReqXmlInString =
				util.updateXML(inputxml, SmIntegrationConstants.TAG_MEMBERID, SmIntegrationConstants.MEMBERID_OLDVALUE, testcasesData.getPatientID());

		log("Updating the xml with Intuit Provider ID");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
				testcasesData.getIntuitProviderID());

		log("Updating the xml with Practice ID as destination ID");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SITEID, SmIntegrationConstants.SITEID_OLDVALUE, testcasesData.getPracticeID());

		log("Posting the request xml and getting the reponse");
		String responseXml = util.postXMLRequest(testcasesData.getEndPointUrl(), updatedReqXmlInString);
		log("********************Response XML******************" + responseXml);

		log("Converting the response string to an xml");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "response.xml", responseXml);

		Thread.sleep(30000);
		log("Step 4 :Getting the ppia status and state value from pgDB");
		// select * from practice_partner_integration_activity where
		// ppia_ppi_id=3011 order by ppia_id desc limit 5
		HashMap<String, String> dataMap = null;
		if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTHEALTH_REMINDER_INBOUND_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
		} else {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTHEALTH_REMINDER_INBOUND_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getDBUserName(), testcasesData.getDBPassword());
		}


		if (dataMap.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIASTATE_VALUE1)
				&& dataMap.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
			BaseTestSoftAssert.verifyTrue(true);
		} else {
			verifyTrue(false, "Failure*** State and Status Values are not as expected");
		}

		log("Step 5: Write the DB xml to below path");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "inbounddbresponse.xml",
				dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML));

		log("Step 6: get the staff ID attribure value from the DB response XML");
		String externalStaffID_Db_Response = SmIntegrationUtil
				.findAttributeOfChildNode(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "inbounddbresponse.xml", "generalMessage", "provider", "staffRef");
		log("EXTERNAL STAFF ID" + externalStaffID_Db_Response);

		log("Step 7: get Patient ID attribute value from the DB response XML");
		String patientID_Db_Response = SmIntegrationUtil.findValueOfGrandChildNode(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "inbounddbresponse.xml",
				"generalMessage", "reference");// , "staffRef");
		log("Patient ID" + patientID_Db_Response);

		verifyEquals(externalStaffID_Db_Response.trim(), testcasesData.getIntuitProviderID());
		verifyEquals(patientID_Db_Response.trim(), testcasesData.getPatientID());

		log("=======================OUTBOUND==================");

		log("URL: " + testcasesData.getPhrUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());

		log("step 1:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver, testcasesData.getPhrUrl());

		log("Step 2: Enter patient user name and password");
		PhrHomePage pPhrHomePage = loginpage.login(testcasesData.getUserName(), testcasesData.getPassword());

		log("step 3:Wait for page to be loaded completely");
		verifyTrue(pPhrHomePage.isSearchPageLoaded(), "Expected the PhrHomePage to be loaded, but it was not.");

		log("step 4:Click on View All Messages link PHR home page");
		PhrMessagesPage pPhrMessagesPage = pPhrHomePage.clickOnViewAllMessages();

		Thread.sleep(40000);
		log("step 5:Click on first message in PHR Inbox");

		// As a fixes of DE7482 commented below two lines
		// driver.switchTo().defaultContent();

		// driver.switchTo()
		// .frame(driver.findElement(By
		// .xpath("//div[@id='iframewrapper']/iframe[@id='externalframe']")));

		pPhrMessagesPage.clickOnFirstMessage();
		log("Step 5:Verify PHR Inbox message opened successfully");

		Thread.sleep(30000);
		log("Step 6 :Getting the ppia status and state value from pgDB");
		HashMap<String, String> dataMapOutBound = null;
		if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
			dataMapOutBound = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTHEALTH_REMINDER_OUTBOUND_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
		} else {
			dataMapOutBound = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTHEALTH_REMINDER_OUTBOUND_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getDBUserName(), testcasesData.getDBPassword());
		}

		log("Step 7: Write the DB xml to below path");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "outboundbresponse.xml",
				dataMapOutBound.get(SmIntegrationConstants.PPIA_REQUEST_XML));

		log("Step 8: get the externalPatientRef value to query the DB");
		String healthMessageSubject = SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "outboundbresponse.xml",
				"readReceiptMessage", "messageSubject");
		String healthMessageBody = SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.PATIENT_HEALTH_REMINDER_REQUEST + "outboundbresponse.xml",
				"readReceiptMessage", "messageBody");

		log("Health Message Subject" + healthMessageSubject);
		log("Health Message Body" + healthMessageBody);

		verifyEquals(healthMessageSubject.trim(), SmIntegrationConstants.PATIENTHEALTH_REMINDER_OUTBOUND_MESSAGE_SUBJECT);
		verifyEquals(healthMessageBody.trim(), SmIntegrationConstants.PATIENTHEALTH_REMINDER_OUTBOUND_MESSAGE_BODY);

	}

	/**
	 * @Author:- Kiran_GT
	 * @Date:-30/Aug/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List steps : Use Patient Invite XML.xml file to immulte allscripts patient invite message. Update xml with patient/practice/provider
	 *                    details. Use url and post xml to Intuit Query to SM DB and Verify ppia_request_xml, ppia_status & ppia_state values Query to Portal DB
	 *                    and Verify Patient inactive status ====================================================== ====== Doesn't work in DEMO,PROD,P10 and QA3
	 *                    since env is not ready ========================================== ===================
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInviteInBound() throws Exception {

		log("Test Case: testPatientInviteInBound");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		SmIntegration smIntegration = new SmIntegration();
		SmIntegrationTestData testcasesData = new SmIntegrationTestData(smIntegration);
		SmIntegrationUtil util = new SmIntegrationUtil(driver);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());
		log("URL: " + testcasesData.getUrl());
		log("Path for Patient Invite Inbound XML" + SmIntegrationConstants.PATIENT_INVITE_REQUEST + "Patient Invite XML.xml");

		String inputxmlFilePath = SmIntegrationConstants.PATIENT_INVITE_REQUEST + "Patient Invite XML.xml";

		log("File to string Conversation");
		String inputxml = util.fileToString(inputxmlFilePath);

		log("Updating the xml with patient,practice and provider details");

		log("Updating the xml with ALLSCRIPTPATIENTMRN");
		String updatedReqXmlInString = util.updateXML(inputxml, SmIntegrationConstants.TAG_ALLSCRIPTPATIENTMRN, SmIntegrationConstants.ALLSCRIPTPATIENTID_OLDVALUE,
				IHGUtil.createRandomNumericString());

		log("Updating the xml with SITEID");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SITEID, SmIntegrationConstants.SITEID_OLDVALUE, testcasesData.getPracticeID());

		log("Updating the xml with PROVIDERID");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
				testcasesData.getIntuitProviderID());

		log("Updating the xml with FIRSTNAME");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_FIRSTNAME, SmIntegrationConstants.PATIENTFIRSTNAME_OLDVALUE,
				testcasesData.getFirstName());

		log("Updating the xml with LASTNAME");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_LASTNAME, SmIntegrationConstants.PATIENTLASTNAME_OLDVALUE,
				testcasesData.getLastName());

		log("Updating the xml with EMAIL");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_EMAIL, SmIntegrationConstants.PATIENTEMAIL_OLDVALUE, testcasesData.getEmail());

		log("Updating the xml with ADDRESS");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_ADDRESS, SmIntegrationConstants.PATIENTADDRESS_OLDVALUE, testcasesData.getAddress());

		log("Updating the xml with CITY");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_CITY, SmIntegrationConstants.PATIENTCITY_OLDVALUE, testcasesData.getAddressCity());

		log("Updating the xml with STATE");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_STATE, SmIntegrationConstants.PATIENTSTATE_OLDVALUE,
				SmIntegrationConstants.PATIENTSTATE_NEWVALUE);

		log("Updating the xml with POSTALCODE");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_POSTALCODE, SmIntegrationConstants.PATIENTPOSTALCODE_OLDVALUE, testcasesData.getZip());

		log("Posting the request xml and getting the reponse");
		String responseXml = util.postXMLRequest(testcasesData.getEndPointUrl(), updatedReqXmlInString);

		log("Converting the response string to an xml");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_INVITE_REQUEST + "response.xml", responseXml);

		Thread.sleep(30000);
		log("Step 2 :Getting the ppia status and state value from pgDB");
		HashMap<String, String> dataMap = null;
		if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTINVITE_PPIA_PPI_ID, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
					testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
		} else {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PATIENTINVITE_PPIA_PPI_ID, testcasesData.getSMDBName(), testcasesData.getDBEnv(),
					testcasesData.getDBUserName(), testcasesData.getDBPassword());
		}

		if (dataMap.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIASTATE_VALUE1)
				&& dataMap.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
			BaseTestSoftAssert.verifyTrue(true);
		} else {
			verifyTrue(false, "Failure*** State and Status Values are not as expected");
		}

		log("Step 3 : Write the DB xml to below path");
		util.writeStringToXml(SmIntegrationConstants.PATIENT_INVITE_REQUEST + "dbresponse.xml", dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML));

		log("Step 4 : get the externalPatientRef value to query the DB");
		String externalPatientRef = SmIntegrationUtil.findAttributeOfChildNode(SmIntegrationConstants.PATIENT_INVITE_REQUEST + "dbresponse.xml",
				"patientAdminRequest", "memberEntity", "externalPatientRef");

		log("externalPatientRef================" + externalPatientRef);
		log("Step 5 : Once the request is success in SM check portal db for external id for our patient");
		String query = "select * from memberinactive where emrid='" + externalPatientRef + "';";
		log("query+++++++++++++++++" + query);
		assertTrue(SmIntegrationUtil.getRowCount(testcasesData.getPGDBName(), query, testcasesData.getDBEnv(), testcasesData.getDBUserName(),
				testcasesData.getDBPassword()));

	}

	/**
	 * @Author:- bkrishnankutty
	 * @Date:-2/sep/2013
	 * @User Story ID in Rally : test
	 * @StepsToReproduce: List steps : SM (Service Mediator) Provider Initiated Message (from Allscripts) - INBOUND Use Connecthub-Inbound
	 *                    -GeneralMessage_ProviderInitiated.xml file to immulte allscripts provider initiated message. Update xml with our
	 *                    patient/practice/provider details. For Dev3 use 'http://dev3-portal-sm01.mf.qhg.local:8080/service/hub?wsdl' this url and post your xml
	 *                    to Intuit Query Database select * from practice_partner_integration_activity where ppia_ppi_id=3007 order by ppia_id desc limit 5;
	 *                    Verify ppia_request_xml, ppia_status & ppia_state values for our record. Check xml in ppia_request_xml column for our record Once the
	 *                    request is success in SM check Patient inbox for provider initiated message.
	 * @AreaImpacted :- Description
	 * @throws Exception
	 */

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testProviderInitiatedMessageFromAllscripts() throws Exception {

		log("Test Case: testProviderInitiatedMessageFromAllscripts");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");
		SmIntegration smIntegration = new SmIntegration();
		SmIntegrationTestData testcasesData = new SmIntegrationTestData(smIntegration);
		SmIntegrationUtil util = new SmIntegrationUtil(driver);

		log("URL: " + testcasesData.getUrl());
		log("USER NAME: " + testcasesData.getUserName());
		log("Password: " + testcasesData.getPassword());
		log("URL: " + testcasesData.getUrl());

		String inputxmlFilePath = SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_REQUEST + "Connecthub-Inbound-GeneralMessage_ProviderInitiated.xml";
		log("Path for Patient Invite Inbound XML" + inputxmlFilePath);

		log("File to string Conversation");
		String inputxml = util.fileToString(inputxmlFilePath);

		log("Updating the xml with patient,practice and provider details");
		int rndnumber = IHGUtil.createRandomNumber();
		log("Random Number" + rndnumber);
		String emailSubject = SmIntegrationConstants.SUBJECT_NEWVALUE + rndnumber;
		log("Email Subject#######################################" + emailSubject);
		String emailContent = SmIntegrationConstants.EMAILCONTENT_NEWVALUE + rndnumber;
		log("Email Subject#######################################" + emailContent);

		log("Updating the xml with SITEID");
		String updatedReqXmlInString =
				util.updateXML(inputxml, SmIntegrationConstants.TAG_SITEID, SmIntegrationConstants.SITEID_OLDVALUE, testcasesData.getPracticeID());

		log("Updating the xml with PROVIDERID");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_PROVIDERID, SmIntegrationConstants.PROVIDERID_OLDVALUE,
				testcasesData.getIntuitProviderID());

		log("Updating the xml with MEMBERID");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_MEMBERID, SmIntegrationConstants.MEMBERID_OLDVALUE, testcasesData.getPatientID());

		log("Updating the xml with SUBJECT");
		updatedReqXmlInString = util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_SUBJECT, SmIntegrationConstants.SUBJECT_OLDVALUE, emailSubject);

		log("Updating the xml with EMAILCONTENT");
		updatedReqXmlInString =
				util.updateXML(updatedReqXmlInString, SmIntegrationConstants.TAG_EMAILCONTENT, SmIntegrationConstants.EMAILCONTENT_OLDVALUE, emailContent);

		log("The request XML is############################# \n" + updatedReqXmlInString);

		log("Posting the request xml and getting the reponse");
		String responseXml = util.postXMLRequest(testcasesData.getEndPointUrl(), updatedReqXmlInString);

		log("The response XML is############################# \n" + responseXml);

		log("Converting the response string to an xml");
		util.writeStringToXml(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_RESPONSE + "response.xml", responseXml);

		Thread.sleep(30000);
		log("Step 2 :Getting the ppia status and state value from pgDB");
		HashMap<String, String> dataMap = null;
		if (IHGUtil.getEnvironmentType().toString().equals("QA1")) {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getdBUserNameSM(), testcasesData.getDBPasswordSM());
		} else {
			dataMap = SmIntegrationUtil.getPPIAReqXMLFromDB(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_PPIA_PPI_ID, testcasesData.getSMDBName(),
					testcasesData.getDBEnv(), testcasesData.getDBUserName(), testcasesData.getDBPassword());
		}

		if (dataMap.get(SmIntegrationConstants.PPIA_STATUS).contains(SmIntegrationConstants.PPIASTATE_VALUE1)
				&& dataMap.get(SmIntegrationConstants.PPIA_STATE).contains(SmIntegrationConstants.PPIASTATE_VALUE2)) {
			BaseTestSoftAssert.verifyTrue(true);
		} else {
			verifyTrue(false, "Failure*** State and Status Values are not as expected");
		}

		log("Step 3 : Write the DB xml to below path");
		util.writeStringToXml(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_RESPONSE + "dbresponse.xml", dataMap.get(SmIntegrationConstants.PPIA_REQUEST_XML));

		log("Step 4 : get the externalPatientRef value to query the DB");
		String emailSubject_Db_Response =
				SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_RESPONSE + "dbresponse.xml", "generalMessage", "messageSubject");
		String emailContent_Db_Response =
				SmIntegrationUtil.findValueOfChildNode(SmIntegrationConstants.PROVIDERINITIATEDMESSAGE_RESPONSE + "dbresponse.xml", "generalMessage", "messageBody");

		log("emailSubject from Db_Response####################################" + emailSubject_Db_Response);
		log("emailContent from Db_Response####################################" + emailContent_Db_Response);

		verifyEquals(emailSubject_Db_Response.trim(), emailSubject.trim());
		verifyEquals(emailContent_Db_Response.trim(), emailContent.trim());

		log("Log into pateint portal for checking the mail");

		log("step 18: login to patient portal (again)");
		PortalLoginPage login = new PortalLoginPage(driver, testcasesData.getUrl());
		MyPatientPage home = login.login(testcasesData.getUserName(), testcasesData.getPassword());

		log("step 19: Go to Inbox");
		MessageCenterInboxPage inboxPage = home.clickViewAllMessagesInMessageCenter();
		assertTrue(inboxPage.isInboxLoaded(), "Inbox failed to load properly.");

		log("step 20: Find message in Inbox");
		MessagePage message = inboxPage.openMessageInInbox(emailSubject_Db_Response);

		log("step 21: Validate message loads and is the right message");
		String actualSubject = message.getPracticeReplyMessageTitle();
		assertTrue(message.getPracticeReplyMessageTitle().trim().equalsIgnoreCase(emailSubject_Db_Response),
				"Expected subject containting [" + emailSubject_Db_Response + "but actual subject was [" + actualSubject + "]");

		Thread.sleep(10000);
		log("step 23: Logout of Patient Portal");
		home.logout(driver);

	}

}
