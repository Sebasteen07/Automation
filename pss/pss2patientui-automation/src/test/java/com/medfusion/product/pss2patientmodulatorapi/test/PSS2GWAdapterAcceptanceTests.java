// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGW;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
import com.medfusion.product.pss2patientapi.payload.PayloadGW;
import com.medfusion.product.pss2patientapi.validation.ValidationGW;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class PSS2GWAdapterAcceptanceTests extends BaseTestNG {
	public static PayloadGW payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNG postAPIRequest;
	public static PostAPIRequestGW postAPIRequestgw;

	ValidationGW validateGW = new ValidationGW();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	public PSSPatientUtils pssPatientUtils;

	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		pssPatientUtils=new PSSPatientUtils();
		payload = new PayloadGW();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestgw = new PostAPIRequestGW();
		log("I am before Test");
		postAPIRequestgw.setupRequestSpecBuilder(propertyData.getProperty("baseurl.gw"));
		log("BASE URL-" + propertyData.getProperty("baseurl.gw"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetdemographics() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.demographics(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyDemographicsResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetdemographicsWithoutPid() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.demographicsWithoutPid(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		validateGW.verifyDemographicsResponseWithoutPid(response);
		
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsearchPatientPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw.searchPatient(
				payload.searchPatientPayload(propertyData.getProperty("dateofbirth.gw"),
						propertyData.getProperty("first.name.gw"), propertyData.getProperty("gender.gw"),
						propertyData.getProperty("last.name.gw"), propertyData.getProperty("practiceTimezone.gw")),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		validateGW.verifySearchPatientResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsearchPatientWithoutFnamePOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw.searchPatient(
				payload.searchPatientWithoutFnamePayload(propertyData.getProperty("dateofbirth.gw"),
						propertyData.getProperty("first.name.gw"), propertyData.getProperty("gender.gw"),
						propertyData.getProperty("last.name.gw"), propertyData.getProperty("practiceTimezone.gw")),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
		validateGW.verifySearchPatientResponseWithoutFname(response);
		


	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentStatus() throws NullPointerException, Exception {
		Response response = postAPIRequestgw.appointmentStatus(propertyData.getProperty("practice.id.gw"),
				propertyData.getProperty("appointment.id.gw"), propertyData.getProperty("start.date.time.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyAppointmentStatus(response);
}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentStatusWithoutAppId() throws NullPointerException, Exception {
		Response responseWithoutAppId = postAPIRequestgw.appointmentStatusWithoutAppId(
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("start.date.time.gw"));
		logStep("Verifying the response");
		assertEquals(responseWithoutAppId.getStatusCode(), 400);
		validateGW.verifyAppointmentStatusWithoutAppId(responseWithoutAppId);

	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCancelAppointment() throws NullPointerException, Exception {
		Response response = postAPIRequestgw.getcancelappointment(propertyData.getProperty("appointment.id.gw.cancel"),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("patient.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailSlotsScheReScheduleApp() throws IOException, InterruptedException {
		String date=pssPatientUtils.sampleDateTime("MM/dd/yyyy HH:mm:ss");
		log("Current date is"+date);

		Map<String, Object> b=payload.availableslotsPayload(propertyData.getProperty("appointment.cat.id.gw"),
				propertyData.getProperty("appointment.type.id"), propertyData.getProperty("extapp.id.gw"),
				propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("resource.cat.id.gw"), propertyData.getProperty("resource.id"),
				date);
		Response response = postAPIRequestgw
				.avaliableSlot( b,propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

		JsonPath js = new JsonPath(response.asString());

		String startDateTime = js.getString("availableSlots[0].startDateTime");
		String endDateTime = js.getString("availableSlots[0].endDateTime");

		String startDateTime_resch = js.getString("availableSlots[1].startDateTime");
		String endDateTime_resch = js.getString("availableSlots[1].endDateTime");

		String patientId = propertyData.getProperty("patient.id.gw");

		log("startDateTime- " + startDateTime);
		log("endDateTime- " + endDateTime);
		log("patientId- " + patientId);

		log("startDateTime- " + startDateTime_resch);
		log("endDateTime- " + endDateTime_resch);
		log("patientId- " + patientId);

		Response scheduleApptResponse = postAPIRequestgw.scheduleappointment(
				payload.schedulePayload(startDateTime, endDateTime, patientId),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(scheduleApptResponse.getStatusCode(), 200);

		String apptid = aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "rescheduleNotAllowed");

		log("Appointment id - " + apptid);

		Response rescheduleResponse = postAPIRequestgw.reScheduleappointment(
				payload.reschedulePayload(startDateTime_resch, endDateTime_resch, patientId, apptid),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(rescheduleResponse.getStatusCode(), 200);
		String apptid2=aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "rescheduleNotAllowed");
		
		Response responseCancel = postAPIRequestgw.cancelappointment(
				payload.cancelAppointmentPayload(apptid2),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("patient.id.gw"));
		logStep("Verifying the response");
		assertEquals(responseCancel.getStatusCode(), 200);

	}
			
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testScheduleAppWithoutPidPOST() throws IOException, InterruptedException {
		Response scheduleApptResponse = postAPIRequestgw.scheduleappointment(
				payload.scheduleWithoutPidPayload(propertyData.getProperty("start.date.time.gw"), propertyData.getProperty("end.date.time.gw")),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(scheduleApptResponse.getStatusCode(), 400);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvaliableSlot() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.avaliableSlot(payload.availableslotsPayload(propertyData.getProperty("appointment.cat.id.gw"),
						propertyData.getProperty("appointment.type.id"), propertyData.getProperty("extapp.id.gw"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.cat.id.gw"), propertyData.getProperty("resource.id"),
						propertyData.getProperty("start.date.time.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyAvailiableSlotResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvaliableSlotWithoutAppTypeId() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.avaliableSlot(payload.availableslotsWithoutAppIdPayload(propertyData.getProperty("appointment.cat.id.gw"),
						 propertyData.getProperty("extapp.id.gw"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.cat.id.gw"), propertyData.getProperty("resource.id"),
						propertyData.getProperty("start.date.time.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 400);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableSlots() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.nextavaliableSlot(payload.nextAvailableslotsPayload(propertyData.getProperty("appointment.cat.id.gw"),
						propertyData.getProperty("appointment.type.id"), propertyData.getProperty("extapp.id.gw"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.cat.id.gw"), propertyData.getProperty("resource.id"),
						propertyData.getProperty("start.date.time.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testNextAvailableSlotsWithoutProvider() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.nextavaliableSlot(payload.nextAvailableslotsWithoutProviderPayload(propertyData.getProperty("appointment.cat.id.gw"),
						propertyData.getProperty("appointment.type.id"), propertyData.getProperty("extapp.id.gw"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.cat.id.gw"),
						propertyData.getProperty("start.date.time.gw")), propertyData.getProperty("practice.id.gw"));
		assertEquals(response.getStatusCode(), 500);
		validateGW.verifyNextAvailableSlotsWithoutProvider(response);
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsPOST() throws NullPointerException, Exception {
		String practiceDisplayName=propertyData.getProperty("practice.displayname.gw");
		String practiceName=propertyData.getProperty("practice.displayname.gw");
		String practiceId=propertyData.getProperty("practice.id.gw");
		String patientId=propertyData.getProperty("upcoming.past.patientid.gw");
	    String startDate=propertyData.getProperty("start.date.time.gw");
		Response response = postAPIRequestgw.pastAppt(propertyData.getProperty("practice.id.gw"),
				payload.pastAppointmentPayload(practiceDisplayName,practiceName,practiceId,patientId,startDate));
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyPastAppointmentResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsWithoutPidPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.pastApptWithOutPid(propertyData.getProperty("practice.id.gw"),
				payload.pastApptPayloadWithoutPid());
		assertEquals(response.getStatusCode(), 500);
		validateGW.verifyPastAppointmentsWithoutPidPOST(response);
	

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPreventSchedulingDate() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.preventSchedulingDate(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("extapp.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPreventSchedulingDateWithoutExtId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.preventSchedulingDateWithoutExtId(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPreventSchedulingDateWithoutPId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.preventSchedulingDateWithoutPid(propertyData.getProperty("practice.id.gw"),
				propertyData.getProperty("extapp.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetinsuranceCarrier() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.insurancecarrier(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyInsurancecCarrierResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetinsuranceCarrierWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.insurancecarrierWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetappointmentTypes() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.appointmenttypes(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		validateGW.verifyAppointmenttypesResponse(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppoTypesWithoutPracticeId() throws NullPointerException, Exception {		
		Response negResponse = postAPIRequestgw.appointmenttypesWithoutPracticeID();
		assertEquals(negResponse.getStatusCode(), 404);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBook() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.book(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyBookResponse(response);
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBookWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.bookWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetflags() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.flags(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyFlagResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetflagsWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.flagsWithout();
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientGetflags() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientFlags(propertyData.getProperty("practice.id.gw"),
				propertyData.getProperty("patient.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyPatientFlag(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientGetflagsWithoutPId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientFlagsWithoutPId(propertyData.getProperty("practice.id.gw"));
		assertEquals(response.getStatusCode(), 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientGetflagsWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientFlagsWithoutPracticeId(propertyData.getProperty("patient.id.gw"));
		assertEquals(response.getStatusCode(), 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthCheck() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.healthcheck(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testHealthCheckWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.healthcheckWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLocations() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.locations(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyLocationsResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLocationsWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.locationsWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLockout() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.lockout(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyLockoutResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLockoutWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.lockoutWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPing() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.ping(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPingWithoutPracticeID() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.pingWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetVersion() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.version(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetVersionWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.versionWithoutPracticeId();
		assertEquals(response.getStatusCode(), 404);

	}


	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointments() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.upcommingAppt(propertyData.getProperty("practice.id.gw"),
				payload.upComingAppointmentsPayload(propertyData.getProperty("start.date.time.gw"),
						propertyData.getProperty("upcoming.past.patientid.gw"), propertyData.getProperty("practice.displayname.gw"),
						propertyData.getProperty("practice.id.gw"),
						propertyData.getProperty("practice.displayname.gw")));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyUpcomingAppointmentsResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsWithoutPatientId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.upcommingApptWithoutPatientId(propertyData.getProperty("practice.id.gw"),
				payload.upComingAppointmentsPayloadWithoutPatientId(propertyData.getProperty("start.date.time.gw"),
						 propertyData.getProperty("practice.displayname.gw"),
						propertyData.getProperty("practice.id.gw"),
						propertyData.getProperty("practice.displayname.gw")));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		validateGW.verifyUpcomingAppointmentsResponseWithoutPid(response);
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientlastvisit() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientLastVisit(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientlastvisitWithoutPatientId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientLastVisitWithoutPatientId(
				propertyData.getProperty("practice.id.gw"));
		assertEquals(response.getStatusCode(), 404);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientlastvisitWithoutPracticeId() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientLastVisitWithoutPracticeId(propertyData.getProperty("patient.id.gw"));
		assertEquals(response.getStatusCode(), 404);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientPOST() throws IOException, InterruptedException {
		String body = payload.addPatientPayload(propertyData.getProperty("first.name.gw"),
				propertyData.getProperty("last.name.gw"), propertyData.getProperty("dateofbirth.gw"),
				propertyData.getProperty("email.gw.api"));
		Response response = postAPIRequestgw.addPatient(body, propertyData.getProperty("practice.id.gw"));
		assertEquals(response.getStatusCode(), 200);
		logStep("Verifying the response");
		validateGW.verifyAddPatientResponse(response);
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientWithoutFnamePOST() throws IOException, InterruptedException {
		
		String body = payload.addPatientPayloadWithoutFName(propertyData.getProperty("last.name.gw"),
				propertyData.getProperty("dateofbirth.gw"), propertyData.getProperty("email.gw.api"));

		Response negResponse = postAPIRequestgw.addPatient(body, propertyData.getProperty("practice.id.gw"));
		assertEquals(negResponse.getStatusCode(), 400);
		validateGW.verifyAddPatientWithoutFname(negResponse);
		
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.matchPatient(payload.matchPatientPayload(propertyData.getProperty("email.gw.api"),
						propertyData.getProperty("first.name.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientWithoutEmailPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.matchPatient(payload.matchPatientWithoutEmailPayload(propertyData.getProperty("first.name.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 500);
		validateGW.verifyMatchPatientWithoutEmail(response);
		

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelStatusPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.cancelstatus(payload.cancelStatusPayload(propertyData.getProperty("appointment.type.id"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.id"), propertyData.getProperty("cancel.app.id.gw")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyCancelStateResponse(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelStatusWithoutPracticeIdPOST() throws IOException, InterruptedException {
		Response negResponse = postAPIRequestgw.cancelstatuswithoutPracticeId(
				payload.cancelStatusPayload(propertyData.getProperty("appointment.type.id"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.id"), propertyData.getProperty("cancel.app.id.gw")));
		assertEquals(negResponse.getStatusCode(), 404);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentWithInvalidAppIDPOST() throws IOException, InterruptedException {
		Response negResponse = postAPIRequestgw.cancelappointment(
				payload.cancelAppInvalidIdPayload(propertyData.getProperty("invalid.cancel.app.id.gw")),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("patient.id.gw"));
		assertEquals(negResponse.getStatusCode(), 400);
		validateGW.verifyCancelStateResponseWithoutAppId(negResponse);
		

	}
}
