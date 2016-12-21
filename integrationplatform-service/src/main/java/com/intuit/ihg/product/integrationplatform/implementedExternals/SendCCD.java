package com.intuit.ihg.product.integrationplatform.implementedExternals;

import java.util.ArrayList;

import com.intuit.ihg.product.integrationplatform.flows.iEHDCSendCCD;
import com.intuit.ihg.product.integrationplatform.pojo.EHDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;
import com.intuit.ihg.product.integrationplatform.utils.SendCCDPayload;

public class SendCCD implements iEHDCSendCCD {

	@Override
	public ArrayList<String> sendCCDToPractice(String RestUrl, String From, String IntegrationPracticeID, String PracticePatientId, String ccdXMLPath, String externalSystemID)
			throws Exception {
		EHDCInfo testData = new EHDCInfo();
		testData.setRestUrl(RestUrl);
		testData.setFrom(From);
		testData.setIntegrationPracticeID(IntegrationPracticeID);
		testData.setPracticePatientId(PracticePatientId);
		testData.setCcdXMLPath(ccdXMLPath);

		String payload = SendCCDPayload.getCCDPayload(testData);
		String messageID = SendCCDPayload.ccdMessageID;
		String response = RequestUtils.getStatus(testData.getRestUrl(), payload, externalSystemID);
		ArrayList<String> ccdDetails = new ArrayList<String>();
		ccdDetails.add(messageID);
		ccdDetails.add(response);
		return ccdDetails;
	}
}