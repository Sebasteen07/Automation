// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;


public class ValidationGW extends BaseTestNG {
	
	public static PSSPropertyFileLoader propertyData;
	APIVerification apiVerification = new APIVerification();


	public void verifySearchPatientResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		String pid = apiVerification.responseKeyValidation(response, "id");
		String fn = apiVerification.responseKeyValidation(response, "firstName");
		String ln = apiVerification.responseKeyValidation(response, "lastName");

		assertEquals(pid, propertyData.getProperty("patient.id.gw"), "Patient id is incorrect");
		assertEquals(fn, propertyData.getProperty("firstName.gw"), "firstName incorrect");
		assertEquals(ln, propertyData.getProperty("lastName.gw"), "Last Name incorrect");
	}

}
