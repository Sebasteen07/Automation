// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
import com.medfusion.product.pss2patientapi.payload.PayloadNG;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2NGAdpterAcceptanceTests extends BaseTestNGWebDriver {

	public static PayloadNG payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNG postAPIRequest;

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		payload = new PayloadNG();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequest = new PostAPIRequestNG();
		log("I am before Test");
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("baseurl.ng"));
		log("BASE URL-" + propertyData.getProperty("baseurl.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AvailableSlotsNGPost() throws IOException, InterruptedException {

		postAPIRequest.availableSlots(PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng")),
				propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void CancellationReasonGET() throws IOException {

		postAPIRequest.cancellationReasonT(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestPastApptNgPOST() throws IOException {

		postAPIRequest.pastApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.past_appt_payload(propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("practice.displayname.ng"),
						propertyData.getProperty("practice.id.ng")));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void nextAvailableSlotPost() throws IOException {

		log("Practice ID- " + propertyData.getProperty("practice.id.ng"));

		log("Practice Display Name- " + propertyData.getProperty("practice.displayname.ng"));

		log("Patient Id- " + propertyData.getProperty("patient.id.ng"));

		postAPIRequest.nextAvailableNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng")));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentStatusGET() throws IOException {

		postAPIRequest.appointmentStatus(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentTypesGET() throws IOException {

		postAPIRequest.appointmentType(propertyData.getProperty("practice.id.ng"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void schedule_Resc_NGPOST() throws IOException {

		String appointmentId = postAPIRequest.scheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.schedule_Payload(propertyData.getProperty("slot.start.time.ng"),
						propertyData.getProperty("slot.end.time.ng")));

		postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.reschedule_Payload(propertyData.getProperty("start.date.time.ng"),
						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"),
						appointmentId));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void upcommingApptPOST() throws IOException {

		postAPIRequest.upcommingApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.upcommingApt_Payload(propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("practice.id.ng"),
						propertyData.getProperty("practice.displayname.ng")));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentTypeListGET() throws IOException {

		postAPIRequest.appointmentType(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void RescheduleApptNGPPOST() throws IOException {

		postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.reschedule_Payload(propertyData.getProperty("start.date.time.ng"),
						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"),
						propertyData.getProperty("appt.id.ng")));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancelAppointmentGet() throws IOException {

		postAPIRequest.cancelAppointmentGET(propertyData.getProperty("practice.id.ng"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancelAppointmentPost() throws IOException {

		postAPIRequest.cancelAppointmentPOST(propertyData.getProperty("practice.id.ng"), payload.cancelAppointment);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancellationReasonGET() throws IOException {

		postAPIRequest.cancellationReason(propertyData.getProperty("practice.id.ng"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void careproviderAvailabilityPOST() throws IOException {

		postAPIRequest.careproviderAvailability(propertyData.getProperty("practice.id.ng"),
				PayloadNG.careprovideravailability_Payload());

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void insuranceCarrierGET() throws IOException {

		postAPIRequest.insuranceCarrier(propertyData.getProperty("practice.id.ng"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void locationsListNGGET() throws IOException {

		postAPIRequest.locations(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void addPatientPOST() throws IOException {

		postAPIRequest.insuranceCarrier(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void demographicsGET() throws IOException {

		postAPIRequest.demographics(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void lockoutGET() throws IOException {

		postAPIRequest.lockout(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void matchPatientPOST() throws IOException {

		PayloadNG payload = new PayloadNG();

		postAPIRequest.matchPatientPOST(propertyData.getProperty("practice.id.ng"), payload.matchpatient);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientLastVisitGET() throws IOException {

		postAPIRequest.patientLastVisit(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientRecordbyApptTypePOST() throws IOException {

		postAPIRequest.patientRecordbyApptTypePOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbyapptypes_payload());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void searchPatientPOST() throws IOException {

		postAPIRequest.searchpatient(propertyData.getProperty("practice.id.ng"), payload.searchpatient);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientStatusGET() throws IOException {

		postAPIRequest.patietStatus(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void prerequisteappointmenttypesPOST() throws IOException {

		postAPIRequest.prerequisteappointmenttypesPOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.prerequisteappointmenttypes_Payload());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientrecordbybooksPOST() throws IOException {

		postAPIRequest.patientrecordbyBooks(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbybooks_payload());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void lastseenProviderPOST() throws IOException {

		postAPIRequest.lastseenProvider(propertyData.getProperty("practice.id.ng"), payload.lastseenprovider);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void fetchNGBookListGET() throws IOException {

		postAPIRequest.fetchNGBookList(propertyData.getProperty("practice.id.ng"));
	}

}
