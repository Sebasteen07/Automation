// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public enum apiConfig {
		
	QAMainEnterpriseUsername("Apiuser5"),
	QAMainEnterprisePassword("Apiuser5!"),
	QAMainEnterpriseEmail("apiuserphoe05142020@yopmail.com"),
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
