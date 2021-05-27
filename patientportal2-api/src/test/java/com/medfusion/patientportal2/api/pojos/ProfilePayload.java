package com.medfusion.patientportal2.api.pojos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "authId", "email", "credentials", "challengeAnswer", "challengePhrase", "source",
		"externalProfile" })

public class ProfilePayload {
	private String authId;
	private String email;
	private String challengeAnswer;
	private String challengePhrase;
	private String source;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getChallengeAnswer() {
		return challengeAnswer;
	}

	public void setChallengeAnswer(String challengeAnswer) {
		this.challengeAnswer = challengeAnswer;
	}

	public String getChallengePhrase() {
		return challengePhrase;
	}

	public void setChallengePhrase(String challengePhrase) {
		this.challengePhrase = challengePhrase;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public static Map<String, Object> updateProfileMap(String authId, String email, String username, String password,
			String source) {
		Map<String, Object> profileMap = new HashMap<String, Object>();
		profileMap.put("authId", authId);
		profileMap.put("email", email);
		profileMap.put("credentials", Credentials.getCredentialsMap(username, password));
		profileMap.put("source", source);
		return profileMap;
	}

	public static Map<String, Object> createProfileMap(String email, String username, String password,
			String challengeAnswer, String challengePhrase, String source, String version) {
		Map<String, Object> profileMap = new HashMap<String, Object>();

		if (version.equals("v4")) {
			profileMap.put("email", email);
			profileMap.put("credentials", Credentials.getCredentialsMap(username, password));
			profileMap.put("source", source);
			return profileMap;
		} else {
			profileMap.put("email", email);
			profileMap.put("credentials", Credentials.getCredentialsMap(username, password));
			profileMap.put("challengeAnswer", challengeAnswer);
			profileMap.put("challengePhrase", challengePhrase);
			profileMap.put("source", source);
			return profileMap;
		}
	}
	
	public static Map<String, Object> createSecurityMap(String securityAnswer) {
		Map<String, Object> securityMap = new HashMap<String, Object>();
		securityMap.put("securityAnswer", securityAnswer);
		return securityMap;
	}
}
