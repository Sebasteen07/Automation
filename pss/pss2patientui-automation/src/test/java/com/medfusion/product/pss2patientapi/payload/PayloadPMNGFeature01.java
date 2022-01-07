// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadPMNGFeature01 {
	
	
	public String ssoScheduleWithCatShowProviderON(String slotId, String date, String slotTime,String locationId,String appId,String bookId)
	{
		String ssoScheduleWithCatShowProviderON="{\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+slotId+"\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": "+bookId+",\r\n"
				+ "  \"appointmentType\": "+appId+",\r\n"
				+ "  \"location\": "+locationId+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slotTime+"\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"flow\": \"sso\",\r\n"
				+ "  \"appTypeDetail\": {\r\n"
				+ "    \"categoryId\": \"210138\",\r\n"
				+ "    \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
				+ "    \"appTypeId\": \"4237\"\r\n"
				+ "  },\r\n"
				+ "  \"rule\": \"L,T,B\",\r\n"
				+ "  \"leafNode\": null\r\n"
				+ "}";
		return ssoScheduleWithCatShowProviderON;
		
	}
	
	public String ssoScheduleWithoutCatShowProviderON(String slotId, String date, String slotTime,String locationId,String appId,String bookId)
	{
		String ssoScheduleWithoutCatShowProviderON="{\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+slotId+"\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": "+bookId+",\r\n"
				+ "  \"appointmentType\": "+appId+",\r\n"
				+ "  \"location\": "+locationId+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slotTime+"\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"flow\": \"sso\",\r\n"
				+ "  \"appTypeDetail\": null,\r\n"
				+ "  \"rule\": \"L,T,B\",\r\n"
				+ "  \"leafNode\": null\r\n"
				+ "}";
		return ssoScheduleWithoutCatShowProviderON;
		
	}

	public String ssoScheduleWithCatShowProviderOFF(String slotId, String date, String slotTime,String locationId,String appId)
	{
		String ssoScheduleWithCatShowProviderOFF="{\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+slotId+"\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": "+appId+",\r\n"
				+ "  \"location\": "+locationId+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slotTime+"\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"flow\": \"sso\",\r\n"
				+ "  \"appTypeDetail\": {\r\n"
				+ "    \"categoryId\": \"210138\",\r\n"
				+ "    \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
				+ "    \"appTypeId\": \"4237\"\r\n"
				+ "  },\r\n"
				+ "  \"rule\": \"L,T\",\r\n"
				+ "  \"leafNode\": null\r\n"
				+ "}";
		return ssoScheduleWithCatShowProviderOFF;
		
	}		
	public String ssoScheduleWithoutCatShowProviderOFF(String slotId, String date, String slotTime,String locationId,String appId)
	{
		String ssoScheduleWithoutCatShowProviderOFF="{\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \""+slotId+"\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": "+appId+",\r\n"
				+ "  \"location\": "+locationId+",\r\n"
				+ "    \"startDateTime\": \""+date+" "+slotTime+"\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"flow\": \"sso\",\r\n"
				+ "  \"appTypeDetail\": null,\r\n"
				+ "  \"rule\": \"L,T\",\r\n"
				+ "  \"leafNode\": null\r\n"
				+ "}";
		return ssoScheduleWithoutCatShowProviderOFF;
		
	}


	


public String  availableslotsPayloadLT(String date, String locationid, String apptid)
{
	String  availableSlots="{\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"slotId\": \"0001\",\r\n"
			+ "    \"traversal\": false,\r\n"
			+ "    \"patientType\": \"PT_NEW\",\r\n"
			+ "    \"extApptId\": null\r\n"
			+ "}";
	return availableSlots;
}

public String  availableslotsPayloadLTB(String date, String locationid, String apptid,String bookId)
{
	String  availableSlots="{\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"book\": "+bookId+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"slotId\": null,\r\n"
			+ "    \"traversal\": false,\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"extApptId\": null\r\n"
			+ "}";
	return availableSlots;
}


public String  anoscheduleAppointmentPayloadWithDecTreeLT(String slotid, String date,String slottime,String locationid, String apptid)
{   
	String  scheduleAppointment="{\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "        \"categoryId\": \"210138\",\r\n"
			+ "        \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "        \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"L,T\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  anoscheduleAppointmentPayloadWithoutDecTreeLT(String slotid, String date,String slottime,String locationid, String apptid)
{   
	String  scheduleAppointment="{\r\n"
			+ "  \"patientType\": \"PT_ALL\",\r\n"
			+ "  \"slotId\": \""+slotid+"\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": null,\r\n"
			+ "  \"appointmentType\": "+apptid+",\r\n"
			+ "  \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"flow\": \"anonymous\",\r\n"
			+ "  \"appTypeDetail\": null,\r\n"
			+ "  \"rule\": \"L,T\",\r\n"
			+ "  \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  anoScheduleAppointmentPayloadLTBWithDecTree(String slotid, String date,String slottime,String locationid, String apptid,String bookId)
{   
	String  scheduleAppointment="{\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": "+bookId+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "        \"categoryId\": \"210138\",\r\n"
			+ "        \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "        \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"L,T,B\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  anoScheduleAppointmentPayloadLTBWithoutDecTree(String slotid, String date,String slottime,String locationid, String apptid,String bookId)
{   
	String  scheduleAppointment="{\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": "+bookId+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": null,\r\n"
			+ "    \"rule\": \"L,T,B\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  llScheduleAppointmentPayloadLTBWithoutDecTree(String slotid, String date,String slottime,String locationid, String apptid,String bookId)
{   
	String  scheduleAppointment="{\r\n"
			+ "  \"patientType\": \"PT_NEW\",\r\n"
			+ "  \"slotId\": \""+slotid+"\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": "+bookId+",\r\n"
			+ "  \"appointmentType\": "+apptid+",\r\n"
			+ "  \"location\": "+locationid+",\r\n"
			+ "  \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"updatePatientDetails\": {\r\n"
			+ "    \"FN\": \"ngpm\",\r\n"
			+ "    \"LN\": \"patient\",\r\n"
			+ "    \"DOB\": \"01/01/2000\",\r\n"
			+ "    \"GENDER\": \"M\",\r\n"
			+ "    \"EMAIL\": null,\r\n"
			+ "    \"PHONE\": null,\r\n"
			+ "    \"INSID\": null,\r\n"
			+ "    \"ADDR1\": null,\r\n"
			+ "    \"ADDR2\": null,\r\n"
			+ "    \"CITY\": null,\r\n"
			+ "    \"STATE\": null,\r\n"
			+ "    \"ZIP\": null\r\n"
			+ "  },\r\n"
			+ "  \"flow\": \"loginless\",\r\n"
			+ "  \"appTypeDetail\": null,\r\n"
			+ "  \"rule\": \"L,T,B\",\r\n"
			+ "  \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  llScheduleAppointmentPayloadLTBWithDecTree(String slotid, String date,String slottime,String locationid, String apptid,String bookId)
{   
	String  scheduleAppointment="{\r\n"
			+ "  \"patientType\": \"PT_NEW\",\r\n"
			+ "  \"slotId\": \""+slotid+"\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": "+bookId+",\r\n"
			+ "  \"appointmentType\": "+apptid+",\r\n"
			+ "  \"location\": "+locationid+",\r\n"
			+ "  \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"updatePatientDetails\": {\r\n"
			+ "    \"FN\": \"ngpm\",\r\n"
			+ "    \"LN\": \"patient\",\r\n"
			+ "    \"DOB\": \"01/01/2000\",\r\n"
			+ "    \"GENDER\": \"M\",\r\n"
			+ "    \"EMAIL\": null,\r\n"
			+ "    \"PHONE\": null,\r\n"
			+ "    \"INSID\": null,\r\n"
			+ "    \"ADDR1\": null,\r\n"
			+ "    \"ADDR2\": null,\r\n"
			+ "    \"CITY\": null,\r\n"
			+ "    \"STATE\": null,\r\n"
			+ "    \"ZIP\": null\r\n"
			+ "  },\r\n"
			+ "  \"flow\": \"loginless\",\r\n"
			+ "  \"appTypeDetail\": {\r\n"
			+ "    \"categoryId\": \"210138\",\r\n"
			+ "    \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "    \"appTypeId\": \"4237\"\r\n"
			+ "  },\r\n"
			+ "  \"rule\": \"L,T,B\",\r\n"
			+ "  \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  llScheduleAppointmentPayloadLTWithDecTree(String slotid, String date,String slottime,String locationid, String apptid)
{   
	String  scheduleAppointment="{\r\n"
			+ "  \"patientType\": \"PT_NEW\",\r\n"
			+ "  \"slotId\": \""+slotid+"\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": null,\r\n"
			+ "  \"appointmentType\": "+apptid+",\r\n"
			+ "  \"location\": "+locationid+",\r\n"
			+ "  \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"updatePatientDetails\": {\r\n"
			+ "    \"FN\": \"ngpm\",\r\n"
			+ "    \"LN\": \"patient\",\r\n"
			+ "    \"DOB\": \"01/01/2000\",\r\n"
			+ "    \"GENDER\": \"M\",\r\n"
			+ "    \"EMAIL\": null,\r\n"
			+ "    \"PHONE\": null,\r\n"
			+ "    \"INSID\": null,\r\n"
			+ "    \"ADDR1\": null,\r\n"
			+ "    \"ADDR2\": null,\r\n"
			+ "    \"CITY\": null,\r\n"
			+ "    \"STATE\": null,\r\n"
			+ "    \"ZIP\": null\r\n"
			+ "  },\r\n"
			+ "  \"flow\": \"loginless\",\r\n"
			+ "  \"appTypeDetail\": {\r\n"
			+ "    \"categoryId\": \"210138\",\r\n"
			+ "    \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "    \"appTypeId\": \"4237\"\r\n"
			+ "  },\r\n"
			+ "  \"rule\": \"L,T\",\r\n"
			+ "  \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String  llScheduleAppointmentPayloadLTWithoutDecTree(String slotid, String date,String slottime,String locationid, String apptid)
{   
	String  scheduleAppointment="{\r\n"
			+ "  \"patientType\": \"PT_NEW\",\r\n"
			+ "  \"slotId\": \""+slotid+"\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": null,\r\n"
			+ "  \"appointmentType\": "+apptid+",\r\n"
			+ "  \"location\": "+locationid+",\r\n"
			+ "  \"startDateTime\": \""+date+" "+slottime+"\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"updatePatientDetails\": {\r\n"
			+ "    \"FN\": \"ngpm\",\r\n"
			+ "    \"LN\": \"patient\",\r\n"
			+ "    \"DOB\": \"01/01/2000\",\r\n"
			+ "    \"GENDER\": \"M\",\r\n"
			+ "    \"EMAIL\": null,\r\n"
			+ "    \"PHONE\": null,\r\n"
			+ "    \"INSID\": null,\r\n"
			+ "    \"ADDR1\": null,\r\n"
			+ "    \"ADDR2\": null,\r\n"
			+ "    \"CITY\": null,\r\n"
			+ "    \"STATE\": null,\r\n"
			+ "    \"ZIP\": null\r\n"
			+ "  },\r\n"
			+ "  \"flow\": \"loginless\",\r\n"
			+ "  \"appTypeDetail\": null,\r\n"
			+ "  \"rule\": \"L,T\",\r\n"
			+ "  \"leafNode\": null\r\n"
			+ "}";
	return scheduleAppointment;
}

public String rescheduleWithCatLT(String slotid, String date,String confirmNo,String locationid, String apptid)
{
	String rescheduleWithCatLT="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_NEW\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atulaa\",\r\n"
			+ "      \"GENDER\": \"M\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"loginless\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "      \"categoryId\": \"210138\",\r\n"
			+ "      \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "      \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"L,T\",\r\n"
			+ "    \"leafNode\": \"[ANSWER1]\"\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithCatLT;
	
}

public String rescheduleWithoutCatLT(String slotid, String date,String confirmNo,String locationid, String apptid)
{
	String rescheduleWithoutCatLT="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_NEW\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atulaa\",\r\n"
			+ "      \"GENDER\": \"M\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"loginless\",\r\n"
			+ "    \"appTypeDetail\": null,\r\n"
			+ "    \"rule\": \"L,T\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": \"B02315FC-A614-40E2-8B96-CF5D7703A325\",\r\n"
			+ "      \"name\": \"No Referral / Authorization\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithoutCatLT;
	
}

public String rescheduleWithCatLTB(String slotid, String date,String confirmNo,String locationid, String apptid,String bookid)
{
	String rescheduleWithCatLTB="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_NEW\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": "+bookid+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {},\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atulaa\",\r\n"
			+ "      \"GENDER\": \"M\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"loginless\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "      \"categoryId\": \"210138\",\r\n"
			+ "      \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "      \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"L,T,B\",\r\n"
			+ "    \"leafNode\": \"[ANSWER1]\"\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithCatLTB;
	
}

public String anoRescheduleWithCatLTB(String slotid, String date,String confirmNo,String locationid, String apptid,String bookid)
{
	String rescheduleWithCatLTB="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": "+bookid+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {\r\n"
			+ "      \"patientMatches\": [\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"first name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"FN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your first name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 1\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"last name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"LN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your last name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 2\r\n"
			+ "        }\r\n"
			+ "      ],\r\n"
			+ "      \"type\": \"ANONYMOUS\",\r\n"
			+ "      \"allowDuplicatePatient\": false\r\n"
			+ "    },\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atul\",\r\n"
			+ "      \"LN\": \"ano\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "      \"categoryId\": \"210138\",\r\n"
			+ "      \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "      \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"T,L,B\",\r\n"
			+ "    \"leafNode\": \"[ANSWER1]\"\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithCatLTB;
	
}

public String anoRescheduleWithoutCatLTB(String slotid, String date,String confirmNo,String locationid, String apptid,String bookid)
{
	String rescheduleWithCatLTB="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": "+bookid+",\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {\r\n"
			+ "      \"patientMatches\": [\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"first name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"FN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your first name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 1\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"last name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"LN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your last name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 2\r\n"
			+ "        }\r\n"
			+ "      ],\r\n"
			+ "      \"type\": \"ANONYMOUS\",\r\n"
			+ "      \"allowDuplicatePatient\": false\r\n"
			+ "    },\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atul\",\r\n"
			+ "      \"LN\": \"ano11\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": null,\r\n"
			+ "    \"rule\": \"L,T,B\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithCatLTB;
	
}

public String anoRescheduleWithCatLT(String slotid, String date,String confirmNo,String locationid, String apptid)
{
	String rescheduleWithCatLTB="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {\r\n"
			+ "      \"patientMatches\": [\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"first name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"FN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your first name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 1\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"last name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"LN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your last name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 2\r\n"
			+ "        }\r\n"
			+ "      ],\r\n"
			+ "      \"type\": \"ANONYMOUS\",\r\n"
			+ "      \"allowDuplicatePatient\": false\r\n"
			+ "    },\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atul\",\r\n"
			+ "      \"LN\": \"ano11\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": {\r\n"
			+ "      \"categoryId\": \"210138\",\r\n"
			+ "      \"guid\": \"3dd1053e-70a6-46cf-aa70-8eb19c2e5e7c\",\r\n"
			+ "      \"appTypeId\": \"4237\"\r\n"
			+ "    },\r\n"
			+ "    \"rule\": \"L,T\",\r\n"
			+ "    \"leafNode\": \"[ANSWER1]\"\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return rescheduleWithCatLTB;
	
}
public String anoRescheduleWithoutCatLT(String slotid, String date,String confirmNo,String locationid, String apptid)
{
	String anoRescheduleWithoutCatLT="{\r\n"
			+ "  \"apptToSchedule\": {\r\n"
			+ "    \"patientType\": \"PT_ALL\",\r\n"
			+ "    \"slotId\": \""+slotid+"\",\r\n"
			+ "    \"specialty\": null,\r\n"
			+ "    \"book\": null,\r\n"
			+ "    \"appointmentType\": "+apptid+",\r\n"
			+ "    \"location\": "+locationid+",\r\n"
			+ "    \"startDateTime\": \""+date+"\",\r\n"
			+ "    \"customQuestion\": null,\r\n"
			+ "    \"insuranceInfo\": null,\r\n"
			+ "    \"patientInfo\": {\r\n"
			+ "      \"patientMatches\": [\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"first name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"FN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your first name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 1\r\n"
			+ "        },\r\n"
			+ "        {\r\n"
			+ "          \"entity\": \"last name\",\r\n"
			+ "          \"isMandatory\": true,\r\n"
			+ "          \"code\": \"LN\",\r\n"
			+ "          \"value\": null,\r\n"
			+ "          \"selected\": true,\r\n"
			+ "          \"search\": true,\r\n"
			+ "          \"error\": \"\",\r\n"
			+ "          \"message\": \"Please enter your last name\",\r\n"
			+ "          \"regex\": \"^(?!\\\\s*$).+\",\r\n"
			+ "          \"type\": \"TEXT\",\r\n"
			+ "          \"key\": null,\r\n"
			+ "          \"seq\": 2\r\n"
			+ "        }\r\n"
			+ "      ],\r\n"
			+ "      \"type\": \"ANONYMOUS\",\r\n"
			+ "      \"allowDuplicatePatient\": false\r\n"
			+ "    },\r\n"
			+ "    \"allowDuplicatePatient\": false,\r\n"
			+ "    \"updatePatientDetails\": {\r\n"
			+ "      \"FN\": \"atul\",\r\n"
			+ "      \"LN\": \"ano11\"\r\n"
			+ "    },\r\n"
			+ "    \"flow\": \"anonymous\",\r\n"
			+ "    \"appTypeDetail\": null,\r\n"
			+ "    \"rule\": \"L,T\",\r\n"
			+ "    \"leafNode\": null\r\n"
			+ "  },\r\n"
			+ "  \"apptToReschedule\": {\r\n"
			+ "    \"appointmentId\": \""+confirmNo+"\",\r\n"
			+ "    \"cancellationMap\": {\r\n"
			+ "      \"id\": null,\r\n"
			+ "      \"name\": \"otherCancelReason\"\r\n"
			+ "    }\r\n"
			+ "  }\r\n"
			+ "}";
	return anoRescheduleWithoutCatLT;
	
}

public String specialityPost() {
	String specialityPost = "{\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"location\": null,\r\n"
			+ "  \"book\": null,\r\n"
			+ "  \"appointmentType\": null,\r\n"
			+ "  \"slotId\": null,\r\n"
			+ "  \"patientType\": \"PT_EXISTING\",\r\n"
			+ "  \"linkGenerationFlow\": false,\r\n"
			+ "  \"patientDetails\": {\r\n"
			+ "    \"FN\": \"ngpm\",\r\n"
			+ "    \"LN\": \"patient\",\r\n"
			+ "    \"DOB\": \"01/01/2000\",\r\n"
			+ "    \"GENDER\": \"M\",\r\n"
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
	return specialityPost;

}
}
