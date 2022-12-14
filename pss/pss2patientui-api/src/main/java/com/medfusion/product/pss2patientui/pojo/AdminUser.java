// Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.pss2patientui.pojo;

public class AdminUser {
	private String user;
	private String password;
	private String adminUrl;
	private String rule;
	private Boolean isExisting;
	private Boolean isInsuranceDisplayed;
	private Boolean isIPDFLow;
	private Boolean isLoginlessFlow;
	private Boolean isAnonymousFlow;
	private Boolean isstartpointPresent;
	private Boolean enableProvdiderPresent= false;
	private Boolean lastQuestionMandatory=false;	

	public Boolean getIsstartpointPresent() {
		return isstartpointPresent;
	}

	public void setIsstartpointPresent(Boolean isstartpointPresent) {
		this.isstartpointPresent = isstartpointPresent;
	}

	private String practiceId;

	public AdminUser() {}

	public Boolean getEnableProvdiderPresent() {
		return enableProvdiderPresent;
	}

	public Boolean getLastQuestionMandatory() {
		return lastQuestionMandatory;
	}

	public void setLastQuestionMandatory(Boolean lastQuestionMandatory) {
		this.lastQuestionMandatory = lastQuestionMandatory;
	}

	public void setEnableProvdiderPresent(Boolean enableProvdiderPresent) {
		this.enableProvdiderPresent = enableProvdiderPresent;
	}

	public Boolean getIsAnonymousFlow() {
		return isAnonymousFlow;
	}

	public void setIsAnonymousFlow(Boolean isAnonymousFlow) {
		this.isAnonymousFlow = isAnonymousFlow;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdminUrl() {
		return adminUrl;
	}

	public void setAdminUrl(String adminUrl) {
		this.adminUrl = adminUrl;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Boolean getIsInsuranceDisplayed() {
		return isInsuranceDisplayed;
	}

	public void setIsInsuranceDisplayed(Boolean isInsuranceDisplayed) {
		this.isInsuranceDisplayed = isInsuranceDisplayed;
	}

	public Boolean getIsExisting() {
		return isExisting;
	}

	public void setIsExisting(Boolean isExisting) {
		this.isExisting = isExisting;
	}

	public Boolean getIsIPDFLow() {
		return isIPDFLow;
	}

	public void setIsIPDFLow(Boolean isIPDFLow) {
		this.isIPDFLow = isIPDFLow;
	}

	public Boolean getIsLoginlessFlow() {
		return isLoginlessFlow;
	}

	public void setIsLoginlessFlow(Boolean isLoginlessFlow) {
		this.isLoginlessFlow = isLoginlessFlow;
	}

	public String getPracticeId() {
		return practiceId;
	}

	public void setPracticeId(String practiceId) {
		this.practiceId = practiceId;
	}
}
