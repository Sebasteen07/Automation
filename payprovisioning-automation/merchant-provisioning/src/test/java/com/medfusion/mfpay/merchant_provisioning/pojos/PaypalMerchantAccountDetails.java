// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"preferredProcessor",
"paypalAccountDetails"
})
public class PaypalMerchantAccountDetails {

@JsonProperty("preferredProcessor")
private String preferredProcessor;
@JsonProperty("paypalAccountDetails")
private PaypalMerchantAccountDetails paypalAccountDetails;

@JsonProperty("preferredProcessor")
public String getPreferredProcessor() {
return preferredProcessor;
}

@JsonProperty("preferredProcessor")
public void setPreferredProcessor(String preferredProcessor) {
this.preferredProcessor = preferredProcessor;
}

@JsonProperty("paypalAccountDetails")
public PaypalMerchantAccountDetails getPaypalAccountDetails() {
return paypalAccountDetails;
}

@JsonProperty("paypalAccountDetails")
public void setPaypalAccountDetails(PaypalMerchantAccountDetails paypalAccountDetails) {
this.paypalAccountDetails = paypalAccountDetails;
}

@Override
public String toString() {
	return "PaypalMedfusionAccountDetails [preferredProcessor="
			+ preferredProcessor + ", paypalAccountDetails="
			+ paypalAccountDetails + "]";
}

public static Map<String, Object> getMerchantAccountDetailsMap(String paypalusername,String paypalpassword) {
	
	Map<String, Object> accountdetails = new HashMap<String, Object>(); 
	accountdetails.put("preferredProcessor", PracticeConstants.PREFERRED_PROCESSOR_PAYPAL);
	accountdetails.put("paypalAccountDetails", PaypalAccountDetails.getAccountDetailsMap(paypalusername,paypalpassword));
	return accountdetails;
	
}

}


