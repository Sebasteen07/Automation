package com.intuit.ihg.product.integrationplatform.flows;

public interface iAMDCSendSecureMessages {
	String sendSecureMessageToPractice(String RestUrl, String From, String PracticePatientId, String externalSystemId) throws Exception;
}
