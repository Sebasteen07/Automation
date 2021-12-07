// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousDismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.CancelRescheduleDecisionPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.CancResc.PatientIdentificationPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.settings.AdminAppointment;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests02 extends BaseTestNGWebDriver {

	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;
	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	APIVerification aPIVerification = new APIVerification();

	public void setUp(String practiceId1, String userID) throws IOException {
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
		practiceId = practiceId1;
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId, payloadAM.openTokenPayload(practiceId, userID));
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"), headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, dataProvider = "partnerType", groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousWithoutPrivacyPolicyAT(String partnerPractice) throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		log("Step 1: set test data for new patient ");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		testData.setAnonymousFlow(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData("AT", testData, adminuser);
		adminuser.setIsAnonymousFlow(true);
		adminuser.setIsExisting(true);

		testData.setFutureApt(true);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFAnonymous());
		aPIVerification.responseCodeValidation(response, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		logStep("Fetch the rules set in Admin");
		String rule = adminuser.getRule();

		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());
		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		psspatientutils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousAT")
	public void testRescheduleAnonymousviaEmailAT() throws Exception {
		
		log("Test to verify if Reschedule an Appointment via Email Notification");		
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);
		
		log("Cancel Reason are as below-"+adminCancelReasonList);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);
		
		log(CancelReschedulelink+" ---This is cancel link");	
		log("Email Used for Link- "+ testData.getGmailUserName());
		log("Subject of email- "+ subject);
		
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,CancelReschedulelink);
		
		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}
		
		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());		
		
		
		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage =patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT( testData, driver);

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousWithoutPrivacyPolicyNG() throws Exception {
		
		log("Step 1: set test data for new patient ");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		testData.setAnonymousFlow(true);
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		psspatientutils.setTestData("NG", testData, adminuser);
		propertyData.setAdminNG(adminuser);
		adminuser.setIsAnonymousFlow(true);
		adminuser.setIsExisting(true);
		testData.setAnonymousFlow(true);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFAnonymous());
		aPIVerification.responseCodeValidation(response, 200);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Fetch rule and settings from PSS 2.0 Admin portal");
		adminUtils.getInsuranceStateandRule(driver, adminuser, testData);
		String rule = adminuser.getRule();
		log("rule are " + rule);
		rule = rule.replaceAll(" ", "");
		log("Step 4: Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());
		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		psspatientutils.selectAFlow(driver, rule, homePage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousNG")
	public void testRescheduleAnonymousviaEmailNG() throws Exception {
		
		log("Test to verify if Reschedule an Appointment via Email Notification");		
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);
		
		log("Cancel Reason are as below-"+adminCancelReasonList);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");
		
		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);
		
		log(CancelReschedulelink+" ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());
		log("Subject of email- "+ subject);
		
		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver,CancelReschedulelink);
		
		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}
		
		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());		
		
		
		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage =patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		cancelRescheduleDecisionPage.clickReschedule();
		psspatientutils.rescheduleAPT( testData, driver);

	}
	
	@Test(enabled = true, groups = {"AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAnonymousviaEmailNG() throws Exception {
		log("Test to verify if Cancel Appointment button available only after given hours.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("NG", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();
		
		testData.setFutureApt(true);

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		Thread.sleep(1000);
		
		HomePage homepage;

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());
		log("Subject of email- "+ subject);

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", testData.getGmailUserName());
	
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void TestDeleteEmail() throws InterruptedException {
		
		PSSPatientUtils psspatientutils= new PSSPatientUtils();
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com/", "ast@mailinator.com");
	}
	
	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousAT")
	public void testCancelAnonymousviaEmailAT() throws Exception {
		log("Test to verify if Cancel Appointment for anonymous flow for the Athena Partners.");
		log("Step 1: Load test Data from External Property file.");
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		PSSAdminUtils pssadminutils = new PSSAdminUtils();

		AdminAppointment adminAppointment = new AdminAppointment(driver);

		psspatientutils.setTestData("AT", testData, adminuser);

		ArrayList<String> adminCancelReasonList = pssadminutils.getCancelRescheduleSettings(driver, adminuser, testData, adminAppointment);

		log("isShowCancellationRescheduleReason --" + testData.isShowCancellationRescheduleReason());
		log("isShowCancellationReasonPM ---" + testData.isShowCancellationReasonPM());

		boolean can1 = testData.isShowCancellationRescheduleReason();
		boolean can2 = testData.isShowCancellationReasonPM();
		
		testData.setFutureApt(true);

		String rule = adminuser.getRule();

		log("rule set in admin = " + rule);
		rule = rule.replaceAll(" ", "");

		Thread.sleep(1000);
		
		HomePage homepage;

		log("Step 8: Fetch the Cancel/Reschedule link from email");
		Mailinator mail = new Mailinator();
		String subject = testData.getEmailSubject();
		String messageLink = "Reschedule or cancel";
		String CancelReschedulelink = mail.getLinkFromEmail(testData.getGmailUserName(), subject, messageLink, 5);

		log(CancelReschedulelink + " ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());
		log("Subject of email- "+ subject);

		PatientIdentificationPage patientIdentificationPage = new PatientIdentificationPage(driver, CancelReschedulelink);

		log("Step 9: Click on Cancel/Reschedule link from email");

		Thread.sleep(2000);
		if (patientIdentificationPage.isPopUPAn()) {
			patientIdentificationPage.popUPAnClick();
		}

		log("Step 10: Fill Patient details for Identification");
		log("First Name- " + testData.getFirstName());
		log("Last Name- " + testData.getLastName());


		log("Step 11: Verify the appointment details and click on Cancel button");
		CancelRescheduleDecisionPage cancelRescheduleDecisionPage = patientIdentificationPage.fillPatientForm(testData.getFirstName(), testData.getLastName());
		homepage = cancelRescheduleDecisionPage.clickCancel();

		if (can1 == true & can2 == false) {

			homepage.cancelAppointmentWithEmail("CANCEL");

		} else if (can1 == true & can2 == true) {

			log("True- True Conditions follow");

			homepage.cancelAppointmentPMReasonviaEmail(adminCancelReasonList);

		} else if (can1 == false & can2 == false) {

			log("False- False Conditions follow");
			String popupmsg =
					"We understand that there are times when you must miss an appointment due to emergencies or obligations for work or family. However, when you do not call to cancel an appointment, you may be preventing another patient from getting much needed treatment. If an appointment is not cancelled in advance you may be charged a fee; this will not be covered by your insurance company.";
			String confirmCancelmsg = "Are you sure you want to cancel your appointment?";

			homepage.defaultcancelAppointmentviaEmail(popupmsg, confirmCancelmsg);
		}
		
		psspatientutils.deleteEmail_Mailinator(driver, "https://www.mailinator.com", testData.getGmailUserName());
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyLLNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFLL());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(2000);
		Assert.assertFalse(loginlessPatientInformation.privacyPolicyStatus());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOnLL());
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyLLAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFLL());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(2000);
		Assert.assertFalse(loginlessPatientInformation.privacyPolicyStatus());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOnLL());
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyLLGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFLL());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(2000);
		Assert.assertFalse(loginlessPatientInformation.privacyPolicyStatus());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOnLL());
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPrivacyPolicyLLGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOFFLL());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		Thread.sleep(2000);
		Assert.assertFalse(loginlessPatientInformation.privacyPolicyStatus());
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleOnLL());
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousWithPrivacyPolicyNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleONAnonymous());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());
		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAnonymousAptWithPrivacyPolicy(aptDateTime, testData, driver);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testE2EAnonymousWithPrivacyPolicyAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.privacyPolicyToggleONAnonymous());
		aPIVerification.responseCodeValidation(response, 200);
		logStep("Login To Patient Portal To Verify Privacy Policy");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment - " + testData.getUrlAnonymous());
		AnonymousDismissPage anonymousDismissPage = new AnonymousDismissPage(driver, testData.getUrlAnonymous());
		HomePage homePage = anonymousDismissPage.clickDismiss();
		Thread.sleep(1000);
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homePage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(), Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		aptDateTime.selectDate(testData.getIsNextDayBooking());
		psspatientutils.bookAnonymousAptWithPrivacyPolicy(aptDateTime, testData, driver);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_BillingNotesNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("lockoutkey.ng");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("firstname.lockout.ng");
		String ln = propertyData.getProperty("lastname.lockout.ng");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutmessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientNotesNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("patientnote.type.ng");
		String key=propertyData.getProperty("patientnote.key.ng");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("firstname.lockout.ng");
		String ln = propertyData.getProperty("lastname.lockout.ng");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutmessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_BillingNotesNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("lockoutkey.ng");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("firstname.lockout.ng");
		String ln = propertyData.getProperty("lastname.lockout.ng");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextAlertPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutmessage, "Lockout message is wrong");
		
		homePage.clickAlertPopUp();
		
		boolean bool=homePage.isbtnstartSchedulingPresent();
		
		assertEquals(bool, true, "Alert workflow is wrong");		
		log("Start Schedule Button is visible. So Test Case passed.");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_PatientNotesNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("patientnote.type.ng");
		String key=propertyData.getProperty("patientnote.key.ng");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("firstname.lockout.ng");
		String ln = propertyData.getProperty("lastname.lockout.ng");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutmessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}
	
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_PatientStatusNG() throws Exception {

		logStep("Verify the Address Line 2 from location");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;

		String enMessage = propertyData.getProperty("patientstatus.msg.en.ng");
		String esMessage = propertyData.getProperty("patientstatus.msg.es.ng");
		String alertId = propertyData.getProperty("patientstatus.id.ng");

		String group = propertyData.getProperty("alertgroup.ng");
		String lockouttype = propertyData.getProperty("patientnote.type.ng");
		String key = propertyData.getProperty("patientstatus.key.ng");

		String lockoutPayload = payloadAM.patientStatusPost(alertId, key, lockouttype, group, enMessage, esMessage,
				enMessage);

		logStep("Remove the already set announcement ");
		response = postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("preventsched.past.fn");
		String ln = propertyData.getProperty("preventsched.past.ln");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextAlertPopUpMsg();

		assertEquals(actualPopUpMessage, enMessage, "Alert message is wrong");

		homePage.clickAlertPopUp();
		boolean bool = homePage.isbtnstartSchedulingPresent();

		assertEquals(bool, true, "Alert workflow is wrong");
		log("Start Schedule Button is visible. So Test Case passed.");
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientStatusNG() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("patientnote.type.ng");
		String key=propertyData.getProperty("patientnote.key.ng");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("firstname.lockout.ng");
		String ln = propertyData.getProperty("lastname.lockout.ng");
		String dob = propertyData.getProperty("dob.lockout.ng");
		String gender = propertyData.getProperty("gender.lockout.ng");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutmessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutMessageNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.LockoutAndNotification(driver, adminUser, testData);
		
		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlertMessageNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		adminUser.setLastQuestionMandatory(true);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.alertsAndNotification(driver, adminUser, testData);		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlertMessageGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.alertsAndNotification(driver, adminUser, testData);		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlertMessageGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.alertsAndNotification(driver, adminUser, testData);		
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlertMessageAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.alertsAndNotification(driver, adminUser, testData);		
	}

	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"AT"}};
		return obj;
	}
}
