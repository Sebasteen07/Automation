package com.medfusion.product.appt.precheck.payload;

public class MfAppointmentSchedulerPayload {
	
	private static MfAppointmentSchedulerPayload payload = new MfAppointmentSchedulerPayload();
	private MfAppointmentSchedulerPayload() {
		
	}
	public static MfAppointmentSchedulerPayload getMfAppointmentSchedulerPayload() {
		return payload;
	}
	
	public String putAppointmentPayload(long time, String email) {
		String Apptdata=" {\r\n"
				+ "\"practiceData\": {\r\n"
				+ "\"pmSystemId\": \"9\",\r\n"
				+ "\"practiceName\": \"PSS-GE-DEV5\",\r\n"
				+ "\"providerName\": \"Brown, Jennifer\",\r\n"
				+ "\"nationalProviderId\": null,\r\n"
				+ "\"locationId\": \"3\",\r\n"
				+ "\"locationName\": \"River Oaks Main\",\r\n"
				+ "\"address\": \"3790 W. First Station : add line 1\",\r\n"
				+ "\"address2\": \"marine lines plaza : add line 2\",\r\n"
				+ "\"city\": \"Dallas\",\r\n"
				+ "\"state\": \"GA\",\r\n"
				+ "\"zip\": \"00000\",\r\n"
				+ "\"phone\": 4344344433,\r\n"
				+ "\"timezoneId\": \"America/Chicago\"\r\n"
				+ "},\r\n"
				+ "\"patientIdData\": {\r\n"
				+ "\"pmInternalId\": \"string\"\r\n"
				+ "},\r\n"
				+ "\"patientData\": {\r\n"
				+ "\"firstName\": \"AppScheduler\",\r\n"
				+ "\"middleName\": null,\r\n"
				+ "\"lastName\": \"One\",\r\n"
				+ "\"dob\": \"1991-01-01\",\r\n"
				+ "\"address\": null,\r\n"
				+ "\"address2\": null,\r\n"
				+ "\"city\": null,\r\n"
				+ "\"state\": null,\r\n"
				+ "\"zip\": \"12345\",\r\n"
				+ "\"phone\": 5087437423,\r\n"
				+ "\"email\": \"" + email + "\",\r\n"
				+ "\"language\": \"en\",\r\n"
				+ "\"primaryInsurance\": {\r\n"
				+ "\"name\": \"Blue Sheild insurance\",\r\n"
				+ "\"groupName\": \"BLSE SE\",\r\n"
				+ "\"groupNumber\": \"BL1998989\",\r\n"
				+ "\"memberName\": \"GEprod test1\",\r\n"
				+ "\"memberId\": \"BL7878\",\r\n"
				+ "\"dateIssued\": \"2002-01-01\",\r\n"
				+ "\"claimsPhoneNumber\": 9876543210,\r\n"
				+ "\"coPayment\": 30\r\n"
				+ "}\r\n"
				+ ",\r\n"
				+ "\"balance\": {\r\n"
				+ "\"amount\": 15,\r\n"
				+ "\"dueBy\": \"2021-04-05\"\r\n"
				+ "}\r\n"
				+ "},\r\n"
				+ "\"appointmentData\": {\r\n"
				+ "\"type\": \"Scot appt type\",\r\n"
				+ "\"status\": \"NEW\",\r\n"
				+ "\"time\": " + time + "\r\n"
				+ "},\r\n"
				+ "\"managementData\": {\r\n"
				+ "\"resourceLink\": \"https://www.medfusion.com/\"\r\n"
				+ "}\r\n"
				+ "}";
		return Apptdata;
	}

	public String putAppointmentPastTimePayload() {
		String pastApt=" {\r\n"
				+ "\"practiceData\": {\r\n"
				+ "\"pmSystemId\": \"9\",\r\n"
				+ "\"practiceName\": \"PSS-GE-DEV5\",\r\n"
				+ "\"providerName\": \"Brown, Jennifer\",\r\n"
				+ "\"nationalProviderId\": null,\r\n"
				+ "\"locationId\": \"3\",\r\n"
				+ "\"locationName\": \"River Oaks Main\",\r\n"
				+ "\"address\": \"3790 W. First Station : add line 1\",\r\n"
				+ "\"address2\": \"marine lines plaza : add line 2\",\r\n"
				+ "\"city\": \"Dallas\",\r\n"
				+ "\"state\": \"GA\",\r\n"
				+ "\"zip\": \"00000\",\r\n"
				+ "\"phone\": 4344344433,\r\n"
				+ "\"timezoneId\": \"America/Chicago\"\r\n"
				+ "},\r\n"
				+ "\"patientIdData\": {\r\n"
				+ "\"pmInternalId\": \"string\"\r\n"
				+ "},\r\n"
				+ "\"patientData\": {\r\n"
				+ "\"firstName\": \"AppScheduler\",\r\n"
				+ "\"middleName\": null,\r\n"
				+ "\"lastName\": \"One\",\r\n"
				+ "\"dob\": \"1991-01-01\",\r\n"
				+ "\"address\": null,\r\n"
				+ "\"address2\": null,\r\n"
				+ "\"city\": null,\r\n"
				+ "\"state\": null,\r\n"
				+ "\"zip\": \"12345\",\r\n"
				+ "\"phone\": 5087437423,\r\n"
				+ "\"email\": \"testpatient.crossasyst@gmail.com\",\r\n"
				+ "\"language\": \"en\",\r\n"
				+ "\"primaryInsurance\": {\r\n"
				+ "\"name\": \"Blue Sheild insurance\",\r\n"
				+ "\"groupName\": \"BLSE SE\",\r\n"
				+ "\"groupNumber\": \"BL1998989\",\r\n"
				+ "\"memberName\": \"GEprod test1\",\r\n"
				+ "\"memberId\": \"BL7878\",\r\n"
				+ "\"dateIssued\": \"2002-01-01\",\r\n"
				+ "\"claimsPhoneNumber\": 9876543210,\r\n"
				+ "\"coPayment\": 30\r\n"
				+ "}\r\n"
				+ ",\r\n"
				+ "\"balance\": {\r\n"
				+ "\"amount\": 15,\r\n"
				+ "\"dueBy\": \"2021-04-05\"\r\n"
				+ "}\r\n"
				+ "},\r\n"
				+ "\"appointmentData\": {\r\n"
				+ "\"type\": \"Scot appt type\",\r\n"
				+ "\"status\": \"NEW\",\r\n"
				+ "\"time\": 1628168185000\r\n"
				+ "},\r\n"
				+ "\"managementData\": {\r\n"
				+ "\"resourceLink\": \"https://www.medfusion.com/\"\r\n"
				+ "}\r\n"
				+ "}";
		return pastApt;
	}

}
