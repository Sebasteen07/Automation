package com.ng.product.integrationplatform.pojo;

/************************
 * 
 * @author Narora
 * <!-- Copyright 2020 NXGN Management, LLC. All Rights Reserved. -->
 ************************/

public class LoginDefaults {
	
	private String enterpriseId="00001";
	private String practiceId="0001";

	public String getEnterpriseId() {
	return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
	this.enterpriseId = enterpriseId;
	}

	public String getPracticeId() {
	return practiceId;
	}

	public void setPracticeId(String practiceId) {
	this.practiceId = practiceId;
	}

}