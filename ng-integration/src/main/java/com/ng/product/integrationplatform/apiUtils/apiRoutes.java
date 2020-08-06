// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public enum apiRoutes {
	
	BaseURL ("https://api.nextgen.com/AWSVPQADB02.consolidated_testing/NextGen.Api.Edge/5.9.5.58/"),
	BaseSITURL ("https://api.nextgen.com/AWSVPQADB02.ngqa_595_MFSIT/NextGen.Api.Edge/5.9.5.35/"),
//	BaseCAGatewayURL ("https://nativeapi.nextgen.com/nga-native-api/"),
	BaseCAGatewayURL ("https://nativeapi.nextgen.com/nge/prod/nge-api/api/"),
	QAMainTokenGenerationURL("https://nativeapi.nextgen.com/nge/prod/nge-oauth/token?site_id=b5204a09-f145-3a59-a2ac-a3fbeb329257&grant_type=client_credentials&client_id=l7d05fd74b907b4c06872db7d91797effd&client_secret=53db804aa22247cea3b24f06adb103dc"),
	SITTokenGenerationURL("https://nativeapi.nextgen.com/nge/prod/nge-oauth/token?site_id=9a094575-1969-52a1-f95e-3937021359b5&grant_type=client_credentials&client_id=l7d05fd74b907b4c06872db7d91797effd&client_secret=53db804aa22247cea3b24f06adb103dc"),
	SelectEnterprisePractice("https://nativeapi.nextgen.com/nge/prod/nge-api/api/users/me/login-defaults"),
	AddPerson("persons/"),
	AddEnterprisePerson("api/persons/"),
	AddChart("api/persons/personId/chart"),
	AddEncounter("api/persons/personId/chart/encounters"),
	PostEnrollment("api/persons/personId/chart/enrollment/begin"),
	GetEnrollmentStatus("api/persons/personId/chart/enrollment/status"),
	AddDiagnosis("api/persons/personId/chart/encounters/encounterId/diagnoses");
	
	private String routeURL;

    apiRoutes(String routeURL) {
        this.routeURL = routeURL;
    }

    public String getRouteURL() {
        return this.routeURL;
    }

}
