package com.intuit.ihg.product.portal.tests;

import org.openqa.selenium.WebDriver;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.MyAccountPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.familyAccount.CreatefamilymemberPage;
import com.intuit.ihg.product.object.maps.portal.page.myAccount.familyAccount.PatientsOnThisAccountPage;
import com.intuit.ihg.product.portal.utils.Portal;
import com.intuit.ihg.product.portal.utils.TestcasesData;

public class FamilyAccountTest extends BaseTestNGWebDriver {

	public void FamilyAccount(WebDriver driver, TestcasesData data) throws Exception {

		log("Test Case: testFamilyAccount");
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());

		log("step 2: Get Data from Excel");

		Portal portal = new Portal();
		TestcasesData testcasesData = new TestcasesData(portal);

		log("URL: " + testcasesData.geturl());
		log("USER NAME: " + testcasesData.getUsername());
		log("Password: " + testcasesData.getPassword());

		log("step 3:LogIn");
		PortalLoginPage loginpage = new PortalLoginPage(driver, testcasesData.geturl());
		MyPatientPage pMyPatientPage = loginpage.login(testcasesData.getUsername(), testcasesData.getPassword());

		log("step 4:Click on myaccountLink on MyPatientPage");
		MyAccountPage pMyAccountPage = pMyPatientPage.clickMyAccountLink();

		log("step 5:Click on FamilyLink  on MyAccountPage");
		PatientsOnThisAccountPage pPatientsOnThisAccountPage = pMyAccountPage.clickfamilyLink();

		log("step 6:Click on Add a Family MemberLink  on CreatefamilymemberPage ");
		CreatefamilymemberPage pCreatefamilymemberPage = pPatientsOnThisAccountPage.clickAddaFamilyMemberLink();

		log("step 7:createFamilyMember");
		pMyPatientPage = pCreatefamilymemberPage.FamilyMemberdetails(testcasesData.getFamilyUN(), testcasesData.getFamilyPW(),
				testcasesData.getRelationship());

		log("step 8:Click on unlink link");
		pMyPatientPage = pCreatefamilymemberPage.Verifymember();

		/*
		 * log("Delete the account created");
		 * log("step 8:Click on myaccountLink on MyPatientPage"); pMyAccountPage
		 * = pMyPatientPage.clickMyAccountLink();
		 * 
		 * log("step 9:Click on FamilyLink  on MyAccountPage");
		 * pPatientsOnThisAccountPage = pMyAccountPage.clickfamilyLink();
		 * 
		 * // log("step 10:Assert the dependent Name"); //
		 * pPatientsOnThisAccountPage
		 * .verifyName(testcasesData.getpatientFirstName(), //
		 * testcasesData.getpatientLastName());
		 * 
		 * log(
		 * "step 11:Click on UnlinkDependentLink  on PatientsOnThisAccountPage "
		 * ); UnlinkPatientFromFamilyPage pUnlinkPatientFromFamilyPage =
		 * pPatientsOnThisAccountPage .clickUnlinkDependentLink();
		 * 
		 * log(
		 * " step 12:Give ur email address and confirm it UnlinkPatientFromFamilyPage "
		 * ); pPatientsOnThisAccountPage = pUnlinkPatientFromFamilyPage
		 * .unLinkFamilyMember(testcasesData.getEmail());
		 * 
		 * log(" step 13:unlinked Dependent Message ");
		 * pPatientsOnThisAccountPage.verifyunlinkedDependentMessage(
		 * testcasesData.getFirstName(), testcasesData.getLastName());
		 */

		log("step 9:Click on Logout");
		pPatientsOnThisAccountPage.logout(driver);

	}
}
