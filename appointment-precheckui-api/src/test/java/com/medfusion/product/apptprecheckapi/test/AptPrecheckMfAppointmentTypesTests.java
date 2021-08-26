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
import com.medfusion.product.appt.precheck.payload.MfAppointmentTypesPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfAppointmentTypes;

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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGETAppointmentsTypesUuid() throws IOException {
		Response response = postAPIRequest.getAppointmentTypesUuid(propertyData.getProperty("mf.apt.types.uuid"),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
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
	public void testPostAppointmentTypes() throws IOException {
		Response response = postAPIRequest.aptpostAppointmentTypes(propertyData.getProperty("mf.apt.types.uuid"),
				payload.apptTypePayload(propertyData.getProperty("mf.apt.types.uuid"),
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		log("Verifying the response");
		log("Payload- " + payload.apptTypePayload(propertyData.getProperty("mf.apt.types.uuid"),
				propertyData.getProperty("mf.apt.scheduler.practice.id")));

		if (response.getStatusCode() == 200) {
			log("Post Appointment Type");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.responseKeyValidationJson(response, "id");
			apiVerification.responseKeyValidationJson(response, "appointmentTypeId");
			apiVerification.responseKeyValidationJson(response, "appointmentTypeName");
			apiVerification.responseKeyValidationJson(response, "categoryId");
			apiVerification.responseKeyValidationJson(response, "categoryName");
			apiVerification.responseKeyValidationJson(response, "active");
		}
		if (response.getStatusCode() == 400) {
			log("Appointment type already exists");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyAlreadyexistsAppt(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPostApptTypeUpdate() throws IOException {
		Response response = postAPIRequest.aptpostUpdateAppointmentTypes(
				propertyData.getProperty("mf.app.type.integration.id"),
				payload.updateApptTypePayload(propertyData.getProperty("mf.app.type.integration.id"),
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("mf.apt.scheduler.practice.id"));
		log("Verifying the response");
		log("Payload- " + payload.updateApptTypePayload(propertyData.getProperty("mf.apt.types.uuid"),
				propertyData.getProperty("mf.apt.scheduler.practice.id")));

		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPutApptTypesUuid() throws IOException {
		Response response = postAPIRequest.aptPutUpdateAppointmentTypesUuid(
				propertyData.getProperty("mf.appointment.Type.uuid.update"),
				payload.updateApptTypesUuidPayload(propertyData.getProperty("mf.appointment.Type.Id"),
						propertyData.getProperty("mf.app.type.integration.id"),
						propertyData.getProperty("mf.apt.scheduler.practice.id")),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		log("Payload- " + payload.updateApptTypesUuidPayload(propertyData.getProperty("mf.appointment.Type.Id"),
				propertyData.getProperty("mf.app.type.integration.id"),
				propertyData.getProperty("mf.apt.scheduler.practice.id")));

		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}

}