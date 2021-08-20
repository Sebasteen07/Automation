// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestAdapterModulator;
import com.medfusion.product.pss2patientapi.payload.PayloadAdapterModulator;
import com.medfusion.product.pss2patientapi.validation.ValidationAdapterModulator;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2PSSAdapterModulatorTests extends BaseTestNG{
	
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
		Response response =postAPIRequestAM.validatePractice(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeInvalidGET() throws NullPointerException, Exception {
		
		logStep("Verifying the response for Validate Practice Invalid");
		Response response =postAPIRequestAM.validatePracticeInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
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
	public void testAnnouncementGetInvalid() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response response=postAPIRequestAM.getAnnouncementInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);			
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementSaveInvalid() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response response=postAPIRequestAM.saveAnnouncementWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementDeleteInvalid() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response responseDeleteAnn=postAPIRequestAM.deleteAnnouncementInvalidAnnId(practiceId, 4999);
		aPIVerification.responseCodeValidation(responseDeleteAnn, 500);
		String message= aPIVerification.responseKeyValidationJson(responseDeleteAnn, "message");
		assertTrue(message.contains("No value present"));
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
	public void testAnnouncementUpdateInvalid() throws NullPointerException, Exception {

		Response response=postAPIRequestAM.updateAnnouncementWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCode() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response response=postAPIRequestAM.getAnnouncementByCode(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		
		String type= aPIVerification.responseKeyValidationJson(response, "type");
		String code=aPIVerification.responseKeyValidationJson(response, "code");
		
		assertEquals(type, "Greetings");
		assertEquals(code, "AG");		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCodeInvalid() throws NullPointerException, Exception {
		
		logStep("Verifying the response");
		Response response=postAPIRequestAM.getAnnouncementByInvalidCode(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Invalid announcement type"));
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
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocationSaveInvalid() throws NullPointerException, Exception {

		Response response=postAPIRequestAM.saveBookAppointmentTypeInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocationDeleteInvalid() throws NullPointerException, Exception {
	
		Response response=postAPIRequestAM.deleteBookLocationInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No location found for locationid"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGET_Save_Delete() throws NullPointerException, Exception {
		
		String bookIdActual=propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual=propertyData.getProperty("bookappointmenttype.apptid.am");		
		
		logStep("First verify the Save call -testBookappointmenttypePOST");
		
		String b=payloadAM.bookAppointmentTypeSavePayload(bookIdActual, apptIdActual);
		Response saveResponse=postAPIRequestAM.bookAppointmentTypeSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");

		logStep("Verify the GET call -testBookappointmenttypeGET");
		
		Response response=postAPIRequestAM.bookAppointmentTypeGET(practiceId, bookIdActual , apptIdActual);
		JsonPath js = new JsonPath(response.asString());
		String bookIdExp= js.getString("book.id");
		log("Expected book Id "+bookIdExp);
		String apptIdExp= js.getString("appointmentType.id");
		log("Expected Appt Id "+apptIdExp);
		
		assertEquals(bookIdActual, bookIdExp, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp, "Appointment Id is not matching with input Book_ID ");
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		logStep("Verify the Delete call -BookappointmenttypeDelete");
		
		Response deleteResponse=postAPIRequestAM.bookAppointmentTypeDelete(practiceId, bookIdActual,apptIdActual );
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGETInvalid() throws NullPointerException, Exception {

		logStep("Verify the GET call -testBookappointmenttypeGET");
		
		Response response=postAPIRequestAM.bookAppointmentTypeGETInvalidBookId(practiceId);
	
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for bookid"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGETInvalidApptId() throws NullPointerException, Exception {

		logStep("Verify the GET call -testBookappointmenttypeGET");
		
		Response response=postAPIRequestAM.bookAppointmentTypeGETInvalidApptId(practiceId);
	
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Appointmenttype found for appointmenttypeid"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeSaveInvalid() throws NullPointerException, Exception {

		Response saveResponse=postAPIRequestAM.bookAppointmentTypeSaveInvalid(practiceId);
		aPIVerification.responseCodeValidation(saveResponse, 400);
		aPIVerification.responseTimeValidation(saveResponse);
		String message= aPIVerification.responseKeyValidationJson(saveResponse, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeDeleteInvalid() throws NullPointerException, Exception {
		
		log("Verify the Delete call -BookappointmenttypeDelete");
		
		Response response=postAPIRequestAM.bookAppointmentTypeDeleteInvalid(practiceId );
		
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Appointmenttype found for appointmenttypeid"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01BookAppointmentTypePOST() throws NullPointerException, Exception {
		
		String bookIdActual=propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual=propertyData.getProperty("bookappointmenttype.apptid.am");		
		
		logStep("First verify the Save call -testBookappointmenttypePOST");
		
		String b=payloadAM.bookAppointmentTypeSavePayload(bookIdActual, apptIdActual);
		Response saveResponse=postAPIRequestAM.bookAppointmentTypeSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test02BookAppointmentTypeGET() throws NullPointerException, Exception {
		
		String bookIdActual=propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual=propertyData.getProperty("bookappointmenttype.apptid.am");		

		logStep("Verify the GET call -testBookAppointmenTtypeGET");
		
		Response response=postAPIRequestAM.bookAppointmentTypeGET(practiceId, bookIdActual , apptIdActual);
		JsonPath js = new JsonPath(response.asString());
		String bookIdExp= js.getString("book.id");
		log("Expected book Id "+bookIdExp);
		String apptIdExp= js.getString("appointmentType.id");
		log("Expected Appt Id "+apptIdExp);
		
		assertEquals(bookIdActual, bookIdExp, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp, "Appointment Id is not matching with input Book_ID ");
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test03BookAppointmentTypePUT() throws NullPointerException, Exception {
		logStep("Verify the Update call -testBookappointmenttypePUT");
		
		String bookIdActual=propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual=propertyData.getProperty("bookappointmenttype.apptid.am");	
		
		String c= payloadAM.bookAppointmentTypUpdatePayload(bookIdActual, apptIdActual);
		
		Response updateResponse=postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, c);
		JsonPath js1 = new JsonPath(updateResponse.asString());
		String bookIdExp1= js1.getString("book.id");
		log("Expected book Id "+bookIdExp1);
		String apptIdExp1= js1.getString("appointmentType.id");
		log("Expected Appt Id "+apptIdExp1);
		
		assertEquals(bookIdActual, bookIdExp1, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp1, "Appointment Id is not matching with input Book_ID ");
		
		aPIVerification.responseCodeValidation(updateResponse, 200);
		aPIVerification.responseTimeValidation(updateResponse);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test04BookAppointmentTypeDELETE() throws NullPointerException, Exception {
		
		String bookIdActual=propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual=propertyData.getProperty("bookappointmenttype.apptid.am");		
		
		logStep("Verify the Delete call -BookAppointmentTypeDelete");
		
		Response deleteResponse=postAPIRequestAM.bookAppointmentTypeDelete(practiceId, bookIdActual,apptIdActual );
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05BookLocationSave() throws NullPointerException, Exception {
		
		String bookid=propertyData.getProperty("booklocation.bookid");
		String locationid=propertyData.getProperty("booklocation.locationid");		
		
		logStep("First verify the Save call -BookLocationPost");
		
		String b=payloadAM.bookLocationSavePayload(bookid, locationid);
		Response saveResponse=postAPIRequestAM.bookLocationSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05BookLocationSaveWithoutBody() throws NullPointerException, Exception {

		Response response=postAPIRequestAM.bookLocationSaveWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06BookLocationGET() throws NullPointerException, Exception {
		
		String bookid=propertyData.getProperty("booklocation.bookid.am");
	
		Response response=postAPIRequestAM.bookLocationGET(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "displayName");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06BookLocationGETInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.bookLocationGETInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test07BookLocationDelete() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("booklocation.bookid.am");
		String locationid=propertyData.getProperty("booklocation.locationid.am");			

		Response deleteResponse=postAPIRequestAM.bookLocationDelete(practiceId, bookid, locationid);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test07BookLocationDeleteInvalid() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("booklocation.bookid.am");
		Response response=postAPIRequestAM.bookLocationDeleteInvalid(practiceId, bookid);		
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No location found for locationid"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test08BooksFromDBGET() throws NullPointerException, Exception {		

		Response response =postAPIRequestAM.getBooksFromDB(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- "+response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test08BooksFromDBGETInvalid() throws NullPointerException, Exception {		

		Response response =postAPIRequestAM.getBooksFromDBInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09BookSave() throws NullPointerException, Exception {			
	
		String b= payloadAM.bookSavePayload();

		Response deleteResponse=postAPIRequestAM.saveBook(practiceId, b);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- "+deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09BookSaveWithoutBody() throws NullPointerException, Exception {			

		Response response=postAPIRequestAM.saveBookWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10BookImage() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("bookimage.bookid.am");

		Response deleteResponse=postAPIRequestAM.getBookImage(practiceId, bookid);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10BookImageInvalid() throws NullPointerException, Exception {		
		
		Response response=postAPIRequestAM.getBookImageInvalid(practiceId, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for bookid"));
	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test11BookById() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("book.id.am");
		Response response=postAPIRequestAM.getBookById(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test11BookByIdInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getBookByIdInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Book found for"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12BookReorder() throws NullPointerException, Exception {		
		
		String b=payloadAM.reorderBookPayload();
		
		Response response=postAPIRequestAM.reorderBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12BookReorderWithoutBody() throws NullPointerException, Exception {		
		
		Response response=postAPIRequestAM.reorderBookWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		aPIVerification.responseTimeValidation(response);
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksFromPartnerGET() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getBooksFromPartner(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksFromPartnerGETInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getBooksFromPartnerInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGET() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.practiceBook(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGETInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.practiceBookInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBookLevel() throws NullPointerException, Exception {		
		Response response=postAPIRequestAM.getBookLevel(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "name");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCancelLevel() throws NullPointerException, Exception {		
		Response response=postAPIRequestAM.getCancelLevel(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "name");
	}
		
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBookLevelInvalid() throws NullPointerException, Exception {		
		Response response=postAPIRequestAM.getBookLevelInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 204);
		aPIVerification.responseTimeValidation(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCancelReasonLevel() throws NullPointerException, Exception {		
			
		Response response= postAPIRequestAM.getCancelLevel(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test13CancellationReasonGET() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getPracticeCancellationReason(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test13CancellationReasonGETInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getPracticeCancellationReasonInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test14CancellationReasonSave() throws NullPointerException, Exception {		
		
		String b=payloadAM.saveCancellationReasonPayload();

		Response response=postAPIRequestAM.saveCancellationReason(practiceId,b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test14CancellationReasonSaveInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.saveCancellationReasonInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		aPIVerification.responseTimeValidation(response);
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test15CancellationReasonReorder() throws NullPointerException, Exception {		
		
		String b=payloadAM.reorderCancellationReasonPayload();
		Response response=postAPIRequestAM.reorderCancellationReason(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test15CancellationReasonReorderInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.reorderCancellationReasonInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);	
		aPIVerification.responseTimeValidation(response);
		String message= aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16CancellationReasonDelete() throws NullPointerException, Exception {		
		
		String cancelreasonid=propertyData.getProperty("cancelreason.id.delete.am");
		Response response=postAPIRequestAM.deleteCancellationReason(practiceId, cancelreasonid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16CancellationReasonDeleteInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.deleteCancellationReasonInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No cancellation reason found"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test17CancellationReasonPractice() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getPracticeCancellationReason(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test17CancellationReasonPracticeInvalid() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.getPracticeCancellationReasonInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test18CancellationReasonById() throws NullPointerException, Exception {		
		
		String cancelreasonid=propertyData.getProperty("cancelreason.id.am");
		
		Response response=postAPIRequestAM.getCancellationReasonById(practiceId, cancelreasonid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test18CancellationReasonByIdInvalid() throws NullPointerException, Exception {		
	
		Response response=postAPIRequestAM.getCancellationReasonByIdInvalid(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19BookBySpecialtyIdAndLevelGET() throws NullPointerException, Exception {		
		
		String specialtyid=propertyData.getProperty("specialty.id.am");
		
		Response response=postAPIRequestAM.getBookBySpecialtyIdAndLevel(practiceId, specialtyid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19BookBySpecialtyIdAndLevelGETInvalid() throws NullPointerException, Exception {		
		
		String specialtyid=propertyData.getProperty("specialty.id.am");		
		Response response=postAPIRequestAM.getBookBySpecialtyIdAndLevelInvalid(practiceId, specialtyid);
		aPIVerification.responseCodeValidation(response, 204);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test20BookAssociatedToCareTeamGET() throws NullPointerException, Exception {		
		
		String careteam=propertyData.getProperty("careteam.id.am");
		
		Response response=postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careteam);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test20BookAssociatedToCareTeamGETInvalid() throws NullPointerException, Exception {		
			
		Response response=postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, "7777");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No careteam found for"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test21CareTeamBookSave() throws NullPointerException, Exception {		
		
		String careteam=propertyData.getProperty("careteam.id.am");
		String name=propertyData.getProperty("careteam.name.am");
		String b= payloadAM.saveCareTeamBookPayload(careteam, name);
		
		Response response=postAPIRequestAM.saveCareTeamBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test21CareTeamBookSaveInvalid() throws NullPointerException, Exception {		
		
		String careteam=propertyData.getProperty("careteam.id.am");
		String name=propertyData.getProperty("careteam.name.am");
		String b= payloadAM.saveCareTeamBookPayload(careteam, name);
		
		Response response=postAPIRequestAM.saveCareTeamBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("CareTeam is already associated with Book"));		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test22CareTeamBookByBookIdGET() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("careteam.book.id.am");
		
		Response response=postAPIRequestAM.getCareTeamByBookId(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test22CareTeamBookByBookIdGETInvalid() throws NullPointerException, Exception {		
		
		String bookid="6666";		
		Response response=postAPIRequestAM.getCareTeamByBookId(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id="+bookid));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23CareTeamBookDelete() throws NullPointerException, Exception {		
		
		String careteamid=propertyData.getProperty("careteam.id.am");
		String bookid=propertyData.getProperty("careteambook.book.id.am");
	
		Response response=postAPIRequestAM.deleteCareTeamBook(practiceId, careteamid, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);	
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23BookSpecialtySave() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("specialty.book.id.am");
		String specialtyid=propertyData.getProperty("specialty.id.am");
		String b= payloadAM.saveBookSpecialtyPayload(bookid, specialtyid);
		
		Response response=postAPIRequestAM.saveSpecialty(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23BookSpecialtySaveInvalid() throws NullPointerException, Exception {		
		
    	String b= payloadAM.saveBookSpecialtyPayload("1234", "8888");
		
		Response response=postAPIRequestAM.saveSpecialty(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test24BookSpecialtyDeleteInvalid() throws NullPointerException, Exception {		
				
		Response response=postAPIRequestAM.deleteSpecialty(practiceId, "1234", "8888");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);		
		String message=aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id"));
	}	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test24BookSpecialtyDelete() throws NullPointerException, Exception {		
		
		String bookid=propertyData.getProperty("specialty.book.id.am");
		String specialtyid=propertyData.getProperty("specialty.id.am");
				
		Response response=postAPIRequestAM.deleteSpecialty(practiceId, bookid, specialtyid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		assertEquals(response.getBody().asString(), "true");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamGET_SAVE_DELETE() throws NullPointerException, Exception {		
		
		String b= payloadAM.saveCareTeam();
				
		Response response=postAPIRequestAM.saveCareTeam(practiceId, b);
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		String careteamid=aPIVerification.responseKeyValidationJson(response, "id");
		
		Response getResponse= postAPIRequestAM.getAssociatedCareteam(practiceId);
		
		aPIVerification.responseCodeValidation(getResponse, 200);
		aPIVerification.responseTimeValidation(getResponse);
		
		Response getCareTeamResponse=postAPIRequestAM.getCareTeamById(practiceId, careteamid);
		
		aPIVerification.responseCodeValidation(getCareTeamResponse, 200);
		aPIVerification.responseTimeValidation(getCareTeamResponse);
		
		Response deleteResponse=postAPIRequestAM.deleteCareTeam(practiceId, careteamid);
		
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		aPIVerification.responseTimeValidation(deleteResponse);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategoryGET_SAVE_DELETE() throws NullPointerException, Exception {		
		
		String b= payloadAM.saveCategory();
				
		Response response=postAPIRequestAM.saveCategory(practiceId, b);
		logStep("Validate SaveCategory Response");
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		JSONArray arr = new JSONArray(response.body().asString());
		int categoryid=arr.getJSONObject(0).getInt("id");
		
		Response getResponse= postAPIRequestAM.getcategoryById(practiceId, String.valueOf(categoryid));
		logStep("Validate GetCategoryById Response");
				
		aPIVerification.responseCodeValidation(getResponse, 200);
		aPIVerification.responseTimeValidation(getResponse);
		
		Response getAssociatedCategory=postAPIRequestAM.associatedCategory(practiceId);
		logStep("Validate AssociatedCategory Response");
		
		aPIVerification.responseCodeValidation(getAssociatedCategory, 200);
		aPIVerification.responseTimeValidation(getAssociatedCategory);
		
		String draft=payloadAM.draftCategoryPayload(categoryid);	
		
		Response draftResponse =postAPIRequestAM.saveCategoryDraft(practiceId, draft);
		logStep("Validate DraftCategory Response");
		
		aPIVerification.responseCodeValidation(draftResponse, 200);
		aPIVerification.responseTimeValidation(draftResponse);	
		
		String export= payloadAM.exportCategoryPayload();
		
		Response exportResponse= postAPIRequestAM.exportCategory(practiceId, export);
		logStep("Validate ExportCategory Response");
		
		aPIVerification.responseCodeValidation(exportResponse, 200);
		aPIVerification.responseTimeValidation(exportResponse);
		
		Response deleteResponse=postAPIRequestAM.deleteCategory(practiceId, String.valueOf(categoryid));
		logStep("Validate DeleteCategory Response");
		
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		aPIVerification.responseTimeValidation(deleteResponse);
	}
		
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testImportCategory() throws NullPointerException, Exception {		

		Response response=postAPIRequestAM.importCategory( practiceId, headerConfig.HeaderwithTokenMulti(openToken));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		String categoryid=aPIVerification.responseKeyValidationJson(response, "id");
		String name=aPIVerification.responseKeyValidationJson(response, "name");

		assertEquals(name, "SuperCategory","Category Name is not matching with input");
		
		postAPIRequestAM.deleteCategory(practiceId, String.valueOf(categoryid));
	}
	
}
