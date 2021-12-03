package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfisAppointmentService extends BaseTestNGWebDriver {
	private static PostAPIRequestMfisAppointmentService postAPIRequest = new PostAPIRequestMfisAppointmentService();

	private PostAPIRequestMfisAppointmentService() {
	}

	public static PostAPIRequestMfisAppointmentService getPostAPIRequestMfisAppointmentService() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getApptService(Map<String, String> Header, String practiceId, String intgrationId, String emrId,
			String apptId) {
		log("Execute Get request for Appointment services");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().get("appointments/appointmentDetail/practice/"
						+ practiceId + "/integration/" + intgrationId + "/emr/" + emrId + "/appointment/" + apptId)
				.then().log().all().extract().response();
		return response;
	}

	public Response getApptServiceWithoutIntgrationId(Map<String, String> Header, String practiceId, String emrId,
			String apptId) {
		log("Execute Get request for Appointment services");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().get("appointments/appointmentDetail/practice/"
						+ practiceId + "/integration/emr/" + emrId + "/appointment/" + apptId)
				.then().log().all().extract().response();
		return response;
	}

	public Response getApptServiceWithoutApptId(Map<String, String> Header, String practiceId, String emrId,
			String intgrationId) {
		log("Execute Get request for Appointment services");
		Response response = given()
				.spec(requestSpec).log().all().headers(Header).when().get("appointments/appointmentDetail/practice/"
						+ practiceId + "/integration/" + intgrationId + "/emr/" + emrId + "/appointment/")
				.then().log().all().extract().response();
		return response;
	}

	public Response apptServicePost(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Appointment services");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response apptServiceForCountPost(String payload, Map<String, String> Header, String practiceId,
			String count) {
		log("Execute Post request for Appointment services for count");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId + "/appointments/count/" + count).then().log().all()
				.extract().response();
		return response;
	}

	public Response apptServiceWithoutCount(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Appointment services for count");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId + "/appointments/count/").then().log().all().extract()
				.response();
		return response;
	}

	public Response apptServiceForBroadcastAppts(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Appointment services for Broadcast Appointments");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId + "/broadcastappointments").then().log().all().extract()
				.response();
		return response;
	}

	public Response apptServiceForCurbsidePage(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Appointment services for Curbside Page");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId + "/curbsidepage").then().log().all().extract().response();
		return response;
	}

	public Response apptServicePut(String payload, Map<String, String> Header, String practiceId, String flag) {
		log("Execute put request for Appointment services");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.put("appointments/practice/" + practiceId + "/mark/" + flag).then().log().all().extract().response();
		return response;
	}

	public Response apptServiceDelete(String payload, Map<String, String> Header, String practiceId) {
		log("Execute delete request for Appointment services");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.delete("appointments/practice/" + practiceId + "/delete").then().log().all().extract().response();
		return response;
	}

	public Response apptServiceCountPost(String payload, Map<String, String> Header, String practiceId) {
		log("Execute Post request for Appointment services for count");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("appointments/practice/" + practiceId + "/appointments").then().log().all().extract().response();
		return response;
	}
}
