package com.medfusion.pay.api.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Validations {
	public void verifyCardInfoOnBin(Response response, String input, String cardType) throws IOException {
		String binNumber = input.substring(0, input.length()-2);
		JsonPath jsonPath = new JsonPath(response.asString());
		
		assertTrue(jsonPath.get("bin").toString().contains(binNumber));
		assertEquals(jsonPath.get("cardBrand"), cardType);
	}
	
	public void verifyBinNotFound(Response response, String binNumber) throws IOException {
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Could not find card information for bin = " + binNumber, "Bin found");
	}
}
