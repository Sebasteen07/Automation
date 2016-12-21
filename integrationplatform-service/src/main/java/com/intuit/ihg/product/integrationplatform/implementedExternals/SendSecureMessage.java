package com.intuit.ihg.product.integrationplatform.implementedExternals;
import com.intuit.ihg.product.integrationplatform.flows.iAMDCSendSecureMessages;
import com.intuit.ihg.product.integrationplatform.pojo.AMDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.AMDCSecurePayload;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;

public class SendSecureMessage implements iAMDCSendSecureMessages {

	@Override
	public String sendSecureMessageToPractice(String RestUrl, String From, String PracticePatientId, String externalSystemID) throws Exception {
		AMDCInfo testData = new AMDCInfo();
		testData.setRestUrl(RestUrl);
		testData.setFrom(From);
		testData.setPatientExternalId(PracticePatientId);

		String payload = AMDCSecurePayload.getAMDCPayload(testData);
		String response = RequestUtils.getStatus(testData.getRestUrl(), payload, externalSystemID);
		return response;
	}
}
