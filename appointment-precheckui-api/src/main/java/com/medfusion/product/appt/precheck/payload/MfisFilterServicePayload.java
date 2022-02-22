package com.medfusion.product.appt.precheck.payload;

public class MfisFilterServicePayload {
	private static MfisFilterServicePayload payload = new MfisFilterServicePayload();

	private MfisFilterServicePayload() {

	}

	public static MfisFilterServicePayload getMfisFilterServicePayload() {
		return payload;
	}
	
	public String filterPracticeIdPayload() {
		String filterPracticeId = "{\r\n"
				+ "  \"blackList\": \"false\",\r\n"
				+ "  \"filters\": [\r\n"
				+ "    {\r\n"
				+ "      \"filterKey\": \"AppointmentStatus\",\r\n"
				+ "      \"filterValue\": \"booked\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return filterPracticeId;
	}
	
	public String filterPracticeIdPutPayload(String filterSetId) {
		String filterPracticeId = "{\r\n"
				+ " \"filterSetId\": \""+filterSetId+"\",\r\n"
				+ " \r\n"
				+ "  \"blackList\": \"true\",\r\n"
				+ "  \"filters\": [\r\n"
				+ "{\r\n"
				+ "            \"filterKey\" : \"AppointmentLocation\",\r\n"
				+ "            \"filterValue\" : \"PSS WALA\"\r\n"
				+ "        }, \r\n"
				+ "        {\r\n"
				+ "            \"filterKey\" : \"AppointmentType\",\r\n"
				+ "            \"filterValue\" : \"PSS WALA\"\r\n"
				+ "        }, \r\n"
				+ "        {\r\n"
				+ "            \"filterKey\" : \"Resource\",\r\n"
				+ "            \"filterValue\" : \"PSS WALA\"\r\n"
				+ "        }\r\n"
				+ "     ]\r\n"
				+ "}\r\n"
				+ "";
		return filterPracticeId;
	}
	
	public String mappingFilterPracticeIdPayload() {
		String filterPracticeId = "{\r\n"
				+ "  \"statusValues\": [\r\n"
				+ "\"PATIENTCANCELLED\",\"DOCTORSICK\",\"DOCTORONVACATION\"\r\n"
				+ "  ]\r\n"
				+ " \r\n"
				+ "}";
		return filterPracticeId;
	}
	
	public String mappingFilterPracticeIdPutPayload(String mappingFilterId) {
		String filterPracticeId = "{\r\n"
				+ "\r\n"
				+ "\"statusValues\":[\"Zombies\"],\r\n"
				+ "\"mappingFilterId\": \""+mappingFilterId+"\"\r\n"
				+ "}\r\n"
				+ "";
		return filterPracticeId;
	}
	
	public String mappingPracticeIdMapsPayload(String practiceId) {
		String filterPracticeId = "{\r\n"
				+ "\r\n"
				+ "  \"maps\": [\r\n"
				+ "    {\r\n"
				+ "      \"toValue\": \"changed location\",\r\n"
				+ "      \"mapType\": \"location\",\r\n"
				+ "      \"originalValues\": [\"some location\", \r\n"
				+ "\"primary location\", \r\n"
				+ "\"test location\"\r\n"
				+ "      ]\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return filterPracticeId;
	}
	
	public String mappingPracticeIdPutPayload(String resourceMapSetId,String practiceId) {
		String filterPracticeId = "{\r\n"
				+ "  \"resourceMapSetId\": \""+resourceMapSetId+"\",\r\n"
				+ "  \"maps\": [\r\n"
				+ "    {\r\n"
				+ "\"mapType\" : \"location\",\r\n"
				+ "\"originalValues\" : [ \r\n"
				+ "\"some location\", \r\n"
				+ "\"primary location\", \r\n"
				+ "\"test location\"\r\n"
				+ "            ],\r\n"
				+ "\"toValue\" : \"changed location\"\r\n"
				+ "        }, \r\n"
				+ "{\r\n"
				+ "\"mapType\" : \"provider\",\r\n"
				+ "\"originalValues\" : [ \r\n"
				+ "\"unknown new\", \r\n"
				+ "\"provider new\", \r\n"
				+ "\"someone new\"\r\n"
				+ "            ],\r\n"
				+ "\"toValue\" : \"changed provider\"\r\n"
				+ "        }\r\n"
				+ "  ],\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return filterPracticeId;
	}
}
