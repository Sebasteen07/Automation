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
		Response response = postAPIRequestDB.getBooksByLocation(practiceId,locationid);
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
		Response response = postAPIRequestDB.getBookAssociatedToCareTeam(practiceId, careteamid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "careteam");
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "displayName");
		apv.responseKeyValidationJson(response, "extBookId");
		apv.responseTimeValidation(response);
	}
	
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareteamsForPracticel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getCareteamsForPractice(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareTeam_getCareTeamById() throws NullPointerException, Exception {
		
		String careteamid=propertyData.getProperty("careteambook.careteam.id.db");
		Response response = postAPIRequestDB.getCareTeamById(practiceId, careteamid);
		apv.responseCodeValidation(response, 200);
		apv.responseKeyValidationJson(response, "createdUserId");
		apv.responseKeyValidationJson(response, "id");
		apv.responseKeyValidationJson(response, "name");
		apv.responseTimeValidation(response);
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
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testBook_getbooklevel() throws NullPointerException, Exception {
		
		Response response = postAPIRequestDB.getbooklevel(practiceId);
		apv.responseCodeValidation(response, 200);
		apv.responseTimeValidation(response);
	}
	
	
	
	

	
}
