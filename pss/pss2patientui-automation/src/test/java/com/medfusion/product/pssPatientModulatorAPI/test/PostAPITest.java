// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

import java.io.IOException;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequest;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PostAPITest extends BaseTestNGWebDriver {

	@Test
	public void getBookListTest01() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());

		postAPIRequest.sampleMethod(testData.getBaseurl_BookRule(), payload.booklist, headerConfig.HeaderwithToken());
	}

	@Test
	public void getLocationListTest02() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());

		postAPIRequest.sampleMethod(testData.getBaseurl_LocationRule(), payload.locationlist,
				headerConfig.HeaderwithToken());
	}

	@Test
	public void getAvailableSlotsTest03() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_AvailableSlots());

		postAPIRequest.sampleMethod01(testData.getBaseurl_AvailableSlots(), payload.availableslots,
				headerConfig.HeaderwithToken());
	}

	@Test
	public void getScheduleAppointmentTest04() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());

		postAPIRequest.sampleMethod(testData.getBaseurl_ScheduleAppointment(), payload.scheduleappt,
				headerConfig.HeaderwithToken());
	}

	@Test
	public void getAppointmentTypeListTest05() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());

		postAPIRequest.sampleMethod(testData.getBaseurl_AppointmentType(), payload.apptbody,
				headerConfig.HeaderwithToken());
	}
	
	@Test
	public void gettoken() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Endpoint url ---> " + testData.getAccessTokenURL());
		postAPIRequest.accessToken(testData.getAccessTokenURL());
		testData.setAccessToken(postAPIRequest.accessToken(testData.getAccessTokenURL()));
		log("The Accesssc Toke is From the Test Method  "+testData.getAccessToken());
	
	}
	@Test
	public void cancelAppointmentGet() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getNg_adapterBaseURL());
		postAPIRequest.cancelAppointmentGET(testData.getNg_adapterBaseURL(),headerConfig.defaultHeader());
	
	}
	@Test
	public void cancelAppointmentPost() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		Payload payload = new Payload();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getNg_adapterBaseURL());
		postAPIRequest.cancelAppointmentPOST(testData.getNg_adapterBaseURL(),payload.cancelAppointment,headerConfig.defaultHeader());
	
	}
	@Test
	public void cancellationReasonGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getNg_adapterBaseURL());
		postAPIRequest.cancellationReason(testData.getNg_adapterBaseURL(),headerConfig.defaultHeader());
	
	}
}
