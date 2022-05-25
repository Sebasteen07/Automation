// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"preferredProcessor",

})

public class InstamedAccountDetails {

private String preferredProcessor;
private InstamedMerchantAccountDetails instamedMerchantAccountDetails;


@Override
public String toString() {
	return "InstamedAccountDetails [preferredProcessor="
			+ preferredProcessor + ",instamedMerchantAccountDetails ="+ instamedMerchantAccountDetails +"]";
}



public static Map<String, Object> getAccountDetailsMap(String preferredProcessor,String merchantId,String storeId ,String virtualVisit,String patientPortal,String preCheck) {
	
	Map<String, Object> accountdetails = new HashMap<String, Object>(); 
	accountdetails.put("preferredProcessor", preferredProcessor);
	accountdetails.put("instaMedAccountDetails", InstamedMerchantAccountDetails.getMerchantAccountDetailsMap(merchantId, storeId , virtualVisit, patientPortal, preCheck));

	
	return accountdetails;
	
}

}
