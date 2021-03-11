// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static io.restassured.RestAssured.given;
import java.util.Map;

import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PostAPIRequest extends BaseTestNGWebDriver {

	RequestSpecification req = new RequestSpecBuilder().setContentType("application/json")
			.setBaseUri("https://dev3-pss.dev.medfusion.net/pss-patient-modulator/v1").build();

	ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType("application/json")
			.build();
	
	APIVerification aPIVerification= new APIVerification();
	
	public Response sampleMethod02(String baseurl, String b, Map<String, String> Header) {

		Response response = given().log().all().queryParam("appointmentId", "4d63a3fd-a2d1-41bd-a64e-a64d4107b14a").queryParams("patientId","3665").queryParam("startDateTime", "02/17/2021 10:00:00").spec(req).headers(Header).get("/24249/appointmentstatus")
				.then().log().all().spec(res).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log(response.asString());
		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "slotTime");
		ParseJSONFile.getKey(jsonobject, "SlotId");
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	public JsonPath appointmentStatus(String baseurl,Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		Response response = 
				given().log().all()
				.queryParam("appointmentId", "00b971f3-b83f-42c2-ac31-9c748fe7bef3")
				.queryParam("patientId", "49911")
				.queryParam("startDateTime", "1612522800")
				.when().get(APIPath.apiPath.Appointment_Status)
				.then().log().all().assertThat()
				.statusCode(200).extract().response();

		JsonPath js = new JsonPath(response.asString());

		log("status of an Appointment -" + js.getString("status"));
		log("startDateTime- " + js.getString("startDateTime"));

		log("appointmentType Id- " + js.getString("appointmentTypeId"));
		return js;

	}
	
	public Response appointmentType(String baseurl) {

		RestAssured.baseURI = baseurl;

		Response response = given().log().all()
				.when().get(APIPath.apiPath.Apt_Type)
				.then().log().all()
				.assertThat()
				.statusCode(200).extract().response();
		aPIVerification.responseCodeValidation(response, 200);
		
		aPIVerification.responseTimeValidation(response);
				
		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "name");
			
		return response;

	}
	public Response cancellationReason(String baseurl) {

		RestAssured.baseURI = baseurl;

		Response response = given().log().all()
				.when().get(APIPath.apiPath.Cancel_Reason)
				.then().log().all().assertThat()
				.statusCode(200).extract().response();
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		aPIVerification.responseKeyValidation(response,"displayName");
		aPIVerification.responseKeyValidation(response,"reasonType");
		
		return response;

	}
	
	public JsonPath pastApptNG(String baseurl,String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		Response response = 
				given().log().all()
				.headers(Header).body(b)
				.when().post(APIPath.apiPath.Past_APPT)
				.then().log().all().assertThat()
				.statusCode(200).extract().response();
		
		JsonPath js = new JsonPath(response.asString());
		
		log("Appointment Type -"+js.getString("appointmentTypes.name"));
		log("Resource Id- "+js.getString("book.resourceId"));
		
		log("Location Id- "+js.getString("location.name"));
		return js;

	}
	
	public JsonPath nextAvailableNG(String baseurl,String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;

		Response response = 
				given().log().all()
				.headers(Header).body(b)
				.when().post(APIPath.apiPath.next_Available)
				.then().log().all().assertThat()
				.statusCode(200).extract().response();
		
		JsonPath js = new JsonPath(response.asString());		
		return js;

	}

	public Response sampleMethod01(String baseurl, String b, Map<String, String> Header) {

		Response response = given().log().all().spec(req).headers(Header).body(b).post("/24249/availableslots/3665")
				.then().log().all().spec(res).extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log(response.asString());
		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "slotTime");
		ParseJSONFile.getKey(jsonobject, "SlotId");
		apiVerification.responseTimeValidation(response);
		return response;
	}

	public Response sampleMethod(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().when().headers(Header).body(b).log().all().when().post("/availableslots/3665").then().log().all()
				.extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "name");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	public Response availableSlots(String baseurl, String b, Map<String, String> Header) {

		RestAssured.baseURI = "http://d3-ns-app02.dev.medfusion.net:8083/pss-ng-adapter";
		
		Response response =given().log().all().headers(Header)
							.body(b)
							.when().post("/24249/availableslots")
							.then().log().all()
							.extract().response();
		log("Response is as below"+response.asString());

		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "availableSlots");
		apiVerification.responseTimeValidation(response);
		return response;
	}
	
	public String getaccessToken(String baseurl) {

		RestAssured.baseURI = baseurl;
		Response response = RestAssured.given().get(baseurl).then().log().all().extract().response();

		JSONObject jsonobject = new JSONObject(response.asString());

		log("Status Code- " + response.getStatusCode());

		APIVerification apiVerification = new APIVerification();

		apiVerification.responseCodeValidation(response, 200);
		ParseJSONFile.getKey(jsonobject, "accessToken");
		apiVerification.responseKeyValidation(response, "accessToken");
		apiVerification.responseTimeValidation(response);

		JsonPath jsonPathEvaluator = response.jsonPath();
		String access_Token = jsonPathEvaluator.get("accessToken");
		log("The Access Token is    " + jsonPathEvaluator.get("accessToken"));

		return access_Token;
	}

}
