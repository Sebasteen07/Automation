// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAPIRequestGE extends BaseTestNGWebDriver {

	APIVerification apiVerification = new APIVerification();

	public JsonPath healthCheck(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.healthcheck).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("version of health check -" + js.getString("version"));
		log("Database Name - " + js.getString("components.DatabaseName"));
		log("Api status- " + js.getString("components.api"));
		return js;
	}

	public Response ping(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.ping).then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public JsonPath version(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.version).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("version -" + js.getString("version"));
		return js;
	}

	public Response lockOut(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.lockout).then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		log("Body ------> " + response.body().asString());

		apiVerification.responseCodeValidation(response, 200);
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

	public Response appointmentType(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.appointmentType).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);

		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");

		return response;
	}

	public Response books(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.books).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		log("Status Code- " + response.asString());
		apiVerification.responseCodeValidation(response, 200);

		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "resourceId");
		apiVerification.responseKeyValidation(response, "resourceName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "providerId");
		apiVerification.responseKeyValidation(response, "type");
		return response;
	}

	public Response cancellationReason(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPath.cancellationReason).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "reasonType");

		return response;
	}

	public Response insuranceCarrier(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).when().get(APIPath.apiPathGE.insurancecarrier).then().log().all().assertThat()
				.statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		log("Body ------ " + response.asString());
		apiVerification.responseCodeValidation(response, 200);
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

	public Response specialty(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.specialty).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");

		return response;
	}

	public Response locations(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).when().get(APIPath.apiPathGE.locations).then().log().all().extract().response();
		log("Status Code- " + response.getStatusCode());
		log("Body ------ " + response.asString());
		apiVerification.responseCodeValidation(response, 200);
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
		Response response = given().log().all().when().get(APIPath.apiPathGE.actuator).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		JsonPath js = new JsonPath(response.asString());
		apiVerification.responseTimeValidation(response);

		log("Self Link - " + js.getString("_links.self.href"));
		log("health Link - " + js.getString("_links.health.href"));
		log("health-path Link - " + js.getString("_links.health-path.href"));

		return response;
	}

	public Response lastseenProvider(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				RestAssured.given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.lastseenprovider).then().log().all().extract().response();
		log("Status Code- " + response.getStatusCode());

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		log("Last Seen Date Time - " + js.getString("lastSeenDateTime"));
		log("Resource Id - " + js.getString("resourceId"));
		log("Provider Availability - " + js.getString("providerAvailability"));

		return response;
	}

	public JsonPath appointmentStatus(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("appointmentId", "158").queryParam("patientId", "27565").queryParam("startDateTime", "1621247086").when()
				.get(APIPath.apiPathGE.AppointmentStatus).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("status of an Appointment -" + js.getString("status"));
		log("startDateTime- " + js.getString("startDateTime"));

		log("appointmentType Id- " + js.getString("appointmentTypeId"));

		return js;
	}

	public Response scheduleApptPatient(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.ScheduleAPPT).then().log().all().extract().response();

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();
		apiVerification.responseCodeValidation(response, 200);

		return response;
	}

	public Response cancelAppointment(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.cancelstatus).then().log().all()
				.assertThat().statusCode(200).log().all().extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Cancelled Appointment Status -" + js.getString("checkCancelAppointmentStatus"));
		log("Status Code- " + response.getStatusCode());

		return response;
	}

	public Response cancelAppointment(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("appointmentId", "158").when().headers(Header).when().get(APIPath.apiPathGE.cancelAppointment).then()
				.log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public Response cancelApptWithCancelReason(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.cancelApptWithCancelReason).then().log().all()
				.assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public String pastAppointments(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.pastAppointments).then().log().all().assertThat().statusCode(200)
						.body("book[0].resourceName", equalTo("Brown, Jennifer J.")).body("location[0].name", equalTo("River Oaks Main")).extract().response().asString();

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

	public String upcommingAppt(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.upcommingAppt).then().log().all().assertThat().statusCode(200)
						.body("book[0].resourceName", equalTo("Brown, Jennifer J.")).body("location[0].name", equalTo("River Oaks Main")).extract().response().asString();

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

	public Response preventScheduling(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).when().get(APIPath.apiPathGE.preventschedulingdate).then().log().all()
				.assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());

		return response;
	}

	public Response rescheduleAppt(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().headers(Header).body(b).post(APIPath.apiPathGE.rescheduleAppt).then().log().all().assertThat().statusCode(200).extract().response();

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response availableSlots(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(b).when().post(APIPath.apiPathGE.availableSlots).then().log().all().assertThat()
				.statusCode(200).extract().response();

		log("Response is as below" + response.asString());
		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "startDateTime");
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public JsonPath nextAvailableSlots(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(b).when().post(APIPath.apiPathGE.nextavailableslots).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}

}
