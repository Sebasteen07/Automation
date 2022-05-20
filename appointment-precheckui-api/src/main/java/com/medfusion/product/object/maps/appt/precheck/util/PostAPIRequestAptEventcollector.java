package com.medfusion.product.object.maps.appt.precheck.util;



import static io.restassured.RestAssured.given;

import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAPIRequestAptEventcollector extends BaseTestNGWebDriver {
	private static PostAPIRequestAptEventcollector postAPIRequest = new PostAPIRequestAptEventcollector();

	private PostAPIRequestAptEventcollector() {
	}

	public static PostAPIRequestAptEventcollector getPostAPIRequestAptEventcollector() {
		return postAPIRequest;
	}
	
	APIVerification apiVerification = new APIVerification();
	public static RequestSpecification requestSpec;

	public void setupRequestSpecBuilder(String baseurl) {
		RestAssured.baseURI = baseurl;
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
	}
	
	public Response aptEventCollector(String payload, Map<String, String> Header) {
		log("Execute PUT request for  event collector");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("event/eventType/TEXT_SENT").then().log().all().extract().response();
		return response;
	}
	
	public Response aptEventCollectorInvalidData(String payload, Map<String, String> Header) {
		log("Execute PUT request for  event collector");
		Response response = given().spec(requestSpec).log().all().headers(Header).body(payload).when()
				.put("event/eventType/TEXT_SENT").then().log().all().extract().response();
		return response;
	}
	
	public Response aptPostDailyAggregation(String practiceId,Map<String, String> Header, String StartDate, String EndDate) {
		log("Execute Post  request for daily aggregation resource");
		Response response = given().when().queryParam("startDate", StartDate).queryParam("endDate", StartDate).
				headers(Header).log().all().when()
				.post("aggregation/daily").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptPostDailyAggregationIncorrectTime(String practiceId,Map<String, String> Header) {
		log("Execute Post  request for daily aggregation resource");
		Response response = given().when().
				queryParam("startDate", "2021-06-1714:35:09.17").
				queryParam("endDate", "2021-06-17T4:35:09.179Z").
				headers(Header).log().all().when()
				.post("aggregation/daily").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptPostDailyAggregationTimeRange(String practiceId,Map<String, String> Header) {
		log("Execute Post  request for daily aggregation resource");
		Response response = given().when().
				queryParam("startDate", "2021-06-19T09:10:18.709Z").
				queryParam("endDate", "2021-06-17T09:10:18.709Z").
				headers(Header).log().all().when()
				.post("aggregation/daily").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptPostLongtermAggregation(String practiceId,Map<String, String> Header, String datelongTerm) {
		log("Execute Post  request for daily aggregation resource");
		Response response = given().when().
				queryParam("date", datelongTerm).
				headers(Header).log().all().when()
				.post("aggregation/longterm").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptPostLongtermAggregationIncorrectDate(String practiceId,Map<String, String> Header) {
		log("Execute Post  request for daily aggregation resource");
		Response response = given().when().
				queryParam("date", "2021-006-1833").
				headers(Header).log().all().when()
				.post("aggregation/longterm").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEvent(String practiceId,Map<String, String> Header, String timeRangeStart, String timeRangeEnd) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", timeRangeStart).
				queryParam("timeRangeEnd", timeRangeEnd).
				headers(Header).log().all().when()
				.get("event").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventIncorrectMonth(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-05-16T10:02:31.343Z").
				queryParam("timeRangeEnd", "2021-07-16T10:02:31.343Z").
				headers(Header).log().all().when()
				.get("event").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptGETEventIncorrectDay(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-06-16T10:02:31.343Z").
				queryParam("timeRangeEnd", "2021-06-17T10:02:31.343Z").
				headers(Header).log().all().when()
				.get("event").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptGETEventIncorrectHour(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-07-18T12:02:31.343Z").
				queryParam("timeRangeEnd", "2021-07-16T10:02:31.343Z").
				headers(Header).log().all().when()
				.get("event").then().log().all()
				.extract().response();
		return response;
	}

	public Response aptGETEventType(String practiceId,Map<String, String> Header, String timeRangeStart, String timeRangeEnd) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", timeRangeStart).
				queryParam("timeRangeEnd", timeRangeEnd).
				headers(Header).log().all().when()
				.get("event/practiceId/" + practiceId + "/eventType/TEXT_SENT").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventTypeIncorrectDateFormat(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-06-1709:45:13.854Z").
				queryParam("timeRangeEnd", "2021-06-17T09:50:13.85").
				headers(Header).log().all().when()
				.get("event/practiceId/"+ practiceId + "/eventType/TEXT_SENT").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventTypeIncorrectStartHour(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-06-17T10:02:47.890Z").
				queryParam("timeRangeEnd", "2021-06-17T12:02:47.890Z").
				headers(Header).log().all().when()
				.get("event/practiceId/"+ practiceId +"/eventType/TEXT_SENT").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventPracticeId(String practiceId,Map<String, String> Header,String timeRangeStart, String timeRangeEnd) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", timeRangeStart).
				queryParam("timeRangeEnd", timeRangeEnd).
				headers(Header).log().all().when()
				.get("event/practiceId/" +practiceId).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventPracticeIdIncorrectDate(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-06-17T12:02:31.343Z").
				queryParam("timeRangeEnd", "2021-06-17T10:02:31.343Z").
				headers(Header).log().all().when()
				.get("event/practiceId/" +practiceId).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventId(String practiceId,Map<String, String> Header, String EventId,String timeRangeStart, String timeRangeEnd) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", timeRangeStart).
				queryParam("timeRangeEnd", timeRangeEnd).
				headers(Header).log().all().when()
				.get("event/practiceId/" +practiceId +"/eventType/APPOINTMENT_SCHEDULED_EVENT/eventId/" +EventId).then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETEventIdIncorrectData(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().
				queryParam("timeRangeStart", "2021-07-21T14:36:00.890Z").
				queryParam("timeRangeEnd", "2021-07-21T14:36:00.890Z").
				headers(Header).log().all().when()
				.get("event/practiceId/--1/eventType/TEXT_SENT/eventId/182266").then().log().all()
				.extract().response();
		return response;
	}
	
	public Response aptGETPracticeUtilization(String practiceId,Map<String, String> Header) {
		log("Execute GET request for event");
		Response response = given().when().headers(Header).log().all().when()
				.get("utilization/practice/" +practiceId).then().log().all()
				.extract().response();
		return response;
	}
}