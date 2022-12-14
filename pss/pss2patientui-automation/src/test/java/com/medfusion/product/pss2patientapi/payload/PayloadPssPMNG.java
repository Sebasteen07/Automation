// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadPssPMNG {

	public String validateProviderLinkPayload(String firstName,String lastName,String dob,String gender,String email,String book ) {
		String validateProviderLink ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": \"" + book + "\",\r\n"
				+ "    \"appointmentType\": null,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_EXISTING\",\r\n"
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
	
	public String validateProviderLinkPayload_New(String book, String locationid) {
		String payload ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"book\": "+book+",\r\n"
				+ "    \"appointmentType\": null,\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"flow\": \"loginless\",\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"abh\",\r\n"
				+ "        \"LN\": \"abh\",\r\n"
				+ "        \"DOB\": \"01/01/2000\",\r\n"
				+ "        \"GENDER\": \"M\",\r\n"
				+ "        \"EMAIL\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
				+ "        \"PHONE\": \"787-878-7878\",\r\n"
				+ "        \"INSID\": null,\r\n"
				+ "        \"ADDR1\": null,\r\n"
				+ "        \"ADDR2\": null,\r\n"
				+ "        \"CITY\": null,\r\n"
				+ "        \"STATE\": null,\r\n"
				+ "        \"ZIP\": \"27518\"\r\n"
				+ "    }\r\n"
				+ "}";
		return payload;
	}
	
	public String locationsByNextAvailablePayload(String bookid, String locationid, String apptid) {
		String locationsByNextAvailable ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"locations\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": "+locationid+"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"book\": "+bookid+",\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"patientType\": \"PT_NEW\"\r\n"
				+ "}";
		return locationsByNextAvailable;
	}
	
	public String appTypeByNextAvailablePayload(String locationid, String apptid) {
		String appTypeByNextAvailable ="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": "+locationid+",\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentTypes\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": "+apptid+"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"patientType\": \"PT_NEW\"\r\n"
				+ "}";
		return appTypeByNextAvailable;
	}
	
	public String appTypeByNextAvailablePayload_Exe(String locationid, String apptid) {
		String appTypeByNextAvailable ="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": "+locationid+",\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentTypes\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": "+apptid+"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"patientType\": \"PT_EXISTING\"\r\n"
				+ "}";
		return appTypeByNextAvailable;
	}
	
	public String locationsByRulePayload(String appt, String dob,String firstName,String lastName,String gender,String email,String phoneNo,String zipCode) {
		String locationsByRule ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": "+appt+",\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"linkGenerationFlow\": false,\r\n"
				+ "    \"patientDetails\": {\r\n"
				+ "        \"FN\": \"" + firstName+ "\",\r\n"
				+ "        \"LN\": \"" +lastName + "\",\r\n"
				+ "        \"DOB\": \""+dob+"\",\r\n"
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
	

	public String anonymousMatchAndCreatePatientPayload() {
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
	
	public String anonymousPatientPayload(String FN, String LN) {
		String payload ="{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"firstname\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"value\": \""+FN+"\",\r\n"
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
				+ "            \"entity\": \"lastname\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"value\": \""+LN+"\",\r\n"
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
				+ "            \"value\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
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
				+ "            \"value\": \"310-965-1102\",\r\n"
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
				+ "            \"entity\": \"Address2\",\r\n"
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
				+ "            \"entity\": \"Address1\",\r\n"
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
				+ "            \"value\": \"12345\",\r\n"
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
		return payload;
	}
	
	
	public String identifyPatientForReschedulePayload(String firstName,String lastName, String guid) {
		String identifyPatientForReschedule ="{\r\n"
				+ "    \"patientIdentityMap\": {\r\n"
				+ "        \"FN\": \"" + firstName + "\",\r\n"
				+ "        \"LN\": \"" +lastName + "\"\r\n"
				+ "    },\r\n"
				+ "    \"flowType\": \"loginless\",\r\n"
				+ "    \"guid\": \""+guid+"\"\r\n"
				+ "}";
		return identifyPatientForReschedule;
	}

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
	
	public String apptTypeNextAvailablePayload(String appt) {
		String apptTypeNextavailable = "{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": 206350,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentTypes\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": "+appt+"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"patientType\": \"PT_EXISTING\"\r\n"
				+ "}";
		return apptTypeNextavailable;
	}

	public String booksByNextAvailablePayload(String bookid, String locationid, String apptid) {
		String booksByNextAvailable = "{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"books\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": "+bookid+"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"patientType\": \"PT_NEW\"\r\n"
				+ "}";
		return booksByNextAvailable;
	}
	
	public String  booksByRulePayload(String apptid, String locationid)
	{
		String  getBookByRule="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"patientType\": \"PT_EXISTING\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"prepast\",\r\n"
				+ "    \"LN\": \"prepast\",\r\n"
				+ "    \"DOB\": \"01/01/2000\",\r\n"
				+ "    \"GENDER\": \"M\",\r\n"
				+ "    \"EMAIL\": null,\r\n"
				+ "    \"PHONE\": null,\r\n"
				+ "    \"INSID\": null,\r\n"
				+ "    \"ADDR1\": null,\r\n"
				+ "    \"ADDR2\": null,\r\n"
				+ "    \"CITY\": null,\r\n"
				+ "    \"STATE\": null,\r\n"
				+ "    \"ZIP\": null\r\n"
				+ "  },\r\n"
				+ "  \"flow\": \"loginless\"\r\n"
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
	
	public String  availableslotsPayload(String date, String bookid, String locationid, String apptid)
	{
		String  availableSlots="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"book\": "+bookid+",\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"startDateTime\": \""+date+"\",\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"traversal\": false,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"extApptId\": null\r\n"
				+ "}";
		return availableSlots;
	}
	
	public String  cancelAppointmentPayload(String extApptId)
	{
		String  cancelAppointment="{\r\n"
				+ "    \"appointmentId\": \""+extApptId+"\",\r\n"
				+ "    \"cancellationMap\": {\r\n"
				+ "        \"id\": null,\r\n"
				+ "        \"name\": \"Equipment Problem\"\r\n"
				+ "    }\r\n"
				+ "}";
		return cancelAppointment;
	}
	
	public String  cancelAppointmentWithReasonOtherPayload(String extApptId)
	{
		String  cancelAppointment="{\r\n"
				+ "    \"appointmentId\": \""+extApptId+"\",\r\n"
				+ "    \"cancellationMap\": {\r\n"
				+ "        \"id\": null,\r\n"
				+ "        \"name\": \"Other\"\r\n"
				+ "    }\r\n"
				+ "}";
		return cancelAppointment;
	}
	
	public String  cancelAppointmentWithoutReasonPayload(String extApptId)
	{
		String  cancelAppointment="{\r\n"
				+ "  \"appointmentId\": \""+extApptId+"\",\r\n"
				+ "  \"cancellationMap\": null\r\n"
				+ "}";
		return cancelAppointment;
	}
	
	public String  rescheduleAppointmentPayload(String slotid, String startdate, String apptid, String bookid, String locationid, String appttypeid)
	{
		String  rescheduleAppointment="{\r\n"
				+ "    \"apptToSchedule\": {\r\n"
				+ "        \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+slotid+"\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "	\"book\": \""+bookid+"\",\r\n"
				+ "	\"appointmentType\": \""+appttypeid+"\",\r\n"
				+ "	\"location\": \""+locationid+"\",\r\n"
				+ "        \"startDateTime\": \""+startdate+"\",\r\n"
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
				+ "        \"appointmentId\": \""+apptid+"\",\r\n"
				+ "        \"cancellationMap\": {\r\n"
				+ "            \"id\": null,\r\n"
				+ "            \"name\": \"Time not matchedddddddddddddddddddddddddduuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return rescheduleAppointment;
	}
	
	public String  scheduleAppointmentPayload(String slotid, String date,String slottime,String bookid, String locationid, String apptid)
	{   
		String  scheduleAppointment="{\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"slotId\": \""+slotid+"\",\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": "+bookid+",\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "    \"insuranceInfo\": null,\r\n"
				+ "    \"patientInfo\": {},\r\n"
				+ "    \"allowDuplicatePatient\": false,\r\n"
				+ "    \"updatePatientDetails\": {\r\n"
				+ "        \"FN\": \"dd\",\r\n"
				+ "        \"LN\": \"dd\",\r\n"
				+ "        \"DOB\": \"01/01/2000\",\r\n"
				+ "        \"GENDER\": \"M\",\r\n"
				+ "        \"EMAIL\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
				+ "        \"PHONE\": null,\r\n"
				+ "        \"INSID\": null,\r\n"
				+ "        \"ADDR1\": null,\r\n"
				+ "        \"ADDR2\": null,\r\n"
				+ "        \"CITY\": null,\r\n"
				+ "        \"STATE\": null,\r\n"
				+ "        \"ZIP\": null\r\n"
				+ "    },\r\n"
				+ "    \"flow\": \"loginless\",\r\n"
				+ "    \"appTypeDetail\": null,\r\n"
				+ "    \"rule\": \"T,L,B\",\r\n"
				+ "    \"leafNode\": null\r\n"
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
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"startDateTime\": \"10/22/2021 08:45:00\",\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"traversal\": false,\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"extApptId\": null,\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"ll\",\r\n"
				+ "    \"LN\": \"ll\",\r\n"
				+ "    \"DOB\": \"01/01/2000\",\r\n"
				+ "    \"GENDER\": \"M\",\r\n"
				+ "    \"EMAIL\": null,\r\n"
				+ "    \"PHONE\": null,\r\n"
				+ "    \"INSID\": null,\r\n"
				+ "    \"ADDR1\": null,\r\n"
				+ "    \"ADDR2\": null,\r\n"
				+ "    \"CITY\": null,\r\n"
				+ "    \"STATE\": null,\r\n"
				+ "    \"ZIP\": null,\r\n"
				+ "    \"AP\": null\r\n"
				+ "  },\r\n"
				+ "  \"flow\": \"loginless\"\r\n"
				+ "}";
		return appmntTypesByRule;
	}
	
	public String  appointmentTypesByrulePayload_New(String fn, String ln, String email,String phone, String zip)
	{
		String  appmntTypesByRule="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"startDateTime\": \"10/10/2021 08:45:00\",\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"traversal\": false,\r\n"
				+ "  \"patientType\": \"PT_EXISTING\",\r\n"
				+ "  \"extApptId\": null,\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \""+fn+"\",\r\n"
				+ "    \"LN\": \""+ln+"\",\r\n"
				+ "    \"DOB\": \"01/01/2000\",\r\n"
				+ "    \"GENDER\": \"M\",\r\n"
				+ "    \"EMAIL\": \""+email+"\",\r\n"
				+ "    \"PHONE\": \""+phone+"\",\r\n"
				+ "    \"INSID\": null,\r\n"
				+ "    \"ADDR1\": null,\r\n"
				+ "    \"ADDR2\": null,\r\n"
				+ "    \"CITY\": null,\r\n"
				+ "    \"STATE\": null,\r\n"
				+ "    \"ZIP\": \""+zip+"\",\r\n"
				+ "    \"AP\": null\r\n"
				+ "  },\r\n"
				+ "  \"flow\": \"loginless\"\r\n"
				+ "}";
		return appmntTypesByRule;
	}
	public String  cancelStatusPayload(String extapp, String catid, String apptid, String numberofdays, String preventscheduling )
	{
		String  cancelstatus="{\r\n"
				+ "  \"categoryId\": \""+catid+"\",\r\n"
				+ "  \"extAppointmentTypeId\": \""+extapp+"\",\r\n"
				+ "  \"numberOfDays\": "+numberofdays+",\r\n"
				+ "  \"preventScheduling\": "+preventscheduling+",\r\n"
				+ "  \"id\": "+apptid+",\r\n"
				+ "  \"extApptId\": null\r\n"
				+ "}";
		return cancelstatus;
	}
	
	public String  getDetailsPayload(String bookid)
	{
		String  payload="{\r\n"
				+ "  \"bookId\": "+bookid+"\r\n"
				+ "}";
		return payload;
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

	public String getOtpDetails(String firstNameValue,String lastNameValue,String DOB,String gender,String email,String phone) {
		String createToken ="{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"value\": \""+firstNameValue+"\",\r\n"
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
				+ "            \"value\": \""+lastNameValue+"\",\r\n"
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
				+ "            \"value\": \""+DOB+"\",\r\n"
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
				+ "            \"code\": \""+gender+"\",\r\n"
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
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"value\": \""+email+"\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"search\": true,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Email is required and format should be john@doe.com\",\r\n"
				+ "            \"regex\": \"\\\\w+([-+.]\\\\w+)*@\\\\w+([-.]\\\\w+)*\\\\.\\\\w+([-.]\\\\w+)*$\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 5\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"entity\": \"Preferred Phone Number\",\r\n"
				+ "            \"isMandatory\": true,\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"value\": \""+phone+"\",\r\n"
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
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"isMandatory\": false,\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"value\": null,\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"search\": false,\r\n"
				+ "            \"error\": \"\",\r\n"
				+ "            \"message\": \"Postal code is required eg(12345 or 12345-1234)\",\r\n"
				+ "            \"regex\": \"^\\\\d{5}(?:[-\\\\s]\\\\d{4})?$\",\r\n"
				+ "            \"type\": \"TEXT\",\r\n"
				+ "            \"key\": null,\r\n"
				+ "            \"seq\": 8\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"type\": \"ANONYMOUS\",\r\n"
				+ "    \"allowDuplicatePatient\": true,\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"additionalProperties\": {\r\n"
				+ "        \"flowType\": \"anonymous\"\r\n"
				+ "    }\r\n"
				+ "}";
		return createToken;
	}

	public String locationsBasedOnZipcodeAndRadiusPayload(String appointmentId,String zipCode) {
		String createToken ="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"location\": null,\r\n"
				+ "    \"appointmentType\":\""+appointmentId+"\",\r\n"
				+ "    \"zipcode\": \""+zipCode+"\",\r\n"
				+ "    \"radius\": \"25\"\r\n"
				+ "}";
		return createToken;
	}
}
