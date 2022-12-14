// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.List;
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
	
	public static Map<String, Object> getFeeAccountDetailsMap(String accountnumber, String routingnumber){
		Map<String, Object> accountdetails = new HashMap<String, Object>(); 
		accountdetails.put("accountType", PracticeConstants.ACCOUNT_TYPE);
		accountdetails.put("routingNumber",routingnumber); 
		accountdetails.put("accountNumber", accountnumber);
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
	
	public static Map<String, Object> editAccountDetailsMap(List<String> cardList,
			String 	accountNumber,String accountType ,String checkingDepositType ,String feeAccountNumber ,String feeAccountType ,String feeRoutingNumber,String preferredProcessor,String seprateFunding,String routingNumber,String merchantName,String maxTransactionLimit) {
		Map<String, Object> merchantAccountDetails = new HashMap<String, Object>();
		
		Map<String, Object> statementOptions = new HashMap<String, Object>();

		merchantAccountDetails.put("acceptedCreditCards",cardList);
		
		merchantAccountDetails.put("accountDetails", getAccountDetailsMap(accountNumber,accountType,checkingDepositType,feeAccountNumber,feeAccountType,feeRoutingNumber,seprateFunding,routingNumber, preferredProcessor));
		
		long maxTransactionLimitLong=Long.parseLong(maxTransactionLimit);  

		merchantAccountDetails.put("maxTransactionLimit",maxTransactionLimitLong);
		merchantAccountDetails.put("merchantName",merchantName);
		merchantAccountDetails.put("statementOptions",statementOptions );

		return merchantAccountDetails;

	}
	
	public static Map<String, Object> getAccountDetailsMap(String accountNumber, String accountType,
			String checkingDepositType,String feeAccountNumber ,String feeAccountType,String feeRoutingNumber,String seprateFunding,String routingNumber,String preferredProcessor ){

		Map<String, Object> accountDetailsMap = new HashMap<String, Object>(); 
		Map<String, Object> worldPayAccountDetailsMap = new HashMap<String, Object>();
		
		accountDetailsMap.put("accountNumber", accountNumber);
		accountDetailsMap.put("accountType",accountType); 
		accountDetailsMap.put("checkingDepositType", checkingDepositType);
	
		accountDetailsMap.put("preferredProcessor",preferredProcessor );
		
		boolean bool = Boolean.parseBoolean(seprateFunding);
		accountDetailsMap.put("separateFundingAccounts",bool );
		accountDetailsMap.put("routingNumber",routingNumber );
		
		accountDetailsMap.put("worldPayAccountDetails", worldPayAccountDetailsMap);
		
		accountDetailsMap.put("feeAccountDetails", getFeeAccountDetailsMap(feeAccountNumber,feeAccountType,feeRoutingNumber));
		return accountDetailsMap;
		
	}

	
	public static Map<String, Object> getFeeAccountDetailsMap(String feeAccountNumber, String feeAccountType,
			String routingNumber){
		
		Map<String, Object> feeAccountDetailsMap = new HashMap<String, Object>(); 
		feeAccountDetailsMap.put("accountNumber", "7294732085023");
		feeAccountDetailsMap.put("accountType","C"); 
		feeAccountDetailsMap.put("routingNumber", "042000313");
		
		return feeAccountDetailsMap;
		
	}


}
