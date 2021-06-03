// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestPatientMod extends BaseTestNGWebDriver {
	APIVerification apiVerification = new APIVerification();

	public JsonPath apptDetailFromGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.APPT_DETAIL_FROM_GUID).then().log().all().assertThat().statusCode(200)
						.body("location.displayName", equalTo("pss wla")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		String id = js.getString("location.id");
		Assert.assertTrue(id.equalsIgnoreCase("205604"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID).then().log().all().assertThat().statusCode(200)
				.body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String firstName = js.get("name");
		String extPracticeId = js.get("extPracticeId");
		Assert.assertTrue(firstName.equalsIgnoreCase("NG_NT123"));
		Assert.assertTrue(extPracticeId.equalsIgnoreCase("24829"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksValueGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKLS_VALUE_GUID).then().log().all()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).body("name", equalTo("PSS-GE-24333-PRACTICE")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String guid = js.get("guid");
		String practiceId = js.get("practiceId");
		Assert.assertTrue(guid.equals("53085385-2d3b-462f-a474-1e501b5a6a21"));
		Assert.assertTrue(practiceId.equals("24333"));
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksValueGuidAndPractice(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKLS_VALUE_GUID_AND_PRACTICE).then().log()
				.all().assertThat().statusCode(200).body("token", Matchers.notNullValue()).body("name", equalTo("PSS-GE-24333-PRACTICE")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksDetailGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKS_DETAIL_GUID).then().log().all()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String patientId = js.get("emrid");
		Assert.assertTrue(patientId.equals("27641"));
		log("Patient id -" + js.getString("emrid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksDetailGuidAndPractice(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKS_DETAIL_GUID_AND_PRACTICE).then().log()
				.all().assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String patientId = js.get("emrid");
		Assert.assertTrue(patientId.equals("27641"));
		log("Patient id -" + js.getString("emrid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath guidForLogoutPatient(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", "LOGINLESS").when().get(APIPath.apiPathPatientMod.GUID_FOR_LOGOUT_PATIENT).then().log().all()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String guid = js.get("guid");
		Assert.assertTrue(guid.equalsIgnoreCase("53085385-2d3b-462f-a474-1e501b5a6a21"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuidLoginless(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID_LOGINLESS).then().log().all().assertThat().statusCode(200)
				.body("token", Matchers.notNullValue())
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String firstName = js.get("name");
		String extPracticeId = js.get("extPracticeId");
		String guid = js.get("guid");
		Assert.assertTrue(firstName.equalsIgnoreCase("PSS - NG2"));
		Assert.assertTrue(extPracticeId.equalsIgnoreCase("24249"));
		Assert.assertTrue(guid.equalsIgnoreCase("bc89e551-ac6d-4d69-b0b2-fb63c0a4951b"));
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		log("Practice guid -" + js.getString("guid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath tokenForLoginless(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.TOKEN_FOR_LOGINLESS).then().log().all().assertThat().statusCode(200)
						.body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String extPracticeId = js.get("extPracticeId");
		Assert.assertTrue(extPracticeId.equals("24333"));

		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response health(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.HEALTH).then().log().all().assertThat().statusCode(200).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "status");
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath logo(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.LOGO).then().log().all().assertThat().statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath timezonePracticeResource(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.TIMEZONE_PRACTICE_RESOURCES).then().log().all().assertThat().statusCode(200)
				.body("practiceName", equalTo("PSS - GE"))
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String practiceId = js.get("practiceId");
		Assert.assertTrue(practiceId.equals("24248"));
		String practiceTimezone = js.get("practiceTimezone");
		Assert.assertTrue(practiceTimezone.equalsIgnoreCase("America/New_York"));
		log("Practice id -" + js.getString("practiceId"));
		log("Practice name-" + js.getString("practiceName"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceInfo(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_INFO).then().log().all().assertThat().statusCode(200)
						.body("name", equalTo("PSS-GE-24333!@(((**&&()")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String extPracticeId = js.get("extPracticeId");
		Assert.assertTrue(extPracticeId.equals("24333"));
		log("Practice name -" + js.getString("id"));
		log("Ext PracticeId -" + js.getString("name"));
		log("Token -" + js.getString("practiceId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath resellerLogo(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.RESELLER_LOGO).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath sessionConfiguration(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.SESSION_CONFIGURATION).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		int tokenExpirationTime = js.get("tokenExpirationTime");
		Assert.assertEquals(tokenExpirationTime, 600);
		int expirationWarningTime = js.get("expirationWarningTime");
		Assert.assertEquals(expirationWarningTime, 60);
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceDetail(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_DETAIL).then().log().all().assertThat().statusCode(200).body("name", equalTo("PSS-GE-24333-PRACTICE")).extract().response();
		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		
		String extPracticeId = js.get("extPracticeId");
		Assert.assertTrue(extPracticeId.equals("24333"));
		String guid = js.get("guid");
		Assert.assertTrue(guid.equals("b246055f-1f52-4e9a-8c77-c00b3a63aaa9"));
		log("Practice id -" + js.getString("practiceId"));
		log("Practice guid -" + js.getString("guid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuidSso(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID_SSO).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String name = js.get("name");
		String extPracticeId = js.get("extPracticeId");
		Assert.assertTrue(name.equalsIgnoreCase("NG_NT123"));
		Assert.assertTrue(extPracticeId.equals("24829"));
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response timeZoneResource(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.TIME_ZONE_RESOURCES).then().log().all().assertThat().statusCode(200).extract().response();

		apiVerification.responseKeyValidation(response, "code");
		apiVerification.responseKeyValidation(response, "description");
		apiVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		log("Practice status -" + js.getString("active"));

		return response;
	}

	public String createToken(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", "LOGINLESS").get("/accesstoken").then().log().all().assertThat().statusCode(200)
				.body("accessToken", Matchers.notNullValue()).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		log("Status Code- " + response.getStatusCode());

		ParseJSONFile.getKey(jsonobject, "accessToken");
		apiVerification.responseTimeValidation(response);

		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("accessToken");
		log("The Access Token is    " + jsonPath.get("accessToken"));

		return access_Token;
	}

	public JsonPath upcomingConfiguration(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.UPCOMING_CONFIGURATION).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Show Cancel Reason -" + js.getString("showCancelReason"));
		log("Show Cancel Reason From PM -" + js.getString("showCancelReasonFromPM"));
		log("Show Cancel Message -" + js.getString("showCancelMessage"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response getapptDetail(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathPatientMod.GET_APPT_DETAIL).then().log().all()
				.assertThat().statusCode(200).body("location.displayName", equalTo("RIVER OAK MAIN ENGLISH")).body("appointmentType.name", equalTo("Fever n Cold"))
				.extract().response();
		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());

		log("Appointment location -" + js.getString("location.displayName"));
		log("Appointment location -" + js.getString("location.id"));
		log("Appointment Type -" + js.getString("appointmentType.name"));
		log("Appointment Book Name -" + js.getString("book.displayName"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath announcementByName(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.ANNOUNCEMENT_BY_NAME).then().log().all().assertThat().statusCode(200)
				.body("message", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Message -" + js.getString("message"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath announcementByLanguage(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.ANNOUNCEMENT_BY_LANGUAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Announcement id -" + js.getString("id"));

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseKeyValidation(response, "type");
		apiVerification.responseKeyValidation(response, "code");
		apiVerification.responseKeyValidation(response, "message");
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath announcementType(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.ANNOUNCEMENT_TYPE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		log("Announcement id -" + js.getString("id"));

		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "code");
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath getImages(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.GET_IMAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath getLanguages(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.GET_LANGUAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "flag");
		apiVerification.responseKeyValidation(response, "code");
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath demographicsProfiles(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.DEMOGRAPHICS_PROFILES).then().log().all().assertThat().statusCode(200)
				.body("token", Matchers.notNullValue()).body("practiceName", equalTo("PSS-GE-24333-PRACTICE")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String id = js.getString("id");
		Assert.assertTrue(id.equalsIgnoreCase("26854"));
		log("Demographics id-" + js.getString("id"));
		log("Practice Name -" + js.getString("practiceName"));
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response matchPatient(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.MATCH_PATIENT).then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		log("Flow Type -" + js.getString("type"));
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "entity");
		ParseJSONFile.getKey(jsonobject, "code");

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response flowIdentity(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.FLOW_IDENTITY).then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		log("Flow Type -" + js.getString("type"));
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "entity");
		ParseJSONFile.getKey(jsonobject, "code");

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response genderMapping(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.GENDER_MAPPING).then().log().all().assertThat()
				.statusCode(200).extract().response();

		apiVerification.responseKeyValidation(response, "pssCode");
		apiVerification.responseKeyValidation(response, "displayName");

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response getStates(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.GET_STATUS).then().log().all().assertThat()
				.statusCode(200).extract().response();

		apiVerification.responseKeyValidation(response, "key");
		apiVerification.responseKeyValidation(response, "value");

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath patientDemographics(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.PATIENT_DEMOGRAPHICS).then().log().all()
				.assertThat().statusCode(200).body("firstName", equalTo("GE6")).extract().response();
		JsonPath js = new JsonPath(response.asString());

		String id = js.getString("id");
		Assert.assertTrue(id.equalsIgnoreCase("26854"));
		log("Demographics id-" + js.getString("id"));
		log("Demographics First Name -" + js.getString("firstName"));
		log("Demographics Last Name -" + js.getString("lastName"));

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response validateProviderLink(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.VALIDATE_PROVIDER_LINK).then()
				.log().all().assertThat().statusCode(200).body("displayName", equalTo("Dylan, Bob")).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		Assert.assertEquals(id, 205300);
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response locationsByNextAvailable(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.LOCATION_BY_NEXT_AVAILABLE).then()
				.log().all().assertThat().statusCode(200).statusCode(200).body("id[0]", equalTo(205605)).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Location id-" + js.getString("id[0]"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response locationsByRule(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.LOCATION_BY_RULE).then().log()
				.all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");
		ParseJSONFile.getKey(jsonobject, "locTimeZone");
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response anonymousMatchAndCreatePatient(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.ANONYMOUS_MATCH_AND_CREATE_PATIENT)
				.then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("id");
		Assert.assertTrue(patientId.equalsIgnoreCase("27698"));

		log("Patient  id -" + js.getString("id"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response identifyPatientForReschedule(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.IDENTIFY_PATIENT_FOR_RESCHEDULE)
				.then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("id");
		Assert.assertTrue(patientId.equalsIgnoreCase("27699"));
		log("Patient  id -" + js.getString("id"));
		log("Patient  id -" + js.getString("otpPatientDetails[0].text"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response specialtyByRule(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.SPECIALITY_BY_RULE).then().log()
				.all().assertThat().statusCode(200).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());
		ParseJSONFile.getKey(jsonobject, "displayName");
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response createToken(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.CREATE_TOKEN).then().log().all()
				.assertThat().statusCode(200).body("accessToken", Matchers.notNullValue()).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());
		log("Valid Token -" + js.getString("validToken"));
		log("Access Token -" + js.getString("accessToken"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response locationsBasedOnZipcodeAndRadius(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(APIPath.apiPathPatientMod.LOCATION_BASED_ON_ZIPCODE_AND_RADIUS).then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		boolean showSearchLocation = js.getBoolean("showSearchLocation");
		Assert.assertEquals(showSearchLocation, true);
		log("Show Search Location -" + js.getString("showSearchLocation"));
		log("Show Next Available -" + js.getString("showNextAvailable"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath appointment(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.APPOINTMENT).then().log().all().assertThat().statusCode(200)
				.body("patientId", equalTo("27597")).body("locationName", equalTo("River Oaks Main")).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		String bookName = js.get("bookName");
		Assert.assertTrue(bookName.equalsIgnoreCase("Jennifer Brown"));

		log("Patient Id -" + js.getString("patientId"));
		log("location Name -" + js.getString("locationName"));
		log("Book Name -" + js.getString("bookName"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath appointmentForIcs(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(APIPath.apiPathPatientMod.APPOINTMENT_FOR_ICS).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath upcomingAppointmentsByPage(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.UPCOMING_APPOINTMENTS_BY_PAGE).then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath insuranceCarrier(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.INSURANCE_CARRIER).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);
		

		return js;
	}

	public JsonPath cancellationReason(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.CANCELLATION_REASON).then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "type");
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath rescheduleReason(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when().get(APIPath.apiPathPatientMod.RESCHEDULE_REASON).then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "type");
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response apptTypeNextAvailable(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.APPT_TYPE_NEXT_AVAIABLE).then()
				.log().all().assertThat().statusCode(200).body("id[0]", equalTo(201006)).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());
		log("Next availability stasus -" + js.getString("nextAvailabilitySlot[0]"));
		return response;
	}

	public Response booksBynextAvailable(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.BOOK_BY_NEXT_AVAILABLE).then()
				.log().all().assertThat().statusCode(200).body("id[0]", equalTo(207003)).extract().response();
		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Next availability Slots -" + js.getString("nextAvailabilitySlot[0]"));
		apiVerification.responseTimeValidation(response);

		return response;
	}
}
