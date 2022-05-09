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
"payAPICustomer"
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

	public static Map<String, Object> createMerchantAccMap(String merchantname, String doingbusinessas, String externalmerchantid,
			String customeraccountnumber, String merchantphonenumber,String transactionlimit,String primaryfirstname,
			String primarylastname,String primaryphonenumber,String primaryemail,String merchantaddress1,String merchantcity,
			String merchantstate, String merchantzip,String accountnumber, String routingnumber, String separatefundingacc, String federaltaxid,
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
			merchantdetails.put("feeAccDetails", AccountDetails.getFeeAccountDetailsMap(accountnumber, routingnumber));
			merchantdetails.put("separateFundingAccounts",separatefundingacc);
			merchantdetails.put("contractedRates", ContractedRates.getContractedRatesMap(amexpercent, midqfeepercent, midqupperfeepercent,
				    nqfeepercent,nqupperfeepercent,pertransactionauthfee, pertransactionrefundfee, qfeepercent, qupperpercent));
			merchantdetails.put("acceptedCreditCards", Arrays.asList(PracticeConstants.CARD_ARRAY_LIST));
			merchantdetails.put("payAPICustomer", PayAPICustomer.getPayCustomerMap(0, PracticeConstants.PAY_CUSTOMER_DEFAULT));
			return merchantdetails;
			
		}
	
	
	public static Map<String, Object> createInstamedMerchantAccMap(String merchantname, String externalmerchantid,
			String customerAccountNumber, Double midQfeePercent,Double nonQFeePercent,
			Double authFee,Double qualifiedFeePercent,String preferredProcessor,String merchantId,String storeId ,String virtualVisit,String patientPortal,String preCheck){
			Map<String, Object> merchantdetails = new HashMap<String, Object>(); 
			merchantdetails.put("merchantName",merchantname);
			merchantdetails.put("externalMerchantId", Integer.parseInt(externalmerchantid));
			merchantdetails.put("customerAccountNumber",customerAccountNumber); 
			merchantdetails.put("acceptedCreditCards", Arrays.asList(PracticeConstants.CARD_ARRAY_LIST));			
			merchantdetails.put("accountDetails", InstamedAccountDetails.getAccountDetailsMap(preferredProcessor, merchantId, storeId , virtualVisit, patientPortal, preCheck));
			merchantdetails.put("contractedRates", ContractedRates.getUpdatedContractedRatesMap(midQfeePercent,
					nonQFeePercent,authFee, qualifiedFeePercent));
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



	private Long id;
	private RemitToAddress remitToAddress;
	private String primaryContactPhoneNumber;
	private ContractedRates contractedRates;
	private String primaryContactFirstName;
	private String primaryContactLastName;
	private String customerAccountNumber;
	private String merchantLegalName;
	private String[] acceptedCreditCards ;
	private String merchantName;
	private String phoneNumber;
	private String doingBusinessAs;
	private Integer externalMerchantId;
	private String alsoKnownAsName;
	private AccountDetails accountDetails;
	private MerchantAddress merchantAddress;
	private PayAPICustomer payAPICustomer;
	private Integer maxTransactionLimit;
	private String primaryContactEmail;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public RemitToAddress getRemitToAddress() {
	return remitToAddress;
	}

	public void setRemitToAddress(RemitToAddress remitToAddress) {
	this.remitToAddress = remitToAddress;
	}

	public String getPrimaryContactPhoneNumber() {
	return primaryContactPhoneNumber;
	}

	public void setPrimaryContactPhoneNumber(String primaryContactPhoneNumber) {
	this.primaryContactPhoneNumber = primaryContactPhoneNumber;
	}
	
	public Long getid() {
	return id;
	}

	public ContractedRates getContractedRates() {
	return contractedRates;
	}

	public void setContractedRates(ContractedRates contractedRates) {
	this.contractedRates = contractedRates;
	}

	public String getPrimaryContactFirstName() {
	return primaryContactFirstName;
	}

	public void setPrimaryContactFirstName(String primaryContactFirstName) {
	this.primaryContactFirstName = primaryContactFirstName;
	}

	public String getPrimaryContactLastName() {
	return primaryContactLastName;
	}

	public void setPrimaryContactLastName(String primaryContactLastName) {
	this.primaryContactLastName = primaryContactLastName;
	}

	public String getCustomerAccountNumber() {
	return customerAccountNumber;
	}

	public void setCustomerAccountNumber(String customerAccountNumber) {
	this.customerAccountNumber = customerAccountNumber;
	}

	public String getMerchantLegalName() {
	return merchantLegalName;
	}

	public void setMerchantLegalName(String merchantLegalName) {
	this.merchantLegalName = merchantLegalName;
	}

	public String[] getAcceptedCreditCards() {
	return acceptedCreditCards;
	}

	public void setAcceptedCreditCards(String[] cardArrayList) {
	this.acceptedCreditCards = cardArrayList;
	}

	public String getMerchantName() {
	return merchantName;
	}

	public void setMerchantName(String merchantName) {
	this.merchantName = merchantName;
	}

	public String getPhoneNumber() {
	return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
	}

	public String getDoingBusinessAs() {
	return doingBusinessAs;
	}

	public void setDoingBusinessAs(String doingBusinessAs) {
	this.doingBusinessAs = doingBusinessAs;
	}

	public Integer getExternalMerchantId() {
	return externalMerchantId;
	}

	public void setExternalMerchantId(Integer externalMerchantId) {
	this.externalMerchantId = externalMerchantId;
	}

	public String getAlsoKnownAsName() {
	return alsoKnownAsName;
	}

	public void setAlsoKnownAsName(String alsoKnownAsName) {
	this.alsoKnownAsName = alsoKnownAsName;
	}

	public AccountDetails getAccountDetails() {
	return accountDetails;
	}

	public void setAccountDetails(AccountDetails accountDetails) {
	this.accountDetails = accountDetails;
	}

	public MerchantAddress getMerchantAddress() {
	return merchantAddress;
	}
	
	public void setMerchantAddress(MerchantAddress merchantAddress) {
	this.merchantAddress = merchantAddress;
	}

	public PayAPICustomer getPayAPICustomer() {
	return payAPICustomer;
	}

	public void setPayAPICustomer(PayAPICustomer payAPICustomer) {
	this.payAPICustomer = payAPICustomer;
	}

	public Integer getMaxTransactionLimit() {
	return maxTransactionLimit;
	}

	public void setMaxTransactionLimit(Integer maxTransactionLimit) {
	this.maxTransactionLimit = maxTransactionLimit;
	}

	public String getPrimaryContactEmail() {
	return primaryContactEmail;
	}

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
