// Copyright 2021 NXGN Management, LLC. All Rights Reserved.

package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;
import java.io.IOException;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.eh.core.dto.Timestamp;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.HeaderConfig;
import com.medfusion.product.object.maps.pss2.page.util.ParseJSONFile;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestDBAdapter;
import com.medfusion.product.pss2patientapi.payload.PayloadDBAdapter;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;

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
	public ParseJSONFile parseJSONFile;

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
		
		parseJSONFile= new ParseJSONFile();
	}
	
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
	public void testAnnouncementSave() throws NullPointerException, Exception {
		
		String b=payloadDB.saveAnnouncementPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncement_Create_Delete() throws NullPointerException, Exception {
		
		String b=payloadDB.createAnnouncementPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String ann_id=apv.responseKeyValidationJson(response, "id");
		log("ann_id- "+ann_id);
		Response delete_Response=postAPIRequestDB.deleteAnnouncement(practiceId, Integer.parseInt(ann_id));
		apv.responseCodeValidation(delete_Response, 200);
		apv.responseTimeValidation(delete_Response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementDeleteInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response responseDeleteAnn = postAPIRequestDB.deleteAnnouncement(practiceId, 4999);
		apv.responseCodeValidation(responseDeleteAnn, 500);
		String message = apv.responseKeyValidationJson(responseDeleteAnn, "message");
		assertTrue(message.contains("No value present"));
	}

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
		apv.responseCodeValidation(response, 404);
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
	
	//Appointment Controller
	
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
	public void testAppointment_SaveAppointment() throws NullPointerException, Exception {

		String b = payloadDB.saveAppointmentPayload();
		Response response = postAPIRequestDB.saveAppointment(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment_UpdateAppointment() throws NullPointerException, Exception {

		String b = payloadDB.updateAppointmentPayload();
		Response response = postAPIRequestDB.updateAppointment(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment_getAppointmentByExtApptIdForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentsForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		
		JSONArray arr = new JSONArray(response.body().asString());

		String extapptid = arr.getJSONObject(1).getString("extApptId");

		Response extApptResponse = postAPIRequestDB.getAppointmentByExtApptIdForPractice(practiceId, extapptid);
		apv.responseCodeValidation(extApptResponse, 200);
		apv.responseTimeValidation(extApptResponse);
		apv.responseKeyValidation(extApptResponse, "patientId");
		apv.responseKeyValidationJson(extApptResponse, "id");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentsByPatientIdForPracticeGet() throws NullPointerException, Exception {

		String patientid = propertyData.getProperty("patientid.upcommingapp.id");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");
		String enddate= pssPatientUtils.createFutureDate(currentdate, 60);
		String startdate=propertyData.getProperty("patientid.upcommingapp.startdate");

		Response response = postAPIRequestDB.patientappointmentsbyrange(practiceId, patientid,
				startdate, enddate);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "patientId");		
		
		JSONArray arr = new JSONArray(response.body().asString());

		String extapptid = arr.getJSONObject(0).getString("extApptId");
		log("extApptId-"+extapptid);

		Response extApptResponse = postAPIRequestDB.getAppointmentByExtApptIdForPractice(practiceId, extapptid);
		apv.responseCodeValidation(extApptResponse, 200);
		apv.responseTimeValidation(extApptResponse);
		apv.responseKeyValidation(extApptResponse, "patientId");
		apv.responseKeyValidationJson(extApptResponse, "id");
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

		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");
		Response response = postAPIRequestDB.getBookAppTypesAssociatedToBook(practiceId, bookid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppTyp_AppTypesAssociatedToBookGet() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getAppTypesAssociatedToBook(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByBookId() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		Response response = postAPIRequestDB.getAssociatedBookAppTypesByBookId(practiceId, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByAppTypeId() throws NullPointerException, Exception {

		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getAssociatedBookAppTypesByAppTypeId(practiceId, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book Appointment Type Location Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testbookapptypelocation_getLocationsAssociatedToBookAndAppointmentType() throws NullPointerException, Exception {

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

		Response response = postAPIRequestDB.getBookById(practiceId,Integer.parseInt(bookid));
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
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookByPracticeAndName() throws NullPointerException, Exception {
		
		String bookname=propertyData.getProperty("book.bookname.db");
		String extbookid=propertyData.getProperty("book.extbook.id.db");

		Response response = postAPIRequestDB.getBookByPracticeAndName(practiceId, bookname);
		String actualextbookid= response.getBody().asString();
		log("actualextbookid- "+actualextbookid);
		assertEquals(actualextbookid, extbookid, "External Book id is not matching with expected id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksByAppType() throws NullPointerException, Exception {

		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksByAppType(practiceId, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)

	public void testBook_getBooksByLocation() throws NullPointerException, Exception {		

		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		Response response = postAPIRequestDB.getBooksByLocation(practiceId,Integer.parseInt(locationid));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksByLocationAndAppointmentTypes() throws NullPointerException, Exception {
		
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksByLocationAndAppointmentTypes(practiceId, locationid , apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpeciality() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");

		Response response = postAPIRequestDB.getBooksBySpeciality(practiceId, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndAppointmentType() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksBySpecialityAndAppointmentType(practiceId, specialty, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndLocation() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		Response response = postAPIRequestDB.getBooksBySpecialityAndLocation(practiceId, specialty, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndLocationAndAppointmentType() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");
		
		Response response = postAPIRequestDB.getBooksBySpecialityAndLocationAndAppointmentType(practiceId,specialty, locationid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Cancellation Reason Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelReason_getCancellationReason() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getCancellationReason(practiceId,"/cancellationreason");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Care Team Book Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamBook_getBookAssociatedToCareTeam() throws NullPointerException, Exception {
		
		String careteamid=propertyData.getProperty("careteambook.careteam.id.db");
		Response response = postAPIRequestDB.getBookAssociatedToCareTeam(practiceId, Integer.parseInt(careteamid));
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "careteam");
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extBookId");
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamBook_save_get_delete() throws NullPointerException, Exception {
		
		String b=payloadDB.careTeamBookPayload();
		
		Response saveResponse = postAPIRequestDB.associateCareTeamToBook(practiceId, b);
		apv.responseCodeValidation(saveResponse, 200);
		apv.responseTimeValidation(saveResponse);
		
		JSONArray arr = new JSONArray(saveResponse.body().asString());

		int careteamid = arr.getJSONObject(0).getJSONObject("careTeam").getInt("id");
		log("Care Team Id- "+careteamid);
		String bookid=propertyData.getProperty("careteambook.boook.id.db") ;
		log("Book Id- "+bookid);

		Response response = postAPIRequestDB.getBookAssociatedToCareTeam(practiceId, careteamid);
		apv.responseCodeValidation(response, 200);
		
		Response deleteResponse= postAPIRequestDB.deleteCareTeamBook(practiceId, careteamid, Integer.parseInt(bookid));
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	
	
	//care-team-controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareteamsForPracticel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCareteamsForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareTeamById() throws NullPointerException, Exception {
		
		String careteamid=propertyData.getProperty("careteambook.careteam.id.db");
		Response response = postAPIRequestDB.getCareTeamById(practiceId, Integer.parseInt(careteamid));
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "createdUserId");
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_save_update_delete() throws NullPointerException, Exception {
		
		String b=payloadDB.createCareTeam();
		String c=payloadDB.saveCareTeamPayload();
		
		Response saveResponse=postAPIRequestDB.saveCareTeam(practiceId, c);
		apv.responseCodeValidation(saveResponse, 200);
		apv.responseTimeValidation(saveResponse);
		
		Response createResponse=postAPIRequestDB.saveCareTeam(practiceId, b);
		apv.responseCodeValidation(createResponse, 200);
		apv.responseTimeValidation(createResponse);
		
		JSONArray arr = new JSONArray(createResponse.body().asString());
		int careteamid = arr.getJSONObject(0).getInt("id");

		Response response = postAPIRequestDB.getCareTeamById(practiceId, careteamid);
		apv.responseCodeValidation(response, 200);
		
		Response deleteResponse=postAPIRequestDB.deleteCareTeamById(practiceId, careteamid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	//Category App Type Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getAppTypeForCategory() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("");
		Response response = postAPIRequestDB.getAppTypeForCategory(practiceId, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getCategoryAppTypeForCategoryAndAppttypel() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		String apptype=propertyData.getProperty("categoryapp.apptype.id.db");
		
		Response response = postAPIRequestDB.getCategoryAppTypeForCategoryAndAppttype(practiceId, categoryid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	
	//Category App Type Location Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getLocationsForCategoryAppType() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		
		Response response = postAPIRequestDB.getLocationsForCategoryAppType(practiceId, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	
	//Category Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategoryById() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		Response response = postAPIRequestDB.getCategoryById(practiceId, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategoryForPractice() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCategoryForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategorysWithLanguageForPractice() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCategorysWithLanguageForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategorysBySpecialty() throws NullPointerException, Exception {
		
		String specialty=propertyData.getProperty("bookapttype.specialty.id.db");
		
		Response response = postAPIRequestDB.getCategorysBySpecialty(practiceId, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_save_get_delete() throws NullPointerException, Exception {
		
		String b= payloadDB.saveCategory();
		
		Response saveResponse=postAPIRequestDB.saveCategory(practiceId, b);
		apv.responseCodeValidation(saveResponse, 200);
		
		JSONArray arr = new JSONArray(saveResponse.body().asString());

		int categoryid = arr.getJSONObject(0).getInt("id");
		
		Response deleteResponse = postAPIRequestDB.deleteCategory(practiceId, categoryid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_reorderCategory() throws NullPointerException, Exception {
		
		String b=payloadDB.reorderPayload();
		
		Response response = postAPIRequestDB.reorderCategory(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getVisitReason() throws NullPointerException, Exception {
		
		String b=payloadDB.visitReasonPayload();
		
		Response response = postAPIRequestDB.getVisitReason(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Category Specialty Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_Specialty_Save_delete() throws NullPointerException, Exception {
		
		String b=payloadDB.categoryspecialtyPayload();
		String categoryid=propertyData.getProperty("categoryspecialty.category.id.db");
		String specialtyid=propertyData.getProperty("categoryspecialty.specialty.id.db");
		
		Response response = postAPIRequestDB.saveCategorySpecialty(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response deleteResponse = postAPIRequestDB.deleteCategorySpecialty(practiceId,categoryid, specialtyid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getSpecialtiesAssociatedToCategory() throws NullPointerException, Exception {
		
		String b=payloadDB.getSpecialtiesAssociatedToCategoryPayload();
		
		Response response = postAPIRequestDB.getSpecialtiesAssociatedToCategory(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	
	public void testStateGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/states");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "statecode");
		apv.responseKeyValidation(response, "statename");
		apv.responseKeyValidationJson(response, "active");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStateInvalidPAthGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/statesa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceId, "/sso");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceId, "/ssoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoCode(practiceId, "/sso/MF");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceId, "/sso/MFa");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOGuidGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid = propertyData.getProperty("sso.guid");
		Response response = postAPIRequestDB.SSOGetPracticeFromGuid("/sso/" + guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "code");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid = "86aa36b2-637c-450d-bcb6-a5c1645d1da6aa";
		Response response = postAPIRequestDB.SSOGetPracticeFromGuid("/sso/" + guid);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Practice found for guid=" + guid);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityPracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");

		Response response = postAPIRequestDB.speciality(practiceId, ("/speciality"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extSpecialtyId");
		apv.responseKeyValidationJson(response, "specialty");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");

		Response response = postAPIRequestDB.speciality(practiceId, ("/specialityaa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = propertyData.getProperty("speciality.id");
		Response response = postAPIRequestDB.State(practiceId, ("/speciality/" + id));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = "20360411";
		Response response = postAPIRequestDB.specilityById(practiceId, ("/speciality/" + id));
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Specialty found for id = " + id);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceId, ("/specialties"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceId, ("/specialtiesa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceId, ("/configurations/rules"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "rule");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceId, ("/configurations/rulesaa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleMaster("/configurations/rulesmaster");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidation(response, "key");
		apv.responseKeyValidation(response, "value");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleMaster("/configurations/rulesmasteraa");
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerPractice(practiceId, "/reseller");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerPractice(practiceId, "/reselleraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceId, "/resellerbylanguage");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceId, "/resellerbylanguageaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceId, "/resellerbylanguageforui");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceId, "/resellerbylanguageforuiaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeTimezoneGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/timezone");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidation(response, "abbr");
		apv.responseKeyValidation(response, "description");
		apv.responseKeyValidation(response, "utcOffset");
		apv.responseKeyValidation(response, "utcOffsetMM");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeTimezoneInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/timezoneaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/language");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidation(response, "name");
		apv.responseKeyValidation(response, "flag");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpracticeLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceTimeZone("/languageaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceInfo(practiceId, "/practiceinfo");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoInvalidGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceInfo(practiceId, "/practiceinfoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceId, "/practice");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceId, "/practiceaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceId, "/patientmatch/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceId, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceId, "/patientmatchmaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceId, "/patientmatchmasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfomaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceId, "/patientinfomasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceId, "/partnercustomandinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "partnerCustomId");
		apv.responseKeyValidationJson(response, "paernerInfoId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceId, "/partnercustomandinfoaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceId, "/partnercustommetadata", "ZIP");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceId, "/partnercustommetadataaa", "ZIP");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaData() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerMetaData(practiceId, "/partnermetadata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidation(response, "message");
		apv.responseKeyValidation(response, "code");
		apv.responseKeyValidation(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerMetaData(practiceId, "/partnermetadataaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String code = propertyData.getProperty("partnermetadata.code");
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceId, "/partnermetadatabycode/" + code);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "message");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataByCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String code = propertyData.getProperty("partnermetadata.code");
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceId, "/partnermetadatabycodeaa/" + code);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerConfig(practiceId, "/partner");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "integrationId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfigInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerConfig(practiceId, "/partneraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerDetailsConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerDetails(practiceId, "/partnerdetails");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "forceCareTeamDuration");
		apv.responseKeyValidationJson(response, "showCancelMessage");
		apv.responseKeyValidationJson(response, "otherCancelReason");
		apv.responseKeyValidationJson(response, "establishPatientLastVisit");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfigDetailsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerDetails(practiceId, "/partnerdetailsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartner() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partner");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "integrationId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partneraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerBaseUrl() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partnerbaseurls");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testpartnerBaseUrlInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerWithoutPractice("/partnerbaseurlsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.loginless(practiceId, "/loginless");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.loginless(practiceId, "/loginlessaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("loginless.guid");
		Response response = postAPIRequestDB.loginlessGuid("/loginless/"+guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("loginless.guid");
		Response response = postAPIRequestDB.loginlessGuid("/loginlessaa/"+guid);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceId,"/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "type");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceId,"/lockoutaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocation() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceId,"/location");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceId,"/locationaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationById() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String locationId=propertyData.getProperty("location.id.db");
		Response response = postAPIRequestDB.locationById(practiceId,"/location/"+locationId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationByIdInvalidLocationIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String InvalidlocationId="2063511";
		Response response = postAPIRequestDB.locationById(practiceId,"/location/"+InvalidlocationId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id="+InvalidlocationId+" or the location could have been deleted.");
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppType() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationByAppId(practiceId,"/location/appointmenttype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId="2063511";
		Response response = postAPIRequestDB.locationByAppId(practiceId,"/location/appointmenttype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid appointmenttype id="+AppId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppointmentTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationByAppointmentId(practiceId,"/location/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationAppointmentInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId="2063511";
		Response response = postAPIRequestDB.locationByAppointmentId(practiceId,"/location/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid appointmenttype id="+AppId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		Response response = postAPIRequestDB.locationBook(practiceId,"/location/book/"+bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidbooId="2063511";
		Response response = postAPIRequestDB.locationBook(practiceId,"/location/book/"+invalidbooId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid bookid="+invalidbooId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookAppTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationBookAppType(practiceId,"/location/book/"+bookId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationBookAppTypeInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidbooId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationBookAppType(practiceId,"/location/book/"+invalidbooId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid bookid="+invalidbooId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		Response response = postAPIRequestDB.locationSpeciality(practiceId,"/location/specialty/"+specialtyId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidspecialtyId="2063511";
		Response response = postAPIRequestDB.locationSpeciality(practiceId,"/location/specialty/"+invalidspecialtyId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidspecialtyId);
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityAppTypeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceId,"/location/specialty/"+specialtyId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityAppTypeInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidSpecialityId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceId,"/location/specialty/"+invalidSpecialityId+"/apptype/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidSpecialityId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityBookGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String bookId=propertyData.getProperty("location.bookid.db");
		String specialtyId=propertyData.getProperty("location.specialityid.db");
		Response response = postAPIRequestDB.locationSpecialityBook(practiceId,"/location/specialty/"+specialtyId+"/book/"+bookId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationSpecialityBookInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidSpecialityId="2063511";
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.locationSpecialityBook(practiceId,"/location/specialty/"+invalidSpecialityId+"/book/"+AppId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "Invalid speciality id="+invalidSpecialityId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationLinkByIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String locationId=propertyData.getProperty("location.id.db");
		Response response = postAPIRequestDB.locationLinkbyid(practiceId,"/locationlinkbyid/"+locationId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationLinkInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidLocationId="2063511";
		Response response = postAPIRequestDB.locationLinkbyid(practiceId,"/locationlinkbyid/"+invalidLocationId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id="+invalidLocationId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.locations(practiceId,"/locations");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extLocationId");
		apv.responseKeyValidationJson(response, "name");	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.locations(practiceId,"/locationsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceId,"/insurancecarrier");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extInsuranceCarrierId");
		apv.responseKeyValidationJson(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsuranceCarrierInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceId,"/insurancecarrieraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierByIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String insuranceId=propertyData.getProperty("insurance.id.db");
        Response response = postAPIRequestDB.insuranceCarrrierById(practiceId,"/insurancecarrier/"+insuranceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extInsuranceCarrierId");
		apv.responseKeyValidationJson(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsuranceCarrierByIdInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String insuranceId=propertyData.getProperty("insurance.id.db");
		Response response = postAPIRequestDB.insuranceCarrrierById(practiceId,"/insurancecarrieraa"+insuranceId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.flowIdentity(practiceId,"/flowidentity", flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.flowIdentity(practiceId,"/flowidentityaa", flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testConfigPartnerCodeGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String partnerCode=propertyData.getProperty("partnercode.db");
        Response response = postAPIRequestDB.configPartnerCode("/configurations/", partnerCode);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "group");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testConfigPartnerCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String partnerCode=propertyData.getProperty("partnercode.db");
		Response response = postAPIRequestDB.configPartnerCode("/configurationsaa/", partnerCode);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testrescheduleAppIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("reschedule.aaptypeid.db");
        Response response = postAPIRequestDB.rescheduleApp(practiceId,"/reschedule/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("reschedule.aaptypeid.db");
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/rescheduleaa/"+AppId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testrescheduleAppGuidGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("reschedule.aaptypeguid.db");
        Response response = postAPIRequestDB.rescheduleAppGuid("/reschedule/"+guid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "appTypeId");
		apv.responseKeyValidationJson(response, "bookId");
		apv.responseKeyValidationJson(response, "locationId");
		apv.responseKeyValidationJson(response, "practiceId");

		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppGuidInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String guid=propertyData.getProperty("reschedule.aaptypeguid.db");
		Response response = postAPIRequestDB.rescheduleAppGuid("/rescheduleaa/"+guid);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
        Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypes/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "extPreAppTypeId");
		apv.responseKeyValidationJson(response, "categoryId");
		apv.responseKeyValidationJson(response, "categoryName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("location.appid.db");
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypesaa/"+AppId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppInvaliIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidAppID="12345";
		Response response = postAPIRequestDB.rescheduleApp(practiceId,"/prerequisiteappointmenttypes/"+invalidAppID);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No appointment found for id = " + invalidAppID);
	
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.practiceMetaData(practiceId,"/customdata",flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "practicePatientInfo.id");
		apv.responseKeyValidationJson(response, "practicePatientInfo.entity");
		apv.responseKeyValidationJson(response, "practicePatientInfo.isSearchRequired");
		apv.responseKeyValidationJson(response, "practicePatientInfo.isCreateRequired");
		apv.responseKeyValidationJson(response, "practicePatientInfo.field");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetadaInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType = propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.practiceMetaData(practiceId, "/customdataaa/", flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataAllGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceId,"/practicecustomalldata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetadataAllInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceId, "/practicecustomalldataaaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataInfoGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceId,"/practicecustomandinfo/"+flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "practiceInfoId");
		

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetadataInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType = propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceId, "/practicecustomandinfoaa/"+flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaData1Get() throws NullPointerException, Exception {

		logStep("Verifying the response");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceId,"/practicemetdata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceId, "/practicemetdataaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_Book() throws NullPointerException, Exception {
		
		String b= payloadDB.createBook();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveBook(practiceId, "/book",b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONArray arr = new JSONArray(response.body().asString());

		int bookid = arr.getJSONObject(0).getInt("id");
		log("Bookid- "+bookid);
			
		Response getResponse =postAPIRequestDB.getBookById(practiceId, bookid) ;
		
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.deleteBook(practiceId, "/book/", bookid);
		
		apv.responseCodeValidation(deleteResponse, 200);	
	}
	
	//Book Location Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_save_get_del() throws NullPointerException, Exception {
		
		String b= payloadDB.saveBookLocationPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveBookLocation(practiceId, "/booklocation",b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.asString());

		int bookid = arr.getJSONObject(0).getJSONObject("book").getInt("id");
		log("Bookid- "+bookid);
		int locationid = arr.getJSONObject(0).getJSONObject("location").getInt("id");
		log("Location Id- "+locationid);
			
		Response getResponse =postAPIRequestDB.getBooksByLocation(practiceId, locationid) ;
		apv.responseCodeValidation(getResponse, 200);
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.deleteBookLocation(practiceId, bookid, locationid);
		
		apv.responseCodeValidation(deleteResponse, 200);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_getLocationsAssociatedToBook() throws NullPointerException, Exception {
		
		String b= payloadDB.bookLocationsGetPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.bookLocations(practiceId, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);	
	}
	
	//Cancellation Reason Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReason_save_get_del() throws NullPointerException, Exception {
		
		String b= payloadDB.cancellationReasonPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.cancellationReasonSave(practiceId,b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.asString());

		String name = arr.getJSONObject(0).getString("name");
		log("Cancellation Reason- "+name);
		
		int reasonid=arr.getJSONObject(0).getInt("id");
			
		Response getResponse =postAPIRequestDB.getCancellationReasonById(practiceId, reasonid) ;
		apv.responseCodeValidation(getResponse, 200);
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.cancellationReasonDelete(practiceId, reasonid);
		
		apv.responseCodeValidation(deleteResponse, 200);
		
		String c=payloadDB.reorderPayload();
		
		Response reorderResponse=postAPIRequestDB.reorderCancellationReason(practiceId, c);
		
		apv.responseCodeValidation(reorderResponse, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetCodeMapTranslation() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodeMapTranslation(practiceId, "/codemaptranslation");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetCodeMapTranslationInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodeMapTranslation(practiceId, "/codemaptranslationaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgendermap() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodeMapTranslation(practiceId, "/gendermap");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "practice.id");
		apv.responseKeyValidationJson(response, "practice.extPracticeId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgendermapInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodeMapTranslation(practiceId, "/gendermapaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetGenderMapMaster() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getGenderMapMaster(practiceId, "/gendermapmaster");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "codeGroup");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "partnerCode");
		apv.responseKeyValidationJson(response, "pssCode");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetGenderMapMasterInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getGenderMapMaster(practiceId, "/gendermapmasteraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetLookUpCodeValueForGroup() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getLookUpCodeValueForGroup(practiceId, "/getlookupcode");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "code");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "grouptype");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetLookUpCodeValueForGroupInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getLookUpCodeValueForGroup(practiceId, "/getlookupcodeaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetPartnerCodeValueForGroup() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getPartnerCodeValueForGroup(practiceId, "/getpartnercode");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetPartnerCodeValueForGroupInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getPartnerCodeValueForGroup(practiceId, "/getpartnercodeaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetPssCodeValueForGroup() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getPssCodeValueForGroup(practiceId, "/getpsscode");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetPssCodeValueForGroupInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getPssCodeValueForGroup(practiceId, "/getpsscodeaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetSpecialtyRule() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getSpecialtyRule(practiceId, "/specialtyrule/O");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetSpecialtyRuleInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getSpecialtyRule(practiceId, "/specialtyruleaa/O");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetCodesByPartnerAndGroup() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodesByPartnerAndGroup("/codesbypartner/2/group/gender");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetCodesByPartnerAndGroupInvalidPath() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getCodesByPartnerAndGroup("/codesbypartneraa/2/group/gender");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testGendermapSavePost() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.gendermap(practiceId, payloadDB.gendermap(), "/gendermap");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "practice.id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsaveConfigurationsWithGroup() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.saveConfigurationsWithGroup(practiceId,
				payloadDB.saveConfigurationsWithGroup(), "/configurations/save");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidation(response, "configItem");
		apv.responseKeyValidation(response, "configValue");
		apv.responseKeyValidation(response, "group");
		apv.responseKeyValidationJson(response, "practice.id");


	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testgetAllConfigurationsByGroup() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.getAllConfigurationsByGroup(practiceId,
				payloadDB.getAllConfigurationsByGroup(), "/configurations");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "group");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsaveOrUpdateFlowIdentityByPractice() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.saveOrUpdateFlowIdentityByPractice(practiceId,
				payloadDB.saveOrUpdateFlowIdentityByPractice(), "/flowidentity");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void reorderInsuranceCarrierReorder() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.reorderInsuranceCarrier(practiceId, payloadDB.reorderInsuranceCarrier(),
				"/insurancecarrier/reorder");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_InsuranceCarrier() throws NullPointerException, Exception {
		String b = payloadDB.saveInsuranceCarrier();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveInsuranceCarrier(practiceId, b, "/insurancecarrier");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "extInsuranceCarrierId");
		apv.responseKeyValidation(response, "name");
		JSONArray arr = new JSONArray(response.body().asString());
		int insuranceId = arr.getJSONObject(0).getInt("id");
		log("insuranceId- " + insuranceId);

		Response getResponse = postAPIRequestDB.getinsuranceById(practiceId, insuranceId);
		apv.responseKeyValidationJson(getResponse, "id");

		Response deleteResponse = postAPIRequestDB.deleteInsurance(practiceId, "/insurancecarrier/", insuranceId);
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_SSO() throws NullPointerException, Exception {
		String b = payloadDB.saveSSOConfigurations();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveSSOConfigurations(practiceId, b, "/sso");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "guid");
        apv.responseKeyValidation(response, "code");

		Response deleteResponse = postAPIRequestDB.deleteSSO(practiceId, "/sso/MF");
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_Speciality() throws NullPointerException, Exception {
		String b = payloadDB.saveSpecialities();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveSpecialities(practiceId, b, "/speciality");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
        
		JSONArray arr = new JSONArray(response.body().asString());
		int specialityId = arr.getJSONObject(0).getInt("id");
		log("Speciality- " + specialityId);

		Response getResponse = postAPIRequestDB.getSpecialityById(practiceId, specialityId);
		apv.responseCodeValidation(getResponse, 200);
		apv.responseTimeValidation(getResponse);
		apv.responseKeyValidationJson(getResponse, "id");

		Response deleteResponse = postAPIRequestDB.deleteSpecility(practiceId, "/speciality/", specialityId);
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testreorderSpecialityReorder() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.reorderSpeciality(practiceId, payloadDB.reorderSpeciality(),
				"/speciality/reorder");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_del_Rule() throws NullPointerException, Exception {
		String b = payloadDB.saveRule();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveRule(practiceId, b, "/configurations/rules");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		String ruleId = apv.responseKeyValidationJson(response, "id");

		log("Rule Id Is- " + ruleId);

		Response deleteResponse = postAPIRequestDB.deleteRule(practiceId, "/configurations/rules/", ruleId);
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testsaveOrUpdateReseller() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.saveOrUpdateReseller(practiceId, payloadDB.saveOrUpdateReseller(),
				"/reseller");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_Prerequisite() throws NullPointerException, Exception {
		String b = payloadDB.savePrerequisiteappointment();
		String prerequisiteAppId = "4244";
		logStep("Verifying the response");
		Response response = postAPIRequestDB.savePrerequisiteappointment(practiceId, b,
				"/prerequisiteappointmenttypes/" + prerequisiteAppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());
		int prerequisiteId = arr.getJSONObject(0).getInt("id");
		log("Speciality- " + prerequisiteId);

		Response getResponse = postAPIRequestDB.getPrerequisiteappointment(practiceId, "/prerequisiteappointmenttypes/",
				prerequisiteAppId);
		apv.responseCodeValidation(getResponse, 200);
		apv.responseTimeValidation(getResponse);
		apv.responseKeyValidationJson(getResponse, "id");

		Response deleteResponse = postAPIRequestDB.deleteSpecility(practiceId, "/prerequisiteappointmenttypes/",
				prerequisiteId);
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_lockOut() throws NullPointerException, Exception {
		String b = payloadDB.lockout();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceId, b, "/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.body().asString());
		int id = arr.getJSONObject(0).getInt("id");
		log("lockout id is - " + id);

		String lockoutsave = payloadDB.saveLockout(id);
		logStep("Verifying the response");
		Response responselockoutsave = postAPIRequestDB.saveLockout(practiceId, lockoutsave, "/lockout/save");
		apv.responseCodeValidation(responselockoutsave, 200);
		apv.responseTimeValidation(responselockoutsave);

		Response deleteResponse = postAPIRequestDB.deletelockout(practiceId, "/lockout/", id);
		apv.responseCodeValidation(deleteResponse, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginless() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.loginless(practiceId, payloadDB.loginless(), "/loginless");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "existingPatient");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfo() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.patientInfo(practiceId, payloadDB.patientInfo(), "/patientinfo");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");
		apv.responseKeyValidationJson(response, "isSearchRequired");
		apv.responseKeyValidationJson(response, "field");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatch() throws NullPointerException, Exception {
		Response response = postAPIRequestDB.patientmatch(practiceId, payloadDB.patientInfo(), "/patientmatch");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");
		apv.responseKeyValidationJson(response, "isSearchRequired");
		apv.responseKeyValidationJson(response, "field");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSavelinks() throws NullPointerException, Exception {
		String b = payloadDB.links();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.links(practiceId, b, "/savelinks");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testReschedule() throws NullPointerException, Exception {
		String b = payloadDB.reschedule();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.reschedule(practiceId, b, "/reschedule");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testfetchRule() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getfetchRule(practiceId, "/LTB/rule");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocation() throws NullPointerException, Exception {
		String b = payloadDB.location();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceId, b, "/location");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationReorder() throws NullPointerException, Exception {
		String b = payloadDB.locationReorder();
		logStep("Verifying the response");
		Response response = postAPIRequestDB.locationReorder(practiceId, b, "/location/reorder");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

}
