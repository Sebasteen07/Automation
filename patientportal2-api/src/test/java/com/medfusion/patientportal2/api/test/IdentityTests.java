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
		String token = getSystemJWT();

		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCountOfDemographicProfiles(response, 11);
	}

	@Test
	public void testGetUserDemographicProfilesInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyCountOfDemographicProfiles(response, 0);
	}

	@Test
	public void testUpdateUserDemographicProfile() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}

	@Test
	public void testUpdateUserDemographicProfileInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profiles");
		Response response = getDemographicProfiles(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyNewUsername(response);
	}

	@Test
	public void testGetUserDemographicProfileByProfileId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.auth.id"),
				testData.getProperty("api.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPatientDOB(response);
	}

	@Test
	public void testGetUserDemographicProfileByProfileIdInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("api.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		validate.verifyErrorMessage(response, "Auth Id on the profile does not match with the give auth Id");
	}

	@Test
	public void testGetUserDemographicProfileByInvalidProfileId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getDemographicProfileById(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("api.invalid.profile.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		validate.verifyErrorMessage(response, "Demographic Profile does not exist for profileId");
	}

	@Test
	public void testGetProfileByUsernameV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, null, testData.getProperty("identity.username"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForUsername(response, testData.getProperty("identity.username"));
	}

	@Test
	public void testGetProfileByUsernameV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, null, testData.getProperty("identity.username"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForUsername(response, testData.getProperty("identity.username"));
	}

	@Test
	public void testGetProfileByUsernameV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, null, testData.getProperty("identity.username"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForUsername(response, testData.getProperty("identity.username"));
	}

	@Test
	public void testGetProfileByEmailV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, testData.getProperty("identity.email"), null, "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForEmail(response, testData.getProperty("identity.email"));
	}

	@Test
	public void testGetProfileByEmailV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, testData.getProperty("identity.email"), null, "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForEmail(response, testData.getProperty("identity.email"));
	}

	@Test
	public void testGetProfileByEmailV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfile(token, testData.getProperty("identity.email"), null, "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForEmail(response, testData.getProperty("identity.email"));
	}

	@Test
	public void testGetProfileByAuthIdV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile by authId");
		Response response = getUserProfileByAuthId(token, testData.getProperty("api.auth.id"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForAuthId(response, testData.getProperty("api.auth.id"));
	}

	@Test
	public void testGetProfileByAuthIdV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfileByAuthId(token, testData.getProperty("api.auth.id"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForAuthId(response, testData.getProperty("api.auth.id"));
	}

	@Test
	public void testGetProfileByAuthIdV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get user's demographic profile");
		Response response = getUserProfileByAuthId(token, testData.getProperty("api.auth.id"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyProfilesForAuthId(response, testData.getProperty("api.auth.id"));
	}

	@Test
	public void testLookUpProfileV2() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute look up user profile");
		Response response = postLookUpUser(token, testData.getProperty("api.username"),
				testData.getProperty("api.password"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPostUserResponse(response, "v2");
	}

	@Test
	public void testCreateProfileV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV3AndV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime(),
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"),
				"v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPostUserResponse(response, "v3");
	}

	@Test
	public void testCreateProfileV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV4(token, testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime(),
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPostUserResponse(response, "v4");
	}

	@Test
	public void testCreateProfileV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV3AndV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime(),
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"),
				"v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyPostUserResponse(response, "v5");
	}

	@Test
	public void testLookUpProfileWithInvalidUsernameAndPasswordV2() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute look up user profile");
		Response response = postLookUpUser(token, testData.getProperty("api.invalid.username"),
				testData.getProperty("api.password"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
		assertTrue(response.asString().contains("Invalid username/password"));
	}

	@Test
	public void testCreateProfileWithExistingUsernameV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV3AndV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("api.username"), testData.getProperty("identity.new.password"),
				testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"),
				"v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(
				response.asString().contains("Username " + testData.getProperty("api.username") + " is already taken"));
	}

	@Test
	public void testCreateProfileWithExistingUsernameV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV4(token, testData.getProperty("identity.new.email"),
				testData.getProperty("api.username"), testData.getProperty("identity.new.password"),
				testData.getProperty("identity.new.source"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(
				response.asString().contains("Username " + testData.getProperty("api.username") + " is already taken"));
	}

	@Test
	public void testCreateProfileWithExistingUsernameV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create user profile");
		Response response = postNewUserV3AndV5(token, testData.getProperty("identity.new.email"),
				testData.getProperty("api.username"), testData.getProperty("identity.new.password"),
				testData.getProperty("identity.new.challenge.answer"),
				testData.getProperty("identity.new.challenge.phrase"), testData.getProperty("identity.new.source"),
				"v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(
				response.asString().contains("Username " + testData.getProperty("api.username") + " is already taken"));
	}

	@Test
	public void testUpdateProfileV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUpdateUserResponse(response, "v3");
	}

	@Test
	public void testUpdateProfileV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUpdateUserResponse(response, "v4");
	}

	@Test
	public void testUpdateProfileV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validate.verifyUpdateUserResponse(response, "v5");
	}

	@Test
	public void testUpdateProfileForInvalidAuthIdV3() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}

	@Test
	public void testUpdateProfileForInvalidAuthIdV4() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}

	@Test
	public void testUpdateProfileForInvalidAuthIdV5() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user profile");
		Response response = updateUserProfile(token, testData.getProperty("api.invalid.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v5");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}

	@Test
	public void testUpdateProfileWithInvalidTokenV3() throws Exception {
		logStep("Execute update user profile");
		Response response = updateUserProfile("", testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v3");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
	}

	@Test
	public void testUpdateProfileWithInvalidTokenV4() throws Exception {
		logStep("Execute update user profile");
		Response response = updateUserProfile("", testData.getProperty("identity.update.auth.id"),
				testData.getProperty("identity.new.email"),
				testData.getProperty("identity.new.username") + timestamp.getTime() + "update",
				testData.getProperty("identity.new.password"), testData.getProperty("identity.new.source"), "v4");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 401);
	}

	@Test
	public void testGetAuthIdsForUsername() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get auth ids for user");
		Response response = getAuthIdsByUsername(token, testData.getProperty("api.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testGetAuthIdsForInvalidUsername() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get auth ids for user");
		Response response = getAuthIdsByUsername(token, testData.getProperty("api.invalid.username"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		assertTrue(response.asString().contains("authIds not found"));
	}

	@Test
	public void testGetAuthIdSecurityPhrase() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get security phrase for user");
		Response response = getAuthIdSecurityPhrase(token, testData.getProperty("api.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testGetAuthIdSecurityPhraseForProfileWithout() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get security phrase for user");
		Response response = getAuthIdSecurityPhrase(token, testData.getProperty("identity.update.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		assertTrue(response.asString().contains("No security phrase found for"));
	}

	@Test
	public void testGetSecurityPhraseForInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get security phrase for user");
		Response response = getAuthIdSecurityPhrase(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		assertTrue(response.asString().contains("No security phrase found for"));
	}

	@Test
	public void testDeleteAuthIdSecurityPhrase() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute delete security phrase for user");
		Response response = deleteAuthIdSecurityPhrase(token, testData.getProperty("identity.update.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 204);
	}

	@Test
	public void testDeleteSecurityPhraseForInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute delete security phrase for user");
		Response response = deleteAuthIdSecurityPhrase(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(response.asString().contains("No user found with authId"));
	}

	@Test
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

	@Test
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

	@Test
	public void testUpdateUserResetCode() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user reset code");
		Response response = postUpdateResetCode(token, testData.getProperty("identity.do.not.change.auth.id"),
				testData.getProperty("identity.new.challenge.answer"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testUpdateUserResetCodeWithWrongSecurityInfo() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute update user reset code");
		Response response = postUpdateResetCode(token, testData.getProperty("identity.do.not.change.auth.id"),
				"Airplane");

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		assertTrue(response.asString().contains("Security answer incorrect"));
	}

	@Test
	public void testCreateNotificationProfile() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute create notification profile for auth id");
		Response response = postNotificationProfile(token, testData.getProperty("identity.do.not.change.auth.id"),
				testData);

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testGetNotificationProfiles() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get notification profile for auth id");
		Response response = getNotificationProfile(token, testData.getProperty("identity.do.not.change.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testGetNotificationProfilesForInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get notification profile for auth id");
		Response response = getNotificationProfile(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
	}

	@Test
	public void testUpdateNotificationProfile() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();
		String mobile = generateRandomPhoneNumber();

		logStep("Execute update notification profile for auth id");
		Response response = updateNotificationProfile(token, testData.getProperty("identity.do.not.change.auth.id"),
				mobile, testData);

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		assertTrue(response.asString().contains(mobile), "Phone number doesn't match");
	}

	@Test
	public void testUpdateNotificationProfileForInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();
		String mobile = generateRandomPhoneNumber();

		logStep("Execute update notification profile for auth id");
		Response response = updateNotificationProfile(token, testData.getProperty("api.invalid.auth.id"), mobile,
				testData);

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test
	public void testGetIdentityByAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get identity for auth id");
		Response response = getIdentitesByAuthId(token, testData.getProperty("identity.do.not.change.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test
	public void testGetIdentityByInvalidAuthId() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get identity for auth id");
		Response response = getIdentitesByAuthId(token, testData.getProperty("api.invalid.auth.id"));

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
		assertTrue(response.asString().contains("No Identity found for authId"));
	}
	
	@Test
	public void testCreateNewIdentity() throws Exception {
		logStep("Get System JWT token");
		String token = getSystemJWT();

		logStep("Execute get identity for auth id");
		Response response = postIdentity(token);

		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
}
