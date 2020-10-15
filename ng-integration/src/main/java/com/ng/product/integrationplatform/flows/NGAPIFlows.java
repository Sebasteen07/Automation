// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 *
 ************************/
package com.ng.product.integrationplatform.flows;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.AcknowledgedProblem;
import com.ng.product.integrationplatform.pojo.ObsPanel;
import com.ng.product.integrationplatform.pojo.Problem;
import com.ng.product.integrationplatform.pojo.Allergy;
import com.ng.product.integrationplatform.pojo.CCDRequest;
import com.ng.product.integrationplatform.pojo.CCDRequestDetails;
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
import com.ng.product.integrationplatform.pojo.UpdateEncounter;
import com.ng.product.integrationplatform.pojo.UpdateLabOrder;
import com.ng.product.integrationplatform.utils.DBUtils;


public class NGAPIFlows {

	NGAPIUtils ngAPIUtils;
	private static String EnterprisebaseURL;
	private static String dateFormat ="yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	private static String strSqlQueryForProvider="select provider_id from provider_mstr where description='ProviderName'";
	private static String strSqlQueryForLocation="select location_id from location_mstr where location_name='locationName'";	
	private static String strSqlQueryForRenderingProvider="select provider_id from provider_mstr where description='ProviderName'";
	

	public NGAPIFlows(PropertyFileLoader propertyLoaderObj) throws Throwable {
		ngAPIUtils = new NGAPIUtils(propertyLoaderObj);
		if(propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")){
			EnterprisebaseURL= ngAPIUtils.getRelativeBaseUrl();		
		}
		else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")){
			EnterprisebaseURL= ngAPIUtils.getRelativeBaseUrl();				           
		}
		else{
			Log4jUtil.log("Invalid Execution Mode");
		}
	}

	public static String CreatePatientinEPM(NewPatient argPayload) throws IOException{
        String person_id="";
	  try{
			ObjectMapper objMap = new ObjectMapper();
			String PatientRequestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(argPayload);
			Log4jUtil.log("Patient Request Body is \n" + PatientRequestbody);
			
		    String personURL =apiRoutes.valueOf("AddEnterprisePerson").getRouteURL(); 
		    String finalURL = EnterprisebaseURL+personURL;
		    person_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,PatientRequestbody , 201);
		    Log4jUtil.log("Person created with id "+person_id);		
	 } catch (Exception e) {
		Log4jUtil.log(e.getMessage());
     }
		return person_id;
	}
	
	public static String addCharttoProvider(String locationName,String ProviderName,String personId) throws Throwable{
		ChartPojo chart = new ChartPojo();
		String chart_id="";
		try{
			String strSqlQueryForProvider="select provider_id from provider_mstr where description='"+ProviderName+"'";
			String strSqlQueryForLocation="select location_id from location_mstr where location_name='"+locationName+"'";
						
			chart.setFirstOfficeEncDate(NGAPIUtils.getXNGDate());
			chart.setLastOfficeEncDate(NGAPIUtils.getXNGDate());
			chart.setPreferredProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			chart.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			chart.setDefaultLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(chart);
	        Log4jUtil.log("Chart request body \n"+requestbody);
	        
		    String chartURL =apiRoutes.valueOf("AddChart").getRouteURL().replace("personId", personId); 
			String finalURL = EnterprisebaseURL+chartURL;
			chart_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Chart created with id "+chart_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return chart_id;
	}

	public static String addEncounter(String locationName,String ProviderName,String personId) throws Throwable{
		EncounterPojo encounter = new EncounterPojo();
		String encounter_id="";
		try{
			encounter.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRenderingProvider.replace("ProviderName",ProviderName)));
			encounter.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(encounter);
	        Log4jUtil.log("Encounter request body \n"+requestbody);
			
			String encounterURL =apiRoutes.valueOf("AddEncounter").getRouteURL().replace("personId", personId); 
			String finalURL = EnterprisebaseURL +encounterURL;
			encounter_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Encounter created with id "+encounter_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return encounter_id;
	}
	
	public static String addDiagnosis(String practiceID,String personID, String encounterId) throws Throwable{
		Diagnosis diagnosis = new Diagnosis();
		String diagnosis_id="";
		try{		
			List<Interaction> interactions =new ArrayList<Interaction>();
			Interaction interaction = new Interaction();
			
			interaction.setPersonId(personID);
			interaction.setPracticeId(practiceID);
			interactions.add(interaction);
			
			diagnosis.setInteractions(interactions); 			
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(diagnosis);
	        Log4jUtil.log("Diagnosis request body \n"+requestbody);
			
			String diagonsisURL =apiRoutes.valueOf("AddDiagnosis").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +diagonsisURL;
			diagnosis_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Diagnosis created with id "+diagnosis_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return diagnosis_id;
	}
	
	public static String addMedication(String practiceID,String locationName,String ProviderName,String MedicationStatus, String personID, String encounterId, String diagnosisCode, int MedicationID) throws Throwable{
		Medication medication = new Medication();
		String medication_id="";
		try{
			medication.setStartDate(sdf.format(new Date()));
            if(MedicationStatus.equalsIgnoreCase("Active")) {
                Date newDate;
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.YEAR, 2);
                newDate = c.getTime();
                medication.setStopDate(sdf.format(newDate));
            }else
            	 medication.setStopDate(sdf.format(new Date()));
 
            medication.setProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName", ProviderName)));
            medication.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
            
            medication.setDiagnosisCode(diagnosisCode);
            medication.setRxUnits("Tablet");
            medication.setMedicationId(MedicationID);
            medication.setSigDescription("To add the medication to the patient");		
			
            List<AcknowledgedProblem> acknowledgedProblems =new ArrayList<AcknowledgedProblem>();
            AcknowledgedProblem acknowledgedProblem = new AcknowledgedProblem();
			
            acknowledgedProblem.setPersonId(personID);
            acknowledgedProblem.setPracticeId(practiceID);
            acknowledgedProblems.add(acknowledgedProblem);
			
            medication.setAcknowledgedProblems(acknowledgedProblems);	
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(medication);
	        Log4jUtil.log("Medication request body \n"+requestbody);
			
			String medicationURL =apiRoutes.valueOf("PrescribeMedication").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +medicationURL;
			medication_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Medication added with id "+medication_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return medication_id;
	}
	
	public static String addAllergy(String locationName,String ProviderName, String personID, String encounterId,String allergyID,int AllergyType) throws Throwable{
		Allergy allergy = new Allergy();
		String allergy_id="";
		try{ 
            allergy.setAllergyId(allergyID);
            allergy.setAllergyTypeId(AllergyType);
            allergy.setOnsetDate(sdf.format(new Date()));
            allergy.setlocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
            allergy.setproviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
           
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(allergy);
	        Log4jUtil.log("Allergy request body \n"+requestbody);
			
			String allergyURL =apiRoutes.valueOf("AddAllergy").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +allergyURL;
			allergy_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Allergy added with id "+allergy_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return allergy_id;
	}
	
	public static String addProcedure(String locationName,String ProviderName, String personID, String encounterId, String diagnosisId) throws Throwable{
		Procedure procedure = new Procedure();
		String procedure_id="";
		try{
			String placeOfService="select top 1 place_of_service from service_item_mstr where service_item_id='"+procedure.getServiceItemId()+"'";
			procedure.setPlaceOfService(DBUtils.executeQueryOnDB("NGCoreDB",placeOfService));
			procedure.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			procedure.setProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
            procedure.setServiceDate(sdf.format(new Date()));
            procedure.setpatientDiagnosisId1(diagnosisId);
            
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(procedure);
	        Log4jUtil.log("Procedure request body \n"+requestbody);
			
			String ProcedureURL =apiRoutes.valueOf("AddProcedure").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +ProcedureURL;
			procedure_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Procedure added with id "+procedure_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return procedure_id;
	}
	
	public static String addLabOrder(String locationName,String ProviderName, String personID, String encounterId) throws Throwable{
		LabOrder labOrder = new LabOrder();
		String labOrder_id="";
		try{ 
			labOrder.setTestLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			labOrder.setOrderingProvider(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
            
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(labOrder);
	        Log4jUtil.log("Lab Order request body \n"+requestbody);
			
			String LabOrderURL =apiRoutes.valueOf("AddNewLabOrder").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +LabOrderURL;
			labOrder_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Lab Order added with id "+labOrder_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return labOrder_id;
	}
	
	public static String addLabOrderTest(String personID, String orderId,String testCodeID) throws Throwable{
		LabOrderTest labOrderTest = new LabOrderTest();
		List<LabOrderTest> labOrderTestArray =new ArrayList<LabOrderTest>();
		String labOrderTest_id="";
		try{ 			
			labOrderTest.setTestCodeId(testCodeID);
			labOrderTest.setCollectionDate(sdf.format(new Date()));
			labOrderTest.setExpectedResultDate(sdf.format(DateUtils.addDays(new Date(), 1)));
			labOrderTest.setScheduleDateTime(sdf.format(new Date()));
			labOrderTestArray.add(labOrderTest);
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(labOrderTestArray);
	        Log4jUtil.log("Lab Order Test request body \n"+requestbody);
			
			String LabOrderTestURL =apiRoutes.valueOf("AddLabOrderTest").getRouteURL().replace("personId", personID).replace("orderId", orderId); 
			String finalURL = EnterprisebaseURL +LabOrderTestURL;
			labOrderTest_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Lab Order Test added with id "+labOrderTest_id);			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return labOrderTest_id;
	}
	
	public static String addObsPanel(String personID, String orderId) throws Throwable{
		ObsPanel addObsPanel = new ObsPanel();
		String ObsPanel_id="";
		try{ 		
			addObsPanel.setOrderId(orderId);
			
			String orderedTestId= "select order_test_id from lab_order_tests where order_num='"+orderId+"'";
			addObsPanel.setOrderedTestId(DBUtils.executeQueryOnDB("NGCoreDB",orderedTestId));
			addObsPanel.setCollectionDateTime(sdf.format(new Date()));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(addObsPanel);
	        Log4jUtil.log("Observation Panel request body \n"+requestbody);
			
			String ObsPanelURL =apiRoutes.valueOf("AddObservationPanel").getRouteURL().replace("personId", personID); 
			String finalURL = EnterprisebaseURL +ObsPanelURL;
			ObsPanel_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Observation Panel added with id "+ObsPanel_id);			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return ObsPanel_id;
	}
	
	public static String addLabResult(String personID, String panelId,String lonicCodeLabResult) throws Throwable{
		LabResult labResult = new LabResult();
		List<LabResult> labResultArray =new ArrayList<LabResult>();
		String labResult_id="";
		try{
			labResult.setObservationDate(sdf.format(new Date()));
			labResult.setLoincCode(lonicCodeLabResult);
			labResult.setAbnormalFlag(2);
			labResultArray.add(labResult);
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(labResultArray);
	        Log4jUtil.log("Lab Result request body \n"+requestbody);
			
			String LabResultURL =apiRoutes.valueOf("AddObservationResults").getRouteURL().replace("personId", personID).replace("panelId", panelId); 
			String finalURL = EnterprisebaseURL +LabResultURL;
			labResult_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Lab Result added with id "+labResult_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return labResult_id;
	}
	
	public static String updateLabOrder(String locationName,String ProviderName,String personID, String orderId) throws Throwable{
		UpdateLabOrder updateLabOrder = new UpdateLabOrder();
		String updateLabOrder_id="";
		try{ 			
			updateLabOrder.setTestLocation(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			updateLabOrder.setOrderingProvider(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
			updateLabOrder.setSignOffDate(sdf.format(new Date()));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(updateLabOrder);
	        Log4jUtil.log("Update Lab Order request body \n"+requestbody);
			
			String UpdateLabOrderURL =apiRoutes.valueOf("UpdateLabOrder").getRouteURL().replace("personId", personID).replace("orderId", orderId); 
			String finalURL = EnterprisebaseURL +UpdateLabOrderURL;
			updateLabOrder_id=NGAPIUtils.setupNGHttpPutRequest("EnterpriseGateway",finalURL,requestbody , 200);
			Log4jUtil.log("Signing off Lab Order with id "+updateLabOrder_id);			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return updateLabOrder_id;
	}
	
	public static String addNewImmunizationsOrder(String locationName,String ProviderName,String personID, String encounterId) throws Throwable{
		Immunization immunization = new Immunization();
		String Immunization_id="";
		try{ 
			List<String> allergiesReviewed = new ArrayList<String>();
			allergiesReviewed.add("Test");
			
			immunization.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			immunization.setOrderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));			
			immunization.setAllergiesReviewed(allergiesReviewed);
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(immunization);
	        Log4jUtil.log("Immunization request body \n"+requestbody);
			
			String ImmunizationURL =apiRoutes.valueOf("AddNewImmunizationsOrder").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +ImmunizationURL;
			Immunization_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Immunization added with id "+Immunization_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return Immunization_id;
	}
	

	public static String addProblem(String locationName,String ProviderName, String personID,String conceptID,String ProblemStatusID, String problemStatus) throws Throwable{
		Problem problem = new Problem();
		String problem_id="";
		try{
			problem.setConceptId(conceptID);
			problem.setProblemStatusId(ProblemStatusID);
			problem.setProblemStatus(problemStatus);
			problem.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			problem.setProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
			problem.setOnsetDate(sdf.format(DateUtils.addDays(new Date(), -1)));
			problem.setLastAddressedDate(sdf.format(new Date()));
            
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(problem);
	        Log4jUtil.log("Problem request body \n"+requestbody);
			
			String ProblemURL =apiRoutes.valueOf("AddProblem").getRouteURL().replace("personId", personID); 
			String finalURL = EnterprisebaseURL +ProblemURL;
			problem_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 201);
			Log4jUtil.log("Problem added with id "+problem_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return problem_id;
	}

	public static String PostCCDRequest(String locationName,String ProviderName, String personID,String requestType,String encounterId) throws Throwable{
		String CCDARequest_id ="";
		try{
		List<CCDRequestDetails> a =new ArrayList<CCDRequestDetails>();
		CCDRequest ccdRequest = new CCDRequest();
		CCDRequestDetails ccdRequestDetails= new CCDRequestDetails();
		
		String MSUParameters = "<parameters><profile>Manual</profile><userID>546</userID><practiceID>0040</practiceID><practiceSettingCode>36b48cf9-46ea-46b2-a446-c7eb096866ba</practiceSettingCode><confidentialityCode>c6b39355-4ad2-4f38-960b-3510a05eb866</confidentialityCode><demographics><ngPersonId>20e4df61-a6d5-43a2-ac5d-85fed9e030f3</ngPersonId><problems><problem><id>c6d71d27-58e6-4ace-9731-5d5645772e26</id></problem></problems></demographics><ccdaDocumentType>Ccd</ccdaDocumentType><encounters /></parameters>";
		String LockedEncounterParameters = "<parameters><profile>Longitudinal</profile><profile>Encounter</profile></parameters>";
				
		ccdRequestDetails.setEncounterId(encounterId);
		ccdRequestDetails.setFromDate(sdf.format(new Date()));
		if(requestType.equalsIgnoreCase("MedicalSummaryUtility"))
		    ccdRequestDetails.setParameters(MSUParameters);
		else if(requestType.equalsIgnoreCase("LockedEncounter"))
			ccdRequestDetails.setParameters(LockedEncounterParameters);
		else 
			Log4jUtil.log("Invalid Post CCD Request");
		ccdRequestDetails.setPersonId(personID);
		ccdRequestDetails.setPersonNumber(DBUtils.executeQueryOnDB("NGCoreDB","select person_nbr from person where person_id = '"+personID+"'"));
		ccdRequestDetails.setProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRenderingProvider.replace("ProviderName",ProviderName)));
		ccdRequestDetails.setRequestedDate(sdf.format(new Date()));
		ccdRequestDetails.setRequestId(UUID.randomUUID().toString());
		ccdRequestDetails.setToDate(sdf.format(new Date()));
		
		a.add(ccdRequestDetails);
		
		ccdRequest.setCcdarequesttype(requestType);
		ccdRequest.setRequests(a); 			
		
		ObjectMapper objMap = new ObjectMapper();
        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(ccdRequest);
        Log4jUtil.log("Post CCD request body \n"+requestbody);
		
		String CCDARequestURL =apiRoutes.valueOf("PostCCDARequest").getRouteURL(); 
		String finalURL = EnterprisebaseURL +CCDARequestURL;
		CCDARequest_id=NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 200);
		Log4jUtil.log(requestType+" CCD Request created with id "+CCDARequest_id);
		} catch (Exception e) {
			Log4jUtil.log(e.getMessage());
	    }
		return CCDARequest_id;
    }
	
	public static void updateToSensitiveEncounter(String locationName,String ProviderName, String personID, String encounterId) throws Throwable{	
		UpdateEncounter updateEncounter = new UpdateEncounter();
		try{
			updateEncounter.setIsSensitive(true);
			updateEncounter.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRenderingProvider.replace("ProviderName",ProviderName)));
			updateEncounter.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.defaultPrettyPrintingWriter().writeValueAsString(updateEncounter);
	        Log4jUtil.log("Update Encounter request body \n"+requestbody);

			String updateEncounterURL =apiRoutes.valueOf("UpdateEncounter").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +updateEncounterURL;
			NGAPIUtils.setupNGHttpPutRequest("EnterpriseGateway",finalURL,requestbody , 200);
			Log4jUtil.log("Encounter is updated successsfully");
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
	}
	
	public static void checkOutEncounter(String personID, String encounterId) throws Throwable{
		try{
			String CheckOutEncounterURL =apiRoutes.valueOf("CheckOutEncounter").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +CheckOutEncounterURL;
			NGAPIUtils.setupNGHttpPutRequest("EnterpriseGateway",finalURL,"" , 200);
			Log4jUtil.log("Encounter is checked out successsfully");			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
	}
	
	public static void checkINEncounter(String personID, String encounterId) throws Throwable{
		try{
			String CheckInEncounterURL =apiRoutes.valueOf("CheckInEncounter").getRouteURL().replace("personId", personID).replace("encounterId", encounterId); 
			String finalURL = EnterprisebaseURL +CheckInEncounterURL;
			NGAPIUtils.setupNGHttpPutRequest("EnterpriseGateway",finalURL,"" , 200);
			Log4jUtil.log("Encounter is checked in successsfully");			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
	}
}
