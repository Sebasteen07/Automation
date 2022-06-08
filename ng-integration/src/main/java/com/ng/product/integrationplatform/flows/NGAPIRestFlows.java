//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.flows;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.ng.product.integrationplatform.apiUtils.NGAPIRestUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.AcknowledgedProblem;
import com.ng.product.integrationplatform.pojo.ObsPanel;
import com.ng.product.integrationplatform.pojo.Problem;
import com.ng.product.integrationplatform.pojo.Allergy;
import com.ng.product.integrationplatform.pojo.ChartPojo;
import com.ng.product.integrationplatform.pojo.Diagnosis;
import com.ng.product.integrationplatform.pojo.EncounterPojo;
import com.ng.product.integrationplatform.pojo.Immunization;
import com.ng.product.integrationplatform.pojo.Interaction;
import com.ng.product.integrationplatform.pojo.LabOrder;
import com.ng.product.integrationplatform.pojo.LabOrderTest;
import com.ng.product.integrationplatform.pojo.LabResult;
import com.ng.product.integrationplatform.pojo.Medication;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.pojo.Procedure;
import com.ng.product.integrationplatform.pojo.UpdateLabOrder;
import com.ng.product.integrationplatform.utils.DBUtils;

public class NGAPIRestFlows {

	NGAPIRestUtils ngAPIRestUtils;
	private static String enterprisebaseURL;
	private static String dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	private static String strSqlQueryForProvider = "select provider_id from provider_mstr where description='ProviderName'";
	private static String strSqlQueryForLocation = "select location_id from location_mstr where location_name='locationName'";
	private static String strSqlQueryForRenderingProvider = "select provider_id from provider_mstr where description='ProviderName'";

	public NGAPIRestFlows(PropertyFileLoader propertyLoaderObj) throws Throwable {
		ngAPIRestUtils = new NGAPIRestUtils(propertyLoaderObj);
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterprisebaseURL = NGAPIRestUtils.getRelativeBaseUrl();
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterprisebaseURL = NGAPIRestUtils.getRelativeBaseUrl();
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
	}

	public static String createPatientinEPM(NewPatient argPayload, String locationName, String providerName)
			throws Throwable {
		String personId = "";
		String chartId = "";
		try {
			String patientRequestbody = new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(argPayload);
			Log4jUtil.log("Patient Request Body is \n" + patientRequestbody);

			String personURL = apiRoutes.valueOf("AddEnterprisePerson").getRouteURL();
			String finalURL = enterprisebaseURL + personURL;
			personId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, patientRequestbody, 201);
			Log4jUtil.log("Person created with id " + personId);

			String strSqlQueryForProvider = "select provider_id from provider_mstr where description='" + providerName
					+ "'";
			String strSqlQueryForLocation = "select location_id from location_mstr where location_name='" + locationName
					+ "'";

			ChartPojo chart = new ChartPojo();
			chart.setFirstOfficeEncDate(NGAPIRestUtils.getXNGDate());
			chart.setLastOfficeEncDate(NGAPIRestUtils.getXNGDate());
			chart.setPreferredProviderId(DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider));
			chart.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider));
			chart.setDefaultLocationId(DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(chart);
			Log4jUtil.log("Chart request body \n" + requestbody);

			String chartURL = enterprisebaseURL
					+ apiRoutes.valueOf("AddChart").getRouteURL().replace("personId", personId);
			chartId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", chartURL, requestbody, 201);
			Log4jUtil.log("Chart created with id " + chartId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return personId;
	}

	public static String addEncounter(String locationName, String providerName, String personId) throws Throwable {
		EncounterPojo encounter = new EncounterPojo();
		String encounterId = "";
		try {
			encounter.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",
					strSqlQueryForRenderingProvider.replace("ProviderName", providerName)));
			encounter.setLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(encounter);
			Log4jUtil.log("Encounter request body \n" + requestbody);

			String encounterURL = apiRoutes.valueOf("AddEncounter").getRouteURL().replace("personId", personId);
			String finalURL = enterprisebaseURL + encounterURL;
			encounterId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Encounter created with id " + encounterId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return encounterId;
	}

	public static String addDiagnosis(String practiceID, String personID, String encounterId) throws Throwable {
		Diagnosis diagnosis = new Diagnosis();
		String diagnosisId = "";
		try {
			List<Interaction> interactions = new ArrayList<Interaction>();
			Interaction interaction = new Interaction();

			interaction.setPersonId(personID);
			interaction.setPracticeId(practiceID);
			interactions.add(interaction);

			diagnosis.setInteractions(interactions);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(diagnosis);
			Log4jUtil.log("Diagnosis request body \n" + requestbody);

			String diagonsisURL = apiRoutes.valueOf("AddDiagnosis").getRouteURL().replace("personId", personID)
					.replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + diagonsisURL;
			diagnosisId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Diagnosis created with id " + diagnosisId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return diagnosisId;
	}

	public static String addMedication(String practiceID, String locationName, String providerName,
			String medicationStatus, String personID, String encounterId, String diagnosisCode, int medicationID)
			throws Throwable {
		Medication medication = new Medication();
		String medicationId = "";
		try {
			medication.setStartDate(sdf.format(new Date()));
			if (medicationStatus.equalsIgnoreCase("Active")) {
				Date newDate;
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, 2);
				newDate = c.getTime();
				medication.setStopDate(sdf.format(newDate));
			} else
				medication.setStopDate(sdf.format(new Date()));

			medication.setProviderId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));
			medication.setLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));

			medication.setDiagnosisCode(diagnosisCode);
			medication.setRxUnits("Tablet");
			medication.setMedicationId(medicationID);
			medication.setSigDescription("To add the medication to the patient");

			List<AcknowledgedProblem> acknowledgedProblems = new ArrayList<AcknowledgedProblem>();
			AcknowledgedProblem acknowledgedProblem = new AcknowledgedProblem();

			acknowledgedProblem.setPersonId(personID);
			acknowledgedProblem.setPracticeId(practiceID);
			acknowledgedProblems.add(acknowledgedProblem);

			medication.setAcknowledgedProblems(acknowledgedProblems);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(medication);
			Log4jUtil.log("Medication request body \n" + requestbody);

			String medicationURL = apiRoutes.valueOf("PrescribeMedication").getRouteURL().replace("personId", personID)
					.replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + medicationURL;
			medicationId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Medication added with id " + medicationId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return medicationId;
	}

	public static String addAllergy(String locationName, String providerName, String personID, String encounterId,
			String allergyID, int allergyType) throws Throwable {
		Allergy allergy = new Allergy();
		String allergyId = "";
		try {
			allergy.setAllergyId(allergyID);
			allergy.setAllergyTypeId(allergyType);
			allergy.setOnsetDate(sdf.format(DateUtils.addDays(new Date(), -1)));
			allergy.setlocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			allergy.setproviderId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(allergy);
			Log4jUtil.log("Allergy request body \n" + requestbody);

			String allergyURL = apiRoutes.valueOf("AddAllergy").getRouteURL().replace("personId", personID)
					.replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + allergyURL;
			allergyId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Allergy added with id " + allergyId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return allergyId;
	}

	public static String addProcedure(String locationName, String providerName, String personID, String encounterId,
			String diagnosisId) throws Throwable {
		Procedure procedure = new Procedure();
		String procedureId = "";
		try {
			String placeOfService = "select top 1 place_of_service from service_item_mstr where service_item_id='"
					+ procedure.getServiceItemId() + "'";
			procedure.setPlaceOfService(DBUtils.executeQueryOnDB("NGCoreDB", placeOfService));
			procedure.setLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			procedure.setProviderId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));
			procedure.setServiceDate(sdf.format(new Date()));
			procedure.setpatientDiagnosisId1(diagnosisId);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(procedure);
			Log4jUtil.log("Procedure request body \n" + requestbody);

			String procedureURL = apiRoutes.valueOf("AddProcedure").getRouteURL().replace("personId", personID)
					.replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + procedureURL;
			procedureId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Procedure added with id " + procedureId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return procedureId;
	}

	public static String addLabOrder(String locationName, String providerName, String personID, String encounterId)
			throws Throwable {
		LabOrder labOrder = new LabOrder();
		String labOrderId = "";
		try {
			labOrder.setTestLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			labOrder.setOrderingProvider(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(labOrder);
			Log4jUtil.log("Lab Order request body \n" + requestbody);

			String LabOrderURL = apiRoutes.valueOf("AddNewLabOrder").getRouteURL().replace("personId", personID)
					.replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + LabOrderURL;
			labOrderId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Lab Order added with id " + labOrderId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return labOrderId;
	}

	public static String addLabOrderTest(String personID, String orderId, String testCodeID) throws Throwable {
		LabOrderTest labOrderTest = new LabOrderTest();
		List<LabOrderTest> labOrderTestArray = new ArrayList<LabOrderTest>();
		String labOrderTestId = "";
		try {
			labOrderTest.setTestCodeId(testCodeID);
			labOrderTest.setCollectionDate(sdf.format(new Date()));
			labOrderTest.setExpectedResultDate(sdf.format(DateUtils.addDays(new Date(), 1)));
			labOrderTest.setScheduleDateTime(sdf.format(new Date()));
			labOrderTestArray.add(labOrderTest);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter()
					.writeValueAsString(labOrderTestArray);
			Log4jUtil.log("Lab Order Test request body \n" + requestbody);

			String LabOrderTestURL = apiRoutes.valueOf("AddLabOrderTest").getRouteURL().replace("personId", personID)
					.replace("orderId", orderId);
			String finalURL = enterprisebaseURL + LabOrderTestURL;
			labOrderTestId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Lab Order Test added with id " + labOrderTestId);
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return labOrderTestId;
	}

	public static String addObsPanel(String personID, String orderId) throws Throwable {
		ObsPanel addObsPanel = new ObsPanel();
		String obsPanelId = "";
		try {
			addObsPanel.setOrderId(orderId);

			String orderedTestId = "select order_test_id from lab_order_tests where order_num='" + orderId + "'";
			addObsPanel.setOrderedTestId(DBUtils.executeQueryOnDB("NGCoreDB", orderedTestId));
			addObsPanel.setCollectionDateTime(sdf.format(new Date()));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(addObsPanel);
			Log4jUtil.log("Observation Panel request body \n" + requestbody);

			String obsPanelURL = apiRoutes.valueOf("AddObservationPanel").getRouteURL().replace("personId", personID);
			String finalURL = enterprisebaseURL + obsPanelURL;
			obsPanelId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Observation Panel added with id " + obsPanelId);
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return obsPanelId;
	}

	public static String addLabResult(String personID, String panelId, String lonicCodeLabResult) throws Throwable {
		LabResult labResult = new LabResult();
		List<LabResult> labResultArray = new ArrayList<LabResult>();
		String labResultId = "";
		try {
			labResult.setObservationDate(sdf.format(new Date()));
			labResult.setLoincCode(lonicCodeLabResult);
			labResult.setAbnormalFlag(2);
			labResultArray.add(labResult);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(labResultArray);
			Log4jUtil.log("Lab Result request body \n" + requestbody);

			String labResultURL = apiRoutes.valueOf("AddObservationResults").getRouteURL().replace("personId", personID)
					.replace("panelId", panelId);
			String finalURL = enterprisebaseURL + labResultURL;
			labResultId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Lab Result added with id " + labResultId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return labResultId;
	}

	public static String updateLabOrder(String locationName, String providerName, String personID, String orderId)
			throws Throwable {
		UpdateLabOrder updateLabOrder = new UpdateLabOrder();
		String updateLabOrderId = "";
		try {
			updateLabOrder.setTestLocation(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			updateLabOrder.setOrderingProvider(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));
			updateLabOrder.setSignOffDate(sdf.format(new Date()));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(updateLabOrder);
			Log4jUtil.log("Update Lab Order request body \n" + requestbody);

			String updateLabOrderURL = apiRoutes.valueOf("UpdateLabOrder").getRouteURL().replace("personId", personID)
					.replace("orderId", orderId);
			String finalURL = enterprisebaseURL + updateLabOrderURL;
			updateLabOrderId = NGAPIRestUtils.setupNGHttpPutRequest("EnterpriseGateway", finalURL, requestbody, 200);
			Log4jUtil.log("Signing off Lab Order with id " + updateLabOrderId);
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return updateLabOrderId;
	}

	public static String addNewImmunizationsOrder(String locationName, String providerName, String personID,
			String encounterId) throws Throwable {
		Immunization immunization = new Immunization();
		String immunizationId = "";
		try {
			List<String> allergiesReviewed = new ArrayList<String>();
			allergiesReviewed.add("Test");

			immunization.setLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			immunization.setOrderingProviderId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));
			immunization.setAllergiesReviewed(allergiesReviewed);

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(immunization);
			Log4jUtil.log("Immunization request body \n" + requestbody);

			String immunizationURL = apiRoutes.valueOf("AddNewImmunizationsOrder").getRouteURL()
					.replace("personId", personID).replace("encounterId", encounterId);
			String finalURL = enterprisebaseURL + immunizationURL;
			immunizationId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Immunization added with id " + immunizationId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return immunizationId;
	}

	public static String addProblem(String locationName, String providerName, String personID, String conceptID,
			String problemStatusID, String problemStatus) throws Throwable {
		Problem problem = new Problem();
		String problemId = "";
		try {
			problem.setConceptId(conceptID);
			problem.setProblemStatusId(problemStatusID);
			problem.setProblemStatus(problemStatus);
			problem.setLocationId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForLocation.replace("locationName", locationName)));
			problem.setProviderId(
					DBUtils.executeQueryOnDB("NGCoreDB", strSqlQueryForProvider.replace("ProviderName", providerName)));
			problem.setOnsetDate(sdf.format(DateUtils.addDays(new Date(), -1)));
			problem.setLastAddressedDate(sdf.format(new Date()));

			String requestbody = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(problem);
			Log4jUtil.log("Problem request body \n" + requestbody);

			String problemURL = apiRoutes.valueOf("AddProblem").getRouteURL().replace("personId", personID);
			String finalURL = enterprisebaseURL + problemURL;
			problemId = NGAPIRestUtils.setupNGHttpPostRequest("EnterpriseGateway", finalURL, requestbody, 201);
			Log4jUtil.log("Problem added with id " + problemId);

		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
		}
		return problemId;
	}

}
