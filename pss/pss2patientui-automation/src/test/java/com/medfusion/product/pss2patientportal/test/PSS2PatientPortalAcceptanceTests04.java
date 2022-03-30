package com.medfusion.product.pss2patientportal.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.Properties;

import org.json.JSONArray;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.AppEntryPoint.StartAppointmentInOrder;
import com.medfusion.product.object.maps.pss2.page.Appointment.DateTime.AppointmentDateTime;
import com.medfusion.product.object.maps.pss2.page.Appointment.HomePage.HomePage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Location.Location;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.DismissPage;
import com.medfusion.product.object.maps.pss2.page.Appointment.Loginless.LoginlessPatientInformation;
import com.medfusion.product.object.maps.pss2.page.Appointment.Main.NewPatientInsuranceInfo;
import com.medfusion.product.object.maps.pss2.page.Appointment.Provider.Provider;
import com.medfusion.product.object.maps.pss2.page.AppointmentType.AppointmentPage;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAM01;
import com.medfusion.product.pss2patientapi.payload.PayloadAM02;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSAdminUtils;
import com.medfusion.product.pss2patientui.utils.PSSConstants;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PatientPortalAcceptanceTests04 extends BaseTestNGWebDriver {

	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;
	public static PayloadAM02 payloadAM02;
	public static PayloadAM01 payloadAM01;
	public static APIVerification apv;

	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public void setUp(String practiceId1, String userID) throws IOException {
		payloadAM = new PayloadAdapterModulator();
		payloadAM01 = new PayloadAM01();
		apv = new APIVerification();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();

		practiceId = practiceId1;

		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM.openTokenPayload(practiceId, userID));

		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}
	
	public void addRule(String r1, String r2) throws Exception {

		Response response;
		JSONArray arr;
		String rule1 = r1.replaceAll("[^a-zA-Z0-9]", "");
		log("Rule 1 - " + r1);
		String rule2 = r2.replaceAll("[^a-zA-Z0-9]", "");
		log("Rule 2 - " + r2);

		logStep("Set up the desired rule in Admin UI using API");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			response = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(response, 200);
		}
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload(rule1, r1));
		apv.responseCodeValidation(response, 200);
		response = postAPIRequestAM.resourceConfigRulePost(practiceId, payloadAM.rulePayload(rule2, r2));
		apv.responseCodeValidation(response, 200);
	}


	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnNG() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		String leadTime = propertyData.getProperty("leadtime.ng");
		int leadTimeint = Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		testData.setLeadtimeDay(leadTimeint);

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String futuredate = psspatientUtils.addDaysToDate(currentdate, leadTime, "MM/dd/yyyy");

		log("Future Date -" + futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		logStep("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnGW() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		String leadTime = propertyData.getProperty("leadtime.ng");
		int leadTimeint = Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.gw"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		testData.setLeadtimeDay(leadTimeint);

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String futuredate = psspatientUtils.addDaysToDate(currentdate, leadTime, "MM/dd/yyyy");

		log("Future Date -" + futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		logStep("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnGE() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		String leadTime = propertyData.getProperty("leadtime.ng");
		int leadTimeint = Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGE());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.ge"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		testData.setLeadtimeDay(leadTimeint);

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String futuredate = psspatientUtils.addDaysToDate(currentdate, leadTime, "MM/dd/yyyy");

		log("Future Date -" + futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		logStep("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLeadTimeShowProviderOnAT() throws Exception {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		String leadTime = propertyData.getProperty("leadtime.ng");
		int leadTimeint = Integer.parseInt(leadTime);
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		apv.responseCodeValidation(response, 200);

		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.at"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		testData.setLeadtimeDay(leadTimeint);

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String futuredate = psspatientUtils.addDaysToDate(currentdate, leadTime, "MM/dd/yyyy");

		log("Future Date -" + futuredate);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, propertyData.getProperty("leadtime.ng"));
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");

		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("LoginlessPatientInformation****");
		log("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Thread.sleep(20000);
		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		assertEquals(date, psspatientUtils.currentDateandLeadDay(testData));
		log("ReSetting Admin UI Setting");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBusinessHourNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		logStep("Get Location TimeZone By Using AM");
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.ng"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		log("Get Time Zone - " + testData.getCurrentTimeZone());

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String startTime = propertyData.getProperty("business.hours.starttime");
		String endTime = propertyData.getProperty("business.hours.endtime");
		String practiceId = propertyData.getProperty("practice.id.ng.ui");
		String practiceName = propertyData.getProperty("updatepractice.name.ng");
		String practiceTimeZone = propertyData.getProperty("updatepratice.timezone.ng");
		String logo = propertyData.getProperty("updatepractice.logo.ng");

		logStep("Update Practice Business Hours By Using AM");
		response = postAPIRequestAM.practiceUpdate(practiceId, payloadAM01.updateBusinessHoursNG(startTime, endTime,
				practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");
		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		log("date- " + date);
		log("Next date is    " + psspatientUtils.numDate(testData));
		if (psspatientUtils.timeDifferance(testData, endTime) < 0) {
			log("Time Diff is  less Than 0 print Next date ");
			assertEquals(date, psspatientUtils.numDate(testData));
		} else {
			log("Time Diff is Not less Than 0 print current date ");
			assertEquals(date, psspatientUtils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		String time = aptDateTime.getfirsttime().substring(0, 5);
		log("Appointment Time is " + time);
		assertEquals(time, startTime);
		logStep("Resetting the Admin Setting ");
		response = postAPIRequestAM.practiceUpdate(practiceId,
				payloadAM01.updateBusinessHoursNG("00:00", "23:59", practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBusinessHourGW() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGW(adminuser);
		propertyData.setAppointmentResponseGW(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.gw"), propertyData.getProperty("mf.authuserid.am.gw"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalGW());
		apv.responseCodeValidation(response, 200);

		logStep("Get Location TimeZone By Using AM");
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.gw"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		log("Get Time Zone - " + testData.getCurrentTimeZone());

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String startTime = propertyData.getProperty("business.hours.starttime");
		String endTime = propertyData.getProperty("business.hours.endtime");
		String practiceId = propertyData.getProperty("practice.id.gw.ui");
		String practiceName = propertyData.getProperty("updatepractice.name.gw");
		String practiceTimeZone = propertyData.getProperty("updatepratice.timezone.gw");
		String logo = propertyData.getProperty("updatepractice.logo.gw");
		String extPracticeId = propertyData.getProperty("updatepractice.extpracticeid.gw");

		logStep("Update Practice Business Hours By Using AM");
		response = postAPIRequestAM.practiceUpdate(practiceId, payloadAM01.updateBusinessHoursGW(startTime, endTime,
				extPracticeId, practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		Thread.sleep(2000);
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		log("date- " + date);
		log("Next date is    " + psspatientUtils.numDate(testData));
		if (psspatientUtils.timeDifferance(testData, endTime) < 0) {
			log("Time Diff is  less Than 0 print Next date ");
			assertEquals(date, psspatientUtils.numDate(testData));
		} else {
			log("Time Diff is Not less Than 0 print current date ");
			assertEquals(date, psspatientUtils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		String time = aptDateTime.getfirsttime().substring(0, 5);
		log("Appointment Time is " + time);
		assertEquals(time, startTime);
		logStep("Resetting the Admin Setting ");
		response = postAPIRequestAM.practiceUpdate(practiceId, payloadAM01.updateBusinessHoursGW("00:00", "23:59",
				extPracticeId, practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBusinessHourAT() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminAT(adminuser);
		propertyData.setAppointmentResponseAT(testData);
		PSSPatientUtils psspatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.at"), propertyData.getProperty("mf.authuserid.am.at"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchAt());
		apv.responseCodeValidation(response, 200);

		logStep("Get Location TimeZone By Using AM");
		response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("locationid.at"));
		apv.responseCodeValidation(response, 200);
		String locationTimeZone = apv.responseKeyValidationJson(response, "timezone");
		log("TimeZone- " + locationTimeZone);
		testData.setCurrentTimeZone(locationTimeZone);
		log("Get Time Zone - " + testData.getCurrentTimeZone());

		String currentdate = psspatientUtils.currentDateWithTimeZone(locationTimeZone);
		log("currentdate - " + currentdate);

		String startTime = propertyData.getProperty("business.hours.starttime");
		String endTime = propertyData.getProperty("business.hours.endtime");
		String practiceId = propertyData.getProperty("practice.id.at.ui");
		String practiceName = propertyData.getProperty("updatepractice.name.at");
		String practiceTimeZone = propertyData.getProperty("updatepratice.timezone.at");
		String logo = propertyData.getProperty("updatepractice.logo.at");

		logStep("Update Practice Business Hours By Using AM");
		response = postAPIRequestAM.practiceUpdate(practiceId, payloadAM01.updateBusinessHoursAT(startTime, endTime,
				practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);
		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		log("date- " + date);
		log("Next date is    " + psspatientUtils.numDate(testData));
		if (psspatientUtils.timeDifferance(testData, endTime) < 0) {
			log("Time Diff is  less Than 0 print Next date ");
			assertEquals(date, psspatientUtils.numDate(testData));
		} else {
			log("Time Diff is Not less Than 0 print current date ");
			assertEquals(date, psspatientUtils.currentESTDate(testData), "Slots are Not Avaliable for current date");
		}
		String time = aptDateTime.getfirsttime().substring(0, 5);
		log("Appointment Time is " + time);
		assertEquals(time, startTime);
		logStep("Resetting the Admin Setting ");
		response = postAPIRequestAM.practiceUpdate(practiceId,
				payloadAM01.updateBusinessHoursNG("00:00", "23:59", practiceId, practiceName, practiceTimeZone, logo));
		apv.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventBackToBackDisableNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		int slotSize = Integer.parseInt(propertyData.getProperty("slotsize.ng04"));
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.leadTimenotReserve(driver, adminuser, testData, "0");

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		log("date- " + date);
		String time = aptDateTime.getfirsttime().substring(0, 5);
		log("Appointment Time is " + time);
		pssPatientUtils.clickOnSubmitAppt1(false, aptDateTime, testData, driver);
		String expectedTime = pssPatientUtils.addToTimeUI(time, slotSize);
		log("expectedTime Time Is ..... " + expectedTime);
		log("Going to Book Appointment again ");
		homePage.btnStartSchedClick();
		startAppointmentInOrder = homePage.skipInsurance(driver);

		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date1 = aptDateTime.selectDate(testData.getIsNextDayBooking());

		logStep("Date selected is for App" + date1);
		log("date- " + date);
		String ActualTime = aptDateTime.getfirsttime().substring(0, 5);
		log("Actual Time  " + ActualTime);
		assertEquals(ActualTime, expectedTime);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventBackToBackEnableNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		int slotSize = Integer.parseInt(propertyData.getProperty("slotsize.ng04"));
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.preventBackToBackEnable(driver, adminuser, testData, "0");

		logStep("Move to PSS patient Portal 2.0 to book an Appointment");
		logStep("Login to PSS Appointment");
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(testData.getFirstName(),
				testData.getLastName(), testData.getDob(), testData.getEmail(), testData.getGender(),
				testData.getZipCode(), testData.getPrimaryNumber());
		homePage.btnStartSchedClick();
		Location location = null;
		StartAppointmentInOrder startAppointmentInOrder = null;
		startAppointmentInOrder = homePage.skipInsurance(driver);
		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		AppointmentPage appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		Provider provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		AppointmentDateTime aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date);
		log("date- " + date);
		String time = aptDateTime.getfirsttime().substring(0, 5);
		log("Appointment Time is " + time);
		int slotValueExpected = slotSize * 2;
		String expectedTime = pssPatientUtils.addToTimeUI(time, slotValueExpected);
		log("expectedTime Time Is ..... " + expectedTime);
		pssPatientUtils.clickOnSubmitAppt1(false, aptDateTime, testData, driver);

		log("Going to Book Appointment again ");
		homePage.btnStartSchedClick();
		startAppointmentInOrder = homePage.skipInsurance(driver);

		location = startAppointmentInOrder.selectFirstLocation(PSSConstants.START_LOCATION);
		logStep("Verfiy Location Page and location =" + testData.getLocation());

		appointment = location.selectAppointment(testData.getLocation());
		logStep("Verfiy Appointment Page and appointment to be selected = " + testData.getAppointmenttype());

		provider = appointment.selectTypeOfProvider(testData.getAppointmenttype(),
				Boolean.valueOf(testData.getIsAppointmentPopup()));
		logStep("Verfiy Provider Page and Provider = " + testData.getProvider());
		aptDateTime = provider.getProviderandClick(testData.getProvider());
		String date1 = aptDateTime.selectDate(testData.getIsNextDayBooking());
		logStep("Date selected is for App" + date1);
		log("date- " + date);
		String ActualTime = aptDateTime.getfirsttime().substring(0, 5);
		log("Actual Time   " + ActualTime);
		assertEquals(ActualTime, expectedTime);
	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStackingNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTBL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTBL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		String appType = propertyData.getProperty("stacking.apptype.ng");
		String providerName = propertyData.getProperty("stacking.provider.ng");
		String locationName = propertyData.getProperty("stacking.location.ng");

		String firstNameP1 = propertyData.getProperty("stacking.p1.firstname.ng");
		String lastNameP1 = propertyData.getProperty("stacking.p1.lastname.ng");
		String dobP1 = propertyData.getProperty("stacking.p1.dob.ng");
		String genderP1 = propertyData.getProperty("stacking.p1.gender.ng");

		String firstNameP2 = propertyData.getProperty("stacking.p2.firstname.ng");
		String lastNameP2 = propertyData.getProperty("stacking.p2.lastname.ng");
		String dobP2 = propertyData.getProperty("stacking.p2.dob.ng");
		String genderP2 = propertyData.getProperty("stacking.p2.gender.ng");

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.appointmentStackingEnable(driver, adminuser, testData, appType, providerName);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss Button");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1,
				"", "");
		homePage.btnStartSchedClick();
		String Patient1FirstTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Time of First Patient First Time Booking is  " + Patient1FirstTime);
		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1, "", "");
		homePage.btnStartSchedClick();
		String patient1SecondBookTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Time of First Patient Second Time Booking is " + patient1SecondBookTime);
		assertNotEquals(patient1SecondBookTime, Patient1FirstTime);

		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP2, lastNameP2, dobP2, "", genderP2, "", "");
		homePage.btnStartSchedClick();
		String secondPatientTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Patient 2 Time Is " + secondPatientTime);
		assertEquals(secondPatientTime, Patient1FirstTime);
		logStep("ReSetting Admin UI For Overbooking");
		adminUtils.appointmentStackingDisable(driver, adminuser, testData, appType, providerName);

	}

	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStackingGE() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminGE(adminuser);
		propertyData.setAppointmentResponseGE(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ge"), propertyData.getProperty("mf.authuserid.am.ge"));
		Response response;
		logStep("Setting Rule By Using Adapter Modulator Api Call");
		response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		apv.responseCodeValidation(response, 200);
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
		for (int i = 0; i < l; i++) {
			int ruleId = arr.getJSONObject(i).getInt("id");
			log("Object No." + i + "- " + ruleId);
			Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, Integer.toString(ruleId));
			apv.responseCodeValidation(responseForDeleteRule, 200);
		}
		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("LTB", "L,T,B"));
		apv.responseCodeValidation(responseRulePost, 200);

		Response responseRulePostTBL = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.rulePayload("TBL", "T,B,L"));
		apv.responseCodeValidation(responseRulePostTBL, 200);
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
				payloadAM01.turnONOFFShowProvider(true));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientMatchGE());
		apv.responseCodeValidation(response, 200);

		String appType = propertyData.getProperty("stacking.apptype.ge");
		String providerName = propertyData.getProperty("stacking.provider.ge");
		String locationName = propertyData.getProperty("stacking.location.ge");

		String firstNameP1 = propertyData.getProperty("stacking.p1.firstname.ge");
		String lastNameP1 = propertyData.getProperty("stacking.p1.lastname.ge");
		String dobP1 = propertyData.getProperty("stacking.p1.dob.ge");
		String genderP1 = propertyData.getProperty("stacking.p1.gender.ge");

		String firstNameP2 = propertyData.getProperty("stacking.p2.firstname.ge");
		String lastNameP2 = propertyData.getProperty("stacking.p2.lastname.ge");
		String dobP2 = propertyData.getProperty("stacking.p2.dob.ge");
		String genderP2 = propertyData.getProperty("stacking.p2.gender.ge");

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.appointmentStackingEnableGE(driver, adminuser, testData, appType, providerName);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1,
				"", "");
		homePage.btnStartSchedClick();
		String Patient1FirstTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Patient 1 First booking time  " + Patient1FirstTime);
		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1, "", "");
		homePage.btnStartSchedClick();
		String Patient1SecondTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Patient 1 Second Booking Time is " + Patient1SecondTime);
		assertNotEquals(Patient1SecondTime, Patient1FirstTime);

		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP2, lastNameP2, dobP2, "", genderP2, "", "");
		homePage.btnStartSchedClick();
		String secondPatientTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
				providerName);
		log("Patient 2 Time Is " + secondPatientTime);
		assertEquals(secondPatientTime, Patient1FirstTime);
		logStep("ReSetting Admin UI For Overbooking");
		adminUtils.appointmentStackingDisableGE(driver, adminuser, testData, appType, providerName);

	}

	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStackingShowProviderOFFNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
		Response response;
		addRule("L,T", "T,L");
		logStep("Show Provider On Using AM ");
		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,payloadAM01.turnONOFFShowProvider(false));
		apv.responseCodeValidation(responseShowOff, 200);

		logStep("Patient Matching By Using Adapter Modulator");
		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
		apv.responseCodeValidation(response, 200);

		String appType = propertyData.getProperty("stacking.apptype.showproviderof.ng");
		String locationName = propertyData.getProperty("stacking.location.ng");

		String firstNameP1 = propertyData.getProperty("stacking.p1.firstname.ng");
		String lastNameP1 = propertyData.getProperty("stacking.p1.lastname.ng");
		String dobP1 = propertyData.getProperty("stacking.p1.dob.ng");
		String genderP1 = propertyData.getProperty("stacking.p1.gender.ng");

		String firstNameP2 = propertyData.getProperty("stacking.p2.firstname.ng");
		String lastNameP2 = propertyData.getProperty("stacking.p2.lastname.ng");
		String dobP2 = propertyData.getProperty("stacking.p2.dob.ng");
		String genderP2 = propertyData.getProperty("stacking.p2.gender.ng");
		
		String firstNameP3 = propertyData.getProperty("stacking.p3.firstname.ng");
		String lastNameP3 = propertyData.getProperty("stacking.p3.lastname.ng");
		String dobP3 = propertyData.getProperty("stacking.p3.dob.ng");
		String genderP3 = propertyData.getProperty("stacking.p3.gender.ng");

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.appointmentStackingEnableShowProviderOFF(driver, adminuser, testData, appType);
		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
		logStep("Clicked on Dismiss Button");
		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
		logStep("going to Book Apppointment For First Patient");
		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1,
				"", "");
		homePage.btnStartSchedClick();
		String Patient1FirstTime = pssPatientUtils.bookLTWithTime(homePage, testData, driver, locationName, appType);
		log("Time of First Patient First Time Booking is  " + Patient1FirstTime);
		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1, "", "");
		homePage.btnStartSchedClick();
		String patient1SecondBookTime = pssPatientUtils.bookLTWithTime(homePage, testData, driver, locationName, appType);
		log("Time of First Patient Second Time Booking is " + patient1SecondBookTime);
		assertNotEquals(patient1SecondBookTime, Patient1FirstTime);
		logStep("going to Book Apppointment For Second Patient");
		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP2, lastNameP2, dobP2, "", genderP2, "", "");
		homePage.btnStartSchedClick();
		String secondPatientTime = pssPatientUtils.bookLTWithTime(homePage, testData, driver, locationName, appType);
		log("Patient 2 Time Is " + secondPatientTime);
		assertEquals(secondPatientTime, Patient1FirstTime);
		logStep("going to Book Apppointment For Third Patient");
		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP3, lastNameP3, dobP3, "", genderP3, "", "");
		homePage.btnStartSchedClick();
		String thirdPatientTime = pssPatientUtils.bookLTWithTime(homePage, testData, driver, locationName, appType);
		log("Patient 2 Time Is " + thirdPatientTime);
		assertNotEquals(thirdPatientTime, Patient1FirstTime);
		logStep("ReSetting Admin UI For Overbooking");
		adminUtils.appointmentStackingDisableShowProviderOFF(driver, adminuser, testData, appType);
	}
	
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStackingWithDurationNG() throws Exception {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		AdminUser adminuser = new AdminUser();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSPatientUtils pssPatientUtils = new PSSPatientUtils();
//		setUp(propertyData.getProperty("mf.practice.id.ng"), propertyData.getProperty("mf.authuserid.am.ng"));
//		Response response;
//		
//		addRule("L,T,B", "T,L,B");
//
//		logStep("Show Provider On Using AM ");
//		Response responseShowOff = postAPIRequestAM.resourceConfigSavePost(practiceId,
//				payloadAM01.turnONOFFShowProvider(true));
//		apv.responseCodeValidation(responseShowOff, 200);
//
//		logStep("Patient Matching By Using Adapter Modulator");
//		response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfoWithOptionalLLNG());
//		apv.responseCodeValidation(response, 200);
//
		String appType = propertyData.getProperty("stacking.apptype.ng");
		String providerName = propertyData.getProperty("stacking.provider.ng");
		String locationName = propertyData.getProperty("stacking.location.ng");
//
//		String firstNameP1 = propertyData.getProperty("stacking.p1.firstname.ng");
//		String lastNameP1 = propertyData.getProperty("stacking.p1.lastname.ng");
//		String dobP1 = propertyData.getProperty("stacking.p1.dob.ng");
//		String genderP1 = propertyData.getProperty("stacking.p1.gender.ng");
//
//		String firstNameP2 = propertyData.getProperty("stacking.p2.firstname.ng");
//		String lastNameP2 = propertyData.getProperty("stacking.p2.lastname.ng");
//		String dobP2 = propertyData.getProperty("stacking.p2.dob.ng");
//		String genderP2 = propertyData.getProperty("stacking.p2.gender.ng");

		PSSAdminUtils adminUtils = new PSSAdminUtils();
		logStep("Login to PSS 2.0 Admin portal");
		adminUtils.appointmentStackingEnableWihDuration(driver, adminuser, testData, appType, providerName);
//		DismissPage dismissPage = new DismissPage(driver, testData.getUrlLoginLess());
//		logStep("Clicked on Dismiss Button");
//		LoginlessPatientInformation loginlessPatientInformation = dismissPage.clickDismiss();
//		HomePage homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1,
//				"", "");
//		homePage.btnStartSchedClick();
//		String Patient1FirstTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
//				providerName);
//		log("Time of First Patient First Time Booking is  " + Patient1FirstTime);
//		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP1, lastNameP1, dobP1, "", genderP1, "", "");
//		homePage.btnStartSchedClick();
//		String patient1SecondBookTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
//				providerName);
//		log("Time of First Patient Second Time Booking is " + patient1SecondBookTime);
//		assertNotEquals(patient1SecondBookTime, Patient1FirstTime);
//
//		homePage = loginlessPatientInformation.fillNewPatientForm(firstNameP2, lastNameP2, dobP2, "", genderP2, "", "");
//		homePage.btnStartSchedClick();
//		String secondPatientTime = pssPatientUtils.bookLTB(homePage, testData, driver, locationName, appType,
//				providerName);
//		log("Patient 2 Time Is " + secondPatientTime);
//		assertEquals(secondPatientTime, Patient1FirstTime);
//		logStep("ReSetting Admin UI For Overbooking");
//		adminUtils.appointmentStackingDisable(driver, adminuser, testData, appType, providerName);

	}
}