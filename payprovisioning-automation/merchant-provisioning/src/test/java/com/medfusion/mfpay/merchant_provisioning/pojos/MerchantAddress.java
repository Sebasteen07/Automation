// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.merchant_provisioning.pojos;

import java.util.HashMap;
import java.util.Map;
import com.medfusion.mfpay.merchant_provisioning.utils.PracticeConstants;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"zip",
"country",
"city",
"address1",
"state"
})

public class MerchantAddress {
	
	@Override
	public String toString() {
		return "MerchantAddress [address1=" + this.address1 + ", city=" + this.city
				+ ", zip=" + this.zip + ", country=" + this.country + ", state=" + this.state
				+ "]";
	}

	
	public static Map<String, Object> getMerchantAddressMap(String merchantaddress1, String merchantcity, String merchantstate, String merchantzip){
		Map<String, Object> merchantaddress = new HashMap<String, Object>(); 
		merchantaddress.put("address1", merchantaddress1);
		merchantaddress.put("city",merchantcity); 
		merchantaddress.put("state", merchantstate);
		merchantaddress.put("zip",merchantzip); 
		merchantaddress.put("country", PracticeConstants.COUNTRY);
		return merchantaddress;
		
	}
	
	
	@JsonProperty("zip")
	private String zip;
	@JsonProperty("country")
	private String country;
	@JsonProperty("city")
	private String city;
	@JsonProperty("address1")
	private String address1;
	@JsonProperty("state")
	private String state;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("zip")
	public String getZip() {
	return zip;
	}

	@JsonProperty("zip")
	public void setZip(String zip) {
	this.zip = zip;
	}

	@JsonProperty("country")
	public String getCountry() {
	return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
	this.country = country;
	}

	@JsonProperty("city")
	public String getCity() {
	return city;
	}

	@JsonProperty("city")
	public void setCity(String city) {
	this.city = city;
	}

	@JsonProperty("address1")
	public String getAddress1() {
	return address1;
	}

	@JsonProperty("address1")
	public void setAddress1(String address1) {
	this.address1 = address1;
	}

	@JsonProperty("state")
	public String getState() {
	return state;
	}

	@JsonProperty("state")
	public void setState(String state) {
	this.state = state;
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
