package com.intuit.ihg.product.integrationplatform.implementedExternals;

import java.util.ArrayList;

import com.intuit.ihg.product.integrationplatform.flows.iEHDCSendCCD;
import com.intuit.ihg.product.integrationplatform.pojo.EHDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;
import com.intuit.ihg.product.integrationplatform.utils.SendCCDPayload;

public class SendCCD implements iEHDCSendCCD {

	@Override
	public ArrayList<String> sendCCDToPractice(String restUrl, String from, String integrationPracticeID, String practicePatientId, String ccdXMLPath, String externalSystemID,String token)
			throws Exception {
		EHDCInfo testData = new EHDCInfo();
		testData.setRestUrl(restUrl);
		testData.setFrom(from);
		testData.setIntegrationPracticeID(integrationPracticeID);
		testData.setPracticePatientId(practicePatientId);
		testData.setCcdXMLPath(ccdXMLPath);

		String payload = SendCCDPayload.getCCDPayload(testData);
		String messageID = SendCCDPayload.ccdMessageID;
		String response = RequestUtils.getStatus(testData.getRestUrl(), payload, externalSystemID,token);
		ArrayList<String> ccdDetails = new ArrayList<String>();
		ccdDetails.add(messageID);
		ccdDetails.add(response);
		return ccdDetails;
	}
}