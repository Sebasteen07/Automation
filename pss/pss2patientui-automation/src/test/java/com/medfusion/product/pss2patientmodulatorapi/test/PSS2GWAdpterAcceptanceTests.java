package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGW;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
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
		assertEquals(response.getStatusCode(), 200);
	}

}
