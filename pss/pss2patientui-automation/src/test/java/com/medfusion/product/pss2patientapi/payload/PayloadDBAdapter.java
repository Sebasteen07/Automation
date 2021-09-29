// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadDBAdapter {
	
	public String saveAnnouncementPayload(int id, String type,String code ) {
		String saveAnn = "{\r\n"
				+ "    \"id\": "+id+",\r\n"
				+ "    \"text\": {\r\n"
				+ "        \"EN\": \"Good Night\",\r\n"
				+ "        \"ES\": \"Espanol Good Night\"\r\n"
				+ "    },\r\n"
				+ "    \"display\": \"Your welcome message will be displayed here. Please contact Medfusion support to add your own text or disable this message.\",\r\n"
				+ "    \"type\": \""+type+"\",\r\n"
				+ "    \"code\": \""+code+"\",\r\n"
				+ "    \"selected\": true,\r\n"
				+ "    \"description\": \"Displayed in dialog when PSS first page loads\"\r\n"
				+ "}";
		return saveAnn;
	}
	
	
	public String customDataInvalidPayload() {
		String customData ="";
		return customData;
	}

}
