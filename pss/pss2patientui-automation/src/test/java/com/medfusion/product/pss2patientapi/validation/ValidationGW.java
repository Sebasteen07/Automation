// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ValidationGW extends BaseTestNG {

	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();

	public void verifySearchPatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id " + arr.getJSONObject(0).getString("id"));
		assertEquals(arr.getJSONObject(0).getString("id"), propertyData.getProperty("patient.id.gw"),
				"patient id wrong");
		assertEquals(arr.getJSONObject(0).getString("firstName"), propertyData.getProperty("first.name.gw"),
				"patient name wrong");
		assertEquals(arr.getJSONObject(0).getString("lastName"), propertyData.getProperty("last.name.gw"),
				"patient lastname wrong");
	}

	public void verifyDemographicsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), propertyData.getProperty("patient.id.gw"), "patient id wrong");
		assertEquals(jsonPath.get("firstName"), propertyData.getProperty("first.name.gw"), "firstname was wrong");
		assertEquals(jsonPath.get("lastName"), propertyData.getProperty("last.name.gw"), "lastname was wrong");

	}

	public void verifyAppointmentStatus(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), propertyData.getProperty("appointment.id.gw"), "Appointment id is incorrect");
		assertEquals(jsonPath.get("locationName"), propertyData.getProperty("locationname.gw"),
				"LocationName Name incorrect");
		apiVerification.responseKeyValidationJson(response, "id");
	}

	public void verifyAvailiableSlotResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidationJson(response, "availableSlots");

	}

	public void verifyPatientFlag(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidation(response, "categoryName");
		apiVerification.responseKeyValidation(response, "flagLabel");
	}

	public void verifyInsurancecCarrierResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
	}

	public void verifyAppointmenttypesResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "categoryId");
		apiVerification.responseKeyValidation(response, "categoryName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "duration");
	}

	public void verifyPastAppointmentResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id " + arr.getJSONObject(0).getString("id"));
		log("Appointment Type- " + arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"));
		assertEquals(arr.getJSONObject(0).getString("id"), propertyData.getProperty("appointment.id.val"),
				"patient id wrong");
		assertEquals(arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"),
				propertyData.getProperty("appointment.type.name.val"), "AppointmentType was wrong");

	}

	public void verifyUpcomingAppointmentsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "startDateTime");
		apiVerification.responseKeyValidation(response, "endDateTime");
	}

	public void verifyBookResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "resourceId");
		apiVerification.responseKeyValidation(response, "resourceName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "categoryId");
		apiVerification.responseKeyValidation(response, "categoryName");
		apiVerification.responseKeyValidation(response, "providerId");
		apiVerification.responseKeyValidation(response, "type");

	}

	public void verifyFlagResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "value");
	}

	public void verifyLocationsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "description");
	}

	public void verifyLockoutResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidation(response, "key");
		apiVerification.responseKeyValidation(response, "value");
		apiVerification.responseKeyValidation(response, "type");
	}

	public void verifyAddPatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("firstName"), propertyData.getProperty("first.name.gw"), "firstName incorrect");
		assertEquals(jsonPath.get("lastName"), propertyData.getProperty("last.name.gw"), "firstName incorrect");
	}

	public void verifyCancelStateResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("checkCancelAppointmentStatus"), true);

	}
}
