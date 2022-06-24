// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.appt.precheck.payload;

public class IMHProxyServicePayload {

	private static IMHProxyServicePayload payload = new IMHProxyServicePayload();

	private IMHProxyServicePayload() {

	}

	public static IMHProxyServicePayload getIMHProxyServicePayload() {
		return payload;
	}

	public String getSaveImhFormPayload(String conceptId, String conceptName, String formId, String practiceId) {
		String saveImhForm = "{\r\n" + "  "
				+ "\"conceptId\": \"" + conceptId + "\",\r\n" + "  "
				+ "\"conceptName\": \""+ conceptName + "\",\r\n" + "  "
				+ "\"formId\": \"" + formId + "\",\r\n" + "  "
				+ "\"formSource\": \"IMH\",\r\n"
				+ "  \"id\": null,\r\n" + "  "
				+ "\"practiceId\": \"" + practiceId + "\"\r\n" + "}";
		return saveImhForm;
	}

	public String saveImhFormPayload( String conceptName, String practiceId) {
		String saveImhForm = "{\r\n"
				+ "  \"conceptId\": null,\r\n"
				+ "  \"conceptName\": \""+conceptName+"\",\r\n"
				+ "  \"formId\": null,\r\n"
				+ "  \"formSource\": null,\r\n"
				+ "  \"id\": null,\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return saveImhForm;
		}

	public String createEncounterPayload(String apptId, long apptTime, String conceptName, String env,String systemId, 
			int day,int month,int year,String fName, String gender, String lang,String lName,String practiceName,String source) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"appointmentDetails\": {\r\n"
				+ "    \"appointmentId\": \""+apptId+"\",\r\n"
				+ "    \"appointmentTime\": \""+apptTime+"\"\r\n"
				+ "  },\r\n"
				+ "  \"conceptName\": \""+conceptName+"\",\r\n"
				+ "  \"env\": \""+env+"\",\r\n"
				+ "  \"externalSystemId\": \""+systemId+"\",\r\n"
				+ "  \"patientDetails\": {\r\n"
				+ "    \"dobDay\": "+day+",\r\n"
				+ "    \"dobMonth\": "+month+",\r\n"
				+ "    \"dobYear\": "+year+",\r\n"
				+ "    \"firstName\": \""+fName+"\",\r\n"
				+ "    \"gender\": \""+gender+"\",\r\n"
				+ "    \"language\": \""+lang+"\",\r\n"
				+ "    \"lastName\": \""+lName+"\",\r\n"
				+ "    \"middleName\": null,\r\n"
				+ "    \"ssn\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"practiceName\": \"PSS - NG-DEV\",\r\n"
				+ "\"provider\":{\r\n"
				+ "\"providerId\":\"\",\r\n"
				+ "\"providerName\":\"\"\r\n"
				+ "},\r\n"
				+ "  \"source\": \"PRECHECK\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String answerPayload(boolean ansValue, boolean skipValue,String answer,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": "+ansValue+",\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": null,\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\":\"IMH-Answer-"+answer+"\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String moveNextOrPrevQuestionPayload(String directionOfQuestion) {
		String directionOfQuesPayload="{ \r\n"
				+ " \"interviewCursor\":{ \r\n"
				+ "     \"operation\":\""+directionOfQuestion+"\" \r\n"
				+ "   } \r\n"
				+ " }";
		return directionOfQuesPayload;
	}
	
	public String firstAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"\",\r\n"
				+ "      \"IMH-Variable-1\": \"\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String secondAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": false,\r\n"
				+ "      \"IMH-Variable-1\": true,\r\n"
				+ "      \"IMH-Variable-2\": false,\r\n"
				+ "      \"IMH-Variable-3\": false\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String thirdAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"IMH-Answer-1\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String forthAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"IMH-Answer-2\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String fifthAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": false,\r\n"
				+ "      \"IMH-Variable-1\": true,\r\n"
				+ "      \"IMH-Variable-2\": false,\r\n"
				+ "      \"IMH-Variable-3\": false,\r\n"
				+ "      \"IMH-Variable-4\": false,\r\n"
				+ "      \"IMH-Variable-5\": false,\r\n"
				+ "      \"IMH-Variable-6\": false,\r\n"
				+ "      \"IMH-Variable-7\": false,\r\n"
				+ "      \"IMH-Variable-8\": false,\r\n"
				+ "      \"IMH-Variable-9\": false,\r\n"
				+ "      \"IMH-Variable-10\": false,\r\n"
				+ "      \"IMH-Variable-11\": false,\r\n"
				+ "      \"IMH-Variable-12\": false,\r\n"
				+ "      \"IMH-Variable-13\": false,\r\n"
				+ "      \"IMH-Variable-14\": false,\r\n"
				+ "      \"IMH-Variable-15\": false,\r\n"
				+ "      \"null\": \"\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String sixthAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"Zantac\",\r\n"
				+ "      \"IMH-Variable-1\": \"20\",\r\n"
				+ "      \"IMH-Variable-2\": \"oral\",\r\n"
				+ "      \"IMH-Variable-3\": \"2x a day\",\r\n"
				+ "      \"IMH-Variable-4\": \"acid\",\r\n"
				+ "      \"IMH-Variable-5\": null,\r\n"
				+ "      \"IMH-Variable-6\": null,\r\n"
				+ "      \"IMH-Variable-7\": null,\r\n"
				+ "      \"IMH-Variable-8\": null,\r\n"
				+ "      \"IMH-Variable-9\": null,\r\n"
				+ "      \"IMH-Variable-10\": null,\r\n"
				+ "      \"IMH-Variable-11\": null,\r\n"
				+ "      \"IMH-Variable-12\": null,\r\n"
				+ "      \"IMH-Variable-13\": null,\r\n"
				+ "      \"IMH-Variable-14\": null,\r\n"
				+ "      \"IMH-Variable-15\": null,\r\n"
				+ "      \"IMH-Variable-16\": null,\r\n"
				+ "      \"IMH-Variable-17\": null,\r\n"
				+ "      \"IMH-Variable-18\": null,\r\n"
				+ "      \"IMH-Variable-19\": null,\r\n"
				+ "      \"IMH-Variable-20\": null,\r\n"
				+ "      \"IMH-Variable-21\": null,\r\n"
				+ "      \"IMH-Variable-22\": null,\r\n"
				+ "      \"IMH-Variable-23\": null,\r\n"
				+ "      \"IMH-Variable-24\": null,\r\n"
				+ "      \"IMH-Variable-25\": false\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String SeventhAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"09:00\",\r\n"
				+ "      \"IMH-Variable-1\": null,\r\n"
				+ "      \"IMH-Variable-2\": null,\r\n"
				+ "      \"IMH-Variable-3\": null,\r\n"
				+ "      \"IMH-Variable-4\": null,\r\n"
				+ "      \"IMH-Variable-5\": null\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String eighthAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"2022-05-06\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String ninthAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": \"suji@yopmail.com\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String multipleAnswerPayload(boolean skipValue,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": true,\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": \"\",\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\": true,\r\n"
				+ "      \"IMH-Variable-1\": true,\r\n"
				+ "      \"IMH-Variable-2\": true,\r\n"
				+ "      \"IMH-Variable-3\": true\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;
	}
	
	public String answerPayload(boolean ansValue, boolean skipValue,String firstAnswer,String secondAnswer,String patientAnswerUrl) {
		String patientEncounterPayload ="{\r\n"
				+ "  \"moveNext\": "+ansValue+",\r\n"
				+ "  \"patientAnswer\": {\r\n"
				+ "    \"answerId\": null,\r\n"
				+ "    \"skip\": "+skipValue+",\r\n"
				+ "    \"variables\": {\r\n"
				+ "      \"IMH-Variable-0\":\""+firstAnswer+"\",\r\n"
				+ "      \"IMH-Variable-1\":\""+secondAnswer+"\"\r\n"
				+ "    }\r\n"
				+ "  },\r\n"
				+ "  \"patientAnswerUrl\": \""+patientAnswerUrl+"\"\r\n"
				+ "}";
		return patientEncounterPayload;//\"\",\r\n"
	}
	
	public String uploadImhFormPayload(String conceptId, String conceptName, String formId, String formSrc,String id,String practiceId) {
		String saveImhForm = "{\r\n"
				+ "  \"conceptId\": \""+conceptId+"\",\r\n"
				+ "  \"conceptName\": \"Shoulder"+conceptName+"\",\r\n"
				+ "  \"formId\": \""+formId+"\",\r\n"
				+ "  \"formSource\": \""+formSrc+"\",\r\n"
				+ "  \"id\": \""+id+"\",\r\n"
				+ "  \"practiceId\": \""+practiceId+"\"\r\n"
				+ "}";
		return saveImhForm;
		}
	
	public String getSaveImhFormPayload(String conceptId,  String formId) {
		String saveImhForm = "{\r\n"
				+ "\"conceptId\": \"" + conceptId + "\",\r\n"
				+ "\"conceptName\": null,\r\n" 
				+ "\"formId\": \"" + formId + "\",\r\n" + "  "
				+ "\"formSource\": \"IMH\",\r\n"
				+ "  \"id\": null,\r\n" + "  "
				+ "\"practiceId\": null\r\n" + "}";
		return saveImhForm;
	}
}
	
