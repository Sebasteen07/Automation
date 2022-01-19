// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadPM02 {
	
	public String avaliableSlot(String currentDate)
	{
	String payload="{\r\n"
	+ " \"specialty\": null,\r\n"
	+ " \"location\": 206321,\r\n"
	+ " \"book\": null,\r\n"
	+ " \"appointmentType\": 206528,\r\n"
	+ " \"startDateTime\": \""+currentDate+"\",\r\n"
	+ " \"slotId\": null,\r\n"
	+ " \"traversal\": false,\r\n"
	+ " \"patientType\": \"PT_NEW\",\r\n"
	+ " \"extApptId\": null\r\n"
	+ "}";
	return payload;

	}
	
	public String  appointmentTypesByrulePayload()
	{
		String  appmntTypesByRule="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": 205604,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"ggg\",\r\n"
				+ "    \"LN\": \"ggg\",\r\n"
				+ "    \"DOB\": \"01/01/2000\",\r\n"
				+ "    \"GENDER\": \"F\",\r\n"
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
		return appmntTypesByRule;
	}
	
	public String allowOnlineCancelPayload(String extapptid) {

		String payload = "{\r\n" + "  \"externalAppointmentId\": \""+extapptid+"\"\r\n" + "}";
		return payload;
	}
	
	public String cancelStatusPyaload() {

		String payload = "{\r\n"
				+ "  \"categoryId\": \"24A7DC4F-4DB6-427C-9FA5-EB76FA2142D0\",\r\n"
				+ "  \"extAppointmentTypeId\": \"E0F61D51-3AFB-412E-AA3C-18786D23821C\",\r\n"
				+ "  \"numberOfDays\": 10,\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"id\": 4235,\r\n"
				+ "  \"extApptId\": null\r\n"
				+ "}";
		return payload;
	}	
	
	
	public String SchedWithDecisionTreePyaload(String date, String slottime, String book ) {

		String payload = "{\r\n"
				+ "  \"patientType\": \"PT_EXISTING\",\r\n"
				+ "  \"slotId\": \"0001\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": "+book+",\r\n"
				+ "  \"appointmentType\": 4235,\r\n"
				+ "  \"location\": 206321,\r\n"
				+ "  \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"updatePatientDetails\": {\r\n"
				+ "    \"FN\": \"kiya\",\r\n"
				+ "    \"LN\": \"kiya\",\r\n"
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
				+ "  \"flow\": \"loginless\",\r\n"
				+ "  \"appTypeDetail\": {\r\n"
				+ "    \"categoryId\": \"208293\",\r\n"
				+ "    \"guid\": \"b31443df-12bc-4571-bec0-9b8f7fa6a4c2\",\r\n"
				+ "    \"appTypeId\": \"4235\"\r\n"
				+ "  },\r\n"
				+ "  \"rule\": \"L,T\",\r\n"
				+ "  \"leafNode\": null\r\n"
				+ "}";
		return payload;
	}	
	
	public String preReqApptTypeRulePyaload() {
		String payload = "{\r\n"
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
		return payload;
	}	
	
	public String bookRuleCareTeamPyaload(String location, String appt, String specialty) {

		String payload = "{\r\n"
				+ "  \"specialty\": "+specialty+",\r\n"
				+ "  \"location\": "+location+",\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": "+appt+",\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"mm\",\r\n"
				+ "    \"LN\": \"mm\",\r\n"
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
		return payload;
	}	

	public String scheduleAppointmentPayload(String slotid, String date, String slottime, String bookid,
			String locationid, String apptid) {
		String scheduleAppointment = "{\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"slotId\": \""+slotid+"\",\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": "+bookid+",\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "  	\"insuranceInfo\": {\r\n"
				+ "    \"insuranceCarrier\": \"insurance\",\r\n"
				+ "    \"memberId\": \"111111\",\r\n"
				+ "    \"groupId\": \"222222\",\r\n"
				+ "    \"phoneNumber\": \"404-112-3123\"\r\n"
				+ " 	 },\r\n"
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

	public String  availableslotsPayloadLT(String date, String locationid, String apptid)
	{
		String  availableSlots="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": "+locationid+",\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": "+apptid+",\r\n"
				+ "    \"startDateTime\": \""+date+"\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"traversal\": false,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"extApptId\": null\r\n"
				+ "}";
		return availableSlots;
	}

}
