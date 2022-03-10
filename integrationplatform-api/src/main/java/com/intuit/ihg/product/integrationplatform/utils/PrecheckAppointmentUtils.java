// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

public class PrecheckAppointmentUtils {
	Long timestamp;
	public PrecheckAppointmentUtils() {
		timestamp = System.currentTimeMillis();
	}
	
	public void verifyPatientDetail(PatientFormsExportInfo testData, List<String> patientData) throws Exception {
		Log4jUtil.log("Step 8: Verify precheck patient information updated in GET PIDC API call");
		Long since = timestamp / 1000L;
		Thread.sleep(9000);
		RestUtils.oauthSetup(testData.oAuthKeyStore1_FE, testData.oAuthProperty1_FE, testData.oAuthAppTokenCCD1_FE, testData.oAuthUsernameCCD1_FE, testData.oAuthPasswordCCD1_FE);
		String responseCode = RestUtils.setupHttpGetRequestWithEmptyResponse(testData.preCheckGetPIDC + "?since=" + since + ",0", testData.responsePath_CCD1_FE);
		
		if(responseCode.equalsIgnoreCase("200")) {	
			RestUtils.verifyPatientDetailsForPrecheckAppointment(testData.responsePath_CCD1_FE, testData.preCheckPatientExternalID, patientData);
		}
	}

	public String[] createTestData(PatientFormsExportInfo testData, WebDriver driver) throws IOException, URISyntaxException, InterruptedException {
		FormsExportUtils formUtilsObject = new FormsExportUtils();
		Log4jUtil.log("Step 1: Load forms data from external files");
		String workingDir = System.getProperty("user.dir");
		workingDir = workingDir + testData.patientfilepath_FE;
		formUtilsObject.setFormsTestData(workingDir, testData);
		String appointmentPayload = ExternalFileReader.readFromFile(testData.precheckAppointmentPayload);
		Log4jUtil.log("timestamp is " + timestamp);
		Long appointmentTime = System.currentTimeMillis();
		appointmentTime = appointmentTime + 180000L;
		Log4jUtil.log("Step 2: Get appointmentDate displayed");
		String tzid = "US/Pacific";
		TimeZone tz = TimeZone.getTimeZone(tzid);
		Date appointmentDate = new Date(appointmentTime);
		DateFormat dAppointmentFormat = new SimpleDateFormat("E M/d h:mm a zzz");
		dAppointmentFormat.setTimeZone(tz);
		Log4jUtil.log("appointmentDate is " + dAppointmentFormat.format(appointmentDate));
		Log4jUtil.log("Step 3: Post precheck appointment");
		appointmentPayload = appointmentPayload.replaceAll("externalIDValue", testData.preCheckPatientExternalID);
		appointmentPayload = appointmentPayload.replaceAll("firstNameValue", testData.preCheckPatientFirstName);
		appointmentPayload = appointmentPayload.replaceAll("emailIDValue", testData.preCheckPatientEmailID);
		appointmentPayload = appointmentPayload.replaceAll("timeValue", Long.toString(appointmentTime));
		RestUtils.setupHttpJSONPostRequest(testData.appointmentRestUrl, appointmentPayload, testData.responsePDFBatch_FE, testData.basicAccessToken);
		Log4jUtil.log("Step 4: Extract precheck appointment link from Email");
		YopMailUtils mail = new YopMailUtils(driver);
		String activationUrl = mail.getLinkFromEmail(testData.preCheckPatientEmailID, testData.preCheckEmailSubject, testData.preCheckEmailLink, 20);
		String[] links = {activationUrl, dAppointmentFormat.format(appointmentDate).toUpperCase().toString()};
		return links;
	}

}