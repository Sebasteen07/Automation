// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.api.test;

import java.io.IOException;
import java.sql.Timestamp;

import static org.testng.Assert.*;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.patientportal2.api.utils.Validations;
import com.medfusion.patientportal2.api.helpers.ApiConstructor;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class IdentityTests extends ApiConstructor {

	protected static PropertyFileLoader testData;
	Validations validate = new Validations();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	@BeforeTest
	public void setUp() throws IOException {
		RestAssured.useRelaxedHTTPSValidation("TLSv1.2");

		testData = new PropertyFileLoader();
		setupRequestSpecBuilder();
	}

	@Test(enabled = true)
	public void testAuthenticateCredentialsV5() throws Exception {
		logStep("Execute post credentials with valid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.username"),
				testData.getProperty("api.password"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifySuccessfulCredentials(response);
	}

	@Test(enabled = true)
	public void testAuthenticateInvalidCredentialsV5() throws Exception {
		logStep("Execute post v5 credentials with invalid username and password");
		Response response = postAuthenticateCredentials(testData.getProperty("api.invalid.username"),
				testData.getProperty("api.password"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
		validate.verifyUnsuccessfulCredentials(response);
	}

	@Test(enabled = true)
	public void testGetExistingUsername() throws Exception {
		logStep("Execute get username status with existing username");
		Response response = getUsernameStatus(testData.getProperty("api.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUsernameExists(response);
	}

	@Test(enabled = true)
	public void testGetNewUsername() throws Exception {
		logStep("Execute get username status with new username");
		Response response = getUsernameStatus(testData.getProperty("identity.new.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}

	@Test(enabled = true)
	public void testGetProfileByUsernameV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, null, testData.getProperty("identity.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForUsername(response, testData.getProperty("identity.username"));
	}

	@Test(enabled = true)
	public void testGetProfileByEmailV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, testData.getProperty("identity.email"), null);

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForEmail(response, testData.getProperty("identity.email"));
	}

	@Test(enabled = true)
	public void testGetProfileByAuthIdV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfileByAuthId(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForAuthId(response, testData.getProperty("api.auth.id"));
	}

	@Test(enabled = true)
	public void testCreateProfileV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime(),
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPostUserResponse(response);
	}

	@Test(enabled = true)
	public void testCreateProfileWithExistingUsernameV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("api.username"), testData.getProperty("identity.new.password"),
				testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(
				response.asString().contains("Username " + testData.getProperty("api.username") + " is already taken"));
	}

	@Test(enabled = true)
	public void testUpdateProfileV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUpdateUserResponse(response);
	}

	@Test(enabled = true)
	public void testUpdateProfileForInvalidAuthIdV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}

	@Test(enabled = true)
	public void testUpdateProfileWithInvalidTokenV5() throws Exception {
		logStep("Execute update user profile");
		Response response = updateUserProfile("", testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
	}

	@Test(enabled = true)
	public void testDeleteAuthIdSecurityPhrase() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute delete security phrase for user");
		Response response = deleteAuthIdSecurityPhrase(token, testData.getProperty("identity.update.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
	}

	@Test(enabled = true)
	public void testDeleteSecurityPhraseForInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute delete security phrase for user");
		Response response = deleteAuthIdSecurityPhrase(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(response.asString().contains("No user found with authId"));
	}

	@Test(enabled = true)
	public void testVerifySecurityAnswer() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute verify security answer");
		Response response = postVerifySecurityAnswer(token, testData.getProperty("identity.do.not.change.auth.id"),
				testData.getProperty("identity.new.challenge.phrase"),
				testData.getProperty("identity.new.challenge.answer"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
	}

	@Test(enabled = true)
	public void testVerifySecurityAnswerWithWrongAnswer() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute verify security answer");
		Response response = postVerifySecurityAnswer(token, testData.getProperty("identity.do.not.change.auth.id"),
				testData.getProperty("identity.new.challenge.phrase"), "Airplane");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(response.asString().contains("Security answer incorrect"));
	}

	@Test(enabled = true)
	public void testGetIdentityByAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get identity for auth id");
		Response response = getIdentitesByAuthId(token, testData.getProperty("identity.do.not.change.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(enabled = true)
	public void testGetIdentityByInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get identity for auth id");
		Response response = getIdentitesByAuthId(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		assertTrue(response.asString().contains("No Identity found for authId"));
	}
}
