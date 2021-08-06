package com.medfusion.product.appt.precheck.payload;

public class AptEventCollectorPayload {
	
	private static AptEventCollectorPayload payload = new AptEventCollectorPayload();
	private AptEventCollectorPayload() {
		
	}
	public static AptEventCollectorPayload getAptEvetCollectorPayload() {
		return payload;
	}
	
	
	public String getEventTypePayload(String eventId, String eventSource, String eventTime, String eventType, String practiceId ) {
		String Event_Type=" {\r\n"
				+ " \"eventId\": \""+eventId+"\",\r\n"
				+ "\"eventReason\": \"test notification\",\r\n"
				+ "  \"eventSource\": \""+eventSource+"\",\r\n"
						+ ""
				+ "  \"eventTime\": \""+eventTime+"\",\r\n"
				+ "  \"eventType\": \""+eventType+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return Event_Type;
	}
	
	public String getEventIncorrectTimePayload(String eventId, String eventSource, String eventIncorrectTime, String eventType, String practiceId ) {
		String Event_Type=" {\r\n"
				+ " \"eventId\": \""+eventId+"\",\r\n"
				+ "\"eventReason\": \"test notification\",\r\n"
				+ "  \"eventSource\": \""+eventSource+"\",\r\n"
						+ ""
				+ "  \"eventTime\": \""+eventIncorrectTime+"\",\r\n"
				+ "  \"eventType\": \""+eventType+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return Event_Type;
	}
	
	public String getEventSourceMissingPayload(String eventId, String eventSource, String eventIncorrectTime, String eventType, String practiceId ) {
		String Event_Type=" {\r\n"
				+ " \"eventId\": \""+eventId+"\",\r\n"
				+ "\"eventReason\": \"test notification\",\r\n"
				+ "  \"eventSource\": \""+eventSource+"\",\r\n"
						+ ""
				+ "  \"eventTime\": \""+eventIncorrectTime+"\",\r\n"
				+ "  \"eventType\": \""+eventType+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return Event_Type;
	}
	
}
