package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestNotificationDisplayService extends BaseTestNGWebDriver {
	private static PostAPIRequestNotificationDisplayService postAPIRequest = new PostAPIRequestNotificationDisplayService();

	private PostAPIRequestNotificationDisplayService() {
	}

	public static PostAPIRequestNotificationDisplayService getPostAPIRequestNotificationDisplayService() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response createPrecheckNotification(String payload, Map<String, String> Header) {
		log("Execute Post request for create precheck notification");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("notification/precheck").then().log().all().extract().response();
		return response;
	}

	public Response createScheduleNotification(String payload, Map<String, String> Header) {
		log("Execute Post request for create Schedule Notification");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("notification/schedule").then().log().all().extract().response();
		return response;
	}

	public Response GetNotificationById(Map<String, String> Header, String id) {
		log("Execute Get request for get Notification By Id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("notification/" + id)
				.then().log().all().extract().response();
		return response;
	}

	public Response GetNotifWithoutIdForPrecheck(Map<String, String> Header) {
		log("Execute Get request for get Notification By Id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("notification/").then()
				.log().all().extract().response();
		return response;
	}

	public Response GetNotifWithoutIdForSchedule(Map<String, String> Header) {
		log("Execute Get request for get Notification By Id");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("notification/").then()
				.log().all().extract().response();
		return response;
	}

}
