//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
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
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.apiRoutes;
import com.ng.product.integrationplatform.pojo.AcknowledgedProblem;
import com.ng.product.integrationplatform.pojo.ObsPanel;
import com.ng.product.integrationplatform.pojo.PrescriptionRenewalRequest;
import com.ng.product.integrationplatform.pojo.Medications;
import com.ng.product.integrationplatform.pojo.Problem;
import com.ng.product.integrationplatform.pojo.Allergy;
import com.ng.product.integrationplatform.pojo.Appointment;
import com.ng.product.integrationplatform.pojo.AppointmentResponse;
import com.ng.product.integrationplatform.pojo.Attachment;
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
import com.ng.product.integrationplatform.pojo.Message;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.pojo.Procedure;
import com.ng.product.integrationplatform.pojo.Recipient;
import com.ng.product.integrationplatform.pojo.SecureMessage;
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

	public static String createPatientinEPM(NewPatient argPayload) throws IOException{
        String person_id="";
	  try{
			ObjectMapper objMap = new ObjectMapper();
			String PatientRequestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(argPayload);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(chart);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(encounter);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(diagnosis);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(medication);
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
            allergy.setOnsetDate(sdf.format(DateUtils.addDays(new Date(), -1)));
            allergy.setlocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
            allergy.setproviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider.replace("ProviderName",ProviderName)));
           
			ObjectMapper objMap = new ObjectMapper();
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(allergy);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(procedure);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(labOrder);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(labOrderTestArray);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(addObsPanel);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(labResultArray);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(updateLabOrder);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(immunization);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(problem);
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

	public static String postCCDRequest(String locationName,String ProviderName, String personID,String requestType,String encounterId) throws Throwable{
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
        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(ccdRequest);
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
	        String requestbody = objMap.writerWithDefaultPrettyPrinter().writeValueAsString(updateEncounter);
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
	
	public static String postSecureMessage(PropertyFileLoader PropertyLoaderObj,String messageType,String personID, String practiceID,String userID, String ProviderName, String locationName, String applicationName, String encounterType,String senderType,String encounterId,String attachmentName,String documentID) throws Throwable{
		String comm_id = null;
		try{
			SecureMessage securemessage = new SecureMessage();
			Message message = new Message();
			Recipient recipient = new Recipient();
			Attachment attachment = new Attachment();
			List<Recipient> recipientList = new ArrayList<Recipient>();
			List<Message> messageList = new ArrayList<Message>();
			List<Attachment> attachmentList = new ArrayList<Attachment>();
			
			recipient.setId(personID);
			String recipientLN = DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id='"+personID+"'");
			String recipientFN = DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id='"+personID+"'");
			recipient.setName(recipientLN + ", " +recipientFN);
			recipient.setType("Patient");
			recipientList.add(recipient);
			
			String userIdQuery="select top 1 user_id from user_mstr where practice_id='"+practiceID+"'";
			String communicationMessageID =UUID.randomUUID().toString().toUpperCase();
			
			message.setId(communicationMessageID);
			
			if(messageType.equalsIgnoreCase("SentByOnlineProfile")){
				String SecureMessageOnlineProfile = PropertyLoaderObj.getProperty("SecureMessageOnlineProfile");
				message.setRoutingRuleName(SecureMessageOnlineProfile);
				message.setRoutingRuleType("1");
				message.setRoutingRuleId(DBUtils.executeQueryOnDB("NGCoreDB","select row_id from ngweb_alias where name='"+SecureMessageOnlineProfile+"'"));				
			} else if(messageType.contains("ReplyToASKAUsingAliasName")){
				String SecureMessageAlias = PropertyLoaderObj.getProperty("SecureMessageAlias");
				message.setRoutingRuleType("0");
				message.setAliasName(SecureMessageAlias);
				message.setRoutingRuleName("RoutingRuleName Check");
			} else
				message.setRoutingRuleType("-1");
			
			if(userID.isEmpty()){
				userID = DBUtils.executeQueryOnDB("NGCoreDB",userIdQuery);}
			    String userFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from user_mstr where user_id='"+userID+"'");
			    String userLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from user_mstr where user_id='"+userID+"'");	
			
			    message.setSenderName(userFirstName+" "+userLastName);
			    message.setSenderId(userID);
			
			if(messageType.contains("ReplyToASKAUsingAliasName"))
			    message.setRoutingRuleId(userID);
			    
			if(encounterType.equalsIgnoreCase("OriginalUnlockedEncounter"))
				message.setOriginalId(communicationMessageID);
				
			if(messageType.contains("ReplyToPortal")){
				message.setParentId(messageType.substring(13).toUpperCase());
				message.setRootThreadId(messageType.substring(13).toUpperCase());
			}
			
			if(messageType.contains("ReplyToASKAUsingAliasName")){
				message.setParentId(messageType.substring(25).toUpperCase());
				message.setRootThreadId(messageType.substring(25).toUpperCase());
			}
			
			if(messageType.contains("ReplyToPortal")){					
		    	String subject = DBUtils.executeQueryOnDB("NGCoreDB","select subject from ngweb_communications where comm_id ='"+messageType.substring(13)+"'");
		    	message.setSubject("RE: "+subject);
			} else if (messageType.contains("ReplyToASKAUsingAliasName")){					
		    	String subject = DBUtils.executeQueryOnDB("NGCoreDB","select subject from ngweb_communications where comm_id ='"+messageType.substring(25)+"'");
		    	message.setSubject("RE: "+subject);
			}
			else
				message.setSubject("Subject" + (new Date()).getTime());
			message.setBody(messageType +"Body" + (new Date()).getTime());
			
			if(messageType.equalsIgnoreCase("DelayedDelivery"))
				Log4jUtil.log("Delayed Delivery should be set");
			else
				message.setSentTimestamp(sdf.format(new Date()));
			
			if(messageType.contains("ReplyToPortal")){
				message.setCategory("Medication Questions Category");
				message.setRepliedWhenTimestamp(sdf.format(new Date()));
			}else if (messageType.contains("ReplyToASKAUsingAliasName")){
				message.setCategory("Medication Questions Category");
				message.setRepliedWhenTimestamp(sdf.format(new Date()));
			}
			else
				message.setCategory("Practice Initiated");
			message.setIsClinical(true);
			message.setRecipients(recipientList);
			
			if(messageType.equalsIgnoreCase("HighPriority"))
				message.setPriority("High");
			else
				message.setPriority("Normal");
			
			if(messageType.equalsIgnoreCase("DisableReply"))
				message.setCanReply(true);
			
			if(messageType.equalsIgnoreCase("ReadReceiptRequested"))
				message.setIsReadReceiptRequested(true);
			
			if(messageType.equalsIgnoreCase("UnreadNotificationRequested"))
				message.setIsUnreadNotificationRequested(true);
			
			if(messageType.equalsIgnoreCase("SaveDraft"))
				message.setIsDraft(true);
			
			if(messageType.equalsIgnoreCase("DelayedDelivery"))
				message.setScheduledTimestamp(sdf.format(new Date()));
			
			if(attachmentName.equalsIgnoreCase("PatientEducation")){
			attachment.setAttachmentId(UUID.randomUUID().toString().toUpperCase());
			attachment.setDocumentId(documentID);
			attachment.setName(DBUtils.executeQueryOnDB("NGCoreDB","select document_desc from patient_education where document_id ='"+documentID+"'"));
			attachment.setType("PatientEducation");
			String format = DBUtils.executeQueryOnDB("NGCoreDB","select file_format from patient_education where document_id ='"+documentID+"'");
			if(format.contains("HTM"))
				format = "HTML";
			attachment.setFormat(format);
			attachment.setContentBytes("2500");
			
			attachmentList.add(attachment);			
			message.setAttachments(attachmentList);
			}
			else if(attachmentName.equalsIgnoreCase("PatientImage")){
				attachment.setAttachmentId(UUID.randomUUID().toString().toUpperCase());
				attachment.setDocumentId(documentID);
				String imageName = DBUtils.executeQueryOnDB("NGCoreDB","select orig_image_file from patient_images where image_id ='"+documentID+"'");
				attachment.setName(imageName);
				attachment.setType("EmrImage");
				attachment.setFormat(imageName.substring(imageName.lastIndexOf(".")).replace(".", ""));
				attachment.setContentBytes("2500");
				
				attachmentList.add(attachment);			
				message.setAttachments(attachmentList);
			}
			
			messageList.add(message);
			
			securemessage.setSenderType(senderType);
			securemessage.setEncounterType(encounterType);
			
			if(encounterType.equalsIgnoreCase("ExistingEncounter"))
				securemessage.setEncounterId(encounterId);
			
			securemessage.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRenderingProvider.replace("ProviderName",ProviderName)));
			securemessage.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			securemessage.setApplicationName(applicationName);
			securemessage.setMessages(messageList);
			
			if(messageType.contains("ReplyToPortal")){
				securemessage.setMessageType("1");
			}
			if(messageType.contains("ReplyToASKAUsingAliasName")){
				securemessage.setMessageType("1");
			}
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);				
	        String requestbody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(securemessage);
	        Log4jUtil.log("Secure Message request body \n"+requestbody);
			
			String secureMessageURL =apiRoutes.valueOf("PostSecureMessage").getRouteURL(); 
			String finalURL = EnterprisebaseURL +secureMessageURL;
			NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 200);
			comm_id = message.getId();
			Log4jUtil.log("Communication Message created with id "+comm_id);		
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return comm_id;
	}
	
	public static void postAppointmentResponse(String appointmentRequestId, String appointmentId, String message,String time,int appointmentDaytobeAdded) throws Throwable{
		AppointmentResponse appointmentResponse = new AppointmentResponse();		
		try{			
			appointmentResponse.setMessage(message);
			
			String date = sdf.format(DateUtils.addDays(new Date(), appointmentDaytobeAdded));
			date = date.substring(0, date.indexOf("T"));
			
			if(time.isEmpty())
				appointmentResponse.setApprovedDate(date + "T00:00:00");
			else
				appointmentResponse.setApprovedDate(date + "T"+time);
			appointmentResponse.setAppointmentId(appointmentId.toUpperCase());
			appointmentResponse.setAppointmentStatus("Booked");
			appointmentResponse.setSourceApplicationType(1);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);				
	        String requestbody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointmentResponse);
	        Log4jUtil.log("Appointment Response request body \n"+requestbody);			
			
			String appointmentResponseURL =apiRoutes.valueOf("PostAppointmentResponse").getRouteURL().replace("appointmentRequestId", appointmentRequestId); 
			String finalURL = EnterprisebaseURL +appointmentResponseURL;
			NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody, 201);
			Log4jUtil.log("Appointment Response is sent successsfully");			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
	}
	
	public static String postAppointment(String personId,String practiceId,String locationName, String ProviderName,String EventName, String ResourceName,int appointmentDaytobeAdded,String time,int expectedStatusCode) throws Throwable{
		Appointment appointment = new Appointment();	String epm_appt_id ="";	
		try{			
			String strSqlQueryForProvider= "select provider_id from provider_mstr where description='"+ProviderName+"'";
			String strSqlQueryForLocation= "select location_id from location_mstr where location_name='"+locationName+"'";
			String strSqlQueryForEvent= "select event_id from events where event ='"+EventName+"' and practice_id ='"+practiceId+"'"  ;
			String strSqlQueryForResource= "select resource_id from resources where description = '"+ResourceName+"' and practice_id ='"+practiceId+"'";		
			String strSqlQueryForDuration= "select duration from events where event ='"+EventName+"'";
						
			appointment.setPersonId(personId);
			appointment.setEventId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForEvent).toLowerCase());
			appointment.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation));
			appointment.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			appointment.setDurationMinutes(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForDuration));
			
			String appointmentDate = sdf.format(DateUtils.addDays(new Date(), appointmentDaytobeAdded));
			appointmentDate = appointmentDate.substring(0, appointmentDate.indexOf("T"));
			if(time.isEmpty())
				appointment.setAppointmentDate(appointmentDate + "T00:00:00");
			else
				appointment.setAppointmentDate(appointmentDate + "T"+time);
			
			List<String> resourceIds =new ArrayList<String>();
			resourceIds.add(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForResource));			
			appointment.setResourceIds(resourceIds);
					
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);				
	        String requestbody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appointment);
	        Log4jUtil.log("Appointment request body \n"+requestbody);			
			
			String appointmentURL =apiRoutes.valueOf("PostAppointment").getRouteURL(); 
			String finalURL = EnterprisebaseURL +appointmentURL;
			epm_appt_id = NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody, expectedStatusCode);
			Log4jUtil.log("Appointment posted successsfully with ID "+epm_appt_id);			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return epm_appt_id;
	}
	
	public static void deleteAppointment(String appointmentId) throws Throwable{	
		try{			
			String deleteAppointmentURL =apiRoutes.valueOf("DeleteAppointment").getRouteURL().replace("appointmentId", appointmentId); 
			String finalURL = EnterprisebaseURL +deleteAppointmentURL;
			NGAPIUtils.setupNGHttpDeleteRequest("EnterpriseGateway",finalURL, 200);
			Log4jUtil.log("Appointment is deleted successsfully from EPM Appointment Book");			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
	}
	
	public static String putPrescriptionRenewalRequest(String personId,String RenewalRequestId,String RenewalResponse, String encounterType, String locationName, String ProviderName,String MedicationStatus, int expectedStatusCode) throws Throwable{
		PrescriptionRenewalRequest prescriptionRenewalRequest = new PrescriptionRenewalRequest();	String prescription_response_id ="";	
		try{			
			String strSqlQueryForProvider= "select provider_id from provider_mstr where description='"+ProviderName+"'";
			String strSqlQueryForLocation= "select location_id from location_mstr where location_name='"+locationName+"'";			
			String strSqlQueryForPatientMed= "select patient_med_id from nxmd_med_renewal_items where transaction_id ='"+RenewalRequestId+"'";			
			
			List<Medications> prescriptionRenewalRequestStatusList = new ArrayList<Medications>();
			Medications prescriptionRenewalRequestStatus = new Medications();
			List<String> comments =new ArrayList<String>();
			
			comments.add(RenewalResponse);			
			prescriptionRenewalRequest.setComments(comments);
			
			prescriptionRenewalRequestStatus.setStatus(MedicationStatus);
			prescriptionRenewalRequestStatus.setPrescriptionId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForPatientMed));			
			prescriptionRenewalRequestStatusList.add(prescriptionRenewalRequestStatus);
			
			prescriptionRenewalRequest.setMedications(prescriptionRenewalRequestStatusList);			
			prescriptionRenewalRequest.setRenewalResponse(RenewalResponse);
			prescriptionRenewalRequest.setResponseDate(sdf.format(new Date()));			
			prescriptionRenewalRequest.setEncounterType(encounterType);
			prescriptionRenewalRequest.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForProvider));
			prescriptionRenewalRequest.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation));
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);				
	        String requestbody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(prescriptionRenewalRequest);
	        Log4jUtil.log("Prescription Renewal Request body \n"+requestbody);			
			
			String prescriptionRenewalRequestURL =apiRoutes.valueOf("PutPrescriptionRenewalRequest").getRouteURL().replace("personId", personId).replace("medicationRenewalRequestId", RenewalRequestId); 
			String finalURL = EnterprisebaseURL +prescriptionRenewalRequestURL;
			prescription_response_id = NGAPIUtils.setupNGHttpPutRequest("EnterpriseGateway",finalURL,requestbody, expectedStatusCode);
			Log4jUtil.log("Prescription Renewal Request is updated successsfully with ID "+prescription_response_id);			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return prescription_response_id;
	}
	
	public static String postBulkSecureMessage(PropertyFileLoader PropertyLoaderObj,String messageType,List<String> personIdList, String practiceID,String userID, String ProviderName, String locationName, String applicationName, String encounterType,String senderType,String encounterId,String attachmentName,String documentID) throws Throwable{
		String comm_id = null;
		try{
			SecureMessage securemessage = new SecureMessage();
			Message message = new Message();
			Recipient recipient = new Recipient();
			Attachment attachment = new Attachment();
			List<Recipient> recipientList = new ArrayList<Recipient>();
			List<Message> messageList = new ArrayList<Message>();
			List<Attachment> attachmentList = new ArrayList<Attachment>();
						
			if(messageType.contains("Bulk")){
				for(int i = 0; i < personIdList.size(); i++)
				 {
	                String personId = personIdList.get(i).toString();
	                recipient.setId(personId);

	                String first_name = DBUtils.executeQueryOnDB("NGCoreDB","select first_name from person where person_id='"+personId+"'");
	                String last_name = DBUtils.executeQueryOnDB("NGCoreDB","select last_name from person where person_id='"+personId+"'");	                
	                recipient.setName(last_name + ", " +first_name);
	                recipient.setType("PatientGroup");
	                
	                recipientList.add(recipient);
	                recipient = new Recipient();
	            }
			}
           
			String userIdQuery="select top 1 user_id from user_mstr where practice_id='"+practiceID+"'";
			String communicationMessageID =UUID.randomUUID().toString().toUpperCase();
			
			message.setId(communicationMessageID);
			
			if(messageType.equalsIgnoreCase("SentByOnlineProfile")){
				String SecureMessageOnlineProfile = PropertyLoaderObj.getProperty("SecureMessageOnlineProfile");
				message.setRoutingRuleName(SecureMessageOnlineProfile);
				message.setRoutingRuleType("1");
				message.setRoutingRuleId(DBUtils.executeQueryOnDB("NGCoreDB","select row_id from ngweb_alias where name='"+SecureMessageOnlineProfile+"'"));				
			} else if(messageType.contains("ReplyToASKAUsingAliasName")){
				String SecureMessageAlias = PropertyLoaderObj.getProperty("SecureMessageAlias");
				message.setRoutingRuleType("0");
				message.setAliasName(SecureMessageAlias);
				message.setRoutingRuleName("RoutingRuleName Check");
			} else
				message.setRoutingRuleType("-1");
			
			if(userID.isEmpty()){
				userID = DBUtils.executeQueryOnDB("NGCoreDB",userIdQuery);}
			    String userFirstName =DBUtils.executeQueryOnDB("NGCoreDB","select first_name from user_mstr where user_id='"+userID+"'");
			    String userLastName =DBUtils.executeQueryOnDB("NGCoreDB","select last_name from user_mstr where user_id='"+userID+"'");	
			
			    message.setSenderName(userFirstName+" "+userLastName);
			    message.setSenderId(userID);
			
			if(messageType.contains("ReplyToASKAUsingAliasName"))
			    message.setRoutingRuleId(userID);
			    
			if(encounterType.equalsIgnoreCase("OriginalUnlockedEncounter"))
				message.setOriginalId(communicationMessageID);
				
			if(messageType.contains("ReplyToPortal")){
				message.setParentId(messageType.substring(13).toUpperCase());
			}
			
			if(messageType.contains("ReplyToASKAUsingAliasName")){
				message.setParentId(messageType.substring(25).toUpperCase());
			}
			
			if(messageType.contains("ReplyToPortal")){					
		    	String subject = DBUtils.executeQueryOnDB("NGCoreDB","select subject from ngweb_communications where comm_id ='"+messageType.substring(13)+"'");
		    	message.setSubject("RE: "+subject);
			} else if (messageType.contains("ReplyToASKAUsingAliasName")){					
		    	String subject = DBUtils.executeQueryOnDB("NGCoreDB","select subject from ngweb_communications where comm_id ='"+messageType.substring(25)+"'");
		    	message.setSubject("RE: "+subject);
			}
			else
				message.setSubject("Subject" + (new Date()).getTime());
			message.setBody(messageType +"Body" + (new Date()).getTime());
			
			if(messageType.equalsIgnoreCase("DelayedDelivery"))
				Log4jUtil.log("Delayed Delivery should be set");
			else
				message.setSentTimestamp(sdf.format(new Date()));
			
			if(messageType.contains("ReplyToPortal")){
				message.setCategory("Medication Questions Category");
				message.setRepliedWhenTimestamp(sdf.format(new Date()));
			}else if (messageType.contains("ReplyToASKAUsingAliasName")){
				message.setCategory("Medication Questions Category");
				message.setRepliedWhenTimestamp(sdf.format(new Date()));
			}
			else
				message.setCategory("Practice Initiated");
			message.setIsClinical(true);
			message.setRecipients(recipientList);
			
			if(messageType.contains("Bulk")){
				message.setIsBulk(true); 
				List<String> reportNames = new ArrayList<String>();
				reportNames.add("AutoBulkReport");
				message.setReportNames(reportNames);
			}
			
			if(messageType.equalsIgnoreCase("Bulk1")){
				message.setPriority("Normal");
				message.setIsUnreadNotificationRequested(true);
				message.setUnreadNotificationInterval(15);
				message.setCanReply(true);
			}
			
			if(messageType.equalsIgnoreCase("Bulk2")){
				message.setPriority("High");
				message.setIsReadReceiptRequested(true);
			}
			
			if(attachmentName.equalsIgnoreCase("PatientEducation")){
			attachment.setAttachmentId(UUID.randomUUID().toString().toUpperCase());
			attachment.setDocumentId(documentID);
			attachment.setName(DBUtils.executeQueryOnDB("NGCoreDB","select document_desc from patient_education where document_id ='"+documentID+"'"));
			attachment.setType("PatientEducation");
			String format = DBUtils.executeQueryOnDB("NGCoreDB","select file_format from patient_education where document_id ='"+documentID+"'");
			if(format.contains("HTM"))
				format = "HTML";
			attachment.setFormat(format);
			attachment.setContentBytes("2500");
			
			attachmentList.add(attachment);			
			message.setAttachments(attachmentList);
			}
			else if(attachmentName.equalsIgnoreCase("PatientImage")){
				attachment.setAttachmentId(UUID.randomUUID().toString().toUpperCase());
				attachment.setDocumentId(documentID);
				String imageName = DBUtils.executeQueryOnDB("NGCoreDB","select orig_image_file from patient_images where image_id ='"+documentID+"'");
				attachment.setName(imageName);
				attachment.setType("EmrImage");
				attachment.setFormat(imageName.substring(imageName.lastIndexOf(".")).replace(".", ""));
				attachment.setContentBytes("2500");
				
				attachmentList.add(attachment);			
				message.setAttachments(attachmentList);
			}
			
			messageList.add(message);
			
			securemessage.setSenderType(senderType);
			securemessage.setEncounterType(encounterType);
			
			if(encounterType.equalsIgnoreCase("ExistingEncounter"))
				securemessage.setEncounterId(encounterId);
			
			securemessage.setRenderingProviderId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForRenderingProvider.replace("ProviderName",ProviderName)));
			securemessage.setLocationId(DBUtils.executeQueryOnDB("NGCoreDB",strSqlQueryForLocation.replace("locationName", locationName)));
			securemessage.setApplicationName(applicationName);
			securemessage.setMessages(messageList);
			
			if(messageType.contains("ReplyToPortal")){
				securemessage.setMessageType("1");
			}
			if(messageType.contains("ReplyToASKAUsingAliasName")){
				securemessage.setMessageType("1");
			}
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);				
	        String requestbody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(securemessage);
	        Log4jUtil.log("Secure Message request body \n"+requestbody);
			
			String secureMessageURL =apiRoutes.valueOf("PostSecureMessage").getRouteURL(); 
			String finalURL = EnterprisebaseURL +secureMessageURL;
			NGAPIUtils.setupNGHttpPostRequest("EnterpriseGateway",finalURL,requestbody , 200);
			comm_id = message.getId();
			Log4jUtil.log("Bulk Communication Message created with id "+comm_id);
			
	} catch (Exception e) {
		Log4jUtil.log(e.getMessage());
    }
		return comm_id;
	}
	
}
