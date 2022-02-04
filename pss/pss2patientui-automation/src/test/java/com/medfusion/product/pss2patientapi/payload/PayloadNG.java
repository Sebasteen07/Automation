// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadNG {
	
	String scheduleApptPatient="{\"slotId\":\"0001\",\"specialty\":null,\"book\":205665,\"appointmentType\":205755,\"location\":205605,\"startDateTime\":\"03/31/2021 02:00:00\",\"customQuestion\":null,\"insuranceInfo\":null,\"patientInfo\":{},\"allowDuplicatePatient\":false,\"updatePatientDetails\":{\"FN\":\"rt\",\"LN\":\"rt\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":\"Shweta.Sontakke@CrossAsyst.com\",\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null},\"flow\":\"loginless\",\"appTypeDetail\":null,\"rule\":\"T,L,B\",\"leafNode\":null}";
	String apptbody="{\"specialty\":null,\"location\":null,\"book\":null,\"appointmentType\":null,\"slotId\":null,\"patientType\":\"PT_EXISTING\",\"linkGenerationFlow\":false,\"patientDetails\":{\"FN\":\"rt\",\"LN\":\"rt\",\"DOB\":\"01/01/2000\",\"GENDER\":\"M\",\"EMAIL\":\"Shweta.Sontakke@CrossAsyst.com\",\"PHONE\":null,\"INSID\":null,\"ADDR1\":null,\"ADDR2\":null,\"CITY\":null,\"STATE\":null,\"ZIP\":null},\"flow\":\"loginless\"}";
	String booklist = "{\r\n" + "    \"specialty\": null,\r\n" + "    \"location\": null,\r\n"
			+ "    \"book\": null,\r\n" + "    \"appointmentType\": null,\r\n" + "    \"slotId\": null,\r\n"
			+ "    \"patientType\": \"PT_NEW\",\r\n" + "    \"linkGenerationFlow\": false,\r\n"
			+ "    \"patientDetails\": {\r\n" + "        \"FN\": \"api\",\r\n" + "        \"LN\": \"api\",\r\n"
			+ "        \"DOB\": \"01/01/2000\",\r\n" + "        \"GENDER\": \"M\",\r\n" + "        \"EMAIL\": null,\r\n"
			+ "        \"PHONE\": null,\r\n" + "        \"INSID\": null,\r\n" + "        \"ADDR1\": null,\r\n"
			+ "        \"ADDR2\": null,\r\n" + "        \"CITY\": null,\r\n" + "        \"STATE\": null,\r\n"
			+ "        \"ZIP\": null,\r\n" + "        \"PHONE1\": null\r\n" + "    },\r\n"
			+ "    \"flow\": \"loginless\"\r\n" + "}";
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
	public String cancelAppointment = "{\r\n" +
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
	

	public static String past_appt_payload(String patientId, String practiceDisplayName, String practiceId, String endate) {
		
		String past_appt="{\r\n"
				+ "  \"additionalFields\": {},\r\n"
				+ "  \"endDate\": \""+endate+"\",\r\n"
				+ "  \"patientId\": \""+patientId+"\",\r\n"
				+ "  \"practiceProvision\": {\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"practiceDisplayName\": \""+practiceDisplayName+"\",\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"practiceName\": \""+practiceDisplayName+"\",\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\"\r\n"
				+ "  },\r\n"

				+ "  \"startDate\": \"07/08/2021\"\r\n"
				+ "}\r\n"
				+ "";
		
		return past_appt;
	}

	public static String nextAvailable_Payload(String patientId, String startdate,  String enddate) {
		
		String nextAvailable="{\r\n"
				+ "    \"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "    \"appointmentCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "    \"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "    \"resourceCategoryId\": \"C8E55131-F1D8-401D-A107-F90222DBD977\",\r\n"
				+ "    \"resourceId\": \"F49641D6-CDF1-4264-B5A5-7489F7E58F8D\",\r\n"
				+ "    \"startDate\": \""+startdate+"\",\r\n"
				+ "    \"endDate\": \""+enddate+"\",\r\n"				
				+ "    \"slotSize\": \"15\",\r\n"
				+ "    \"patientId\": null,\r\n"
				+ "    \"reservedForSameDay\": false,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"nextAvailability\": false,\r\n"
				+ "    \"stackingFlag\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"sameDayAppointment\": false,\r\n"
				+ "    \"contiguous\": false,\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"leadTime\": 0,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"reservedForSameDate\": \"n\",\r\n"
				+ "    \"appointmentTypeDBId\": \"205755\",\r\n"
				+ "    \"locationDBId\": \"205605\",\r\n"
				+ "    \"providerDBId\": \"205664\",\r\n"
				+ "    \"practiceTimezone\": \"America/New_York\",\r\n"
				+ "    \"nextAvailable\": true\r\n"
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
	
	public static String reschedule_Payload(String startDateTime, String endDateTime, String patientid, String firstName, String lastName, String apptid) {
		
		String reschedule="{\r\n"
				+ "\"locationId\":\"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "\"appointmentCategoryId\":\"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "\"appointmentTypeId\":\"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "\"duration\":0,\r\n"
				+ "\"comments\":\"CustomQuestion:hello~(pss) 231365 02/23/2021 09:33:19~\",\r\n"
				+ "\"startDateTime\":\""+startDateTime+"\",\r\n"
				+ "\"endDateTime\":\""+endDateTime+"\",\r\n"
				+ "\"patientId\":\""+patientid+"\",\r\n"
				+ "\"resourceCategoryId\":\"10F8A2F7-4291-4855-BBFA-87AB3E3A6C60\",\r\n"
				+ "\"resourceId\":\"6A6269EE-E9F3-4880-B36F-C90E51EB33CF\",\r\n"
				+ "\"slotId\":\"0001\",\r\n"
				+ "\"stackingFlag\":false,\r\n"
				+ "\"schedulingDuration\":0,\r\n"
				+ "\"additionalProperties\":{\r\n"
				+ "\"FN\":\""+firstName+"\",\r\n"
				+ "\"LN\":\""+lastName+"\"\r\n"
				+ "},\r\n"
				+ "\"notesProperties\":{\r\n"
				+ "\"apptIndicatorWithConfirmationNo\":\"(pss) 231365 03/23/2021 09:33:19\",\r\n"
				+ "\"customQuestion\":\"hello\"\r\n"
				+ "},\r\n"
				+ "\"existingAppointment\":{\r\n"
				+ "\"duration\":0,\r\n"
				+ "\"stackingFlag\":false,\r\n"
				+ "\"schedulingDuration\":0,\r\n"
				+ "\"appointmentId\":\""+apptid+"\"\r\n"
				+ "},\r\n"
				+ "\"rescheduleReason\":{\r\n"
				+ "\"name\":\"otherCancelReason\"\r\n"
				+ "},\r\n"
				+ "\"practiceTimezone\":\"America/New_York\"\r\n"
				+ "}";
		
		return reschedule;
	}
	
	public static String schedule_Payload(String slotStartTime, String slotEndTime) {
		
		String schedule="{\r\n"
				+ "  \"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "  \"appointmentCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "  \"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "  \"duration\": 0,\r\n"
				+ "  \"comments\": \"~(pss) 231701 03/12/2021 04:34:27~\",\r\n"
				+ "  \"startDateTime\": \""+slotStartTime+"\",\r\n"
				+ "  \"endDateTime\": \""+slotEndTime+"\",\r\n"
				+ "  \"patientId\": \"50245\",\r\n"
				+ "  \"resourceCategoryId\": \"C8E55131-F1D8-401D-A107-F90222DBD977\",\r\n"
				+ "  \"resourceId\": \"F49641D6-CDF1-4264-B5A5-7489F7E58F8D\",\r\n"
				+ "  \"slotId\": \"0001\",\r\n"
				+ "  \"stackingFlag\": false,\r\n"
				+ "  \"schedulingDuration\": 0,\r\n"
				+ "  \"additionalProperties\": {\r\n"
				+ "    \"FN\": \"mai\",\r\n"
				+ "    \"LN\": \"mai\",\r\n"
				+ "    \"DOB\": \"01/01/2001\",\r\n"
				+ "    \"GENDER\": \"F\",\r\n"
				+ "    \"EMAIL\": \"Shweta.Sontakke@crossasyst.com\",\r\n"
				+ "    \"PHONE\": \"961-992-1668\",\r\n"
				+ "    \"ZIP\": \"12345\"\r\n"
				+ "  },\r\n"
				+ "  \"notesProperties\": {\r\n"
				+ "    \"apptIndicatorWithConfirmationNo\": \"(pss) 231701 03/12/2021 04:34:27\"\r\n"
				+ "  },\r\n"
				+ "  \"practiceTimezone\": \"America/New_York\"\r\n"
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
	
		
	public static String addPatient() {
		String addPatient="{\r\n"
				+ "  \"firstName\": \"Rai01\",\r\n"
				+ "  \"lastName\": \"Jeera01\",\r\n"
				+ "  \"dateOfBirth\": \"01/01/2001\",\r\n"
				+ "  \"gender\": \"F\",\r\n"
				+ "  \"emailAddress\": \"Shweta.Sontakke@crossasyst.com\",\r\n"
				+ "  \"phoneNumber\": \"7972330364\",\r\n"
				+ "  \"address\": {\r\n"
				+ "    \"zipCode\": \"12345\"\r\n"
				+ "  },\r\n"
				+ "  \"additionFields\": {},\r\n"
				+ "  \"slotId\": \"0001\"\r\n"
				+ "}";
		return addPatient;
	}
		
		public String matchpatient = "{\r\n"
				+ "\"patientMatches\": [\r\n"
				+ " {\r\n"
				+ "\"entity\": \"First Name\",\r\n"
				+ "\"isMandatory\": true,\r\n"
				+ "\"code\": \"FN\",\r\n"
				+ "\"value\": \"mithila\",\r\n"
				+ "\"selected\": true,\r\n"
				+ "\"search\": true\r\n"
				+ " },\r\n"
				+ " {\r\n"
				+ "\"entity\": \"Gender\",\r\n"
				+ "\"isMandatory\": true,\r\n"
				+ "\"code\": \"GENDER\",\r\n"
				+ "\"value\": \"F\",\r\n"
				+ "\"selected\": true,\r\n"
				+ "\"search\": true\r\n"
				+ " },\r\n"
				+ " \r\n"
				+ " \r\n"
				+ " {\r\n"
				+ "\"entity\": \"Date Of Birth\",\r\n"
				+ "\"isMandatory\": true,\r\n"
				+ "\"code\": \"DOB\",\r\n"
				+ "\"value\": \"01/01/2000\",\r\n"
				+ "\"selected\": true,\r\n"
				+ "\"search\": true\r\n"
				+ " },\r\n"
				+ " \r\n"
				+ " {\r\n"
				+ "\"entity\": \"Last Name\",\r\n"
				+ "\"isMandatory\": true,\r\n"
				+ "\"code\": \"LN\",\r\n"
				+ "\"value\": \"shirodkar\",\r\n"
				+ "\"selected\": true,\r\n"
				+ "\"search\": true\r\n"
				+ " }\r\n"
				+ " \r\n"
				+ " ],\r\n"
				+ "\"maxCriteria\":\"4\",\r\n"
				+ "\"allowDuplicatePatient\":false\r\n"
				+ "}";
		
		String patientrecordbyapptypes="{\r\n"
				+ "  \"additionalProperties\": {},\r\n"
				+ "  \"appointmentTypes\": [\r\n"
				+ "    {\r\n"
				+ "      \"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "      \"patientRecord\": true\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		
		public String searchpatient = "{\r\n"
				+ "  \"id\": \"50056\",\r\n"
				+ "  \"firstName\": \"march\",\r\n"
				+ "  \"lastName\": \"march\",\r\n"
				+ "  \"dateOfBirth\": \"01/01/2000\",\r\n"
				+ "  \"emailAddress\": \"ttestttest49@gmail.com\",\r\n"
				+ "  \"gender\": \"M\",\r\n"
				+ "  \"phoneNumber\": \"4041111111\",\r\n"
				+ "  \"maritalStatus\": null,\r\n"
				+ "  \"phoneNumber2\": null,\r\n"
				+ "  \"alertNotes\": null,\r\n"
				+ "  \"address\": {\r\n"
				+ "    \"state\": \"   \"\r\n"
				+ "  },\r\n"
				+ "  \"status\": \"Bad Debt\",\r\n"
				+ "  \"primaryCareProvider\": \"78e497ef-0c8f-4d59-ad40-fe7be12b9842\",\r\n"
				+ "  \"alert\": null,\r\n"
				+ "  \"patientAlertNotes\": [],\r\n"
				+ "  \"billingNotes\": [],\r\n"
				+ "  \"lockoutMsg\": null,\r\n"
				+ "  \"lockOut\": false,\r\n"
				+ "  \"patientCreationDate\": \"02/18/2021\",\r\n"
				+ "  \"responsibleProvider\": null,\r\n"
				+ "  \"phoneMapProperties\": {\r\n"
				+ "    \"DP\": \"4041111111\"\r\n"
				+ "  },\r\n"
				+ "  \"insuranceId\": null,\r\n"
				+ "  \"preferredLanguage\": null,\r\n"
				+ "  \"noPatientRecord\": false,\r\n"
				+ "  \"patient\": null,\r\n"
				+ "  \"externalId\": null,\r\n"
				+ "  \"statusMessage\": null,\r\n"
				+ "  \"altertNoteMessage\": null,\r\n"
				+ "  \"visitFlag\": true,\r\n"
				+ "  \"message\": null,\r\n"
				+ "  \"token\": null,\r\n"
				+ "  \"created\": null,\r\n"
				+ "  \"newPatientCreated\": false,\r\n"
				+ "  \"authId\": null,\r\n"
				+ "  \"logout\": false,\r\n"
				+ "  \"existingPatient\": true,\r\n"
				+ "  \"showAlert\": false,\r\n"
				+ "  \"alertMesage\": null,\r\n"
				+ "  \"extraParams\": null,\r\n"
				+ "  \"practiceName\": null,\r\n"
				+ "  \"patientType\": null,\r\n"
				+ "  \"otpPatientDetails\": null,\r\n"
				+ "  \"flagList\": null,\r\n"
				+ "  \"isPatient\": null,\r\n"
				+ "  \"MRN\": null\r\n"
				+ "}";
		
		String prerequisitesappttype="{\r\n"
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

		public String lastseenprovider = "{\r\n"
				+ "  \"additionalField\": null,\r\n"
				+ "  \"appointmentCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "  \"appointmentTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "  \"apptTypeAllocated\": false,\r\n"
				+ "  \"duration\":null,\r\n"
				+ "  \"locationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "  \"noOfDays\": null,\r\n"
				+ "  \"patientId\": \"50056\",\r\n"
				+ "  \"resourceCategoryId\": \"437DCCDB-8D78-475F-B661-EF393674F1F8\",\r\n"
				+ "  \"resourceId\": \"78E497EF-0C8F-4D59-AD40-FE7BE12B9842\",\r\n"
				+ "  \"slotCount\": 0\r\n"
				+ "}";
}
