package com.intuit.ihg.common.utils.ccd;


//import java.util.UUID;

//import junit.framework.Assert;

import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
//import org.junit.Test;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.util.CDADiagnostic;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.ValidationResult;

import com.intuit.ihg.rest.RestUtils;


public class CCDTest {
	

	private static String completeRestUrl(String restUrl, long timeStamp) {
		restUrl = new String(restUrl + timeStamp + ",0&max=100");
		return restUrl;
	}
	
	static void validateCDA(ClinicalDocument document) {
    	ValidationResult result = new ValidationResult();
		CDAUtil.validate(document, result);

		System.out.println(result.getAllDiagnostics().size());

		for (Diagnostic diagnostic : result.getAllDiagnostics()) {
			CDADiagnostic cdaDiagnostic = new CDADiagnostic(diagnostic);
			if(cdaDiagnostic.isError()) {
				EObject target = cdaDiagnostic.getTarget();
				System.out.println(cdaDiagnostic.getMessage());
				System.out.println("target: " + target);
				System.out.println("");
			}						
		}
    }
	
	/**
	 * @brief Retrieves CCD produced by form using rest call.
	 * @param timeStamp The time (unix timestamp) to define when the form was submitted. The form should be
	 * 					submitted sometime between the timestamp time and present time
	 * @param restUrl	URL for the rest call (includes practice integration ID retrievable from - SiteGenerator > Interface Setup > External Systems) 
	 * @return Method returns CCD in the form of xml as a String value
	 */ 
	public static String getFormCCD(long timeStamp, String restUrl) throws Exception {
		String xml = new String();
		restUrl = completeRestUrl(restUrl, timeStamp);
		Map<String, Object> headers = new TreeMap<String, Object>();
		
		headers.put("Authentication-Type", "2wayssl");
		System.out.println("Generated url is " + restUrl);
		
		try {
			xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
		} 
		catch (Exception requestException) {
			// first 3 or more letters of the exception message contain request error code
			int errorCode = Integer.parseInt( requestException.getMessage().substring(0, 3) ); 
			if (errorCode == 204) { // CCD may not have yet been generated
				Thread.sleep(2000);
				xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
			}
			else {
				throw requestException;
			}
		}
		
		xml = StringEscapeUtils.unescapeXml(xml);
		
		return xml;
	}
}
