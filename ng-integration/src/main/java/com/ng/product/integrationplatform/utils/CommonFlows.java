// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
/************************
 * 
 * @author Narora
 * 
 ************************/
package com.ng.product.integrationplatform.utils;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.DocumentsPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.MedicalRecordSummariesPage;
import com.medfusion.product.object.maps.patientportal2.page.CcdPage.NGCcdViewerPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.ng.product.integrationplatform.flows.NGAPIFlows;

public class CommonFlows {

	static int arg_timeOut=900; 
	
   public static void verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyFileLoader PropertyLoaderObj,String entityidentifier,String integrationID, String type) throws Throwable{
		
	    entityidentifier= entityidentifier.trim().replace("\t", "");
		Log4jUtil.log("Step Begins: Verify the status of MF agent job for "+type);
        String emailStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+entityidentifier+"'");
		String jobStatus1 =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+entityidentifier+"')");

		Log4jUtil.log("Status of MF agent job "+jobStatus1);
		if(!jobStatus1.isEmpty()){
			if(jobStatus1.equalsIgnoreCase("Pending"))
				Log4jUtil.log("Step End: Request is sent to RSDK and inprocess with job status "+jobStatus1);
			else if(jobStatus1.equalsIgnoreCase("COMPLETED"))
				Log4jUtil.log(type+" is received");
			}
		else{
			Log4jUtil.log("Please check Bad request or MF agent is not working");
		}
			
        String jobID = DBUtils.executeQueryOnDB("MFAgentDB","select jobid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+entityidentifier+"')");
		
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB","select messageid from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+entityidentifier+"')");
		
		Log4jUtil.log("Step Begins: Setup Oauth client" + PropertyLoaderObj.getResponsePath());
		RestUtils.oauthSetup(PropertyLoaderObj.getOAuthKeyStore(), PropertyLoaderObj.getOAuthProperty(), PropertyLoaderObj.getOAuthAppToken(), PropertyLoaderObj.getOAuthUsername(),PropertyLoaderObj.getOAuthPassword());

		Thread.sleep(7000);
		Log4jUtil.log("Step Begins: Do get processing status call and verify the processing time of "+type+" to be received");

//		String processingUrl=PropertyLoaderObj.getProcessingURL().replaceAll("integrationID", integrationID).replaceAll("jobID", jobID.toLowerCase());
//		Boolean completed = PatientRegistrationUtils.checkMessageProcessingOntime(processingUrl, PropertyLoaderObj.getResponsePath());
//		Assert.assertTrue(completed, "Message processing was not completed in time");
	
		Thread.sleep(60000);
		Log4jUtil.log("Step Begins: Verify the processing status of MF agent job after the request reached to RSDK");
		String ccdaStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+entityidentifier+"'");
		
		if(ccdaStatus.equalsIgnoreCase("Pending")){
			for (int i = 0; i < arg_timeOut; i++) {
				ccdaStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus_entity where entityidentifier ='"+entityidentifier+"'");
				if (ccdaStatus.equalsIgnoreCase("COMPLETED")) {
					Log4jUtil.log("Step End: Request is sent to RSDK successfully");
	                break;
	            } else {
	                if (i == arg_timeOut - 1)
	                    Thread.sleep(1000);
	            }
	        }
		}
		
		CommonUtils.VerifyTwoValues(ccdaStatus,"equals","COMPLETED");
		
		String jobStatus =DBUtils.executeQueryOnDB("MFAgentDB","select status from processingstatus where id = (select processingstatus_id from processingstatus_entity where entityidentifier ='"+entityidentifier+"')");
		CommonUtils.VerifyTwoValues(jobStatus,"equals","COMPLETED");
		Log4jUtil.log("Step End: The processing status of MF agent job is "+jobStatus+" Request is sent to RSDK");		
	}
   
   public static void verifyCCDProcessingStatus(PropertyFileLoader PropertyLoaderObj,String person_id,String practice_id,String integrationID,int ccdaType) throws Throwable{

	String ccdaRequestsQuery="select processing_status from pxp_ccda_requests where person_id ='"+person_id.trim()+"' and type ='"+ccdaType+"' and practice_id='"+practice_id.trim()+"'";
	String request_id =DBUtils.executeQueryOnDB("NGCoreDB","select request_id from pxp_ccda_requests where person_id ='"+person_id.trim()+"' and type ='"+ccdaType+"' and practice_id='"+practice_id.trim()+"'");
	String ccdaDocumentQuery="select delete_ind from pxp_documents where request_id ='"+request_id.trim()+"'";
	String processing_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaRequestsQuery);

	if(processing_status.equals("1")){
		    Log4jUtil.log("Processing status is "+processing_status+" i.e. CCD is requested by patient from Portal.");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaRequestsQuery);
           if (processing_status.equals("2")) {
        	   Log4jUtil.log("Processing status is "+processing_status+" i.e. CCD is generated from Rosetta.");
               break;
           } else {
               if (i == arg_timeOut - 1)
                   Thread.sleep(1000);
           }
       }
		CommonUtils.VerifyTwoValues(processing_status,"equals","2");
	}
	if((processing_status.equals("2"))||(processing_status.equals("4"))){
		if(processing_status.equals("2")){
        	Log4jUtil.log("Processing status is "+processing_status+" i.e. CCD is generated from Rosetta.");
        	String document_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaDocumentQuery);
        	if(!(document_status.equals("0"))||!(document_status.equals("false"))){
        	for (int i = 0; i < arg_timeOut; i++) {
        	   document_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaDocumentQuery);
               if (document_status.equals("0")||document_status.equals("false")) {
                   Log4jUtil.log("Document is present in document table");
                   break;
               }else {
                  if (i == arg_timeOut - 1)
                        Thread.sleep(1000);
                   }
              }}
        	CommonUtils.VerifyTwoValues(document_status,"equals","false");
		}
		
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaRequestsQuery);
           if (processing_status.equals("4")) {
        	   Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. CCD has been posted to MF agent");
               break;
           } else {
               if (i == arg_timeOut - 1)
                   Thread.sleep(1000);
           }
       }
//		CommonUtils.VerifyTwoValues(processing_status,"equals","4");
		
		verifyMFJOBStatusWithoutValidatingGetProcessingStatusCall(PropertyLoaderObj,request_id,integrationID,"CCD");
		for (int i = 0; i < arg_timeOut; i++) {
			processing_status =DBUtils.executeQueryOnDB("NGCoreDB",ccdaRequestsQuery);
	       if (processing_status.equals("6")) {
	    	   Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. RSDK has posted CCD to portal");
	           break;
	       } else {
	           if (i == arg_timeOut - 1)
	               Thread.sleep(1000);
	       }
	     }
	}
	else if(processing_status.equals("6")){
		Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. RSDK has posted CCD to portal");
	}
	else if(processing_status.equals("3")){
		Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. Failed to generate CCD from Rosetta");
	}
	else if(processing_status.equals("5")){
		Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. Failed to post CCD to MF Agent");
	}
	else if(processing_status.equals("7")){
		Log4jUtil.log("Step End: Processing status is "+processing_status+" i.e. Failed to deliver CCD to Portal");
	}
	CommonUtils.VerifyTwoValues(processing_status,"equals","6");
   }
   
   public static void IsCCDReceived(WebDriver driver,String URL,String username, String password, String personType,String practiceName) throws InterruptedException{
	   Log4jUtil.log("Step Begins: Login to Portal");
       NGLoginPage loginPage = new NGLoginPage(driver, URL);
	   JalapenoHomePage homePage = loginPage.login(username, password);
	
	   if(personType.equalsIgnoreCase("Dependent")||personType.equalsIgnoreCase("TrustedRepresentative")){
	      homePage.faChangePatient();
	      Thread.sleep(10000);}
	   else if(personType.equalsIgnoreCase("MultiPractice")){
		   homePage.switchToPractice(practiceName);
		   Thread.sleep(40000);
	   }

	   Log4jUtil.log("Step Begins: Click on messages solution");
	   JalapenoMessagesPage messagesPage = homePage.showMessages(driver);
	   Assert.assertTrue(messagesPage.areBasicPageElementsPresent(), "Inbox failed to load properly.");

	   Log4jUtil.log("Step Begins: Validate message subject and send date");
	   Thread.sleep(1000);
	   Log4jUtil.log("Message Date" + IHGUtil.getEstTiming());
	   Assert.assertTrue(messagesPage.isMessageDisplayed(driver, "You have a new health data summary"));
	   Log4jUtil.log("CCD sent date & time is : " + messagesPage.returnMessageSentDate());

	   Log4jUtil.log("Step Begins: Click on link View health data");
	   NGCcdViewerPage ngCcdPage = messagesPage.findNGCcdMessage(driver);

	   Log4jUtil.log("Step Begins: Verify if CCD Viewer is loaded");
       Assert.assertTrue(ngCcdPage.areBasicPageElementsPresent());
	
       Log4jUtil.log("Step Begins: Verify all elements are present in CCD Viewer and click Close Viewer");
	   ngCcdPage.verifyCCDPageElementsPresent();
	   if(personType.equalsIgnoreCase("HavingSensitiveEncounterONDemand"))
	       ngCcdPage.verifySensitiveONDemandCCDElementsContent(driver);
	   else if(personType.equalsIgnoreCase("HavingSensitiveEncounterMSU"))
	       ngCcdPage.verifySensitiveMSUCCDElementsContent(driver);
	   else if(personType.equalsIgnoreCase("EncounterHavingALLData"))
		   ngCcdPage.verifyCCDElementsContent(driver,"guaifenesin","Lipitor 10 mg tablet","Chest pain, unspecified","Panel Description: Glucose [Mass/volume] in Serum or Plasma","Chest pain, unspecified");
	   else if(personType.equalsIgnoreCase("HavingUnSignedOffResult"))
		   ngCcdPage.verifyCCDElementsContent(driver,"guaifenesin","Lipitor 10 mg tablet","Chest pain, unspecified","","Chest pain, unspecified");
	   
	   messagesPage = ngCcdPage.closeCcd(driver);
	
	   Log4jUtil.log("Step Begins: Archive the CCDA message");
	   messagesPage.archiveMessage();	
	   
	   Log4jUtil.log("Step Begins: Logout");
	   homePage.LogoutfromNGMFPortal();
	   Log4jUtil.log("CCD is received to patient");	
    }
   
   public static void requestCCD(WebDriver driver,String URL,String username, String password, String personType,String practiceName) throws InterruptedException{	
	   Log4jUtil.log("Step Begins: Login to Portal");
       NGLoginPage loginPage = new NGLoginPage(driver, URL);
	   JalapenoHomePage homePage = loginPage.login(username, password);
	
	   if(personType.equalsIgnoreCase("Dependent")||personType.equalsIgnoreCase("TrustedRepresentative")){
	      homePage.faChangePatient();
	      Thread.sleep(10000);}
	   else if(personType.equalsIgnoreCase("MultiPractice")){
		   homePage.switchToPractice(practiceName);
		   Thread.sleep(40000);
	   }
	   
       Log4jUtil.log("Step Begins: Go to  Health Record Summaries");
       MedicalRecordSummariesPage MedicalRecordSummariesPageObject = homePage.clickOnMedicalRecordSummaries(driver);
       Assert.assertTrue(MedicalRecordSummariesPageObject.areBasicPageElementsPresent(), "Failed to Load Health Record Summaries ");
	
	   Log4jUtil.log("Step Begins: Click on Request Health Record");
	   MedicalRecordSummariesPageObject.selectHealthRecordRequestButton();
	   Thread.sleep(6000);
	
	   Log4jUtil.log("Step Begins: Request complete Health Record");
       MedicalRecordSummariesPageObject.requestCompleteRecord();
			
	   Log4jUtil.log("Step Begins: Close the onDemand PopUp");
	   MedicalRecordSummariesPageObject.closeOnDemandPopUpButton();
	
	   Log4jUtil.log("Step Begins: Logout");
	   homePage.LogoutfromNGMFPortal();
	   Log4jUtil.log("ON Demand CCD is requested by patient successfully");
   }
   
   public static void deactivatePatient(WebDriver driver, String URL, String Usermame, String Password, String FName, String LName) throws Exception{
	    Log4jUtil.log("Step Begins: Login to Practice Portal");
		Thread.sleep(3000);
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, URL);
		PracticeHomePage practiceHome = practiceLogin.login(Usermame, Password);
		Log4jUtil.log("Step Begins: Click on Search");
		PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
		Log4jUtil.log("Step Begins: Search for Patient");
		patientSearchPage.searchForPatientInPatientSearch(FName,LName);
		patientSearchPage.clickOnPatient(FName,LName);
		Log4jUtil.log("Step Begins: Deactivate the searched patient");
		patientSearchPage.deactivatePatient();
		Log4jUtil.log("Step Begins: Verify the Patient is deactivated from practive portal");
		patientSearchPage.verifyDeactivatedPatient(FName, LName);
   }
   
   public static void deletePatient(WebDriver driver, String URL, String Usermame, String Password, String FName, String LName) throws Exception{
	   Log4jUtil.log("Step Begins: Login to Practice Portal");
	   PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, URL);
	   PracticeHomePage practiceHome = practiceLogin.login(Usermame, Password);
	   Log4jUtil.log("Step Begins: Click on Search");
	   PatientSearchPage patientSearchPage = practiceHome.clickPatientSearchLink();
	   Log4jUtil.log("Step Begins: Search for Patient");
	   patientSearchPage.searchForPatientInPatientSearch(FName, LName);
	   patientSearchPage.clickOnPatient(FName, LName);
	   Log4jUtil.log("Step Begins: Delete the searched patient");
	   patientSearchPage.deletePatient();
	   patientSearchPage.verifyDeletedPatient(FName, LName);
   }
   
   public static String addDataToCCD(String locationName, String providerName, String person_id, String practiceId) throws Throwable{
	   Log4jUtil.log("Step Begins: Adding Test data to patient CCD "+person_id);
	   Log4jUtil.log("Step Begins: Add Chart to patient");
	   NGAPIFlows.addCharttoProvider(locationName,providerName,person_id); 
	
	   Log4jUtil.log("Step Begins: Add Encounter to patient chart");
	   String encounter_id = NGAPIFlows.addEncounter(locationName,providerName,person_id); 
	
	   Log4jUtil.log("Step Begins: Add Diagnosis to created encounter");
	   String diagnosis_id = NGAPIFlows.addDiagnosis(practiceId, person_id, encounter_id);
	
	   Log4jUtil.log("Step Begins: Add Medication to created encounter");
	   String medication_id = NGAPIFlows.addMedication(practiceId,locationName,providerName, "Active", person_id, encounter_id,"R07.9",286939);
	
	   Log4jUtil.log("Step Begins: Add allergy to created encounter");
	   String allergy_id = NGAPIFlows.addAllergy(locationName,providerName,person_id, encounter_id,"1000",2);
	
	   Log4jUtil.log("Step Begins: Add Problem to patient chart");
	   String problem_id = NGAPIFlows.addProblem(locationName,providerName,  person_id,"420543008","55561003","Active");
	
	   Log4jUtil.log("Step Begins: Add procedure to created encounter and diagnosis");
	   String procedure_id = NGAPIFlows.addProcedure(locationName,providerName, person_id, encounter_id,diagnosis_id);	

	   Log4jUtil.log("Step Begins: Add Immunization to created encounter");
	   String immunization_id = NGAPIFlows.addNewImmunizationsOrder(locationName,providerName,person_id,encounter_id);
	
	   Log4jUtil.log("Step Begins: Add lab Order and lab results to patient CCD");
	   String labOrder_id = NGAPIFlows.addLabOrder(locationName,providerName, person_id, encounter_id);		
	   String labOrderTest_id = NGAPIFlows.addLabOrderTest(person_id, labOrder_id,"NG001032");
	   String ObsPanel_id = NGAPIFlows.addObsPanel(person_id, labOrder_id);
	   String labResult_id = NGAPIFlows.addLabResult(person_id, ObsPanel_id,"75325-1");
	   String updatelabOrder_id = NGAPIFlows.updateLabOrder(locationName, providerName, person_id, labOrder_id);
	   Log4jUtil.log("Step End: Test data added to patient CCD successfully to encounter "+encounter_id);
	   return encounter_id;	   
   }
}