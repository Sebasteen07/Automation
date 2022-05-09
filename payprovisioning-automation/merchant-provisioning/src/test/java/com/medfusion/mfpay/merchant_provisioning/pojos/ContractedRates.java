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
@JsonPropertyOrder({ "qualifiedFeePercent", "midQualifiedFeePercent", "midQualifiedUpperBoundaryPercent",
		"perTransRefundFee", "qualifiedUpperBoundaryPercent", "perTransAuthFee", "nonQualifiedUpperBoundaryPercent",
		"amexPercent", "nonQualifiedFeePercent", "feeSettlementType" })

public class ContractedRates {

	public static Map<String, Object> getContractedRatesMap(String feeSettlementType, String amexpercent, String midqfeepercent,
			String midqupperfeepercent, String nqfeepercent, String nqupperfeepercent, String pertransactionauthfee,
			String pertransactionrefundfee, String qfeepercent, String qupperpercent) {
		Map<String, Object> contractrates = new HashMap<String, Object>();
		contractrates.put("feeSettlementType", feeSettlementType);
		contractrates.put("perTransAuthFee", Integer.parseInt(pertransactionauthfee));
		contractrates.put("perTransRefundFee", Integer.parseInt(pertransactionrefundfee));
		contractrates.put("qualifiedUpperBoundaryPercent", Double.parseDouble(qupperpercent));
		contractrates.put("qualifiedFeePercent", Double.parseDouble(qfeepercent));
		contractrates.put("midQualifiedUpperBoundaryPercent", Double.parseDouble(midqupperfeepercent));
		contractrates.put("midQualifiedFeePercent", Double.parseDouble(midqfeepercent));
		contractrates.put("nonQualifiedUpperBoundaryPercent", Double.parseDouble(nqupperfeepercent));
		contractrates.put("nonQualifiedFeePercent", Double.parseDouble(nqfeepercent));
		contractrates.put("amexPercent", Double.parseDouble(amexpercent));
		return contractrates;

	}

	public static Map<String, Object> getUpdatedContractedRatesMap(Double midqfeepercent, Double nqfeepercent,
			Double pertransactionauthfee, Double qfeepercent) {
		Map<String, Object> contractrates = new HashMap<String, Object>();
		contractrates.put("perTransAuthFee", pertransactionauthfee);
		contractrates.put("qualifiedFeePercent", qfeepercent);
		contractrates.put("midQualifiedFeePercent", midqfeepercent);
		contractrates.put("nonQualifiedFeePercent", nqfeepercent);
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
	private String feeType;

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

	public String putFeeSettlementType() {
		return feeType;
	}

	public void setFeeSettlement(String feeType) {
		this.feeType = feeType;
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
		return "ContractedRates [perTransAuthFee=" + perTransAuthFee + ", perTransRefundFee=" + this.perTransRefundFee
				+ ", qualifiedUpperBoundaryPercent=" + this.qualifiedUpperBoundaryPercent + ", qualifiedFeePercent="
				+ this.qualifiedFeePercent + ", midQualifiedUpperBoundaryPercent="
				+ this.midQualifiedUpperBoundaryPercent + ", midQualifiedFeePercent=" + this.midQualifiedFeePercent
				+ ", nonQualifiedUpperBoundaryPercent=" + this.nonQualifiedUpperBoundaryPercent
				+ ", nonQualifiedFeePercent=" + this.nonQualifiedFeePercent + ", amexPercent=" + this.amexPercent + "]";
	}

	public static Map<String, Object> updateMerchantfeesMap(String amexpercent, String feeSettlementType,
			String midQualifiedFeePercent, String midQualifiedUpperBoundaryPercent, String nonQualifiedFeePercent,
			String nonQualifiedUpperBoundaryPercent, String perTransAuthFee, String perTransRefundFee,
			String qualifiedFeePercent, String qualifiedUpperBoundaryPercent, String suppressFeeSettlement) {

		Map<String, Object> merchantrates = new HashMap<String, Object>();
		merchantrates.put("amexPercent", Double.parseDouble(amexpercent));
		merchantrates.put("feeSettlementType", feeSettlementType);
		merchantrates.put("perTransAuthFee", Integer.parseInt(perTransAuthFee));
		merchantrates.put("perTransRefundFee", Integer.parseInt(perTransRefundFee));
		merchantrates.put("qualifiedUpperBoundaryPercent", qualifiedUpperBoundaryPercent);
		merchantrates.put("qualifiedFeePercent", Double.parseDouble(qualifiedFeePercent));
		merchantrates.put("midQualifiedUpperBoundaryPercent", Double.parseDouble(midQualifiedUpperBoundaryPercent));
		merchantrates.put("midQualifiedFeePercent", Double.parseDouble(midQualifiedFeePercent));
		merchantrates.put("nonQualifiedUpperBoundaryPercent", Double.parseDouble(nonQualifiedUpperBoundaryPercent));
		merchantrates.put("nonQualifiedFeePercent", Double.parseDouble(nonQualifiedFeePercent));
		merchantrates.put("suppressFeeSettlement", suppressFeeSettlement);
		return merchantrates;

	}

}
