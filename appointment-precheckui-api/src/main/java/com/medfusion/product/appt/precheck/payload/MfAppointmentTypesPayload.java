package com.medfusion.product.appt.precheck.payload;



public class MfAppointmentTypesPayload {
	private static MfAppointmentTypesPayload payload = new MfAppointmentTypesPayload();

	private MfAppointmentTypesPayload() {

	}

	public static MfAppointmentTypesPayload getMfAptTypesPayload() {
		return payload;
	}

	public String apptTypePayload(String apptType,String integrationId,String practiceId) {
		String aptType=" {\r\n"
				+ "  \"active\": true,\r\n"
				+ "  \"appointmentTypeId\": \""+apptType+"\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "  \"categoryId\": \"test\",\r\n"
				+ "  \"categoryName\": \"Default\",\r\n"
				+ "  \"integrationId\": \""+integrationId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return aptType;
	}
	
	public String updateApptTypePayload(String apptType,String integrationId,String practiceId) {
		String updateAptType=" {\r\n"
				+ "	\"active\": true,\r\n"
				+ "  \"appointmentTypeId\": \""+apptType+"\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type3\",\r\n"
				+ "  \"categoryId\": \"test\",\r\n"
				+ "  \"categoryName\": \"Default\",\r\n"
				+ "  \"integrationId\": \""+integrationId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return updateAptType;
	}
	
	public String updateApptTypesUuidPayload(String appTypeId,String integrationId,String practiceId) {
		String updateAptType=" {\r\n"
				+ "	\"active\": true,\r\n"
				+ "  \"appointmentTypeId\": \""+appTypeId+"\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "  \"categoryId\": \"test\",\r\n"
				+ "  \"categoryName\": \"Default\",\r\n"
				+ "  \"integrationId\": \""+integrationId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return updateAptType;
	}
	
	public String updateMorethanOneApptTypePayload(String integrationId,String practiceId) {
		String updateAptType="{\r\n"
				+ "    \"appointmentTypes\": [\r\n"
				+ "        {\r\n"
				+ "            \"active\": true,\r\n"
				+ "            \"appointmentTypeId\": \"16\",\r\n"
				+ "            \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "            \"categoryId\": \"test\",\r\n"
				+ "            \"categoryName\": \"Default\",\r\n"
				+ "            \"integrationId\": \""+integrationId+"\",\r\n"
				+ "            \"practiceId\": \""+practiceId+"\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"active\": true,\r\n"
				+ "            \"appointmentTypeId\": \"40\",\r\n"
				+ "            \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "            \"categoryId\": \"test\",\r\n"
				+ "            \"categoryName\": \"Default\",\r\n"
				+ "            \"integrationId\": \""+integrationId+"\",\r\n"
				+ "            \"practiceId\": \""+practiceId+"\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return updateAptType;
	}
	
	public String oldApptTypePayload(String apptType,String integrationId,String practiceId) {
		String aptType=" {\r\n"
				+ "  \"active\": true,\r\n"
				+ "  \"appointmentTypeId\": \""+apptType+"\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "  \"categoryId\": \"test\",\r\n"
				+ "  \"categoryName\": \"Default\",\r\n"
				+ "  \"integrationId\": \""+integrationId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return aptType;
	}
	
	public String apptTypePayload(boolean apptTypeStatus,String apptType,String integrationId,String practiceId) {
		String aptType=" {\r\n"
				+ "  \"active\": "+apptTypeStatus+",\r\n"
				+ "  \"appointmentTypeId\": \""+apptType+"\",\r\n"
				+ "  \"appointmentTypeName\": \"RX Update Exam\",\r\n"
				+ "  \"categoryId\": \"4576c5e2-453e-405f-a618-b253df9e36b0\",\r\n"
				+ "  \"categoryName\": \"Office\",\r\n"
				+ "  \"integrationId\": \""+integrationId+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return aptType;
	}
	
}
