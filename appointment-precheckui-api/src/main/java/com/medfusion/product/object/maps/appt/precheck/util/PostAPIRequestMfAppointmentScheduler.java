package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class PostAPIRequestMfAppointmentScheduler extends BaseTestNGWebDriver {

	private static PostAPIRequestMfAppointmentScheduler postAPIRequest = new PostAPIRequestMfAppointmentScheduler();

	private PostAPIRequestMfAppointmentScheduler() {
	}

	public static PostAPIRequestMfAppointmentScheduler getPostAPIRequestMfAppointmentScheduler() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}
	
	public Response getAppointmentAptId(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute GET request for appointment_appId");
		Response response = given().when().headers(Header).log().all().when()
				.get("practice/" + practiceId + "/patient/" + PatientId + "/appointment/" + Appointmentid).then()
				.log().all().extract().response();
		return response;
	}

	public Response getDELETETAppointment(String practiceId, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute DELETE call for Appointment");
		Response response = given().when().
				queryParam("source", "PSS").
				headers(Header).log().all().when()
				.delete("practice/" + practiceId + "/integration/9/patient/" + PatientId + "/appointment/" + Appointmentid).then()
				.log().all().extract().response();
		return response;

	}
	
	public Response aptPutAppointment(String practiceId, String payload, Map<String, String> Header, String PatientId,
			String Appointmentid) {
		log("Execute PUT request for Forms");
		Response response = given().when().
				queryParam("oldPmAppointmentId", Appointmentid).
				queryParam("source", "PSS").
				headers(Header).body(payload).log().all().when()
				.put("practice/" + practiceId + "/patient/" + PatientId + "/appointment/" + Appointmentid)
				.then().log().all().extract().response();
		return response;
	}


}