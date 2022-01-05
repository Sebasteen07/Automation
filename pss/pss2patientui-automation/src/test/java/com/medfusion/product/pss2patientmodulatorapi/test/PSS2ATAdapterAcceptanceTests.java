// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAT;
import com.medfusion.product.pss2patientapi.payload.PayloadAT;
import com.medfusion.product.pss2patientapi.validation.ValidationAT;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2ATAdapterAcceptanceTests extends BaseTestNG {

	public static PayloadAT payloadAT;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAT postAPIRequestat;
	public PSSPatientUtils pssPatientUtils;


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
		pssPatientUtils=new PSSPatientUtils();
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
	public void testAppointmentStatusInvalidGET() throws NullPointerException, Exception {
		
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
	public void testCancelAppointmentGET() throws NullPointerException, Exception {
		
		String startDate=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String endDate=pssPatientUtils.createFutureDate(startDate, 10);
		String b=payloadAT.getavailableSlotPayload(startDate,endDate, propertyData.getProperty("patient.id.at"));		

		Response response = postAPIRequestat.availableSlots(propertyData.getProperty("practice.id.at"),b);

		JsonPath js = new JsonPath(response.asString());

		String startDateTime=js.getString("availableSlots[0].startDateTime");;
		String endDateTime=js.getString("availableSlots[0].endDateTime");
		String slotId=js.getString("availableSlots[0].slotId");
		
		String patientId= propertyData.getProperty("patient.id.at");
		
		String schedPayload=payloadAT.schedulePayload(startDateTime, endDateTime, patientId, slotId);
		
		Response scheduleApptResponse =postAPIRequestat.scheduleAppt(propertyData.getProperty("practice.id.at"), schedPayload);
		String apptid=aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		
		postAPIRequestat.cancelAppt(propertyData.getProperty("practice.id.at"),apptid,patientId);
		Response responseInvalid =postAPIRequestat.cancelApptWithoutAppointmentId(propertyData.getProperty("practice.id.at"),patientId);
		
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
	public void testUpcomingAppointmentsPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.upcommingApptPayload(propertyData.getProperty("start.date.time.at"), propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at"));	
		
		Response response = postAPIRequestat.upcommingAppt(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyUpcommingApptResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsInvalidPOST() throws NullPointerException, Exception {
	
		String c=payloadAT.upcommingApptInvalidApptIdPayload(propertyData.getProperty("start.date.time.at"), propertyData.getProperty("end.date.time.at"), propertyData.getProperty("patient.id.at"));
		Response responseInvalid=postAPIRequestat.upcommingApptInvalid(propertyData.getProperty("practice.id.at"),c);
		aPIVerification.responseCodeValidation(responseInvalid, 500);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSchedReschPOST() throws NullPointerException, Exception {
		
		String startDate=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String endDate=pssPatientUtils.createFutureDate(startDate, 10);
		
		String practiceid=propertyData.getProperty("practice.id.at");
		String b=payloadAT.getavailableSlotPayload(startDate, endDate, propertyData.getProperty("patient.id.at"));		

		Response response = postAPIRequestat.availableSlots(propertyData.getProperty("practice.id.at"),b);
		aPIVerification.responseTimeValidation(response);

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
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		String apptid=aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "rescheduleNotAllowed");
		
		log("Appointment id - "+apptid);
		
		String reschPayload=payloadAT.reschPayload(startDateTime_resch, endDateTime_resch, patientId, slotId_resch,apptid);
			
		Response rescheduleResponse=postAPIRequestat.rescheduleAppt(practiceid, reschPayload);
		aPIVerification.responseTimeValidation(rescheduleResponse);
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "rescheduleNotAllowed");			
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleInvalidPOST() throws NullPointerException, Exception {
		String startDate=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String endDate=pssPatientUtils.createFutureDate(startDate, 10);
		
		String practiceid=propertyData.getProperty("practice.id.at");
		String b=payloadAT.getavailableSlotPayload(startDate,endDate, propertyData.getProperty("patient.id.at"));		

		Response response = postAPIRequestat.availableSlots(practiceid,b);

		JsonPath js = new JsonPath(response.asString());

		String startDateTime=js.getString("availableSlots[0].startDateTime");;
		String endDateTime=js.getString("availableSlots[0].endDateTime");
		String slotId=js.getString("availableSlots[0].slotId");
		
		String schedPayloadInvalid=payloadAT.scheduleWithoutPatientIdPayload(startDateTime, endDateTime, slotId);		
		Response scheduleApptResponse =postAPIRequestat.scheduleApptInvalid(practiceid, schedPayloadInvalid);
		validateAT.verifySchedApptInvalidResponse(scheduleApptResponse);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLasteenProviderPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.lastseenProviderPayload();		
		Response response = postAPIRequestat.lastSeenProvider(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyLastseenProviderResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLasteenProviderInvalidPOST() throws NullPointerException, Exception {

		String c=payloadAT.lastseenProviderInvalidPatientIdPayload();
		Response responseInvalid= postAPIRequestat.lastSeenproviderInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyLastseenProviderInvalidResponse(responseInvalid);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareProviderAvailabilityPOST() throws NullPointerException, Exception {

		String startDate = pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		String endDate = pssPatientUtils.createFutureDate(startDate, 10);

		String b = payloadAT.careProviderAvailabilityPayload(startDate, endDate,
				propertyData.getProperty("cpresourcecat.id.at"), propertyData.getProperty("cpresource.id.at"),
				propertyData.getProperty("cpslot.size.at"));
		Response response = postAPIRequestat.careProviderAvailability(propertyData.getProperty("practice.id.at"), b);
		validateAT.verifyCareProviderAvailabilityResponse(response);

		String c = payloadAT.careProviderAvailabilityInvalidPayload();
		Response responseInvalid = postAPIRequestat
				.careProviderAvailabilityInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyCareProviderAvailabilityInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.addPatientPayload();		
		Response response = postAPIRequestat.addPatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyaddpatientResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddpatientInvalidPOST() throws NullPointerException, Exception {
		
		String c = payloadAT.addPatientWithoutFirstNamePayload();
		Response responseInvalid = postAPIRequestat.addPatientInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyaddpatientWithouFirstNameResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.matchPatientPayload();
		Response response = postAPIRequestat.matchPatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyMatchPatientResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchpatientInvalidPOST() throws NullPointerException, Exception {

		String c = payloadAT.matchPatientInvalidPayload();
		Response responseInvalid = postAPIRequestat.matchPatientInvalid(propertyData.getProperty("practice.id.at"), c);
		validateAT.verifyMatchPatientInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchpatientPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.searchPatientPayload();		
		log("Payload- "+b);
		Response response = postAPIRequestat.searchPatient(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifySearchatientResponse(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchpatientInvalidPOST() throws NullPointerException, Exception {

		String b = payloadAT.searchPatientInvalidPayload();
		Response response = postAPIRequestat.searchPatientInvalid(propertyData.getProperty("practice.id.at"), b);
		validateAT.verifySearchatientInvalidResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteAppointmentTypesPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.prerequisteappointmenttypesPayload(propertyData.getProperty("prerequiste.appointmenttype.Id"));		
		log("Payload- "+b);
		Response response = postAPIRequestat.prerequisteAppointmentTypes(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyPrerequisteApptResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteAppointmentTypesInvalidPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.prerequisteappointmenttypesInvalidPayload(propertyData.getProperty("prerequiste.appointmenttype.Id"));		
		Response response = postAPIRequestat.prerequisteAppointmentTypesInvalid(propertyData.getProperty("practice.id.at"),b);
		aPIVerification.responseCodeValidation(response, 400);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextavailableslotsPOST() throws NullPointerException, Exception {
		
		String b=payloadAT.nextAvailablePayload(propertyData.getProperty("start.date.time.at"));		
		Response response = postAPIRequestat.nextavailableSlots(propertyData.getProperty("practice.id.at"),b);
		validateAT.verifyNextavailableSlotsResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextavailableslotsInvalidPOST() throws NullPointerException, Exception {
		
		String c=payloadAT.nextAvailableInvalidPayload(propertyData.getProperty("start.date.time.at"));
		Response responseInvalid= postAPIRequestat.nextavailableSlotsWithInvalidApptId(propertyData.getProperty("practice.id.at"),c);
		validateAT.verifyNextavailableSlotsWithInvalidApptIdResponse(responseInvalid);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsurancecarrierGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.insuranceCarrier(propertyData.getProperty("practice.id.at"));
		validateAT.verifyInsuranceCarrierResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthcheckGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.healthcheck(propertyData.getProperty("practice.id.at"));
		validateAT.verifyHealthcheckResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthcheckInvalidGET() throws NullPointerException, Exception {

		Response responseInvalid= postAPIRequestat.healthcheckInvalid(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseCodeValidation(responseInvalid, 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptTypesGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.appointmentTypes(propertyData.getProperty("practice.id.at"));
		validateAT.verifyAppointmentTypesResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptTypesInvalidGET() throws NullPointerException, Exception {

		Response responseInvallid = postAPIRequestat.appointmentTypesInvalid();
		assertEquals(responseInvallid.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.books(propertyData.getProperty("practice.id.at"));
		validateAT.verifyBookResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksInvalidGET() throws NullPointerException, Exception {
		
		Response responseInvalid= postAPIRequestat.booksInvalid();
		assertEquals(responseInvalid.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksWithoutPracticeIdGET() throws NullPointerException, Exception {
	
		Response responseInvalid= postAPIRequestat.booksInvalid();
		assertEquals(responseInvalid.getStatusCode(), 500);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.cancellationReasonInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifyCancellationReasonResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonInvalidGET() throws NullPointerException, Exception {
	
		Response responseInvalid= postAPIRequestat.cancellationReasonInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifyCancellationReasonInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsGET() throws NullPointerException, Exception {
	
		Response responseInvalid = postAPIRequestat.locationsInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsInvalidGET() throws NullPointerException, Exception {

		Response responseInvalid = postAPIRequestat.locationsInvalid(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsInvalidResponse(responseInvalid);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.lockout(propertyData.getProperty("practice.id.at"));
		validateAT.verifLocationsResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutInvalidGET() throws NullPointerException, Exception {

		Response responseInvalid= postAPIRequestat.lockoutInvalid(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseCodeValidation(responseInvalid, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteAppointmentTypesGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.prerequisteAppointmentTypes(propertyData.getProperty("practice.id.at"));
		validateAT.verifyPrerequisteAppointmenttypesResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteAppointmentTypesInvalidGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.prerequisteAppointmentTypesInvalid(propertyData.getProperty("practice.id.at"));
		aPIVerification.responseCodeValidation(response, 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPingGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.ping(propertyData.getProperty("practice.id.at"));
		validateAT.verifyaPingResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActuatorGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.actuator(propertyData.getProperty("practice.id.at"));
		validateAT.verifyactuatorResponse(response);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActuatorInvalidGET() throws NullPointerException, Exception {

		Response responseInvalid = postAPIRequestat.actuatorInvalid(propertyData.getProperty("practice.id.at"));
		assertEquals(responseInvalid.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingdateGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.preventSchedulingDate(propertyData.getProperty("practice.id.at"), "/preventschedulingdate/10282/82");
		aPIVerification.responseTimeValidation(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventschedulingdateInvalidGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.preventSchedulingDate(propertyData.getProperty("practice.id.at"),"/preventschedulingdateaa/10282/82");
		aPIVerification.responseTimeValidation(response);		
		aPIVerification.responseCodeValidation(response, 404);
	}
}
