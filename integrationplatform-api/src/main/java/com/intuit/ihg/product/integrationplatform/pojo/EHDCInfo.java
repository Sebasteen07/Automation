package com.intuit.ihg.product.integrationplatform.pojo;

public class EHDCInfo {
	private String restUrl = "";
	private String from = "";
	private String integrationPracticeID = "";
	private String practicePatientId = "";
	private String ccdXMLPath = "";

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrlExternal) {
		restUrl = restUrlExternal;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String fromExternal) {
		from = fromExternal;
	}

	public String getIntegrationPracticeID() {
		return integrationPracticeID;
	}

	public void setIntegrationPracticeID(String integrationPracticeIDExternal) {
		integrationPracticeID = integrationPracticeIDExternal;
	}

	public String getCcdXMLPath() {
		return ccdXMLPath;
	}

	public void setCcdXMLPath(String ccdXMLPathExternal) {
		ccdXMLPath = ccdXMLPathExternal;
	}

	public String getPracticePatientId() {
		return practicePatientId;
	}

	public void setPracticePatientId(String practicePatientIdExternal) {
		practicePatientId = practicePatientIdExternal;
	}
}