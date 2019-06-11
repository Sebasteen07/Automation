package com.intuit.ihg.product.phr.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ihg.product.object.maps.phr.page.PhrAlleryPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrConditionsAndDiagnosesPage;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;
import com.intuit.ihg.product.object.maps.phr.page.PhrLoginPage;
import com.intuit.ihg.product.object.maps.phr.page.phrEmergencyContactPage.PhrEmergencyContactPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.LaboratoryAndTestResultPage;
import com.intuit.ihg.product.object.maps.phr.page.phrHealthInformationPage.PhrHealthInformationPage;
import com.intuit.ihg.product.object.maps.phr.page.phrRegistrationInformationPage.PhrRegistrationInformationPage;
import com.intuit.ihg.product.phr.utils.Phr;
import com.intuit.ihg.product.phr.utils.PhrConstants;
import com.intuit.ihg.product.phr.utils.PhrTestcasesData;
import com.medfusion.common.utils.IHGUtil;

public class PhrRegressionTests extends BaseTestNGWebDriver {

		/**
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/22/2013
		 * @StepsToReproduce: PHR login Click on Health Information-->Medication Add Medication and Validate Added Medication
		 * @AreaImpacted :
		 */

		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testAddMedication() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				logStep("Click Medications Link");
				PhrHealthInformationPage pPhrHealthInformationPage = pPhrHomePage.clickMedications();

				log("Set the Medication");
				pPhrHealthInformationPage.setMedication(PhrConstants.MedicationName);

				logStep("Add the Medication");
				pPhrHealthInformationPage.addMedication();

				logStep("Verify the Added Medication");
				pPhrHealthInformationPage.verifyAddedMedication();
		}

		/**
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/22/2013
		 * @StepsToReproduce: PHR login Click on Health Information-->Medication Validate Rows in Medication Table and Remove Medications
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testRemoveMedication() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginPage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginPage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				logStep("Click Medications Link");
				PhrHealthInformationPage pPhrHealthInformationPage = pPhrHomePage.clickMedications();

				logStep("Remove the Medication Values");
				pPhrHealthInformationPage.removeValues();
		}

		/**
		 * @throws Exception
		 * @Author:- Gajendran
		 * @Date:-7/22/2013
		 * @StepsToReproduce: LogIn to PHR portal using ccduser from excel Click on Health Information Select Allergy Add Allergy
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testAddAllergy() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginPage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginPage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				logStep("Click on Health Information");
				PhrAlleryPage phrallergy = pPhrHomePage.clickHelthInformation();

				logStep("Select Allergy");
				phrallergy.selectAllergy();

				logStep("Add Allergy");
				phrallergy.addAllergy();
		}

		/**
		 * @throws Exception
		 * @Author:- Gajendran
		 * @Date:-7/22/2013
		 * @StepsToReproduce: LogIn to PHR portal using ccduser from excel Click on Health Information Select Allergy Add Allergy
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testRemoveAllergy() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				logStep("Click on Health Information");
				PhrAlleryPage phrallergy = pPhrHomePage.clickHelthInformation();

				logStep("Select Allergy");
				phrallergy.selectAllergy();

				logStep("Remove Allergy");
				phrallergy.RemoveValues();
		}

		/**
		 * @throws Exception
		 * @Author: Kiran_GT
		 * @Date: 07/23/2013
		 * @StepsToReproduce: PHR login Click on Profile Tab-->Click on Emergency Contact Link Add Emergency Contact Information
		 * @AreaImpacted :
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testEmergencyContact() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getccdUserName(), phrtestcasesData.getccdUserPassword());

				logStep("Click On Profile Tab ");
				PhrRegistrationInformationPage pPhrRegistrationInformationPage = pPhrHomePage.clickProfile();

				log("Click on Emergency Contact Link");
				PhrEmergencyContactPage pPhrEmergencyContactPage = pPhrRegistrationInformationPage.clickEmergencyContact();

				logStep("Set Emergency Contact Fields");
				pPhrEmergencyContactPage.setEmergencyContactFields();

				logStep("Verify for Emergency Contact Added");
				IHGUtil.waitForElement(driver, 10, pPhrEmergencyContactPage.emergencyContacttxt);
				assertEquals(pPhrEmergencyContactPage.emergencyContacttxt.getText().trim(), "Emergency Contact updated successfully");
		}

		/**
		 * @throws Exception
		 * @Author:-bbinisha
		 * @Date :- 07-23-2013
		 * @UserStrory ID in Rally : US6504, US6503
		 * @StepsToReproduce: Login to phr Navigate to 'Health Information' page Click on "Conditions and Diagnoses" Click on "Add conditions" Add Conditions and save
		 * Verify whether the Conditions s added Remove the conditions added. verify whether the condition is removed.
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testAddAndRemoveConditionsAndDiagnostics() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("LogIn");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(), phrtestcasesData.getPassword());

				logStep("Navigate to 'Health Inormation' Page.");
				PhrHealthInformationPage healthInfoPage = pPhrHomePage.clickOnHealthInformationTab();

				log("Verify Health Information page is displayed.");
				assertTrue(healthInfoPage.checkHealthInfoPage());

				logStep("Navigate to 'Conditions and Diagnoses' page.");
				PhrConditionsAndDiagnosesPage condAndDiagnosesPage = healthInfoPage.clickOnConditionsAndDiagnoses();
				condAndDiagnosesPage.removeDiagnoses(PhrConstants.diagnoses);

				log("Verify 'Conditions and Diagnoses' Page is Displayed.");
				condAndDiagnosesPage.checkconditionsAndDiagnosesPage();

				logStep("Adding Conditions");
				condAndDiagnosesPage.clickOnAddConditions();

				log("verify whether the condition is added");
				assertEquals(verifyTextPresent(driver, PhrConstants.diagnoses), true, "The condition is not added in 'Conditions and Disgnoses' Page.");

				logStep("Remove the added Diagnoses.");
				condAndDiagnosesPage.removeDiagnoses(PhrConstants.diagnoses);

				log("verify whether the Diagnoses is removed Successfully.");
				assertEquals(condAndDiagnosesPage.isDiagnosesPresent(PhrConstants.diagnoses), false, "Diagnoses is not removed properly.");
		}

		/**
		 * @throws Exception
		 * @Author:-bbinisha
		 * @Date :- 07-24-2013
		 * @UserStrory ID in Rally : US6501, US6502
		 * @StepsToReproduce: Login to phr Navigate to 'Health Information' page Click on "Laboratory Test Result" link. Click on "Add Test Result" Add test result
		 * and save Verify whether the result s added Remove the result added. verify whether the result is removed.
		 */
		@Test(enabled = true, groups = {"RegressionTests"}, retryAnalyzer = RetryAnalyzer.class)
		public void testAddAndRemoveLaboratoryAndTestResults() throws Exception {
				logStep("Get Data from Excel");
				Phr phr = new Phr();
				PhrTestcasesData phrtestcasesData = new PhrTestcasesData(phr);

				log("URL: " + phrtestcasesData.geturl());
				log("USER NAME: " + phrtestcasesData.getccdUserName());
				log("Password: " + phrtestcasesData.getccdUserPassword());

				logStep("Login to PHR");
				PhrLoginPage loginpage = new PhrLoginPage(driver, phrtestcasesData.geturl());
				PhrHomePage pPhrHomePage = loginpage.login(phrtestcasesData.getUsername(), phrtestcasesData.getPassword());

				logStep("Navigate to 'Health Inormation' Page.");
				PhrHealthInformationPage healthInfoPage = pPhrHomePage.clickOnHealthInformationTab();

				log("Verify Health Information page is displayed.");
				assertTrue(healthInfoPage.checkHealthInfoPage());

				logStep("Navigate to 'Laboratory and Test Result'");
				LaboratoryAndTestResultPage labResultPage = healthInfoPage.clickOnLaboratoryAndTestResultLink();
				assertTrue(labResultPage.checkLaboratoryAndTestResult(), "'Laboratory and Test Result' page is not displayed properly.");

				labResultPage.removeTestResult(PhrConstants.testName);

				logStep("Add Laboratory Test Result");
				labResultPage.addLaboratoryTestResult(PhrConstants.testName, PhrConstants.resultInterpretation);

				log("Verify whether the test result is added properly");
				assertEquals(verifyTextPresent(driver, PhrConstants.testName), true, "Test result is not added properly");

				labResultPage.removeTestResult(PhrConstants.testName);

				log("Verify whether the test result is removed properly");
				assertEquals(verifyTextPresent(driver, PhrConstants.testName), false, "Test result is not removed properly");
		}
}
