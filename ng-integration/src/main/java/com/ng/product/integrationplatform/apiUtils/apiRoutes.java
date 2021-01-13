// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public enum apiRoutes {
	
	BaseURL ("https://router-qa.dev.ngeapi.nextgenaws.net/gateway"),
	BaseCAGatewayURL ("https://cag-test.nextgenaws.net/nge/qa/nge-api/api/"),
	QAMainTokenGenerationURL("https://cag-test.nextgenaws.net/nge/qa/nge-oauth/token?site_id=14106bdf-325c-2eaa-56d0-5a0073a968b0&grant_type=client_credentials&client_id=l7d05fd74b907b4c06872db7d91797effd&client_secret=53db804aa22247cea3b24f06adb103dc"),
	SITTokenGenerationURL("https://cag-test.nextgenaws.net/nge/qa/nge-oauth/token?site_id=1e6cd73a-0922-2d9e-a607-b93cc592c3a0&grant_type=client_credentials&client_id=l7d05fd74b907b4c06872db7d91797effd&client_secret=53db804aa22247cea3b24f06adb103dc"),
	SelectEnterprisePractice("https://cag-test.nextgenaws.net/nge/qa/nge-api/api/users/me/login-defaults"),
	AddPerson("persons/"),
	AddEnterprisePerson("api/persons/"),
	AddChart("api/persons/personId/chart"),
	AddEncounter("api/persons/personId/chart/encounters"),
	PostEnrollment("api/persons/personId/chart/enrollment/begin"),
	GetEnrollmentStatus("api/persons/personId/chart/enrollment/status"),
	AddDiagnosis("api/persons/personId/chart/encounters/encounterId/diagnoses"),
	LogInDefaults("api/users/userId/login-defaults"),
	PrescribeMedication("api/persons/personId/chart/encounters/encounterId/medications"),
	AddAllergy("api/persons/personId/chart/encounters/encounterId/allergies"),
	AddProcedure("api/persons/personId/chart/encounters/encounterId/procedures"),
	AddProblem("api/persons/personId/chart/problems"),
	AddNewLabOrder("api/persons/personId/chart/encounters/encounterId/lab/orders"),
	AddNewImmunizationsOrder("api/persons/personId/chart/encounters/encounterId/immunizations/orders"),
	AddLabPanel("api/persons/personId/chart/lab/panels"),
	AddLabOrderTest("api/persons/personId/chart/lab/orders/orderId/tests"),
	AddObservationPanel("api/persons/personId/chart/lab/panels"),
	AddObservationResults("api/persons/personId/chart/lab/panels/panelId/results"),
	UpdateLabOrder("api/persons/personId/chart/lab/orders/orderId"),
	PostCCDARequest("api/ccda-requests"),
	CheckOutEncounter("api/persons/personId/chart/encounters/encounterId/checkout"),
	CheckInEncounter("api/persons/personId/chart/encounters/encounterId/checkin"),
	UpdateEncounter("api/persons/personId/chart/encounters/encounterId"),
	PostSecureMessage("api/secure-messages"),
	GetSecureMessage("api/secure-messages/secureMessageId"),
	PostAppointmentResponse("api/appointments/requests/appointmentRequestId/responses"),
	PostAppointment("api/appointments"),
	DeleteAppointment("api/appointments/appointmentId");
	
	private String routeURL;

    apiRoutes(String routeURL) {
        this.routeURL = routeURL;
    }

    public String getRouteURL() {
        return this.routeURL;
    }

}
