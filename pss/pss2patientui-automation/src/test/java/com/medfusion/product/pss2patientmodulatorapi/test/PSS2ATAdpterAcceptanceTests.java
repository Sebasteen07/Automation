// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAT;
import com.medfusion.product.pss2patientapi.payload.PayloadAT;
import com.medfusion.product.pss2patientapi.validation.ValidationAT;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2ATAdpterAcceptanceTests extends BaseTestNGWebDriver {

	public static PayloadAT payloadAT;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAT postAPIRequestat;

	ValidationAT validateAT = new ValidationAT();
	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payloadAT = new PayloadAT();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestat = new PostAPIRequestAT();
		log("I am before Test for Athena Partner");
		postAPIRequestat.setupRequestSpecBuilder(propertyData.getProperty("base.url.at"));
		log("BASE URL-" + propertyData.getProperty("base.url.at"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusGET() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		log("Patient Id- " + propertyData.getProperty("patient.id.at"));
		log("Practice Id- " + propertyData.getProperty("practice.id.at"));
		log("Appointment Id- " + propertyData.getProperty("apptid.at"));
		log("Start Date Time - " + propertyData.getProperty("start.date.time.at"));
		Response response = postAPIRequestat.appointmentStatus(propertyData.getProperty("patient.id.at"),
				propertyData.getProperty("practice.id.at"), propertyData.getProperty("apptid.at"),
				propertyData.getProperty("start.date.time.at"));
		validateAT.verifyAppointmentStatusRponse(response);
		
		Response responseInvalid =postAPIRequestat.appointmentStatusWithoutPatientId(propertyData.getProperty("practice.id.at"), propertyData.getProperty("apptid.at"),
				propertyData.getProperty("start.date.time.at"));
		validateAT.verifyAppointmentStatusWithoutPatientIdRponse(responseInvalid);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCanceledAppointmentStatusGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.appointmentStatus(propertyData.getProperty("patient.id.at"),
				propertyData.getProperty("practice.id.at"), propertyData.getProperty("cancelled.apptid.at"),
				propertyData.getProperty("start.date.time.at"));
		validateAT.verifyCancelledAppointmentStatusRponse(response);
		log("Payload- " + payloadAT.pastApptPayload(propertyData.getProperty("patient.id.at"),
				propertyData.getProperty("start.date.time.at")));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testcancelappointmentGET() throws NullPointerException, Exception {

		String b=payloadAT.getavailableSlotPayload(propertyData.getProperty("start.date.time.at"), propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at"));		

		Response response = postAPIRequestat.availableslots(propertyData.getProperty("practice.id.at"),b);

		JsonPath js = new JsonPath(response.asString());

		String startDateTime=js.getString("availableSlots[0].startDateTime");;
		String endDateTime=js.getString("availableSlots[0].endDateTime");
		String slotId=js.getString("availableSlots[0].slotId");
		
		String patientId= propertyData.getProperty("patient.id.at");
		
		String schedPayload=payloadAT.schedulePayload(startDateTime, endDateTime, patientId, slotId);
		
		Response scheduleApptResponse =postAPIRequestat.scheduleAppt(propertyData.getProperty("practice.id.at"), schedPayload);
		String apptid=aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		
		postAPIRequestat.cancelappt(propertyData.getProperty("practice.id.at"),apptid,patientId);
		Response responseInvalid =postAPIRequestat.cancelapptWithoutAppointmentId(propertyData.getProperty("practice.id.at"),patientId);
		
		validateAT.verifyCancelApptWithoutApptIdResponse(responseInvalid);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestat.pastAppt(propertyData.getProperty("practice.id.at"),
				propertyData.getProperty("cancelled.apptid.at"), propertyData.getProperty("patient.id.at"), payloadAT.pastApptPayload(propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at")));
		validateAT.verifPastAppointmentResponse(response);
		
		Response responseInvalid = postAPIRequestat.pastApptInvalid(propertyData.getProperty("practice.id.at"),
				propertyData.getProperty("cancelled.apptid.at"), propertyData.getProperty("patient.id.at"), payloadAT.pastApptInvalidPayload(propertyData.getProperty("end.date.time.at")));
		validateAT.verifPastAppointmentInvalidResponse(responseInvalid);		
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testupcomingappointmentsPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.upcommingApptPayload(propertyData.getProperty("start.date.time.at"), propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at"));	
	
		Response response = postAPIRequestat.upcommingAppt(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyUpcommingApptResponse(response);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSchedReschPOST() throws NullPointerException, Exception {
		
		String practiceid=propertyData.getProperty("practice.id.at");
		String b=payloadAT.getavailableSlotPayload(propertyData.getProperty("start.date.time.at"), propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at"));		

		Response response = postAPIRequestat.availableslots(propertyData.getProperty("practice.id.at"),b);

		JsonPath js = new JsonPath(response.asString());

		String startDateTime=js.getString("availableSlots[0].startDateTime");;
		String endDateTime=js.getString("availableSlots[0].endDateTime");
		String slotId=js.getString("availableSlots[0].slotId");
		
		String startDateTime_resch=js.getString("availableSlots[1].startDateTime");;
		String endDateTime_resch=js.getString("availableSlots[1].endDateTime");
		String slotId_resch=js.getString("availableSlots[1].slotId");
		
		String patientId= propertyData.getProperty("patient.id.at");
		
		log("startDateTime- "+startDateTime);
		log("endDateTime- "+endDateTime);
		log("slotId- "+slotId);
		log("patientId- "+patientId);
		
		log("startDateTime- "+startDateTime_resch);
		log("endDateTime- "+endDateTime_resch);
		log("slotId- "+slotId_resch);
		log("patientId- "+patientId);
		
		String schedPayload=payloadAT.schedulePayload(startDateTime, endDateTime, patientId, slotId);
		
		Response scheduleApptResponse =postAPIRequestat.scheduleAppt(propertyData.getProperty("practice.id.at"), schedPayload);
		String apptid=aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "rescheduleNotAllowed");
		
		log("Appointment id - "+apptid);
		
		String reschPayload=payloadAT.reschPayload(startDateTime_resch, endDateTime_resch, patientId, slotId_resch,apptid);
			
		Response rescheduleResponse=postAPIRequestat.rescheduleAppt(practiceid, reschPayload);
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "rescheduleNotAllowed");		
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlastseenproviderPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.lastseenProviderPayload();	
		String c=payloadAT.lastseenProviderInvalidPatientIdPayload();
		
		Response response = postAPIRequestat.lastseenprovider(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyLastseenProviderResponse(response);
		
		Response responseInvalid= postAPIRequestat.lastseenproviderInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyLastseenProviderResponse(responseInvalid);		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testcareprovideravailabilityPOST() throws NullPointerException, Exception {

		String b = payloadAT.careProviderAvailabilityPayload(
				propertyData.getProperty("start.date.time.at"),
				propertyData.getProperty("end.date.time.at"),		
				propertyData.getProperty("cpresourcecat.id.at"),
				propertyData.getProperty("cpresource.id.at"),
				propertyData.getProperty("cpslot.size.at")
				);
		Response response = postAPIRequestat.careprovideravailability(propertyData.getProperty("practice.id.at"), b);
		validateAT.verifyCareProviderAvailabilityResponse(response);
		
		String c= payloadAT.careProviderAvailabilityInvalidPayload();
		Response responseInvalid= postAPIRequestat.careprovideravailabilityInvalid(propertyData.getProperty("practice.id.at"),c);
		validateAT.verifyCareProviderAvailabilityInvalidResponse(responseInvalid);

	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testaddpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.addPatientPayload();		
		Response response = postAPIRequestat.addpatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyaddpatientResponse(response);
		String c=payloadAT.addPatientWithoutFirstNamePayload();	
		Response responseInvalid= postAPIRequestat.addpatientInvalid(propertyData.getProperty("practice.id.at"),c);
		validateAT.verifyaddpatientWithouFirstNameResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testmatchpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.matchPatientPayload();	
		String c=payloadAT.matchPatientInvalidPayload();

		Response response = postAPIRequestat.matchpatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyMatchPatientResponse(response);
		Response responseInvalid=postAPIRequestat.matchpatientInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyMatchPatientInvalidResponse(responseInvalid);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsearchpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.searchPatientPayload();		
		log("Payload- "+b);
		Response response = postAPIRequestat.searchpatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifySearchatientResponse(response);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testprerequisteappointmenttypesPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.prerequisteappointmenttypesPayload(propertyData.getProperty("prerequiste.appointmenttype.Id"));		
		log("Payload- "+b);
		Response response = postAPIRequestat.prerequisteappointmenttypes(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyPrerequisteApptResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testnextavailableslotsPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.nextAvailablePayload(propertyData.getProperty("start.date.time.at"));		
		String c=payloadAT.nextAvailableInvalidPayload(propertyData.getProperty("start.date.time.at"));
	
		Response response = postAPIRequestat.nextavailableslots(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyNextavailableSlotsResponse(response);
		Response responseInvalid= postAPIRequestat.nextavailableslotsWithInvalidApptId(propertyData.getProperty("practice.id.at"),c);
		validateAT.verifyNextavailableSlotsWithInvalidApptIdResponse(responseInvalid);
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsurancecarrierGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.insurancecarrier(propertyData.getProperty("practice.id.at"));
		validateAT.verifyInsuranceCarrierResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testhealthcheckGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.healthcheck(propertyData.getProperty("practice.id.at"));
		validateAT.verifyHealthcheckResponse(response);
		
		Response responseInvalid= postAPIRequestat.healthcheckInvalid(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseCodeValidation(responseInvalid, 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptTypesGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.appointmenttypes(propertyData.getProperty("practice.id.at"));
		validateAT.verifyAppointmentTypesResponse(response);
		
		Response responseInvallid = postAPIRequestat.appointmenttypesInvalid();
		assertEquals(responseInvallid.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.books(propertyData.getProperty("practice.id.at"));
		validateAT.verifyBookResponse(response);
		
		Response responseInvalid= postAPIRequestat.booksInvalid();
		assertEquals(responseInvalid.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testcancellationreasonrGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.cancellationreason(propertyData.getProperty("practice.id.at"));
		validateAT.verifyCancellationReasonResponse(response);
		
		Response responseInvalid= postAPIRequestat.cancellationreasonInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifyCancellationReasonInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.locations(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsResponse(response);
		
		Response responseInvalid = postAPIRequestat.locationsInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.lockout(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsResponse(response);
		Response responseInvalid= postAPIRequestat.lockoutInvalid(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseCodeValidation(responseInvalid, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testprerequisteappointmenttypesGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.prerequisteappointmenttypes(propertyData.getProperty("practice.id.at"));
		validateAT.verifyPrerequisteAppointmenttypesResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpingGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.ping(propertyData.getProperty("practice.id.at"));
		validateAT.verifyaPingResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testactuatorGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.actuator(propertyData.getProperty("practice.id.at"));
		validateAT.verifyactuatorResponse(response);	
		Response responseInvalid= postAPIRequestat.actuatorInvalid(propertyData.getProperty("practice.id.at"));
		assertEquals(responseInvalid.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpreventschedulingdateGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.preventschedulingdate(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseTimeValidation(response);
		
	}
}
