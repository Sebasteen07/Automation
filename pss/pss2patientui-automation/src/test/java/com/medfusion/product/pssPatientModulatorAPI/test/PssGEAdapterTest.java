// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

import java.io.IOException;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestGE;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

public class PssGEAdapterTest extends BaseTestNGWebDriver {

	@Test
	public void healthCheckGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.healthCheck(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void pingGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.ping(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void versionGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.version(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void lockoutGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.lockOut(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void appointmentTypesGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentType(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void booksGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.books(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void CancellationReasonGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancellationReason(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void insuranceCarrierGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.insuranceCarrier(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void specialtyGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.specialty(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void locationGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.locations(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void actuatorGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.actuator(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void lastseenProviderPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.lastseenProvider(testData.getBasicURI(), PayloadGE.providerLastSeenPayload(), headerConfig.defaultHeader());
	}

	@Test
	public void AppointmentStatusGET() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIData(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.appointmentStatus(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void scheduleAppointmentPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.scheduleApptPatient(testData.getBasicURI(), PayloadGE.schedApptPayload(), headerConfig.defaultHeader());

	}

	@Test
	public void CancelledAppointmentStatusPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointment(testData.getBasicURI(), PayloadGE.cancelledApptStatusPayload(), headerConfig.defaultHeader());
	}

	@Test
	public void cancelAppointmentGet() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelAppointment(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void cancelApptWithCancelReasonPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.cancelApptWithCancelReason(testData.getBasicURI(), PayloadGE.cancelApptWithCancelReasonPayload(), headerConfig.defaultHeader());
	}

	@Test
	public void pastAppointmentsPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.pastAppointments(testData.getBasicURI(), PayloadGE.pastappointmentsPayload(), headerConfig.defaultHeader());
	}

	@Test
	public void upcommingApptPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();

		log("Base URL is   " + testData.getBasicURI());
		log("Payload- " + PayloadGE.upcommingApt_Payload(testData.getPatientId(), testData.getPracticeId(), testData.getPracticeDisplayName()));

		postAPIRequest.upcommingAppt(testData.getBasicURI(),
				PayloadGE.upcommingApt_Payload(testData.getPatientId(), testData.getPracticeId(), testData.getPracticeDisplayName()), headerConfig.defaultHeader());
	}

	@Test
	public void preventSchedulingGet() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();
		log("Base URL is   " + testData.getBasicURI());
		postAPIRequest.preventScheduling(testData.getBasicURI(), headerConfig.defaultHeader());
	}

	@Test
	public void rescheduleApptPOST() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();

		log("Base url- " + testData.getBasicURI());
		log("Payload is -" + PayloadGE.rescheduleAppointmentPayload(testData.getStartDateTime(), testData.getEndDateTime(), testData.getLocationId(),
				testData.getPatientId(), testData.getResourceId(), testData.getSlotId(), testData.getApptid()));

		postAPIRequest.rescheduleAppt(testData.getBasicURI(), PayloadGE.rescheduleAppointmentPayload(testData.getStartDateTime(), testData.getEndDateTime(),
				testData.getLocationId(), testData.getPatientId(), testData.getResourceId(), testData.getSlotId(), testData.getApptid()), headerConfig.defaultHeader());
	}

	@Test
	public void availableSlotsPost() throws IOException, InterruptedException {
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
				headerConfig.defaultHeader());
	}

	@Test
	public void nextAvailableSlotPost() throws IOException {
		HeaderConfig headerConfig = new HeaderConfig();
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		Appointment testData = new Appointment();
		propertyData.setRestAPIDataGE(testData);
		PostAPIRequestGE postAPIRequest = new PostAPIRequestGE();

		log("Base URL- " + testData.getBasicURI());
		log("Payload is as below-" + PayloadGE.nextAvailableSlotsPayload(testData.getPatientId(), testData.getApptid(), testData.getLocation(),
				testData.getResourceId(), testData.getStartDateTime(), testData.getSlotSize()));

		log("Patient Id- " + testData.getPatientId());

		postAPIRequest.nextAvailableSlots(testData.getBasicURI(), PayloadGE.nextAvailableSlotsPayload(testData.getPatientId(), testData.getApptid(),
				testData.getLocation(), testData.getResourceId(), testData.getStartDateTime(), testData.getSlotSize()), headerConfig.defaultHeader());

	}

}
