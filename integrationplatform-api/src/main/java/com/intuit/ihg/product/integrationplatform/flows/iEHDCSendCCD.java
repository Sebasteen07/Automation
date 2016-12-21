package com.intuit.ihg.product.integrationplatform.flows;

import java.util.ArrayList;


public interface iEHDCSendCCD {
	ArrayList<String> sendCCDToPractice(String RestUrl, String From, String IntegrationPracticeID, String PracticePatientId, String ccdXMLPath, String externalSystemId)
			throws Exception;
}