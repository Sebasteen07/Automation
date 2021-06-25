// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestPatientMod extends BaseTestNGWebDriver {
	APIVerification apiVerification = new APIVerification();

	public String apptDetailFromGuid(String baseurl, Map<String, String> Header, String guidId,
			String locationDisplayName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(guidId + "/getapptdetails").then().log().all().assertThat()
				.statusCode(200).body("location.displayName", equalTo(locationDisplayName)).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String id = js.getString("location.id");

		return id;
	}

	public String practiceFromGuid(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/anonymous/" + guidId).then().log().all().assertThat()
				.statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String extPracticeId = js.get("extPracticeId");

		return extPracticeId;
	}

	public String linksValueGuid(String baseurl, Map<String, String> Header, String guidId, String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/link/" + guidId).then()
				.log().all().assertThat().statusCode(200).body("token", Matchers.notNullValue())
				.body("name", equalTo(practiceName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));

		return practiceId;
	}

	public String linksValueGuidAndPractice(String baseurl, Map<String, String> Header, String guidId,
			String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/link/" + guidId).then()
				.log().all().assertThat().statusCode(200).body("token", Matchers.notNullValue())
				.body("name", equalTo(practiceName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice name -" + js.getString("name"));
		log("Practice name -" + js.getString("practiceId"));

		return practiceId;
	}

	public String linksDetailGuid(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/linkdetail/" + guidId)
				.then().log().all().assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("emrid");
		log("Patient id -" + js.getString("emrid"));

		return patientId;
	}

	public String linksDetailGuidAndPractice(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("isreschedule", "true").when().get("/linkdetail/" + guidId)
				.then().log().all().assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("emrid");
		log("Patient id -" + js.getString("emrid"));

		return patientId;
	}

	public String guidForLogoutPatient(String baseurl, Map<String, String> Header, String PtacticeId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", "LOGINLESS").when()
				.get(PtacticeId + "/patientlogout").then().log().all().assertThat().statusCode(200)
				.body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String guid = js.get("guid");

		return guid;
	}

	public String practiceFromGuidLoginless(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/loginless/" + guidId).then().log().all().assertThat()
				.statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));

		return extPracticeId;
	}

	public String tokenForLoginless(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/view-appointment/" + guidId).then().log().all()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));

		return extPracticeId;
	}

	public Response health(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/actuator/health").then().log().all().assertThat()
				.statusCode(200).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "status");

		return response;
	}

	public JsonPath logo(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/logo").then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public String timezonePracticeResource(String baseurl, Map<String, String> Header, String timezonePracticeId,
			String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(timezonePracticeId + "/medfusionpractice").then().log().all()
				.assertThat().statusCode(200).body("practiceName", equalTo(practiceName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String practiceId = js.get("practiceId");
		log("Practice id -" + js.getString("practiceId"));
		log("Practice name-" + js.getString("practiceName"));

		return practiceId;
	}

	public String practiceInfo(String baseurl, Map<String, String> Header, String PracticeId, String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(PracticeId + "/practice").then().log().all().assertThat()
				.statusCode(200).body("name", equalTo(practiceName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("practiceId"));

		return extPracticeId;
	}

	public JsonPath resellerLogo(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/reseller/logo").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public int sessionConfiguration(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/getsessionconfiguration").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		int tokenExpirationTime = js.get("tokenExpirationTime");
		int expirationWarningTime = js.get("expirationWarningTime");
		log("Expiration Warning Time -" + expirationWarningTime);

		return tokenExpirationTime;
	}

	public String practiceDetail(String baseurl, Map<String, String> Header, String practiceId, String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/sso").then().log().all().assertThat()
				.statusCode(200).body("name", equalTo(practiceName)).extract().response();
		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice id -" + js.getString("practiceId"));
		log("Practice guid -" + js.getString("guid"));

		return extPracticeId;
	}

	public String practiceFromGuidSso(String baseurl, Map<String, String> Header, String guidId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/sso/" + guidId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String extPracticeId = js.get("extPracticeId");
		log("Practice name -" + js.getString("name"));
		log("Ext PracticeId -" + js.getString("extPracticeId"));

		return extPracticeId;
	}

	public Response timeZoneResource(String baseurl, Map<String, String> Header, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/timezone/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Practice status -" + js.getString("active"));

		apiVerification.responseKeyValidation(response, "code");
		apiVerification.responseKeyValidation(response, "description");

		return response;
	}

	public String createToken(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().header("flowType", "LOGINLESS").get("/accesstoken").then().log().all()
				.assertThat().statusCode(200).body("accessToken", Matchers.notNullValue()).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		ParseJSONFile.getKey(jsonobject, "accessToken");

		JsonPath jsonPath = response.jsonPath();
		String access_Token = jsonPath.get("accessToken");
		log("The Access Token is    " + jsonPath.get("accessToken"));

		return access_Token;
	}

	public JsonPath upcomingConfiguration(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/upcomingconfiguration").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Show Cancel Reason -" + js.getString("showCancelReason"));
		log("Show Cancel Reason From PM -" + js.getString("showCancelReasonFromPM"));
		log("Show Cancel Message -" + js.getString("showCancelMessage"));

		return js;
	}

	public Response getapptDetail(String baseurl, String b, Map<String, String> Header, String practiceId,
			String locationDisplayName, String apptTypeName) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(practiceId + "/getdetails").then().log().all().assertThat().statusCode(200)
				.body("location.displayName", equalTo(locationDisplayName))
				.body("appointmentType.name", equalTo(apptTypeName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Appointment location -" + js.getString("location.displayName"));
		log("Appointment location -" + js.getString("location.id"));
		log("Appointment Type -" + js.getString("appointmentType.name"));
		log("Appointment Book Name -" + js.getString("book.displayName"));

		return response;
	}

	public JsonPath announcementByName(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/announcement/AG").then().log().all()
				.assertThat().statusCode(200).body("message", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Message -" + js.getString("message"));

		return js;
	}

	public JsonPath announcementByLanguage(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/announcementbylanguage").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Announcement id -" + js.getString("id"));

		apiVerification.responseKeyValidation(response, "type");
		apiVerification.responseKeyValidation(response, "code");
		apiVerification.responseKeyValidation(response, "message");

		return js;
	}

	public JsonPath announcementType(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/announcementtype").then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Announcement id -" + js.getString("id"));

		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "code");

		return js;
	}

	public JsonPath getImages(String baseurl, Map<String, String> Header, String practiceId, String bookId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/book/" + bookId + "/image").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath getLanguages(String baseurl, Map<String, String> Header, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/language/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "flag");
		apiVerification.responseKeyValidation(response, "code");

		return js;
	}

	public String demographicsProfiles(String baseurl, Map<String, String> Header, String practiceId, String patientId,
			String practiceName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/createtoken/" + patientId).then().log().all()
				.assertThat().statusCode(200).body("token", Matchers.notNullValue())
				.body("practiceName", equalTo(practiceName)).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String demographicsId = js.getString("id");
		log("Practice Name -" + js.getString("practiceName"));

		return demographicsId;
	}

	public Response matchPatient(String baseurl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/patientmatch/" + patientId + "/LOGINLESS").then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		log("Flow Type -" + js.getString("type"));
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "entity");
		ParseJSONFile.getKey(jsonobject, "code");

		return response;
	}

	public Response flowIdentity(String baseurl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/flowidentity/" + patientId + "/LOGINLESS").then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());

		log("Flow Type -" + js.getString("type"));
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "entity");
		ParseJSONFile.getKey(jsonobject, "code");

		return response;
	}

	public Response genderMapping(String baseurl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/gendermapping/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		apiVerification.responseKeyValidation(response, "pssCode");
		apiVerification.responseKeyValidation(response, "displayName");

		return response;
	}

	public Response getStates(String baseurl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/states/" + patientId).then().log().all().assertThat().statusCode(200).extract()
				.response();

		apiVerification.responseKeyValidation(response, "key");
		apiVerification.responseKeyValidation(response, "value");

		return response;
	}

	public String patientDemographics(String baseurl, Map<String, String> Header, String practiceId, String patientId,
			String firstName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/demographics/" + patientId).then().log().all().assertThat().statusCode(200)
				.body("firstName", equalTo(firstName)).extract().response();
		JsonPath js = new JsonPath(response.asString());

		String demographicId = js.getString("id");
		log("Demographics First Name -" + js.getString("firstName"));
		log("Demographics Last Name -" + js.getString("lastName"));

		return demographicId;
	}

	public int validateProviderLink(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId, String displayName) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/validateproviderlink/" + patientId).then().log().all().assertThat().statusCode(200)
				.body("displayName", equalTo(displayName)).extract().response();

		JsonPath js = new JsonPath(response.asString());
		int id = js.getInt("id");
		log("Provider id-" + js.getString("id"));
		log("Provider Name -" + js.getString("displayName"));

		return id;
	}

	public int locationsByNextAvailable(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId, String locationId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/location/nextavailable/" + patientId).then().log().all().assertThat()
				.statusCode(200).statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		int locationPracticeId = js.getInt("id[0]");

		return locationPracticeId;
	}

	public Response locationsByRule(String baseurl, String b, Map<String, String> Header, String LocationsPracticeId,
			String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(LocationsPracticeId + "/location/rule/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");
		ParseJSONFile.getKey(jsonobject, "locTimeZone");

		return response;
	}

	public String anonymousMatchAndCreatePatient(String baseurl, String b, Map<String, String> Header,
			String practiceId, String MatchPatientId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/anonymouspatient/" + MatchPatientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("id");
		log("Patient  id -" + js.getString("id"));

		return patientId;
	}

	public String identifyPatientForReschedule(String baseurl, String b, Map<String, String> Header, String practiceId,
			String ReschedulePatientId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/identifypatient/" + ReschedulePatientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String patientId = js.get("id");
		log("Patient  id -" + js.getString("otpPatientDetails[0].text"));

		return patientId;
	}

	public Response specialtyByRule(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/specialty/rule/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");

		return response;
	}

	public Response createToken(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post("/createtoken/" + practiceId + "/tokens").then().log().all().assertThat().statusCode(200)
				.body("accessToken", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("Valid Token -" + js.getString("validToken"));
		log("Access Token -" + js.getString("accessToken"));

		return response;
	}

	public Response locationsBasedOnZipcodeAndRadius(String baseurl, String b, Map<String, String> Header,
			String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/zipcode/" + patientId).then().log().all().assertThat().statusCode(200).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());
		log("Show Search Location -" + js.getString("showSearchLocation"));
		log("Show Next Available -" + js.getString("showNextAvailable"));

		return response;
	}

	public String appointment(String baseurl, Map<String, String> Header, String practiceId, String appointmentId,
			String patientId, String locationName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/appointment/" + appointmentId + "/book/test")
				.then().log().all().assertThat().statusCode(200).body("patientId", equalTo(patientId))
				.body("locationName", equalTo(locationName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String bookName = js.get("bookName");
		log("Patient Id -" + js.getString("patientId"));
		log("location Name -" + js.getString("locationName"));
		log("Book Name -" + js.getString("bookName"));

		return bookName;
	}

	public JsonPath appointmentForIcs(String baseurl, Map<String, String> Header, String practiceId,
			String appointmentId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/appointment/" + appointmentId).then().log()
				.all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath upcomingAppointmentsByPage(String baseurl, Map<String, String> Header, String practiceId,
			String appointmentId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/appointment/" + appointmentId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath insuranceCarrier(String baseurl, Map<String, String> Header, String practiceId,
			String appointmentId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/insurancecarrier/" + appointmentId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath cancellationReason(String baseurl, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/cancellationreason/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "type");

		return js;
	}

	public JsonPath rescheduleReason(String baseurl, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/reschedulereason/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();
		JsonPath js = new JsonPath(response.asString());

		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "type");

		return js;
	}

	public int apptTypeNextAvailable(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId, String slotId) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/apptype/nextavailable/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		int nextAvailableId = js.getInt("id[0]");
		log("Next availability stasus -" + js.getString("nextAvailabilitySlot[0]"));
		return nextAvailableId;
	}

	public int booksBynextAvailable(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId, String slotId) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/book/nextavailable/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();
		JsonPath js = new JsonPath(response.asString());
		int bookByNextAvailableId = js.getInt("id[0]");
		log("Next availability Slots -" + js.getString("nextAvailabilitySlot[0]"));
		return bookByNextAvailableId;
	}

	public String booksByRule(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {

		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/book/rule/" + patientId).then().log().all().assertThat().statusCode(200).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");

		log("Book Rule Id:-" + js.getString("books[0].id"));
		log("Book DsiplayName:-" + js.getString("books[0].displayName"));

		String displayName = js.getString("books[0].displayName");

		return displayName;
	}

	public JsonPath allowonlinecancellation(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/allowonlinecancellation/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath cancelStatus(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/cancelstatus/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public JsonPath commentDetails(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/getCommentDetails/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public String timeZoneCode(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/timezonecode/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		String localTimeZoneCode = js.getString("locTimeZoneCode");

		return localTimeZoneCode;
	}

	public JsonPath availableSlots(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/availableslots/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		apiVerification.responseKeyValidation(response, "date");

		return js;
	}

	public JsonPath cancelAppointment(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/cancelappointment/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

	public String rescheduleAppointment(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/rescheduleappointment/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		String PatientId = js.getString("patientId");

		return PatientId;
	}

	public String scheduleAppointment(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {
		RestAssured.baseURI = baseurl;

		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/scheduleappointment/" + patientId).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		String PatientId = js.getString("patientId");

		return PatientId;
	}

	public Response appointmentTypesByRule(String baseurl, String b, Map<String, String> Header, String practiceId,
			String patientId) {

		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).body(b).log().all().when()
				.post(practiceId + "/appointmenttypes/rule/" + patientId).then().log().all().assertThat()
				.statusCode(200).extract().response();
		JsonPath js = new JsonPath(response.asString());
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "displayName");

		log("AppointmentType Rule Id:-" + js.getString("exposedAppointmentType[0].id"));
		log("AppointmentType DsiplayName:-" + js.getString("exposedAppointmentType[0].displayName"));
		return response;
	}

	public String pastAppointmentsByPage(String baseurl, Map<String, String> Header, String practiceId,
			String appointmentId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).log().all().when()
				.get(practiceId + "/pastappointmentsbypage/" + appointmentId + "?pageIndex=1").then().log().all()
				.assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		String AppmntId = js.getString("content[0].id");

		return AppmntId;
	}

}
