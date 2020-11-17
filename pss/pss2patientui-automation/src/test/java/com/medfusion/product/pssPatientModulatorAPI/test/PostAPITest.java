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

}
