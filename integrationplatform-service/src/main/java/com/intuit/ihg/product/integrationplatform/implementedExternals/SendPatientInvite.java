package com.intuit.ihg.product.integrationplatform.implementedExternals;

import java.util.ArrayList;

import com.intuit.ihg.product.integrationplatform.flows.iPIDCSendPatientInvite;
import com.intuit.ihg.product.integrationplatform.pojo.PIDCInfo;
import com.intuit.ihg.product.integrationplatform.utils.RequestUtils;
import com.intuit.ihg.product.integrationplatform.utils.sendPatientInvitePayload;

public class SendPatientInvite implements iPIDCSendPatientInvite {

	@Override
	public ArrayList<String> sendPatientInviteToPractice(String restUrl, String practiceId, String externalSystemID) throws Exception {
		PIDCInfo testData = new PIDCInfo();
		testData.setRestUrl(restUrl);
		testData.setPracticeId(practiceId);

		
		//Enhancement Code
		testData.setBatchSize("1");
		ArrayList<String> gender = new ArrayList<String>(1);
		gender.add("MALE");
		testData.setGender(gender);
		ArrayList<String> race = new ArrayList<String>(1);
		race.add("White");
		testData.setRace(race);
		ArrayList<String> ethnicity = new ArrayList<String>(1);
		ethnicity.add("Hispanic or Latino");
		testData.setEthnicity(ethnicity);
		ArrayList<String> preferredLanguage = new ArrayList<String>(1);
		preferredLanguage.add("English");
		testData.setPreferredLanguage(preferredLanguage);
		ArrayList<String> preferredCommunication = new ArrayList<String>(1);
		preferredCommunication.add("US Mail");
		testData.setPreferredCommunication(preferredCommunication);
		testData.setPortalVersion("2.0");
		testData.setZipCode("27560");
		testData.setBirthDay("01/11/1987");
			
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