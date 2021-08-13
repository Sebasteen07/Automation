package com.medfusion.product.appt.precheck.payload;



public class MfAppointmentTypesPayload {
	private static MfAppointmentTypesPayload payload = new MfAppointmentTypesPayload();

	private MfAppointmentTypesPayload() {

	}

	public static MfAppointmentTypesPayload getMfAptTypesPayload() {
		return payload;
	}

	public String apptTypePayload(String uuid,String practiceId) {
		String aptType=" {\r\n"
				+ "  \"id\": \""+uuid+"\",\r\n"
				+ "  \"appointmentTypeId\": \"16\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
				+ "  \"categoryId\": \"test\",\r\n"
				+ "  \"categoryName\": \"Default\",\r\n"
				+ "	\"active\": true,\r\n"
				+ "  \"integrationId\": \"9\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return aptType;
	}
	public String updateApptTypePayload(String integrationId,String practiceId) {
		String updateAptType=" {\r\n"
				+ "	\"active\": true,\r\n"
				+ "  \"appointmentTypeId\": \"16\",\r\n"
				+ "  \"appointmentTypeName\": \"TestOne Apt Type\",\r\n"
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
	
}
