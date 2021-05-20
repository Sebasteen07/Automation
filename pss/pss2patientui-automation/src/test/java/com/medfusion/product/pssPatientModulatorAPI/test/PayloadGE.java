// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

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
				+ "  \"patientId\": \"26854\",\r\n"
				+ "  \"resourceCategoryId\":null,\r\n"
				+ "  \"resourceId\": \"158\",\r\n"
				+ "  \"slotCount\": 0\r\n"
				+ "}";
		return getlastseenproviders;
	}
	
	public static String schedApptPayload() {
		String schedAppt="{\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": null,\r\n"
				+ "  \"endDateTime\": \"05/30/2021 02:00:00\",\r\n"
				+ "  \"locationId\": \"3\",\r\n"
				+ "  \"notesProperties\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"patientId\": \"27565\",\r\n"
				+ "  \"resourceCategoryId\": null,\r\n"
				+ "  \"resourceId\": \"466\",\r\n"
				+ "  \"slotId\": \"4993302\",\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"startDateTime\": \"05/17/2021 1:30:00\",\r\n"
				+ "  \"updatePatientInfoKeys\": {\r\n"
				+ "    \"additionalProp1\": {},\r\n"
				+ "    \"additionalProp2\": {},\r\n"
				+ "    \"additionalProp3\": {}\r\n"
				+ "  }\r\n"
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
	
	public static String pastappointmentsPayload() {
		String pastappointments="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": \"05/20/2021\",\r\n"
				+ "  \"patientId\": \"26854\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \"PSS - GE\",\r\n"
				+ "    \"practiceId\": \"24333\",\r\n"
				+ "    \"practiceName\": \"PSS - GE\",\r\n"
				+ "    \"practiceTimezone\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"startDate\": \"05/12/2021\"\r\n"
				+ "}";
		return pastappointments;
	}
	
	public static String upcommingApt_Payload(String patientid, String practiceid, String practicedisplayname) {
		String upcommingappt ="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": \"04/01/2021\",\r\n"
				+ "  \"patientId\": \""+patientid+"\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \""+practicedisplayname+"\",\r\n"
				+ "    \"practiceId\": \""+practiceid+"\",\r\n"
				+ "    \"practiceName\": \"PSS - GE\",\r\n"
				+ "    \"practiceTimezone\": \"\"\r\n"
				+ "  },\r\n"
				+ "  \"startDate\": \"05/12/2021\"\r\n"
				+ "}";
		return upcommingappt;
	}
	
	public static String rescheduleAppointmentPayload(String startDateTime, String endDateTime, String locationid, String patientid,String resourceid, String slotid, String apptid) {
		String rescheduleAppointment="{\r\n"
				+ "  \"additionalProperties\": {},\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": null,\r\n"
				+ "  \"comments\": \"CustomQuestion:hello~(pss GE)\",\r\n"
				+ "  \"duration\": 0,\r\n"
				+ "  \"endDateTime\": \""+endDateTime+"\",\r\n"
				+ "  \"existingAppointment\": {\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentId\": \""+apptid+"\",\r\n"
				+ "    \"appointmentTypeId\": null,\r\n"
				+ "    \"comments\": \"CustomQuestion:hello~(pss GE)\",\r\n"
				+ "    \"duration\": \"0\",\r\n"
				+ "    \"endDateTime\": \"05/29/2021 08:00:00\",\r\n"
				+ "    \"locationId\": \""+locationid+"\",\r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"resourceCategoryId\": null,\r\n"
				+ "    \"resourceId\": \""+resourceid+"\",\r\n"
				+ "    \"slotId\": \""+slotid+"\",\r\n"
				+ "    \"startDateTime\": \""+startDateTime+"\"\r\n"
				+ "  },\r\n"
				+ "  \"locationId\": \""+locationid+"\",\r\n"
				+ "  \"notesProperties\": {\r\n"
				+ "    \"additionalProp1\": \"string\",\r\n"
				+ "    \"additionalProp2\": \"string\",\r\n"
				+ "    \"additionalProp3\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"numberOfDays\": 0,\r\n"
				+ "  \"patientId\": \""+patientid+"\",\r\n"
				+ "  \"rescheduleReason\": {\r\n"
				+ "    \"id\": \"string\",\r\n"
				+ "    \"name\": \"sk\"\r\n"
				+ "  },\r\n"
				+ "  \"resourceCategoryId\": null,\r\n"
				+ "  \"resourceId\": \""+resourceid+"\",\r\n"
				+ "  \"slotId\": \""+slotid+"\",\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"startDateTime\": \"05/14/2021 01:30:00\",\r\n"
				+ "  \"updatePatientInfoKeys\": {\r\n"
				+ "    \"additionalProp1\": {},\r\n"
				+ "    \"additionalProp2\": {},\r\n"
				+ "    \"additionalProp3\": {}\r\n"
				+ "  }\r\n"
				+ "}";
		return rescheduleAppointment;
	}
	
    public static String availableSlotsPayload(String patientId, String locationId, String startDate, String slotSize  ) {
		String availableSlots="{\r\n"
				+ "    \"locationId\": \""+locationId+"\",\r\n"
				+ "    \"appointmentCategoryId\": null,\r\n"
				+ "    \"appointmentTypeId\": null,\r\n"
				+ "    \"startDate\": \""+startDate+"\",\r\n"
				+ "    \"slotSize\": \""+slotSize+"\",\r\n"
				+ "    \"patientId\": \""+patientId+"\",\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"nextAvailability\": true,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": false,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"reservedForSameDate\": \"n\",\r\n"
				+ "    \"appointmentTypeDBId\": \"203607\",\r\n"
				+ "    \"locationDBId\": \"203905\"\r\n"
				+ "}";
		return availableSlots;
    }
    
	public static String nextAvailableSlotsPayload(String patientId, String apptid, String locationId,String resourceid,String startDate, String slotSize ) {
		String nextAvailableSlots="{\r\n"
				+ "  \"appointmentCategoryId\": null,\r\n"
				+ "  \"appointmentTypeId\": null,\r\n"
				+ "  \"apptTypeAllocated\": true,\r\n"
				+ "  \"contiguous\": false,\r\n"
				+ "  \"extApptId\": \""+apptid+",\r\n"
				+ "  \"leadTime\": 0,\r\n"
				+ "  \"locationId\": \""+locationId+"\",\r\n"
				+ "  \"maxPerDay\": 0,\r\n"
				+ "  \"nextAvailability\": true,\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"reservedForSameDay\": false,\r\n"
				+ "  \"resourceCategoryId\": null,\r\n"
				+ "  \"resourceId\": \""+resourceid+"\",\r\n"
				+ "  \"sameDayAppointment\": false,\r\n"
				+ "  \"slotCount\": 1,\r\n"
				+ "  \"slotSize\": \""+slotSize+"\",\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"startDate\": \""+startDate+"\"\r\n"
				+ "}";
		return nextAvailableSlots;
	}
	
	public  String addPatientPayload() {
		
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
	
public  String careProviderAvailabilityPayload(String startDateTime, String endDateTime,String resourceId,String slotSize,String locationId,String appointmentTypeId) {
		
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

public  String matchPatientPayload() {
	
	String  matchPatient="{\r\n" + 
			"    \"patientMatches\": [\r\n" + 
			"        {\r\n" + 
			"            \"entity\": \"Email Address\",\r\n" + 
			"            \"isMandatory\": true,\r\n" + 
			"            \"code\": \"EMAIL\",\r\n" + 
			"            \"value\": \"GECare111@mailinator.com\",\r\n" + 
			"            \"selected\": true,\r\n" + 
			"            \"search\": true\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"entity\": \"First Name\",\r\n" + 
			"            \"isMandatory\": true,\r\n" + 
			"            \"code\": \"FN\",\r\n" + 
			"            \"value\": \"GECare111\",\r\n" + 
			"            \"selected\": true,\r\n" + 
			"            \"search\": true\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"entity\": \"Gender\",\r\n" + 
			"            \"isMandatory\": false,\r\n" + 
			"            \"code\": \"GENDER\",\r\n" + 
			"            \"value\": \"M\",\r\n" + 
			"            \"selected\": true,\r\n" + 
			"            \"search\": false\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"entity\": \"Date Of Birth\",\r\n" + 
			"            \"isMandatory\": true,\r\n" + 
			"            \"code\": \"DOB\",\r\n" + 
			"            \"value\": \"01/01/2000\",\r\n" + 
			"            \"selected\": true,\r\n" + 
			"            \"search\": true\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"            \"entity\": \"Last Name\",\r\n" + 
			"            \"isMandatory\": true,\r\n" + 
			"            \"code\": \"LN\",\r\n" + 
			"            \"value\": \"GECare111\",\r\n" + 
			"            \"selected\": true,\r\n" + 
			"            \"search\": true\r\n" + 
			"        }\r\n" + 
			"    ],\r\n" + 
			"    \"maxCriteria\": \"4\",\r\n" + 
			"    \"allowDuplicatePatient\": false\r\n" + 
			"}";
	
	return  matchPatient;
}

public  String searchPatientPayload() {
	
	String searchPatient="{\r\n" + 
			"        \"id\": \"27574\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"27582\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"27583\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"27584\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"27585\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"        \"id\": \"27586\",\r\n" + 
			"        \"firstName\": \"Test\",\r\n" + 
			"        \"lastName\": \"APIThree\",\r\n" + 
			"        \"dateOfBirth\": \"01/28/1997\",\r\n" + 
			"        \"emailAddress\": \"rima.karmakar@crossasyst.com\",\r\n" + 
			"        \"gender\": \"M\",\r\n" + 
			"        \"status\": \"A\",\r\n" + 
			"        \"address\": {\r\n" + 
			"            \"zipCode\": \"90231\"\r\n" + 
			"        }\r\n" + 
			"    }\r\n" + 
			"]";
	
	return searchPatient;
}

}
