// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequest;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PostAPITest extends BaseTestNGWebDriver {
	
	@BeforeTest
	public void generateAccessToken() {
		
	}

	@Test
	public void getBookListTest01() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());

		postAPIRequest.sampleMethod(testData.getBaseurl_BookRule(), payload.booklist, headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void getAvailableSlotsNG() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBasicURI());

		log(Payload.nextAvailable_Payload(testData.getPatientId()));

		postAPIRequest.nextAvailableNG(testData.getBasicURI(), Payload.nextAvailable_Payload(testData.getPatientId()),
				headerConfig.defaultHeader());
	}
	
	@Test
	public void getCancellationReason() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		postAPIRequest.cancellationReason(testData.getBasicURI());
	}
	
	@Test
	public void getPastApptNG() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Payload is as below-" + Payload.past_appt_payload("3665", "PSS - NG", "24249"));

		log("Base URL- " + testData.getBasicURI());

		log("Practice ID- " + testData.getPracticeId());

		log("Practice Display Name- " + testData.getPracticeDisplayName());

		log("Patient Id- " + testData.getPatientId());

		postAPIRequest.pastApptNG(testData.getBasicURI(), Payload.past_appt_payload(testData.getPatientId(),
				testData.getPracticeDisplayName(), testData.getPracticeId()), headerConfig.defaultHeader());

	}
	
	@Test
	public void nextAvailableSlot() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Payload is as below-" + Payload.nextAvailable_Payload(testData.getPatientId()));

		log("Base URL- " + testData.getBasicURI());

		log("Practice ID- " + testData.getPracticeId());

		log("Practice Display Name- " + testData.getPracticeDisplayName());

		log("Patient Id- " + testData.getPatientId());

		postAPIRequest.pastApptNG(testData.getBasicURI(), Payload.past_appt_payload(testData.getPatientId(),
				testData.getPracticeDisplayName(), testData.getPracticeId()), headerConfig.defaultHeader());

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
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	
	@Test
	public void getAppointmentStatus() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		postAPIRequest.appointmentStatus(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	
	@Test
	public void getAppointmentTypes() throws IOException {

		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		postAPIRequest.appointmentType(testData.getBasicURI());
		
	}	
	
	@Test
    public void gettoken() throws IOException {
 

        PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
        Appointment testData = new Appointment();
        propertyData.setRestAPIData(testData);

        PostAPIRequest postAPIRequest = new PostAPIRequest();
    
       String accessToken= postAPIRequest.getaccessToken(testData.getAccessTokenURL());
        
        testData.setAccessToken(accessToken);
        
        log("The Accesssc Toke is From the Test Method  "+testData.getAccessToken());
    
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
				headerConfig.HeaderwithToken(testData.getAccessToken()));
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
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}
	
	@Test
	public void getPracticeStatus() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		
	}

	@Test
	public void cancelAppointmentGet() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getBasicURI());
		postAPIRequest.cancelAppointmentGET(testData.getBasicURI(),headerConfig.defaultHeader());
	
	}
	@Test
	public void cancelAppointmentPost() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		Payload payload = new Payload();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getBasicURI());
		postAPIRequest.cancelAppointmentPOST(testData.getBasicURI(),payload.cancelAppointment,headerConfig.defaultHeader());
	
	}
	@Test
	public void cancellationReasonGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   "+testData.getBasicURI());
		postAPIRequest.cancellationReason(testData.getBasicURI(),headerConfig.defaultHeader());
	
	}
}
