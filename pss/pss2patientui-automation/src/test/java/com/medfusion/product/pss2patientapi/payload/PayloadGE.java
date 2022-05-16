// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadGE {
	public static String providerLastSeenPayload() {
		String getlastseenproviders="{\r\n"
				+ "  \"additionalField\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": null,\r\n"
				+ "  \"apptTypeAllocated\": false,\r\n"
				+ "  \"locationId\": \"3\",\r\n"
				+ "  \"noOfDays\": 0,\r\n"
				+ "  \"patientId\": \"28447\",\r\n"
				+ "  \"resourceCategoryId\":null,\r\n"
				+ "  \"resourceId\": \"581\",\r\n"
				+ "  \"slotCount\": 0\r\n"
				+ "}";
		return getlastseenproviders;
	}
	
	public static String schedApptPayload(String startTime,String patientId,String slotId,String endTime) {
		String schedAppt="{\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": \"158\",\r\n"
				+ "  \"endDateTime\": \""+endTime+"\",\r\n"
				+ "  \"locationId\": \"3\",\r\n"
				+ "  \"notesProperties\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"resourceCategoryId\": null,\r\n"
				+ "  \"resourceId\": \"466\",\r\n"
				+ "  \"slotId\": \""+slotId+"\",\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"startDateTime\": \""+startTime+"\",\r\n"
				+ "  \"updatePatientInfoKeys\": {\r\n"
				+ "    \"additionalProp1\": {},\r\n"
				+ "    \"additionalProp2\": {},\r\n"
				+ "    \"additionalProp3\": {}\r\n"
				+ "  }\r\n"
				+ "}";
		return schedAppt;
	}
	
	public static String schedApptForNewPatientPayload(String startTime,String slotId,String endTime) {
		String schedAppt="{\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentTypeId\": \"158\",\r\n"
				+ "    \"endDateTime\": \""+endTime+"\",\r\n"
				+ "    \"locationId\": \"3\",\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"additionalProp1\": \"string\",\r\n"
				+ "        \"additionalProp2\": \"string\",\r\n"
				+ "        \"additionalProp3\": \"string\"\r\n"
				+ "    },\r\n"
				+ "    \"patientId\": null,\r\n"
				+ "    \"resourceCategoryId\": null,\r\n"
				+ "    \"resourceId\": \"466\",\r\n"
				+ "    \"slotId\": \""+slotId+"\",\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"startDateTime\": \""+startTime+"\",\r\n"
				+ "    \"updatePatientInfoKeys\": {\r\n"
				+ "        \"additionalProp1\": {\r\n"
				+ "            \r\n"
				+ "        },\r\n"
				+ "        \"additionalProp2\": {\r\n"
				+ "            \r\n"
				+ "        },\r\n"
				+ "        \"additionalProp3\": {\r\n"
				+ "            \r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return schedAppt;
	}
	
	
	
	public static String cancelledApptStatusPayload() {
		String cancelledApptStatus="{\r\n"
				+ "  \"additionalProperties\": {},\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": \"158\",\r\n"
				+ "  \"comments\": \"no comment\",\r\n"
				+ "  \"duration\": 0,\r\n"
				+ "  \"locationId\": \"3\",\r\n"
				+ "  \"notesProperties\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"numberOfDays\": 0,\r\n"
				+ "  \"patientId\": \"27554\",\r\n"
				+ "  \"resourceCategoryId\": null,\r\n"
				+ "  \"resourceId\": \"466\",\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"stackingFlag\": true,\r\n"
				+ "  \"startDateTime\": \"05/15/2021 01:10:00\",\r\n"
				+ "  \"updatePatientInfoKeys\": {\r\n"
				+ "    \"additionalProp1\": {},\r\n"
				+ "    \"additionalProp2\": {},\r\n"
				+ "    \"additionalProp3\": {}\r\n"
				+ "  }\r\n"
				+ "}";
				return cancelledApptStatus;
	}
	
	public static String cancelApptWithCancelReasonPayload() {
		String cancelledApptStatus="{\r\n"
				+ "  \"appointmentId\": \"158\",\r\n"
				+ "  \"cancellationMap\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  }\r\n"
				+ "}";
		return cancelledApptStatus;
	}
	
	public static String pastappointmentsPayload(String startDate,String endDate,String patientId,String practiceDisplayName,String practiceName,String practiceId) {
		String pastappointments="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": \"05/16/2022\",\r\n"
				+ "  \"patientId\": \"28356\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \"PSS-Ge-PG2-DP\",\r\n"
				+ "    \"practiceId\": \"11916\",\r\n"
				+ "    \"practiceName\": \"PSS-Ge-PG2-DP\",\r\n"
				+ "    \"practiceTimezone\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"startDate\": \"05/12/2021\"\r\n"
				+ "}";
		return pastappointments;
	}
	
	public static String upcommingApt_Payload(String patientid, String practiceid, String practicedisplayname,String starttime) {
		String upcommingappt ="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": null,\r\n"
				+ "  \"patientId\": \""+patientid+"\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \""+practicedisplayname+"\",\r\n"
				+ "    \"practiceId\": \""+practiceid+"\",\r\n"
				+ "    \"practiceName\": \""+practicedisplayname+"\",\r\n"
				+ "    \"practiceTimezone\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"startDate\": \""+starttime+"\"\r\n"
				+ "}";
		return upcommingappt;
	}
	
	public static String rescheduleAppointmentPayload(String startDateTime, String endDateTime, String locationid, String patientid,String resourceid, String slotid, String apptId) {
		String rescheduleAppointment="{\r\n"
				+ "    \"locationId\": \""+locationid+"\",\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentTypeId\": \"158\",\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "    \"comments\": \"~(pss) 229331 01/18/2021 10:56:03~\",\r\n"
				+ "    \"startDateTime\": \""+startDateTime+"\",\r\n"
				+ "    \"endDateTime\": \""+endDateTime+"\",\r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"resourceCategoryId\": null,\r\n"
				+ "    \"resourceId\": \""+resourceid+"\",\r\n"
				+ "    \"slotId\": \""+slotid+"\",\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"FN\": \"atrge11\",\r\n"
				+ "        \"LN\": \"atrge11\"\r\n"
				+ "    },\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"apptIndicatorWithConfirmationNo\": \"(pss) 36141 07/14/2021 08:00:00\"\r\n"
				+ "    },\r\n"
				+ "    \"existingAppointment\": {\r\n"
				+ "        \"duration\": 0,\r\n"
				+ "        \"stackingFlag\": false,\r\n"
				+ "        \"schedulingDuration\": 0,\r\n"
				+ "        \"appointmentId\": \""+apptId+"\"\r\n"
				+ "    },\r\n"
				+ "    \"rescheduleReason\": {\r\n"
				+ "        \"id\": \"\",\r\n"
				+ "        \"name\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "}";
		return rescheduleAppointment;
	}
	
    public static String availableSlotsPayload(String patientId, String locationId, String startDate, String slotSize ,String date ) {
		String availableSlots="{\r\n"
				+ "    \"locationId\": \"3\",\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentTypeId\": \"158\",\r\n"
				+ "    \"resourceCategoryId\": null,\r\n"
				+ "    \"resourceId\": \"581\",\r\n"
				+ "    \"preferredDuration\": null,\r\n"
				+ "    \"duration\": null,\r\n"
				+ "    \"startDate\": \""+date+"\",\r\n"
				+ "    \"endDate\": null,\r\n"
				+ "    \"slotSize\": 5,\r\n"
				+ "    \"patientId\": null,\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": false,\r\n"
				+ "    \"nextAvailability\": false,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": false,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"reservedForSameDate\": \"n\",\r\n"
				+ "    \"appointmentTypeDBId\": \"204201\",\r\n"
				+ "    \"locationDBId\": \"4272\",\r\n"
				+ "    \"providerDBId\": \"205269\",\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\",\r\n"
				+ "    \"extApptId\": null,\r\n"
				+ "    \"nextAvailable\": false\r\n"
				+ "}";
		return availableSlots;
	}
    
	public static String addPatientPayload() {
		String addPatient="{\r\n" + 
				"\r\n" + 
				"  \"address\": {\r\n" + 
				"    \"address1\": null,\r\n" + 
				"    \"address2\": null,\r\n" + 
				"    \"city\": null,\r\n" + 
				"    \"country\": null,\r\n" + 
				"    \"state\": null,\r\n" + 
				"    \"zipCode\": \"90231\"\r\n" + 
				"  },\r\n" + 
				"  \"alertNotes\": null,\r\n" + 
				"  \"billingNotes\": [\r\n" + 
				"    \"string\"\r\n" + 
				"  ],\r\n" + 
				"  \"dateOfBirth\": \"01/28/1997\",\r\n" + 
				"  \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
				"  \"firstName\": \"Test\",\r\n" + 
				"  \"gender\": \"M\",\r\n" + 
				"  \"id\": \"string\",\r\n" + 
				"  \"isPatient\": true,\r\n" + 
				"  \"lastName\": \"APIThree\",\r\n" + 
				"  \"maritalStatus\": null,\r\n" + 
				"  \"patientAlertNotes\": [\r\n" + 
				"    \"string\"\r\n" + 
				"  ],\r\n" + 
				"  \"patientCreationDate\": null,\r\n" + 
				"  \"phoneMapProperties\": {},\r\n" + 
				"  \"phoneNumber\": null,\r\n" + 
				"  \"phoneNumber2\": null,\r\n" + 
				"  \"preferredLanguage\": \"English\",\r\n" + 
				"  \"primaryCareProvider\": null,\r\n" + 
				"  \"responsibleProvider\": null,\r\n" + 
				"  \"status\": \"string\"\r\n" + 
				"}";
		
		return addPatient;
	}
	
public static  String careProviderAvailabilityPayload(String startDateTime, String endDateTime,String resourceId,String slotSize,String locationId,String appointmentTypeId) {
		
	String careProAvailability=" {\r\n" + 
			"  \"appointmentTypeCatId\": null,\r\n" + 
			"  \"appointmentTypeId\": \""+appointmentTypeId+"\",\r\n" + 
			"  \"careProvider\": [\r\n" + 
			"    {\r\n" + 
			"      \"nextAvailabledate\": null,\r\n" + 
			"      \"resourceCatId\": null,\r\n" + 
			"      \"resourceId\": \""+resourceId+"\",\r\n" + 
			"      \"slotSize\": \""+slotSize+"\"\r\n" + 
			"    }\r\n" + 
			"  ],\r\n" + 
			"  \"endDateTime\": \""+endDateTime+"\",\r\n" + 
			"  \"locationId\": \""+locationId+"\",\r\n" + 
			"  \"startDateTime\": \""+startDateTime+"\"\r\n" + 
			"}";
	
	return careProAvailability;
	}

public static  String matchPatientPayload() {
	
	String  matchPatient="{\r\n"
			+ "    \"patientMatches\": [\r\n"
			+ "        {\r\n"
			+ "            \"entity\": \"Email Address\",\r\n"
			+ "            \"isMandatory\": true,\r\n"
			+ "            \"code\": \"EMAIL\",\r\n"
			+ "            \"value\": \"rima.karmakar@crossasyst.com\",\r\n"
			+ "            \"selected\": true,\r\n"
			+ "            \"search\": true\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "            \"entity\": \"First Name\",\r\n"
			+ "            \"isMandatory\": true,\r\n"
			+ "            \"code\": \"FN\",\r\n"
			+ "            \"value\": \"Test\",\r\n"
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
			+ "            \"entity\": \"Date Of Birth\",\r\n"
			+ "            \"isMandatory\": true,\r\n"
			+ "            \"code\": \"DOB\",\r\n"
			+ "            \"value\": \"01/28/1997\",\r\n"
			+ "            \"selected\": true,\r\n"
			+ "            \"search\": true\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "            \"entity\": \"Last Name\",\r\n"
			+ "            \"isMandatory\": true,\r\n"
			+ "            \"code\": \"LN\",\r\n"
			+ "            \"value\": \"APIThree\",\r\n"
			+ "            \"selected\": true,\r\n"
			+ "            \"search\": true\r\n"
			+ "        }\r\n"
			+ "    ],\r\n"
			+ "    \"maxCriteria\": \"4\",\r\n"
			+ "    \"allowDuplicatePatient\": false\r\n"
			+ "}";
	
	return  matchPatient;
}

public static  String searchPatientPayload() {
	
	String searchPatient="{\r\n"
			+ "    \"id\": \"27574\",\r\n"
			+ "    \"firstName\": \"Test\",\r\n"
			+ "    \"lastName\": \"APIThree\",\r\n"
			+ "    \"dateOfBirth\": \"01/28/1997\",\r\n"
			+ "    \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n"
			+ "    \"gender\": \"M\",\r\n"
			+ "    \"status\": \"A\",\r\n"
			+ "    \"address\":{\r\n"
			+ "        \"zipCode\": \"90231\"\r\n"
			+ "    } \r\n"
			+ "}";
	
	return searchPatient;
}

}
