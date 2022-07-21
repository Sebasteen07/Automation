// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfPracticeSettingsManager extends BaseTestNGWebDriver {
	private static PostAPIRequestMfPracticeSettingsManager postAPIRequest = new PostAPIRequestMfPracticeSettingsManager();

	private PostAPIRequestMfPracticeSettingsManager() {
	}

	public static PostAPIRequestMfPracticeSettingsManager getPostAPIRequestMfPracticeSettingsManager() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response defaultConfirmationSetting(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the default confirmation setting for a practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/defaultConfirmationSetting/Email").then().log().all().extract().response();
		return response;
	}

	public Response defaultConfSettingWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for Get the default confirmation setting for a practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("/defaultConfirmationSetting/Email").then().log().all().extract().response();
		return response;
	}

	public Response defaultConfSettingWithoutDeliveryMethod(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the default confirmation setting for a practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/defaultConfirmationSetting").then().log().all().extract().response();
		return response;
	}

	public Response defaultReminderSetting(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the default reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/defaultReminderSetting/Email").then().log().all().extract().response();
		return response;
	}

	public Response defaultReminderSettingWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for Get the default reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("/defaultReminderSetting/Email").then().log().all().extract().response();
		return response;
	}

	public Response defaultReminderSettingWithoutDeliveryMethod(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the default reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/defaultReminderSetting").then().log().all().extract().response();
		return response;
	}

	public Response reminderSetting(Map<String, String> Header, String practiceId, String reminderId) {
		log("Execute Get request for Get the reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/reminderSetting/" + reminderId).then().log().all().extract().response();
		return response;
	}

	public Response reminderSettingWithoutPracticeId(Map<String, String> Header, String reminderId) {
		log("Execute Get request for Get the reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("/reminderSetting/" + reminderId).then().log().all().extract().response();
		return response;
	}

	public Response reminderSettingWithoutReminderId(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the reminder setting for a practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get(practiceId + "/reminderSetting/").then().log().all().extract().response();
		return response;
	}

	public Response retrieveSettingForAllPractices(Map<String, String> Header) {
		log("Execute Get request for retrieve settings for all practices");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("all").then().log().all()
				.extract().response();
		return response;
	}

	public Response retrieveSettingForSpecificPractice(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get retrieve setting for a specific practice.");
		Response response = given().spec(requestSpec).log().all().queryParam("ids", practiceId).headers(Header).when()
				.get("all").then().log().all().extract().response();
		return response;
	}

	public Response retrivesSettingsGetForAPractice(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the settings for a practice. Patient user only.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("practice/" + practiceId)
				.then().log().all().extract().response();
		return response;
	}

	public Response retrivesSettingsGetWithoutAPracticeId(Map<String, String> Header) {
		log("Execute Get request for Get the settings for a practice. Patient user only.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("practice/").then().log()
				.all().extract().response();
		return response;
	}

	public Response retrivesCadenceForAllPractices(Map<String, String> Header) {
		log("Execute Get request for Get the settings for Cadence For All Practices.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("practicesCadence").then()
				.log().all().extract().response();
		return response;
	}

	public Response retrivesCadenceForSpecifiedPractice(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Get the settings for Cadence For Specified Practices.");
		Response response = given().spec(requestSpec).log().all().queryParam("ids", practiceId).headers(Header).when()
				.get("practicesCadence").then().log().all().extract().response();
		return response;
	}

	public Response updateReminderSettings(String Payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update reminder settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(Payload).when()
				.put(practiceId + "/reminderSetting").then().log().all().extract().response();
		return response;
	}

	public Response updateSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response updateActiveSettings(String payload, Map<String, String> Header, String practiceId,
			String activeSetting) {
		log("Execute Put request for Update active setting for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("/practice/" + practiceId + "/active/" + activeSetting).then().log().all().extract().response();
		return response;
	}

	public Response updateActiveSettingsWithoutActiveValue(String payload, Map<String, String> Header,
			String practiceId) {
		log("Execute Put request for Update active setting for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("/practice/" + practiceId + "/active/").then().log().all().extract().response();
		return response;
	}

	public Response updateLocationSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update location settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/locationSettings").then().log().all().extract().response();
		return response;
	}

	public Response updateMerchantSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update merchant settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/merchantSettings").then().log().all().extract().response();
		return response;
	}

	public Response updateNotifySettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update notify settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/notifySettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePmIntegrationSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update PM integration settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/pmIntegrationSettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePmIntegrationSettingsWithoutPracticeID(String payload, Map<String, String> Header) {
		log("Execute Put request for Update PM integration settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/pmIntegrationSettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePrecheckSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update precheck settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/precheckSettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePrecheckSettingsWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute Put request for Update precheck settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/precheckSettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePssSettings(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Put request for Update PSS settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/" + practiceId + "/pssSettings").then().log().all().extract().response();
		return response;
	}

	public Response updatePssSettingsWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute Put request for Update PSS settings for an existing practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("practice/pssSettings").then().log().all().extract().response();
		return response;
	}

	public Response createSettings(String payload, Map<String, String> Header) {
		log("Execute Post request for Create settings for a practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().post().then()
				.log().all().extract().response();
		return response;
	}

	public Response getSettingsForAPractice(String baseurl, Map<String, String> Header, String practiceId,
			String patientId, String apptId, String payload) {
		log("Execute Get request for Return Encrypted Identifier");
		RestAssured.baseURI = baseurl;
		Response getEncryptedIdentifier = given().log().all().headers(Header).when()
				.get("practices/" + practiceId + "/patients/" + patientId + "/appointments/" + apptId).then().log()
				.all().extract().response();
		JsonPath jsonPath = new JsonPath(getEncryptedIdentifier.asString());
		String getEncryptedId = jsonPath.get("encryptedIdentifier");
		log("Encrypted Identifier-- " + getEncryptedId);
		log("Execute Get request for Return Guest Token");
		Response getGuestToken = given().log().all().headers(Header).queryParam("token", getEncryptedId).body(payload)
				.when().post("guestSessions/session").then().log().all().extract().response();
		String GuestToken = getGuestToken.header("Guest");
		log("Guest Token : " + GuestToken);
		log("Execute get request for Get the settings for a practice. Patient user only.");
		Response response = given().spec(requestSpec).log().all().queryParam("pmPatientId", patientId)
				.headers("Authorization", "Guest " + GuestToken).when().get("practice/" + practiceId + "/patient/guest")
				.then().log().all().extract().response();
		return response;
	}

	public Response getImhFormByConceptName(Map<String, String> Header, String practiceId, String conceptName) {
		log("Execute get request for get IMH Form By Concept Name");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practiceId/" + practiceId + "/form/conceptName/" + conceptName).then().log().all().extract()
				.response();
		return response;
	}

	public Response saveCustomImhFormPost(Map<String, String> Header, String payload, String practiceId) {
		log("Execute Post request for save IMH Form");
		Response response = given().spec(requestSpec).log().all().body(payload).headers(Header).when()
				.post("practice/" + practiceId + "/imh/form").then().log().all().extract().response();
		return response;
	}

	public Response getImhFormWithoutConceptName(Map<String, String> Header, String practiceId) {
		log("Execute get request for get IMH Form By Concept Name");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practiceId/" + practiceId + "/form/conceptName/").then().log().all().extract().response();
		return response;
	}

	public Response getImhFormWithoutAccessToken(String practiceId, String conceptName) {
		log("Execute get request for get IMH Form By Concept Name");
		Response response = given().spec(requestSpec).log().all().when()
				.get("practiceId/" + practiceId + "/form/conceptName/" + conceptName).then().log().all().extract()
				.response();
		return response;
	}

	public Response getListOfForms(Map<String, String> Header, String practiceId) {
		log("Execute get request for Get the list of forms for a practice");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("practice/" + practiceId + "/all/forms").then().log().all().extract().response();
		return response;
	}

	public Response getUpdateForm(String payload, Map<String, String> Header, String practiceId) {
		log("Execute get request for Update the forms list for an existing practice");
		Response response = given().spec(requestSpec).log().all().body(payload).headers(Header).when()
				.put("practice/" + practiceId + "/forms").then().log().all().extract().response();
		return response;
	}
	
	public Response getUpdateForm(String baseUrl,String payload, Map<String, String> Header, String practiceId) {
		log("Execute get request for Update the forms list for an existing practice");
		RestAssured.baseURI=baseUrl;
		Response response = given().log().all().body(payload).headers(Header).when()
				.put("practice/" + practiceId + "/forms").then().log().all().extract().response();
		return response;
	}
	
	public Response getListOfForms(String baseUrl, Map<String, String> Header, String practiceId) {
		log("Execute get request for Get the list of forms for a practice");
		RestAssured.baseURI=baseUrl;
		Response response = given().log().all().headers(Header).when()
				.get("practice/" + practiceId + "/all/forms").then().log().all().extract().response();
		return response;
	}

}
