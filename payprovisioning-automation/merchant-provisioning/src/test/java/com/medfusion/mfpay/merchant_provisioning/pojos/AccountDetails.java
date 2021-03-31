// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"preferredProcessor",
"routingNumber",
"federalTaxId",
"checkingDepositType",
"accountType",
"accountNumber",
"worldPayAccountDetails",
"routingNumber",
"accountNumber",
"accountType",
"checkingDepositType",
"amexSid"
})


public class AccountDetails {
	
	
	
	public static Map<String, Object> getMerchantAccountDetailsMap(String accountnumber, String routingnumber,String federaltaxid,
			String doingbusinessas, String businessestablisheddate, String businesstype, String mcccode, String ownershiptype, String websiteurl){
		Map<String, Object> accountdetails = new HashMap<String, Object>(); 
		accountdetails.put("accountType", PracticeConstants.ACCOUNT_TYPE);
		accountdetails.put("routingNumber",routingnumber); 
		accountdetails.put("accountNumber", accountnumber);
		accountdetails.put("federalTaxId",federaltaxid); 
		accountdetails.put("preferredProcessor", PracticeConstants.PREFERRED_PROCESSOR_ELEMENT);
		accountdetails.put("checkingDepositType", PracticeConstants.CHECKING_TYPE);
		accountdetails.put("worldPayAccountDetails",WorldPayAccountDetails.getWPAccountDetailsMap(doingbusinessas,
				businessestablisheddate,businesstype,mcccode,ownershiptype,websiteurl )); 
		return accountdetails;
		
	}
	
	
	private String preferredProcessor;
	private String routingNumber;
	private String federalTaxId;
	private String checkingDepositType;
	private String accountType;
	private String accountNumber;
	private WorldPayAccountDetails worldPayAccountDetails;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();


	public String getPreferredProcessor() {
	return preferredProcessor;
	}


	public void setPreferredProcessor(String preferredProcessor) {
	this.preferredProcessor = preferredProcessor;
	}


	public String getRoutingNumber() {
	return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
	this.routingNumber = routingNumber;
	}

	public String getFederalTaxId() {
	return federalTaxId;
	}

	public void setFederalTaxId(String federalTaxId) {
	this.federalTaxId = federalTaxId;
	}

	public String getCheckingDepositType() {
	return checkingDepositType;
	}

	public void setCheckingDepositType(String checkingDepositType) {
	this.checkingDepositType = checkingDepositType;
	}

	public String getAccountType() {
	return accountType;
	}

	public void setAccountType(String accountType) {
	this.accountType = accountType;
	}

	public String getAccountNumber() {
	return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public WorldPayAccountDetails getWorldPayAccountDetails() {
	return worldPayAccountDetails;
	}

	public void setWorldPayAccountDetails(WorldPayAccountDetails worldPayAccountDetails) {
	this.worldPayAccountDetails = worldPayAccountDetails;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}


	@Override
	public String toString() {
		return "AccountDetails [accountType=" + this.accountType
				+ ", routingNumber=" + this.routingNumber + ", accountNumber="
				+ this.accountNumber + ", federalTaxId=" + this.federalTaxId
				+ ", preferredProcessor=" + this.preferredProcessor
				+ ", worldPayAccountDetails=" + this.worldPayAccountDetails + "]";
	}
	
	

}
