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
"zip",
"country",
"city",
"address1",
"state"
})

public class RemitToAddress {
	
	public static Map<String, Object> getMerchantRemitAddressMap(String merchantaddress1, String merchantcity, String merchantstate, String merchantzip){
		Map<String, Object> remitaddress = new HashMap<String, Object>(); 
		remitaddress.put("address1", merchantaddress1);
		remitaddress.put("city",merchantcity); 
		remitaddress.put("state", merchantstate);
		remitaddress.put("zip",merchantzip); 
		remitaddress.put("country", "US");
		return remitaddress;
		
	}
	
	
	@Override
	public String toString() {
		return "RemitToAddress [address1=" + this.address1 + ", city=" + this.city
				+ ", zip=" + this.zip + ", country=" + this.country + ", state=" + this.state
				+ "]";
	}


	private String zip;
	private String country;
	private String city;
	private String address1;
	private String state;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getZip() {
	return zip;
	}
	
	public void setZip(String zip) {
	this.zip = zip;
	}

	public String getCountry() {
	return country;
	}

	public void setCountry(String country) {
	this.country = country;
	}

	public String getCity() {
	return city;
	}

	public void setCity(String city) {
	this.city = city;
	}

	public String getAddress1() {
	return address1;
	}

	public void setAddress1(String address1) {
	this.address1 = address1;
	}

	public String getState() {
	return state;
	}

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
