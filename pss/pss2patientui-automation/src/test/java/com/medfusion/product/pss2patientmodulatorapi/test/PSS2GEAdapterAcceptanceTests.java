// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGE;
import com.medfusion.product.pss2patientapi.payload.PayloadGE;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2GEAdapterAcceptanceTests extends BaseTestNG {
	
	
	public PSSPatientUtils pssPatientUtils;
	public static PayloadGE payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestGE postAPIRequestge;


	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification apiVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		pssPatientUtils=new PSSPatientUtils();
		payload = new PayloadGE();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestge = new PostAPIRequestGE();
		log("I am before Test");
		postAPIRequestge.setupRequestSpecBuilder(propertyData.getProperty("base.url.ge"));
		log("BASE URL-" + propertyData.getProperty("base.url.ge"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthCheckGET() throws NullPointerException, Exception {

		Response response = postAPIRequestge.healthCheck(propertyData.getProperty("practiceid.ge"));
		logStep("Verifying the response");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "version");
		apiVerification.responseKeyValidationJson(response, "components.DatabaseName");
		apiVerification.responseKeyValidationJson(response, "components.api");
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthCheckWithoutPracticeIDGET() throws NullPointerException, Exception {

		Response response = postAPIRequestge.healthCheck("");
		logStep("Verifying the response");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}
	

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPingGET() throws IOException {
		Response response = postAPIRequestge.ping(propertyData.getProperty("practiceid.ge"));
		logStep("Verifying the response");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPingWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.ping("");
		logStep("Verifying the response");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVersionGET() throws IOException {
		Response response = postAPIRequestge.version(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "version");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testVersionWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.version("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGET() throws IOException {
		Response response = postAPIRequestge.lockOut(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "key");
		apiVerification.responseKeyValidation(response, "value");
		apiVerification.responseKeyValidation(response, "type");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.lockOut("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesGET() throws IOException {
		Response response = postAPIRequestge.appointmentType(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
		apiVerification.responseKeyValidation(response, "displayName");
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.appointmentType("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGET() throws IOException {
		Response response = postAPIRequestge.books(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);	
		apiVerification.responseKeyValidation(response, "resourceId");
		apiVerification.responseKeyValidation(response, "resourceName");
		apiVerification.responseKeyValidation(response, "displayName");
		apiVerification.responseKeyValidation(response, "providerId");
		apiVerification.responseKeyValidation(response, "type");	
	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.books("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGET() throws IOException {
		Response response = postAPIRequestge.insuranceCarrier(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);	
		
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "zipCode");
		apiVerification.responseKeyValidationJson(response, "address1");
		apiVerification.responseKeyValidationJson(response, "state");	
		apiVerification.responseKeyValidationJson(response, "city");	

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierWithoutPracticeIdGET() throws IOException {
		Response response = postAPIRequestge.insuranceCarrier("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationGET() throws IOException {
		
		Response response = postAPIRequestge.locations(propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);	
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "name");
		apiVerification.responseKeyValidationJson(response, "displayName");
		apiVerification.responseKeyValidationJson(response, "phoneNumber1");
			
		}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationWithoutPracticeIdGET() throws IOException {
		
		Response response = postAPIRequestge.locations("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);	
		
		}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActuatorGET() throws IOException {
		Response response = postAPIRequestge.actuator("/actuator");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);	
		apiVerification.responseKeyValidationJson(response, "_links");
		apiVerification.responseKeyValidationJson(response, "health");
		apiVerification.responseKeyValidationJson(response, "health-path");
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testActuatorInvalidPathGET() throws IOException {
		Response response = postAPIRequestge.actuator("/actuatoraa");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderPOST() throws IOException {
		Response response =	postAPIRequestge.lastseenProvider(PayloadGE.providerLastSeenPayload(),propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);	
		apiVerification.responseKeyValidationJson(response, "lastSeenDateTime");
		apiVerification.responseKeyValidationJson(response, "resourceId");
		apiVerification.responseKeyValidationJson(response, "providerAvailability");


	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderWithoutBodyPOST() throws IOException {
		Response response =	postAPIRequestge.lastseenProvider("",propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusGET() throws IOException {

		Response response = postAPIRequestge.appointmentStatus(propertyData.getProperty("practiceid.ge"),
				"appointmentId", propertyData.getProperty("appt.status.id.ge"), "patientId",
				propertyData.getProperty("appt.status.patient.id.ge"), "startDateTime",
				propertyData.getProperty("start.date.time.ge"), propertyData.getProperty("appt.id.ge"));

		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "startDateTime");
		apiVerification.responseKeyValidationJson(response, "endDateTime");
		apiVerification.responseKeyValidationJson(response, "status");
		apiVerification.responseKeyValidationJson(response, "resourceId");
		apiVerification.responseKeyValidationJson(response, "appointmentTypeId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusWithoutStartTimeGET() throws IOException {

		Response response = postAPIRequestge.appointmentStatus(propertyData.getProperty("practiceid.ge"),
				"appointmentId", propertyData.getProperty("appt.status.id.ge"), "patientId",
				propertyData.getProperty("appt.status.patient.id.ge"), "", "", propertyData.getProperty("appt.id.ge"));

		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertTrue(msg.contains("Required"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentWithoutPatientIdStatusGET() throws IOException {

		Response response = postAPIRequestge.appointmentStatus(propertyData.getProperty("practiceid.ge"),
				"appointmentId", propertyData.getProperty("appt.status.id.ge"), "", "", "startDateTime",
				propertyData.getProperty("start.date.time.ge"), propertyData.getProperty("appt.id.ge"));

		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "Required request parameter 'patientId' for method parameter type String is not present");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusWithoutAppIdGET() throws IOException {

		Response response = postAPIRequestge.appointmentStatus(propertyData.getProperty("practiceid.ge"), "", "",
				"patientId", propertyData.getProperty("appt.status.patient.id.ge"), "startDateTime",
				propertyData.getProperty("start.date.time.ge"), propertyData.getProperty("appt.id.ge"));

		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "Required request parameter 'appointmentId' for method parameter type String is not present");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleAppointmentPOST() throws IOException {

		String date=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		log("Current date is"+date);
		String body = PayloadGE.availableSlotsPayload(propertyData.getProperty("patientid.ge"),
				propertyData.getProperty("location.id.ge"), propertyData.getProperty("start.date.time.ge"),
				propertyData.getProperty("slot.size.ge"), date);

		Response response = postAPIRequestge.availableSlots(body, propertyData.getProperty("practiceid.ge"));
		JsonPath js = new JsonPath(response.asString());

		String startDateTime = js.getString("availableSlots[0].startDateTime");
		;
		String slotId = js.getString("availableSlots[0].slotId");
		String endTime = js.getString("availableSlots[1].startDateTime");

		String startDateTime_resch = js.getString("availableSlots[1].startDateTime");
		String endDateTime_resch = js.getString("availableSlots[2].startDateTime");
		String slotId_resch = js.getString("availableSlots[1].slotId");

		String patientId = propertyData.getProperty("patientid.ge");

		log("startDateTime- " + startDateTime);
		log("slotId- " + slotId);
		log("patientId- " + patientId);

		log("startDateTime- " + startDateTime_resch);
		log("slotId- " + slotId_resch);
		log("patientId- " + patientId);

		String schedPayload = PayloadGE.schedApptPayload(startDateTime, patientId, slotId, endTime);

		Response scheduleApptResponse = postAPIRequestge.scheduleApptPatient(schedPayload,
				propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(scheduleApptResponse, 200);
		apiVerification.responseTimeValidation(scheduleApptResponse);
		apiVerification.responseTimeValidation(scheduleApptResponse);
		String id=apiVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		log("Id is   "+id);
		apiVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		
		
		String body1=PayloadGE.rescheduleAppointmentPayload(startDateTime_resch, endDateTime_resch, propertyData.getProperty("location.id.ge"), propertyData.getProperty("patientid.ge"),
				propertyData.getProperty("resource.id.ge"), slotId_resch, id);
		Response reScheduleApptResponse =postAPIRequestge.rescheduleAppt(body1,propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(reScheduleApptResponse, 200);
		apiVerification.responseTimeValidation(reScheduleApptResponse);
		apiVerification.responseKeyValidationJson(reScheduleApptResponse, "id");
		apiVerification.responseKeyValidationJson(reScheduleApptResponse, "slotAlreadyTaken");


		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleAppointmentForNewPatientPOST() throws IOException {

		String date=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		log("Current date is"+date);
		String body = PayloadGE.availableSlotsPayload(propertyData.getProperty("patientid.ge"),
				propertyData.getProperty("location.id.ge"), propertyData.getProperty("start.date.time.ge"),
				propertyData.getProperty("slot.size.ge"), date);

		Response response = postAPIRequestge.availableSlots(body, propertyData.getProperty("practiceid.ge"));
		JsonPath js = new JsonPath(response.asString());

		String startDateTime = js.getString("availableSlots[0].startDateTime");
		;
		String slotId = js.getString("availableSlots[0].slotId");
		String endTime = js.getString("availableSlots[1].startDateTime");

		String patientId = propertyData.getProperty("patientid.ge");

		log("startDateTime- " + startDateTime);
		log("slotId- " + slotId);
		log("patientId- " + patientId);

		String schedPayload = PayloadGE.schedApptForNewPatientPayload(startDateTime, slotId, endTime);

		Response scheduleApptResponse = postAPIRequestge.scheduleApptPatient(schedPayload,
				propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(scheduleApptResponse, 200);
		apiVerification.responseTimeValidation(scheduleApptResponse);
		apiVerification.responseTimeValidation(scheduleApptResponse);
		String id=apiVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		log("Id is   "+id);
		apiVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelledAppointmentStatusPost() throws IOException {
		String body = PayloadGE.cancelledApptStatusPayload();

		
		Response response = postAPIRequestge.cancelAppointmentStatus(body, propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "checkCancelAppointmentStatus");
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelledAppointmentStatusWithoutBodyPost() throws IOException {
		Response response = postAPIRequestge.cancelAppointmentStatus("", propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentGet() throws IOException {
		Response response=postAPIRequestge.cancelAppointment(propertyData.getProperty("appt.status.id.ge"), propertyData.getProperty("practiceid.ge"),
				propertyData.getProperty("patientid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentWithoutAppIdGet() throws IOException {
		Response response=postAPIRequestge.cancelAppointment("", propertyData.getProperty("practiceid.ge"),
				propertyData.getProperty("patientid.ge"));
		apiVerification.responseCodeValidation(response, 500);
		apiVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "Unable to cancel Appointment");
     
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWithCancelReasonPost() throws IOException {
		Response response=postAPIRequestge.cancelApptWithCancelReason(PayloadGE.cancelApptWithCancelReasonPayload(),
				propertyData.getProperty("practiceid.ge"),propertyData.getProperty("sso.patient.id"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelApptWithCancelReasonWithoutBodyPost() throws IOException {
		Response response=postAPIRequestge.cancelApptWithCancelReason("",
				propertyData.getProperty("practiceid.ge"),propertyData.getProperty("sso.patient.id"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsPost() throws IOException {
		String startDate = propertyData.getProperty("start.date.time.ge");
		String endDate = propertyData.getProperty("end.date.time.ge");
		String patientId = propertyData.getProperty("past.patient.id");
		String practiceDisplayName = propertyData.getProperty("practice.display.name.ge");
		String practiceName = propertyData.getProperty("practice.name.ge");
		String practiceId = propertyData.getProperty("practiceid.ge");
		Response response = postAPIRequestge.pastAppointments(PayloadGE.pastappointmentsPayload(startDate, endDate,
				patientId, practiceDisplayName, practiceName, practiceId), practiceId);
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "patientId");
		apiVerification.responseKeyValidationJson(response, "startDateTime");
		apiVerification.responseKeyValidationJson(response, "endDateTime");
		apiVerification.responseKeyValidationJson(response, "comments");

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsWithoutBodyPost() throws IOException {
		Response response=postAPIRequestge.pastAppointments("",propertyData.getProperty("practiceid.ge"));
	apiVerification.responseCodeValidation(response, 400);
	apiVerification.responseTimeValidation(response);
	String message= apiVerification.responseKeyValidationJson(response, "message");
	assertTrue(message.contains("Required request body is missing"));
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcommingApptPOST() throws IOException {
        String body=PayloadGE.upcommingApt_Payload(propertyData.getProperty("upcomingappt.patientid.ge"), propertyData.getProperty("practiceid.ge"),propertyData.getProperty("practice.display.name.ge"), propertyData.getProperty("start.date.time.ge"));	
        Response response =postAPIRequestge.upcomingAppt(body,propertyData.getProperty("practiceid.ge"));
        apiVerification.responseCodeValidation(response, 200);
        apiVerification.responseTimeValidation(response);
        apiVerification.responseKeyValidation(response, "id");
        apiVerification.responseKeyValidation(response, "patientId");
        apiVerification.responseKeyValidation(response, "startDateTime");
        apiVerification.responseKeyValidation(response, "endDateTime");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingApptWithoutBodyPost() throws IOException {
		Response response=postAPIRequestge.upcomingAppt("",propertyData.getProperty("practiceid.ge"));
	apiVerification.responseCodeValidation(response, 400);
	apiVerification.responseTimeValidation(response);
	String message= apiVerification.responseKeyValidationJson(response, "message");
	assertTrue(message.contains("Required request body is missing"));
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingGet() throws IOException {
		Response response=postAPIRequestge.preventScheduling(propertyData.getProperty("practiceid.ge"), propertyData.getProperty("patientid.ge"),
				propertyData.getProperty("appt.id.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreventSchedulingWithoutAppIdGet() throws IOException {
		Response response = postAPIRequestge.preventScheduling(propertyData.getProperty("practiceid.ge"),
				propertyData.getProperty("patientid.ge"), "");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleApptPOST() throws IOException {
		String body=PayloadGE.rescheduleAppointmentPayload(propertyData.getProperty("start.date.time.ge"), propertyData.getProperty("end.date.time.ge"), propertyData.getProperty("location.id.ge"), propertyData.getProperty("patientid.ge"),
				propertyData.getProperty("resource.id.ge"), propertyData.getProperty("slotid.ge"), propertyData.getProperty("appt.id.ge"));
		Response response =postAPIRequestge.rescheduleAppt(body,propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "slotAlreadyTaken");
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleApptWithoutBodyPOST() throws IOException {
		
		Response response =postAPIRequestge.rescheduleAppt("",propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsPost() throws IOException, InterruptedException {

		String date=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		log("Current date is"+date);
        String body=PayloadGE.availableSlotsPayload(propertyData.getProperty("patientid.ge"), propertyData.getProperty("location.id.ge"), propertyData.getProperty("start.date.time.ge"),propertyData.getProperty ("slot.size.ge"), date);	
        Response response =postAPIRequestge.availableSlots(body,propertyData.getProperty("practiceid.ge"));
        apiVerification.responseCodeValidation(response, 200);
        apiVerification.responseKeyValidationJson(response, "availableSlots[0].startDateTime");
        apiVerification.responseKeyValidationJson(response, "availableSlots[0].slotId");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsWithoutBodyPost() throws IOException, InterruptedException {

		
        Response response =postAPIRequestge.availableSlots("",propertyData.getProperty("practiceid.ge"));
        apiVerification.responseCodeValidation(response, 400);
        apiVerification.responseTimeValidation(response);
        String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientPost() throws IOException {
		String body=PayloadGE.addPatientPayload();
		Response response =postAPIRequestge.addPatientPost(body,propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "firstName");
		apiVerification.responseKeyValidationJson(response, "lastName");
		apiVerification.responseKeyValidationJson(response, "dateOfBirth");
		apiVerification.responseKeyValidationJson(response, "emailAddress");
		apiVerification.responseKeyValidationJson(response, "gender");


	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientWithoutBodyPost() throws IOException {
		Response response =postAPIRequestge.addPatientPost("",propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
		

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareProviderAvailabilityPOST() throws IOException {

		String body = PayloadGE.careProviderAvailabilityPayload(propertyData.getProperty("start.date.time.ge"),
				propertyData.getProperty("end.date.time.ge"), propertyData.getProperty("resource.id.ge"),
				propertyData.getProperty("slot.size.ge"), propertyData.getProperty("location.id.ge"),
				propertyData.getProperty("appt.id.ge"));
		Response response = postAPIRequestge.careProviderPost(body, propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseKeyValidationJson(response, "careProvider[0].resourceId");
		apiVerification.responseKeyValidationJson(response, "careProvider[0].nextAvailabledate");

	}

	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareProviderAvailabilityWithoutBodyPOST() throws IOException {
		Response response =postAPIRequestge.careProviderPost("",propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
		

	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsGET() throws IOException {
		Response response=postAPIRequestge.demographicsGET( propertyData.getProperty("practiceid.ge"),propertyData.getProperty("sso.patient.id"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "id");
		apiVerification.responseKeyValidationJson(response, "firstName");
		apiVerification.responseKeyValidationJson(response, "lastName");
		apiVerification.responseKeyValidationJson(response, "dateOfBirth");
		apiVerification.responseKeyValidationJson(response, "emailAddress");

	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsWithouPatientIdGET() throws IOException {
		Response response=postAPIRequestge.demographicsGET( propertyData.getProperty("practiceid.ge"),"");
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Patient Id Is Not Valid"));

	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthOperationGET() throws IOException {
		Response response=postAPIRequestge.healthOperationGET("/actuator/health");
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
	String status=	apiVerification.responseKeyValidationJson(response, "status");
	assertEquals(status,"UP");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthOperationInvalidPathGET() throws IOException {
		Response response=postAPIRequestge.healthOperationGET("/actuator/heal");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientPOST() throws IOException {
		
		Response response = postAPIRequestge.matchPatientPost(PayloadGE.matchPatientPayload(), propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientPOSTWithoutBodyPOST() throws IOException {
		Response response = postAPIRequestge.matchPatientPost("", propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitGET() throws IOException {
	
		Response response=postAPIRequestge.patientLastVisit( propertyData.getProperty("practiceid.ge"),propertyData.getProperty("sso.patient.id"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidationJson(response, "lastVisitDateTime");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitWithoutPatientIdGET() throws IOException {
	
		Response response=postAPIRequestge.patientLastVisit( propertyData.getProperty("practiceid.ge"),"");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreReqAppointmentTypesGET() throws IOException {
		Response response=postAPIRequestge.preReqAppointmentTypes( propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreReqAppointmentTypesWithoutPracticeIdGET() throws IOException {
		Response response=postAPIRequestge.preReqAppointmentTypes("");
		apiVerification.responseCodeValidation(response, 404);
		apiVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchPatientPOST() throws IOException {
		Response response = postAPIRequestge.searchPatientPost(PayloadGE.searchPatientPayload(), propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 200);
		apiVerification.responseTimeValidation(response);
		apiVerification.responseKeyValidation(response, "id");
		apiVerification.responseKeyValidation(response, "firstName");
		apiVerification.responseKeyValidation(response, "lastName");
		apiVerification.responseKeyValidation(response, "dateOfBirth");
		apiVerification.responseKeyValidation(response, "emailAddress");
		apiVerification.responseKeyValidation(response, "gender");


	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchPatientWithoutBodyPOST() throws IOException {
		Response response = postAPIRequestge.searchPatientPost("", propertyData.getProperty("practiceid.ge"));
		apiVerification.responseCodeValidation(response, 400);
		apiVerification.responseTimeValidation(response);
		String message= apiVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

}
