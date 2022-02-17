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
import com.medfusion.product.appt.precheck.payload.AptProviderImagesPayload;
import com.medfusion.product.appt.precheck.pojo.Appointment;
import com.medfusion.product.object.maps.appt.precheck.util.APIVerification;
import com.medfusion.product.object.maps.appt.precheck.util.AccessToken;
import com.medfusion.product.object.maps.appt.precheck.util.HeaderConfig;
import com.medfusion.product.object.maps.appt.precheck.util.PostAPIRequestAptProviderImages;

import io.restassured.response.Response;

public class ApptPrecheckAptProviderImagesTest extends BaseTestNG {
		String getaccessToken;
		public static PropertyFileLoader propertyData;
		public static AptProviderImagesPayload payload;
		public static PostAPIRequestAptProviderImages postAPIRequest;
		public static AccessToken accessToken;
		public static HeaderConfig headerConfig;
		public static Appointment testData;
		APIVerification apiVerification = new APIVerification();

		@BeforeTest(enabled = true, groups = { "APItest" })
		public void setUp() throws IOException {
			propertyData = new PropertyFileLoader();
			postAPIRequest = PostAPIRequestAptProviderImages.getPostAPIRequestAptProviderImages();
			accessToken = AccessToken.getAccessToken();
			getaccessToken = accessToken.getaccessTokenPost();
			payload = AptProviderImagesPayload.getAptProviderImagesPayload();
			headerConfig = HeaderConfig.getHeaderConfig();
			testData = new Appointment();
			postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.apt.provider.images"));
			log("BASE URL-" + propertyData.getProperty("baseurl.apt.provider.images"));

		}

		@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
		public void testProviderListByPracticeGet() throws IOException {
			Response response = postAPIRequest.providerListByPractice(headerConfig.HeaderwithToken(getaccessToken),
					propertyData.getProperty("apt.provider.images.practice.id"));
			log("Verifying the response");
			assertEquals(response.getStatusCode(), 200);
			apiVerification.responseTimeValidation(response);
			apiVerification.responseKeyValidation(response, "providerId");
			apiVerification.responseKeyValidation(response, "firstName");
			apiVerification.responseKeyValidation(response, "lastName");
		}

		@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
		public void testProviderListByPracticeGetWithoutPracticeid() throws IOException {
			Response response = postAPIRequest
					.providerListByPracticeWithoutPracticeId(headerConfig.HeaderwithToken(getaccessToken));
			log("Verifying the response");
			assertEquals(response.getStatusCode(), 404);
			apiVerification.responseTimeValidation(response);
		}

		@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
		public void testCreateProviderForPracticePost() throws IOException {
			Response response = postAPIRequest.createProviderForPractice(
					payload.getCreateProviderForPracticePayload(propertyData.getProperty("create.provider.first.name"),
							propertyData.getProperty("create.provider.last.name"),
							propertyData.getProperty("apt.provider.images.practice.id"),
							propertyData.getProperty("create.provider.id"),
							propertyData.getProperty("create.provider.content.type"),
							propertyData.getProperty("create.provider.file.name")),
					headerConfig.HeaderwithToken(getaccessToken),
					propertyData.getProperty("apt.provider.images.practice.id"));
			log("Verifying the response");
			if (response.getStatusCode() == 200) {
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyCreateProviderForPractice(response,
					propertyData.getProperty("apt.provider.images.practice.id"));
			apiVerification.responseTimeValidation(response);
			apiVerification.responseKeyValidationJson(response, "practiceId");
			apiVerification.responseKeyValidationJson(response, "providerId");
			apiVerification.responseKeyValidationJson(response, "firstName");
			apiVerification.responseKeyValidationJson(response, "lastName");
		}
		else {
			log("Provider already exists");
			assertEquals(response.getStatusCode(), 500);
			apiVerification.verifyCreateProviderForPracticeIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateProviderForPracticePostInvalidPracticeId() throws IOException {
		Response response = postAPIRequest.createProviderForPractice(
				payload.getCreateProviderForPracticePayload(propertyData.getProperty("create.provider.first.name"),
						propertyData.getProperty("create.provider.last.name"),
						propertyData.getProperty("create.provider.invalid.practice.id"),
						propertyData.getProperty("create.provider.id"),
						propertyData.getProperty("create.provider.content.type"),
						propertyData.getProperty("create.provider.file.name")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.verifyCreateProviderForPracticeInvalidPracticeId(response);
		apiVerification.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCreateProviderForPracticePostWithoutFileName() throws IOException {
		Response response = postAPIRequest.createProviderForPractice(
				payload.getCreateProviderForPracticePayloadWithoutFileName(
						propertyData.getProperty("create.provider.first.name"),
						propertyData.getProperty("create.provider.last.name"),
						propertyData.getProperty("apt.provider.images.practice.id"),
						propertyData.getProperty("create.provider.id"),
						propertyData.getProperty("create.provider.content.type")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"));
		log("Verifying the response");
		if (response.getStatusCode() == 200) {
			assertEquals(response.getStatusCode(), 200);
			apiVerification.verifyCreateProviderForPractice(response,
					propertyData.getProperty("apt.provider.images.practice.id"));
			apiVerification.responseTimeValidation(response);
			apiVerification.responseKeyValidationJson(response, "practiceId");
			apiVerification.responseKeyValidationJson(response, "providerId");
			apiVerification.responseKeyValidationJson(response, "firstName");
			apiVerification.responseKeyValidationJson(response, "lastName");
		} else {
			log("Provider already exists");
			assertEquals(response.getStatusCode(), 500);
			apiVerification.verifyCreateProviderForPracticeIfAlreadyExist(response);
		}
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProviderDetailsGet() throws IOException {
		Response response = postAPIRequest.providerDetails(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("create.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProviderDetails(response, propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("create.provider.id"), propertyData.getProperty("create.provider.first.name"),
				propertyData.getProperty("create.provider.last.name"),
				propertyData.getProperty("create.provider.file.name"),
				propertyData.getProperty("create.provider.content.type"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProviderDetailsGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.providerDetailsWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("create.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testProviderDetailsGetWithoutProviderId() throws IOException {
		Response response = postAPIRequest.providerDetailsWithoutProviderId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyProviderDetailsWithoutPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateAnExistingProviderPut() throws IOException {
		Response response = postAPIRequest.updateAnExistingProvider(payload.getUpdateAnExistingProviderPayload(
				propertyData.getProperty("create.provider.first.name"),
				propertyData.getProperty("create.provider.last.name"),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("existing.provider.id"),
				propertyData.getProperty("create.provider.content.type"), propertyData.getProperty(
						"create.provider.file.name"))
				,headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("create.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateAnExistingProvider(response,
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("existing.provider.id"),
				propertyData.getProperty("create.provider.first.name"),
				propertyData.getProperty("create.provider.last.name"),
				propertyData.getProperty("create.provider.file.name"),
				propertyData.getProperty("create.provider.content.type"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateAnExistingProviderPutInvalidPracticeId() throws IOException {
		Response response = postAPIRequest.updateAnExistingProvider(
				payload.getUpdateAnExistingProviderPayload(propertyData.getProperty("create.provider.first.name"),
						propertyData.getProperty("create.provider.last.name"),
						propertyData.getProperty("create.provider.invalid.practice.id"),
						propertyData.getProperty("existing.provider.id"),
						propertyData.getProperty("create.provider.content.type"),
						propertyData.getProperty("create.provider.file.name")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("create.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyUpdateAnExistingProviderWithInvalidPracticeId(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateAnExistingProviderPutWithoutFileName() throws IOException {
		Response response = postAPIRequest.updateAnExistingProvider(
				payload.getUpdateAnExistingProviderPayloadwithoutFileName(
						propertyData.getProperty("create.provider.first.name"),
						propertyData.getProperty("create.provider.last.name"),
						propertyData.getProperty("apt.provider.images.practice.id"),
						propertyData.getProperty("existing.provider.id"),
						propertyData.getProperty("create.provider.content.type")),
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("create.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteExistingProvider() throws IOException {
		Response response = postAPIRequest.deleteExistingProvider(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("delete.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteExistingProviderWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.deleteExistingProviderWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("delete.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDeleteExistingProviderWithoutProviderId() throws IOException {
		Response response = postAPIRequest.deleteExistingProviderWithoutProviderId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 405);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyDeleteExistingProviderWithoutProviderId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTheImageDataGet() throws IOException {
		Response response = postAPIRequest.getsTheImageData(headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("gets.image.provider.id"), propertyData.getProperty("gets.image.size"),
				propertyData.getProperty("gets.image.thumbnail"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTheImageDataGetWithoutPracticeId() throws IOException {
		Response response = postAPIRequest.getsTheImageDataWithoutPracticeId(
				headerConfig.HeaderwithToken(getaccessToken), propertyData.getProperty("gets.image.provider.id"),
				propertyData.getProperty("gets.image.size"), propertyData.getProperty("gets.image.thumbnail"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTheImageDataGetWithoutProviderId() throws IOException {
		Response response = postAPIRequest.getsTheImageDataWithoutProviderId(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("gets.image.size"), propertyData.getProperty("gets.image.thumbnail"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		apiVerification.responseTimeValidation(response);
		apiVerification.verifyGetsTheImageDataWithoutProviderId(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testTheImageDataGetWithoutSizeAndThumbnail() throws IOException {
		Response response = postAPIRequest.getsTheImageDataWithoutSizeAndThumbnail(
				headerConfig.HeaderwithToken(getaccessToken),
				propertyData.getProperty("apt.provider.images.practice.id"),
				propertyData.getProperty("gets.image.provider.id"));
		log("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		apiVerification.responseTimeValidation(response);

	}

	@BeforeMethod(enabled = true, groups = { "APItest" })
	public void getMethodName(ITestResult result) throws IOException {
		log("Method Name-- " + result.getMethod().getMethodName());
	}
}
