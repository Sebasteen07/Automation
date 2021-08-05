// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

import java.util.HashMap;
import java.util.Map;

public class PayloadAT {

	public Map<String, Object> pastApptPayload(String enddate, String patientId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", enddate);
		hm.put("patientId", patientId);

		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "24269");
		practiceProvision.put("practiceName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceDisplayName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("providerId", "27");
		hm.put("startDate", "05/05/2021 02:00:00");

		return hm;
	}
	
	public Map<String, Object> pastApptInvalidPayload(String enddate) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("endDate", enddate);
		
		HashMap<String, Object> practiceProvision = new HashMap<String, Object>();
		practiceProvision.put("active", true);
		practiceProvision.put("practiceId", "24269");
		practiceProvision.put("practiceName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceDisplayName", "PSS - Athena - Dev1");
		practiceProvision.put("practiceTimezone", "America/New_York");

		hm.put("practiceProvision", practiceProvision);
		hm.put("providerId", "27");
		hm.put("startDate", "05/05/2021 02:00:00");

		return hm;
	}

	public Map<String, Object> cancelappointmentPayload(String appointmentId) {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("appointmentId", appointmentId);
		return hm;
	}
	
	public String getavailableSlotPayload(String startdate, String enddate, String patientid) {
		
		String availableslot="{\r\n"
				+ "    \"locationId\": \"150\",\r\n"
				+ "    \"appointmentTypeId\": \"82\",\r\n"
				+ "    \"resourceCategoryId\": \"MD\",\r\n"
				+ "    \"resourceId\": \"71\",\r\n"
				+ "    \"startDate\": \""+startdate+"\",\r\n"
				+ "     \"endDate\": \""+enddate+"\",\r\n"
				+ "    \"slotSize\": \"15\",\r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"nextAvailability\": true,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": true,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"allowSameDayAppts\": false,\r\n"
				+ "    \"appointmentTypeDBId\": \"200300\",\r\n"
				+ "    \"locationDBId\": \"200301\",\r\n"
				+ "    \"providerDBId\": \"200853\"\r\n"
				+ "}";		
		return availableslot;
	}
	
	public String schedulePayload(String startdate, String enddate, String patientid, String slotid) {
		
		String schedule="{\r\n"
				+ "	\"locationId\": \"150\",\r\n"
				+ "	\"appointmentTypeId\": \"82\",\r\n"
				+ "	\"providerid\" : \"27\",\r\n"
				+ "    \"duration\": \"15\",\r\n"
				+ "	\"comments\": \"CustomQuestion:testcomment~:(pss)BOOKED:205098 01/03/2019 16:15:14~\",\r\n"
				+ "	\"patientId\" : \"10282\",\r\n"
				+ "	\"slotId\": \""+slotid+"\",\r\n"
				+ "	\"startDateTime\": \""+startdate+"\",\r\n"
				+ "    \"endDateTime\": \""+enddate+"\",\r\n"
				+ "    \"additionalProperties\" : {\r\n"
				+ "    	\"PHONE\" : \"9876543210\"\r\n"
				+ "    },\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"insuranceInfo\": \"Car=Aetna Life and Casualty,GrpId=464364,Ph=2753757656,MbrId=474587457\",\r\n"
				+ "        \"apptIndicatorWithConfirmationNo\": \"(pss)BOOKED:null 10/29/2019 03:18:52\",\r\n"
				+ "        \"customQuestion\": \"bhbhj\",\r\n"
				+ "        \"visitReason\": \"my reason for visit\"\r\n"
				+ "    }\r\n"
				+ "}";		
		return schedule;
	}
	
	public String reschPayload(String startdate, String enddate, String patientid, String slotid, String apptid) {
		
		String reschedule="{\r\n"
				+ "    \"locationId\": \"150\",\r\n"
				+ "    \"appointmentTypeId\": \"82\",\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "    \"comments\": \"~Specialty-Specialty 1:(pss)BOOKED:212173 09/30/2019 03:12:27~\",\r\n"
				+ "    \"startDateTime\": \""+startdate+"\",\r\n"
				+ "    \"endDateTime\": \""+enddate+"\",\r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"resourceCategoryId\": \"MD\",\r\n"
				+ "    \"resourceId\": \"71\",\r\n"
				+ "    \"slotId\": \""+slotid+"\",\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"FN\": \"at\",\r\n"
				+ "        \"LN\": \"at\",\r\n"
				+ "        \"DOB\": \"01/01/2000\",\r\n"
				+ "        \"GENDER\": \"M\"\r\n"
				+ "    },\r\n"
				+ "    \"existingAppointment\": {\r\n"
				+ "        \"duration\": 0,\r\n"
				+ "        \"stackingFlag\": false,\r\n"
				+ "        \"schedulingDuration\": 0,\r\n"
				+ "        \"appointmentId\": \""+apptid+"\"\r\n"
				+ "    },\r\n"
				+ "    \"rescheduleReason\": {\r\n"
				+ "        \"id\": \"\",\r\n"
				+ "        \"name\": \"test\"\r\n"
				+ "    }\r\n"
				+ "}";		
		return reschedule;
	}
	
	public String upcommingApptPayload(String startdate, String enddate, String patientid) {
		
		String upcomming="{\r\n"
				+ "    \"appointmentTypeId\": \"201006\",\r\n"
				+ "    \"endDate\": \""+enddate+"\",\r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"practiceProvision\": {\r\n"
				+ "        \"active\": true,\r\n"
				+ "        \"practiceDisplayName\": \"PSS - Athena - Dev1\",\r\n"
				+ "        \"practiceId\": \"24269\",\r\n"
				+ "        \"practiceName\": \"PSS - Athena - Dev1\",\r\n"
				+ "        \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "    },\r\n"
				+ "    \"providerId\": 200861,\r\n"
				+ "    \"startDate\": \""+startdate+"\"\r\n"
				+ "}";		
		return upcomming;
	}
	public String careProviderAvailabilityPayload(String startdate, String enddate, String cpresourcecat, String cpresourcece, String cpslot) {		
		String careProviderAvailability= "{\r\n"
				+ "    \"locationId\": \"150\",\r\n"
				+ "    \"appointmentTypeId\": \"82\",\r\n"
				+ "    \"startDateTime\": \""+startdate+"\",\r\n"
				+ "    \"endDateTime\": \""+enddate+"\",\r\n"
				+ "    \"careProvider\": [\r\n"
				+ "        {\r\n"
				+ "            \"resourceCatId\": \""+cpresourcecat+"\",\r\n"
				+ "            \"resourceId\": \""+cpresourcece+"\",\r\n"
				+ "            \"slotSize\": \""+cpslot+"\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return careProviderAvailability;
	}
	
	public String careProviderAvailabilityInvalidPayload() {		
		String careProviderAvailability= "{\r\n"
				+ "    \"locationId\": \"15055\",\r\n"
				+ "    \"appointmentTypeId\": \"8255\",\r\n"
				+ "    \"startDateTime\": \"07/17/2021 07:51:16\",\r\n"
				+ "    \"endDateTime\": \"12/18/2021 07:51:16\",\r\n"
				+ "    \"careProvider\": [\r\n"
				+ "        {\r\n"
				+ "            \"resourceCatId\": \"MD\",\r\n"
				+ "            \"resourceId\": \"27\",\r\n"
				+ "            \"slotSize\": \"5\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return careProviderAvailability;
	}
	
	public String addPatientPayload() {
		String addpatient="{\r\n"
				+ "    \"firstName\": \"Shweta\",\r\n"
				+ "    \"lastName\": \"Sontakke\",\r\n"
				+ "    \"dateOfBirth\": \"01/01/2000\",\r\n"
				+ "    \"emailAddress\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
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
				+ "    	\"MRN_CODE\" : \"1234\",\r\n"
				+ "    	\"stdid\" : \"12\"\r\n"
				+ "    }\r\n"
				+ "}";			
		return addpatient;
	}
	
	public String addPatientWithoutFirstNamePayload() {
		String addpatient="{\r\n"
				+ "    \"lastName\": \"Sontakke\",\r\n"
				+ "    \"dateOfBirth\": \"01/01/2000\",\r\n"
				+ "    \"emailAddress\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
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
				+ "    \"map\": {\r\n"
				+ "        \"MRN_CODE\": \"1234\",\r\n"
				+ "        \"stdid\": \"12\"\r\n"
				+ "    }\r\n"
				+ "}";			
		return addpatient;
	}
	
	public String matchPatientPayload() {
		String matchPatient="{  \r\n"
				+ "      \"patientMatches\":[  \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Email Address\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"EMAIL\",\r\n"
				+ "            \"value\":\"Shweta.Sontakke@CrossAsyst.com\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         },\r\n"
				+ "        \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"First Name\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"FN\",\r\n"
				+ "            \"value\":\"Shweta\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         },\r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Gender\",\r\n"
				+ "            \"isMandatory\":false,\r\n"
				+ "            \"code\":\"GENDER\",\r\n"
				+ "            \"value\":\"M\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":false\r\n"
				+ "         },\r\n"
				+ "       \r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Date Of Birth\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"DOB\",\r\n"
				+ "            \"value\":\"01/01/2000\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         },\r\n"
				+ "         {  \r\n"
				+ "            \"entity\":\"Last Name\",\r\n"
				+ "            \"isMandatory\":true,\r\n"
				+ "            \"code\":\"LN\",\r\n"
				+ "            \"value\":\"Sontakke\",\r\n"
				+ "            \"selected\":true,\r\n"
				+ "            \"search\":true\r\n"
				+ "         }\r\n"
				+ "        \r\n"
				+ "      ], \r\n"
				+ "      \"maxCriteria\":\"4\",\r\n"
				+ "      \"allowDuplicatePatient\":false\r\n"
				+ "}";			
		return matchPatient;
	}
	
	public String matchPatientInvalidPayload() {
		String matchPatient="{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"value\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": true\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Gender\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"GENDER\",\r\n"
				+ "            \"value\": \"M\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": false\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Last Name\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"value\": \"Sontakke\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": true\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"maxCriteria\": \"4\",\r\n"
				+ "    \"allowDuplicatePatient\": false\r\n"
				+ "}";	
		
		return matchPatient;
	}
	
	public String searchPatientPayload() {
		String searchpatient="{\r\n"
				+ "    \"firstName\": \"Shweta\",\r\n"
				+ "    \"lastName\": \"Sontakke\",\r\n"
				+ "    \"dateOfBirth\": \"01/01/2000\",\r\n"
				+ "    \"gender\": \"M\"\r\n"
				+ "}";
		return searchpatient;
	}
	
	public String prerequisteappointmenttypesPayload(String prerequisteapptid) {
		String prerequisteappointmenttypes="{  \r\n"
				+ "   \"patientId\":\"8093\",\r\n"
				+ "   \"appointmentType\":[  \r\n"
				+ "      {  \r\n"
				+ "         \"appointmentTypeId\":\""+prerequisteapptid+"\",\r\n"
				+ "         \"prerequisiteAppointmentType\":[  \r\n"
				+ "            {  \r\n"
				+ "               \"preAppointmentTypeId\":\"1023\",\r\n"
				+ "               \"noOfDays\":-1\r\n"
				+ "            }\r\n"
				+ "         ]\r\n"
				+ "      }\r\n"
				+ "   ]\r\n"
				+ "}";
		return prerequisteappointmenttypes;
	}
	
	public String nextAvailablePayload(String startdate) {
		String nextAvailable="{\r\n"
				+ "    \"locationId\": \"150\",\r\n"
				+ "    \"appointmentTypeId\": \"82\",\r\n"
				+ "    \"resourceCategoryId\": \"MD\",\r\n"
				+ "    \"resourceId\": \"71\",\r\n"
				+ "    \"startDate\": \""+startdate+"\",\r\n"
				+ "    \"slotSize\": \"15\",\r\n"
				+ "    \"patientId\": \"10282\",\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"nextAvailability\": true,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": true,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"allowSameDayAppts\": false,\r\n"
				+ "    \"appointmentTypeDBId\": \"200300\",\r\n"
				+ "    \"locationDBId\": \"200301\",\r\n"
				+ "    \"providerDBId\": \"200853\"\r\n"
				+ "}";
		return nextAvailable;
	}
	
	public String nextAvailableInvalidPayload(String startdate) {
		String nextAvailable="{\r\n"
				+ "    \"locationId\": \"150\",\r\n"
				+ "    \"appointmentTypeId\": \"8200\",\r\n"
				+ "    \"resourceCategoryId\": \"MD\",\r\n"
				+ "    \"resourceId\": \"71\",\r\n"
				+ "    \"startDate\": \""+startdate+"\",\r\n"
				+ "    \"slotSize\": \"15\",\r\n"
				+ "    \"patientId\": \"10282\",\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"nextAvailability\": true,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": true,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"allowSameDayAppts\": false,\r\n"
				+ "    \"appointmentTypeDBId\": \"200300\",\r\n"
				+ "    \"locationDBId\": \"200301\",\r\n"
				+ "    \"providerDBId\": \"200853\"\r\n"
				+ "}";
		return nextAvailable;
	}
	
	public String lastseenProviderPayload() {
		String lastseenProvider="{\r\n"
				+ "    \"appointmentTypeId\": 82,\r\n"
				+ "    \"location\": 150,\r\n"
				+ "    \"resourceId\": null,\r\n"
				+ "    \"patientId\": \"10282\",\r\n"
				+ "    \"duration\": 15,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"noOfDays\": null\r\n"
				+ "}";
		return lastseenProvider;
	}
	
	public String lastseenProviderInvalidPatientIdPayload() {
		String lastseenProvider="{\r\n"
				+ "    \"appointmentTypeId\": 82,\r\n"
				+ "    \"location\": 150,\r\n"
				+ "    \"resourceId\": null,\r\n"
				+ "    \"patientId\": \"0000\",\r\n"
				+ "    \"duration\": 15,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"noOfDays\": null\r\n"
				+ "}";
		return lastseenProvider;
	}

	
	
	


}
