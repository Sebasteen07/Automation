package com.medfusion.product.appt.precheck.payload;

public class AptPrecheckNegativeCasesPayload {
	
	private static AptPrecheckNegativeCasesPayload payload = new AptPrecheckNegativeCasesPayload();
	private AptPrecheckNegativeCasesPayload() {
		
	}
	public static AptPrecheckNegativeCasesPayload getAptPrecheckPayload() {
		return payload;
	}
	
	public String getFormsPayload() {
		String checkinActions=" {\r\n"
				+ "      \"status\": \"INCOMPLETE\",\r\n"
				+ "      \"title\": \"NEW\",\r\n"
				+ "      \"url\": \"www.gmail.com\"\r\n"
				+ "    }";
		return checkinActions;
		
}
	public String getInsuranceStatusBlankPayload() {
		String insurance=" {\r\n"
				+ "  \"insuranceList\": [\r\n"
				+ "    {\r\n"
				+ "      \"details\": {\r\n"
				+ "        \"groupName\": \"ABCD22\",\r\n"
				+ "        \"groupNumber\": \"ABCD23\",\r\n"
				+ "        \"insuranceName\": \"HEALTH\",\r\n"
				+ "        \"memberId\": \"12334\",\r\n"
				+ "        \"memberName\": \"GEprod test1\"\r\n"
				+ "      },\r\n"
				+ "      \"editStatus\": \"CONFIRMED\",\r\n"
				+ "      \"imageInfo\": {\r\n"
				+ "        \"image\": {\r\n"
				+ "          \"cardBack\": \"DATA\",\r\n"
				+ "          \"cardFront\": \"FILE\"\r\n"
				+ "        },\r\n"
				+ "        \"imageFileName\": \"string\"\r\n"
				+ "      },\r\n"
				+ "      \"tier\": \"PRIMARY\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"\"\r\n"
				+ "}";
		return insurance;	
}
	public String getInsuranceEditstatusIncorrectPayload() {
		String insurance=" {\r\n"
				+ "  \"insuranceList\": [\r\n"
				+ "    {\r\n"
				+ "      \"details\": {\r\n"
				+ "        \"groupName\": \"ABCD22\",\r\n"
				+ "        \"groupNumber\": \"ABCD23\",\r\n"
				+ "        \"insuranceName\": \"HEALTH\",\r\n"
				+ "        \"memberId\": \"12334\",\r\n"
				+ "        \"memberName\": \"GEprod test1\"\r\n"
				+ "      },\r\n"
				+ "      \"editStatus\": \"CORMED\",\r\n"
				+ "      \"imageInfo\": {\r\n"
				+ "        \"image\": {\r\n"
				+ "          \"cardBack\": \"DATA\",\r\n"
				+ "          \"cardFront\": \"FILE\"\r\n"
				+ "        },\r\n"
				+ "        \"imageFileName\": \"string\"\r\n"
				+ "      },\r\n"
				+ "      \"tier\": \"PRIMARY\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"COMPLETE\"\r\n"
				+ "}";
		return insurance;	
}

	public String getInsuranceIncorrectTierPayload() {
		String insurance=" {\r\n"
				+ "  \"insuranceList\": [\r\n"
				+ "    {\r\n"
				+ "      \"details\": {\r\n"
				+ "        \"groupName\": \"ABCD22\",\r\n"
				+ "        \"groupNumber\": \"ABCD23\",\r\n"
				+ "        \"insuranceName\": \"HEALTH\",\r\n"
				+ "        \"memberId\": \"12334\",\r\n"
				+ "        \"memberName\": \"GEprod test1\"\r\n"
				+ "      },\r\n"
				+ "      \"editStatus\": \"CONFIRMED\",\r\n"
				+ "      \"imageInfo\": {\r\n"
				+ "        \"image\": {\r\n"
				+ "          \"cardBack\": \"DATA\",\r\n"
				+ "          \"cardFront\": \"FILE\"\r\n"
				+ "        },\r\n"
				+ "        \"imageFileName\": \"string\"\r\n"
				+ "      },\r\n"
				+ "      \"tier\": \"PRARY\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"COMPLETE\"\r\n"
				+ "}";
		return insurance;	
}
	
	public String getDemographicsPayload() {
		String demographics=" {\r\n"
				+ "	\"address\": null,\r\n"
				+ "	\"address2\": null,\r\n"
				+ "	\"birthDate\": \"1991-01-01\",\r\n"
				+ "	\"city\": null,\r\n"
				+ "	\"email\": \"testpatient.crossasyst@gmail.com\",\r\n"
				+ "	\"firstName\": \"PrecheckThree\",\r\n"
				+ "	\"language\": \"en\",\r\n"
				+ "	\"lastName\": \"Test\",\r\n"
				+ "	\"middleName\": null,\r\n"
				+ "	\"pharmacies\": [{\r\n"
				+ "		\"address\": {\r\n"
				+ "			\"city\": \"string\",\r\n"
				+ "			\"line1\": \"string\",\r\n"
				+ "			\"line2\": \"string\",\r\n"
				+ "			\"state\": \"string\",\r\n"
				+ "			\"zipCode\": \"string\"\r\n"
				+ "		},\r\n"
				+ "		\"deleted\": true,\r\n"
				+ "		\"id\": \"string\",\r\n"
				+ "		\"name\": \"string\",\r\n"
				+ "		\"number\": 0\r\n"
				+ "	}],\r\n"
				+ "	\"phone\": 5087437423,\r\n"
				+ "\r\n"
				+ "	\"phoneType\": null,\r\n"
				+ "	\"state\": null,\r\n"
				+ "	\"status\": \"COMPLETE\",\r\n"
				+ "	\"verified\": true,\r\n"
				+ "	\"zip\": \"12345\"\r\n"
				+ "}";
		return demographics;
}
	
	public String getCopayPayPayload(String PatientId) {
		String copayPay=" {\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"patientName\": \"EventID Test\",\r\n"
				+ "  \"patientDob\": \"1991-01-01\",\r\n"
				+ "  \"patientEmail\": \"testpatient.crossasyst@gmail.com\",\r\n"
				+ "  \"creditCardName\": \"Health\",\r\n"
				+ "  \"creditCardNumber\": \"5425233430109903\",\r\n"
				+ "  \"creditCardType\": \"VISA\",\r\n"
				+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
				+ "  \"creditCardCvvCode\": \"333\",\r\n"
				+ "  \"creditCardZip\": \"12345\",\r\n"
				+ "  \"amount\": 0\r\n"
				+ "}";
		return copayPay;
}
	
	public String getBalancePayPayload(String PatientId) {
		String balancePay=" {\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"patientName\": \"EventID Test\",\r\n"
				+ "  \"patientDob\": \"1991-01-01\",\r\n"
				+ "  \"patientEmail\": \"testpatient.crossasyst@gmail.com\",\r\n"
				+ "  \"creditCardName\": \"Health\",\r\n"
				+ "  \"creditCardNumber\": \"5425233430109903\",\r\n"
				+ "  \"creditCardType\": \"VISA\",\r\n"
				+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
				+ "  \"creditCardCvvCode\": \"333\",\r\n"
				+ "  \"creditCardZip\": \"12345\",\r\n"
				+ "  \"amount\": 0\r\n"
				+ "}";
		return balancePay;
		
}
	
	public String postAppointmentsIncorrectPracticeIdPayload(String IntegrationId) {
		String postAppointment=" {\r\n"
				+ "	\"status\": \"NEW\",\r\n"
				+ "	\"time\": 1628426017000,\r\n"
				+ "	\"locationName\": \"River Oak Main\",\r\n"
				+ "	\"providerName\": \"Test Provider\",\r\n"
				+ "	\"timezoneId\": \"Asia/Calcutta\",\r\n"
				+ "	\"practiceDemographics\": {\r\n"
				+ "		\"pmExternalId\": \"12344\",\r\n"
				+ "		\"firstName\": \"AppPatient\",\r\n"
				+ "		\"lastName\": \"Test\",\r\n"
				+ "		\"dob\": \"1991-01-01\",\r\n"
				+ "		\"address\": \"saas1233\",\r\n"
				+ "		\"city\": \"LA\",\r\n"
				+ "		\"state\": \"LA\",\r\n"
				+ "		\"zip\": \"12345\",\r\n"
				+ "		\"phone\": 5087437423,\r\n"
				+ "		\"email\": \"testpatient.crossasyst@gmail.com\"\r\n"
				+ "	},\r\n"
				+ "	\"insurance\": {\r\n"
				+ "		\"primaryInsuranceInfo\": {\r\n"
				+ "			\"name\": \"Blueshield Insurance\",\r\n"
				+ "			\"groupNumber\": \"BLK12345\"\r\n"
				+ "		},\r\n"
				+ "		\"status\": \"INCOMPLETE\"\r\n"
				+ "	},\r\n"
				+ "	\"coPayment\": {\r\n"
				+ "		\"status\": \"INCOMPLETE\",\r\n"
				+ "		\"amount\": \"30\"\r\n"
				+ "	},\r\n"
				+ "	\"balance\": {\r\n"
				+ "		\"status\": \"INCOMPLETE\",\r\n"
				+ "		\"amount\": \"10\"\r\n"
				+ "	},\r\n"
				
				+ "  \"integrationId\": \""+IntegrationId+"\",\r\n"
				+ "	\"type\": \"Fever n Cold\"\r\n"
				+ "}";
		return postAppointment;
}
	
	public String getPracticeIdIncorrectPayload(String appDateRangeStart, String appDateRangeEnd) {
		String PracticeId=" {\r\n"
				+ "    \"applyFilters\": true,\r\n"
				+ "    \"applyStatusMappings\": true,\r\n"
				+ "    \"pageSize\": 50,\r\n"
				+ "    \"pageNumber\": 1,\r\n"
				+ "  \"appointmentDateRangeStart\": \""+appDateRangeStart+"\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+appDateRangeEnd+"\"\r\n"
				+ "}";
		return PracticeId;
	}
	
	public String getPracticeIdIncorrecDatatPayload(String appDateRangeStart, String appDateRangeEnd) {
		String PracticeId=" {\r\n"
				+ "    \"applyFilters\": true,\r\n"
				+ "    \"applyStatusMappings\": true,\r\n"
				+ "    \"pageSize\": 50,\r\n"
				+ "    \"pageNumber\": --1,\r\n"
				+ "  \"appointmentDateRangeStart\": \""+appDateRangeStart+"\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+appDateRangeEnd+"\"\r\n"
				+ "}";
		return PracticeId;
	}
	
	public String getHistoryMessageIncorrect() {
		String historymessage=" {\r\n"
				+ "	\"latitude\": ,\r\n"
				+ "	\"longitude\": 0,\r\n"
				+ "	\"timezoneId\": \"America/New_York\",\r\n"
				+ "	\"timezoneName\": null\r\n"
				+ "}";
		return historymessage;
}
	public String getMessageHistoryPayload() {
		String historymessage=" {\r\n"
				+ "	\"timezoneId\": \"America/New_York\",\r\n"
				+ "	\"timezoneName\": null\r\n"
				+ "}";
		return historymessage;
}
}