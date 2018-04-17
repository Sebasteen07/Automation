package com.intuit.ihg.product.integrationplatform.flows;

import java.util.ArrayList;

public interface iPIDCSendPatientInvite {
	ArrayList<String> sendPatientInviteToPractice(String restUrl, String practiceId, String externalSystemId,String birthDay,String zipCode,String token) throws Exception;
}