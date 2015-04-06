package com.intuit.ihg.product.integrationplatform.test;


import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.ibm.icu.util.Calendar;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10;
import com.intuit.ihg.product.integrationplatform.utils.Oauth10TestData;
import com.intuit.ihg.product.integrationplatform.utils.OauthUtils;



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
	Long sinceTime=null;
    Long sixMonthAgo =null;
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
		
		Calendar c= Calendar.getInstance();
		c.add(Calendar.WEEK_OF_MONTH, -24);
		sixMonthAgo = c.getTime().getTime();
		sinceTime=sixMonthAgo/1000;
		
	}
	
	/*
	 * Set Oauth1.0 connection
	 */
	
	public void setConnection() throws IOException
	{
		Log4jUtil.log("Step 1: Setup Oauth client 1.0"); 
		assertTrue(OauthUtils.oauthSetup1O(testData.getOAuthKeyStore(),testData.getOAuthProperty(), testData.getOAuthAppToken(), testData.getOAuthUsername(), testData.getOAuthPassword()),"Oauth Connection Failed");

	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPIDC() throws Exception{
		
		Log4jUtil.log("TestPIDC covers Post and Get PIDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		String practicePatientId = "Patient" +sinceTime;
		String firstName = "Name" + sinceTime;
		String lastName = "TestPatient1" + sinceTime;
		String email = "vasudeo.parab"+sinceTime+"@tejora.com";
		payload = OauthUtils.preparePatient(testData.getCommonPath()+"/patient_Oauth10.xml", practicePatientId, firstName, lastName, email,null);
		
		Log4jUtil.log("Step 2: Do a POST call and get processing status URL");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getPatientRestURL(), payload, testData.getCommonPath()+"/response.xml");
		
		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPatientRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
				
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAMDC() throws Exception{
				
		Log4jUtil.log("TestAMDC covers Post and Get AMDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		payload = OauthUtils.prepareSecureMessage(testData.getCommonPath()+"/secureMessage.xml", testData.getFrom(), testData.getUserName(), "Test " + sinceTime,null);
				
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getAMDCRestURL(), payload, testData.getCommonPath()+"/response.xml");

	   
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getAMDCRestURL() + "?since=" + "1428316536" + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment() throws Exception{
		
		Log4jUtil.log("TestAppointment covers Post and Get Appointment with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		payload = OauthUtils.convertXMLFileToString(testData.getCommonPath()+"/PostAppointment.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getAppointmentRestURL(), payload, testData.getCommonPath()+"/response.xml");

		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getAppointmentRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCCD() throws Exception{
		
		Log4jUtil.log("TestPostCCD covers Post EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();

		payload = OauthUtils.prepareCCD(testData.getCommonPath()+"/CCD_Oauth10.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getEHDCRestURL(), payload, testData.getCommonPath()+"/response.xml");
		
		Log4jUtil.log("Processing status URl:"+processingUrl);
		
	}
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetReadCommunication() throws Exception{
		
		Log4jUtil.log("TestGetReadCommunication covers Get ReadCommunication with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getReadCommunicationURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCCDExchangeBatch() throws Exception{
		
		Log4jUtil.log("TestGetCCDExchangeBatch covers Get EHDC with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();
		
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getccdExchangeBatch() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrescription() throws Exception{
		
		Log4jUtil.log("TestPrescription covers Post and Get Prescription with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());

		setConnection();
		
		payload = OauthUtils.convertXMLFileToString(testData.getCommonPath()+"/post_rx_payload.xml");
		
		Log4jUtil.log("Step 2: Do Message Post Request");
		processingUrl = OauthUtils.setupHttpPostRequest(testData.getPrescriptionRestURL(), payload, testData.getCommonPath()+"/response.xml");

		Log4jUtil.log("Processing status URl:"+processingUrl);
		
		Log4jUtil.log("Step 3: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPrescriptionRestURL() + "?since=" + sinceTime + ",0", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetEvents() throws Exception{
		
		Log4jUtil.log("TestGetEvents covers Pull Events with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();
		
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPullEventsURL() + "&sinceTime=" + sixMonthAgo + "&maxEvents=400", testData.getCommonPath()+"/response.xml");
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPayments() throws Exception{
		
		Log4jUtil.log("TestGetPayments covers payments with OAuth 1.0");
		Log4jUtil.log("Execution Environment: " + IHGUtil.getEnvironmentType());
		
		setConnection();
		
		Log4jUtil.log("Step 2: Do a Get call");
		OauthUtils.setupHttpGetRequest(testData.getPaymentURL() + "?sinceTime=" + "1419233246000", testData.getCommonPath()+"/response.xml");
		
	}

}