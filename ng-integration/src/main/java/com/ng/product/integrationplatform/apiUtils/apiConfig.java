// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public enum apiConfig {
		
	QAMainEnterpriseUsername("MedIntAdmin"),
	QAMainEnterprisePassword("MedIntAdmin!1"),
	QAMainEnterpriseEmail("mfagent@yopmail.com"),
	SITEnterpriseUsername("MFNGapiuser5"),
	SITEnterprisePassword("MFNGapiuser5!"),
	SITEnterpriseEmail("MFNGapiuser5sit@yopmail.com");
	private String configProperty;

	apiConfig(String configProperty) {
        this.configProperty = configProperty;
    }

    public String getConfigProperty() {
        return this.configProperty;
    }

}
