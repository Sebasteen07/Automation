package com.medfusion.product.appt.precheck.payload;

public class NotificationDisplayServicePayload {
	private static NotificationDisplayServicePayload payload = new NotificationDisplayServicePayload();
	
	private NotificationDisplayServicePayload() {
		
	}
	public static NotificationDisplayServicePayload getNotificationDisplayServicePayload() {
		return payload;
	}
	
	public String getCreatePrecheckNotifPayload(String id,String practiceId, String practiceName) {
		String precheckNotification = "{\r\n"
				+ "\"id\": \""+id+"\",\r\n"
				+ "\"externalEntityId\": \"3323\",\r\n"
				+ "\"practiceId\": \""+practiceId+"\",\r\n"
				+ "\"practiceName\": \""+practiceName+"\",\r\n"
				+ "\"emrId\": \"234\",\r\n"
				+ "\"integrationId\": \"9\",\r\n"
				+ "\"callToAction\": \"www.google.com\",\r\n"
				+ "\"message\": \"Your email address\",\r\n"
				+ "\"source\": \"PRECHECK\"\r\n"
				+ "}";
		return precheckNotification;
	}
	
	public String getCreateScheduleNotifPayload(String id,String practiceId, String practiceName) {
		String scheduleNotification = "{\r\n"
				+ "\"id\": \""+id+"\",\r\n"
				+ "\"externalEntityId\": \"3323\",\r\n"
				+ "\"practiceId\": \""+practiceId+"\",\r\n"
				+ "\"practiceName\": \""+practiceName+"\",\r\n"
				+ "\"emrId\": \"234\",\r\n"
				+ "\"integrationId\": \"9\",\r\n"
				+ "\"callToAction\": \"www.google.com\",\r\n"
				+ "\"message\": \"Your email address\",\r\n"
				+ "\"source\": \"PRECHECK\"\r\n"
				+ "}";
		return scheduleNotification;
	}
}
