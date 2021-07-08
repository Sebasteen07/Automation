// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientmodulatorapi.test;

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


	public String validateProviderLinkPayload(String firstName,String lastName,String dob,String gender,String email ) {
		String validateProviderLink ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": 205300,\r\n"
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
				+ "            \"entity\": \"firstname\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"value\": \""+firstName+"\",\r\n"
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

}
