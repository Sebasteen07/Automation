package com.intuit.ihg.product.integrationplatform.implementedExternals;

import java.util.ArrayList;

import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.pojo.PatientDetail;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;
import com.intuit.ihg.product.integrationplatform.utils.sendPatientInvitePayload;

public class SendPatientInvite implements iPIDCSendPatientInvite {

	@Override
	public ArrayList<String> sendPatientInviteToPractice(String restUrl, String practiceId, String externalSystemID,String birthDay,String zipCode) throws Exception {
		PIDCInfo testData = new PIDCInfo();
		testData.setRestUrl(restUrl);
		testData.setPracticeId(practiceId);

		
		//Enhancement Code
		testData.patientDetailList.add(new PatientDetail("Race", "Ethnicity", "Gender", "PreferredLanguage", "PreferredCommunication", "GenderIdentity", "SexualOrientation"));
		testData.patientDetailList.add(new PatientDetail("American Indian or Alaska Native", "Hispanic or Latino", "MALE", "English", "US Mail", "Straight or heterosexual", "Male"));
		testData.setBatchSize("1");
		testData.setPortalVersion("2.0");
		testData.setZipCode(zipCode);
		testData.setBirthDay(birthDay);
		sendPatientInvitePayload payloadObj = new sendPatientInvitePayload();
		String payload = payloadObj.getPIDCPayload(testData,"2.0");
		String firstName = payloadObj.firstName;
		String lastName = payloadObj.lastName;
		String zip = payloadObj.zip;
		String birthday = payloadObj.date;
		String email = payloadObj.email;
		
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