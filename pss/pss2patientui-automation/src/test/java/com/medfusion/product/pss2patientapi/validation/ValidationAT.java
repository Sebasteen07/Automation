// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class ValidationAT extends BaseTestNG {

	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();

	public void verifyAppointmentStatusRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String pid = apiVerification.responseKeyValidationJson(response, "id");
		String status = apiVerification.responseKeyValidationJson(response, "status");
		String appointmentTypeId = apiVerification.responseKeyValidationJson(response, "appointmentTypeId");

		assertEquals(pid, propertyData.getProperty("apptid.at"), "Appointment id is incorrect");
		assertEquals(status, "SCHEDULED", "Appointment's Status is not SCHEDULED");
		assertEquals(appointmentTypeId, "82", "Appointment Type id is not correct");
	}

	public void verifyCancelledAppointmentStatusRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String pid = apiVerification.responseKeyValidationJson(response, "id");
		String status = apiVerification.responseKeyValidationJson(response, "status");
		String appointmentTypeId = apiVerification.responseKeyValidationJson(response, "appointmentTypeId");

		assertEquals(pid, propertyData.getProperty("cancelled.apptid.at"), "Appointment id is incorrect");
		assertEquals(status, "CANCELLED", "Appointment's Status is not CANCELLED");
		assertEquals(appointmentTypeId, propertyData.getProperty("appttype.id.at"),
				"Appointment Type id is not correct");
	}

}
