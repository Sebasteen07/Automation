// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

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

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2ATAdpterAcceptanceTests extends BaseTestNGWebDriver {

	public static PayloadAT payload;
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
		payload = new PayloadAT();
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
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCanceledAppointmentStatusGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.appointmentStatus(propertyData.getProperty("patient.id.at"),
				propertyData.getProperty("practice.id.at"), "1742653", propertyData.getProperty("start.date.time.at"));
		validateAT.verifyCancelledAppointmentStatusRponse(response);
		log("Payload- " + payload.pastApptPayload(propertyData.getProperty("patient.id.at"),
				propertyData.getProperty("start.date.time.at")));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testcancelappointmentGET() throws NullPointerException, Exception {

		Response response = postAPIRequestat.cancelappt(propertyData.getProperty("practice.id.at"),
				propertyData.getProperty("cancelled.apptid.at"), propertyData.getProperty("patient.id.at"));
		log("Response- " + response.asString());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastAppointmentsPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestat.pastAppt(propertyData.getProperty("practice.id.at"),
				propertyData.getProperty("cancelled.apptid.at"), propertyData.getProperty("patient.id.at"));
		log("Response- " + response.asString());
	}

}
