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

public class PostAPIRequestAptPrecheck extends BaseTestNGWebDriver {

	private static PostAPIRequestAptPrecheck postAPIRequest = new PostAPIRequestAptPrecheck();

	private PostAPIRequestAptPrecheck() {
	}

	public static PostAPIRequestAptPrecheck getPostAPIRequestAptPrecheck() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getAppointments(String practiceId, Map<String, String> Header, String PatientId) {
		log("Execute GET request for appointments for  a patient for a given start date");
		Response response = given().spec(requestSpec).when().queryParam("startDate", "1624150818").headers(Header).log()
				.all().when().get("practices/" + practiceId + "/patients/" + PatientId + "/appointments").then().log()
				.all().extract().response();
		return response;
	}

	public Response getAppointmentsAppId(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for appointments_appId");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		return response;
	}

	public Response getAppointmentsStartDay(String practiceId, Map<String, String> Header, String StartDay) {
		log("Execute GET request for appointments for a practise for a particular day");
		Response response = given().spec(requestSpec).when().queryParam("dayStart", StartDay).headers(Header).log()
				.all().when().get("practices/" + practiceId + "/appointments").then().log().all().extract().response();
		return response;
	}

	public Response aptPostPracticeId(String practiceId, String payload, Map<String, String> Header) {
		log("Execute Post  request for practices/practiseId");
		Response response = given().spec(requestSpec).when().headers(Header).body(payload).log().all().when()
				.post("practices/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response aptPostArrivals(String practiceId, String payload, Map<String, String> Header) {
		log("Execute Post  request for arrivals");
		Response response = given().spec(requestSpec).when().headers(Header).body(payload).log().all().when()
				.post("practices/" + practiceId + "/arrivals").then().log().all().extract().response();
		return response;
	}

	public Response aptPostHistoryMessage(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute Post  request for history message for patient");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().post("practices/"
						+ practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/historymessage")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptMessageHistory(String startDate, String medium, String type, String practiceId, String payload,
			Map<String, String> Header, String PatientId, String Appointmentid) {
		log("Execute Post  request for CHECKIN, BROADCAST message history for patient");
		Response response = given().spec(requestSpec).when().headers(Header).body(payload).log().all().when()
				.queryParam("startDate", startDate).queryParam("medium", medium).queryParam("type", type)
				.post("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid
						+ "/messagehistory")
				.then().log().all().extract().response();

		return response;
	}

	public Response getFormInformation(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET  request for form information");

		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response responseforminfo = given().spec(requestSpec).when().queryParam("patient_token", encryptedIdentifier)
				.headers(Header).log().all().when().get("patients/form-information").then().log().all().extract()
				.response();

		return responseforminfo;
	}

	public Response aptBroadcastMessage(String practiceId, String payload, Map<String, String> Header) {
		log("Execute POST request for BroadcastMessage");
		Response response = given().spec(requestSpec).spec(requestSpec).when().headers(Header).body(payload).log().all()
				.when().post("practices/" + practiceId + "/broadcastmessage").then().log().all().extract().response();
		return response;
	}

	public Response getCheckinActions(String baseurl, String practiceId, String payload, Map<String, String> Header) {
		log("Execute POST request for CheckinActions");
		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).body(payload).log().all().when()
				.post("checkin_actions/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response getDELETEAppointmentTypes(Map<String, String> Header) {
		log("Execute DELETE call for appointmnet types");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.delete("cache/appointmentTypes").then().log().all().extract().response();
		return response;
	}

	public Response getDELETEPracticeSettings(Map<String, String> Header) {
		log("Execute DELETE call for practice settings");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.delete("cache/practiceSettings").then().log().all().extract().response();
		return response;
	}

	public Response getDELETETimezones(Map<String, String> Header) {
		log("Execute DELETE call for timezones types");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.delete("cache/timeZones").then().log().all().extract().response();
		return response;

	}

	public Response getDELETEAppointmentsFromDb(String practiceId, String payload, Map<String, String> Header) {
		log("Execute DELETE call for appointments to deleted from db");
		Response response = given().spec(requestSpec).when().headers(Header).body(payload).log().all().when()
				.delete("practices/" + practiceId + "/appointments").then().log().all().extract().response();
		return response;
	}

	public Response getPatientsIdentification(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for patient identification");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response responsePatientIdentification = given().spec(requestSpec).when()
				.queryParam("patient_token", encryptedIdentifier).headers(Header).log().all().when()
				.get("patients/identification").then().log().all().extract().response();

		return responsePatientIdentification;
	}

	public Response aptpostAppointments(String practiceId, String payload, Map<String, String> Header,
			String IntegrationId, String PatientId) {
		log("Execute POST request for appointments");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().post("practices/"
						+ practiceId + "/integrations/" + IntegrationId + "/patients/" + PatientId + "/appointments")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptpostCopaySkip(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Copay/Skip");
		Response response = given()
				.spec(requestSpec).when().headers(Header).log().all().when().post("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/copay/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptpostCopaySkipIncorrectPatientId(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Copay/Skip with incorrect patient id");
		Response response = given()
				.spec(requestSpec).when().headers(Header).log().all().when().post("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/copay/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPostBalanceSkip(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Balance/Skip");
		Response response = given()
				.spec(requestSpec).when().headers(Header).log().all().when().post("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/balance/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptpostBalanceSkipIncorrectPatientId(String practiceId, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute POST request for balance/Skip with incorrect patient id");
		Response response = given()
				.spec(requestSpec).when().headers(Header).log().all().when().post("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/balance/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPutForms(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute PUT request for Forms");
		Response response = given().spec(requestSpec).when().headers(Header).body(payload).log().all().when()
				.put("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/forms")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPutInsurance(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute PUT request for Insurance");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().put("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/insurance")
				.then().log().all().extract().response();
		return response;
	}

	public Response getRequiredForms(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for Required forms");
		Response response = given()
				.spec(requestSpec).when().headers(Header).log().all().when().get("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/requiredForms")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPutDemographics(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute PUT request for demographics");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().put("practices/" + practiceId
						+ "/patients/" + PatientId + "/appointments/" + Appointmentid + "/demographics")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptBalancePay(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for balance pay");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().post("practices/"
						+ practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/balance/pay")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptCopayPay(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Copay pay");
		Response response = given()
				.spec(requestSpec).when().headers(Header).body(payload).log().all().when().post("practices/"
						+ practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid + "/copay/pay")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptAppointmentActionsConfirm(String baseurl, String practiceId, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute POST request for Appointmnet actions as Confirm");
		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "CONFIRM")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("appointment_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptAppointmentActionsCancel(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Appointmnet actions as cancel");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().spec(requestSpec).when().queryParam("action", "CANCEL")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("appointment_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptAppointmentActionsArrival(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Appointmnet actions as Arrival");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "ARRIVAL")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("appointment_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptAppointmentActionsCurbscheckin(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Appointmnet actions as CurbsideCheckin");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "CURBSCHECKIN")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("appointment_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptArrivalActionsCurbsideConfirm(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Arrival actions curbside confirm");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "CONFIRM")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("arrival_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptArrivalActionsCurbsideCancel(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute POST request for Arrival actions curbside cancel");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "CANCEL")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("arrival_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptArrivalActionsCurbsideArrival(String baseurl, String practiceId, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		RestAssured.baseURI = baseurl;
		log("Execute POST request for Arrival actions curbside arrival");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "ARRIVAL")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("arrival_actions")
				.then().log().all().extract().response();

		log("Appointment actions:" + appointmentActions);
		return appointmentActions;
	}

	public Response aptArrivalActionsCurbsideCurbscheckin(String baseurl, String practiceId, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		RestAssured.baseURI = baseurl;
		log("Execute POST request for Arrival actions curbside checkin");
		Response response = given().when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response appointmentActions = given().when().queryParam("action", "CURBSCHECKIN")
				.queryParam("token", encryptedIdentifier).headers(Header).log().all().when().post("arrival_actions")
				.then().log().all().extract().response();

		return appointmentActions;
	}

	public String aptGuestSessionsSession(String practiceId, String payload, Map<String, String> Header,
			String PatientId, String Appointmentid) {
		log("Execute POST request for guestSession_Session request");

		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();

		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");
		log("TokenEncrypt:" + encryptedIdentifier);

		Response guestToken = given().spec(requestSpec).when().headers(Header).queryParam("token", encryptedIdentifier)
				.body(payload).log().all().when().post("guestSessions/session").then().log().all().extract().response();

		String guest = guestToken.header("guest");

		log("GuestTokenValue:" + guest);

		return guest;
	}

	public Response getAppointmentsGuest(String practiceId, String header, String PatientId) {
		log("Execute GET request for appointments guest resource");
		Response response = given().spec(requestSpec).when().queryParam("startDate", "1627886393")
				.headers("Authorization", "Guest " + header).log().all().when()
				.get("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments").then().log()
				.all().extract().response();
		return response;
	}

	public Response GuestSessionsAuthorization(String header) {
		log("Execute GET request for guestSessionAuthorization");
		Response response = given().spec(requestSpec).when().headers("Authorization", "Guest " + header).log().all()
				.when().get("guestSessions/authorization").then().log().all().extract().response();
		return response;
	}

	public Response getAppointmentsAppIdGuest(String practiceId, String header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for appointmentsAppid guest resource");
		Response response = given().spec(requestSpec).when().headers("Authorization", "Guest " + header).log().all()
				.when().get("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid)
				.then().log().all().extract().response();
		return response;
	}

	public Response getRequiredFormsGuest(String practiceId, String header, String PatientId, String Appointmentid) {
		log("Execute GET request for required forms");
		Response response = given().spec(requestSpec).when().headers("Authorization", "Guest " + header).log().all()
				.when().get("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/requiredForms")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPutFormsGuest(String practiceId, String payload, String header, String PatientId,
			String Appointmentid, Map<String, String> Defaultheader) {
		log("Execute PUT request for forms");
		Response response = given().spec(requestSpec).when().headers(Defaultheader)
				.headers("Authorization", "Guest " + header).body(payload).log().all().when()
				.put("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/forms")
				.then().log().all().extract().response();

		String contentType = response.header("Content-Type");
		log("content type:" + contentType);
		return response;
	}

	public Response aptPutInsuranceGuest(String practiceId, String payload, String header, String PatientId,
			String Appointmentid, Map<String, String> Defaultheader) {
		log("Execute PUT request for Insurance");
		Response response = given().spec(requestSpec).when().headers(Defaultheader)
				.headers("Authorization", "Guest " + header).body(payload).log().all().when()
				.put("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/insurance")
				.then().log().all().extract().response();

		return response;
	}

	public Response aptPutDemographicsGuest(String practiceId, String payload, String header, String PatientId,
			String Appointmentid, Map<String, String> Defaultheader) {
		log("Execute PUT request for demographics");
		Response response = given().spec(requestSpec).when().headers(Defaultheader)
				.headers("Authorization", "Guest " + header).body(payload).log().all().when()
				.put("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/demographics")
				.then().log().all().extract().response();

		return response;
	}

	public Response aptPostCopaySkipGuest(String practiceId, String header, String PatientId, String Appointmentid) {
		log("Execute POST request for Copay/skip");
		Response response = given().spec(requestSpec).when().headers("Authorization", "Guest " + header).log().all()
				.when().post("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/copay/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPostBalanceSkipGuest(String practiceId, String header, String PatientId, String Appointmentid) {
		log("Execute Post request for balance skip");
		Response response = given().spec(requestSpec).when().headers("Authorization", "Guest " + header).log().all()
				.when().post("guestSessions/practices/" + practiceId + "/patients/" + PatientId + "/appointments/"
						+ Appointmentid + "/balance/skip")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPostBalancePayGuest(String practiceId, String payload, String header, String PatientId,
			String Appointmentid, Map<String, String> Defaultheader) {
		log("Execute POST request for balance pay");
		Response response = given()
				.spec(requestSpec).when().headers(Defaultheader).headers("Authorization", "Guest " + header)
				.body(payload).log().all().when().post("guestSessions/practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/balance/pay")
				.then().log().all().extract().response();
		return response;
	}

	public Response aptPostCopayPayGuest(String practiceId, String payload, String header, String PatientId,
			String Appointmentid, Map<String, String> Defaultheader) {
		log("Execute POST request for copay pay");
		Response response = given()
				.spec(requestSpec).when().headers(Defaultheader).headers("Authorization", "Guest " + header)
				.body(payload).log().all().when().post("guestSessions/practices/" + practiceId + "/patients/"
						+ PatientId + "/appointments/" + Appointmentid + "/copay/pay")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response getFormInfoWithInvalidPatientToken(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET  request for form information");
		Response response = given().spec(requestSpec).when().headers(Header).log().all().when()
				.get("practices/" + practiceId + "/patients/" + PatientId + "/appointments/" + Appointmentid).then()
				.log().all().extract().response();
		JsonPath js = new JsonPath(response.asString());

		String encryptedIdentifier = js.getString("encryptedIdentifier");

		Response responseforminfo = given().spec(requestSpec).when()
				.queryParam("patient_token", encryptedIdentifier + "xyz").headers(Header).log().all().when()
				.get("patients/form-information").then().log().all().extract().response();

		return responseforminfo;
	}


}