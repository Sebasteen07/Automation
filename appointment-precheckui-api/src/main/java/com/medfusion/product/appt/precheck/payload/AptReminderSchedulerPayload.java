package com.medfusion.product.appt.precheck.payload;

public class AptReminderSchedulerPayload {
	private static AptReminderSchedulerPayload payload = new AptReminderSchedulerPayload();

	private AptReminderSchedulerPayload() {

	}

	public static AptReminderSchedulerPayload getAptReminderSchedulerPayload() {
		return payload;
	}

	public String getUpdateApptMetadataPayload(String practiceId, String patientId, String appointmentId, String type,
			String status) {
		String updateApptMetadata = "{\r\n"
				+ "  \"practiceId\": \""+practiceId+"\",\r\n"
				+ "  \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "  \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"time\": 1623838200000,\r\n"
				+ "  \"status\": \""+status+"\",\r\n"
				+ "  \"filtered\": true\r\n"
				+ "}";
		return updateApptMetadata;
	}

	public String getUpdateApptMetadataPayloadWithoutPracticeId(String patientId, String appointmentId, String type,
			String status) {
		String updateApptMetadata = "{\r\n"
				+ "  \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "  \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"time\": 1623838200000,\r\n"
				+ "  \"status\": \""+status+"\",\r\n"
				+ "  \"filtered\": true\r\n"
				+ "}";
		return updateApptMetadata;
	}

	public String getScheduleRemindersPayload( String appointmentId,String patientId) {
		String scheduleReminders = " [\r\n"
				+ "    {\r\n"
				+ "        \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "        \"pmPatientId\": \""+patientId+"\"\r\n"
				+ "    }\r\n"
				+ "]";
		return scheduleReminders;
	}

	public String getScheduleRemindersPayloadWithoutPatientId(String appointmentId) {
		String scheduleReminders = " [\r\n"
				+ "    {\r\n"
				+ "        \"pmAppointmentId\": \""+appointmentId+"\"\r\n"
				+ "    }\r\n"
				+ "]";
		return scheduleReminders;
	}

	public String getSaveApptMetadataPayload(String practiceId,String patientId, String appointmentId, String type,String status) {
		String saveApptMetadata = "{\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"time\": 1623434403000,\r\n"
				+ "    \"status\": \""+status+"\",\r\n"
				+ "    \"filtered\": false\r\n"
				+ "}";
		return saveApptMetadata;
	}
	public String getSaveApptMetadataPayloadWithoutFilter(String practiceId,String patientId, String appointmentId, String type,String status) {
		String saveApptMetadata = "{\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"time\": 1623434403000,\r\n"
				+ "    \"status\": \""+status+"\"\r\n"
				+ "}";
		return saveApptMetadata;
	}

	public String getSaveApptMetadataPayloadWithoutTime(String practiceId, String patientId, String appointmentId,
			String type, String status) {
		String saveApptMetadata = "{\r\n"
				+ "    \"practiceId\": \""+practiceId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\",\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"status\": \""+status+"\",\r\n"
				+ "    \"filtered\": false\r\n"
				+ "}";
		return saveApptMetadata;
	}

	public String getDeleteApptsRemindersPayload(String appointmentId, String patientId) {
		String deleteApptsReminders = "[\r\n"
				+ "  {\r\n"
				+ "    \"pmAppointmentId\": \""+appointmentId+"\",\r\n"
				+ "    \"pmPatientId\": \""+patientId+"\"\r\n"
				+ "  }\r\n"
				+ "]";
		return deleteApptsReminders;
	}
}
