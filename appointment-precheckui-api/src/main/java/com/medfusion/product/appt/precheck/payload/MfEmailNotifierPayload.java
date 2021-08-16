package com.medfusion.product.appt.precheck.payload;

public class MfEmailNotifierPayload {
	private static MfEmailNotifierPayload payload = new MfEmailNotifierPayload();

	private MfEmailNotifierPayload() {

	}

	public static MfEmailNotifierPayload getMfEmailNotifierPayload() {
		return payload;
	}

	public String sendEmailPayload(String notificationPurpose, String notificationType, String subjectId,
			String subjectUrn, String type, String recipientAddress, String subject) {
		String sendEmail = "{\r\n"
				+ "    \"highPriority\": true,\r\n"
				+ "    \"notificationPurpose\": \""+notificationPurpose+"\",\r\n"
				+ "    \"notificationType\": \""+notificationType+"\",\r\n"
				+ "    \"sendingApplication\": \"SWAGGER\",\r\n"
				+ "    \"subjectId\": \""+subjectId+"\",\r\n"
				+ "    \"subjectUrn\": \""+subjectUrn+"\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"sender address\":\"appointments@medfusion.net\",\r\n"
				+ "    \"recipient address\":\""+recipientAddress+"\",\r\n"
				+ "    \"subject\":\"" + subject + "\",\r\n"
				+ "    \"content\": \"Your appointment is coming up\"\r\n"
				+ "  }";
		return sendEmail;
	}

	public String handleCallbackPayload(String email) {
		String handleCallback = "[\r\n"
				+ "{\r\n"
				+ "\"attempt\": null,\r\n"
				+ "\"email\": \"" + email + "\",\r\n"
				+ "\"event\": \"cHJvY2Vzc2VkLTIwODg4NTE5LWpieE1ick9TVHBlOERPV0Nsd3lZTmctMA\",\r\n"
				+ "\"ip\": null,\r\n"
				+ "\"notificationsLogId\": \"e2e63d7b-4d0f-4f36-aa65-2d7197b6e094\",\r\n"
				+ "\"reason\": null,\r\n"
				+ "\"requestId\": \"e2855c99ec49443f8746e4737a23375f\",\r\n"
				+ "\"response\": null,\r\n"
				+ "\"sg_event_id\": \"cHJvY2Vzc2VkLTIwODg4NTE5LWpieE1ick9TVHBlOERPV0Nsd3lZTmctMA\",\r\n"
				+ "\"sg_message_id\": \"jbxMbrOSTpe8DOWClwyYNg.filterdrecv-6ff7cc5fdf-7z54f-1-60E1476A-A2.0\",\r\n"
				+ "\"smtp-id\": \"<jbxMbrOSTpe8DOWClwyYNg@ismtpd0159p1mdw1.sendgrid.net>\",\r\n"
				+ "\"status\": \"string\",\r\n"
				+ "\"timestamp\": 1625376619,\r\n"
				+ "\"type\": \"processed\"\r\n"
				+ "}\r\n"
				+ "]";
		return handleCallback;
	}

}
