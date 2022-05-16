// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

import java.util.HashMap;
import java.util.Map;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;

public class PayloadGW extends BaseTestNGWebDriver {
	
	

	public Map<String, Object> pastApptPayload(String patientId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", null);
		hm.put("patientId", patientId);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "11691");
		practiceProvision.put("practiceName", "PSS-GW-DEMO-ENV^");
		practiceProvision.put("practiceDisplayName", "PSS-GW-DEMO-ENV^");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("startDate", "05/16/2022 00:00:00");

		return hm;
	}
	

	public Map<String, Object> pastApptPayloadWithoutPid() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", null);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "24253");
		practiceProvision.put("practiceName", "PSS-GW-12242@1#(%#(%(#%");
		practiceProvision.put("practiceDisplayName", "PSS-GW-12242@1#(%#(%(#%");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("startDate", "05/05/2021 02:00:00");

		return hm;
	}

	public Map<String, Object> searchPatientPayload(String dob, String firstName, String gender, String lastName,
			String practiceTZ) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("dateOfBirth", dob);
		hm.put("firstName", firstName);
		hm.put("gender", gender);
		hm.put("lastName", lastName);
		hm.put("practiceTimezone", practiceTZ);
		return hm;
	}
	
	public Map<String, Object> searchPatientWithoutFnamePayload(String dob, String firstName, String gender, String lastName,
			String practiceTZ) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("dateOfBirth", dob);
		hm.put("gender", gender);
		hm.put("lastName", lastName);
		hm.put("practiceTimezone", practiceTZ);
		return hm;
	}
	
	public Map<String, Object> availableslotsPayload(String appointmentCatId,String appointmentTypeId,String extId,String locationId,
			String patientId,String resourceCatId,String resourceId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("appointmentTypeId", appointmentTypeId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", null);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
		hm.put("resourceId", resourceId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "5");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;

	
	}
	
	public Map<String, Object> availableslotsWithoutAppIdPayload(String appointmentCatId,String extId,String locationId,
			String patientId,String resourceCatId,String resourceId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", patientId);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
		hm.put("resourceId", resourceId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "5");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;

	
	}

	public Map<String, Object> nextAvailableslotsPayload(String appointmentCatId,String appointmentTypeId,String extId,String locationId,
			String patientId,String resourceCatId,String resourceId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("appointmentTypeId", appointmentTypeId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", patientId);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
		hm.put("resourceId", resourceId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "15");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;
		
	}
	
	public Map<String, Object> nextAvailableslotsWithoutProviderPayload(String appointmentCatId,String appointmentTypeId,String extId,String locationId,
			String patientId,String resourceCatId,String startTime) {

		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentCategoryId", appointmentCatId);
		hm.put("appointmentTypeId", appointmentTypeId);
		hm.put("apptTypeAllocated", true);
		hm.put("duration", null);
     	hm.put("endDate", null);
     	hm.put("extApptId", extId);
		hm.put("leadTime", "0");
		hm.put("locationId", locationId);
		hm.put("nextAvailability", true);
		hm.put("patientId", patientId);
		hm.put("practiceTimezone", "America/New_York");
		hm.put("preventScheduling",0);
		hm.put("reservedForSameDay",true);
		hm.put("resourceCategoryId", resourceCatId);
    	hm.put("sameDayAppointment", true);
		hm.put("slotCount", 0);
		hm.put("slotLimit", null);
		hm.put("slotSize", "15");
		hm.put("stackingFlag",true);
		hm.put("startDate", startTime);
		return hm;
		
	}
	public Map<String, Object> upComingAppointmentsPayload(String startdate,String patientId,String practiceName,String practiceId,String practiceDisplayName) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", null);
		hm.put("patientId", patientId);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", practiceDisplayName);
		practiceProvision.put("practiceName", practiceName);
		practiceProvision.put("practiceDisplayName", practiceDisplayName);
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("startDate", startdate);

		return hm;
	}
	
	public Map<String, Object> upComingAppointmentsPayloadWithoutPatientId(String startdate,String practiceName,String practiceId,String practiceDisplayName) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", null);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", practiceDisplayName);
		practiceProvision.put("practiceName", practiceName);
		practiceProvision.put("practiceDisplayName", practiceDisplayName);
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("startDate", startdate);

		return hm;
	}
	

	public String addPatientPayload(String firstName,String lastName,String dob,String email) {
		String addpatient="\r\n"
				+ "{\r\n"
				+ "    \"firstName\": \""+firstName+"\",\r\n"
				+ "    \"lastName\": \""+lastName+"\",\r\n"
				+ "    \"dateOfBirth\": \""+dob+"\",\r\n"
				+ "    \"emailAddress\": \""+email+"\",\r\n"
				+ "    \"gender\": \"F\",\r\n"
				+ "    \"alertNotes\": \"\",\r\n"
				+ "    \"phoneNumber\": \"9876543210\",\r\n"
				+ "    \"status\": \"active\",\r\n"
				+ "    \"address\": {\r\n"
				+ "        \"address1\": \"person Street\",\r\n"
				+ "        \"address2\": \"East Lane for person\",\r\n"
				+ "        \"city\": \"\",\r\n"
				+ "        \"state\": \"NY\",\r\n"
				+ "        \"zipCode\": \"30117\"\r\n"
				+ "    },\r\n"
				+ "    \"map\" : {\r\n"
				+ "        \"MRN_CODE\" : \"1234\",\r\n"
				+ "        \"stdid\" : \"12\"\r\n"
				+ "    }\r\n"
				+ "}\r\n"
				+ "";		
		
		return addpatient;
	}
	
	public String addPatientPayloadWithoutFName(String lastName,String dob,String email) {
		String addpatient="\r\n"
				+ "{\r\n"
				+ "    \"lastName\": \""+lastName+"\",\r\n"
				+ "    \"dateOfBirth\": \""+dob+"\",\r\n"
				+ "    \"emailAddress\": \""+email+"\",\r\n"
				+ "    \"gender\": \"F\",\r\n"
				+ "    \"alertNotes\": \"\",\r\n"
				+ "    \"phoneNumber\": \"9876543210\",\r\n"
				+ "    \"status\": \"active\",\r\n"
				+ "    \"address\": {\r\n"
				+ "        \"address1\": \"person Street\",\r\n"
				+ "        \"address2\": \"East Lane for person\",\r\n"
				+ "        \"city\": \"\",\r\n"
				+ "        \"state\": \"NY\",\r\n"
				+ "        \"zipCode\": \"30117\"\r\n"
				+ "    },\r\n"
				+ "    \"map\" : {\r\n"
				+ "        \"MRN_CODE\" : \"1234\",\r\n"
				+ "        \"stdid\" : \"12\"\r\n"
				+ "    }\r\n"
				+ "}\r\n"
				+ "";		
		
		return addpatient;
	}


	public String matchPatientWithoutEmailPayload(String firstName) {
		String matchPatient="{  \r\n"
				+ "      \"patientMatches\":[  \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Email Address\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         },\r\n"
				+ "        \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"First Name\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"FN\",\r\n"
				+ "            \"value\":\""+firstName+"\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         }\r\n"
				+ "      ], \r\n"
				+ "      \"maxCriteria\":\"4\",\r\n"
				+ "      \"allowDuplicatePatient\":false\r\n"
				+ "}\r\n"
				+ "";		
		
		return matchPatient;
	}
	
	public String matchPatientPayload(String email,String firstName) {
		String matchPatient="{  \r\n"
				+ "      \"patientMatches\":[  \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Email Address\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"EMAIL\",\r\n"
				+ "            \"value\":\""+email+"\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         },\r\n"
				+ "        \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"First Name\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"FN\",\r\n"
				+ "            \"value\":\""+firstName+"\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         }\r\n"
				+ "      ], \r\n"
				+ "      \"maxCriteria\":\"4\",\r\n"
				+ "      \"allowDuplicatePatient\":false\r\n"
				+ "}\r\n"
				+ "";		
		
		return matchPatient;
	}
	
	public String cancelStatusPayload(String appointmentTypeId,String locationId,String patientId,String resourceId,String appId) {
		String cancelStatus="{\r\n"
				+ "  \"additionalProperties\": {},\r\n"
				+ "  \"appointmentCategoryId\": \"1087\",\r\n"
				+ "  \"appointmentTypeId\": \""+appointmentTypeId+"\",\r\n"
				+ "  \"comments\":null,\r\n"
				+ "  \"duration\": \"5\",\r\n"
				+ "  \"endDateTime\": \"07/26/2021 08:05:00\",\r\n"
				+ "  \"existingAppointment\": {\r\n"
				+ "    \"appointmentId\": \""+appId+"\"\r\n"
				+ "  },\r\n"
				+ "  \"locationId\": \""+locationId+"\",\r\n"
				+ "  \"notesProperties\": {},\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"practiceTimezone\": \"America/New_York\",\r\n"
				+ "  \"resourceCategoryId\": \"1025\",\r\n"
				+ "  \"resourceId\": \""+resourceId+"\",\r\n"
				+ "  \"startDateTime\": \"07/26/2021 08:00:00\"\r\n"
				+ "}";		
		
		return cancelStatus;
	}
	
	public String cancelAppointmentPayload(String appointmentidCancel) {
		String cancelStatus="{\r\n"
				+ "  \"appointmentId\": \""+appointmentidCancel+"\",\r\n"
				+ "  \"cancellationMap\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";		
		
		return cancelStatus;
	}
	
	public String cancelAppInvalidIdPayload(String invalidAppointmentidCancel) {
		String cancelStatus="{\r\n"
				+ "  \"appointmentId\": \""+invalidAppointmentidCancel+"\",\r\n"
				+ "  \"cancellationMap\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  }\r\n"
				+ "}\r\n"
				+ "";		
		
		return cancelStatus;
	}
	public String schedulePayload(String startdate,String enddate,String patientId) {
		String schedulePayload="{\r\n"
				+ "\"locationId\": \"1019\",\r\n"
				+ "\"appointmentCategoryId\": \"1087\",\r\n"
				+ "\"appointmentTypeId\": \"1086\",\r\n"
				+ "\"duration\": 0,\r\n"
				+ "\"comments\": \"~(pss) 231701 03/12/2021 04:34:27~\",\r\n"
				+ "\"startDateTime\": \""+startdate+"\",\r\n"
				+ " \"endDateTime\": \""+enddate+"\",\r\n"
				+ "\"patientId\": \""+patientId+"\",\r\n"
				+ "\"resourceCategoryId\": \"1025\",\r\n"
				+ "\"resourceId\": \"1072\",\r\n"
				+ "\"stackingFlag\": false,\r\n"
				+ "\"schedulingDuration\": 0,\r\n"
				+ "\"additionalProperties\": {\r\n"
				+ "\"FN\": \"atr11\",\r\n"
				+ "\"LN\": \"atr11\",\r\n"
				+ "\"DOB\": \"01/01/2001\",\r\n"
				+ "\"GENDER\": \"M\",\r\n"
				+ "\"EMAIL\": \"atul.rathod@crossasyst.com\",\r\n"
				+ "\"PHONE\": \"961-992-1668\",\r\n"
				+ "\"ZIP\": \"12345\"\r\n"
				+ "  },\r\n"
				+ "\"notesProperties\": {\r\n"
				+ "\"apptIndicatorWithConfirmationNo\": \"(pss) 231701 03/12/2021 04:34:27\"\r\n"
				+ "  },\r\n"
				+ "\"practiceTimezone\": \"America/New_York\"\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "";		
		
		return schedulePayload;
	}
	
	public String scheduleWithoutPidPayload(String startdate,String enddate) {
		String schedulePayload="{\r\n"
				+ "\"locationId\": \"1019\",\r\n"
				+ "\"appointmentCategoryId\": \"1087\",\r\n"
				+ "\"appointmentTypeId\": \"1086\",\r\n"
				+ "\"duration\": 0,\r\n"
				+ "\"comments\": \"~(pss) 231701 03/12/2021 04:34:27~\",\r\n"
				+ "\"startDateTime\": \""+startdate+"\",\r\n"
				+ " \"endDateTime\": \""+enddate+"\",\r\n"
				+ "\"resourceCategoryId\": \"1025\",\r\n"
				+ "\"resourceId\": \"1072\",\r\n"
				+ "\"stackingFlag\": false,\r\n"
				+ "\"schedulingDuration\": 0,\r\n"
				+ "\"additionalProperties\": {\r\n"
				+ "\"FN\": \"atr11\",\r\n"
				+ "\"LN\": \"atr11\",\r\n"
				+ "\"DOB\": \"01/01/2001\",\r\n"
				+ "\"GENDER\": \"M\",\r\n"
				+ "\"EMAIL\": \"atul.rathod@crossasyst.com\",\r\n"
				+ "\"PHONE\": \"961-992-1668\",\r\n"
				+ "\"ZIP\": \"12345\"\r\n"
				+ "  },\r\n"
				+ "\"notesProperties\": {\r\n"
				+ "\"apptIndicatorWithConfirmationNo\": \"(pss) 231701 03/12/2021 04:34:27\"\r\n"
				+ "  },\r\n"
				+ "\"practiceTimezone\": \"America/New_York\"\r\n"
				+ "}\r\n"
				+ "\r\n"
				+ "";		
		
		return schedulePayload;
	}
	public String reschedulePayload(String startdate,String enddate,String patientId,String appId) {
		String reschedulePayload="{\r\n"
				+ "\r\n"
				+ "    \"locationId\": \"1019\",\r\n"
				+ "\r\n"
				+ "    \"appointmentCategoryId\": \"1087\",\r\n"
				+ "\r\n"
				+ "    \"appointmentTypeId\": \"1086\",\r\n"
				+ "\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "\r\n"
				+ "    \"comments\": \"~(pss) 229331 01/18/2021 10:56:03~\",\r\n"
				+ "\r\n"
				+ "    \"startDateTime\": \""+startdate+"\",\r\n"
				+ "\r\n"
				+ "    \"endDateTime\": \""+enddate+"\",\r\n"
				+ "\r\n"
				+ "    \"patientId\": \""+patientId+"\",\r\n"
				+ "\r\n"
				+ "    \"resourceCategoryId\": \"1025\",\r\n"
				+ "\r\n"
				+ "    \"resourceId\": \"1074\",\r\n"
				+ "\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "\r\n"
				+ "        \"FN\": \"atr11\",\r\n"
				+ "\r\n"
				+ "        \"LN\": \"atr11\"\r\n"
				+ "\r\n"
				+ "    },\r\n"
				+ "\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "\r\n"
				+ "        \"apptIndicatorWithConfirmationNo\": \"(pss) 36141 07/14/2021 08:00:00\"\r\n"
				+ "\r\n"
				+ "    },\r\n"
				+ "\r\n"
				+ "    \"existingAppointment\": {\r\n"
				+ "\r\n"
				+ "        \"duration\": 0,\r\n"
				+ "\r\n"
				+ "        \"stackingFlag\": false,\r\n"
				+ "\r\n"
				+ "        \"schedulingDuration\": 0,\r\n"
				+ "\r\n"
				+ "        \"appointmentId\": \""+appId+"\"\r\n"
				+ "\r\n"
				+ "    },\r\n"
				+ "\r\n"
				+ "    \"rescheduleReason\": {\r\n"
				+ "\r\n"
				+ "        \"id\": \"\",\r\n"
				+ "\r\n"
				+ "        \"name\": \"\"\r\n"
				+ "\r\n"
				+ "    },\r\n"
				+ "\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "\r\n"
				+ "}\r\n"
				+ "";		
		
		return reschedulePayload;
	}

}
