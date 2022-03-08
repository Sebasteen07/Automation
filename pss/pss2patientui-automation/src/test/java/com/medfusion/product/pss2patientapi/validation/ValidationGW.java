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
		apiVerification.responseTimeValidation(response);
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id " + arr.getJSONObject(0).getString("id"));
		assertEquals(arr.getJSONObject(0).getString("id"), propertyData.getProperty("patient.id.gw"),
				"patient id wrong");
		assertEquals(arr.getJSONObject(0).getString("firstName"), propertyData.getProperty("first.name.gw"),
				"patient name wrong");
		assertEquals(arr.getJSONObject(0).getString("lastName"), propertyData.getProperty("last.name.gw"),
				"patient lastname wrong");
	}

	public void verifySearchPatientResponseWithoutFname(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "First Name, Last Name, Gender And Date Of Birth Can Not Be Empty");
	}

	public void verifyDemographicsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), propertyData.getProperty("patient.id.gw"), "patient id wrong");
		assertEquals(jsonPath.get("firstName"), propertyData.getProperty("first.name.gw"), "firstname was wrong");
		assertEquals(jsonPath.get("lastName"), propertyData.getProperty("last.name.gw"), "lastname was wrong");

	}

	public void verifyDemographicsResponseWithoutPid(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Required request parameter 'patientId' for method parameter type String is not present",
				"Incorrect Patient id");

	}

	public void verifyAppointmentStatus(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), propertyData.getProperty("appointment.id.gw"), "Appointment id is incorrect");
		assertEquals(jsonPath.get("locationName"), propertyData.getProperty("locationname.gw"),
				"LocationName Name incorrect");
		apiVerification.responseKeyValidationJson(response, "id");
	}

	public void verifyAppointmentStatusWithoutAppId(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Appointment Id should not be empty", "InValid message");
	}

	public void verifyAvailiableSlotResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "availableSlots");

	}

	public void verifyPatientFlag(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "categoryName");
		apiVerification.responseKeyValidation(response, "flagLabel");
	}

	public void verifyInsurancecCarrierResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
	}

	public void verifyAppointmenttypesResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "categoryId");
		apiVerification.responseKeyValidation(response, "categoryName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "duration");
	}

	public void verifyPastAppointmentResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id " + arr.getJSONObject(0).getString("id"));
		log("Appointment Type- " + arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"));
	}

	public void verifyUpcomingAppointmentsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "startDateTime");
		apiVerification.responseKeyValidation(response, "endDateTime");
	}

	public void verifyUpcomingAppointmentsResponseWithoutPid(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Invalid Parameters");
	}

	public void verifyBookResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
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
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "value");
	}

	public void verifyLocationsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "description");
	}

	public void verifyLockoutResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "key");
		apiVerification.responseKeyValidation(response, "value");
		apiVerification.responseKeyValidation(response, "type");
	}

	public void verifyAddPatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("firstName"), propertyData.getProperty("first.name.gw"), "firstName incorrect");
		assertEquals(jsonPath.get("lastName"), propertyData.getProperty("last.name.gw"), "firstName incorrect");
	}

	public void verifyAddPatientWithoutFname(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"),
				"RequiredFieldException in Greenway.PrimeSuite.Controllers.Person.Patient.Patient.ValidatePatientAddNewRequest: (PatientAddNewRequest.Patient.Firstname is required.)");

	}

	public void verifyCancelStateResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseCodeValidation(response, 200);
	}

	public void verifyCancelStateResponseWithoutAppId(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Invalid AppointmentID.");

	}

	public void verifyMatchPatientWithoutEmail(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("error"), "Internal Server Error");
	}

	public void verifyNextAvailableSlotsWithoutProvider(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("error"), "Internal Server Error");
	}

	public void verifyPastAppointmentsWithoutPidPOST(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("message"), "Invalid Parameters");
	}

	public void verifytestScheduleAppPOST(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "slotAlreadyTaken");
		apiVerification.responseKeyValidationJson(response, "rescheduleNotAllowed");
	}
}
