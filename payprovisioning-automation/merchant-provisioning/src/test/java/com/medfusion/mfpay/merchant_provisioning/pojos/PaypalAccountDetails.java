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
"paypalCardNotPresentUsername",
"paypalCardNotPresentPassword",
"paypalPartner",
"routingNumber",
"accountNumber",
"accountType",
"checkingDepositType",
"amexSid",
})

public class PaypalAccountDetails {

@JsonProperty("paypalCardNotPresentUsername")
private String paypalCardNotPresentUsername;
@JsonProperty("paypalCardNotPresentPassword")
private String paypalCardNotPresentPassword;
@JsonProperty("paypalPartner")
private String paypalPartner;

@JsonProperty("paypalCardNotPresentUsername")
public String getPaypalCardNotPresentUsername() {
return paypalCardNotPresentUsername;
}

@JsonProperty("paypalCardNotPresentUsername")
public void setPaypalCardNotPresentUsername(String paypalCardNotPresentUsername) {
this.paypalCardNotPresentUsername = paypalCardNotPresentUsername;
}

@JsonProperty("paypalCardNotPresentPassword")
public String getPaypalCardNotPresentPassword() {
return paypalCardNotPresentPassword;
}

@JsonProperty("paypalCardNotPresentPassword")
public void setPaypalCardNotPresentPassword(String paypalCardNotPresentPassword) {
this.paypalCardNotPresentPassword = paypalCardNotPresentPassword;
}

@JsonProperty("paypalPartner")
public String getPaypalPartner() {
return paypalPartner;
}

@JsonProperty("paypalPartner")
public void setPaypalPartner(String paypalPartner) {
this.paypalPartner = paypalPartner;
}

@Override
public String toString() {
	return "PaypalAccountDetails [paypalCardNotPresentUsername="
			+ paypalCardNotPresentUsername + ", paypalCardNotPresentPassword="
			+ paypalCardNotPresentPassword + ", paypalPartner=" + paypalPartner
			+ "]";
}

public static Map<String, Object> getAccountDetailsMap(String paypalusername,String paypalpassword) {
	
	Map<String, Object> accountdetails = new HashMap<String, Object>(); 
	accountdetails.put("paypalCardNotPresentUsername", paypalusername);
	accountdetails.put("paypalCardNotPresentPassword", paypalpassword);
	accountdetails.put("paypalPartner", PracticeConstants.PAYPAL_PARTNER);
	return accountdetails;
	
}

}
