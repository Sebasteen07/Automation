// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequestNG extends BaseTestNGWebDriver {

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	public void setupRequestSpecBuilder(String baseurl) {

		RestAssured.baseURI = baseurl;

		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
		responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	}

	public JsonPath appointmentStatus(String practiceid) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("appointmentId", "00b971f3-b83f-42c2-ac31-9c748fe7bef3").queryParam("patientId", "49911")
				.queryParam("startDateTime", "1612522800").when().get(practiceid + APIPath.apiPath.Appointment_Status)
				.then().spec(responseSpec).log().all().spec(responseSpec).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("status of an Appointment -" + js.getString("status"));
		log("startDateTime- " + js.getString("startDateTime"));
		log("appointmentType Id- " + js.getString("appointmentTypeId"));
		return js;

	}

	public Response appointmentType(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when().get(practiceid + APIPath.apiPath.Apt_Type)
				.then().spec(responseSpec).log().all().extract().response();
		aPIVerification.responseCodeValidation(response, 200);

		aPIVerification.responseTimeValidation(response);

		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "name");

		return response;
	}

	public Response rescheduleApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().body(b)
				.post(practiceid + APIPath.apiPath.rescheduleAppt).then().log().all().assertThat().statusCode(200)
				.extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		return response;
	}

	public Response cancellationReasonT(String practiceid) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec).when()
				.get(practiceid + APIPath.apiPath.Cancel_Reason).then().spec(responseSpec).log().all()
				.spec(responseSpec).extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "reasonType");

		return response;

	}

	public JsonPath pastApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().body(b).when()
				.post(practiceid + APIPath.apiPath.Past_APPT).then().log().all().assertThat().statusCode(200).extract()
				.response();

		JsonPath js = new JsonPath(response.asString());

		log("Appointment Type -" + js.getString("appointmentTypes.name"));
		log("Resource Id- " + js.getString("book.resourceId"));

		log("Location Id- " + js.getString("location.name"));
		return js;

	}

	public JsonPath nextAvailableNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.next_Available).then().log().all().assertThat().statusCode(200)
				.extract().response();

		JsonPath js = new JsonPath(response.asString());
		return js;

	}

	public Response sampleMethod(String practiceid, String b) {

		Response response = given().when().body(b).log().all().when().post("/availableslots/3665").then()
				.spec(responseSpec).log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "name");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response locationList(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.Get_List_Location).then().log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "name");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response bookList(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.Get_List_Book).then().log().all().spec(responseSpec).extract()
				.response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "displayName");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response scheduleApptPatient(String practiceid, String b) {

		Response response = given().when().body(b).log().all().when().post(practiceid + APIPath.apiPath.ScheduleAPPT)
				.then().log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "displayName");
		apiVerification.responseKeyValidation(response, "categoryId");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public String scheduleApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).when().body(b).log().all().when()
				.post(practiceid + APIPath.apiPath.scheduleApptNG).then().spec(responseSpec).log().all().extract()
				.response();

		JSONObject js = new JSONObject(response.asString());

		String s = js.getString("id");

		log("Value of id - " + s);

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return s;
	}

	public Response upcommingApptNG(String practiceid, String b) {

		Response response = given().spec(requestSpec).when().body(b).log().all()
				.post(practiceid + APIPath.apiPath.upcommingApptNG).then().spec(responseSpec).log().all()
				.body("book[0].resourceName", equalTo("Saif PSS")).body("location[0].name", equalTo("PSS WLA"))
				.extract().response();

		JsonPath js = new JsonPath(response.toString());

		String apptName = js.getString("appointmentTypes.name");
		String resourceName = js.getString("book.resourceName");
		String locationName = js.getString("location.name");

		log("APPOINTMENT TYPE - " + apptName);
		log("resourceName - " + resourceName);
		log("locationName - " + locationName);

		return response;

	}

	public Response appointmenttypesRule(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.Get_List_Appointment).then().log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "displayName");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response availableSlots(String b, String practiceId) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec).body(b).when()
				.post(practiceId + "/availableslots").then().log().all().extract()
				.response();
		log("Response is as below" + response.asString());
		APIVerification apiVerification = new APIVerification();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public String getaccessToken(String practiceid) {

		Response response = given().spec(requestSpec).log().all().header("flowType", "LOGINLESS")
				.get(practiceid + "/accesstoken").then().spec(responseSpec).log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "accessToken");
		apiVerification.responseKeyValidation(response, "accessToken");
		apiVerification.responseTimeValidation(response);

		JsonPath jsonPathEvaluator = response.jsonPath();
		String access_Token = jsonPathEvaluator.get(practiceid + "accessToken");
		log("The Access Token is    " + jsonPathEvaluator.get(practiceid + "accessToken"));

		return access_Token;
	}

	public String accessToken(String practiceid) {

		Response response = given().get(practiceid).then().spec(responseSpec).log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "accessToken");
		apiVerification.responseKeyValidation(response, "accessToken");
		apiVerification.responseTimeValidation(response);

		JsonPath jsonPathEvaluator = response.jsonPath();
		String access_Token = jsonPathEvaluator.get(practiceid + "accessToken");
		log("The Access Token is    " + jsonPathEvaluator.get(practiceid + "accessToken"));

		return access_Token;
	}

	public Response cancelAppointmentGET(String practiceid) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("additionalFields", "Cancel")
				.queryParam("appointmentId", "8ce85b6a-268a-4ef1-9baa-446afd56367d").when()
				.get(practiceid + APIPath.apiPath.cancelAppointment).then().spec(responseSpec).log().all().extract()
				.response();

		return response;
	}

	public Response cancelAppointmentPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().spec(requestSpec)
				.queryParam("additionalFields", "Cancel")
				.queryParam("appointmentId", "8ce85b6a-268a-4ef1-9baa-446afd56367d").when().body(b)
				.post(practiceid + APIPath.apiPath.cancelAppointment).then().spec(responseSpec).log().all().extract()
				.response();

		return response;
	}

	public Response prerequisteappointmenttypesPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.prerequisteappointmenttypesNG).then().spec(responseSpec).log().all()
				.extract().response();

		return response;
	}

	public Response cancellationReason(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when()
				.get(practiceid + APIPath.apiPath.cancellationReason).then().spec(responseSpec).log().all().extract()
				.response();

		return response;
	}

	public Response careproviderAvailability(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.careprovideravailabilityNG).then().spec(responseSpec).log().all()
				.extract().response();
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());

		ParseJSONFile.getKey(jsonobject, "resourceId");
		ParseJSONFile.getKey(jsonobject, "nextAvailabledate");

		return response;
	}

	public Response insuranceCarrier(String practiceid) {

		Response response = given().when().spec(requestSpec).log().all()
				.get(practiceid + APIPath.apiPath.insurancecarrierNG).then().spec(responseSpec).log().all().extract()
				.response();

		aPIVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());

		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "name");
			i++;
		}

		return response;
	}

	public Response locations(String practiceid) {

		Response response = given().when().get(practiceid + APIPath.apiPath.locationsNG).then().spec(responseSpec).log()
				.all().extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());

		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "name");
			i++;
		}

		return response;
	}

	public Response demographics(String practiceid) {

		Response response = given().spec(requestSpec).log().all().queryParam("patientId", "50302").when()
				.get(practiceid + APIPath.apiPath.demographicNG).then().spec(responseSpec).log().all().extract()
				.response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "firstName");
		ParseJSONFile.getKey(jsonobject, "lastName");
		ParseJSONFile.getKey(jsonobject, "emailAddress");
		ParseJSONFile.getKey(jsonobject, "gender");

		return response;
	}

	public Response lockout(String practiceid) {

		Response response = given().when().get(practiceid + APIPath.apiPath.lockoutNG).then().spec(responseSpec).log()
				.all().extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

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

	public Response patientLastVisit(String practiceid) {

		Response response = given().queryParam("patientId", "50056").when()
				.get(practiceid + APIPath.apiPath.patientLastVisistNG).then().spec(responseSpec).log().all().extract()
				.response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "lastVisitDateTime");
		return response;
	}

	public Response patietStatus(String practiceid) {

		Response response = given().log().all().when().get(practiceid + APIPath.apiPath.patientStatusNG).then()
				.spec(responseSpec).log().all().extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONArray jsonarray = new JSONArray(response.asString());

		JSONObject jsonobject = new JSONObject();

		int i = 0;
		while (i < jsonarray.length()) {

			jsonobject = jsonarray.getJSONObject(i);
			ParseJSONFile.getKey(jsonobject, "key");
			ParseJSONFile.getKey(jsonobject, "value");
			i++;
		}

		return response;
	}

	public Response matchPatientPOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientMatchNG).then().spec(responseSpec).log().all().extract()
				.response();

		return response;
	}

	public Response patientRecordbyApptTypePOST(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientrecordbyapptypesNG).then().spec(responseSpec).log().all()
				.extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "patientRecord");
		ParseJSONFile.getKey(jsonobject, "appointmentTypeId");
		return response;
	}

	public Response searchpatient(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.searchpatientNG).then().spec(responseSpec).log().all().extract()
				.response();

		return response;
	}

	public Response patientrecordbyBooks(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.patientrecordbybooksNG).then().spec(responseSpec).log().all()
				.extract().response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "patientRecord");
		ParseJSONFile.getKey(jsonobject, "bookId");

		return response;
	}

	public Response lastseenProvider(String practiceid, String b) {

		Response response = given().spec(requestSpec).log().all().when().body(b)
				.post(practiceid + APIPath.apiPath.lastseenproviderNG).then().spec(responseSpec).log().all().extract()
				.response();

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "lastSeenDateTime");
		ParseJSONFile.getKey(jsonobject, "resourceId");

		return response;
	}

	public Response fetchNGBookList(String practiceid) {

		Response response = given().when().get(practiceid + APIPath.apiPath.booklistNG).then().spec(responseSpec).log()
				.all().assertThat().statusCode(200).extract().response();

		return response;
	}

	public Response fetchNGSpeciltyList(String practiceid) {

		Response response = given().spec(requestSpec).log().all().when().get(practiceid + APIPath.apiPath.specialtyNG)
				.then().spec(responseSpec).log().all().extract().response();

		return response;
	}
}
