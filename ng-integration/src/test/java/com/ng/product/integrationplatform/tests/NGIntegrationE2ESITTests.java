// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PatientRegistrationUtils;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginEnrollment;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.JalapenoAppointmentRequestV2Step2;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentRequestV2HistoryPage;
import com.medfusion.product.object.maps.patientportal2.page.AppointmentRequestPage.NGAppointmentRequestV2Step1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page2;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.patientportal2.page.PayNow.JalapenoPayNowPage;
import com.medfusion.product.object.maps.patientportal2.page.PrescriptionsPage.JalapenoPrescriptionsPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.medfusion.product.practice.tests.VirtualCardSwiperTest;
import com.medfusion.qa.mailinator.Email;
import com.medfusion.qa.mailinator.Mailer;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.flows.NGPatient;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonFlows;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGIntegrationE2ESITTests extends BaseTestNGWebDriver{
 
	private PropertyFileLoader PropertyLoaderObj;
	
	private static final String NewDependentActivationMessage ="You are invited to create a Patient Portal guardian account at ";
	private static final String MemberConfirmationMessage ="New Member Confirmation";
	private static final String PortalURL ="Visit our patient portal now";
	private static final String INVITE_EMAIL_SUBJECT_REPRESENTATIVE = "You're invited to create a Portal account to be a trusted representative of a patient at ";
	private static final String INVITE_EMAIL_BUTTON_TEXT = "Sign Up!";
	private static final String WELCOME_EMAIL_BUTTON_TEXT = "Visit our patient portal now";
	private static final String INVITE_EMAIL_SUBJECT_PATIENT = "You're invited to create a Patient Portal account at ";
	private static final String WELCOME_EMAIL_SUBJECT_PATIENT = "New Member Confirmation";
	private static final String WELCOME_EMAIL_BODY_PATTERN_PRACTICE = "Thank you for creating an account with PracticeName";
	
    int arg_timeOut=1800; 
    NGAPIUtils ngAPIUtils;
    String EnterprisebaseURL;
    NGAPIFlows NGAPIFlows;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws Throwable {
		log("Getting Test Data");
		PropertyLoaderObj = new PropertyFileLoader();
		ngAPIUtils = new NGAPIUtils(PropertyLoaderObj);
		NGAPIFlows = new NGAPIFlows(PropertyLoaderObj);
		if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
			EnterprisebaseURL= ngAPIUtils.getRelativeBaseUrl();
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			EnterprisebaseURL= ngAPIUtils.getRelativeBaseUrl();
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	}		
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithOnlyMandatoryDemographicsToMFPortal() throws Throwable {
		log("Test Case:  Verify the new patient having all the required Demographics is able to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id.trim()+"'");
		
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		
		log("Step 4: Verify the enrollment and processing status of patient in pxp_enrollment table");
		if(processing_status.equals("1")){
			log("Processing status is "+processing_status+" i.e. First time enrollment record is inserted into table.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("2")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		CommonUtils.VerifyTwoValues(processing_status,"equals","2");		
        
        log("Step 5: Verify the status of MF agent job");
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
//		CommonUtils.VerifyTwoValues(emailStatus1,"equals","Pending");
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
//		CommonUtils.VerifyTwoValues(jobStatus1,"equals","Pending");
		log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
		    log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
			log("Registration URL is received");
			}
		else{
			log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		Log4jUtil.log("Step 6: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
//		log("Step 7: Do get processing status call and verify the processing time of registration mail to be received");

//		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
//		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		Thread.sleep(60000);
		log("Step 8: Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		
		if(emailStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
	            	log("Step End: Mail sent to patient successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(emailStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		log("Step End: The processing status of MF agent job is "+jobStatus+" registration mail is sent to patient for enrollment");		
		
		log("Step 9: Verify the processing status of patient in pxp_enrollment table");
		processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
        
		if(processing_status.equals("2")){
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("3")) {
	            	log("Step End: Processing status is "+processing_status+", RSDK has sent email to patient.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
        CommonUtils.VerifyTwoValues(processing_status,"equals","3");
        enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		log("Step End: Enrollment status is "+enrollment_status);
		
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			log(sDate1);				
			
		log("Step 11 : Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		log("Step 12 : Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		Thread.sleep(5000);
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		log("Step 13: Detecting if Home Page is opened and logout");
		Thread.sleep(4000);
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
		jalapenoHomePage.LogoutfromNGMFPortal();
		
		Thread.sleep(20000);
		Log4jUtil.log("Step 14: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getPIDCURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		Thread.sleep(2000);
		RestUtils.isPatientRegistered(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""), createPatient.getFirstName(), createPatient.getLastName(), null);
		
		Thread.sleep(2000);
		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");

		verifyProcessingStatusto4(person_id);
		Log4jUtil.log("Step 15: Find the patient and check if he is registered");
//        String externalid =DBUtils.executeQueryOnDB("MFAgentDB","select externalid from person where externalid ='"+person_nbr.trim().replace("\t", "")+"'");
//        CommonUtils.VerifyTwoValues(externalid,"equals",person_nbr.trim().replace("\t", ""));
//        log("Result: RSDK sent the enrollemnt status to MF agent "+externalid+" person is added to person table in MF agent DB");
		
        String GetEnrollmentStatusresponse2 =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"),"equals","Completed"); 
		log("Result: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
		
		log("Step 16: Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status2,"equals","9");
		String processing_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(processing_status2,"equals","4");
		log("Step End: Patient enrollment status is "+enrollment_status2+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
				
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsToMFPortal() throws Throwable {			
		log("Test Case:  Verify the patient without mandatory demographics is unable to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step Begins: Create the patient in NG EPM without First Name");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutFirstName");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Last Name");
		NewPatient createPatient1 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutLastName");
		
		log("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient1));
	    ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient1) , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Dob");
		NewPatient createPatient2 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutDOB");
		
		log("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient2));
	    ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient2) , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Gender");
		NewPatient createPatient3 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutSex");
		log("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient3));
		
	    ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient3) , 400);
		log("Step End: Person should not be created");
					
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsEmailAddressToMFPortal() throws Throwable {
				
		log("Test Case:  Verify the patient without EmailAddress is unable to enroll to MF portal.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step 1: Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutEmailaddress");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 400);
		log("MF agent doesnot initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,404);
		log("No Enrollment status found for the patient");				
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsZipToMFPortal() throws Throwable {
				
		log("Test Case:  Verify the patient without zip is unable to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutZip");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 400);
		log("MF agent doesnot initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,404);
		log("No Enrollment status found for the patient");				
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testADeletePatientEnrollment() throws Throwable {
		log("Test Case:  Verify the patient enrollment status after deleting the patient enrollment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		Long timestamp = System.currentTimeMillis();
		
		String personId = registerNGPatienttoMFPortal();
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
		String PatientFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id = '"+personId.trim()+"'");
		String PatientLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id = '"+personId.trim()+"'");
		
		
		log("Step 17: Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getDoctorLogin(), PropertyLoaderObj.getDoctorPassword());
		log("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(PatientFirstName,PatientLastName);
		patientSearchPage.clickOnPatient(PatientFirstName,PatientLastName);
		log("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(PatientFirstName,PatientLastName);
		
		VerifyGetPIDCCall(timestamp, person_nbr, PatientFirstName, PatientLastName, "DELETED",PropertyLoaderObj.getIntegrationPracticeID());
		
		Thread.sleep(60000);
		log("Step 18: Verify the enrollment and processing status of patient in pxp enrollment table");	
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
		
		if(processing_status.equals("3")||processing_status.equals("4")){
			log("Processing status is "+processing_status+" RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
	            if (processing_status.equals("5")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is deleted successfully.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(processing_status,"equals","5");
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","11");				
        
		log("Step 19: Verify the enrollment status of patient");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","11"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Account Deleted"); 
		log("Step End: Patient enrollment status is "+enrollment_status+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testACreateDependentAndGuardian() throws Throwable {
	log("Test Case:  Verify the patient enrollment e2e workflow when a Guardian is having an existing MF account");
	log("Execution Environment: " + IHGUtil.getEnvironmentType());
	log("Execution Browser: " + TestConfig.getBrowserType());
	Long timestamp = System.currentTimeMillis();
	
	log("Step 1: Create the Guardian in NG EPM");
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1"), PropertyLoaderObj.getProperty("NGEnterprise1Practice1"));
	NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
	System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());
	
	ObjectMapper objMap = new ObjectMapper();
    String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
    log("Guardian Request Body is \n" + requestbody);
	
    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
	String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
	String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
	log("Step End: Guardian created with id "+person_id);
	
	log("Step 2: Create the Dependent in NG EPM");
	NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");
	
    String dependentrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createdependent);
    log("Dependent Request Body is \n" + dependentrequestbody);
		
	String dependentperson_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,dependentrequestbody , 201);
	log("Step End: Dependent created with id "+dependentperson_id);
	
	log("Step 3: Using Post Enrollment call, Verify the MF agent trigger for new patient");
	String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	log("Step End: MF agent initiate the enrollment automatically");

	log("Step 4: Verify the enrollment status of guardian and dependent");
	String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
	String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
	log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
	
	String getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
	String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
	log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
         
	log("Step 5: Verify the enrollment and processing status of patient in pxp_enrollment table");
	String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
	String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
	String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
	CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
	
	verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(person_id.trim(),PropertyLoaderObj.getProperty("NGEnterprise1Practice1"), PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
	verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(dependentperson_id.trim(),PropertyLoaderObj.getProperty("NGEnterprise1Practice1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
	
		Mailinator mail = new Mailinator();
		Thread.sleep(15000);
		log("Step 6: Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
		log("Step End: Guradian mail is received");
		
		log("Step 7: Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
		log("Step End: Dependent mail is received");
		
		log("Step 8 : Enroll the Guardian to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationGuardianUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		String patientLogin=createPatient.getEmailAddress();
		log("Step 9 : Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		log("Step 10: Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		log("Step 11: Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(activationDependentUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createdependent.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYearUnderage());
		
		log("Step 12: Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(), createdependent.getEmailAddress());
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, PropertyLoaderObj.getPassword(), "Parent");

		log("Step 13: Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		log("Step 14: Logout, login and change patient");
		NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();
		jalapenoHomePage = loginPage.login(patientLogin, PropertyLoaderObj.getPassword());
		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

        Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
        
		VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered",PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
		
		log("Step 15: Using mailinator Mailer to retrieve the latest emails for patient and guardian");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + MemberConfirmationMessage + "     :   " + PortalURL);
		Thread.sleep(60000);
		String MemberConfirmationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), MemberConfirmationMessage, PortalURL, 80);
		if(!MemberConfirmationUrl.isEmpty()){
		log("The new member confirmation mail is received successfully");}
		
		log("Step 16: Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		
		if(dependentprocessing_status.equals("3")){
			log("Processing status is "+dependentprocessing_status+" i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
	            if (dependentprocessing_status.equals("4")) {
	            	log("Step End: Processing status is "+dependentprocessing_status+" i.e. Processing status is completed");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		CommonUtils.VerifyTwoValues(processing_status,"equals","4");
		CommonUtils.VerifyTwoValues(dependentprocessing_status,"equals","4");
		enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","9");
		enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","9");
		
		log("Step 17: Verify the enrollment status of guardian and dependent");
		getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Completed"); 
		log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
		}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testACreateGuardianAndDependentANDEnrollOnlyDependentToPortal() throws Throwable {
		log("Test Case:  Verify the patient enrollment e2e workflow when a Guardian doesnot have an existing MF account");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		Long timestamp = System.currentTimeMillis();
		
		String guardianFirstName = "Guardian" + (new Date()).getTime();
		String guardianLastName = "Guardian" + (new Date()).getTime();
		System.setProperty("ParentEmailAddress", guardianFirstName+ "@mailinator.com");
		
		ObjectMapper objMap = new ObjectMapper();
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	

		log("Step 1: Create the Dependent in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");
		
	    String dependentrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createdependent);
	    log("Dependent Request Body is \n" + dependentrequestbody);
			
		String dependentperson_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,dependentrequestbody , 201);
		log("Step End: Dependent created with id "+dependentperson_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for dependent");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		
		log("Step End: MF agent initiate the enrollment automatically");

		log("Step 3: Verify the enrollment status of dependent");		
		String getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	              
		log("Step 4: Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(dependentperson_id.trim(),PropertyLoaderObj.getProperty("NGMainPracticeID"),PropertyLoaderObj.getIntegrationPracticeID());
		
			Mailinator mail = new Mailinator();
			Thread.sleep(15000);		
			log("Step 6: Verify the dependent mail");
			Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			log("Step End: Dependent mail is received");
			
			log("Step 7 : Enroll the Dependent to MedFusion Portal");
			PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationDependentUrl);
			Thread.sleep(3000);
			AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
					createdependent.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYearUnderage());
			
			log("Step 8: Continue registration - check dependent info and fill login credentials");
			linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(), createdependent.getEmailAddress());
			SecurityDetailsPage accountDetailsPage= linkAccountPage.continueToCreateGuardianOnly(guardianFirstName, guardianLastName, "Parent");

			log("Step 9: Finishing of dependent activation: Filling patient data");
			JalapenoHomePage jalapenoHomePage =
					accountDetailsPage.fillAccountDetailsAndContinue(createdependent.getEmailAddress(),PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());
			
			log("Step 10: Detecting if Home Page is opened");
			Thread.sleep(2000);
			Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));		
			
			log("Step 11: Logout from Portal");
			Thread.sleep(9000);
			NGLoginPage loginPage =jalapenoHomePage.LogoutfromNGMFPortal();
				
			VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered",PropertyLoaderObj.getIntegrationPracticeID());
			
			log("Step 12: Verify the enrollment and processing status of dependent in pxp_enrollment table");
			String dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
	        
			if(dependentprocessing_status.equals("3")){
				log("Processing status is "+dependentprocessing_status+" i.e. Request is in progress");
				for (int i = 0; i < arg_timeOut; i++) {
					dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		            if (dependentprocessing_status.equals("4")) {
		            	log("Step End: Processing status is "+dependentprocessing_status+" i.e. Processing status is completed");
		                break;
		            } else {
		                if (i == arg_timeOut - 1)
		                    Thread.sleep(1000);
		            }
		        }
			}
			CommonUtils.VerifyTwoValues(dependentprocessing_status,"equals","4");
			enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
			CommonUtils.VerifyTwoValues(enrollment_status1,"equals","9");
		
			
			log("Step 13: Verify the enrollment status of dependent");
			getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
			GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
			
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
			log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	}

	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testALACreateTrustedRepresentativeOnly() throws Throwable {
		log("Test Case:  Verify the patient enrollment e2e workflow when a family account access to patient (Trusted Representative account)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
				
		log("Step 1: Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		log("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(person_id.trim(),PropertyLoaderObj.getProperty("NGMainPracticeID"),PropertyLoaderObj.getIntegrationPracticeID());

  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 4: Moving to the link obtained from the email message- Patient 1");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			log(sDate1);
			
		
			log("Step 5 : Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
			PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
			Thread.sleep(3000);
			SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
					createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

			log("Step 6 : Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
			JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

			log("Step 7: Detecting if Home Page is opened and logout");
			assertTrue(jalapenoHomePage.areBasicPageElementsPresent());
			NGLoginPage loginPage= jalapenoHomePage.LogoutfromNGMFPortal();

			
		log("Step 8: Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");	
		String trustedPatientrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(trustedPatient);
		log("Trusted Patient Request Body " +trustedPatientrequestbody);
		String trustedperson_id= NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,trustedPatientrequestbody, 201);
		log("Step End: Person created with id "+trustedperson_id);

		log("Step 9 :Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		Thread.sleep(7000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		log("Step 10: Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		log("Step 11: Redirecting to verification page");
		patientVerificationPage.getToThisPage(patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		log("Step 12: Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		
		Thread.sleep(5000);
		log("Step 13: Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");
		
		Thread.sleep(5000);
		log("Step 14: Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),
				PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(), PropertyLoaderObj.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		log("Step 15: Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.LogoutfromNGMFPortal();
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientHavingInvalidZipToMFPortal() throws Throwable {
		log("Test Case:  Verify the patient having invalid zip is unable to enroll to MF portal and Enrollment status is NOT ENROLLED");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step 1: Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"invalidZIP");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
    	
		log("Step 3: Verify the enrollment status of patient in pxp_enrollment table");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		if((processing_status.equals("1"))||(processing_status.equals("2"))){
		log("Processing status is "+processing_status+ ", Enrollment record is inserted into DB");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
            if (processing_status.equals("7")) {
            	log("Step End: Processing status is "+processing_status+" Error:" +DBUtils.executeQueryOnDB("NGCoreDB","select error_message from pxp_enrollments where person_id = '"+person_id.trim()+"'"));
                break;
            } else {
                if (i == arg_timeOut - 1)
                    Thread.sleep(1000);
            }
        }
	    }
		CommonUtils.VerifyTwoValues(processing_status,"equals","7");
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","0");
		
		log("Step 4: Verify the enrollment status of patient");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","0"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Not Enrolled"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription") +" and Error Message: "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"errorMessage"));
	}
	
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testADeactivatePatientEnrollment() throws Throwable {
		log("Test Case:  Verify the patient enrollment status after deactivating the patient enrollment");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		Long timestamp = System.currentTimeMillis();
		
		String personId = registerNGPatienttoMFPortal();
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
		String PatientFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id = '"+personId.trim()+"'");
		String PatientLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id = '"+personId.trim()+"'");
		
		log("Step 17: Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getDoctorLogin(), PropertyLoaderObj.getDoctorPassword());
		log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(PatientFirstName,PatientLastName);
		patientSearchPage.clickOnPatient(PatientFirstName,PatientLastName);
		log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(PatientFirstName,PatientLastName);
		
		VerifyGetPIDCCall(timestamp, person_nbr,PatientFirstName, PatientLastName,"DEACTIVATED",PropertyLoaderObj.getIntegrationPracticeID());
		
		Thread.sleep(10000);
		log("Step 18: Verify the enrollment and processing status of patient in pxp enrollment table");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
        
		if(processing_status.equals("3")||processing_status.equals("4")){
			log("Processing status is "+processing_status+" RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
	            if (processing_status.equals("6")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is deactivated successfully.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(processing_status,"equals","6");
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+personId.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","10");				
        
		log("Step 15: Verify the enrollment status of patient");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","10"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Deactivated"); 
		log("Step End: Patient enrollment status is "+enrollment_status+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
	}
	
	public String registerNGPatienttoMFPortal() throws Throwable{

		log("Registering the NG patient to MF portal");
		Long timestamp1 = System.currentTimeMillis();
		
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id.trim()+"'");
		
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		
		log("Step 4: Verify the enrollment and processing status of patient in pxp_enrollment table");
		if(processing_status.equals("1")){
			log("Processing status is "+processing_status+" i.e. First time enrollment record is inserted into table.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("2")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		CommonUtils.VerifyTwoValues(processing_status,"equals","2");		
        
        log("Step 5: Verify the status of MF agent job");
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
		    log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
			log("Registration URL is received");
			}
		else{
			log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		Log4jUtil.log("Step 6: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
//		log("Step 7: Do get processing status call and verify the processing time of registration mail to be received");

//		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
//		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		Thread.sleep(60000);
		log("Step 8: Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		
		if(emailStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
	            	log("Step End: Mail sent to patient successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(emailStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		log("Step End: The processing status of MF agent job is "+jobStatus+" registration mail is sent to patient for enrollment");		
		
		log("Step 9: Verify the processing status of patient in pxp_enrollment table");
		processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
        
		if(processing_status.equals("2")){
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("3")) {
	            	log("Step End: Processing status is "+processing_status+", RSDK has sent email to patient.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
        CommonUtils.VerifyTwoValues(processing_status,"equals","3");
        enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		log("Step End: Enrollment status is "+enrollment_status);
		
		Thread.sleep(60000);
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			log(sDate1);				
			
		log("Step 11 : Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		log("Step 12 : Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		log("Step 13: Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();
	
		Log4jUtil.log("Step 14: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp1 / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getPIDCURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		Thread.sleep(2000);
		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");

		verifyProcessingStatusto4(person_id);
		
		Log4jUtil.log("Step 15: Find the patient and check if he is registered");
        String GetEnrollmentStatusresponse2 =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"),"equals","Completed"); 
		log("Result: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
		
		log("Step 16: Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status2,"equals","9");
		String processing_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(processing_status2,"equals","4");
		log("Step End: Patient enrollment status is "+enrollment_status2+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
			
		return person_id;
	}
	
	public String registerPatientWithoutDBValidation() throws Throwable{
		log("Registering the NG patient to MF portal");
		
		Long timestamp = System.currentTimeMillis();
		
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        log("Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
				
		Thread.sleep(120000);
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			log(sDate1);				
			
		log("Step 11 : Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		log("Step 12 : Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		log("Step 13: Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();
		
		return person_id;
	}
	
	public void verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(String person_id,String integrationID) throws Throwable{
		
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id.trim()+"'");
		String emailStatusQuery ="select top 1 status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"' order by createdts desc";
		String processingStatusIDQuery = "select top 1 processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"' order by createdts desc";
		
		log("Step Begins: Verify the status of MF agent job");
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB",emailStatusQuery);
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = ("+processingStatusIDQuery+")");

		if(jobStatus1.isEmpty()){
			for (int i = 0; i < arg_timeOut; i++) {
				jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = ("+processingStatusIDQuery+")");
				if ((jobStatus1.equalsIgnoreCase("Pending")) || (jobStatus1.equalsIgnoreCase("COMPLETED"))) {
					Log4jUtil.log("Step End: Request is sent to RSDK successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
		    log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
			log("Registration URL is received");
			}
		else{
			log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = ("+processingStatusIDQuery+")");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = ("+processingStatusIDQuery+")");
		
		Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		log("Step Begins: Do get processing status call and verify the processing time of registration mail to be received");

//		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
//		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		Thread.sleep(60000);
		log("Step Begins: Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus =DBUtils.executeQueryOnDB("MFAgentDB",emailStatusQuery);
		
		if(emailStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus =DBUtils.executeQueryOnDB("MFAgentDB",emailStatusQuery);
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
	            	log("Step End: Mail sent to patient successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(emailStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = ("+processingStatusIDQuery+")");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		log("Step End: The processing status of MF agent job is "+jobStatus+" registration mail is sent to patient for enrollment");		
	}
	
	public void VerifyGetPIDCCall(Long timestamp, String person_nbr,String FName, String LName,String portalStatus,String integrationID) throws IOException, ParserConfigurationException, SAXException, InterruptedException{

		Thread.sleep(20000);
		Log4jUtil.log("Step Begins: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getPIDCURL().replaceAll("integrationID", integrationID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		Thread.sleep(2000);
		if(portalStatus.equalsIgnoreCase("Registered")){
			RestUtils.isPatientRegistered(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""), FName, LName, null);
		}
		else if(portalStatus.equalsIgnoreCase("DEACTIVATED")){
			RestUtils.isPatientDeactivatedorDeleted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""), FName, LName, null,portalStatus);	
		}	
		else if(portalStatus.equalsIgnoreCase("DELETED")){				
			RestUtils.isPatientDeactivatedorDeleted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""), FName, LName, null,portalStatus);
		}
		else{
			Log4jUtil.log("Invalid Portal Status");
		}
		
		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");
	}
	
	public void verifyProcessingStatusto3(String person_id,String integrationID) throws Throwable{
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		if(processing_status.equals("1")||processing_status.equals("2")){
			log("Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("2")) {
	            	verifyMFJOBStatus(person_id,integrationID);
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("3")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. RSDK has sent mail to the guardian");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
	}
	
	public void verifyProcessingStatusto4(String person_id) throws Throwable{
	  String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	  if(processing_status.equals("3")){
		log("Processing status is "+processing_status+" i.e. Request is in progress");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
            if (processing_status.equals("4")) {
            	log("Step End: Processing status is "+processing_status+" i.e. Processing status is completed");
                break;
            } else {
                if (i == arg_timeOut - 1)
                    Thread.sleep(1000);
            }
        }
	  }
	}
	
    public void verifyMFJOBStatus(String person_id,String integrationID) throws Throwable{
		
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id.trim()+"'");
		
		log("Step Begins: Verify the status of MF agent job");
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");

		log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
		    log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
			log("Registration URL is received");
			}
		else{
			log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		
		Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		log("Step Begins: Do get processing status call and verify the processing time of registration mail to be received");

		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		Thread.sleep(60000);
		log("Step Begins: Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		
		if(emailStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
	            	log("Step End: Mail sent to patient successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(emailStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		log("Step End: The processing status of MF agent job is "+jobStatus+" registration mail is sent to patient for enrollment");		
	}
    
    public void verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(String person_id,String practice_id,String integrationID) throws Throwable{
    	String processingStatusQuery ="select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"' and practice_id='"+practice_id.trim()+"'";
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB",processingStatusQuery);
		if(processing_status.equals("1")||processing_status.equals("2")){
			log("Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB",processingStatusQuery);
	            if (processing_status.equals("2")||processing_status.equals("3")) {
	            	verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(person_id,integrationID);
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB",processingStatusQuery);
	            if (processing_status.equals("3")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. RSDK has sent mail to the guardian");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		else if (processing_status.equals("3"))
        	log("Step End: Processing status is "+processing_status+" i.e. RSDK has sent mail to the guardian");
	}
    
    
    @Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEAutoPatientEnrollmentP2() throws Throwable {
		log("Test Case:  Verify auto enrollment should happen in Practice2 when the Patient has account in Practice1.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM first practice");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		Thread.sleep(60000);
		Long timestamp = System.currentTimeMillis();
		logStep("Create the chart in second practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),personId); 
		
		Thread.sleep(90000);
        logStep("Waiting for welcome mail at patient inbox from second practice");  
        String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url is "+visitPortal);
		
		logStep("Log into Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,visitPortal);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(20000);
        assertTrue(homePage.areBasicPageElementsPresent());
        
        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
        Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername2"),PropertyLoaderObj.getProperty("oAuthPassword2"));
        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"Registered", PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"));
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getProperty("NGEnterprise1Practice2"),PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1")); 
        log("Test Case End: Auto Enrolment to Second Practice is completed");	
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEAutoPatientEnrollmentP1() throws Throwable{
		log("Test Case:  Verify auto enrollment should happen in Practice1 when the Patient has account in Practice3.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in first Practice");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
		log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName1") + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	    String activationUrlP1 = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(),
	                INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName1"), PortalConstants.NewPatientActivationMessageLinkText, 40);
	    assertNotNull(activationUrlP1, "Error: Activation link not found.");

	    logStep("Create the chart in second practice");
	    Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),personId); 
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, personId, PropertyLoaderObj.getProperty("practiceName3"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P3"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3()); 
		
		Thread.sleep(60000);
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName", PropertyLoaderObj.getProperty("practiceName1"))));
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName", PropertyLoaderObj.getProperty("practiceName3"))));
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 80));		
		
		Thread.sleep(10000);
		logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
		
		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());

        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        assertTrue(homePage.areBasicPageElementsPresent());
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));        

        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
        Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"Registered", PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getProperty("NGEnterprise1Practice1"),PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1")); 
        log("Test Case End: Auto Enrolment to First Practice is completed");	
	}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeleteP3AutoPatientEnrollmentP1() throws Throwable {
		log("Test Case: Verify the patient is able to login into P1 when the P3 is delete Patient.(P1 is auto enrolled into P3 account).");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		logStep("Create the chart in second practice");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),personId); 
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, personId, PropertyLoaderObj.getProperty("practiceName3"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P3"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3()); 
		
		Thread.sleep(40000);
		logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
		
		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
             	
        logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getProperty("doctorLoginPractice3"), PropertyLoaderObj.getProperty("doctorPasswordPractice3"));
		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		logStep("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(),createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(),createPatient.getLastName());
		logStep("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(createPatient.getFirstName(),createPatient.getLastName());
			
		logStep("Log into Practice2 Portal");
		loginPage = new JalapenoLoginEnrollment(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
		logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        log("Verify the Multiple Practice Toggle is not displayed");
        homePage.VerifyMuiltiplePracticeToggle();
        
        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
        Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername3"),PropertyLoaderObj.getProperty("oAuthPassword3"));
        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"DELETED", PropertyLoaderObj.getProperty("integrationPracticeIDE1P3"));

        verifyPatientEnrollmentDeletedStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
		log("Test Case End: The patient is able to login into P1 when the P3 is deleted Patient.(P1 is auto enrolled into P3 account)");
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeactivateP3AutoPatientEnrollmentP1() throws Throwable {
		log("Test Case: Verify the patient enrollment satus when patient is deactivated from P3 and reactivated. (P1 is auto Enrolled into Patient 3 account).");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		logStep("Create the chart in second practice");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),personId); 
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, personId, PropertyLoaderObj.getProperty("practiceName3"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P3"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		
		Thread.sleep(60000);
        logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
		
		logStep("Log into Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
		logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        Thread.sleep(30000);
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        
        logStep("Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getProperty("doctorLoginPractice3"), PropertyLoaderObj.getProperty("doctorPasswordPractice3"));
		log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(),createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(),createPatient.getLastName());
		log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(createPatient.getFirstName(),createPatient.getLastName());
		
		logStep("Log into Practice2 Portal");
		loginPage = new JalapenoLoginEnrollment(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
		logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Verify the Multiple Practice Toggle is not displayed");
        homePage.VerifyMuiltiplePracticeToggle();

        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
        Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername3"),PropertyLoaderObj.getProperty("oAuthPassword3"));
        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"DEACTIVATED", PropertyLoaderObj.getProperty("integrationPracticeIDE1P3"));

        verifyPatientEnrollmentDeactivatedStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
      	}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentPracticeLevelE1P4() throws Throwable {
		log("Test Case:  Verify enrolled Patient in enterprise 1 and practice 4 is able to receive the Invitation for P4 (Practice level enrollment)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		logStep("Create the chart in Practice 4");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P4Location(),PropertyLoaderObj.getNGE1P4Provider(),personId); 
	
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", personId);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
				
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4(),PropertyLoaderObj.getProperty("integrationPracticeIDE1P4"));

  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName4") + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);	
			logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	        String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
	                INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName4"), PortalConstants.NewPatientActivationMessageLinkText, 80);
	        assertNotNull(activationUrl, "Error: Activation link not found.");

	    logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getFirstName(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();
				
		Thread.sleep(60000);
        logStep("Waiting for welcome mail at patient inbox from fourth practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
		
		logStep("Log into Practice4 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getFirstName(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Verify the Multiple Practice Toggle is not displayed");
        homePage.VerifyMuiltiplePracticeToggle();

        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
//        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"Registered", PropertyLoaderObj.getProperty("integrationPracticeIDE1P4"));
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
   
        log("Test Case End: The enrolled Patient in enterprise 1 and practice 4 is able to receive the Invitation for P4 (Practice level enrollment)");	
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeleteP2PatientEnrolledP1P2P3() throws Throwable {
		log("Test Case:  Verify the patient status in Practice 1 and Practice 3 when the patient is deleted from Practice 2.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
        logStep("Create the chart in Practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),personId); 
		
		logStep("Create the chart in Practice 3");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),personId);

		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		
		Thread.sleep(80000);		
		logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");	
		
		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        Thread.sleep(30000);
        assertTrue(homePage.areBasicPageElementsPresent());	
      
        logStep("Detecting if Home Page is opened and logout");
		homePage.LogoutfromNGMFPortal();
	
        logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getProperty("doctorLoginPractice2"), PropertyLoaderObj.getProperty("doctorPasswordPractice2"));
		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(),createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(),createPatient.getLastName());
		logStep("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(createPatient.getFirstName(),createPatient.getLastName());
			
		logStep("Log into Practice Portal");
		loginPage = new JalapenoLoginEnrollment(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        assertTrue(homePage.areBasicPageElementsPresent());
    
        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
//        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"DELETED", PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"));

        verifyPatientEnrollmentDeletedStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        
		log("Test Case End: The patient status in Practice 1 and Practice 3 is verified successfully when the patient is deleted from Practice 2.");
	}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeactivateP2PatientEnrolledP1P2P3() throws Throwable {
		log("Test Case:  Verify the patient status in Practice 1 and Practice 3 when the patient is deactivated from Practice 2.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
        logStep("Create the chart in Practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),personId); 
		
		logStep("Create the chart in Practice 3");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),personId);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
				
		Thread.sleep(80000);		
		logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");	
		
		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        assertTrue(homePage.areBasicPageElementsPresent());	
	
        logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getProperty("doctorLoginPractice2"), PropertyLoaderObj.getProperty("doctorPasswordPractice2"));
		log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(),createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(),createPatient.getLastName());
		log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(createPatient.getFirstName(),createPatient.getLastName());
			
		logStep("Log into Practice Portal");
		loginPage = new JalapenoLoginEnrollment(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Switching to First Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        assertTrue(homePage.areBasicPageElementsPresent());
    
        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
//        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"DEACTIVATED", PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"));

        verifyPatientEnrollmentDeactivatedStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1()); 
        
        log("Test Case End: The patient status in Practice 1 and Practice 3 is verified successfully when the patient is deactivated from Practice 2.");
	}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P1TrustedRepresentativeE1P4() throws Throwable {
		log("Test Case:  Verify the patient is able to enroll in P1 (Enterprise level) and invite the trusted representative from P4 (Practice level)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		logStep("Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");		
		String trustedperson_id=NGAPIFlows.CreatePatientinEPM(trustedPatient);
		log("Step End: Person created with id "+trustedperson_id);
		
		logStep("Waiting for welcome mail at patient inbox from practice");        
  		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	    String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url is "+visitPortal);
		
		logStep("Load login page for the Practice Portal");
		NGLoginPage loginPage = new NGLoginPage(driver,visitPortal);
		
		logStep("Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		Thread.sleep(7000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		
		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");
		
		Thread.sleep(5000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),
				PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(), PropertyLoaderObj.getPhoneNumber());
        assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
        homePage.LogoutfromNGMFPortal();		
		
		log("Test Case End:  The patient is able to enroll in P1 (Enterprise level) and invite the trusted representative from P4 (Practice level)");
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P1TrustedRepresentativeE1P2() throws Throwable {
		log("Test Case:  Verify the patient is able to enroll in P1 (Enterprise level) and invite the trusted representative from P2 (Enterprise level)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		
		logStep("Create the chart in Enterprise 1 practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());		
        NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),personId);		
		
		logStep("Create the trusted patient in NG EPM in practice 2");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");		
		String trustedperson_id=NGAPIFlows.CreatePatientinEPM(trustedPatient);
		log("Step End: Person created with id "+trustedperson_id);
		
		enrollPatientWithoutGetProcessingStatusValidation(trustedPatient,trustedperson_id,PropertyLoaderObj.getProperty("practiceName2"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		
		logStep("Waiting for welcome mail at patient inbox from practice 2");        
  		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for second Practice");
	    String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url for practice 1 is "+visitPortal);
		
	    logStep("Waiting for welcome mail at Trusted Representative patient inbox from practice 2");        
  		Thread.sleep(15000);
		log(trustedPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for second Practice");
	    String Portal2URL = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(Portal2URL, "Error: Portal link not found.");
	    log("Patient portal url for practice 2 is "+Portal2URL);		
		
		logStep("Load login page for the Practice 2 Portal");
		NGLoginPage loginPage = new NGLoginPage(driver,visitPortal);
		
		logStep("Load login page and log in to Patient 2 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		
		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
	        
		Thread.sleep(20000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		
		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		homePage = linkAccountPage.linkPatientToCreateGuardian(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "Spouse");
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		loginPage = homePage.LogoutfromNGMFPortal();
		
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();
				
		log("Test Case End: The patient is able to enroll in P1 (Enterprise level) and invite the trusted representative from P2 (Enterprise level)");
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P4TrustedRepresentativeE1P5() throws Throwable {
		log("Test Case: Verify the patient is able to invite the trusted representative from P4 and P5.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P5());
		logStep("Create the patient in NG EPM for Practice 5");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName5"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P5"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P5());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		logStep("Create the trusted patient in NG EPM for Practice 4");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");		
		String trustedperson_id=NGAPIFlows.CreatePatientinEPM(trustedPatient);
		log("Step End: Person created with id "+trustedperson_id);
		
		enrollPatientWithoutGetProcessingStatusValidation(trustedPatient,trustedperson_id,PropertyLoaderObj.getProperty("practiceName4"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P4"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		
		logStep("Waiting for welcome mail at patient inbox from practice 5");        
  		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	    String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url for practice 5 is "+visitPortal);
		
	    logStep("Waiting for welcome mail at Trusted Representative patient inbox from practice 4");        
  		Thread.sleep(15000);
		log(trustedPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	    String Portal4URL = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(Portal4URL, "Error: Portal link not found.");
	    log("Patient portal url for practice 4 is "+Portal4URL);		
		
		logStep("Load login page for the Practice 5 Portal");
		NGLoginPage loginPage = new NGLoginPage(driver,visitPortal);
		
		logStep("Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		Thread.sleep(7000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		
		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");
		
		Thread.sleep(10000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getFirstName(), PropertyLoaderObj.getPassword(),
				PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(), PropertyLoaderObj.getPhoneNumber());
        assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		Thread.sleep(5000);
		homePage = loginPage.login(trustedPatient.getFirstName(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		loginPage = homePage.LogoutfromNGMFPortal();
		
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();
		log("Test Case End: The patient is able to invite the trusted representative from P4 and P5.");
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientStatusE2P1EnrolledinE1P1() throws Throwable{
		log("Test Case:  Verify the Patient status in E2 - P1 which is enrolled in E1 - P1.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Enterprise 1 Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, personId, PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		logStep("Create the chart in Enterprise 2 practice 1");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE2(), PropertyLoaderObj.getNGEnterpiseEnrollmentE2P1());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE2P1Location(),PropertyLoaderObj.getNGE2P1Provider(),personId); 
		
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", personId);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE2P1(),PropertyLoaderObj.getProperty("integrationPracticeIDE2P1"));
		
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("E2practiceName1") + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);	
			logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	        String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
	                INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("E2practiceName1"), PortalConstants.NewPatientActivationMessageLinkText, 80);
	        assertNotNull(activationUrl, "Error: Activation link not found.");

	    logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getFirstName(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();		
				
		Thread.sleep(60000);
		logStep("Waiting for welcome mail at patient inbox from Enterprise 2 practice 1");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
		
		logStep("Load login page for the Enterprise 2 Practice 1 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getFirstName(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
    
        logStep("Verify the Multiple Practice Toggle is not displayed");
        homePage.VerifyMuiltiplePracticeToggle();
        
        String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId.trim()+"'");
//        VerifyGetPIDCCall(timestamp, person_nbr,createPatient.getFirstName(), createPatient.getLastName(),"Registered", PropertyLoaderObj.getProperty("integrationPracticeIDE2P1"));
        verifyPatientEnrollmentStatus(personId,PropertyLoaderObj.getNGEnterpiseEnrollmentE2P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE2());
        log("Test Case End: The Patient status in E2 - P1 is verified successfully which is enrolled in E1 - P1.");

	}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBCreateDependentAndGuardianP1P2P3() throws Throwable {
	log("Test Case:  Verify the patient enrollment e2e workflow when a Guardian is having an existing MF account in Enterprise 1 and practice 1");
	log("Execution Environment: " + IHGUtil.getEnvironmentType());
	log("Execution Browser: " + TestConfig.getBrowserType());
	Long timestamp = System.currentTimeMillis();
	
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
	logStep("Create the Guardian in NG EPM Practice 1");
	NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
	System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());
	String person_id= NGAPIFlows.CreatePatientinEPM(createPatient);
	log("Step End: Guardian created with id "+person_id);
	
	logStep("Create the Dependent in NG EPM Practice 1");
	NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");	
	createdependent = NGPatient.addDataToPatientDemographics(PropertyLoaderObj, createdependent);
	String dependentperson_id=NGAPIFlows.CreatePatientinEPM(createdependent);
	log("Step End: Dependent created with id "+dependentperson_id);
	
	logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
	String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	log("Step End: MF agent initiate the enrollment automatically");

	logStep("Verify the enrollment status of guardian and dependent");
	String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
	String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
	log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
	
	String getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
	String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
	log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
         
	logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
	String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
	String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
	String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
	CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
	
	verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(person_id.trim(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
	verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(dependentperson_id.trim(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));

	logStep("Create the Guardian chart in second practice");
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
	Instant testStart = Instant.now();
	NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),person_id); 
	
	logStep("Create the Dependent chart in second practice");
	NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),dependentperson_id); 
	
	logStep("Create the Guardian chart in third practice");
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
	NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),person_id);
	
	logStep("Create the Dependent chart in third practice");
	NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),dependentperson_id); 

		Mailinator mail = new Mailinator();
		Thread.sleep(15000);
		logStep("Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName1") + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName1"), PortalConstants.NewPatientActivationMessageLinkText, 80);
		assertNotNull(activationGuardianUrl,"Error: No Registeration email found with specified subject: " + INVITE_EMAIL_SUBJECT_PATIENT + PropertyLoaderObj.getProperty("practiceName1"));
		log("Step End: Guradian mail is received");
		
		logStep("Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + PropertyLoaderObj.getProperty("practiceName1")+ "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage + PropertyLoaderObj.getProperty("practiceName1"), PortalConstants.NewPatientActivationMessageLinkText, 80);
		assertNotNull(activationDependentUrl,"Error: No Registeration email found with specified subject: " + NewDependentActivationMessage + PropertyLoaderObj.getProperty("practiceName1"));
		log("Step End: Dependent mail is received");
		
		logStep("Enroll the Guardian to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationGuardianUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		String patientLogin=createPatient.getEmailAddress();
		logStep("Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened");
		assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(activationDependentUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createdependent.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYearUnderage());
		
		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(), createdependent.getEmailAddress());
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, PropertyLoaderObj.getPassword(), "Parent");

		logStep("Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));
			
		Thread.sleep(120000);
		logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");
		log("Waiting for welcome mail at patient inbox from second practice");
        Email visitPortal = new Mailer(createdependent.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT,90,
				testSecondsTaken(testStart));
		assertNotNull(visitPortal,"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("patient portal url for Portal 2 is "+portalUrlLink);
		
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
			
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver,portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
			   
        logStep("Detecting if Home Page is opened");
        assertTrue(homePage.areBasicPageElementsPresent());
        	
        logStep("Switching to Second Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
		assertTrue(homePage.assessFamilyAccountElements(true));
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(40000);
        assertTrue(homePage.areBasicPageElementsPresent());
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
        Thread.sleep(20000);
        assertTrue(homePage.areBasicPageElementsPresent());
        
        logStep("Switching to dependent account to verify auto enrollment");
        homePage.faChangePatient();
        Thread.sleep(10000);
        assertTrue(homePage.areBasicPageElementsPresent());
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
        Thread.sleep(40000);
		assertTrue(homePage.assessFamilyAccountElements(true));
        driver.navigate().refresh();
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(40000);
        assertTrue(homePage.areBasicPageElementsPresent());
        logStep("Auto Enrolment of Guardian and Dependent to Second and third Practice is completed");
		
//		VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered");
		verifyPatientEnrollmentStatus(person_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(dependentperson_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(person_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(dependentperson_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(person_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
        verifyPatientEnrollmentStatus(dependentperson_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());       
		
        log("Test Case End: The patient enrollment e2e workflow when a Guardian is having an existing MF account in Enterprise 1 and practice 1");
		}
	
	@Test(enabled = true, groups = {"acceptance-EnterpriseEnrollment"}, retryAnalyzer = RetryAnalyzer.class)
	public void testBCreateGuardianAndDependentANDEnrollOnlyDependentToP1P2P3() throws Throwable {
		log("Test Case: Verify the patient auto enrollment in P2 and P3 when a Guardian doesnot have an existing MF account in E1 and P1");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		Long timestamp = System.currentTimeMillis();
		
		String guardianFirstName = "Guardian" + (new Date()).getTime();
		String guardianLastName = "Guardian" + (new Date()).getTime();
		System.setProperty("ParentEmailAddress", guardianFirstName+ "@mailinator.com");	

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the Dependent in NG EPM Practice 1");
		NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");	
		createdependent = NGPatient.addDataToPatientDemographics(PropertyLoaderObj, createdependent);
		String dependentperson_id=NGAPIFlows.CreatePatientinEPM(createdependent);
		log("Step End: Dependent created with id "+dependentperson_id);
		
		logStep("Using Post Enrollment call, Verify the MF agent trigger for dependent");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of dependent");		
		String getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	              
		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(dependentperson_id.trim(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),PropertyLoaderObj.getNGEnterpiseEnrollmentintegrationPracticeIDE1P1());
		
		logStep("Create the Dependent chart in second practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),dependentperson_id); 
		
		logStep("Create the Dependent chart in third practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),dependentperson_id); 

			Mailinator mail = new Mailinator();
			Thread.sleep(15000);		
			logStep("Verify the dependent mail");
			Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			log("Step End: Dependent mail is received");
			
			logStep("Enroll the Dependent to MedFusion Portal");
			PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationDependentUrl);
			Thread.sleep(3000);
			AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
					createdependent.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYearUnderage());
			
			logStep("Continue registration - check dependent info and fill login credentials");
			linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(), createdependent.getEmailAddress());
			SecurityDetailsPage accountDetailsPage= linkAccountPage.continueToCreateGuardianOnly(guardianFirstName, guardianLastName, "Parent");

			logStep("Finishing of dependent activation: Filling patient data");
			JalapenoHomePage jalapenoHomePage =
					accountDetailsPage.fillAccountDetailsAndContinue(createdependent.getEmailAddress(),PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());
			
			logStep("Detecting if Home Page is opened");
			Thread.sleep(2000);
			Assert.assertTrue(jalapenoHomePage.isHomeButtonPresent(driver));		
			
			logStep("Logout from Portal");
			Thread.sleep(9000);
			NGLoginPage loginPage =jalapenoHomePage.LogoutfromNGMFPortal();
				
//			VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered");
			
			logStep("Verify the enrollment and processing status of dependent in pxp_enrollment table");
			String dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
	        
			if(dependentprocessing_status.equals("3")){
				log("Processing status is "+dependentprocessing_status+" i.e. Request is in progress");
				for (int i = 0; i < arg_timeOut; i++) {
					dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		            if (dependentprocessing_status.equals("4")) {
		            	log("Step End: Processing status is "+dependentprocessing_status+" i.e. Processing status is completed");
		                break;
		            } else {
		                if (i == arg_timeOut - 1)
		                    Thread.sleep(1000);
		            }
		        }
			}
			CommonUtils.VerifyTwoValues(dependentprocessing_status,"equals","4");
			enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
			CommonUtils.VerifyTwoValues(enrollment_status1,"equals","9");
		
			logStep("Verify the enrollment status of dependent in Practice 1");
			getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
			GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
			
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
			log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
			
			Thread.sleep(60000);
			logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");
	        Email visitPortal = new Mailer(createdependent.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT,90,
					testSecondsTaken(testStart));
			assertNotNull(visitPortal,"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
			String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
			log("patient portal url for Portal 2 is "+portalUrlLink);
			
			assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
				
			loginPage = new NGLoginPage(driver,portalUrlLink);
			JalapenoHomePage homePage = loginPage.login(createdependent.getEmailAddress(), PropertyLoaderObj.getPassword());
				   
	        logStep("Detecting if Home Page is opened");
	        assertTrue(homePage.areBasicPageElementsPresent());
		
	        logStep("Switching to Second Practice to verify auto enrollment");
	        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName1"));
	        Thread.sleep(40000);
            driver.navigate().refresh();
	        jalapenoHomePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
	        Thread.sleep(40000);
            driver.navigate().refresh();
	        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName3"));
	        assertTrue(homePage.areBasicPageElementsPresent());
	        log("Auto Enrolment of Dependent to Second Practice is completed");						
			
	        verifyPatientEnrollmentStatus(dependentperson_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P3(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
	        verifyPatientEnrollmentStatus(dependentperson_id,PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),PropertyLoaderObj.getNGEnterpiseEnrollmentE1());
	        log("Test Case End: The patient enrolled successfully in P2 and P3 when a Guardian doesnot have an existing MF account in E1 and P1");
	}

	public String enrollPatientWithoutGetProcessingStatusValidation(NewPatient createPatient,String person_id,String practiceName,String integrationID, String enterprise_id,String practice_id) throws Throwable{
		logStep("Enrollling the patient in practice portal for "+practiceName);
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
				
     	verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(person_id,practice_id,integrationID);
		
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + practiceName + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	        String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
	                INVITE_EMAIL_SUBJECT_PATIENT + practiceName, PortalConstants.NewPatientActivationMessageLinkText, 80);
	        assertNotNull(activationUrl, "Error: Activation link not found.");

	    logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();
		
		verifyPatientEnrollmentStatus(person_id,practice_id,enterprise_id); 
		log("Patient is enrolled successfully to "+practiceName);
		return person_id;
	}
	
	public String enrollNGPatienttoMFPortal(NewPatient createPatient,String person_id,String practiceName,String integrationID) throws Throwable{
		Long timestamp1 = System.currentTimeMillis();
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		String person_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id.trim()+"'");
		
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		
		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		if(processing_status.equals("1")){
			log("Processing status is "+processing_status+" i.e. First time enrollment record is inserted into table.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("2")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		CommonUtils.VerifyTwoValues(processing_status,"equals","2");		
        
		logStep("Verify the status of MF agent job");
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
		    log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
			log("Registration URL is received");
			}
		else{
			log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = (select top 1 processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+" order by createdts desc')");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = (select top 1 processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+" order by createdts desc')");
		
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		logStep("Do get processing status call and verify the processing time of registration mail to be received");

		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		logStep("Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
		
		if(emailStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
	            	log("Step End: Mail sent to patient successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(emailStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+person_nbr.trim().replace("\t", "")+"')");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		log("Step End: The processing status of MF agent job is "+jobStatus+" registration mail is sent to patient for enrollment");		
		
		logStep("Verify the processing status of patient in pxp_enrollment table");
		processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
        
		if(processing_status.equals("2")){
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("3")) {
	            	log("Step End: Processing status is "+processing_status+", RSDK has sent email to patient.");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
        CommonUtils.VerifyTwoValues(processing_status,"equals","3");
        enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		log("Step End: Enrollment status is "+enrollment_status);
		
		Mailinator mail = new Mailinator();
		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT + practiceName + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
        String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
                INVITE_EMAIL_SUBJECT_PATIENT + practiceName, PortalConstants.NewPatientActivationMessageLinkText, 80);
        assertNotNull(activationUrl, "Error: Activation link not found.");

        logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
	    PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
	    Thread.sleep(3000);
	    SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
			createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

	    logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
	    JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

	    logStep("Detecting if Home Page is opened and logout");
	    jalapenoHomePage.LogoutfromNGMFPortal();
	
	    logStep("Do a GET on PIDC Url to get registered patient");
		Long since = timestamp1 / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getPIDCURL().replaceAll("integrationID", integrationID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		Thread.sleep(2000);
		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");

		verifyProcessingStatusto4(person_id);
		
		logStep("Find the patient and check if he is registered");
        String GetEnrollmentStatusresponse2 =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"),"equals","Completed"); 
		log("Result: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
		
		logStep("Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status2,"equals","9");
		String processing_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(processing_status2,"equals","4");
		log("Step End: Patient enrollment status is "+enrollment_status2+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
			
		return person_id;
	}
	
	public void verifyPatientEnrollmentStatus(String personId,String practice_id,String enterprise_id) throws Throwable{
		logStep("Find the patient and check if he is registered");
		 String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
		  if(processing_status.equals("3")){
			log("Processing status is "+processing_status+" i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
	            if (processing_status.equals("4")) {
	            	log("Step End: Processing status is "+processing_status+" i.e. Processing status is completed");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		  }
		  
		logStep("Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
		CommonUtils.VerifyTwoValues(enrollment_status2,"equals","9");
		processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
		CommonUtils.VerifyTwoValues(processing_status,"equals","4");
		
	    NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterprise_id, practice_id);
        String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
        String GetEnrollmentStatusresponse2 =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
        CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"status"),"equals","9"); 
   	    CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"),"equals","Completed"); 
	    log("Result: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
	    log("Step End: Patient enrollment status is "+enrollment_status2+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse2,"statusDescription"));
	}
	
	public void verifyPatientEnrollmentDeletedStatus(String personId,String practice_id,String enterprise_id) throws Throwable{
	logStep("Verify the enrollment and processing status of patient in pxp enrollment table for practice_id "+practice_id);	
	String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
	if(processing_status.equals("3")||processing_status.equals("4")){
		log("Processing status is "+processing_status+" RSDK is processing the request");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
            if (processing_status.equals("5")) {
            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is deleted successfully.");
                break;
            } else {
                if (i == arg_timeOut - 1)
                    Thread.sleep(1000);
            }
        }
	}
	
	CommonUtils.VerifyTwoValues(processing_status,"equals","5");
	String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
	CommonUtils.VerifyTwoValues(enrollment_status,"equals","11");				
    
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterprise_id, practice_id);
	logStep("Verify the enrollment status of patient for practice_id "+practice_id);
	String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
	String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","11"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Account Deleted"); 
	log("Step End: Patient enrollment status is "+enrollment_status+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
	}
	
	public void verifyPatientEnrollmentDeactivatedStatus(String personId,String practice_id,String enterprise_id) throws Throwable{
	logStep("Verify the enrollment and processing status of patient in pxp enrollment table for practice_id "+practice_id);
	String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
    if(processing_status.equals("3")||processing_status.equals("4")){
		log("Processing status is "+processing_status+" RSDK is processing the request");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
            if (processing_status.equals("6")) {
            	log("Step End: Processing status is "+processing_status+" i.e. Enrollment record is deactivated successfully.");
                break;
            } else {
                if (i == arg_timeOut - 1)
                    Thread.sleep(1000);
            }
        }
	}
	
	CommonUtils.VerifyTwoValues(processing_status,"equals","6");
	String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+personId.trim()+"' and practice_id='"+practice_id+"'");
	CommonUtils.VerifyTwoValues(enrollment_status,"equals","10");				
    
	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterprise_id, practice_id);
	logStep("Verify the enrollment status of patient for practice_id "+practice_id);
	String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
	String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","10"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Deactivated"); 
	log("Step End: Patient enrollment status is "+enrollment_status+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
    }
	
	private long testSecondsTaken(Instant testStart) {
		return testStart.until(Instant.now(), ChronoUnit.SECONDS);
	}
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDPracticeLevelEnrollmentOnDemandCCD() throws Throwable{		
		log("Test Case: Verify the patient is able to receive the CCD after the enrollment of patient and ON Demand CCD at Practice Level");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGMainPracticeID"));

		String locationName = PropertyLoaderObj.getProperty("EPMLocationName");
		String providerName = PropertyLoaderObj.getProperty("EPMProviderName");
		String enterpriseId = PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		String practiceId = PropertyLoaderObj.getProperty("NGMainPracticeID");
		String integrationPracticeId = PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		
		logStep("Created the patient in NG EPM Practice "+practiceId);
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String person_id= NGAPIFlows.CreatePatientinEPM(createPatient);	
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, person_id, PropertyLoaderObj.getProperty("practiceName"),integrationPracticeId,enterpriseId, practiceId);
		
		Thread.sleep(40000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);
		
		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("url"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
		CommonFlows.addDataToCCD(locationName, providerName, person_id, practiceId);
	
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight"+since);
		
		Thread.sleep(5000);
		logStep("Request for OnDemand CCD");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("url"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeId) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id,practiceId, integrationPracticeId, 2);
		
		logStep("Verify OnDemand CCD is received by patient");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("url"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"EncounterHavingALLData","");

		log("Test Case End: The patient is able to receive the Enrollment and ON Demand CCD (Practice Level) successfully");		
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDGuardianDependentCCD() throws Throwable{
		log("Test Case : Verify the guardian is able to request for dependent CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		Long timestamp = System.currentTimeMillis();
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1"), PropertyLoaderObj.getProperty("NGEnterprise1Practice1"));
		logStep("Create the Guardian in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());
		
		ObjectMapper objMap = new ObjectMapper();
	    String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
	    log("Guardian Request Body is \n" + requestbody);
		
	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Guardian created with id "+person_id);
		
		logStep("Create the Dependent in NG EPM");
		NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(PropertyLoaderObj, createdependent);
	    String dependentrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createdependent);
	    log("Dependent Request Body is \n" + dependentrequestbody);
			
		String dependentperson_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,dependentrequestbody , 201);
		log("Step End: Dependent created with id "+dependentperson_id);
		
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		
		PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of guardian and dependent");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		String getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	         
		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
		String enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status,"equals","1");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(person_id.trim(),PropertyLoaderObj.getProperty("NGEnterprise1Practice1"), PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
		verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(dependentperson_id.trim(),PropertyLoaderObj.getProperty("NGEnterprise1Practice1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"));
		
			Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			logStep("Verify the Guardian mail");
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			log("Step End: Guradian mail is received");
			
			logStep("Verify the dependent mail");
			Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
			log("Step End: Dependent mail is received");
			
			logStep("Enroll the Guardian to MedFusion Portal : step 1 - verifying identity");
			PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationGuardianUrl);
			Thread.sleep(3000);
			SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
					createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());

			String patientLogin=createPatient.getEmailAddress();
			logStep("Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
			JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

			logStep("Detecting if Home Page is opened");
			assertTrue(jalapenoHomePage.areBasicPageElementsPresent());

			logStep("Identify Dependent without logging out the patient");
			patientVerificationPage.getToThisPage(activationDependentUrl);
			AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
					createdependent.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYearUnderage());
			
			logStep("Continue registration - check dependent info and fill login credentials");
			linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(), createdependent.getEmailAddress());
			jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, PropertyLoaderObj.getPassword(), "Parent");

			logStep("Continue to the portal and check elements");
			assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

			logStep("Logout, login and change patient");
			NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();
			jalapenoHomePage = loginPage.login(patientLogin, PropertyLoaderObj.getPassword());
			jalapenoHomePage.faChangePatient();
			assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));
			loginPage = jalapenoHomePage.LogoutfromNGMFPortal();
			
			logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
			String dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
			String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
			
			if(dependentprocessing_status.equals("3")){
				log("Processing status is "+dependentprocessing_status+" i.e. Request is in progress");
				for (int i = 0; i < arg_timeOut; i++) {
					dependentprocessing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		            if (dependentprocessing_status.equals("4")) {
		            	log("Step End: Processing status is "+dependentprocessing_status+" i.e. Processing status is completed");
		                break;
		            } else {
		                if (i == arg_timeOut - 1)
		                    Thread.sleep(1000);
		            }
		        }
			}
			CommonUtils.VerifyTwoValues(processing_status,"equals","4");
			CommonUtils.VerifyTwoValues(dependentprocessing_status,"equals","4");
			enrollment_status =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
			CommonUtils.VerifyTwoValues(enrollment_status,"equals","9");
			enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
			CommonUtils.VerifyTwoValues(enrollment_status1,"equals","9");
			
			logStep("Verify the enrollment status of guardian and dependent");
			getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
			GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
			
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","9"); 
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Completed"); 
			log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
			
			getEnrollmentURL1 =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
			GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
			
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
			log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
			
		String locationName =PropertyLoaderObj.getProperty("NGE1P1Location"); 
		String providerName =PropertyLoaderObj.getProperty("NGE1P1Provider");	
		String practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice1");

//		CommonFlows.addDataToCCD(locationName, providerName, dependentperson_id, practiceId);

		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, dependentperson_id, PropertyLoaderObj.getProperty("NGEnterprise1Practice1"), PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"), 1);
		
		logStep("Verify Dependent is able to receive Enrollment CCD");
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "Dependent","");
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight"+since);
		
		logStep("Verify Guardian is able to request dependent On Demand CCD");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"Dependent","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", PropertyLoaderObj.getProperty("integrationPracticeIDE1P1")) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, dependentperson_id, PropertyLoaderObj.getProperty("NGEnterprise1Practice1"), PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"), 2);
		
		logStep("Verify Dependent is able to receive OnDemand CCD");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"Dependent","");

		log("Test Case End: The Guardian is able to receive On-Demand CCD of dependent successfully");
	}
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDTrustedRepresentativeWithoutMFAccount() throws Throwable {
		log("Test Case:  Verify the trusted representative is able to request for On demand CCD (Without Medfusion Account)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		logStep("Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");		
		String trustedperson_id=NGAPIFlows.CreatePatientinEPM(trustedPatient);
		log("Step End: Person created with id "+trustedperson_id);
		
		logStep("Waiting for welcome mail at patient inbox from practice");        
  		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for first Practice");
	    String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url is "+visitPortal);
		
		logStep("Load login page for the Practice Portal");
		NGLoginPage loginPage = new NGLoginPage(driver,visitPortal);
		
		logStep("Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		Thread.sleep(7000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		
		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");
		
		Thread.sleep(5000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),
				PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(), PropertyLoaderObj.getPhoneNumber());
        assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
        homePage.LogoutfromNGMFPortal();		

		String locationName1 =PropertyLoaderObj.getProperty("NGE1P1Location"); String providerName1 =PropertyLoaderObj.getProperty("NGE1P1Provider");	
		String practiceId1 = PropertyLoaderObj.getProperty("NGEnterprise1Practice1"); String integrationPracticeId1 =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");

//		CommonFlows.addDataToCCD(locationName1, providerName1, trustedperson_id, practiceId1);

		logStep("Verify Enrollment CCD is received by Trusted Representative for Practice 1");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId, practiceId1, integrationPracticeId1, 1);
		
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("MFPortalURLPractice1"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight1 = LocalTime.MIDNIGHT;
		LocalDate today1 = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight1 = LocalDateTime.of(today1, midnight1);
		ZonedDateTime zdt1 = todayMidnight1.atZone(ZoneId.of("America/New_York"));
		long since1 = zdt1.toInstant().toEpochMilli() / 1000;
		log("midnight"+since1);
		
		Thread.sleep(60000);
		logStep("Request On Demand CCD for Practice 1");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeId1) + "?since=" + since1 + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(80000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId,practiceId1, integrationPracticeId1, 2);
		
		logStep("Verify OnDemand CCD is received by Trusted Representative for Practice 1");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");	

		log("Test Case End: The trusted representative (Without Medfusion Account) is able to request for On demand CCD successfully");
 }
	  
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDMSUCCD() throws Throwable{
		log("Test Case:  Verify the Patient is able to receive MSU CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		String enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		String practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
	    String integrationPracticeId =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
	    String url = PropertyLoaderObj.getProperty("url");	
	    String locationName =PropertyLoaderObj.getProperty("EPMLocationName"); 
	    String providerName =PropertyLoaderObj.getProperty("EPMProviderName");
	    String practiceName =PropertyLoaderObj.getProperty("practiceName");
	    
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		logStep("Create the patient in NG EPM in Practice 1");
	    NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		ObjectMapper objMap = new ObjectMapper();
	    String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
	    log("Request Body is \n" + requestbody);

	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,person_id,practiceName,integrationPracticeId,enterpriseId, practiceId);
		
	    Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);
		
		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, url,createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		logStep("Add Chart to patient");
		NGAPIFlows.addCharttoProvider(locationName,providerName,person_id); 
		
		logStep("Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id);
		
		logStep("Request for MSU CCD");
		NGAPIFlows.PostCCDRequest(locationName,providerName, person_id,"MedicalSummaryUtility", encounter_id);
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 5);
		
		logStep("Verify the patient is able to receive MSU CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, url,createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		log("Test Case End: The Patient is able to receive MSU CCD successfully");
	    }

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDLockedEncounterCCD() throws Throwable{
		log("Test Case:  Verify the Patient is able to receive Locked Encounter CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		String enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		String practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
	    String integrationPracticeId =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
	    String url = PropertyLoaderObj.getProperty("url");	
	    String locationName =PropertyLoaderObj.getProperty("EPMLocationName"); 
	    String providerName =PropertyLoaderObj.getProperty("EPMProviderName");
	    String practiceName =PropertyLoaderObj.getProperty("practiceName");
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		logStep("Create the patient in NG EPM in Practice 1");
	    NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		ObjectMapper objMap = new ObjectMapper();
	    String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
	    log("Request Body is \n" + requestbody);

	    apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,person_id,practiceName,integrationPracticeId,enterpriseId, practiceId);
		
	    Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);
		
		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, url,createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		logStep("Adding Test data to patient CCD "+person_id);
		   Log4jUtil.log("Step Begins: Add Chart to patient");
		   NGAPIFlows.addCharttoProvider(locationName,providerName,person_id); 
		
		   Log4jUtil.log("Step Begins: Add Encounter to patient chart");
		   String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id); 
		
		   Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		   String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);
		
		   Log4jUtil.log("Step Begins: Add Medication to created encounter");
		   String medication_id = NGAPIFlows.addMedication(practiceId,locationName,providerName, "Active", person_id, encounter_id,"R07.9",286939);
		
		   Log4jUtil.log("Step Begins: Add allergy to created encounter");
		   String allergy_id = NGAPIFlows.addAllergy(locationName,providerName,person_id, encounter_id,"1000",2);
		
		   Log4jUtil.log("Step Begins: Add Problem to patient chart");
		   String problem_id = NGAPIFlows.addProblem(locationName,providerName,  person_id,"420543008","55561003","Active");

		   Log4jUtil.log("Step Begins: Add Immunization to created encounter");
		   String immunization_id = NGAPIFlows.addNewImmunizationsOrder(locationName,providerName,person_id,encounter_id);
		
		   Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
		   String labOrder_id = NGAPIFlows.addLabOrder(locationName,providerName, person_id, encounter_id);		
		   String labOrderTest_id = NGAPIFlows.addLabOrderTest(person_id, labOrder_id,"NG001032");
		   String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
		   String labResult_id = NGAPIFlows.addLabResult(person_id, ObsPanel_id,"75325-1");
		   String updatelabOrder_id = NGAPIFlows.updateLabOrder(locationName, providerName, person_id, labOrder_id);
		   Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter "+encounter_id);
		   
		logStep("Request for Locked Encounter CCD");
		NGAPIFlows.PostCCDRequest(locationName,providerName, person_id,"LockedEncounter", encounter_id);
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 4);
		
		logStep("Verify the patient is able to receive Locked Encounter CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, url,createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "EncounterHavingALLData","");
		log("Test Case End: The Patient is able to receive Locked Encounter CCD successfully");
	    }
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDUnSignedOffLabResultInCCD() throws Throwable{
		log("Test Case: Verify the Un signed off Lab results are not displayed in CCD.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		String locationName =PropertyLoaderObj.getNGE1P1Location();
		String providerName =PropertyLoaderObj.getNGE1P1Provider();
        String practiceId =PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1();
        String integrationPracticeId =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");	
        String enterpriseID =PropertyLoaderObj.getNGEnterpiseEnrollmentE1();
        String practiceName = PropertyLoaderObj.getProperty("practiceName1");
        String URL = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		String person_id = createCommonPatient(PropertyLoaderObj,enterpriseID,practiceId, practiceName, integrationPracticeId);
		String username = DBUtils.executeQueryOnDB("NGCoreDB","select email_address from person where person_id = '"+person_id+"'");
		
		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);
		
		logStep("Verify the patient is able to receive CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL,username, PropertyLoaderObj.getPassword(), "","");
		
		logStep("Adding Test data to patient CCD "+person_id);
		  Log4jUtil.log("Step Begins: Add Chart to patient");
		  NGAPIFlows.addCharttoProvider(locationName,providerName,person_id); 
		
		  Log4jUtil.log("Step Begins: Add Encounter to patient chart");
		  String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id);
		  
		  Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		  String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);
		
		  Log4jUtil.log("Step Begins: Add Medication to created encounter");
		  String medication_id = NGAPIFlows.addMedication(practiceId,locationName,providerName, "Active", person_id, encounter_id,"R07.9",286939);
		
		  Log4jUtil.log("Step Begins: Add allergy to created encounter");
		  String allergy_id = NGAPIFlows.addAllergy(locationName,providerName,person_id, encounter_id,"1000",2);
		
		  Log4jUtil.log("Step Begins: Add Problem to patient chart");
		  String problem_id = NGAPIFlows.addProblem(locationName,providerName,  person_id,"420543008","55561003","Active");
			
		  Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
		  String labOrder_id = NGAPIFlows.addLabOrder(locationName,providerName, person_id, encounter_id);		
		  String labOrderTest_id = NGAPIFlows.addLabOrderTest(person_id, labOrder_id,"NG001032");
		  String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
		  String labResult_id = NGAPIFlows.addLabResult(person_id, ObsPanel_id,"75325-1");
		  Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter without signing off lab order"+encounter_id);
		
		logStep("Request for Locked Encounter CCD");
		NGAPIFlows.PostCCDRequest(locationName,providerName, person_id,"LockedEncounter", encounter_id);
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 4);
		
		logStep("Verify the patient is able to receive Locked Encounter CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL,username, PropertyLoaderObj.getPassword(), "HavingUnSignedOffResult","");
		log("Test Case End: The Un signed off Lab results are not displayed in CCD.");
	}
	
	public String createCommonPatient(PropertyFileLoader PropertyLoaderObj,String enterpriseID,String practiceID,String practiceName,String integrationPacticeID) throws NullPointerException, Throwable{
		String person_id = null;
		try{
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseID, practiceID);
			Log4jUtil.log("Create the Patient in NG EPM");
			NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		    String requestbody = new ObjectMapper().defaultPrettyPrintingWriter().writeValueAsString(createPatient);
		    Log4jUtil.log("Common Patient Request Body is \n" + requestbody);
			String finalURL =EnterprisebaseURL + apiRoutes.valueOf("AddEnterprisePerson").getRouteURL();	
			person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Step End: Common Patient created with id "+person_id);
			
			enrollPatientWithoutGetProcessingStatusValidation(createPatient, person_id, practiceName,integrationPacticeID,enterpriseID, practiceID); 
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
	    }
	return person_id;
	}
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDSensitiveMSUONDemandLockedEncounterCCD() throws Throwable{
		log("Test Case: Verify the details are not there when the Encounter is sensitive.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
				
		String locationName =PropertyLoaderObj.getNGE1P1Location();
		String providerName =PropertyLoaderObj.getNGE1P1Provider();
		String practiceId =PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1();
		String integrationPracticeId =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
		String enterpriseID =PropertyLoaderObj.getNGEnterpiseEnrollmentE1();
		String practiceName = PropertyLoaderObj.getProperty("practiceName1");
		String URL = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseID, practiceId);		
		String person_id = createCommonPatient(PropertyLoaderObj,enterpriseID,practiceId, practiceName, integrationPracticeId);
		String username = DBUtils.executeQueryOnDB("NGCoreDB","select email_address from person where person_id = '"+person_id+"'");
		
		Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);
		
		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, URL,username, PropertyLoaderObj.getPassword(), "","");
		
		Log4jUtil.log("Step Begins: Add Chart to patient");
		NGAPIFlows.addCharttoProvider(locationName,providerName,person_id);
		   
		   logStep("Add Encounter to patient chart");		
		   String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id); 
		
		   Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		   String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);
		
		   Log4jUtil.log("Step Begins: Add Medication to created encounter");
		   String medication_id = NGAPIFlows.addMedication(practiceId,locationName,providerName, "Active", person_id, encounter_id,"R07.9",286939);
		
		   Log4jUtil.log("Step Begins: Add allergy to created encounter");
		   String allergy_id = NGAPIFlows.addAllergy(locationName,providerName,person_id, encounter_id,"1000",2);
		
		   Log4jUtil.log("Step Begins: Add Problem to patient chart");
		   String problem_id = NGAPIFlows.addProblem(locationName,providerName,  person_id,"420543008","55561003","Active");

		   Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
		   String labOrder_id = NGAPIFlows.addLabOrder(locationName,providerName, person_id, encounter_id);		
		   String labOrderTest_id = NGAPIFlows.addLabOrderTest(person_id, labOrder_id,"NG001032");
		   String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
		   String labResult_id = NGAPIFlows.addLabResult(person_id, ObsPanel_id,"75325-1");
		   String updatelabOrder_id = NGAPIFlows.updateLabOrder(locationName, providerName, person_id, labOrder_id);
		   Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter "+encounter_id);
		
		NGAPIFlows.updateToSensitiveEncounter(locationName,providerName, person_id, encounter_id);
//		NGAPIFlows.checkINEncounter(person_id, encounter_id);
//		NGAPIFlows.checkOutEncounter(person_id, encounter_id);
		
//		logStep("Request for Locked Encounter CCD");
//		NGAPIFlows.PostCCDRequest(locationName,providerName, person_id,"LockedEncounter", encounter_id);
//				
//		Thread.sleep(60000);
//		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 4);
//		
//		logStep("Verify the patient is able to receive Locked Encounter CCD");
//		Thread.sleep(10000);
//		CommonFlows.IsCCDReceived(driver,URL,username, PropertyLoaderObj.getPassword(), "HavingSensitiveEncounterMSU","");
		
		logStep("Request for MSU CCD");
		NGAPIFlows.PostCCDRequest(locationName,providerName, person_id,"MedicalSummaryUtility", encounter_id);
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id, practiceId, integrationPracticeId, 5);
		
		logStep("Verify the patient is able to receive MSU CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver,URL,username, PropertyLoaderObj.getPassword(), "HavingSensitiveEncounterMSU","");
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight"+since);
		
		Thread.sleep(5000);
		logStep("Request for OnDemand CCD");
		CommonFlows.requestCCD(driver,URL,username, PropertyLoaderObj.getPassword(),"","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeId) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id,practiceId, integrationPracticeId, 2);
		
		logStep("Verify OnDemand CCD is received by patient");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL,username, PropertyLoaderObj.getPassword(), "HavingSensitiveEncounterONDemand","");

		log("Test Case End: The details are not present when the Encounter is sensitive.");
	}
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDMultiPracticeCCDP1P2ANDOnDemandCCDTrustedRepresentativeWithMFAccount() throws Throwable {
		log("Test Case: Verify the trusted representative are able to request for On demand CCD (With Medfusion Account)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"complete");
		String personId= NGAPIFlows.CreatePatientinEPM(createPatient);
		
		enrollPatientWithoutGetProcessingStatusValidation(createPatient,personId,PropertyLoaderObj.getProperty("practiceName1"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P1"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		
		logStep("Create the chart in Enterprise 1 practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());		
        NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P2Location(),PropertyLoaderObj.getNGE1P2Provider(),personId);		
		
		logStep("Create the trusted patient in NG EPM in practice 2");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"trustedPatient");	
		trustedPatient = NGPatient.addDataToPatientDemographics(PropertyLoaderObj, trustedPatient);
		String trustedperson_id=NGAPIFlows.CreatePatientinEPM(trustedPatient);
		log("Step End: Person created with id "+trustedperson_id);
		
		enrollPatientWithoutGetProcessingStatusValidation(trustedPatient,trustedperson_id,PropertyLoaderObj.getProperty("practiceName2"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		
		logStep("Waiting for welcome mail at patient inbox from practice 2");        
  		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for second Practice");
	    String visitPortal = new Mailinator().getLinkFromEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(visitPortal, "Error: Portal link not found.");
	    log("Patient portal url for practice 1 is "+visitPortal);
		
	    logStep("Waiting for welcome mail at Trusted Representative patient inbox from practice 2");        
  		Thread.sleep(15000);
		log(trustedPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   " + WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);	
		logStep("Logging into Mailinator and getting Patient Activation url for second Practice");
	    String Portal2URL = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
	    assertNotNull(Portal2URL, "Error: Portal link not found.");
	    log("Patient portal url for practice 2 is "+Portal2URL);		
		
		logStep("Load login page for the Practice 2 Portal");
		NGLoginPage loginPage = new NGLoginPage(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice2"));
		
		logStep("Load login page and log in to Patient 2 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		
		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
	        
		Thread.sleep(20000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();
		
		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(),trustedPatient.getLastName(),trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new Mailinator().getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		
		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(), trustedPatient.getEmailAddress());
		homePage = linkAccountPage.linkPatientToCreateGuardian(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "Spouse");
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		loginPage = homePage.LogoutfromNGMFPortal();
		
		homePage = loginPage.login(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();

		String locationName =PropertyLoaderObj.getProperty("NGE1P2Location"); String providerName =PropertyLoaderObj.getProperty("NGE1P2Provider");	
		String practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice2"); String integrationPracticeId =PropertyLoaderObj.getProperty("integrationPracticeIDE1P2");

		logStep("Verify Enrollment CCD is received by Trusted Representative");
//		CommonFlows.addDataToCCD(locationName, providerName, trustedperson_id, practiceId);
		
		log("Test Case : Verify the patient is able to receive CCD from both practices P1 and P2");
		
		String locationName1 =PropertyLoaderObj.getProperty("NGE1P1Location"); String providerName1 =PropertyLoaderObj.getProperty("NGE1P1Provider");	
		String practiceId1 = PropertyLoaderObj.getProperty("NGEnterprise1Practice1"); String integrationPracticeId1 =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");

//		CommonFlows.addDataToCCD(locationName1, providerName1, personId, practiceId1);

		logStep("Verify Enrollment CCD is received by Patient for Practice 1");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId, practiceId1, integrationPracticeId1, 1);
		
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight1 = LocalTime.MIDNIGHT;
		LocalDate today1 = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight1 = LocalDateTime.of(today1, midnight1);
		ZonedDateTime zdt1 = todayMidnight1.atZone(ZoneId.of("America/New_York"));
		long since1 = zdt1.toInstant().toEpochMilli() / 1000;
		log("midnight"+since1);
		
		logStep("Request On Demand CCD for Practice 1");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeId1) + "?since=" + since1 + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personId+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(50000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId,practiceId1, integrationPracticeId1, 2);
		
		logStep("Verify OnDemand CCD is received by Patient for Practice 1");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice1"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");	
		log("Patient received CCD successfully for Practice 1");
		
		logStep("Verify Enrollment CCD is received by Patient for Practice 2");
        CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId, practiceId, integrationPracticeId, 1);
		
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("MFPortalURLPractice2"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
		logStep("Request OnDemand CCD for Practice 2");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice2"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");
		
		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, personId,practiceId, integrationPracticeId, 2);
		
		logStep("Verify OnDemand CCD is received by Patient for Practice 2");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice2"),createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");

		log("Test Case End: The patient is able to receive CCD from both practices P1 and P2");
		
        CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, trustedperson_id, practiceId, integrationPracticeId, 1);
		
		CommonFlows.IsCCDReceived(driver, PropertyLoaderObj.getProperty("MFPortalURLPractice2"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), "","");
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight"+since);
		
		logStep("Request for OnDemand CCD for Trusted Representative");
		CommonFlows.requestCCD(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice2"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");
		
		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername2"),PropertyLoaderObj.getProperty("oAuthPassword2"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeId) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		String trustedperson_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+trustedperson_id+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), trustedperson_nbr.trim().replace("\t", ""));
		
		Thread.sleep(50000);
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, trustedperson_id,practiceId, integrationPracticeId, 2);
		
		logStep("Verify OnDemand CCD is received by Trusted Representative");
		CommonFlows.IsCCDReceived(driver,PropertyLoaderObj.getProperty("MFPortalURLPractice2"),trustedPatient.getEmailAddress(), PropertyLoaderObj.getPassword(),"","");

		log("Test Case End: The trusted representative is able to request successfully for On demand CCD (With Medfusion Account)");
		}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMHighPrioritySecureMessageDoNotAddToEncounterSendByPracticeUser() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with 'high priority' flag on and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button and patient reply to message sent by Practice User");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());	
		
		logStep("Getting Existing User");
	    String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");	
	    String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
	    logStep("Compose Message with High Priority and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"HighPriority",person_id,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","","");
    	
    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";
    	String rootThreadIdQuery ="select root_thread_id from ngweb_communications where comm_id ='"+comm_id+"'";
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	String rootThreadId = DBUtils.executeQueryOnDB("NGCoreDB",rootThreadIdQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    		
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
	    	RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
	    }
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));						           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}    
	    String replyMessageID = CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"SentByPracticeUser","");
	    Thread.sleep(60000);
	    CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID,comm_id,rootThreadId.toUpperCase(),"Re: "+subject, JalapenoMessagesPage.getPatientReply());
	    log("Test Case End: The practice user is able to compose a message with 'high priority' flag on and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button and patient reply to message sent by Practice User");	
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMReadReceiptSecureMessageNewLockedEncounter() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with 'Read Receipt' flag on and send it to enrolled patient with “Add to New Encounter(Locked)” option selected in Send & Chart button.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());	
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");	
	    String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
	    logStep("Compose Message with Read Receipt and send it to enrolled patient with “Add to New Encounter(Locked)” option selected in Send & Chart button.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"ReadReceiptRequested",person_id,practiceId,userId,providerName,locationName, "EHR", "NewLockedEncounter","PracticeUser","","","");		

    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"ReadReceiptRequested");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    	
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
	    	RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
	    }
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));						           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"ReadReceiptRequested","");
	    log("Test Case End: The practice user is able to compose a message with 'Read Receipt' flag on and send it to enrolled patient with “Add to New Encounter(Locked)” option selected in Send & Chart button.");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMUnreadNotificationSecureMessageNewUnLockedEncounter() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with 'Unread Notification' flag on and send it to enrolled patient with “Add to New Encounter(UnLocked)” option selected in Send & Chart button.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());	
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");	
	    String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
	    logStep("Compose Message with Unread Notification and send it to enrolled patient with “Add to New Encounter(UnLocked)” option selected in Send & Chart button.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"UnreadNotificationRequested",person_id,practiceId,userId,providerName,locationName, "EHR", "NewUnLockedEncounter","PracticeUser","","","");    	
	    log("Test Case End: The practice user is able to compose a message with 'Unread Notification' flag on and send it to enrolled patient with “Add to New Encounter(UnLocked)” option selected in Send & Chart button.");	
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMDisableReplySecureMessageExistingEncounter() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with 'Cann't Reply' flag on and send it to enrolled patient with “Existing Encounter” option selected in Send & Chart button.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());		
		
		logStep("Getting Existing User");
	    String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
    	NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id);
		 
	    logStep("Compose Message with Cann't Reply and send it to enrolled patient with “Existing Encounter” option selected in Send & Chart button.");
        String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"DisableReply",person_id,practiceId,userId,providerName,locationName, "EHR", "ExistingEncounter","PracticeUser",encounter_id,"","");		
        
    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    		
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"CannotReply","");
    	log("Test Case End: The practice user is able to compose a message with 'Cann't Reply' flag on and send it to enrolled patient with “Existing Encounter” option selected in Send & Chart button.");	
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSecureMessageSentAndReplyUsingOnlineProfile() throws Throwable{
		log("Test Case: Verify the practice user is able to send a message with Onine Profile as sender of message and patient is able to reply to that message");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());		
		
		logStep("Getting Existing User");
	    String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
		 
	    logStep("Compose Message and send it to enrolled patient by selecting Online Profile as sender of message.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	
        String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"SentByOnlineProfile",person_id,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","","");		

    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";
    	String rootThreadIdQuery ="select root_thread_id from ngweb_communications where comm_id ='"+comm_id+"'";
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	String rootThreadId = DBUtils.executeQueryOnDB("NGCoreDB",rootThreadIdQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
	
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

    	String replyMessageID = CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"SentByOnlineProfile","");
	    Thread.sleep(60000);
		CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID,comm_id,rootThreadId.toUpperCase(),"Re: "+subject, JalapenoMessagesPage.getPatientReply());
    	log("Test Case End: The practice user is able to send a message with Onine Profile as sender of message and patient is able to reply to that message.");	
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSecureMessageSentAndReplyUsingAliasName() throws Throwable{
		log("Test Case: Verify the practice user is able to send a message with Alias Name as sender of message and patient is able to reply to that message.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());		
		
		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		String userFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from user_mstr where user_id='"+userId+"'");
		String userLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from user_mstr where user_id='"+userId+"'");	
		String userProviderName =userLastName+", Dr";

		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	String userLocationName = PropertyLoaderObj.getProperty("PortalLocationName");
    	
		long timestamp = System.currentTimeMillis();
		Log4jUtil.log("Step Begins: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;
		
		logStep("Login patient");
		NGLoginPage loginPage = new NGLoginPage(driver, PropertyLoaderObj.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(PropertyLoaderObj.getProperty("CCDAUsername"),PropertyLoaderObj.getPassword());

		logStep("Click Ask A Question tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaQuestion(PropertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillAndContinue(askaSubject, questionText,userProviderName,userLocationName);

		assertTrue(askaSubject.equals(askPage2.getSubject()),"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();
		homePage.LogoutfromNGMFPortal();

    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String PatientFirstName = DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id ='"+person_id+"'");
		String PatientLastName = DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id ='"+person_id+"'");
		String expectedBody= "Dear "+PropertyLoaderObj.getProperty("practiceName")+",<br/><br/>"+IntegrationConstants.MESSAGE_REPLY+"<br/><br/>Thanks,<br>"+PatientFirstName+" "+PatientLastName;		
		
		String messageID = RestUtils.isReplyPresentReturnMessageID(PropertyLoaderObj.getResponsePath(), askaSubject,expectedBody);
		
		String expectedBodyinInbox= "Dear "+PropertyLoaderObj.getProperty("practiceName")+",\n"+IntegrationConstants.MESSAGE_REPLY+"\nThanks,\n"+PatientFirstName+" "+PatientLastName;		
		Thread.sleep(60000);
		Log4jUtil.log("Verify message received at NG core");
		CommonFlows.verifyMessageReceivedAtNGCore(PropertyLoaderObj,messageID,askaSubject,expectedBodyinInbox.replace("\n", ""),PropertyLoaderObj.getProperty("askAV2Name"));
		
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"ReplyToASKAUsingAliasName"+messageID,person_id,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","","");		
    	
    	Thread.sleep(60000);
    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    	
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

    	String replyMessageID =CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageIDAtMF,integrationPracticeID,"SentByAlias","");

		Thread.sleep(60000);
		CommonFlows.verifyReplyReceivedAtNGCore(replyMessageID, comm_id,messageID.toUpperCase(),"Re: "+ subject, JalapenoMessagesPage.getPatientReply());
	    log("Test Case End: The practice user is able to send a message with Alias Name as sender of message and patient is able to reply to that message.");	
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMAskAQuestionRepliedUsingOriginalUnlockedEncounter() throws Throwable{
		log("Test Case : Verify the patient is able to ask a question from Practice User and practice user is able to reply to that message.");
		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		String userFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from user_mstr where user_id='"+userId+"'");
		String userLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from user_mstr where user_id='"+userId+"'");	
		String userProviderName =userLastName+", Dr";

		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	String userLocationName = PropertyLoaderObj.getProperty("PortalLocationName");
    	
		long timestamp = System.currentTimeMillis();
		Log4jUtil.log("Step Begins: Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;
		
		logStep("Login patient");
		NGLoginPage loginPage = new NGLoginPage(driver, PropertyLoaderObj.getProperty("url"));
		JalapenoHomePage homePage = loginPage.login(PropertyLoaderObj.getProperty("CCDAUsername"),PropertyLoaderObj.getPassword());

		logStep("Click Ask A Question tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage.openSpecificAskaQuestion(PropertyLoaderObj.getProperty("askAV2Name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillAndContinue(askaSubject, questionText,userProviderName,userLocationName);

		assertTrue(askaSubject.equals(askPage2.getSubject()),"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();
		homePage.LogoutfromNGMFPortal();

    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetInboundMessage").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String PatientFirstName = DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id ='"+person_id+"'");
		String PatientLastName = DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id ='"+person_id+"'");
		String expectedBody= "Dear "+PropertyLoaderObj.getProperty("practiceName")+",<br/><br/>"+IntegrationConstants.MESSAGE_REPLY+"<br/><br/>Thanks,<br>"+PatientFirstName+" "+PatientLastName;		
		
		String messageID = RestUtils.isReplyPresentReturnMessageID(PropertyLoaderObj.getResponsePath(), askaSubject,expectedBody);
		
		String expectedBodyinInbox= "Dear "+PropertyLoaderObj.getProperty("practiceName")+",\n"+IntegrationConstants.MESSAGE_REPLY+"\nThanks,\n"+PatientFirstName+" "+PatientLastName;		
		Thread.sleep(60000);
		CommonFlows.verifyMessageReceivedAtNGCore(PropertyLoaderObj,messageID,askaSubject,expectedBodyinInbox.replace("\n", ""),PropertyLoaderObj.getProperty("askAV2Name"));
		
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"ReplyToPortal"+messageID,person_id,practiceId,userId,providerName,locationName, "EHR", "OriginalUnlockedEncounter","PracticeUser","","","");		
    	
    	Thread.sleep(60000);
    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    	
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageIDAtMF,integrationPracticeID,"","");
	    log("Test Case End: The patient is able to ask a question from Practice User and practice user is able to reply to that message.");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMSendSecureMessageDoNotAddToChart() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with Delayed Delivery and send it to enrolled patient with “Do Not Add To Chart” option selected in Send & Chart button.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());		
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
	    logStep("Compose Message with Delayed Delivery and send it to enrolled patient with “Do Not Add To Chart” option selected in Send & Chart button.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"",person_id,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","","");		

    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
	
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"","");
	    log("Test Case End: The practice user is able to compose a message with Delayed Delivery and send it to enrolled patient with “Do Not Add To Chart” option selected in Send & Chart button.");	
	}
	
	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDDateFilterCCD() throws Throwable{
		log("Test Case: Verify Patient is able to request for DateFilter CCD");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());		
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
		
		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight"+since);
		
		Log4jUtil.log("Step Begins: Login to Portal");
	    NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		Log4jUtil.log("Step Begins: Go to  Health Record Summaries");
	    MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);
	    Assert.assertTrue(MedicalRecordSummariesPageObject.areBasicPageElementsPresent(), "Failed to Load Health Record Summaries ");		
		
		log("Step Begins: Click on Request Health Record");
		MedicalRecordSummariesPageObject.selectHealthRecordRequestButton();
		Thread.sleep(6000);
			
		log("Step Begins: Selecting the date range for the health Data Request");		
		MedicalRecordSummariesPageObject.filterCCDs(MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat(), MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		MedicalRecordSummariesPageObject.requestCcdOnDemandFromPopUp();
		Thread.sleep(5000);
		
		log("Step Begins: Close the onDemand PopUp ");
		MedicalRecordSummariesPageObject.closeOnDemandPopUpButton();
		
		log("Step Begins: Logout");
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
				
    	Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetHealthData").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
		
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+person_id+"'");
		
		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(PropertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""));
		
		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(PropertyLoaderObj, person_id,practiceId, integrationPracticeID, 3);
		
		logStep("Verify date filter CCD is received by patient");
		CommonFlows.IsCCDReceived(driver,url,username, PropertyLoaderObj.getPassword(),"","");
		log("Test Case End: The patient is able to receive the Date Filter CCD successfully");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointment() throws Throwable{
		log("Test Case: Verify the patient is able to request for appointment and practice user is able to book appointment and send response to patient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");
		
		String UpdateToEPMCategoryQuery ="update service_setting set value = 'EPM' where [key] = 'epmcategory' and practice_id = (select id from practice where extpracticeid ='"+practiceId+"')";
		DBUtils.executeQueryOnDB("MFAgentDB",UpdateToEPMCategoryQuery);
		
		String apptTime ="08:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery ="select top 1 delete_ind from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB",deleteINDQuery);
		String ApptIDQuery ="select top 1 appt_id from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"' order by create_timestamp desc";
		
		if(deleteIND.equalsIgnoreCase("N")){
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB",ApptIDQuery));
		}			
		
    	long timestamp = System.currentTimeMillis();
    	Long since = timestamp / 1000L - 60 * 24;
		String appointmentReason = "Illness" + System.currentTimeMillis();

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));		

		logStep("Navigate to appointment page");
		homePage.clickOnAppointmentV3(driver);
		
		logStep("Request for appointment");
		// workaround for extra appointments list when multiple appointments solutions are on
		try {

			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");// go on assuming we didn't find the extra page and button
		}

		NGAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);
		
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseProvider(PropertyLoaderObj.getProperty("ProviderName"));

		logStep("Continue to step 2: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		try{
			NGAppointmentPage appointmentPage =homePage.clickOnAppointmentV3(driver);}
		catch(Exception e){
			Log4jUtil.log(e.getMessage());}
		
		Thread.sleep(5000);
		NGAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.LogoutfromNGMFPortal();
		
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetAppointment").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String appointmentID = RestUtils.isAppointmentReasonResponseXMLValid(PropertyLoaderObj.getResponsePath(), appointmentReason);
		
		logStep("Verify appointment request is reached to EPM/EHR Inbox");
		CommonFlows.verifyAppointmentRequestReceived(appointmentID, appointmentReason,PropertyLoaderObj.getProperty("AppointmentStartTime"), PropertyLoaderObj.getProperty("AppointmentEndTime"), PropertyLoaderObj.getProperty("AppointmentDays"),practiceId);
		
		DBUtils.executeQueryOnDB("MFAgentDB",UpdateToEPMCategoryQuery);
		logStep("Schedule an appointment for Patient");		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		String EPMAppointmenttId =NGAPIFlows.postAppointment(person_id,practiceId,locationName, providerName, eventName, resourceName,2,apptTime,201);
		
		DBUtils.executeQueryOnDB("MFAgentDB",UpdateToEPMCategoryQuery);
		String appointmentResponse = "ApptResponse" + System.currentTimeMillis();
		logStep("Send appointment response to Patient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		NGAPIFlows.postAppointmentResponse(appointmentID, EPMAppointmenttId, appointmentResponse,apptTime,2);
		
		logStep("Verify appointment is booked and response is captured");
		CommonFlows.verifyAppointmentBookedResponseCaptured(appointmentID, appointmentResponse);
				
		logStep("Verify processing status of appointment");
		CommonFlows.verifyAppointmentProcessingStatus(PropertyLoaderObj, appointmentID, integrationPracticeID);
		
		String appointmentDate = DBUtils.executeQueryOnDB("NGCoreDB","select appt_date from appointments where appt_id ='"+EPMAppointmenttId+"'");
		appointmentDate = appointmentDate.substring(0, 4)+"/"+appointmentDate.substring(4, 6)+"/"+appointmentDate.substring(6);
				
		appointmentDate = appointmentDate.replaceAll("/", "-")+"T"+apptTime+"Z";
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(appointmentDate);
		log("Appointment Date is "+new SimpleDateFormat("M/d/yy hh:mm").format(date));
//		appointmentDate = CommonUtils.changeESTtoIST(new SimpleDateFormat("M/d/yy hh:mm").format(date));
		appointmentDate = new SimpleDateFormat("M/d/yy hh:mm").format(date);
		log("Expected appointment Date is "+appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")));		
		String expectedTime =appointmentDate.substring(appointmentDate.lastIndexOf(" ")+1);
		
		if(expectedTime.startsWith("0"))
			expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ")+2);
		log("Expected appointment Time is "+expectedTime);
		
		Thread.sleep(60000);
		CommonFlows.verifyAppointmentReceivedinPortal(PropertyLoaderObj, driver, url, username, appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")),expectedTime,appointmentResponse);
		Log4jUtil.log("Test Case End: The patient is able to request for appointment and practice user is able to book appointment and send response to patient");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointmentSameSlot() throws Throwable{
		log("Test Case: Verify the practice user is not able to book same slot which is already booked for patient");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");
		
		String apptTime ="09:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery ="select top 1 delete_ind from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB",deleteINDQuery);
		String ApptIDQuery ="select top 1 appt_id from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"' order by create_timestamp desc";
		
		if(deleteIND.equalsIgnoreCase("N")){
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB",ApptIDQuery));
		}		
		
		logStep("Schedule an appointment for Patient to same slot");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		String EPMAppointmenttId =NGAPIFlows.postAppointment(person_id,practiceId,locationName, providerName, eventName, resourceName,2,apptTime,201);
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		NGAPIFlows.postAppointment(person_id,practiceId,locationName, providerName, eventName, resourceName,2,apptTime,400);
		log("Test Case End: The practice user is not able to book same slot which is already booked for patient");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointmentBookMultipleSlotsandDeleteAppointment() throws Throwable{
		log("Test Case: Verify the practice user is able to book multiple appointments for patient and delete appointment from EPM appointment book");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");
		
		String apptTime ="10:30:00";
		String begintime = apptTime.substring(0, 5).replaceAll(":", "");
		String deleteINDQuery ="select top 1 delete_ind from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"'order by create_timestamp desc";
		String deleteIND = DBUtils.executeQueryOnDB("NGCoreDB",deleteINDQuery);
		String ApptIDQuery ="select top 1 appt_id from appointments where person_id ='"+person_id+"' and begintime ='"+begintime+"' and practice_id ='"+practiceId+"' order by create_timestamp desc";
				
		if(deleteIND.equalsIgnoreCase("N")){
			NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
			NGAPIFlows.deleteAppointment(DBUtils.executeQueryOnDB("NGCoreDB",ApptIDQuery));
		}
		
		String UpdateToEPMCategoryQuery ="update service_setting set value = 'EPM' where [key] = 'epmcategory' and practice_id = (select id from practice where extpracticeid ='"+practiceId+"')";
		DBUtils.executeQueryOnDB("MFAgentDB",UpdateToEPMCategoryQuery);
		
		logStep("Schedule another appointment for Patient to different slot");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		String EPMAppointmenttId =NGAPIFlows.postAppointment(person_id,practiceId,locationName, providerName, eventName, resourceName,1,apptTime,201);
		
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
		
		logStep("Verify processing status of appointment");
		CommonFlows.verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj,EPMAppointmenttId,integrationPracticeID, "Appointment");
		
		String appointmentDate = DBUtils.executeQueryOnDB("NGCoreDB","select appt_date from appointments where appt_id ='"+EPMAppointmenttId+"'");
		appointmentDate = appointmentDate.substring(0, 4)+"/"+appointmentDate.substring(4, 6)+"/"+appointmentDate.substring(6);
		
		appointmentDate = appointmentDate.replaceAll("/", "-")+"T"+apptTime+"Z";
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(appointmentDate);
		log("Appointment Date is "+new SimpleDateFormat("M/d/yy hh:mm").format(date));
//		appointmentDate = CommonUtils.changeESTtoIST(new SimpleDateFormat("M/d/yy hh:mm").format(date));
		appointmentDate = new SimpleDateFormat("M/d/yy hh:mm").format(date);
		log("Expected appointment Date is "+appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")));
		String expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ")+1);
		
		if(expectedTime.startsWith("0"))
			expectedTime = appointmentDate.substring(appointmentDate.lastIndexOf(" ")+2);		
		log("Expected appointment Time is "+expectedTime);
		
		logStep("Verfiy appointment is received in Portal");
        Thread.sleep(80000);
		CommonFlows.verifyAppointmentReceivedinPortal(PropertyLoaderObj, driver, url, username, appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")),expectedTime,"");
		
		logStep("Delete booked appointment from EPM Appointment Book having appointment ID "+EPMAppointmenttId);
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		NGAPIFlows.deleteAppointment(EPMAppointmenttId);
		
		logStep("Verify appointment is deleted from EPM Appointment Book");
		String deleteInd =DBUtils.executeQueryOnDB("NGCoreDB","select delete_ind from appointments where appt_id ='"+EPMAppointmenttId+"'");
		CommonUtils.VerifyTwoValues(deleteInd,"equals","Y"); 
		logStep("Appointment is deleted from EPM Appointment Book with delete Indicator "+deleteInd);
		
		Thread.sleep(90000);
		logStep("Verify appointment is deleted from Portal");
		CommonFlows.verifyAppointmentDeletedinPortal(PropertyLoaderObj, driver, url, username,appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")),expectedTime);		
		log("Test Case End: The practice user is able to book multiple appointments for patient and delete appointment from EPM appointment book successfully");
	}
	
	/************************
	 * 
	 * @author Raghavendra Gupta
	 * Test case: NEX-4374
	 * 
	************************/
	@Test(enabled = true, groups = { "acceptance-INBOX-MULTI-PRACTICE" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxMultiPracticeAppointmentBookWithExistingPatient() throws Throwable {
		log("Make sure Patient is enrolled in 2 Practices which are at Enterprise Level Enrollment Type");
		log("Test Case: Verify the patient is able to perform multi-practice appointment scenario and verify the appointment status in NextGen database");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("MultiPracticeApptUsername");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",	"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,	integrationPracticeID = null, url = null;

		if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = PropertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = PropertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = PropertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
		    providerName = PropertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = PropertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else {
				Log4jUtil.log("Invalid Execution Mode");
		}
		String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String appointmentReason = "Illness" + System.currentTimeMillis();

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());

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

		NGAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);

		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseProvider(PropertyLoaderObj.getProperty("E1P1ProviderName"));

		logStep("Continue to step 2: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));
				
		// Switch the practice to request appointment for P2 practice
		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
		Thread.sleep(40000);
				
		logStep("Navigate to appointment page");
		homePage.clickOnAppointmentV3(driver);

		logStep("Request for appointment");
		// workaround for extra appointments list when multiple appointments solutions are on
		try {		
			driver.findElement(By.id("appointmentSolutionBtn")).click();
			} catch (WebDriverException e) {
					System.out.println("Exception caught");// go on assuming we didn't find the extra page and button
		}

		NGAppointmentRequestV2Step1 appointmentRequestStep3 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);

		logStep("Assess Elements and choose provider");
		appointmentRequestStep3.chooseProvider(PropertyLoaderObj.getProperty("E1P2ProviderName"));

		logStep("Continue to step 3: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep4 = appointmentRequestStep3.continueToStep2(driver);
		assertTrue(appointmentRequestStep4.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep4.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep4.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));
				
		logStep("Navigate to Appointment Request History");
		try{
			NGAppointmentPage appointmentPage =homePage.clickOnAppointmentV3(driver);}
			catch(Exception e){
			Log4jUtil.log(e.getMessage());}
				
		Thread.sleep(5000);
		NGAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.LogoutfromNGMFPortal();
			
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetAppointment").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String appointmentID = RestUtils.isAppointmentReasonResponseXMLValid(PropertyLoaderObj.getResponsePath(), appointmentReason);
				
		logStep("Verify appointment request is reached to EPM/EHR Inbox");
		CommonFlows.verifyAppointmentRequestReceived(appointmentID, appointmentReason,PropertyLoaderObj.getProperty("AppointmentStartTime"), PropertyLoaderObj.getProperty("AppointmentEndTime"), PropertyLoaderObj.getProperty("AppointmentDays"),practiceId);
		}

		@Test(enabled = true, groups = { "acceptance-INBOX-MULTI-PRACTICE" }, retryAnalyzer = RetryAnalyzer.class)
		public void testInboxMultiPracticeAppointmentBookWithNewPatient() throws Throwable {
		log("Test Case: Verify the patient is able to perform multi-practice appointment scenario and verify the appointment status in NextGen database");

		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
				
//				Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getProperty("NGMainEnterpriseID"), PropertyLoaderObj.getProperty("NGEnterprise1Practice1"));
				
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,	integrationPracticeID = null, url = null;

		if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
			practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = PropertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = PropertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = PropertyLoaderObj.getProperty("NGMainEnterpriseID");
			practiceId = PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
			providerName = PropertyLoaderObj.getProperty("NGE1P1Provider");
			locationName = PropertyLoaderObj.getProperty("NGE1P1Location");
			integrationPracticeID = PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
			url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");
		} else {
				Log4jUtil.log("Invalid Execution Mode");
		}
		String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");
				
		log("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
				
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);
				
		apiRoutes personURL =apiRoutes.valueOf("AddEnterprisePerson"); 
		String finalURL =EnterprisebaseURL + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
				
		log("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
				
		log("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
				
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
						
		Thread.sleep(120000);
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();
		Mailinator mail = new Mailinator();
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 80);
		Log4jUtil.log("Moving to the link obtained from the email message");
		Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
		Thread.sleep(20000);
		String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
		log(sDate1);				
					
		log("Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
		createPatient.getZip(), PropertyLoaderObj.getDOBMonth(), PropertyLoaderObj.getDOBDay(), PropertyLoaderObj.getDOBYear());
		log("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage homePage = accountDetailsPage.fillAccountDetailsAndContinue(createPatient.getEmailAddress(), PropertyLoaderObj.getPassword(), PropertyLoaderObj.getSecretQuestion(), PropertyLoaderObj.getSecretAnswer(),PropertyLoaderObj.getPhoneNumber());

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String appointmentReason = "Illness" + System.currentTimeMillis();
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

		NGAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseProvider(PropertyLoaderObj.getProperty("E1P1ProviderName"));

		logStep("Continue to step 2: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));
			
		logStep("Create the chart in second practice");
	    timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(PropertyLoaderObj.getNGE1P3Location(),PropertyLoaderObj.getNGE1P3Provider(),person_id); 
			
		enrollPatientWithoutGetProcessingStatusValidation(createPatient, person_id, PropertyLoaderObj.getProperty("practiceName2"),PropertyLoaderObj.getProperty("integrationPracticeIDE1P2"),PropertyLoaderObj.getNGEnterpiseEnrollmentE1(), PropertyLoaderObj.getNGEnterpiseEnrollmentE1P2()); 
				
		Thread.sleep(60000);
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName", PropertyLoaderObj.getProperty("practiceName1"))));
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName", PropertyLoaderObj.getProperty("practiceName2"))));
		assertTrue(new Mailinator().areAllMessagesInInbox(mails, 80));		
				
		Thread.sleep(10000);
		logStep("Waiting for welcome mail at patient inbox from second practice");        
        Email visitPortal = new Mailer(createPatient.getEmailAddress()).pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal, "Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal,WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is "+portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");
			
		Thread.sleep(20000);
        driver.navigate().refresh();
				
		// Switch the practice to request appointment for P2 practice
		logStep("Switching to Second Practice to verify auto enrollment");
        homePage.switchToPractice(PropertyLoaderObj.getProperty("practiceName2"));
        Thread.sleep(40000);
				
		logStep("Navigate to appointment page");
		homePage.clickOnAppointmentV3(driver);

		logStep("Request for appointment");
		// workaround for extra appointments list when multiple appointments solutions are on
		try {
			driver.findElement(By.id("appointmentSolutionBtn")).click();
		} catch (WebDriverException e) {
			System.out.println("Exception caught");// go on assuming we didn't find the extra page and button
		}

		NGAppointmentRequestV2Step1 appointmentRequestStep3 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);

		logStep("Assess Elements and choose provider");
		appointmentRequestStep3.chooseProvider(PropertyLoaderObj.getProperty("E1P2ProviderName"));

		logStep("Continue to step 3: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep4 = appointmentRequestStep3.continueToStep2(driver);
		assertTrue(appointmentRequestStep4.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep4.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep4.submitAppointment(driver);
		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));
				
		logStep("Navigate to Appointment Request History");
		try{
			NGAppointmentPage appointmentPage =homePage.clickOnAppointmentV3(driver);}
		catch(Exception e){
			Log4jUtil.log(e.getMessage());}
				
		Thread.sleep(5000);
		NGAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.LogoutfromNGMFPortal();
				
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetAppointment").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String appointmentID = RestUtils.isAppointmentReasonResponseXMLValid(PropertyLoaderObj.getResponsePath(), appointmentReason);
				
		logStep("Verify appointment request is reached to EPM/EHR Inbox");
		CommonFlows.verifyAppointmentRequestReceived(appointmentID, appointmentReason,PropertyLoaderObj.getProperty("AppointmentStartTime"), PropertyLoaderObj.getProperty("AppointmentEndTime"), PropertyLoaderObj.getProperty("AppointmentDays"),practiceId);
		}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP138SendPatientEducation() throws Throwable{
		log("Test Case: Verify the practice user is able to send Patient Education document");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());	
		
		logStep("Getting Existing User");
	    String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");	
	    String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    
    	String PEdocumentId = DBUtils.executeQueryOnDB("NGCoreDB","select top 1 document_id from patient_education where practice_id ='"+practiceId+"' and person_id ='"+person_id+"'");
	    
	    logStep("Compose Message with Patient Education Document and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postSecureMessage(PropertyLoaderObj,"",person_id,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","PatientEducation",PEdocumentId);		

    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    	
    	String attachmentName =DBUtils.executeQueryOnDB("NGCoreDB","select document_desc from patient_education where document_id ='"+PEdocumentId+"'");		
    	Thread.sleep(15000);
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
	    	RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
	    }
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));						           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}    
	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getPassword(),subject,body,comm_id,messageID,integrationPracticeID,"messageWithPE",attachmentName);
	    log("Test Case End: The practice user is able to send Patient Education document successfully and patient is able to receive.");	
	}	
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxPrescription() throws Throwable{
		log("Test Case: Add the medication for the prescription renewal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);		
		Log4jUtil.log("Step Begins: Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id);
		  
		Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
		String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);
		
		Log4jUtil.log("Step Begins: Add Medication to created encounter");
		String medication_id = NGAPIFlows.addMedication(practiceId,locationName,providerName, "Active", person_id, encounter_id,"R07.9",286939);
		
		logStep("Verify processing status of medication");
		CommonFlows.verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj,medication_id,integrationPracticeID, "Medication");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestPrescriptionRenewalApprovedbyPracticeUser() throws Throwable{
		log("Test Case: Verify the patient is able to request for prescription renewal and Practice User is able to approve the medication");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
				
    	long timestamp = System.currentTimeMillis();
    	Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = PropertyLoaderObj.getProperty("MedicationToRenew");
		
		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);
		
		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver, PropertyLoaderObj.getProperty("PortalLocationName"),PropertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForPrescriptionRenewal(driver,prescritonRenewalRequestReason,medicationToRenew);
		
		homePage.LogoutfromNGMFPortal();
	
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
    	Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(PropertyLoaderObj.getResponsePath(), prescritonRenewalRequestReason);
		
		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID, "[#1:"+prescritonRenewalRequestReason+"]",practiceId);
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		String prescritonRenewalResponse = "PrescriptionRenewalResponse" + System.currentTimeMillis();
		logStep("Approve the Prescription Renewal Request by Practice User");
		NGAPIFlows.putPrescriptionRenewalRequest(person_id, prescriptionID, prescritonRenewalResponse, "NewLockedEncounter", locationName, providerName, "Accepted", 200);
		log("The Prescription Renewal Request is approved by Practice User");
		
		Thread.sleep(90000);
		logStep("Log into Portal");
		loginPage = new NGLoginPage(driver, url);
		homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		logStep("Navigate to Messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Verify Prescription Renewal Message is received");
		assertTrue(messagesPage.isMessageDisplayed(driver, PropertyLoaderObj.getProperty("PrescriptionSubject")));
		
		messagesPage.OpenMessage(driver, "Prescription Renewal Request");
		
		logStep("Verify PrescritonRenewalResponse added by Practice is displayed in Portal");
		messagesPage.verifyPrescriptionResponse(driver,prescritonRenewalResponse);
		
		logStep("Verify medication status is approved");
		messagesPage.verifyMedicationStatus(driver,"Approved");
		
		messagesPage.archiveMessage();
    	log("Test Case End: The patient is able to request for prescription renewal and Practice User is able to approve the medication");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestPrescriptionRenewalRejectedbyPracticeUser() throws Throwable{
		log("Test Case: Verify the patient is able to request for prescription renewal and Practice User is able to reject the medication");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
				
    	long timestamp = System.currentTimeMillis();
    	Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = PropertyLoaderObj.getProperty("MedicationToRenew");
		
		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);
		
		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver, PropertyLoaderObj.getProperty("PortalLocationName"),PropertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForPrescriptionRenewal(driver,prescritonRenewalRequestReason,medicationToRenew);
		
		homePage.LogoutfromNGMFPortal();
	
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
    	Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(PropertyLoaderObj.getResponsePath(), prescritonRenewalRequestReason);
		
		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID, "[#1:"+prescritonRenewalRequestReason+"]",practiceId);
		
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId, practiceId);
		String prescritonRenewalResponse = "PrescriptionRenewalResponse" + System.currentTimeMillis();
		logStep("Reject the Prescription Renewal Request by Practice User");
		NGAPIFlows.putPrescriptionRenewalRequest(person_id, prescriptionID, prescritonRenewalResponse, "NewLockedEncounter", locationName, providerName, "Rejected", 200);
		log("The Prescription Renewal Request is rejected by Practice User");
		
		Thread.sleep(90000);
		logStep("Log into Portal");
		loginPage = new NGLoginPage(driver, url);
		homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		logStep("Navigate to Messages");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Verify Prescription Renewal Message is received");
		assertTrue(messagesPage.isMessageDisplayed(driver, PropertyLoaderObj.getProperty("PrescriptionSubject")));
		
		messagesPage.OpenMessage(driver, "Prescription Renewal Request");
		
		logStep("Verify PrescritonRenewalResponse added by Practice is displayed in Portal");
		messagesPage.verifyPrescriptionResponse(driver,prescritonRenewalResponse);
		
		messagesPage.archiveMessage();
    	log("Test Case End: The patient is able to request for prescription renewal and Practice User is able to reject the medication");
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCOMBulkSendSecureMessageDoNotAddToEncounterSendByPracticeUser() throws Throwable{
		log("Test Case: Verify the practice user is able to compose a message with normal priority, enable flag for unread notification, disable reply and send it to more than one patient enrolled in MF patient portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());	
		
		logStep("Getting Existing User");
    	String person_id = PropertyLoaderObj.getProperty("PersonId1");
    	String username = DBUtils.executeQueryOnDB("NGCoreDB","select email_address from person where person_id = '"+person_id+"'");	
	    String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
    	List<String> personIdList = new ArrayList<String>();
    	personIdList.add(person_id);
    	personIdList.add(PropertyLoaderObj.getProperty("PersonId2"));
    	
	    logStep("Compose message with normal priority, unread notification, disable reply and send it to more than one recipient");
        NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);    
    	String comm_id =NGAPIFlows.postBulkSecureMessage(PropertyLoaderObj,"Bulk1",personIdList,practiceId,userId,providerName,locationName, "EHR", "DoNotAddToEncounter","PracticeUser","","","");		

    	String subjectQuery ="select subject from ngweb_communications where comm_id ='"+comm_id+"'";
    	String bodyQuery ="select body from ngweb_communications where comm_id ='"+comm_id+"'";	
    	String subject = DBUtils.executeQueryOnDB("NGCoreDB",subjectQuery);
    	String body = DBUtils.executeQueryOnDB("NGCoreDB",bodyQuery);
    	
    	logStep("Verify the processing status of message");
    	CommonFlows.verifyMessageProcessingStatus(PropertyLoaderObj, person_id, practiceId, comm_id, integrationPracticeID,"UnReadRequested");
    	
    	String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='"+comm_id+"'";
    	String messageID = DBUtils.executeQueryOnDB("MFAgentDB",messageDeliveryQuery);
    		
    	logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
	    if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
	    	RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
	    }
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));						           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	    CommonFlows.verifyMessageINInbox(PropertyLoaderObj,driver,url,username,PropertyLoaderObj.getProperty("BulkPassword"),subject,body,comm_id,messageID,integrationPracticeID,"BulkCannotReply","");
	    log("Test Case End: The practice user is able to compose a message with normal priority, enable flag for unread notification, disable reply and send it to more than one patient enrolled in MF patient portal");	
	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX-MULTI-PRACTICE" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxAppointmentBookForDependentUsingExistingGuardianAccount() throws Throwable {
		log("Test Case: Verify Guardian is able to book appointment for his dependent and verify the appointment status in NextGen database");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("GuardianUserName");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",	"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,	integrationPracticeID = null, url = null;
		
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    userId =PropertyLoaderObj.getProperty("SecureMessageUserID");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
		String eventName = PropertyLoaderObj.getProperty("EventName");
		String resourceName = PropertyLoaderObj.getProperty("ResourceName");

		long timestamp = System.currentTimeMillis();
		Long since = timestamp / 1000L - 60 * 24;
		String appointmentReason = "Illness" + System.currentTimeMillis();

		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getProperty("GuardianPassword"));

		Log4jUtil.log("Detecting if Home Page is opened");
		assertTrue(homePage.isHomeButtonPresent(driver));

		logStep("Switch to Dependent's Account");
		homePage.faChangePatient();
		
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

		NGAppointmentRequestV2Step1 appointmentRequestStep1 = PageFactory.initElements(driver, NGAppointmentRequestV2Step1.class);
		
		logStep("Assess Elements and choose provider");
		appointmentRequestStep1.chooseProvider(PropertyLoaderObj.getProperty("ProviderName"));

		logStep("Continue to step 2: click continue and assess elements");
		JalapenoAppointmentRequestV2Step2 appointmentRequestStep2 = appointmentRequestStep1.continueToStep2(driver);
		assertTrue(appointmentRequestStep2.areBasicPageElementsPresent());

		logStep("Fill details and submit");
		appointmentRequestStep2.fillAppointmentRequestForm(appointmentReason);
		homePage = appointmentRequestStep2.submitAppointment(driver);

		logStep("Check if thank you frame is displayd");
		Thread.sleep(10000);
		assertTrue(homePage.isTextDisplayed("Thank you"));

		logStep("Navigate to Appointment Request History");
		try{
			NGAppointmentPage appointmentPage =homePage.clickOnAppointmentV3(driver);}
		catch(Exception e){
			Log4jUtil.log(e.getMessage());}
		
		Thread.sleep(5000);
		NGAppointmentRequestV2HistoryPage historyPage = appointmentRequestStep1.goToHistory(driver);

		logStep("Check elements and appointment request reason");
		assertTrue(historyPage.areBasicPageElementsPresent());
		assertTrue(historyPage.findAppointmentReasonAndOpen(appointmentReason));

		logStep("Check appointment request details");
		assertTrue(historyPage.checkAppointmentDetails(appointmentReason));
		homePage = historyPage.clickOnMenuHome();
		homePage.LogoutfromNGMFPortal();
		
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}

		Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetAppointment").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String appointmentID = RestUtils.isAppointmentReasonResponseXMLValid(PropertyLoaderObj.getResponsePath(), appointmentReason);
		
		logStep("Verify appointment request is reached to EPM/EHR Inbox");
		CommonFlows.verifyAppointmentRequestReceived(appointmentID, appointmentReason,PropertyLoaderObj.getProperty("AppointmentStartTime"), PropertyLoaderObj.getProperty("AppointmentEndTime"), PropertyLoaderObj.getProperty("AppointmentDays"),practiceId);
		
		logStep("Get Dependent's person Id");
		String guardianEmail = PropertyLoaderObj.getProperty("GuardianEmail");
		String dependentLastName = PropertyLoaderObj.getProperty("DependentLastName");
		String dependent_person_id =DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+guardianEmail+"' and last_name='"+dependentLastName+"'");
		logStep("Schedule an appointment for Patient");
		String apptTime ="05:30:00";
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		String EPMAppointmenttId =NGAPIFlows.postAppointment(dependent_person_id,practiceId,locationName, providerName, eventName, resourceName,2,apptTime,201);
		
		String appointmentResponse = "ApptResponse" + System.currentTimeMillis();
		logStep("Send appointment response to Patient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",enterpriseId,practiceId);
		NGAPIFlows.postAppointmentResponse(appointmentID, EPMAppointmenttId, appointmentResponse,apptTime,2);
		
		logStep("Verify appointment is booked and response is captured");
		CommonFlows.verifyAppointmentBookedResponseCaptured(appointmentID, appointmentResponse);
				
		logStep("Verify processing status of appointment");
		CommonFlows.verifyAppointmentProcessingStatus(PropertyLoaderObj, appointmentID, integrationPracticeID);
		
		String appointmentDate = DBUtils.executeQueryOnDB("NGCoreDB","select appt_date from appointments where appt_id ='"+EPMAppointmenttId+"'");
		appointmentDate = appointmentDate.substring(0, 4)+"/"+appointmentDate.substring(4, 6)+"/"+appointmentDate.substring(6);
				
		appointmentDate = appointmentDate.replaceAll("/", "-")+"T00:00:00Z";
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(appointmentDate);
		log("Appointment Date is "+new SimpleDateFormat("M/d/yy hh:mm").format(date));
		appointmentDate = CommonUtils.changeESTtoIST(new SimpleDateFormat("M/d/yy hh:mm").format(date));
		
		log("Expected appointment Date is "+appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")));
		log("Expected appointment Time is "+appointmentDate.substring(appointmentDate.lastIndexOf(" ")+1));
		CommonFlows.verifyAppointmentReceivedinPortal(PropertyLoaderObj, driver, url, username, appointmentDate.substring(0,appointmentDate.lastIndexOf(" ")),appointmentDate.substring(appointmentDate.lastIndexOf(" ")+1),appointmentResponse);
		Log4jUtil.log("Test Case End: The patient is able to request for appointment and practice user is able to book appointment and send response to patient");	}
	
	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInboxRequestMultiplePrescriptionRenewal() throws Throwable{
		log("Test Case: Verify the patient is able to request for multiple medications and Prescribed elsewhere medications");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
				
    	long timestamp = System.currentTimeMillis();
    	Long since = timestamp / 1000L - 60 * 24;
		String prescritonRenewalRequestReason = "PrescriptionRenewalRequest" + System.currentTimeMillis();
		String medicationToRenew = PropertyLoaderObj.getProperty("MedicationToRenew");
		
		logStep("Log into Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		
		logStep("Navigate to Prescription Renewal Page");
		JalapenoPrescriptionsPage prescriptionsPage = homePage.clickOnPrescriptions(driver);
		Thread.sleep(10000);
		
		logStep("Select Location and Provider");
		prescriptionsPage.SelectProviderLocationclickContinueButton(driver, PropertyLoaderObj.getProperty("PortalLocationName"),PropertyLoaderObj.getProperty("PortalProviderName"));

		logStep("Request for Prescription Renewal from Portal");
		homePage = prescriptionsPage.requestForMultiplePrescriptionRenewal(driver,prescritonRenewalRequestReason,medicationToRenew, PropertyLoaderObj.getProperty("SecondMedicationToRenew"));
		
		homePage.LogoutfromNGMFPortal();
	
		logStep("Setup Oauth client" + PropertyLoaderObj.getResponsePath());
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername1"),PropertyLoaderObj.getProperty("oAuthPassword1"));
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getProperty("oAuthUsername"),PropertyLoaderObj.getProperty("oAuthPassword"));			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
    	
    	Log4jUtil.log("Step Begins: Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		Log4jUtil.log("Step Begins: Do a GET Prescription Call and get the message");
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getProperty("GetPrescription").replaceAll("integrationID", integrationPracticeID) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());

		Log4jUtil.log("Step Begins: Validate message reply");
		String prescriptionID = RestUtils.isPrescriptionRenewalRequestPresent(PropertyLoaderObj.getResponsePath(), prescritonRenewalRequestReason);
		
		Thread.sleep(60000);
		logStep("Verify Prescription Renewal request is reached to EPM/EHR Inbox");
		CommonFlows.verifyPrescriptionRenewalRequestReceived(prescriptionID, "[#1:"+prescritonRenewalRequestReason+"][#2:"+prescritonRenewalRequestReason+"]",practiceId);	
    	log("Test Case End: The patient is able to request for multiple medications and Prescribed elsewhere medications");
	}
	
	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentPayBills() throws Throwable {
		log("Test Case: Verify the patient is able to pay the bill using Pay Bills and payment is being posted to NG");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		logStep("Getting Existing User");
		String username = PropertyLoaderObj.getProperty("CCDAUsername");
    	String person_id = DBUtils.executeQueryOnDB("NGCoreDB","select person_id from person where email_address = '"+username+"'");
    	String enterpriseId = null, practiceId = null, providerName = null, locationName = null , userId = null, integrationPracticeID= null, url =null;
    	
    	if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
    		enterpriseId= PropertyLoaderObj.getProperty("NGEnterpiseEnrollmentEnterprise1");
    	    practiceId= PropertyLoaderObj.getProperty("NGEnterprise1Practice1");
    	    providerName =PropertyLoaderObj.getProperty("NGE1P1Provider"); 
    	    locationName =PropertyLoaderObj.getProperty("NGE1P1Location");
    	    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDE1P1");
    	    url = PropertyLoaderObj.getProperty("MFPortalURLPractice1");	
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			enterpriseId= PropertyLoaderObj.getProperty("NGMainEnterpriseID");
		    practiceId= PropertyLoaderObj.getProperty("NGMainPracticeID");
		    providerName =PropertyLoaderObj.getProperty("EPMProviderName"); 
		    locationName =PropertyLoaderObj.getProperty("EPMLocationName");
		    integrationPracticeID =PropertyLoaderObj.getProperty("integrationPracticeIDAMDC");
		    url = PropertyLoaderObj.getProperty("url");			           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
		
    	String acctNBRQuery = "select acct_nbr from accounts where guar_id ='"+person_id+"' and practice_id='"+practiceId.trim()+"'";
		logStep("Initiate payment data");
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB",acctNBRQuery);
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Mastercard, name);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);

		JalapenoHomePage homePage = loginPage.login(username, PropertyLoaderObj.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		assertTrue(payBillsPage.areBasicPageElementsPresent());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,	creditCard);
		assertTrue(confirmationPage.areBasicPageElementsPresent());
		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));
		
		String PaymentComments = "Testing payment from number: " + accountNumber+" Current Timestamp "+(new Date()).getTime();
		homePage = confirmationPage.commentAndSubmitPayment(PaymentComments);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();

//		logStep("Login to Practice Portal");
//		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, PropertyLoaderObj.getPortalUrl());
//		PracticeHomePage practiceHome = practiceLogin.login(PropertyLoaderObj.getDoctorLogin(), PropertyLoaderObj.getDoctorPassword());
//
//		logStep("Click On Online BillPayment Tab in Practice Portal");
//		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();
//
//		logStep("Search Paid Bills By Current Date");
//		onlineBillPaySearchPage.searchForBillPayToday();
//
//		logStep("Search For Today's Paid Bill By Account Number");
//		onlineBillPaySearchPage.searchForBillPayment(accountNumber);
//
//		logStep("Get Bill Details");
//		onlineBillPaySearchPage.getBillPayDetails();
//
//		logStep("Set Payment Communication Details");
//		onlineBillPaySearchPage.setPaymentCommunicationDetails();
//
//		logStep("Logout of Practice Portal");
//		practiceHome.logOut();
		
		String actualAmount = amount.charAt(0)+"."+amount.substring(1);
		log("Actual Amount "+actualAmount);
		CommonFlows.verifyPaymentReachedtoMFAgent(PaymentComments,"BillPayment", "", accountNumber,"POSTED", actualAmount,"MasterCard");
		
		String SourceIdQuery = "select acct_id from accounts where guar_id ='"+person_id+"' and practice_id='"+practiceId.trim()+"'";
		String SourceId = DBUtils.executeQueryOnDB("NGCoreDB",SourceIdQuery);
		CommonFlows.verifyPaymentPostedtoNG(PaymentComments,SourceId , person_id, "-"+actualAmount, "Payment type: BillPayment, Last 4 CC digits: "+creditCard.getLastFourDigits(),practiceId);
		log("Test Case End: The patient is able to pay the bill using Pay Bills and payment is being posted to NG");
	}
}
