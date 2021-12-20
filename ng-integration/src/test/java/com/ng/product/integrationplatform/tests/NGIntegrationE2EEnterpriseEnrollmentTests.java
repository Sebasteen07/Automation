//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.pojo.ExpectedEmail;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.common.utils.YopMail;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginEnrollment;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AccountPage.JalapenoAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.AuthUserLinkAccountPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.PatientVerificationPage;
import com.medfusion.product.object.maps.patientportal2.page.CreateAccount.SecurityDetailsPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.patientportal2.utils.JalapenoConstants;
import com.medfusion.qa.mailinator.Email;
import com.medfusion.qa.mailinator.Mailer;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.flows.NGPatient;
import com.ng.product.integrationplatform.flows.PatientEnrollment;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGIntegrationE2EEnterpriseEnrollmentTests extends BaseTestNGWebDriver {

	private PropertyFileLoader propertyLoaderObj;

	int arg_timeOut = 1800;
	NGAPIUtils ngAPIUtils;
	String enterprisebaseURL;
	NGAPIFlows ngAPIFlows;

	private static final String INVITE_EMAIL_SUBJECT_REPRESENTATIVE = "You're invited to create a Portal account to be a trusted representative of a patient at";
	private static final String INVITE_EMAIL_BUTTON_TEXT = "Sign Up!";
	private static final String INVITE_EMAIL_SUBJECT_PATIENT = "You're invited to create a Patient Portal account at";
	private static final String WELCOME_EMAIL_BODY_PATTERN_PRACTICE = "Thank you for creating an account with PracticeName";
	private static final String WELCOME_EMAIL_SUBJECT_PATIENT = "New Member Confirmation";
	private static final String WELCOME_EMAIL_BUTTON_TEXT = "Visit our patient portal now";
	private static final String NEWDEPENDENT_ACTIVATION_MESSAGE = "You are invited to create a Patient Portal guardian account at";

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

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEAutoPatientEnrollmentP2() throws Throwable {
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM first practice");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);
		NGAPIFlows.addEncounter(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(), personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		Thread.sleep(60000);
		Long timestamp = System.currentTimeMillis();
		logStep("Create the chart in second practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());

		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				personId);
		NGAPIFlows.addEncounter(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(), personId);

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p2"));

		Thread.sleep(90000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		String visitPortal = new YopMail(driver).getLinkFromEmail(createPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(visitPortal, "Error: Portal link not found.");
		log("Patient portal url is " + visitPortal);

		logStep("Log into Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, visitPortal);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to First Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name1"));

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username2"),
				propertyLoaderObj.getProperty("oauth.password2"));
		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, createPatient.getFirstName(),
				createPatient.getLastName(), "Registered", propertyLoaderObj.getProperty("integration.practice.id.e1.p2"));
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getProperty("ng.enterprise1.practice2"),
				propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1"));
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEAutoPatientEnrollmentP1() throws Throwable {
		List<ExpectedEmail> mails = new ArrayList<ExpectedEmail>();

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in first Practice");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));
		log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT
				+ propertyLoaderObj.getProperty("practice.name1") + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String activationUrlP1 = new YopMail(driver).getLinkFromEmail(createPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_PATIENT + propertyLoaderObj.getProperty("practice.name1"),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 40);
		assertNotNull(activationUrlP1, "Error: Activation link not found.");

		logStep("Create the chart in second practice");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practiceName3"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p3"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());

		Thread.sleep(60000);
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT,
				WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName",
						propertyLoaderObj.getProperty("practice.name1"))));
		mails.add(new ExpectedEmail(createPatient.getEmailAddress(), WELCOME_EMAIL_SUBJECT_PATIENT,
				WELCOME_EMAIL_BODY_PATTERN_PRACTICE.replace("PracticeName",
						propertyLoaderObj.getProperty("practiceName3"))));
		assertTrue(new YopMail(driver).areAllMessagesInInbox(mails, 80));

		Thread.sleep(10000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to First Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name1"));
		Thread.sleep(40000);
		driver.navigate().refresh();
		homePage.switchToPractice(propertyLoaderObj.getProperty("practiceName3"));

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
				propertyLoaderObj.getProperty("oauth.password1"));
		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, createPatient.getFirstName(),
				createPatient.getLastName(), "Registered", propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getProperty("ng.enterprise1.practice1"),
				propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1"));
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeleteP3AutoPatientEnrollmentP1() throws Throwable {
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		logStep("Create the chart in second practice");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practiceName3"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p3"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());

		Thread.sleep(40000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to First Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name1"));

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getProperty("doctor.login.practice3"),
				propertyLoaderObj.getProperty("doctor.password.practice3"));
		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		logStep("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(), createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(), createPatient.getLastName());
		logStep("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(createPatient.getFirstName(), createPatient.getLastName());

		logStep("Log into Practice2 Portal");
		loginPage = new JalapenoLoginEnrollment(driver, propertyLoaderObj.getProperty("mf.portal.url.practice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		log("Verify the Multiple Practice Toggle is not displayed");
		homePage.VerifyMuiltiplePracticeToggle();

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username3"),
				propertyLoaderObj.getProperty("oauth.password3"));
		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, createPatient.getFirstName(),
				createPatient.getLastName(), "DELETED", propertyLoaderObj.getProperty("integration.practice.id.e1.p3"));

		PatientEnrollment.verifyPatientEnrollmentDeletedStatus(enterprisebaseURL, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeactivateP3AutoPatientEnrollmentP1() throws Throwable {
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		logStep("Create the chart in second practice");
		Long timestamp = System.currentTimeMillis();
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practiceName3"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p3"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());

		Thread.sleep(60000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Log into Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		homePage.LogoutfromNGMFPortal();

		logStep("Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getProperty("doctor.login.practice3"),
				propertyLoaderObj.getProperty("doctor.password.practice3"));
		log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(), createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(), createPatient.getLastName());
		log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(createPatient.getFirstName(), createPatient.getLastName());

		logStep("Log into Practice2 Portal");
		loginPage = new JalapenoLoginEnrollment(driver, propertyLoaderObj.getProperty("mf.portal.url.practice1"));
		homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Verify the Multiple Practice Toggle is not displayed");
		homePage.VerifyMuiltiplePracticeToggle();

		String person_nbr = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		Log4jUtil.log("Step Begins: Setup Oauth client" + propertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
				propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username3"),
				propertyLoaderObj.getProperty("oauth.password3"));
		PatientEnrollment.VerifyGetPIDCCall(propertyLoaderObj, timestamp, person_nbr, createPatient.getFirstName(),
				createPatient.getLastName(), "DEACTIVATED", propertyLoaderObj.getProperty("integration.practice.id.e1.p3"));

		PatientEnrollment.verifyPatientEnrollmentDeactivatedStatus(enterprisebaseURL, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentPracticeLevelE1P4() throws Throwable {
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		logStep("Create the chart in Practice 4");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P4Location(), propertyLoaderObj.getNGE1P4Provider(),
				personId);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String postEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", personId);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", postEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P4(),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p4"));

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT
				+ propertyLoaderObj.getProperty("practiceName4") + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_PATIENT + propertyLoaderObj.getProperty("practiceName4"),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		assertNotNull(activationUrl, "Error: Activation link not found.");

		logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getFirstName(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();

		Thread.sleep(60000);
		logStep("Waiting for welcome mail at patient inbox from fourth practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Log into Practice4 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getFirstName(), propertyLoaderObj.getPassword());

		logStep("Verify the Multiple Practice Toggle is not displayed");
		homePage.VerifyMuiltiplePracticeToggle();

		DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P4(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());

	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeleteP2PatientEnrolledP1P2P3() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		logStep("Create the chart in Practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				personId);

		logStep("Create the chart in Practice 3");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		Thread.sleep(80000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Detecting if Home Page is opened and logout");
		homePage.LogoutfromNGMFPortal();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getProperty("doctor.login.practice2"),
				propertyLoaderObj.getProperty("doctor.password.practice2"));
		logStep("Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(), createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(), createPatient.getLastName());
		logStep("Delete the searched patient");
		patientSearchPage.deletePatient();
		patientSearchPage.verifyDeletedPatient(createPatient.getFirstName(), createPatient.getLastName());

		DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");

		PatientEnrollment.verifyPatientEnrollmentDeletedStatus(enterprisebaseURL, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEDeactivateP2PatientEnrolledP1P2P3() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		logStep("Create the chart in Practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				personId);

		logStep("Create the chart in Practice 3");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		Thread.sleep(80000);
		logStep("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Load login page for the auto enrolled Practice2 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		
		homePage.LogoutfromNGMFPortal();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getProperty("doctor.login.practice2"),
				propertyLoaderObj.getProperty("doctor.password.practice2"));
		log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(createPatient.getFirstName(), createPatient.getLastName());
		patientSearchPage.clickOnPatient(createPatient.getFirstName(), createPatient.getLastName());
		log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(createPatient.getFirstName(), createPatient.getLastName());

		DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");

		PatientEnrollment.verifyPatientEnrollmentDeactivatedStatus(enterprisebaseURL, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P1TrustedRepresentativeE1P4() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		logStep("Create the trusted patient in NG EPM");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "trustedPatient");
		trustedPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, trustedPatient);
		String trustedperson_id = NGAPIFlows.createPatientinEPM(trustedPatient);
		log("Step End: Person created with id " + trustedperson_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P4Location(), propertyLoaderObj.getNGE1P4Provider(),
				trustedperson_id);

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
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P1TrustedRepresentativeE1P2() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		logStep("Create the chart in Enterprise 1 practice 2");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				personId);

		logStep("Create the trusted patient in NG EPM in practice 2");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "trustedPatient");
		trustedPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, trustedPatient);
		String trustedperson_id = NGAPIFlows.createPatientinEPM(trustedPatient);
		log("Step End: Person created with id " + trustedperson_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				trustedperson_id);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, trustedPatient, trustedperson_id, propertyLoaderObj.getProperty("practice.name2"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p2"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());

		logStep("Waiting for welcome mail at patient inbox from practice 2");
		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   "
				+ WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for second Practice");
		String visitPortal = new YopMail(driver).getLinkFromEmail(createPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(visitPortal, "Error: Portal link not found.");
		log("Patient portal url for practice 1 is " + visitPortal);

		logStep("Waiting for welcome mail at Trusted Representative patient inbox from practice 2");
		Thread.sleep(15000);
		log(trustedPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   "
				+ WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for second Practice");
		String Portal2URL = new YopMail(driver).getLinkFromEmail(trustedPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(Portal2URL, "Error: Portal link not found.");
		log("Patient portal url for practice 2 is " + Portal2URL);

		logStep("Load login page for the Practice 2 Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, visitPortal);

		logStep("Load login page and log in to Patient 2 account");
		Thread.sleep(3000);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name2"));

		Thread.sleep(20000);
		JalapenoAccountPage accountPage = homePage.clickOnAccount();

		logStep("Invite Trusted Representative");
		accountPage.inviteTrustedRepresentative(trustedPatient.getFirstName(), trustedPatient.getLastName(),
				trustedPatient.getEmailAddress());

		log("Waiting for invitation email");
		String patientTrustedRepresentativeUrl = new YopMail(driver).getLinkFromEmail(trustedPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
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
		homePage = linkAccountPage.linkPatientToCreateGuardian(trustedPatient.getEmailAddress(),
				propertyLoaderObj.getPassword(), "Spouse");
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		homePage = loginPage.login(trustedPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		loginPage = homePage.LogoutfromNGMFPortal();

		homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();

	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientEnrollmentE1P4TrustedRepresentativeE1P5() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P5());
		logStep("Create the patient in NG EPM for Practice 5");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P5Location(), propertyLoaderObj.getNGE1P5Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practiceName5"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p5"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P5());

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P4());
		logStep("Create the trusted patient in NG EPM for Practice 4");
		NewPatient trustedPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "trustedPatient");
		trustedPatient = NGPatient.addDataToPatientDemographics(propertyLoaderObj, trustedPatient);
		String trustedperson_id = NGAPIFlows.createPatientinEPM(trustedPatient);
		log("Step End: Person created with id " + trustedperson_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P4Location(), propertyLoaderObj.getNGE1P4Provider(),
				trustedperson_id);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, trustedPatient, trustedperson_id, propertyLoaderObj.getProperty("practiceName4"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p4"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P4());

		logStep("Waiting for welcome mail at patient inbox from practice 5");
		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   "
				+ WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String visitPortal = new YopMail(driver).getLinkFromEmail(createPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(visitPortal, "Error: Portal link not found.");
		log("Patient portal url for practice 5 is " + visitPortal);

		logStep("Waiting for welcome mail at Trusted Representative patient inbox from practice 4");
		Thread.sleep(15000);
		log(trustedPatient.getEmailAddress() + "   :    " + WELCOME_EMAIL_SUBJECT_PATIENT + "     :   "
				+ WELCOME_EMAIL_BUTTON_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String Portal4URL = new YopMail(driver).getLinkFromEmail(trustedPatient.getEmailAddress(),
				WELCOME_EMAIL_SUBJECT_PATIENT, WELCOME_EMAIL_BUTTON_TEXT, 80);
		assertNotNull(Portal4URL, "Error: Portal link not found.");
		log("Patient portal url for practice 4 is " + Portal4URL);

		logStep("Load login page for the Practice 5 Portal");
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
				INVITE_EMAIL_SUBJECT_REPRESENTATIVE, INVITE_EMAIL_BUTTON_TEXT, 15);
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

		Thread.sleep(10000);
		logStep("Continue registration - create dependents credentials and continue to Home page");
		accountDetailsPage.fillAccountDetailsAndContinue(trustedPatient.getFirstName(), propertyLoaderObj.getPassword(),
				propertyLoaderObj.getSecretQuestion(), propertyLoaderObj.getSecretAnswer(),
				propertyLoaderObj.getPhoneNumber());
		assertTrue(homePage.assessFamilyAccountElements(false));

		logStep("Log out and log in");
		loginPage = homePage.LogoutfromNGMFPortal();
		Thread.sleep(5000);
		homePage = loginPage.login(trustedPatient.getFirstName(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		loginPage = homePage.LogoutfromNGMFPortal();

		homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());
		assertTrue(homePage.assessFamilyAccountElements(false));
		homePage.LogoutfromNGMFPortal();
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBELEPatientStatusE2P1EnrolledinE1P1() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the patient in NG EPM in Enterprise 1 Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				personId);

		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, propertyLoaderObj.getProperty("practice.name1"),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());

		logStep("Create the chart in Enterprise 2 practice 1");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE2(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE2P1());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE2P1Location(), propertyLoaderObj.getNGE2P1Provider(),
				personId);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", personId);
		ngAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", PostEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of patient after initiation of enrollment using Get Enrollment status call");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", personId);
		String GetEnrollmentStatusresponse = ngAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Patient enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE2P1(),
				propertyLoaderObj.getProperty("integration.practice.id.e2.p1"));

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT
				+ propertyLoaderObj.getProperty("e2.practice.name1") + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		logStep("Logging into YopMail and getting Patient Activation url for first Practice");
		String activationUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_PATIENT + propertyLoaderObj.getProperty("e2.practice.name1"),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		assertNotNull(activationUrl, "Error: Activation link not found.");

		logStep("Moving to the link obtained from the email message and enroll the Patient to MedFusion Portal : step 1 - verifying identity");
		PatientVerificationPage patientVerificationPage = new PatientVerificationPage(driver, activationUrl);
		Thread.sleep(3000);
		SecurityDetailsPage accountDetailsPage = patientVerificationPage.fillPatientInfoAndContinue(
				createPatient.getZip(), propertyLoaderObj.getDOBMonth(), propertyLoaderObj.getDOBDay(),
				propertyLoaderObj.getDOBYear());

		logStep("Enroll the Patient to MedFusion Portal : step 2 - filling patient data");
		JalapenoHomePage jalapenoHomePage = accountDetailsPage.fillAccountDetailsAndContinue(
				createPatient.getFirstName(), propertyLoaderObj.getPassword(), propertyLoaderObj.getSecretQuestion(),
				propertyLoaderObj.getSecretAnswer(), propertyLoaderObj.getPhoneNumber());

		logStep("Detecting if Home Page is opened and logout");
		jalapenoHomePage.LogoutfromNGMFPortal();

		Thread.sleep(60000);
		logStep("Waiting for welcome mail at patient inbox from Enterprise 2 practice 1");
		Email visitPortal = new Mailer(createPatient.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("Patient portal url is " + portalUrlLink);
		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		logStep("Load login page for the Enterprise 2 Practice 1 Portal");
		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createPatient.getFirstName(), propertyLoaderObj.getPassword());

		logStep("Verify the Multiple Practice Toggle is not displayed");
		homePage.VerifyMuiltiplePracticeToggle();

		DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId.trim() + "'");
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, personId,
				propertyLoaderObj.getNGEnterpiseEnrollmentE2P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE2());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBCreateDependentAndGuardianP1P2P3() throws Throwable {

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the Guardian in NG EPM Practice 1");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		System.setProperty("ParentEmailAddress", createPatient.getEmailAddress());
		String person_id = NGAPIFlows.createPatientinEPM(createPatient);
		log("Step End: Guardian created with id " + person_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				person_id);

		logStep("Create the Dependent in NG EPM Practice 1");
		NewPatient createdependent = NGPatient.patientUsingJSON(propertyLoaderObj, "Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createdependent);
		String dependentperson_id = NGAPIFlows.createPatientinEPM(createdependent);
		log("Step End: Dependent created with id " + dependentperson_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				dependentperson_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for new patient");
		String PostEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", person_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", PostEnrollmentURL, "", 409);

		PostEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", PostEnrollmentURL, "", 409);

		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of guardian and dependent");
		String getEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", person_id);
		String GetEnrollmentStatusresponse = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"),
				"equals", "Pending");
		log("Step End: Guardian enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse, "statusDescription"));

		String getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Pending");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"));

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String enrollment_status = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + person_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status, "equals", "1");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				person_id.trim(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));
		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				dependentperson_id.trim(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),
				propertyLoaderObj.getProperty("integration.practice.id.e1.p1"));

		logStep("Create the Guardian chart in second practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				person_id);

		logStep("Create the Dependent chart in second practice");
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				dependentperson_id);

		logStep("Create the Guardian chart in third practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				person_id);

		logStep("Create the Dependent chart in third practice");
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				dependentperson_id);

		YopMail mail = new YopMail(driver);
		Thread.sleep(15000);
		logStep("Verify the Guardian mail");
		Thread.sleep(15000);
		Log4jUtil.log(createPatient.getEmailAddress() + "   :    " + INVITE_EMAIL_SUBJECT_PATIENT
				+ propertyLoaderObj.getProperty("practice.name1") + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationGuardianUrl = mail.getLinkFromEmail(createPatient.getEmailAddress(),
				INVITE_EMAIL_SUBJECT_PATIENT + propertyLoaderObj.getProperty("practice.name1"),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		assertNotNull(activationGuardianUrl, "Error: No Registeration email found with specified subject: "
				+ INVITE_EMAIL_SUBJECT_PATIENT + propertyLoaderObj.getProperty("practice.name1"));
		log("Step End: Guradian mail is received");

		logStep("Verify the dependent mail");
		Log4jUtil.log(createdependent.getEmailAddress() + "   :    " + NEWDEPENDENT_ACTIVATION_MESSAGE
				+ propertyLoaderObj.getProperty("practice.name1") + "     :   "
				+ JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT);
		Thread.sleep(60000);
		String activationDependentUrl = mail.getLinkFromEmail(createdependent.getEmailAddress(),
				NEWDEPENDENT_ACTIVATION_MESSAGE + propertyLoaderObj.getProperty("practice.name1"),
				JalapenoConstants.NEW_PATIENT_ACTIVATION_MESSAGE_LINK_TEXT, 80);
		assertNotNull(activationDependentUrl, "Error: No Registeration email found with specified subject: "
				+ NEWDEPENDENT_ACTIVATION_MESSAGE + propertyLoaderObj.getProperty("practice.name1"));
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

		Thread.sleep(120000);
		logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");
		log("Waiting for welcome mail at patient inbox from second practice");
		Email visitPortal = new Mailer(createdependent.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("patient portal url for Portal 2 is " + portalUrlLink);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		JalapenoLoginEnrollment loginPage = new JalapenoLoginEnrollment(driver, propertyLoaderObj.getProperty("mf.portal.url.practice2"));
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name1"));
		Thread.sleep(40000);
		assertTrue(homePage.assessFamilyAccountElements(true));

		logStep("Switching to dependent account to verify auto enrollment");
		homePage.faChangePatient();
		logStep("Auto Enrolment of Guardian and Dependent to Second and third Practice is completed");

		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, person_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, dependentperson_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, person_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, dependentperson_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, person_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, dependentperson_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	@Test(enabled = true, groups = { "acceptance-EnterpriseEnrollment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBCreateGuardianAndDependentANDEnrollOnlyDependentToP1P2P3() throws Throwable {

		String guardianFirstName = "Guardian" + (new Date()).getTime();
		String guardianLastName = "Guardian" + (new Date()).getTime();
		System.setProperty("ParentEmailAddress", guardianFirstName + "@yopmail.com");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P1());
		logStep("Create the Dependent in NG EPM Practice 1");
		NewPatient createdependent = NGPatient.patientUsingJSON(propertyLoaderObj, "Dependent");
		createdependent = NGPatient.addDataToPatientDemographics(propertyLoaderObj, createdependent);
		String dependentperson_id = NGAPIFlows.createPatientinEPM(createdependent);
		log("Step End: Dependent created with id " + dependentperson_id);
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P1Location(), propertyLoaderObj.getNGE1P1Provider(),
				dependentperson_id);

		logStep("Using Post Enrollment call, Verify the MF agent trigger for dependent");
		String PostEnrollmentURL = enterprisebaseURL
				+ apiRoutes.valueOf("PostEnrollment").getRouteURL().replaceAll("personId", dependentperson_id);
		NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway", PostEnrollmentURL, "", 409);
		log("Step End: MF agent initiate the enrollment automatically");

		logStep("Verify the enrollment status of dependent");
		String getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		String GetEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1,
				200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "status"), "equals",
				"1");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Pending");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"));

		logStep("Verify the enrollment and processing status of patient in pxp_enrollment table");
		String enrollment_status1 = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enrollment_status from pxp_enrollments where person_id = '" + dependentperson_id.trim() + "'");
		CommonUtils.VerifyTwoValues(enrollment_status1, "equals", "1");

		PatientEnrollment.verifyProcessingStatusto3WithoutValidatingGetProcessingStatusCall(propertyLoaderObj,
				dependentperson_id.trim(), propertyLoaderObj.getNGEnterpiseEnrollmentE1P1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentintegrationPracticeIDE1P1());

		logStep("Create the Dependent chart in second practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2());
		Instant testStart = Instant.now();
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P2Location(), propertyLoaderObj.getNGE1P2Provider(),
				dependentperson_id);

		logStep("Create the Dependent chart in third practice");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", propertyLoaderObj.getNGEnterpiseEnrollmentE1(),
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3());
		NGAPIFlows.addCharttoProvider(propertyLoaderObj.getNGE1P3Location(), propertyLoaderObj.getNGE1P3Provider(),
				dependentperson_id);

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
		Thread.sleep(9000);
		NGLoginPage loginPage = jalapenoHomePage.LogoutfromNGMFPortal();

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

		logStep("Verify the enrollment status of dependent in Practice 1");
		getEnrollmentURL1 = enterprisebaseURL
				+ apiRoutes.valueOf("GetEnrollmentStatus").getRouteURL().replaceAll("personId", dependentperson_id);
		GetEnrollmentStatusresponse1 = NGAPIUtils.setupNGHttpGetRequest("EnterpriseGateway", getEnrollmentURL1, 200);

		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "status"), "equals",
				"9");
		CommonUtils.VerifyTwoValues(CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"),
				"equals", "Completed");
		log("Step End: Dependent enrollment status is "
				+ CommonUtils.getResponseKeyValue(GetEnrollmentStatusresponse1, "statusDescription"));

		Thread.sleep(60000);
		logStep("Validate Welcome mail recieved by guardianpatient at Practice Portal2");
		Email visitPortal = new Mailer(createdependent.getEmailAddress())
				.pollForNewEmailWithSubject(WELCOME_EMAIL_SUBJECT_PATIENT, 90, testSecondsTaken(testStart));
		assertNotNull(visitPortal,
				"Error: No Welcome email found recent enough with specified subject: " + WELCOME_EMAIL_SUBJECT_PATIENT);
		String portalUrlLink = Mailer.getLinkByText(visitPortal, WELCOME_EMAIL_BUTTON_TEXT);
		log("patient portal url for Portal 2 is " + portalUrlLink);

		assertTrue(portalUrlLink.length() > 0, "Error: No matching link found in patient welcome email!");

		loginPage = new NGLoginPage(driver, portalUrlLink);
		JalapenoHomePage homePage = loginPage.login(createdependent.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Switching to Second Practice to verify auto enrollment");
		homePage.switchToPractice(propertyLoaderObj.getProperty("practice.name1"));
		log("Auto Enrolment of Dependent to Second Practice is completed");

		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, dependentperson_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P3(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
		PatientEnrollment.verifyPatientEnrollmentStatus(enterprisebaseURL, ngAPIUtils, dependentperson_id,
				propertyLoaderObj.getNGEnterpiseEnrollmentE1P2(), propertyLoaderObj.getNGEnterpiseEnrollmentE1());
	}

	private long testSecondsTaken(Instant testStart) {
		return testStart.until(Instant.now(), ChronoUnit.SECONDS);
	}

}