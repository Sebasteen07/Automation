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
		"paymentSource"
	})

public class MFGatewayMerchant {
	
	private String paymentSource;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getPaymentSource() {
	return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
	this.paymentSource = paymentSource;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
	return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
	this.additionalProperties.put(name, value);
	}

	public static Map<String, Object> getMFGatewayMerchantMap(String paymentsource){
		Map<String, Object> merchantmap = new HashMap<String, Object>(); 
		merchantmap.put("paymentSource", paymentsource);
		return merchantmap;
	}

}
