// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.medfusion.product.object.maps.pss2.page.util.APIVerification;
import com.medfusion.product.object.maps.pss2.page.util.ParseJSONFile;
import com.medfusion.product.object.maps.pss2.page.util.PostAPIRequestNG;
import com.medfusion.product.pss2patientapi.payload.PayloadNG;
import com.medfusion.product.pss2patientui.pojo.Appointment;
import com.medfusion.product.pss2patientui.utils.PSSPropertyFileLoader;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PSS2NGAdapterAcceptanceTests extends BaseTestNG {

	public static PayloadNG payload;
	public static PSSPropertyFileLoader propertyData;
	public static Appointment testData;
	public static PostAPIRequestNG postAPIRequest;
	APIVerification aPIVerification = new APIVerification();

	@BeforeTest(enabled = true, groups = { "APItest" })
	public void setUp() throws IOException {

		payload = new PayloadNG();
		propertyData = new PSSPropertyFileLoader();
		postAPIRequest = new PostAPIRequestNG();
		log("I am before Test");
		postAPIRequest.setupRequestSpecBuilder(propertyData.getProperty("base.url.ng"));
		log("BASE URL-" + propertyData.getProperty("base.url.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AvailableSlotsNGPost() throws IOException, InterruptedException {

		Response response = postAPIRequest.availableSlots(
				PayloadNG.nextAvailable_Payload(propertyData.getProperty("patient.id.ng")),
				propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AvailableSlotsNGPostInvalidPayload() throws IOException, InterruptedException {

		Response response = postAPIRequest.availableSlots("", propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 400);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void CancellationReasonGET() throws IOException {

		Response response = postAPIRequest.cancellationReasonT(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "reasonType");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void CancellationReasonWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.cancellationReasonT("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestPastApptNgPOST() throws IOException {

		Response response = postAPIRequest.pastApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.past_appt_payload(propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("practice.displayname.ng"),
						propertyData.getProperty("practice.id.ng")));
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "appointmentTypes.name");
		aPIVerification.responseKeyValidationJson(response, "book.resourceId");
		aPIVerification.responseKeyValidationJson(response, "location.name");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void TestPastApptWithoutPayloadPOST() throws IOException, InterruptedException {

		Response response = postAPIRequest.pastApptNG(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void nextAvailableSlotPost() throws IOException {

		log("Practice ID- " + propertyData.getProperty("practice.id.ng"));

		log("Practice Display Name- " + propertyData.getProperty("practice.displayname.ng"));

		log("Patient Id- " + propertyData.getProperty("patient.id.ng"));

		Response response = postAPIRequest.nextAvailableNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.nextAvailable_New());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentStatusGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(propertyData.getProperty("practice.id.ng"), "49911");
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "startDateTime");
		aPIVerification.responseKeyValidationJson(response, "status");
		aPIVerification.responseKeyValidationJson(response, "appointmentTypeId");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentStatusWithoutPidGET() throws IOException {

		Response response = postAPIRequest.appointmentStatus(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
		JsonPath js = new JsonPath(response.asString());
		String msg = js.getString("message");
		assertEquals(msg, "PatientId Should Not Be Empty...!!!");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentTypesGET() throws IOException {

		Response response = postAPIRequest.appointmentType(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "categoryName");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "name");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentTypesInvalidPathGET() throws IOException {

		Response response = postAPIRequest.appointmentType("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

//	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
//	public void schedule_Resc_NGPOST() throws IOException {
//
//		String appointmentId = postAPIRequest.scheduleApptNG(propertyData.getProperty("practice.id.ng"),
//				PayloadNG.schedule_Payload(propertyData.getProperty("slot.start.time.ng"),
//						propertyData.getProperty("slot.end.time.ng")));
//
//		postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
//				PayloadNG.reschedule_Payload(propertyData.getProperty("start.date.time.ng"),
//						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
//						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"),
//						appointmentId));
//
//	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void schedule_Resc_NGPOST() throws NullPointerException, Exception {

		Response scheduleApptResponse = postAPIRequest.scheduleApptNG1(propertyData.getProperty("practice.id.ng"),
				PayloadNG.schedule_Payload(propertyData.getProperty("slot.start.time.ng"),
						propertyData.getProperty("slot.end.time.ng")));
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		String apptid = aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");
		log("Appointment id - " + apptid);

		Response rescheduleResponse = postAPIRequest.rescheduleApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.reschedule_Payload(propertyData.getProperty("start.date.time.ng"),
						propertyData.getProperty("end.date.time.ng"), propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("first.name.ng"), propertyData.getProperty("first.name.ng"), apptid));
		aPIVerification.responseTimeValidation(scheduleApptResponse);
		aPIVerification.responseCodeValidation(rescheduleResponse, 200);
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "id");
		aPIVerification.responseKeyValidationJson(scheduleApptResponse, "slotAlreadyTaken");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void upcommingApptPOST() throws IOException {

		Response response = postAPIRequest.upcommingApptNG(propertyData.getProperty("practice.id.ng"),
				PayloadNG.upcommingApt_Payload(propertyData.getProperty("patient.id.ng"),
						propertyData.getProperty("practice.id.ng"),
						propertyData.getProperty("practice.displayname.ng")));
		aPIVerification.responseCodeValidation(response, 200);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void AppointmentTypeListGET() throws IOException {

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
	public void AppointmentTypesListInvalidPathGET() throws IOException {

		Response response = postAPIRequest.appointmentType("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void RescheduleApptNGPInvalidAppIdPOST() throws IOException {

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
	public void cancelAppointmentGet() throws IOException {

		Response response = postAPIRequest.cancelAppointmentGET(propertyData.getProperty("practice.id.ng"),
				propertyData.getProperty("appt.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancelAppointmentInvalidAppIdGet() throws IOException {

		Response response = postAPIRequest.cancelAppointmentGET(propertyData.getProperty("practice.id.ng"),
				propertyData.getProperty("invalidappt.id.ng"));
		aPIVerification.responseCodeValidation(response, 500);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancelAppointmentPost() throws IOException {

		Response response = postAPIRequest.cancelAppointmentPOST(propertyData.getProperty("practice.id.ng"),
				payload.cancelAppointment);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancelAppointmentWithoutBodyPost() throws IOException {

		Response response = postAPIRequest.cancelAppointmentPOST(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancellationReasonGET() throws IOException {

		Response response = postAPIRequest.cancellationReason(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "displayName");
		aPIVerification.responseKeyValidation(response, "reasonType");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void cancellationReasonWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.cancellationReason("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void careproviderAvailabilityPOST() throws IOException {

		Response response = postAPIRequest.careproviderAvailability(propertyData.getProperty("practice.id.ng"),
				PayloadNG.careprovideravailability_Payload());

		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void insuranceCarrierGET() throws IOException {

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
	public void insuranceCarrierWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.insuranceCarrier("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void locationsListNGGET() throws IOException {

		Response response = postAPIRequest.locations(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "name");
		aPIVerification.responseKeyValidation(response, "displayName");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void locationsListWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.locations("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void addPatientPOST() throws IOException {

		postAPIRequest.insuranceCarrier(propertyData.getProperty("practice.id.ng"));
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void demographicsGET() throws IOException {

		Response response = postAPIRequest.demographics(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "id");
		aPIVerification.responseKeyValidationJson(response, "firstName");
		aPIVerification.responseKeyValidationJson(response, "lastName");
		aPIVerification.responseKeyValidationJson(response, "dateOfBirth");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void demographicsWithoutPracticeIdGET() throws IOException {

		Response response = postAPIRequest.demographics("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void lockoutGET() throws IOException {

		Response response = postAPIRequest.lockout(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "key");
		aPIVerification.responseKeyValidation(response, "value");
		aPIVerification.responseKeyValidation(response, "type");

	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void matchPatientPOST() throws IOException {

		PayloadNG payload = new PayloadNG();

		Response response = postAPIRequest.matchPatientPOST(propertyData.getProperty("practice.id.ng"),
				payload.matchpatient);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void matchPatientWithoutBodyPOST() throws IOException {

		Response response = postAPIRequest.matchPatientPOST(propertyData.getProperty("practice.id.ng"),
				"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);

	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientLastVisitGET() throws IOException {

		Response response =postAPIRequest.patientLastVisit(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "lastVisitDateTime");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientLastVisitWithoutPracticeIdGET() throws IOException {

		Response response =postAPIRequest.patientLastVisit("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientRecordbyApptTypePOST() throws IOException {

		Response response =postAPIRequest.patientRecordbyApptTypePOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbyapptypes_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientRecordbyApptTypeWithoutBodyPOST() throws IOException {

		Response response =postAPIRequest.patientRecordbyApptTypePOST(propertyData.getProperty("practice.id.ng"),
				"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void searchPatientPOST() throws IOException {

		Response response =postAPIRequest.searchpatient(propertyData.getProperty("practice.id.ng"), payload.searchpatient);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "id");
		aPIVerification.responseKeyValidation(response, "firstName");
		aPIVerification.responseKeyValidation(response, "lastName");
		aPIVerification.responseKeyValidation(response, "dateOfBirth");
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void searchPatientWithoutBodyPOST() throws IOException {

		Response response =postAPIRequest.searchpatient(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}

	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientStatusGET() throws IOException {

		Response response =postAPIRequest.patietStatus(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "key");
		aPIVerification.responseKeyValidation(response, "value");
	}
	
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientStatusWithoutPracticeIdGET() throws IOException {

		Response response =postAPIRequest.patietStatus("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);
	}

	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void prerequisteappointmenttypesPOST() throws IOException {

		Response response =postAPIRequest.prerequisteappointmenttypesPOST(propertyData.getProperty("practice.id.ng"),
				PayloadNG.prerequisteappointmenttypes_Payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidation(response, "appointmentTypeId");
		aPIVerification.responseKeyValidation(response, "apptCategoryId");
		aPIVerification.responseKeyValidationJson(response, "showApptType");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void prerequisteappointmenttypesWithoutBodyPOST() throws IOException {

		Response response =postAPIRequest.prerequisteappointmenttypesPOST(propertyData.getProperty("practice.id.ng"),
				"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientrecordbybooksPOST() throws IOException {

		Response response =postAPIRequest.patientrecordbyBooks(propertyData.getProperty("practice.id.ng"),
				PayloadNG.patientrecordbybooks_payload());
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		JSONObject jsonobject = new JSONObject(response.asString());
		ParseJSONFile.getKey(jsonobject, "patientRecord");
		ParseJSONFile.getKey(jsonobject, "bookId");
	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void patientrecordbybooksWithoutBodyPOST() throws IOException {

		Response response =postAPIRequest.patientrecordbyBooks(propertyData.getProperty("practice.id.ng"),"");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void lastseenProviderPOST() throws IOException {

		Response response =postAPIRequest.lastseenProvider(propertyData.getProperty("practice.id.ng"), payload.lastseenprovider);
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response, "resourceId");
		aPIVerification.responseKeyValidationJson(response, "providerAvailability");

	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void lastseenProviderWithoutBodyPOST() throws IOException {

		Response response =postAPIRequest.lastseenProvider(propertyData.getProperty("practice.id.ng"), "");
		aPIVerification.responseCodeValidation(response, 400);
		aPIVerification.responseTimeValidation(response);
	}


	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void fetchNGBookListGET() throws IOException {

		Response response =	postAPIRequest.fetchNGBookList(propertyData.getProperty("practice.id.ng"));
		aPIVerification.responseCodeValidation(response, 200);
		aPIVerification.responseTimeValidation(response);
		aPIVerification.responseKeyValidationJson(response,"resourceId");
		aPIVerification.responseKeyValidationJson(response,"resourceName");
		aPIVerification.responseKeyValidationJson(response,"displayName");
		aPIVerification.responseKeyValidationJson(response,"type");
		aPIVerification.responseKeyValidationJson(response,"categoryId");
		aPIVerification.responseKeyValidationJson(response,"categoryName");
		aPIVerification.responseKeyValidationJson(response,"providerId");


	}
	
	@Test(enabled = true, groups = { "APItest" }, retryAnalyzer = RetryAnalyzer.class)
	public void fetchNGBookListWithoutPracticeIdGET() throws IOException {

		Response response =	postAPIRequest.fetchNGBookList("");
		aPIVerification.responseCodeValidation(response, 404);
		aPIVerification.responseTimeValidation(response);


	}


}
