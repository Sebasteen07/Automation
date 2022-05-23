package com.medfusion.product.appt.precheck.payload;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.TimeZone;

public class AptPrecheckPayload {
	
	private static AptPrecheckPayload payload = new AptPrecheckPayload();
	private AptPrecheckPayload() {
		
	}
	public static AptPrecheckPayload getAptPrecheckPayload() {
		return payload;
	}
	
	public String getPracticeIdPayload(String appDateRangeStart, String appDateRangeEnd) {
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
	
	public String getArrivalsPayload(String appDateRangeStart, String appDateRangeEnd) {
		String Arrivals=" {\r\n"
				+ "	\"applyFilters\": true,\r\n"
				+ "	\"applyStatusMappings\": true,\r\n"
				+ "	\"pageSize\": 50,\r\n"
				+ "	\"pageNumber\": 1,\r\n"
				+ "  \"appointmentDateRangeStart\": \""+appDateRangeStart+"\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+appDateRangeEnd+"\"\r\n"
				+ "}";
		return Arrivals;
	}
	
	public String getHistoryMessage() {
		String historymessage=" {\r\n"
				+ "	\"latitude\": 0,\r\n"
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
	
	public String getBroadcastMessagePayload(String appDateRangeStart, String appDateRangeEnd, String PatientId, String ApptId) { 
		   LocalDateTime endDate = LocalDateTime.now();  
		String broadcastmsg=" {\r\n"
				+ "	\"broadcastMessage\": {\r\n"
				+ "		\"en\": \"Test\",\r\n"
				+ "		\"es\": \"Test\"\r\n"
				+ "	},\r\n"
				+ "	\"selectedAll\": false,\r\n"
				+ "	\"pagingParameters\": {\r\n"
				+ "		\"applyFilters\": true,\r\n"
				+ "		\"applyStatusMappings\": true,\r\n"
				+ "		\"pageSize\": 50,\r\n"
				+ "		\"pageNumber\": 0,\r\n"
				+ "  \"appointmentDateRangeStart\": \""+appDateRangeStart+"\",\r\n"
				+ "  \"appointmentDateRangeEnd\": \""+endDate+"\"\r\n"
				+ "	},\r\n"
				+ "	\"totalAppts\": 1,\r\n"
				+ "	\"patientAndAppointments\": [{\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"pmAppointmentId\": \""+ApptId+"\"\r\n"
				+ "	}]\r\n"
				+ "}";
		return broadcastmsg;
	}
	
	public String getCheckinActionsPayload(String ApptId, String PatientId, String PracticeId) {
		String checkinActions=" [{\r\n"
				+ "  \"pmAppointmentId\": \""+ApptId+"\",\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"practiceId\": \""+PracticeId+"\"\r\n"
				+ "}]";
		return checkinActions;
	}
	
	public String getDELETEAppointmentsFromDbPayload(String appDateRangeStart, String appDateRangeEnd) {
		String deleteAppt="{\r\n"
				+ "    \"applyFilters\": true,\r\n"
				+ "    \"applyStatusMappings\": true,\r\n"
				+ "    \"pageSize\": 50,\r\n"
				+ "    \"pageNumber\": 1,\r\n"
				+ "    \"appointmentDateRangeStart\": \""+appDateRangeStart+"\",\r\n"
				+ "    \"appointmentDateRangeEnd\": \""+appDateRangeEnd+"\"\r\n"
				+ "}";
		return deleteAppt;
	}
	
	public String postAppointmentsPayload(String IntegrationId,long time) {
		String postAppointment=" {\r\n"
				+ "	\"status\": \"NEW\",\r\n"
				+ "	\"time\": "+time+",\r\n"
				+ "	\"locationName\": \"River Oak Main\",\r\n"
				+ "	\"providerName\": \"Test Provider\",\r\n"
				+ "	\"timezoneId\": \"Asia/Calcutta\",\r\n"
				+ "	\"practiceDemographics\": {\r\n"
				+ "		\"pmExternalId\": \"12344\",\r\n"
				+ "		\"firstName\": \"AppointmentNew\",\r\n"
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
	
	public String getFormsPayload() {
		String checkinActions=" {\r\n"
				+ "      \"status\": \"INCOMPLETE\",\r\n"
				+ "      \"title\": \"NEW\",\r\n"
				+ "      \"url\": \"www.gmail.com\"\r\n"
				+ "    }";
		return checkinActions;
	}
	
	public String getInsurancePayload() {
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
				+ "  \"status\": \"COMPLETE\"\r\n"
				+ "}";
		return insurance;	
	}
	
	public String getDemographicsPayload() {
		String demographics=" {\r\n"
				+ "	\"address\": \"3790 W. First Station : add line 1\",\r\n"
				+ "	\"address2\": \"marine lines plaza : add line 2\",\r\n"
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
	
	public String getBalancePayPayload(String PatientId) {
		String balancePay=" {\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"patientName\": \"AppScheduler One\",\r\n"
				+ "  \"patientDob\": \"2001-01-01\",\r\n"
				+ "  \"patientEmail\": \"sujit.kolhe@yahoo.com\",\r\n"
				+ "  \"creditCardName\": \"Health\",\r\n"
				+ "  \"creditCardNumber\": \"4111111111111111\",\r\n"
				+ "  \"creditCardType\": \"VISA\",\r\n"
				+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
				+ "  \"creditCardCvvCode\": \"333\",\r\n"
				+ "  \"creditCardZip\": \"12345\",\r\n"
				+ "  \"amount\": 20\r\n"
				+ "}";
		return balancePay;
	}
	
	public String getCopayPayPayload(String PatientId) {
		String copayPay="{\r\n"
				+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
				+ "  \"patientName\": \"AppScheduler One\",\r\n"
				+ "  \"patientDob\": \"1991-01-01\",\r\n"
				+ "  \"patientEmail\": \"sujit.kolhe@yahoo.com\",\r\n"
				+ "  \"creditCardName\": \"Health\",\r\n"
				+ "  \"creditCardNumber\": \"4111111111111111\",\r\n"
				+ "  \"creditCardType\": \"VISA\",\r\n"
				+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
				+ "  \"creditCardCvvCode\": \"333\",\r\n"
				+ "  \"creditCardZip\": \"12345\",\r\n"
				+ "  \"amount\": 30\r\n"
				+ "}";
		return copayPay;
	}
	
	public String getGuestSessionPayload() {
		String copayPay=" {\r\n"
				+ "  \"dob\": \"1991-01-01\",\r\n"
				+ "	\"zip\": \"12345\"\r\n"
				
				+ "}";
		return copayPay;
	}
	
	public String getFormsPayloadGuest() {
		String forms=" {\r\n"
				+ "      \"status\": \"INCOMPLETE\",\r\n"
				+ "      \"title\": \"NEW\",\r\n"
				+ "      \"url\": \"www.gmail.com\"\r\n"
				+ "    }";
		return forms;
	}
		
		public String getInsurancePayloadGuest() {
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
					+ "  \"status\": \"COMPLETE\"\r\n"
					+ "}";
			return insurance;	
	}
		
		public String getDemographicsPayloadGuest() {
			String demographics=" {\r\n"
					+ "	\"address\": null,\r\n"
					+ "	\"address2\": null,\r\n"
					+ "	\"birthDate\": \"1991-01-01\",\r\n"
					+ "	\"city\": null,\r\n"
					+ "	\"email\": \"testpatient.crossasyst@gmail.com\",\r\n"
					+ "	\"firstName\": \"ArrivalActionsThree\",\r\n"
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
		
		public String getBalancePayloadGuest(String PatientId,String dob) {
			String balancePay=" {\r\n"
					+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
					+ "  \"patientName\": \"AppScheduler One\",\r\n"
					+ "  \"patientDob\": \""+dob+"\",\r\n"
					+ "  \"patientEmail\": \"sujit.kolhe@yahoo.com\",\r\n"
					+ "  \"creditCardName\": \"Health\",\r\n"
					+ "  \"creditCardNumber\": \"4111111111111111\",\r\n"
					+ "  \"creditCardType\": \"VISA\",\r\n"
					+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
					+ "  \"creditCardCvvCode\": \"333\",\r\n"
					+ "  \"creditCardZip\": \"12345\",\r\n"
					+ "  \"amount\": 20\r\n"
					+ "}";
			return balancePay;
	}
		
		public String getCopayPayPayloadGuest(String PatientId) {
			String copayPay="{\"pmPatientId\":\""+PatientId+"\",\r\n"
					+ "	 \"creditCardName\":\"Health\",\r\n"
					+ "	 \"creditCardNumber\":\"4111111111111111\",\r\n"
					+ "	 \"creditCardCvvCode\":\"333\",\r\n"
					+ "	 \"creditCardType\":\"VISA\",\r\n"
					+ "	 \"creditCardZip\":\"12345\",\r\n"
					+ "	 \"amount\":15,\r\n"
					+ "	 \"creditCardExpirationDate\":\"2023-01-01\",\r\n"
					+ "	 \"patientEmail\":\"sujit.kolhe@yahoo.com\",\r\n"
					+ "	 \"patientDob\":\"1991-01-01\",\r\n"
					+ "	 \"patientName\":\"AppScheduler One\"\r\n"
					+ "	}";
			return copayPay;
			
	}
		
		public String getPracticeIdWithInvalidDateRange(String appDateRangeStart, String appDateRangeEnd) {
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
		
		public String precheckApptPayloadWithoutName(long time) {
			String postAppointment="{\r\n"
					+ "    \"status\": \"NEW\",\r\n"
					+ "    \"time\": "+time+",\r\n"
					+ "    \"locationName\": \"River Oak Main\",\r\n"
					+ "    \"providerName\": \"Test Provider\",\r\n"
					+ "    \"timezoneId\": \"Asia/Calcutta\",\r\n"
					+ "    \"practiceDemographics\": {\r\n"
					+ "        \"pmExternalId\": \"12344\",\r\n"
					+ "        \"dob\": \"1991-01-01\",\r\n"
					+ "        \"address\": \"saas1233\",\r\n"
					+ "        \"city\": \"LA\",\r\n"
					+ "        \"state\": \"LA\",\r\n"
					+ "        \"zip\": \"12345\",\r\n"
					+ "        \"phone\": 5087437423,\r\n"
					+ "        \"email\": \"testpatient.crossasyst@gmail.com\"\r\n"
					+ "    },\r\n"
					+ "    \"insurance\": {\r\n"
					+ "        \"primaryInsuranceInfo\": {\r\n"
					+ "            \"name\": \"Blueshield Insurance\",\r\n"
					+ "            \"groupNumber\": \"BLK12345\"\r\n"
					+ "        },\r\n"
					+ "        \"status\": \"INCOMPLETE\"\r\n"
					+ "    },\r\n"
					+ "    \"coPayment\": {\r\n"
					+ "        \"status\": \"INCOMPLETE\",\r\n"
					+ "        \"amount\": \"30\"\r\n"
					+ "    },\r\n"
					+ "    \"balance\": {\r\n"
					+ "        \"status\": \"INCOMPLETE\",\r\n"
					+ "        \"amount\": \"10\"\r\n"
					+ "    },\r\n"
					+ "    \"integrationId\": \"9\",\r\n"
					+ "    \"type\": \"Fever n Cold\"\r\n"
					+ "}";
			return postAppointment;
		}
		
		public String precheckApptPayloadWithoutPhone(long time) {
			String postAppointment="{\r\n"
					+ "    \"status\": \"NEW\",\r\n"
					+ "    \"time\": "+time+",\r\n"
					+ "    \"locationName\": \"River Oak Main\",\r\n"
					+ "    \"providerName\": \"Test Provider\",\r\n"
					+ "    \"timezoneId\": \"Asia/Calcutta\",\r\n"
					+ "    \"practiceDemographics\": {\r\n"
					+ "        \"pmExternalId\": \"12344\",\r\n"
					+ "        \"firstName\": \"AppointmentNew\",\r\n"
					+ "        \"lastName\": \"Test\",\r\n"
					+ "        \"dob\": \"1991-01-01\",\r\n"
					+ "        \"address\": \"saas1233\",\r\n"
					+ "        \"city\": \"LA\",\r\n"
					+ "        \"state\": \"LA\",\r\n"
					+ "        \"zip\": \"12345\",\r\n"
					+ "        \"email\": \"testpatient.crossasyst@gmail.com\"\r\n"
					+ "    },\r\n"
					+ "    \"insurance\": {\r\n"
					+ "        \"primaryInsuranceInfo\": {\r\n"
					+ "            \"name\": \"Blueshield Insurance\",\r\n"
					+ "            \"groupNumber\": \"BLK12345\"\r\n"
					+ "        },\r\n"
					+ "        \"status\": \"INCOMPLETE\"\r\n"
					+ "    },\r\n"
					+ "    \"coPayment\": {\r\n"
					+ "        \"status\": \"INCOMPLETE\",\r\n"
					+ "        \"amount\": \"30\"\r\n"
					+ "    },\r\n"
					+ "    \"balance\": {\r\n"
					+ "        \"status\": \"INCOMPLETE\",\r\n"
					+ "        \"amount\": \"10\"\r\n"
					+ "    },\r\n"
					+ "    \"integrationId\": \"9\",\r\n"
					+ "    \"type\": \"Fever n Cold\"\r\n"
					+ "}";
			return postAppointment;
		}
		
		public String getBalancePayPayloadForPrecheck(String PatientId) {
			String balancePay=" {\r\n"
					+ "  \"pmPatientId\": \""+PatientId+"\",\r\n"
					+ "  \"patientName\": \"AppointmentNew Test\",\r\n"
					+ "  \"patientDob\": \"1991-01-01\",\r\n"
					+ "  \"patientEmail\": \"testpatient.crossasyst@gmail.com\",\r\n"
					+ "  \"creditCardName\": \"Health\",\r\n"
					+ "  \"creditCardNumber\": \"4111111111111111\",\r\n"
					+ "  \"creditCardType\": \"VISA\",\r\n"
					+ "  \"creditCardExpirationDate\": \"2023-01-01\",\r\n"
					+ "  \"creditCardCvvCode\": \"333\",\r\n"
					+ "  \"creditCardZip\": \"12345\",\r\n"
					+ "  \"amount\": 20\r\n"
					+ "}";
			return balancePay;
	}
		
		public String getDELETEApptsFromDbWithSelectAllFalsePayload() {
			   LocalDateTime endDate = LocalDateTime.now();
			String deleteAppt="{\r\n"
					+ "\"selectedAll\":false,\r\n"
					+ "\"pagingParameters\":{\r\n"
					+ "\"applyFilters\":true,\r\n"
					+ "\"applyStatusMappings\":true,\r\n"
					+ "\"pageSize\":50,\r\n"
					+ "\"pageNumber\":0,\r\n"
					+ "\"appointmentDateRangeStart\":\"2021-09-22T18:30:00Z\",\r\n"
					+ "\"appointmentDateRangeEnd\":\""+endDate+"\",\r\n"
					+ "\"patientPagingFilter\":{\r\n"
					+ "}\r\n"
					+ "},\r\n"
					+ "\"totalAppts\":5,\r\n"
					+ "\"patientAndAppointments\":[\r\n"
					+ "{\r\n"
					+ "\"pmPatientId\":\"11111\",\r\n"
					+ "\"pmAppointmentId\":\"22221\"\r\n"
					+ "},\r\n"
					+ "{\r\n"
					+ "\"pmPatientId\":\"11112\",\r\n"
					+ "\"pmAppointmentId\":\"22222\"\r\n"
					+ "},\r\n"
					+ "{\r\n"
					+ "\"pmPatientId\":\"11113\",\r\n"
					+ "\"pmAppointmentId\":\"22223\"\r\n"
					+ "},\r\n"
					+ "{\r\n"
					+ "\"pmPatientId\":\"11114\",\r\n"
					+ "\"pmAppointmentId\":\"22224\"\r\n"
					+ "},\r\n"
					+ "{\r\n"
					+ "\"pmPatientId\":\"11115\",\r\n"
					+ "\"pmAppointmentId\":\"22225\"\r\n"
					+ "}\r\n"
					+ "]\r\n"
					+ "}";
			return deleteAppt;
	}
		
		public String getDELETEApptsFromDbWithSelectAllTruePayload() {
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("GMT"));
			int startdat = cal.get(Calendar.DATE)-1;
			int dd = cal.get(Calendar.DATE) + 1;
			int yyyy = cal.get(Calendar.YEAR);
			int mm = cal.get(Calendar.MONTH)+1;

			String deleteAppt="{\r\n"
					+ "    \"selectedAll\": true,\r\n"
					+ "    \"pagingParameters\": {\r\n"
					+ "        \"applyFilters\": true,\r\n"
					+ "        \"applyStatusMappings\": true,\r\n"
					+ "        \"pageSize\": 50,\r\n"
					+ "        \"pageNumber\": 0,\r\n"
					+ "\"appointmentDateRangeStart\":\""+yyyy+"-"+mm+"-"+startdat+"T15:29:59Z\",\r\n"
					+ "\"appointmentDateRangeEnd\":\""+yyyy+"-"+mm+"-"+dd+"T18:29:59Z\"\r\n"
					+ "    },\r\n"
					+ "    \"totalAppts\": 15,\r\n"
					+ "    \"patientAndAppointments\": []\r\n"
					+ "}";
					
			return deleteAppt;
		}
		
		public String getGuestSessionPayloadForCopay() {
			String copayPay="{\r\n"
					+ "  \"dob\": \"1991-01-01\",\r\n"
					+ "  \"zip\": \"12345\"\r\n"
					+ "}";
			return copayPay;
	}
		
		public String getCopayPayloadForPrecheck(String PatientId) {
			String balancePay="{\"pmPatientId\":\""+PatientId+"\",\r\n"
					+ "		\"creditCardName\":\"Health\",\r\n"
					+ "		\"creditCardNumber\":\"4111111111111111\",\r\n"
					+ "		\"creditCardCvvCode\":\"333\",\r\n"
					+ "		\"creditCardType\":\"VISA\",\r\n"
					+ "		\"creditCardZip\":\"12345\",\r\n"
					+ "		\"amount\":15,\r\n"
					+ "		\"creditCardExpirationDate\":\"2023-01-01\",\r\n"
					+ "		\"patientEmail\":\"sujit.kolhe@yahoo.com\",\r\n"
					+ "		\"patientDob\":\"1991-01-01\",\r\n"
					+ "		\"patientName\":\"AppScheduler One\""
					+ "}";
			return balancePay;
		}
		
		public String getDeleteAppointmentsFromDbPayload(String patientId, String practiceId) {
			String deleteAppt="[\r\n"
					+ "  {\r\n"
					+ "    \"pmAppointmentId\": \""+patientId+"\",\r\n"
					+ "    \"pmPatientId\": \""+practiceId+"\"\r\n"
					+ "  }\r\n"
					+ "]";
			return deleteAppt;	
	}
}
