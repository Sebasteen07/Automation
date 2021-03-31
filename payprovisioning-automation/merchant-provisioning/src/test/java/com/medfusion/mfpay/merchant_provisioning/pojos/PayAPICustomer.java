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
"id",
"customerName"
})
public class PayAPICustomer {
	
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("customerName")
	private String customerName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("id")
	public Integer getId() {
	return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
	this.id = id;
	}

	@JsonProperty("customerName")
	public String getCustomerName() {
	return customerName;
	}

	@JsonProperty("customerName")
	public void setCustomerName(String customerName) {
	this.customerName = customerName;
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
		return "PayCustomer [id=" + this.id + ", customerName=" + this.customerName + "]";
	}

	
	public static Map<String, Object> getPayCustomerMap(int i, String customerName){
		Map<String, Object> paycustomermap = new HashMap<String, Object>(); 
		paycustomermap.put("id",i);
		paycustomermap.put("customerName",customerName); 
		return paycustomermap;
		
	}

}
