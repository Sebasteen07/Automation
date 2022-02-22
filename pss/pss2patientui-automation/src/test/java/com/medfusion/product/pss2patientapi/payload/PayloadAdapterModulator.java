// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientapi.payload;

public class PayloadAdapterModulator {
	
	public String openTokenPayload(String practiceid,String authUser) {
		String openToken = "{\r\n"
				+ "    \"practiceId\": \""+practiceid+"\",\r\n"
				+ "    \"authUserId\": \""+authUser+"\"\r\n"
				+ "}";
		return openToken;
	}
	
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
	
	public String annSavePayload(String type,String code, String welcomeMsg) {
		String saveAnn = "{\r\n"
				+ "  \"text\": {\r\n"
				+ "    \"EN\": \""+welcomeMsg+"\",\r\n"
				+ "    \"ES\": \"Su mensaje-Espanol\"\r\n"
				+ "  },\r\n"
				+ "  \"display\": \"Your welcome message will be displayed here. Please contact Medfusion support to add your own text or disable this message.\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"code\": \""+code+"\",\r\n"
				+ "  \"selected\": false,\r\n"
				+ "  \"description\": \"Displayed in dialog when PSS first page loads\"\r\n"
				+ "}";
		return saveAnn;
	}
	
	public String updateAnnouncementPayload(int id, String type,String code ) {
		String updateAnn = "{\r\n"
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
		return updateAnn;
	}
	
	public String saveApptTypePayload() {
		String saveAppt = "[{\r\n"
				+ "    \"isageRule\": false,\r\n"
				+ "    \"id\": 201105,\r\n"
				+ "    \"name\": \"Newpatient30\",\r\n"
				+ "    \"displayName\": \"Newpatient30\",\r\n"
				+ "    \"displayNames\": {\r\n"
				+ "        \"EN\": \"Newpatient30\",\r\n"
				+ "        \"ES\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"message\": {\r\n"
				+ "        \"EN\": \"message\",\r\n"
				+ "        \"ES\": \"message span 1\"\r\n"
				+ "    },\r\n"
				+ "    \"question\": {\r\n"
				+ "        \"EN\": \"Custom question custom question?\",\r\n"
				+ "        \"ES\": \"Custom question custom question span?\"\r\n"
				+ "    },\r\n"
				+ "    \"customMessages\": {\r\n"
				+ "        \"EN\": null,\r\n"
				+ "        \"ES\": null\r\n"
				+ "    },\r\n"
				+ "    \"sortOrder\": 0,\r\n"
				+ "    \"categoryId\": \"c9cc92fb-06c2-420b-ab60-e95dd5c7af83\",\r\n"
				+ "    \"categoryName\": \"Newpatient30\",\r\n"
				+ "    \"extAppointmentTypeId\": \"ec5c2faa-57e1-4121-9c0b-fc99a462281d\",\r\n"
				+ "    \"preventRescheduleOnCancel\": 0,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"locations\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"200350\",\r\n"
				+ "            \"name\": \"Main Office\",\r\n"
				+ "            \"displayName\": \"Main Office\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 200350,\r\n"
				+ "                \"address1\": \"2011 Falls Valley Dr\",\r\n"
				+ "                \"address2\": \"#106\",\r\n"
				+ "                \"city\": \"Raleigh\",\r\n"
				+ "                \"state\": \"North Carolina\",\r\n"
				+ "                \"zipCode\": \"27615\",\r\n"
				+ "                \"latitude\": 35.8973143,\r\n"
				+ "                \"longitude\": -78.60541409999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"9d971e61-2b5a-4504-9016-7fd863790ee2\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"202403\",\r\n"
				+ "            \"name\": \"RHC Main Office\",\r\n"
				+ "            \"displayName\": \"RHC Main Office\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 202353,\r\n"
				+ "                \"address1\": \"998 Collier Rd\",\r\n"
				+ "                \"city\": \"Atlanta\",\r\n"
				+ "                \"state\": \"GA\",\r\n"
				+ "                \"zipCode\": \"303091234\",\r\n"
				+ "                \"latitude\": 33.8100883,\r\n"
				+ "                \"longitude\": -84.42035880000003\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"5EF9DC47-B3CB-43C3-AA88-FF9D330CF5E9\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"201651\",\r\n"
				+ "            \"name\": \"ABC Optical\",\r\n"
				+ "            \"displayName\": \"ABC Optical span\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 201601,\r\n"
				+ "                \"address1\": \"4421 Lake Boone Trail W\",\r\n"
				+ "                \"address2\": \"Address line 2, Updated #3\",\r\n"
				+ "                \"city\": \"Raleigh\",\r\n"
				+ "                \"state\": \"NC\",\r\n"
				+ "                \"zipCode\": \"27607\",\r\n"
				+ "                \"latitude\": 38.6624944,\r\n"
				+ "                \"longitude\": -90.47854949999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"80DE19AE-6BBF-477E-BC8B-371AEF0773DA\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": true,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"202402\",\r\n"
				+ "            \"name\": \"Behavioral Health At Lake Aire Medical\",\r\n"
				+ "            \"displayName\": \"Behavioral Health At Lake Aire Medical\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 202352,\r\n"
				+ "                \"address1\": \"2423 Williams Dr\",\r\n"
				+ "                \"address2\": \"Suite 108\",\r\n"
				+ "                \"city\": \"Georgetown\",\r\n"
				+ "                \"state\": \"TX\",\r\n"
				+ "                \"zipCode\": \"78628\",\r\n"
				+ "                \"latitude\": 30.6605928,\r\n"
				+ "                \"longitude\": -97.68796429999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"13E61FF5-7E2A-4FD4-895D-8E79A99F1597\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"200702\",\r\n"
				+ "            \"name\": \"Saint Joseph's Hospital\",\r\n"
				+ "            \"displayName\": \"Triangle Health Knightdale\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 200652,\r\n"
				+ "                \"address1\": \"909 Knightdale Blvd\",\r\n"
				+ "                \"city\": \"Knightdale\",\r\n"
				+ "                \"state\": \"NC\",\r\n"
				+ "                \"zipCode\": \"27545\",\r\n"
				+ "                \"latitude\": 35.7956002,\r\n"
				+ "                \"longitude\": -78.51262750000001\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"c9a32a00-796e-41a6-91ae-1b46f645f5cc\",\r\n"
				+ "            \"directionUrl\": \"https://www.google.com/maps/dir//909+Knightdale+Blvd,+Knightdale,+NC+27545/@35.7956002,-78.5148162,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x89ac5c8255dc5e75:0x30f29823c147509b!2m2!1d-78.5126275!2d35.7956002\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"204152\",\r\n"
				+ "            \"name\": \"Satellite Location 1\",\r\n"
				+ "            \"displayName\": \"Satellite Location 1\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 204052,\r\n"
				+ "                \"address1\": \"449 Roswell Rd\",\r\n"
				+ "                \"city\": \"Atlanta\",\r\n"
				+ "                \"state\": \"GA\",\r\n"
				+ "                \"zipCode\": \"30342\",\r\n"
				+ "                \"latitude\": 0,\r\n"
				+ "                \"longitude\": 0\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"A21148E1-66C3-459A-AD83-C0733D97693A\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"param\": {\r\n"
				+ "        \"id\": 200402,\r\n"
				+ "        \"appointmentStacking\": false,\r\n"
				+ "        \"slotCount\": 1,\r\n"
				+ "        \"allowSameDayAppts\": true,\r\n"
				+ "        \"apptTimeMark\": 0,\r\n"
				+ "        \"apptTypeAllocated\": true,\r\n"
				+ "        \"isContiguous\": true,\r\n"
				+ "        \"leadTime\": {\r\n"
				+ "            \"days\": \"0\",\r\n"
				+ "            \"hours\": \"0\",\r\n"
				+ "            \"mins\": \"0\"\r\n"
				+ "        },\r\n"
				+ "        \"excludeSlots\": [],\r\n"
				+ "        \"apptTypeReservedReason\": \"n\",\r\n"
				+ "        \"acceptComment\": true,\r\n"
				+ "        \"allowOnlineCancellation\": true,\r\n"
				+ "        \"slotSize\": 5,\r\n"
				+ "        \"schedulingDuration\": 15,\r\n"
				+ "        \"pttype\": \"PT_ALL\",\r\n"
				+ "        \"lastQuestRequired\": true\r\n"
				+ "    },\r\n"
				+ "    \"ageRule\": \"\"\r\n"
				+ "}]";
		return saveAppt;
	}
	
	public String saveApptTypeWithoutIdPayload() {
		String saveAppt = "[{\r\n"				
				+ "    \"displayNames\": {\r\n"
				+ "        \"EN\": \"Newpatient30\",\r\n"
				+ "        \"ES\": \"\"\r\n"
				+ "    },\r\n"
				+ "    \"message\": {\r\n"
				+ "        \"EN\": \"message\",\r\n"
				+ "        \"ES\": \"message span 1\"\r\n"
				+ "    },\r\n"
				+ "    \"question\": {\r\n"
				+ "        \"EN\": \"Custom question custom question?\",\r\n"
				+ "        \"ES\": \"Custom question custom question span?\"\r\n"
				+ "    },\r\n"
				+ "    \"customMessages\": {\r\n"
				+ "        \"EN\": null,\r\n"
				+ "        \"ES\": null\r\n"
				+ "    },\r\n"
				+ "    \"sortOrder\": 0,\r\n"
				+ "    \"categoryId\": \"c9cc92fb-06c2-420b-ab60-e95dd5c7af83\",\r\n"
				+ "    \"categoryName\": \"Newpatient30\",\r\n"
				+ "    \"extAppointmentTypeId\": \"ec5c2faa-57e1-4121-9c0b-fc99a462281d\",\r\n"
				+ "    \"preventRescheduleOnCancel\": 0,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"locations\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"200350\",\r\n"
				+ "            \"name\": \"Main Office\",\r\n"
				+ "            \"displayName\": \"Main Office\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 200350,\r\n"
				+ "                \"address1\": \"2011 Falls Valley Dr\",\r\n"
				+ "                \"address2\": \"#106\",\r\n"
				+ "                \"city\": \"Raleigh\",\r\n"
				+ "                \"state\": \"North Carolina\",\r\n"
				+ "                \"zipCode\": \"27615\",\r\n"
				+ "                \"latitude\": 35.8973143,\r\n"
				+ "                \"longitude\": -78.60541409999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"9d971e61-2b5a-4504-9016-7fd863790ee2\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"202403\",\r\n"
				+ "            \"name\": \"RHC Main Office\",\r\n"
				+ "            \"displayName\": \"RHC Main Office\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 202353,\r\n"
				+ "                \"address1\": \"998 Collier Rd\",\r\n"
				+ "                \"city\": \"Atlanta\",\r\n"
				+ "                \"state\": \"GA\",\r\n"
				+ "                \"zipCode\": \"303091234\",\r\n"
				+ "                \"latitude\": 33.8100883,\r\n"
				+ "                \"longitude\": -84.42035880000003\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"5EF9DC47-B3CB-43C3-AA88-FF9D330CF5E9\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"201651\",\r\n"
				+ "            \"name\": \"ABC Optical\",\r\n"
				+ "            \"displayName\": \"ABC Optical span\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 201601,\r\n"
				+ "                \"address1\": \"4421 Lake Boone Trail W\",\r\n"
				+ "                \"address2\": \"Address line 2, Updated #3\",\r\n"
				+ "                \"city\": \"Raleigh\",\r\n"
				+ "                \"state\": \"NC\",\r\n"
				+ "                \"zipCode\": \"27607\",\r\n"
				+ "                \"latitude\": 38.6624944,\r\n"
				+ "                \"longitude\": -90.47854949999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"80DE19AE-6BBF-477E-BC8B-371AEF0773DA\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": true,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"202402\",\r\n"
				+ "            \"name\": \"Behavioral Health At Lake Aire Medical\",\r\n"
				+ "            \"displayName\": \"Behavioral Health At Lake Aire Medical\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 202352,\r\n"
				+ "                \"address1\": \"2423 Williams Dr\",\r\n"
				+ "                \"address2\": \"Suite 108\",\r\n"
				+ "                \"city\": \"Georgetown\",\r\n"
				+ "                \"state\": \"TX\",\r\n"
				+ "                \"zipCode\": \"78628\",\r\n"
				+ "                \"latitude\": 30.6605928,\r\n"
				+ "                \"longitude\": -97.68796429999999\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"13E61FF5-7E2A-4FD4-895D-8E79A99F1597\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"200702\",\r\n"
				+ "            \"name\": \"Saint Joseph's Hospital\",\r\n"
				+ "            \"displayName\": \"Triangle Health Knightdale\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 200652,\r\n"
				+ "                \"address1\": \"909 Knightdale Blvd\",\r\n"
				+ "                \"city\": \"Knightdale\",\r\n"
				+ "                \"state\": \"NC\",\r\n"
				+ "                \"zipCode\": \"27545\",\r\n"
				+ "                \"latitude\": 35.7956002,\r\n"
				+ "                \"longitude\": -78.51262750000001\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"c9a32a00-796e-41a6-91ae-1b46f645f5cc\",\r\n"
				+ "            \"directionUrl\": \"https://www.google.com/maps/dir//909+Knightdale+Blvd,+Knightdale,+NC+27545/@35.7956002,-78.5148162,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x89ac5c8255dc5e75:0x30f29823c147509b!2m2!1d-78.5126275!2d35.7956002\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"204152\",\r\n"
				+ "            \"name\": \"Satellite Location 1\",\r\n"
				+ "            \"displayName\": \"Satellite Location 1\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 204052,\r\n"
				+ "                \"address1\": \"449 Roswell Rd\",\r\n"
				+ "                \"city\": \"Atlanta\",\r\n"
				+ "                \"state\": \"GA\",\r\n"
				+ "                \"zipCode\": \"30342\",\r\n"
				+ "                \"latitude\": 0,\r\n"
				+ "                \"longitude\": 0\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"A21148E1-66C3-459A-AD83-C0733D97693A\",\r\n"
				+ "            \"selected\": false,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"param\": {\r\n"
				+ "        \"id\": 200402,\r\n"
				+ "        \"appointmentStacking\": false,\r\n"
				+ "        \"slotCount\": 1,\r\n"
				+ "        \"allowSameDayAppts\": true,\r\n"
				+ "        \"apptTimeMark\": 0,\r\n"
				+ "        \"apptTypeAllocated\": true,\r\n"
				+ "        \"isContiguous\": true,\r\n"
				+ "        \"leadTime\": {\r\n"
				+ "            \"days\": \"0\",\r\n"
				+ "            \"hours\": \"0\",\r\n"
				+ "            \"mins\": \"0\"\r\n"
				+ "        },\r\n"
				+ "        \"excludeSlots\": [],\r\n"
				+ "        \"apptTypeReservedReason\": \"n\",\r\n"
				+ "        \"acceptComment\": true,\r\n"
				+ "        \"allowOnlineCancellation\": true,\r\n"
				+ "        \"slotSize\": 5,\r\n"
				+ "        \"schedulingDuration\": 15,\r\n"
				+ "        \"pttype\": \"PT_ALL\",\r\n"
				+ "        \"lastQuestRequired\": true\r\n"
				+ "    },\r\n"
				+ "    \"ageRule\": \"\"\r\n"
				+ "}]";
		return saveAppt;
	}
	
	public String saveBookAppointmentTypePayload() {
		String saveBookAppt = "{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": 4247\r\n"
				+ "    },\r\n"
				+ "    \"appointmentType\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4195\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return saveBookAppt;
	}
	
	public String reorderApptPayload() {
		String reorder = "{\r\n"
				+ "    \"source\": 2,\r\n"
				+ "    \"target\": 0\r\n"
				+ "}";
		return reorder;
	}
	
	public String reorderBookPayload() {
		String reorder = "{\r\n"
				+ "    \"source\": 1,\r\n"
				+ "    \"target\": 2\r\n"
				+ "}";
		return reorder;
	}
	
	public String saveBookLocationPayload() {
		String booklocation="{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": 4248\r\n"
				+ "    },\r\n"
				+ "    \"location\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"4161\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return booklocation;
	}
	
	public String bookAppointmentTypeSavePayload(String bookid, String apptid) {
		String bookAppointment="{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": "+bookid+"\r\n"
				+ "    },\r\n"
				+ "    \"appointmentType\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": "+apptid+"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return bookAppointment;
	}
	
	public String bookAppointmentTypUpdatePayload(String bookid, String apptid) {
		String bookAppointment="{\r\n"
				+ "    \"isageRule\": false,\r\n"
				+ "    \"id\": 4442,\r\n"
				+ "    \"description\": null,\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": 206501,\r\n"
				+ "        \"name\": null,\r\n"
				+ "        \"displayName\": null,\r\n"
				+ "        \"emailAddress\": null,\r\n"
				+ "        \"extBookId\": null,\r\n"
				+ "        \"acceptComment\": null,\r\n"
				+ "        \"acceptEmail\": null,\r\n"
				+ "        \"acceptNew\": null,\r\n"
				+ "        \"ageRule\": null,\r\n"
				+ "        \"deleted\": null,\r\n"
				+ "        \"providerMessage\": null,\r\n"
				+ "        \"sharePatients\": null,\r\n"
				+ "        \"slotSize\": null,\r\n"
				+ "        \"status\": false,\r\n"
				+ "        \"categoryId\": null,\r\n"
				+ "        \"categoryName\": null,\r\n"
				+ "        \"practice\": null,\r\n"
				+ "        \"specialty\": null,\r\n"
				+ "        \"bookSort\": null,\r\n"
				+ "        \"bookTranslations\": null,\r\n"
				+ "        \"bookType\": null,\r\n"
				+ "        \"bookLevel\": null,\r\n"
				+ "        \"careteam\": null,\r\n"
				+ "        \"links\": null\r\n"
				+ "    },\r\n"
				+ "    \"appointmentType\": {\r\n"
				+ "        \"id\": 206533,\r\n"
				+ "        \"name\": null,\r\n"
				+ "        \"displayName\": null,\r\n"
				+ "        \"description\": null,\r\n"
				+ "        \"extAppointmentTypeId\": null,\r\n"
				+ "        \"duration\": null,\r\n"
				+ "        \"categoryId\": null,\r\n"
				+ "        \"categoryName\": null,\r\n"
				+ "        \"messages\": null,\r\n"
				+ "        \"customQuestion\": null,\r\n"
				+ "        \"customMessage\": null,\r\n"
				+ "        \"appointmentTypeTranslation\": null,\r\n"
				+ "        \"preventRescheduleOnCancel\": null,\r\n"
				+ "        \"ageRule\": null,\r\n"
				+ "        \"preventScheduling\": null,\r\n"
				+ "        \"appointmentTypeSort\": null\r\n"
				+ "    },\r\n"
				+ "    \"appointmentStacking\": false,\r\n"
				+ "    \"preventScheduling\": 0,\r\n"
				+ "    \"slotCount\": 1,\r\n"
				+ "    \"allowSameDayAppts\": true,\r\n"
				+ "    \"apptTimeMark\": 0,\r\n"
				+ "    \"apptTypeAllocated\": true,\r\n"
				+ "    \"isContiguous\": false,\r\n"
				+ "    \"leadTime\": {\r\n"
				+ "        \"days\": \"0\",\r\n"
				+ "        \"hours\": \"0\",\r\n"
				+ "        \"mins\": \"0\"\r\n"
				+ "    },\r\n"
				+ "    \"maxPerDay\": 0,\r\n"
				+ "    \"excludeSlots\": [],\r\n"
				+ "    \"apptTypeReservedReason\": \"n\",\r\n"
				+ "    \"ageRule\": \"\",\r\n"
				+ "    \"status\": true,\r\n"
				+ "    \"allowOnlineCancellation\": true,\r\n"
				+ "    \"providerAvailabilityDays\": 0,\r\n"
				+ "    \"schedulingDuration\": 0,\r\n"
				+ "    \"locations\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \"206350\",\r\n"
				+ "            \"name\": \"Interfaces Location\",\r\n"
				+ "            \"displayName\": \"Interfaces Location\",\r\n"
				+ "            \"address\": {\r\n"
				+ "                \"id\": 206250,\r\n"
				+ "                \"address1\": \"50 Marcus\",\r\n"
				+ "                \"address2\": \"\",\r\n"
				+ "                \"city\": \"Melville\",\r\n"
				+ "                \"state\": \"NY\",\r\n"
				+ "                \"zipCode\": \"11747\",\r\n"
				+ "                \"latitude\": 40.75729159999999,\r\n"
				+ "                \"longitude\": -73.4121919,\r\n"
				+ "                \"locAddress\": \"50 Marcus  Melville NY 11747\"\r\n"
				+ "            },\r\n"
				+ "            \"timezone\": \"\",\r\n"
				+ "            \"extLocationId\": \"97624552-7991-41E8-87FD-464362D1EAF8\",\r\n"
				+ "            \"selected\": true,\r\n"
				+ "            \"phoneNumber\": \"\",\r\n"
				+ "            \"restrictToCareteam\": false,\r\n"
				+ "            \"locationLinks\": {}\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"pttype\": \"PT_ALL\",\r\n"
				+ "    \"lastQuestRequired\": false\r\n"
				+ "}";
		return bookAppointment;
	}
	
	public String bookLocationSavePayload(String bookid, String locationid) {
		String booklocation="{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": "+bookid+"\r\n"
				+ "    },\r\n"
				+ "    \"location\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \""+locationid+"\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return booklocation;
	}
	
	public String bookSavePayload() {
		String book="[\r\n"
				+ "    {\r\n"
				+ "        \"isageRule\": false,\r\n"
				+ "        \"id\": 4254,\r\n"
				+ "        \"name\": \"Brut PSS [Brut PSS]\",\r\n"
				+ "        \"displayNames\": {\r\n"
				+ "            \"EN\": \"Brut PSS [Brut PSS]\",\r\n"
				+ "            \"ES\": \"Brut PSS Espanol hola amigo\"\r\n"
				+ "        },\r\n"
				+ "        \"displayName\": \"Brut PSS [Brut PSS]\",\r\n"
				+ "        \"ageRule\": \"\",\r\n"
				+ "        \"extBookId\": \"F625C319-3CA2-47C7-B116-51D13294C37F\",\r\n"
				+ "        \"sharePatients\": true,\r\n"
				+ "        \"slotSize\": 5,\r\n"
				+ "        \"status\": true,\r\n"
				+ "        \"providerImage\": \"https://dev3-pss-adminportal-ui.dev.medfusion.net/pss-adapter-modulator/24702/book/F625C319-3CA2-47C7-B116-51D13294C37F/image\",\r\n"
				+ "        \"specialty\": [\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"isgenderRule\": false,\r\n"
				+ "                \"id\": 203604,\r\n"
				+ "                \"name\": \"specialty\",\r\n"
				+ "                \"displayName\": \"specialty\",\r\n"
				+ "                \"ageRule\": \"\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"isgenderRule\": false,\r\n"
				+ "                \"id\": 4117,\r\n"
				+ "                \"name\": \"sp'1\",\r\n"
				+ "                \"displayName\": \"sp'1\",\r\n"
				+ "                \"ageRule\": \"\"\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"location\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": \"206532\",\r\n"
				+ "                \"name\": \"PSS Cary\",\r\n"
				+ "                \"displayName\": \"PSS Cary\",\r\n"
				+ "                \"address\": {\r\n"
				+ "                    \"id\": 206432,\r\n"
				+ "                    \"city\": \"Test\",\r\n"
				+ "                    \"state\": \"AL\",\r\n"
				+ "                    \"zipCode\": \"555555555\",\r\n"
				+ "                    \"latitude\": 0,\r\n"
				+ "                    \"longitude\": 0\r\n"
				+ "                },\r\n"
				+ "                \"timezone\": \"\",\r\n"
				+ "                \"extLocationId\": \"1B595F99-278A-4956-8F04-1879B12FB43D\",\r\n"
				+ "                \"selected\": false,\r\n"
				+ "                \"phoneNumber\": \"\",\r\n"
				+ "                \"restrictToCareteam\": false,\r\n"
				+ "                \"locationLinks\": {}\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"appointmentType\": [\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"id\": 206558,\r\n"
				+ "                \"name\": \"Shrikant\",\r\n"
				+ "                \"displayName\": \"Shrikant\",\r\n"
				+ "                \"displayNames\": {},\r\n"
				+ "                \"message\": {},\r\n"
				+ "                \"question\": {},\r\n"
				+ "                \"customMessages\": {},\r\n"
				+ "                \"categoryId\": \"04E98D09-3797-4250-A0C4-E467D11FE2FA\",\r\n"
				+ "                \"categoryName\": \"Shrikant\",\r\n"
				+ "                \"extAppointmentTypeId\": \"C1DDA8A2-B513-42AD-B6AA-4643AC4345CA\",\r\n"
				+ "                \"preventRescheduleOnCancel\": 0,\r\n"
				+ "                \"preventScheduling\": 5\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"id\": 206536,\r\n"
				+ "                \"name\": \"Optical One\",\r\n"
				+ "                \"displayName\": \"Optical One\",\r\n"
				+ "                \"displayNames\": {},\r\n"
				+ "                \"message\": {},\r\n"
				+ "                \"question\": {},\r\n"
				+ "                \"customMessages\": {},\r\n"
				+ "                \"categoryId\": \"234C5101-63AD-403F-93C6-C98B45B81709\",\r\n"
				+ "                \"categoryName\": \"Optical One\",\r\n"
				+ "                \"extAppointmentTypeId\": \"695257A9-4F0D-44D4-82D0-ADED623DC6B1\",\r\n"
				+ "                \"preventRescheduleOnCancel\": 0,\r\n"
				+ "                \"preventScheduling\": 0\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"id\": 206557,\r\n"
				+ "                \"name\": \"General Health Checkup\",\r\n"
				+ "                \"displayName\": \"General Health Checkup\",\r\n"
				+ "                \"displayNames\": {},\r\n"
				+ "                \"message\": {},\r\n"
				+ "                \"question\": {},\r\n"
				+ "                \"customMessages\": {},\r\n"
				+ "                \"categoryId\": \"7B40680C-9CCE-478D-9455-2B017FD048A2\",\r\n"
				+ "                \"categoryName\": \"General Health Check\",\r\n"
				+ "                \"extAppointmentTypeId\": \"9E0BC44E-4200-4433-8941-DA49C408F1BA\",\r\n"
				+ "                \"preventRescheduleOnCancel\": 0,\r\n"
				+ "                \"preventScheduling\": 0\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"sortOrder\": 9,\r\n"
				+ "        \"emailAddress\": \"Shweta.Sontakke@CrossAsyst.com\",\r\n"
				+ "        \"acceptComment\": false,\r\n"
				+ "        \"acceptNew\": false,\r\n"
				+ "        \"categoryId\": \"11B85759-9394-4C06-8F20-CF57F1EA81BB\",\r\n"
				+ "        \"categoryName\": \"Brut PSS [Brut PSS]\",\r\n"
				+ "        \"bookType\": {\r\n"
				+ "            \"name\": \"PERSON\",\r\n"
				+ "            \"code\": \"RS_PERSON\",\r\n"
				+ "            \"grouptype\": \"RESOURCE\"\r\n"
				+ "        },\r\n"
				+ "        \"bookLevel\": {\r\n"
				+ "            \"name\": \"Level 1\",\r\n"
				+ "            \"code\": \"RS_L1\",\r\n"
				+ "            \"grouptype\": \"RESOURCE_LEVEL\"\r\n"
				+ "        },\r\n"
				+ "        \"providerlinks\": {},\r\n"
				+ "        \"providerMessage\": \"Hello Doctor\"\r\n"
				+ "    }\r\n"
				+ "]";
		return book;
	}
	
	public String reorderCancellationReasonPayload() {
		String payload="{\r\n"
				+ "  \"source\": 0,\r\n"
				+ "  \"target\": 1\r\n"
				+ "}";
		return payload;
	}
	
	public String saveCancellationReasonPayload() {
		String payload="[\r\n"
				+ "  {\r\n"
				+ "    \"id\": null,\r\n"
				+ "    \"name\": \"Not Covered By Insurance\",\r\n"
				+ "    \"sortOrder\": 6,\r\n"
				+ "    \"extCancellationReasonId\": \"1297CE8A-C43A-4FF8-9DFB-4922F69AC14E\",\r\n"
				+ "    \"displayName\": \"Not Covered By Insurance\",\r\n"
				+ "    \"type\": {\r\n"
				+ "      \"name\": \"Cancellation Reason\",\r\n"
				+ "      \"code\": \"CR_CANCEL\",\r\n"
				+ "      \"grouptype\": \"CANCEL_REASON\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}
	
	public String saveCancellationReasonPayload(String name, String extid, String dname, String typename, String typecode, String grouptype) {
		String payload="[\r\n"
				+ "  {\r\n"
				+ "    \"id\": null,\r\n"
				+ "    \"name\": \""+name+",\r\n"
				+ "    \"selected\": true,\r\n"
				+ "    \"extCancellationReasonId\": \""+extid+"\",\r\n"
				+ "    \"displayName\": \""+dname+"\",\r\n"
				+ "    \"type\": {\r\n"
				+ "      \"name\": \""+typename+"\",\r\n"
				+ "      \"code\": \""+typecode+",\r\n"
				+ "      \"grouptype\": \""+grouptype+"\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}
	
	public String saveCareTeamPayload(String careteam, String name) {
		String payload="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": "+careteam+",\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"book\": [\r\n"
				+ "            {\r\n"
				+ "                \"isageRule\": false,\r\n"
				+ "                \"id\": 4303,\r\n"
				+ "                \"name\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "                \"displayNames\": {\r\n"
				+ "                    \"EN\": \"Ng1 Pss [PSS, NG1]\"\r\n"
				+ "                },\r\n"
				+ "                \"displayName\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "                \"extBookId\": \"FB84CDED-8A67-4A40-81F8-9401F59DC6C6\",\r\n"
				+ "                \"sharePatients\": true,\r\n"
				+ "                \"slotSize\": 5,\r\n"
				+ "                \"status\": true,\r\n"
				+ "                \"acceptComment\": true,\r\n"
				+ "                \"acceptNew\": false,\r\n"
				+ "                \"categoryId\": \"730C6F5E-4F8C-482F-BB81-F6050841FD8D\",\r\n"
				+ "                \"categoryName\": \"Ng1 Pss [PSS, NG1]\",\r\n"
				+ "                \"bookType\": {\r\n"
				+ "                    \"name\": \"PERSON\",\r\n"
				+ "                    \"code\": \"RS_PERSON\",\r\n"
				+ "                    \"grouptype\": \"RESOURCE\"\r\n"
				+ "                },\r\n"
				+ "                \"bookLevel\": {\r\n"
				+ "                    \"name\": \"None\",\r\n"
				+ "                    \"code\": \"RS_NONE\",\r\n"
				+ "                    \"grouptype\": \"RESOURCE_LEVEL\"\r\n"
				+ "                },\r\n"
				+ "                \"careteam\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"id\": 4068,\r\n"
				+ "                        \"name\": \"care team\"\r\n"
				+ "                    }\r\n"
				+ "                ],\r\n"
				+ "                \"providerlinks\": {}\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;
	}	
	
	public String saveCareTeamBookPayload(String careteam, String name) {
		String payload="{\"careTeam\":{\"id\":4068},\"book\":[{\"id\":4304}]}";
		return payload;
	}	
	public String saveBookSpecialtyPayload(String bookid, String specialtyid) {
		String payload="{\r\n"
				+ "    \"book\": {\r\n"
				+ "        \"id\": "+bookid+"\r\n"
				+ "    },\r\n"
				+ "    \"specialty\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": "+specialtyid+"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return payload;
	}	
	
	public String saveCareTeam() {
		String payload="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 4070,\r\n"
				+ "        \"name\": \"Care Team for delete\"\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;
	}

	public String saveCategory() {		
		String payload="[\r\n"
				+ "    {\r\n"
				+ "        \"displayNames\": {\r\n"
				+ "            \"EN\": \"English Sushma\",\r\n"
				+ "            \"ES\": \"Sushma_Espanol\"\r\n"
				+ "        },\r\n"
				+ "        \"name\": \"Sushma\",\r\n"
				+ "        \"type\": \"CG_APPOINTMENT_TYPE\",\r\n"
				+ "        \"status\": \"DRAFT\"\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;		
	}
	
	public String draftCategoryPayload(int categoryname) {		
		String payload="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": "+categoryname+",\r\n"
				+ "        \"name\": \"Sushma\",\r\n"
				+ "        \"type\": \"CG_APPOINTMENT_TYPE\",\r\n"
				+ "        \"nodes\": [\r\n"
				+ "            {\r\n"
				+ "                \"guid\": 4397,\r\n"
				+ "                \"type\": \"CATEGORY\",\r\n"
				+ "                \"displayName\": \"English Sushma\",\r\n"
				+ "                \"displayNames\": {\r\n"
				+ "                    \"EN\": \"English Sushma\",\r\n"
				+ "                    \"ES\": \"Sushma_Espanol\"\r\n"
				+ "                },\r\n"
				+ "                \"entity\": {\r\n"
				+ "                    \"type\": \"appointment_type\",\r\n"
				+ "                    \"id\": 4195,\r\n"
				+ "                    \"name\": \"One Medical\",\r\n"
				+ "                    \"categoryId\": \"003DB4E4-8F86-424F-9A08-9EE212285CBE\",\r\n"
				+ "                    \"categoryName\": \"One Medical Category\",\r\n"
				+ "                    \"extAppointmentTypeId\": \"F39DDC8C-C061-445B-8564-8B4A220F5D49\"\r\n"
				+ "                },\r\n"
				+ "                \"children\": []\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"displayName\": \"English Sushma\",\r\n"
				+ "        \"displayNames\": {\r\n"
				+ "            \"EN\": \"English Sushma\",\r\n"
				+ "            \"ES\": \"Sushma_Espanol\"\r\n"
				+ "        },\r\n"
				+ "        \"status\": \"DRAFT\"\r\n"
				+ "    }\r\n"
				+ "]";
		return payload;		
	}
	
	public String exportCategoryPayload(){
		String payload="{\r\n"
				+ "    \"id\": 4397,\r\n"
				+ "    \"name\": \"Sushma\",\r\n"
				+ "    \"type\": \"CG_APPOINTMENT_TYPE\",\r\n"
				+ "    \"nodes\": [\r\n"
				+ "        {\r\n"
				+ "            \"guid\": 4397,\r\n"
				+ "            \"type\": \"CATEGORY\",\r\n"
				+ "            \"displayName\": \"English Sushma\",\r\n"
				+ "            \"displayNames\": {\r\n"
				+ "                \"EN\": \"English Sushma\",\r\n"
				+ "                \"ES\": \"Sushma_Espanol\"\r\n"
				+ "            },\r\n"
				+ "            \"entity\": null,\r\n"
				+ "            \"children\": [\r\n"
				+ "                \"5e90668c-564f-4a42-97e1-9fd2f8cf1beb\"\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"guid\": \"5e90668c-564f-4a42-97e1-9fd2f8cf1beb\",\r\n"
				+ "            \"type\": \"QUESTION\",\r\n"
				+ "            \"displayName\": \"[QUESTION0]\",\r\n"
				+ "            \"displayNames\": {\r\n"
				+ "                \"EN\": \"What is the appointment Name\",\r\n"
				+ "                \"ES\": \"[QUESTION0]\"\r\n"
				+ "            },\r\n"
				+ "            \"parentId\": 4397,\r\n"
				+ "            \"entity\": null,\r\n"
				+ "            \"children\": [\r\n"
				+ "                \"9b0a70ed-ae2d-4aef-ad5b-68b6501605a5\"\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"guid\": \"9b0a70ed-ae2d-4aef-ad5b-68b6501605a5\",\r\n"
				+ "            \"type\": \"ANSWER\",\r\n"
				+ "            \"displayName\": \"[ANSWER0]\",\r\n"
				+ "            \"displayNames\": {\r\n"
				+ "                \"EN\": \"Medical One\",\r\n"
				+ "                \"ES\": \"[ANSWER0]\"\r\n"
				+ "            },\r\n"
				+ "            \"parentId\": \"5e90668c-564f-4a42-97e1-9fd2f8cf1beb\",\r\n"
				+ "            \"entity\": null,\r\n"
				+ "            \"children\": []\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"displayName\": \"English Sushma\",\r\n"
				+ "    \"displayNames\": {\r\n"
				+ "        \"EN\": \"English Sushma\",\r\n"
				+ "        \"ES\": \"Sushma_Espanol\"\r\n"
				+ "    },\r\n"
				+ "    \"status\": \"DRAFT\"\r\n"
				+ "}";
		return payload;
	}
	

	public String categorySpecialityPayload(String bookId,String specialityId) {
		String categorySpeciality = "{\r\n"
				+ "    \"category\": {\r\n"
				+ "        \"id\": \""+bookId+"\"\r\n"
				+ "    },\r\n"
				+ "    \"specialty\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": \""+specialityId+"\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return categorySpeciality;
	}
	
	public String resourceConfigPayload() {
		String resourceConfig = "[\r\n"
				+ "    {\r\n"
				+ "       \"config\": [\r\n"
				+ "            {\r\n"
				+ "                \"key\": \"searchLocation\",\r\n"
				+ "                \"dataType\": \"Boolean\",\r\n"
				+ "                \"isRequired\": false,\r\n"
				+ "                \"value\": \"true\",\r\n"
				+ "                \"displayName\": \"Show search location\"\r\n"
				+ "            }\r\n"
				+ "        ],\r\n"
				+ "        \"group\": \"LOCATION\"\r\n"
				+ "    }\r\n"
				+ "]";
		return resourceConfig;
	}
	
	public String resourceConfigSavePayload() {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"LOCATION\",\r\n"
				+ "    \"key\": \"searchLocation\",\r\n"
				+ "    \"value\": \"true\"\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}
	
	public String insuranceAtStartorEnd (boolean bool) {
		String payload = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"showInsuranceStart\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"insuranceMandatory\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"selfPay\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"showInsuranceCarrierFromPM\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"restrictPatient\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"allowSpecialCharForInsurance\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE_ROUTING\",\r\n"
				+ "    \"key\": \"one\",\r\n"
				+ "    \"value\": \"true\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE_ROUTING\",\r\n"
				+ "    \"key\": \"two\",\r\n"
				+ "    \"value\": \"false\"\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}
	
	public String insuranceAtStartorEndAT (boolean bool) {
		String payload = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"showInsuranceStart\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"insuranceMandatory\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"selfPay\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"showInsuranceCarrierFromPM\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"INSURANCE\",\r\n"
				+ "    \"key\": \"restrictPatient\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}
	
	public String loginlessEnable() {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"loginless\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}
	
	public String loginlessDisable() {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"loginless\",\r\n"
				+ "    \"value\": null\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}
	
	public String upcimingPastAptOnOff(boolean bool) {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"allowCancellationHours\",\r\n"
				+ "    \"value\": \"00:00\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PATIENT_ACCESS\",\r\n"
				+ "    \"key\": \"ValidDuration\",\r\n"
				+ "    \"value\": \"60\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"displaySlotsCount\",\r\n"
				+ "    \"value\": \"200\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"maxCalendarMonths\",\r\n"
				+ "    \"value\": \"20\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ageRestriction\",\r\n"
				+ "    \"value\": \"50\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"maxAppointments\",\r\n"
				+ "    \"value\": \"5\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ShowPastAppointmentMonths\",\r\n"
				+ "    \"value\": \"5\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PROVIDER\",\r\n"
				+ "    \"key\": \"showNextAvailable\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"LOCATION\",\r\n"
				+ "    \"key\": \"searchLocation\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showUpcoming\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showPast\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"patientUpdateConfig\",\r\n"
				+ "    \"value\": \"PATIENT_NOTES\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"establishPatientLastVisit\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showCancelReason\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"patientCreationDuration\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}
	
	public String upcimingPastAptOnOffGW(boolean bool) {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"allowCancellationHours\",\r\n"
				+ "    \"value\": \"00:00\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PATIENT_ACCESS\",\r\n"
				+ "    \"key\": \"ValidDuration\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"displaySlotsCount\",\r\n"
				+ "    \"value\": \"200\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"SLOTS\",\r\n"
				+ "    \"key\": \"maxCalendarMonths\",\r\n"
				+ "    \"value\": \"20\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ageRestriction\",\r\n"
				+ "    \"value\": \"50\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"maxAppointments\",\r\n"
				+ "    \"value\": \"5\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"ShowPastAppointmentMonths\",\r\n"
				+ "    \"value\": \"5\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PROVIDER\",\r\n"
				+ "    \"key\": \"showNextAvailable\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"LOCATION\",\r\n"
				+ "    \"key\": \"searchLocation\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showUpcoming\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"showPast\",\r\n"
				+ "    \"value\": "+false+"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"patientUpdateConfig\",\r\n"
				+ "    \"value\": \"PATIENT_NOTES\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"establishPatientLastVisit\",\r\n"
				+ "    \"value\": \"1095\"\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"PATIENT_ACCESS\",\r\n"
				+ "    \"key\": \"replaceSpecialChar\",\r\n"
				+ "    \"value\": true\r\n"
				+ "  },\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"APPOINTMENT\",\r\n"
				+ "    \"key\": \"targetTimezone\",\r\n"
				+ "    \"value\": \"America/New_York\"\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}
	
	public String anonymousConfg(boolean bool) {
		String resourceConfigSave = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"anonymous\",\r\n"
				+ "    \"value\": "+bool+"\r\n"
				+ "  }\r\n"
				+ "]";
		return resourceConfigSave;
	}

	
	public String resourceConfigRulePostPayload() {
		String resourceConfigRule = "{\r\n"
				+ "    \"name\": \"LTB\",\r\n"
				+ "    \"rule\": \"L,T,B\"\r\n"
				+ "}";
		return resourceConfigRule;
	}
	
	public String resourceConfigRulePostPayloadTL() {
		String resourceConfigRule = "{\r\n"
				+ "    \"name\": \"TL\",\r\n"
				+ "    \"rule\": \"T,L\"\r\n"
				+ "}";
		return resourceConfigRule;
	}
	
	
	public String resourceConfigRulePutPayload() {
		String resourceConfigRule = "{\r\n"
				+ "    \"name\": \"TLB\",\r\n"
				+ "    \"rule\": \"T,L,B\"\r\n"
				+ "}";
		return resourceConfigRule;
	}
	
	public String resourceConfigRuleLBTPayload() {
		String resourceConfigRule = "{\r\n"
				+ "    \"name\": \"LBT\",\r\n"
				+ "    \"rule\": \"L,B,T\"\r\n"
				+ "}";
		return resourceConfigRule;
	}
	
	public String rulePayload(String name, String rule) {
		String payload = "{\r\n"
				+ "    \"name\": \""+name+"\",\r\n"
				+ "    \"rule\": \""+rule+"\"\r\n"
				+ "}";
		return payload;
	}
	

	public String resourceConfigRulePutPayloadLT() {
		String resourceConfigRule = "{\r\n"
				+ "    \"name\": \"LT\",\r\n"
				+ "    \"rule\": \"L,T\"\r\n"
				+ "}";
		return resourceConfigRule;
	}
	
	public String providerOff() {
		String payload = "[\r\n"
				+ "  {\r\n"
				+ "    \"group\": \"RULEENGINE\",\r\n"
				+ "    \"key\": \"showProvider\",\r\n"
				+ "    \"value\": false\r\n"
				+ "  }\r\n"
				+ "]";
		return payload;
	}
	
	public String genderMapPayload() {
		String genderMap = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 201969,\r\n"
				+ "        \"practice\": {\r\n"
				+ "            \"id\": 24702,\r\n"
				+ "            \"extPracticeId\": \"24702\",\r\n"
				+ "            \"active\": true,\r\n"
				+ "            \"name\": \"PSS-NG-PG16CN\",\r\n"
				+ "            \"practiceId\": \"24702\",\r\n"
				+ "            \"partner\": {\r\n"
				+ "                \"id\": 2,\r\n"
				+ "                \"name\": \"NextGen\",\r\n"
				+ "                \"code\": \"NG\",\r\n"
				+ "                \"integrationId\": \"88\"\r\n"
				+ "            },\r\n"
				+ "            \"timeZone\": null,\r\n"
				+ "            \"language\": [\r\n"
				+ "                {\r\n"
				+ "                    \"name\": \"English\",\r\n"
				+ "                    \"code\": \"EN\",\r\n"
				+ "                    \"flag\": \"us\"\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"name\": \"Español\",\r\n"
				+ "                    \"code\": \"ES\",\r\n"
				+ "                    \"flag\": \"mx\"\r\n"
				+ "                }\r\n"
				+ "            ],\r\n"
				+ "            \"startTime\": \"00:00\",\r\n"
				+ "            \"endTime\": \"04:00\",\r\n"
				+ "            \"themes\": \"#008c7f\",\r\n"
				+ "            \"reseller\": {\r\n"
				+ "                \"id\": 201050,\r\n"
				+ "                \"logoUrl\": null,\r\n"
				+ "                \"logo\": \"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAIBAQEBAQIBAQECAgICAgQDAgICAgUEBAMEBgUGBgYFBgYGBwkIBgcJBwYGCAsICQoKCgoKBggLDAsKDAkKCgr/2wBDAQICAgICAgUDAwUKBwYHCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgr/wAARCABUAJYDASIAAhEBAxEB/8QAHgABAAEFAQEBAQAAAAAAAAAAAAIBAwQFBwYICQr/xAA1EAABAwMDAwMCAwcFAQAAAAACAQMEAAURBgcSEyFBCDFhIlEjMpEJChQWM3GhFUJzgdGi/8QAGwEBAAIDAQEAAAAAAAAAAAAAAAECAwQGBQf/xAAlEQACAQMDBAMBAQAAAAAAAAAAAQIDBBEFEiETMTJRIkFxYYH/2gAMAwEAAhEDEQA/AP3iiMi2nIV8VfEeXmoinEUHNT6nxQFSLj4qjfmqEXLxU6AUpVRHl5oCY/kT+9KUoCTfmqPyGIravSXEABFSIyXCCiJlVVfCVVvzXBv2nT+sx9EGuLToR1Wpt7YhWN+SKFyjxLhPjQZTwqKookEeQ6aEn5VDPiqzlsjktCO+aj7aPgn9of8AvMkHaC5SdA+jnati7yeTrcbWmqGnUhPCJk2RxmBUCeHI5QyJB8KK+OX+gf8AeR/UpO3BSD6wLBbdQaYlGovTbNa24kyAv0pyAQUW3RTOVEsEuexJ7L9G6i2P9Km+mh5eyms9lLfdLJpiMUZmC6yyJxFaDgiMG2fOM4iCid1Akx37Zr8+t6f2d+ntv7Bfd5fThqh/U+jbPIVq52eJPYlTbV9RCZSFDsTQLlOoIkgkODXjyJPEttVVxLbLhnQ19DlSgpR+S+z+iLbncPSO7GiLXuNoG8tXGzXmEEq3TWV+l1svbsvcVT2UVRFRUVFRFRUrd1+T/wC7besq63mFqL0Y68kySchMHfdGuO8ibKNzQJLQmXlCJs0Htn8Q8JyxX6wV7UJblk5+pDZLApSlXKClKUBQh5eaVWlAa+lKqI8vNATpSlCWsCpN+ajUm/NCCVKUoC4iYHjWk3I0/wDzXoG8aaRoDKfbX44i4nZVNtRx7L9/fwuF8VuW/NSqs47o4LRlsmpej4QtkLQ+gNa3UtLaOtTlxkCRXCPFVGXJDpoqCRo22RcV75PCr2X6VXKV77073zSX8w3HR0AY6u29/pvi0zxFvmgGgKionfg4GUVEXCp7IqJWFvbD2Cum4l0TReo4M6bDllE1AxbXeRwJCIJrGeUe4ZFwTQC9kNF9iSt0etdG7TbO3Pdt21Gtu0rYJlylpDFOasRI5POCCdkzwbXCJ5xXAyg6F10peafP4fTldULjT+pDtJGy9OHo22XsG8y+onSGmbLEciyLiFrK02wGRB6QgMy1QwxyRTZc5dvqUk74bHH0tXntstW6K15oCz6z24vMO42K525qTap0ExJp9kx5CQqPbCovt484rf8AU+K7i1t421FQTyfOby6qXlfqT/i/xcIlSlUEuXitg1StKUoBSlKA19Sb81GqiPLzQE6UpQtIVMS5eKhUm/NCpKlKi9/RKgIPS22/yfXj3VF7VyL1ibq630LsdcIe18tmLrLU0uNp7RHNBLp3Oa6LDclQVPxQjCTkxwPLUR37ZTq5F0hQcZrgPrD3Z2p223S2db3Fmmrjeu5U4IcG2vzpTDQ2O6R/4v8Ah4wOOq0LsplknEBRBZQKqimVTZUIpFdxx7QfpUh+laU7s9pc7jMtkqe7PiTrlIORJmm+Suk8+6v9V4jI+o4q5M0I17kueoXSFc12V1NYguM62PP2KbFanWx9W5URxxkg6rRoi8HBzkSwuCRFx2rrwT9Fb96Ite4O2OpoN0hu8nLbcoj+W3RQiA21+yoQkJCqIQkBIqIuUTX2PbjUbGsYSS4wNQ0PrzVSQOT4dxFBzlcnxz2xhF71xdbRq1LUG4ZcZPOfSfv8Owt9UoT09Kbw0uV7/DwHoD25i6b2NdGxwgtF+g6mu9u1PCtwpHg/6rGmOx5MlmKiILbMp1tZYtqmQCWggogjYh9HJyx9Q4rj3pXlOju1vzbETiwxurGOO2i9hR3TVkdNUTxlwjJfklWu0kAn+auqUFTW1fRyUpOUslilKVJAqXU+KjSgJdT4pUaUBh1US4+KpShLWC5SqCXLxVaE+QpSlCpMS5eKtTnRYDmXipVr7zKLu0K5+ayUk5T4Ibwa/UWqLdpfTs3Ut66yRYUcnn1jRjePinvgG0UiX7IiKqr2RFrx9j0LdWfVTdt1H7PFft1+28tlnB91hRkRJEGdcHiBeSdweG4oWEXssZVL3GvUSjurslkIz4jGSMpuNl5cR5pRXPwKEn6VsXBccjDIDiTgH1GwPPdUyiomF91ElT4Vc98VtySUclU8Gg2g2la2t1hriVbLmblu1TqJu+x4KQxabgPHEZjvttqK4VDcjLILsn4klxVypKte7jigm6+qZV3Cf2RE9q0DtzaS/wBsmRnObj8R9lkPBoqtFy/6VP8ArKpW4lzQhsFIcJBEfzKq+1a8otsuct9McKQ3vBvxNfTAv7qRVa+RHTNjBf8A6Qq7GRcSQce9cn9Kd0tF9ia11RZ79brk3eNdy5KSrZc25TaiLLDDf1NqqIvTZbXGe2a6Ush5b4TC/lbj8k+cqv8A5WOS+TBeMuJr2qIlx8VekpkEXNWKoCYly8VWrdKAuUq3SgMelY8eQKh9I/5rIoW8RUup8VGlCGsEup8VKrdTEuXihBCUShHIw7Ensv2rzt6u78RlTZHjxTvyjGiL/jtXopICbBoSewqtaxxpp0eKZrPb98EN4NVYH5N0J5HGSDgwqIuUIVyQr2VOy+3+a3Zrw+jGcVqLLcWCur1rckAr4NiStCWVBFzhF/StkjnN8lxjGPNbcuHgoYFqsUifdzuD7rrPRVOg0CInBVVFL6sqvfiKFjjlEx2yucncDRcbcDSdy0dcpUhuNcoTsWQ7EdJp0QcBQJQMVRQLBLgk7ovdPas/TyuGjrp9kM0QBXwiJ/7ms4stL1CXIKSIaVqye2eTIfPPo49HO6Ppt1pPuuod440/Slv01DsOidF2a3vx4tuiMLnqvk6+4r72EBsF7dNoeCKSKKD9BTbjEansxnHcGbKEKY90ytXxTptkCd/mtU4zcZmqgVk2G4jUMckKJ1FJVJVRPsmMeKxrvkG6cMkY/D7/AHqx1PishuLHjtqrTIplO+E96xV7KtYwS6nxTqfFRpQEup8UqBFx8UoDW20yMU5L7Vn0pQClKULRFSb80pQqSrXmHUNQ5kOfIrhaUrLR8gednwo9t1ZGkxRw5IEQePySI4CIn6EtbeGZG69lf99KV6DMZsrN+cv+RazuAnz5eKUrTreZNPxLbWEbVE+6p+narceCw3cXXk5KTpIiqpKuEFERMfalKxFzMcJSinlfasKlKxgUpSgLcgyBExSlKyLsD//Z\",\r\n"
				+ "                \"resellerTranslation\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"id\": 200700,\r\n"
				+ "                        \"language\": {\r\n"
				+ "                            \"name\": \"Español\",\r\n"
				+ "                            \"code\": \"ES\",\r\n"
				+ "                            \"flag\": \"mx\"\r\n"
				+ "                        },\r\n"
				+ "                        \"disclaimer\": \"hi\",\r\n"
				+ "                        \"reseller\": null\r\n"
				+ "                    }\r\n"
				+ "                ]\r\n"
				+ "            },\r\n"
				+ "            \"logo\": null,\r\n"
				+ "            \"rules\": [\r\n"
				+ "                {\r\n"
				+ "                    \"id\": 32861,\r\n"
				+ "                    \"name\": \"Location\",\r\n"
				+ "                    \"rule\": \"L,T,B\"\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"id\": 32880,\r\n"
				+ "                    \"name\": \"Provider\",\r\n"
				+ "                    \"rule\": \"B,T,L\"\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        \"displayName\": \"Male\",\r\n"
				+ "        \"pssCode\": \"M\",\r\n"
				+ "        \"active\": true,\r\n"
				+ "        \"partnerCode\": \"M\",\r\n"
				+ "        \"createdTsz\": null,\r\n"
				+ "        \"updatedTsz\": null,\r\n"
				+ "        \"seq\": 1,\r\n"
				+ "        \"codeGroup\": null\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"id\": 201969,\r\n"
				+ "        \"practice\": {\r\n"
				+ "            \"id\": 24702,\r\n"
				+ "            \"extPracticeId\": \"24702\",\r\n"
				+ "            \"active\": true,\r\n"
				+ "            \"name\": \"PSS-NG-PG16CN\",\r\n"
				+ "            \"practiceId\": \"24702\",\r\n"
				+ "            \"partner\": {\r\n"
				+ "                \"id\": 2,\r\n"
				+ "                \"name\": \"NextGen\",\r\n"
				+ "                \"code\": \"NG\",\r\n"
				+ "                \"integrationId\": \"88\"\r\n"
				+ "            },\r\n"
				+ "            \"timeZone\": null,\r\n"
				+ "            \"language\": [\r\n"
				+ "                {\r\n"
				+ "                    \"name\": \"English\",\r\n"
				+ "                    \"code\": \"EN\",\r\n"
				+ "                    \"flag\": \"us\"\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"name\": \"Español\",\r\n"
				+ "                    \"code\": \"ES\",\r\n"
				+ "                    \"flag\": \"mx\"\r\n"
				+ "                }\r\n"
				+ "            ],\r\n"
				+ "            \"startTime\": \"00:00\",\r\n"
				+ "            \"endTime\": \"04:00\",\r\n"
				+ "            \"themes\": \"#008c7f\",\r\n"
				+ "            \"reseller\": {\r\n"
				+ "                \"id\": 201050,\r\n"
				+ "                \"logoUrl\": null,\r\n"
				+ "                \"logo\": \"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAIBAQEBAQIBAQECAgICAgQDAgICAgUEBAMEBgUGBgYFBgYGBwkIBgcJBwYGCAsICQoKCgoKBggLDAsKDAkKCgr/2wBDAQICAgICAgUDAwUKBwYHCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgr/wAARCABUAJYDASIAAhEBAxEB/8QAHgABAAEFAQEBAQAAAAAAAAAAAAIBAwQFBwYICQr/xAA1EAABAwMDAwMCAwcFAQAAAAACAQMEAAURBgcSEyFBCDFhIlEjMpEJChQWM3GhFUJzgdGi/8QAGwEBAAIDAQEAAAAAAAAAAAAAAAECAwQGBQf/xAAlEQACAQMDBAMBAQAAAAAAAAAAAQIDBBEFEiETMTJRIkFxYYH/2gAMAwEAAhEDEQA/AP3iiMi2nIV8VfEeXmoinEUHNT6nxQFSLj4qjfmqEXLxU6AUpVRHl5oCY/kT+9KUoCTfmqPyGIravSXEABFSIyXCCiJlVVfCVVvzXBv2nT+sx9EGuLToR1Wpt7YhWN+SKFyjxLhPjQZTwqKookEeQ6aEn5VDPiqzlsjktCO+aj7aPgn9of8AvMkHaC5SdA+jnati7yeTrcbWmqGnUhPCJk2RxmBUCeHI5QyJB8KK+OX+gf8AeR/UpO3BSD6wLBbdQaYlGovTbNa24kyAv0pyAQUW3RTOVEsEuexJ7L9G6i2P9Km+mh5eyms9lLfdLJpiMUZmC6yyJxFaDgiMG2fOM4iCid1Akx37Zr8+t6f2d+ntv7Bfd5fThqh/U+jbPIVq52eJPYlTbV9RCZSFDsTQLlOoIkgkODXjyJPEttVVxLbLhnQ19DlSgpR+S+z+iLbncPSO7GiLXuNoG8tXGzXmEEq3TWV+l1svbsvcVT2UVRFRUVFRFRUrd1+T/wC7besq63mFqL0Y68kySchMHfdGuO8ibKNzQJLQmXlCJs0Htn8Q8JyxX6wV7UJblk5+pDZLApSlXKClKUBQh5eaVWlAa+lKqI8vNATpSlCWsCpN+ajUm/NCCVKUoC4iYHjWk3I0/wDzXoG8aaRoDKfbX44i4nZVNtRx7L9/fwuF8VuW/NSqs47o4LRlsmpej4QtkLQ+gNa3UtLaOtTlxkCRXCPFVGXJDpoqCRo22RcV75PCr2X6VXKV77073zSX8w3HR0AY6u29/pvi0zxFvmgGgKionfg4GUVEXCp7IqJWFvbD2Cum4l0TReo4M6bDllE1AxbXeRwJCIJrGeUe4ZFwTQC9kNF9iSt0etdG7TbO3Pdt21Gtu0rYJlylpDFOasRI5POCCdkzwbXCJ5xXAyg6F10peafP4fTldULjT+pDtJGy9OHo22XsG8y+onSGmbLEciyLiFrK02wGRB6QgMy1QwxyRTZc5dvqUk74bHH0tXntstW6K15oCz6z24vMO42K525qTap0ExJp9kx5CQqPbCovt484rf8AU+K7i1t421FQTyfOby6qXlfqT/i/xcIlSlUEuXitg1StKUoBSlKA19Sb81GqiPLzQE6UpQtIVMS5eKhUm/NCpKlKi9/RKgIPS22/yfXj3VF7VyL1ibq630LsdcIe18tmLrLU0uNp7RHNBLp3Oa6LDclQVPxQjCTkxwPLUR37ZTq5F0hQcZrgPrD3Z2p223S2db3Fmmrjeu5U4IcG2vzpTDQ2O6R/4v8Ah4wOOq0LsplknEBRBZQKqimVTZUIpFdxx7QfpUh+laU7s9pc7jMtkqe7PiTrlIORJmm+Suk8+6v9V4jI+o4q5M0I17kueoXSFc12V1NYguM62PP2KbFanWx9W5URxxkg6rRoi8HBzkSwuCRFx2rrwT9Fb96Ite4O2OpoN0hu8nLbcoj+W3RQiA21+yoQkJCqIQkBIqIuUTX2PbjUbGsYSS4wNQ0PrzVSQOT4dxFBzlcnxz2xhF71xdbRq1LUG4ZcZPOfSfv8Owt9UoT09Kbw0uV7/DwHoD25i6b2NdGxwgtF+g6mu9u1PCtwpHg/6rGmOx5MlmKiILbMp1tZYtqmQCWggogjYh9HJyx9Q4rj3pXlOju1vzbETiwxurGOO2i9hR3TVkdNUTxlwjJfklWu0kAn+auqUFTW1fRyUpOUslilKVJAqXU+KjSgJdT4pUaUBh1US4+KpShLWC5SqCXLxVaE+QpSlCpMS5eKtTnRYDmXipVr7zKLu0K5+ayUk5T4Ibwa/UWqLdpfTs3Ut66yRYUcnn1jRjePinvgG0UiX7IiKqr2RFrx9j0LdWfVTdt1H7PFft1+28tlnB91hRkRJEGdcHiBeSdweG4oWEXssZVL3GvUSjurslkIz4jGSMpuNl5cR5pRXPwKEn6VsXBccjDIDiTgH1GwPPdUyiomF91ElT4Vc98VtySUclU8Gg2g2la2t1hriVbLmblu1TqJu+x4KQxabgPHEZjvttqK4VDcjLILsn4klxVypKte7jigm6+qZV3Cf2RE9q0DtzaS/wBsmRnObj8R9lkPBoqtFy/6VP8ArKpW4lzQhsFIcJBEfzKq+1a8otsuct9McKQ3vBvxNfTAv7qRVa+RHTNjBf8A6Qq7GRcSQce9cn9Kd0tF9ia11RZ79brk3eNdy5KSrZc25TaiLLDDf1NqqIvTZbXGe2a6Ush5b4TC/lbj8k+cqv8A5WOS+TBeMuJr2qIlx8VekpkEXNWKoCYly8VWrdKAuUq3SgMelY8eQKh9I/5rIoW8RUup8VGlCGsEup8VKrdTEuXihBCUShHIw7Ensv2rzt6u78RlTZHjxTvyjGiL/jtXopICbBoSewqtaxxpp0eKZrPb98EN4NVYH5N0J5HGSDgwqIuUIVyQr2VOy+3+a3Zrw+jGcVqLLcWCur1rckAr4NiStCWVBFzhF/StkjnN8lxjGPNbcuHgoYFqsUifdzuD7rrPRVOg0CInBVVFL6sqvfiKFjjlEx2yucncDRcbcDSdy0dcpUhuNcoTsWQ7EdJp0QcBQJQMVRQLBLgk7ovdPas/TyuGjrp9kM0QBXwiJ/7ms4stL1CXIKSIaVqye2eTIfPPo49HO6Ppt1pPuuod440/Slv01DsOidF2a3vx4tuiMLnqvk6+4r72EBsF7dNoeCKSKKD9BTbjEansxnHcGbKEKY90ytXxTptkCd/mtU4zcZmqgVk2G4jUMckKJ1FJVJVRPsmMeKxrvkG6cMkY/D7/AHqx1PishuLHjtqrTIplO+E96xV7KtYwS6nxTqfFRpQEup8UqBFx8UoDW20yMU5L7Vn0pQClKULRFSb80pQqSrXmHUNQ5kOfIrhaUrLR8gednwo9t1ZGkxRw5IEQePySI4CIn6EtbeGZG69lf99KV6DMZsrN+cv+RazuAnz5eKUrTreZNPxLbWEbVE+6p+narceCw3cXXk5KTpIiqpKuEFERMfalKxFzMcJSinlfasKlKxgUpSgLcgyBExSlKyLsD//Z\",\r\n"
				+ "                \"resellerTranslation\": [\r\n"
				+ "                    {\r\n"
				+ "                        \"id\": 200700,\r\n"
				+ "                        \"language\": {\r\n"
				+ "                            \"name\": \"Español\",\r\n"
				+ "                            \"code\": \"ES\",\r\n"
				+ "                            \"flag\": \"mx\"\r\n"
				+ "                        },\r\n"
				+ "                        \"disclaimer\": \"hi\",\r\n"
				+ "                        \"reseller\": null\r\n"
				+ "                    }\r\n"
				+ "                ]\r\n"
				+ "            },\r\n"
				+ "            \"logo\": null,\r\n"
				+ "            \"rules\": [\r\n"
				+ "                {\r\n"
				+ "                    \"id\": 32861,\r\n"
				+ "                    \"name\": \"Location\",\r\n"
				+ "                    \"rule\": \"L,T,B\"\r\n"
				+ "                },\r\n"
				+ "                {\r\n"
				+ "                    \"id\": 32880,\r\n"
				+ "                    \"name\": \"Provider\",\r\n"
				+ "                    \"rule\": \"B,T,L\"\r\n"
				+ "                }\r\n"
				+ "            ]\r\n"
				+ "        },\r\n"
				+ "        \"displayName\": \"Male\",\r\n"
				+ "        \"pssCode\": \"M\",\r\n"
				+ "        \"active\": true,\r\n"
				+ "        \"partnerCode\": \"M\",\r\n"
				+ "        \"createdTsz\": null,\r\n"
				+ "        \"updatedTsz\": null,\r\n"
				+ "        \"seq\": 1,\r\n"
				+ "        \"codeGroup\": null\r\n"
				+ "    }\r\n"
				+ "]";
		return genderMap;
	}
	
	
	public String saveLink(String linkId,String linkProvider,String extBookIdLink,String catId,String displayName) {
		String saveLink ="{\r\n"
				+ "    \"type\": \"LOGINLESS\",\r\n"
				+ "    \"location\": [],\r\n"
				+ "    \"resource\": [\r\n"
				+ "        {\r\n"
				+ "            \"isageRule\": false,\r\n"
				+ "            \"id\": \""+linkId+"\",\r\n"
				+ "            \"name\": \""+linkProvider+"\",\r\n"
				+ "            \"displayName\": \""+displayName+"\",\r\n"
				+ "            \"extBookId\": \""+extBookIdLink+"\",\r\n"
				+ "            \"status\": false,\r\n"
				+ "            \"categoryId\": \""+catId+"\",\r\n"
				+ "            \"providerlinks\": {}\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return saveLink;
	}
	public String location(String locationId,String zipCode,String locationDisplayName,String extLocationId) {
		String location ="[\r\n"
				+ "  {\r\n"
				+ "    \"address\": {\r\n"
				+ "      \"address1\": \"Address line 1\",\r\n"
				+ "      \"address2\": \"Address line 2\",\r\n"
				+ "      \"city\": \"Cary\",\r\n"
				+ "      \"country\": \"string\",\r\n"
				+ "      \"id\": \""+locationId+"\",\r\n"
				+ "      \"latitude\": \"35.7469276\",\r\n"
				+ "      \"longitude\": \"-78.77481569999999\",\r\n"
				+ "      \"state\": \"NC\",\r\n"
				+ "      \"zipCode\": \""+zipCode+"\"\r\n"
				+ "    },\r\n"
				+ "    \"deleted\": true,\r\n"
				+ "    \"directionUrl\": null,\r\n"
				+ "    \"displayName\": \""+locationDisplayName+"\",\r\n"
				+ "    \"displayNames\": {\r\n"
				+ "      \"EN\": \"PSS WLA\",\r\n"
				+ "      \"ES\": \"PSS WLA\"\r\n"
				+ "    },\r\n"
				+ "    \"extLocationId\": \""+extLocationId+"\",\r\n"
				+ "    \"id\": \""+locationId+"\",\r\n"
				+ "    \"links\": [\r\n"
				+ "      {\r\n"
				+ "        \"appTypeId\": \"string\",\r\n"
				+ "        \"bookId\": \"206458\",\r\n"
				+ "        \"extAppTypeCategoryId\": \"8F563A6C-7098-4446-97E2-7EF9345ED469\",\r\n"
				+ "        \"extAppTypeId\": \"86D0E00F-C136-4A24-BF7A-6C17E9AB35ED\",\r\n"
				+ "        \"extBookCategoryId\": \"C8E55131-F1D8-401D-A107-F90222DBD977\",\r\n"
				+ "        \"extBookId\": \"F49641D6-CDF1-4264-B5A5-7489F7E58F8D\",\r\n"
				+ "        \"extLocationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n"
				+ "        \"guid\": \"fc7ec01a-c5af-4be1-804d-f0ef00af576e\",\r\n"
				+ "        \"id\": 0,\r\n"
				+ "        \"locationId\": \""+locationId+"\",\r\n"
				+ "        \"type\": \"LOGINLESS\"\r\n"
				+ "      }\r\n"
				+ "    ]\r\n"
				+ "  }\r\n"
				+ "]";
		return location;
	}
	
	
	public String locationReorder() {
		String locationreorder ="{\r\n"
				+ "    \"source\": 2,\r\n"
				+ "    \"target\": 1\r\n"
				+ "}";
		return locationreorder;
	}
	
	public String locakoutPost(String lockoutkey,String lockoutValue,String lockoutType,String lockoutGroup) {
		String lockout ="{\r\n"
				+ "        \"key\": \""+lockoutkey+"\",\r\n"
				+ "        \"value\": \""+lockoutValue+"\",\r\n"
				+ "        \"type\": \""+lockoutType+"\",\r\n"
				+ "        \"group\": \""+lockoutGroup+"\",\r\n"
				+ "        \"messages\": {\r\n"
				+ "            \"EN\": null\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "";
		return lockout;
	}
	public String locakoutPut(String id,String lockoutkey,String lockoutValue,String lockoutType,String lockoutGroup) {
		String lockout ="{\r\n"
				+ "        \"id\": \""+id+"\",\r\n"
				+ "        \"key\": \""+lockoutkey+"\",\r\n"
				+ "        \"value\": \""+lockoutValue+"\",\r\n"
				+ "        \"type\": \""+lockoutType+"\",\r\n"
				+ "        \"group\": \""+lockoutGroup+"\",\r\n"
				+ "        \"messages\": {\r\n"
				+ "            \"EN\": null\r\n"
				+ "        }\r\n"
				+ "    }\r\n"
				+ "";
		return lockout;
	}
	
	public String loginlessSavePayload(String guid,String link) {
		String loginlessSave = "{\r\n"
				+ "    \"enabled\": false,\r\n"
				+ "    \"guid\": \"4bb5e195-b525-4ce6-9848-60edaa4c15ee\",\r\n"
				+ "    \"link\": \"https://dev3-pss.dev.medfusion.net/psspatient/pss-patient-loginless/4bb5e195-b525-4ce6-9848-60edaa4c15ee\",\r\n"
				+ "    \"newPatient\": true,\r\n"
				+ "    \"existingPatient\": true\r\n"
				+ "}";
		return loginlessSave;
	}
	public String patientInfo() {
		String patientInfo = "{\r\n"
				+ "    \"flowType\": \"LG_LOG\",\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222536,\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 1,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "                \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"FN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222532,\r\n"
				+ "            \"entity\": \"Patient Last Name\",\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 2,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient Last Name\",\r\n"
				+ "                \"ES\": \"Apellido del paciente\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"LN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222542,\r\n"
				+ "            \"entity\": \"Date Of Birth\",\r\n"
				+ "            \"code\": \"DOB\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 3,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Date Of Birth\",\r\n"
				+ "                \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"DOB\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222543,\r\n"
				+ "            \"entity\": \"Sex assigned at birth\",\r\n"
				+ "            \"code\": \"GENDER\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 4,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Sex assigned at birth\",\r\n"
				+ "                \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"GENDER\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222538,\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 5,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Email Address\",\r\n"
				+ "                \"ES\": \"Dirección de email\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"EMAIL\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222535,\r\n"
				+ "            \"entity\": \"Preferred Phone Number\",\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 6,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": true,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Preferred Phone Number\",\r\n"
				+ "                \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"PHONE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222533,\r\n"
				+ "            \"entity\": \"Insurance ID\",\r\n"
				+ "            \"code\": \"INSID\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 7,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Insurance ID\",\r\n"
				+ "                \"ES\": \"id seguro\\\"\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"INSID\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222539,\r\n"
				+ "            \"entity\": \"Address1\",\r\n"
				+ "            \"code\": \"ADDR1\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 9,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Address1\",\r\n"
				+ "                \"ES\": \"Dirección 1\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR1\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222540,\r\n"
				+ "            \"entity\": \"tttttt\",\r\n"
				+ "            \"code\": \"ADDR2\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 10,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"tttttt\",\r\n"
				+ "                \"ES\": \"Dirección 2\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR2\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222541,\r\n"
				+ "            \"entity\": \"City\",\r\n"
				+ "            \"code\": \"CITY\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 11,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"City\",\r\n"
				+ "                \"ES\": \"Ciudad\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"CITY\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222537,\r\n"
				+ "            \"entity\": \"State\",\r\n"
				+ "            \"code\": \"STATE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 12,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"State\",\r\n"
				+ "                \"ES\": \"Estado\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"STATE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222534,\r\n"
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 13,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Zip Code\",\r\n"
				+ "                \"ES\": \"Código posta\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ZIP\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return patientInfo;
	}
	
	public String patientInfoWithoutFlowType() {
		String patientInfo = "{\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222536,\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 1,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "                \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"FN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222532,\r\n"
				+ "            \"entity\": \"Patient Last Name\",\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 2,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient Last Name\",\r\n"
				+ "                \"ES\": \"Apellido del paciente\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"LN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222542,\r\n"
				+ "            \"entity\": \"Date Of Birth\",\r\n"
				+ "            \"code\": \"DOB\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 3,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Date Of Birth\",\r\n"
				+ "                \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"DOB\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222543,\r\n"
				+ "            \"entity\": \"Sex assigned at birth\",\r\n"
				+ "            \"code\": \"GENDER\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 4,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Sex assigned at birth\",\r\n"
				+ "                \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"GENDER\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222538,\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 5,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Email Address\",\r\n"
				+ "                \"ES\": \"Dirección de email\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"EMAIL\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222535,\r\n"
				+ "            \"entity\": \"Preferred Phone Number\",\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 6,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": true,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Preferred Phone Number\",\r\n"
				+ "                \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"PHONE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222533,\r\n"
				+ "            \"entity\": \"Insurance ID\",\r\n"
				+ "            \"code\": \"INSID\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 7,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Insurance ID\",\r\n"
				+ "                \"ES\": \"id seguro\\\"\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"INSID\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222539,\r\n"
				+ "            \"entity\": \"Address1\",\r\n"
				+ "            \"code\": \"ADDR1\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 9,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Address1\",\r\n"
				+ "                \"ES\": \"Dirección 1\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR1\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222540,\r\n"
				+ "            \"entity\": \"tttttt\",\r\n"
				+ "            \"code\": \"ADDR2\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 10,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"tttttt\",\r\n"
				+ "                \"ES\": \"Dirección 2\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR2\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222541,\r\n"
				+ "            \"entity\": \"City\",\r\n"
				+ "            \"code\": \"CITY\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 11,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"City\",\r\n"
				+ "                \"ES\": \"Ciudad\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"CITY\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222537,\r\n"
				+ "            \"entity\": \"State\",\r\n"
				+ "            \"code\": \"STATE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 12,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"State\",\r\n"
				+ "                \"ES\": \"Estado\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"STATE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222534,\r\n"
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 13,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Zip Code\",\r\n"
				+ "                \"ES\": \"Código posta\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ZIP\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return patientInfo;
	}

	public String practice(String startTime,String endTime,String practiceId,String name,String timeZone) {
		String practice ="{\r\n"
				+ "    \"active\": true,\r\n"
				+ "    \"endtime\": \"04:00\",\r\n"
				+ "    \"extPracticeId\": \"24702\",\r\n"
				+ "    \"id\": 0,\r\n"
				+ "    \"languages\": [\r\n"
				+ "        {\r\n"
				+ "            \"name\": \"English\",\r\n"
				+ "            \"code\": \"EN\",\r\n"
				+ "            \"flag\": \"us\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"logo\": null,\r\n"
				+ "    \"name\": \"PSS-NG-PG16CN\",\r\n"
				+ "    \"partner\": \"NG\",\r\n"
				+ "    \"practiceId\": \"24702\",\r\n"
				+ "    \"resellerLogo\": null,\r\n"
				+ "    \"rules\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 32861,\r\n"
				+ "            \"name\": \"Location\",\r\n"
				+ "            \"rule\": \"L,T,B\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 32880,\r\n"
				+ "            \"name\": \"Provider\",\r\n"
				+ "            \"rule\": \"B,T,L\"\r\n"
				+ "        }\r\n"
				+ "    ],\r\n"
				+ "    \"starttime\": \"00:00\",\r\n"
				+ "    \"timezone\": \"America/Chicago\"\r\n"
				+ "}";
		return practice;
	}
	

	public String preRequisiteAppPayload(String id,String name,String extPreAppTypeId,String categoryId,String categoryName) {
		String resourceConfig = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": \""+id+"\",\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"extPreAppTypeId\": \""+extPreAppTypeId+"\",\r\n"
				+ "        \"numOfDays\": -1,\r\n"
				+ "        \"categoryId\": \""+categoryId+"\",\r\n"
				+ "        \"categoryName\": \""+categoryName+"\",\r\n"
				+ "        \"selected\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return resourceConfig;
	}
	public String locationSearchPayload() {
		String locationSearch = "{\r\n"
				+ "    \"type\": null\r\n"
				+ "}";
		return locationSearch;
	}
	
	public String booksearchPayload() {
		String locationSearch = "{\r\n"
				+ "    \"type\": null\r\n"
				+ "}";
		return locationSearch;
	}
	
	public String locationSearchEmptyPayload() {
		String locationSearch = "";
		return locationSearch;
	}

	public String booksearchEmptyPayload() {
		String locationSearch = "";
		return locationSearch;
	}
	
	public String anonymousPost(String code,String anonymousGuid,String link) {
		String anonymous = "[\r\n"
				+ "    {\r\n"
				+ "        \"code\": \""+code+"\",\r\n"
				+ "        \"guid\": \""+anonymousGuid+"\",\r\n"
				+ "        \"newPatients\": false,\r\n"
				+ "        \"existingPatients\": false,\r\n"
				+ "        \"link\": \""+link+"\",\r\n"
				+ "        \"allowOtp\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return anonymous;
	}
	
	
	public String anonymousWithoutCodePost(String anonymousGuid,String link) {
		String anonymous = "[\r\n"
				+ "    {\r\n"
				+ "        \"guid\": \""+anonymousGuid+"\",\r\n"
				+ "        \"newPatients\": false,\r\n"
				+ "        \"existingPatients\": false,\r\n"
				+ "        \"link\": \""+link+"\",\r\n"
				+ "        \"allowOtp\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return anonymous;
	}
	
	public String ssoPost(String code,String ssoGuid,String displayName) {
		String sso = "[\r\n"
				+ "    {\r\n"
				+ "        \"code\": \""+code+"\",\r\n"
				+ "        \"guid\": \""+ssoGuid+"\",\r\n"
				+ "        \"selected\": true,\r\n"
				+ "        \"displayName\": \""+displayName+"\",\r\n"
				+ "        \"newPatients\": false,\r\n"
				+ "        \"existingPatients\": false,\r\n"
				+ "        \"allowOtp\": false\r\n"
				+ "    }\r\n"
				+ "]";
		return sso;
	}
	
	public String ssoWithoutCodePost(String ssoGuid,String displayName) {
		String sso = "[\r\n"
				+ "    {\r\n"
				+ "        \"guid\": \""+ssoGuid+"\",\r\n"
				+ "        \"selected\": true,\r\n"
				+ "        \"displayName\": \""+displayName+"\",\r\n"
				+ "        \"newPatients\": false,\r\n"
				+ "        \"existingPatients\": false,\r\n"
				+ "        \"allowOtp\": false\r\n"
				+ "    }\r\n"
				+ "]";
		return sso;
	}
	
	public String reseller(String logo) {
		String reseller ="{\r\n"
				+ "    \"logo\": \""+logo+"\",\r\n"
				+ "    \"logoUrl\": null,\r\n"
				+ "    \"imageSize\": 5.8955078125,\r\n"
				+ "    \"disclaimers\": {\r\n"
				+ "        \"EN\": null,\r\n"
				+ "        \"ES\": \"hi\"\r\n"
				+ "    }\r\n"
				+ "}";
		return reseller;
	}
	
	public String specialityPayload(String specialityId ) {
		String speciality = "[\r\n"
				+ "    {\r\n"
				+ "        \"isageRule\": false,\r\n"
				+ "        \"isgenderRule\": false,\r\n"
				+ "        \"id\": \""+specialityId+"\",\r\n"
				+ "        \"name\": \"specialty\",\r\n"
				+ "        \"displayNames\": {\r\n"
				+ "            \"EN\": \"specialty\",\r\n"
				+ "            \"ES\": \"specialty's es\"\r\n"
				+ "        },\r\n"
				+ "        \"displayName\": \"specialty\",\r\n"
				+ "        \"sortOrder\": 0,\r\n"
				+ "        \"ageRule\": \"\",\r\n"
				+ "        \"genderRule\": [\r\n"
				+ "            {\r\n"
				+ "                \"id\": 0,\r\n"
				+ "                \"displayName\": \"Female\",\r\n"
				+ "                \"pssCode\": \"F\",\r\n"
				+ "                \"partnerCode\": \"F\",\r\n"
				+ "                \"active\": true,\r\n"
				+ "                \"seq\": 2,\r\n"
				+ "                \"codeGroup\": \"gender\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 0,\r\n"
				+ "                \"displayName\": \"Male\",\r\n"
				+ "                \"pssCode\": \"M\",\r\n"
				+ "                \"partnerCode\": \"M\",\r\n"
				+ "                \"active\": true,\r\n"
				+ "                \"seq\": 1,\r\n"
				+ "                \"codeGroup\": \"gender\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 0,\r\n"
				+ "                \"displayName\": \"Unknown\",\r\n"
				+ "                \"pssCode\": \"U\",\r\n"
				+ "                \"partnerCode\": \"U\",\r\n"
				+ "                \"active\": true,\r\n"
				+ "                \"seq\": 3,\r\n"
				+ "                \"codeGroup\": \"gender\"\r\n"
				+ "            },\r\n"
				+ "            {\r\n"
				+ "                \"id\": 0,\r\n"
				+ "                \"displayName\": \"Undifferentiated (Other)\",\r\n"
				+ "                \"pssCode\": \"UN\",\r\n"
				+ "                \"partnerCode\": \"O\",\r\n"
				+ "                \"active\": true,\r\n"
				+ "                \"seq\": 5,\r\n"
				+ "                \"codeGroup\": \"gender\"\r\n"
				+ "            }\r\n"
				+ "        ]\r\n"
				+ "    }\r\n"
				+ "]";
		return speciality;
	}
	
	public String specialityReorderPayload( ) {
		String speciality = "{\"source\":1,\"target\":2}\r\n"
				+ "";
		return speciality;
	}
	
	public String insuranceReorder() {
		String insuranceReorder ="{\r\n"
				+ "    \"source\": 2,\r\n"
				+ "    \"target\": 1\r\n"
				+ "}";
		return insuranceReorder;
	}
	
	public String insuranceSave() {
		String insuranceSave ="[\r\n"
				+ "    {\r\n"
				+ "        \"id\": null,\r\n"
				+ "        \"name\": \"Blue Cross Payer\",\r\n"
				+ "        \"selected\": true,\r\n"
				+ "        \"extInsuranceCarrierId\": \"F5B58B41-6E28-4FAC-912D-4AB682AF310E\"\r\n"
				+ "    }\r\n"
				+ "]";
		return insuranceSave;
	}
	
	public String customDataPayload() {
		String customData ="{\r\n"
				+ "    \"flowType\": \"LG_LOG\",\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": null,\r\n"
				+ "            \"entity\": \"Custom Field\",\r\n"
				+ "            \"code\": \"DP\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 14,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"AAA\",\r\n"
				+ "                \"ES\": \"AAA\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"pmField\": \"Day Phone\",\r\n"
				+ "            \"key\": \"DP\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return customData;
	}
	public String privacyPolicyToggleOnLL() {
		String resourceConfigSave = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"ANNOUNCEMENT\",\r\n"
				+ "        \"key\": \"showPrivacyPolicyMessage\",\r\n"
				+ "        \"value\": true\r\n"
				+ "    }\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "";
		return resourceConfigSave;
	}
	

	public String privacyPolicyToggleOFFLL() {
		String resourceConfigSave = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"ANNOUNCEMENT\",\r\n"
				+ "        \"key\": \"showPrivacyPolicyMessage\",\r\n"
				+ "        \"value\": false\r\n"
				+ "    }\r\n"
				+ "]\r\n"
				+ "\r\n"
				+ "";
		return resourceConfigSave;
	}
	
	public String privacyPolicyToggleONAnonymous() {
		String privacyPolicyToggleONAnonymous = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"showPrivacyForAnonymous\",\r\n"
				+ "        \"value\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"allowDuplicatePatient\",\r\n"
				+ "        \"value\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return privacyPolicyToggleONAnonymous;
	}

	public String privacyPolicyToggleOFFAnonymous() {
		String privacyPolicyToggleOFFAnonymous = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"showPrivacyForAnonymous\",\r\n"
				+ "        \"value\": false\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"allowDuplicatePatient\",\r\n"
				+ "        \"value\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return privacyPolicyToggleOFFAnonymous;
	}
	
	public String alertAndLocakout(String lockoutkey,String lockoutValue,String lockoutType,String lockoutGroup, String msg) {
		String lockout ="{\r\n"
				+ "        \"key\": \""+lockoutkey+"\",\r\n"
				+ "        \"value\": \""+lockoutValue+"\",\r\n"
				+ "        \"type\": \""+lockoutType+"\",\r\n"
				+ "        \"group\": \""+lockoutGroup+"\",\r\n"
				+ " 	    \"messages\": {\r\n"
				+ "    \"EN\": \""+msg+"\",\r\n"
				+ "    \"ES\": \"\"\r\n"
				+ "  }\r\n"
				+ "}";
		return lockout;
	}
	
	
	public String patientStatusPost(String id,String key,String type,String lockoutGroup, String engMsg, String spMsg,String displayMsg) {
		String lockout ="{\r\n"
				+ "  \"id\": "+id+",\r\n"
				+ "  \"key\": \""+key+"\",\r\n"
				+ "  \"value\": \""+key+"\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"messages\": {\r\n"
				+ "    \"EN\": \""+engMsg+"\",\r\n"
				+ "    \"ES\": \""+spMsg+"\"\r\n"
				+ "  },\r\n"
				+ "  \"group\": \""+lockoutGroup+"\",\r\n"
				+ "  \"displayMessage\": \""+displayMsg+"\"\r\n"
				+ "}";
		return lockout;
	}
	
	public String patientStatusAT(String id,String key,String type,String lockoutGroup, String engMsg) {
		String lockout ="{\r\n"
				+ "  \"id\": "+id+",\r\n"
				+ "  \"key\": \""+key+"\",\r\n"
				+ "  \"value\": \""+key+"\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"messages\": {\r\n"
				+ "    \"EN\": \""+engMsg+"\",\r\n"
				+ "    \"ES\": \""+engMsg+"\"\r\n"
				+ "  },\r\n"
				+ "  \"group\": \""+lockoutGroup+"\"\r\n"
				+ "}";
		return lockout;
	}

	public String patientStatusUpdateGE(String id,String key,String type,String lockoutGroup, String engMsg, String esMsg) {
		String lockout ="{\r\n"
				+ "  \"id\": "+id+",\r\n"
				+ "  \"key\": \""+key+"\",\r\n"
				+ "  \"value\": \"Active\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"messages\": {\r\n"
				+ "    \"EN\": \""+engMsg+"\",\r\n"
				+ "    \"ES\": \""+esMsg+"\"\r\n"
				+ "  },\r\n"
				+ "  \"group\": \""+lockoutGroup+"\"\r\n"
				+ "}";
		return lockout;
	}
	
	public String patientStatusGE() {
		String lockout ="{\r\n"
				+ "  \"key\": \"A\",\r\n"
				+ "  \"value\": \"Active\",\r\n"
				+ "  \"type\": \"PATIENT_STATUS\",\r\n"
				+ "  \"selected\": true,\r\n"
				+ "  \"group\": \"LOCKOUT\",\r\n"
				+ "  \"id\": null\r\n"
				+ "}";
		return lockout;
	}
	
	public String patientStatusGW(String id,String key,String type,String lockoutGroup, String engMsg,String displayMsg) {
		String lockout ="{\r\n"
				+ "  \"id\": "+id+",\r\n"
				+ "  \"key\": \""+key+"\",\r\n"
				+ "  \"value\": \"Active\",\r\n"
				+ "  \"type\": \""+type+"\",\r\n"
				+ "  \"messages\": {\r\n"
				+ "    \"EN\": \""+engMsg+"\"\r\n"
				+ "  },\r\n"
				+ "  \"group\": \""+lockoutGroup+"\",\r\n"
				+ "  \"displayMessage\": \""+engMsg+"\"\r\n"
				+ "}";
		return lockout;
	}

	public String multiplePatientAnnoucement(String customMessage) {
		String saveAnn = "{\r\n"
				+ "    \"text\": {\r\n"
				+ "        \"EN\": \"" + customMessage + "\",\r\n"
				+ "        \"ES\": \"Según la información proporcionada, no podemos localizar con precisión su registro de salud. Por favor, póngase en contacto con la práctica para verificar su información y programar su cita.\"\r\n"
				+ "    },\r\n"
				+ "    \"display\": \"Based on the information provided, we cannot accurately locate your health record. Please contact the practice to verify your information and schedule your appointment.\",\r\n"
				+ "    \"type\": \"Inactive or Multiple Patient\",\r\n"
				+ "    \"code\": \"IOMP\",\r\n"
				+ "    \"selected\": false,\r\n"
				+ "    \"description\": \"Displayed when patient matching returns multiple patients or the patient found is inactive\"\r\n"
				+ "}";
		return saveAnn;
	}	

	public String allowDuplicateONOff(boolean dupValue) {
		String allowDuplicateOff = "[\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"showPrivacyForAnonymous\",\r\n"
				+ "        \"value\": true\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "        \"group\": \"RULEENGINE\",\r\n"
				+ "        \"key\": \"allowDuplicatePatient\",\r\n"
				+ "    \"value\": "+dupValue+"\r\n"		
				+ "    }\r\n"
				+ "]";
		return allowDuplicateOff;
	}

	public String patientMatchGE() {
		
		String payload="{\r\n"
				+ "  \"flowType\": \"LG_LOG\",\r\n"
				+ "  \"patientMatches\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4397,\r\n"
				+ "      \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "      \"code\": \"FN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 1,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "        \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"FN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4401,\r\n"
				+ "      \"entity\": \"Patient Last Name\",\r\n"
				+ "      \"code\": \"LN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 2,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient Last Name\",\r\n"
				+ "        \"ES\": \"Apellido del paciente\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"LN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4405,\r\n"
				+ "      \"entity\": \"Date Of Birth\",\r\n"
				+ "      \"code\": \"DOB\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 3,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Date Of Birth\",\r\n"
				+ "        \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"DOB\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203411,\r\n"
				+ "      \"entity\": \"Sex assigned at birth\",\r\n"
				+ "      \"code\": \"GENDER\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 4,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Sex assigned at birth\",\r\n"
				+ "        \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"GENDER\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203407,\r\n"
				+ "      \"entity\": \"Email Address\",\r\n"
				+ "      \"code\": \"EMAIL\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 5,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Email Address\",\r\n"
				+ "        \"ES\": \"Dirección de email\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"EMAIL\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203405,\r\n"
				+ "      \"entity\": \"Phone no\",\r\n"
				+ "      \"code\": \"PHONE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 6,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": true,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Phone no\",\r\n"
				+ "        \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"PHONE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203403,\r\n"
				+ "      \"entity\": \"Insurance ID\",\r\n"
				+ "      \"code\": \"INSID\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 7,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Insurance ID\",\r\n"
				+ "        \"ES\": \"id seguro\\\"\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"INSID\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203408,\r\n"
				+ "      \"entity\": \"Address1\",\r\n"
				+ "      \"code\": \"ADDR1\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 9,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Address1\",\r\n"
				+ "        \"ES\": \"Dirección 1\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR1\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203409,\r\n"
				+ "      \"entity\": \"tttttt\",\r\n"
				+ "      \"code\": \"ADDR2\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 10,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"tttttt\",\r\n"
				+ "        \"ES\": \"Dirección 2\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR2\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203410,\r\n"
				+ "      \"entity\": \"City\",\r\n"
				+ "      \"code\": \"CITY\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 11,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"City\",\r\n"
				+ "        \"ES\": \"Ciudad\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"CITY\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203406,\r\n"
				+ "      \"entity\": \"State\",\r\n"
				+ "      \"code\": \"STATE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 12,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"State\",\r\n"
				+ "        \"ES\": \"Estado\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"STATE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203404,\r\n"
				+ "      \"entity\": \"Zip Code\",\r\n"
				+ "      \"code\": \"ZIP\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 13,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Zip Code\",\r\n"
				+ "        \"ES\": \"Código posta\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ZIP\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return payload;
	}
	
	public String patientMatchAt() {
		String saveAnn = "{\r\n"
				+ "  \"flowType\": \"LG_LOG\",\r\n"
				+ "  \"patientMatches\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4162,\r\n"
				+ "      \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "      \"code\": \"FN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 1,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "        \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"FN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4158,\r\n"
				+ "      \"entity\": \"Patient Last Name\",\r\n"
				+ "      \"code\": \"LN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 2,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient Last Name\",\r\n"
				+ "        \"ES\": \"Apellido del paciente\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"LN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4168,\r\n"
				+ "      \"entity\": \"Date Of Birth\",\r\n"
				+ "      \"code\": \"DOB\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 3,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Date Of Birth\",\r\n"
				+ "        \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"DOB\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4169,\r\n"
				+ "      \"entity\": \"Sex assigned at birth\",\r\n"
				+ "      \"code\": \"GENDER\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 4,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Sex assigned at birth\",\r\n"
				+ "        \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"GENDER\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4164,\r\n"
				+ "      \"entity\": \"Email Address\",\r\n"
				+ "      \"code\": \"EMAIL\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 5,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Email Address\",\r\n"
				+ "        \"ES\": \"Dirección de email\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"EMAIL\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4161,\r\n"
				+ "      \"entity\": \"Preferred Phone Number\",\r\n"
				+ "      \"code\": \"PHONE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 6,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Preferred Phone Number\",\r\n"
				+ "        \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"PHONE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4159,\r\n"
				+ "      \"entity\": \"Insurance ID\",\r\n"
				+ "      \"code\": \"INSID\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 7,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Insurance ID\",\r\n"
				+ "        \"ES\": \"id seguro\\\"\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"INSID\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4165,\r\n"
				+ "      \"entity\": \"Address1\",\r\n"
				+ "      \"code\": \"ADDR1\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 9,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Address1\",\r\n"
				+ "        \"ES\": \"Dirección 1\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR1\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4166,\r\n"
				+ "      \"entity\": \"Address2\",\r\n"
				+ "      \"code\": \"ADDR2\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 10,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Address2\",\r\n"
				+ "        \"ES\": \"Dirección 2\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR2\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4167,\r\n"
				+ "      \"entity\": \"City\",\r\n"
				+ "      \"code\": \"CITY\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 11,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"City\",\r\n"
				+ "        \"ES\": \"Ciudad\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"CITY\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4163,\r\n"
				+ "      \"entity\": \"State\",\r\n"
				+ "      \"code\": \"STATE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 12,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"State\",\r\n"
				+ "        \"ES\": \"Estado\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"STATE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4160,\r\n"
				+ "      \"entity\": \"Zip Code\",\r\n"
				+ "      \"code\": \"ZIP\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 13,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Zip Code\",\r\n"
				+ "        \"ES\": \"Código posta\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ZIP\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return saveAnn;
	}	

	public String patientInfoWithOptionalLLNG() {

		String patientInfoWithOptionalLL = "{\r\n"
				+ "    \"flowType\": \"LG_LOG\",\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4035,\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 1,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "                \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"FN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4041,\r\n"
				+ "            \"entity\": \"Patient Last Name\",\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 2,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient Last Name\",\r\n"
				+ "                \"ES\": \"Apellido del paciente\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"LN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4047,\r\n"
				+ "            \"entity\": \"Date Of Birth\",\r\n"
				+ "            \"code\": \"DOB\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 3,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Date Of Birth\",\r\n"
				+ "                \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"DOB\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 4053,\r\n"
				+ "            \"entity\": \"Sex assigned at birth\",\r\n"
				+ "            \"code\": \"GENDER\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 4,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Sex assigned at birth\",\r\n"
				+ "                \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"GENDER\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200711,\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 5,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Email Address\",\r\n"
				+ "                \"ES\": \"Dirección de email\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"EMAIL\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200703,\r\n"
				+ "            \"entity\": \"Phone no\",\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 6,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": true,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Phone no\",\r\n"
				+ "                \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"PHONE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200708,\r\n"
				+ "            \"entity\": \"Insurance ID\",\r\n"
				+ "            \"code\": \"INSID\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 7,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Insurance ID\",\r\n"
				+ "                \"ES\": \"id seguro\\\"\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"INSID\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200712,\r\n"
				+ "            \"entity\": \"Address1\",\r\n"
				+ "            \"code\": \"ADDR1\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 9,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Address1\",\r\n"
				+ "                \"ES\": \"Dirección 1\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR1\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200713,\r\n"
				+ "            \"entity\": \"tttttt\",\r\n"
				+ "            \"code\": \"ADDR2\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 10,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"tttttt\",\r\n"
				+ "                \"ES\": \"Dirección 2\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ADDR2\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200714,\r\n"
				+ "            \"entity\": \"City\",\r\n"
				+ "            \"code\": \"CITY\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 11,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"City\",\r\n"
				+ "                \"ES\": \"Ciudad\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"CITY\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200710,\r\n"
				+ "            \"entity\": \"State\",\r\n"
				+ "            \"code\": \"STATE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 12,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": true,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"State\",\r\n"
				+ "                \"ES\": \"Estado\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"STATE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 200709,\r\n"
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 13,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Zip Code\",\r\n"
				+ "                \"ES\": \"Código posta\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ZIP\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return patientInfoWithOptionalLL;
	}
	
	public String patientInfoWithOptionalGE() {
		String patientInfoWithOptionalGE = "{\r\n"
				+ "  \"flowType\": \"LG_LOG\",\r\n"
				+ "  \"patientMatches\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4397,\r\n"
				+ "      \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "      \"code\": \"FN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 1,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "        \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"FN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4401,\r\n"
				+ "      \"entity\": \"Patient Last Name\",\r\n"
				+ "      \"code\": \"LN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 2,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient Last Name\",\r\n"
				+ "        \"ES\": \"Apellido del paciente\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"LN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4405,\r\n"
				+ "      \"entity\": \"Date Of Birth\",\r\n"
				+ "      \"code\": \"DOB\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 3,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Date Of Birth\",\r\n"
				+ "        \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"DOB\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203411,\r\n"
				+ "      \"entity\": \"Sex assigned at birth\",\r\n"
				+ "      \"code\": \"GENDER\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 4,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Sex assigned at birth\",\r\n"
				+ "        \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"GENDER\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203407,\r\n"
				+ "      \"entity\": \"Email Address\",\r\n"
				+ "      \"code\": \"EMAIL\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 5,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Email Address\",\r\n"
				+ "        \"ES\": \"Dirección de email\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"EMAIL\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203405,\r\n"
				+ "      \"entity\": \"Phone no\",\r\n"
				+ "      \"code\": \"PHONE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 6,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": true,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Phone no\",\r\n"
				+ "        \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"PHONE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203403,\r\n"
				+ "      \"entity\": \"Insurance ID\",\r\n"
				+ "      \"code\": \"INSID\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 7,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Insurance ID\",\r\n"
				+ "        \"ES\": \"id seguro\\\"\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"INSID\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203408,\r\n"
				+ "      \"entity\": \"Address1\",\r\n"
				+ "      \"code\": \"ADDR1\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 9,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Address1\",\r\n"
				+ "        \"ES\": \"Dirección 1\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR1\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203409,\r\n"
				+ "      \"entity\": \"tttttt\",\r\n"
				+ "      \"code\": \"ADDR2\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 10,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"tttttt\",\r\n"
				+ "        \"ES\": \"Dirección 2\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR2\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203410,\r\n"
				+ "      \"entity\": \"City\",\r\n"
				+ "      \"code\": \"CITY\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 11,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"City\",\r\n"
				+ "        \"ES\": \"Ciudad\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"CITY\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203406,\r\n"
				+ "      \"entity\": \"State\",\r\n"
				+ "      \"code\": \"STATE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 12,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"State\",\r\n"
				+ "        \"ES\": \"Estado\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"STATE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 203404,\r\n"
				+ "      \"entity\": \"Zip Code\",\r\n"
				+ "      \"code\": \"ZIP\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 13,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Zip Code\",\r\n"
				+ "        \"ES\": \"Código posta\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ZIP\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return patientInfoWithOptionalGE;
	}

	public String patientInfoWithOptionalGW() {
		String patientInfoWithOptionalGW = "{\r\n"
				+ "  \"flowType\": \"LG_LOG\",\r\n"
				+ "  \"patientMatches\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4360,\r\n"
				+ "      \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "      \"code\": \"FN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 1,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "        \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"FN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4364,\r\n"
				+ "      \"entity\": \"Patient Last Name\",\r\n"
				+ "      \"code\": \"LN\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 2,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": true,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Patient Last Name\",\r\n"
				+ "        \"ES\": \"Apellido del paciente\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"LN\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4368,\r\n"
				+ "      \"entity\": \"Date Of Birth\",\r\n"
				+ "      \"code\": \"DOB\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 3,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Date Of Birth\",\r\n"
				+ "        \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"DOB\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 202754,\r\n"
				+ "      \"entity\": \"Sex assigned at birth\",\r\n"
				+ "      \"code\": \"GENDER\",\r\n"
				+ "      \"searchRequired\": true,\r\n"
				+ "      \"createRequired\": true,\r\n"
				+ "      \"field\": \"MANDATORY\",\r\n"
				+ "      \"seq\": 4,\r\n"
				+ "      \"systemMandatory\": true,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Sex assigned at birth\",\r\n"
				+ "        \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"GENDER\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200966,\r\n"
				+ "      \"entity\": \"Email Address\",\r\n"
				+ "      \"code\": \"EMAIL\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 5,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Email Address\",\r\n"
				+ "        \"ES\": \"Dirección de email\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"EMAIL\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200972,\r\n"
				+ "      \"entity\": \"Phone no\",\r\n"
				+ "      \"code\": \"PHONE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 6,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": true,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Phone no\",\r\n"
				+ "        \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"PHONE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200967,\r\n"
				+ "      \"entity\": \"Insurance ID\",\r\n"
				+ "      \"code\": \"INSID\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 7,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Insurance ID\",\r\n"
				+ "        \"ES\": \"id seguro\\\"\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"INSID\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200968,\r\n"
				+ "      \"entity\": \"Address1\",\r\n"
				+ "      \"code\": \"ADDR1\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 9,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Address1\",\r\n"
				+ "        \"ES\": \"Dirección 1\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR1\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200970,\r\n"
				+ "      \"entity\": \"tttttt\",\r\n"
				+ "      \"code\": \"ADDR2\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 10,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"tttttt\",\r\n"
				+ "        \"ES\": \"Dirección 2\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ADDR2\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200969,\r\n"
				+ "      \"entity\": \"City\",\r\n"
				+ "      \"code\": \"CITY\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 11,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"City\",\r\n"
				+ "        \"ES\": \"Ciudad\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"CITY\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 200971,\r\n"
				+ "      \"entity\": \"State\",\r\n"
				+ "      \"code\": \"STATE\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"HIDE\",\r\n"
				+ "      \"seq\": 12,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": true,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"State\",\r\n"
				+ "        \"ES\": \"Estado\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"STATE\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"id\": 4022,\r\n"
				+ "      \"entity\": \"Zip Code\",\r\n"
				+ "      \"code\": \"ZIP\",\r\n"
				+ "      \"searchRequired\": false,\r\n"
				+ "      \"createRequired\": false,\r\n"
				+ "      \"field\": \"OPTIONAL\",\r\n"
				+ "      \"seq\": 13,\r\n"
				+ "      \"systemMandatory\": false,\r\n"
				+ "      \"searchDisabled\": false,\r\n"
				+ "      \"allowCustom\": false,\r\n"
				+ "      \"patientIdentity\": false,\r\n"
				+ "      \"entityMap\": {\r\n"
				+ "        \"EN\": \"Zip Code\",\r\n"
				+ "        \"ES\": \"Código posta\"\r\n"
				+ "      },\r\n"
				+ "      \"entityEdit\": {},\r\n"
				+ "      \"key\": \"ZIP\"\r\n"
				+ "    }\r\n"
				+ "  ]\r\n"
				+ "}";
		return patientInfoWithOptionalGW;
	}


	public String patientInfoWithOptionalAnoNG() {
		String patientInfoWithOptionalAno = "{\r\n"
				+ "    \"flowType\": \"LG_ANO\",\r\n"
				+ "    \"patientMatches\": [\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222572,\r\n"
				+ "            \"entity\": \"Patient First Name (Legal Name)\",\r\n"
				+ "            \"code\": \"FN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 1,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient First Name (Legal Name)\",\r\n"
				+ "                \"ES\": \"Nombre del paciente (nombre legal)\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"FN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222568,\r\n"
				+ "            \"entity\": \"Patient Last Name\",\r\n"
				+ "            \"code\": \"LN\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 2,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": true,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Patient Last Name\",\r\n"
				+ "                \"ES\": \"Apellido del paciente\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"LN\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222573,\r\n"
				+ "            \"entity\": \"Date Of Birth\",\r\n"
				+ "            \"code\": \"DOB\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 3,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Date Of Birth\",\r\n"
				+ "                \"ES\": \"Fecha de cumpleaños\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"DOB\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222574,\r\n"
				+ "            \"entity\": \"Sex assigned at birth\",\r\n"
				+ "            \"code\": \"GENDER\",\r\n"
				+ "            \"searchRequired\": true,\r\n"
				+ "            \"createRequired\": true,\r\n"
				+ "            \"field\": \"MANDATORY\",\r\n"
				+ "            \"seq\": 4,\r\n"
				+ "            \"systemMandatory\": true,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Sex assigned at birth\",\r\n"
				+ "                \"ES\": \"Sexo asignado al nacer\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"GENDER\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222575,\r\n"
				+ "            \"entity\": \"Email Address\",\r\n"
				+ "            \"code\": \"EMAIL\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 5,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Email Address\",\r\n"
				+ "                \"ES\": \"Dirección de email\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"EMAIL\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222571,\r\n"
				+ "            \"entity\": \"Preferred Phone Number\",\r\n"
				+ "            \"code\": \"PHONE\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 6,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": true,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Preferred Phone Number\",\r\n"
				+ "                \"ES\": \"Número de teléfono preferido\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"PHONE\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222569,\r\n"
				+ "            \"entity\": \"Insurance ID\",\r\n"
				+ "            \"code\": \"INSID\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"HIDE\",\r\n"
				+ "            \"seq\": 7,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Insurance ID\",\r\n"
				+ "                \"ES\": \"id seguro\\\"\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"INSID\"\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"id\": 222570,\r\n"
				+ "            \"entity\": \"Zip Code\",\r\n"
				+ "            \"code\": \"ZIP\",\r\n"
				+ "            \"searchRequired\": false,\r\n"
				+ "            \"createRequired\": false,\r\n"
				+ "            \"field\": \"OPTIONAL\",\r\n"
				+ "            \"seq\": 8,\r\n"
				+ "            \"systemMandatory\": false,\r\n"
				+ "            \"searchDisabled\": false,\r\n"
				+ "            \"allowCustom\": false,\r\n"
				+ "            \"patientIdentity\": false,\r\n"
				+ "            \"entityMap\": {\r\n"
				+ "                \"EN\": \"Zip Code\",\r\n"
				+ "                \"ES\": \"Código posta\"\r\n"
				+ "            },\r\n"
				+ "            \"entityEdit\": {},\r\n"
				+ "            \"key\": \"ZIP\"\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
		return patientInfoWithOptionalAno;
	}
	
	public String activeLockoutAT(String groupName) {
		String payload = "{\r\n"
				+ "  \"key\": \"active\",\r\n"
				+ "  \"value\": \"active\",\r\n"
				+ "  \"type\": \"PATIENT_STATUS\",\r\n"
				+ "  \"selected\": true,\r\n"
				+ "  \"group\": \""+groupName+"\",\r\n"
				+ "  \"id\": null\r\n"
				+ "}";
		return payload;
	}
	
	

	public String preRequisiteAppointmentTypesDefualtNG(String name, String extAppID, String catId, String catName) {
		String preRequisiteAppointmentTypesDefualtNG = "[\r\n"
				+ "    {\r\n"
				+ "        \"name\": \"" + name + "\",\r\n"
				+ "        \"extPreAppTypeId\": \"" + extAppID + "\",\r\n"
				+ "        \"numOfDays\": -1,\r\n"
				+ "        \"categoryId\": \"" + catId + "\",\r\n"
				+ "        \"categoryName\": \"" + catName + "\",\r\n"
				+ "        \"selected\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesDefualtNG;
	}
	
	public String preRequisiteAppointmentTypesDefualtNG1(String name, String extAppID, String catId, String catName) {
		String preRequisiteAppointmentTypesDefualtNG = "[\r\n"
				+ "    {\r\n"
				+ "        \"name\": \"" + name + "\",\r\n"
				+ "        \"extPreAppTypeId\": \"" + extAppID + "\",\r\n"
				+ "        \"numOfDays\": -1,\r\n"
				+ "        \"categoryId\": \"" + catId + "\",\r\n"
				+ "        \"categoryName\": \"" + catName + "\",\r\n"
				+ "        \"selected\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesDefualtNG;
	}
	

	public String preRequisiteAppointmentTypesNoOfDaysNG(String name, String extAppID, String catId, String catName, String numberOfDay, int prereqId) {
		String preRequisiteAppointmentTypesNoOfDaysNG = "\r\n"
				+ "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": "+prereqId+",\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"extPreAppTypeId\": \""+extAppID+"\",\r\n"
				+ "        \"numOfDays\": \"" + numberOfDay + "\",\r\n"
				+ "        \"categoryId\": \""+catId+"\",\r\n"
				+ "        \"categoryName\": \""+catName+"\",\r\n"
				+ "        \"selected\": false\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesNoOfDaysNG;
	}

	public String preRequisiteAppointmentTypesDefualtGE(String name, String extAppID) {
		String preRequisiteAppointmentTypesDefualtGE = "[\r\n"
				+ "    {\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"extPreAppTypeId\": \"" + extAppID + "\",\r\n"
				+ "        \"selected\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesDefualtGE;
	}
	
	public String preRequisiteAppointmentTypesDefualtGE1(String name, String extAppID) {
		String preRequisiteAppointmentTypesDefualtGE = "[\r\n"
				+ "    {\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"extPreAppTypeId\": \"" + extAppID + "\",\r\n"
				+ "        \"selected\": true\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesDefualtGE;
	}
	
	public String preRequisiteAppointmentTypesWithNoOfDaysGE(String name, String extAppID,String numberOfDays) {
		String preRequisiteAppointmentTypesDefualtGE = "[\r\n"
				+ "    {\r\n"
				+ "        \"id\": 204792,\r\n"
				+ "        \"name\": \""+name+"\",\r\n"
				+ "        \"extPreAppTypeId\": \""+extAppID+"\",\r\n"
				+ "        \"numOfDays\": \""+numberOfDays+"\",\r\n"
				+ "        \"selected\": false\r\n"
				+ "    }\r\n"
				+ "]";
		return preRequisiteAppointmentTypesDefualtGE;
	}
	
	public String updateBusinessHoursNG(String startTime,String endTime)
	{
		String updateBusinessHours="{\r\n"
				+ "  \"id\": 24702,\r\n"
				+ "  \"name\": \"PSS-NG-PG16CN\",\r\n"
				+ "  \"practiceId\": \"24702\",\r\n"
				+ "  \"themeColor\": \"#235ba8\",\r\n"
				+ "  \"extPracticeId\": \"24702\",\r\n"
				+ "  \"partner\": \"NG\",\r\n"
				+ "  \"timezone\": \"America/Chicago\",\r\n"
				+ "  \"active\": true,\r\n"
				+ "  \"languages\": [\r\n"
				+ "    {\r\n"
				+ "      \"code\": \"EN\",\r\n"
				+ "      \"flag\": \"us\",\r\n"
				+ "      \"name\": \"English\"\r\n"
				+ "    },\r\n"
				+ "    {\r\n"
				+ "      \"code\": \"ES\",\r\n"
				+ "      \"flag\": \"mx\",\r\n"
				+ "      \"name\": \"Español\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"starttime\": \""+startTime+"\",\r\n"
				+ "  \"endtime\": \""+endTime+"\",\r\n"
				+ "  \"logo\": null,\r\n"
				+ "  \"type\": null\r\n"
				+ "}";
		return updateBusinessHours;
	}
	public String setAgeRuleOnInApptType() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": true,\r\n" + 
				"        \"id\": 203701,\r\n" + 
				"        \"name\": \"Joke 10\",\r\n" + 
				"        \"displayName\": \"Joke 10\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Joke 10\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": -1,\r\n" + 
				"        \"categoryId\": \"65E6117E-F8F3-4619-91BB-D39E2D600EB8\",\r\n" + 
				"        \"categoryName\": \"Joke 10 Category\",\r\n" + 
				"        \"extAppointmentTypeId\": \"AB64AC0B-F04A-4B98-B9A1-FF573A54C7BD\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 1,\r\n" + 
				"        \"locations\": [\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"201651\",\r\n" + 
				"                \"name\": \"ABC Optical\",\r\n" + 
				"                \"displayName\": \"ABC Optical span\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 201601,\r\n" + 
				"                    \"address1\": \"4421 Lake Boone Trail W\",\r\n" + 
				"                    \"address2\": \"Address line 2, Updated #3\",\r\n" + 
				"                    \"city\": \"Raleigh\",\r\n" + 
				"                    \"state\": \"NC\",\r\n" + 
				"                    \"zipCode\": \"27607\",\r\n" + 
				"                    \"latitude\": 38.6624944,\r\n" + 
				"                    \"longitude\": -90.47854949999999\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"80DE19AE-6BBF-477E-BC8B-371AEF0773DA\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": true,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"200350\",\r\n" + 
				"                \"name\": \"Main Office\",\r\n" + 
				"                \"displayName\": \"Main Office\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 200350,\r\n" + 
				"                    \"address1\": \"2011 Falls Valley Dr\",\r\n" + 
				"                    \"address2\": \"#106\",\r\n" + 
				"                    \"city\": \"Raleigh\",\r\n" + 
				"                    \"state\": \"North Carolina\",\r\n" + 
				"                    \"zipCode\": \"27615\",\r\n" + 
				"                    \"latitude\": 35.8973143,\r\n" + 
				"                    \"longitude\": -78.60541409999999\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"9d971e61-2b5a-4504-9016-7fd863790ee2\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"200654\",\r\n" + 
				"                \"name\": \"NextGen Optical\",\r\n" + 
				"                \"displayName\": \"Triangle Health Cary-spanish\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 200604,\r\n" + 
				"                    \"address1\": \"339 Collier Rd\",\r\n" + 
				"                    \"address2\": \"Atlanta, GA 30324\",\r\n" + 
				"                    \"city\": \"New york\",\r\n" + 
				"                    \"state\": \"New york\",\r\n" + 
				"                    \"zipCode\": \"96985\",\r\n" + 
				"                    \"latitude\": 35.7665681,\r\n" + 
				"                    \"longitude\": -78.75688049999997\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"54a194e5-19c2-43d0-b7a9-a34d31994ee3\",\r\n" + 
				"                \"directionUrl\": \"https://www.google.com/intl/en-GB/gmail/about/#\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"204405\",\r\n" + 
				"                \"name\": \"Richards Specialty Clinic\",\r\n" + 
				"                \"displayName\": \"Richards Specialty Clinic\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 204305,\r\n" + 
				"                    \"address1\": \"120 Dekalb Ave\",\r\n" + 
				"                    \"city\": \"Decatur\",\r\n" + 
				"                    \"state\": \"GA\",\r\n" + 
				"                    \"zipCode\": \"30033\",\r\n" + 
				"                    \"latitude\": 0,\r\n" + 
				"                    \"longitude\": 0\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"1081EC23-F241-485D-8874-C3A81A242429\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"205605\",\r\n" + 
				"                \"name\": \"PSS WLA\",\r\n" + 
				"                \"displayName\": \"PSS WLA -English\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 205505,\r\n" + 
				"                    \"address1\": \"New York\",\r\n" + 
				"                    \"address2\": \"Dillard Dr\",\r\n" + 
				"                    \"city\": \"Cary\",\r\n" + 
				"                    \"state\": \"North Carolina\",\r\n" + 
				"                    \"latitude\": 40.7127753,\r\n" + 
				"                    \"longitude\": -74.0059728\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n" + 
				"                \"directionUrl\": \"www.google.com123\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": 201200,\r\n" + 
				"            \"appointmentStacking\": true,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": \"0\",\r\n" + 
				"                \"hours\": \"0\",\r\n" + 
				"                \"mins\": \"0\"\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"> 1 And < 60\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	
	public String setAgeRuleOffInApptType() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": false,\r\n" + 
				"        \"id\": 203701,\r\n" + 
				"        \"name\": \"Joke 10\",\r\n" + 
				"        \"displayName\": \"Joke 10\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Joke 10\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": -1,\r\n" + 
				"        \"categoryId\": \"65E6117E-F8F3-4619-91BB-D39E2D600EB8\",\r\n" + 
				"        \"categoryName\": \"Joke 10 Category\",\r\n" + 
				"        \"extAppointmentTypeId\": \"AB64AC0B-F04A-4B98-B9A1-FF573A54C7BD\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 1,\r\n" + 
				"        \"locations\": [\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"201651\",\r\n" + 
				"                \"name\": \"ABC Optical\",\r\n" + 
				"                \"displayName\": \"ABC Optical span\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 201601,\r\n" + 
				"                    \"address1\": \"4421 Lake Boone Trail W\",\r\n" + 
				"                    \"address2\": \"Address line 2, Updated #3\",\r\n" + 
				"                    \"city\": \"Raleigh\",\r\n" + 
				"                    \"state\": \"NC\",\r\n" + 
				"                    \"zipCode\": \"27607\",\r\n" + 
				"                    \"latitude\": 38.6624944,\r\n" + 
				"                    \"longitude\": -90.47854949999999\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"80DE19AE-6BBF-477E-BC8B-371AEF0773DA\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": true,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"200350\",\r\n" + 
				"                \"name\": \"Main Office\",\r\n" + 
				"                \"displayName\": \"Main Office\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 200350,\r\n" + 
				"                    \"address1\": \"2011 Falls Valley Dr\",\r\n" + 
				"                    \"address2\": \"#106\",\r\n" + 
				"                    \"city\": \"Raleigh\",\r\n" + 
				"                    \"state\": \"North Carolina\",\r\n" + 
				"                    \"zipCode\": \"27615\",\r\n" + 
				"                    \"latitude\": 35.8973143,\r\n" + 
				"                    \"longitude\": -78.60541409999999\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"9d971e61-2b5a-4504-9016-7fd863790ee2\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"200654\",\r\n" + 
				"                \"name\": \"NextGen Optical\",\r\n" + 
				"                \"displayName\": \"Triangle Health Cary-spanish\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 200604,\r\n" + 
				"                    \"address1\": \"339 Collier Rd\",\r\n" + 
				"                    \"address2\": \"Atlanta, GA 30324\",\r\n" + 
				"                    \"city\": \"New york\",\r\n" + 
				"                    \"state\": \"New york\",\r\n" + 
				"                    \"zipCode\": \"96985\",\r\n" + 
				"                    \"latitude\": 35.7665681,\r\n" + 
				"                    \"longitude\": -78.75688049999997\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"54a194e5-19c2-43d0-b7a9-a34d31994ee3\",\r\n" + 
				"                \"directionUrl\": \"https://www.google.com/intl/en-GB/gmail/about/#\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"204405\",\r\n" + 
				"                \"name\": \"Richards Specialty Clinic\",\r\n" + 
				"                \"displayName\": \"Richards Specialty Clinic\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 204305,\r\n" + 
				"                    \"address1\": \"120 Dekalb Ave\",\r\n" + 
				"                    \"city\": \"Decatur\",\r\n" + 
				"                    \"state\": \"GA\",\r\n" + 
				"                    \"zipCode\": \"30033\",\r\n" + 
				"                    \"latitude\": 0,\r\n" + 
				"                    \"longitude\": 0\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"1081EC23-F241-485D-8874-C3A81A242429\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"id\": \"205605\",\r\n" + 
				"                \"name\": \"PSS WLA\",\r\n" + 
				"                \"displayName\": \"PSS WLA -English\",\r\n" + 
				"                \"address\": {\r\n" + 
				"                    \"id\": 205505,\r\n" + 
				"                    \"address1\": \"New York\",\r\n" + 
				"                    \"address2\": \"Dillard Dr\",\r\n" + 
				"                    \"city\": \"Cary\",\r\n" + 
				"                    \"state\": \"North Carolina\",\r\n" + 
				"                    \"latitude\": 40.7127753,\r\n" + 
				"                    \"longitude\": -74.0059728\r\n" + 
				"                },\r\n" + 
				"                \"timezone\": \"\",\r\n" + 
				"                \"extLocationId\": \"283BB437-B0C6-4626-A7CC-57FD0D1D6574\",\r\n" + 
				"                \"directionUrl\": \"www.google.com123\",\r\n" + 
				"                \"selected\": false,\r\n" + 
				"                \"phoneNumber\": \"\",\r\n" + 
				"                \"restrictToCareteam\": false,\r\n" + 
				"                \"locationLinks\": {}\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": 201200,\r\n" + 
				"            \"appointmentStacking\": true,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": \"0\",\r\n" + 
				"                \"hours\": \"0\",\r\n" + 
				"                \"mins\": \"0\"\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
		}
	
	public String setAgeRuleOnInApptTypeGW() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": true,\r\n" + 
				"        \"id\": 205751,\r\n" + 
				"        \"name\": \"Injection 15\",\r\n" + 
				"        \"displayName\": \"Injection 15\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Injection 15\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"sdds\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": \"abc\"\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 6,\r\n" + 
				"        \"duration\": \"15\",\r\n" + 
				"        \"categoryId\": \"1381\",\r\n" + 
				"        \"categoryName\": \"Polio\",\r\n" + 
				"        \"extAppointmentTypeId\": \"1374\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"> 1 And < 60\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	
	public String setAgeRuleOffInApptTypeGW() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": false,\r\n" + 
				"        \"id\": 205751,\r\n" + 
				"        \"name\": \"Injection 15\",\r\n" + 
				"        \"displayName\": \"Injection 15\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Injection 15\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"sdds\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": \"abc\"\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 6,\r\n" + 
				"        \"duration\": \"15\",\r\n" + 
				"        \"categoryId\": \"1381\",\r\n" + 
				"        \"categoryName\": \"Polio\",\r\n" + 
				"        \"extAppointmentTypeId\": \"1374\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	
	public String setAgeRuleOnInApptTypeAT() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": true,\r\n" + 
				"        \"id\": 201953,\r\n" + 
				"        \"name\": \"Cardiology\",\r\n" + 
				"        \"displayName\": \"Cardiology\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Cardiology\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 12,\r\n" + 
				"        \"duration\": \"45\",\r\n" + 
				"        \"extAppointmentTypeId\": \"182\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"> 1 And < 60\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	public String setAgeRuleOffInApptTypeAT() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": false,\r\n" + 
				"        \"id\": 201953,\r\n" + 
				"        \"name\": \"Cardiology\",\r\n" + 
				"        \"displayName\": \"Cardiology\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Cardiology\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 12,\r\n" + 
				"        \"duration\": \"45\",\r\n" + 
				"        \"extAppointmentTypeId\": \"182\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	
	public String setAgeRuleOnInApptTypeGE() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": true,\r\n" + 
				"        \"id\": 204201,\r\n" + 
				"        \"name\": \"Fever n Cold\",\r\n" + 
				"        \"displayName\": \"Fever n Cold\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Fever n Cold\",\r\n" + 
				"            \"ES\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"Next Step\",\r\n" + 
				"            \"ES\": \"Next Step\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null,\r\n" + 
				"            \"ES\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null,\r\n" + 
				"            \"ES\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 1,\r\n" + 
				"        \"extAppointmentTypeId\": \"158\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"> 1 And < 60\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	
	public String setAgeRuleOffInApptTypeGE() {
		String payload = "[\r\n" + 
				"    {\r\n" + 
				"        \"isageRule\": false,\r\n" + 
				"        \"id\": 204201,\r\n" + 
				"        \"name\": \"Fever n Cold\",\r\n" + 
				"        \"displayName\": \"Fever n Cold\",\r\n" + 
				"        \"displayNames\": {\r\n" + 
				"            \"EN\": \"Fever n Cold\",\r\n" + 
				"            \"ES\": \"\"\r\n" + 
				"        },\r\n" + 
				"        \"message\": {\r\n" + 
				"            \"EN\": \"Next Step\",\r\n" + 
				"            \"ES\": \"Next Step\"\r\n" + 
				"        },\r\n" + 
				"        \"question\": {\r\n" + 
				"            \"EN\": null,\r\n" + 
				"            \"ES\": null\r\n" + 
				"        },\r\n" + 
				"        \"customMessages\": {\r\n" + 
				"            \"EN\": null,\r\n" + 
				"            \"ES\": null\r\n" + 
				"        },\r\n" + 
				"        \"sortOrder\": 1,\r\n" + 
				"        \"extAppointmentTypeId\": \"158\",\r\n" + 
				"        \"preventRescheduleOnCancel\": 0,\r\n" + 
				"        \"preventScheduling\": 0,\r\n" + 
				"        \"param\": {\r\n" + 
				"            \"id\": null,\r\n" + 
				"            \"appointmentStacking\": false,\r\n" + 
				"            \"slotCount\": 1,\r\n" + 
				"            \"allowSameDayAppts\": true,\r\n" + 
				"            \"apptTimeMark\": 0,\r\n" + 
				"            \"apptTypeAllocated\": true,\r\n" + 
				"            \"isContiguous\": false,\r\n" + 
				"            \"leadTime\": {\r\n" + 
				"                \"days\": 0,\r\n" + 
				"                \"hours\": 0,\r\n" + 
				"                \"mins\": 0\r\n" + 
				"            },\r\n" + 
				"            \"excludeSlots\": [],\r\n" + 
				"            \"apptTypeReservedReason\": \"n\",\r\n" + 
				"            \"acceptComment\": false,\r\n" + 
				"            \"allowOnlineCancellation\": true,\r\n" + 
				"            \"slotSize\": 5,\r\n" + 
				"            \"schedulingDuration\": 0,\r\n" + 
				"            \"pttype\": \"PT_ALL\",\r\n" + 
				"            \"lastQuestRequired\": false\r\n" + 
				"        },\r\n" + 
				"        \"ageRule\": \"\"\r\n" + 
				"    }\r\n" + 
				"]";
		return payload;
	}
	public String setDecisionTree(boolean enableDecisionTree) {
		String payload = "[{\"group\":\"RULEENGINE\",\"key\":\"showCategory\",\"value\":"+enableDecisionTree+"}]";
		return payload;
	}
}

