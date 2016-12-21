package com.intuit.ihg.product.integrationplatform.pojo;

public class PIDCInfo {
	private String restUrl = "";
	private String practiceId = "";
	private String gender = "";
	
	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrlExternal) {
		restUrl = restUrlExternal;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String patientExternalId) {
		practiceId = patientExternalId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String genderExternal) {
		gender = genderExternal;
	}

}