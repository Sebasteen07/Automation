package com.intuit.ihg.product.integrationplatform.flows;

public interface iAMDCSendSecureMessages {
	String sendSecureMessageToPractice(String restUrl, String from, String practicePatientId, String externalSystemId,String token) throws Exception;
}
