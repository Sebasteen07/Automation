package com.medfusion.product.appt.precheck.payload;

public class MfNotificationModulatorPayload {
	private static MfNotificationModulatorPayload payload = new MfNotificationModulatorPayload();

	private MfNotificationModulatorPayload() {

	}

	public static MfNotificationModulatorPayload getMfNotificationModulatorPayload() {
		return payload;
	}

	public String sendNotificationPayload( String notificationPurpose, String notificationType,String subjectId,String subjectUrn,String type,String recipientAddress,String subject) {
		String sendNotificatiob = "[\r\n"
				+ "  {\r\n"
				+ "    \"highPriority\": true,\r\n"
				+ "    \"notificationPurpose\": \""+notificationPurpose+"\",\r\n"
				+ "    \"notificationType\": \""+notificationType+"\",\r\n"
				+ "    \"sendingApplication\": \"SWAGGER\",\r\n"
				+ "    \"subjectId\": \""+subjectId+"\",\r\n"
				+ "    \"subjectUrn\": \""+subjectUrn+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"sender address\":\"appointments@medfusion.net\",\r\n"
				+ "    \"recipient address\":\""+recipientAddress+"\",\r\n"
				+ "    \"subject\":\""+subject+"\",\r\n"
				+ "    \"content\": \"Your appointment is coming up\"\r\n"
				+ "  }\r\n"
				+ "]\r\n"
				+ "";
		return sendNotificatiob;
	}

	public String sendNotificationPayloadWithoutNotifType(String notificationPurpose, String notificationType,
			String subjectId, String subjectUrn, String recipientAddress, String subject) {
		String sendNotificatiob = "[\r\n"
				+ "  {\r\n"
				+ "    \"highPriority\": true,\r\n"
				+ "    \"notificationPurpose\": \""+notificationPurpose+"\",\r\n"
				+ "    \"notificationType\": \""+notificationType+"\",\r\n"
				+ "    \"sendingApplication\": \"SWAGGER\",\r\n"
				+ "    \"subjectId\": \""+subjectId+"\",\r\n"
				+ "    \"subjectUrn\": \""+subjectUrn+"\",\r\n"
				+ "    \"sender address\":\"appointments@medfusion.net\",\r\n"
				+ "    \"recipient address\":\""+recipientAddress+"\",\r\n"
				+ "    \"subject\":\""+subject+"\",\r\n"
				+ "    \"content\": \"Your appointment is coming up\"\r\n"
				+ "  }\r\n"
				+ "]\r\n"
				+ "";
		return sendNotificatiob;
	}
}
