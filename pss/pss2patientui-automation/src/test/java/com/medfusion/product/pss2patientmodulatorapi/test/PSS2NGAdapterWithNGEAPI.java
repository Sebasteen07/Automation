// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.ParseJSONFile;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
import com.medfusion.product.pss2patientapi.payload.PayloadNG;
import com.medfusion.product.pss2patientapi.payload.PayloadNGEAPI;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PSS2NGAdapterWithNGEAPI extends BaseTestNG {

	public static PayloadNGEAPI payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNG postAPIRequest;
	public static PSSPatientUtils pSSPatientUtils;
	public static String practiceid;
	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		payload = new PayloadNGEAPI();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequest = new PostAPIRequestNG();
		pSSPatientUtils= new PSSPatientUtils();
		log("I am before Test");
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("base.url.ng"));
		log("BASE URL-" + propertyData.getProperty("base.url.ng"));
		
		practiceid=propertyData.getProperty("practice.id.ng");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsNGPost() throws IOException, InterruptedException, ParseException {
		
		String startdate=pSSPatientUtils.sampleDateTime("MM/dd/yyyy HH:MM:SS");
		String enddate1=pSSPatientUtils.addDaysToDate(startdate, "1","MM/dd/yyyy HH:MM:SS");		
		log("End Date- "+enddate1);		
		String b=PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng"), startdate, enddate1);		
		
		Response response = postAPIRequest.availableSlots(b, practiceid);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsNGPostInvalidPayload() throws IOException, InterruptedException {
		Response response = postAPIRequest.availableSlots("", propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 400);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastApptNgPOST() throws IOException {

		String edate=pSSPatientUtils.sampleDateTime("MM/dd/yyyy");

		Response response = postAPIRequest.pastApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.past_appt_payload(propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("practice.displayname.ng"),
						propertyData.getProperty("practice.id.ng"), edate));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "appointmentTypes.name");
		aPIVerification.responseKeyValidationJson(response, "book.resourceId");
		aPIVerification.responseKeyValidationJson(response, "location.name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastApptWithoutPayloadPOST() throws IOException, InterruptedException {

		Response response = postAPIRequest.pastApptNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testnextAvailableSlotPost() throws IOException {

		Response response = postAPIRequest.nextAvailableNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.nextAvailable_New());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testnextAvailableSlotWithoutBodyPost() throws IOException {

		Response response = postAPIRequest.nextAvailableNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(propertyData.getProperty("practice.id.ng"), "49911");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "startDateTime");
		aPIVerification.responseKeyValidationJson(response, "status");
		aPIVerification.responseKeyValidationJson(response, "appointmentTypeId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusWithoutPidGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "PatientId Should Not Be Empty...!!!");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesGET() throws IOException {

		Response response = postAPIRequest.appointmentType(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesInvalidPathGET() throws IOException {

		Response response = postAPIRequest.appointmentType("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testschedule_Resc_NGPOST() throws NullPointerException, Exception {
		

		String startdate = pSSPatientUtils.sampleDateTime("MM/dd/yyyy HH:MM:SS");
		String enddate = pSSPatientUtils.addDaysToDate(startdate, "1", "MM/dd/yyyy HH:MM:SS");

		String b = PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng"), startdate, enddate);

		Response response = postAPIRequest.availableSlots(b, practiceid);

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String startDateTime = js.getString("availableSlots[0].startDateTime");
		String startDateTimeResch = js.getString("availableSlots[1].startDateTime");
		Response scheduleApptResponse = postAPIRequest.scheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.schedule_Payload(startDateTime, propertyData.getProperty("slot.end.time.ng")));
		aPIVerification.responseCodeValidation(scheduleApptResponse, 200);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		String apptid = aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		log("Appointment id - " + apptid);

		Response rescheduleResponse = postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.reschedule_Payload(startDateTimeResch,
						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"), apptid));
		aPIVerification.responseTimeValidation(rescheduleResponse);
		aPIVerification.responseCodeValidation(rescheduleResponse, 200);
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testscheduleWithoutBody() throws NullPointerException, Exception {

		Response scheduleApptResponse = postAPIRequest.scheduleApptNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(scheduleApptResponse, 400);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleWithoutBody() throws NullPointerException, Exception {

		Response scheduleApptResponse = postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(scheduleApptResponse, 400);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcommingApptPOST() throws IOException {
		
		String startdate = pSSPatientUtils.sampleDateTime("MM/dd/YYYY HH:MM:SS");

		Response response = postAPIRequest.upcommingApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.upcommingApt_Payload(propertyData.getProperty("uppcomming.patient.id.ng"),
						propertyData.getProperty("practice.id.ng"), startdate));
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "startDateTime");
		aPIVerification.responseKeyValidation(response, "endDateTime");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcommingApptWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.upcommingApptNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypeListGET() throws IOException {

		Response response = postAPIRequest.appointmentType(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "categoryId");
		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesListInvalidPathGET() throws IOException {

		Response response = postAPIRequest.appointmentType("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleApptNGPInvalidAppIdPOST() throws IOException {
		// this test case actual failed case
		Response response = postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.reschedule_Payload(propertyData.getProperty("start.date.time.ng"),
						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"),
						propertyData.getProperty("invalidappt.id.ng")));
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "500 Unable to reschedule appointment");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentGet() throws IOException {

		Response response = postAPIRequest.cancelAppointmentGET(propertyData.getProperty("practice.id.ng"),
				propertyData.getProperty("appt.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentInvalidAppIdGet() throws IOException {

		Response response = postAPIRequest.cancelAppointmentGET(propertyData.getProperty("practice.id.ng"),
				propertyData.getProperty("invalidappt.id.ng"));
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentPost() throws IOException {

		Response response = postAPIRequest.cancelAppointmentPOST(propertyData.getProperty("practice.id.ng"),
				payload.cancelAppointment);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentWithoutBodyPost() throws IOException {

		Response response = postAPIRequest.cancelAppointmentPOST(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGET() throws IOException {

		Response response = postAPIRequest.cancellationReason(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "reasonType");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.cancellationReason("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareproviderAvailabilityPOST() throws IOException {
		String startdate = pSSPatientUtils.sampleDateTime("MM/dd/yyyy");
		String enddate1 = pSSPatientUtils.addDaysToDate(startdate, "100", "MM/dd/yyyy");
		Response response = postAPIRequest.careproviderAvailability(propertyData.getProperty("practice.id.ng"),
				PayloadNG.careprovideravailability_Payload(startdate, enddate1));

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareproviderAvailabilityWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.careproviderAvailability(propertyData.getProperty("practice.id.ng"), "");

		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "zipCode");
		aPIVerification.responseKeyValidation(response, "address1");
		aPIVerification.responseKeyValidation(response, "address2");
		aPIVerification.responseKeyValidation(response, "state");
		aPIVerification.responseKeyValidation(response, "city");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsListNGGET() throws IOException {

		Response response = postAPIRequest.locations(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		
		JSONArray arr = new JSONArray(response.body().asString());
		int l = arr.length();
		log("Length is- " + l);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsListWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.locations("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientPOST() throws IOException {

		Response response = postAPIRequest.addPatient(propertyData.getProperty("practice.id.ng"),
				PayloadNG.addPatient());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "firstName");
		aPIVerification.responseKeyValidationJson(response, "lastName");
		aPIVerification.responseKeyValidationJson(response, "dateOfBirth");
		aPIVerification.responseKeyValidationJson(response, "emailAddress");
		aPIVerification.responseKeyValidationJson(response, "gender");
		aPIVerification.responseKeyValidationJson(response, "phoneNumber");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.addPatient(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsGET() throws IOException {

		Response response = postAPIRequest.demographics(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "firstName");
		aPIVerification.responseKeyValidationJson(response, "lastName");
		aPIVerification.responseKeyValidationJson(response, "dateOfBirth");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.demographics("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGET() throws IOException {

		Response response = postAPIRequest.lockout(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "key");
		aPIVerification.responseKeyValidation(response, "value");
		aPIVerification.responseKeyValidation(response, "type");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientPOST() throws IOException {

		PayloadNG payload = new PayloadNG();

		Response response = postAPIRequest.matchPatientPOST(propertyData.getProperty("practice.id.ng"),
				payload.matchpatient);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.matchPatientPOST(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitGET() throws IOException {

		Response response = postAPIRequest.patientLastVisit(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "lastVisitDateTime");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.patientLastVisit("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRecordbyApptTypePOST() throws IOException {

		Response response = postAPIRequest.patientRecordbyApptTypePOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbyapptypes_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRecordbyApptTypeWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.patientRecordbyApptTypePOST(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchPatientPOST() throws IOException {

		Response response = postAPIRequest.searchpatient(propertyData.getProperty("practice.id.ng"),
				payload.searchpatient);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "firstName");
		aPIVerification.responseKeyValidation(response, "lastName");
		aPIVerification.responseKeyValidation(response, "dateOfBirth");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.searchpatient(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientStatusGET() throws IOException {

		Response response = postAPIRequest.patietStatus(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "key");
		aPIVerification.responseKeyValidation(response, "value");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientStatusWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.patietStatus("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteappointmenttypesPOST() throws IOException {

		Response response = postAPIRequest.prerequisteappointmenttypesPOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.prerequisteappointmenttypes_Payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "appointmentTypeId");
		aPIVerification.responseKeyValidation(response, "apptCategoryId");
		aPIVerification.responseKeyValidationJson(response, "showApptType");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteappointmenttypesWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.prerequisteappointmenttypesPOST(propertyData.getProperty("practice.id.ng"),
				"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientrecordbybooksPOST() throws IOException {

		Response response = postAPIRequest.patientrecordbyBooks(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbybooks_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "patientRecord");
		ParseJSONFile.getKey(jsonobject, "bookId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientrecordbybooksWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.patientrecordbyBooks(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderPOST() throws IOException {

		Response response = postAPIRequest.lastseenProvider(propertyData.getProperty("practice.id.ng"),
				payload.lastseenprovider);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "resourceId");
		aPIVerification.responseKeyValidationJson(response, "providerAvailability");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.lastseenProvider(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFetchNGBookListGET() throws IOException {

		Response response = postAPIRequest.fetchNGBookList(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "resourceId");
		aPIVerification.responseKeyValidationJson(response, "resourceName");
		aPIVerification.responseKeyValidationJson(response, "displayName");
		aPIVerification.responseKeyValidationJson(response, "type");
		aPIVerification.responseKeyValidationJson(response, "categoryId");
		aPIVerification.responseKeyValidationJson(response, "categoryName");
		aPIVerification.responseKeyValidationJson(response, "providerId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFetchNGBookListWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.fetchNGBookList("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

}
