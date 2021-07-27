// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.apiUtils;

public enum pamAPIRoutes {
	
	redirectURL("https://nextgen.layer7.saas.broadcom.com/admin/oauthCallback"),
	AuthURL("https://cag-test.nextgenaws.net/nge/qa/nge-oauth/authorize"),
	AuthToken("https://cag-test.nextgenaws.net/nge/qa/nge-oauth/token"),	
	clientID("l7d05fd74b907b4c06872db7d91797effd"),
	clientSecret("53db804aa22247cea3b24f06adb103dc"),
	PAMPatient("https://cag-test.nextgenaws.net/nge/qa/fhir-api/fhir/R4/patient/me");
	
	private String routeURL;

	pamAPIRoutes(String routeURL) {
        this.routeURL = routeURL;
    }

    public String getRouteURL() {
        return this.routeURL;
    }

}