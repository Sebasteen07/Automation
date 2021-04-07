// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"billingDescriptor",
"mccCode",
"ownershipType",
"businessEstablishedDate",
"websiteURL",
"chainCode",
"beneficialOwners",
"businessType"
})

public class WorldPayAccountDetails {
	
	public static final String BACKING_MERCHANT = null;

	public static Map<String, Object> getWPAccountDetailsMap(String doingbusinessas, String businessestablisheddate,
			String businesstype, String mcccode, String ownershiptype, String websiteurl){
		
		Map<String, Object> wpdetails = new HashMap<String, Object>(); 
		wpdetails.put("chainCode", PracticeConstants.CHAIN_CODE);
		wpdetails.put("billingDescriptor","PFA*"+doingbusinessas); 
		wpdetails.put("businessEstablishedDate", businessestablisheddate);
		wpdetails.put("businessType",businesstype); 
		wpdetails.put("mccCode", mcccode);
		wpdetails.put("ownershipType", ownershiptype);
		wpdetails.put("websiteURL",websiteurl); 
		wpdetails.put("beneficialOwners", Arrays.asList());
		
		return wpdetails;
		
	}
	
	@Override
	public String toString() {
		return "WorldPayAccountDetails [chainCode=" + this.chainCode
				+ ", billingDescriptor=" + this.billingDescriptor
				+ ", businessEstablishedDate=" + this.businessEstablishedDate
				+ ", businessType=" + this.businessType + ", ownershipType="
				+ this.ownershipType + ", websiteURL=" + this.websiteURL + ", mccCode="
				+ this.mccCode + "]";
	}

	private String billingDescriptor;
	private String mccCode;
	private String ownershipType;
	private String businessEstablishedDate;
	private String websiteURL;
	private String chainCode;
	private List<Object> beneficialOwners;
	private String businessType;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getBillingDescriptor() {
	return billingDescriptor;
	}

	public void setBillingDescriptor(String billingDescriptor) {
	this.billingDescriptor = billingDescriptor;
	}

	public String getMccCode() {
	return mccCode;
	}

	public void setMccCode(String mccCode) {
	this.mccCode = mccCode;
	}

	public String getOwnershipType() {
	return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
	this.ownershipType = ownershipType;
	}

	public String getBusinessEstablishedDate() {
	return businessEstablishedDate;
	}

	public void setBusinessEstablishedDate(String businessEstablishedDate) {
	this.businessEstablishedDate = businessEstablishedDate;
	}

	public String getWebsiteURL() {
	return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
	this.websiteURL = websiteURL;
	}

	public String getChainCode() {
	return chainCode;
	}

	public void setChainCode(String chainCode) {
	this.chainCode = chainCode;
	}

	public List<Object> getBeneficialOwners() {
	return beneficialOwners;
	}

	public void setBeneficialOwners(List<Object> beneficialOwners) {
	this.beneficialOwners = beneficialOwners;
	}

	public String getBusinessType() {
	return businessType;
	}

	public void setBusinessType(String businessType) {
	this.businessType = businessType;
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
