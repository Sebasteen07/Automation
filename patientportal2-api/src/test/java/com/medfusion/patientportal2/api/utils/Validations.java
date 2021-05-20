package com.medfusion.patientportal2.api.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.testng.Assert.*;
import io.restassured.response.Response;

import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.path.json.JsonPath;

public class Validations {
	protected static PropertyFileLoader testData;

	public void verifySuccessfulCredentials(Response response, String version) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		Map<String, String> credentials = jsonPath.getMap("credentials");

		if (version.equals("v3")) {
			assertNotNull(jsonPath.get("authId"), "AuthId was null");
			assertEquals(jsonPath.get("email"), "kgladtest@gmail.com", "Email is incorrect");
			assertNull(credentials.get("password"), "Password is incorrect");
		} else if (version.equals("v4")) {
			assertNotNull(jsonPath.get("authId"), "AuthId was null");
			assertEquals(jsonPath.get("email"), "kgladtest@gmail.com", "Email is incorrect");
			assertNull(credentials.get("password"), "Password is incorrect");
			assertEquals(jsonPath.get("source"), "MF_MEMBER", "Source is incorrect");
			assertNull(jsonPath.get("externalProfile"), "External profile was null");
		} else if (version.equals("v5")) {
			assertNotNull(jsonPath.get("authId"), "AuthId was null");
			assertEquals(jsonPath.get("email"), "kgladtest@gmail.com", "Email is incorrect");
			assertEquals(credentials.get("password"), null, "Password is incorrect");
			assertEquals(jsonPath.get("challengeAnswer"), "focus", "Challenge answer is incorrect");
			assertTrue(jsonPath.get("challengePhrase").toString().contains("v1"), "Challenge phrase is incorrect");
			assertEquals(jsonPath.get("source"), "MF_MEMBER", "Source is incorrect");
			assertNull(jsonPath.get("externalProfile"), "External profile was null");
		}
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
		assertEquals(count, list.size());
	}
	
	public void verifyErrorMessage(Response response, String errorMessage) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		String message = jsonPath.get("message");
		assertTrue(message.contains(errorMessage), "Error doesn't match");
	}
}
