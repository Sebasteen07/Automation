// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"merchantName",
"merchantLegalName",
"doingBusinessAs",
"merchantAddress",
"remitToAddress",
"phoneNumber",
"externalMerchantId",
"customerAccountNumber",
"maxTransactionLimit",
"merchantAddress",
"qbPayAccountDetails",
"status",
"fraudVars",
"alsoKnownAsName",
"primaryContactFirstName",
"primaryContactLastName",
"primaryContactEmail",
"primaryContactPhoneNumber",
"legalEntityAddress",
"legalEntityPhoneNumber",
"infiniceptMerchantApplicationId",
"payAPICustomer",
"deactivatedDate",
"contractedRates",
"remitToAddress",
"acceptedCreditCards",
"accountDetails",
"contractedRates",
"payAPICustomer",
"merchantAttributes"
})

public class PaypalMerchantInfo {
	
@JsonProperty("id")
private String id;
@JsonProperty("merchantName")
private String merchantName;
@JsonProperty("externalMerchantId")
private Integer externalMerchantId;
@JsonProperty("customerAccountNumber")
private String customerAccountNumber;
@JsonProperty("maxTransactionLimit")
private Integer maxTransactionLimit;
@JsonProperty("merchantAddress")
private MerchantAddress merchantAddress;
@JsonProperty("remitToAddress")
private Object remitToAddress;
@JsonProperty("acceptedCreditCards")
private List<String> acceptedCreditCards = null;
@JsonProperty("accountDetails")
private AccountDetails accountDetails;
@JsonProperty("contractedRates")
private Object contractedRates;
@JsonProperty("payAPICustomer")
private PayAPICustomer payAPICustomer;
@JsonProperty("merchantAttributes")
private List<Object> merchantAttributes = null;

@JsonProperty("merchantName")
public String getMerchantName() {
return merchantName;
}

@JsonProperty("merchantName")
public void setMerchantName(String merchantName) {
this.merchantName = merchantName;
}

@JsonProperty("externalMerchantId")
public Integer getExternalMerchantId() {
return externalMerchantId;
}

@JsonProperty("externalMerchantId")
public void setExternalMerchantId(Integer externalMerchantId) {
this.externalMerchantId = externalMerchantId;
}

@JsonProperty("customerAccountNumber")
public String getCustomerAccountNumber() {
return customerAccountNumber;
}

@JsonProperty("customerAccountNumber")
public void setCustomerAccountNumber(String customerAccountNumber) {
this.customerAccountNumber = customerAccountNumber;
}

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setid(String id) {
this.id = id;
}

@JsonProperty("maxTransactionLimit")
public Integer getMaxTransactionLimit() {
return maxTransactionLimit;
}

@JsonProperty("maxTransactionLimit")
public void setMaxTransactionLimit(Integer maxTransactionLimit) {
this.maxTransactionLimit = maxTransactionLimit;
}

@JsonProperty("merchantAddress")
public MerchantAddress getMerchantAddress() {
return merchantAddress;
}

@JsonProperty("merchantAddress")
public void setMerchantAddress(MerchantAddress merchantAddress) {
this.merchantAddress = merchantAddress;
}

@JsonProperty("remitToAddress")
public Object getRemitToAddress() {
return remitToAddress;
}

@JsonProperty("remitToAddress")
public void setRemitToAddress(Object remitToAddress) {
this.remitToAddress = remitToAddress;
}


@JsonProperty("acceptedCreditCards")
public List<String> getAcceptedCreditCards() {
return acceptedCreditCards;
}

@JsonProperty("acceptedCreditCards")
public void setAcceptedCreditCards(List<String> acceptedCreditCards) {
this.acceptedCreditCards = acceptedCreditCards;
}

@JsonProperty("accountDetails")
public AccountDetails getAccountDetails() {
return accountDetails;
}

@JsonProperty("accountDetails")
public void setAccountDetails(AccountDetails accountDetails) {
this.accountDetails = accountDetails;
}

@JsonProperty("contractedRates")
public Object getContractedRates() {
return contractedRates;
}

@JsonProperty("contractedRates")
public void setContractedRates(Object contractedRates) {
this.contractedRates = contractedRates;
}

@JsonProperty("payAPICustomer")
public PayAPICustomer getPayAPICustomer() {
return payAPICustomer;
}

@JsonProperty("payAPICustomer")
public void setPayAPICustomer(PayAPICustomer payAPICustomer) {
this.payAPICustomer = payAPICustomer;
}

@JsonProperty("merchantAttributes")
public List<Object> getMerchantAttributes() {
return merchantAttributes;
}

@JsonProperty("merchantAttributes")
public void setMerchantAttributes(List<Object> merchantAttributes) {
this.merchantAttributes = merchantAttributes;
}

public static Map<String, Object> getMerchantMap(String merchantname,String externalmerchantid,String customeraccountnumber,
	String transactionlimit,String paypalusername,String paypalpassword) {
	
	Map<String, Object> merchantdetails = new HashMap<String, Object>(); 
	merchantdetails.put("merchantName", merchantname+ProvisioningUtils.randomizeMerchantIdentifiers());
	merchantdetails.put("externalMerchantId", externalmerchantid);
	merchantdetails.put("customerAccountNumber", customeraccountnumber);
	merchantdetails.put("maxTransactionLimit", transactionlimit);
	merchantdetails.put("maxTransactionLimit", transactionlimit);
	merchantdetails.put("acceptedCreditCards", Arrays.asList(PracticeConstants.CARD_ARRAY_LIST));
	merchantdetails.put("accountDetails", PaypalMerchantAccountDetails.getMerchantAccountDetailsMap(paypalusername,paypalpassword));
	return merchantdetails;
}
}
