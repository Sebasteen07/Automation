// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pssPatientModulatorAPI.test;

public class Payload {
	
	String scheduleApptPatient="{\"slotId\":\"0001\",\"specialty\":null,\"book\":205665,\"appointmentType\":205755,\"location\":205605,\"startDateTime\":\"03/29/2021 02:00:00\",\"customQuestion\":null,\"insuranceInfo\":null,\"patientInfo\":{},\"allowDuplicatePatient\":false,\"updatePatientDetails\":{\"FN\":\"rt\",\"LN\":\"rt\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":\"Shweta.Sontakke@CrossAsyst.com\",\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null},\"flow\":\"loginless\",\"appTypeDetail\":null,\"rule\":\"T,L,B\",\"leafNode\":null}";
	String apptbody="{\"specialty\":null,\"location\":null,\"book\":null,\"appointmentType\":null,\"slotId\":null,\"patientType\":\"PT_EXISTING\",\"linkGenerationFlow\":false,\"patientDetails\":{\"FN\":\"rt\",\"LN\":\"rt\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":\"Shweta.Sontakke@CrossAsyst.com\",\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null},\"flow\":\"loginless\"}";
	String booklist="{\"specialty\":null,\"location\":null,\"book\":null,\"appointmentType\":null,\"slotId\":null,\"patientType\":\"PT_NEW\",\"linkGenerationFlow\":false,\"patientDetails\":{\"FN\":\"api\",\"LN\":\"api\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":null,\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null,\"PHONE1\":null},\"flow\":\"loginless\"}";
	String locationlist="{\"specialty\":null,\"location\":null,\"book\":null,\"appointmentType\":205902,\"slotId\":null,\"patientType\":\"PT_NEW\",\"linkGenerationFlow\":false,\"patientDetails\":{\"FN\":\"api\",\"LN\":\"api\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":null,\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null,\"PHONE1\":null},\"flow\":\"loginless\"}";
	String availableslots="{\"specialty\":null,\"location\":200353,\"book\":205802,\"appointmentType\":205902,\"startDateTime\":\"11/06/2020\",\"slotId\":\"4182387\",\"traversal\":false,\"patientType\":\"PT_NEW\"}";
	String scheduleappt= "{\r\n" + 
			"    \"slotId\": \"4160125\",\r\n" + 
			"    \"specialty\": null,\r\n" + 
			"    \"book\": 204151,\r\n" + 
			"    \"appointmentType\": 204201,\r\n" + 
			"    \"location\": 205400,\r\n" + 
			"    \"startDateTime\": \"10/26/2020 05:30:00\",\r\n" + 
			"    \"customQuestion\": null,\r\n" + 
			"    \"insuranceInfo\": null,\r\n" + 
			"    \"patientInfo\": {},\r\n" + 
			"    \"allowDuplicatePatient\": false,\r\n" + 
			"    \"updatePatientDetails\": {\r\n" + 
			"        \"FN\": \"mrudul\",\r\n" + 
			"        \"LN\": \"shirodkar\",\r\n" + 
			"        \"DOB\": \"01/01/2000\",\r\n" + 
			"        \"GENDER\": \"F\",\r\n" + 
			"        \"EMAIL\": \"nshirodkar.test@gmail.com\",\r\n" + 
			"        \"PHONE\": null,\r\n" + 
			"        \"INSID\": null,\r\n" + 
			"        \"ADDR1\": null,\r\n" + 
			"        \"ADDR2\": null,\r\n" + 
			"        \"CITY\": null,\r\n" + 
			"        \"STATE\": null,\r\n" + 
			"        \"ZIP\": \"27318\"\r\n" + 
			"    },\r\n" + 
			"    \"flow\": \"loginless\",\r\n" + 
			"    \"appTypeDetail\": null,\r\n" + 
			"    \"rule\": \"L,T,B\",\r\n" + 
			"    \"leafNode\": null\r\n" + 
			"}";
	String cancelAppointment="{\r\n" + 
			"  \"appointmentId\": \"59f90f71-f4b9-4752-9810-24792ad5412b\",\r\n" + 
			"  \"cancellationMap\": {\r\n" + 
			"    \"additionalProp1\": \"string\",\r\n" + 
			"    \"additionalProp2\": \"string\",\r\n" + 
			"    \"additionalProp3\": \"string\"\r\n" + 
			"  }\r\n" + 
			"}";
	
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
	
	public static String past_appt_payload(String patientId, String practiceDisplayName, String practiceId) {
		
		String past_appt="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": \"03/03/2021 \",\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \""+practiceDisplayName+"\",\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"practiceName\": \""+practiceDisplayName+"\",\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "  },\r\n"
				+ "  \"startDate\": \"01/01/2021\"\r\n"
				+ "}\r\n"
				+ "";
		
		return past_appt;
	}
	

	public static String nextAvailable_Payload(String patientId) {
		
		String nextAvailable="{\r\n"
				+ "\r\n"
				+ "\"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "\"appointmentCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "\"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "\"startDate\": \"04/03/2021 00:49:32\",\r\n"
				+ "\"slotSize\": \"15\",\r\n"
				+ "\"patientId\": \""+patientId+"\",\r\n"
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
				+ "\"allowSameDayAppts\": true,\r\n"
				+ "\"reservedForSameDate\": \"n\",\r\n"
				+ "\"appointmentTypeDBId\": \"205755\",\r\n"
				+ "\"locationDBId\": \"205605\"\r\n"
				+ "}";
		
		return nextAvailable;
	}
	

}
