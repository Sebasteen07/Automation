package com.intuit.ihg.product.practice.test;

import org.testng.annotations.Test;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.PracticeHomePage;
import com.intuit.ihg.product.practice.page.PracticeLoginPage;
import com.intuit.ihg.product.practice.page.documentManagement.documentManagementpage;
import com.intuit.ihg.product.practice.utils.Practice;
import com.intuit.ihg.product.practice.utils.PracticeConstants;
import com.intuit.ihg.product.practice.utils.PracticeTestData;
import com.intuit.ihg.product.practice.utils.PracticeUtil;
import com.intuit.ihg.product.practice.utils.ReadFilePath;

public class PracticePortalRegressionTests extends BaseTestNGWebDriver {
	
	ReadFilePath path = new ReadFilePath();

	public PracticePortalRegressionTests() throws Exception {
		path.getFilepath("documents");
	}

	/**
	 * @author bbinisha
	 * @Date : 07-12-2013
	 * @UserStory : US6420
	 * @Steps To Reproduce : 
	 * Login to Practice Portal
	 * Navigate to "Document Management".
	 * Add a new Patient
	 * Click on "Browse" and upload a doc.
	 * Verify whether document is uploaded successfully.
	 *
	 */
	@Test(enabled = false, groups = {"AcceptanceTests"} )
	public void testUploadDoc() throws Exception {

		log("Test Case: TestLoginLogout");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 1: Get Data from Excel");	

		// Load up practice test data
		Practice practice = new Practice();
		PracticeTestData practiceTestData =new PracticeTestData(practice);

		log("step 2: Navigate to Login page"); 
		PracticeLoginPage practiceLogin =new PracticeLoginPage(driver, practiceTestData.getUrl());

		log("step 3: Enter credentials and login");
		PracticeHomePage practiceHome = practiceLogin.login(practiceTestData.getUsername(), practiceTestData.getPassword());

		log("Step 4 : Navigate to Document Management page and click on Upload Document Link");
		documentManagementpage docManagement = practiceHome.clickOnDocManagement();
		docManagement.clickOnUploadDocLink();

		log("Step 5 : Adding a New Patient ");
		docManagement.addNewPatient(PracticeConstants.firstName, PracticeConstants.lastName, PracticeConstants.patientID, PracticeConstants.email,PracticeConstants.value, PracticeConstants.year, PracticeConstants.zipCode);

		log("Step 6 : Getting the document to upload from filepath");
		PracticeUtil pUtil = new PracticeUtil(driver);
		String value =pUtil.getFilepath(PracticeConstants.fileDirectory).concat(PracticeConstants.filename);

		log("Step 7 : Uploading the document.");
		docManagement.browseFile();
		docManagement.uploadDocument();

		log("verify whether the document uploaded successfully.");
		verifyTrue(docManagement.checkUploadSuccessMessage());
	}

	
	
}
