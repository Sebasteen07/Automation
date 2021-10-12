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
	
	
	public String saveBookPayload_new() {
		String payload ="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 4303,\r\n"
				+ "        \"name\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "        \"displayName\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "        \"emailAddress\": null,\r\n"
				+ "        \"extBookId\": \"FB84CDED-8A67-4A40-81F8-9401F59DC6C6\",\r\n"
				+ "        \"acceptComment\": true,\r\n"
				+ "        \"acceptEmail\": false,\r\n"
				+ "        \"acceptNew\": false,\r\n"
				+ "        \"ageRule\": \"\",\r\n"
				+ "        \"deleted\": false,\r\n"
				+ "        \"providerMessage\": null,\r\n"
				+ "        \"sharePatients\": true,\r\n"
				+ "        \"slotSize\": 5,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"categoryId\": \"730C6F5E-4F8C-482F-BB81-F6050841FD8D\",\r\n"
				+ "        \"categoryName\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"bookTranslations\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": null,\r\n"
				+ "                \"language\": {\r\n"
				+ "                    \"code\": \"EN\",\r\n"
				+ "                    \"name\": null,\r\n"
				+ "                    \"flag\": null\r\n"
				+ "                },\r\n"
				+ "                \"displayName\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "                \"createdUserId\": null,\r\n"
				+ "                \"updatedUserId\": null\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"bookSort\": null,\r\n"
				+ "        \"bookType\": {\r\n"
				+ "            \"code\": \"RS_PERSON\",\r\n"
				+ "            \"name\": \"PERSON\",\r\n"
				+ "            \"grouptype\": \"RESOURCE\"\r\n"
				+ "        },\r\n"
				+ "        \"bookLevel\": {\r\n"
				+ "            \"code\": \"RS_NONE\",\r\n"
				+ "            \"name\": \"None\",\r\n"
				+ "            \"grouptype\": \"RESOURCE_LEVEL\"\r\n"
				+ "        },\r\n"
				+ "        \"careteam\": null,\r\n"
				+ "        \"createdUserId\": null,\r\n"
				+ "        \"updatedUserId\": null,\r\n"
				+ "        \"links\": [\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"LG_ANO\",\r\n"
				+ "                \"extBookId\": \"FB84CDED-8A67-4A40-81F8-9401F59DC6C6\",\r\n"
				+ "                \"guid\": \"43e5be88-7321-47ee-87bf-b86c8fe6d724\",\r\n"
				+ "                \"extBookCategoryId\": \"730C6F5E-4F8C-482F-BB81-F6050841FD8D\",\r\n"
				+ "                \"deleted\": false\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"LG_LOG\",\r\n"
				+ "                \"extBookId\": \"FB84CDED-8A67-4A40-81F8-9401F59DC6C6\",\r\n"
				+ "                \"guid\": \"1c4ccc09-9381-464b-a972-39cda6230047\",\r\n"
				+ "                \"extBookCategoryId\": \"730C6F5E-4F8C-482F-BB81-F6050841FD8D\",\r\n"
				+ "                \"deleted\": false\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;
	}
	
	public String createBook() {
		String payload ="[\r\n"
				+ "    {\r\n"
				+ "        \"name\": \"Gates1 Bil [BilGate1]\",\r\n"
				+ "        \"displayName\": \"Gates1 Bil [BilGate1]\",\r\n"
				+ "        \"emailAddress\": null,\r\n"
				+ "        \"extBookId\": \"89C26EFE-5572-4A61-B550-07FE979977DC\",\r\n"
				+ "        \"acceptComment\": false,\r\n"
				+ "        \"acceptEmail\": false,\r\n"
				+ "        \"acceptNew\": false,\r\n"
				+ "        \"ageRule\": \"\",\r\n"
				+ "        \"deleted\": false,\r\n"
				+ "        \"providerMessage\": null,\r\n"
				+ "        \"sharePatients\": false,\r\n"
				+ "        \"slotSize\": 0,\r\n"
				+ "        \"status\": false,\r\n"
				+ "        \"categoryId\": \"A8D202D1-17A9-4BD7-B7A2-49AAE945157C\",\r\n"
				+ "        \"categoryName\": \"Gates1 Bil [BilGate1]\",\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"bookTranslations\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": null,\r\n"
				+ "                \"language\": {\r\n"
				+ "                    \"code\": \"EN\",\r\n"
				+ "                    \"name\": null,\r\n"
				+ "                    \"flag\": null\r\n"
				+ "                },\r\n"
				+ "                \"displayName\": \"Gates1 Bil [BilGate1]\",\r\n"
				+ "                \"createdUserId\": null,\r\n"
				+ "                \"updatedUserId\": null\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"bookSort\": null,\r\n"
				+ "        \"bookType\": {\r\n"
				+ "            \"code\": \"RS_PERSON\",\r\n"
				+ "            \"name\": \"PERSON\",\r\n"
				+ "            \"grouptype\": \"RESOURCE\"\r\n"
				+ "        },\r\n"
				+ "        \"bookLevel\": {\r\n"
				+ "            \"code\": \"RS_NONE\",\r\n"
				+ "            \"name\": \"None\",\r\n"
				+ "            \"grouptype\": \"RESOURCE_LEVEL\"\r\n"
				+ "        },\r\n"
				+ "        \"careteam\": null,\r\n"
				+ "        \"createdUserId\": null,\r\n"
				+ "        \"updatedUserId\": null,\r\n"
				+ "        \"links\": [\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"LG_ANO\",\r\n"
				+ "                \"extBookId\": \"89C26EFE-5572-4A61-B550-07FE979977DC\",\r\n"
				+ "                \"guid\": \"42ae37f3-3d22-40c9-8089-4cf0ad8b1ffb\",\r\n"
				+ "                \"extBookCategoryId\": \"A8D202D1-17A9-4BD7-B7A2-49AAE945157C\",\r\n"
				+ "                \"deleted\": false\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"type\": \"LG_LOG\",\r\n"
				+ "                \"extBookId\": \"89C26EFE-5572-4A61-B550-07FE979977DC\",\r\n"
				+ "                \"guid\": \"60d65f80-09a6-4f63-9364-7bf177f895b6\",\r\n"
				+ "                \"extBookCategoryId\": \"A8D202D1-17A9-4BD7-B7A2-49AAE945157C\",\r\n"
				+ "                \"deleted\": false\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;
	}
	
	public String saveBookLocationPayload() {
		String payload ="{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": 206457,\r\n"
				+ "        \"name\": null,\r\n"
				+ "        \"displayName\": null,\r\n"
				+ "        \"emailAddress\": null,\r\n"
				+ "        \"extBookId\": null,\r\n"
				+ "        \"acceptComment\": false,\r\n"
				+ "        \"acceptEmail\": false,\r\n"
				+ "        \"acceptNew\": false,\r\n"
				+ "        \"ageRule\": null,\r\n"
				+ "        \"deleted\": false,\r\n"
				+ "        \"providerMessage\": null,\r\n"
				+ "        \"sharePatients\": false,\r\n"
				+ "        \"slotSize\": 0,\r\n"
				+ "        \"status\": false,\r\n"
				+ "        \"categoryId\": null,\r\n"
				+ "        \"categoryName\": null,\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"bookTranslations\": null,\r\n"
				+ "        \"bookSort\": null,\r\n"
				+ "        \"bookType\": null,\r\n"
				+ "        \"bookLevel\": null,\r\n"
				+ "        \"careteam\": null,\r\n"
				+ "        \"createdUserId\": null,\r\n"
				+ "        \"updatedUserId\": null\r\n"
				+ "    },\r\n"
				+ "    \"location\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4139,\r\n"
				+ "            \"displayName\": null,\r\n"
				+ "            \"extLocationId\": null,\r\n"
				+ "            \"name\": null,\r\n"
				+ "            \"description\": null,\r\n"
				+ "            \"directionUrl\": null,\r\n"
				+ "            \"address\": null,\r\n"
				+ "            \"timeZone\": null,\r\n"
				+ "            \"locationTranslations\": null,\r\n"
				+ "            \"locationSort\": null,\r\n"
				+ "            \"createdUserId\": null,\r\n"
				+ "            \"updatedUserId\": null,\r\n"
				+ "            \"locationPhones\": null,\r\n"
				+ "            \"restrictToCareteam\": false\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return payload;
	}
	
	public String bookLocationsGetPayload() {
		String payload ="{\r\n"
				+ "  \"id\" : 206502,\r\n"
				+ "  \"name\" : null,\r\n"
				+ "  \"displayName\" : null,\r\n"
				+ "  \"emailAddress\" : null,\r\n"
				+ "  \"extBookId\" : null,\r\n"
				+ "  \"acceptComment\" : false,\r\n"
				+ "  \"acceptEmail\" : false,\r\n"
				+ "  \"acceptNew\" : false,\r\n"
				+ "  \"ageRule\" : null,\r\n"
				+ "  \"deleted\" : false,\r\n"
				+ "  \"providerMessage\" : null,\r\n"
				+ "  \"sharePatients\" : false,\r\n"
				+ "  \"slotSize\" : 0,\r\n"
				+ "  \"status\" : false,\r\n"
				+ "  \"categoryId\" : null,\r\n"
				+ "  \"categoryName\" : null,\r\n"
				+ "  \"specialty\" : null,\r\n"
				+ "  \"bookTranslations\" : null,\r\n"
				+ "  \"bookSort\" : null,\r\n"
				+ "  \"bookType\" : null,\r\n"
				+ "  \"bookLevel\" : null,\r\n"
				+ "  \"careteam\" : null,\r\n"
				+ "  \"createdUserId\" : null,\r\n"
				+ "  \"updatedUserId\" : null\r\n"
				+ "}";
		return payload;
	}
	
	public String cancellationReasonPayload() {
		String payload ="[{\r\n"
				+ "  \"id\" : null,\r\n"
				+ "  \"extCancellationReasonId\" : \"1297CE8A-C43A-4FF8-9DFB-4922F69AC14E\",\r\n"
				+ "  \"name\" : \"Not Covered By Insurance\",\r\n"
				+ "  \"displayName\" : \"Not Covered By Insurance\",\r\n"
				+ "  \"cancellationReasonSort\" : null,\r\n"
				+ "  \"practice\" : null,\r\n"
				+ "  \"createdUserId\" : null,\r\n"
				+ "  \"updatedUserId\" : null,\r\n"
				+ "  \"type\" : {\r\n"
				+ "    \"code\" : \"CR_CANCEL\",\r\n"
				+ "    \"name\" : \"Cancellation Reason\",\r\n"
				+ "    \"grouptype\" : \"CANCEL_REASON\"\r\n"
				+ "  }\r\n"
				+ "}]";
		return payload;
	}
	
	public String reorderPayload() {
		String payload ="{\r\n"
				+ "  \"source\": 0,\r\n"
				+ "  \"target\": 1\r\n"
				+ "}";
		return payload;
	}
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}
	public String saveBookPayload() {
		String payload ="";
		return payload;
	}

}
