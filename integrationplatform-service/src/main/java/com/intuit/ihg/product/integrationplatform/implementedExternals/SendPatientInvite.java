package com.intuit.ihg.product.integrationplatform.implementedExternals;

import java.util.ArrayList;

import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;
import com.intuit.ihg.product.integrationplatform.utils.sendPatientInvitePayload;

public class SendPatientInvite implements iPIDCSendPatientInvite {

	@Override
	public ArrayList<String> sendPatientInviteToPractice(String restUrl, String practiceId, String externalSystemID) throws Exception {
		// TODO Auto-generated method stub
		PIDCInfo testData = new PIDCInfo();
		testData.setRestUrl(restUrl);
		testData.setPracticeId(practiceId);
		// testData.setGender(gender);
		
		System.out.println("getPIDCPayload rest url"+restUrl);
		
		String payload = sendPatientInvitePayload.getPIDCPayload(testData);
		String firstName = sendPatientInvitePayload.firstName;
		String lastName = sendPatientInvitePayload.lastName;
		String zip = sendPatientInvitePayload.zip;
		String birthday = sendPatientInvitePayload.date;
		String email = sendPatientInvitePayload.email;
		String response = RequestUtils.getStatus(testData.getRestUrl(), payload, externalSystemID);
		ArrayList<String> newPatientDetails = new ArrayList<String>();
		newPatientDetails.add(firstName);
		newPatientDetails.add(lastName);
		newPatientDetails.add(zip);
		newPatientDetails.add(birthday);
		newPatientDetails.add(email);
		newPatientDetails.add(response);

		return newPatientDetails;
	}
}
