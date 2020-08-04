package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * <!-- Copyright 2020 NXGN Management, LLC. All Rights Reserved. -->
 ************************/

public enum apiConfig {
	
	QAMainEnterpriseUsername("MFapiuser1"),
	QAMainEnterprisePassword("MFapiuser1!"),
	QAMainEnterpriseEmail("MFapiuser1@yopmail.com"),	
	SITEnterpriseUsername("AdminNew"),
	SITEnterprisePassword("AdminNew"),
	SITEnterpriseEmail("sitadminnew@yopmail.com");
	private String configProperty;

	apiConfig(String configProperty) {
        this.configProperty = configProperty;
    }

    public String getConfigProperty() {
        return this.configProperty;
    }

}
