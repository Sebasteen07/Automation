// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"preferredProcessor",
"paypalAccountDetails"
})
public class InstamedMerchantAccountDetails {

	private String merchantId;
	private String storeId;
	private InstamedTerminalIdDetails instamedTerminalId;


public InstamedTerminalIdDetails getInstamedTerminalId() {
		return instamedTerminalId;
	}

	public void setInstamedTerminalId(InstamedTerminalIdDetails instamedTerminalId) {
		this.instamedTerminalId = instamedTerminalId;
	}

@Override
public String toString() {
	return " [merchantId="
			+ merchantId +",storeId =" +storeId + ",InstamedTerminalIdDetails =" + instamedTerminalId +"]";
}

 static Map<String, Object> getMerchantAccountDetailsMap(String merchantId,String storeId ,String virtualVisit,String patientPortal,String preCheck) {
	
	Map<String, Object> accountdetails = new HashMap<String, Object>(); 
	accountdetails.put("merchantId", merchantId);
	accountdetails.put("storeId", storeId);
	accountdetails.put("instaMedTerminalIDList", InstamedTerminalIdDetails.getInstaMedTerminalIdMap(virtualVisit, patientPortal, preCheck));

	return accountdetails;
	
}

}


