// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
		"name",
		"zipCode",
		"lastName",
		"addressLine1",
		"city",
		"state",
		"country",
		"email",
		"phone"
	})

public class BillToAddress {
	
	private String name;
	private String zipCode;
	private String lastName;
	private String addressLine1;
	private String city;
	private String state;
	private String country;
	private String email;
	private String phone;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	public String getZipCode() {
	return zipCode;
	}

	public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
	}

	public String getLastName() {
	return lastName;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public String getAddressLine1() {
	return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
	}

	public String getCity() {
	return city;
	}

	public void setCity(String city) {
	this.city = city;
	}

	public String getState() {
	return state;
	}

	public void setState(String state) {
	this.state = state;
	}
	
	public String getCountry() {
	return country;
	}

	public void setCountry(String country) {
	this.country = country;
	}

	public String getEmail() {
	return email;
	}

	public void setEmail(String email) {
	this.email = email;
	}

	
	public String getPhone() {
	return phone;
	}

	public void setPhone(String phone) {
	this.phone = phone;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	
	public static Map<String, Object> getBillToAddressMap(String zipcode, String lastname, String addressline1, String city, String state,String firstname){
		Map<String, Object> addressmap = new HashMap<String, Object>(); 
		addressmap.put("zipCode", zipcode);
		addressmap.put("lastName", lastname);
		addressmap.put("addressLine1", addressline1);
		addressmap.put("city", city);
		addressmap.put("state", state);
		addressmap.put("firstName", firstname);
		return addressmap;
	}

	public static Map<String, Object> getBillingAdressMap(String zipCode){
		Map<String, Object> billingAdressMap = new HashMap<String, Object>();
		billingAdressMap.put("zipCode", zipCode);
		return billingAdressMap;
	}

}
