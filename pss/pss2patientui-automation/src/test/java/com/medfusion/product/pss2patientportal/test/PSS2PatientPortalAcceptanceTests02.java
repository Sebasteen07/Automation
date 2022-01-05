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
import com.medfusion.product.pss2patientui.utils.YopMailUtility;

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
		String userName=testData.getEmail();
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);
		
		log(CancelReschedulelink+" ---This is cancel link");	
		log("Email Used for Link- "+ testData.getGmailUserName());
		
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
		
		yo.deleteEmail(driver, userName);	

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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class, dependsOnMethods="testE2EAnonymousWithPrivacyPolicyNG")
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
		String userName=testData.getEmail();
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);

		
		log(CancelReschedulelink+" ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());
			
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
		
		yo.deleteEmail(driver, userName);

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
		String userName=testData.getEmail();
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);

		log(CancelReschedulelink + " ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());

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
		
		yo.deleteEmail(driver, userName);		
	
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
		String userName=testData.getEmail();
		YopMailUtility yo = new YopMailUtility(driver);
		String CancelReschedulelink = yo.getCancelRescheduleLink(driver, userName);

		log(CancelReschedulelink + " ---This is cancel link");
		log("Email Used for Link- "+ testData.getGmailUserName());

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
		
		yo.deleteEmail(driver, userName);	
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

		logStep("Verify Lockout - Billing Note for NG");
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
	public void testLockout_BillingNotesGE() throws Exception {

		logStep("Verify Lockout - Billing Note for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutMessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockoutType=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("billingnote.key.ge");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockoutType, group, lockoutMessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		
		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.fn.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutMessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
		
		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		
		response=postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientNotesGE() throws Exception {

		logStep("Verify Lockout - Patient Note for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("billingnote.key.ge");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.fn.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");	
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
	public void testLockout_PatientNotesGW() throws Exception {

		logStep("Verify Lockout - Patient Note for GreenWay");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("patientnote.type.ng");
		String key=propertyData.getProperty("patientnote.key.gw");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		
		String fn = propertyData.getProperty("lockout.fn.gw");
		String ln = propertyData.getProperty("lockout.ln.gw");
		String dob = propertyData.getProperty("lockout.dob.gw");
		String gender = propertyData.getProperty("lockout.gender.gw");
		
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

		logStep("Verify Lockout - Patient Note for NG");
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
	public void testAlerts_PatientNotesGE() throws Exception {

		logStep("Verify Alerts - Patient Note for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		
		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);	
		
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("billingnote.key.ge");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.fn.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");	
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
	public void testAlerts_BillingNotesGE() throws Exception {

		logStep("Verify Alerts - Billing Note for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		
		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);	
		
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("billingnote.type.ng");
		String key=propertyData.getProperty("billingnote.key.ge");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.fn.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");	
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
	public void testAlerts_BillingNotesNG() throws Exception {

		logStep("Verify Alerts - Billing Note for NG");
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

		logStep("Verify Alerts - Patient Note for NG");
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
	public void testAlerts_PatientNotesGW() throws Exception {

		logStep("Verify the Patient Note- ALERTS for GreenWay partner");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutmessage=propertyData.getProperty("lockoutbillingnote.ng");
		String lockouttype=propertyData.getProperty("patientnote.type.ng");
		String key=propertyData.getProperty("patientnote.key.gw");
		
		String lockoutPayload=payloadAM.alertAndLocakout(key, key, lockouttype, group, lockoutmessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.gw");
		String ln = propertyData.getProperty("lockout.ln.gw");
		String dob = propertyData.getProperty("lockout.dob.gw");
		String gender = propertyData.getProperty("lockout.gender.gw");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextAlertPopUpMsg();
		
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

		logStep("Verify Alerts - Patient Status Note for NG");
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
	public void testAlerts_PatientStatusAT() throws Exception {

		logStep("Verify Alerts - Patient Status Athena");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;

		String patientMatch = payloadAM.patientMatchAt();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);		

		String group = propertyData.getProperty("alertgroup.ng");
		String lockoutMessage = propertyData.getProperty("patientstatus.msg.en.at");
		String lockouType = propertyData.getProperty("patientstatus.type");
		String key = propertyData.getProperty("patientstatus.key.at");

		String activeL = payloadAM.activeLockoutAT(group);
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		String lockoutid = aPIVerification.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		String lockoutPayload = payloadAM.patientStatusAT(lockoutid, key, lockouType, group, lockoutMessage);
		log("Payload for update lockout- " + lockoutPayload);

		logStep("Set Patient Status- Alert in Athena");
		response = postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);


		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.at");
		String ln = propertyData.getProperty("lockout.ln.at");
		String dob = propertyData.getProperty("lockout.dob.at");
		String gender = propertyData.getProperty("lockout.gender.at");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextAlertPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutMessage, "Alert message is wrong");

		homePage.clickAlertPopUp();
		boolean bool = homePage.isbtnstartSchedulingPresent();

		assertEquals(bool, true, "Alert workflow is wrong");
		log("Start Schedule Button is visible. So Test Case passed.");
		
		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		aPIVerification.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientStatusNG() throws Exception {

		logStep("Verify Lockout - Patient Status for NG");
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
	public void testLockout_PatientStatusGW() throws Exception {

		logStep("Verfiy the Lockout -Patient Status for GreenWay");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		String patientMatch = payloadAM.patientInfoWithOptionalGW();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);
		
		String idPatientStatus= propertyData.getProperty("patientstatus.id.gw");
		String group=propertyData.getProperty("lockoutgroup.ng");
		String lockoutMessage=propertyData.getProperty("patientstatus.msg.en.gw");
		String lockouType=propertyData.getProperty("patientstatus.type");
		String key=propertyData.getProperty("patientstatus.key.gw");
		
		String lockoutPayload=payloadAM.patientStatusGW(idPatientStatus, key, lockouType, group, lockoutMessage, lockoutMessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.gw");
		String ln = propertyData.getProperty("lockout.ln.gw");
		String dob = propertyData.getProperty("lockout.dob.gw");
		String gender = propertyData.getProperty("lockout.gender.gw");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");	
		String actualPopUpMessage=homePage.getTextLockoutPopUpMsg();
		
		assertEquals(actualPopUpMessage, lockoutMessage, "Lockout message is wrong");
		
		response=postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);	
	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientStatusAT() throws Exception {

		logStep("Verfiy the Lockout -Patient Status for Athena");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;

		String patientMatch = payloadAM.patientMatchAt();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);

		String group = propertyData.getProperty("lockoutgroup.ng");
		String lockoutMessage = propertyData.getProperty("patientstatus.msg.en.at");
		String lockouType = propertyData.getProperty("patientstatus.type");
		String key = propertyData.getProperty("patientstatus.key.at");

		String activeL = payloadAM.activeLockoutAT(group);
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		String lockoutid = aPIVerification.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		String lockoutPayload = payloadAM.patientStatusAT(lockoutid, key, lockouType, group, lockoutMessage);
		log("Payload for update lockout- " + lockoutPayload);

		logStep("Remove the already set announcement ");
		response = postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.at");
		String ln = propertyData.getProperty("lockout.ln.at");
		String dob = propertyData.getProperty("lockout.dob.at");
		String gender = propertyData.getProperty("lockout.gender.at");

		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextLockoutPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutMessage, "Lockout message is wrong");

		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		aPIVerification.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockout_PatientStatusGE() throws Exception {

		logStep("Verfiy the Lockout -Patient Status for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		String patientMatch = payloadAM.patientMatchAt();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);

		String group = propertyData.getProperty("lockoutgroup.ng");
		String lockoutEngMessage = propertyData.getProperty("patientstatus.msg.en.ge");
		String lockoutSpanMessage = propertyData.getProperty("patientstatus.msg.es.ge");
		String lockouType = propertyData.getProperty("patientstatus.type");
		String key = propertyData.getProperty("patientstatus.key.ge");

		String activeL = payloadAM.patientStatusGE();
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		String lockoutid = aPIVerification.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		String lockoutPayload = payloadAM.patientStatusUpdateGE(lockoutid, key, lockouType, group, lockoutEngMessage, lockoutSpanMessage);
		log("Payload for update lockout- " + lockoutPayload);

		logStep("Remove the already set announcement ");
		response = postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.ln.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");		
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");
		String actualPopUpMessage = homePage.getTextLockoutPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutEngMessage, "Lockout message is wrong");

		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		aPIVerification.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_PatientStatusGE() throws Exception {

		logStep("Verfiy the Lockout -Patient Status for GE");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		String patientMatch = payloadAM.patientMatchGE();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);

		String group = propertyData.getProperty("alertgroup.ng");
		String lockoutEngMessage = propertyData.getProperty("patientstatus.msg.en.ge");
		String lockoutSpanMessage = propertyData.getProperty("patientstatus.msg.es.ge");
		String lockouType = propertyData.getProperty("patientstatus.type");
		String key = propertyData.getProperty("patientstatus.key.ge");

		String activeL = payloadAM.patientStatusGE();
		response = postAPIRequestAM.lockoutPost(practiceId, activeL, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		String lockoutid = aPIVerification.responseKeyValidationJson(response, "id");

		log("Lockout Id- " + lockoutid);

		String lockoutPayload = payloadAM.patientStatusUpdateGE(lockoutid, key, lockouType, group, lockoutEngMessage, lockoutSpanMessage);
		log("Payload for update lockout- " + lockoutPayload);

		logStep("Remove the already set announcement ");
		response = postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.ge");
		String ln = propertyData.getProperty("lockout.ln.ge");
		String dob = propertyData.getProperty("lockout.dob.ge");
		String gender = propertyData.getProperty("lockout.gender.ge");		
		String email = propertyData.getProperty("lockout.email.ge");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");

		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, email, gender, "", "");
		String actualPopUpMessage = homePage.getTextAlertPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutEngMessage, "Lockout message is wrong");
		
		homePage.clickAlertPopUp();		
		boolean bool=homePage.isbtnstartSchedulingPresent();
		
		assertEquals(bool, true, "Alert workflow is wrong");		
		log("Start Schedule Button is visible. So Test Case passed.");

		response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.deleteLockoutById(practiceId, lockoutid);
		aPIVerification.responseCodeValidation(response, 200);

	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAlerts_PatientStatusGW() throws Exception {

		logStep("Verfiy the Alerts -Patient Status for GreenWay");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		
		String patientMatch = payloadAM.patientInfoWithOptionalGW();
		response = postAPIRequestAM.patientInfoPost(practiceId, patientMatch);
		aPIVerification.responseCodeValidation(response, 200);
		
		String idPatientStatus= propertyData.getProperty("patientstatus.id.gw");
		String group=propertyData.getProperty("alertgroup.ng");
		String lockoutMessage=propertyData.getProperty("patientstatus.msg.en.gw");
		String lockouType=propertyData.getProperty("patientstatus.type");
		String key=propertyData.getProperty("patientstatus.key.gw");
		
		String lockoutPayload=payloadAM.patientStatusGW(idPatientStatus, key, lockouType, group, lockoutMessage, lockoutMessage);
		
		logStep("Remove the already set announcement ");
		response=postAPIRequestAM.lockoutPost(practiceId, lockoutPayload, "/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());		

		logStep("Open the link and click on Dismiss Button ");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();

		String fn = propertyData.getProperty("lockout.fn.gw");
		String ln = propertyData.getProperty("lockout.ln.gw");
		String dob = propertyData.getProperty("lockout.dob.gw");
		String gender = propertyData.getProperty("lockout.gender.gw");
		
		logStep("Enter the below mentioned patient details in demographic page- ");
		log("Demographic Details- " + fn + " " + ln + " " + dob + " " + gender + " ");
		
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(fn, ln, dob, "", gender, "", "");
		String actualPopUpMessage = homePage.getTextAlertPopUpMsg();

		assertEquals(actualPopUpMessage, lockoutMessage, "Alert message is wrong");

		homePage.clickAlertPopUp();
		boolean bool = homePage.isbtnstartSchedulingPresent();

		assertEquals(bool, true, "Alert workflow is wrong");
		log("Start Schedule Button is visible. So Test Case passed.");	
	
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutMessageNG() throws Exception {

		logStep("Verify Lockout Message in Admin UI");
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

		logStep("Verify Alert Message in Admin UI");
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

		logStep("Verify Alert Message in Admin UI");
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

		logStep("Verify Alert Message in Admin UI");
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

		logStep("Verify Alert Message in Admin UI");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminAT(adminUser);
		PSSAdminUtils adminUtils = new PSSAdminUtils();

		logStep("Login to PSS 2.0 Admin portal and do the seetings for Last Question Required");
		adminUtils.alertsAndNotification(driver, adminUser, testData);		
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAddressLine2NG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		String addressLine2 = "";
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(response, 200);
		}
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);
		response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		log("Length is- " + len);
		log("Location is" + testData.getLocation());
		for (int i = 0; i < len; i++) {
			String name = array.getJSONObject(i).getString("name");
			if (name.equalsIgnoreCase(testData.getLocation())) {
				addressLine2 = array.getJSONObject(i).getJSONObject("address").getString("address2");
				log("Address Line 2 is - " + addressLine2);
			}
		}


		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		String addresslineUI = location.address2(addressLine2);
		Assert.assertEquals(addresslineUI, addressLine2);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAddressLine2AT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminAT(adminUser);
		propertyData.setAppointmentResponseAT(testData);
		String addressLine2 = "";
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		log("Length is- " + len);
		log("Location is" + testData.getLocation());
		for (int i = 0; i < len; i++) {
			String name = array.getJSONObject(i).getString("name");
			if (name.equalsIgnoreCase(testData.getLocation())) {
				addressLine2 = array.getJSONObject(i).getJSONObject("address").getString("address2");
				log("Address Line 2 is - " + addressLine2);
			}
		}

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		String addresslineUI = location.address2(addressLine2);
		Assert.assertEquals(addresslineUI, addressLine2);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAddressLine2GW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		String addressLine2 = "";
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);


		response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		log("Length is- " + len);
		log("Location is" + testData.getLocation());
		for (int i = 0; i < len; i++) {
			String name = array.getJSONObject(i).getString("name");
			if (name.equalsIgnoreCase(testData.getLocation())) {
				addressLine2 = array.getJSONObject(i).getJSONObject("address").getString("address2");
				log("Address Line 2 is - " + addressLine2);
			}
		}
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		String addresslineUI = location.address2(addressLine2);
		log("Address Line2 In Admin UI......" + addressLine2);
		log("Address Line2 In Patient UI......" + addresslineUI);
		Assert.assertEquals(addresslineUI, addressLine2);
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAddressLine2GE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		String addressLine2 = "";
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);

		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			aPIVerification.responseCodeValidation(response, 200);
		}

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("TBL", "T,B,L"));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload("LTB", "L,T,B"));
		aPIVerification.responseCodeValidation(response, 200);


		response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		log("Length is- " + len);
		log("Location is" + testData.getLocation());
		for (int i = 0; i < len; i++) {
			String name = array.getJSONObject(i).getString("name");
			if (name.equalsIgnoreCase(testData.getLocation())) {
				addressLine2 = array.getJSONObject(i).getJSONObject("address").getString("address2");
				log("Address Line 2 is - " + addressLine2);
			}
		}
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homepage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(), testData.getLastName(), testData.getDob(), testData.getEmail(),
				testData.getGender(), testData.getZipCode(), testData.getPrimaryNumber());
		homepage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startappointmentInOrder = null;
		startappointmentInOrder = homepage.skipInsurance(driver);
		location = startappointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());
		String addresslineUI = location.address2(addressLine2);
		log("Address Line2 In Admin UI......" + addressLine2);
		log("Address Line2 In Patient UI......" + addresslineUI);
		Assert.assertEquals(addresslineUI, addressLine2);
	}

	@DataProvider(name = "partnerType")
	public Object[][] portalVersionForRegistration() {
		Object[][] obj = new Object[][] {{"AT"}};
		return obj;
	}
}
