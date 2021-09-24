// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestDBAdapter;
import com.medfusion.product.pss2patientapi.payload.PayloadDBAdapter;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PSS2DBAdapterModulatorTests extends BaseTestNG {

	public static PayloadDBAdapter payloadDB;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestDBAdapter postAPIRequestDB;
	HeaderConfig headerConfig;
	public static String practiceId;
	public PSSPatientUtils pssPatientUtils;

	Timestamp timestamp = new Timestamp();

	public static RequestSpecification requestSpec;
	public static ResponseSpecification responseSpec;
	
	APIVerification apv;

	 

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {
		payloadDB = new PayloadDBAdapter();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequestDB = new PostAPIRequestDBAdapter();
		headerConfig = new HeaderConfig();
		apv= new APIVerification();
		pssPatientUtils= new PSSPatientUtils();
		practiceId = propertyData.getProperty("practice.id.db");
		postAPIRequestDB.setupRequestSpecBuilder(propertyData.getProperty("base.url.db"),
				headerConfig.defaultHeader());
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.validatePractice(practiceId, "/validatepractice");
		apv.responseCodeValidation(response, 200);
	}

//	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
//	public void testAnnouncement_Fetch_Save_Delete() throws NullPointerException, Exception {
//
//		logStep("Verifying the response");
//		Response response = postAPIRequestDB.getAnnouncement(practiceId, "/announcement");
//		apv.responseCodeValidation(response, 200);
//
//		JSONArray arr = new JSONArray(response.body().asString());
//
//		int ann_id = arr.getJSONObject(1).getInt("id");
//		String ann_type = arr.getJSONObject(1).getString("type");
//		String ann_code = arr.getJSONObject(1).getString("code");
//
//		log("announcement Id- " + ann_id);
//		log("announcement Type- " + ann_type);
//		log("announcement code- " + ann_code);
//
//		String b = payloadAM.saveAnnouncementPayload(ann_id, ann_type, ann_code);
//
//		Response responseDeleteAnn = postAPIRequestDB.deleteAnnouncement(practiceId, ann_id);
//		apv.responseCodeValidation(responseDeleteAnn, 200);
//
//		Response responseSaveAnn = postAPIRequestDB.saveAnnouncement(practiceId, b);
//		apv.responseCodeValidation(responseSaveAnn, 200);
//	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncement(practiceId, "/announcement");
		apv.responseCodeValidation(response, 200);

		JSONArray ja = new JSONArray(response.asString());

		for (int i = 0; i < ja.length(); i++) {

			JSONObject jo = ja.getJSONObject(i);
			log("Value of i-" + i);

			Object sd = jo.get("id");
			log("Value of i-" + i);
			log("id- " + sd);
			assertFalse(JSONObject.NULL.equals(sd));

			log("Announcement Text- " + jo.getString("announcementText"));
			log("Announcement Name- " + jo.getJSONObject("announcementType").getString("name"));
		}
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementGetInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncement(practiceId, "/announcementt");
		apv.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementSaveInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceId, "");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementDeleteInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response responseDeleteAnn = postAPIRequestDB.deleteAnnouncement(practiceId, 4999);
		apv.responseCodeValidation(responseDeleteAnn, 500);
		String message = apv.responseKeyValidationJson(responseDeleteAnn, "message");
		assertTrue(message.contains("No value present"));
	}

//	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
//	public void testAnnouncementUpdate() throws NullPointerException, Exception {
//
//		Response response = postAPIRequestDB.getAnnouncement(practiceId, "/announcement");
//		apv.responseCodeValidation(response, 200);
//
//		JSONArray arr = new JSONArray(response.body().asString());
//
//		int ann_id = arr.getJSONObject(1).getInt("id");
//		String ann_type = arr.getJSONObject(1).getString("type");
//		String ann_code = arr.getJSONObject(1).getString("code");
//
//		log("announcement Id- " + ann_id);
//		log("announcement Type- " + ann_type);
//		log("announcement code- " + ann_code);
//
//		String b = payloadAM.updateAnnouncementPayload(ann_id, ann_type, ann_code);
//
//		Response responseSaveAnn = postAPIRequestDB.updateAnnouncement(practiceId, b);
//		apv.responseCodeValidation(responseSaveAnn, 200);
//
//		response = postAPIRequestDB.getAnnouncement(practiceId, "/announcement");
//	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementUpdateInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.updateAnnouncement(practiceId, "");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncementByCode(practiceId, "AG");
		apv.responseCodeValidation(response, 200);

		String type = apv.responseKeyValidationJson(response, "type");
		String code = apv.responseKeyValidationJson(response, "code");

		assertEquals(type, "Greetings");
		assertEquals(code, "AG");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCodeInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncementByCode(practiceId, "ZZZ");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Invalid announcement type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptType() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.saveAppointmenttype(practiceId, b);
		apv.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.saveAppointmenttype(practiceId, b);

		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message,
				"Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptType() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.updateAppointmenttype(practiceId, b);
		apv.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.updateAppointmenttype(practiceId, b);

		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message,
				"Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGetAppointmentTypeByExtApptId() throws NullPointerException, Exception {

		String extapptid=propertyData.getProperty("extappt.id.db");
		log("Appt Id- "+extapptid);
		Response response = postAPIRequestDB.getAppointmentTypeByExtApptId(practiceId,extapptid);
		apv.responseCodeValidation(response, 200);
		log("Appointment Id- "+apv.responseKeyValidationJson(response, "id"));
		log("Patient Id- "+apv.responseKeyValidationJson(response, "patientId"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypeByIdGet() throws NullPointerException, Exception {

		String apptid = propertyData.getProperty("appt.confm.id.db");
		log("Appt Id- " + apptid);
		Response response = postAPIRequestDB.getAppointmentTypeById(practiceId, apptid);
		apv.responseCodeValidation(response, 200);
		log("Appointment Id- " + apv.responseKeyValidationJson(response, "id"));
		log("Patient Id- " + apv.responseKeyValidationJson(response, "patientId"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentsForPracticeGet() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentsForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "patientId");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsByPatientIdForPracticeGet01() throws NullPointerException, Exception {

		String patientid = propertyData.getProperty("patientid.upcommingapp.id");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		Response response = postAPIRequestDB.getUpcomingAppointmentsByPatientIdForPractice(practiceId, patientid,
				currentdate);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "patientId");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentsByPatientIdForPracticeGet() throws NullPointerException, Exception {

		String patientid = propertyData.getProperty("patientid.upcommingapp.id");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String enddate= pssPatientUtils.createFutureDate(currentdate, 60);

		Response response = postAPIRequestDB.patientappointmentsbyrange(practiceId, patientid,
				currentdate, enddate);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "patientId");
	}
	
	//Appointment Type Config Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testtAppointmentTypeConfigGet() throws NullPointerException, Exception {

		String apptypeid = propertyData.getProperty("appttypeconfig.appttype.id.db");

		Response response = postAPIRequestDB.getAppointmentTypeConfig(practiceId, apptypeid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testtAppointmentTypeConfigGetInvalid() throws NullPointerException, Exception {

		String apptypeid = "9999";

		Response response = postAPIRequestDB.getAppointmentTypeConfig(practiceId, apptypeid);

		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "No Appointmenttype found for appointmenttypeid=9999");
	}
	
	//Appointment Type Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationForNoBookGet() throws NullPointerException, Exception {

		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesByLocationForNoBook(practiceId, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesForPracticeGet() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppTypeByIdGet() throws NullPointerException, Exception {

		String apptype =propertyData.getProperty("appttypeconfig.appttype.id.db");
		
		Response response = postAPIRequestDB.getAppTypeById(practiceId, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByBookGet() throws NullPointerException, Exception {

		String bookid= propertyData.getProperty("appttype.book.id.db");
		Response response = postAPIRequestDB.getAppointmentTypesByBook(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationGet() throws NullPointerException, Exception {

		String locationid = propertyData.getProperty("appttype.location.id.db");
		Response response = postAPIRequestDB.getAppointmentTypesByLocation(practiceId, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationAndBooksGet() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("appttype.book.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesByLocationAndBooks(practiceId, bookid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialty(practiceId, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndBookGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String bookid= propertyData.getProperty("appttype.specialty.book.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndBook(practiceId, specialty,bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndBookAndLocationGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String bookid = propertyData.getProperty("appttype.specialty.book.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndBookAndLocation(practiceId, specialty,
				bookid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndLocationGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndLocation(practiceId, specialty,
				locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptypeExtByIdGet() throws NullPointerException, Exception {

		String extapptid = propertyData.getProperty("appttype.extappt.id.db");

		Response response = postAPIRequestDB.getApptypeExtById(practiceId, extapptid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesWithLanguageForPracticeGetEnglish() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesWithLanguageForPractice(practiceId, "EN");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesWithLanguageForPracticeGetEspanol() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesWithLanguageForPractice(practiceId, "ES");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppAuthUserByUserIdGet() throws NullPointerException, Exception {

		String id = propertyData.getProperty("extuserid.authappuser.id.db");		

		Response response = postAPIRequestDB.getAppAuthUserByUserId(practiceId, id);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppAuthUserByExtUserIdGet() throws NullPointerException, Exception {

		String extuserid = propertyData.getProperty("extuserid.db");

		Response response = postAPIRequestDB.getAppAuthUserByExtUserId(practiceId, extuserid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book App Type Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppTyp_BookAppTypesAssociatedToBookGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBookAppTypesAssociatedToBook(practiceId, bookid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppTyp_AppTypesAssociatedToBookGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getAppTypesAssociatedToBook(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByBookId() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getAssociatedBookAppTypesByBookId(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByAppTypeId() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getAssociatedBookAppTypesByAppTypeId(practiceId, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book Appointment Type Location Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testbookapptypelocation_getLocationsAssociatedToBookAndAppointmentType() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getLocationsAssociatedToBookAndAppointmentType(practiceId, bookid,apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getBooksForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookById() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getBookById(practiceId,bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookExtById() throws NullPointerException, Exception {
		
		String extbokid=propertyData.getProperty("book.extbook.id");

		Response response = postAPIRequestDB.getBookExtById(practiceId, extbokid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookWithLinksById() throws NullPointerException, Exception {
		
		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getBookWithLinksById(practiceId,bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksWithLanguageForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getBooksWithLanguageForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	

	
	
	

	
}
