// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.integrationplatform.utils;

import java.util.List;
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
}