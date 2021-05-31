package com.medfusion.patientportal2.api.helpers;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import com.intuit.ifs.csscat.core.utils.ApiCommonUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.patientportal2.api.pojos.Credentials;
import com.medfusion.patientportal2.api.pojos.NotificationProfilePayload;
import com.medfusion.patientportal2.api.pojos.ProfilePayload;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiConstructor extends ApiCommonUtil {
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public static void setupRequestSpecBuilder() throws IOException {
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("mf.identity.base.url");
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).and()
				.addFilter(new ResponseLoggingFilter()).addFilter(new RequestLoggingFilter()).build();
	}

	// Identity Endpoints
	public Response postAuthenticateCredentials(String username, String password, String version) throws Exception {
		Map<String, Object> credentialsDetails = Credentials.getCredentialsMap(username, password);

		Response response = given().spec(requestSpec).body(credentialsDetails).when().post(version + "/credentials")
				.then().and().extract().response();
		return response;
	}

	public Response getUsernameStatus(String username) throws Exception {
		Response response = given().spec(requestSpec).when().get("v5/credentials/usernames/" + username + "/status")
				.then().and().extract().response();
		return response;
	}

	public Response getDemographicProfiles(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/demographicprofiles").then().and().extract().response();
		return response;
	}

	public Response getDemographicProfileById(String token, String authId, String profileId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/demographicprofiles/" + profileId).then().and().extract().response();
		return response;
	}

	public Response getNotificationProfile(String token, String authId, String profileId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response updateNotificationProfile(String token, String authId, String profileId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response postNotificationProfile(String token, String authId, String profileId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response getUserProfile(String token, String email, String username, String version) throws Exception {
		if (username != null) {
			Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
					.get(version + "/profiles?username=" + username).then().and().extract().response();
			return response;
		} else if (email != null) {
			Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
					.get(version + "/profiles?email=" + email).then().and().extract().response();
			return response;
		}
		return null;
	}

	public Response getUserProfileByAuthId(String token, String authId, String version) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get(version + "/profiles/" + authId).then().and().extract().response();
		return response;
	}

	public Response updateUserProfile(String token, String authId, String email, String username, String password,
			String source, String version) throws Exception {
		Map<String, Object> profilePayload = ProfilePayload.updateProfileMap(authId, email, username, password, source);

		Response response = given().spec(requestSpec).body(profilePayload).and().auth().oauth2(token).when()
				.put(version + "/profiles/" + authId).then().and().extract().response();
		return response;
	}

	public Response postLookUpUser(String token, String username, String password) throws Exception {
		Map<String, Object> credentialsDetails = Credentials.getCredentialsMap(username, password);

		Response response = given().spec(requestSpec).body(credentialsDetails).when().post("v2/profiles").then().and()
				.extract().response();
		return response;
	}

	public Response postNewUserV3AndV5(String token, String email, String username, String password,
			String challengeAnswer, String challengePhrase, String source, String version) throws Exception {
		Map<String, Object> profilePayload = ProfilePayload.createProfileMap(email, username, password, challengeAnswer,
				challengePhrase, source, version);

		Response response = given().spec(requestSpec).body(profilePayload).and().auth().oauth2(token).when()
				.post(version + "/profiles").then().and().extract().response();
		return response;
	}

	public Response postNewUserV4(String token, String email, String username, String password, String source,
			String version) throws Exception {
		Map<String, Object> profilePayload = ProfilePayload.createProfileMap(email, username, password, null, null,
				source, version);

		Response response = given().spec(requestSpec).body(profilePayload).and().auth().oauth2(token).when()
				.post("v4/profiles").then().and().extract().response();
		return response;
	}

	public Response getAuthIdsByUsername(String token, String username) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/profiles/getAuthIds?username=" + username).then().and().extract().response();
		return response;
	}

	public Response getAuthIdSecurityPhrase(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/profiles/securityPhrase/" + authId).then().and().extract().response();
		return response;
	}

	public Response deleteAuthIdSecurityPhrase(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.delete("v5/profiles/securityPhrase/" + authId).then().and().extract().response();
		return response;
	}

	public Response postVerifySecurityAnswer(String token, String authId, String phrase, String answer)
			throws Exception {
		Map<String, Object> securityPayload = ProfilePayload.createSecurityMap(answer);

		Response response = given().spec(requestSpec).body(securityPayload).and().auth().oauth2(token).when()
				.post("v5/profiles/securityPhrase/" + authId + "/verifyAnswer").then().and().extract().response();
		return response;
	}

	public Response postUpdateResetCode(String token, String authId, String answer) throws Exception {
		Map<String, Object> securityPayload = ProfilePayload.createSecurityMap(answer);

		Response response = given().spec(requestSpec).body(securityPayload).and().auth().oauth2(token).when()
				.post("v5/profiles/updateResetCode/" + authId).then().and().extract().response();
		return response;
	}

	public Response postNotificationProfile(String token, String authId, PropertyFileLoader testData) throws Exception {
		Map<String, Object> securityPayload = NotificationProfilePayload.createNotificationMap(testData);

		Response response = given().spec(requestSpec).body(securityPayload).and().auth().oauth2(token).when()
				.post("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response updateNotificationProfile(String token, String authId, String phoneNumber,
			PropertyFileLoader testData) throws Exception {
		Map<String, Object> securityPayload = NotificationProfilePayload.updateNotificationMap(testData, phoneNumber);

		Response response = given().spec(requestSpec).body(securityPayload).and().auth().oauth2(token).when()
				.put("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response getNotificationProfile(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when()
				.get("v5/identities/" + authId + "/notificationprofiles").then().and().extract().response();
		return response;
	}

	public Response getIdentitesByAuthId(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when().get("v4/identities/" + authId)
				.then().and().extract().response();
		return response;
	}

	public Response postIdentity(String token) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when().post("v4/identities").then()
				.and().extract().response();
		return response;
	}
}
