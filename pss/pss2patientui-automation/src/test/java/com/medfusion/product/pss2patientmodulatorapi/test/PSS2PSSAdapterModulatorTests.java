// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PSSAdapterModulatorTests extends BaseTestNGWebDriver{
	
	public static PayloadAdapterModulator payloadAM;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestAdapterModulator postAPIRequestAM;
	HeaderConfig headerConfig;
	public static String openToken;
	public static String practiceId;

	ValidationAdapterModulator validateAdapter = new ValidationAdapterModulator();
	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;

	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payloadAM = new PayloadAdapterModulator();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestAM = new PostAPIRequestAdapterModulator();
		headerConfig = new HeaderConfig();
	
		practiceId=propertyData.getProperty("practice.id.am");		
		
		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId, payloadAM.openTokenPayload(practiceId));
		
		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeGET() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		postAPIRequestAM.validatePractice(practiceId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncement_Fetch_Save_Delete() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response response=postAPIRequestAM.getAnnouncement(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		
		JSONArray arr = new JSONArray(response.body().asString());
		
		int ann_id=arr.getJSONObject(1).getInt("id");
		String ann_type=arr.getJSONObject(1).getString("type");
		String ann_code=arr.getJSONObject(1).getString("code");
		
		log("announcement Id- "+ann_id);
		log("announcement Type- "+ ann_type);		
		log("announcement code- "+ ann_code);	
		
		String b= payloadAM.saveAnnouncementPayload(ann_id, ann_type, ann_code);
		
		Response responseDeleteAnn=postAPIRequestAM.deleteAnnouncement(practiceId, ann_id);
		aPIVerification.responseCodeValidation(responseDeleteAnn, 200);
				
		Response responseSaveAnn=postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(responseSaveAnn, 200);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementUpdate() throws NullPointerException, Exception {
		
		Response response=postAPIRequestAM.getAnnouncement(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		
		JSONArray arr = new JSONArray(response.body().asString());
		
		int ann_id=arr.getJSONObject(1).getInt("id");
		String ann_type=arr.getJSONObject(1).getString("type");
		String ann_code=arr.getJSONObject(1).getString("code");
		
		log("announcement Id- "+ann_id);
		log("announcement Type- "+ ann_type);		
		log("announcement code- "+ ann_code);	
		
		String b= payloadAM.updateAnnouncementPayload(ann_id, ann_type, ann_code);

		Response responseSaveAnn=postAPIRequestAM.updateAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(responseSaveAnn, 200);
		
		response=postAPIRequestAM.getAnnouncement(practiceId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCode() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		postAPIRequestAM.getAnnouncementByCode(practiceId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptType() throws NullPointerException, Exception {
		
		String b=payloadAM.saveApptTypePayload();
		Response response=postAPIRequestAM.saveAppointmenttype(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptTypeWithoutApptId() throws NullPointerException, Exception {
		
		String b=payloadAM.saveApptTypeWithoutIdPayload();
		Response response =postAPIRequestAM.saveAppointmenttypeInvalid(practiceId,b);	
		
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		aPIVerification.responseTimeValidation(response);
	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptType() throws NullPointerException, Exception {
		
		String b=payloadAM.saveApptTypePayload();
		Response response=postAPIRequestAM.updateAppointmenttype(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b=payloadAM.saveApptTypeWithoutIdPayload();
		Response response =postAPIRequestAM.updateAppointmenttypeInvalid(practiceId,b);
		
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertEquals(message, "Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentTypeById() throws NullPointerException, Exception {
		
		String b=payloadAM.saveApptTypePayload();
		Response response=postAPIRequestAM.updateAppointmenttype(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentTypeByBookId() throws NullPointerException, Exception {

		Response response=postAPIRequestAM.getAppointmentTypeByBookId(practiceId,propertyData.getProperty("book.id.am"));
		validateAdapter.verifyGetAppointmentTypeByBookIdResponse(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenTtype_Save_Delete() throws NullPointerException, Exception {
		
		String b=payloadAM.saveBookAppointmentTypePayload();
		Response response=postAPIRequestAM.saveBookAppointmentType(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
		
		Response deleteResponse=postAPIRequestAM.deleteBookAppointmentType(practiceId);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testreorderAppointmentType() throws NullPointerException, Exception {
		
		String b=payloadAM.reorderApptPayload();
		Response response=postAPIRequestAM.reorderAppointmentType(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testreorderAppointmentTypeInvalid() throws NullPointerException, Exception {

		Response response=postAPIRequestAM.reorderAppointmentTypeWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		log("Body- "+response.getBody().asString());
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_Save_Delete() throws NullPointerException, Exception {
		
		String b=payloadAM.saveBookLocationPayload();
		Response response=postAPIRequestAM.saveBookAppointmentType(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
		
		Response deleteResponse=postAPIRequestAM.deleteBookLocation(practiceId);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

}
