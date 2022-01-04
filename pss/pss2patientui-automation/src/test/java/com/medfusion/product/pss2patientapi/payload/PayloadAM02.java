package com.medfusion.product.pss2patientapi.payload;

public class PayloadAM02 {
	
	public String openTokenPayload(String practiceid,String authUser) {
		String openToken = "{\r\n"
		+ " \"practiceId\": \""+practiceid+"\",\r\n"
		+ " \"authUserId\": \""+authUser+"\"\r\n"
		+ "}";
		return openToken;
	}

		

	public String reserveForSameDay(String value) {			
			String reserveForSameDay = "{\r\n"
					+ " \"id\": 203099,\r\n"
					+ " \"appointmentStacking\": false,\r\n"
					+ " \"slotCount\": 1,\r\n"
					+ " \"allowSameDayAppts\": true,\r\n"
					+ " \"apptTimeMark\": 0,\r\n"
					+ " \"apptTypeAllocated\": true,\r\n"
					+ " \"isContiguous\": false,\r\n"
					+ " \"leadTime\": {\r\n"
					+ " \"days\": 0,\r\n"
					+ " \"hours\": \"0\",\r\n"
					+ " \"mins\": \"0\"\r\n"
					+ " },\r\n"
					+ " \"excludeSlots\": [],\r\n"
					+ " \"apptTypeReservedReason\": \""+value+"\",\r\n"
					+ " \"acceptComment\": false,\r\n"
					+ " \"allowOnlineCancellation\": true,\r\n"
					+ " \"slotSize\": 5,\r\n"
					+ " \"schedulingDuration\": 0,\r\n"
					+ " \"pttype\": \"PT_ALL\",\r\n"
					+ " \"lastQuestRequired\": false\r\n"
					+ "}";
					return reserveForSameDay;
	}

	public String turnOFFShowProvider(boolean bool) {

		String turnOFFShowProvider = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"showProvider\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  }\r\n"
				+ "]";
		return turnOFFShowProvider;
	}		
						

	public String timeMark() {
			
	String timeMark = "{\r\n"
					+ " \"id\": 201750,\r\n"
					+ " \"appointmentStacking\": false,\r\n"
					+ " \"slotCount\": 1,\r\n"
					+ " \"allowSameDayAppts\": true,\r\n"
					+ " \"apptTimeMark\": 30,\r\n"
					+ " \"apptTypeAllocated\": true,\r\n"
					+ " \"isContiguous\": false,\r\n"
					+ " \"leadTime\": {\r\n"
					+ " \"days\": \"0\",\r\n"
					+ " \"hours\": \"0\",\r\n"
					+ " \"mins\": \"0\"\r\n"
					+ " },\r\n"
					+ " \"excludeSlots\": [],\r\n"
					+ " \"apptTypeReservedReason\": \"n\",\r\n"
					+ " \"acceptComment\": false,\r\n"
					+ " \"allowOnlineCancellation\": true,\r\n"
					+ " \"slotSize\": 5,\r\n"
					+ " \"schedulingDuration\": 0,\r\n"
					+ " \"pttype\": \"PT_ALL\",\r\n"
					+ " \"lastQuestRequired\": false\r\n"
					+ "}";
		return timeMark;
	}
	
	public String practicePyaload() {

		String payload = "{\r\n"
				+ "  \"id\": 24702,\r\n"
				+ "  \"name\": \"PSS-NG-PG16CN\",\r\n"
				+ "  \"practiceId\": \"24702\",\r\n"
				+ "  \"themeColor\": \"#008c7f\",\r\n"
				+ "  \"extPracticeId\": \"24702\",\r\n"
				+ "  \"partner\": \"NG\",\r\n"
				+ "  \"timezone\": \"America/Chicago\",\r\n"
				+ "  \"active\": true,\r\n"
				+ "  \"languages\": [\r\n"
				+ "    {\r\n"
				+ "      \"code\": \"EN\",\r\n"
				+ "      \"flag\": \"us\",\r\n"
				+ "      \"name\": \"English\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"starttime\": \"00:00\",\r\n"
				+ "  \"endtime\": \"23:59\",\r\n"
				+ "  \"logo\": null,\r\n"
				+ "  \"type\": null\r\n"
				+ "}";
		return payload;
	}	
	
	public String cancelReasonOffPyaload(boolean bool) {

		String payload = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"allowCancellationHours\",\r\n"
				+ "    \"value\": \"00:00\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PATIENT_ACCESS\",\r\n"
				+ "    \"key\": \"ValidDuration\",\r\n"
				+ "    \"value\": \"12\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"displaySlotsCount\",\r\n"
				+ "    \"value\": 2\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"maxCalendarMonths\",\r\n"
				+ "    \"value\": 2\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ageRestriction\",\r\n"
				+ "    \"value\": \"18\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"maxAppointments\",\r\n"
				+ "    \"value\": 10\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ShowPastAppointmentMonths\",\r\n"
				+ "    \"value\": 5\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PROVIDER\",\r\n"
				+ "    \"key\": \"showNextAvailable\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"LOCATION\",\r\n"
				+ "    \"key\": \"searchLocation\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showUpcoming\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showPast\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"patientUpdateConfig\",\r\n"
				+ "    \"value\": \"PATIENT_NOTES\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"establishPatientLastVisit\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showCancelReason\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"patientCreationDuration\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"MULTI_PRACTICE_SETTING\",\r\n"
				+ "    \"key\": \"sharePatientAcrossPractices\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}	
	
	public String rescheduleOnCancelPyaload(String days) {

		String payload = "[\r\n"
				+ "  {\r\n"
				+ "    \"isageRule\": false,\r\n"
				+ "    \"id\": 4235,\r\n"
				+ "    \"name\": \"Insomnia\",\r\n"
				+ "    \"displayName\": \"Insomnia\",\r\n"
				+ "    \"displayNames\": {\r\n"
				+ "      \"EN\": \"Insomnia\"\r\n"
				+ "    },\r\n"
				+ "    \"message\": {\r\n"
				+ "      \"EN\": \"ss\"\r\n"
				+ "    },\r\n"
				+ "    \"question\": {\r\n"
				+ "      \"EN\": null\r\n"
				+ "    },\r\n"
				+ "    \"customMessages\": {\r\n"
				+ "      \"EN\": null\r\n"
				+ "    },\r\n"
				+ "    \"sortOrder\": 1,\r\n"
				+ "    \"extAppointmentTypeId\": \"E0F61D51-3AFB-412E-AA3C-18786D23821C\",\r\n"
				+ "    \"preventRescheduleOnCancel\": \""+days+"\",\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"locations\": [\r\n"
				+ "      {\r\n"
				+ "        \"id\": \"206321\",\r\n"
				+ "        \"name\": \"PSS WLA\",\r\n"
				+ "        \"displayName\": \"PSS WLA\",\r\n"
				+ "        \"address\": {\r\n"
				+ "          \"id\": 206321,\r\n"
				+ "          \"address1\": \"Address line 1\",\r\n"
				+ "          \"address2\": \"Address line 2\",\r\n"
				+ "          \"city\": \"Cary\",\r\n"
				+ "          \"state\": \"NC\",\r\n"
				+ "          \"zipCode\": \"27518\",\r\n"
				+ "          \"country\": \"string\",\r\n"
				+ "          \"latitude\": 35.7469276,\r\n"
				+ "          \"longitude\": -78.77481569999999\r\n"
				+ "        },\r\n"
				+ "        \"timezone\": \"\",\r\n"
				+ "        \"extLocationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "        \"directionUrl\": \"https://dev3-pss-adminportal-ui.dev.medfusion.net/#/app/location\",\r\n"
				+ "        \"selected\": false,\r\n"
				+ "        \"phoneNumber\": \"\",\r\n"
				+ "        \"restrictToCareteam\": false,\r\n"
				+ "        \"locationLinks\": {}\r\n"
				+ "      }\r\n"
				+ "    ],\r\n"
				+ "    \"param\": {\r\n"
				+ "      \"id\": 4052,\r\n"
				+ "      \"appointmentStacking\": false,\r\n"
				+ "      \"slotCount\": 1,\r\n"
				+ "      \"allowSameDayAppts\": true,\r\n"
				+ "      \"apptTimeMark\": 0,\r\n"
				+ "      \"apptTypeAllocated\": true,\r\n"
				+ "      \"isContiguous\": false,\r\n"
				+ "      \"leadTime\": {\r\n"
				+ "        \"days\": \"0\",\r\n"
				+ "        \"hours\": \"0\",\r\n"
				+ "        \"mins\": \"0\"\r\n"
				+ "      },\r\n"
				+ "      \"excludeSlots\": [\r\n"
				+ "        {\r\n"
				+ "          \"startTime\": \"05:00\",\r\n"
				+ "          \"endTime\": \"06:00\",\r\n"
				+ "          \"id\": 202374,\r\n"
				+ "          \"beforeAfterStart\": \">\",\r\n"
				+ "          \"beforeAfterEnd\": \"<\"\r\n"
				+ "        }\r\n"
				+ "      ],\r\n"
				+ "      \"apptTypeReservedReason\": \"n\",\r\n"
				+ "      \"acceptComment\": false,\r\n"
				+ "      \"allowOnlineCancellation\": true,\r\n"
				+ "      \"slotSize\": 5,\r\n"
				+ "      \"schedulingDuration\": 30,\r\n"
				+ "      \"pttype\": \"PT_ALL\",\r\n"
				+ "      \"lastQuestRequired\": false\r\n"
				+ "    },\r\n"
				+ "    \"ageRule\": \"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}	
	
	public String bookappointmenttypePyaload(int days) {

		String payload = "{\r\n"
				+ "  \"isageRule\": false,\r\n"
				+ "  \"id\": 4400,\r\n"
				+ "  \"description\": null,\r\n"
				+ "  \"book\": {\r\n"
				+ "    \"id\": 4303,\r\n"
				+ "    \"name\": null,\r\n"
				+ "    \"displayName\": null,\r\n"
				+ "    \"emailAddress\": null,\r\n"
				+ "    \"extBookId\": null,\r\n"
				+ "    \"acceptComment\": null,\r\n"
				+ "    \"acceptEmail\": null,\r\n"
				+ "    \"acceptNew\": null,\r\n"
				+ "    \"ageRule\": null,\r\n"
				+ "    \"deleted\": null,\r\n"
				+ "    \"providerMessage\": null,\r\n"
				+ "    \"sharePatients\": null,\r\n"
				+ "    \"slotSize\": null,\r\n"
				+ "    \"status\": false,\r\n"
				+ "    \"categoryId\": null,\r\n"
				+ "    \"categoryName\": null,\r\n"
				+ "    \"practice\": null,\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"bookSort\": null,\r\n"
				+ "    \"bookTranslations\": null,\r\n"
				+ "    \"bookType\": null,\r\n"
				+ "    \"bookLevel\": null,\r\n"
				+ "    \"careteam\": null,\r\n"
				+ "    \"links\": null\r\n"
				+ "  },\r\n"
				+ "  \"appointmentType\": {\r\n"
				+ "    \"id\": 4235,\r\n"
				+ "    \"name\": null,\r\n"
				+ "    \"displayName\": null,\r\n"
				+ "    \"description\": null,\r\n"
				+ "    \"extAppointmentTypeId\": null,\r\n"
				+ "    \"duration\": null,\r\n"
				+ "    \"categoryId\": null,\r\n"
				+ "    \"categoryName\": null,\r\n"
				+ "    \"messages\": null,\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "    \"customMessage\": null,\r\n"
				+ "    \"appointmentTypeTranslation\": null,\r\n"
				+ "    \"preventRescheduleOnCancel\": null,\r\n"
				+ "    \"ageRule\": null,\r\n"
				+ "    \"preventScheduling\": null,\r\n"
				+ "    \"appointmentTypeSort\": null\r\n"
				+ "  },\r\n"
				+ "  \"appointmentStacking\": false,\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"slotCount\": 1,\r\n"
				+ "  \"allowSameDayAppts\": true,\r\n"
				+ "  \"apptTimeMark\": 0,\r\n"
				+ "  \"apptTypeAllocated\": true,\r\n"
				+ "  \"isContiguous\": false,\r\n"
				+ "  \"leadTime\": {\r\n"
				+ "    \"days\": "+days+",\r\n"
				+ "    \"hours\": \"0\",\r\n"
				+ "    \"mins\": \"0\"\r\n"
				+ "  },\r\n"
				+ "  \"maxPerDay\": 0,\r\n"
				+ "  \"excludeSlots\": [],\r\n"
				+ "  \"apptTypeReservedReason\": \"n\",\r\n"
				+ "  \"ageRule\": \"\",\r\n"
				+ "  \"status\": true,\r\n"
				+ "  \"allowOnlineCancellation\": true,\r\n"
				+ "  \"providerAvailabilityDays\": 0,\r\n"
				+ "  \"schedulingDuration\": 0,\r\n"
				+ "  \"locations\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": \"206321\",\r\n"
				+ "      \"name\": \"PSS WLA\",\r\n"
				+ "      \"displayName\": \"PSS WLA\",\r\n"
				+ "      \"address\": {\r\n"
				+ "        \"id\": 206321,\r\n"
				+ "        \"address1\": \"Address line 1\",\r\n"
				+ "        \"address2\": \"Address line 2\",\r\n"
				+ "        \"city\": \"Cary\",\r\n"
				+ "        \"state\": \"NC\",\r\n"
				+ "        \"zipCode\": \"27518\",\r\n"
				+ "        \"country\": \"string\",\r\n"
				+ "        \"latitude\": 35.7469276,\r\n"
				+ "        \"longitude\": -78.77481569999999,\r\n"
				+ "        \"locAddress\": \"Address line 1 Address line 2 Cary NC 27518\"\r\n"
				+ "      },\r\n"
				+ "      \"timezone\": \"\",\r\n"
				+ "      \"extLocationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "      \"directionUrl\": \"https://dev3-pss-adminportal-ui.dev.medfusion.net/#/app/location\",\r\n"
				+ "      \"selected\": true,\r\n"
				+ "      \"phoneNumber\": \"\",\r\n"
				+ "      \"restrictToCareteam\": false,\r\n"
				+ "      \"locationLinks\": {}\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"pttype\": \"PT_ALL\",\r\n"
				+ "  \"lastQuestRequired\": false\r\n"
				+ "}";
		return payload;
	}	
	
	
	public String locationsByNextAvailablePyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	
	

}
