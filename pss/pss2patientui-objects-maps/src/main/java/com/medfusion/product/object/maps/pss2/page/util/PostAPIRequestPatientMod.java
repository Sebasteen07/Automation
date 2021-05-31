// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;


import static io.restassured.RestAssured.given;

import java.util.Map;

import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestPatientMod extends BaseTestNGWebDriver {
	APIVerification apiVerification = new APIVerification();

	public JsonPath apptDetailFromGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.APPT_DETAIL_FROM_GUID).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Appointment location -" + js.getString("location.displayName"));
		log("Appointment location -" + js.getString("location.id"));
		log("Appointment Type -" + js.getString("appointmentType.name"));
		log("Appointment Book Name -" + js.getString("book.displayName"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		log("Token -" + js.getString("token"));
		log("Practice guid -" + js.getString("guid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksValueGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKLS_VALUE_GUID).then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Patient id -" + js.getString("emrid"));
		log("Welcome Message -" + js.getString("welcomeMessage"));
		log("Token-" + js.getString("token"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksValueGuidAndPractice(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKLS_VALUE_GUID_AND_PRACTICE).then().log()
				.all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("name"));
		log("Token-" + js.getString("token"));
		log("Welcome Message -" + js.getString("linkGen.locationId"));
		log("Practice name -" + js.getString("practiceId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksDetailGuid(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKS_DETAIL_GUID).then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Patient id -" + js.getString("emrid"));
		log("Welcome Message -" + js.getString("welcomeMessage"));
		log("Token-" + js.getString("token"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath linksDetailGuidAndPractice(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get(APIPath.apiPathPatientMod.LINKS_DETAIL_GUID_AND_PRACTICE).then().log()
				.all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Patient id -" + js.getString("emrid"));
		log("Welcome Message -" + js.getString("welcomeMessage"));
		log("Token-" + js.getString("token"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath guidForLogoutPatient(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", "LOGINLESS").when().get(APIPath.apiPathPatientMod.GUID_FOR_LOGOUT_PATIENT).then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Patient id -" + js.getString("id"));
		log("Patient name -" + js.getString("name"));
		log("Patient ext Practiceid -" + js.getString("extPracticeId"));
		log("Patient book -" + js.getString("book"));
		log("Token-" + js.getString("token"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuidLoginless(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID_LOGINLESS).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		log("Token -" + js.getString("token"));
		log("Practice guid -" + js.getString("guid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath tokenForLoginless(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.TOKEN_FOR_LOGINLESS).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		log("Token -" + js.getString("token"));
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
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice id -" + js.getString("practiceId"));
		log("Practice name-" + js.getString("practiceName"));
		log("Practice status -" + js.getString("active"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceInfo(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_INFO).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("id"));
		log("Ext PracticeId -" + js.getString("name"));
		log("Token -" + js.getString("practiceId"));
		log("Practice guid -" + js.getString("partner"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath resellerLogo(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.RESELLER_LOGO).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath sessionConfiguration(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =	given().log().all().when().get(APIPath.apiPathPatientMod.SESSION_CONFIGURATION).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("tokenExpirationTime"));
		log("Ext PracticeId -" + js.getString("expirationWarningTime"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceDetail(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_DETAIL).then().log().all().assertThat().statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("practice id -" + js.getString("id"));
		log("practice name -" + js.getString("name"));
		log("Practice id -" + js.getString("practiceId"));
		log("Practice guid -" + js.getString("guid"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath practiceFromGuidSso(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.PRACTICE_FROM_GUID_SSO).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public Response timeZoneResource(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.TIME_ZONE_RESOURCES).then().log().all().assertThat().statusCode(200).extract().response();

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
		Response response =	given().log().all().header("flowType", "LOGINLESS").get("/accesstoken").then().log().all().assertThat().statusCode(200).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		log("Status Code- " + response.getStatusCode());

		ParseJSONFile.getKey(jsonobject, "accessToken");
		apiVerification.responseKeyValidation(response, "accessToken");
		apiVerification.responseTimeValidation(response);

		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("accessToken");
		log("The Access Token is    " + jsonPath.get("accessToken"));

		return access_Token;
	}

	public JsonPath upcomingConfiguration(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.UPCOMING_CONFIGURATION).then().log().all().assertThat().statusCode(200).extract().response();

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
				.assertThat().statusCode(200).extract().response();
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
		Response response =	given().log().all().when().get(APIPath.apiPathPatientMod.ANNOUNCEMENT_BY_NAME).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Message -" + js.getString("message"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath announcementByLanguage(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =	given().log().all().when().get(APIPath.apiPathPatientMod.ANNOUNCEMENT_BY_LANGUAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		log("Announcement id -" + js.getString("id"));
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
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.GET_IMAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath getLanguages(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.GET_LANGUAGE).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "flag");
		apiVerification.responseKeyValidation(response, "code");
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseTimeValidation(response);
		
		return js;
	}

	public JsonPath demographicsProfiles(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathPatientMod.DEMOGRAPHICS_PROFILES).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

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
				.assertThat().statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

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
				.log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		JsonPath js = new JsonPath(response.asString());

		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response locationsByNextAvailable(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.LOCATION_BY_NEXT_AVAILABLE).then()
				.log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Location id-" + js.getString("id[0]"));
		log("Next Availability- " + js.getString("nextAvailability[0]"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response locationsByRule(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.LOCATION_BY_RULE).then().log()
				.all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Search location -" + js.getString("showSearchLocation"));
		log("Location Display name -" + js.getString("locations[0].displayName"));
		log("Location id -" + js.getString("locations[0].id"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response anonymousMatchAndCreatePatient(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.ANONYMOUS_MATCH_AND_CREATE_PATIENT)
				.then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
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
				.assertThat().statusCode(200).extract().response();

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
		log("Show Search Location -" + js.getString("showSearchLocation"));
		log("Show Next Available -" + js.getString("showNextAvailable"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath appointment(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =	given().log().all().when().get(APIPath.apiPathPatientMod.APPOINTMENT).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Status Code- " + response.getStatusCode());

		log("Patient Id -" + js.getString("patientId"));
		log("Confirmation No -" + js.getString("confirmationNo"));
		log("location Name -" + js.getString("locationName"));
		log("Book Name -" + js.getString("bookName"));
		apiVerification.responseTimeValidation(response);

		return js;
	}

	public JsonPath appointmentForIcs(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =	given().log().all().when().get(APIPath.apiPathPatientMod.APPOINTMENT_FOR_ICS).then().log().all().assertThat().statusCode(200).extract().response();

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
				.log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Next available id -" + js.getString("id[0]"));
		log("Next availability stasus -" + js.getString("nextAvailability[0]"));
		log("Next availability Slots -" + js.getString("nextAvailabilitySlot[0]"));
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response booksBynextAvailable(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathPatientMod.BOOK_BY_NEXT_AVAILABLE).then()
				.log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());

		JsonPath js = new JsonPath(response.asString());
		log("Next available id -" + js.getString("id[0]"));
		log("Next availability stasus -" + js.getString("nextAvailability[0]"));
		log("Next availability Slots -" + js.getString("nextAvailabilitySlot[0]"));
		apiVerification.responseTimeValidation(response);

		return response;
	}
}
