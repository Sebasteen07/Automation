// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static org.testng.Assert.assertEquals;

import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class APIVerification extends BaseTestNGWebDriver {

	public void responseCodeValidation(Response response, int statuscode) {
		assertEquals(response.getStatusCode(),statuscode, "Status Code doesnt match properly. Test Case failed");
		log("Status Code Validated as " + response.getStatusCode());
	}

	public void responseKeyValidation(Response response, String key) {
		JSONArray arr = new JSONArray(response.getBody().asString());
		for (int i = 0; i < arr.length(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			log("Validated key-> " + key + " value is-  " + obj.getString(key));
		}

	}

	public String responseKeyValidationJson(Response response, String key) {
		String value = null;
			JsonPath js = new JsonPath(response.asString());
			value = js.getString(key);
		return value;
	}

	public void responseTimeValidation(Response response) {
		long time = response.time();
		log("Response time " + time + " milliseconds");
		ValidatableResponse valRes = response.then();
		valRes.time(Matchers.lessThan(50000L));
	}

}
