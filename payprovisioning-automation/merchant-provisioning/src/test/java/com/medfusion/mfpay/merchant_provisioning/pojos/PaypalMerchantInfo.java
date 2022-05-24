// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
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
	
private String id;
private String merchantName;
private Integer externalMerchantId;
private String customerAccountNumber;
private Integer maxTransactionLimit;
private MerchantAddress merchantAddress;
private Object remitToAddress;
private List<String> acceptedCreditCards = null;
private AccountDetails accountDetails;
private Object contractedRates;
private PayAPICustomer payAPICustomer;
private List<Object> merchantAttributes = null;

public String getMerchantName() {
return merchantName;
}

public void setMerchantName(String merchantName) {
this.merchantName = merchantName;
}

public Integer getExternalMerchantId() {
return externalMerchantId;
}

public void setExternalMerchantId(Integer externalMerchantId) {
this.externalMerchantId = externalMerchantId;
}

public String getCustomerAccountNumber() {
return customerAccountNumber;
}

public void setCustomerAccountNumber(String customerAccountNumber) {
this.customerAccountNumber = customerAccountNumber;
}

public String getId() {
return id;
}

public void setid(String id) {
this.id = id;
}

public Integer getMaxTransactionLimit() {
return maxTransactionLimit;
}

public void setMaxTransactionLimit(Integer maxTransactionLimit) {
this.maxTransactionLimit = maxTransactionLimit;
}

public MerchantAddress getMerchantAddress() {
return merchantAddress;
}

public void setMerchantAddress(MerchantAddress merchantAddress) {
this.merchantAddress = merchantAddress;
}

public Object getRemitToAddress() {
return remitToAddress;
}

public void setRemitToAddress(Object remitToAddress) {
this.remitToAddress = remitToAddress;
}

public List<String> getAcceptedCreditCards() {
return acceptedCreditCards;
}

public void setAcceptedCreditCards(List<String> acceptedCreditCards) {
this.acceptedCreditCards = acceptedCreditCards;
}

public AccountDetails getAccountDetails() {
return accountDetails;
}

public void setAccountDetails(AccountDetails accountDetails) {
this.accountDetails = accountDetails;
}

public Object getContractedRates() {
return contractedRates;
}

public void setContractedRates(Object contractedRates) {
this.contractedRates = contractedRates;
}

public PayAPICustomer getPayAPICustomer() {
return payAPICustomer;
}

public void setPayAPICustomer(PayAPICustomer payAPICustomer) {
this.payAPICustomer = payAPICustomer;
}

public List<Object> getMerchantAttributes() {
return merchantAttributes;
}

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
