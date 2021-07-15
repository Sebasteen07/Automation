// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.patientportal2.api.pojos;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
		"username",
		"password"
	})

public class Credentials {
		private String username;
		private String password;
		@JsonIgnore
		private Map<String, Object> additionalProperties = new HashMap<String, Object>();

		public String getUsername() {
		return username;
		}

		public void setUsername(String username) {
		this.username = username;
		}

		public String getPassword() {
		return password;
		}

		public void setPassword(String password) {
		this.password = password;
		}

		@JsonAnyGetter
		public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
		}

		@JsonAnySetter
		public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		}

		public static Map<String, Object> getCredentialsMap(String username, String password){
			Map<String, Object> credentialsMap = new HashMap<String, Object>(); 
			credentialsMap.put("username", username);
			credentialsMap.put("password", password);
			return credentialsMap;
		}
}
