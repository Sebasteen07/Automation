//Copyright 2018-2020 NXGN Management, LLC. All Rights Reserved.
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


	private String practiceId;

	public AdminUser() {}

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