package com.intuit.ihg.common.utils.ccd;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

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
			if(cdaDiagnostic.isError()) {
				EObject target = cdaDiagnostic.getTarget();
				System.out.println(cdaDiagnostic.getMessage());
				System.out.println("target: " + target);
				System.out.println("");
			}
		}
    }

	/**
	 * Retrieves CCD produced by form using rest call.
	 * @param timeStamp The time (unix timestamp) to define when the form was submitted. The form should be
	 * 					submitted sometime between the timestamp time and present time
	 * @param restUrl	URL for the rest call (includes practice integration ID retrievable from - SiteGenerator > Interface Setup > External Systems)
	 * @return Method returns CCD in the form of xml as a String value
	 */
	public static String getFormCCD(long timeStamp, String restUrl) throws Exception {
		String xml;
		restUrl = completeRestUrl(restUrl, timeStamp);
		Map<String, Object> headers = new TreeMap<String, Object>();

		headers.put("Authentication-Type", "2wayssl");
		System.out.println("Generated url is " + restUrl);

		try {
			xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
		}
		catch (Exception requestException) {
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
                    TimeUnit.SECONDS.sleep(10);
                    xml = RestUtils.get(restUrl, String.class, MediaType.APPLICATION_XML, headers);
                } else {
                    throw requestException;
                }
            }
        }

		xml = StringEscapeUtils.unescapeXml(xml);

		return xml;
	}
}
