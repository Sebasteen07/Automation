// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadNGEAPI {
	

	
public static String cancelAppointment(String appointmentId) {
		
		String cancelAppointment="{\r\n"
				+ "    \"appointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"cancellationMap\": {\r\n"
				+ "        \"id\": \"4791929E-0999-46FC-9624-B9B2E415AFCD\",\r\n"
				+ "        \"name\": \"Other\"\r\n"
				+ "    }\r\n"
				+ "}";
		
		return cancelAppointment;
	}

public static String lastSeenProvider() {
	
	String cancelAppointment="{\r\n"
			+ "    \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
			+ "    \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
			+ "    \"noOfDays\": \"1\",\r\n"
			+ "    \"patientId\": \"090f22f6-116b-4fe9-9afc-0bad333898c6\",\r\n"
			+ "    \"lastSeenProvider\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
			+ "    \"appointmentTypeCatId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
			+ "    \"resourceCatId\": \"58e61653-fe3d-448b-ba06-8bcdbfa1cf0f\",\r\n"
			+ "    \"duration\": \"5\"\r\n"
			+ "}";
	
	return cancelAppointment;
}


public static String appointmentSearch() {
	
	String cancelAppointment="{\r\n"
			+ "    \"eventId\": null,\r\n"
			+ "    \"eventName\": \"Annual\"\r\n"
			+ "}";
	
	return cancelAppointment;
}
	
	String body1="[{\"group\":\"NG_WEBSERVICES\",\"key\":\"NGAPIBaseUrl\",\"value\":\"https://dev-nge-test-pss-nge-int-test.dev.mf.pxp.nextgenaws.net:7205/api\"}]";
	String available_Slot_Payload ="{\"locationId\":\"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
			+ "   \"appointmentCategoryId\":\"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
			+ "   \"appointmentTypeId\":\"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
			+ "   \"startDate\":\"02/17/2021 10:00:00\",\r\n"
			+ "   \"slotSize\":\"15\",\r\n"
			+ "   \"patientId\":\"3665\",\r\n"
			+ "   \"reservedForSameDay\":false,\r\n"
			+ "   \"apptTypeAllocated\":true,\r\n"
			+ "   \"nextAvailability\":false,\r\n"
			+ "   \"stackingFlag\":false,\r\n"
			+ "   \"preventScheduling\":0,\r\n"
			+ "   \"sameDayAppointment\":false,\r\n"
			+ "   \"contiguous\":false,\r\n"
			+ "   \"maxPerDay\":0,\r\n"
			+ "   \"leadTime\":0,\r\n"
			+ "   \"slotCount\":1,\r\n"
			+ "   \"allowSameDayAppts\":true,\r\n"
			+ "   \"reservedForSameDate\":\"n\",\r\n"
			+ "   \"appointmentTypeDBId\":\"205755\",\r\n"
			+ "   \"locationDBId\":\"205605\"\r\n"
			+ "}";
	

	public static String past_appt_payload(String patientId, String endate) {
		
		String past_appt="{\r\n"
				+ "    \"practiceProvision\": {\r\n"
				+ "        \"practiceId\": null,\r\n"
				+ "        \"practiceName\": null,\r\n"
				+ "        \"practiceDisplayName\": null,\r\n"
				+ "        \"practiceTimezone\": null,\r\n"
				+ "        \"active\": false\r\n"
				+ "    },\r\n"
				+ "    \"slotAlreadyTaken\": false,\r\n"
				+ "    \"rescheduleNotAllowed\": false,\r\n"
				+ "    \"startDate\": \"07/14/2021\",\r\n"
				+ "    \"endDate\": \""+endate+"\",\r\n"
				+ "    \"patientId\": \""+patientId+"\",\r\n"
				+ "    \"showReschedule\": false\r\n"
				+ "}";
		
		return past_appt;
	}

	public static String nextAvailable_Payload(String patientId, String startdate,  String enddate,int maxPerDay) {
		
		String nextAvailable="{\r\n"
				+ "  \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "  \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "  \"resourceId\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
				+ "  \"startDate\": \"06/18/2022 17:45:00\",\r\n"
				+ "  \"endDate\": \"06/30/2022 18:45:00\",\r\n"
				+ "  \"slotSize\": \"05\",\r\n"
				+ "  \"reservedForSameDay\": false,\r\n"
				+ "  \"apptTypeAllocated\": true,\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"contiguous\": false,\r\n"
				+ "  \"maxPerDay\": 0,\r\n"
				+ "  \"nextAvailability\": false,\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"sameDayAppointment\": false,\r\n"
				+ "  \"leadTime\": 0,\r\n"
				+ "  \"slotCount\": 1,\r\n"
				+ "  \"nextAvailable\": false\r\n"
				+ "}";
		
		return nextAvailable;
	}
	
public static String nextAvailableMaxPerDay_Payload(String patientId, String startdate,  String enddate,int maxPerDay) {
		
		String nextAvailable="{\r\n"
				+ "  \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "  \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "  \"resourceId\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
				+ "  \"startDate\": \"06/18/2022 17:45:00\",\r\n"
				+ "  \"endDate\": \"06/30/2022 18:45:00\",\r\n"
				+ "  \"slotSize\": \"05\",\r\n"
				+ "  \"reservedForSameDay\": false,\r\n"
				+ "  \"apptTypeAllocated\": true,\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"contiguous\": false,\r\n"
				+ "  \"maxPerDay\": "+maxPerDay+",\r\n"
				+ "  \"nextAvailability\": false,\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"sameDayAppointment\": false,\r\n"
				+ "  \"leadTime\": 0,\r\n"
				+ "  \"slotCount\": 1,\r\n"
				+ "  \"nextAvailable\": false\r\n"
				+ "}";
		
		return nextAvailable;
	}
	

	public static String available_ShowOFFPayload(String patientId, String startdate,  String enddate,int maxPerDay) {
		
		String nextAvailable="{\r\n"
				+ "    \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "    \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "    \"startDate\": \"06/18/2022 17:45:00\",\r\n"
				+ "    \"endDate\": \"06/30/2022 18:45:00\",\r\n"
				+ "    \"slotSize\": \"05\",\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"patientId\": \"597d7141-79ec-4e27-a089-d6ca280ce687\",\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"nextAvailability\": false,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": false,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"nextAvailable\": false\r\n"
				+ "}";
		
		return nextAvailable;
	}
	
public static String nextAvailable_New() {
		
		String nextAvailable="{\r\n"
				+ "\"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "\"appointmentCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "\"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "\"resourceCategoryId\":null,\r\n"
				+ "\"startDate\":\"10/08/2021 05:02:22\",\r\n"
				+ "\"endDate\":null,\r\n"
				+ "\"slotSize\": 5,\r\n"
				+ "\"patientId\": null,\r\n"
				+ "\"reservedForSameDay\": false,\r\n"
				+ "\"apptTypeAllocated\": true,\r\n"
				+ "\"nextAvailability\": true,\r\n"
				+ "\"stackingFlag\": false,\r\n"
				+ "\"preventScheduling\": 0,\r\n"
				+ "\"sameDayAppointment\": false,\r\n"
				+ "\"contiguous\": false,\r\n"
				+ "\"maxPerDay\": 0,\r\n"
				+ "\"leadTime\": 0,\r\n"
				+ "\"slotCount\": 1,\r\n"
				+ "\"allowSameDayAppts\": true\r\n"
				+ "\r\n"
				+ "}";
		
		return nextAvailable;
	}
	
public static String reschedule_Payload(String startDateTime, String endDateTime, String patientid, String apptid) {
	
	String reschedule="{\r\n"
			+ "  \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
			+ "  \"appointmentCategoryId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
			+ "  \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
			+ "  \"duration\": 0,\r\n"
			+ "  \"comments\": \"~(pss) 239797 01/24/2022 05:20:04~\",\r\n"
			+ "  \"startDateTime\": \""+startDateTime+"\",\r\n"
			+ "  \"endDateTime\": \""+endDateTime+"\",\r\n"
			+ "  \"patientId\": \""+patientid+"\",\r\n"
			+ "  \"resourceCategoryId\": \"58e61653-fe3d-448b-ba06-8bcdbfa1cf0f\",\r\n"
			+ "  \"resourceId\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
			+ "  \"slotId\": \"0001\",\r\n"
			+ "  \"stackingFlag\": false,\r\n"
			+ "  \"schedulingDuration\": 0,\r\n"
			+ "  \"additionalProperties\": {\r\n"
			+ "    \"ZIP\": \"12345\",\r\n"
			+ "    \"PHONE\": \"808-201-6243\",\r\n"
			+ "    \"EMAIL\": \"Ketan@yopmail.com\"\r\n"
			+ "  },\r\n"
			+ "  \"notesProperties\": {\r\n"
			+ "    \"apptIndicatorWithConfirmationNo\": \"(pss) 239797 01/24/2022 05:20:04\"\r\n"
			+ "  },\r\n"
			+ "  \"existingAppointment\": {\r\n"
			+ "    \"duration\": 0,\r\n"
			+ "    \"stackingFlag\": false,\r\n"
			+ "    \"schedulingDuration\": 0,\r\n"
			+ "    \"appointmentId\": \""+apptid+"\"\r\n"
			+ "  },\r\n"
			+ "  \"rescheduleReason\": {\r\n"
			+ "    \"id\": \"8EED1E6A-1FA2-4CCF-9660-0F553B1D8C17\",\r\n"
			+ "    \"name\": \"Laurie\"\r\n"
			+ "  },\r\n"
			+ "  \"practiceTimezone\": \"America/New_York\"\r\n"
			+ "}";
	
	return reschedule;
}

public static String reschedule_PayloadShowOFF(String startDateTime, String endDateTime, String patientid, String apptid) {
	
	String reschedule="{\r\n"
			+ "  \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
			+ "  \"appointmentCategoryId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
			+ "  \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
			+ "  \"duration\": 0,\r\n"
			+ "  \"comments\": \"~(pss) 239797 01/24/2022 05:20:04~\",\r\n"
			+ "  \"startDateTime\": \""+startDateTime+"\",\r\n"
			+ "  \"endDateTime\": \""+endDateTime+"\",\r\n"
			+ "  \"patientId\": \""+patientid+"\",\r\n"
			+ "  \"slotId\": \"0001\",\r\n"
			+ "  \"stackingFlag\": false,\r\n"
			+ "  \"schedulingDuration\": 0,\r\n"
			+ "  \"additionalProperties\": {\r\n"
			+ "    \"ZIP\": \"12345\",\r\n"
			+ "    \"PHONE\": \"808-201-6243\",\r\n"
			+ "    \"EMAIL\": \"Ketan@yopmail.com\"\r\n"
			+ "  },\r\n"
			+ "  \"notesProperties\": {\r\n"
			+ "    \"apptIndicatorWithConfirmationNo\": \"(pss) 239797 01/24/2022 05:20:04\"\r\n"
			+ "  },\r\n"
			+ "  \"existingAppointment\": {\r\n"
			+ "    \"duration\": 0,\r\n"
			+ "    \"stackingFlag\": false,\r\n"
			+ "    \"schedulingDuration\": 0,\r\n"
			+ "    \"appointmentId\": \""+apptid+"\"\r\n"
			+ "  },\r\n"
			+ "  \"rescheduleReason\": {\r\n"
			+ "    \"id\": \"8EED1E6A-1FA2-4CCF-9660-0F553B1D8C17\",\r\n"
			+ "    \"name\": \"Laurie\"\r\n"
			+ "  },\r\n"
			+ "  \"practiceTimezone\": \"America/New_York\"\r\n"
			+ "}";
	
	return reschedule;
}
	
	public static String schedule_Payload(String slotStartTime, String slotEndTime) {
		
		String schedule="{\r\n"
				+ "    \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "    \"appointmentCategoryId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
				+ "    \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "    \"comments\": \"~(pss) 239797 01/24/2022 05:20:04~\",\r\n"
				+ "    \"startDateTime\": \""+slotStartTime+"\",\r\n"
				+ "    \"endDateTime\": \""+slotEndTime+"\",\r\n"
				+ "    \"patientId\": \"597d7141-79ec-4e27-a089-d6ca280ce687\",\r\n"
				+ "    \"resourceCategoryId\": \"58e61653-fe3d-448b-ba06-8bcdbfa1cf0f\",\r\n"
				+ "    \"resourceId\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"EMAIL\": \"ketan@yopmail.com\"\r\n"
				+ "    },\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"apptIndicatorWithConfirmationNo\": \"(pss) 239797 01/24/2022 05:20:04\"\r\n"
				+ "    },\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "}";
		
		return schedule;
	}
	
public static String schedule_PayloadShowOFF(String slotStartTime, String slotEndTime) {
		
		String schedule="{\r\n"
				+ "    \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "    \"appointmentCategoryId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
				+ "    \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "    \"duration\": 0,\r\n"
				+ "    \"comments\": \"~(pss) 239797 01/24/2022 05:20:04~\",\r\n"
				+ "    \"startDateTime\": \""+slotStartTime+"\",\r\n"
				+ "    \"endDateTime\": \""+slotEndTime+"\",\r\n"
				+ "    \"patientId\": \"597d7141-79ec-4e27-a089-d6ca280ce687\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"EMAIL\": \"ketan@yopmail.com\"\r\n"
				+ "    },\r\n"
				+ "    \"notesProperties\": {\r\n"
				+ "        \"apptIndicatorWithConfirmationNo\": \"(pss) 239797 01/24/2022 05:20:04\"\r\n"
				+ "    },\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "}";
		
		return schedule;
	}
	
	public static String upcommingApt_Payload(String patientid, String practiceid, String sdate) {

		String upcommingappt = "{\r\n"
				+ "  \r\n"
				+ "    \"patientId\": \""+patientid+"\",\r\n"
				+ "    \"practiceProvision\": {\r\n"
				+ "        \"active\": true,\r\n"
				+ "        \"practiceId\": \""+practiceid+"\"\r\n"
				+ "    },\r\n"
				+ "    \"startDate\": \""+sdate+"\"\r\n"
				+ "}";
		return upcommingappt;
	}
	
	public static String careprovideravailability_Payload(String startDate, String endDate) {
		
		String careprovideravailability="{\r\n"
				+ "    \"appointmentTypeCatId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "    \"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "    \"careProvider\": [\r\n"
				+ "        {\r\n"
				+ "            \"nextAvailabledate\": \"10/08/2021 08:45:00\",\r\n"
				+ "            \"resourceCatId\": \"437DCCDB-8D78-475F-B661-EF393674F1F8\",\r\n"
				+ "            \"resourceId\": \"78E497EF-0C8F-4D59-AD40-FE7BE12B9842\",\r\n"
				+ "            \"slotSize\": 5\r\n"
				+ "        }\r\n"
				+ "        \r\n"
				+ "    ],\r\n"
				+ "    \"endDateTime\": \""+endDate+"\",\r\n"
				+ "    \"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "    \"startDateTime\": \""+startDate+"\"\r\n"
				+ "}";
		return careprovideravailability;
		
	}
	
	public static String prerequisteappointmenttypes_Payload() {

		String prerequisteappointmenttypes = "{\r\n"
				+ "    \"patientId\": \"50467\",\r\n"
				+ "    \"appointmentType\": [\r\n"
				+ "        {\r\n"
				+ "            \"appointmentTypeId\": \"EA6ECC11-2A95-420C-9698-0753DBEDA3FE\",\r\n"
				+ "            \"apptCategoryId\": \"4FE5D330-0692-4C8A-8158-7C701FA27DEF\",\r\n"
				+ "            \"prerequisiteAppointmentType\": [\r\n"
				+ "                {\r\n"
				+ "                    \"preAppointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "                    \"noOfDays\": -1,\r\n"
				+ "                    \"preCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\"\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"appointmentTypeId\": \"C1DDA8A2-B513-42AD-B6AA-4643AC4345CA\",\r\n"
				+ "            \"apptCategoryId\": \"04E98D09-3797-4250-A0C4-E467D11FE2FA\",\r\n"
				+ "            \"prerequisiteAppointmentType\": [\r\n"
				+ "                {\r\n"
				+ "                    \"preAppointmentTypeId\": \"9E0BC44E-4200-4433-8941-DA49C408F1BA\",\r\n"
				+ "                    \"noOfDays\": -1,\r\n"
				+ "                    \"preCategoryId\": \"7B40680C-9CCE-478D-9455-2B017FD048A2\"\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"appointmentTypeId\": \"D68F8134-5F4F-48AC-A303-FEC4E0009E69\",\r\n"
				+ "            \"apptCategoryId\": \"81EAC44B-8EDF-4E53-97C5-AEB84C91EDF5\",\r\n"
				+ "            \"prerequisiteAppointmentType\": [\r\n"
				+ "                {\r\n"
				+ "                    \"preAppointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "                    \"noOfDays\": -1,\r\n"
				+ "                    \"preCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\"\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return prerequisteappointmenttypes;

	}
	
	public static String patientrecordbybooks_payload() {
		String patientrecordbybooks = "{\r\n"
				+ "    \"books\": [\r\n"
				+ "        {\r\n"
				+ "            \"bookId\": \"78E497EF-0C8F-4D59-AD40-FE7BE12B9842\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"bookId\": \"12AFE77E-9F71-472B-BA9D-33FDBE15B8CF\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return patientrecordbybooks;
	}	
	
	
	public static String patientrecordbyapptypes_payload() {
		String patientrecordbyapptypes = "{\r\n"
				+ "  \"additionalProperties\": {},\r\n"
				+ "  \"appointmentTypes\": [\r\n"
				+ "    {\r\n"
				+ "      \"appointmentTypeId\": \"244718D0-066F-4348-9778-B02B2DD2BE9A\",\r\n"
				+ "      \"patientRecord\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"appointmentTypeId\": \"8DE02BB1-307A-4A93-837F-3F8BF156B77F\",\r\n"
				+ "      \"patientRecord\": false\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"appointmentTypeId\": \"4A5C323C-0BC9-43EC-A43A-CC43D9285418\",\r\n"
				+ "      \"patientRecord\": false\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return patientrecordbyapptypes;
	}	
	
		
	public static String addPatient(String firstName,String lastName,String dob,String gender) {
		String addPatient="{\r\n"
				+ "    \"firstName\": \""+firstName+"\",\r\n"
				+ "    \"lastName\": \""+lastName+"\",\r\n"
				+ "    \"dateOfBirth\": \""+dob+"\",\r\n"
				+ "    \"gender\": \""+gender+"\",\r\n"
				+ "    \"zip\": \"27518\",\r\n"
				+ "    \"address\": {},\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"additionFields\": {\r\n"
				+ "        \"CP\": {\r\n"
				+ "            \"CP\": \"\"\r\n"
				+ "        },\r\n"
				+ "        \"HP\": {\r\n"
				+ "            \"HP\": \"\"\r\n"
				+ "        },\r\n"
				+ "        \"AP\": {\r\n"
				+ "            \"AP\": \"\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return addPatient;
	}
		
	
	public static String matchPatient() {
		 String matchpatient = "{\r\n"
		 		+ "    \"patientMatches\": [\r\n"
		 		+ "        {\r\n"
		 		+ "            \"entity\": \"Patient Last Name\",\r\n"
		 		+ "            \"isMandatory\": true,\r\n"
		 		+ "            \"code\": \"LN\",\r\n"
		 		+ "            \"value\": \"Ketan124\",\r\n"
		 		+ "            \"selected\": true,\r\n"
		 		+ "            \"search\": true,\r\n"
		 		+ "            \"error\": \"\",\r\n"
		 		+ "            \"message\": \"Please enter your last name\",\r\n"
		 		+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
		 		+ "            \"type\": \"TEXT\",\r\n"
		 		+ "            \"key\": null,\r\n"
		 		+ "            \"seq\": 2\r\n"
		 		+ "        },\r\n"
		 		+ "        {\r\n"
		 		+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
		 		+ "            \"isMandatory\": true,\r\n"
		 		+ "            \"code\": \"FN\",\r\n"
		 		+ "            \"value\": \"Ketan124\",\r\n"
		 		+ "            \"selected\": true,\r\n"
		 		+ "            \"search\": true,\r\n"
		 		+ "            \"error\": \"\",\r\n"
		 		+ "            \"message\": \"Please enter your first name\",\r\n"
		 		+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
		 		+ "            \"type\": \"TEXT\",\r\n"
		 		+ "            \"key\": null,\r\n"
		 		+ "            \"seq\": 1\r\n"
		 		+ "        },\r\n"
		 		+ "        {\r\n"
		 		+ "            \"entity\": \"Date Of Birth\",\r\n"
		 		+ "            \"isMandatory\": true,\r\n"
		 		+ "            \"code\": \"DOB\",\r\n"
		 		+ "            \"value\": \"01/01/2000\",\r\n"
		 		+ "            \"selected\": true,\r\n"
		 		+ "            \"search\": true,\r\n"
		 		+ "            \"error\": \"\",\r\n"
		 		+ "            \"message\": \"Please enter a valid birth date\",\r\n"
		 		+ "            \"regex\": \"^(0[1-9]|1[0-2])\\\\/(0[1-9]|1\\\\d|2\\\\d|3[01])\\\\/(19|20)\\\\d{2}$\",\r\n"
		 		+ "            \"type\": \"DOB\",\r\n"
		 		+ "            \"key\": null,\r\n"
		 		+ "            \"seq\": 3\r\n"
		 		+ "        },\r\n"
		 		+ "        {\r\n"
		 		+ "            \"entity\": \"Sex assigned at birth\",\r\n"
		 		+ "            \"isMandatory\": true,\r\n"
		 		+ "            \"code\": \"GENDER\",\r\n"
		 		+ "            \"value\": \"M\",\r\n"
		 		+ "            \"selected\": true,\r\n"
		 		+ "            \"search\": true,\r\n"
		 		+ "            \"error\": \"\",\r\n"
		 		+ "            \"message\": \"Please select your gender\",\r\n"
		 		+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
		 		+ "            \"type\": \"SELECT\",\r\n"
		 		+ "            \"key\": null,\r\n"
		 		+ "            \"seq\": 4\r\n"
		 		+ "        }\r\n"
		 		+ "    ],\r\n"
		 		+ "    \"maxCriteria\": \"4\",\r\n"
		 		+ "    \"allowDuplicatePatient\": false,\r\n"
		 		+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
		 		+ "}";
		return matchpatient;
	}
	
public static String careProvider() {
		
		String careProvider="{\r\n"
				+ "  \"locationId\": \"2e77cb9c-c0af-4b08-a5c9-0ae33b8dfcc7\",\r\n"
				+ "  \"appointmentTypeId\": \"40d88aae-d627-4d07-8558-337abedcd88d\",\r\n"
				+ "  \"appointmentTypeCatId\": \"aa81a2da-5781-46e7-8b67-19b873eb3cdb\",\r\n"
				+ "  \"startDateTime\": \"06/07/2022 04:00:28\",\r\n"
				+ "  \"endDateTime\": \"06/09/2022 04:00:28\",\r\n"
				+ "  \"careProvider\": [\r\n"
				+ "    {\r\n"
				+ "      \"resourceCatId\": \"58e61653-fe3d-448b-ba06-8bcdbfa1cf0f\",\r\n"
				+ "      \"resourceId\": \"e47ea31b-8436-40df-8152-c9ef9d8721fe\",\r\n"
				+ "      \"slotSize\": \"5\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		
		return careProvider;
	}

		
		public static String cancelStatus() {
			
			String cancelStatus="{\r\n"
					+ "    \"numberOfDays\": 1,\r\n"
					+ "    \"appointmentTypeId\": \"2273a0b4-add6-4f8a-9b36-b6857e2fd344\",\r\n"
					+ "    \"patientId\": \"6d042c21-2d20-46cf-8197-be1f6a69b5fe\"\r\n"
					+ "}";
			
			return cancelStatus;
		}

}


