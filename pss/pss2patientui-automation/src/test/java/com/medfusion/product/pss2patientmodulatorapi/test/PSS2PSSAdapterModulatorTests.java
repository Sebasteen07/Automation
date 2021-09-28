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

public class PSS2PSSAdapterModulatorTests extends BaseTestNG {

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

		practiceId = propertyData.getProperty("practice.id.am");

		log("BASE URL AM -" + propertyData.getProperty("base.url.am"));
		openToken = postAPIRequestAM.openToken(propertyData.getProperty("base.url.am"), practiceId,
				payloadAM.openTokenPayload(practiceId));

		postAPIRequestAM.setupRequestSpecBuilder(propertyData.getProperty("base.url.am"),
				headerConfig.HeaderwithToken(openToken));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.validatePractice(practiceId, "validatepractice");
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeInvalidGET() throws NullPointerException, Exception {

		logStep("Verifying the response for Validate Practice Invalid");
		Response response = postAPIRequestAM.validatePractice(practiceId, "/validatepracticee");
		aPIVerification.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncement_Fetch_Save_Delete() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);

		JSONArray arr = new JSONArray(response.body().asString());

		int ann_id = arr.getJSONObject(1).getInt("id");
		String ann_type = arr.getJSONObject(1).getString("type");
		String ann_code = arr.getJSONObject(1).getString("code");

		log("announcement Id- " + ann_id);
		log("announcement Type- " + ann_type);
		log("announcement code- " + ann_code);

		String b = payloadAM.saveAnnouncementPayload(ann_id, ann_type, ann_code);

		Response responseDeleteAnn = postAPIRequestAM.deleteAnnouncement(practiceId, ann_id);
		aPIVerification.responseCodeValidation(responseDeleteAnn, 200);

		Response responseSaveAnn = postAPIRequestAM.saveAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(responseSaveAnn, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementGetInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.getAnnouncement(practiceId, "/announcementt");
		aPIVerification.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementSaveInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.saveAnnouncement(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementDeleteInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response responseDeleteAnn = postAPIRequestAM.deleteAnnouncement(practiceId, 4999);
		aPIVerification.responseCodeValidation(responseDeleteAnn, 500);
		String message = aPIVerification.responseKeyValidationJson(responseDeleteAnn, "message");
		assertTrue(message.contains("No value present"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementUpdate() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
		aPIVerification.responseCodeValidation(response, 200);

		JSONArray arr = new JSONArray(response.body().asString());

		int ann_id = arr.getJSONObject(1).getInt("id");
		String ann_type = arr.getJSONObject(1).getString("type");
		String ann_code = arr.getJSONObject(1).getString("code");

		log("announcement Id- " + ann_id);
		log("announcement Type- " + ann_type);
		log("announcement code- " + ann_code);

		String b = payloadAM.updateAnnouncementPayload(ann_id, ann_type, ann_code);

		Response responseSaveAnn = postAPIRequestAM.updateAnnouncement(practiceId, b);
		aPIVerification.responseCodeValidation(responseSaveAnn, 200);

		response = postAPIRequestAM.getAnnouncement(practiceId, "/announcement");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementUpdateInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.updateAnnouncement(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.getAnnouncementByCode(practiceId, "AG");
		aPIVerification.responseCodeValidation(response, 200);

		String type = aPIVerification.responseKeyValidationJson(response, "type");
		String code = aPIVerification.responseKeyValidationJson(response, "code");

		assertEquals(type, "Greetings");
		assertEquals(code, "AG");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCodeInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestAM.getAnnouncementByCode(practiceId, "ZZZ");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Invalid announcement type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptType() throws NullPointerException, Exception {

		String b = payloadAM.saveApptTypePayload();
		Response response = postAPIRequestAM.saveAppointmenttype(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = payloadAM.saveApptTypeWithoutIdPayload();
		Response response = postAPIRequestAM.saveAppointmenttype(practiceId, b);

		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertEquals(message,
				"Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptType() throws NullPointerException, Exception {

		String b = payloadAM.saveApptTypePayload();
		Response response = postAPIRequestAM.updateAppointmenttype(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = payloadAM.saveApptTypeWithoutIdPayload();
		Response response = postAPIRequestAM.updateAppointmenttype(practiceId, b);

		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertEquals(message,
				"Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentTypeById() throws NullPointerException, Exception {

		String b = payloadAM.saveApptTypePayload();
		Response response = postAPIRequestAM.getAppointmentTypeById(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentTypeByBookId() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getAppointmentTypeByBookId(practiceId,
				propertyData.getProperty("book.id.am"));
		validateAdapter.verifyGetAppointmentTypeByBookIdResponse(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenTtype_Save_Delete() throws NullPointerException, Exception {

		String b = payloadAM.saveBookAppointmentTypePayload();
		Response response = postAPIRequestAM.saveBookAppointmentType(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");

		Response deleteResponse = postAPIRequestAM.deleteBookAppointmentType(practiceId);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testreorderAppointmentType() throws NullPointerException, Exception {

		String b = payloadAM.reorderApptPayload();
		Response response = postAPIRequestAM.reorderAppointmentType(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testreorderAppointmentTypeInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.reorderAppointmentType(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		log("Body- " + response.getBody().asString());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_Save_Delete() throws NullPointerException, Exception {

		String b = payloadAM.saveBookLocationPayload();
		Response response = postAPIRequestAM.saveBookAppointmentType(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");

		Response deleteResponse = postAPIRequestAM.deleteBookLocation(practiceId,
				propertyData.getProperty("booklocation.delete.id.am"));
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocationSaveInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.saveBookAppointmentType(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocationDeleteInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteBookLocation(practiceId, "40610");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No location found for locationid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGET_Save_Delete() throws NullPointerException, Exception {

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		logStep("First verify the Save call -testBookappointmenttypePOST");

		String b = payloadAM.bookAppointmentTypeSavePayload(bookIdActual, apptIdActual);
		Response saveResponse = postAPIRequestAM.bookAppointmentTypeSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");

		logStep("Verify the GET call -testBookappointmenttypeGET");

		Response response = postAPIRequestAM.bookAppointmentTypeGET(practiceId, bookIdActual, apptIdActual);
		JsonPath js = new JsonPath(response.asString());
		String bookIdExp = js.getString("book.id");
		log("Expected book Id " + bookIdExp);
		String apptIdExp = js.getString("appointmentType.id");
		log("Expected Appt Id " + apptIdExp);

		assertEquals(bookIdActual, bookIdExp, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp, "Appointment Id is not matching with input Book_ID ");

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		logStep("Verify the Delete call -BookappointmenttypeDelete");

		Response deleteResponse = postAPIRequestAM.bookAppointmentTypeDelete(practiceId, bookIdActual, apptIdActual);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGETInvalid() throws NullPointerException, Exception {

		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		logStep("Verify the GET call -testBookappointmenttypeGET");

		Response response = postAPIRequestAM.bookAppointmentTypeGET(practiceId, "12345", apptIdActual);

		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for bookid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeGETInvalidApptId() throws NullPointerException, Exception {

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");

		logStep("Verify the GET call -testBookappointmenttypeGET");

		Response response = postAPIRequestAM.bookAppointmentTypeGET(practiceId, bookIdActual, "12345");

		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Appointmenttype found for appointmenttypeid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeSaveInvalid() throws NullPointerException, Exception {

		Response saveResponse = postAPIRequestAM.bookAppointmentTypeSave(practiceId, "");
		aPIVerification.responseCodeValidation(saveResponse, 400);
		aPIVerification.responseTimeValidation(saveResponse);
		String message = aPIVerification.responseKeyValidationJson(saveResponse, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppointmenttypeDeleteInvalid() throws NullPointerException, Exception {

		log("Verify the Delete call -BookappointmenttypeDelete");

		Response response = postAPIRequestAM.bookAppointmentTypeDelete(practiceId, "206501", "200033");

		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Appointmenttype found for appointmenttypeid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test01BookAppointmentTypePOST() throws NullPointerException, Exception {

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		logStep("First verify the Save call -testBookappointmenttypePOST");

		String b = payloadAM.bookAppointmentTypeSavePayload(bookIdActual, apptIdActual);
		Response saveResponse = postAPIRequestAM.bookAppointmentTypeSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test02BookAppointmentTypeGET() throws NullPointerException, Exception {

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		logStep("Verify the GET call -testBookAppointmenTtypeGET");

		Response response = postAPIRequestAM.bookAppointmentTypeGET(practiceId, bookIdActual, apptIdActual);
		JsonPath js = new JsonPath(response.asString());
		String bookIdExp = js.getString("book.id");
		log("Expected book Id " + bookIdExp);
		String apptIdExp = js.getString("appointmentType.id");
		log("Expected Appt Id " + apptIdExp);

		assertEquals(bookIdActual, bookIdExp, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp, "Appointment Id is not matching with input Book_ID ");

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test03BookAppointmentTypePUT() throws NullPointerException, Exception {
		logStep("Verify the Update call -testBookappointmenttypePUT");

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		String c = payloadAM.bookAppointmentTypUpdatePayload(bookIdActual, apptIdActual);

		Response updateResponse = postAPIRequestAM.bookAppointmentTypeUpdate(practiceId, c);
		JsonPath js1 = new JsonPath(updateResponse.asString());
		String bookIdExp1 = js1.getString("book.id");
		log("Expected book Id " + bookIdExp1);
		String apptIdExp1 = js1.getString("appointmentType.id");
		log("Expected Appt Id " + apptIdExp1);

		assertEquals(bookIdActual, bookIdExp1, "Book Id is not matching with input Book_ID ");
		assertEquals(apptIdActual, apptIdExp1, "Appointment Id is not matching with input Book_ID ");

		aPIVerification.responseCodeValidation(updateResponse, 200);
		aPIVerification.responseTimeValidation(updateResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test04BookAppointmentTypeDELETE() throws NullPointerException, Exception {

		String bookIdActual = propertyData.getProperty("bookappointmenttype.bookid.am");
		String apptIdActual = propertyData.getProperty("bookappointmenttype.apptid.am");

		logStep("Verify the Delete call -BookAppointmentTypeDelete");

		Response deleteResponse = postAPIRequestAM.bookAppointmentTypeDelete(practiceId, bookIdActual, apptIdActual);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05BookLocationSave() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("booklocation.bookid");
		String locationid = propertyData.getProperty("booklocation.locationid");

		logStep("First verify the Save call -BookLocationPost");

		String b = payloadAM.bookLocationSavePayload(bookid, locationid);
		Response saveResponse = postAPIRequestAM.bookLocationSave(practiceId, b);
		aPIVerification.responseCodeValidation(saveResponse, 200);
		aPIVerification.responseTimeValidation(saveResponse);
		assertEquals(saveResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test05BookLocationSaveWithoutBody() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.bookLocationSave(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06BookLocationGET() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("booklocation.bookid.am");

		Response response = postAPIRequestAM.bookLocationGET(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test06BookLocationGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.bookLocationGET(practiceId, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test07BookLocationDelete() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("booklocation.bookid.am");
		String locationid = propertyData.getProperty("booklocation.locationid.am");

		Response deleteResponse = postAPIRequestAM.bookLocationDelete(practiceId, bookid, locationid);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test07BookLocationDeleteInvalid() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("booklocation.bookid.am");
		Response response = postAPIRequestAM.bookLocationDelete(practiceId, bookid, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No location found for locationid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test08BooksFromDBGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBooksFromDB(practiceId, "/associatedbook");
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test08BooksFromDBGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBooksFromDB(practiceId, "/associatedbookk");
		aPIVerification.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09BookSave() throws NullPointerException, Exception {

		String b = payloadAM.bookSavePayload();

		Response deleteResponse = postAPIRequestAM.saveBook(practiceId, b);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
		log("Body- " + deleteResponse.getBody().asString());
		assertEquals(deleteResponse.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test09BookSaveWithoutBody() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.saveBook(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10BookImage() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("bookimage.bookid.am");

		Response deleteResponse = postAPIRequestAM.getBookImage(practiceId, bookid);
		aPIVerification.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test10BookImageInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBookImage(practiceId, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for bookid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test11BookById() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("book.id.am");
		Response response = postAPIRequestAM.getBookById(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test11BookByIdInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBookById(practiceId, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No Book found for"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12BookReorder() throws NullPointerException, Exception {

		String b = payloadAM.reorderBookPayload();

		Response response = postAPIRequestAM.reorderBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test12BookReorderWithoutBody() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.reorderBook(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksFromPartnerGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBooksFromPartner(practiceId, "/partnerbook");
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksFromPartnerGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBooksFromPartner(practiceId, "/partnerbookkk");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.practiceBook(practiceId, "/practicebook");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBooksGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.practiceBook(practiceId, "/practicebookk");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBookLevel() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.getBookLevel(practiceId, "RESOURCE_LEVEL");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCancelLevel() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.getBookLevel(practiceId, "CANCEL_REASON");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetBookLevelInvalid() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.getBookLevel(practiceId, "abcd");
		aPIVerification.responseCodeValidation(response, 204);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetCancelReasonLevel() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBookLevel(practiceId, "CANCEL_REASON");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test13CancellationReasonGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getPracticeCancellationReason(practiceId, "/practicecancellationreason");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test13CancellationReasonGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getPracticeCancellationReason(practiceId, "/practicecancellationreasonnn");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test14CancellationReasonSave() throws NullPointerException, Exception {

		String b = payloadAM.saveCancellationReasonPayload();

		Response response = postAPIRequestAM.saveCancellationReason(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test14CancellationReasonSaveInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.saveCancellationReason(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test15CancellationReasonReorder() throws NullPointerException, Exception {

		String b = payloadAM.reorderCancellationReasonPayload();
		Response response = postAPIRequestAM.reorderCancellationReason(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test15CancellationReasonReorderInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.reorderCancellationReason(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16CancellationReasonDelete() throws NullPointerException, Exception {

		String cancelreasonid = propertyData.getProperty("cancelreason.id.delete.am");
		Response response = postAPIRequestAM.deleteCancellationReason(practiceId, cancelreasonid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test16CancellationReasonDeleteInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteCancellationReason(practiceId, "1222");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No cancellation reason found"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test17CancellationReasonPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getPracticeCancellationReason(practiceId, "/practicecancellationreason");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test17CancellationReasonPracticeInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getPracticeCancellationReason(practiceId, "/practicecancellationreason");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test18CancellationReasonById() throws NullPointerException, Exception {

		String cancelreasonid = propertyData.getProperty("cancelreason.id.am");

		Response response = postAPIRequestAM.getCancellationReasonById(practiceId, cancelreasonid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test18CancellationReasonByIdInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getCancellationReasonById(practiceId, "1222");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19BookBySpecialtyIdAndLevelGET() throws NullPointerException, Exception {

		String specialtyid = propertyData.getProperty("specialty.id.am");

		Response response = postAPIRequestAM.getBookBySpecialtyIdAndLevel(practiceId, specialtyid, "RS_L1");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test19BookBySpecialtyIdAndLevelGETInvalid() throws NullPointerException, Exception {

		String specialtyid = propertyData.getProperty("specialty.id.am");
		Response response = postAPIRequestAM.getBookBySpecialtyIdAndLevel(practiceId, specialtyid, "RS_L10");
		aPIVerification.responseCodeValidation(response, 204);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test20BookAssociatedToCareTeamGET() throws NullPointerException, Exception {

		String careteam = propertyData.getProperty("careteam.id.am");

		Response response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, careteam);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test20BookAssociatedToCareTeamGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getBookAssociatedToCareTeam(practiceId, "7777");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No careteam found for"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test21CareTeamBookSave() throws NullPointerException, Exception {

		String careteam = propertyData.getProperty("careteam.id.am");
		String name = propertyData.getProperty("careteam.name.am");
		String b = payloadAM.saveCareTeamBookPayload(careteam, name);

		Response response = postAPIRequestAM.saveCareTeamBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test21CareTeamBookSaveInvalid() throws NullPointerException, Exception {

		String careteam = propertyData.getProperty("careteam.id.am");
		String name = propertyData.getProperty("careteam.name.am");
		String b = payloadAM.saveCareTeamBookPayload(careteam, name);

		Response response = postAPIRequestAM.saveCareTeamBook(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("CareTeam is already associated with Book"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test22CareTeamBookByBookIdGET() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("careteam.book.id.am");

		Response response = postAPIRequestAM.getCareTeamByBookId(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test22CareTeamBookByBookIdGETInvalid() throws NullPointerException, Exception {

		String bookid = "6666";
		Response response = postAPIRequestAM.getCareTeamByBookId(practiceId, bookid);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id=" + bookid));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23CareTeamBookDelete() throws NullPointerException, Exception {

		String careteamid = propertyData.getProperty("careteam.id.am");
		String bookid = propertyData.getProperty("careteambook.book.id.am");

		Response response = postAPIRequestAM.deleteCareTeamBook(practiceId, careteamid, bookid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23BookSpecialtySave() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("specialty.book.id.am");
		String specialtyid = propertyData.getProperty("specialty.id.am");
		String b = payloadAM.saveBookSpecialtyPayload(bookid, specialtyid);

		Response response = postAPIRequestAM.saveSpecialty(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test23BookSpecialtySaveInvalid() throws NullPointerException, Exception {

		String b = payloadAM.saveBookSpecialtyPayload("1234", "8888");

		Response response = postAPIRequestAM.saveSpecialty(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test24BookSpecialtyDeleteInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteSpecialty(practiceId, "1234", "8888");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No book found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test24BookSpecialtyDelete() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("specialty.book.id.am");
		String specialtyid = propertyData.getProperty("specialty.id.am");

		Response response = postAPIRequestAM.deleteSpecialty(practiceId, bookid, specialtyid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamGET_SAVE_DELETE() throws NullPointerException, Exception {

		String b = payloadAM.saveCareTeam();

		Response response = postAPIRequestAM.saveCareTeam(practiceId, b);

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		String careteamid = aPIVerification.responseKeyValidationJson(response, "id");

		Response getResponse = postAPIRequestAM.getAssociatedCareteam(practiceId);

		aPIVerification.responseCodeValidation(getResponse, 200);
		aPIVerification.responseTimeValidation(getResponse);

		Response getCareTeamResponse = postAPIRequestAM.getCareTeamById(practiceId, careteamid);

		aPIVerification.responseCodeValidation(getCareTeamResponse, 200);
		aPIVerification.responseTimeValidation(getCareTeamResponse);

		Response deleteResponse = postAPIRequestAM.deleteCareTeam(practiceId, careteamid);

		aPIVerification.responseCodeValidation(deleteResponse, 200);
		aPIVerification.responseTimeValidation(deleteResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamSaveInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.saveCareTeamWithoutBody(practiceId);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getCareTeamById(practiceId, "12345");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No careteam found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamDeleteInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteCareTeam(practiceId, "1234");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No careteam found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategoryGET_SAVE_DELETE() throws NullPointerException, Exception {

		String b = payloadAM.saveCategory();

		Response response = postAPIRequestAM.saveCategory(practiceId, b);
		logStep("Validate SaveCategory Response");

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());
		int categoryid = arr.getJSONObject(0).getInt("id");

		Response getResponse = postAPIRequestAM.getcategoryById(practiceId, String.valueOf(categoryid));
		logStep("Validate GetCategoryById Response");

		aPIVerification.responseCodeValidation(getResponse, 200);
		aPIVerification.responseTimeValidation(getResponse);

		Response getAssociatedCategory = postAPIRequestAM.associatedCategory(practiceId);
		logStep("Validate AssociatedCategory Response");

		aPIVerification.responseCodeValidation(getAssociatedCategory, 200);
		aPIVerification.responseTimeValidation(getAssociatedCategory);

		String draft = payloadAM.draftCategoryPayload(categoryid);

		Response draftResponse = postAPIRequestAM.saveCategoryDraft(practiceId, draft);
		logStep("Validate DraftCategory Response");

		aPIVerification.responseCodeValidation(draftResponse, 200);
		aPIVerification.responseTimeValidation(draftResponse);

		String export = payloadAM.exportCategoryPayload();

		Response exportResponse = postAPIRequestAM.exportCategory(practiceId, export);
		logStep("Validate ExportCategory Response");

		aPIVerification.responseCodeValidation(exportResponse, 200);
		aPIVerification.responseTimeValidation(exportResponse);

		Response deleteResponse = postAPIRequestAM.deleteCategory(practiceId, String.valueOf(categoryid));
		logStep("Validate DeleteCategory Response");

		aPIVerification.responseCodeValidation(deleteResponse, 200);
		aPIVerification.responseTimeValidation(deleteResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySaveInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.saveCategory(practiceId, "");
		logStep("Validate SaveCategory Response");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategoryGETInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getcategoryById(practiceId, "22222");
		logStep("Validate GetCategoryById Response");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No category found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategoryDeleteInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteCategory(practiceId, "12345");
		logStep("Validate DeleteCategory Response");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No category found for id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategoryExportInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.exportCategory(practiceId, "");
		logStep("Validate ExportCategory Response");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testImportCategory() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.importCategory(practiceId, headerConfig.HeaderwithTokenMulti(openToken));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		String categoryid = aPIVerification.responseKeyValidationJson(response, "id");
		String name = aPIVerification.responseKeyValidationJson(response, "name");

		assertEquals(name, "SuperCategory", "Category Name is not matching with input");

		postAPIRequestAM.deleteCategory(practiceId, String.valueOf(categoryid));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialty01Post() throws NullPointerException, Exception {

		String b = payloadAM.categorySpecialityPayload(propertyData.getProperty("cat.specialitybook.id.am"),
				propertyData.getProperty("cat.speci.id.am"));
		Response response = postAPIRequestAM.categorySpecialtyPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialty01WithoutBodyPost() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestAM.categorySpecialtyPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));


	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialty02BookGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.categorySpecialtyBook(practiceId,
				propertyData.getProperty("cat.specialitybook.id.am"), "/categoryspecialty/");
		aPIVerification.responseCodeValidation(response, 200);
		validateAdapter.verifyCategorySpecialtyBook(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialtyBookInvalidPath() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.categorySpecialtyBook(practiceId,
				propertyData.getProperty("cat.specialitybook.id.am"), "/categoryspecialtyaa/");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialty00Delete() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteCatSpeciality(practiceId,
				propertyData.getProperty("cat.specialitybook.id.am"),
				propertyData.getProperty("speciality.cat.location.id.am"));
		aPIVerification.responseCodeValidation(response, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategorySpecialty00DeleteByInvalidID() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteCatSpeciality(practiceId,
				propertyData.getProperty("cat.specialitybook.id.am"), "2222");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("No specialty found for SpecialtyId=2222"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigGet(practiceId, "/configuration");
		validateAdapter.verifyResourceConfig(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigInvalidPathGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigGet(practiceId, "/configurationaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigPost(practiceId, payloadAM.resourceConfigPayload());
		validateAdapter.verifyResourceConfigPost(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigWithoutBodyPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigPost(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigSavePOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigSavePost(practiceId, payloadAM.resourceConfigSavePayload());
		validateAdapter.verifyResourceConfigPost(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigSaveWithoutBodyPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigSavePost(practiceId,
				"");
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigRuleDeleteInvalidId() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteRuleById(practiceId,
				propertyData.getProperty("resourceconfig.invalid.ruleid"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No rule for id=" + propertyData.getProperty("resourceconfig.invalid.ruleid"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigRuleGetDeletePutPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.resourceConfigRuleGet(practiceId);
		validateAdapter.verifyResourceConfigRuleGet(response);
		JsonPath js = new JsonPath(response.asString());
		String ruleId = js.getString("id[0]");
		String ruleId1 = js.getString("id[1]");
		log("Rule id is    " + ruleId);
		log("Rule id is    " + ruleId1);

		Response responseForDeleteRule = postAPIRequestAM.deleteRuleById(practiceId, ruleId);
		aPIVerification.responseCodeValidation(responseForDeleteRule, 200);

		Response responseForDeleteRule1 = postAPIRequestAM.deleteRuleById(practiceId, ruleId1);
		aPIVerification.responseCodeValidation(responseForDeleteRule1, 200);

		Response responseRulePost = postAPIRequestAM.resourceConfigRulePost(practiceId,
				payloadAM.resourceConfigRulePostPayload());
		aPIVerification.responseCodeValidation(responseRulePost, 200);
		aPIVerification.responseKeyValidationJson(responseRulePost, "name");
		aPIVerification.responseKeyValidationJson(responseRulePost, "rule");

		Response responseRulePut = postAPIRequestAM.resourceConfigRulePUT(practiceId,
				payloadAM.resourceConfigRulePutPayload());
		aPIVerification.responseCodeValidation(responseRulePut, 200);
		aPIVerification.responseKeyValidationJson(responseRulePut, "name");
		aPIVerification.responseKeyValidationJson(responseRulePut, "rule");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigVersionGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.versionGet(practiceId, "/version");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testResourceConfigVersionInvalidPathGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.versionGet(practiceId, "/versionaa");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingGET() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.gendermapGet(practiceId, "/gendermap");
		validateAdapter.verifyGenderMap(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingInvalidPathGET() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.gendermapGet(practiceId, "/gendermapaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.gendermapPost(practiceId, payloadAM.genderMapPayload());
		validateAdapter.verifyGenderMap(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGenderMappingWithoutBodyPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.gendermapPost(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.linkGet(practiceId, "/link");
		validateAdapter.verifyLinkGet(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkGetInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.linkGet(practiceId, "/link");
		aPIVerification.responseCodeValidation(response, 404);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkSavePost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.savelinkPost(practiceId,
				payloadAM.saveLink(propertyData.getProperty("link.id.am"), propertyData.getProperty("link.provider.am"),
						propertyData.getProperty("link.ext.id.am"), propertyData.getProperty("link.cat.id.am"),
						propertyData.getProperty("link.display.name.am")),
				"/savelink");
		validateAdapter.verifySaveLink(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkSaveWithoutBodyPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.savelinkPost(practiceId,"","/savelink");
		aPIVerification.responseCodeValidation(response, 400);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkDelete() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteLink(practiceId, propertyData.getProperty("link.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkDeleteInvalidId() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.deleteLink(practiceId, propertyData.getProperty("link.invalid.id.am"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message,
				"Invalid Id=" + propertyData.getProperty("link.invalid.id.am") + " passed to delete link");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationAssociatedGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocation");
		validateAdapter.verifyLocationAssociated(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationAssociatedInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationAssociated(practiceId, "/associatedlocationaa");
		aPIVerification.responseCodeValidation(response, 404);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationPut() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationPut(practiceId,
				payloadAM.location(propertyData.getProperty("location.id.am"), propertyData.getProperty("zipcode.am"),
						propertyData.getProperty("location.dispname.am"),
						propertyData.getProperty("ext.location.id.am")),
				"/location");
		validateAdapter.verifyLocation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationPutInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationPut(practiceId,
				payloadAM.location(propertyData.getProperty("location.id.am"), propertyData.getProperty("zipcode.am"),
						propertyData.getProperty("location.dispname.am"),
						propertyData.getProperty("ext.location.id.am")),
				"/locationaa");
		aPIVerification.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationSavePost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationSavePost(practiceId,
				payloadAM.location(propertyData.getProperty("location.id.am"), propertyData.getProperty("zipcode.am"),
						propertyData.getProperty("location.dispname.am"),
						propertyData.getProperty("ext.location.id.am")),
				"/location");
		validateAdapter.verifyLocation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationSavePostInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationSavePost(practiceId,
				payloadAM.location(propertyData.getProperty("location.id.am"), propertyData.getProperty("zipcode.am"),
						propertyData.getProperty("location.dispname.am"),
						propertyData.getProperty("ext.location.id.am")),
				"/locationaa");
		aPIVerification.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByIdGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationById(practiceId, propertyData.getProperty("location.id.am"));
		validateAdapter.verifyLocationByid(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByIdDelete() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationDeleteById(practiceId,
				propertyData.getProperty("location.del.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByInvalidIdDelete() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationDeleteById(practiceId,
				propertyData.getProperty("location.del.invalidid.am"));
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No value present");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByInvalidIdGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationById(practiceId,
				propertyData.getProperty("location.invalid.id.am"));
		aPIVerification.responseCodeValidation(response, 400);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id=" + propertyData.getProperty("location.invalid.id.am"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationreorderPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationreorderPost(practiceId, payloadAM.locationReorder(),
				"/location/reorder");
		validateAdapter.verifyLocation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationreorderInvalidPathPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationreorderPost(practiceId, payloadAM.locationReorder(),
				"/location/reorderaa");
		aPIVerification.responseCodeValidation(response, 405);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationGetBySearch() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationBySearch(practiceId,
				propertyData.getProperty("location.dispname.am"));
		validateAdapter.verifyLocationBySearch(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationGetBySearchWithoutName() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationBySearch(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationGetByExtId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByExtId(practiceId,
				propertyData.getProperty("ext.location.id.am"));
		validateAdapter.verifyLocationByid(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByWithoutExtId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByExtId(practiceId, "");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByPartner() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByPartner(practiceId, "/partnerlocation");
		validateAdapter.verifyLocationByid(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByPracticeGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByPractice(practiceId, "/practicelocation");
		validateAdapter.verifyLocationByid(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByPracticeInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByPractice(practiceId, "/practicelocationaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationByPartnerInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.locationByPartner(practiceId, "/partnerlocationaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutPracticeGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicelockout(practiceId, "/practicelockout");
		validateAdapter.verifyAssociatedLockout(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutPracticeInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicelockout(practiceId, "/practicelockoutaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGetAssociated() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		validateAdapter.verifyAssociatedLockout(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGetAssociatedInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockoutaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutPostInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.lockoutPost(practiceId,
				payloadAM.locakoutPost(propertyData.getProperty("lockout.key.am"),
						propertyData.getProperty("lockout.value.am"), propertyData.getProperty("lockout.type.am"),
						propertyData.getProperty("lockout.group.am")),
				"/lockoutaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutPost_PUT_GET_DELETE() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.lockoutPost(practiceId,
				payloadAM.locakoutPost(propertyData.getProperty("lockout.key.am"),
						propertyData.getProperty("lockout.value.am"), propertyData.getProperty("lockout.type.am"),
						propertyData.getProperty("lockout.group.am")),
				"/lockout");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String id = js.getString("id");

		Response responselockoutPut = postAPIRequestAM.lockoutPut(practiceId,
				payloadAM.locakoutPut(id, propertyData.getProperty("lockout.key.am"),
						propertyData.getProperty("lockout.value.am"), propertyData.getProperty("lockout.type.am"),
						propertyData.getProperty("lockout.group.am")),
				"/lockout");
		aPIVerification.responseCodeValidation(responselockoutPut, 200);
		aPIVerification.responseTimeValidation(responselockoutPut);

		Response responseget = postAPIRequestAM.associatedlockout(practiceId, "/associatedlockout");
		validateAdapter.verifyAssociatedLockout(responseget);

		Response responseForDelete = postAPIRequestAM.deleteLockoutById(practiceId, id);
		aPIVerification.responseCodeValidation(responseForDelete, 200);
		aPIVerification.responseTimeValidation(responseForDelete);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutDeleteByInvalidId() throws NullPointerException, Exception {
		Response responseForDelete = postAPIRequestAM.deleteLockoutById(practiceId, "12345");
		aPIVerification.responseCodeValidation(responseForDelete, 400);
		aPIVerification.responseTimeValidation(responseForDelete);
		JsonPath js = new JsonPath(responseForDelete.asString());
		String message = js.getString("message");
		assertEquals(message, "No id=12345 found to delete");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessSavePost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.loginlessSavePost(practiceId,
				payloadAM.loginlessSavePayload(propertyData.getProperty("loginless.guid.am"),
						propertyData.getProperty("loginless.link.am")),
				"/loginless");
		validateAdapter.verifyLoginlessPost(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessSaveInvalidPathPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.loginlessSavePost(practiceId,
				payloadAM.loginlessSavePayload(propertyData.getProperty("loginless.guid.am"),
						propertyData.getProperty("loginless.link.am")),
				"/loginlessaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.loginlessGet(practiceId, "/loginless");
		validateAdapter.verifyLoginless(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLoginlessInvalidPathGet() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.loginlessGet(practiceId, "/loginlessaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.loginlessGuidGet(practiceId, "/loginless/guid");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.loginlessGuidGet(practiceId, "/loginless/guidaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerDetails() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.partnerConfigGet(practiceId, "/partnerdetails");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerDetailsInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.partnerConfigGet(practiceId, "/partnerdetailsaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.patientInfoGet(practiceId, propertyData.getProperty("flow.type.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoWithoutFlowTypeGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.patientInfoWithoutFlowTypeGet(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.patientInfoPost(practiceId, payloadAM.patientInfo());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoWithoutBodyPOST() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.patientInfoPost(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeGetTimeZone() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpractice");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "practiceName");
		aPIVerification.responseKeyValidationJson(response, "practiceDisplayName");
		aPIVerification.responseKeyValidationJson(response, "practiceTimezone");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeGetTimeZoneInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.medfusionpracticeTimeZone(practiceId, "/medfusionpracticeaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practiceInfo(practiceId, "/practice");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "practiceId");
		aPIVerification.responseKeyValidationJson(response, "starttime");
		aPIVerification.responseKeyValidationJson(response, "endtime");
		aPIVerification.responseKeyValidationJson(response, "themeColor");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practiceInfo(practiceId, "/practiceaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticePut() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicePut(practiceId,
				payloadAM.practice(propertyData.getProperty("practice.starttime.am"),
						propertyData.getProperty("practice.endtime.am"), propertyData.getProperty("practice.id.am"),
						propertyData.getProperty("practice.name.am"),
						propertyData.getProperty("practice.timezone.am")));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInvalidPayloadPut() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicePut(practiceId,"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticePost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicePost(practiceId,
				payloadAM.practice(propertyData.getProperty("practice.starttime.am"),
						propertyData.getProperty("practice.endtime.am"), propertyData.getProperty("practice.id.am"),
						propertyData.getProperty("practice.name.am"),
						propertyData.getProperty("practice.timezone.am")));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInvalidPayloadPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.practicePost(practiceId,"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteApp01Post() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.preRequisiteAppointmenttypes(practiceId,
				propertyData.getProperty("prerequisite.id.am"),
				payloadAM.preRequisiteAppPayload(propertyData.getProperty("prerequisite.id.am"),
						propertyData.getProperty("prerequisite.name.am"),
						propertyData.getProperty("prerequisite.extpreapptypeid.am"),
						propertyData.getProperty("prerequisite.categoryid.am"),
						propertyData.getProperty("prerequisite.categoryname.am")));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "extPreAppTypeId");
		aPIVerification.responseKeyValidationJson(response, "appointmentType");
		aPIVerification.responseKeyValidationJson(response, "numOfDays");
		aPIVerification.responseKeyValidationJson(response, "categoryId");
		aPIVerification.responseKeyValidationJson(response, "categoryName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteApp01InvalidIdPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.preRequisiteAppointmenttypes(practiceId,
				propertyData.getProperty("prerequisite.id.am"),
				payloadAM.preRequisiteAppPayload(propertyData.getProperty("prerequisite.invalidid.am"),
						propertyData.getProperty("prerequisite.name.am"),
						propertyData.getProperty("prerequisite.extpreapptypeid.am"),
						propertyData.getProperty("prerequisite.categoryid.am"),
						propertyData.getProperty("prerequisite.categoryname.am")));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message,
				"No prerequisite found for prerequisiteId = " + propertyData.getProperty("prerequisite.invalidid.am"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteAppBy03Id() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppById(practiceId,
				propertyData.getProperty("prerequisite.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "extPreAppTypeId");

		aPIVerification.responseKeyValidationJson(response, "appointmentType");
		aPIVerification.responseKeyValidationJson(response, "numOfDays");
		aPIVerification.responseKeyValidationJson(response, "categoryId");
		aPIVerification.responseKeyValidationJson(response, "categoryName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteAppBy03InvalidId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppById(practiceId,
				propertyData.getProperty("prerequisite.invalidid.am"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No appointment found for id = " + propertyData.getProperty("prerequisite.invalidid.am"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteAppDeleteById() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId,
				propertyData.getProperty("prerequisite.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteAppDeleteByInvalidId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppDeleteById(practiceId,
				propertyData.getProperty("prerequisite.invalidid.am"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message,
				"No prerequisite found for prerequisiteId = " + propertyData.getProperty("prerequisite.invalidid.am"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteApp02Get() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppGet(practiceId, "/practiceprerequiste");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "extPreAppTypeId");

		aPIVerification.responseKeyValidationJson(response, "appointmentType");
		aPIVerification.responseKeyValidationJson(response, "numOfDays");
		aPIVerification.responseKeyValidationJson(response, "categoryId");
		aPIVerification.responseKeyValidationJson(response, "categoryName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPreRequisiteApp02InvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.preRequisiteAppGet(practiceId, "/practiceprerequistea");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkLocationSearch() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.locationSearch(practiceId, payloadAM.locationSearchPayload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "displayName");
		aPIVerification.responseKeyValidationJson(response, "extLocationId");
		aPIVerification.responseKeyValidationJson(response, "restrictToCareteam");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkLocationSearchWithoutBody() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.locationSearch(practiceId, payloadAM.locationSearchEmptyPayload());
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkBookSearch() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.bookSearch(practiceId, payloadAM.booksearchPayload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "displayName");
		aPIVerification.responseKeyValidationJson(response, "extLocationId");
		aPIVerification.responseKeyValidationJson(response, "restrictToCareteam");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLinkBookSearchWithoutBody() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.bookSearch(practiceId, payloadAM.booksearchEmptyPayload());
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOAnonymousGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.anonymousGet(practiceId, "/anonymous");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "guid");
		aPIVerification.responseKeyValidationJson(response, "code");
		aPIVerification.responseKeyValidationJson(response, "newPatients");
		aPIVerification.responseKeyValidationJson(response, "existingPatients");
		aPIVerification.responseKeyValidationJson(response, "allowOtp");
		aPIVerification.responseKeyValidationJson(response, "link");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOAnonymousInvalidPathGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.anonymousGet(practiceId, "/anonymousaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOAnonymousPost() throws NullPointerException, Exception {
		String b = payloadAM.anonymousPost(propertyData.getProperty("code.anonymous.am"),
				propertyData.getProperty("guid.anonymous.am"), propertyData.getProperty("link.anonymous.am"));
		Response response = postAPIRequestAM.anonymousPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseKeyValidationJson(response, "guid");
		aPIVerification.responseKeyValidationJson(response, "code");
		aPIVerification.responseKeyValidationJson(response, "newPatients");
		aPIVerification.responseKeyValidationJson(response, "existingPatients");
		aPIVerification.responseKeyValidationJson(response, "allowOtp");
		aPIVerification.responseKeyValidationJson(response, "displayName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOAnonymousWithoutCodePost() throws NullPointerException, Exception {
		String b = payloadAM.anonymousWithoutCodePost(propertyData.getProperty("guid.anonymous.am"),
				propertyData.getProperty("link.anonymous.am"));

		Response response = postAPIRequestAM.anonymousPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid code=null");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO02GET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoGet(practiceId, "/sso");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "guid");
		aPIVerification.responseKeyValidationJson(response, "code");
		aPIVerification.responseKeyValidationJson(response, "newPatients");
		aPIVerification.responseKeyValidationJson(response, "existingPatients");
		aPIVerification.responseKeyValidationJson(response, "allowOtp");
		aPIVerification.responseKeyValidationJson(response, "displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO02InvalidPathGET() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoGet(practiceId, "/ssoaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO01Post() throws NullPointerException, Exception {
		String b = payloadAM.ssoPost(propertyData.getProperty("code.sso.am"), propertyData.getProperty("guid.sso.am"),
				propertyData.getProperty("displayName.sso.am"));
		Response response = postAPIRequestAM.ssoPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseKeyValidationJson(response, "guid");
		aPIVerification.responseKeyValidationJson(response, "code");
		aPIVerification.responseKeyValidationJson(response, "newPatients");
		aPIVerification.responseKeyValidationJson(response, "existingPatients");
		aPIVerification.responseKeyValidationJson(response, "allowOtp");
		aPIVerification.responseKeyValidationJson(response, "displayName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO01WithoutCodePost() throws NullPointerException, Exception {
		String b = payloadAM.ssoWithoutCodePost(propertyData.getProperty("guid.sso.am"),
				propertyData.getProperty("displayName.sso.am"));
		Response response = postAPIRequestAM.ssoPost(practiceId, b);
		aPIVerification.responseCodeValidation(response, 400);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid code=null");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO00GenerateGuid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoGenGuid(practiceId, propertyData.getProperty("code.sso.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO00GenerateGuidWithoutCode() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoGenWithoutGuid(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO03DeleteGuid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoDeleteGuid(practiceId, propertyData.getProperty("code.sso.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSO03DeleteGuidWithoutCode() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.ssoDeleteGuidWithoutCode(practiceId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller01LogoPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerPost(practiceId,
				payloadAM.reseller(propertyData.getProperty("logo.am")), "/reseller");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller01LogoInvalidPPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerPost(practiceId,
				payloadAM.reseller(propertyData.getProperty("logo.am")), "/reselleraa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller02LogoGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerLogo(practiceId, "/reseller/logo");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller02LogoInvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerLogo(practiceId, "/reseller/logoaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller02Get() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.reseller(practiceId, "/reseller");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller02InvalidPathGet() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.reseller(practiceId, "/reselleraa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller03LogoDelete() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerLogoDelete(practiceId, "/reseller");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReseller03LogoDeleteInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.resellerLogoDelete(practiceId, "/reselleraa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSessionConfigurationGet() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getSessionConfiguration(practiceId, "/getsessionconfiguration");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSessionConfigurationInvalidPathGet() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getSessionConfiguration(practiceId, "/getsessionconfigurationaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyAssociated() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedspecialty(practiceId, "/associatedspecialty");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyAssociatedInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedspecialty(practiceId, "/associatedspecialtyaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyAssociatedPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedPracticespecialty(practiceId, "/practicespecialty");
		aPIVerification.responseCodeValidation(response, 204);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyAssociatedPracticeInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedPracticespecialty(practiceId, "/practicespecialtyaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyPut() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityPut(practiceId,
				payloadAM.specialityPayload(propertyData.getProperty("speciality.id.am")), "/specialty");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyPutInvalidPath() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityPut(practiceId, payloadAM.specialityPayload("speciality.id.am"),
				"/specialtyaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityPost(practiceId,
				payloadAM.specialityPayload(propertyData.getProperty("speciality.id.am")), "/specialty");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyInvalidPathPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityPost(practiceId,
				payloadAM.specialityPayload(propertyData.getProperty("speciality.id.am")), "/specialtya");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyById() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedspecialtyById(practiceId,
				propertyData.getProperty("speciality.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyByInvalidId() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.getassociatedspecialtyById(practiceId,
				propertyData.getProperty("speciality.invalidid.am"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Specialty found for id = " + propertyData.getProperty("speciality.invalidid.am"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyReorderPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityReorderPost(practiceId, payloadAM.specialityReorderPayload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialtyReorderInvalidPayloadPost() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityReorderPost(practiceId,"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityDeleteByInvalidId() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityDeleteByid(practiceId,
				propertyData.getProperty("speciality.invalidid.am"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message,
				"Could not delete speciality for id=" + propertyData.getProperty("speciality.invalidid.am"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityDeleteById() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.specialityDeleteByid(practiceId,
				propertyData.getProperty("speciality.id.am"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceGET_GETById_DeleteByID() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.associatedInsuranceCarrier(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "extInsuranceCarrierId");
		String id = aPIVerification.responseKeyValidationJson(response, "id[0]");
		log("Id is  " + id);

		Response responseById = postAPIRequestAM.insuranceCarrierById(practiceId, id);
		aPIVerification.responseCodeValidation(responseById, 200);
		aPIVerification.responseTimeValidation(responseById);
		aPIVerification.responseKeyValidationJson(responseById, "name");
		aPIVerification.responseKeyValidationJson(responseById, "id");
		aPIVerification.responseKeyValidationJson(responseById, "extInsuranceCarrierId");

		Response responseDelete = postAPIRequestAM.insuranceDeleteById(practiceId, id);
		aPIVerification.responseCodeValidation(responseDelete, 200);
		aPIVerification.responseTimeValidation(responseDelete);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsurancePractice() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.InsurancePractice(practiceId, "/practiceinsurancecarrier");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "name");
		aPIVerification.responseKeyValidationJson(response, "extInsuranceCarrierId");
		aPIVerification.responseKeyValidationJson(response, "id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsurancePracticeInvalidPath() throws NullPointerException, Exception {

		Response response = postAPIRequestAM.InsurancePractice(practiceId, "/practiceinsurancecarrieraa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierReorderPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceReorder(practiceId, payloadAM.insuranceReorder());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierReordeInvalidPayloadrPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceReorder(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierSavePost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceSave(practiceId, payloadAM.insuranceSave());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierSaveWithoutBodyPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceSave(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		String message = aPIVerification.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));


	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceGetByInvalidId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceCarrierById(practiceId,
				propertyData.getProperty("insurance.invalid.id"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Insurance Carrier found for id=" + propertyData.getProperty("insurance.invalid.id"));

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceDeleteByInvalidId() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.insuranceDeleteById(practiceId,
				propertyData.getProperty("insurance.invalid.id"));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Insurance Carrier found for id=" + propertyData.getProperty("insurance.invalid.id"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchCustDataWithoutBodyPost() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.customDataPost(practiceId,"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchCustPost_Get_Delete() throws NullPointerException, Exception {

		Response response1 = postAPIRequestAM.customDataPost(practiceId, payloadAM.customDataPayload());
		aPIVerification.responseCodeValidation(response1, 200);

		Response response = postAPIRequestAM.customDataGet(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "patientMatches[0].id");
		String id = aPIVerification.responseKeyValidationJson(response, "patientMatches[0].id");

		Response responseDelete = postAPIRequestAM.customDataDelete(practiceId, id);
		aPIVerification.responseCodeValidation(responseDelete, 200);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchCustMetaData() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.partnerCustommetaData(practiceId, "/partnercustommetadata");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchCustMetaDataInvalidPath() throws NullPointerException, Exception {
		Response response = postAPIRequestAM.partnerCustommetaData(practiceId, "/partnercustommetadataaa");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

}
