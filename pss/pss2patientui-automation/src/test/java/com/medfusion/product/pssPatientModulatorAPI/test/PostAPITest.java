// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

import java.io.IOException;

import org.testng.annotations.Test;
import org.hamcrest.Matchers.*;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequest;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PostAPITest extends BaseTestNGWebDriver {

	@Test
	public void BookListPost() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		String accessToken = postAPIRequest.getaccessToken(testData.getAccessTokenURL());

		testData.setAccessToken(accessToken);

		log("Endpoint url ---> " + testData.getBaseurl_BookRule());
		log("Access Token --> " + testData.getAccessToken());

		postAPIRequest.bookList(testData.getBaseurl_BookRule(), payload.booklist,
				headerConfig.HeaderwithToken(testData.getAccessToken()));

	}

	@Test
	public void Sched_ApptPatient_PatientPOST() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		String accessToken = postAPIRequest.getaccessToken(testData.getAccessTokenURL());

		testData.setAccessToken(accessToken);

		log("Endpoint url ---> " + testData.getBaseurl_ScheduleAppointment());
		log("Access Token --> " + testData.getAccessToken());

		postAPIRequest.scheduleApptPatient(testData.getBaseurl_ScheduleAppointment(), payload.scheduleApptPatient,
				headerConfig.HeaderwithToken(testData.getAccessToken()));

	}

	@Test
	public void ApptTypePatient() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		String accessToken = postAPIRequest.getaccessToken(testData.getAccessTokenURL());

		testData.setAccessToken(accessToken);

		log("Endpoint url ---> " + testData.getBaseurl_APT());
		log("Access Token --> " + testData.getAccessToken());

		postAPIRequest.appointmenttypesRule(testData.getBaseurl_APT(), payload.apptbody,
				headerConfig.HeaderwithToken(testData.getAccessToken()));

	}

	@Test
	public void AvailableSlotsNGPost() throws IOException, InterruptedException {

		HeaderConfig headerConfig = new HeaderConfig();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		Thread.sleep(1000);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBasicURI());

		log(Payload.nextAvailable_Payload(testData.getPatientId()));

		postAPIRequest.nextAvailableNG(testData.getBasicURI(), Payload.nextAvailable_Payload(testData.getPatientId()),
				headerConfig.defaultHeader());
	}

	@Test
	public void CancellationReasonGET() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		postAPIRequest.cancellationReason(testData.getBasicURI());
	}

	@Test
	public void TestPastApptNgPOST() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Payload is as below-" + Payload.past_appt_payload(testData.getPatientId(), testData.getPracticeDisplayName(), testData.getPracticeId()));

		log("Base URL- " + testData.getBasicURI());

		log("Practice ID- " + testData.getPracticeId());

		log("Practice Display Name- " + testData.getPracticeDisplayName());

		log("Patient Id- " + testData.getPatientId());

		postAPIRequest.pastApptNG(testData.getBasicURI(), Payload.past_appt_payload(testData.getPatientId(),
				testData.getPracticeDisplayName(), testData.getPracticeId()), headerConfig.defaultHeader());

	}

	@Test
	public void nextAvailableSlotPost() throws IOException {

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

		postAPIRequest.nextAvailableNG(testData.getBasicURI(), Payload.nextAvailable_Payload(testData.getPatientId()),
				headerConfig.defaultHeader());

	}

	@Test(dependsOnMethods = "Access_tokenGET")
	public void LocationListPOST() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();

		Payload payload = new Payload();

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		String accessToken = postAPIRequest.getaccessToken(testData.getAccessTokenURL());

		testData.setAccessToken(accessToken);

		log("Endpoint url ---> " + testData.getBaseurl_LocationRule());
		log("Access Token --> " + testData.getAccessToken());

		postAPIRequest.locationList(testData.getBaseurl_LocationRule(), payload.locationlist,
				headerConfig.HeaderwithToken(testData.getAccessToken()));
	}

	@Test
	public void AppointmentStatusGET() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		postAPIRequest.appointmentStatus(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void AppointmentTypesGET() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		postAPIRequest.appointmentType(testData.getBasicURI());

	}

	@Test
	public void Access_tokenGET() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		String accessToken = postAPIRequest.getaccessToken(testData.getAccessTokenURL());

		testData.setAccessToken(accessToken);

		log("The Accesssc Toke is From the Test Method  " + testData.getAccessToken());

	}

	@Test
	public void schedule_Resc_NGPOST() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBasicURI());

		log("Payload- " + Payload.schedule_Payload(testData.getSlotStartTime(), testData.getSlotEndTime()));

		String appointmentId = postAPIRequest.scheduleApptNG(testData.getBasicURI(),
				Payload.schedule_Payload(testData.getSlotStartTime(), testData.getSlotEndTime()),
				headerConfig.defaultHeader());

		postAPIRequest.rescheduleApptNG(testData.getBasicURI(),
				Payload.reschedule_Payload(testData.getStartDateTime(), testData.getEndDateTime(),
						testData.getPatientId(), testData.getFirstName(), testData.getLastName(), appointmentId),
				headerConfig.defaultHeader());

	}
	
	@Test
	public void upcommingApptPOST() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBasicURI());

		log("Payload- " + Payload.upcommingApt_Payload(testData.getPatientId(), testData.getPracticeId(), testData.getPracticeDisplayName()));

		postAPIRequest.upcommingApptNG(testData.getBasicURI(),
				Payload.upcommingApt_Payload(testData.getPatientId(), testData.getPracticeId(), testData.getPracticeDisplayName()),
				headerConfig.defaultHeader());

	}

	@Test
	public void AppointmentTypeListGET() throws IOException {

		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Endpoint url ---> " + testData.getBasicURI());

		postAPIRequest.appointmentType(testData.getBasicURI());
	}

	@Test
	public void RescheduleApptNGPPOST() throws IOException {

		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);

		PostAPIRequest postAPIRequest = new PostAPIRequest();

		log("Base url- " + testData.getBasicURI());
		log("Payload is -" + Payload.reschedule_Payload(testData.getStartDateTime(), testData.getEndDateTime(),
				testData.getPatientId(), testData.getFirstName(), testData.getLastName(), testData.getApptid()));

		postAPIRequest.rescheduleApptNG(testData.getBasicURI(),
				Payload.reschedule_Payload(testData.getStartDateTime(), testData.getEndDateTime(),
						testData.getPatientId(), testData.getFirstName(), testData.getLastName(), testData.getApptid()),
				headerConfig.defaultHeader());

	}

	@Test
	public void cancelAppointmentGet() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointmentGET(testData.getBasicURI(), headerConfig.defaultHeader());

	}

	@Test
	public void cancelAppointmentPost() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		Payload payload = new Payload();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointmentPOST(testData.getBasicURI(), payload.cancelAppointment,
				headerConfig.defaultHeader());

	}

	@Test
	public void cancellationReasonGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancellationReason(testData.getBasicURI(), headerConfig.defaultHeader());

	}
	
	@Test
	public void careproviderAvailabilityPOST() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.careproviderAvailability(testData.getBasicURI(),Payload.careprovideravailability_Payload(),headerConfig.defaultHeader());

	}
	
	@Test
	public void insuranceCarrierGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void locationsListNGGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.locations(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void addPatientPOST() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void demographicsGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.demographics(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void lockoutGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.lockout(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void matchPatientPOST() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		
		Payload payload= new Payload();
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.matchPatientPOST(testData.getBasicURI(),payload.matchpatient, headerConfig.defaultHeader());
	}
	
	@Test
	public void patientLastVisitGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.patientLastVisit(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void patientRecordbyApptTypePOST() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.patientRecordbyApptTypePOST(testData.getBasicURI(), Payload.patientrecordbyapptypes_payload(), headerConfig.defaultHeader());
	}
	
	@Test
	public void searchPatientPOST() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		
		Payload payload= new Payload();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.searchpatient(testData.getBasicURI(),payload.searchpatient, headerConfig.defaultHeader());
	}
	
	
	@Test
	public void patientStatusGET() throws IOException {
		
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.patietStatus(testData.getBasicURI(), headerConfig.defaultHeader());
	}
	
	@Test
	public void prerequisteappointmenttypesPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();

		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.prerequisteappointmenttypesPOST(testData.getBasicURI(), Payload.prerequisteappointmenttypes_Payload(),
				headerConfig.defaultHeader());
	}
	
	@Test
	public void patientrecordbybooksPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();

		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.patientrecordbyBooks(testData.getBasicURI(), Payload.patientrecordbybooks_payload(),
				headerConfig.defaultHeader());
	}
	
	@Test
	public void lastseenProviderPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		Payload payload= new Payload();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.lastseenProvider(testData.getBasicURI(), payload.lastseenprovider,
				headerConfig.defaultHeader());
	}
	
	@Test
	public void fetchNGBookListGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.fetchNGBookList(testData.getBasicURI(),headerConfig.defaultHeader());
	}
	
	@Test
	public void fetchNGSpeciltyListGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIData(testData);
		PostAPIRequest postAPIRequest = new PostAPIRequest();
		
		log("Base URL is   " + testData.getBasicURI());
		
		postAPIRequest.fetchNGSpeciltyList(testData.getBasicURI(),headerConfig.defaultHeader());
	}

}
