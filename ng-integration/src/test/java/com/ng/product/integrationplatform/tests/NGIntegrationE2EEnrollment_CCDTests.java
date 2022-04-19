//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.flows.NGPatient;
import com.ng.product.integrationplatform.flows.PatientEnrollment;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonFlows;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGIntegrationE2EEnrollment_CCDTests extends BaseTestNGWebDriver {

	private PropertyFileLoader propertyLoaderObj;

	int arg_timeOut = 1800;
	NGAPIUtils ngAPIUtils;
	String enterprisebaseURL;
	NGAPIFlows ngAPIFlows;

	private static final String NEWDEPENDENT_ACTIVATION_MESSAGE = "You are invited to create a Patient Portal guardian account at";
	private static final String MEMBER_CONFIRMATION_MESSAGE = "New Member Confirmation";
	private static final String PORTAL_URL = "Visit our patient portal now";
	private static final String INVITE_EMAIL_SUBJECT_REPRESENTATIVE = "You're invited to create a Portal account to be a trusted representative of a patient at";
	private static final String INVITE_EMAIL_BUTTON_TEXT = "Sign Up!";
	private static final String WELCOME_EMAIL_BUTTON_TEXT = "Visit our patient portal now";
	private static final String WELCOME_EMAIL_SUBJECT_PATIENT = "New Member Confirmation";

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

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithOnlyMandatoryDemographicsToMFPortal() throws Throwable {
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String personId = ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + personId);

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", personId);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String getEnrollmentStatusresponse = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");

		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		if (processing_status.equals("1")) {
			log("Processing status is " + processing_status
					+ " i.e. First time enrollment record is inserted into table.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
				if (processing_status.equals("2")) {
					log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is sent to RSDK.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "2");

		logStep("Verify the status of MF agent job");
		DBUtils.executeQueryOnDB("MFAgentDB", "select status from processingstatus_entity where entityidentifier ='"
				+ person_nbr.trim().replace("\t", "") + "'");
		String jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");
		log("Status of MF agent job " + jobStatus1);
		if (!jobStatus1.isEmpty()) {
			if (jobStatus1.equalsIgnoreCase("Pending"))
				log("Step End: Request is sent to RSDK and inprocess with job status " + jobStatus1);
			else if (jobStatus1.equalsIgnoreCase("COMPLETED"))
				log("Registration URL is received");
		} else {
			log("Please check Bad request or MF agent is not working");
		}

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getOAuthUsername(),
				propertyLoaderObj.getOAuthPassword());

		Thread.sleep(60000);
		logStep("Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "'");

		if (emailStatus.equalsIgnoreCase("Pending")) {
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus = DBUtils.executeQueryOnDB("MFAgentDB",
						"select status from processingstatus_entity where entityidentifier ='"
								+ person_nbr.trim().replace("\t", "") + "'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
					log("Step End: Mail sent to patient successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(emailStatus, "equals", "COMPLETED");

		String jobStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");
		CommonUtils.VerifyTwoValues(jobStatus, "equals", "COMPLETED");
		log("Step End: The processing status of MF agent job is " + jobStatus
				+ " registration mail is sent to patient for enrollment");

		logStep("Verify the processing status of patient in pxp_enrollment table");
		processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");

		if (processing_status.equals("2")) {
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
				if (processing_status.equals("3")) {
					log("Step End: Processing status is " + processing_status + ", RSDK has sent email to patient.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "3");
		enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		log("Step End: Enrollment status is " + enrollment_status);

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		logStep("Moving to the link obtained from the email message");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		Thread.sleep(20000);
		String sDate1 = propertyLoaderObj.getProperty("dob.month") + "/" + propertyLoaderObj.getProperty("dob.day") + "/"
				+ propertyLoaderObj.getProperty("dob.year");
		log(sDate1);

		logStep("Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		Thread.sleep(5000);
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();

		Thread.sleep(20000);
		logStep("Do a GET on PIDC Url to get registered patient");
		Long since = timestamp / 1000L - 60 * 24;
		Log4jUtil.log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getPIDCURL().replaceAll("integrationID", propertyLoaderObj.getIntegrationPracticeID())
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());
		Thread.sleep(2000);
		RestUtils.isPatientRegistered(propertyLoaderObj.getResponsePath(), person_nbr.trim().replace("\t", ""),
				createPatient.getFirstName(), createPatient.getLastName(), null);

		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");

		PatientEnrollment.verifyProcessingStatusto4(personId);

		String getEnrollmentStatusresponse2 = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"),
				"equals", "Completed");
		log("Result: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));

		logStep("Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status2, "equals", "9");
		String processing_status2 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(processing_status2, "equals", "4");
		log("Step End: Patient enrollment status is " + enrollment_status2 + " which is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));

	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsToMFPortal() throws Throwable {
		logStep("Create the patient in NG EPM without First Name");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutFirstName");

		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 400);
		log("Step End: Person should not be created");

		logStep("Create the patient in NG EPM without Last Name");
		NewPatient createPatient1 = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutLastName");

		log("Request Body is \n" + objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient1));
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient1), 400);
		log("Step End: Person should not be created");

		logStep("Create the patient in NG EPM without Dob");
		NewPatient createPatient2 = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutDOB");

		log("Request Body is \n" + objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient2));
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient2), 400);
		log("Step End: Person should not be created");

		logStep("Create the patient in NG EPM without Gender");
		NewPatient createPatient3 = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutSex");
		log("Request Body is \n" + objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient3));

		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient3), 400);
		log("Step End: Person should not be created");

	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsEmailAddressToMFPortal() throws Throwable {
		logStep("Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutEmailaddress");

		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("person created with id " + person_id);

		logStep("Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 400);
		log("MF agent doesnot initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL, 404);
		log("No Enrollment status found for the patient");
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientWithoutMandatoryDemographicsZipToMFPortal() throws Throwable {
		logStep("Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "withoutZip");

		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("person created with id " + person_id);

		logStep("Using Post Enrollment call, Verify the MF agent should not trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 400);
		log("MF agent doesnot initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL, 404);
		log("No Enrollment status found for the patient");
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testADeletePatientEnrollment() throws Throwable {
		Long timestamp = System.currentTimeMillis();

		String personId = registerNGPatienttoMFPortal();
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + personId.trim() + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + personId.trim() + "'");

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());
		log("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(patientFirstName, patientLastName);
		patientSearchPage.clickOnPatient(patientFirstName, patientLastName);
		log("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(patientFirstName, patientLastName);

		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, patientFirstName, patientLastName,
				"DELETED", propertyLoaderObj.getIntegrationPracticeID());

		Thread.sleep(60000);
		logStep("Verify the enrollment and processing status of patient in pxp enrollment table");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");

		if (processing_status.equals("3") || processing_status.equals("4")) {
			log("Processing status is " + processing_status + " RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
				if (processing_status.equals("5")) {
					log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is deleted successfully.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "5");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "11");

		logStep("Verify the enrollment status of patient");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"11");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Account Deleted");
		log("Step End: Patient enrollment status is " + enrollment_status + " which is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testACreateDependentAndGuardian() throws Throwable {
		Long timestamp = System.currentTimeMillis();

		logStep("Create the Guardian in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway",
				propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1"),
				propertyLoaderObj.getProperty("ng.enterprise1.practice1"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());

		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Guardian Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Guardian created with id " + person_id);

		String locationName = propertyLoaderObj.getProperty("ng.e1.p1.location");
		String providerName = propertyLoaderObj.getProperty("ng.e1.p1.provider");
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Create the Dependent in NG EPM");
		NewPatient createdependent = NGPatient.patientUsingJSON(propertyLoaderObj, "Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createdependent);
		String dependentrequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createdependent);
		log("Dependent Request Body is \n" + dependentrequestbody);

		String dependentperson_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				dependentrequestbody, 201);
		log("Step End: Dependent created with id " + dependentperson_id);

		NGAPIFlows.addCharttoProvider(locationName, providerName, dependentperson_id);
		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);

		postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);

		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of guardian and dependent");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Guardian enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		String getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Pending");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + dependentperson_id.trim() + "'");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				person_id.trim(), propertyLoaderObj.getProperty("ng.enterprise1.practice1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));
		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				dependentperson_id.trim(), propertyLoaderObj.getProperty("ng.enterprise1.practice1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));
		
		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		logStep("Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		log("Step End: Guradian mail is received");

		logStep("Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NEWDEPENDENT_ACTIVATION_MESSAGE + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(),
				NEWDEPENDENT_ACTIVATION_MESSAGE, JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		log("Step End: Dependent mail is received");

		logStep("Enroll the Guardian to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationGuardianUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		String patientLogin = createPatient.getEmailAddress();
		logStep("Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(activationDependentUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createdependent.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(),
				createdependent.getEmailAddress());
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, propertyLoaderObj.getPassword(),
				"Parent");

		logStep("Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Logout, login and change patient");
		NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();
		jalapenoHomePage = loginPage.login(patientLogin, propertyLoaderObj.getPassword());
		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
				propertyLoaderObj.getProperty("oauth.password1"));

		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, dependentperson_nbr,
				createdependent.getFirstName(), createdependent.getLastName(), "Registered",
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));

		logStep("Using YopMail Mailer to retrieve the latest emails for patient and guardian");
		Thread.sleep(15000);
		Log4jUtil.log(
				createPatient.getEmailAddress() + "   :    " + MEMBER_CONFIRMATION_MESSAGE + "     :   " + PORTAL_URL);
		Thread.sleep(60000);
		String MemberConfirmationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				MEMBER_CONFIRMATION_MESSAGE, PORTAL_URL, 80);
		if (!MemberConfirmationUrl.isEmpty()) {
			log("The new member confirmation mail is received successfully");
		}

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");

		if (dependentprocessing_status.equals("3")) {
			log("Processing status is " + dependentprocessing_status + " i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim()
								+ "'");
				if (dependentprocessing_status.equals("4")) {
					log("Step End: Processing status is " + dependentprocessing_status
							+ " i.e. Processing status is completed");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "4");
		CommonUtils.VerifyTwoValues(dependentprocessing_status, "equals", "4");
		enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "9");
		enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "9");

		logStep("Verify the enrollment status of guardian and dependent");
		getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Completed");
		log("Step End: Guardian enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Completed");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testACreateGuardianAndDependentANDEnrollOnlyDependentToPortal() throws Throwable {
		Long timestamp = System.currentTimeMillis();

		String guardianFirstName = "Guardian" + (new Date()).getTime();
		String guardianLastName = "Guardian" + (new Date()).getTime();
		System.setProperty("ParentEmailAddress", guardianFirstName + "@yopmail.com");

		ObjectMapper objMap = new ObjectMapper();
		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();

		logStep("Create the Dependent in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createdependent = NGPatient.patientUsingJSON(propertyLoaderObj, "Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createdependent);
		String dependentrequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createdependent);
		log("Dependent Request Body is \n" + dependentrequestbody);

		String dependentperson_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				dependentrequestbody, 201);
		log("Step End: Dependent created with id " + dependentperson_id);

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		NGAPIFlows.addCharttoProvider(locationName, providerName, dependentperson_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for dependent");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);

		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of dependent");
		String getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Pending");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentperson_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + dependentperson_id.trim() + "'");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				dependentperson_id.trim(), propertyLoaderObj.getProperty("ng.main.practice.id"),
				propertyLoaderObj.getIntegrationPracticeID());

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		logStep("Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NEWDEPENDENT_ACTIVATION_MESSAGE + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(),
				NEWDEPENDENT_ACTIVATION_MESSAGE, JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		log("Step End: Dependent mail is received");

		logStep("Enroll the Dependent to MedFusion Portal");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationDependentUrl);
		Thread.sleep(3000);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createdependent.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(),
				createdependent.getEmailAddress());
		SecurityDetailsPage accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly(guardianFirstName,
				guardianLastName, "Parent");

		logStep("Finishing of dependent activation: Filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createdependent.getEmailAddress(), propertyLoaderObj.getPassword(),
				propertyLoaderObj.getSecretQuestion(), propertyLoaderObj.getSecretAnswer(),
				propertyLoaderObj.getPhoneNumber());

		logStep("Logout from Portal");
		jalapenoHomePage.LogoutfromNGMFPortal();

		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, dependentperson_nbr,
				createdependent.getFirstName(), createdependent.getLastName(), "Registered",
				propertyLoaderObj.getIntegrationPracticeID());

		logStep("Verify the enrollment and processing status of dependent in pxp_enrollment table");
		String dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");

		if (dependentprocessing_status.equals("3")) {
			log("Processing status is " + dependentprocessing_status + " i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim()
								+ "'");
				if (dependentprocessing_status.equals("4")) {
					log("Step End: Processing status is " + dependentprocessing_status
							+ " i.e. Processing status is completed");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(dependentprocessing_status, "equals", "4");
		enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "9");

		logStep("Verify the enrollment status of dependent");
		getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Completed");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testALACreateTrustedRepresentativeOnly() throws Throwable {
		logStep("Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + person_id);

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		log("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				person_id.trim(), propertyLoaderObj.getProperty("ng.main.practice.id"),
				propertyLoaderObj.getIntegrationPracticeID());

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 40);
		logStep("Moving to the link obtained from the email message- Patient 1");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		Thread.sleep(20000);
		String sDate1 = propertyLoaderObj.getProperty("dob.month") + "/" + propertyLoaderObj.getProperty("dob.day") + "/"
				+ propertyLoaderObj.getProperty("dob.year");
		log(sDate1);

		logStep("Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		logStep("Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Logout");
		NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();

		logStep("Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "trustedPatient");
		trustedPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, trustedPatient);
		String trustedPatientrequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(trustedPatient);
		log("Trusted Patient Request Body " + trustedPatientrequestbody);
		String trustedperson_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				trustedPatientrequestbody, 201);
		log("Step End: Person created with id " + trustedperson_id);

		NGAPIFlows.addCharttoProvider(locationName, providerName, trustedperson_id);

		logStep("Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(), trustedPatient.getLastName(),
				trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new YopMail(driver).getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		patientVerificationPage.getToThisPage(patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(),
				trustedPatient.getEmailAddress());
		accountDetailsPage = linkAccountPage.continueToCreateGuardianOnly(trustedPatient.getFirstName(),
				trustedPatient.getLastName(), "Child");

		Thread.sleep(5000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));

		homePage.LogoutfromNGMFPortal();
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAEnrollPatientHavingInvalidZipToMFPortal() throws Throwable {
		logStep("Create the patient in NG EPM");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "invalidZIP");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + person_id);

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient in pxp_enrollment table");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		if ((processing_status.equals("1")) || (processing_status.equals("2"))) {
			log("Processing status is " + processing_status + ", Enrollment record is inserted into DB");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
				if (processing_status.equals("7")) {
					log("Step End: Processing status is " + processing_status + " Error:" + DBUtils.executeQueryOnDB(
							"NGCoreDB",
							"select error_message from pxp_enrollments where person_id = '" + person_id.trim() + "'"));
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "7");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "0");

		logStep("Verify the enrollment status of patient");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"0");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Not Enrolled");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription")
				+ " and Error Message: "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "errorMessage"));
	}

	@Test(enabled = true, groups = { "acceptance-patientEnrollmentPracticeLevel" }, retryAnalyzer = RetryAnalyzer.class)
	public void testADeactivatePatientEnrollment() throws Throwable {
		Long timestamp = System.currentTimeMillis();

		String personId = registerNGPatienttoMFPortal();
		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + personId.trim() + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + personId.trim() + "'");

		logStep("Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());
		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		logStep("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(patientFirstName, patientLastName);
		patientSearchPage.clickOnPatient(patientFirstName, patientLastName);
		logStep("Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		logStep("Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(patientFirstName, patientLastName);

		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, patientFirstName, patientLastName,
				"DEACTIVATED", propertyLoaderObj.getIntegrationPracticeID());

		Thread.sleep(10000);
		logStep("Verify the enrollment and processing status of patient in pxp enrollment table");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");

		if (processing_status.equals("3") || processing_status.equals("4")) {
			log("Processing status is " + processing_status + " RSDK is processing the request");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
				if (processing_status.equals("6")) {
					log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is deactivated successfully.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "6");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + personId.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "10");

		logStep("Verify the enrollment status of patient");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"10");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Deactivated");
		log("Step End: Patient enrollment status is " + enrollment_status + " which is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

	}

	public String registerNGPatienttoMFPortal() throws Throwable {
		log("Registering the NG patient to MF portal");
		Long timestamp1 = System.currentTimeMillis();

		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + person_id);

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String getEnrollmentStatusresponse = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + person_id.trim() + "'");

		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		if (processing_status.equals("1")) {
			log("Processing status is " + processing_status
					+ " i.e. First time enrollment record is inserted into table.");
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
				if (processing_status.equals("2")) {
					log("Step End: Processing status is " + processing_status
							+ " i.e. Enrollment record is sent to RSDK.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "2");

		logStep("Verify the status of MF agent job");
		DBUtils.executeQueryOnDB("MFAgentDB", "select status from processingstatus_entity where entityidentifier ='"
				+ person_nbr.trim().replace("\t", "") + "'");
		String jobStatus1 = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");
		log("Status of MF agent job " + jobStatus1);
		if (!jobStatus1.isEmpty()) {
			if (jobStatus1.equalsIgnoreCase("Pending"))
				log("Step End: Request is sent to RSDK and inprocess with job status " + jobStatus1);
			else if (jobStatus1.equalsIgnoreCase("COMPLETED"))
				log("Registration URL is received");
		} else {
			log("Please check Bad request or MF agent is not working");
		}

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");

		DBUtils.executeQueryOnDB("MFAgentDB",
				"select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getOAuthUsername(),
				propertyLoaderObj.getOAuthPassword());

		Thread.sleep(60000);
		logStep("Verify the processing status of MF agent job after receiving the registration mail");
		String emailStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "'");

		if (emailStatus.equalsIgnoreCase("Pending")) {
			for (int i = 0; i < arg_timeOut; i++) {
				emailStatus = DBUtils.executeQueryOnDB("MFAgentDB",
						"select status from processingstatus_entity where entityidentifier ='"
								+ person_nbr.trim().replace("\t", "") + "'");
				if (emailStatus.equalsIgnoreCase("COMPLETED")) {
					log("Step End: Mail sent to patient successfully");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(emailStatus, "equals", "COMPLETED");

		String jobStatus = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"
						+ person_nbr.trim().replace("\t", "") + "')");
		CommonUtils.VerifyTwoValues(jobStatus, "equals", "COMPLETED");
		log("Step End: The processing status of MF agent job is " + jobStatus
				+ " registration mail is sent to patient for enrollment");

		logStep("Verify the processing status of patient in pxp_enrollment table");
		processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");

		if (processing_status.equals("2")) {
			for (int i = 0; i < arg_timeOut; i++) {
				processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
				if (processing_status.equals("3")) {
					log("Step End: Processing status is " + processing_status + ", RSDK has sent email to patient.");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}

		CommonUtils.VerifyTwoValues(processing_status, "equals", "3");
		enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		log("Step End: Enrollment status is " + enrollment_status);

		Thread.sleep(60000);
		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		logStep("Moving to the link obtained from the email message");
		assertNotNull(activationUrl, "Error: Activation link not found.");
		Thread.sleep(20000);
		String sDate1 = propertyLoaderObj.getProperty("dob.month") + "/" + propertyLoaderObj.getProperty("dob.day") + "/"
				+ propertyLoaderObj.getProperty("dob.year");
		log(sDate1);

		logStep("Enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();

		logStep("Do a GET on PIDC Url to get registered patient");
		Long since = timestamp1 / 1000L - 60 * 24;
		log("Getting patients since timestamp: " + since);
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getPIDCURL().replaceAll("integrationID", propertyLoaderObj.getIntegrationPracticeID())
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		Thread.sleep(2000);
		log("Step End: RSDK sent enrollment status to MF agent after patient enrollment to MF portal");

		PatientEnrollment.verifyProcessingStatusto4(person_id);

		logStep("Find the patient and check if he is registered");
		String getEnrollmentStatusresponse2 = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"),
				"equals", "Completed");
		log("Result: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));

		logStep("Verify the enrollment status in pxp_enrollments table");
		String enrollment_status2 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status2, "equals", "9");
		String processing_status2 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(processing_status2, "equals", "4");
		log("Step End: Patient enrollment status is " + enrollment_status2 + " which is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse2, "statusDescription"));

		return person_id;
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDPracticeLevelEnrollmentOnDemandCCD() throws Throwable {
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getProperty("ng.main.enterprise.id"),
				propertyLoaderObj.getProperty("ng.main.practice.id"));

		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String integrationPracticeId = propertyLoaderObj.getIntegrationPracticeID();

		logStep("Created the patient in NG EPM Practice " + practiceId);
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		String personId = NGAPIFlows.createPatientinEPM(createPatient);

		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name"),
				integrationPracticeId, enterpriseId, practiceId);

		Thread.sleep(40000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 1);

		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, propertyLoaderObj.getProperty("url"), createPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), "", "");

		CommonFlows.addDataToCCD(locationName, providerName, personId, practiceId);

		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight" + since);

		Thread.sleep(5000);
		logStep("Request for OnDemand CCD");
		CommonFlows.requestCCD(driver, propertyLoaderObj.getProperty("url"), createPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), "", "");

		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
				propertyLoaderObj.getProperty("oauth.password"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.health.data").replaceAll("integrationID", integrationPracticeId)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId + "'");

		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(propertyLoaderObj.getResponsePath(),
				person_nbr.trim().replace("\t", ""));

		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 2);

		logStep("Verify OnDemand CCD is received by patient");
		CommonFlows.IsCCDReceived(driver, propertyLoaderObj.getProperty("url"), createPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), "EncounterHavingALLData", "");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDMSUCCD() throws Throwable {
		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String integrationPracticeId = propertyLoaderObj.getIntegrationPracticeID();
		String url = propertyLoaderObj.getProperty("url");
		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String practiceName = propertyLoaderObj.getProperty("practice.name");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String personId = ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + personId);

		logStep("Add Chart to patient");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, practiceName, integrationPracticeId, enterpriseId,
				practiceId);

		Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 1);

		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "",
				"");

		logStep("Add Encounter to patient chart");
		String encounterId = NGAPIFlows.addEncounter(locationName, providerName, personId);

		logStep("Request for MSU CCD");
		NGAPIFlows.postCCDRequest(locationName, providerName, personId, "MedicalSummaryUtility", encounterId);

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 5);

		logStep("Verify the patient is able to receive MSU CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "",
				"");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDLockedEncounterCCD() throws Throwable {
		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String integrationPracticeId = propertyLoaderObj.getIntegrationPracticeID();
		String url = propertyLoaderObj.getProperty("url");
		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String practiceName = propertyLoaderObj.getProperty("practice.name");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		createPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createPatient);
		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String personId = ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Person created with id " + personId);

		logStep("Add Chart to patient");
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, practiceName, integrationPracticeId, enterpriseId,
				practiceId);

		Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 1);

		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "",
				"");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Adding Test data to patient CCD " + personId);
		logStep("Add Encounter to patient chart");
		String encounterId = NGAPIFlows.addEncounter(locationName, providerName, personId);

		logStep("Add Diagnosis to created encounter");
		NGAPIFlows.addDiagnosis(practiceId, personId, encounterId);

		logStep("Add Medication to created encounter");
		NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", personId, encounterId, "R07.9",
				286939);

		logStep("Add allergy to created encounter");
		NGAPIFlows.addAllergy(locationName, providerName, personId, encounterId, "1000", 2);

		logStep("Add Problem to patient chart");
		NGAPIFlows.addProblem(locationName, providerName, personId, "420543008", "55561003", "Active");

		logStep("Add Immunization to created encounter");
		NGAPIFlows.addNewImmunizationsOrder(locationName, providerName, personId, encounterId);

		logStep("Add lab Order and lab results to patient CCD");
		String labOrder_id = NGAPIFlows.addLabOrder(locationName, providerName, personId, encounterId);
		NGAPIFlows.addLabOrderTest(personId, labOrder_id, "NG001032");
		String ObsPanel_id = NGAPIFlows.addObsPanel(personId, labOrder_id);
		NGAPIFlows.addLabResult(personId, ObsPanel_id, "75325-1");
		NGAPIFlows.updateLabOrder(locationName, providerName, personId, labOrder_id);
		Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter " + encounterId);

		logStep("Request for Locked Encounter CCD");
		NGAPIFlows.postCCDRequest(locationName, providerName, personId, "LockedEncounter", encounterId);

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 4);

		logStep("Verify the patient is able to receive Locked Encounter CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(),
				"EncounterHavingALLData", "");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDDateFilterCCD() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getIntegrationPracticeID();
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight" + since);

		logStep("Login to Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Go to  Health Record Summaries");
		MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);

		log("Step Begins: Click on Request Health Record");
		MedicalRecordSummariesPageObject.selectHealthRecordRequestButton();
		Thread.sleep(6000);

		log("Step Begins: Selecting the date range for the health Data Request");
		MedicalRecordSummariesPageObject.onDemandFilterCCDs(
				MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat(),
				MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.get3MonthsOldDateinYYYY_MM_DDFormat());
		log(MedicalRecordSummariesPageObject.getTodaysDateinYYYY_MM_DDFormat());
		MedicalRecordSummariesPageObject.requestCcdOnDemandFromPopUp();
		Thread.sleep(5000);

		log("Step Begins: Close the onDemand PopUp ");
		MedicalRecordSummariesPageObject.closeOnDemandPopUpButton();

		log("Step Begins: Logout");
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
					propertyLoaderObj.getProperty("oauth.password1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.health.data").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + person_id + "'");

		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(propertyLoaderObj.getResponsePath(),
				person_nbr.trim().replace("\t", ""));

		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, person_id, practiceId, integrationPracticeID, 3);

		logStep("Verify date filter CCD is received by patient");
		CommonFlows.IsCCDReceived(driver, url, username, propertyLoaderObj.getPassword(), "", "");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDGuardianDependentCCD() throws Throwable {
		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String integrationPracticeID = propertyLoaderObj.getIntegrationPracticeID();
		String url = propertyLoaderObj.getProperty("url");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the Guardian in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());

		ObjectMapper objMap = new ObjectMapper();
		String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createPatient);
		log("Guardian Request Body is \n" + requestbody);

		apiRoutes personURL = apiRoutes.valueOf("AddEnterprisePerson");
		String finalURL = enterprisebaseURL + personURL.getRouteURL();
		String person_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
		log("Step End: Guardian created with id " + person_id);

		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Create the Dependent in NG EPM");
		NewPatient createdependent = NGPatient.patientUsingJSON(propertyLoaderObj, "Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createdependent);
		String dependentrequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(createdependent);
		log("Dependent Request Body is \n" + dependentrequestbody);

		String dependentperson_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL,
				dependentrequestbody, 201);
		log("Step End: Dependent created with id " + dependentperson_id);

		NGAPIFlows.addCharttoProvider(locationName, providerName, dependentperson_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);

		postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);

		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of guardian and dependent");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Guardian enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		String getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Pending");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + dependentperson_id.trim() + "'");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				person_id.trim(), practiceId, integrationPracticeID);
		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				dependentperson_id.trim(), practiceId, integrationPracticeID);

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		logStep("Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE
				+ "     :   " + JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE,
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		log("Step End: Guradian mail is received");

		logStep("Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NEWDEPENDENT_ACTIVATION_MESSAGE + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(),
				NEWDEPENDENT_ACTIVATION_MESSAGE, JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		log("Step End: Dependent mail is received");

		logStep("Enroll the Guardian to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationGuardianUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		String patientLogin = createPatient.getEmailAddress();
		logStep("Enroll the Guardian to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getEmailAddress(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Identify Dependent without logging out the patient");
		patientVerificationPage.getToThisPage(activationDependentUrl);
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createdependent.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYearUnderage());

		logStep("Continue registration - check dependent info and fill login credentials");
		linkAccountPage.checkDependentInfo(createdependent.getFirstName(), createdependent.getLastName(),
				createdependent.getEmailAddress());
		jalapenoHomePage = linkAccountPage.linkPatientToCreateGuardian(patientLogin, propertyLoaderObj.getPassword(),
				"Parent");

		logStep("Continue to the portal and check elements");
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));

		logStep("Logout, login and change patient");
		NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();
		jalapenoHomePage = loginPage.login(patientLogin, propertyLoaderObj.getPassword());
		jalapenoHomePage.faChangePatient();
		assertTrue(jalapenoHomePage.assessFamilyAccountElements(true));
		loginPage = jalapenoHomePage.LogoutfromNGMFPortal();

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		String processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		processing_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select processing_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");

		if (dependentprocessing_status.equals("3")) {
			log("Processing status is " + dependentprocessing_status + " i.e. Request is in progress");
			for (int i = 0; i < arg_timeOut; i++) {
				dependentprocessing_status = DBUtils.executeQueryOnDB("NGCoreDB",
						"select processing_status from pxp_enrollments where person_id = '" + dependentperson_id.trim()
								+ "'");
				if (dependentprocessing_status.equals("4")) {
					log("Step End: Processing status is " + dependentprocessing_status
							+ " i.e. Processing status is completed");
					break;
				} else {
					if (i == arg_timeOut - 1)
						Thread.sleep(1000);
				}
			}
		}
		CommonUtils.VerifyTwoValues(processing_status, "equals", "4");
		CommonUtils.VerifyTwoValues(dependentprocessing_status, "equals", "4");
		enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "9");
		enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "9");

		logStep("Verify the enrollment status of guardian and dependent");
		getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		getEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"),
				"equals", "Completed");
		log("Step End: Guardian enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse, "statusDescription"));

		getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		getEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Completed");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(getEnrollmentStatusresponse1, "statusDescription"));

		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, dependentperson_id, practiceId, integrationPracticeID,
				1);

		logStep("Verify Dependent is able to receive Enrollment CCD");
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(),
				"Dependent", "");

		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight" + since);

		logStep("Verify Guardian is able to request dependent On Demand CCD");
		CommonFlows.requestCCD(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(),
				"Dependent", "");

		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
				propertyLoaderObj.getProperty("oauth.password"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.health.data").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + dependentperson_id + "'");

		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(propertyLoaderObj.getResponsePath(),
				person_nbr.trim().replace("\t", ""));

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, dependentperson_id, practiceId, integrationPracticeID,
				2);

		logStep("Verify Dependent is able to receive OnDemand CCD");
		CommonFlows.IsCCDReceived(driver, url, createPatient.getEmailAddress(), propertyLoaderObj.getPassword(),
				"Dependent", "");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDTrustedRepresentativeWithoutMFAccount() throws Throwable {

		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String integrationPracticeID = propertyLoaderObj.getIntegrationPracticeID();
		String url = propertyLoaderObj.getProperty("url");
		String practiceName = propertyLoaderObj.getProperty("practice.name");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);
		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, practiceName, integrationPracticeID, enterpriseId,
				practiceId);

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "trustedPatient");
		String trustedperson_id = NGAPIFlows.createPatientinEPM(trustedPatient);
		NGAPIFlows.addCharttoProvider(locationName, providerName, trustedperson_id);
		log("Step End: Person created with id " + trustedperson_id);

		logStep("Waiting for welcome mail at patient inbox from practice");
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   "
				+ WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String visitPortal = new YopMail(driver).getLinkFromEmail(createPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(visitPortal, "Error: Portal link not found.");
		log("Patient portal url is " + visitPortal);

		logStep("Load login page for the Practice Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, visitPortal);

		logStep("Load login page and log in to Patient 1 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		Thread.sleep(7000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(), trustedPatient.getLastName(),
				trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new YopMail(driver).getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(patientTrustedRepresentativeUrl, "Error: Activation patients link not found.");

		logStep("Redirecting to verification page");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver,
				patientTrustedRepresentativeUrl);

		Thread.sleep(15000);
		logStep("Identify patient");
		AuthUserLinkAccountPage linkAccountPage = patientVerificationPage.fillDependentInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		Thread.sleep(5000);
		logStep("Continue registration - check dependent info and fill trusted representative name");
		linkAccountPage.checkDependentInfo(createPatient.getFirstName(), createPatient.getLastName(),
				trustedPatient.getEmailAddress());
		SecurityDetailsPage accountDetailsPage = linkAccountPage
				.continueToCreateGuardianOnly(trustedPatient.getFirstName(), trustedPatient.getLastName(), "Child");

		Thread.sleep(5000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();

		logStep("Verify Enrollment CCD is received by Trusted Representative for Practice 1");
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeID, 1);

		CommonFlows.IsCCDReceived(driver, url, trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "",
				"");

		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight1 = LocalTime.MIDNIGHT;
		LocalDate today1 = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight1 = LocalDateTime.of(today1, midnight1);
		ZonedDateTime zdt1 = todayMidnight1.atZone(ZoneId.of("America/New_York"));
		long since1 = zdt1.toInstant().toEpochMilli() / 1000;
		log("midnight" + since1);

		Thread.sleep(60000);
		logStep("Request On Demand CCD for Practice 1");
		CommonFlows.requestCCD(driver, url, trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "", "");

		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
				propertyLoaderObj.getProperty("oauth.password"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.health.data").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since1 + ",0",
				propertyLoaderObj.getResponsePath());

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId + "'");

		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(propertyLoaderObj.getResponsePath(),
				person_nbr.trim().replace("\t", ""));

		Thread.sleep(80000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeID, 2);

		logStep("Verify OnDemand CCD is received by Trusted Representative for Practice 1");
		CommonFlows.IsCCDReceived(driver, url, trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword(), "",
				"");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDUnSignedOffLabResultInCCD() throws Throwable {
		String locationName = propertyLoaderObj.getNGE1P1Location();
		String providerName = propertyLoaderObj.getNGE1P1Provider();
		String practiceId = propertyLoaderObj.getNGEnterpiseEnrollmentE1P1();
		String integrationPracticeId = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
		String enterpriseID = propertyLoaderObj.getNGEnterpiseEnrollmentE1();
		String URL = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		String practiceName = propertyLoaderObj.getProperty("practice.name1");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseID, practiceId);
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);
		String username = DBUtils.executeQueryOnDB("NGCoreDB",
				"select email_address from person where person_id = '" + personId + "'");
		
		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, practiceName, integrationPracticeId, enterpriseID,
				practiceId);

		Thread.sleep(60000);
		logStep("Verify the processing status of CCD");
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 1);

		logStep("Verify the patient is able to receive CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL, username, propertyLoaderObj.getPassword(), "", "");

		logStep("Adding Test data to patient CCD " + personId);
		logStep("Add Chart to patient");
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		logStep("Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, personId);

		logStep("Add Diagnosis to created encounter");
		NGAPIFlows.addDiagnosis(practiceId, personId, encounter_id);

		logStep("Add Medication to created encounter");
		NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", personId, encounter_id, "R07.9",
				286939);

		logStep("Add allergy to created encounter");
		NGAPIFlows.addAllergy(locationName, providerName, personId, encounter_id, "1000", 2);

		logStep("Add Problem to patient chart");
		NGAPIFlows.addProblem(locationName, providerName, personId, "420543008", "55561003", "Active");

		logStep("Add lab Order and lab results to patient CCD");
		String labOrder_id = NGAPIFlows.addLabOrder(locationName, providerName, personId, encounter_id);
		NGAPIFlows.addLabOrderTest(personId, labOrder_id, "NG001032");
		String ObsPanel_id = NGAPIFlows.addObsPanel(personId, labOrder_id);
		NGAPIFlows.addLabResult(personId, ObsPanel_id, "75325-1");
		Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter without signing off lab order"
				+ encounter_id);

		logStep("Request for Locked Encounter CCD");
		NGAPIFlows.postCCDRequest(locationName, providerName, personId, "LockedEncounter", encounter_id);

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, personId, practiceId, integrationPracticeId, 4);

		logStep("Verify the patient is able to receive Locked Encounter CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL, username, propertyLoaderObj.getPassword(), "HavingUnSignedOffResult",
				"");
	}

	@Test(enabled = true, groups = { "acceptance-CCD" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCCDSensitiveMSUONDemandLockedEncounterCCD() throws Throwable {
		String locationName = propertyLoaderObj.getNGE1P1Location();
		String providerName = propertyLoaderObj.getNGE1P1Provider();
		String practiceId = propertyLoaderObj.getNGEnterpiseEnrollmentE1P1();
		String integrationPracticeId = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
		String enterpriseID = propertyLoaderObj.getNGEnterpiseEnrollmentE1();
		String URL = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		String practiceName = propertyLoaderObj.getProperty("practice.name1");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseID, practiceId);

		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String person_id = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);
		String username = DBUtils.executeQueryOnDB("NGCoreDB",
				"select email_address from person where person_id = '" + person_id + "'");
		
		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, person_id, practiceName, integrationPracticeId, enterpriseID,
				practiceId);

		Thread.sleep(40000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, person_id, practiceId, integrationPracticeId, 1);

		logStep("Verify the patient is able to receive CCD");
		CommonFlows.IsCCDReceived(driver, URL, username, propertyLoaderObj.getPassword(), "", "");

		logStep("Add Chart to patient");
		NGAPIFlows.addCharttoProvider(locationName, providerName, person_id);

		logStep("Add Encounter to patient chart");
		String encounter_id = NGAPIFlows.addEncounter(locationName, providerName, person_id);

		logStep("Add Diagnosis to created encounter");
		NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);

		logStep("Add Medication to created encounter");
		NGAPIFlows.addMedication(practiceId, locationName, providerName, "Active", person_id, encounter_id, "R07.9",
				286939);

		logStep("Add allergy to created encounter");
		NGAPIFlows.addAllergy(locationName, providerName, person_id, encounter_id, "1000", 2);

		logStep("Add Problem to patient chart");
		NGAPIFlows.addProblem(locationName, providerName, person_id, "420543008", "55561003", "Active");

		logStep("Add lab Order and lab results to patient CCD");
		String labOrder_id = NGAPIFlows.addLabOrder(locationName, providerName, person_id, encounter_id);
		NGAPIFlows.addLabOrderTest(person_id, labOrder_id, "NG001032");
		String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
		NGAPIFlows.addLabResult(person_id, ObsPanel_id, "75325-1");
		NGAPIFlows.updateLabOrder(locationName, providerName, person_id, labOrder_id);
		Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter " + encounter_id);

		NGAPIFlows.updateToSensitiveEncounter(locationName, providerName, person_id, encounter_id);

		logStep("Request for MSU CCD");
		NGAPIFlows.postCCDRequest(locationName, providerName, person_id, "MedicalSummaryUtility", encounter_id);

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, person_id, practiceId, integrationPracticeId, 5);

		logStep("Verify the patient is able to receive MSU CCD");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL, username, propertyLoaderObj.getPassword(), "HavingSensitiveEncounterMSU",
				"");

		logStep("Generate Since time for the GET API Call.");
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("America/New_York"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
		ZonedDateTime zdt = todayMidnight.atZone(ZoneId.of("America/New_York"));
		long since = zdt.toInstant().toEpochMilli() / 1000;
		log("midnight" + since);

		Thread.sleep(5000);
		logStep("Request for OnDemand CCD");
		CommonFlows.requestCCD(driver, URL, username, propertyLoaderObj.getPassword(), "", "");

		logStep("Setup Oauth Token");
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
				propertyLoaderObj.getProperty("oauth.password1"));

		Thread.sleep(60000);
		logStep("Do the Get onDemand Health Data Get API Call.");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.health.data").replaceAll("integrationID", integrationPracticeId)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + person_id + "'");

		logStep("Verify CCD received in the Get Api Call.");
		RestUtils.verifyOnDemandRequestSubmitted(propertyLoaderObj.getResponsePath(),
				person_nbr.trim().replace("\t", ""));

		Thread.sleep(60000);
		CommonFlows.verifyCCDProcessingStatus(propertyLoaderObj, person_id, practiceId, integrationPracticeId, 2);

		logStep("Verify OnDemand CCD is received by patient");
		Thread.sleep(10000);
		CommonFlows.IsCCDReceived(driver, URL, username, propertyLoaderObj.getPassword(),
				"HavingSensitiveEncounterONDemand", "");
	}

}
