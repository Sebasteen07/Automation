// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.sql.Timestamp;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAT;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGW;
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
	public static PostAPIRequestGW postAPIRequestgw;

	ValidationAT validateAT = new ValidationAT();
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payload = new PayloadAT();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestat = new PostAPIRequestAT();
		log("I am before Test for Athena Partner");
		postAPIRequestat.setupRequestSpecBuilder(propertyData.getProperty("baseurl.at"));
		log("BASE URL-" + propertyData.getProperty("baseurl.at"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentStatus() throws NullPointerException, Exception {

		Response response = postAPIRequestat.appointmentStatus(propertyData.getProperty("patient.id.gw"),
				propertyData.getProperty("practice.id.gw"), propertyData.getProperty("apptid.at"),
				propertyData.getProperty("start.date.time.at"));
		logStep("Verifying the response");
		assertEquals(response.getStatusCode(), 200);
	}
}
