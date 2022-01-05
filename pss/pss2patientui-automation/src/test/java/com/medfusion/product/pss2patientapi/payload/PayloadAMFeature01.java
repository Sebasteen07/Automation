// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadAMFeature01 {
	
	public String openTokenPayload(String practiceid,String authUser) {
		String openToken = "{\r\n"
				+ "    \"practiceId\": \""+practiceid+"\",\r\n"
				+ "    \"authUserId\": \""+authUser+"\"\r\n"
				+ "}";
		return openToken;
	}
	

	public String timeMark() {
		String timeMark = "{\r\n"
				+ "    \"id\": 201750,\r\n"
				+ "    \"appointmentStacking\": false,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"apptTimeMark\": 30,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"isContiguous\": false,\r\n"
				+ "    \"leadTime\": {\r\n"
				+ "        \"days\": \"0\",\r\n"
				+ "        \"hours\": \"0\",\r\n"
				+ "        \"mins\": \"0\"\r\n"
				+ "    },\r\n"
				+ "    \"excludeSlots\": [],\r\n"
				+ "    \"apptTypeReservedReason\": \"n\",\r\n"
				+ "    \"acceptComment\": false,\r\n"
				+ "    \"allowOnlineCancellation\": true,\r\n"
				+ "    \"slotSize\": 5,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"pttype\": \"PT_ALL\",\r\n"
				+ "    \"lastQuestRequired\": false\r\n"
				+ "}";
		return timeMark;
	}
	
	
	
	public String turnONOFFShowProvider(boolean value) {
		String turnONShowProvider = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"showProvider\",\r\n"
				+ "        \"value\": "+value+"\r\n"
				+ "    }\r\n"
				+ "]";
		return turnONShowProvider;
	}

	
	public String turnONOFFDecisionTree(boolean value) {
		String turnOFFShowProvider = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"showCategory\",\r\n"
				+ "    \"value\": "+value+"\r\n"
				+ "  }\r\n"
				+ "]";
		return turnOFFShowProvider;
	}
	
	public String reserveForSameDay(String reserveValue,boolean acceptValue,int timeMark) {
		String reserveForSameDay = "{\r\n"
				+ "    \"id\": 4054,\r\n"
				+ "    \"appointmentStacking\": false,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": "+acceptValue+",\r\n"
				+ "    \"apptTimeMark\": "+timeMark+",\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"isContiguous\": false,\r\n"
				+ "    \"leadTime\": {\r\n"
				+ "        \"days\": 0,\r\n"
				+ "        \"hours\": \"0\",\r\n"
				+ "        \"mins\": \"0\"\r\n"
				+ "    },\r\n"
				+ "    \"excludeSlots\": [],\r\n"
				+ "    \"apptTypeReservedReason\": \""+reserveValue+"\",\r\n"
				+ "    \"acceptComment\": false,\r\n"
				+ "    \"allowOnlineCancellation\": true,\r\n"
				+ "    \"slotSize\": 5,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"pttype\": \"PT_ALL\",\r\n"
				+ "    \"lastQuestRequired\": false\r\n"
				+ "}";
		return reserveForSameDay;
	}
	
	public String hideSlots(int leadValue)
	{
		String hideSlots="{\r\n"
				+ "  \"id\": 4054,\r\n"
				+ "  \"appointmentStacking\": true,\r\n"
				+ "  \"slotCount\": 1,\r\n"
				+ "  \"allowSameDayAppts\": true,\r\n"
				+ "  \"apptTimeMark\": 0,\r\n"
				+ "  \"apptTypeAllocated\": true,\r\n"
				+ "  \"isContiguous\": false,\r\n"
				+ "  \"leadTime\": {\r\n"
				+ "    \"days\": "+leadValue+",\r\n"
				+ "    \"hours\": \"0\",\r\n"
				+ "    \"mins\": \"0\"\r\n"
				+ "  },\r\n"
				+ "  \"excludeSlots\": [],\r\n"
				+ "  \"apptTypeReservedReason\": \"n\",\r\n"
				+ "  \"acceptComment\": false,\r\n"
				+ "  \"allowOnlineCancellation\": true,\r\n"
				+ "  \"slotSize\": 5,\r\n"
				+ "  \"schedulingDuration\": 0,\r\n"
				+ "  \"pttype\": \"PT_ALL\",\r\n"
				+ "  \"lastQuestRequired\": false\r\n"
				+ "}";
		return hideSlots;
		
	}
	
	public String showUpcomingToggle()
	{
		String showUpcomingToggle="[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"allowCancellationHours\",\r\n"
				+ "    \"value\": \"00:00\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PATIENT_ACCESS\",\r\n"
				+ "    \"key\": \"ValidDuration\",\r\n"
				+ "    \"value\": \"1\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"displaySlotsCount\",\r\n"
				+ "    \"value\": \"50\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"maxCalendarMonths\",\r\n"
				+ "    \"value\": \"100\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ageRestriction\",\r\n"
				+ "    \"value\": \"30\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"maxAppointments\",\r\n"
				+ "    \"value\": \"10\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ShowPastAppointmentMonths\",\r\n"
				+ "    \"value\": \"10\"\r\n"
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
				+ "    \"value\": \"UPDATE_INFO\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"establishPatientLastVisit\",\r\n"
				+ "    \"value\": \"365\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showCancelReason\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"patientCreationDuration\",\r\n"
				+ "    \"value\": \"365\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"MULTI_PRACTICE_SETTING\",\r\n"
				+ "    \"key\": \"sharePatientAcrossPractices\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  }\r\n"
				+ "]";
		return showUpcomingToggle;
		
	}
	

	public String acceptForSameDayONOFF(String value) {
		String reserveForSameDay = "{\r\n"
				+ "    \"id\": 203099,\r\n"
				+ "    \"appointmentStacking\": false,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"apptTimeMark\": 0,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"isContiguous\": false,\r\n"
				+ "    \"leadTime\": {\r\n"
				+ "        \"days\": 0,\r\n"
				+ "        \"hours\": \"0\",\r\n"
				+ "        \"mins\": \"0\"\r\n"
				+ "    },\r\n"
				+ "    \"excludeSlots\": [],\r\n"
				+ "    \"apptTypeReservedReason\": \""+value+"\",\r\n"
				+ "    \"acceptComment\": false,\r\n"
				+ "    \"allowOnlineCancellation\": true,\r\n"
				+ "    \"slotSize\": 5,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"pttype\": \"PT_ALL\",\r\n"
				+ "    \"lastQuestRequired\": false\r\n"
				+ "}";
		return reserveForSameDay;
	}
}

