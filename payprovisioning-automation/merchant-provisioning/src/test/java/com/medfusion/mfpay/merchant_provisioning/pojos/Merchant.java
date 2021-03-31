// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;
import com.medfusion.mfpay.merchant_provisioning.utils.ProvisioningUtils;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"id",
"primaryContactPhoneNumber",
"primaryContactFirstName",
"primaryContactLastName",
"customerAccountNumber",
"merchantLegalName",
"merchantName",
"phoneNumber",
"doingBusinessAs",
"externalMerchantId",
"alsoKnownAsName",
"maxTransactionLimit",
"primaryContactEmail",
"remitToAddress",
"contractedRates",
"acceptedCreditCards",
"accountDetails",
"merchantAddress",
"payAPICustomer",
})

public class Merchant {
	
	
	public static Map<String, Object> getMerchantMap(String merchantname, String doingbusinessas, String externalmerchantid,
		String customeraccountnumber, String merchantphonenumber,String transactionlimit,String primaryfirstname,
		String primarylastname,String primaryphonenumber,String primaryemail,String merchantaddress1,String merchantcity,
		String merchantstate, String merchantzip,String accountnumber, String routingnumber,String federaltaxid,
		String businessestablisheddate, String businesstype, String mcccode, String ownershiptype, String websiteurl,
		String amexpercent, String midqfeepercent,String midqupperfeepercent,String nqfeepercent, String nqupperfeepercent,
		String pertransactionauthfee,String pertransactionrefundfee,String qfeepercent,String qupperpercent){
		
		Map<String, Object> merchantdetails = new HashMap<String, Object>(); 
		merchantdetails.put("merchantName", merchantname+ProvisioningUtils.randomizeMerchantIdentifiers());
		merchantdetails.put("doingBusinessAs",doingbusinessas); 
		merchantdetails.put("externalMerchantId", Integer.parseInt(externalmerchantid));
		merchantdetails.put("customerAccountNumber",customeraccountnumber); 
		merchantdetails.put("phoneNumber", merchantphonenumber);
		merchantdetails.put("maxTransactionLimit",Integer.parseInt(transactionlimit)); 
		merchantdetails.put("primaryContactFirstName", primaryfirstname);
		merchantdetails.put("primaryContactLastName",primarylastname); 
		merchantdetails.put("primaryContactPhoneNumber",primaryphonenumber); 
		merchantdetails.put("primaryContactEmail", primaryemail);
		merchantdetails.put("merchantLegalName",doingbusinessas); 
		merchantdetails.put("alsoKnownAsName",doingbusinessas); 
		merchantdetails.put("merchantAddress",MerchantAddress.getMerchantAddressMap(merchantaddress1, merchantcity,
				merchantstate, merchantzip));
		merchantdetails.put("remitToAddress", RemitToAddress.getMerchantRemitAddressMap(merchantaddress1, merchantcity, 
				merchantstate, merchantzip));
		merchantdetails.put("accountDetails", AccountDetails.getMerchantAccountDetailsMap(accountnumber,routingnumber,federaltaxid,
				doingbusinessas,businessestablisheddate,businesstype,mcccode,ownershiptype,websiteurl));
		merchantdetails.put("contractedRates", ContractedRates.getContractedRatesMap(amexpercent, midqfeepercent, midqupperfeepercent,
			    nqfeepercent,nqupperfeepercent,pertransactionauthfee, pertransactionrefundfee, qfeepercent, qupperpercent));
		merchantdetails.put("acceptedCreditCards", Arrays.asList(PracticeConstants.CARD_ARRAY_LIST));
		merchantdetails.put("payAPICustomer", PayAPICustomer.getPayCustomerMap(0, PracticeConstants.PAY_CUSTOMER_DEFAULT));
		return merchantdetails;
		
	}
	
	

	@Override
	public String toString() {
		return "Merchant [remitToAddress=" + remitToAddress
				+ ", primaryContactPhoneNumber=" + primaryContactPhoneNumber
				+ ", contractedRates=" + contractedRates
				+ ", primaryContactFirstName=" + primaryContactFirstName
				+ ", primaryContactLastName=" + primaryContactLastName
				+ ", customerAccountNumber=" + customerAccountNumber
				+ ", merchantLegalName=" + merchantLegalName
				+ ", acceptedCreditCards=" + acceptedCreditCards
				+ ", merchantName=" + merchantName + ", phoneNumber="
				+ phoneNumber + ", doingBusinessAs=" + doingBusinessAs
				+ ", externalMerchantId=" + externalMerchantId
				+ ", alsoKnownAsName=" + alsoKnownAsName + ", accountDetails="
				+ accountDetails + ", merchantAddress=" + merchantAddress
				+ ", payAPICustomer=" + payAPICustomer
				+ ", maxTransactionLimit=" + maxTransactionLimit
				+ ", primaryContactEmail=" + primaryContactEmail
				+ ", additionalProperties=" + additionalProperties + "]";
	}


	@JsonProperty("id")
	private Long id;
	@JsonProperty("remitToAddress")
	private RemitToAddress remitToAddress;
	@JsonProperty("primaryContactPhoneNumber")
	private String primaryContactPhoneNumber;
	@JsonProperty("contractedRates")
	private ContractedRates contractedRates;
	@JsonProperty("primaryContactFirstName")
	private String primaryContactFirstName;
	@JsonProperty("primaryContactLastName")
	private String primaryContactLastName;
	@JsonProperty("customerAccountNumber")
	private String customerAccountNumber;
	@JsonProperty("merchantLegalName")
	private String merchantLegalName;
	@JsonProperty("acceptedCreditCards")
	private String[] acceptedCreditCards ;
	@JsonProperty("merchantName")
	private String merchantName;
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	@JsonProperty("doingBusinessAs")
	private String doingBusinessAs;
	@JsonProperty("externalMerchantId")
	private Integer externalMerchantId;
	@JsonProperty("alsoKnownAsName")
	private String alsoKnownAsName;
	@JsonProperty("accountDetails")
	private AccountDetails accountDetails;
	@JsonProperty("merchantAddress")
	private MerchantAddress merchantAddress;
	@JsonProperty("payAPICustomer")
	private PayAPICustomer payAPICustomer;
	@JsonProperty("maxTransactionLimit")
	private Integer maxTransactionLimit;
	@JsonProperty("primaryContactEmail")
	private String primaryContactEmail;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("remitToAddress")
	public RemitToAddress getRemitToAddress() {
	return remitToAddress;
	}

	@JsonProperty("remitToAddress")
	public void setRemitToAddress(RemitToAddress remitToAddress) {
	this.remitToAddress = remitToAddress;
	}

	@JsonProperty("primaryContactPhoneNumber")
	public String getPrimaryContactPhoneNumber() {
	return primaryContactPhoneNumber;
	}

	@JsonProperty("primaryContactPhoneNumber")
	public void setPrimaryContactPhoneNumber(String primaryContactPhoneNumber) {
	this.primaryContactPhoneNumber = primaryContactPhoneNumber;
	}
	
	@JsonProperty("id")
	public Long getid() {
	return id;
	}


	@JsonProperty("contractedRates")
	public ContractedRates getContractedRates() {
	return contractedRates;
	}

	@JsonProperty("contractedRates")
	public void setContractedRates(ContractedRates contractedRates) {
	this.contractedRates = contractedRates;
	}

	@JsonProperty("primaryContactFirstName")
	public String getPrimaryContactFirstName() {
	return primaryContactFirstName;
	}

	@JsonProperty("primaryContactFirstName")
	public void setPrimaryContactFirstName(String primaryContactFirstName) {
	this.primaryContactFirstName = primaryContactFirstName;
	}

	@JsonProperty("primaryContactLastName")
	public String getPrimaryContactLastName() {
	return primaryContactLastName;
	}

	@JsonProperty("primaryContactLastName")
	public void setPrimaryContactLastName(String primaryContactLastName) {
	this.primaryContactLastName = primaryContactLastName;
	}

	@JsonProperty("customerAccountNumber")
	public String getCustomerAccountNumber() {
	return customerAccountNumber;
	}

	@JsonProperty("customerAccountNumber")
	public void setCustomerAccountNumber(String customerAccountNumber) {
	this.customerAccountNumber = customerAccountNumber;
	}

	@JsonProperty("merchantLegalName")
	public String getMerchantLegalName() {
	return merchantLegalName;
	}

	@JsonProperty("merchantLegalName")
	public void setMerchantLegalName(String merchantLegalName) {
	this.merchantLegalName = merchantLegalName;
	}

	@JsonProperty("acceptedCreditCards")
	public String[] getAcceptedCreditCards() {
	return acceptedCreditCards;
	}

	@JsonProperty("acceptedCreditCards")
	public void setAcceptedCreditCards(String[] cardArrayList) {
	this.acceptedCreditCards = cardArrayList;
	}

	@JsonProperty("merchantName")
	public String getMerchantName() {
	return merchantName;
	}

	@JsonProperty("merchantName")
	public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
	}

	@JsonProperty("phoneNumber")
	public String getPhoneNumber() {
	return phoneNumber;
	}

	@JsonProperty("phoneNumber")
	public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
	}

	@JsonProperty("doingBusinessAs")
	public String getDoingBusinessAs() {
	return doingBusinessAs;
	}

	@JsonProperty("doingBusinessAs")
	public void setDoingBusinessAs(String doingBusinessAs) {
	this.doingBusinessAs = doingBusinessAs;
	}

	@JsonProperty("externalMerchantId")
	public Integer getExternalMerchantId() {
	return externalMerchantId;
	}

	@JsonProperty("externalMerchantId")
	public void setExternalMerchantId(Integer externalMerchantId) {
	this.externalMerchantId = externalMerchantId;
	}

	@JsonProperty("alsoKnownAsName")
	public String getAlsoKnownAsName() {
	return alsoKnownAsName;
	}

	@JsonProperty("alsoKnownAsName")
	public void setAlsoKnownAsName(String alsoKnownAsName) {
	this.alsoKnownAsName = alsoKnownAsName;
	}

	@JsonProperty("accountDetails")
	public AccountDetails getAccountDetails() {
	return accountDetails;
	}

	@JsonProperty("accountDetails")
	public void setAccountDetails(AccountDetails accountDetails) {
	this.accountDetails = accountDetails;
	}

	@JsonProperty("merchantAddress")
	public MerchantAddress getMerchantAddress() {
	return merchantAddress;
	}

	@JsonProperty("merchantAddress")
	public void setMerchantAddress(MerchantAddress merchantAddress) {
	this.merchantAddress = merchantAddress;
	}

	@JsonProperty("payAPICustomer")
	public PayAPICustomer getPayAPICustomer() {
	return payAPICustomer;
	}

	@JsonProperty("payAPICustomer")
	public void setPayAPICustomer(PayAPICustomer payAPICustomer) {
	this.payAPICustomer = payAPICustomer;
	}

	@JsonProperty("maxTransactionLimit")
	public Integer getMaxTransactionLimit() {
	return maxTransactionLimit;
	}

	@JsonProperty("maxTransactionLimit")
	public void setMaxTransactionLimit(Integer maxTransactionLimit) {
	this.maxTransactionLimit = maxTransactionLimit;
	}

	@JsonProperty("primaryContactEmail")
	public String getPrimaryContactEmail() {
	return primaryContactEmail;
	}

	@JsonProperty("primaryContactEmail")
	public void setPrimaryContactEmail(String primaryContactEmail) {
	this.primaryContactEmail = primaryContactEmail;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	
}
