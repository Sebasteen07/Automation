// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;

import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ValidationAdapterModulator {

	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();

	public void verifyAppointmentStatusWithoutPatientIdRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String message = apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Required String parameter 'patientId' is not present", "Message is not valid");
		apiVerification.responseTimeValidation(response);
	}

	public void verifyGetAppointmentTypeByBookIdResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());

		int id = arr.getJSONObject(0).getInt("id");
		String displayname = arr.getJSONObject(0).getString("displayName");
		String name = arr.getJSONObject(0).getString("name");
		String category = arr.getJSONObject(0).getString("categoryName");

		assertEquals(displayname, propertyData.getProperty("book.displayname.am"));
		assertEquals(Integer.toString(id), propertyData.getProperty("book.appt.id.am"));
		assertEquals(name, propertyData.getProperty("book.name.am"));
		assertEquals(category, propertyData.getProperty("book.categoryname.am"));
	}

	public void verifyCategorySpecialtyBook(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "ageRule");
		apiVerification.responseTimeValidation(response);

	}

	public void verifyResourceConfig(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidation(response, "group");
		apiVerification.responseTimeValidation(response);

	}

	public void verifyResourceConfigPost(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

	}

	public void verifyResourceConfigRuleGet(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "rule");
		apiVerification.responseTimeValidation(response);

	}

	public void verifyGenderMap(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "practice");
		apiVerification.responseKeyValidationJson(response, "displayName");
		apiVerification.responseKeyValidationJson(response, "pssCode");
		apiVerification.responseKeyValidationJson(response, "partnerCode");
		apiVerification.responseKeyValidationJson(response, "createdTsz");
		apiVerification.responseKeyValidationJson(response, "updatedTsz");
		apiVerification.responseKeyValidationJson(response, "seq");
		apiVerification.responseKeyValidationJson(response, "codeGroup");
		apiVerification.responseTimeValidation(response);

	}

	public void verifyLinkGet(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidationJson(response, "baseurl");
		apiVerification.responseKeyValidationJson(response, "links[0].id");
		apiVerification.responseKeyValidationJson(response, "links[0].type");
		apiVerification.responseKeyValidationJson(response, "links[0].resource");
		apiVerification.responseTimeValidation(response);

	}

	public void verifySaveLink(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

	}

	public void verifyLocationAssociated(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "extLocationId");
	}

	public void verifyLocation(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

	}

	public void verifyLocationByid(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "extLocationId");
	}

	public void verifyLocationBySearch(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "extLocationId");
	}

	public void verifyAssociatedLockout(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "key");
		apiVerification.responseKeyValidationJson(response, "type");
		apiVerification.responseKeyValidationJson(response, "group");

	}

	public void verifyLoginless(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "guid");
		apiVerification.responseKeyValidationJson(response, "link");

	}

	public void verifyLoginlessPost(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String id = js.getString("id");
		assertEquals(id, propertyData.getProperty("loginless.id.am"));
	}

}
