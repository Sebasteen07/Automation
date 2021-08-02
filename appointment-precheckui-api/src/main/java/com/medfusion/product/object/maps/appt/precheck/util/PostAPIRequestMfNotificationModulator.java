package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfNotificationModulator extends BaseTestNGWebDriver {
	private static PostAPIRequestMfNotificationModulator postAPIRequest = new PostAPIRequestMfNotificationModulator();

	private PostAPIRequestMfNotificationModulator() {
	}

	public static PostAPIRequestMfNotificationModulator getPostAPIRequestMfNotificationModulator() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response sendNotifications(String payload, Map<String, String> Header) {
		log("Execute Post request for for Send Notifications");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("sendNotifications").then().log().all().extract()
				.response();
		return response;
	}

}
