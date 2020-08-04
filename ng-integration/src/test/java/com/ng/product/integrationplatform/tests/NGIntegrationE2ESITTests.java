package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PatientRegistrationUtils;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.portal.utils.PortalConstants;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.flows.NGPatient;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * <!-- Copyright 2020 NXGN Management, LLC. All Rights Reserved. -->
 ************************/

public class NGIntegrationE2ESITTests extends BaseTestNGWebDriver{
 
	private PropertyFileLoader PropertyLoaderObj;
	
	private static final String NewDependentActivationMessage ="You are invited to create a Patient Portal guardian account";
	private static final String MemberConfirmationMessage ="New Member Confirmation";
	private static final String PortalURL ="Visit our patient portal now";
	private static final String INVITE_EMAIL_SUBJECT_REPRESENTATIVE = "You're invited to create a Portal account to be a trusted representative of a patient at ";
	private static final String INVITE_EMAIL_BUTTON_TEXT = "Sign Up!";
	
    int arg_timeOut=300; 
    NGAPIUtils ngAPIUtils;
    apiRoutes EnterprisebaseURL;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws IOException {
		log("Getting Test Data");
		PropertyLoaderObj = new PropertyFileLoader();
		ngAPIUtils = new NGAPIUtils(PropertyLoaderObj);
		if(PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
			EnterprisebaseURL= apiRoutes.valueOf("BaseURL");
		}
		else if (PropertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			EnterprisebaseURL= apiRoutes.valueOf("BaseSITURL");
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	}		
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEnrollPatientWithOnlyMandatoryDemographicsToMFPortal() throws Throwable {
		log("Test Case:  Verify the new patient having all the required Demographics is able to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		Long timestamp = System.currentTimeMillis();
		
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
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

		log("Step 7: Do get processing status call and verify the processing time of registration mail to be received");

		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()).replaceAll("jobID", jobID.toLowerCase());
		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
	
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
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			System.out.println(sDate1);				
			
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
	public void testEnrollPatientWithoutMandatoryDemographicsToMFPortal() throws Throwable {			
		log("Test Case:  Verify the patient without mandatory demographics is unable to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step Begins: Create the patient in NG EPM without First Name");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutFirstName");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		ngAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Last Name");
		NewPatient createPatient1 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutLastName");
		
        System.out.println("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient1));
	    ngAPIUtils.setupNGHttpPostRequest("CAGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient1) , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Dob");
		NewPatient createPatient2 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutDOB");
		
        System.out.println("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient2));
	    ngAPIUtils.setupNGHttpPostRequest("CAGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient2) , 400);
		log("Step End: Person should not be created");
		
		log("Step Begins: Create the patient in NG EPM without Gender");
		NewPatient createPatient3 = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutSex");
        System.out.println("Request Body is \n" + objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient3));
		
	    ngAPIUtils.setupNGHttpPostRequest("CAGateway",finalURL,objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient3) , 400);
		log("Step End: Person should not be created");
					
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEnrollPatientWithoutMandatoryDemographicsEmailAddressToMFPortal() throws Throwable {
				
		log("Test Case:  Verify the patient without EmailAddress is unable to enroll to MF portal.");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutEmailaddress");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 400);
		log("MF agent doesnot initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,404);
		log("No Enrollment status found for the patient");				
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEnrollPatientWithoutMandatoryDemographicsZipToMFPortal() throws Throwable {
				
		log("Test Case:  Verify the patient without zip is unable to enroll to MF portal");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"withoutZip");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 400);
		log("MF agent doesnot initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,404);
		log("No Enrollment status found for the patient");				
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeletePatientEnrollment() throws Throwable {
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
		
		VerifyGetPIDCCall(timestamp, person_nbr, PatientFirstName, PatientLastName, "DELETED");
		
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
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","11"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Account Deleted"); 
		log("Step End: Patient enrollment status is "+enrollment_status+" which is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
	}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateDependentAndGuardian() throws Throwable {
	log("Test Case:  Verify the patient enrollment e2e workflow when a Guardian is having an existing MF account");
	log("Execution Environment: " + IHGUtil.getEnvironmentType());
	log("Execution Browser: " + TestConfig.getBrowserType());
	Long timestamp = System.currentTimeMillis();
	
	log("Step 1: Create the Guardian in NG EPM");
	NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
	System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());
	
	ObjectMapper objMap = new ObjectMapper();
    String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
    System.out.println("Guardian Request Body is \n" + requestbody);
	
    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
	String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
	String person_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
	log("Step End: Guardian created with id "+person_id);
	
	log("Step 2: Create the Dependent in NG EPM");
	NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");
	
    String dependentrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createdependent);
    System.out.println("Dependent Request Body is \n" + dependentrequestbody);
		
	String dependentperson_id=NGAPIUtils.setupNGHttpPostRequest("CAGateway",finalURL,dependentrequestbody , 201);
	log("Step End: Dependent created with id "+dependentperson_id);
	
	log("Step 3: Using Post Enrollment call, Verify the MF agent trigger for new patient");
	String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
	NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
	
	log("Step End: MF agent initiate the enrollment automatically");

	log("Step 4: Verify the enrollment status of guardian and dependent");
	String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
	String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
	
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
	CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
	log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
	
	String getEnrollmentURL1 =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
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
	
	verifyProcessingStatusto3(person_id.trim());
	verifyProcessingStatusto3(dependentperson_id.trim());
	
		Mailinator mail = new Mailinator();
		Thread.sleep(15000);
		log("Step 6: Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
		log("Step End: Guradian mail is received");
		
		log("Step 7: Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
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

		VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered");
		
		log("Step 15: Using mailinator Mailer to retrieve the latest emails for patient and guardian");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + MemberConfirmationMessage + "     :   " + PortalURL);
		Thread.sleep(60000);
		String MemberConfirmationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), MemberConfirmationMessage, PortalURL, 40);
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
		getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Completed"); 
		log("Step End: Guardian enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		getEnrollmentURL1 =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
		}
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateGuardianAndDependentANDEnrollOnlyDependentToPortal() throws Throwable {
		log("Test Case:  Verify the patient enrollment e2e workflow when a Guardian doesnot have an existing MF account");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		Long timestamp = System.currentTimeMillis();
		
		String guardianFirstName = "Guardian" + (new Date()).getTime();
		String guardianLastName = "Guardian" + (new Date()).getTime();
		System.setProperty("ParentEmailAddress", guardianFirstName+ "@mailinator.com");
		
		ObjectMapper objMap = new ObjectMapper();		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	

		log("Step 1: Create the Dependent in NG EPM");
		NewPatient createdependent = NGPatient.patientUsingJSON(PropertyLoaderObj,"Dependent");
		
	    String dependentrequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createdependent);
	    System.out.println("Dependent Request Body is \n" + dependentrequestbody);
			
		String dependentperson_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,dependentrequestbody , 201);
		log("Step End: Dependent created with id "+dependentperson_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for dependent");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		
		log("Step End: MF agent initiate the enrollment automatically");

		log("Step 3: Verify the enrollment status of dependent");		
		String getEnrollmentURL1 =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Pending"); 
		log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	              
		log("Step 4: Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr =DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+dependentperson_id.trim()+"'");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+dependentperson_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3(dependentperson_id.trim());
		
			Mailinator mail = new Mailinator();
			Thread.sleep(15000);		
			log("Step 6: Verify the dependent mail");
			Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NewDependentActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(), NewDependentActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
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
				
			VerifyGetPIDCCall(timestamp, dependentperson_nbr,createdependent.getFirstName(), createdependent.getLastName(),"Registered");
			
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
			getEnrollmentURL1 =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
			GetEnrollmentStatusresponse1 =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL1,200);
			
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"status"),"equals","9"); 
			CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"),"equals","Completed"); 
			log("Step End: Dependent enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1,"statusDescription"));
	}

	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLACreateTrustedRepresentativeOnly() throws Throwable {
		log("Test Case:  Verify the patient enrollment e2e workflow when a family account access to patient (Trusted Representative account)");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
				
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
		
		log("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String enrollment_status1 =DBUtils.executeQueryOnDB("NGCoreDB","select enrollment_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		CommonUtils.VerifyTwoValues(enrollment_status1,"equals","1");
		
		verifyProcessingStatusto3(person_id.trim());

  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 4: Moving to the link obtained from the email message- Patient 1");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			System.out.println(sDate1);
			
		
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
		String trustedperson_id=NGPatient.CreateNGPatient(trustedPatient);
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
	
	@Test
	public void testEnrollPatientHavingInvalidZipToMFPortal() throws Throwable {
		log("Test Case:  Verify the patient having invalid zip is unable to enroll to MF portal and Enrollment status is NOT ENROLLED");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		
		log("Step 1: Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(PropertyLoaderObj,"invalidZIP");
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(createPatient);
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=NGAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
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
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","0"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Not Enrolled"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription") +" and Error Message: "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"errorMessage"));
	}
	
	
	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeactivatePatientEnrollment() throws Throwable {
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
		
		VerifyGetPIDCCall(timestamp, person_nbr,PatientFirstName, PatientLastName,"DEACTIVATED");
		
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
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
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
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
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

		log("Step 7: Do get processing status call and verify the processing time of registration mail to be received");

		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()).replaceAll("jobID", jobID.toLowerCase());
		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
	
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
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			System.out.println(sDate1);				
			
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
        System.out.println("Request Body is \n" + requestbody);
		
	    apiRoutes baseURL = apiRoutes.valueOf("BaseCAGatewayURL");
	    apiRoutes personURL =apiRoutes.valueOf("AddPerson"); 
		String finalURL =baseURL.getRouteURL() + personURL.getRouteURL();	
		String person_id=ngAPIUtils.setupNGHttpFirstPostRequest("CAGateway",finalURL,requestbody , 201);
		log("Step End: Person created with id "+person_id);
		
		log("Step 2: Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",PostEnrollmentURL,"" , 409);
		log("Step End: MF agent initiate the enrollment automatically");
		
		log("Step 3: Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL =EnterprisebaseURL.getRouteURL()+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse =ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway",getEnrollmentURL,200);
		
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"status"),"equals","1"); 
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"),"equals","Pending"); 
		log("Step End: Patient enrollment status is "+CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse,"statusDescription"));
				
		Thread.sleep(120000);
  		Mailinator mail = new Mailinator();
			Thread.sleep(15000);
			Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + PortalConstants.NewPatientActivationMessage + "     :   " + PortalConstants.NewPatientActivationMessageLinkText);
			Thread.sleep(60000);
			String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(), PortalConstants.NewPatientActivationMessage, PortalConstants.NewPatientActivationMessageLinkText, 40);
			Log4jUtil.log("Step 10: Moving to the link obtained from the email message");
			Assert.assertNotNull(activationUrl, "Error: Activation link not found.");
			Thread.sleep(20000);
			String sDate1 = PropertyLoaderObj.getProperty("DOBMonth")+"/"+PropertyLoaderObj.getProperty("DOBDay")+"/"+PropertyLoaderObj.getProperty("DOBYear");
			System.out.println(sDate1);				
			
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
	
	public void verifyMFJOBStatus(String person_id) throws Throwable{
		
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

		log("Step Begins: Do get processing status call and verify the processing time of registration mail to be received");

		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()).replaceAll("jobID", jobID.toLowerCase());
		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
		Assert.assertTrue(completed, "Message processing was not completed in time");
	
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
	
	public void VerifyGetPIDCCall(Long timestamp, String person_nbr,String FName, String LName,String portalStatus) throws IOException, ParserConfigurationException, SAXException, InterruptedException{

		Thread.sleep(20000);
		Log4jUtil.log("Step Begins: Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(PropertyLoaderObj.getPIDCURL().replaceAll("integrationID", PropertyLoaderObj.getIntegrationPracticeID()) + "?since=" + since + ",0", PropertyLoaderObj.getResponsePath());
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
	
	public void verifyProcessingStatusto3(String person_id) throws Throwable{
		String processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
		if(processing_status.equals("1")||processing_status.equals("2")){
			log("Processing status is "+processing_status+" i.e. Enrollment record is sent to RSDK.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status =DBUtils.executeQueryOnDB("NGCoreDB","select processing_status from pxp_enrollments where person_id = '"+person_id.trim()+"'");
	            if (processing_status.equals("2")) {
	            	verifyMFJOBStatus(person_id);
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
}
