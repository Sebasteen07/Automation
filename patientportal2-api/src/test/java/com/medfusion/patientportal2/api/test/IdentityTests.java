package com.medfusion.patientportal2.api.test;

import java.io.IOException;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.patientportal2.api.utils.Validations;
import com.medfusion.patientportal2.api.utils.CommonUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class IdentityTests extends BaseRestTest {

	protected static PropertyFileLoader testData;
	Validations validate = new Validations();

	@BeforeTest
	public void setUp() throws IOException {
		RestAssured.useRelaxedHTTPSValidation("TLSv1.2");
		
		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
		setupResponseSpecBuilder();
	}

	@Test
	public void testAuthenticateCredentialsV3() throws Exception {
		logStep("Execute post v3 credentials with valid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.username"),
				testData.getProperty("api.password"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifySuccessfulCredentials(response, "v3");
	}

	@Test
	public void testAuthenticateCredentialsV4() throws Exception {
		logStep("Execute post v4 credentials with valid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.username"),
				testData.getProperty("api.password"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifySuccessfulCredentials(response, "v4");
	}

	@Test
	public void testAuthenticateCredentialsV5() throws Exception {
		logStep("Execute post v5 credentials with valid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.username"),
				testData.getProperty("api.password"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifySuccessfulCredentials(response, "v5");
	}

	@Test
	public void testAuthenticateInvalidCredentialsV3() throws Exception {
		logStep("Execute post v3 credentials with invalid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.invalid.username"),
				testData.getProperty("api.password"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
		validate.verifyUnsuccessfulCredentials(response);
	}

	@Test
	public void testAuthenticateInvalidCredentialsV4() throws Exception {
		logStep("Execute post v4 credentials with invalid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.invalid.username"),
				testData.getProperty("api.password"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
		validate.verifyUnsuccessfulCredentials(response);
	}

	@Test
	public void testAuthenticateInvalidCredentialsV5() throws Exception {
		logStep("Execute post v5 credentials with invalid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.invalid.username"),
				testData.getProperty("api.password"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
		validate.verifyUnsuccessfulCredentials(response);
	}
	
	@Test
	public void testGetExistingUsername() throws Exception {
		logStep("Execute get username status with existing username");
		Response response = getUsernameStatus(testData.getProperty("api.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUsernameExists(response);
	}
	
	@Test
	public void testGetNewUsername() throws Exception {
		logStep("Execute get username status with new username");
		Response response = getUsernameStatus(testData.getProperty("api.new.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}
	
	@Test
	public void testGetUserDemographicProfiles() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCountOfDemographicProfiles(response, 11);
	}
	
	@Test
	public void testGetUserDemographicProfilesInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCountOfDemographicProfiles(response, 0);
	}
	
	@Test
	public void testUpdateUserDemographicProfile() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}
	
	@Test
	public void testUpdateUserDemographicProfileInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}
	
	@Test
	public void testGetUserDemographicProfileByProfileId() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.auth.id"), testData.getProperty("api.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPatientDOB(response);
	}
	
	@Test
	public void testGetUserDemographicProfileByProfileIdInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.invalid.auth.id"), testData.getProperty("api.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		validate.verifyErrorMessage(response, "Auth Id on the profile does not match with the give auth Id");
	}
	
	@Test
	public void testGetUserDemographicProfileByInvalidProfileId() throws Exception {
		logStep("Get System JWT token");
		String token = CommonUtils.getSystemJWT();
		
		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.invalid.auth.id"), testData.getProperty("api.invalid.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		validate.verifyErrorMessage(response, "Demographic Profile does not exist for profileId");
	}
}
