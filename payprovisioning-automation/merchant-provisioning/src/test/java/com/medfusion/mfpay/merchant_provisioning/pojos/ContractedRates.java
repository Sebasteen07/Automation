// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"qualifiedFeePercent",
"midQualifiedFeePercent",
"midQualifiedUpperBoundaryPercent",
"perTransRefundFee",
"qualifiedUpperBoundaryPercent",
"perTransAuthFee",
"nonQualifiedUpperBoundaryPercent",
"amexPercent",
"nonQualifiedFeePercent"
})

public class ContractedRates {
	
	
	public static Map<String, Object> getContractedRatesMap(String amexpercent, String midqfeepercent,
			String midqupperfeepercent,String nqfeepercent, String nqupperfeepercent,String pertransactionauthfee,
			String pertransactionrefundfee,String qfeepercent,String qupperpercent){
		Map<String, Object> contractrates = new HashMap<String, Object>(); 
		contractrates.put("perTransAuthFee",Integer.parseInt(pertransactionauthfee));
		contractrates.put("perTransRefundFee",Integer.parseInt(pertransactionrefundfee)); 
		contractrates.put("qualifiedUpperBoundaryPercent", Double.parseDouble(qupperpercent));
		contractrates.put("qualifiedFeePercent",Double.parseDouble(qfeepercent)); 
		contractrates.put("midQualifiedUpperBoundaryPercent", Double.parseDouble(midqupperfeepercent));
		contractrates.put("midQualifiedFeePercent", Double.parseDouble(midqfeepercent));
		contractrates.put("nonQualifiedUpperBoundaryPercent",Double.parseDouble(nqupperfeepercent)); 
		contractrates.put("nonQualifiedFeePercent", Double.parseDouble(nqfeepercent));
		contractrates.put("amexPercent",Double.parseDouble(amexpercent)); 
		return contractrates;
		
	}
	
	@JsonProperty("qualifiedFeePercent")
	private Double qualifiedFeePercent;
	@JsonProperty("midQualifiedFeePercent")
	private Double midQualifiedFeePercent;
	@JsonProperty("midQualifiedUpperBoundaryPercent")
	private Double midQualifiedUpperBoundaryPercent;
	@JsonProperty("perTransRefundFee")
	private Integer perTransRefundFee;
	@JsonProperty("qualifiedUpperBoundaryPercent")
	private Double qualifiedUpperBoundaryPercent;
	@JsonProperty("perTransAuthFee")
	private Integer perTransAuthFee;
	@JsonProperty("nonQualifiedUpperBoundaryPercent")
	private Double nonQualifiedUpperBoundaryPercent;
	@JsonProperty("amexPercent")
	private Double amexPercent;
	@JsonProperty("nonQualifiedFeePercent")
	private Double nonQualifiedFeePercent;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("qualifiedFeePercent")
	public Double getQualifiedFeePercent() {
	return qualifiedFeePercent;
	}

	@JsonProperty("qualifiedFeePercent")
	public void setQualifiedFeePercent(Double qualifiedFeePercent) {
	this.qualifiedFeePercent = qualifiedFeePercent;
	}

	@JsonProperty("midQualifiedFeePercent")
	public Double getMidQualifiedFeePercent() {
	return midQualifiedFeePercent;
	}

	@JsonProperty("midQualifiedFeePercent")
	public void setMidQualifiedFeePercent(Double midQualifiedFeePercent) {
	this.midQualifiedFeePercent = midQualifiedFeePercent;
	}

	@JsonProperty("midQualifiedUpperBoundaryPercent")
	public Double getMidQualifiedUpperBoundaryPercent() {
	return midQualifiedUpperBoundaryPercent;
	}

	@JsonProperty("midQualifiedUpperBoundaryPercent")
	public void setMidQualifiedUpperBoundaryPercent(Double midQualifiedUpperBoundaryPercent) {
	this.midQualifiedUpperBoundaryPercent = midQualifiedUpperBoundaryPercent;
	}

	@JsonProperty("perTransRefundFee")
	public Integer getPerTransRefundFee() {
	return perTransRefundFee;
	}

	@JsonProperty("perTransRefundFee")
	public void setPerTransRefundFee(Integer perTransRefundFee) {
	this.perTransRefundFee = perTransRefundFee;
	}

	@JsonProperty("qualifiedUpperBoundaryPercent")
	public Double getQualifiedUpperBoundaryPercent() {
	return qualifiedUpperBoundaryPercent;
	}

	@JsonProperty("qualifiedUpperBoundaryPercent")
	public void setQualifiedUpperBoundaryPercent(Double qualifiedUpperBoundaryPercent) {
	this.qualifiedUpperBoundaryPercent = qualifiedUpperBoundaryPercent;
	}

	@JsonProperty("perTransAuthFee")
	public Integer getPerTransAuthFee() {
	return perTransAuthFee;
	}

	@JsonProperty("perTransAuthFee")
	public void setPerTransAuthFee(Integer perTransAuthFee) {
	this.perTransAuthFee = perTransAuthFee;
	}

	@JsonProperty("nonQualifiedUpperBoundaryPercent")
	public Double getNonQualifiedUpperBoundaryPercent() {
	return nonQualifiedUpperBoundaryPercent;
	}

	@JsonProperty("nonQualifiedUpperBoundaryPercent")
	public void setNonQualifiedUpperBoundaryPercent(Double nonQualifiedUpperBoundaryPercent) {
	this.nonQualifiedUpperBoundaryPercent = nonQualifiedUpperBoundaryPercent;
	}

	@JsonProperty("amexPercent")
	public Double getAmexPercent() {
	return amexPercent;
	}

	@JsonProperty("amexPercent")
	public void setAmexPercent(Double amexPercent) {
	this.amexPercent = amexPercent;
	}

	@JsonProperty("nonQualifiedFeePercent")
	public Double getNonQualifiedFeePercent() {
	return nonQualifiedFeePercent;
	}

	@JsonProperty("nonQualifiedFeePercent")
	public void setNonQualifiedFeePercent(Double nonQualifiedFeePercent) {
	this.nonQualifiedFeePercent = nonQualifiedFeePercent;
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
		return "ContractedRates [perTransAuthFee=" + perTransAuthFee
				+ ", perTransRefundFee=" + this.perTransRefundFee
				+ ", qualifiedUpperBoundaryPercent="
				+ this.qualifiedUpperBoundaryPercent + ", qualifiedFeePercent="
				+ this.qualifiedFeePercent + ", midQualifiedUpperBoundaryPercent="
				+ this.midQualifiedUpperBoundaryPercent
				+ ", midQualifiedFeePercent=" + this.midQualifiedFeePercent
				+ ", nonQualifiedUpperBoundaryPercent="
				+ this.nonQualifiedUpperBoundaryPercent
				+ ", nonQualifiedFeePercent=" + this.nonQualifiedFeePercent
				+ ", amexPercent=" + this.amexPercent + "]";
	}
	

}
