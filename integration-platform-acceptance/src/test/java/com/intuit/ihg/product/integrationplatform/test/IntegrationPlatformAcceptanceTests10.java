package com.intuit.ihg.product.integrationplatform.test;


import static org.testng.Assert.assertTrue;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.ibm.icu.util.Calendar;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10TestData;
import com.intuit.ihg.product.integrationplatform.utils.OauthUtils;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;



/**
 * @author Vasudeo P
 * @Date 23/Dec/2014
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests10 {



	static Oauth10TestData testData = null;
	String payload = null;
	String processingUrl = null;
	Long sinceTime = null;
	Long sixMonthAgo = null;
	long epoch = System.currentTimeMillis() / 1000;

	/*
	 * Read test-data from excel File
	 */
	@BeforeClass
	public void setOauth10() throws Exception {
		Oauth10 Oauth10Data = new Oauth10();
		testData = new Oauth10TestData(Oauth10Data);

		Log4jUtil.log("Common Path: " + testData.getCommonPath());
		Log4jUtil.log("OAuthProperty: " + testData.getOAuthProperty());
		Log4jUtil.log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		Log4jUtil.log("OAuthAppToken: " + testData.getOAuthAppToken());
		Log4jUtil.log("OAuthUsername: " + testData.getOAuthUsername());
		Log4jUtil.log("OAuthPassword: " + testData.getOAuthPassword());

		Calendar c = Calendar.getInstance();
		c.add(Calendar.WEEK_OF_MONTH, -24);
		sixMonthAgo = c.getTime().getTime();
		sinceTime = sixMonthAgo / 1000;



	}

	/*
	 * Set Oauth1.0 connection
	 */

	public void setConnection() throws IOException {
		Log4jUtil.log("Step 1: Setup Oauth client 1.0");
		assertTrue(OauthUtils.oauthSetup1O(testData.getOAuthKeyStore(), testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(),
				testData.getOAuthPassword()), "Oauth Connection Failed");

	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDC() throws Exception {

		Log4jUtil.log("TestPIDC covers Post and Get PIDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		String practicePatientId = "Patient" + sinceTime;
		String firstName = "Name" + sinceTime;
		String lastName = "TestPatient1" + sinceTime;
		String email = "vasudeo.parab" + sinceTime + "@tejora.com";
		payload = OauthUtils.preparePatient(testData.getCommonPath() + "/patient_Oauth10.xml", practicePatientId, firstName, lastName, email, null);

		Log4jUtil.log("Step 2: Do a POST call and get processing status URL");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getPatientRestURL(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);
		checkProcessingUrlStatus();
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPatientRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDC() throws Exception {

		Log4jUtil.log("TestAMDC covers Post and Get AMDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		payload =
				OauthUtils.prepareSecureMessage(testData.getCommonPath() + "/secureMessage.xml", testData.getFrom(), testData.getUserName(), "Test " + sinceTime, null);

		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getAMDCRestURL(), payload, testData.getCommonPath() + "/response.xml");



		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getAMDCRestURL() + "?since=" + epoch + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment() throws Exception {

		Log4jUtil.log("TestAppointment covers Post and Get Appointment with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		payload = OauthUtils.convertXMLFileToString(testData.getCommonPath() + "/PostAppointment.xml");

		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getAppointmentRestURL(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);

		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getAppointmentRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCCD() throws Exception {

		Log4jUtil.log("TestPostCCD covers Post EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		payload = OauthUtils.prepareCCD(testData.getCommonPath() + "/CCD_Oauth10.xml");

		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getEHDCRestURL(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetReadCommunication() throws Exception {

		Log4jUtil.log("TestGetReadCommunication covers Get ReadCommunication with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCCDExchangeBatch() throws Exception {

		Log4jUtil.log("TestGetCCDExchangeBatch covers Get EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getccdExchangeBatch() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescription() throws Exception {

		Log4jUtil.log("TestPrescription covers Post and Get Prescription with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		payload = OauthUtils.convertXMLFileToString(testData.getCommonPath() + "/post_rx_payload.xml");

		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getPrescriptionRestURL(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);

		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPrescriptionRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetEvents() throws Exception {

		Log4jUtil.log("TestGetEvents covers Pull Events with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPullEventsURL() + "&sinceTime=" + sixMonthAgo + "&maxEvents=400", testData.getCommonPath() + "/response.xml");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPayments() throws Exception {

		Log4jUtil.log("TestGetPayments covers payments with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPaymentURL() + "?sinceTime=" + "1419233246000", testData.getCommonPath() + "/response.xml");

	}
	
	public void checkProcessingUrlStatus() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
		boolean completed = false;
		for (int i = 0; i < 3; i++) {
			// wait 10 seconds so the message can be processed
			Thread.sleep(100000);
			RestUtils.setupHttpGetRequest(processingUrl, testData.getCommonPath() + "/response.xml");
			if (RestUtils.isMessageProcessingCompleted(testData.getCommonPath() + "/response.xml")) {
				completed = true;
				break;
			}
		}
		assertTrue(completed, "Message processing was not completed in time");
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCcdExchangePdfBatch() throws Exception {

		Log4jUtil.log("TestGetCcdExchangePdfBatch covers CcdExchangePdfBatch API call with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();

		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getCcdExchangePdfBatchURL() + "?sinceTime=" + "1419233246000", testData.getCommonPath() + "/response.xml");

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCcdExchangePdf() throws Exception {

		Log4jUtil.log("TestGetCcdExchangePdf covers CcdExchangePdf API call with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		Log4jUtil.log("Step 2: Do a Get call " + testData.getCcdExchangePdfURL());
		OauthUtils.setupHttpGetRequest(testData.getCcdExchangePdfURL() , testData.getCommonPath() + "/response.xml");

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDCV2() throws Exception {

		Log4jUtil.log("TestPIDCV2 covers Post and Get PIDC v2 with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		String practicePatientId = "Patient" + sinceTime;
		String firstName = "Name" + sinceTime;
		String lastName = "TestPatient1" + sinceTime;
		String email = "Test" + sinceTime + "@mailinator.com";
		payload = OauthUtils.preparePatient(testData.getCommonPath() + "/patient_Oauth10.xml", practicePatientId, firstName, lastName, email, null);
		payload = payload.replaceAll("patient/v1", "patient/v2");
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL");
		String postPidcV2RestUrl = testData.getPatientRestURL();
		postPidcV2RestUrl = postPidcV2RestUrl.replaceAll("v1", "v2");
		if(IHGUtil.getEnvironmentType().toString().equalsIgnoreCase("DEV3")) {
			postPidcV2RestUrl = postPidcV2RestUrl.replaceAll("https://dev3-int.dev.medfusion.net", "http://d3-pp-aapp07.dev.medfusion.net:8080/rwsdk");
		}		
		Log4jUtil.log("postPidcV2 " + postPidcV2RestUrl);
		processingUrl = OauthUtils.setupHttpPostRequest(postPidcV2RestUrl, payload, testData.getCommonPath() + "/response.xml");
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPatientRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath() + "/response.xml");
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPayNow() throws Exception {

		Log4jUtil.log("TestGetPayNow covers Pay Now API with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		Log4jUtil.log("Step 2: Do a Get call ");
		OauthUtils.setupHttpGetRequest(testData.getPayments()+ "=payNowpayment" + "&since=" + "1419233246000" , testData.getCommonPath() + "/response.xml");

	}
	
	@Test(enabled = false, groups = {"P2P"}, retryAnalyzer = RetryAnalyzer.class)
	public void testGetDirectMessageStatus() throws Exception {

		Log4jUtil.log("TestGetDirectMessageStatus covers DirectMessageStatus API with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		Log4jUtil.log("Step 2: Do a Get call ");
		OauthUtils.setupHttpGetRequest(testData.getDirectMessageStatus() , testData.getCommonPath() + "/response.xml");
	}
	
	@Test(enabled = false, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPostStatement() throws Exception {

		Log4jUtil.log("TestPostStatement covers Post of statements API with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/statement.xml");
		payload = payload.replaceAll("--id--", IHGUtil.createRandomNumericString(12));
		payload = payload.replaceAll("--patient_id--", testData.getUserName());
		
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL ");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getStatement(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);
		checkProcessingUrlStatus();
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPostBalance() throws Exception {

		Log4jUtil.log("TestPostBalance covers Post of Balance API with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/balance.xml");
		payload = payload.replaceAll("--id--", IHGUtil.createRandomNumericString(5));
		payload = payload.replaceAll("000001", IHGUtil.createRandomNumericString(6));
		payload = payload.replaceAll("00001", IHGUtil.createRandomNumericString(5));
		payload = payload.replaceAll("patientExternalId", testData.getUserName());
		
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL ");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getBalance(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);
		checkProcessingUrlStatus();
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testMassMessaging() throws Exception {

		Log4jUtil.log("TestMassMessaging covers Posting and of mass admin message with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/massmessage.xml");
		payload = payload.replaceAll("-message_id-", OauthUtils.getUUID());
		payload = payload.replaceAll("-From-", testData.getFrom());
		payload = payload.replaceAll("-bulk-", OauthUtils.getUUID());
		
		setConnection();
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getMassAdminMessage(), payload, testData.getCommonPath() + "/response.xml");

	}
	
	@Test(enabled = false, groups = {"P2P"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDirectSearch() throws Exception {

		Log4jUtil.log("TestDirectSearch covers Post and of direct search API for Provider with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/directorysearch.xml");
		setConnection();
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getDirectorySearch(), payload, testData.getCommonPath() + "/response.xml");
		
	}
	
	@Test(enabled = false, groups = {"P2P"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDirectMessage() throws Exception {

		Log4jUtil.log("TestDirectMessage covers Post of p2p message with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		Long timeStamp = System.currentTimeMillis();
		String subject = "Test"+timeStamp;
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/directmessage.xml");
		String guuid = OauthUtils.getUUID();
		payload = payload.replaceAll("--partnerMessageId--", guuid);
		payload = payload.replaceAll("--Subject--", subject);
		setConnection();
		Log4jUtil.log("Step 2: Do Message Post Request");
		OauthUtils.setupHttpPostRequest(testData.getDirectMessages(), payload, testData.getCommonPath() + "/response.xml");
		
		Log4jUtil.log("Step 3: Verify MFMessageId, PartnerMessageId and StatusCode");
		String mfMsgID =RestUtils.verifyDirectMessageResponse(testData.getCommonPath() + "/response.xml",guuid);
		
		Log4jUtil.log("Step 4: Verify messageStatus by invoking Get Status on MFMessageId " + mfMsgID);
		String getStatusUrl=testData.getDirectMessages().replaceAll("directmessages", "directmessage");
		processingUrl = getStatusUrl+"/"+mfMsgID+"/status";
		
		Log4jUtil.log("Step 5: Do a Get call to check the STATUS");
		OauthUtils.setupHttpGetRequest(processingUrl , testData.getCommonPath() + "/response.xml");
		
		Log4jUtil.log("Step 6: Do a Get message header to list message uid");
		Log4jUtil.log("path to store xml="+testData.getCommonPath() + "/response.xml");
		OauthUtils.setupHttpGetRequest(testData.getDirectMessageHeaders() , testData.getCommonPath() + "/response.xml");
		
		Log4jUtil.log("Step 7: Extract message uid from the response payload "+subject);
		String msgUid = RestUtils.verifyUnseenMessageListAndGetMessageUID(testData.getCommonPath() + "/response.xml", subject);
		
		Log4jUtil.log("Step 8:Get Direct message with Message Uuid is  " + msgUid + ".");
		String directmessageUrl = testData.getDirectMessageDelete()+msgUid;
		OauthUtils.setupHttpGetRequest(directmessageUrl , testData.getCommonPath() + "/response.xml");
		
		String deleteUrl = testData.getDirectMessageDelete()+msgUid+"/delete";
		timeStamp = (timeStamp+3600000)/1000;
		Log4jUtil.log("timeStamp :- "+Long.toString(timeStamp));
		Log4jUtil.log("deleteUrl :- "+deleteUrl);
		String token = testData.getToken();
		token = token.replaceAll("--nonce--", OauthUtils.getUUID());
		token = token.replaceAll("--timeStamp--",Long.toString(timeStamp));
		Log4jUtil.log("Step 9:Delete Direct message");
		OauthUtils.setupHttpDeleteRequestOauth10(deleteUrl,testData.getCommonPath() + "/response.xml",token);
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPostAppointment() throws Exception {

		Log4jUtil.log("Test Post Appointment covers Post of appointmentsData API with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/appointmentsdata.xml");
		
		setConnection();
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getAppointmentData(), payload, testData.getCommonPath() + "/response.xml");
		
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testStatementPreference() throws Exception {
		Log4jUtil.log("Test StatementPreference covers post and get of statement preferences with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();
		String payload = OauthUtils.prepareCCD(testData.getXmlResourcePath()+"/statementpreference.xml");
		payload = payload.replaceAll("--PracticePatientId--", testData.getUserName());
		
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getStatementPreference(), payload, testData.getCommonPath() + "/response.xml");

		Log4jUtil.log("Processing status URl:" + processingUrl);
		sinceTime = sinceTime*1000;
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getStatementPreference() + "?since=" + sinceTime + "", testData.getCommonPath() + "/response.xml");
	}
}
