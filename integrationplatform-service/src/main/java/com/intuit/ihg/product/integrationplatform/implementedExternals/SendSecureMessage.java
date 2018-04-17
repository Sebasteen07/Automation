package com.intuit.ihg.product.integrationplatform.implementedExternals;
import com.intuit.ihg.product.integrationplatform.flows.iAMDCSendSecureMessages;
import com.intuit.ihg.product.integrationplatform.pojo.AMDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.AMDCSecurePayload;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;

public class SendSecureMessage implements iAMDCSendSecureMessages {

	@Override
	public String sendSecureMessageToPractice(String restUrl, String from, String practicePatientId, String externalSystemID,String token) throws Exception {
		AMDCInfo testData = new AMDCInfo();
		testData.setRestUrl(restUrl);
		testData.setFrom(from);
		testData.setPatientExternalId(practicePatientId);

		String payload = AMDCSecurePayload.getAMDCPayload(testData);
		String response = RequestUtils.getStatus(testData.getRestUrl(), payload, externalSystemID,token);
		return response;
	}
}
