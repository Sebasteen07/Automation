package com.intuit.ihg.product.integrationplatform.test;


import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10TestData;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;



/**
 * @author Vasudeo P
 * @Date 23/Dec/2014
 * @Description :-
 * @Note :
 */

public class IntegrationPlatformAcceptanceTests10 {

	
	
	static Oauth10TestData testData = null;
	String payload=null;
	String processingUrl=null;
	Long timestamp = System.currentTimeMillis();
	String sinceTime="1418714846";
	/*
	 * Read test-data from excel File
	 */
	@BeforeClass
	public void setOauth10() throws Exception
	{
		Oauth10 Oauth10Data= new Oauth10();
		testData =new Oauth10TestData(Oauth10Data);
		
		Log4jUtil.log("Common Path: " + testData.getCommonPath());
		Log4jUtil.log("OAuthProperty: " + testData.getOAuthProperty());
		Log4jUtil.log("OAuthKeyStore: " + testData.getOAuthKeyStore());
		Log4jUtil.log("OAuthAppToken: " + testData.getOAuthAppToken());
		Log4jUtil.log("OAuthUsername: " + testData.getOAuthUsername());
		Log4jUtil.log("OAuthPassword: " + testData.getOAuthPassword());
		
	}
	
	/*
	 * Set Oauth1.0 connection
	 */
	@BeforeMethod
	public void setConnection() throws IOException
	{
		Log4jUtil.log("Step 1: Setup Oauth client 1.0"); 
		RestUtils.oauthSetup1O(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword());
		
		Log4jUtil.log("Oauth connection successful");
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDC() throws Exception{
		
		Log4jUtil.log("TestPIDC covers Post and Get PIDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		String practicePatientId = "Patient" +timestamp;
		String firstName = "Name" + timestamp;
		String lastName = "TestPatient1" + timestamp;
		String email = "vasudeo.parab"+timestamp+"@tejora.com";
		payload = RestUtils.preparePatient(testData.getCommonPath()+"/patient.xml", practicePatientId, firstName, lastName, email,null);
		
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL");
		processingUrl = RestUtils.setupHttpPostRequest(testData.getPatientRestURL(), payload, testData.getCommonPath()+"/response.xml");
		
		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getPatientRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
				
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDC() throws Exception{
				
		Log4jUtil.log("TestAMDC covers Post and Get AMDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		payload = RestUtils.prepareSecureMessage(testData.getCommonPath()+"/secureMessage.xml", testData.getFrom(), testData.getUserName(), "Test " + timestamp,null);
				
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = RestUtils.setupHttpPostRequest(testData.getAMDCRestURL(), payload, testData.getCommonPath()+"/response.xml");

		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getAMDCRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment() throws Exception{
		
		Log4jUtil.log("TestAppointment covers Post and Get Appointment with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		payload = RestUtils.convertXMLFileToString(testData.getCommonPath()+"/PostAppointment.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = RestUtils.setupHttpPostRequest(testData.getAppointmentRestURL(), payload, testData.getCommonPath()+"/response.xml");

		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getAppointmentRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCCD() throws Exception{
		
		Log4jUtil.log("TestPostCCD covers Post EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		payload = RestUtils.prepareCCD(testData.getCommonPath()+"/CCD.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = RestUtils.setupHttpPostRequest(testData.getEHDCRestURL(), payload, testData.getCommonPath()+"/response.xml");
		
		Log4jUtil.log("Processing status URl:"+processingUrl);
		
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetReadCommunication() throws Exception{
		
		Log4jUtil.log("TestGetReadCommunication covers Get ReadCommunication with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		Log4jUtil.log("Step 2: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCCDExchangeBatch() throws Exception{
		
		Log4jUtil.log("TestGetCCDExchangeBatch covers Get EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		Log4jUtil.log("Step 2: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getccdExchangeBatch() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescription() throws Exception{
		
		Log4jUtil.log("TestPrescription covers Post and Get Prescription with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		payload = RestUtils.convertXMLFileToString(testData.getCommonPath()+"/post_rx_payload.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = RestUtils.setupHttpPostRequest(testData.getPrescriptionRestURL(), payload, testData.getCommonPath()+"/response.xml");

		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getPrescriptionRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetEvents() throws Exception{
		
		Log4jUtil.log("TestGetEvents covers Pull Events with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		Log4jUtil.log("Step 2: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getPullEventsURL() + "&sinceTime=" + "1419228075000" + "&maxEvents=400", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPayments() throws Exception{
		
		Log4jUtil.log("TestGetPayments covers payments with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		Log4jUtil.log("Step 2: Do a Get call");
		RestUtils.setupHttpGetRequest(testData.getPaymentURL() + "?sinceTime=" + "1419233246000", testData.getCommonPath()+"/response.xml");
		
	}
	@AfterMethod
	public void close() throws IOException
	{
		Log4jUtil.log("======================================================================================================");
	}
}