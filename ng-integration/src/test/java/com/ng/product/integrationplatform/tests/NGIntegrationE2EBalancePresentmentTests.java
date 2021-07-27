//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.*;

import java.util.Date;
import java.util.UUID;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.balancepresentment.Charges;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.flows.NGPatient;
import com.ng.product.integrationplatform.flows.PatientEnrollment;
import com.ng.product.integrationplatform.pojo.NewPatient;
import com.ng.product.integrationplatform.utils.CommonFlows;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author VMurugesh
 * 
 ************************/

public class NGIntegrationE2EBalancePresentmentTests extends BaseTestNGWebDriver {

	private PropertyFileLoader propertyLoaderObj;

	int arg_timeOut = 1800;
	NGAPIUtils ngAPIUtils;
	String enterprisebaseURL;
	NGAPIFlows ngAPIFlows;

	@BeforeClass(alwaysRun = true)
	public void prepareTestData() throws Throwable {
		log("Getting Test Data");
		propertyLoaderObj = new PropertyFileLoader();
		ngAPIUtils = new NGAPIUtils(propertyLoaderObj);
		ngAPIFlows = new NGAPIFlows(propertyLoaderObj);
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterprisebaseURL = ngAPIUtils.getRelativeBaseUrl();
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterprisebaseURL = ngAPIUtils.getRelativeBaseUrl();
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
	}

	@Test(enabled = true, groups = { "acceptance-BalancePresentment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPatientAbletoSeeChargesAndPaytheBill() throws Throwable {
		String enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
		String practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		String integrationPracticeId = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
		String url = propertyLoaderObj.getProperty("url");
		String locationName = propertyLoaderObj.getProperty("epm.location.name");
		String providerName = propertyLoaderObj.getProperty("epm.provider.name");
		String practiceName = propertyLoaderObj.getProperty("practice.name");

		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		logStep("Create the patient in NG EPM");
		NewPatient createPatient = NGPatient.patientUsingJSON(propertyLoaderObj, "complete");
		String personId = NGAPIFlows.createPatientinEPM(createPatient);
		NGAPIFlows.addCharttoProvider(locationName, providerName, personId);

		logStep("Enroll the patient into Portal");
		PatientEnrollment.enrollPatientWithoutGetProcessingStatusValidation(propertyLoaderObj, enterprisebaseURL,
				ngAPIUtils, driver, createPatient, personId, practiceName, integrationPracticeId, enterpriseId,
				practiceId);

		logStep("Add Encounter to patient chart");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String encounterId = NGAPIFlows.addEncounter(locationName, providerName, personId);

		logStep("Updating the Patient Encounter to be billable");
		DBUtils.executeQueryOnDB("NGCoreDB", "update patient_encounter set billable_ind='Y' where person_id='"
				+ personId + "' and enc_id ='" + encounterId + "'");

		logStep("Fetching the values from Properties file and SQL");
		String emruniqId = createRandomGUID();
		String chargeId = createRandomGUID();
		String patAmt = propertyLoaderObj.getProperty("pat.amt");
		String amt = propertyLoaderObj.getProperty("amt");
		String quantity = propertyLoaderObj.getProperty("quantity");
		String sourceId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select enc_id from patient_encounter where person_id = '" + personId + "'".trim());
		String locationId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select location_id from location_mstr where location_name = '" + locationName + "'");
		String serviceitemId = propertyLoaderObj.getProperty("service.item.id");
		String serviceitemlibId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select service_item_lib_Id from service_item_mstr where service_item_id = '" + serviceitemId + "'");
		String cpt4codeId = propertyLoaderObj.getProperty("cpt4.code.id");
		String icd9cmcodeId = propertyLoaderObj.getProperty("icd9cm.code.id");
		String icd9cmcodeDesc = propertyLoaderObj.getProperty("icd9cm.code.desc");
		String icd9cmcodeDesc1 = propertyLoaderObj.getProperty("icd9cm.code.desc1");
		String renderingId = DBUtils.executeQueryOnDB("NGCoreDB",
				"Select rendering_provider_id from patient_encounter where person_id= '" + personId + "'".trim());

		logStep("Adding Charges to the Patient Encounter in NG");
		Charges.addingChargestoPatientEncounter(enterpriseId, practiceId, emruniqId, sourceId, chargeId, personId,
				patAmt, locationId, serviceitemlibId, serviceitemId, renderingId, cpt4codeId, amt, quantity,
				icd9cmcodeId, icd9cmcodeDesc, icd9cmcodeDesc1);

		String acctNBRQuery = "select acct_nbr from accounts where guar_id ='" + personId + "' and practice_id='"
				+ practiceId.trim() + "'";

		logStep("Login into Portal");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(createPatient.getEmailAddress(), propertyLoaderObj.getPassword());

		logStep("Wait 90 second so that added charges in NG will be synced with Portal");
		Thread.sleep(90000);
		logStep("Refreshing the page so that charges will be displayed in Portal");
		driver.navigate().refresh();
		driver.findElement(By.xpath("//span[@class='badge amountDue']")).isDisplayed();
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);

		logStep("Verifying the expected and actual bill amount");
		String amountText = driver.findElement(By.xpath("//div[@id='balanceDue']//strong")).getText().substring(1);

		logStep("Amount which is displayed in Portal:" + amountText);
		CommonUtils.VerifyTwoValues(amountText, "equals", propertyLoaderObj.getProperty("pat.amt"));
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());
		logStep("Initiate payment data");
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB", acctNBRQuery);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Discover, name);
		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(patAmt, accountNumber,
				creditCard);
		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));
		String paymentComments = "Testing payment from number: " + accountNumber + " Current Timestamp "
				+ (new Date()).getTime();
		homePage = confirmationPage.commentAndSubmitPayment(paymentComments);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();
		logStep("Verifying whether the Payment is reached to NG");
		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComments, "BillPayment", "", accountNumber, "POSTED", patAmt,
				"Discover");
		String sourceIdQuery = DBUtils.executeQueryOnDB("NGCoreDB", "select acct_id from accounts where guar_id ='"
				+ personId + "' and practice_id='" + practiceId.trim() + "'");
		CommonFlows.verifyPaymentPostedtoNG(paymentComments, sourceIdQuery.toUpperCase(), personId.toUpperCase(),
				"-" + patAmt, "Payment type: BillPayment, Last 4 CC digits: " + creditCard.getLastFourDigits(),
				practiceId);

	}

	public static String createRandomGUID() throws Throwable {
		String guid = UUID.randomUUID().toString().toUpperCase();
		return guid;
	}
}
