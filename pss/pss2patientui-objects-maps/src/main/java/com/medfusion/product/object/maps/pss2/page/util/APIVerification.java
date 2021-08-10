// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.pss2.page.util;

import static org.testng.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class APIVerification extends BaseTestNGWebDriver {

	public void responseCodeValidation(Response response, int statuscode) {
		assertEquals(statuscode, response.getStatusCode(), "Status Code doesnt match properly. Test Case failed");
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
		try {
			JsonPath js = new JsonPath(response.asString());
			log("Validated key-> " + key + " value is-  " + js.getString(key));
			value = js.getString(key);
		} catch (Exception e) {
			log("Test Case Failed-Response not validated");
		}
		return value;
	}

	public void responseTimeValidation(Response response) {
		long time = response.time();
		log("Response Time in ms- " + time);
	}

}
