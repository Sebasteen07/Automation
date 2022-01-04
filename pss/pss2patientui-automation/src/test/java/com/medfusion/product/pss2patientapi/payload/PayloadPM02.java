package com.medfusion.product.pss2patientapi.payload;

public class PayloadPM02 {
	
	public String avaliableSlot(String currentDate)
	{
	String payload="{\r\n"
	+ " \"specialty\": null,\r\n"
	+ " \"location\": 206321,\r\n"
	+ " \"book\": null,\r\n"
	+ " \"appointmentType\": 206528,\r\n"
	+ " \"startDateTime\": \""+currentDate+"\",\r\n"
	+ " \"slotId\": null,\r\n"
	+ " \"traversal\": false,\r\n"
	+ " \"patientType\": \"PT_NEW\",\r\n"
	+ " \"extApptId\": null\r\n"
	+ "}";
	return payload;

	}
	
	public String  appointmentTypesByrulePayload()
	{
		String  appmntTypesByRule="{\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"location\": 205604,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": null,\r\n"
				+ "  \"slotId\": null,\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"linkGenerationFlow\": false,\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"FN\": \"ggg\",\r\n"
				+ "    \"LN\": \"ggg\",\r\n"
				+ "    \"DOB\": \"01/01/2000\",\r\n"
				+ "    \"GENDER\": \"F\",\r\n"
				+ "    \"EMAIL\": null,\r\n"
				+ "    \"PHONE\": null,\r\n"
				+ "    \"INSID\": null,\r\n"
				+ "    \"ADDR1\": null,\r\n"
				+ "    \"ADDR2\": null,\r\n"
				+ "    \"CITY\": null,\r\n"
				+ "    \"STATE\": null,\r\n"
				+ "    \"ZIP\": null\r\n"
				+ "  },\r\n"
				+ "  \"flow\": \"loginless\"\r\n"
				+ "}";
		return appmntTypesByRule;
	}
	
	public String allowOnlineCancelPayload(String extapptid) {

		String payload = "{\r\n" + "  \"externalAppointmentId\": \""+extapptid+"\"\r\n" + "}";
		return payload;
	}
	
	public String cancelStatusPyaload() {

		String payload = "{\r\n"
				+ "  \"categoryId\": \"24A7DC4F-4DB6-427C-9FA5-EB76FA2142D0\",\r\n"
				+ "  \"extAppointmentTypeId\": \"E0F61D51-3AFB-412E-AA3C-18786D23821C\",\r\n"
				+ "  \"numberOfDays\": 10,\r\n"
				+ "  \"preventScheduling\": 0,\r\n"
				+ "  \"id\": 4235,\r\n"
				+ "  \"extApptId\": null\r\n"
				+ "}";
		return payload;
	}	
	
	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	
	public String Pyaload() {

		String payload = "";
		return payload;
	}	

}
