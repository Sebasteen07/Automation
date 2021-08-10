package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfEmailNotifier extends BaseTestNGWebDriver {
	private static PostAPIRequestMfEmailNotifier postAPIRequest = new PostAPIRequestMfEmailNotifier();

	private PostAPIRequestMfEmailNotifier() {
	}

	public static PostAPIRequestMfEmailNotifier getPostAPIRequestMfEmailNotifier() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response sendEmail(String payload, Map<String, String> Header) {
		log("Execute Post request for Send Email");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("email/sendEmail").then().log().all().extract().response();
		return response;
	}

	public Response sendEmailWithoutSendEmail(String payload, Map<String, String> Header) {
		log("Execute Post request for Send Email");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().post("email")
				.then().log().all().extract().response();
		return response;
	}

	public Response handleCallback(String baseUrl, String userName, String password, String payload,
			Map<String, String> Header) {
		log("Execute Post request for Handle Callback");
		RestAssured.baseURI = baseUrl;
		Response response = given().log().all().auth().preemptive().basic(userName, password).body(payload).when()
				.headers(Header).post("sendGrid/handleCallback").then().log().all().extract().response();
		return response;
	}

}
