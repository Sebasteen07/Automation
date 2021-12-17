// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.ParseJSONFile;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestPatientMod;
import com.medfusion.product.pss2patientapi.payload.PayloadPssPatientModulator;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PSS2PatientModulatorAcceptanceGETests extends BaseTestNG {

	public static HeaderConfig headerConfig;
	public static PSSPropertyFileLoader propertyData;

	public static Appointment testData;
	public static PostAPIRequestPatientMod postAPIRequest;
	public static PayloadPssPatientModulator payloadPatientMod;
	public static String accessToken;
	public static String practiceid;
	public PSSPatientUtils pssPatientUtils;

	APIVerification apv;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		log("Set the Authorization for Patient Modulator");

		headerConfig = new HeaderConfig();
		propertyData = new PSSPropertyFileLoader();
		testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		postAPIRequest = new PostAPIRequestPatientMod();
		payloadPatientMod = new PayloadPssPatientModulator();
		accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		practiceid = testData.getPracticeId();

		pssPatientUtils = new PSSPatientUtils();
		apv = new APIVerification();

		log("Base URL for Patient Modulator - " + testData.getBasicURI());

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptDetailFromGuidGET() throws IOException {

		Response response = postAPIRequest.apptDetailFromGuid(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), testData.getApptDetailGuidId(), practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "appointmentType.name");
		apv.responseKeyValidationJson(response, "book.displayName");
		apv.responseKeyValidationJson(response, "location.displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptDetailFromGuidGETInvalid() throws IOException {

		Response response = postAPIRequest.apptDetailFromGuid(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), "b7fb1a34-8538-4a39-bbba-4159f3627dd0",
				practiceid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No link found. "));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidAnonymousGET() throws IOException {

		Response response = postAPIRequest.practiceFromGuid(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getAnonymousGuidId());
		JsonPath js = new JsonPath(response.asString());
		String extpracticeid = js.get("extPracticeId");
		String practicename = js.getString("name");
		log("Practice Id-" + extpracticeid);
		log("Practice Name-" + practicename);
		assertEquals(extpracticeid, testData.getAnonymousPracticeId(), "Ext Practice Id is wrong");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidAnonymousGETInvalid() throws IOException {

		Response response = postAPIRequest.practiceFromGuid(testData.getBasicURI(), headerConfig.defaultHeader(),
				"b7fb1a34-8538-4a39-bbba-4159f3627dd0");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Practice found for guid="));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidGET() throws IOException {

		Response response = postAPIRequest.linksValueGuid(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getLinksValueGuidId());

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));

		assertEquals(practiceId, practiceid, "practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidGETInvalid() throws IOException {

		Response response = postAPIRequest.linksValueGuid(testData.getBasicURI(), headerConfig.defaultHeader(), "");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
		String error = apv.responseKeyValidationJson(response, "error");
		assertTrue(error.contains("Not Found"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidAndPracticeGET() throws IOException {

		Response response = postAPIRequest.linksValueGuidAndPractice(testData.getBasicURI(),
				headerConfig.defaultHeader(), testData.getLinksValueGuidId());

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));
		assertEquals(practiceId, practiceid, "Practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksValueGuidAndPracticeGETInvalid() throws IOException {

		Response response = postAPIRequest.linksValueGuidAndPractice(testData.getBasicURI(),
				headerConfig.defaultHeader(), "b741-b2ce6817aaaa");
		apv.responseCodeValidation(response, 204);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidGET() throws IOException {

		Response response = postAPIRequest.linksDetailGuid(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getLinksDetailGuidId());

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("emrid");
		log("Patient id -" + js.getString("emrid"));
		assertEquals(patientId, testData.getLinksDetailPatientId(), "Patient Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidGETInvalid() throws IOException {

		Response response = postAPIRequest.linksDetailGuid(testData.getBasicURI(), headerConfig.defaultHeader(), "");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
		String error = apv.responseKeyValidationJson(response, "error");
		assertTrue(error.contains("Not Found"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidAndPracticeGET() throws IOException {

		Response response = postAPIRequest.linksDetailGuidAndPractice(testData.getBasicURI(),
				headerConfig.defaultHeader(), testData.getLinksDetailGuidId());

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("emrid");
		log("Patient id -" + js.getString("emrid"));
		assertEquals(patientId, testData.getLinksDetailPatientId(), "Patient Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinksDetailGuidAndPracticeGETInvalid() throws IOException {

		Response response = postAPIRequest.linksDetailGuidAndPractice(testData.getBasicURI(),
				headerConfig.defaultHeader(), "cf61827d-cfaa-4024-94e4-03d305f");
		String msg = apv.responseKeyValidationJson(response, "welcomeMessage");
		assertEquals(msg, "Link is currently unavailable for the practice", "Invalid message for wrong guid id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGuidForLogoutPatientGET() throws IOException {

		Response response = postAPIRequest.guidForLogoutPatient(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid);
		JsonPath js = new JsonPath(response.asString());
		String guid = js.get("guid");
		assertEquals(guid, testData.getLogoutguidId(), "guid Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidLoginlessGET() throws IOException {

		Response response = postAPIRequest.practiceFromGuidLoginless(testData.getBasicURI(),
				headerConfig.defaultHeader(), testData.getLoginlessGuidId());

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		assertEquals(extPracticeId, testData.getLoginlessPrcticeId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidLoginlessGETInvalid() throws IOException {

		Response response = postAPIRequest.practiceFromGuidLoginless(testData.getBasicURI(),
				headerConfig.defaultHeader(), "bc89e551-ac6d-4d69-b0b2-fb63c0a4bbbb");

		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Practice found for guid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTokenForLoginlessGET() throws IOException {

		Response response = postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getTokenForLoginlessGuidId());

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		assertEquals(extPracticeId, practiceid, "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTokenForLoginlessGETInvalid() throws IOException {

		Response response = postAPIRequest.tokenForLoginless(testData.getBasicURI(), headerConfig.defaultHeader(), "");

		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
		String error = apv.responseKeyValidationJson(response, "error");
		assertTrue(error.contains("Not Found"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthGET() throws IOException {

		Response response = postAPIRequest.health(testData.getBaseUrlHealth(), headerConfig.defaultHeader());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "status");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoGET() throws IOException {

		Response response = postAPIRequest.logo(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getLoginlessPrcticeId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimezonePracticeResourceGET() throws IOException {

		Response response = postAPIRequest.timezonePracticeResource(testData.getBasicURI(),
				headerConfig.defaultHeader(), practiceid, testData.getTimezonePracticeName());

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice id -" + js.getString("practiceId"));
		log("Practice name-" + js.getString("practiceName"));
		assertEquals(practiceId, practiceid, "Practice Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoGET() throws IOException {

		Response response = postAPIRequest.practiceInfo(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, testData.getLinksValueGuidPracticeName());

		JsonPath js = new JsonPath(response.asString());

		String extpracticeid = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("practiceId"));
		assertEquals(extpracticeid, practiceid, "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResellerLogoGET() throws IOException {

		Response response = postAPIRequest.resellerLogo(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSessionConfigurationGET() throws IOException {

		Response response = postAPIRequest.sessionConfiguration(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid);
		JsonPath js = new JsonPath(response.asString());
		int tokenExpirationTime = js.get("tokenExpirationTime");
		int expirationWarningTime = js.get("expirationWarningTime");
		log("Expiration Warning Time -" + expirationWarningTime);
		assertEquals(tokenExpirationTime, Integer.parseInt(testData.getSessionConfigurationExpirationTime()),
				"Token Expiration Time is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailGET() throws IOException {

		Response response = postAPIRequest.practiceDetail(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, testData.getLinksValueGuidPracticeName());
		JsonPath js = new JsonPath(response.asString());

		log("ExtPracticeId- " + js.get("extPracticeId"));
		log("Practice id -" + js.getString("practiceId"));
		log("Practice guid -" + js.getString("guid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeFromGuidSsoGET() throws IOException {

		Response response = postAPIRequest.practiceFromGuidSso(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getPracticeFromGuidSsoId());

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		assertEquals(extPracticeId, testData.getPracticeSsoId(), "Ext PracticeId is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeZoneResourceGET() throws IOException {

		Response response = postAPIRequest.timeZoneResource(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getPatientId());

		JsonPath js = new JsonPath(response.asString());
		log("Practice status -" + js.getString("active"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		apv.responseKeyValidation(response, "code");
		apv.responseKeyValidation(response, "description");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeZoneResourceGETInvalid() throws IOException {

		Response response = postAPIRequest.timeZoneResource(testData.getBasicURI(), headerConfig.defaultHeader(), "");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateTokenGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataPatientModulator(testData);
		PostAPIRequestPatientMod postAPIRequest = new PostAPIRequestPatientMod();
		String accessToken = postAPIRequest.createToken(testData.getAccessTokenURL());
		testData.setAccessToken(accessToken);
		log("The Accesssc Token is From the Test Method  " + testData.getAccessToken(), practiceid);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingConfigurationGET() throws IOException {

		Response response = postAPIRequest.upcomingConfiguration(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid);

		JsonPath js = new JsonPath(response.asString());

		log("Show Cancel Reason -" + js.getString("showCancelReason"));
		log("Show Cancel Reason From PM -" + js.getString("showCancelReasonFromPM"));
		log("Show Cancel Message -" + js.getString("showCancelMessage"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByNameGET() throws IOException {

		String validanncode = "AG";
		Response response = postAPIRequest.announcementByName(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, validanncode);
		String message = apv.responseKeyValidationJson(response, "message");
		log("Announcement message is -" + message);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByNameGETInvalid() throws IOException {

		String invalidanncode = "KSS";
		Response response = postAPIRequest.announcementByName(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, "KSS");
		String message = apv.responseKeyValidationJson(response, "message");
		log("Announcement message is -" + message);
		apv.responseCodeValidation(response, 400);
		assertEquals(message, "Announcement with code=" + invalidanncode + " is not active or it does not exist.");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByLanguageGET() throws IOException {

		Response response = postAPIRequest.announcementByLanguage(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementTypeGET() throws IOException {

		Response response = postAPIRequest.announcementType(testData.getBasicURI(), headerConfig.defaultHeader());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "code");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetImagesGET() throws IOException {

		Response response = postAPIRequest.getImages(testData.getBasicURI(), headerConfig.defaultHeader(), practiceid,
				testData.getGetImagesBookId());
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetImagesGETInvalid() throws IOException {

		Response response = postAPIRequest.getImages(testData.getBasicURI(), headerConfig.defaultHeader(), practiceid,
				"222");
		apv.responseCodeValidation(response, 500);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Image Found For Book"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLanguagesGET() throws IOException {

		Response response = postAPIRequest.getLanguages(testData.getBasicURI(), headerConfig.defaultHeader(),
				testData.getPatientId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLanguagesGETInvalid() throws IOException {

		Response response = postAPIRequest.getLanguages(testData.getBasicURI(), headerConfig.defaultHeader(), "");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsProfilesGET() throws IOException {

		Response response = postAPIRequest.demographicsProfiles(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());

		String demographicsid = apv.responseKeyValidationJson(response, "id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		assertEquals(demographicsid, testData.getPatientId(), "Demographics Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsProfilesGETInvalid() throws IOException {

		Response response = postAPIRequest.demographicsProfiles(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "22");

		String demographicsid = apv.responseKeyValidationJson(response, "id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		assertNull(demographicsid);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchGET() throws IOException {

		Response response = postAPIRequest.matchPatient(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFlowIdentityGET() throws IOException {

		Response response = postAPIRequest.flowIdentity(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());

		log("Flow Type -" + js.getString("type"));
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "entity");
		ParseJSONFile.getKey(jsonobject, "code");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingGET() throws IOException {

		Response response = postAPIRequest.genderMapping(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseKeyValidation(response, "pssCode");
		apv.responseKeyValidation(response, "displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetStatesGET() throws IOException {

		Response response = postAPIRequest.getStates(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseKeyValidationJson(response, "key");
		apv.responseKeyValidationJson(response, "value");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientDemographicsGET() throws IOException {

		Response response = postAPIRequest.patientDemographics(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		JsonPath js = new JsonPath(response.asString());

		String demographicid = js.getString("id");
		log("Demographics First Name -" + js.getString("firstName"));
		log("Demographics Last Name -" + js.getString("lastName"));
		assertEquals(demographicid, testData.getPatientId(), "Demographics Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateProviderLinkPost_ExistingPatient() throws IOException {

		String b = payloadPatientMod.validateProviderLinkPayload(testData.getPatientDemographicsFirstName(),
				testData.getPatientDemographicsLastName(), testData.getPatientDemographicsDOB(),
				testData.getPatientDemographicsGender(), testData.getPatientDemographicsEmail(),
				testData.getValidateProviderLinkId());

		Response response = postAPIRequest.validateProviderLink(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId(),
				testData.getValidateProviderLinkDisplayName());

		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));

		String linkId = Integer.toString(id);
		assertEquals(linkId, testData.getValidateProviderLinkId(), "Link id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateProviderLinkPost_ExistingPatientInvalid() throws IOException {

		String b = payloadPatientMod.validateProviderLinkPayload(testData.getPatientDemographicsFirstName(),
				testData.getPatientDemographicsLastName(), testData.getPatientDemographicsDOB(),
				testData.getPatientDemographicsGender(), testData.getPatientDemographicsEmail(), "22222");

		Response response = postAPIRequest.validateProviderLink(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId(),
				testData.getValidateProviderLinkDisplayName());

		assertNull(apv.responseKeyValidationJson(response, "id"), "Book id is not null for invalid Book Id");
		assertNull(apv.responseKeyValidationJson(response, "displayName"), "DisplayName is not null");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateProviderLinkPost_NewPatient() throws IOException {

		String b = payloadPatientMod.validateProviderLinkPayload_New(testData.getValidateProviderLinkId());
		String patientid = null;
		Response response = postAPIRequest.validateProviderLink(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid,
				testData.getValidateProviderLinkDisplayName());
		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));

		String linkId = Integer.toString(id);
		assertEquals(linkId, testData.getValidateProviderLinkId(), "Link id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testValidateProviderLinkPost_NewPatientInvalid() throws IOException {

		String b = payloadPatientMod.validateProviderLinkPayload_New("11111");
		String patientid = null;
		Response response = postAPIRequest.validateProviderLink(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid, "");

		assertNull(apv.responseKeyValidationJson(response, "id"),
				"New Patient-Book id is not null for invalid Book Id");
		assertNull(apv.responseKeyValidationJson(response, "displayName"), "New Patient-DisplayName is not null");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailablePost_NewPatient() throws IOException {

		String patientid = null;

		String b = payloadPatientMod.locationsByNextAvailablePayload(
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid,
				testData.getLocationsByNextAvailableId());

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray jo = new JSONArray(response.asString());
		int locationid = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Location " + locationid + " is- " + nextavailableslot);
		assertEquals(locationid, Integer.parseInt(propertyData.getProperty("availableslot.locationid.pm")),
				"location practice id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailablePost_NewPatientInvalid() throws IOException {

		String patientid = null;

		String b = payloadPatientMod.locationsByNextAvailablePayload("12345", "4444",
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid,
				testData.getLocationsByNextAvailableId());

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slot = arr.getJSONObject(0).getString("nextAvailabilitySlot");
		log("SLOT- " + slot);
		assertNull(slot);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailablePost_ExistingPatient() throws IOException {

		String patientid = testData.getPatientId();
		String b = payloadPatientMod.locationsByNextAvailablePayload(
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid,
				testData.getLocationsByNextAvailableId());

		JSONArray jo = new JSONArray(response.asString());
		int locationid = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");

		log("Next Available slot for Location " + locationid + " is- " + nextavailableslot);
		assertEquals(locationid, Integer.parseInt(propertyData.getProperty("availableslot.locationid.pm")),
				"location practice id is wrong");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByNextAvailablePost_ExistingPatientInvalid() throws IOException {

		String patientid = testData.getPatientId();
		String b = payloadPatientMod.locationsByNextAvailablePayload("12345",
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.locationsByNextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid,
				testData.getLocationsByNextAvailableId());

		JSONArray jo = new JSONArray(response.asString());
		int locationid = jo.getJSONObject(0).getInt("id");
		Object nextavailableslot = jo.getJSONObject(0).get("nextAvailabilitySlot");

		log("Next Available slot for Location " + locationid + " is- " + nextavailableslot);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsByRulePost() throws IOException {

		Response response = postAPIRequest.locationsByRule(testData.getBasicURI(),
				payloadPatientMod.locationsByRulePayload(testData.getPatientDemographicsDOB(),
						testData.getPatientDemographicsLastName(), testData.getPatientDemographicsGender(),
						testData.getPatientDemographicsEmail(), testData.getPatientDemographicsPhoneNo(),
						testData.getPatientDemographicsZipCode()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");
		ParseJSONFile.getKey(jsonobject, "locTimeZone");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousMatchAndCreatePatientPost_ExistingPatient() throws IOException {

		String b = payloadPatientMod.anonymousPatientPayload(propertyData.getProperty("identifypatient.firstname.pm"),
				propertyData.getProperty("identifypatient.firstname.pm"));

		Response existingPatientResponse = postAPIRequest.anonymousPatientNewPatient(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseCodeValidation(existingPatientResponse, 200);
		String patientid = apv.responseKeyValidationJson(existingPatientResponse, "id");

		JsonPath js = new JsonPath(existingPatientResponse.asString());

		ArrayList<JSONObject> jo = js.getJsonObject("otpPatientDetails");

		log("otpPatientDetails- " + jo);

		assertEquals(patientid, testData.getPatientId(), "Patient Id is not matching");
		assertNotNull(jo, "OTP Patient Details are not set");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnonymousMatchAndCreatePatientPost_NewPatient() throws IOException {

		Response response = postAPIRequest.anonymousPatientNewPatient(testData.getBasicURI(),
				payloadPatientMod.anonymousMatchAndCreatePatientPayload(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIdentifyPatientForReschedulePost() throws IOException {

		String b = payloadPatientMod.identifyPatientForReschedulePayload(
				propertyData.getProperty("identifypatient.firstname.pm"),
				propertyData.getProperty("identifypatient.firstname.pm"),
				propertyData.getProperty("identifypatient.guid.pm"));

		log("Verifying the patient Id");
		Response response = postAPIRequest.identifyPatientForReschedule(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);

		String patientid = apv.responseKeyValidationJson(response, "id");

		assertEquals(patientid, testData.getPatientId(), "Patient Id is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIdentifyPatientForReschedulePostInvalid() throws IOException {

		String b = payloadPatientMod.identifyPatientForReschedulePayload(
				propertyData.getProperty("identifypatient.firstname.pm"),
				propertyData.getProperty("identifypatient.firstname.pm"), "b7fb1a34-8538-4a39-bbba-4159f3627dd1");

		log("Verifying the patient Id");
		Response response = postAPIRequest.identifyPatientForReschedule(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);

		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No link found"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyByRulePost() throws IOException {

		Response response = postAPIRequest.specialtyByRule(testData.getBasicURI(),
				payloadPatientMod.specialtyByRulePayload(testData.getAppointmentIdApp()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid,
				testData.getSpecialtyByRulePatientId());

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyByRulePostInvalid() throws IOException {

		Response response = postAPIRequest.specialtyByRule(testData.getBasicURI(),
				payloadPatientMod.specialtyByRulePayload("12345"),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid,
				testData.getSpecialtyByRulePatientId());
		String ob = apv.responseKeyValidationJson(response, "entityNotAvailable");
		assertEquals(true, Boolean.parseBoolean(ob));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsBasedOnZipcodeAndRadiusPost() throws IOException {

		Response response = postAPIRequest.locationsBasedOnZipcodeAndRadius(testData.getBasicURI(),
				payloadPatientMod.locationsBasedOnZipcodeAndRadiusPayload(testData.getAppointmentIdApp()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());

		JsonPath js = new JsonPath(response.asString());
		log("Show Search Location -" + js.getString("showSearchLocation"));
		log("Show Next Available -" + js.getString("showNextAvailable"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsBasedOnZipcodeAndRadiusPostInvalid() throws IOException {

		Response response = postAPIRequest.locationsBasedOnZipcodeAndRadius(testData.getBasicURI(),
				payloadPatientMod.locationsBasedOnZipcodeAndRadiusPayload("12345"),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());

		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "Invalid appointmenttype id=12345");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentGET() throws IOException {

		Response response = postAPIRequest.appointment(testData.getBasicURI(), headerConfig.defaultHeader(), practiceid,
				testData.getAppointmentId());
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "patientId");
		apv.responseKeyValidationJson(response, "confirmationNo");
		apv.responseKeyValidationJson(response, "bookName");
		apv.responseKeyValidationJson(response, "locationName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentGETInvalid() throws IOException {

		Response response = postAPIRequest.appointment(testData.getBasicURI(), headerConfig.defaultHeader(), practiceid,
				"12345");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "No appointment found for extappointmentid=12345");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentForIcsGET() throws IOException {

		Response response = postAPIRequest.appointmentForIcs(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, propertyData.getProperty("ext.appt.id.pm"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentForIcsGETInvalid() throws IOException {

		Response response = postAPIRequest.appointmentForIcs(testData.getBasicURI(), headerConfig.defaultHeader(),
				practiceid, "12345");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "No appointment found for extappointmentid=12345");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsByPageGET() throws IOException {

		Response response = postAPIRequest.upcomingAppointmentsByPage(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getAppointmentId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsByPageGETInvalid() throws IOException {

		Response response = postAPIRequest.upcomingAppointmentsByPage(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "12345");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "No appointment found for extappointmentid=12345");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGETInvalid() throws IOException {

		Response response = postAPIRequest.insuranceCarrier(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "");
		// apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGET() throws IOException {

		Response response = postAPIRequest.cancellationReason(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGETInvalid() throws IOException {

		Response response = postAPIRequest.cancellationReason(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "111111111");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "type");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleReasonGET() throws IOException {

		Response response = postAPIRequest.rescheduleReason(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "displayName");
		apv.responseKeyValidation(response, "type");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksByNextAvailablePost_NewPatient() throws IOException {

		String patientid = null;
		String b = payloadPatientMod.booksByNextAvailablePayload(propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.booksBynextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int bookid = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");
		log("Next Available slot for Location " + bookid + " is- " + nextavailableslot);
		assertEquals(bookid, Integer.parseInt(propertyData.getProperty("availableslot.bookid.pm")), "Book id is wrong");
		assertNotNull(nextavailableslot);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksByNextAvailablePost_ExistingPatient() throws IOException {
		String patientid = testData.getPatientId();
		String b = payloadPatientMod.booksByNextAvailablePayload(propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.booksBynextAvailable(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);

		JSONArray jo = new JSONArray(response.asString());
		int bookid = jo.getJSONObject(0).getInt("id");
		String nextavailableslot = jo.getJSONObject(0).getString("nextAvailabilitySlot");
		log("Next Available slot for Location " + bookid + " is- " + nextavailableslot);
		assertEquals(bookid, Integer.parseInt(propertyData.getProperty("availableslot.bookid.pm")), "Book id is wrong");
		assertNotNull(nextavailableslot);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksByRulePost() throws IOException {

		Response response = postAPIRequest.booksByRule(testData.getBasicURI(),
				payloadPatientMod.booksByRulePayload(testData.getAppSlotId(), testData.getLocationIdApp()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientIdPm());

		JsonPath js = new JsonPath(response.asString());

		log("Book Rule Id:-" + js.getString("books[0].id"));
		log("Book DsiplayName:-" + js.getString("books[0].displayName"));

		String displayname = js.getString("books[0].displayName");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		assertEquals(displayname, testData.getDisplayName(), "Display name is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowOnlineCancellationPost() throws IOException {

		String b = payloadPatientMod.allowOnlineCancellationPayload(testData.getAppointmentId());
		log("Practice Id is-" + practiceid);

		Response response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientIdPm());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		assertEquals(apv.responseKeyValidationJson(response, "checkCancelAppointmentStatus"), "true");
		assertEquals(apv.responseKeyValidationJson(response, "preventScheduling"), null);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAllowOnlineCancellationPostInvalid() throws IOException {

		String b = payloadPatientMod.allowOnlineCancellationPayload(testData.getAppointmentId());
		log("Practice Id is-" + practiceid);

		Response response = postAPIRequest.allowonlinecancellation(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelStatusPost() throws IOException {

		Response response = postAPIRequest.cancelStatus(testData.getBasicURI(),
				payloadPatientMod.cancelStatusPayload(testData.getAppointmentId()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientIdPm());

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCommentDetailsPost() throws IOException {

		Response response = postAPIRequest.commentDetails(testData.getBasicURI(),
				payloadPatientMod.commentDetailsPayload(testData.getAppointmentIdApp(), testData.getBookIdApp(),
						testData.getLocationIdApp()),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientIdPm());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTimeZoneCodePost() throws IOException {

		Response response = postAPIRequest.timeZoneCode(testData.getBasicURI(),
				payloadPatientMod.timeZnCodeForDatePayload(), headerConfig.HeaderwithToken(testData.getAccessToken()),
				practiceid, testData.getPatientIdPm());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String localtimezonecode = js.getString("locTimeZoneCode");
		assertEquals(localtimezonecode, testData.getLocationTimeZoneCode(), "TimeZoneCode is wrong");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsPost_ExistingPatient() throws IOException {

		String patientid = testData.getPatientId();

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.availableSlots(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsPost_ExistingPatient_Invalid() throws IOException {

		String patientid = testData.getPatientId();

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate, "12345",
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.availableSlots(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsPost_NewPatient() throws IOException {

		String patientid = null;

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		log("Current Date- " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.availableSlots(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsPost_NewPatient_Invalid() throws IOException {

		String patientid = null;

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String currentdate = dateFormat.format(date);
		log("Current Date- " + currentdate);

		String b = payloadPatientMod.availableslotsPayload(currentdate, "12345",
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.availableSlots(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleAppointmentPost() throws IOException {

		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		String b = payloadPatientMod.availableslotsPayload(currentdate,
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response response = postAPIRequest.availableSlots(testData.getBasicURI(), b,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		String slotid = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotId");
		String time = arr.getJSONObject(2).getJSONArray("slotList").getJSONObject(0).getString("slotTime");
		String date = arr.getJSONObject(2).getString("date");

		log("slotTime-" + time);
		log("slotId- " + slotid);
		log("Date-" + date);

		String c = payloadPatientMod.scheduleAppointmentPayload(slotid, date, time,
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		log("Payload-" + c);

		Response schedResponse = postAPIRequest.scheduleAppointment(testData.getBasicURI(), c,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId(),
				testData.getPatientType());
		apv.responseCodeValidation(schedResponse, 200);
		apv.responseTimeValidation(schedResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "patientId");
		String confirmationno = apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		apv.responseKeyValidationJson(schedResponse, "slotAlreadyTaken");

		String startdate = date + " " + time;

		String d = payloadPatientMod.rescheduleAppointmentPayload(slotid, startdate, confirmationno,
				propertyData.getProperty("availableslot.bookid.pm"),
				propertyData.getProperty("availableslot.locationid.pm"),
				propertyData.getProperty("availableslot.apptid.pm"));

		Response rescheduleResponse = postAPIRequest.rescheduleAppointment(testData.getBasicURI(), d,
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid,
				testData.getPatientIdAvailableSlots(), testData.getPatientType());

		apv.responseCodeValidation(rescheduleResponse, 200);
		apv.responseTimeValidation(rescheduleResponse);

		apv.responseKeyValidationJson(schedResponse, "confirmationNo");
		String extapptid_new = apv.responseKeyValidationJson(schedResponse, "extApptId");

		Response cancelResponse = postAPIRequest.cancelAppointment(testData.getBasicURI(),
				payloadPatientMod.cancelAppointmentPayload(extapptid_new),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, testData.getPatientId());
		apv.responseCodeValidation(cancelResponse, 200);
		apv.responseTimeValidation(cancelResponse);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByRulePost_NewPatient() throws IOException {

		String patientid = null;

		Response response = postAPIRequest.appointmentTypesByRule(testData.getBasicURI(),
				payloadPatientMod.appointmentTypesByrulePayload_New(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, patientid);

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByRulePost_ExPatient() throws IOException {

		Response response = postAPIRequest.appointmentTypesByRule(testData.getBasicURI(),
				payloadPatientMod.appointmentTypesByrulePayload_New(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid,
				testData.getPatientIdAppointmentTypesRule());

		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsByPageGET() throws IOException {

		Response response = postAPIRequest.pastAppointmentsByPage(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid,
				propertyData.getProperty("pastappt.patient.id.pm"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());

		String appmntid = js.getString("content[0].appointmentTypes.name");
		log("Appointment Id- " + appmntid);

		String location = js.getString("content[0].location.name");
		log("Location Name- " + location);

		String book = js.getString("content[0].book.resourceName");
		log("Resource Name - " + book);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsByPageGETInvalid() throws IOException {

		Response response = postAPIRequest.pastAppointmentsByPage(testData.getBasicURI(),
				headerConfig.HeaderwithToken(testData.getAccessToken()), practiceid, "1111");
		apv.responseCodeValidation(response, 204);
		apv.responseTimeValidation(response);
	}
}
