package com.medfusion.product.appt.precheck.payload;

public class MfNotificationSubscriptionManagerPayload {
	private static MfNotificationSubscriptionManagerPayload payload = new MfNotificationSubscriptionManagerPayload();

	private MfNotificationSubscriptionManagerPayload() {

	}

	public static MfNotificationSubscriptionManagerPayload getMfNotificationSubscriptionManagerPayload() {
		return payload;
	}

	public String getSavesSubscriptionDataPayload(String emailId, String systemId, String resource, String practiceId,
			String apptType, String mechanism) {
		String savesSubscriptionData = "{\r\n"
				+ "        \"email\": \""+emailId+"\",\r\n"
				+ "        \"phone\": 3313304741,\r\n"
				+ "        \"system\": \"" + systemId + "\",\r\n"
				+ "        \"resource\": \"" + resource + "\",\r\n"
				+ "        \"identifier\": \""+practiceId+"\",\r\n"
				+ "        \"type\": \""+apptType+"\",\r\n"
				+ "        \"mechanism\": \"" + mechanism + "\"\r\n"
				+ "    }";
		return savesSubscriptionData;
	}

	public String getSavesSubsDataPayloadWithInvalidSystemId(String emailId, String resource, String practiceId,
			String apptType, String mechanism) {
		String savesSubscriptionData = "{\r\n"
				+ "        \"email\": \""+emailId+"\",\r\n"
				+ "        \"phone\": 3313304741,\r\n"
				+ "        \"system\": null,\r\n"
				+ "        \"resource\": \""+resource+"\",\r\n"
				+ "        \"identifier\": \""+practiceId+"\",\r\n"
				+ "        \"type\": \""+apptType+"\",\r\n"
				+ "        \"mechanism\": \""+mechanism+"\"\r\n"
				+ "    }";
		return savesSubscriptionData;
	}

	public String getSavesAllSubscriptionDataPayload(String emailId, String practiceId, String mechanism,
			String resource, String systemId, String apptType) {
		String savesSubscriptionData = "[\r\n"
				+ "  {\r\n"
				+ "  \"email\": \""+emailId+"\",\r\n"
				+ "  \"identifier\": \""+practiceId+"\",\r\n"
				+ "  \"mechanism\": \""+mechanism+"\",\r\n"
				+ "  \"phone\": 3313304741,\r\n"
				+ "  \"resource\": \""+resource+"\",\r\n"
				+ "  \"system\": \"" + systemId + "\",\r\n"
				+ "  \"type\": \""+apptType+"\"\r\n"
				+ "}\r\n"
				+ "]";
		return savesSubscriptionData;
	}

	public String getSavesAllSubsDataPayloadWithoutSystemId(String emailId, String practiceId, String mechanism,
			String resource, String apptType) {
		String savesSubscriptionData = "[\r\n"
				+ "  {\r\n"
				+ "  \"email\": \""+emailId+"\",\r\n"
				+ "  \"identifier\": \""+practiceId+"\",\r\n"
				+ "  \"mechanism\": \""+mechanism+"\",\r\n"
				+ "  \"phone\": 3313304741,\r\n"
				+ "  \"resource\": \""+resource+"\",\r\n"
				+ "  \"system\": null,\r\n"
				+ "  \"type\": \""+apptType+"\"\r\n"
				+ "}\r\n"
				+ "]";
		return savesSubscriptionData;
	}
	public String getSavesAllSubsDataPayloadWithoutResource(String emailId, String practiceId, String mechanism,
			String systemId, String apptType) {
		String savesSubscriptionData = "[\r\n"
				+ "  {\r\n"
				+ "  \"email\": \""+emailId+"\",\r\n"
				+ "  \"identifier\": \""+practiceId+"\",\r\n"
				+ "  \"mechanism\": \""+mechanism+"\",\r\n"
				+ "  \"phone\": 3313304741,\r\n"
				+ "  \"resource\": null,\r\n"
				+ "  \"system\": \"" + systemId + "\",\r\n"
				+ "  \"type\": \""+apptType+"\"\r\n"
				+ "}\r\n"
				+ "]";
		return savesSubscriptionData;
	}
	public String getSavesAllSubsDataPayloadWithoutApptType(String emailId, String practiceId, String mechanism,
			String resource, String systemId) {
		String savesSubscriptionData = "[\r\n"
				+ "  {\r\n"
				+ "  \"email\": \""+emailId+"\",\r\n"
				+ "  \"identifier\": \""+practiceId+"\",\r\n"
				+ "  \"mechanism\": \""+mechanism+"\",\r\n"
				+ "  \"phone\": 3313304741,\r\n"
				+ "  \"resource\": \""+resource+"\",\r\n"
				+ "  \"system\": \"" + systemId + "\"\r\n"
				+ "}\r\n"
				+ "]";
		return savesSubscriptionData;
	}
}
