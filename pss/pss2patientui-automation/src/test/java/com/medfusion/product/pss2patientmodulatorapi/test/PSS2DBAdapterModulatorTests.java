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
	public static String practiceid;
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
		practiceid = propertyData.getProperty("practice.id.db");
		postAPIRequestDB.setupRequestSpecBuilder(propertyData.getProperty("base.url.db"),
				headerConfig.defaultHeader());
		
		parseJSONFile= new ParseJSONFile();
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testvalidatePracticeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.validatePractice(practiceid, "/validatepractice");
		apv.responseCodeValidation(response, 200);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncement(practiceid, "/announcement");
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
		Response response = postAPIRequestDB.getAnnouncement(practiceid, "/announcementt");
		apv.responseCodeValidation(response, 404);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementSaveInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceid, "");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementSave() throws NullPointerException, Exception {
		
		String b=payloadDB.saveAnnouncementPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncement_Create_Delete() throws NullPointerException, Exception {
		
		String b=payloadDB.createAnnouncementPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveAnnouncement(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		String ann_id=apv.responseKeyValidationJson(response, "id");
		log("ann_id- "+ann_id);
		Response delete_Response=postAPIRequestDB.deleteAnnouncement(practiceid, Integer.parseInt(ann_id));
		apv.responseCodeValidation(delete_Response, 200);
		apv.responseTimeValidation(delete_Response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementDeleteInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response responseDeleteAnn = postAPIRequestDB.deleteAnnouncement(practiceid, 4999);
		apv.responseCodeValidation(responseDeleteAnn, 500);
		String message = apv.responseKeyValidationJson(responseDeleteAnn, "message");
		assertTrue(message.contains("No value present"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementUpdateInvalid() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.updateAnnouncement(practiceid, "");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Required request body is missing"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncementByCode(practiceid, "AG");
		apv.responseCodeValidation(response, 200);

		String type = apv.responseKeyValidationJson(response, "type");
		String code = apv.responseKeyValidationJson(response, "code");

		assertEquals(type, "Greetings");
		assertEquals(code, "AG");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAnnouncementByCodeInvalid() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.getAnnouncementByCode(practiceid, "ZZZ");
		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertTrue(message.contains("Invalid announcement type"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptType() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.saveAppointmenttype(practiceid, b);
		apv.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSaveApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.saveAppointmenttype(practiceid, b);

		apv.responseCodeValidation(response, 400);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message,
				"Extappointmenttypeid=ec5c2faa-57e1-4121-9c0b-fc99a462281d and categoryid=c9cc92fb-06c2-420b-ab60-e95dd5c7af83 already exists");
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptType() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.updateAppointmenttype(practiceid, b);
		apv.responseCodeValidation(response, 200);
		log("Body- " + response.getBody().asString());
		assertEquals(response.getBody().asString(), "true");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpdateApptTypeWithoutApptId() throws NullPointerException, Exception {

		String b = "";
		Response response = postAPIRequestDB.updateAppointmenttype(practiceid, b);

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
		Response response = postAPIRequestDB.getAppointmentTypeByExtApptId(practiceid,extapptid);
		apv.responseCodeValidation(response, 200);
		log("Appointment Id- "+apv.responseKeyValidationJson(response, "id"));
		log("Patient Id- "+apv.responseKeyValidationJson(response, "patientId"));
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypeByIdGet() throws NullPointerException, Exception {

		String apptid = propertyData.getProperty("appt.confm.id.db");
		log("Appt Id- " + apptid);
		Response response = postAPIRequestDB.getAppointmentTypeById(practiceid, apptid);
		apv.responseCodeValidation(response, 200);
		log("Appointment Id- " + apv.responseKeyValidationJson(response, "id"));
		log("Patient Id- " + apv.responseKeyValidationJson(response, "patientId"));
	}
	
	//Appointment Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentsForPracticeGet() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentsForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "patientId");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcomingAppointmentsByPatientIdForPracticeGet01() throws NullPointerException, Exception {

		String patientid = propertyData.getProperty("patientid.upcommingapp.id");
		String currentdate = pssPatientUtils.sampleDateTime("MM/dd/yyyy");

		Response response = postAPIRequestDB.getUpcomingAppointmentsByPatientIdForPractice(practiceid, patientid,
				currentdate);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "patientId");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment_SaveAppointment() throws NullPointerException, Exception {

		String b = payloadDB.saveAppointmentPayload();
		Response response = postAPIRequestDB.saveAppointment(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment_UpdateAppointment() throws NullPointerException, Exception {

		String b = payloadDB.updateAppointmentPayload();
		Response response = postAPIRequestDB.updateAppointment(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointment_getAppointmentByExtApptIdForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentsForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		
		JSONArray arr = new JSONArray(response.body().asString());

		String extapptid = arr.getJSONObject(1).getString("extApptId");

		Response extApptResponse = postAPIRequestDB.getAppointmentByExtApptIdForPractice(practiceid, extapptid);
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

		Response response = postAPIRequestDB.patientappointmentsbyrange(practiceid, patientid,
				startdate, enddate);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "patientId");		
		
		JSONArray arr = new JSONArray(response.body().asString());

		String extapptid = arr.getJSONObject(0).getString("extApptId");
		log("extApptId-"+extapptid);

		Response extApptResponse = postAPIRequestDB.getAppointmentByExtApptIdForPractice(practiceid, extapptid);
		apv.responseCodeValidation(extApptResponse, 200);
		apv.responseTimeValidation(extApptResponse);
		apv.responseKeyValidation(extApptResponse, "patientId");
		apv.responseKeyValidationJson(extApptResponse, "id");
	}
	
	//Appointment Type Config Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testtAppointmentTypeConfigGet() throws NullPointerException, Exception {

		String apptypeid = propertyData.getProperty("appttypeconfig.appttype.id.db");

		Response response = postAPIRequestDB.getAppointmentTypeConfig(practiceid, apptypeid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testtAppointmentTypeConfigGetInvalid() throws NullPointerException, Exception {

		String apptypeid = "9999";

		Response response = postAPIRequestDB.getAppointmentTypeConfig(practiceid, apptypeid);

		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		String message = apv.responseKeyValidationJson(response, "message");
		assertEquals(message, "No Appointmenttype found for appointmenttypeid=9999");
	}
	
	//Appointment Type Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationForNoBookGet() throws NullPointerException, Exception {

		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesByLocationForNoBook(practiceid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesForPracticeGet() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppTypeByIdGet() throws NullPointerException, Exception {

		String apptype =propertyData.getProperty("appttypeconfig.appttype.id.db");
		
		Response response = postAPIRequestDB.getAppTypeById(practiceid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByBookGet() throws NullPointerException, Exception {

		String bookid= propertyData.getProperty("appttype.book.id.db");
		Response response = postAPIRequestDB.getAppointmentTypesByBook(practiceid, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationGet() throws NullPointerException, Exception {

		String locationid = propertyData.getProperty("appttype.location.id.db");
		Response response = postAPIRequestDB.getAppointmentTypesByLocation(practiceid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesByLocationAndBooksGet() throws NullPointerException, Exception {

		String bookid = propertyData.getProperty("appttype.book.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesByLocationAndBooks(practiceid, bookid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialty(practiceid, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndBookGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String bookid= propertyData.getProperty("appttype.specialty.book.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndBook(practiceid, specialty,bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndBookAndLocationGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String bookid = propertyData.getProperty("appttype.specialty.book.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndBookAndLocation(practiceid, specialty,
				bookid, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesBySpecialtyAndLocationGet() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("appttype.specialty.id.db");
		String locationid = propertyData.getProperty("appttype.location.id.db");

		Response response = postAPIRequestDB.getAppointmentTypesBySpecialtyAndLocation(practiceid, specialty,
				locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testApptypeExtByIdGet() throws NullPointerException, Exception {

		String extapptid = propertyData.getProperty("appttype.extappt.id.db");

		Response response = postAPIRequestDB.getApptypeExtById(practiceid, extapptid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesWithLanguageForPracticeGetEnglish() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesWithLanguageForPractice(practiceid, "EN");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesWithLanguageForPracticeGetEspanol() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getAppointmentTypesWithLanguageForPractice(practiceid, "ES");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppAuthUserByUserIdGet() throws NullPointerException, Exception {

		String id = propertyData.getProperty("extuserid.authappuser.id.db");		

		Response response = postAPIRequestDB.getAppAuthUserByUserId(practiceid, id);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppAuthUserByExtUserIdGet() throws NullPointerException, Exception {

		String extuserid = propertyData.getProperty("extuserid.db");

		Response response = postAPIRequestDB.getAppAuthUserByExtUserId(practiceid, extuserid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book App Type Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppTyp_BookAppTypesAssociatedToBookGet() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");
		Response response = postAPIRequestDB.getBookAppTypesAssociatedToBook(practiceid, bookid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookAppTyp_AppTypesAssociatedToBookGet() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getAppTypesAssociatedToBook(practiceid, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByBookId() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		Response response = postAPIRequestDB.getAssociatedBookAppTypesByBookId(practiceid, bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookApp_getAssociatedBookAppTypesByAppTypeId() throws NullPointerException, Exception {

		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getAssociatedBookAppTypesByAppTypeId(practiceid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book Appointment Type Location Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testbookapptypelocation_getLocationsAssociatedToBookAndAppointmentType() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getLocationsAssociatedToBookAndAppointmentType(practiceid, bookid,apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Book Controller

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getBooksForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookById() throws NullPointerException, Exception {

		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getBookById(practiceid,Integer.parseInt(bookid));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookExtById() throws NullPointerException, Exception {
		
		String extbokid=propertyData.getProperty("book.extbook.id");

		Response response = postAPIRequestDB.getBookExtById(practiceid, extbokid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookWithLinksById() throws NullPointerException, Exception {
		
		String bookid=propertyData.getProperty("bookapttype.book.id.db");

		Response response = postAPIRequestDB.getBookWithLinksById(practiceid,bookid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksWithLanguageForPractice() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getBooksWithLanguageForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBookByPracticeAndName() throws NullPointerException, Exception {
		
		String bookname=propertyData.getProperty("book.bookname.db");
		String extbookid=propertyData.getProperty("book.extbook.id.db");

		Response response = postAPIRequestDB.getBookByPracticeAndName(practiceid, bookname);
		String actualextbookid= response.getBody().asString();
		log("actualextbookid- "+actualextbookid);
		assertEquals(actualextbookid, extbookid, "External Book id is not matching with expected id");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksByAppType() throws NullPointerException, Exception {

		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksByAppType(practiceid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)

	public void testBook_getBooksByLocation() throws NullPointerException, Exception {		

		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		Response response = postAPIRequestDB.getBooksByLocation(practiceid,Integer.parseInt(locationid));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksByLocationAndAppointmentTypes() throws NullPointerException, Exception {
		
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksByLocationAndAppointmentTypes(practiceid, locationid , apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpeciality() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");

		Response response = postAPIRequestDB.getBooksBySpeciality(practiceid, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndAppointmentType() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");

		Response response = postAPIRequestDB.getBooksBySpecialityAndAppointmentType(practiceid, specialty, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndLocation() throws NullPointerException, Exception {
		
		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		Response response = postAPIRequestDB.getBooksBySpecialityAndLocation(practiceid, specialty, locationid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getBooksBySpecialityAndLocationAndAppointmentType() throws NullPointerException, Exception {

		String specialty = propertyData.getProperty("bookapttype.specialty.id.db");
		String locationid = propertyData.getProperty("bookapttype.location.id.db");
		String apptype=propertyData.getProperty("bookapttype.apttype.id.db");
		
		Response response = postAPIRequestDB.getBooksBySpecialityAndLocationAndAppointmentType(practiceid,specialty, locationid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Cancellation Reason Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelReason_getCancellationReason() throws NullPointerException, Exception {

		Response response = postAPIRequestDB.getCancellationReason(practiceid,"/cancellationreason");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Care Team Book Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeamBook_getBookAssociatedToCareTeam() throws NullPointerException, Exception {
		
		String careteamid=propertyData.getProperty("careteambook.careteam.id.db");
		Response response = postAPIRequestDB.getBookAssociatedToCareTeam(practiceid, Integer.parseInt(careteamid));
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
		
		Response saveResponse = postAPIRequestDB.associateCareTeamToBook(practiceid, b);
		apv.responseCodeValidation(saveResponse, 200);
		apv.responseTimeValidation(saveResponse);
		
		JSONArray arr = new JSONArray(saveResponse.body().asString());

		int careteamid = arr.getJSONObject(0).getJSONObject("careTeam").getInt("id");
		log("Care Team Id- "+careteamid);
		String bookid=propertyData.getProperty("careteambook.boook.id.db") ;
		log("Book Id- "+bookid);

		Response response = postAPIRequestDB.getBookAssociatedToCareTeam(practiceid, careteamid);
		apv.responseCodeValidation(response, 200);
		
		Response deleteResponse= postAPIRequestDB.deleteCareTeamBook(practiceid, careteamid, Integer.parseInt(bookid));
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	
	
	//care-team-controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareteamsForPracticel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCareteamsForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareTeamById() throws NullPointerException, Exception {
		
		String careteamid=propertyData.getProperty("careteambook.careteam.id.db");
		Response response = postAPIRequestDB.getCareTeamById(practiceid, Integer.parseInt(careteamid));
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
		
		Response saveResponse=postAPIRequestDB.saveCareTeam(practiceid, c);
		apv.responseCodeValidation(saveResponse, 200);
		apv.responseTimeValidation(saveResponse);
		
		Response createResponse=postAPIRequestDB.saveCareTeam(practiceid, b);
		apv.responseCodeValidation(createResponse, 200);
		apv.responseTimeValidation(createResponse);
		
		JSONArray arr = new JSONArray(createResponse.body().asString());
		int careteamid = arr.getJSONObject(0).getInt("id");

		Response response = postAPIRequestDB.getCareTeamById(practiceid, careteamid);
		apv.responseCodeValidation(response, 200);
		
		Response deleteResponse=postAPIRequestDB.deleteCareTeamById(practiceid, careteamid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	//Category App Type Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getAppTypeForCategory() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("");
		Response response = postAPIRequestDB.getAppTypeForCategory(practiceid, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getCategoryAppTypeForCategoryAndAppttypel() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		String apptype=propertyData.getProperty("categoryapp.apptype.id.db");
		
		Response response = postAPIRequestDB.getCategoryAppTypeForCategoryAndAppttype(practiceid, categoryid, apptype);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	
	//Category App Type Location Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCatgoryAppType_getLocationsForCategoryAppType() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		
		Response response = postAPIRequestDB.getLocationsForCategoryAppType(practiceid, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	
	//Category Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategoryById() throws NullPointerException, Exception {
		
		String categoryid=propertyData.getProperty("categoryapp.category.id.db");
		Response response = postAPIRequestDB.getCategoryById(practiceid, categoryid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategoryForPractice() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCategoryForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategorysWithLanguageForPractice() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCategorysWithLanguageForPractice(practiceid);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getCategorysBySpecialty() throws NullPointerException, Exception {
		
		String specialty=propertyData.getProperty("bookapttype.specialty.id.db");
		
		Response response = postAPIRequestDB.getCategorysBySpecialty(practiceid, specialty);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_save_get_delete() throws NullPointerException, Exception {
		
		String b= payloadDB.saveCategory();
		
		Response saveResponse=postAPIRequestDB.saveCategory(practiceid, b);
		apv.responseCodeValidation(saveResponse, 200);
		
		JSONArray arr = new JSONArray(saveResponse.body().asString());

		int categoryid = arr.getJSONObject(0).getInt("id");
		
		Response deleteResponse = postAPIRequestDB.deleteCategory(practiceid, categoryid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_reorderCategory() throws NullPointerException, Exception {
		
		String b=payloadDB.reorderPayload();
		
		Response response = postAPIRequestDB.reorderCategory(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getVisitReason() throws NullPointerException, Exception {
		
		String b=payloadDB.visitReasonPayload();
		
		Response response = postAPIRequestDB.getVisitReason(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	//Category Specialty Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_Specialty_Save_delete() throws NullPointerException, Exception {
		
		String b=payloadDB.categoryspecialtyPayload();
		String categoryid=propertyData.getProperty("categoryspecialty.category.id.db");
		String specialtyid=propertyData.getProperty("categoryspecialty.specialty.id.db");
		
		Response response = postAPIRequestDB.saveCategorySpecialty(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		Response deleteResponse = postAPIRequestDB.deleteCategorySpecialty(practiceid,categoryid, specialtyid);
		apv.responseCodeValidation(deleteResponse, 200);
		apv.responseTimeValidation(deleteResponse);		
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCategory_getSpecialtiesAssociatedToCategory() throws NullPointerException, Exception {
		
		String b=payloadDB.getSpecialtiesAssociatedToCategoryPayload();
		
		Response response = postAPIRequestDB.getSpecialtiesAssociatedToCategory(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}

	
	public void testStateGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceid, "/states");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidation(response, "statecode");
		apv.responseKeyValidation(response, "statename");
		apv.responseKeyValidationJson(response, "active");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testStateInvalidPAthGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceid, "/statesa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceid, "/sso");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoConfig(practiceid, "/ssoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ssoCode(practiceid, "/sso/MF");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
		apv.responseKeyValidationJson(response, "newPatients");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSSOConfigCodeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.State(practiceid, "/sso/MFa");
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

		Response response = postAPIRequestDB.speciality(practiceid, ("/speciality"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extSpecialtyId");
		apv.responseKeyValidationJson(response, "specialty");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");

		Response response = postAPIRequestDB.speciality(practiceid, ("/specialityaa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = propertyData.getProperty("speciality.id");
		Response response = postAPIRequestDB.State(practiceid, ("/speciality/" + id));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByInvalidIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String id = "20360411";
		Response response = postAPIRequestDB.specilityById(practiceid, ("/speciality/" + id));
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Specialty found for id = " + id);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceid, ("/specialties"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSpecialityByIdandLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.specilityByIdLanguage(practiceid, ("/specialtiesa"));
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceid, ("/configurations/rules"));
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseKeyValidationJson(response, "rule");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRuleInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.ruleAll(practiceid, ("/configurations/rulesaa"));
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
		Response response = postAPIRequestDB.resellerPractice(practiceid, "/reseller");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerPractice(practiceid, "/reselleraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceid, "/resellerbylanguage");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguage(practiceid, "/resellerbylanguageaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceid, "/resellerbylanguageforui");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testresellerPracticeByLanguageForUIInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.resellerByLanguageByUI(practiceid, "/resellerbylanguageforuiaa");
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
		Response response = postAPIRequestDB.practiceInfo(practiceid, "/practiceinfo");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeInfoInvalidGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceInfo(practiceid, "/practiceinfoaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceid, "/practice");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extPracticeId");
		apv.responseKeyValidationJson(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeDetailsInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceDetails(practiceid, "/practiceaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceid, "/patientmatch/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatch(practiceid, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceid, "/patientmatchmaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientMatchMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientMatchMaster(practiceid, "/patientmatchmasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceid, "/patientinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceid, "/patientmatchaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceid, "/patientinfomaster/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "entity");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientInfoMasterInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.patientInfo(practiceid, "/patientinfomasteraa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceid, "/partnercustomandinfo/LG_LOG");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "partnerCustomId");
		apv.responseKeyValidationJson(response, "paernerInfoId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustomandInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustomandInfo(practiceid, "/partnercustomandinfoaa/LG_LOG");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceid, "/partnercustommetadata", "ZIP");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerCustMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerCustMetaData(practiceid, "/partnercustommetadataaa", "ZIP");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaData() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerMetaData(practiceid, "/partnermetadata");
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
		Response response = postAPIRequestDB.partnerMetaData(practiceid, "/partnermetadataaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerMetaDataByCode() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String code = propertyData.getProperty("partnermetadata.code");
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceid, "/partnermetadatabycode/" + code);
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
		Response response = postAPIRequestDB.partnerMetaDataByCode(practiceid, "/partnermetadatabycodeaa/" + code);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerConfig(practiceid, "/partner");
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
		Response response = postAPIRequestDB.partnerConfig(practiceid, "/partneraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPartnerDetailsConfig() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.partnerDetails(practiceid, "/partnerdetails");
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
		Response response = postAPIRequestDB.partnerDetails(practiceid, "/partnerdetailsaa");
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
		Response response = postAPIRequestDB.loginless(practiceid, "/loginless");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "guid");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testloginlessInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.loginless(practiceid, "/loginlessaa");
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
		Response response = postAPIRequestDB.lockout(practiceid,"/lockout");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "type");
	
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlockoutInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.lockout(practiceid,"/lockoutaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocation() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.location(practiceid,"/location");
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
		Response response = postAPIRequestDB.location(practiceid,"/location");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationById() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String locationId=propertyData.getProperty("location.id.db");
		Response response = postAPIRequestDB.locationById(practiceid,"/location/"+locationId);
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
		Response response = postAPIRequestDB.locationById(practiceid,"/location/"+InvalidlocationId);
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
		Response response = postAPIRequestDB.locationByAppId(practiceid,"/location/appointmenttype/"+AppId);
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
		Response response = postAPIRequestDB.locationByAppId(practiceid,"/location/appointmenttype/"+AppId);
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
		Response response = postAPIRequestDB.locationByAppointmentId(practiceid,"/location/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationByAppointmentId(practiceid,"/location/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationBook(practiceid,"/location/book/"+bookId);
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
		Response response = postAPIRequestDB.locationBook(practiceid,"/location/book/"+invalidbooId);
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
		Response response = postAPIRequestDB.locationBookAppType(practiceid,"/location/book/"+bookId+"/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationBookAppType(practiceid,"/location/book/"+invalidbooId+"/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationSpeciality(practiceid,"/location/specialty/"+specialtyId);
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
		Response response = postAPIRequestDB.locationSpeciality(practiceid,"/location/specialty/"+invalidspecialtyId);
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
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceid,"/location/specialty/"+specialtyId+"/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationSpecialityAppType(practiceid,"/location/specialty/"+invalidSpecialityId+"/apptype/"+AppId);
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
		Response response = postAPIRequestDB.locationSpecialityBook(practiceid,"/location/specialty/"+specialtyId+"/book/"+bookId);
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
		Response response = postAPIRequestDB.locationSpecialityBook(practiceid,"/location/specialty/"+invalidSpecialityId+"/book/"+AppId);
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
		Response response = postAPIRequestDB.locationLinkbyid(practiceid,"/locationlinkbyid/"+locationId);
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
		Response response = postAPIRequestDB.locationLinkbyid(practiceid,"/locationlinkbyid/"+invalidLocationId);
		apv.responseCodeValidation(response, 400);
		apv.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String message = js.getString("message");
		assertEquals(message, "No Location found for id="+invalidLocationId);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testlocationsGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.locations(practiceid,"/locations");
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
		Response response = postAPIRequestDB.locations(practiceid,"/locationsaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceid,"/insurancecarrier");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "extInsuranceCarrierId");
		apv.responseKeyValidationJson(response, "name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testinsuranceCarrierInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.insuranceCarrrier(practiceid,"/insurancecarrieraa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierByIdGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String insuranceId=propertyData.getProperty("insurance.id.db");
        Response response = postAPIRequestDB.insuranceCarrrierById(practiceid,"/insurancecarrier/"+insuranceId);
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
		Response response = postAPIRequestDB.insuranceCarrrierById(practiceid,"/insurancecarrieraa"+insuranceId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.flowIdentity(practiceid,"/flowidentity", flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "code");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testflowIdentityInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.flowIdentity(practiceid,"/flowidentityaa", flowType);
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
        Response response = postAPIRequestDB.rescheduleApp(practiceid,"/reschedule/"+AppId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "id");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleAppInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String AppId=propertyData.getProperty("reschedule.aaptypeid.db");
		Response response = postAPIRequestDB.rescheduleApp(practiceid,"/rescheduleaa/"+AppId);
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
        Response response = postAPIRequestDB.rescheduleApp(practiceid,"/prerequisiteappointmenttypes/"+AppId);
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
		Response response = postAPIRequestDB.rescheduleApp(practiceid,"/prerequisiteappointmenttypesaa/"+AppId);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);
	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisiteAppInvaliIdGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String invalidAppID="12345";
		Response response = postAPIRequestDB.rescheduleApp(practiceid,"/prerequisiteappointmenttypes/"+invalidAppID);
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
        Response response = postAPIRequestDB.practiceMetaData(practiceid,"/customdata",flowType);
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
		Response response = postAPIRequestDB.practiceMetaData(practiceid, "/customdataaa/", flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataAllGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceid,"/practicecustomalldata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetadataAllInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceid, "/practicecustomalldataaaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataInfoGet() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType=propertyData.getProperty("flowtype.db");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceid,"/practicecustomandinfo/"+flowType);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		apv.responseKeyValidationJson(response, "practiceInfoId");
		

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetadataInfoInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		String flowType = propertyData.getProperty("flowtype.db");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceid, "/practicecustomandinfoaa/"+flowType);
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaData1Get() throws NullPointerException, Exception {

		logStep("Verifying the response");
        Response response = postAPIRequestDB.practiceMetaDataAll(practiceid,"/practicemetdata");
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPracticeMetaDataInvalidPathGET() throws NullPointerException, Exception {

		logStep("Verifying the response");
		Response response = postAPIRequestDB.practiceMetaDataAll(practiceid, "/practicemetdataaa");
		apv.responseCodeValidation(response, 404);
		apv.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSave_get_del_Book() throws NullPointerException, Exception {
		
		String b= payloadDB.createBook();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveBook(practiceid, "/book",b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
		
		JSONArray arr = new JSONArray(response.body().asString());

		int bookid = arr.getJSONObject(0).getInt("id");
		log("Bookid- "+bookid);
			
		Response getResponse =postAPIRequestDB.getBookById(practiceid, bookid) ;
		
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.deleteBook(practiceid, "/book/", bookid);
		
		apv.responseCodeValidation(deleteResponse, 200);	
	}
	
	//Book Location Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_save_get_del() throws NullPointerException, Exception {
		
		String b= payloadDB.saveBookLocationPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.saveBookLocation(practiceid, "/booklocation",b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.asString());

		int bookid = arr.getJSONObject(0).getJSONObject("book").getInt("id");
		log("Bookid- "+bookid);
		int locationid = arr.getJSONObject(0).getJSONObject("location").getInt("id");
		log("Location Id- "+locationid);
			
		Response getResponse =postAPIRequestDB.getBooksByLocation(practiceid, locationid) ;
		apv.responseCodeValidation(getResponse, 200);
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.deleteBookLocation(practiceid, bookid, locationid);
		
		apv.responseCodeValidation(deleteResponse, 200);	
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBookLocation_getLocationsAssociatedToBook() throws NullPointerException, Exception {
		
		String b= payloadDB.bookLocationsGetPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.bookLocations(practiceid, b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);	
	}
	
	//Cancellation Reason Controller
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReason_save_get_del() throws NullPointerException, Exception {
		
		String b= payloadDB.cancellationReasonPayload();

		logStep("Verifying the response");
		Response response = postAPIRequestDB.cancellationReasonSave(practiceid,b);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);

		JSONArray arr = new JSONArray(response.asString());

		String name = arr.getJSONObject(0).getString("name");
		log("Cancellation Reason- "+name);
		
		int reasonid=arr.getJSONObject(0).getInt("id");
			
		Response getResponse =postAPIRequestDB.getCancellationReasonById(practiceid, reasonid) ;
		apv.responseCodeValidation(getResponse, 200);
		apv.responseKeyValidationJson(getResponse, "id");
		
		Response deleteResponse=postAPIRequestDB.cancellationReasonDelete(practiceid, reasonid);
		
		apv.responseCodeValidation(deleteResponse, 200);
		
		String c=payloadDB.reorderPayload();
		
		Response reorderResponse=postAPIRequestDB.reorderCancellationReason(practiceid, c);
		
		apv.responseCodeValidation(reorderResponse, 200);
	}
	

}
