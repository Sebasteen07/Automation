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
		Response response = given().log().all().when().get(APIPath.apiPathGE.HEALTH_CHECK).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("version of health check -" + js.getString("version"));
		log("Database Name - " + js.getString("components.DatabaseName"));
		log("Api status- " + js.getString("components.api"));
		return js;
	}

	public Response ping(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.PING).then().log().all().assertThat().statusCode(200).extract().response();

		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public JsonPath version(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.VERSION).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("version -" + js.getString("version"));
		return js;
	}

	public Response lockOut(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.LOCKOUT).then().log().all().assertThat().statusCode(200).extract().response();

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
		Response response = given().log().all().when().get(APIPath.apiPathGE.APPOINTMENT_TYPE).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);

		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");

		return response;
	}

	public Response books(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.BOOKS).then().log().all().assertThat().statusCode(200).extract().response();
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
		Response response = RestAssured.given().when().headers(Header).when().get(APIPath.apiPathGE.INSURANCE_CARRIER).then().log().all().assertThat()
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
		Response response = given().log().all().when().get(APIPath.apiPathGE.SPECIALITY).then().log().all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");

		return response;
	}

	public Response locations(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).when().get(APIPath.apiPathGE.LOCATIONS).then().log().all().extract().response();
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
		Response response = given().log().all().when().get(APIPath.apiPathGE.ACTUATOR).then().log().all().assertThat().statusCode(200).extract().response();
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
				RestAssured.given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.LAST_SEEN_PROVIDER).then().log().all().extract()
						.response();
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
		Response response = given().log().all().queryParam("appointmentId", "158").queryParam("patientId", "26854").queryParam("startDateTime", "1621247086").when()
				.get(APIPath.apiPathGE.APPOINTMENT_STATUS).then().log().all().assertThat().statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("status of an Appointment -" + js.getString("status"));
		log("startDateTime- " + js.getString("startDateTime"));

		log("appointmentType Id- " + js.getString("appointmentTypeId"));

		return js;
	}

	public Response scheduleApptPatient(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.SCHEDUL_APPT).then().log().all().extract().response();

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();
		apiVerification.responseCodeValidation(response, 200);

		return response;
	}

	public Response cancelAppointment(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.CANCEL_STATUS).then().log().all()
				.assertThat().statusCode(200).log().all().extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("Cancelled Appointment Status -" + js.getString("checkCancelAppointmentStatus"));
		log("Status Code- " + response.getStatusCode());

		return response;
	}

	public Response cancelAppointment(String baseurl, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("appointmentId", "158").when().headers(Header).when().get(APIPath.apiPathGE.CANCEL_APPOINTMENT).then()
				.log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public Response cancelApptWithCancelReason(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().headers(Header).body(b).when().post(APIPath.apiPathGE.CANCEL_APPT_WITH_CANCEL_REASON).then().log().all()
				.assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		return response;
	}

	public String pastAppointments(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.PAST_APPOINTMENTS).then().log().all().assertThat().statusCode(200)
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

	public String upcomingAppt(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		String response =
				given().when().headers(Header).body(b).log().all().when().post(APIPath.apiPathGE.UPCOMING_APPT).then().log().all().assertThat().statusCode(200)
						.body("book[0].resourceName", equalTo("Gathani, Pooja")).body("location[0].name", equalTo("River Oaks Main")).extract().response().asString();

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
		Response response = RestAssured.given().log().all().when().headers(Header).when().get(APIPath.apiPathGE.PREVENT_SCHEDULING_DATE).then().log().all()
				.assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());

		return response;
	}

	public Response rescheduleAppt(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response =
				given().log().all().headers(Header).body(b).post(APIPath.apiPathGE.RESCHEDUL_APPT).then().log().all().assertThat().statusCode(200).extract().response();

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		return response;
	}

	public Response availableSlots(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().headers(Header).body(b).when().post(APIPath.apiPathGE.AVAILABLE_SLOTS).then().log().all().assertThat()
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
		Response response = given().log().all().headers(Header).body(b).when().post(APIPath.apiPathGE.NEXT_AVAILABLE_SLOTS).then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		return js;
	}
	public Response addPatientPost(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(APIPath.apiPathGE.ADDPATIENTGE).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		APIVerification apiVerification = new APIVerification();
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "id");
		ParseJSONFile.getKey(jsonobject, "firstName");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	public Response careProviderPost(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(APIPath.apiPathGE.CAREPROVIDERGE).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		APIVerification apiVerification = new APIVerification();
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "resourceId");
		ParseJSONFile.getKey(jsonobject, "nextAvailabledate");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response demographicsGET(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().queryParam("patientId", "26854").log().all().when().get(APIPath.apiPathGE.DEMOGRAPHICSGE).then().log()
				.all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "id");
		return response;
	}
	public Response healthOperationGET(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.HEALTHOPERATIONGE).then().log()
				.all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "status");
		return response;
	}
	
	public Response matchPatientPost(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(APIPath.apiPathGE.MATCHPATIENTGE).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		APIVerification apiVerification = new APIVerification();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	public Response patientLastVisit(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.PATIENTLASTVISITGE).then().log()
				.all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "lastVisitDateTime");
		return response;
	}
	
	public Response preReqAppointmentTypes(String baseurl) {
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().when().get(APIPath.apiPathGE.PREREQUISTEAPPOINTMENTTYPESGETGE).then().log()
				.all().assertThat().statusCode(200).extract().response();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		return response;
	}
	
	public Response searchPatientPost(String baseurl, String b, Map<String, String> Header) {
		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().log().all().when().headers(Header).body(b).when()
				.post(APIPath.apiPathGE.SEARCHPATIENTGE).then().log().all().assertThat().statusCode(200).extract().response();
		log("Status Code- " + response.getStatusCode());
		APIVerification apiVerification = new APIVerification();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	
	
}
