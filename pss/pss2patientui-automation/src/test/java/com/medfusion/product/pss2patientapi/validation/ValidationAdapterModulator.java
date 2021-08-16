// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;

public class ValidationAdapterModulator {
	
	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();
	
	public void verifyAppointmentStatusWithoutPatientIdRponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String message = apiVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Required String parameter 'patientId' is not present", "Message is not valid");
		apiVerification.responseTimeValidation(response);
	}

}
