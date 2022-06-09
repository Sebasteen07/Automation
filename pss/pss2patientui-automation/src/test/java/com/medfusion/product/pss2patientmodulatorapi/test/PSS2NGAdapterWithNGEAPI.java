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
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNGE;
import com.medfusion.product.pss2patientapi.payload.PayloadNG;
import com.medfusion.product.pss2patientapi.payload.PayloadNGEAPI;
import com.medfusion.product.pss2patientui.pojo.AdminUser;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSNewPatient;
import com.medfusion.product.pss2patientui.utils.PSSPatientUtils;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PSS2NGAdapterWithNGEAPI extends BaseTestNG {

	public static PayloadNGEAPI payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNGE postAPIRequest;
	public static PSSPatientUtils pSSPatientUtils;
	public static String practiceId;
	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		payload = new PayloadNGEAPI();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequest = new PostAPIRequestNGE();
		pSSPatientUtils= new PSSPatientUtils();
		log("I am before Test");
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("base.url.nge"));
		log("BASE URL-" + propertyData.getProperty("base.url.nge"));
		
		practiceId=propertyData.getProperty("practice.id.nge.api");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsNGPost() throws IOException, InterruptedException, ParseException {
		
		String startdate=pSSPatientUtils.sampleDateTime("MM/dd/yyyy HH:MM:SS");
		String enddate1=pSSPatientUtils.addDaysToDate(startdate, "1","MM/dd/yyyy HH:MM:SS");		
		log("End Date- "+enddate1);		
		String b=PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng"), startdate, enddate1);		
		
		Response response = postAPIRequest.availableSlots(b, practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvailableSlotsNGPostInvalidPayload() throws IOException, InterruptedException {
		Response response = postAPIRequest.availableSlots("", practiceId);
		aPIVerification.responseCodeValidation(response, 400);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastApptPOST() throws IOException {

		String edate=pSSPatientUtils.sampleDateTime("MM/dd/yyyy");

        String patientId="b1d6f475-2074-42e7-a2f8-f9e3fbc8ea9d";
		Response response = postAPIRequest.pastApptNG(practiceId,
				PayloadNGEAPI.past_appt_payload(patientId, edate));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "appointmentTypes.name");
		aPIVerification.responseKeyValidationJson(response, "book.resourceId");
		aPIVerification.responseKeyValidationJson(response, "location.name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPastApptWithoutPayloadPOST() throws IOException, InterruptedException {

		Response response = postAPIRequest.pastApptNG(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testnextAvailableSlotPost() throws IOException {

		Response response = postAPIRequest.nextAvailableNG(practiceId,
				PayloadNG.nextAvailable_New());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testnextAvailableSlotWithoutBodyPost() throws IOException {

		Response response = postAPIRequest.nextAvailableNG(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(practiceId, "49911");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "startDateTime");
		aPIVerification.responseKeyValidationJson(response, "status");
		aPIVerification.responseKeyValidationJson(response, "appointmentTypeId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentStatusWithoutPidGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(practiceId, "");
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "PatientId Should Not Be Empty...!!!");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesGET() throws IOException {

		Response response = postAPIRequest.appointmentType(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "name");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesSearchPost() throws IOException {
		String payload=PayloadNGEAPI.appointmentSearch();
		Response response = postAPIRequest.appointmentTypeSearch(practiceId, payload);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "categoryId");

		

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypesInvalidPathGET() throws IOException {

		Response response = postAPIRequest.appointmentType("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test_Avai_Schedule_Resc_Cancel_NGPOSTOFF() throws NullPointerException, Exception {

		String startDate = propertyData.getProperty("start.date.time.nge");
		String endDate = propertyData.getProperty("end.date.time.nge");
		String patinetId = propertyData.getProperty("patient.id.nge");
		int maxPerDay = 0;
		String b = PayloadNGEAPI.available_ShowOFFPayload(patinetId, startDate, endDate, maxPerDay);

		Response response = postAPIRequest.availableSlots(b, practiceId);

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String startDateTime = js.getString("availableSlots[0].startDateTime");
		String endDateTime = js.getString("availableSlots[1].startDateTime");
		String startDateTimeResch1 = js.getString("availableSlots[2].startDateTime");
		String startDateTimeResch2 = js.getString("availableSlots[3].startDateTime");
		Response scheduleApptResponse = postAPIRequest.scheduleApptNG(practiceId,
				PayloadNGEAPI.schedule_PayloadShowOFF(startDateTime, endDateTime));
		aPIVerification.responseCodeValidation(scheduleApptResponse, 200);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		String apptid = aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		log("Appointment id - " + apptid);

		Response rescheduleResponse = postAPIRequest.rescheduleApptNG(practiceId, PayloadNGEAPI
				.reschedule_PayloadShowOFF(startDateTimeResch1, startDateTimeResch2, patinetId, apptid));
		aPIVerification.responseTimeValidation(rescheduleResponse);
		aPIVerification.responseCodeValidation(rescheduleResponse, 200);
		String apptCancelId = aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");
		log("Appointment id Reschedule- " + apptCancelId);

		Response responseCancel = postAPIRequest.cancelAppointmentPOST(practiceId,
				PayloadNGEAPI.cancelAppointment(apptCancelId));
		aPIVerification.responseCodeValidation(responseCancel, 200);
		aPIVerification.responseTimeValidation(responseCancel);

	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void test_Avai_Schedule_Resc_Cancel_NGPOST() throws NullPointerException, Exception {
		String startDate = propertyData.getProperty("start.date.time.nge");
		String endDate = propertyData.getProperty("end.date.time.nge");
		String patinetId = propertyData.getProperty("patient.id.nge");
		int maxPerDay = 0;
		String b = PayloadNGEAPI.nextAvailable_Payload(patinetId, startDate, endDate, maxPerDay);
		Response response = postAPIRequest.availableSlots(b, practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		JsonPath js = new JsonPath(response.asString());
		String startDateTime = js.getString("availableSlots[0].startDateTime");
		String endDateTime = js.getString("availableSlots[1].startDateTime");
		String startDateTimeResch = js.getString("availableSlots[2].startDateTime");
		String endDateTimeResch = js.getString("availableSlots[3].startDateTime");
		Response scheduleApptResponse = postAPIRequest.scheduleApptNG(practiceId,
				PayloadNGEAPI.schedule_Payload(startDateTime, endDateTime));
		aPIVerification.responseCodeValidation(scheduleApptResponse, 200);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		String apptid = aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		log("Appointment id - " + apptid);

		Response rescheduleResponse = postAPIRequest.rescheduleApptNG(practiceId, PayloadNGEAPI
				.reschedule_Payload(startDateTimeResch, endDateTimeResch, patinetId,apptid));
		aPIVerification.responseTimeValidation(rescheduleResponse);
		aPIVerification.responseCodeValidation(rescheduleResponse, 200);
		String apptCancelId = aPIVerification.responseKeyValidationJson(rescheduleResponse, "id");
		aPIVerification.responseKeyValidationJson(rescheduleResponse, "slotAlreadyTaken");
		log("Appointment id Reschedule- " + apptCancelId);

		Response responseCancel = postAPIRequest.cancelAppointmentPOST(practiceId,
				PayloadNGEAPI.cancelAppointment(apptCancelId));
		aPIVerification.responseCodeValidation(responseCancel, 200);
		aPIVerification.responseTimeValidation(responseCancel);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testscheduleWithoutBody() throws NullPointerException, Exception {

		Response scheduleApptResponse = postAPIRequest.scheduleApptNG(practiceId, "");
		aPIVerification.responseCodeValidation(scheduleApptResponse, 400);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRescheduleWithoutBody() throws NullPointerException, Exception {

		Response scheduleApptResponse = postAPIRequest.rescheduleApptNG(practiceId, "");
		aPIVerification.responseCodeValidation(scheduleApptResponse, 400);
		aPIVerification.responseTimeValidation(scheduleApptResponse);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcommingApptPOST() throws IOException {
		
		String startdate = pSSPatientUtils.sampleDateTime("MM/dd/YYYY HH:MM:SS");

		Response response = postAPIRequest.upcommingApptNG(practiceId,
				PayloadNG.upcommingApt_Payload(propertyData.getProperty("uppcomming.patient.id.ng"),
						practiceId, startdate));
		
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "startDateTime");
		aPIVerification.responseKeyValidation(response, "endDateTime");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testUpcommingApptWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.upcommingApptNG(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAppointmentTypeListGET() throws IOException {

		Response response = postAPIRequest.appointmentType(practiceId);
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
		Response response = postAPIRequest.rescheduleApptNG(practiceId,
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

		Response response = postAPIRequest.cancelAppointmentGET(practiceId,
				propertyData.getProperty("appt.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentInvalidAppIdGet() throws IOException {

		Response response = postAPIRequest.cancelAppointmentGET(practiceId,
				propertyData.getProperty("invalidappt.id.ng"));
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancelAppointmentWithoutBodyPost() throws IOException {

		Response response = postAPIRequest.cancelAppointmentPOST(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCancellationReasonGET() throws IOException {

		Response response = postAPIRequest.cancellationReason(practiceId);
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

		Response response = postAPIRequest.careproviderAvailability(practiceId, PayloadNGEAPI.careProvider());

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testCareproviderAvailabilityWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.careproviderAvailability(practiceId, "");

		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testInsuranceCarrierWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLocationsListNGGET() throws IOException {

		Response response = postAPIRequest.locations(practiceId);
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
	public void testAddPatientDemographics() throws IOException {
		PSSPropertyFileLoader propertyData = new PSSPropertyFileLoader();
		AdminUser adminuser = new AdminUser();
		Appointment testData = new Appointment();
		propertyData.setAdminNG(adminuser);
		propertyData.setAppointmentResponseNG(testData);
		PSSNewPatient pssNewPatient = new PSSNewPatient();
		pssNewPatient.createPatientDetails(testData);
		String firstName = testData.getFirstName();
		String lastName = testData.getLastName();
		String dob = testData.getDob();
		String gender = testData.getGender();
		log("First Name- " + firstName);
		log("Last Name- " + lastName);
		log("Gender- " + gender);
		log("Date Of Birth- " + dob);
		Response response = postAPIRequest.addPatient(practiceId,PayloadNGEAPI.addPatient(firstName, lastName, dob, gender));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		String patientId = aPIVerification.responseKeyValidationJson(response, "id");
		log("Patient Id is " + patientId);
		Response responseDemographics = postAPIRequest.demographicsNGE(practiceId,patientId);
		aPIVerification.responseCodeValidation(responseDemographics, 200);
		aPIVerification.responseTimeValidation(responseDemographics);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.addPatient(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsGET() throws IOException {
        String patientId= propertyData.getProperty("demographics.patient.id.nge");
		Response response = postAPIRequest.demographics(practiceId,patientId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "firstName");
		aPIVerification.responseKeyValidationJson(response, "lastName");
		aPIVerification.responseKeyValidationJson(response, "dateOfBirth");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testDemographicsWithoutPracticeIdGET() throws IOException {
		String patientId="93c7d062-e9f7-42e0-9197-72cd031eb2f2";
		Response response = postAPIRequest.demographics("",patientId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLockoutGET() throws IOException {

		Response response = postAPIRequest.lockout(practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "key");
		aPIVerification.responseKeyValidation(response, "value");
		aPIVerification.responseKeyValidation(response, "type");

	}
	

	

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientPOST() throws IOException {
		Response response = postAPIRequest.matchPatientPOST(practiceId,
				PayloadNGEAPI.matchPatient());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testMatchPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.matchPatientPOST(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitGET() throws IOException {
        String patientId= propertyData.getProperty("lastvisit.patient.id.nge");
		Response response = postAPIRequest.patientLastVisit(practiceId,patientId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "lastVisitDateTime");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientLastVisitWithoutPracticeIdGET() throws IOException {
		  String patientId="e47ea31b-8436-40df-8152-c9ef9d8721fe";
		Response response = postAPIRequest.patientLastVisit("",patientId);
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRecordbyApptTypePOST() throws IOException {

		Response response = postAPIRequest.patientRecordbyApptTypePOST(practiceId,
				PayloadNG.patientrecordbyapptypes_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientRecordbyApptTypeWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.patientRecordbyApptTypePOST(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testSearchPatientPOST() throws IOException {

		Response response = postAPIRequest.searchpatient(practiceId,
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

		Response response = postAPIRequest.searchpatient(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientStatusGET() throws IOException {

		Response response = postAPIRequest.patietStatus(practiceId);
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

		Response response = postAPIRequest.prerequisteappointmenttypesPOST(practiceId,
				PayloadNG.prerequisteappointmenttypes_Payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "appointmentTypeId");
		aPIVerification.responseKeyValidation(response, "apptCategoryId");
		aPIVerification.responseKeyValidationJson(response, "showApptType");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPrerequisteappointmenttypesWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.prerequisteappointmenttypesPOST(practiceId,
				"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientrecordbybooksPOST() throws IOException {

		Response response = postAPIRequest.patientrecordbyBooks(practiceId,
				PayloadNG.patientrecordbybooks_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "patientRecord");
		ParseJSONFile.getKey(jsonobject, "bookId");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientrecordbybooksWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.patientrecordbyBooks(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderPOST() throws IOException {

		Response response = postAPIRequest.lastseenProvider(practiceId,
				payload.lastseenprovider);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "resourceId");
		aPIVerification.responseKeyValidationJson(response, "providerAvailability");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testLastseenProviderWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.lastseenProvider(practiceId, "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testFetchNGBookListGET() throws IOException {

		Response response = postAPIRequest.fetchNGBookList(practiceId);
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
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAvaliableSlots() throws NullPointerException, Exception {
		

		String startDate = propertyData.getProperty("start.date.time.nge");
		String endDate = propertyData.getProperty("end.date.time.nge");
		String patinetId = propertyData.getProperty("patient.id.nge");
		int maxPerDay = 0;
		String b = PayloadNGEAPI.nextAvailable_Payload(patinetId, startDate, endDate, maxPerDay);
		Response response = postAPIRequest.availableSlots(b, practiceId);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}
	
@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
public void testAvaliableSlotswithMaxperDay() throws NullPointerException, Exception {
	


	String startDate = propertyData.getProperty("start.date.time.nge");
	String endDate = propertyData.getProperty("end.date.time.nge");
	String patinetId = propertyData.getProperty("patient.id.nge");
	int maxPerDay = 1;
	String b = PayloadNGEAPI.nextAvailableMaxPerDay_Payload(patinetId, startDate, endDate, maxPerDay);
	Response response = postAPIRequest.availableSlots(b, practiceId);
	aPIVerification.responseCodeValidation(response, 200);
	aPIVerification.responseTimeValidation(response);

}
}
