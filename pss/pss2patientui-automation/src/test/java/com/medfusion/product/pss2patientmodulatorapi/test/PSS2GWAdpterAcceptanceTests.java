// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;

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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsearchPatientPOST() throws IOException, InterruptedException {

		Response response = postAPIRequestgw.searchPatient(
				payload.searchPatientPayload(propertyData.getProperty("dateOfBirth.gw"),
						propertyData.getProperty("firstName.gw"), propertyData.getProperty("gender.gw"),
						propertyData.getProperty("lastName.gw"), propertyData.getProperty("practiceTimezone.gw")),
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

		Response response = postAPIRequestgw.pastAppt(propertyData.getProperty("practice.id.gw"),payload.pastApptPayload(propertyData.getProperty("patient.id.gw")));
		JSONArray arr = new JSONArray(response.body().asString());
		log("Id "+arr.getJSONObject(0).getString("id"));
		log("Appointment Type- "+ arr.getJSONObject(0).getJSONObject("appointmentTypes").getString("name"));
		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetPreventSchedulingDate() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.preventSchedulingDate(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"),propertyData.getProperty("extapp.id.gw"));
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
	public void testGetAppointmentTypes() throws NullPointerException, Exception {

		Response response = postAPIRequestgw.insurancecarrier(propertyData.getProperty("practice.id.gw"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
		validateGW.verifyInsurancecCarrierResponse(response);
	}
}
