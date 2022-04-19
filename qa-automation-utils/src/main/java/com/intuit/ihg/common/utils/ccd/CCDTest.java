//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.common.utils.ccd;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;


import com.intuit.ihg.rest.RestUtils;


public class CCDTest {


	private static String completeRestUrl(String restUrl, long timeStamp) {
		return restUrl + timeStamp + ",0&max=100";
	}

	static void validateCDA(ClinicalDocument document) {
		ValidationResult result = new ValidationResult();
		CDAUtil.validate(document, result);

		System.out.println(result.getAllDiagnostics().size());

		for (Diagnostic diagnostic : result.getAllDiagnostics()) {
			CDADiagnostic cdaDiagnostic = new CDADiagnostic(diagnostic);
			if (cdaDiagnostic.isError()) {
				EObject target = cdaDiagnostic.getTarget();
				System.out.println(cdaDiagnostic.getMessage());
				System.out.println("target: " + target);
				System.out.println("");
			}
		}
	}

	public static String getFormCCD(long timeStamp, String restUrl) throws Exception {
		String xml;
		restUrl = completeRestUrl(restUrl, timeStamp);
		Map<String, Object> headers = new TreeMap<String, Object>();

		headers.put("Authentication-Type", "2wayssl");
		System.out.println("Generated url is " + restUrl);

		try {
			xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
		} catch (Exception requestException) {
			// Try to get response code from the exception message using regular expression
			int errorCode;
			Pattern pattern = Pattern.compile("\\d\\d\\d");
			Matcher matcher = pattern.matcher(requestException.getMessage());

			if (!matcher.find()) {
				System.out.print("Error code not found");
				throw requestException;
			} else {
				errorCode = Integer.parseInt(matcher.group());

				if (errorCode == 204) { // CCD may not have yet been generated
					System.out.println("No CCD found on first attempt, sleeping for 30 seconds before trying again");
					TimeUnit.SECONDS.sleep(30);
					xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
				} else {
					throw requestException;
				}
			}
		}

		xml = StringEscapeUtils.unescapeXml(xml);

		return xml;
	}

	public static String getAccessTokenForSystem(String url, String clientId, String clientSecret, String username, String password) throws Exception{		
		String urlForm = "grant_type=password&username=" + username + "&password=" + password;
		byte[] postData = urlForm.getBytes( StandardCharsets.UTF_8 );
				
		ClientRequest request = new ClientRequest(url);		
		request.header("Content-Type", "application/x-www-form-urlencoded");
		request.header("Authorization", "Basic " + clientId + ":" + clientSecret);
		request.body(MediaType.APPLICATION_FORM_URLENCODED_TYPE , postData);		
		ClientResponse<String> response = request.post(String.class);		
	
		assertEquals(response.getStatus(), 200, "HTTP Status not what expected");
		
		//loop through response to print it and find a match on the accessToken xml node (it's expected to be in a single response line)
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));		
		Pattern pattern = Pattern.compile("<accessToken>(.*?)</accessToken>");
		Matcher matcher;
		boolean found = false;
		
		String output, token = null;
		System.out.println("### TOKEN CALL RESPONSE: \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);			
			if (!found) {
				matcher = pattern.matcher(output);
				if (matcher.find())
				{
					found = true;
				    token = matcher.group(1);
				}
			}
		}
		System.out.println("\n");
		
		return token;
	}
}
