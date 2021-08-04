// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;

import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGW;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
import com.medfusion.product.pss2patientapi.payload.PayloadGW;
import com.medfusion.product.pss2patientapi.validation.ValidationGW;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2GWAdpterAcceptanceTests extends BaseTestNGWebDriver {

	public static PayloadGW payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNG postAPIRequest;
	public static PostAPIRequestGW postAPIRequestgw;

	ValidationGW validateGW = new ValidationGW();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
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
	public void testGetAppointmentStatus() throws NullPointerException, Exception {
		Response response = postAPIRequestgw.appointmentStatus(propertyData.getProperty("practice.id.gw"),
				propertyData.getProperty("appointment.id.gw"), propertyData.getProperty("start.date.time.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyAppointmentStatus(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlots() throws IOException, InterruptedException {

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
	public void testPastAppointmentsPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.pastAppt(propertyData.getProperty("practice.id.gw"),
				payload.pastApptPayload(propertyData.getProperty("patient.id.gw")));
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyPastAppointmentResponse(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPreventSchedulingDate() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.preventSchedulingDate(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("extapp.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetinsuranceCarrier() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.insurancecarrier(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyInsurancecCarrierResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetappointmentTypes() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.appointmenttypes(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyAppointmenttypesResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBook() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.book(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyBookResponse(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetflags() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.flags(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyFlagResponse(response);
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
	public void testHealthCheck() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.healthcheck(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLocations() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.locations(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyLocationsResponse(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetLockout() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.lockout(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyLockoutResponse(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPing() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.ping(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetVersion() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.version(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointments() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.upcommingAppt(propertyData.getProperty("practice.id.gw"),
				payload.upComingAppointmentsPayload(propertyData.getProperty("start.date.time.gw"),
						propertyData.getProperty("patient.id.gw"), propertyData.getProperty("practice.displayname.gw"),
						propertyData.getProperty("practice.id.gw"),
						propertyData.getProperty("practice.displayname.gw")));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyUpcomingAppointmentsResponse(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientlastvisit() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.patientLastVisit(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.addPatient(payload.addPatientPayload(propertyData.getProperty("first.name.gw"),
						propertyData.getProperty("last.name.gw"), propertyData.getProperty("dateofbirth.gw"),
						propertyData.getProperty("email.gw.api")), propertyData.getProperty("practice.id.gw"));
		assertEquals(response.getStatusCode(), 200);
		logStep("Verifying the response");

		validateGW.verifyAddPatientResponse(response);

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
	public void testCancelStatusPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.cancelstatus(payload.cancelStatusPayload(propertyData.getProperty("appointment.type.id"),
						propertyData.getProperty("location.id.gw"), propertyData.getProperty("patient.id.gw"),
						propertyData.getProperty("resource.id")), propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyCancelStateResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw
				.cancelappointment(payload.cancelAppointmentPayload(propertyData.getProperty("cancel.app.id.gw")), propertyData.getProperty("practice.id.gw"), propertyData.getProperty("patient.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyCancelStateResponse(response);

	}
}
