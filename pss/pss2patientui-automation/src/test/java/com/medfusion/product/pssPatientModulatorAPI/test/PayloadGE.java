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
				+ "  \"patientId\": \"27555\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \"PSS - GE\",\r\n"
				+ "    \"practiceId\": \"24248\",\r\n"
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
}
