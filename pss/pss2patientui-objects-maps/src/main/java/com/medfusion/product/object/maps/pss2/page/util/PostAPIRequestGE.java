// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestGE extends BaseTestNGWebDriver {

	APIVerification apiVerification = new APIVerification();

	public JsonPath healthCheck(String baseurl, Map<String, String> Header, String practiceId, String databaseName) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/healthcheck").then().log().all().assertThat().statusCode(200)
				.body("components.DatabaseName", equalTo(databaseName)).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("version of health check -" + js.getString("version"));
		log("Database Name - " + js.getString("components.DatabaseName"));
		log("Api status- " + js.getString("components.api"));
		return js;
	}

	public Response ping(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/ping").then().log().all().assertThat().statusCode(200).extract().response();
		return response;
	}

	public JsonPath version(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/version").then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());
		log("version -" + js.getString("version"));
		return js;
	}

	public Response lockOut(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/lockout").then().log().all().assertThat().statusCode(200).extract().response();

		log("Body ------> " + response.body().asString());

		apiVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());
		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "key");
			ParseJSONFile.getKey(jsonobject, "type");
			i++;
		}

		return response;
	}

	public Response appointmentType(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/appointmenttypes").then().log().all().assertThat().statusCode(200).extract().response();

		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");

		return response;
	}

	public Response books(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(practiceId + "/books").then().log().all().assertThat().statusCode(200).extract().response();

		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "resourceId");
		apiVerification.responseKeyValidation(response, "resourceName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "providerId");
		apiVerification.responseKeyValidation(response, "type");
		return response;
	}

	public Response insuranceCarrier(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).when().get(practiceId + "/insurancecarrier").then().log().all().assertThat().statusCode(200)
				.extract().response();

		log("Body ------ " + response.asString());
		apiVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());
		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "name");
			ParseJSONFile.getKey(jsonobject, "zipCode");
			i++;
		}

		return response;
	}

	public Response locations(String baseurl, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).when().get(practiceId + "/locations").then().log().all().extract().response();
		log("Body ------ " + response.asString());
		apiVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());
		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "id");
			ParseJSONFile.getKey(jsonobject, "name");
			i++;
		}

		return response;
	}

	public Response actuator(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/actuator").then().log().all().assertThat().statusCode(200)
				.body("_links.self.href", Matchers.notNullValue()).extract().response();

		JsonPath js = new JsonPath(response.asString());
		apiVerification.responseTimeValidation(response);
		log("Actuator Link - " + js.getString("_links.self.href"));

		return response;
	}

	public int lastseenProvider(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response =
				RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/getlastseenprovider").then().log().all().extract().response();
		apiVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		int resourceId = js.getInt("resourceId");
		log("Resource Id - " + js.getString("resourceId"));

		return resourceId;
	}

	public JsonPath appointmentStatus(String baseurl, Map<String, String> Header, String practiceId, String apptId, String patientId, String startDateTime,
			String apptTypeId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("appointmentId", apptId).queryParam("patientId", patientId).queryParam("startDateTime", startDateTime)
				.when().get(practiceId + "/appointmentstatus").then().log().all().assertThat().statusCode(200).body("appointmentTypeId", equalTo(apptTypeId)).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());

		log("status of an Appointment -" + js.getString("status"));
		log("startDateTime- " + js.getString("startDateTime"));
		apiVerification.responseTimeValidation(response);
		return js;
	}

	public Response scheduleApptPatient(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().when().headers(Header).body(b).log().all().when().post(practiceId + "/scheduleappointment").then().log().all().extract().response();

		return response;
	}

	public Response cancelAppointmentStatus(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/cancelstatus").then().log().all().assertThat()
				.statusCode(200).log().all().extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Cancelled Appointment Status -" + js.getString("checkCancelAppointmentStatus"));

		return response;
	}

	public Response cancelAppointment(String baseurl, Map<String, String> Header, String apptId, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("appointmentId", apptId).when().headers(Header).when()
				.get(practiceId + "/cancelappointment/" + patientId)
				.then().log().all().assertThat().statusCode(200).extract().response();
		return response;
	}

	public Response cancelApptWithCancelReason(String baseurl, String b, Map<String, String> Header, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().headers(Header).body(b).when().post(practiceId + "/cancelappointment/" + patientId).then().log().all()
				.assertThat().statusCode(200).extract().response();
		return response;
	}

	public String pastAppointments(String baseurl, String b, Map<String, String> Header, String practiceId, String apptResourceName, String apptLocationName) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(practiceId + "/pastappointments").then().log().all().assertThat().statusCode(200)
						.body("book[0].resourceName", equalTo(apptResourceName)).body("location[0].name", equalTo(apptLocationName)).extract().response().asString();

		log("Response is - " + response);
		JsonPath js = new JsonPath(response.toString());

		String apptName = js.getString("appointmentTypes.name");
		String resourceName = js.getString("book.resourceName");
		String locationName = js.getString("location.name");
		log("APPOINTMENT TYPE - " + apptName);
		log("resourceName - " + resourceName);
		log("locationName - " + locationName);

		return response;
	}

	public String upcomingAppt(String baseurl, String b, Map<String, String> Header, String practiceId, String apptResourceName, String apptLocationName) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(practiceId + "/upcomingappointments").then().log().all().assertThat().statusCode(200)
						.body("book[0].resourceName", equalTo(apptResourceName)).body("location[0].name", equalTo(apptLocationName)).extract().response().asString();

		log("Response is - " + response);

		JsonPath js = new JsonPath(response.toString());

		String apptName = js.getString("appointmentTypes.name");
		String resourceName = js.getString("book.resourceName");
		String locationName = js.getString("location.name");

		log("APPOINTMENT TYPE - " + apptName);
		log("resourceName - " + resourceName);
		log("locationName - " + locationName);

		return response;
	}

	public Response preventScheduling(String baseurl, Map<String, String> Header, String practiceId, String patientId, String apptId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().headers(Header).when().get(practiceId + "/preventschedulingdate/" + patientId + "/" + apptId)
				.then()
				.log().all().assertThat().statusCode(200).extract().response();

		return response;
	}

	public Response rescheduleAppt(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(b).post(practiceId + "/rescheduleappointment").then().log().all().assertThat().statusCode(200)
				.extract().response();

		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response availableSlots(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(b).when().post(practiceId + "/availableslots").then().log().all().assertThat().statusCode(200)
				.extract().response();

		log("Response is as below" + response.asString());
		JSONObject jsonobject = new JSONObject(response.asString());

		ParseJSONFile.getKey(jsonobject, "startDateTime");
		ParseJSONFile.getKey(jsonobject, "slotId");
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response addPatientPost(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/addpatient").then().log().all().assertThat()
				.statusCode(200).extract().response();
		APIVerification apiVerification = new APIVerification();
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "id");
		ParseJSONFile.getKey(jsonobject, "firstName");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response careProviderPost(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/careprovideravailability").then().log().all()
				.assertThat().statusCode(200).extract().response();
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "resourceId");
		ParseJSONFile.getKey(jsonobject, "nextAvailabledate");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response demographicsGET(String baseurl, String practiceId, String firstName, String lastName) {
		RestAssured.baseURI = baseurl;
		Response response = given().queryParam("patientId", "26854").log().all().when().get(practiceId + "/demographics").then().log().all().assertThat()
				.statusCode(200).body("firstName", equalTo(firstName)).body("lastName", equalTo(lastName)).extract().response();
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "id");
		return response;
	}

	public Response healthOperationGET(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get("/actuator/health").then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "status");
		return response;
	}

	public Response matchPatientPost(String baseurl, String b, Map<String, String> Header, String practiceId, String patientId, String firstName,
			String lastName) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/matchpatient").then().log().all().assertThat()
				.statusCode(200).body(patientId + ".firstName", equalTo(firstName)).body(patientId + ".lastName", equalTo(lastName)).extract().response();
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response patientLastVisit(String baseurl, String practiceId, String patientId) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(practiceId + "/patientlastvisit/" + patientId).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "lastVisitDateTime");
		return response;
	}

	public Response preReqAppointmentTypes(String baseurl, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().when().get(practiceId + "/prerequisteappointmenttypes").then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		return response;
	}

	public Response searchPatientPost(String baseurl, String b, Map<String, String> Header, String practiceId) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(practiceId + "/searchpatient").then().log().all()
				.assertThat().statusCode(200).extract().response();
		APIVerification apiVerification = new APIVerification();
		apiVerification.responseTimeValidation(response);
		JSONArray jsonarray = new JSONArray(response.asString());
		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {
			jsonobject = jsonarray.getJSONObject(i);
			String firstname = jsonobject.getString("firstName");
			Assert.assertTrue(firstname.equalsIgnoreCase("Test"));
			ParseJSONFile.getKey(jsonobject, "id");
			ParseJSONFile.getKey(jsonobject, "firstName");
			i++;
		}
		return response;
	}
}
