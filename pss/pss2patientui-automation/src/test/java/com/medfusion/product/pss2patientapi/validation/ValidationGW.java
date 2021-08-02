// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
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

	public void verifyAppointmentStatus(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		JsonPath jsonPath = new JsonPath(response.asString());
		assertEquals(jsonPath.get("id"), propertyData.getProperty("appointment.id.gw"), "Appointment id is incorrect");
		assertEquals(jsonPath.get("locationName"), propertyData.getProperty("locationname.gw"),
			"LocationName Name incorrect");
		apiVerification.responseKeyValidationJson(response, "id");
	}
	
	public void verifyAvailiableSlotResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();
		apiVerification.responseKeyValidationJson(response, "availableSlots");

}
	public void verifyPatientFlag(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

//		String catName = apiVerification.responseKeyValidation(response, "categoryName");
//		String regflagid = apiVerification.responseKeyValidation(response, "regFlagId");
//		String flaglaBel = apiVerification.responseKeyValidation(response, "flagLabel");
//
//		assertEquals(catName, propertyData.getProperty("cat.name"), "Flag Category name is incorrect");
//		assertEquals(regflagid, propertyData.getProperty("regflag.id"), "flag id incorrect");
//		assertEquals(flaglaBel, propertyData.getProperty("flag.label"), "flag label incorrect");
	}


	public void verifyInsurancecCarrierResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		 apiVerification.responseKeyValidation(response, "id");
		 apiVerification.responseKeyValidation(response, "name");
		 apiVerification.responseKeyValidation(response, "payerId");


		
	}
}
