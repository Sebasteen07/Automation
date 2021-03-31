// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
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
"amexSid"
})

public class PaypalAccountDetails {

private String paypalCardNotPresentUsername;
private String paypalCardNotPresentPassword;
private String paypalPartner;

public String getPaypalCardNotPresentUsername() {
return paypalCardNotPresentUsername;
}

public void setPaypalCardNotPresentUsername(String paypalCardNotPresentUsername) {
this.paypalCardNotPresentUsername = paypalCardNotPresentUsername;
}

public String getPaypalCardNotPresentPassword() {
return paypalCardNotPresentPassword;
}

public void setPaypalCardNotPresentPassword(String paypalCardNotPresentPassword) {
this.paypalCardNotPresentPassword = paypalCardNotPresentPassword;
}

public String getPaypalPartner() {
return paypalPartner;
}

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
