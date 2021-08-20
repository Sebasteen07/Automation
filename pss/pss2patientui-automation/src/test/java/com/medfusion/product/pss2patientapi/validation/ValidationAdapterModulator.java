// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.validation;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;

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
	
	public void verifyGetAppointmentTypeByBookIdResponse(Response response) throws IOException {
		propertyData = new PSSPropertyFileLoader();

		apiVerification.responseCodeValidation(response, 200);	
		apiVerification.responseTimeValidation(response);
		
		JSONArray arr = new JSONArray(response.body().asString());
		
		int id = arr.getJSONObject(0).getInt("id");
		String displayname= arr.getJSONObject(0).getString("displayName");
		String name= arr.getJSONObject(0).getString("name");
		String category= arr.getJSONObject(0).getString("categoryName");
	
		assertEquals(displayname,  propertyData.getProperty("book.displayname.am"));
		assertEquals(Integer.toString(id),  propertyData.getProperty("book.appt.id.am"));
		assertEquals(name,  propertyData.getProperty("book.name.am"));
		assertEquals(category, propertyData.getProperty("book.categoryname.am"));
	}

}
