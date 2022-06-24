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

public class PostAPIRequestAptPrecheckNegativeCases extends BaseTestNGWebDriver {

	private static PostAPIRequestAptPrecheckNegativeCases postAPIRequest = new PostAPIRequestAptPrecheckNegativeCases();

	private PostAPIRequestAptPrecheckNegativeCases() {
	}

	public static PostAPIRequestAptPrecheckNegativeCases getPostAPIRequestAptPrecheck() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response aptpostCopaySkipIncorrectPatientId(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Copay/Skip with incorrect patient id");
		Response response = given().when().headers(Header).log().all().when().post(
				"practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/copay/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptpostBalanceSkip(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Balance/Skip");
		Response response = given().when().headers(Header).log().all().when().post("practices/" + practiceId
				+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/balance/skip").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptpostBalanceSkipIncorrectPatientId(String practiceId, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute POST request for balance/Skip with incorrect patient id");
		Response response = given().when().headers(Header).log().all().when().post("practices/" + practiceId
				+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/balance/skip").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptPutForms(String practiceId, String payload, Map<String, String> Header, String Appointmentid) {
		log("Execute PUT request for Forms");
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.put("practices/" + practiceId + "/patients/appointments/" + Appointmentid + "/forms").then().log()
				.all().extract().response();
		return response;
	}

	public Response aptPutFormsIncorrectPatientId(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute PUT request for Forms");
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.put("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/forms")
				.then().log().all().extract().response();
		return response;
	}

	public Response getRequiredFormsIncorrectPatientId(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for Required forms");
		Response response = given().when().headers(Header).log().all().when().get("practices/" + practiceId
				+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/requiredForms").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptPutInsurance(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute PUT request for Insurance");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().put("practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/insurance")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPutDemographicsIncorrectPatientId(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute PUT request for demographics");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().put("practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/demographics")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptCopayPayNoAmount(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Copay pay");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().post("practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/copay/pay")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptBalancePayNoAmount(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute POST request for Copay pay");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().post("practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/balance/pay")
				.then().log().all().extract().response();
		return response;
	}

	public Response getPatientsIdentfnIncorrectData(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for patient identification");

		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response responsePatientIdentification = given().when().queryParam("patient_token", encryptedIdentifier)
				.headers(Header).log().all().when().get("patients/identification").then().log().all().extract()
				.response();

		log("Patient Identification:" + responsePatientIdentification);
		return responsePatientIdentification;
	}

	public Response getFormInformationIncorrectData(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET  request for form information");

		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response responseforminfo = given().when().queryParam("patient_token", encryptedIdentifier).headers(Header)
				.log().all().when().get("patients/form-information").then().log().all().extract().response();

		log("form info:" + responseforminfo);
		return responseforminfo;
	}
	public Response aptPostPracticeId(String practiceId, String payload, Map<String, String> Header) {
		log("Execute Post  request for practices/practiseId");
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.post("practices/" + practiceId).then().log().all().extract().response();
		return response;
	}
	
	public Response aptPostHistoryMessageIncorrectData(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute Post  request for history message for patient");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().post("practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/historymessage")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response aptMessageHistoryIncorectMedium(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute Post  request for CHECKIN, BROADCAST message history for patient");
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.queryParam("startDate", "2021-06-17T14:35:09.179Z").queryParam("medium", "fcghb")
				.queryParam("type", "BROADCAST").post("practices/" + practiceId + "/patients/" + PatientId
						+ "/appointments/" + Appointmentid + "/messagehistory")
				.then().log().all().extract().response();

		return response;
	}
	
	public Response aptMessageHistoryIncorectType(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute Post  request for CHECKIN, BROADCAST message history for patient");
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.queryParam("startDate", "2021-06-17T14:35:09.179Z").queryParam("medium", "EMAIL")
				.queryParam("type", "cgg").post("practices/" + practiceId + "/patients/" + PatientId
						+ "/appointments/" + Appointmentid + "/messagehistory")
				.then().log().all().extract().response();

		return response;
	}

}
