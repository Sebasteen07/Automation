// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
	

	private Double qualifiedFeePercent;
	private Double midQualifiedFeePercent;
	private Double midQualifiedUpperBoundaryPercent;
	private Integer perTransRefundFee;
	private Double qualifiedUpperBoundaryPercent;
	private Integer perTransAuthFee;
	private Double nonQualifiedUpperBoundaryPercent;
	private Double amexPercent;

	private Double nonQualifiedFeePercent;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Double getQualifiedFeePercent() {
	return qualifiedFeePercent;
	}

	public void setQualifiedFeePercent(Double qualifiedFeePercent) {
	this.qualifiedFeePercent = qualifiedFeePercent;
	}

	public Double getMidQualifiedFeePercent() {
	return midQualifiedFeePercent;
	}

	public void setMidQualifiedFeePercent(Double midQualifiedFeePercent) {
	this.midQualifiedFeePercent = midQualifiedFeePercent;
	}

	public Double getMidQualifiedUpperBoundaryPercent() {
	return midQualifiedUpperBoundaryPercent;
	}

	public void setMidQualifiedUpperBoundaryPercent(Double midQualifiedUpperBoundaryPercent) {
	this.midQualifiedUpperBoundaryPercent = midQualifiedUpperBoundaryPercent;
	}

	public Integer getPerTransRefundFee() {
	return perTransRefundFee;
	}

	public void setPerTransRefundFee(Integer perTransRefundFee) {
	this.perTransRefundFee = perTransRefundFee;
	}

	public Double getQualifiedUpperBoundaryPercent() {
	return qualifiedUpperBoundaryPercent;
	}

	public void setQualifiedUpperBoundaryPercent(Double qualifiedUpperBoundaryPercent) {
	this.qualifiedUpperBoundaryPercent = qualifiedUpperBoundaryPercent;
	}

	public Integer getPerTransAuthFee() {
	return perTransAuthFee;
	}

	public void setPerTransAuthFee(Integer perTransAuthFee) {
	this.perTransAuthFee = perTransAuthFee;
	}

	public Double getNonQualifiedUpperBoundaryPercent() {
	return nonQualifiedUpperBoundaryPercent;
	}

	public void setNonQualifiedUpperBoundaryPercent(Double nonQualifiedUpperBoundaryPercent) {
	this.nonQualifiedUpperBoundaryPercent = nonQualifiedUpperBoundaryPercent;
	}

	public Double getAmexPercent() {
	return amexPercent;
	}

	public void setAmexPercent(Double amexPercent) {
	this.amexPercent = amexPercent;
	}

	public Double getNonQualifiedFeePercent() {
	return nonQualifiedFeePercent;
	}


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
