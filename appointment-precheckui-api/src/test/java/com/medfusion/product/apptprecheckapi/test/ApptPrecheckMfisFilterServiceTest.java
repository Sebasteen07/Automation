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
import com.medfusion.product.appt.precheck.payload.MfisFilterServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfisFilterService;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ApptPrecheckMfisFilterServiceTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfisFilterServicePayload payload;
	public static PostAPIRequestMfisFilterService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfisFilterService.getPostAPIRequestMfisFilterService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfisFilterServicePayload.getMfisFilterServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.filter.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.filter.service"));
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFilterPracticeIdPost() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Post request for filter practice id");
		Response response = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "filterSetId");
		apiVerification.verifyfilters(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeatFilterPracticeIdPost() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make repeat Post request for filter practice id");
		postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		Response response = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Post request for filter practice id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String filterSetId = js.getString("filterSetId");

		log("Put request for filter practice id");
		Response response = postAPIRequest.getFilterPracticeIdPut(payload.filterPracticeIdPutPayload(filterSetId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalideFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Post request for filter practice id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String filterSetId = js.getString("filterSetId");

		log("Put request for invalid filter practice id");
		Response response = postAPIRequest.getFilterPracticeIdPut(
				payload.filterPracticeIdPutPayload(filterSetId + "defgyb"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeatFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make put request for filter practice id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String filterSetId = js.getString("filterSetId");
		log("Delete filter practice id");
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make repeat put request for filter practice id");
		postAPIRequest.getFilterPracticeIdPut(payload.filterPracticeIdPutPayload(filterSetId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		Response response = postAPIRequest.getFilterPracticeIdPut(payload.filterPracticeIdPutPayload(filterSetId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFilterPracticeIdGet() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make Post request for filter practice id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make get request for filter practice id");
		Response response = postAPIRequest.getFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyfilters(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteFilterPracticeId() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for filter practice id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make delete request for filter practice id");
		Response response = postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFilterSetIdGet() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for filter set id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String filterSetId = js.getString("filterSetId");

		log("Make get request for filter set id");
		Response response = postAPIRequest.getFilterSetId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), filterSetId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyfilters(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidFilterSetIdGet() throws IOException {
		postAPIRequest.getDeleteFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for filter set id");
		Response postResponse = postAPIRequest.getFilterPracticeIdPost(payload.filterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String filterSetId = js.getString("filterSetId");

		log("Make get request for invalid filter set id");
		Response response = postAPIRequest.getFilterSetId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), filterSetId + "abhdhd");

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFilterMappingPracticeIdPost() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "mappingFilterId");
		apiVerification.responseKeyValidationJson(response, "statusValues");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeateMappingFilterPracticeIdPost() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make repeat post request for Mapping Practice Id");
		postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		Response response = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingFilterPracticeIdGet() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make get request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "mappingFilterId");
		apiVerification.responseKeyValidationJson(response, "statusValues");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make put request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingFilterPracticeIdPut(
				payload.mappingFilterPracticeIdPutPayload(mappingFilterId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidMappingFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make put request for invalid Mapping Practice Id");
		Response response = postAPIRequest.getMappingFilterPracticeIdPut(
				payload.mappingFilterPracticeIdPutPayload(mappingFilterId + "gftffj"),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeatMappingFilterPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make delete request for Mapping Practice Id");
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make repeate put request for Mapping Practice Id");
		postAPIRequest.getMappingFilterPracticeIdPut(payload.mappingFilterPracticeIdPutPayload(mappingFilterId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		Response response = postAPIRequest.getMappingFilterPracticeIdPut(
				payload.mappingFilterPracticeIdPutPayload(mappingFilterId),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteMappingFilterPracticeId() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make delete request for Mapping Practice Id");
		Response response = postAPIRequest.getDeleteMappingFilterPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingFilterIdGet() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");
		log("Make get request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingFilterId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), mappingFilterId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "mappingFilterId");
		apiVerification.responseKeyValidationJson(response, "statusValues");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidMappingFilterIdGet() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make get request for invalid Mapping Practice Id");
		Response response = postAPIRequest.getFilterSetId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), mappingFilterId + "abhdhd");

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteMappingFilterId() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");
		log("Make delete request for Mapping Practice Id");
		Response response = postAPIRequest.getDeleteMappingFilterId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), mappingFilterId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInvalidMappingFilterId() throws IOException {
		postAPIRequest.getDeleteMappingFilterPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingFilterPracticeIdPost(payload.mappingFilterPracticeIdPayload(),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make delete request for invalid Mapping Practice Id");
		Response response = postAPIRequest.getDeleteMappingFilterId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), mappingFilterId + "abhdhd");
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingPracticeIdMapsPost() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "resourceMapSetId");
		apiVerification.verifyMappingPractice(response, propertyData.getProperty("filter.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeateMappingPracticeIdPost() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Make repeat post request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingPracticeIdMapsGet() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make get request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "resourceMapSetId");
		apiVerification.verifyMappingPractice(response, propertyData.getProperty("filter.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMappingPracticeIdMapsPut() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("resourceMapSetId");

		log("Make put request for Mapping Practice Id");
		Response response = postAPIRequest.getMappingPracticeIdMapsPut(
				payload.mappingPracticeIdPutPayload(mappingFilterId, propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidMappingPracticeIdMapsPut() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("resourceMapSetId");

		log("Make put request for invalid Mapping Practice Id");
		Response response = postAPIRequest.getMappingPracticeIdMapsPut(
				payload.mappingPracticeIdPutPayload(mappingFilterId + "gtftdft",
						propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRepeatMappingPracticeIdPut() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		log("Verifying the response");
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String mappingFilterId = js.getString("mappingFilterId");

		log("Make delete put request for Mapping Practice Id");
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make repeat put request for Mapping Practice Id");
		postAPIRequest.getMappingPracticeIdMapsPut(
				payload.mappingPracticeIdPutPayload(mappingFilterId, propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		Response response = postAPIRequest.getMappingPracticeIdMapsPut(
				payload.mappingPracticeIdPutPayload(mappingFilterId, propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteMappingPracticeIdMaps() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		log("Make delete request for Mapping Practice Id");
		Response response = postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceMapSetIdGet() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String resourceMapSetId = js.getString("resourceMapSetId");

		log("Make get request for Resource Map Set Id");
		Response response = postAPIRequest.getResourceMapSetIdGet(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), resourceMapSetId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "resourceMapSetId");
		apiVerification.verifyMappingPractice(response, propertyData.getProperty("filter.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInvalidResourceMapSetIdGet() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String resourceMapSetId = js.getString("resourceMapSetId");

		log("Make get request for invalid Resource Map Set Id");
		Response response = postAPIRequest.getResourceMapSetIdGet(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), resourceMapSetId + "abhdhd");

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteResourceMapSetId() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String resourceMapSetId = js.getString("resourceMapSetId");

		log("Make delete request for Mapping Resource Map Set Id");
		Response response = postAPIRequest.getDeleteResourceMapSetId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), resourceMapSetId);

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteInvalidResourceMapSetId() throws IOException {
		postAPIRequest.getDeleteMappingPracticeIdMaps(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"));
		log("Make post request for Mapping Practice Id");
		Response postResponse = postAPIRequest.getMappingPracticeIdMapsPost(
				payload.mappingPracticeIdMapsPayload(propertyData.getProperty("filter.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("filter.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postResponse.asString());
		String resourceMapSetId = js.getString("resourceMapSetId");

		log("Make delete request for invalid Resource Map Set Id");
		Response response = postAPIRequest.getDeleteResourceMapSetId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("filter.practice.id"), resourceMapSetId + "abhdhd");

		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}

}
