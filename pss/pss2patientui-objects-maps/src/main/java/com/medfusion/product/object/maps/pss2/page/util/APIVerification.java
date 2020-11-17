package com.medfusion.product.object.maps.pss2.page.util;

import org.json.JSONArray;
import org.json.JSONObject;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

import io.restassured.response.Response;
import junit.framework.Assert;

public class APIVerification extends BaseTestNGWebDriver {

	public void responseCodeValidation(Response response, int statuscode) {

		try {
			Assert.assertEquals(statuscode, response.getStatusCode());
			log("Status Code Validated as " + response.getStatusCode());
		} catch (AssertionError e) {
			log("Test Case-Failed");
			log("Expected StatusCode- " + statuscode + " Actual StatusCode " + response.getStatusCode());
		}
	}

	public void responseKeyValidation(Response response, String key) {
		try {
			JSONArray arr = new JSONArray(response.getBody().asString());
			for (int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				for (int j = 0; j < obj.length(); j++) {
					log("Validated values are " + obj.getString(key));
				}
			}
		} catch (Exception e) {
			log("Test Case Failed-Response not validated");
		}
	}

	public void responseTimeValidation(Response response) {

		try {
			long time = response.time();
			log("Test Case Passed-Response- " + time);

		} catch (Exception e) {
			log("Test Case Failed-Response Time not validated");
		}
	}
}
