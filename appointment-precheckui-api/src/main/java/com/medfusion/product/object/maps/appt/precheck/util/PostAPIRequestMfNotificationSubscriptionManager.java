package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfNotificationSubscriptionManager extends BaseTestNGWebDriver {
	private static PostAPIRequestMfNotificationSubscriptionManager postAPIRequest = new PostAPIRequestMfNotificationSubscriptionManager();

	private PostAPIRequestMfNotificationSubscriptionManager() {
	}

	public static PostAPIRequestMfNotificationSubscriptionManager getPostAPIRequestMfNotificationSubscriptionManager() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response deleteAllSubscriptionDataUsingEmailId(String baseurl,Map<String, String> Header, String emailId) {
		RestAssured.baseURI = baseurl;
		log("Execute Delete request for Delete all subscription data using Email ID");
		Response response = given().log().all().headers(Header).when().delete("data/id/" + emailId)
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteAllSubsDataWithoutEmailId(Map<String, String> Header) {
		log("Execute Delete request for Delete all subscription data using Email ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().delete("data/id/").then().log()
				.all().extract().response();
		return response;
	}

	public Response deleteAllSubsDataUsingEmailIdAndTypeId(Map<String, String> Header, String emailId,
			String practiceId) {
		log("Execute Delete request for Delete all subscription data Email ID and type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/" + emailId + "/resource/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response deleteAllSubsDataWithoutEmailId(Map<String, String> Header, String practiceId) {
		log("Execute Delete request for Delete all subscription data Email ID and type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/resource/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response deleteAllSubsDataWithoutPracticeId(Map<String, String> Header, String emailId) {
		log("Execute Delete request for Delete all subscription data Email ID and type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/" + emailId + "/resource/").then().log().all().extract().response();
		return response;
	}

	public Response deleteSubsDataUsingEmailIdAndResourceId(Map<String, String> Header, String emailId,
			String practiceId, String type) {
		log("Execute Delete request for Delete subscription data using Email ID resource identifier and type");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/" + emailId + "/resource/" + practiceId + "/type/" + type).then().log().all().extract()
				.response();
		return response;
	}

	public Response deleteSubsDataWithoutApptType(Map<String, String> Header, String emailId, String practiceId) {
		log("Execute Delete request for Delete subscription data using ID resource identifier and type");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/" + emailId + "/resource/" + practiceId + "/type/").then().log().all().extract()
				.response();
		return response;
	}

	public Response deleteSubsDataWithoutPracticeId(Map<String, String> Header, String emailId, String type) {
		log("Execute Delete request for Delete subscription data using ID resource identifier and type");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.delete("data/id/" + emailId + "/resource/type/" + type).then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsDataId(Map<String, String> Header, String emailId, String practiceId) {
		log("Execute Get request for Retrieve all subscription data ID and the resource identifier");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/" + emailId + "/resource/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsDataWithoutResourceId(Map<String, String> Header, String emailId) {
		log("Execute Get request for Retrieve all subscription data ID and the resource identifier");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/" + emailId + "/resource/").then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsDataWithoutEmailId(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Retrieve all subscription data ID and the resource identifier");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/resource/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response retrieveSubsDataUsingEmailIdAndResourceId(Map<String, String> Header, String emailId,
			String practiceId, String type) {
		log("Execute Get request for Retrieve subscription data using ID resource identifier.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/" + emailId + "/resource/" + practiceId + "/type/" + type).then().log().all().extract()
				.response();
		return response;
	}

	public Response retrieveSubsDataWithoutApptType(Map<String, String> Header, String emailId, String practiceId) {
		log("Execute Get request for Retrieve subscription data using ID resource identifier.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/" + emailId + "/resource/" + practiceId + "/type/").then().log().all().extract()
				.response();
		return response;
	}

	public Response retrieveSubsDataWithoutEmailId(Map<String, String> Header, String practiceId, String type) {
		log("Execute Get request for Retrieve subscription data using ID resource identifier.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/id/resource/" + practiceId + "/type/" + type).then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsDataUsingTypeId(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Retrieve all subscription data using type ID.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/resource/" + practiceId).then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsDataWithoutPracticeId(Map<String, String> Header) {
		log("Execute Get request for Retrieve all subscription data using type ID.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().get("data/resource/").then()
				.log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsnDataUsingTypeId(Map<String, String> Header, String practiceId, String type) {
		log("Execute Get request for Retrieve all subscription data using type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/resource/" + practiceId + "/type/" + type).then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsnDataWithoutApptType(Map<String, String> Header, String practiceId) {
		log("Execute Get request for Retrieve all subscription data using type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/resource/" + practiceId + "/type/").then().log().all().extract().response();
		return response;
	}

	public Response retrieveAllSubsnDataWithoutPracticeId(Map<String, String> Header, String type) {
		log("Execute Get request for Retrieve all subscription data using type ID");
		Response response = given().spec(requestSpec).log().all().headers(Header).when()
				.get("data/resource/type/" + type).then().log().all().extract().response();
		return response;
	}

	public Response saveSubsDataWithApptType(Map<String, String> Header, String Payload) {
		log("Execute Post request for Save subscription data with Appointment type");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(Payload).when()
				.post("data").then().log().all().extract().response();
		return response;
	}

	public Response saveAllSubsDataWithApptType(Map<String, String> Header, String Payload) {
		log("Execute Post request for Save all subscription data with Appointment type");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(Payload).when().post("data/all")
				.then().log().all().extract().response();
		return response;
	}
}