package com.medfusion.product.object.maps.appt.precheck.util;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.medfusion.common.utils.PropertyFileLoader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
public class AccessToken {
	private static AccessToken accessToken = new AccessToken();

	private AccessToken() {
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}
	public String getaccessTokenPost() throws IOException {
		PropertyFileLoader propertyData = new PropertyFileLoader();
		String baseurl= propertyData.getProperty("appt.precheck.access.token.url");
		String grantType= propertyData.getProperty("appt.precheck.grant.type");
		String clientId= propertyData.getProperty("appt.precheck.client.id");
		String clientSecret= propertyData.getProperty("appt.precheck.client.secret");
		RestAssured.baseURI = baseurl;
		Response response = given().log().all().queryParam("grant_type", grantType).queryParam("client_id", clientId).queryParam("client_secret", clientSecret).post().then().log().all().extract().response();
		JsonPath jsonPath = new JsonPath(response.asString());
		String access_Token = jsonPath.get("access_token");
		return access_Token;
	}
}
