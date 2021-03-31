// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.pojos;

import java.util.HashMap;
import java.util.Map;import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
		"cvvNumber",
		"type",
		"accountNumber",
		"expirationDate",
		"bin",
		"cardPresent"
	})
	
public class Card {


		private String cvvNumber;
		private String type;
		private String accountNumber;
		private String expirationDate;
		private String bin;
		private Boolean cardPresent;
		
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();


		public String getCvvNumber() {
			return cvvNumber;
		}

		public void setCvvNumber(String cvvNumber) {
			this.cvvNumber = cvvNumber;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public String getExpirationDate() {
			return expirationDate;
		}

		public void setExpirationDate(String expirationDate) {
			this.expirationDate = expirationDate;
		}

		public String getBin() {
			return bin;
		}

		public void setBin(String bin) {
			this.bin = bin;
		}

		public Boolean getCardPresent() {
			return cardPresent;
		}

		public void setCardPresent(Boolean cardPresent) {
			this.cardPresent = cardPresent;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
			return this.additionalProperties;
		}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
	

	public static Map<String, Object> getCardMap(String cvv, String type, String cardnumber, String expirationnumber, String bin){
		Map<String, Object> cardmap = new HashMap<String, Object>(); 
		cardmap.put("cvvNumber", cvv);
		cardmap.put("type", type);
		cardmap.put("accountNumber", cardnumber);
		cardmap.put("expirationDate", expirationnumber);
		cardmap.put("bin", bin);
		cardmap.put("cardPresent", false);
		return cardmap;
	}
	
	
	
	

}
