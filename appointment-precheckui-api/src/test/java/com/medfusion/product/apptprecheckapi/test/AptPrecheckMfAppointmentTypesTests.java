package com.medfusion.product.apptprecheckapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.appt.precheck.payload.MfAppointmentTypesPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentTypes;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AptPrecheckMfAppointmentTypesTests extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfAppointmentTypesPayload payload;
	public static PostAPIRequestMfAppointmentTypes postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfAppointmentTypes.getPostAPIRequestMfAppointmentTypes();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfAppointmentTypesPayload.getMfAptTypesPayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.appointment.types"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.appointment.types"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsTypes() throws IOException {
		Response response = postAPIRequest.getAppointmentTypes(propertyData.getProperty("mf.apt.scheduler.practice.id"),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyApptTypeKeyValidation(response, "id");
		apiVerification.verifyApptTypeKeyValidation(response, "appointmentTypeId");
		apiVerification.verifyApptTypeKeyValidation(response, "appointmentTypeName");
		apiVerification.verifyApptTypeKeyValidation(response, "categoryId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsTypesUuid() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(1000);
		String integrationId = String.valueOf(randamNo);
		Response postApptTypeResponse = postAPIRequest.aptpostAppointmentTypes(
				payload.apptTypePayload(propertyData.getProperty("mf.apt.scheduler.appt.type.id"), integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		assertEquals(postApptTypeResponse.getStatusCode(), 200);
		JsonPath js = new JsonPath(postApptTypeResponse.asString());
		String getUuid = js.getString("id");

		log("Get Appointment type");
		Response response = postAPIRequest.getAppointmentTypesUuid(getUuid,
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNewApptType(response, propertyData.getProperty("mf.appt.type.id"),
				propertyData.getProperty("mf.appt.type.name"), propertyData.getProperty("mf.appt.type.category.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsTypesIncorrectUuid() throws IOException {
		Response response = postAPIRequest.getAppointmentTypesIncorrectUuid(
				propertyData.getProperty("mf.apt.types.incorrect.uuid"), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.verifyIncorrectUuid(response);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostCreateNewApptType() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(1000);
		String integrationId = String.valueOf(randamNo);
		String appointmentTypeId = String.valueOf(randamNo);
		Response response = postAPIRequest.aptpostAppointmentTypes(
				payload.apptTypePayload(appointmentTypeId, integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));

		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Post Appointment Type");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyCreateNewApptType(response, appointmentTypeId,
					propertyData.getProperty("mf.appt.type.name"),
					propertyData.getProperty("mf.appt.type.category.id"));
		}
		if (response.getStatusCode() == 400) {
			log("Appointment type already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAlreadyexistsAppt(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostOneApptTypeUpdate() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(1000);
		String integrationId = String.valueOf(randamNo);
		String appointmentTypeId = String.valueOf(integrationId);
		Response postResponse = postAPIRequest.aptpostAppointmentTypes(
				payload.oldApptTypePayload(appointmentTypeId, integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		int randomNum = random.nextInt(1000);
		String updateintegrationId = String.valueOf(randomNum);
		Response response = postAPIRequest.aptpostUpdateAppointmentTypes(
				propertyData.getProperty("mf.app.type.integration.id"),
				payload.updateApptTypePayload(appointmentTypeId, updateintegrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostMoreThanOneApptTypeUpdate() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(1000);
		String integrationId = String.valueOf(randamNo);

		Response postResponse = postAPIRequest.aptpostUpdateAppointmentTypes(integrationId,
				payload.updateMorethanOneApptTypePayload(integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		assertEquals(postResponse.getStatusCode(), 200);

		int randomNum = random.nextInt(1000);
		String updateIntId = String.valueOf(randomNum);

		Response response = postAPIRequest.aptpostUpdateAppointmentTypes(updateIntId,
				payload.updateMorethanOneApptTypePayload(updateIntId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutApptTypesUuid() throws IOException {
		Random random = new Random();
		int randamNo = random.nextInt(1000);
		String integrationId = String.valueOf(randamNo);
		String appointmentTypeId = String.valueOf(randamNo);
		Response postApptTypeResponse = postAPIRequest.aptpostAppointmentTypes(
				payload.apptTypePayload(appointmentTypeId, integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		assertEquals(postApptTypeResponse.getStatusCode(), 200);

		JsonPath js = new JsonPath(postApptTypeResponse.asString());
		String getUuid = js.getString("id");

		Response response = postAPIRequest.aptPutUpdateAppointmentTypesUuid(getUuid,
				payload.updateApptTypesUuidPayload(propertyData.getProperty("mf.appointment.type.id"), integrationId,
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyCreateNewApptType(response, propertyData.getProperty("mf.appointment.type.id"),
				propertyData.getProperty("mf.appt.type.name"), propertyData.getProperty("mf.appt.type.category.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutApptTypesWithIncorrectUuid() throws IOException {

		Response response = postAPIRequest.aptPutUpdateAppointmentTypesUuid(
				propertyData.getProperty("mf.apt.types.incorrect.uuid"),
				payload.updateApptTypesUuidPayload(propertyData.getProperty("mf.appointment.type.id"),
						propertyData.getProperty("mf.app.type.integration.id"),
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}