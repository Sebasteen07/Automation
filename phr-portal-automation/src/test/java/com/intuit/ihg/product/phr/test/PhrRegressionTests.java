package com.intuit.ihg.product.phr.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.common.utils.monitoring.TestStatusReporter;
import com.intuit.ihg.product.object.maps.phr.page.PhrAlleryPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrConditionsAndDiagnosesPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.PhrLoginPage;
import com.intuit.ihg.product.object.maps.phr.page.phrEmergencyContactPage.PhrEmergencyContactPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.LaboratoryAndTestResultPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.PhrHealthInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.VitalSignsPage;
import com.intuit.ihg.product.object.maps.phr.page.phrRegistrationInformationPage.PhrRegistrationInformationPage;
import com.intuit.ihg.product.phr.utils.Phr;
import com.intuit.ihg.product.phr.utils.PhrConstants;
import com.intuit.ihg.product.phr.utils.PhrTestcasesData;

public class PhrRegressionTests extends BaseTestNGWebDriver {

	@AfterMethod
	public void logTestStatus(ITestResult result) {
		TestStatusReporter.logTestStatus(result.getName(), result.getStatus());
	}
	
	/**
	 * @Author: Kiran_GT
	 * @Date: 07/22/2013
	 * @StepsToReproduce: PHR login Click on Health Information-->Medication
	 *                    Add Medication and Validate Added Medication
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddMedication() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click Medications Link");
		PhrHealthInformationPage pPhrHealthInformationPage=pPhrHomePage.clickMedications();

		log("Set the Medication");
		pPhrHealthInformationPage.setMedication(PhrConstants.MedicationName);

		log("step 5:Add the Medication");
		pPhrHealthInformationPage.addMedication();

		log("step 6:Verify the Added Medication");
		pPhrHealthInformationPage.verifyAddedMedication();

	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/22/2013
	 * @StepsToReproduce: PHR login Click on Health Information-->Medication
	 *                    Validate Rows in Medication Table and Remove Medications
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRemoveMedication() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click Medications Link");
		PhrHealthInformationPage pPhrHealthInformationPage=pPhrHomePage.clickMedications();

		log("step 5:Remove the Medication Values");
		pPhrHealthInformationPage.removeValues();


	}
	/**
	 * @Author:- Gajendran
	 * @Date:-7/22/2013
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Click on Health Information
	 * Select Allergy
	 * Add Allergy
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAllergy() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click on Health Information");
		PhrAlleryPage phrallergy=pPhrHomePage.clickHelthInformation();

		log("step 5:Select Allergy");
		phrallergy.selectAllergy();

		log("step 6:Add Allergy");
		phrallergy.addAllergy();
	}

	/**
	 * @Author:- Gajendran
	 * @Date:-7/22/2013
	 * @StepsToReproduce:
	 * LogIn to PHR portal using ccduser from excel
	 * Click on Health Information
	 * Select Allergy
	 * Add Allergy
	 * =============================================================
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testRemoveAllergy() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click on Health Information");
		PhrAlleryPage phrallergy=pPhrHomePage.clickHelthInformation();

		log("step 5:Select Allergy");
		phrallergy.selectAllergy();

		log("step 6:Remove Allergy");
		phrallergy.RemoveValues();
	}

	/**
	 * @Author: Kiran_GT
	 * @Date: 07/23/2013
	 * @StepsToReproduce: PHR login Click on Profile Tab-->Click on Emergency Contact Link
	 * 					  Add Emergency Contact Information
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testEmergencyContact() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(),phrtestcasesData.getccdUserPassword());

		log("step 4:Click On Profile Tab ");
		PhrRegistrationInformationPage pPhrRegistrationInformationPage= pPhrHomePage.clickProfile();

		log("Click on Emergency Contact Link");
		PhrEmergencyContactPage pPhrEmergencyContactPage=pPhrRegistrationInformationPage.clickEmergencyContact();

		log("step 5:Set Emergency Contact Fields");
		pPhrEmergencyContactPage.setEmergencyContactFields();

		log("step 6:Verify for Emergency Contact Added");
		IHGUtil.waitForElement(driver,10,pPhrEmergencyContactPage.emergencyContacttxt);
		verifyEquals(pPhrEmergencyContactPage.emergencyContacttxt.getText().trim(),"Emergency Contact updated successfully");


	}

	/**
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-23-2013
	 * @UserStrory ID in Rally : US6504, US6503
	 * @StepsToReproduce:
	 * Login to phr
	 * Navigate to 'Health Information' page
	 * Click on "Conditions and Diagnoses"
	 * Click on "Add conditions"
	 * Add Conditions and save
	 * Verify whether the Conditions s added
	 * Remove the conditions added.
	 * verify whether the condition is removed.
	 *  
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAndRemoveConditionsAndDiagnostics() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(),phrtestcasesData.getPassword());

		log("Step 4 : Navigate to 'Health Inormation' Page.");
		PhrHealthInformationPage healthInfoPage = pPhrHomePage.clickOnHealthInformationTab();

		log("Verify Health Information page is displayed.");
		verifyTrue(healthInfoPage.checkHealthInfoPage());

		log("Step 5: Navigate to 'Conditions and Diagnoses' page.");
		PhrConditionsAndDiagnosesPage condAndDiagnosesPage = healthInfoPage.clickOnConditionsAndDiagnoses();
		condAndDiagnosesPage.removeDiagnoses(PhrConstants.diagnoses);

		log("Verify 'Conditions and Diagnoses' Page is Displayed.");
		condAndDiagnosesPage.checkconditionsAndDiagnosesPage();

		log("Step 6 : Adding Conditions");
		condAndDiagnosesPage.clickOnAddConditions();

		log("verify whether the condition is added");
		verifyEquals(verifyTextPresent(driver,PhrConstants.diagnoses), true, "The condition is not added in 'Conditions and Disgnoses' Page.");

		log("Step 7 : Remove the added Diagnoses.");
		condAndDiagnosesPage.removeDiagnoses(PhrConstants.diagnoses);

		log("verify whether the Diagnoses is removed Successfully.");
		verifyEquals(condAndDiagnosesPage.isDiagnosesPresent(PhrConstants.diagnoses), false, "Diagnoses is not removed properly.");

	}

	/**
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-24-2013
	 * @UserStrory ID in Rally : US6501, US6502
	 * @StepsToReproduce:
	 * Login to phr
	 * Navigate to 'Health Information' page
	 * Click on "Laboratory Test Result" link.
	 * Click on "Add Test Result"
	 * Add test result and save
	 * Verify whether the result s added
	 * Remove the result added.
	 * verify whether the result is removed.
	 *  
	 */
	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAndRemoveLaboratoryAndTestResults() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3: Login to PHR");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(),phrtestcasesData.getPassword());

		log("Step 4 : Navigate to 'Health Inormation' Page.");
		PhrHealthInformationPage healthInfoPage = pPhrHomePage.clickOnHealthInformationTab();

		log("Verify Health Information page is displayed.");
		verifyTrue(healthInfoPage.checkHealthInfoPage());

		log("Step 5 : Navigate to 'Laboratory and Test Result'");
		LaboratoryAndTestResultPage labResultPage = healthInfoPage.clickOnLaboratoryAndTestResultLink();
		verifyTrue(labResultPage.checkLaboratoryAndTestResult(), "'Laboratory and Test Result' page is not displayed properly.");

		labResultPage.removeTestResult(PhrConstants.testName);

		log("Step 6 : Add Laboratory Test Result");
		labResultPage.addLaboratoryTestResult(PhrConstants.testName, PhrConstants.resultInterpretation);

		log("Verify whether the test result is added properly");
		verifyEquals(verifyTextPresent(driver, PhrConstants.testName), true, "Test result is not added properly");

		labResultPage.removeTestResult(PhrConstants.testName);

		log("Verify whether the test result is removed properly");
		verifyEquals(verifyTextPresent(driver, PhrConstants.testName), false, "Test result is not removed properly");

	}
	
	/**
	 * @throws Exception 
	 * @Author:-bbinisha
	 * @Date :- 07-25-2013
	 * @UserStrory ID in Rally : US6499, US6500
	 * @StepsToReproduce:
	 * Login to phr
	 * Navigate to 'Health Information' page
	 * Click on "Vital Signs" link.
	 * Click on "Add Vital Sign"
	 * Add test Vital Signs and save
	 * Verify whether the Vital Signs s added
	 * Remove the Vital Signs added.
	 * verify whether the Vital Signs is removed.
	 *  
	 */
	@Test(enabled = true, groups = { "AcceptanceTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAndRemoveVitalSigns() throws Exception {

		log("Test Case: testCCDImportThroughEHDC");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getccdUserName());
		log("Password: "+phrtestcasesData.getccdUserPassword());

		log("step 3: Login to PHR");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(),phrtestcasesData.getPassword());

		log("Step 4 : Navigate to 'Health Inormation' Page.");
		PhrHealthInformationPage healthInfoPage = pPhrHomePage.clickOnHealthInformationTab();

		log("Verify Health Information page is displayed.");
		verifyTrue(healthInfoPage.checkHealthInfoPage());
		
		log(" Step 5 : Navigate to Vital Signs page");
		VitalSignsPage vitalSigns = healthInfoPage.clickOnVitalSigns();
		
		log("Verify the Vital Signs Page is displayed properly.");
		verifyTrue(vitalSigns.checkVitalSignsPage(), "Vital Signs Page is not displayed properly.");
				
		vitalSigns.removeVitalSigns(PhrConstants.vitalSign);
		
		log(" Step 6 : Add the vital signs. ");
		vitalSigns.addVitalSigns(PhrConstants.weight);
		
		log("Verify whether the vital Sign is added properly.");
		vitalSigns.isAdded(PhrConstants.vitalSign);
		
		log(" Step 7 : Remove the vital Sign.");
		vitalSigns.removeVitalSigns(PhrConstants.vitalSign);
		
		log("Verify whether the Vital sign is removed properly.");
		verifyEquals(verifyTextPresent(driver, PhrConstants.vitalSign), false, "Vital sign is not removed properly.");
	}
	
	
	/**
	 * @Author: Kiran_GT
	 * @Date: 07/25/2013
	  * @StepsToReproduce: PHR login Click on Health Information-->SurgeriesandProcedures
	 *                    Add SurgeriesandProcedures and Validate Added SurgeriesandProcedures
	 *                    Click on Health Information-->SurgeriesandProcedures
	 *                    Validate Rows in SurgeriesandProcedures Table and Remove SurgeriesandProcedures
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAndRemoveSurgeriesAndProcedures() throws Exception {

		log("Test Case: testAddAndRemoveSurgeriesAndProcedures");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getUsername());
		log("Password: "+phrtestcasesData.getPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(),phrtestcasesData.getPassword());

		log("step 4:Click Surgeries and Procedures Link");
		PhrHealthInformationPage pPhrHealthInformationPage=pPhrHomePage.clickSurgeriesandProcedures();
		
		log("step 5:Add Surgeries and Procedures Link");
		pPhrHealthInformationPage.addSurgeriesAndProcedures();
		
		log("step 6:Verify the Added Surgery");
		pPhrHealthInformationPage.verifyAddedSurgery();
		
		log("step 7:Click Surgeries and Procedures Link");
		pPhrHealthInformationPage=pPhrHomePage.clickSurgeriesandProcedures();
		
		log("step 8:Remove the Sugery Values");
		pPhrHealthInformationPage.removeSurgeriesAndProcedures();
		
	}
	
	
	
	/**
	 * @Author: Kiran_GT
	 * @Date: 07/30/2013
	  * @StepsToReproduce: PHR login Click on Health Information-->Immunizations
	 *                    Add Immunizations and Validate Added Immunizations
	 *                    Click on Health Information-->Immunizations
	 *                    Validate Rows in Immunizations Table and Remove Immunizations
	 *                    ====================================
	 *                    =========================
	 * @AreaImpacted :
	 * @throws Exception
	 */

	@Test(enabled = true, groups = { "RegressionTests" }, retryAnalyzer = RetryAnalyzer.class)
	public void testAddAndRemoveImmunization() throws Exception {

		log("Test Case: testAddAndRemoveImmunization");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");
		Phr phr=new Phr();
		PhrTestcasesData phrtestcasesData=new PhrTestcasesData(phr);

		log("URL: "+phrtestcasesData.geturl());
		log("USER NAME: "+phrtestcasesData.getUsername());
		log("Password: "+phrtestcasesData.getPassword());

		log("step 3:LogIn");
		PhrLoginPage loginpage = new PhrLoginPage(driver,phrtestcasesData.geturl());
		PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(),phrtestcasesData.getPassword());

		log("step 4:Click On Immunizations Link");
		PhrHealthInformationPage pPhrHealthInformationPage=pPhrHomePage.clickImmunizations();
		
		log("step 5:Add Immunizations");
		pPhrHealthInformationPage.addImmunizations();
		
		log("step 6:Verify the Added Immunizations");
		pPhrHealthInformationPage.verifyAddedImmunization();
		
		log("step 7:Click On Immunizations Link");
		pPhrHealthInformationPage=pPhrHomePage.clickImmunizations();
		
		log("step 8:Remove the Immunizations Values");
		pPhrHealthInformationPage.removeImmunizations();
		
		
	}
	
}
