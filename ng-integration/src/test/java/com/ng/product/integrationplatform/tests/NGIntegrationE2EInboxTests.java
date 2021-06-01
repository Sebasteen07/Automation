//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentRequestV2HistoryPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryDetailPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2HistoryListPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page2;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.utils.CommonFlows;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGIntegrationE2EInboxTests extends BaseTestNGWebDriver {

	private PropertyFileLoader propertyLoaderObj;

	int arg_timeOut = 1800;
	NGAPIUtils ngAPIUtils;
	String enterprisebaseURL;
	NGAPIFlows ngAPIFlows;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws Throwable {
		log("Getting Test Data");
		propertyLoaderObj = new PropertyFileLoader();
		ngAPIUtils = new NGAPIUtils(propertyLoaderObj);
		ngAPIFlows = new NGAPIFlows(propertyLoaderObj);
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterprisebaseURL = ngAPIUtils.getRelativeBaseUrl();
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterprisebaseURL = ngAPIUtils.getRelativeBaseUrl();
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMHighPrioritySecureMessageDoNotAddToEncounterSendByPracticeUserReplyByPatient() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Compose Message with High Priority and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "HighPriority", person_id, practiceId, userId,
				providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String rootThreadIdQuery = "select root_thread_id from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);
		String rootThreadId = DBUtils.executeQueryOnDB("NGCoreDB", rootThreadIdQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String replyMessageID = CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), subject, body, comm_id, messageID, integrationPracticeID,
				"SentByPracticeUser", "");
		Thread.sleep(60000);
		CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID, comm_id, rootThreadId.toUpperCase(), "Re: " + subject,
				JalapenoMessagesPage.getPatientReply());
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMReadReceiptSecureMessageNewLockedEncounter() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Compose Message with Read Receipt and send it to enrolled patient with “Add to New Encounter(Locked)” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "ReadReceiptRequested", person_id, practiceId,
				userId, providerName, locationName, "EHR", "NewLockedEncounter", "PracticeUser", "", "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "ReadReceiptRequested");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageID, integrationPracticeID, "ReadReceiptRequested", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMUnreadNotificationSecureMessageNewUnLockedEncounter() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Compose Message with Unread Notification and send it to enrolled patient with “Add to New Encounter(UnLocked)” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.postSecureMessage(propertyLoaderObj, "UnreadNotificationRequested", person_id, practiceId, userId,
				providerName, locationName, "EHR", "NewUnLockedEncounter", "PracticeUser", "", "", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMDisableReplySecureMessageExistingEncounter() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, person_id);

		logStep("Compose Message with Cann't Reply and send it to enrolled patient with “Existing Encounter” option selected in Send & Chart button.");
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "DisableReply", person_id, practiceId, userId,
				providerName, locationName, "EHR", "ExistingEncounter", "PracticeUser", encounter_id, "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageID, integrationPracticeID, "CannotReply", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSecureMessageSentAndReplyUsingOnlineProfile() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Compose Message and send it to enrolled patient by selecting Online Profile as sender of message.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);

		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "SentByOnlineProfile", person_id, practiceId,
				userId, providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String rootThreadIdQuery = "select root_thread_id from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);
		String rootThreadId = DBUtils.executeQueryOnDB("NGCoreDB", rootThreadIdQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String replyMessageID = CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), subject, body, comm_id, messageID, integrationPracticeID,
				"SentByOnlineProfile", "");
		Thread.sleep(60000);
		CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID, comm_id, rootThreadId.toUpperCase(), "Re: " + subject,
				JalapenoMessagesPage.getPatientReply());
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSecureMessageSentAndReplyUsingAliasName() throws Throwable {
		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String userLocationName = propertyLoaderObj.getProperty("PortalLocationName");

		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		NGLoginPage loginPage = new NGLoginPage(driver, propertyLoaderObj.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(propertyLoaderObj.getProperty("CCDAUsername"),
				propertyLoaderObj.getPassword());

		logStep("Click Ask A Question tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillAndContinue(askaSubject, questionText, userProviderName,
				userLocationName);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + person_id + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + person_id + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;
		Thread.sleep(60000);
		Log4jUtil.log("Verify message received at NG core");
		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("askAV2Name"));

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "ReplyToASKAUsingAliasName" + messageID,
				person_id, practiceId, userId, providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser",
				"", "", "");

		Thread.sleep(60000);
		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String replyMessageID = CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), subject, body, comm_id, messageIDAtMF, integrationPracticeID,
				"SentByAlias", "");

		Thread.sleep(60000);
		CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID, comm_id, messageID.toUpperCase(), "Re: " + subject,
				JalapenoMessagesPage.getPatientReply());
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMAskAQuestionRepliedUsingOriginalUnlockedEncounter() throws Throwable {
		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		DBUtils.executeQueryOnDB("NGCoreDB", "select first_name from user_mstr where user_id='" + userId + "'");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String userLocationName = propertyLoaderObj.getProperty("PortalLocationName");

		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		NGLoginPage loginPage = new NGLoginPage(driver, propertyLoaderObj.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(propertyLoaderObj.getProperty("CCDAUsername"),
				propertyLoaderObj.getPassword());

		logStep("Click Ask A Question tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillAndContinue(askaSubject, questionText, userProviderName,
				userLocationName);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + person_id + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + person_id + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;
		Thread.sleep(60000);
		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("askAV2Name"));

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "ReplyToPortal" + messageID, person_id,
				practiceId, userId, providerName, locationName, "EHR", "OriginalUnlockedEncounter", "PracticeUser", "",
				"", "");

		Thread.sleep(60000);
		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageIDAtMF, integrationPracticeID, "", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSendSecureMessageDoNotAddToChart() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Compose Message with Delayed Delivery and send it to enrolled patient with “Do Not Add To Chart” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "", person_id, practiceId, userId,
				providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageID, integrationPracticeID, "", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxSendAppointmentRequestBookAppointmentSendResponse() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String eventName = propertyLoaderObj.getProperty("EventName");
		String resourceName = propertyLoaderObj.getProperty("ResourceName");

		String updateToEPMCategoryQuery = "update service_setting set value = 'EPM' where [key] = 'epmcategory' and practice_id = (select id from practice where extpracticeid ='"
				+ practiceId + "')";
		DBUtils.executeQueryOnDB("MFAgentDB", updateToEPMCategoryQuery);

		String apptTime = "08:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery = "select top 1 delete_ind from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB", deleteINDQuery);
		String apptIDQuery = "select top 1 appt_id from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "' order by create_timestamp desc";

		if (deleteIND.equalsIgnoreCase("N")) {
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB", apptIDQuery));
		}

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String appointmentReason = "Illness" + System.currentTimeMillis();

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		logStep("Navigate to appointment page");
		homePage.clickOnAppointmentV3(driver);

		logStep("Request for appointment");
		// workaround for extra appointments list when multiple appointments solutions
		// are on
		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");// go on assuming we didn't find the extra page and button
		}

		NGAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver,
				NGAppointmentRequestV2Step1.class);

		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseProvider(propertyLoaderObj.getProperty("ProviderName"));

		logStep("Continue to step 2: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		try {
			homePage.clickOnAppointmentV3(driver);
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}

		Thread.sleep(5000);
		NGAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check appointment request reason");
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetAppointment").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String appointmentID = RestUtils.isAppointmentReasonResponseXMLValid(propertyLoaderObj.getResponsePath(),
				appointmentReason);

		logStep("Verify appointment request is reached to EPM/EHR Inbox");
		CommonFlows.verifyAppointmentRequestReceived(appointmentID, appointmentReason,
				propertyLoaderObj.getProperty("AppointmentStartTime"),
				propertyLoaderObj.getProperty("AppointmentEndTime"), propertyLoaderObj.getProperty("AppointmentDays"),
				practiceId);

		DBUtils.executeQueryOnDB("MFAgentDB", updateToEPMCategoryQuery);
		logStep("Schedule an appointment for Patient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String epmAppointmenttId = NGAPIFlows.postAppointment(person_id, practiceId, locationName, providerName,
				eventName, resourceName, 2, apptTime, 201);

		DBUtils.executeQueryOnDB("MFAgentDB", updateToEPMCategoryQuery);
		String appointmentResponse = "ApptResponse" + System.currentTimeMillis();
		logStep("Send appointment response to Patient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.postAppointmentResponse(appointmentID, epmAppointmenttId, appointmentResponse, apptTime, 2);

		logStep("Verify appointment is booked and response is captured");
		CommonFlows.verifyAppointmentBookedResponseCaptured(appointmentID, appointmentResponse);

		logStep("Verify processing status of appointment");
		CommonFlows.verifyAppointmentProcessingStatus(propertyLoaderObj, appointmentID, integrationPracticeID);

		String appointmentDate = DBUtils.executeQueryOnDB("NGCoreDB",
				"select appt_date from appointments where appt_id ='" + epmAppointmenttId + "'");
		appointmentDate = appointmentDate.substring(0, 4) + "/" + appointmentDate.substring(4, 6) + "/"
				+ appointmentDate.substring(6);

		appointmentDate = appointmentDate.replaceAll("/", "-") + "T" + apptTime + "Z";
		Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(appointmentDate);
		log("Appointment Date is " + new SimpleDateFormat("M/d/yy hh:mm").format(date));
//		appointmentDate = CommonUtils.changeESTtoIST(new SimpleDateFormat("M/d/yy hh:mm").format(date));
		appointmentDate = new SimpleDateFormat("M/d/yy hh:mm").format(date);
		log("Expected appointment Date is " + appointmentDate.substring(0, appointmentDate.lastIndexOf(" ")));
		String expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ") + 1);

		if (expectedTime.startsWith("0"))
			expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ") + 2);
		log("Expected appointment Time is " + expectedTime);

		Thread.sleep(60000);
		CommonFlows.verifyAppointmentReceivedinPortal(propertyLoaderObj, driver, url, username,
				appointmentDate.substring(0, appointmentDate.lastIndexOf(" ")), expectedTime, appointmentResponse);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointmentSameSlot() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String eventName = propertyLoaderObj.getProperty("EventName");
		String resourceName = propertyLoaderObj.getProperty("ResourceName");

		String apptTime = "09:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery = "select top 1 delete_ind from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB", deleteINDQuery);
		String apptIDQuery = "select top 1 appt_id from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "' order by create_timestamp desc";

		if (deleteIND.equalsIgnoreCase("N")) {
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB", apptIDQuery));
		}

		logStep("Schedule an appointment for Patient to same slot");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.postAppointment(person_id, practiceId, locationName, providerName, eventName, resourceName, 2,
				apptTime, 201);
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.postAppointment(person_id, practiceId, locationName, providerName, eventName, resourceName, 2,
				apptTime, 400);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointmentBookMultipleSlotsandDeleteAppointmentVerifyInPortal() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String eventName = propertyLoaderObj.getProperty("EventName");
		String resourceName = propertyLoaderObj.getProperty("ResourceName");

		String apptTime = "10:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery = "select top 1 delete_ind from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB", deleteINDQuery);
		String apptIDQuery = "select top 1 appt_id from appointments where person_id ='" + person_id
				+ "' and begintime ='" + begintime + "' and practice_id ='" + practiceId
				+ "' order by create_timestamp desc";

		if (deleteIND.equalsIgnoreCase("N")) {
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB", apptIDQuery));
		}

		String updateToEPMCategoryQuery = "update service_setting set value = 'EPM' where [key] = 'epmcategory' and practice_id = (select id from practice where extpracticeid ='"
				+ practiceId + "')";
		DBUtils.executeQueryOnDB("MFAgentDB", updateToEPMCategoryQuery);

		logStep("Schedule another appointment for Patient to different slot");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String epmAppointmenttId = NGAPIFlows.postAppointment(person_id, practiceId, locationName, providerName,
				eventName, resourceName, 1, apptTime, 201);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Verify processing status of appointment");
		CommonFlows.verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, epmAppointmenttId,
				integrationPracticeID, "Appointment");

		String appointmentDate = DBUtils.executeQueryOnDB("NGCoreDB",
				"select appt_date from appointments where appt_id ='" + epmAppointmenttId + "'");
		appointmentDate = appointmentDate.substring(0, 4) + "/" + appointmentDate.substring(4, 6) + "/"
				+ appointmentDate.substring(6);

		appointmentDate = appointmentDate.replaceAll("/", "-") + "T" + apptTime + "Z";
		Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(appointmentDate);
		log("Appointment Date is " + new SimpleDateFormat("M/d/yy hh:mm").format(date));
//		appointmentDate = CommonUtils.changeESTtoIST(new SimpleDateFormat("M/d/yy hh:mm").format(date));
		appointmentDate = new SimpleDateFormat("M/d/yy hh:mm").format(date);
		log("Expected appointment Date is " + appointmentDate.substring(0, appointmentDate.lastIndexOf(" ")));
		String expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ") + 1);

		if (expectedTime.startsWith("0"))
			expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ") + 2);
		log("Expected appointment Time is " + expectedTime);

		logStep("Verfiy appointment is received in Portal");
		Thread.sleep(80000);
		CommonFlows.verifyAppointmentReceivedinPortal(propertyLoaderObj, driver, url, username,
				appointmentDate.substring(0, appointmentDate.lastIndexOf(" ")), expectedTime, "");

		logStep("Delete booked appointment from EPM Appointment Book having appointment ID " + epmAppointmenttId);
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.deleteAppointment(epmAppointmenttId);

		logStep("Verify appointment is deleted from EPM Appointment Book");
		String deleteInd = DBUtils.executeQueryOnDB("NGCoreDB",
				"select delete_ind from appointments where appt_id ='" + epmAppointmenttId + "'");
		CommonUtils.VerifyTwoValues(deleteInd, "equals", "Y");
		logStep("Appointment is deleted from EPM Appointment Book with delete Indicator " + deleteInd);

		Thread.sleep(90000);
		logStep("Verify appointment is deleted from Portal");
		CommonFlows.verifyAppointmentDeletedinPortal(propertyLoaderObj, driver, url, username,
				appointmentDate.substring(0, appointmentDate.lastIndexOf(" ")), expectedTime);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP138SendPatientEducationByCommunicationMessage() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String peDocumentId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select top 1 document_id from patient_education where practice_id ='" + practiceId
						+ "' and person_id ='" + person_id + "'");

		logStep("Compose Message with Patient Education Document and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "", person_id, practiceId, userId,
				providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "PatientEducation",
				peDocumentId);

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		String attachmentName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select document_desc from patient_education where document_id ='" + peDocumentId + "'");
		Thread.sleep(15000);
		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageID, integrationPracticeID, "messageWithPE", attachmentName);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxPrescription() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, person_id);

		logStep("Add Diagnosis to created encounter");
		NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);

		logStep("Add Medication to created encounter");
		String medication_id = NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", person_id,
				encounter_id, "R07.9", 286939);

		logStep("Verify processing status of medication");
		CommonFlows.verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(propertyLoaderObj, medication_id,
				integrationPracticeID, "Medication");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestPrescriptionRenewalApprovedbyPracticeUser() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("SingleGuarantorUser");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = propertyLoaderObj.getProperty("MedicationToRenew");

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);

		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver,
				propertyLoaderObj.getProperty("PortalLocationName"),
				propertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForPrescriptionRenewal(driver, prescritonRenewalRequestReason,
				medicationToRenew);

		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(propertyLoaderObj.getResponsePath(),
				prescritonRenewalRequestReason);

		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID,
				"[#1:" + prescritonRenewalRequestReason + "]", practiceId);

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String prescritonRenewalResponse = "PrescriptionRenewalResponse" + System.currentTimeMillis();
		logStep("Approve the Prescription Renewal Request by Practice User");
		NGAPIFlows.putPrescriptionRenewalRequest(person_id, prescriptionID, prescritonRenewalResponse,
				"NewLockedEncounter", locationName, providerName, "Accepted", 200);
		log("The Prescription Renewal Request is approved by Practice User");

		Thread.sleep(90000);
		logStep("Log into Portal");
		loginPage = new NGLoginPage(driver, url);
		homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Navigate to Messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Verify Prescription Renewal Message is received");
		assertTrue(messagesPage.isMessageDisplayed(driver, propertyLoaderObj.getProperty("PrescriptionSubject")));

		messagesPage.OpenMessage(driver, "Prescription Renewal Request");

		logStep("Verify PrescritonRenewalResponse added by Practice is displayed in Portal");
		messagesPage.verifyPrescriptionResponse(driver, prescritonRenewalResponse);

		logStep("Verify medication status is approved");
		messagesPage.verifyMedicationStatus(driver, "Approved");

		messagesPage.archiveMessage();
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestPrescriptionRenewalRejectedbyPracticeUser() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("SingleGuarantorUser");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = propertyLoaderObj.getProperty("MedicationToRenew");

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);

		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver,
				propertyLoaderObj.getProperty("PortalLocationName"),
				propertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForPrescriptionRenewal(driver, prescritonRenewalRequestReason,
				medicationToRenew);

		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(propertyLoaderObj.getResponsePath(),
				prescritonRenewalRequestReason);

		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID,
				"[#1:" + prescritonRenewalRequestReason + "]", practiceId);

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String prescritonRenewalResponse = "PrescriptionRenewalResponse" + System.currentTimeMillis();
		logStep("Reject the Prescription Renewal Request by Practice User");
		NGAPIFlows.putPrescriptionRenewalRequest(person_id, prescriptionID, prescritonRenewalResponse,
				"NewLockedEncounter", locationName, providerName, "Rejected", 200);
		log("The Prescription Renewal Request is rejected by Practice User");

		Thread.sleep(90000);
		logStep("Log into Portal");
		loginPage = new NGLoginPage(driver, url);
		homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Navigate to Messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Verify Prescription Renewal Message is received");
		assertTrue(messagesPage.isMessageDisplayed(driver, propertyLoaderObj.getProperty("PrescriptionSubject")));

		messagesPage.OpenMessage(driver, "Prescription Renewal Request");

		logStep("Verify PrescritonRenewalResponse added by Practice is displayed in Portal");
		messagesPage.verifyPrescriptionResponse(driver, prescritonRenewalResponse);

		messagesPage.archiveMessage();
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMBulkSendMessageWithNormalPriorityUnReadDisbaleReplyDoNotAddToEncounterSendByPracticeUser()
			throws Throwable {
		logStep("Getting Existing User");
		String person_id = propertyLoaderObj.getProperty("PersonId1");
		String username = DBUtils.executeQueryOnDB("NGCoreDB",
				"select email_address from person where person_id = '" + person_id + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			userId = propertyLoaderObj.getProperty("SecureMessageUserID");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		List<String> personIdList = new ArrayList<String>();
		personIdList.add(person_id);
		personIdList.add(propertyLoaderObj.getProperty("PersonId2"));

		logStep("Compose message with normal priority, unread notification, disable reply and send it to more than one recipient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postBulkSecureMessage(propertyLoaderObj, "Bulk1", personIdList, practiceId, userId,
				providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "", "");

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "UnReadRequested");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getProperty("BulkPassword"), subject, body, comm_id, messageID, integrationPracticeID,
				"BulkCannotReply", "");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestMultiplePrescriptionRenewal() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("SingleGuarantorUser");
		DBUtils.executeQueryOnDB("NGCoreDB", "select person_id from person where email_address = '" + username + "'");
		String practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			propertyLoaderObj.getProperty("NGE1P1Provider");
			propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			propertyLoaderObj.getProperty("EPMProviderName");
			propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = propertyLoaderObj.getProperty("MedicationToRenew");

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);

		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver,
				propertyLoaderObj.getProperty("PortalLocationName"),
				propertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForMultiplePrescriptionRenewal(driver, prescritonRenewalRequestReason,
				medicationToRenew, propertyLoaderObj.getProperty("SecondMedicationToRenew"));

		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(propertyLoaderObj.getResponsePath(),
				prescritonRenewalRequestReason);

		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID,
				"[#1:" + prescritonRenewalRequestReason + "][#2:" + prescritonRenewalRequestReason + "]", practiceId);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP19SendAttachmentByASKAReplyByPracticeUser() throws Throwable {
		String expectedCorrectFileText = "sw-test-academy.txt";

		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = propertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = propertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = propertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = propertyLoaderObj.getProperty("NGMainPracticeID");
			providerName = propertyLoaderObj.getProperty("EPMProviderName");
			locationName = propertyLoaderObj.getProperty("EPMLocationName");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String errorfilePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\File_Attachment\\Error_Files_Testing.pdf";
		String correctfilePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\File_Attachment\\sw-test-academy.txt";

		Log4jUtil.log("CorrectfilePath " + correctfilePath);
		String userLocationName = propertyLoaderObj.getProperty("PortalLocationName");
		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillASKADetails(askaSubject, questionText, userProviderName,
				userLocationName);

		logStep("Add Attachment and remove Attachment ");
		askPage1.uploadFileWithRobotRepeat(errorfilePath, correctfilePath);

		logStep("Remove All the Attachment Except one and click on continue button ");
		askPage1.removeAttachment();

		logStep("Verify Uploaded file name in submit page ");
		assertTrue(expectedCorrectFileText.equals(askPage1.getProperFileText()),
				"Expected: " + expectedCorrectFileText + ", found: " + askPage1.getProperFileText());

		logStep("Verify Subject in submit page ");
		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());

		logStep("Verify Quesion in Submit paget ");
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());
		homePage = askPage2.submit();

		logStep("Go back to the aska and check question history");
		askPage1 = homePage.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));
		JalapenoAskAStaffV2HistoryListPage askHistoryList = askPage1.clickOnHistory();

		logStep("Find history entry by subject/reason and navigate to detail");
		JalapenoAskAStaffV2HistoryDetailPage askHistoryDetail = askHistoryList.goToDetailByReason(askaSubject);

		logStep("Verify the subject and question in history detail match submission");
		assertTrue(askaSubject.equals(askHistoryDetail.getRequestDetailSubject()),
				"Expected: " + askaSubject + ", found: " + askHistoryDetail.getRequestDetailSubject());
		assertTrue(questionText.equals(askHistoryDetail.getRequestDetailQuestion()),
				"Expected: " + questionText + ", found: " + askHistoryDetail.getRequestDetailQuestion());
		assertTrue("Open".equals(askHistoryDetail.getRequestDetailStatus()),
				"Expected: Open" + ", found: " + askHistoryDetail.getRequestDetailStatus());

		assertTrue(expectedCorrectFileText.equals(askHistoryDetail.getRequestAttachedFile()),
				"Expected: " + expectedCorrectFileText + ", found: " + askHistoryDetail.getRequestAttachedFile());

		logStep("Logout patient");
		askHistoryDetail.clickOnLogout();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		else
			Log4jUtil.log("Invalid Execution Mode");

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + person_id + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + person_id + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;

		Thread.sleep(60000);

		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("askAV2Name"));

		CommonFlows.verifyAttachmentReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "txt ",
				expectedCorrectFileText.substring(0, expectedCorrectFileText.lastIndexOf(".")));

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, person_id);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "ReplyToPortal" + messageID, person_id,
				practiceId, userId, providerName, locationName, "EHR", "ExistingEncounter", "PracticeUser",
				encounter_id, "", "");

		Thread.sleep(60000);
		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		else
			Log4jUtil.log("Invalid Execution Mode");

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageIDAtMF, integrationPracticeID, "", "");
		log("Test Case End: The patient is able to ask a question with attachment and practice user is able to reply to that message.");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP19SendDifferentTypeOfAttachments() throws Throwable {
		String expectedCorrectFileText1 = "TIFFImage.tiff";
		String expectedCorrectFileText2 = "SampleDoc.docx";
		String expectedCorrectFileText3 = "SamplePDF.pdf";
		String expectedCorrectFileText4 = "PNGImage.png";

		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {

			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		// .jpeg, .tif, .doc
		String tiffFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\TIFFImage.tiff";
		String docFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\SampleDoc.docx";
		String pdfFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\SamplePDF.pdf";
		String pngFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\PNGImage.png";

		String userLocationName = propertyLoaderObj.getProperty("PortalLocationName");
		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		askPage1.NGfillASKADetails(askaSubject, questionText, userProviderName, userLocationName);

		logStep("Add Attachments of Tiff, Doc, PDF, PNG types");
		askPage1.uploadFile(tiffFile, tiffFile.substring(tiffFile.lastIndexOf("\\") + 1));
		askPage1.uploadFile(docFile, docFile.substring(docFile.lastIndexOf("\\") + 1));
		askPage1.uploadFile(pdfFile, pdfFile.substring(pdfFile.lastIndexOf("\\") + 1));
		askPage1.uploadFile(pngFile, pngFile.substring(pngFile.lastIndexOf("\\") + 1));

		JalapenoAskAStaffV2Page2 askPage2 = askPage1.clickContinue();
		logStep("Verify Subject in submit page");
		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());

		logStep("Verify Quesion in Submit page");
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());
		homePage = askPage2.submit();

		logStep("Logout patient");
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		else
			Log4jUtil.log("Invalid Execution Mode");

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + person_id + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + person_id + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;

		Thread.sleep(60000);
		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("askAV2Name"));

		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "tiff",
				expectedCorrectFileText1.substring(0, expectedCorrectFileText1.lastIndexOf(".")));
		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "pdf ",
				expectedCorrectFileText2.substring(0, expectedCorrectFileText2.lastIndexOf(".")));
		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "pdf ",
				expectedCorrectFileText3.substring(0, expectedCorrectFileText3.lastIndexOf(".")));
		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "png ",
				expectedCorrectFileText4.substring(0, expectedCorrectFileText4.lastIndexOf(".")));
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP19Send2MBAttachments() throws Throwable {
		String expectedCorrectFileText1 = "1.7MB File.txt";
		String expectedCorrectFileText2 = "BMPImage.bmp";
		String expectedCorrectFileText3 = "JPGImage.jpg";

		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("SecureMessageUserID");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("CCDAUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			integrationPracticeID = propertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = propertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String jpgFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\JPGImage.jpg";
		String bmpFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\BMPImage.bmp";
		String txtFile = System.getProperty("user.dir") + "\\src\\test\\resources\\File_Attachment\\1.7MB File.txt";

		String userLocationName = propertyLoaderObj.getProperty("PortalLocationName");
		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Click Ask A Staff tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		askPage1.NGfillASKADetails(askaSubject, questionText, userProviderName, userLocationName);

		logStep("Add Attachments of JPG, BMP, txt types of 2 MB");
		askPage1.uploadFile(jpgFile, jpgFile.substring(jpgFile.lastIndexOf("\\") + 1));
		askPage1.uploadFile(bmpFile, bmpFile.substring(bmpFile.lastIndexOf("\\") + 1));
		askPage1.uploadFile(txtFile, txtFile.substring(txtFile.lastIndexOf("\\") + 1));

		JalapenoAskAStaffV2Page2 askPage2 = askPage1.clickContinue();
		logStep("Verify Subject in submit page");
		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());

		logStep("Verify Quesion in Submit page");
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());
		homePage = askPage2.submit();

		logStep("Logout patient");
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oAuthUsername1"),
					propertyLoaderObj.getProperty("oAuthPassword1"));
		else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT"))
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		else
			Log4jUtil.log("Invalid Execution Mode");

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + person_id + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + person_id + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practiceName") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;

		Thread.sleep(60000);
		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("askAV2Name"));

		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "txt ",
				expectedCorrectFileText1.substring(0, expectedCorrectFileText1.lastIndexOf(".")));
		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "bmp ",
				expectedCorrectFileText2.substring(0, expectedCorrectFileText2.lastIndexOf(".")));
		CommonFlows.verifyMultipleAttachmentsReceivedInMessageAtNGCore(propertyLoaderObj, messageID, "jpg ",
				expectedCorrectFileText3.substring(0, expectedCorrectFileText3.lastIndexOf(".")));
	}

}
