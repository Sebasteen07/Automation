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
import com.medfusion.product.appt.precheck.payload.MfLogoServicePayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestMfLogoService;

import io.restassured.response.Response;

public class ApptPrecheckMfLogoServiceTest extends BaseTestNG {
	String getaccessToken;
	public static PropertyFileLoader propertyData;
	public static MfLogoServicePayload payload;
	public static PostAPIRequestMfLogoService postAPIRequest;
	public static AccessToken accessToken;
	public static HeaderConfig headerConfig;
	public static Appointment testData;
	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		propertyData = new PropertyFileLoader();
		postAPIRequest = PostAPIRequestMfLogoService.getPostAPIRequestMfLogoService();
		accessToken = AccessToken.getAccessToken();
		getaccessToken = accessToken.getaccessTokenPost();
		payload = MfLogoServicePayload.getMfLogoServicePayload();
		headerConfig = HeaderConfig.getHeaderConfig();
		testData = new Appointment();
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.mf.logo.service"));
		log("BASE URL-" + propertyData.getProperty("baseurl.mf.logo.service"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoByIdGet() throws IOException {
		Response response = postAPIRequest.getLogoById(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("logo.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoByIdGetWithoutLogoId() throws IOException {
		Response response = postAPIRequest.getLogoByIdWithoutLogoId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoByPracticeIdGet() throws IOException {
		Response response = postAPIRequest.getLogoByPracticeId(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mf.logo.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoByIdGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.getLogoWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetLogoForPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoInfoGet() throws IOException {
		Response response = postAPIRequest.getLogoInfo(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("mf.logo.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyLogoInfo(response, propertyData.getProperty("mf.logo.service.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLogoInfoWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.getLogoInfoWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyLogoInfoWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLogoInfoPost() throws IOException {
		Response response = postAPIRequest.getLogoInfoPost(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("logo.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyLogoInfo(response, propertyData.getProperty("mf.logo.service.practice.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLogoInfoPostWithoutLogoId() throws IOException {
		Response response = postAPIRequest.getLogoInfoPostWithoutLogoId(headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLogoByIdPut() throws IOException {
		Response response = postAPIRequest.updateLogoById(propertyData.getProperty("upload.logo"),
				headerConfig.HeaderWithToken(getaccessToken),
				propertyData.getProperty("logo.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateLogo(response, propertyData.getProperty("logo.id"),
				propertyData.getProperty("mf.logo.service.practice.id"),propertyData.getProperty("mf.logo.service.practice.name"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLogoByIdPutWithoutLogoId() throws IOException {
		Response response = postAPIRequest.updateLogoByIdWithoutLogoId(propertyData.getProperty("upload.logo"),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLogoByPracticeIdPut() throws IOException {
		Response response = postAPIRequest.updateLogoByPracticeId(propertyData.getProperty("upload.logo"),
				headerConfig.HeaderWithToken(getaccessToken), propertyData.getProperty("mf.logo.service.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateLogo(response, propertyData.getProperty("logo.id"),
				propertyData.getProperty("mf.logo.service.practice.id"),propertyData.getProperty("mf.logo.service.practice.name"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateLogoByPracticeIdPutWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.updateLogoByPracticeIdWithoutPracticeId(
				propertyData.getProperty("upload.logo"), headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadLogoByPracticeIdPost() throws IOException {
		Response response = postAPIRequest.uploadLogo(propertyData.getProperty("upload.logo"),
				headerConfig.HeaderWithToken(getaccessToken), propertyData.getProperty("mf.logo.service.practice.id"));
		
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			log("Post an appointments action");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyUpdateLogo(response, propertyData.getProperty("logo.id"),
					propertyData.getProperty("mf.logo.service.practice.id"),propertyData.getProperty("mf.logo.service.practice.name"));
		}
		if (response.getStatusCode() == 400) {
			log("An appointment action not allowed");
			assertEquals(response.getStatusCode(), 400);
			apiVerification.verifyIfLogoAlreadyExists(response,propertyData.getProperty("mf.logo.service.practice.id"));
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUploadLogoPostWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.uploadLogoWithoutPracticeId(propertyData.getProperty("upload.logo"),
				headerConfig.HeaderwithToken(getaccessToken));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}

}
