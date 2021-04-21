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
		"accountNumber",
		"consumerName"
	})

public class MFGatewayConsumer {

	private String accountNumber;
	private String consumerName;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getAccountNumber() {
	return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public String getConsumerName() {
	return consumerName;
	}

	public void setConsumerName(String consumerName) {
	this.consumerName = consumerName;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}
	
	public static Map<String, Object> getMFGatewayConsumerMap(String accountnumber, String consumername){
		Map<String, Object> consumermap = new HashMap<String, Object>(); 
		consumermap.put("accountNumber", accountnumber);
		consumermap.put("consumerName", consumername);
		return consumermap;
	}	

}
