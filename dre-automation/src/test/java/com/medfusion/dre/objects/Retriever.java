package com.medfusion.dre.objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import com.medfusion.dre.util.HTTPCalls;
import org.json.JSONObject;

public class Retriever extends HTTPCalls {

	public String Useruuid;
	public String ProfileId;
	public String PortalId;
	public String Username;
	public String Password;
	public String Cusername;
	public String Cpassword;
	public String Secret;
	public String Type;
	public String InvalidUsername;
	public String InvalidPassword;

	public JSONObject generatePayload() {
		JSONObject payload = new JSONObject();
		JSONObject creds = new JSONObject();

		payload.put("profileId", ProfileId);
		payload.put("portalId", PortalId);
		payload.put("credentials", creds);
		creds.put("type", Type);
		creds.put("username", Username);
		creds.put("password", Password);

		System.out.println(payload.toString());
		return payload;
	}
	
	public JSONObject generateInvalidPayload() {
		JSONObject payload = new JSONObject();
		JSONObject creds = new JSONObject();

		payload.put("profileId", ProfileId);
		payload.put("portalId", PortalId);
		payload.put("credentials", creds);
		creds.put("type", Type);
		creds.put("username", InvalidUsername);
		creds.put("password", InvalidPassword);

		System.out.println(payload.toString());
		return payload;
	}
	
	public JSONObject generatePayloadWithSecret() {
		JSONObject payload = new JSONObject();
		JSONObject creds = new JSONObject();

		payload.put("profileId", ProfileId);
		payload.put("portalId", PortalId);
		payload.put("credentials", creds);
		creds.put("type", Type);
		creds.put("username", Username);
		creds.put("password", Password);
		creds.put("securityAnswer", Secret);

		System.out.println(payload.toString());
		return payload;
	}

	public String verifyJobStatus(HttpGet httpGet) throws InterruptedException {
		String response = new String();
		Thread.sleep(180000);  // Initially wait 3 minutes before checking
		executeRequestGetContent(httpGet);

		for (int i = 0; i < 10; i++) {
			Thread.sleep(30000);
			String finalStatus = executeRequestGetContent(httpGet);

			if (isContain(finalStatus, "SUCCESS")) {
				response = "SUCCESS";
				break;
			} 
			else if (isContain(finalStatus, "SUBMITTED")) {
			  System.out.println("At " + (i * 30 + 180) + " seconds, job is still processing");
			  response = "SUBMITTED";
			}
			else if (isContain(finalStatus, "FAILED")) {
				System.out.println("Retrieval has failed");
				response = "FAILED";
				break;
			}
			else if (isContain(finalStatus, "ERROR_USER_AUTH")) {
				System.out.println("Incorrect user credentials");
				response = "INVALID_CREDS";
				break;
			}
		}

		return response;
	}
	
	public String verifyErrorStatus(HttpGet httpGet) throws InterruptedException {
		String response = new String();
		Thread.sleep(30000);  // Initially wait 30 seconds before checking
		executeRequestGetContent(httpGet);

		for (int i = 0; i < 10; i++) {
			Thread.sleep(15000);
			String finalStatus = executeRequestGetContent(httpGet);

			if (isContain(finalStatus, "SUCCESS")) {
				response = "SUCCESS";
				break;
			} 
			else if (isContain(finalStatus, "SUBMITTED")) {
			  System.out.println("At " + (i * 15 + 30) + " seconds, job is still processing");
			  response = "SUBMITTED";
			}
			else if (isContain(finalStatus, "FAILED")) {
				System.out.println("Retrieval has failed");
				response = "FAILED";
				break;
			}
			else if (isContain(finalStatus, "ERROR_USER_AUTH")) {
				System.out.println("Incorrect user credentials");
				response = "ERROR_USER_AUTH";
				break;
			}
		}

		return response;
	}

	public static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}
}
