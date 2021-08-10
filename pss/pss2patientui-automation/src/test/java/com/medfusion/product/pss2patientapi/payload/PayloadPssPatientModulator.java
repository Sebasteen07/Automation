// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadPssPatientModulator {

	public String getApptDetailPayload(String appointmentTypeId,String bookid,String locationid) {
		String apptDetail = "{\r\n"
				+ "  \"appointmentTypeId\": \""+appointmentTypeId+"\",\r\n"
				+ "  \"bookId\": \""+bookid+"\",\r\n"
				+ "  \"locationId\": \""+locationid+"\",\r\n"
				+ "  \"type\": \"PT_EXISTING\"\r\n"
				+ "}";
		return apptDetail;
	}


	public String validateProviderLinkPayload(String firstName,String lastName,String dob,String gender,String email,String book ) {
		String validateProviderLink ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": \"" + book + "\",\r\n"
				+ "    \"appointmentType\": null,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"flow\": \"loginless\",\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"" + firstName + "\",\r\n"
				+ "        \"LN\": \"" +  lastName + "\",\r\n"
				+ "        \"DOB\": \""+ dob  +"\",\r\n"
				+ "        \"GENDER\": \"" + gender+"\",\r\n"
				+ "        \"EMAIL\": \"" + email + "\",\r\n"
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
	
	public String locationsByRulePayload(String firstName,String lastName,String gender,String email,String phoneNo,String zipCode) {
		String locationsByRule ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"linkGenerationFlow\": false,\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"" + firstName+ "\",\r\n"
				+ "        \"LN\": \"" +lastName + "\",\r\n"
				+ "        \"DOB\": \"11/11/1999\",\r\n"
				+ "        \"GENDER\": \"" + gender + "\",\r\n"
				+ "        \"EMAIL\": \"" +email +"\",\r\n"
				+ "        \"PHONE\": \""+phoneNo+"\",\r\n"
				+ "        \"INSID\": null,\r\n"
				+ "        \"ADDR1\": null,\r\n"
				+ "        \"ADDR2\": null,\r\n"
				+ "        \"CITY\": null,\r\n"
				+ "        \"STATE\": null,\r\n"
				+ "        \"ZIP\": \"" +zipCode+ "\",\r\n"
				+ "        \"PHONE1\": null\r\n"
				+ "    },\r\n"
				+ "    \"flow\": \"loginless\"\r\n"
				+ "}";
		return locationsByRule;
	}
	

	public String anonymousMatchAndCreatePatientPayload(String firstName) {
		String anonymousMatchAndCreatePatient ="{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"value\": \"GECare\",\r\n"
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
				+ "            \"entity\": \"Patient Last Name\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"value\": \"GECare\",\r\n"
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
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"value\": \"GECare@mailinator.com\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Email is required and format should be john@doe.com\",\r\n"
				+ "            \"regex\": \"\\\\w+([-+.]\\\\w+)*@\\\\w+([-.]\\\\w+)*\\\\.\\\\w+([-.]\\\\w+)*$\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 5\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Preferred Phone Number\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter a valid phone number\",\r\n"
				+ "            \"regex\": \"^\\\\(?[0-9]{3}\\\\)?[-]([0-9]{3})[-]([0-9]{4})$\",\r\n"
				+ "            \"type\": \"PHONE\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 6\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Insurance ID\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"INSID\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter a valid insurance id\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 7\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Address1\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"ADDR1\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter your address\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 9\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Address2\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"ADDR2\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter your address\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 10\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"City\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"CITY\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please enter a valid city\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 11\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"State\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"STATE\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Please select a state\",\r\n"
				+ "            \"regex\": \"^(?!\\\\s*$).+\",\r\n"
				+ "            \"type\": \"SELECT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 12\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Postal code is required eg(12345 or 12345-1234)\",\r\n"
				+ "            \"regex\": \"^\\\\d{5}(?:[-\\\\s]\\\\d{4})?$\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 13\r\n"
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
	
	
	public String identifyPatientForReschedulePayload(String firstName,String lastName) {
		String identifyPatientForReschedule ="{\r\n"
				+ "    \"patientIdentityMap\": {\r\n"
				+ "        \"FN\": \"" + firstName + "\",\r\n"
				+ "        \"LN\": \"" +lastName + "\"\r\n"
				+ "    },\r\n"
				+ "    \"flowType\": \"loginless\",\r\n"
				+ "    \"guid\": \"0eb4e14a-cc19-4cd9-b354-02bb8f6cf522\"\r\n"
				+ "}";
		return identifyPatientForReschedule;
	}
//
//	public String specialtyByRulePayload() {
//		String specialtyByRulePayload ="{\r\n"
//				+ "    \"appointmentType\": 203950,\r\n"
//				+ "    \"book\": 205300,\r\n"
//				+ "    \"flow\": \"loginless\",\r\n"
//				+ "    \"linkGenerationFlow\": true,\r\n"
//				+ "    \"location\": 204200,\r\n"
//				+ "    \"patientDetails\": {\r\n"
//				+ "        \"additionalProp1\": \"string\",\r\n"
//				+ "        \"additionalProp2\": \"string\",\r\n"
//				+ "        \"additionalProp3\": \"string\"\r\n"
//				+ "    },\r\n"
//				+ "    \"patientType\": null,\r\n"
//				+ "    \"radius\": 0,\r\n"
//				+ "    \"slotId\": null,\r\n"
//				+ "    \"specialty\": 202513,\r\n"
//				+ "    \"startDateTime\": \"05/25/2021 01:30:00\",\r\n"
//				+ "    \"traversal\": true,\r\n"
//				+ "    \"zipcode\": 12345\r\n"
//				+ "}";
//		return specialtyByRulePayload;
//	}
	

	public String specialtyByRulePayload(String appointmentId) {
		String specialtyByRulePayload ="{\r\n"
				+ "    \"appointmentType\": 203950,\r\n"
				+ "    \"book\": \""+appointmentId+"\",\r\n"
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

	public String locationsBasedOnZipcodeAndRadiusPayload(String appointmentId) {
		String createToken ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"appointmentType\":\""+appointmentId+"\",\r\n"
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
	
	public String  booksByRulePayload(String AppmntTypeId, String locationId)
	{
		String  getBookByRule="{\r\n"
				+ "	\"appointmentType\": \""+AppmntTypeId+"\",\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"flow\": \"loginless\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "	\"location\": \""+locationId+"\",\r\n"
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
	
	public String  allowOnlineCancellationPayload(String extAppid)
	{
		String  allowOnlineCancellation="{\r\n"
				+ "	\"externalAppointmentId\": \""+extAppid+"\",\r\n"
				+ "  \"map\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  }\r\n"
				+ "}";
		return allowOnlineCancellation;
	}
	
	public String  availableslotsPayload(String locationId, String bookId, String AppmntTypeId)
	{
		String  availableSlots="{\r\n"
				+ "	\"specialty\": null,\r\n"
				+ "	\"location\": \""+locationId+"\",\r\n"
				+ "	\"book\": \""+bookId+"\",\r\n"
				+ "	\"appointmentType\": \""+AppmntTypeId+"\",\r\n"
				+ "	\"startDateTime\": \"07/14/2021\",\r\n"
				+ "	\"slotId\": null,\r\n"
				+ "	\"traversal\": false,\r\n"
				+ "	\"patientType\": \"PT_NEW\",\r\n"
				+ "	\"extApptId\": null\r\n"
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
	
	public String  rescheduleAppointmentPayload(String SlotId,String bookId,String AppmntTypeId,String locationId,String DateTime, String ReschAppId)
	{
		String  rescheduleAppointment="{\r\n"
				+ "    \"apptToSchedule\": {\r\n"
				+ "        \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+SlotId+"\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "	\"book\": \""+bookId+"\",\r\n"
				+ "	\"appointmentType\": \""+AppmntTypeId+"\",\r\n"
				+ "	\"location\": \""+locationId+"\",\r\n"
				+ "        \"startDateTime\": \""+DateTime+"\",\r\n"
				+ "        \"customQuestion\": \"test\",\r\n"
				+ "        \"insuranceInfo\": null,\r\n"
				+ "        \"patientInfo\": {},\r\n"
				+ "        \"allowDuplicatePatient\": false,\r\n"
				+ "        \"updatePatientDetails\": {\r\n"
				+ "            \"FN\": \"Test\",\r\n"
				+ "            \"LN\": \"Slotone\"\r\n"
				+ "        },\r\n"
				+ "        \"flow\": \"loginless\",\r\n"
				+ "        \"appTypeDetail\": null,\r\n"
				+ "        \"rule\": \"T,L,B\",\r\n"
				+ "        \"leafNode\": null\r\n"
				+ "    },\r\n"
				+ "    \"apptToReschedule\": {\r\n"
				+ "        \"appointmentId\": \"5952\",\r\n"
				+ "        \"cancellationMap\": {\r\n"
				+ "            \"id\": null,\r\n"
				+ "            \"name\": \"Time not matchedddddddddddddddddddddddddduuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return rescheduleAppointment;
	}
	
	public String  scheduleAppointmentPayload(String SlotId,String bookId,String AppmntTypeId,String locationId, String date,String SlotTime)
	{   
		String  scheduleAppointment="{\r\n"
				+ "	\"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+SlotId+"\",\r\n"
				+ "	\"specialty\": null,\r\n"
				+ "	\"book\": \""+bookId+"\",\r\n"
				+ "	\"appointmentType\": \""+AppmntTypeId+"\",\r\n"
				+ "	\"location\": \""+locationId+"\",\r\n"
					+ "  \"startDateTime\": \""+date+" "+SlotTime+"\",\r\n"		
				+ "	\"customQuestion\": \"test\",\r\n"
				+ "	\"insuranceInfo\": null,\r\n"
				+ "	\"patientInfo\": {},\r\n"
				+ "	\"allowDuplicatePatient\": false,\r\n"
				+ "	\"updatePatientDetails\": {\r\n"
				+ "		\"FN\": \"Test\",\r\n"
				+ "		\"LN\": \"Slotone\",\r\n"
				+ "		\"DOB\": \"01/28/1997\",\r\n"
				+ "		\"GENDER\": \"M\",\r\n"
				+ "		\"EMAIL\": \"rima.karmakar@crossasyst.com\",\r\n"
				+ "		\"PHONE\": \"510-931-9126\",\r\n"
				+ "		\"INSID\": null,\r\n"
				+ "		\"ADDR1\": null,\r\n"
				+ "		\"ADDR2\": null,\r\n"
				+ "		\"CITY\": null,\r\n"
				+ "		\"STATE\": null,\r\n"
				+ "		\"ZIP\": \"90231\",\r\n"
				+ "		\"PHONE2\": null,\r\n"
				+ "		\"PHONE1\": null\r\n"
				+ "	},\r\n"
				+ "	\"flow\": \"loginless\",\r\n"
				+ "	\"appTypeDetail\": null,\r\n"
				+ "	\"rule\": \"T,L,B\",\r\n"
				+ "	\"leafNode\": null\r\n"
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
	public String  cancelStatusPayload(String extAppid)
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
				+ "	\"extApptId\": \""+extAppid+"\",\r\n"
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
	
	public String commentDetailsPayload(String ApptypeId, String bookId, String locationId)
	{
		String  commentDetails="{\r\n"
		
				+ "	\"appointmentType\": \""+ApptypeId+"\",\r\n"
				
				+ "  \"bookId\": \""+bookId+"\",\r\n"
				+ "  \"flow\": null,\r\n"
				+ "  \"linkGenerationFlow\": true,\r\n"
				+ "  \"locationId\": \""+locationId+"\",\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"additionalProp1\": null,\r\n"
				+ "    \"additionalProp2\": null,\r\n"
				+ "    \"additionalProp3\": null\r\n"
				+ "  },\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"radius\": 0,\r\n"
				+ "  \"slotId\": \"4993071\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"startDateTime\": \"05/22/2021 02:30:00\",\r\n"
				+ "  \"traversal\": false,\r\n"
				+ "  \"zipcode\": 0\r\n"
				+ "}";
		return commentDetails;
	}

}
