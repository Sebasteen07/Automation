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

	public String matchPatientPayload(String email,String firstName) {
		String addpatient="{  \r\n"
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
		
		return addpatient;
	}
	public String cancelStatusPayload(String appointmentTypeId,String locationId,String patientId,String resourceId) {
		String addpatient="{\r\n"
				+ "    \"additionalProperties\": {},\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentTypeId\":\"" +appointmentTypeId+"\",\r\n"
				+ "    \"comments\": \"no comment\",\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "    \"locationId\":\"" +locationId+ "\",\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"additionalProp1\": null,\r\n"
				+ "        \"additionalProp2\": null,\r\n"
				+ "        \"additionalProp3\": null\r\n"
				+ "    },\r\n"
				+ "    \"numberOfDays\": 0,\r\n"
				+ "    \"patientId\": \""+patientId+"\",\r\n"
				+ "    \"resourceCategoryId\": null,\r\n"
				+ "    \"resourceId\": \""+resourceId+"\",\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"stackingFlag\": true,\r\n"
				+ "    \"startDateTime\": \"07/16/2021 08:45:00\",\r\n"
				+ "    \"updatePatientInfoKeys\": {\r\n"
				+ "        \"additionalProp1\": {},\r\n"
				+ "        \"additionalProp2\": {},\r\n"
				+ "        \"additionalProp3\": {}\r\n"
				+ "    }\r\n"
				+ "}";		
		
		return addpatient;
	}

}
