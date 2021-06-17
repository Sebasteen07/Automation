// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGE;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PSS2GEAdapterAcceptanceTests extends BaseTestNGWebDriver {

	@Test
	public void testHealthCheckGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.healthCheck(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(), testData.getHealthCheckDatabaseName());
	}

	@Test
	public void testPingGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.ping(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testVersionGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.version(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testLockoutGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.lockOut(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testAppointmentTypesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentType(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testBooksGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.books(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testInsuranceCarrierGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testLocationGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.locations(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testActuatorGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.actuator(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void testLastseenProviderPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		log("Verifying resource Id");
		int resourceId =
				postAPIRequest.lastseenProvider(testData.getBasicURI(), PayloadGE.providerLastSeenPayload(), headerConfig.defaultHeader(), testData.getPracticeIdGE());
		String apptId = Integer.toString(resourceId);
		Assert.assertEquals(apptId, testData.getApptid(), "Resource Id is wrong");
	}

	@Test
	public void testAppointmentStatusGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentStatus(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeId(), testData.getApptStatusId(),
				testData.getApptStatusPatientId(), testData.getApptStatusStartDateTime(), testData.getApptid());
	}

	@Test
	public void testScheduleAppointmentPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.scheduleApptPatient(testData.getBasicURI(), PayloadGE.schedApptPayload(), headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testCancelledAppointmentStatusPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointmentStatus(testData.getBasicURI(), PayloadGE.cancelledApptStatusPayload(), headerConfig.defaultHeader(),
				testData.getPracticeId());
	}

	@Test
	public void testCancelAppointmentGet() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointment(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getApptid(), testData.getPracticeId(),
				testData.getPatientId());
	}

	@Test
	public void testCancelApptWithCancelReasonPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelApptWithCancelReason(testData.getBasicURI(), PayloadGE.cancelApptWithCancelReasonPayload(), headerConfig.defaultHeader(),
				testData.getPracticeId(), testData.getSsoPatientId());
	}

	@Test
	public void testPastAppointmentsPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.pastAppointments(testData.getBasicURI(), PayloadGE.pastappointmentsPayload(), headerConfig.defaultHeader(), testData.getPracticeId(),
				testData.getPastAppointmentsResourceName(), testData.getPastAppointmentsLocationname());
	}

	@Test
	public void testUpcommingApptPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		log("Payload- " + PayloadGE.upcommingApt_Payload(testData.getSsoPatientId(), testData.getPracticeIdGE(), testData.getPracticeDisplayName()));

		postAPIRequest.upcomingAppt(testData.getBasicURI(),
				PayloadGE.upcommingApt_Payload(testData.getSsoPatientId(), testData.getPracticeIdGE(), testData.getPracticeDisplayName()), headerConfig.defaultHeader(),
				testData.getPracticeIdGE(), testData.getUpcomingApptresourceName(), testData.getUpcomingApptlocationName());
	}

	@Test
	public void testPreventSchedulingGet() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.preventScheduling(testData.getBasicURI(), headerConfig.defaultHeader(), testData.getPracticeIdGE(), testData.getPatientId(),
				testData.getApptid());
	}

	@Test
	public void testRescheduleApptPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base url- " + testData.getBasicURI());
		log("Payload is -" + PayloadGE.rescheduleAppointmentPayload(testData.getStartDateTime(), testData.getEndDateTime(), testData.getLocationId(),
				testData.getPatientId(), testData.getResourceId(), testData.getSlotId(), testData.getApptid()));

		postAPIRequest.rescheduleAppt(testData.getBasicURI(),
				PayloadGE.rescheduleAppointmentPayload(testData.getStartDateTime(), testData.getEndDateTime(), testData.getLocationId(), testData.getPatientId(),
						testData.getResourceId(), testData.getSlotId(), testData.getApptid()),
				headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testAvailableSlotsPost() throws IOException, InterruptedException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base url- " + testData.getBasicURI());

		log("Payload is -"
				+ PayloadGE.availableSlotsPayload(testData.getPatientId(), testData.getLocationId(), testData.getStartDateTime(), testData.getSlotSize()));

		postAPIRequest.availableSlots(testData.getBasicURI(),
				PayloadGE.availableSlotsPayload(testData.getPatientId(), testData.getLocationId(), testData.getStartDateTime(), testData.getSlotSize()),
				headerConfig.defaultHeader(), testData.getPracticeId());
	}

	@Test
	public void testAddPatientPost() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		PayloadGE payloadGE = new PayloadGE();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		log("Payload- " + payloadGE.addPatientPayload());
		postAPIRequestGE.addPatientPost(testData.getBasicURI(), payloadGE.addPatientPayload(), headerConfig.defaultHeader(), testData.getPracticeIdGE());
	}

	@Test
	public void testCareProviderAvailabilityPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		PayloadGE payloadGE = new PayloadGE();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		log("Payload is " + payloadGE.careProviderAvailabilityPayload(testData.getStartDateTime(), testData.getEndDateTime(), testData.getResourceId(),
				testData.getSlotSize(), testData.getLocationId(), testData.getApptid()));

		postAPIRequestGE.careProviderPost(testData.getBasicURI(), payloadGE.careProviderAvailabilityPayload(testData.getStartDateTime(), testData.getEndDateTime(),
				testData.getResourceId(), testData.getSlotSize(), testData.getLocationId(), testData.getApptid()), headerConfig.defaultHeader(),
				testData.getPracticeIdGE());
	}

	@Test
	public void testDemographicsGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		postAPIRequestGE.demographicsGET(testData.getBasicURI(), testData.getPracticeId(), testData.getDemographicsFirstName(), testData.getDemographicsLastName());
	}

	@Test
	public void testHealthOperationGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		postAPIRequestGE.healthOperationGET(testData.getBasicURI());
	}

	@Test
	public void testMatchPatientPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		PayloadGE payloadGE = new PayloadGE();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		log("Payload is " + payloadGE.matchPatientPayload());
		postAPIRequestGE.matchPatientPost(testData.getBasicURI(), payloadGE.matchPatientPayload(), headerConfig.defaultHeader(), testData.getPracticeIdGE(),
				testData.getMatchPatientId(), testData.getMatchPatientFirstName(), testData.getMatchPatientLastName());
	}

	@Test
	public void testPatientLastVisitGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		postAPIRequestGE.patientLastVisit(testData.getBasicURI(), testData.getPracticeIdGE(), testData.getSsoPatientId());
	}

	@Test
	public void testPreReqAppointmentTypesGET() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		postAPIRequestGE.preReqAppointmentTypes(testData.getBasicURI(), testData.getPracticeIdGE());
	}

	@Test
	public void testSearchPatientPOST() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		PayloadGE payloadGE = new PayloadGE();
		HeaderConfig headerConfig = new HeaderConfig();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequestGE = new PostAPIRequestGE();
		log("Base URL- " + testData.getBasicURI());
		postAPIRequestGE.searchPatientPost(testData.getBasicURI(), payloadGE.searchPatientPayload(), headerConfig.defaultHeader(), testData.getPracticeIdGE());
	}

}
