package com.intuit.ihg.product.integrationplatform.flows;

import java.util.ArrayList;

public interface iPIDCSendPatientInvite {
	ArrayList<String> sendPatientInviteToPractice(String restUrl, String practiceId, String externalSystemId) throws Exception;
}