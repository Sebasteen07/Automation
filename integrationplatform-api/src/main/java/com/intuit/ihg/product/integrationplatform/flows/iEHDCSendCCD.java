package com.intuit.ihg.product.integrationplatform.flows;

import java.util.ArrayList;


public interface iEHDCSendCCD {
	ArrayList<String> sendCCDToPractice(String restUrl, String from, String integrationPracticeID, String practicePatientId, String ccdXMLPath, String externalSystemId)
			throws Exception;
}