// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestMfNotificationsLog extends BaseTestNGWebDriver {
	private static PostAPIRequestMfNotificationsLog postAPIRequest = new PostAPIRequestMfNotificationsLog();

	private PostAPIRequestMfNotificationsLog() {
	}

	public static PostAPIRequestMfNotificationsLog getPostAPIRequestMfNotificationsLog() {
		return postAPIRequest;
	}

	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}

	public Response returnsLog(Map<String, String> Header, String subjUrn, String subjectId) {
		log("Execute Get request for Returns a log for the given subjectUrn and subjectId.");
		Response response = given().spec(requestSpec).log().all().queryParam("subj_urn", subjUrn)
				.queryParam("subj_id", subjectId).headers(Header).when().get().then().log().all().extract().response();
		return response;
	}

	public Response returnsLogWithoutSubjUrn(Map<String, String> Header, String subjectId) {
		log("Execute Get request for  Returns a log for the given subjectUrn and subjectId.");
		Response response = given().spec(requestSpec).log().all().queryParam("subj_id", subjectId).headers(Header)
				.when().get().then().log().all().extract().response();
		return response;
	}

	public Response returnsLogWithoutSubjId(Map<String, String> Header, String subjUrn) {
		log("Execute Get request for  Returns a log for the given subjectUrn and subjectId.");
		Response response = given().spec(requestSpec).log().all().queryParam("subj_urn", subjUrn).headers(Header).when()
				.get().then().log().all().extract().response();
		return response;
	}

	public Response returnsLogPost(String payload, Map<String, String> Header) {
		log("Execute Post request for Returns a log for the given subjectUrn and subjectId");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload).post("search")
				.then().log().all().extract().response();
		return response;
	}

	public Response deleteLog(String payload, Map<String, String> Header) {
		log("Execute Delete request for Deletes logs for the given log requests.");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload).delete("delete")
				.then().log().all().extract().response();
		return response;
	}

	public Response createsStatus(String payload, Map<String, String> Header, String logId, String notificationId) {
		log("Execute Post request for Creates Status");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("v1/logs/" + logId + "/notifications/" + notificationId + "/statuses").then().log().all()
				.extract().response();
		return response;
	}

	public Response createsStatusWithoutNotifId(String payload, Map<String, String> Header, String logId) {
		log("Execute Post request for Creates Status");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("v1/logs/" + logId + "/notifications/statuses").then().log().all().extract().response();
		return response;
	}

	public Response createsNotification(String payload, Map<String, String> Header, String logId,
			String notificationId) {
		log("Execute Put request for Creates Notification");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload)
				.post("v1/logs/" + logId + "/notifications/" + notificationId + "/statuses").then().log().all()
				.extract().response();
		return response;
	}

	public Response createsLogs(String payload, Map<String, String> Header) {
		log("Execute Post request for Creates Status");
		Response response = given().spec(requestSpec).log().all().headers(Header).when().body(payload).post().then()
				.log().all().extract().response();
		return response;
	}

}
