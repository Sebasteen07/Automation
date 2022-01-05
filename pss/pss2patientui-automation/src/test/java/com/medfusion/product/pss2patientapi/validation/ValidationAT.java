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

public class ValidationAT extends BaseTestNG {

	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();

	public void verifyAppointmentStatusRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String pid = apiVerification.responseKeyValidationJson(response, "id");
		String status = apiVerification.responseKeyValidationJson(response, "status");
		String appointmentTypeId = apiVerification.responseKeyValidationJson(response, "appointmentTypeId");
		apiVerification.responseTimeValidation(response);

		assertEquals(pid, propertyData.getProperty("apptid.at"), "Appointment id is incorrect");
		assertEquals(status, "SCHEDULED", "Appointment's Status is not SCHEDULED");
		assertEquals(appointmentTypeId, propertyData.getProperty("appttype.id.at"), "Appointment Type id is not correct");
	}
	
	public void verifyAppointmentStatusWithoutPatientIdRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String message = apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Required String parameter 'patientId' is not present", "Message is not valid");
		apiVerification.responseTimeValidation(response);
	}

	public void verifyCancelledAppointmentStatusRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String pid = apiVerification.responseKeyValidationJson(response, "id");
		String status = apiVerification.responseKeyValidationJson(response, "status");
		String appointmentTypeId = apiVerification.responseKeyValidationJson(response, "appointmentTypeId");
		apiVerification.responseTimeValidation(response);

		assertEquals(pid, propertyData.getProperty("cancelled.apptid.at"), "Appointment id is incorrect");
		assertEquals(status, "CANCELLED", "Appointment's Status is not CANCELLED");
		assertEquals(appointmentTypeId, propertyData.getProperty("appttype.id.at"),
				"Appointment Type id is not correct");
	}
	
	public void verifypastappointmentsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id "+arr.getJSONObject(0).getString("id"));
		log("appt name "+arr.getJSONObject(0).getString("appointmentTypes.name"));
		

		String pid = apiVerification.responseKeyValidationJson(response, "id");
		String status = apiVerification.responseKeyValidationJson(response, "status");
		String appointmentTypeId = apiVerification.responseKeyValidationJson(response, "appointmentTypeId");
		apiVerification.responseTimeValidation(response);

		assertEquals(pid, propertyData.getProperty("apptid.at"), "Appointment id is incorrect");
		assertEquals(status, "SCHEDULED", "Appointment's Status is not SCHEDULED");
		assertEquals(appointmentTypeId, "82", "Appointment Type id is not correct");
	}
	
	public void verifyaddpatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String fname= apiVerification.responseKeyValidationJson(response, "firstName");
		String lname= apiVerification.responseKeyValidationJson(response, "lastName");
		String email= apiVerification.responseKeyValidationJson(response, "emailAddress");
		String gender= apiVerification.responseKeyValidationJson(response, "gender");
		apiVerification.responseTimeValidation(response);
		
		assertEquals(fname, propertyData.getProperty("addpatient.fname.at"), "First Name is incorrect");
		assertEquals(lname, propertyData.getProperty("addpatient.lname.at"));
		assertEquals(email, propertyData.getProperty("addpatient.email.at"));
		assertEquals(gender,propertyData.getProperty("addpatient.gender.at"));	

	}
	
	public void verifyaddpatientWithouFirstNameResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String message= apiVerification.responseKeyValidationJson(response, "message");
		apiVerification.responseTimeValidation(response);
		assertEquals(response.getStatusCode(), 400);
		assertEquals(message, "Additional fields are required.");
	}

	public void verifyAppointmentTypesResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "displayName");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "duration");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyCancelApptWithoutApptIdResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Required String parameter 'appointmentId' is not present","Error message is wrong");
		assertEquals(response.getStatusCode(), 400);
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyBookResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "resourceName");
		apiVerification.responseKeyValidationJson(response, "categoryName");
		apiVerification.responseKeyValidationJson(response, "providerId");
		apiVerification.responseKeyValidationJson(response, "type");
		apiVerification.responseKeyValidationJson(response, "resourceId");
		apiVerification.responseTimeValidation(response);

	}
	public void verifyCancellationReasonInvalidResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String error=apiVerification.responseKeyValidationJson(response, "error");
		apiVerification.responseTimeValidation(response);
		assertEquals(error, "Not Found");
		assertEquals(response.getStatusCode(), 404);

	}
	
	public void verifyCancellationReasonResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "displayName");
		apiVerification.responseKeyValidationJson(response, "reasonType");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyCareProviderAvailabilityResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath js = new JsonPath(response.asString());
		
		String resourceid= js.getString("careProvider[0].resourceId");
		String catid=js.getString("careProvider[0].resourceCatId");
		
		apiVerification.responseKeyValidationJson(response, "careProvider[0].slotSize");
		apiVerification.responseKeyValidationJson(response, "careProvider[0].nextAvilabledate");
		apiVerification.responseTimeValidation(response);
		apiVerification.responseCodeValidation(response,200);
		
		assertEquals(catid, propertyData.getProperty("cpresourcecat.id.at"));
		assertEquals(resourceid,propertyData.getProperty("cpresource.id.at"));	
	}
	
	public void verifyCareProviderAvailabilityInvalidResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
	
		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(response.getStatusCode(), 400);
		assertEquals(message, "Appointment type ID is invalid.");
		apiVerification.responseTimeValidation(response);	
	}
	
	public void verifyHealthcheckResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String value=apiVerification.responseKeyValidationJson(response, "components.api");
		assertEquals(value, "true");	
		apiVerification.responseTimeValidation(response);

	}
	public void verifyInsuranceCarrierResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "phone");
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyLastseenProviderResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String lastseen=apiVerification.responseKeyValidationJson(response, "resourceId");		
		assertEquals(lastseen, propertyData.getProperty("lastseen.provider.at"));		
		apiVerification.responseKeyValidationJson(response, "lastSeenDateTime");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyLastseenProviderInvalidResponse(Response response) throws IOException {
		
		propertyData = new PSSPropertyFileLoader();
		String message=apiVerification.responseKeyValidationJson(response, "message");		
		assertEquals(message, "Patient ID is invalid.");
		apiVerification.responseTimeValidation(response);
		
	}
	
	public void verifLocationsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "address.address1");
		apiVerification.responseKeyValidationJson(response, "address.city");
		apiVerification.responseKeyValidationJson(response, "address.state");
		apiVerification.responseKeyValidationJson(response, "address.zipCode");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifLocationsInvalidResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		String error=apiVerification.responseKeyValidationJson(response, "error");
		assertEquals(error, "Not Found");
		assertEquals(response.getStatusCode(), 404);
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyLockoutsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "key");
		apiVerification.responseKeyValidationJson(response, "value");
		apiVerification.responseKeyValidationJson(response, "type");
		apiVerification.responseTimeValidation(response);

	}
	public void verifyMatchPatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String fname= apiVerification.responseKeyValidationJson(response, "10113.firstName");
		String lname= apiVerification.responseKeyValidationJson(response, "10113.lastName");
	
		assertEquals(fname, propertyData.getProperty("addpatient.fname.at"), "First Name is incorrect");
		assertEquals(lname, propertyData.getProperty("addpatient.lname.at"));
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyMatchPatientInvalidResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Additional fields are required.", "Error message is not matching");
		apiVerification.responseTimeValidation(response);
		
	}
	
	public void verifyNextavailableSlotsResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseCodeValidation(response, 200);
		
	}
	
	public void verifyNextavailableSlotsWithInvalidApptIdResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Appointment type ID is invalid.");		
	}
	
	public void verifPastAppointmentResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		JSONArray arr = new JSONArray(response.body().asString());
		log("Id "+arr.getJSONObject(0).getString("id"));
		log("Appointment Type- "+ arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"));
		log("Provider Name-  "+arr.getJSONObject(0).getJSONObject("book").getString("resourceName"));
		log("Location Name-  "+arr.getJSONObject(0).getJSONObject("location").getString("displayName"));
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifPastAppointmentInvalidResponse(Response response) throws IOException {

		apiVerification.responseCodeValidation(response, 500);
		apiVerification.responseTimeValidation(response);
		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Invalid Parameters");	
		apiVerification.responseTimeValidation(response);

	}
	
	public void verifyaPingResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseTimeValidation(response);

	}
	public void verifyPrerequisteAppointmenttypesResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseTimeValidation(response);
	}
	public void verifyactuatorResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseKeyValidationJson(response, "_links.self.href");
		apiVerification.responseKeyValidationJson(response, "_links.health.href");
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifyPrerequisteApptResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		JSONArray arr = new JSONArray(response.body().asString());			
		String appointmentTypeId=arr.getJSONObject(0).getString("appointmentTypeId");
		apiVerification.responseTimeValidation(response);
		
		log("Id "+appointmentTypeId);	
		assertEquals(appointmentTypeId, propertyData.getProperty("prerequiste.appointmenttype.Id"));

	}
	
	public void verifySearchatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		JSONArray arr = new JSONArray(response.body().asString());
		
		String fname = arr.getJSONObject(0).getString("firstName");
		String lname = arr.getJSONObject(0).getString("lastName");
		
		assertEquals(fname, propertyData.getProperty("addpatient.fname.at"));
		assertEquals(lname, propertyData.getProperty("addpatient.lname.at"));	
		apiVerification.responseTimeValidation(response);
	}
	
	public void verifySearchatientInvalidResponse(Response response) throws IOException {

		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "First Name, Last Name, Gender And Date Of Birth Can Not Be Empty");
	}
	
	public void verifySchedApptInvalidResponse(Response response) throws IOException {
		
		String message=apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Additional fields are required.");
		apiVerification.responseTimeValidation(response);
	}
	
	
	public void verifyUpcommingApptResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		JSONArray arr = new JSONArray(response.body().asString());
		log("Id "+arr.getJSONObject(0).getString("id"));
		log("Appointment Type- "+ arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"));
		log("Provider Name-  "+arr.getJSONObject(0).getJSONObject("book").getString("resourceName"));
		log("Location Name-  "+arr.getJSONObject(0).getJSONObject("location").getString("displayName"));
		
		apiVerification.responseTimeValidation(response);

	}
	
}
