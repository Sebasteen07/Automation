package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.AptEventCollectorPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptEventcollector;

import io.restassured.response.Response;

public class AptEventCollectorTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static AptEventCollectorPayload payload;
	public static PostAPIRequestAptEventcollector postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	
	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestAptEventcollector.getPostAPIRequestAptEventcollector();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = AptEventCollectorPayload.getAptEvetCollectorPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("base.url.apr.event.coll"));
		log("BASE URL-" + propertyData.getProperty("base.url.apr.event.coll"));
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEventTypePut() throws IOException {
		Response response = postAPIRequest.aptEventCollector(
				payload.getEventTypePayload(propertyData.getProperty("event.id"),
						propertyData.getProperty("event.source"),
						propertyData.getProperty("event.time"),propertyData.getProperty("event.type"),propertyData.getProperty("event.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		
			log("Display appointments events");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyEventResponse(response, propertyData.getProperty("event.id"),
					propertyData.getProperty("event.source"),
					propertyData.getProperty("event.time"),propertyData.getProperty("event.type"),propertyData.getProperty("event.practice.id"));
		apiVerification.responseTimeValidation(response);
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testIncorrectEventTime() throws IOException {
		Response response = postAPIRequest.aptEventCollector(
				payload.getEventIncorrectTimePayload(propertyData.getProperty("event.id"),
						propertyData.getProperty("event.source"),
						propertyData.getProperty("event.incorrect.time"),propertyData.getProperty("event.type"),propertyData.getProperty("event.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.verifyEventIncorrectTime(response);
		apiVerification.responseTimeValidation(response);
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMissingDataEventType() throws IOException {
		Response response = postAPIRequest.aptEventCollector(
				payload.getEventSourceMissingPayload(propertyData.getProperty("event.id"),
						propertyData.getProperty("event.source.missing"),
						propertyData.getProperty("event.time"),propertyData.getProperty("event.type"),propertyData.getProperty("event.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.verifyMissingEventsource(response);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDailyAggregationPost() throws IOException {
		Response response = postAPIRequest.aptPostDailyAggregation(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("post.daily.start.date"),
				propertyData.getProperty("post.daily.end.date"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidationDailyAggregation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDailyAggregationPostIncorrectTime() throws IOException {
		Response response = postAPIRequest.aptPostDailyAggregationIncorrectTime(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidationDailyAggregation(response);
		apiVerification.verifyDailyAggregationIncorrectTime(response);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDailyAggregationPostValidateTimeRange() throws IOException {
		Response response = postAPIRequest.aptPostDailyAggregationTimeRange(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidationDailyAggregation(response);
		apiVerification.verifyDailyAggregationTimeRange(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLongtermAggregationPost() throws IOException {
		Response response = postAPIRequest.aptPostLongtermAggregation(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("date.longterm.aggregation"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLongtermAggregationPostIncorrectDate() throws IOException {
		Response response = postAPIRequest.aptPostLongtermAggregationIncorrectDate(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.verifyLongtermAggregationDateFormat(response);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEvent() throws IOException {
		Response response = postAPIRequest.aptGETEvent(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("event.time.range.start"),
				propertyData.getProperty("get.event.time.range.end"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventIncorrectMonth() throws IOException {
		Response response = postAPIRequest.aptGETEventIncorrectMonth(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventIncorrectDay() throws IOException {
		Response response = postAPIRequest.aptGETEventIncorrectDay(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventIncorrectHour() throws IOException {
		Response response = postAPIRequest.aptGETEventIncorrectHour(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventType() throws IOException {
		Response response = postAPIRequest.aptGETEventType(propertyData.getProperty("get.event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("get.event.time.range.start"),
				propertyData.getProperty("get.event.time.range.end"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventTypeIncorrectDate() throws IOException {
		Response response = postAPIRequest.aptGETEventTypeIncorrectDateFormat(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventTypeIncorrectStartHour() throws IOException {
		Response response = postAPIRequest.aptGETEventTypeIncorrectStartHour(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventPracticeId() throws IOException {
		Response response = postAPIRequest.aptGETEventPracticeId(propertyData.getProperty("get.event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("get.event.time.range.start"),
				propertyData.getProperty("get.event.time.range.end"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventPracticeIdIncorrectDate() throws IOException {
		Response response = postAPIRequest.aptGETEventPracticeIdIncorrectDate(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventId() throws IOException {
		Response response = postAPIRequest.aptGETEventId(propertyData.getProperty("get.event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("get.event.id"),
				propertyData.getProperty("get.event.time.range.start"),
				propertyData.getProperty("get.event.time.range.end")
				);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETEventIdIncorrectData() throws IOException {
		Response response = postAPIRequest.aptGETEventIdIncorrectData(propertyData.getProperty("event.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETPracticeUtilization() throws IOException {
		Response response = postAPIRequest.aptGETPracticeUtilization(propertyData.getProperty("event.practice.utilization.id"),
				headerConfig.HeaderwithToken(getaccessToken));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
