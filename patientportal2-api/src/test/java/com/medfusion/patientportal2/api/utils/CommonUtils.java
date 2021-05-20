package com.medfusion.patientportal2.api.utils;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import com.medfusion.common.utils.PropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CommonUtils {
	protected static PropertyFileLoader testData;
	
	public static String getSystemJWT() throws IOException {
		testData = new PropertyFileLoader();
		
		Response response = given().when().post("https://d1-ping-01.dev.medfusion.net:9031/as/token.oauth2?grant_type=client_credentials&client_id=SystemJwtValidationClientV1&client_secret=" + testData.getProperty("api.client.secret")).then().extract().response();
		JsonPath jsonPath = new JsonPath(response.asString());
		
		String token = jsonPath.get("access_token");
		return token;
	}
}
