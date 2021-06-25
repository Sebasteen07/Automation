// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

public class PayloadPssPatientModulator {
	public String getApptDetailPayload() {
		String apptDetail = "{\r\n"
				+ "  \"appointmentTypeId\": 203950,\r\n"
				+ "  \"bookId\": 204202,\r\n"
				+ "  \"locationId\": 204200,\r\n"
				+ "  \"type\": \"PT_EXISTING\"\r\n"
				+ "}";
		return apptDetail;
	}

	public String validateProviderLinkPayload() {
		String validateProviderLink ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": 205300,\r\n"
				+ "    \"appointmentType\": null,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"flow\": \"loginless\",\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"suj\",\r\n"
				+ "        \"LN\": \"it\",\r\n"
				+ "        \"DOB\": \"11/11/1999\",\r\n"
				+ "        \"GENDER\": \"M\",\r\n"
				+ "        \"EMAIL\": \"sujit2@gmail.com\",\r\n"
				+ "        \"PHONE\": null,\r\n"
				+ "        \"INSID\": null,\r\n"
				+ "        \"ADDR1\": null,\r\n"
				+ "        \"ADDR2\": null,\r\n"
				+ "        \"CITY\": null,\r\n"
				+ "        \"STATE\": null,\r\n"
				+ "        \"ZIP\": null,\r\n"
				+ "        \"PHONE3\": null,\r\n"
				+ "        \"PHONE2\": null\r\n"
				+ "    }\r\n"
				+ "}";
		return validateProviderLink;
	}
	
	public String locationsByNextAvailablePayload() {
		String locationsByNextAvailable ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"locations\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 205605\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": 205755,\r\n"
				+ "    \"patientType\": \"PT_NEW\"\r\n"
				+ "}";
		return locationsByNextAvailable;
	}
	
	public String locationsByRulePayload() {
		String locationsByRule ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"linkGenerationFlow\": false,\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"one\",\r\n"
				+ "        \"LN\": \"two\",\r\n"
				+ "        \"DOB\": \"11/11/1999\",\r\n"
				+ "        \"GENDER\": \"M\",\r\n"
				+ "        \"EMAIL\": \"sujit.kolhe@crossasyst.com\",\r\n"
				+ "        \"PHONE\": \"111-111-1111\",\r\n"
				+ "        \"INSID\": null,\r\n"
				+ "        \"ADDR1\": null,\r\n"
				+ "        \"ADDR2\": null,\r\n"
				+ "        \"CITY\": null,\r\n"
				+ "        \"STATE\": null,\r\n"
				+ "        \"ZIP\": \"12345\",\r\n"
				+ "        \"PHONE1\": null\r\n"
				+ "    },\r\n"
				+ "    \"flow\": \"loginless\"\r\n"
				+ "}";
		return locationsByRule;
	}

	public String anonymousMatchAndCreatePatientPayload() {
		String anonymousMatchAndCreatePatient ="{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"firstname\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"value\": \"one\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": true,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter your first name\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 1\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"type\": \"LOGINLESS\",\r\n"
				+ "    \"allowDuplicatePatient\": false,\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"flowType\": \"loginless\"\r\n"
				+ "    }\r\n"
				+ "}";
		return anonymousMatchAndCreatePatient;
		}
	
	public String identifyPatientForReschedulePayload() {
		String identifyPatientForReschedule ="{\r\n"
				+ "    \"patientIdentityMap\": {\r\n"
				+ "        \"FN\": \"one\",\r\n"
				+ "        \"LN\": \"two\"\r\n"
				+ "    },\r\n"
				+ "    \"flowType\": \"loginless\",\r\n"
				+ "    \"guid\": \"0eb4e14a-cc19-4cd9-b354-02bb8f6cf522\"\r\n"
				+ "}";
		return identifyPatientForReschedule;
	}

	public String specialtyByRulePayload() {
		String specialtyByRulePayload ="{\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"book\": 205300,\r\n"
				+ "    \"flow\": \"loginless\",\r\n"
				+ "    \"linkGenerationFlow\": true,\r\n"
				+ "    \"location\": 204200,\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"additionalProp1\": \"string\",\r\n"
				+ "        \"additionalProp2\": \"string\",\r\n"
				+ "        \"additionalProp3\": \"string\"\r\n"
				+ "    },\r\n"
				+ "    \"patientType\": null,\r\n"
				+ "    \"radius\": 0,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"specialty\": 202513,\r\n"
				+ "    \"startDateTime\": \"05/25/2021 01:30:00\",\r\n"
				+ "    \"traversal\": true,\r\n"
				+ "    \"zipcode\": 12345\r\n"
				+ "}";
		return specialtyByRulePayload;
	}
	
	public String createTokenPayload(String accessToken) {
		String createToken ="{\r\n"
				+ "  \"patientId\": \"27766\",\r\n"
				+ "  \"token\": \"" + accessToken + "\",\r\n"
				+ "  \"type\": \"PT_NEW\"\r\n"
				+ "}";
		return createToken;
	}

	public String locationsBasedOnZipcodeAndRadiusPayload() {
		String createToken ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"zipcode\": \"60611\",\r\n"
				+ "    \"radius\": \"25\"\r\n"
				+ "}";
		return createToken;
	}

	public String apptTypeNextAvailablePayload() {
		String apptTypeNextavailable = "{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": 200856,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentTypes\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 201006\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"patientType\": \"PT_NEW\"\r\n"
				+ "}\r\n"
				+ "";
		return apptTypeNextavailable;
	}

	public String booksByNextAvailablePayload() {
		String booksByNextAvailable = "{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": 206752,\r\n"
				+ "    \"books\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 207003\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"appointmentType\": 206901,\r\n"
				+ "    \"patientType\": \"PT_NEW\"\r\n"
				+ "}\r\n"
				+ "";
		return booksByNextAvailable;
	}
	
	public String  booksByRulePayload()
	{
		String  getBookByRule="{\r\n"
				+ "  \"appointmentType\": 203950,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"flow\": \"loginless\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"location\": 204200,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"Test\",\r\n"
				+ "    \"LN\": \"APISix\",\r\n"
				+ "    \"DOB\": \"01/28/1997\"\r\n"
				+ "  },\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"radius\": 0,\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"startDateTime\": null,\r\n"
				+ "  \"traversal\": true,\r\n"
				+ "  \"zipcode\": 0\r\n"
				+ "}";
		return getBookByRule;
	}
	
	public String  allowOnlineCancellationPayload()
	{
		String  allowOnlineCancellation="{\r\n"
				+ "  \"externalAppointmentId\": \"9522\",\r\n"
				+ "  \"map\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  }\r\n"
				+ "}";
		return allowOnlineCancellation;
	}
	
	public String  availableslotsPayload()
	{
		String  availableSlots="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": 204200,\r\n"
				+ "    \"book\": 204450,\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"startDateTime\": \"05/28/2021\",\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"traversal\": false,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"extApptId\": null\r\n"
				+ "}";
		return availableSlots;
	}
	
	public String  cancelAppointmentPayload()
	{
		String  cancelAppointment="{\r\n"
				+ "    \"appointmentId\": \"9604\",\r\n"
				+ "    \"cancellationMap\": null\r\n"
				+ "}";
		return cancelAppointment;
	}
	
	public String  rescheduleAppointmentPayload()
	{
		String  rescheduleAppointment="{\r\n"
				+ "    \"apptToSchedule\": {\r\n"
				+ "        \"patientType\": \"PT_NEW\",\r\n"
				+ "        \"slotId\": \"4994162\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"book\": 204450,\r\n"
				+ "        \"appointmentType\": 203950,\r\n"
				+ "        \"location\": 204200,\r\n"
				+ "        \"startDateTime\": \"05/30/2021 01:15:00\",\r\n"
				+ "        \"customQuestion\": \"due to unavailibility\",\r\n"
				+ "        \"insuranceInfo\": null,\r\n"
				+ "        \"patientInfo\": {},\r\n"
				+ "        \"allowDuplicatePatient\": false,\r\n"
				+ "        \"updatePatientDetails\": {\r\n"
				+ "            \"FN\": \"Test\",\r\n"
				+ "            \"LN\": \"APITen\"\r\n"
				+ "        },\r\n"
				+ "        \"flow\": \"loginless\",\r\n"
				+ "        \"appTypeDetail\": null,\r\n"
				+ "        \"rule\": \"B,L,T\",\r\n"
				+ "        \"leafNode\": null\r\n"
				+ "    },\r\n"
				+ "    \"apptToReschedule\": {\r\n"
				+ "        \"appointmentId\": \"4831\",\r\n"
				+ "        \"cancellationMap\": {\r\n"
				+ "            \"id\": \"\",\r\n"
				+ "            \"name\": \"\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return rescheduleAppointment;
	}
	
	public String  scheduleAppointmentPayload()
	{
		String  scheduleAppointment="{\r\n"
				+ "  \"allowDupliacatePatient\": false,\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"appTypeCategory\": null,\r\n"
				+ "  \"appTypeDetail\": null,\r\n"
				+ "  \"appointmentType\": \"203950\",\r\n"
				+ "  \"book\": \"204450\",\r\n"
				+ "  \"bookCategory\":null,\r\n"
				+ "  \"customQuestion\": \"sdsd\",\r\n"
				+ "  \"extApptId\": null,\r\n"
				+ "  \"flow\": \"loginless\",\r\n"
				+ " \r\n"
				+ "  \"leafNode\": null,\r\n"
				+ "  \"location\": \"204200\",\r\n"
				+ "  \"nextAvailable\": true,\r\n"
				+ "\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"practiceTiezone\": \"EDT\",\r\n"
				+ "  \"rule\": \"T,L,B\",\r\n"
				+ "  \"slotId\": \"4993624\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"stackingFlag\": true,\r\n"
				+ "  \"startDateTime\": \"05/22/2021 02:45:00\",\r\n"
				+ "  \"traversal\": true\r\n"
				+ "  \r\n"
				+ "}";
		return scheduleAppointment;
	}
	
	public String  timeZnCodeForDatePayload()
	{
		String  timezncodefrdate="{\r\n"
				+ "  \"dateTime\": \"05/21/2021 01:45:00\",\r\n"
				+ "  \"locTimeZone\": \"America/New_York\"\r\n"
				+ "}";
		return timezncodefrdate;
	}
	
	public String  appointmentTypesByrulePayload()
	{
		String  appmntTypesByRule="{\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"flow\": \"loginless\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"location\": null,\r\n"
				+ "  \r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"radius\": 0,\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"startDateTime\": null,\r\n"
				+ "  \"traversal\": true,\r\n"
				+ "  \"zipcode\": 0\r\n"
				+ "}";
		return appmntTypesByRule;
	}
	public String  cancelStatusPayload()
	{
		String  cancelstatus="{\r\n"
				+ "	\"acceptComment\": true,\r\n"
				+ "\r\n"
				+ "	\"categoryId\": null,\r\n"
				+ "	\"customMessage\": null,\r\n"
				+ "	\"customQuestion\": null,\r\n"
				+ "	\"displayName\": null,\r\n"
				+ "\r\n"
				+ "	\"extAppointmentTypeId\": \"158\",\r\n"
				+ "	\"extApptId\": null,\r\n"
				+ "	\"extBookId\": \"string\",\r\n"
				+ "	\"extLocationId\": \"string\",\r\n"
				+ "	\"id\": 203950,\r\n"
				+ "	\"lastQuestRequired\": true,\r\n"
				+ "	\"messages\": null,\r\n"
				+ "	\"name\": \"string\",\r\n"
				+ "	\"nextAvailability\": true,\r\n"
				+ "	\"nextAvailabilityDate\": null,\r\n"
				+ "	\"nextAvailabilitySlot\": null,\r\n"
				+ "	\"numberOfDays\": 0,\r\n"
				+ "	\"preventScheduling\": 0,\r\n"
				+ "\r\n"
				+ "	\"type\": null,\r\n"
				+ "	\"visitReason\": null\r\n"
				+ "}";
		return cancelstatus;
	}
	
	public String commentDetailsPayload()
	{
		String  commentDetails="{\r\n"
				+ "  \"appointmentType\": 203950,\r\n"
				+ "  \"book\": 204450,\r\n"
				+ "  \"flow\": null,\r\n"
				+ "  \"linkGenerationFlow\": true,\r\n"
				+ "  \"location\": 204200,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  },\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"radius\": 0,\r\n"
				+ "  \"slotId\": \"4993623\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"startDateTime\": \"05/22/2021 02:30:00\",\r\n"
				+ "  \"traversal\": false,\r\n"
				+ "  \"zipcode\": 0\r\n"
				+ "}";
		return commentDetails;
	}

}
