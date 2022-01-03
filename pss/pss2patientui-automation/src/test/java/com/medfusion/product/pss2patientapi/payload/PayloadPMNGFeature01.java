// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadPMNGFeature01 {
	
	
	public String ssoSchedulewithDectree()
	{
		String ssoPost="{\r\n"
				+ "  \"patientType\": \"PT_NEW\",\r\n"
				+ "  \"slotId\": \"0001\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": null,\r\n"
				+ "  \"appointmentType\": 4237,\r\n"
				+ "  \"location\": 201251,\r\n"
				+ "  \"startDateTime\": \"11/21/2021 13:10:00\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": true,\r\n"
				+ "  \"flow\": \"sso\",\r\n"
				+ "  \"appTypeDetail\": {\r\n"
				+ "    \"categoryId\": \"200857\",\r\n"
				+ "    \"guid\": \"68fc2ad8-4a85-48b3-a33e-d3fb279b99b1\",\r\n"
				+ "    \"appTypeId\": \"4130\"\r\n"
				+ "  },\r\n"
				+ "  \"rule\": \"T,L\",\r\n"
				+ "  \"leafNode\": \"Inso reason\"\r\n"
				+ "}";
		return ssoPost;
		
	}
	
	public String ssoSchedulewithDectree1()
	{
		String ssoPost="{\r\n"
				+ "  \"patientType\": \"PT_EXISTING\",\r\n"
				+ "  \"slotId\": \"0001\",\r\n"
				+ "  \"specialty\": null,\r\n"
				+ "  \"book\": 4309,\r\n"
				+ "  \"appointmentType\": 4237,\r\n"
				+ "  \"location\": 205604,\r\n"
				+ "  \"startDateTime\": \"01/03/2022 07:35:00\",\r\n"
				+ "  \"customQuestion\": null,\r\n"
				+ "  \"insuranceInfo\": null,\r\n"
				+ "  \"patientInfo\": {},\r\n"
				+ "  \"allowDuplicatePatient\": false,\r\n"
				+ "  \"updatePatientDetails\": {\r\n"
				+ "    \"FN\": \"anong\",\r\n"
				+ "    \"LN\": \"anong\",\r\n"
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
				+ "    \"categoryId\": \"205277\",\r\n"
				+ "    \"guid\": \"3d4a9022-0cd8-4112-b7f2-e2566ada9640\",\r\n"
				+ "    \"appTypeId\": \"4237\"\r\n"
				+ "  },\r\n"
				+ "  \"rule\": \"L,T,B\",\r\n"
				+ "  \"leafNode\": \"Inso reason\"\r\n"
				+ "}";
		return ssoPost;
		
	}

	public String ssoPostwithoutDecTree()
	{
		String ssoPostwithoutDecTree="{\r\n"
				+ "    \"apptToSchedule\": {\r\n"
				+ "        \"patientType\": \"PT_NEW\",\r\n"
				+ "        \"slotId\": \"0001\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"book\": null,\r\n"
				+ "        \"appointmentType\": 4130,\r\n"
				+ "        \"location\": 201251,\r\n"
				+ "        \"startDateTime\": \"11/21/2021 14:20:00\",\r\n"
				+ "        \"customQuestion\": null,\r\n"
				+ "        \"insuranceInfo\": null,\r\n"
				+ "        \"patientInfo\": {},\r\n"
				+ "        \"allowDuplicatePatient\": true,\r\n"
				+ "        \"flow\": \"sso\",\r\n"
				+ "        \"appTypeDetail\": null,\r\n"
				+ "        \"rule\": \"T,L\",\r\n"
				+ "        \"leafNode\": null\r\n"
				+ "    },\r\n"
				+ "    \"apptToReschedule\": {\r\n"
				+ "        \"appointmentId\": \"211054\",\r\n"
				+ "        \"cancellationMap\": {\r\n"
				+ "            \"id\": null,\r\n"
				+ "            \"name\": \"test\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return ssoPostwithoutDecTree;
		
	}		
	public String ssoPostwithoutDecTreeShowProviderOFF()
	{
		String ssoPostwithoutDecTreeShowProviderOFF="{\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": 4130,\r\n"
				+ "    \"location\": 201251,\r\n"
				+ "    \"startDateTime\": \"11/21/2021 14:30:00\",\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "    \"insuranceInfo\": null,\r\n"
				+ "    \"patientInfo\": {},\r\n"
				+ "    \"allowDuplicatePatient\": true,\r\n"
				+ "    \"flow\": \"sso\",\r\n"
				+ "    \"appTypeDetail\": null,\r\n"
				+ "    \"rule\": \"T,L\",\r\n"
				+ "    \"leafNode\": null\r\n"
				+ "},\r\n"
				+ "\"apptToReschedule\": {\r\n"
				+ "    \"appointmentId\": \"211058\",\r\n"
				+ "    \"cancellationMap\": {\r\n"
				+ "        \"id\": null,\r\n"
				+ "        \"name\": \"test\"\r\n"
				+ "    }\r\n"
				+ "}\r\n"
				+ "}";
		return ssoPostwithoutDecTreeShowProviderOFF;
		
	}
	public String ssoPostwithDecTreeShowProviderOFF()
	{
		String ssoPostwithDecTreeShowProviderOFF="{\r\n"
				+ "    \"apptToSchedule\": {\r\n"
				+ "        \"patientType\": \"PT_NEW\",\r\n"
				+ "        \"slotId\": \"0001\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"book\": null,\r\n"
				+ "        \"appointmentType\": 4130,\r\n"
				+ "        \"location\": 201251,\r\n"
				+ "        \"startDateTime\": \"11/21/2021 14:10:00\",\r\n"
				+ "        \"customQuestion\": null,\r\n"
				+ "        \"insuranceInfo\": null,\r\n"
				+ "        \"patientInfo\": {},\r\n"
				+ "        \"allowDuplicatePatient\": true,\r\n"
				+ "        \"flow\": \"sso\",\r\n"
				+ "        \"appTypeDetail\": {\r\n"
				+ "            \"categoryId\": \"200857\",\r\n"
				+ "            \"guid\": \"68fc2ad8-4a85-48b3-a33e-d3fb279b99b1\",\r\n"
				+ "            \"appTypeId\": \"4130\"\r\n"
				+ "        },\r\n"
				+ "        \"rule\": \"T,L\",\r\n"
				+ "        \"leafNode\": \"Inso reason\"\r\n"
				+ "    },\r\n"
				+ "    \"apptToReschedule\": {\r\n"
				+ "        \"appointmentId\": \"211060\",\r\n"
				+ "        \"cancellationMap\": {\r\n"
				+ "            \"id\": \"B02315FC-A614-40E2-8B96-CF5D7703A325\",\r\n"
				+ "            \"name\": \"No Referral / Authorization\"\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "}";
		return ssoPostwithDecTreeShowProviderOFF;
		
	}
	public String avaliableSlot(String currentDate)
	{
		String avaliableSlot="{\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"location\": 206321,\r\n"
				+ "    \"book\": null,\r\n"
				+ "    \"appointmentType\": 206528,\r\n"
				+ "    \"startDateTime\": \""+currentDate+"\",\r\n"
				+ "    \"slotId\": null,\r\n"
				+ "    \"traversal\": false,\r\n"
				+ "    \"patientType\": \"PT_NEW\",\r\n"
				+ "    \"extApptId\": null\r\n"
				+ "}";
		return avaliableSlot;
		
	}
	
	public String scheduleApp(String currentDate)
	{
		String scheduleApp="{\r\n"
				+ "    \"patientType\": \"PT_ALL\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": 205662,\r\n"
				+ "    \"appointmentType\": 205754,\r\n"
				+ "    \"location\": 205604,\r\n"
				+ "    \"startDateTime\": \""+currentDate+"\",\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "    \"insuranceInfo\": null,\r\n"
				+ "    \"patientInfo\": {},\r\n"
				+ "    \"allowDuplicatePatient\": false,\r\n"
				+ "    \"flow\": \"anonymous\",\r\n"
				+ "    \"appTypeDetail\": null,\r\n"
				+ "    \"rule\": \"L,T,B\",\r\n"
				+ "    \"leafNode\": null\r\n"
				+ "}";
		return scheduleApp;
		
	}
	
	public String scheduleAppForPTNEW(String currentDate)
	{
		String scheduleApp="{\r\n"
				+ "    \"patientType\": \"PT_ALL\",\r\n"
				+ "    \"slotId\": \"0001\",\r\n"
				+ "    \"specialty\": null,\r\n"
				+ "    \"book\": 205662,\r\n"
				+ "    \"appointmentType\": 205754,\r\n"
				+ "    \"location\": 205604,\r\n"
				+ "    \"startDateTime\": \""+currentDate+"\",\r\n"
				+ "    \"customQuestion\": null,\r\n"
				+ "    \"insuranceInfo\": null,\r\n"
				+ "    \"patientInfo\": {},\r\n"
				+ "    \"allowDuplicatePatient\": false,\r\n"
				+ "    \"flow\": \"anonymous\",\r\n"
				+ "    \"appTypeDetail\": null,\r\n"
				+ "    \"rule\": \"L,T,B\",\r\n"
				+ "    \"leafNode\": null\r\n"
				+ "}";
		return scheduleApp;
		
	}
public String schedulewithoutProvider()
{
	String schedulewithoutProvider="{\r\n"
			+ "  \"patientType\": \"PT_EXISTING\",\r\n"
			+ "  \"slotId\": \"0001\",\r\n"
			+ "  \"specialty\": null,\r\n"
			+ "  \"book\": null,\r\n"
			+ "  \"appointmentType\": 4237,\r\n"
			+ "  \"location\": 205604,\r\n"
			+ "  \"startDateTime\": \"01/03/2022 09:10:00\",\r\n"
			+ "  \"customQuestion\": null,\r\n"
			+ "  \"insuranceInfo\": null,\r\n"
			+ "  \"patientInfo\": {},\r\n"
			+ "  \"allowDuplicatePatient\": false,\r\n"
			+ "  \"updatePatientDetails\": {\r\n"
			+ "    \"FN\": \"anong\",\r\n"
			+ "    \"LN\": \"anong\",\r\n"
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
	return schedulewithoutProvider;
	
}
}
