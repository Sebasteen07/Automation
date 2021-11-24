// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.payload;

public class AptReminderProcessorPayload {
	private static AptReminderProcessorPayload payload = new AptReminderProcessorPayload();

	private AptReminderProcessorPayload() {

	}

	public static AptReminderProcessorPayload getAptReminderProcessorPayload() {
		return payload;
	}

	public String getProcessReminderDataPayload(String cadence, String practiceId, String patientId,
			String appointmentId, String type, long time, String status) {
		String processReminderData = "{\r\n"
				+ "  \"cadence\": \""+cadence+"\",\r\n"
				+ "  \"checkAppointmentTimeWithinCadence\": true,\r\n"
				+ "  \"metadata\": {\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"time\": "+time+",\r\n"
				+ "    \"status\": \""+status+"\"\r\n"
				+ "  }\r\n"
				+ "}";
		return processReminderData;
	}

	public String getProcessReminderDataPayloadWithoutCadence(String practiceId, String patientId,
			String appointmentId, String type,long time, String status) {
		String processReminderData = "{\r\n"
				+ "  \"cadence\": null,\r\n"
				+ "  \"checkAppointmentTimeWithinCadence\": true,\r\n"
				+ "  \"metadata\": {\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"time\": "+time+",\r\n"
				+ "    \"status\": \""+status+"\"\r\n"
				+ "  }\r\n"
				+ "}";
		return processReminderData;
	}
	public String getProcessReminderDataPayloadWithoutStatus(String cadence, String practiceId, String patientId,
			String appointmentId, String type,long time) {
		String processReminderData = "{\r\n"
				+ "  \"cadence\": \"1\",\r\n"
				+ "  \"checkAppointmentTimeWithinCadence\": true,\r\n"
				+ "  \"metadata\": {\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"time\": "+time+"\r\n"
				+ "  }\r\n"
				+ "}";
		return processReminderData;
	}
	public String getProcessReminderDataPayloadWithoutTime(String cadence, String practiceId, String patientId,
			String appointmentId, String type, String status) {
		String processReminderData = "{\r\n"
				+ "  \"cadence\": \"1\",\r\n"
				+ "  \"checkAppointmentTimeWithinCadence\": true,\r\n"
				+ "  \"metadata\": {\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"status\": \""+status+"\"\r\n"
				+ "  }\r\n"
				+ "}";
		return processReminderData;
	}
}
