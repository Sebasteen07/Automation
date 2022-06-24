package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfAppointmentTypes extends BaseTestNGWebDriver {
	private static PostAPIRequestMfAppointmentTypes postAPIRequest = new PostAPIRequestMfAppointmentTypes();

	private PostAPIRequestMfAppointmentTypes() {
	}

	public static PostAPIRequestMfAppointmentTypes getPostAPIRequestMfAppointmentTypes() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getAppointmentTypes(String practiceId,Map<String, String> Header) {
		log("Execute GET request for Appointment types");
		Response response = given().when().
				queryParam("includeInactive", true).
				queryParam("practiceId", practiceId).
				headers(Header).log().all().when()
				.get("appointmentTypes").then()
				.log().all().extract().response();
		return response;
	}
	
	public Response getAppointmentTypesUuid(String uuid,Map<String, String> Header) {
		log("Execute GET request for Appointment types");
		Response response = given().when().headers(Header).log().all().when()
				.get("appointmentTypes/" + uuid).then()
				.log().all().extract().response();
		return response;
	}
	
	public Response getAppointmentTypesIncorrectUuid(String uuid,Map<String, String> Header) {
		log("Execute GET request for Appointment types");
		Response response = given().when().headers(Header).log().all().when()
				.get("appointmentTypes/" + uuid).then()
				.log().all().extract().response();
		return response;
	}
	
	public Response aptpostAppointmentTypes(String payload, Map<String, String> Header,
			 String PracticeId) {
		log("Execute POST request for appointmentTypes");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().post("appointmentTypes")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response aptpostUpdateAppointmentTypes(String integrationId, String payload, Map<String, String> Header,
			 String PracticeId) {
		log("Execute POST request for Update appointmentType");
		Response response = given().when().queryParam("integrationId", integrationId).queryParam("practiceId", PracticeId).
				headers(Header).body(payload).log().all().when().post("appointmentTypes/update")
				.then().log().all().extract().response();
		return response;
	}
	
	public Response aptPutUpdateAppointmentTypesUuid(String uuid, String payload, Map<String, String> Header) {
		log("Execute POST request for Update appointmentType UUID");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().put("appointmentTypes/" +uuid)
				.then().log().all().extract().response();
		return response;
	}
	public Response aptPostAppointmentTypes(String baseUrl,String payload, Map<String, String> Header,
			 String PracticeId) {
		RestAssured.baseURI=baseUrl;
		log("Execute POST request for appointmentTypes");
		Response response = given()
				.when().headers(Header).body(payload).log().all().when().post("appointmentTypes")
				.then().log().all().extract().response();
		return response;
	}
}

