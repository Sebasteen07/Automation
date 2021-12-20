package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousDismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Anonymous.AnonymousPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.ConfirmationPage.ConfirmationPage;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests03 extends BaseTestNGWebDriver {

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

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAddressLine2() throws Exception {

		logStep("Verify the Announcemnet- Greetings on welcome page");
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();

		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;

		response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			String name = arr.getJSONObject(i).getString("name");

			if (name.equalsIgnoreCase("PSS WLA")) {

				String s = arr.getJSONObject(i).getJSONObject("address").getString("address2");
				log("Address 2 - " + s);
			}
		}
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultMsgMultiplePatientNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);
		String firstName = propertyData.getProperty("firstname.dup.ng");
		String lastName = propertyData.getProperty("lastname.dup.ng");
		String gender = propertyData.getProperty("gender.dup.ng");
		String dob = propertyData.getProperty("dob.dup.ng");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		Thread.sleep(2000);
		logStep("Verifying Actual and Expected Message");
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		log("Actual Message" + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.msg.ng");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultMsgMultiplePatientGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);
		String firstName = propertyData.getProperty("firstname.dup.ge");
		String lastName = propertyData.getProperty("lastname.dup.ge");
		String gender = propertyData.getProperty("gender.dup.ge");
		String dob = propertyData.getProperty("dob.dup.ge");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		logStep("Verifying Actual and Expected Message");
		Thread.sleep(2000);
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		log("Actual Message" + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.msg.ge");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
	}


	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testDefaultMsgMultiplePatientGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);
		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);
		String firstName = propertyData.getProperty("firstname.dup.gw");
		String lastName = propertyData.getProperty("lastname.dup.gw");
		String gender = propertyData.getProperty("gender.dup.gw");
		String dob = propertyData.getProperty("dob.dup.gw");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		Thread.sleep(2000);
		logStep("Verifying Actual and Expected Message");
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		log("Actual Message" + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.msg.gw");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCustomMsgMultiplePatientGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGW(adminUser);
		propertyData.setAppointmentResponseGW(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		String b = payloadAM.multiplePatientAnnoucement(propertyData.getProperty("multiple.patient.custommsg.gw"));
		response = postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.gw");
		String lastName = propertyData.getProperty("lastname.dup.gw");
		String gender = propertyData.getProperty("gender.dup.gw");
		String dob = propertyData.getProperty("dob.dup.gw");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		logStep("Verifying Actual and Expected Message");
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		log("Actual Message  " + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.custommsg.gw");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
		response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		int id = 0;
		log("Length is- " + len);
		for (int i = 0; i < len; i++) {
			String type = array.getJSONObject(i).getString("type");
			if (type.equalsIgnoreCase("Inactive or Multiple Patient")) {
				log("id Is  " + array.getJSONObject(i).getInt("id"));
				id = array.getJSONObject(i).getInt("id");
				break;
			}

		}
		log("Announcement Id is  " + id);
		response = postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCustomMsgMultiplePatientGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		String b = payloadAM.multiplePatientAnnoucement(propertyData.getProperty("multiple.patient.custommsg.ge"));
		response = postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.ge");
		String lastName = propertyData.getProperty("lastname.dup.ge");
		String gender = propertyData.getProperty("gender.dup.ge");
		String dob = propertyData.getProperty("dob.dup.ge");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		logStep("Verifying Actual and Expected Message");
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		log("Actual Message  " + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.custommsg.ge");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
		response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		int id = 0;
		log("Length is- " + len);
		for (int i = 0; i < len; i++) {
			String type = array.getJSONObject(i).getString("type");
			if (type.equalsIgnoreCase("Inactive or Multiple Patient")) {
				log("id Is  " + array.getJSONObject(i).getInt("id"));
				id = array.getJSONObject(i).getInt("id");
				break;
			}

		}
		log("Announcement Id is  " + id);
		response = postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testCustomMsgMultiplePatientNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		String b = payloadAM.multiplePatientAnnoucement(propertyData.getProperty("multiple.patient.custommsg.ng"));
		response = postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.ng");
		String lastName = propertyData.getProperty("lastname.dup.ng");
		String gender = propertyData.getProperty("gender.dup.ng");
		String dob = propertyData.getProperty("dob.dup.ng");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		loginlessPatientInformation.fillNewPatientForm(firstName, lastName, dob, "", gender, "", "");
		String actualMessage = loginlessPatientInformation.multiplePatientMessage();
		logStep("Verifying Actual and Expected Message");
		log("Actual Message  " + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.custommsg.ng");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
		response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		int id = 0;
		log("Length is- " + len);
		for (int i = 0; i < len; i++) {
			String type = array.getJSONObject(i).getString("type");
			if (type.equalsIgnoreCase("Inactive or Multiple Patient")) {
				log("id Is  " + array.getJSONObject(i).getInt("id"));
				id = array.getJSONObject(i).getInt("id");
				break;
			}

		}
		log("Announcement Id is  " + id);
		response = postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowDuplicatePatientOFFDefaultMsgNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.allowDuplicateONOff(false));
		validateAdapter.verifyResourceConfigPost(response);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalAno());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.ng");
		String lastName = propertyData.getProperty("lastname.dup.ng");
		String gender = propertyData.getProperty("gender.dup.ng");
		String dob = propertyData.getProperty("dob.dup.ng");
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
		AnonymousPatientInformation anonymousPatientInformation = aptDateTime.selectAppointmentTimeSlot(testData.getIsNextDayBooking());
		anonymousPatientInformation.fillPatientFormWithPrivacyPolicy(firstName, lastName, dob, "", gender, "");
		logStep("Verifying Actual and Expected Message");
		String actualMessage = anonymousPatientInformation.multiplePatientMessage();
		log("Actual Message" + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.msg.ng");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowDuplicatePatientCustomMsgOFFNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.allowDuplicateONOff(false));
		validateAdapter.verifyResourceConfigPost(response);

		String b = payloadAM.multiplePatientAnnoucement(propertyData.getProperty("multiple.patient.custommsg.ng"));
		response = postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalAno());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.ng");
		String lastName = propertyData.getProperty("lastname.dup.ng");
		String gender = propertyData.getProperty("gender.dup.ng");
		String dob = propertyData.getProperty("dob.dup.ng");
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
		AnonymousPatientInformation anonymousPatientInformation = aptDateTime.selectAppointmentTimeSlot(testData.getIsNextDayBooking());
		anonymousPatientInformation.fillPatientFormWithPrivacyPolicy(firstName, lastName, dob, "", gender, "");
		logStep("Verifying Actual and Expected Message");
		String actualMessage = anonymousPatientInformation.multiplePatientMessage();
		log("Actual Message" + actualMessage);
		String expectedMessage = propertyData.getProperty("multiple.patient.custommsg.ng");
		log("Expected Message " + expectedMessage);
		assertEquals(actualMessage, expectedMessage, "Default Announcement message is wrong");
		response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray array = new JSONArray(response.body().asString());
		int len = array.length();
		int id = 0;
		log("Length is- " + len);
		for (int i = 0; i < len; i++) {
			String type = array.getJSONObject(i).getString("type");
			if (type.equalsIgnoreCase("Inactive or Multiple Patient")) {
				log("id Is  " + array.getJSONObject(i).getInt("id"));
				id = array.getJSONObject(i).getInt("id");
				break;
			}

		}
		log("Announcement Id is  " + id);
		response = postAPIRequestAM.deleteAnnouncement(practiceId, id);
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowDuplicatePatientONNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		PSSPatientUtils psspatientutils = new PSSPatientUtils();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.allowDuplicateONOff(true));
		validateAdapter.verifyResourceConfigPost(response);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstName = propertyData.getProperty("firstname.dup.ng");
		String lastName = propertyData.getProperty("lastname.dup.ng");
		String gender = propertyData.getProperty("gender.dup.ng");
		String dob = propertyData.getProperty("dob.dup.ng");
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

		AnonymousPatientInformation anonymousPatientInformation = aptDateTime.selectAppointmentTimeSlot(testData.getIsNextDayBooking());
		ConfirmationPage confirmationpage = anonymousPatientInformation.fillPatientFormWithPrivacyPolicy(firstName, lastName, dob, "", gender, "");
		psspatientutils.appointmentToScheduledAnonymous(confirmationpage, testData);
	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteDefaultNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminNG(adminUser);
		propertyData.setAppointmentResponseNG(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		String name = propertyData.getProperty("prerequisite.appointmenttype.name.ng");
		String extAppID = propertyData.getProperty("prerequisite.appointmenttype.extapp.id.ng");
		String catId = propertyData.getProperty("prerequisite.appointmenttype.cat.id.ng");
		String catName = propertyData.getProperty("prerequisite.appointmenttype.cat.name.ng");
		String preReqAppId = propertyData.getProperty("appointment.id.prerequisite.ng");

		response = postAPIRequestAM.preRequisiteAppointmenttypes(practiceId, preReqAppId,
				payloadAM.preRequisiteAppointmentTypesDefualt(name, extAppID, catId, catName));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstNamePreReq = propertyData.getProperty("firstname.prereqpast.ng");
		String lastNamePreReq = propertyData.getProperty("lastname.prereqpast.ng");
		String genderPreReq = propertyData.getProperty("gender.prereqpast.ng");
		String dobPreReq = propertyData.getProperty("dob.prereqpast.ng");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNamePreReq, lastNamePreReq, dobPreReq, "", genderPreReq, "", "");
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		String appName = propertyData.getProperty("appointmenttypefor.prereqname.ng");
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		String appTypeName = appointment.selectTypeOfApp1(appName);
		log("Actaul Appointment Type " + appTypeName);
		String expectedAppTpe = appTypeName;
		assertEquals(appTypeName, expectedAppTpe);

		response = postAPIRequestAM.preRequisiteAppById(practiceId, preReqAppId);
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		int id = 0;
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			id = arr.getJSONObject(i).getInt("id");
		}
		String s = Integer.toString(id);
		log("preRequisiteApp Id is for Delete " + s);
		response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId, s);
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = {"AcceptanceTests"}, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteDefaultGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminUser = new AdminUser();
		propertyData.setAdminGE(adminUser);
		propertyData.setAppointmentResponseGE(testData);

		logStep("Set up the API authentication");
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;

		String name = propertyData.getProperty("prerequisite.appointmenttype.name.ng");
		String extAppID = propertyData.getProperty("prerequisite.appointmenttype.extapp.id.ng");
		String catId = propertyData.getProperty("prerequisite.appointmenttype.cat.id.ng");
		String catName = propertyData.getProperty("prerequisite.appointmenttype.cat.name.ng");
		String preReqAppId = propertyData.getProperty("appointment.id.prerequisite.ng");
		response =
				postAPIRequestAM.preRequisiteAppointmenttypes(practiceId, preReqAppId, payloadAM.preRequisiteAppointmentTypesDefualt(name, extAppID, catId, catName));
		aPIVerification.responseCodeValidation(response, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLL());
		aPIVerification.responseCodeValidation(response, 200);

		String firstNamePreReq = propertyData.getProperty("firstname.prereqpast.ng");
		String lastNamePreReq = propertyData.getProperty("lastname.prereqpast.ng");
		String genderPreReq = propertyData.getProperty("gender.prereqpast.ng");
		String dobPreReq = propertyData.getProperty("dob.prereqpast.ng");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		Thread.sleep(1000);
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNamePreReq, lastNamePreReq, dobPreReq, "", genderPreReq, "", "");
		homePage.btnStartSchedClick();
		logStep("Clicked on the Start Button ");
		String appName = propertyData.getProperty("appointmenttype.prereqname.ng");
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		logStep("Clicked on the Skip Insurance Button ");
		AppointmentPage appointment = startAppointmentInOrder.selectFirstAppointment(PSSConstants.START_APPOINTMENT);
		log("Verfiy Appointment Page and appointment =" + testData.getAppointmenttype());
		String appTypeName = appointment.selectTypeOfApp1(appName);
		log("Actaul Appointment Type " + appTypeName);
		String expectedAppTpe = appTypeName;
		assertEquals(appTypeName, expectedAppTpe);

		response = postAPIRequestAM.preRequisiteAppById(practiceId, preReqAppId);
		aPIVerification.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		int id = 0;
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			id = arr.getJSONObject(i).getInt("id");
		}
		String s = Integer.toString(id);
		log("preRequisiteApp Id is for Delete " + s);
		response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId, s);
		aPIVerification.responseCodeValidation(response, 200);

	}
}
