package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfLogoService extends BaseTestNGWebDriver {
	private static PostAPIRequestMfLogoService postAPIRequest = new PostAPIRequestMfLogoService();

	private PostAPIRequestMfLogoService() {
	}

	public static PostAPIRequestMfLogoService getPostAPIRequestMfLogoService() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response getLogoById(Map<String, String> Header, String logoId) {
		log("Execute Get request for get Logo By Id.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("logo/" + logoId).then().log().all().extract().response();
		return response;
	}

	public Response getLogoByIdWithoutLogoId(Map<String, String> Header) {
		log("Execute Get request for get Logo By Id.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("logo/").then().log().all()
				.extract().response();
		return response;
	}

	public Response getLogoByPracticeId(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Logo By Practice Id.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("logo/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response getLogoWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for Logo By Practice Id.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("logo/practice/")
				.then().log().all()
				.extract().response();
		return response;
	}

	public Response getLogoInfo(Map<String, String> Header, String practiceId) {
		log("Execute Get request get Logo Info.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("logo/practice/" + practiceId + "/info").then().log().all().extract().response();
		return response;
	}

	public Response getLogoInfoWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for get Logo Info.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("logo/practice/info")
				.then().log().all().extract().response();
		return response;
	}

	public Response getLogoInfoPost(Map<String, String> Header, String logoId) {
		log("Execute Post request for get Logo Info.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.post("logo/" + logoId + "/info")
				.then().log().all().extract().response();
		return response;
	}

	public Response getLogoInfoPostWithoutLogoId(Map<String, String> Header) {
		log("Execute Post request for get Logo Info.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().post("logo/info").then().log()
				.all().extract().response();
		return response;
	}

	public Response updateLogoById(String logo, Map<String, String> Header, String logoId) {
		log("Execute Put request for update Logo By Id.");
		Response response = given().spec(requestSpec).contentType("multipart/form-data")
			    .multiPart("document", new File(logo)).log().all().headers(Header).when()
				.put("logo/" + logoId).then()
				.log().all().extract().response();
		return response;
	}

	public Response updateLogoByIdWithoutLogoId(String payload, Map<String, String> Header) {
		log("Execute put request for update Logo By Id.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when().put("logo/")
				.then().log().all()
				.extract().response();
		return response;
	}

	public Response updateLogoByPracticeId(String logo, Map<String, String> Header, String practiceId) {
		log("Execute Put request for update Logo By Practice.");
		Response response = given().spec(requestSpec).contentType("multipart/form-data")
			    .multiPart("document", new File(logo)).log().all().headers(Header).when()
				.put("logo/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response updateLogoByPracticeIdWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute put request for update Logo By Practice.");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("logo/practice/").then().log().all().extract().response();
		return response;
	}

	public Response uploadLogo(String logo, Map<String, String> Header, String practiceId) {
		log("Execute Post request for upload Logo");
		Response response = given().spec(requestSpec).contentType("multipart/form-data")
			    .multiPart("document", new File(logo)).log().all().headers(Header).when()
				.post("logo/practice/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response uploadLogoWithoutPracticeId(String payload, Map<String, String> Header) {
		log("Execute Post request for upload Logo");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.post("logo/practice/").then().log().all().extract().response();
		return response;
	}
}
