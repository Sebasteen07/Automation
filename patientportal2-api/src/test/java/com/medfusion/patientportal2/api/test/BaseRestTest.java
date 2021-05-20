package com.medfusion.patientportal2.api.test;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.patientportal2.api.pojos.Credentials;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseRestTest extends BaseTestNG  {
	protected static PropertyFileLoader testData;
	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	public static void setupRequestSpecBuilder() throws IOException {
		testData = new PropertyFileLoader();
		RestAssured.baseURI = testData.getProperty("mf.identity.base.url");
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).and()
				.addFilter(new ResponseLoggingFilter()).addFilter(new RequestLoggingFilter()).build();
	}

	public void setupResponseSpecBuilder() {
		responseSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}

	public Response postAuthenticateCredentials(String username, String password, String version) throws Exception {
		Map<String, Object> credentialsDetails = Credentials.getCredentialsMap(username, password);

		Response response = given().spec(requestSpec).body(credentialsDetails).when().post(version + "/credentials").then()
				.spec(responseSpec).and().extract().response();
		return response;
	}
	
	public Response getUsernameStatus(String username) throws Exception {
		Response response = given().spec(requestSpec).when().get("v5/credentials/usernames/" + username + "/status").then()
				.spec(responseSpec).and().extract().response();
		return response;
	}
	
	public Response getDemographicProfiles(String token, String authId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when().get("v5/identities/" + authId + "/demographicprofiles").then()
				.spec(responseSpec).and().extract().response();
		return response;
	}
	
	public Response getDemographicProfileById(String token, String authId, String profileId) throws Exception {
		Response response = given().spec(requestSpec).and().auth().oauth2(token).when().get("v5/identities/" + authId + "/demographicprofiles/" + profileId).then()
				.spec(responseSpec).and().extract().response();
		return response;
	}
}
