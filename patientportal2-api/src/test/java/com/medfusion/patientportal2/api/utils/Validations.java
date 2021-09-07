// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.testng.Assert.*;
import io.restassured.response.Response;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.path.json.JsonPath;

public class Validations extends BaseTestNG {
	protected static PropertyFileLoader testData;

	public void verifySuccessfulCredentials(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		Map<String, String> credentials = jsonPath.getMap("credentials");
		assertNotNull(jsonPath.get("authId"), "AuthId was null");
		assertEquals(jsonPath.get("email"), "kgladtest@gmail.com", "Email is incorrect");
		assertEquals(credentials.get("password"), null, "Password is incorrect");
		assertEquals(jsonPath.get("challengeAnswer"), "focus", "Challenge answer is incorrect");
		assertTrue(jsonPath.get("challengePhrase").toString().contains("v1"), "Challenge phrase is incorrect");
		assertEquals(jsonPath.get("source"), "MF_MEMBER", "Source is incorrect");
		assertNull(jsonPath.get("externalProfile"), "External profile was null");
	}

	public void verifyUnsuccessfulCredentials(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Invalid username/password", "User found");
	}

	public void verifyNewUsername(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("status"), "VALID", "Error occurred");
	}

	public void verifyUsernameExists(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("status"), "DUPLICATE", "Error occurred");
	}

	public void verifyPatientDOB(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("dob"), "1992-04-27", "DOB does not match");
	}

	public void verifyCountOfDemographicProfiles(Response response, int count) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		ArrayList<?> list = jsonPath.get("id");
		assertEquals(list.size(), count);
	}

	public void verifyErrorMessage(Response response, String errorMessage) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		String message = jsonPath.get("message");
		assertTrue(message.contains(errorMessage), "Error doesn't match");
	}

	public void verifyProfilesForEmail(Response response, String searchedEmail) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		ArrayList<?> email = jsonPath.get("email");
		for (int i = 0; i < email.size(); i++) {
			assertEquals(email.get(i), searchedEmail);
			log(email.get(i) + " matches " + searchedEmail);
		}
	}

	public void verifyProfilesForUsername(Response response, String searchedUsername) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertTrue(jsonPath.getList("credentials.username").contains(searchedUsername));
		assertEquals(jsonPath.getList("credentials.username").size(), 1);
	}

	public void verifyProfilesForAuthId(Response response, String searchedAuthId) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		String authId = jsonPath.get("authId");
		assertTrue(authId.contains(searchedAuthId));
	}

	public void verifyPostUserResponse(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		Map<String, String> credentials = jsonPath.getMap("credentials");
		assertNotNull(jsonPath.get("authId"), "AuthId was null");
		assertEquals(jsonPath.get("email"), "mbush@nextgen.com", "Email is incorrect");
		assertEquals(credentials.get("password"), null, "Password is incorrect");
		assertEquals(jsonPath.get("challengeAnswer"), "Bird", "Challenge answer was null");
		assertEquals(jsonPath.get("challengePhrase"), "What flies?", "Challenge phrase was null");
		assertEquals(jsonPath.get("source"), "MF_MEMBER", "Source is incorrect");
		assertNull(jsonPath.get("externalProfile"), "External profile wasn't null");
	}

	public void verifyUpdateUserResponse(Response response) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		Map<String, String> credentials = jsonPath.getMap("credentials");
		assertNotNull(jsonPath.get("authId"), "AuthId was null");
		assertEquals(jsonPath.get("email"), "mbush@nextgen.com", "Email is incorrect");
		log(jsonPath.get("credentials.username").toString());
		assertTrue(jsonPath.get("credentials.username").toString().contains("update"));
		assertEquals(credentials.get("password"), null, "Password is incorrect");
		assertNull(jsonPath.get("challengeAnswer"), "Challenge answer wasn't null");
		assertEquals(jsonPath.get("source"), "MF_MEMBER", "Source is incorrect");
		assertNull(jsonPath.get("externalProfile"), "External profile wasn't null");
	}
}
