package com.intuit.ihg.product.integrationplatform.pojo;

public class AMDCInfo {
	private String restUrl = "";
	private String from = "";
	private String patientExternalId = "";

	public String getFrom() {
		return from;
	}

	public void setFrom(String fromExternal) {
		from = fromExternal;
	}

	public String getPatientExternalId() {
		return patientExternalId;
	}

	public void setPatientExternalId(String patientExternalIdExternal) {
		patientExternalId = patientExternalIdExternal;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrlExternal) {
		restUrl = restUrlExternal;
	}
}