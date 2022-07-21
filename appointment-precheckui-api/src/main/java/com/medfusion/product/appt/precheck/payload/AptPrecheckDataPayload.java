// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.payload;

public class AptPrecheckDataPayload {
	private static AptPrecheckDataPayload payload = new AptPrecheckDataPayload();

	private AptPrecheckDataPayload() {

	}

	public static AptPrecheckDataPayload getAptPrecheckDataPayload() {
		return payload;
	}

	public String getUpdateApptActionPayload(String appointmentId, String patientId, String practiceId) {
		String apptAction=" {\r\n"
				+ "  \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "  \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return apptAction;
	}

	public String getUpdateApptActionPayloadWithoutPatientId(String appointmentId, String practiceId) {
		String updateApptAction = " {\r\n"
				+ "  \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return updateApptAction;
	}
	
	public String getRetrievesApptActionPayload(String appointmentId, String patientId, String practiceId) {
		String retrivesApptAction=" [\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"practiceId\": \""+practiceId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return retrivesApptAction;
	}

	public String getRetrievesApptActionPayloadWithoutPatientId(String appointmentId, String practiceId) {
		String retrivesApptAction = " [\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"practiceId\": \""+practiceId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return retrivesApptAction;
	}
	
	public String getDeleteApptActionPayload(String appointmentId, String patientId) {
		String deleteApptAction=" [\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return deleteApptAction;
	}

	public String getNotificationsPayload(String appointmentId, String patientId, String practiceId, String type) {
		String getNotifications="{\r\n"
				+ "  \"notificationIdentifiers\": [\r\n"
				+ "    {\r\n"
				+ "      \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "      \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "      \"practiceId\": \""+practiceId+"\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"type\": \"" + type + "\"\r\n"
				+ "}";
		return getNotifications;
	}

	public String getNotificationsPayloadWithoutType(String appointmentId, String patientId, String practiceId) {
		String getNotifications="{\r\n"
				+ "  \"notificationIdentifiers\": [\r\n"
				+ "    {\r\n"
				+ "      \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "      \"pmPatientId\": \"" + patientId + "\",\r\n"
				+ "      \"practiceId\": \""+practiceId+"\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return getNotifications;
	}

	public String getNotificationsPayloadWithoutPatientId(String appointmentId, String practiceId, String type) {
		String getNotifications="{\r\n"
				+ "  \"notificationIdentifiers\": [\r\n"
				+ "    {\r\n"
				+ "      \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "      \"practiceId\": \""+practiceId+"\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"type\": \"" + type + "\"\r\n"
				+ "}";
		return getNotifications;
	}

	public String saveAllNotificationsPayload(String id, String integrationId, String type, String appointmentId,
			String patientId, String practiceId) {
		String saveAllNotifications = " {\r\n"
				+ "  \"notifications\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": \""+id+"\",\r\n"
				+ "      \"integrationId\": \""+integrationId+"\",\r\n"
				+ "      \"notifications\": [\r\n"
				+ "        {\r\n"
				+ "          \"content\": {\r\n"
				+ "            \"en\": \"we will call you shortly to collect your insurance information.\",\r\n"
				+ "            \"es\": \"lo llamaremos en breve para recopilar la informaciouFFFDn de su seguro.\"\r\n"
				+ "          },\r\n"
				+ "          \"id\": \"CURBSIDE_CHECKIN_UPDATE\",\r\n"
				+ "          \"medium\": {\r\n"
				+ "           \"TEXT\": \"SUCCESS\",\r\n"
				+ "            \"EMAIL\": \"SUCCESS\"\r\n"
				+ "          },\r\n"
				+ "          \"type\": \""+type+"\",\r\n"
				+ "          \"time\": \"2021-04-26T14:12:01.624Z[UTC]\"\r\n"
				+ "        }\r\n"
				+ "      ],\r\n"
				+ "      \"pmAppointmentId\": \"" + appointmentId + "\",\r\n"
				+ "      \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "      \"practiceId\": \""+practiceId+"\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return saveAllNotifications;
	}
	
	public String savesNotificationsPayload(String type) {
		String saveAllNotifications = "{\r\n"
				+ "  \"content\": {\r\n"
				+ "            \"en\": \"we will call you shortly to collect your insurance information.\",\r\n"
				+ "            \"es\": \"lo llamaremos en breve para recopilar la informaciouFFFDn de su seguro.\"\r\n"
				+ "  },\r\n"
				+ "  \"id\": \"CURBSIDE_CHECKIN_UPDATE\",\r\n"
				+ "  \"medium\": {\r\n"
				+ "            \"TEXT\": \"SUCCESS\",\r\n"
				+ "            \"EMAIL\": \"SUCCESS\"\r\n"
				+ "  },\r\n"
				+ "  \"type\": \"" + type + "\",\r\n"
				+ "  \"time\": \"2021-04-26T14:12:01.624Z[UTC]\"\r\n"
				+ "}";
		return saveAllNotifications;
	}

	public String savesNotificationsPayloadWithoutType() {
		String saveAllNotifications = "{\r\n"
				+ "  \"content\": {\r\n"
				+ "            \"en\": \"we will call you shortly to collect your insurance information.\",\r\n"
				+ "            \"es\": \"lo llamaremos en breve para recopilar la informaciouFFFDn de su seguro.\"\r\n"
				+ "  },\r\n"
				+ "  \"id\": \"CURBSIDE_CHECKIN_UPDATE\",\r\n"
				+ "  \"medium\": {\r\n"
				+ "            \"TEXT\": \"SUCCESS\",\r\n"
				+ "            \"EMAIL\": \"SUCCESS\"\r\n"
				+ "  },\r\n"
				+ "  \"time\": \"2021-04-26T14:12:01.624Z[UTC]\"\r\n"
				+ "}";
		return saveAllNotifications;
	}

	public String returnsListOfAppointmentsPayload(String apptId, String patientId, String practiceId) {
		String listOfAppt ="[\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+apptId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"practiceId\": \"" + practiceId + "\"\r\n"
				+ "  }\r\n"
				+ "]";
		return listOfAppt;
	}

	public String returnsListOfApptsPayloadWithoutPatientId(String apptId, String practiceId) {
		String listOfAppt ="[\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+apptId+"\",\r\n"
				+ "    \"practiceId\": \"" + practiceId + "\"\r\n"
				+ "  }\r\n"
				+ "]";
		return listOfAppt;
	}

	public String updateBalanceInfoPayload(String amount, long date, String status) {
		String balanceInfo ="{\r\n"
				+ "  \"payInOffice\": true,\r\n"
				+ "  \"payments\": [\r\n"
				+ "    {\r\n"
				+ "      \"amount\": " + amount + ",\r\n"
				+ "      \"date\": " + date + ",\r\n"
				+ "      \"token\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"" + status + "\"\r\n"
				+ "}";
		return balanceInfo;
	}

	public String updateBalanceInfoPayloadWithoutStatus(String amount, long date) {
		String balanceInfo ="{\r\n"
				+ "  \"payInOffice\": true,\r\n"
				+ "  \"payments\": [\r\n"
				+ "    {\r\n"
				+ "      \"amount\": " + amount + ",\r\n"
				+ "      \"date\": " + date + ",\r\n"
				+ "      \"token\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return balanceInfo;
	}

	public String updateCopayInfoPayload(String amount, long date, String status) {
		String copayInfo =" {\r\n"
				+ "  \"payInOffice\": true,\r\n"
				+ "  \"payments\": [\r\n"
				+ "    {\r\n"
				+ "      \"amount\": "+amount+",\r\n"
				+ "      \"date\": "+date+",\r\n"
				+ "      \"token\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \""+status+"\"\r\n"
				+ "}";
		return copayInfo;
	}

	public String updateCopayInfoPayloadWithoutStatus(String amount, long date) {
		String copayInfo =" {\r\n"
				+ "  \"payInOffice\": true,\r\n"
				+ "  \"payments\": [\r\n"
				+ "    {\r\n"
				+ "      \"amount\": "+amount+",\r\n"
				+ "      \"date\": "+date+",\r\n"
				+ "      \"token\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return copayInfo;
	}

	public String updateDemographicsPayload(String bDate, String city, String firstName, String lastName) {
		String demographicsInfo ="{\r\n"
				+ "  \"address\": \"LA\",\r\n"
				+ "  \"address2\": \"LA\",\r\n"
				+ "  \"birthDate\": \"" + bDate + "\",\r\n"
				+ "  \"city\": \"" + city + "\",\r\n"
				+ "  \"email\": \"jitendra.lekhak@crossasyst.com\",\r\n"
				+ "  \"firstName\": \"" + firstName + "\",\r\n"
				+ "  \"language\": \"en\",\r\n"
				+ "  \"lastName\": \"" + lastName + "\",\r\n"
				+ "  \"middleName\": null,\r\n"
				+ "  \"phone\": 2125451888,\r\n"
				+ "  \"preferredPharmacyName\": \"Deepak Pharmact\",\r\n"
				+ "  \"preferredPharmacyPhoneNumber\": 2125451888,\r\n"
				+ "  \"state\": \"NY\",\r\n"
				+ "  \"status\": \"COMPLETE\",\r\n"
				+ "  \"verified\": true,\r\n"
				+ "  \"zip\": \"12345\"\r\n"
				+ "}";
		return demographicsInfo;
	}

	public String updateDemographicsPayloadWithoutStatus(String bDate, String city, String firstName, String lastName) {
		String demographicsInfo ="{\r\n"
				+ "  \"address\": \"LA\",\r\n"
				+ "  \"address2\": \"LA\",\r\n"
				+ "  \"birthDate\": \"" + bDate + "\",\r\n"
				+ "  \"city\": \"" + city + "\",\r\n"
				+ "  \"email\": \"jitendra.lekhak@crossasyst.com\",\r\n"
				+ "  \"firstName\": \"" + firstName + "\",\r\n"
				+ "  \"language\": \"en\",\r\n"
				+ "  \"lastName\": \"" + lastName + "\",\r\n"
				+ "  \"middleName\": null,\r\n"
				+ "  \"phone\": 2125451888,\r\n"
				+ "  \"preferredPharmacyName\": \"Deepak Pharmact\",\r\n"
				+ "  \"preferredPharmacyPhoneNumber\": 2125451888,\r\n"
				+ "  \"state\": \"NY\",\r\n"
				+ "  \"verified\": true,\r\n"
				+ "  \"zip\": \"12345\"\r\n"
				+ "}";
		return demographicsInfo;
	}

	public String updateFormInfoPayload(String title, String url) {
		String formInfo = "{\r\n"
				+ "  \"forms\": [\r\n"
				+ "    {\r\n"
				+ "      \"status\": \"COMPLETE\",\r\n"
				+ "      \"title\": \"" + title + "\",\r\n"
				+ "      \"url\": \"" + url + "\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return formInfo;
	}

	public String updateInsurancePayload(String groupNumber, String insuranceName, String memberId, String memberName,
			String editStatus) {
		String insuranceInfo = "{\r\n"
				+ "  \"insuranceList\": [\r\n"
				+ "    {\r\n"
				+ "      \"details\": {\r\n"
				+ "        \"groupNumber\": \""+groupNumber+"\",\r\n"
				+ "        \"insuranceName\": \""+insuranceName+"\",\r\n"
				+ "        \"memberId\": \""+memberId+"\",\r\n"
				+ "        \"memberName\": \""+memberName+"\"\r\n"
				+ "      },\r\n"
				+ "      \"editStatus\": \"" + editStatus + "\",\r\n"
				+ "      \"imageInfo\": {\r\n"
				+ "        \"imageFileName\": \"5755612b-16bd-45a5-8750-c8a9e9460b35\"\r\n"
				+ "      },\r\n"
				+ "      \"tier\": \"PRIMARY\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"COMPLETE\"\r\n"
				+ "}";
		return insuranceInfo;
	}

	public String updateInsurancePayloadWithoutStatus(String groupNumber, String insuranceName, String memberId,
			String memberName,
			String editStatus) {
		String insuranceInfo = "{\r\n"
				+ "  \"insuranceList\": [\r\n"
				+ "    {\r\n"
				+ "      \"details\": {\r\n"
				+ "        \"groupNumber\": \""+groupNumber+"\",\r\n"
				+ "        \"insuranceName\": \""+insuranceName+"\",\r\n"
				+ "        \"memberId\": \""+memberId+"\",\r\n"
				+ "        \"memberName\": \""+memberName+"\"\r\n"
				+ "      },\r\n"
				+ "      \"editStatus\": \"" + editStatus + "\",\r\n"
				+ "      \"imageInfo\": {\r\n"
				+ "        \"imageFileName\": \"5755612b-16bd-45a5-8750-c8a9e9460b35\"\r\n"
				+ "      },\r\n"
				+ "      \"tier\": \"PRIMARY\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return insuranceInfo;
	}
}
