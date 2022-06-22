// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.payload;

public class MfNotificationsLogPayload {
	private static MfNotificationsLogPayload payload = new MfNotificationsLogPayload();

	private MfNotificationsLogPayload() {

	}

	public static MfNotificationsLogPayload getMfNotificationsLogPayload() {
		return payload;
	}

	public String returnLogsPayload(String subjectId, String subjectUrn ) {
		String returnLogs = " [\r\n"
				+ "    {\r\n"
				+ "        \"subjectId\": \""+subjectId+"\",\r\n"
				+ "        \"subjectUrn\": \""+subjectUrn+"\"\r\n"
				+ "    }\r\n"
				+ "]";
		return returnLogs;
	}

	public String returnLogsPayloadWithoutSubjId(String subjectUrn) {
		String returnLogs = " [\r\n"
				+ "    {\r\n"
				+ "        \"subjectUrn\": \""+subjectUrn+"\"\r\n"
				+ "    }\r\n"
				+ "]";
		return returnLogs;
	}

	public String deleteLogsPayload(String subjectId, String subjectUrn) {
		String deleteLogs = "[\r\n"
				+ "  {\r\n"
				+ "    \"subjectId\": \"" + subjectId + "\",\r\n"
				+ "    \"subjectUrn\": \"" + subjectUrn + "\"\r\n"
				+ "  }\r\n"
				+ "]";
		return deleteLogs;
	}
	
	public String createStatusPayload(String time) {
		String createStatus = "{\r\n"
				+ "  \"mfStatus\": \"SUCCESSFUL\",\r\n"
				+ "  \"time\": \"" + time + "\",\r\n"
				+ "  \"vendorStatus\": \"delivered\"\r\n"
				+ "}";
		return createStatus;
	}

	public String createsNotificationPayload(String createdTime, String mechanism, String notificationId,
			String notificationType, String notifPurpose, String time, String vendorStatus, String vendor) {
		String createNotification = " {\r\n"
				+ "  \"created\": \"" + createdTime + "\",\r\n"
				+ "  \"mechanism\": \""+mechanism+"\",\r\n"
				+ "  \"notificationId\": \""+notificationId+"\",\r\n"
				+ "  \"notificationType\": \""+notificationType+"\",\r\n"
				+ "  \"purpose\": \"" + notifPurpose + "\",\r\n"
				+ "  \"source\": \"APPOINTMENTS\",\r\n"
				+ "  \"statuses\": [\r\n"
				+ "    {\r\n"
				+ "      \"mfStatus\": \"PENDING\",\r\n"
				+ "      \"time\": \"" + time + "\",\r\n"
				+ "      \"vendorStatus\": \"" + vendorStatus + "\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"vendor\": \"" + vendor + "\"\r\n"
				+ "}";
		return createNotification;
	}
	
	public String createLogsPayload(String subjectId, String subjectUrn ) {
		String returnLogs = "{\r\n"
				+ "  \"subjectId\": \""+subjectId+"\",\r\n"
				+ "  \"subjectUrn\": \""+subjectUrn+"\"\r\n"
				+ "}";
		return returnLogs;
	}

}
