//Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.tests;

import static org.testng.Assert.*;

import java.text.DecimalFormat;
import java.util.Date;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.RetryAnalyzer;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.intuit.ihg.product.integrationplatform.utils.IntegrationConstants;
import com.intuit.ihg.product.integrationplatform.utils.PropertyFileLoader;
import com.intuit.ihg.product.integrationplatform.utils.RestUtils;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.NGLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page1;
import com.medfusion.product.object.maps.patientportal2.page.AskAStaff.JalapenoAskAStaffV2Page2;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.IMHPage;
import com.medfusion.product.object.maps.patientportal2.page.MessagesPage.JalapenoMessagesPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.OnlineBillPaySearchPage;
import com.medfusion.product.object.maps.practice.page.onlinebillpay.PayMyBillOnlinePage;
import com.medfusion.product.object.maps.practice.page.patientSearch.PatientSearchPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;
import com.medfusion.product.practice.api.utils.PracticeConstants;
import com.ng.product.integrationplatform.apiUtils.NGAPIUtils;
import com.ng.product.integrationplatform.apiUtils.PAMAuthentication;
import com.ng.product.integrationplatform.apiUtils.pamAPIRoutes;
import com.ng.product.integrationplatform.flows.NGAPIFlows;
import com.ng.product.integrationplatform.utils.CommonFlows;
import com.ng.product.integrationplatform.utils.CommonUtils;
import com.ng.product.integrationplatform.utils.DBUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

public class NGIntegrationE2EPayment_SendChartItemTests extends BaseTestNGWebDriver {

	private PropertyFileLoader propertyLoaderObj;

	int arg_timeOut = 1800;
	NGAPIUtils ngAPIUtils;
	String enterprisebaseURL;
	NGAPIFlows ngAPIFlows;

	private static final String PAYMENT_SUCCESSFULL_TEXT = "A payment was made for ";
	private static final String DOCUMENT_RECEIVEDTEXT = "Document received please open the attachment to review.";
	private static final String DOC_RECEIVED_TEXT = "Document received please open the attachment to review.";

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

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentPayBills() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String acctNBRQuery = "select acct_nbr from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		logStep("Initiate payment data");
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB", acctNBRQuery);
		String amount = IHGUtil.createRandomNumericString(3);
		String name = "TestPatient CreditCard";
		CreditCard creditCard = new CreditCard(CardType.Discover, name);

		logStep("Load login page");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, url);

		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		logStep("Remove all cards because Selenium can't see AddNewCard button");
		payBillsPage.removeAllCards();
		logStep("Check that no card is present");
		assertFalse(payBillsPage.isAnyCardPresent());

		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,
				creditCard);
		logStep("Verifying credit card ending");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));

		String paymentComments = "Testing payment from number: " + accountNumber + " Current Timestamp "
				+ (new Date()).getTime();
		homePage = confirmationPage.commentAndSubmitPayment(paymentComments);
		assertTrue(homePage.wasPayBillsSuccessfull());
		homePage.clickOnLogout();

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Click On Online BillPayment Tab in Practice Portal");
		OnlineBillPaySearchPage onlineBillPaySearchPage = practiceHome.clickOnlineBillPayTab();

		logStep("Search Paid Bills By Current Date");
		onlineBillPaySearchPage.searchForBillPayToday();

		logStep("Search For Today's Paid Bill By Account Number");
		onlineBillPaySearchPage.searchForBillPayment(accountNumber);

		logStep("Get Bill Details");
		onlineBillPaySearchPage.getBillPayDetails();

		logStep("Set Payment Communication Details");
		onlineBillPaySearchPage.setPaymentCommunicationDetails();

		logStep("Logout of Practice Portal");
		practiceHome.logOut();

		String actualAmount = amount.charAt(0) + "." + amount.substring(1);
		log("Actual Amount " + actualAmount);
		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComments, "BillPayment", "", accountNumber, "POSTED",
				actualAmount, "Discover");

		String sourceIdQuery = "select acct_id from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String sourceId = DBUtils.executeQueryOnDB("NGCoreDB", sourceIdQuery);
		CommonFlows.verifyPaymentPostedtoNG(paymentComments, sourceId, person_id, "-" + actualAmount,
				"Payment type: BillPayment, Last 4 CC digits: " + creditCard.getLastFourDigits(), practiceId);
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentVirtualCardSwiper() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Initiate payment data");
		String acctNBRQuery = "select acct_nbr from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB", acctNBRQuery);
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where email_address = '" + username + "'");
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		String paymentComment = PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString());

		DecimalFormat df = new DecimalFormat("0.00");
		String actualAmount = df.format(Integer.parseInt(amount));
		log("Amount to be paid " + actualAmount);

		logStep("Navigate to Login page");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Navigate to Virtual Card Swiper page.");
		VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

		logStep("Verify whether Virtual Card Swiper page is displayed.");
		assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(),
				"Virtual Card Swiper page is not displayed properly.");

		logStep("Add card info and click on 'Click Here To Charge Card' button.");
		virtualCardSwiper.addCreditCardInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUMBER,
				PracticeConstants.CARD_TYPE_VISA, PracticeConstants.EXP_MONTH, PracticeConstants.EXP_YEAR, amount,
				PracticeConstants.CVV, PracticeConstants.ZIP, accountNumber, firstName, paymentComment,
				propertyLoaderObj.getProperty("portal.location.name"));

		logStep("Verify whether the payment is completed successfully.");
		assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg()
				.contains(PracticeConstants.PAYMENT_COMPLETED_SUCCESS_MSG), true, "The payment is completed properly.");

		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComment, "VCSPayment", "", accountNumber, "POSTED",
				actualAmount, PracticeConstants.CARD_TYPE_VISA);

		String SourceIdQuery = "select acct_id from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String SourceId = DBUtils.executeQueryOnDB("NGCoreDB", SourceIdQuery);
		CommonFlows.verifyPaymentPostedtoNG(paymentComment, SourceId, person_id, "-" + actualAmount,
				"Payment type: VCSPayment, Last 4 CC digits: " + PracticeConstants.CARD_NUMBER.substring(12),
				practiceId);
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentOnlineBillPayProcess() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Initiate payment data");
		String acctNBRQuery = "select acct_nbr from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB", acctNBRQuery);
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + person_id + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + person_id + "'");
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		log("Random generated amount: " + amount);

		DecimalFormat df = new DecimalFormat("0.00");
		String actualAmount = df.format(Integer.parseInt(amount));
		log("Amount to be paid " + actualAmount);

		String cardNumber = null, cardType = null;
		if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("Visa")) {
			cardNumber = PracticeConstants.CARD_NUMBER;
			cardType = PracticeConstants.CARD_TYPE_VISA;
		} else if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("MasterCard")) {
			cardNumber = PracticeConstants.CARD_NUM_MASTERCARD;
			cardType = PracticeConstants.CARD_TYPE_MASTERCARD;
		}

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(firstName, lastName);

		String paymentComment = PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString());
		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(propertyLoaderObj.getProperty("portal.location.name"),
				propertyLoaderObj.getProperty("portal.provider.name").replaceAll(", Dr", ""), accountNumber, amount,
				PracticeConstants.PROCESS_CARD_HOLDER_NAME, cardNumber, cardType, paymentComment);

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PAYMENT_SUCCESSFULL_TEXT + firstName + " " + lastName));

		logStep("Navigate to Patient Search Page.");
		OnlineBillPaySearchPage onlineBillPay = new OnlineBillPaySearchPage(driver);
		PatientSearchPage patientsearchPage = onlineBillPay.clickOnPatientSearchLink();

		logStep("Search the patient in Patient Search page.");
		patientsearchPage.searchPatient(firstName, lastName);

		logStep("Verify whether the transaction is present.");
		assertTrue(patientsearchPage.isTransactionPresent(actualAmount, firstName, lastName));

		logStep("Select the particular Transaction from the Search Result.");
		patientsearchPage.selectTheTransaction(actualAmount, firstName, lastName);
		assertFalse(pPayMyBillOnlinePage.isVoidTransactionPresent());

		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComment, "BillPayment", "", accountNumber, "POSTED",
				actualAmount, cardType);

		String sourceIdQuery = "select acct_id from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String sourceId = DBUtils.executeQueryOnDB("NGCoreDB", sourceIdQuery);
		CommonFlows.verifyPaymentPostedtoNG(paymentComment, sourceId, person_id, "-" + actualAmount,
				"Payment type: BillPayment, Last 4 CC digits: " + cardNumber.substring(12), practiceId);
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentBudgetPaymentProcess() throws Throwable {
		String amount = propertyLoaderObj.getProperty("budget.amount");
		String prepayamount = propertyLoaderObj.getProperty("budget.prepay.amount");

		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Initiate payment data");
		String acctNBRQuery = "select acct_nbr from accounts where guar_id ='" + person_id + "' and practice_id='"
				+ practiceId.trim() + "'";
		String accountNumber = DBUtils.executeQueryOnDB("NGCoreDB", acctNBRQuery);
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + person_id + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + person_id + "'");

		String cardNumber = null, cardType = null;
		if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("Visa")) {
			cardNumber = PracticeConstants.CARD_NUMBER;
			cardType = PracticeConstants.CARD_TYPE_VISA;
		} else if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("MasterCard")) {
			cardNumber = PracticeConstants.CARD_NUM_MASTERCARD;
			cardType = PracticeConstants.CARD_TYPE_MASTERCARD;
		}

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(firstName, lastName);

		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForBudgetPaymentPlan(propertyLoaderObj.getProperty("portal.location.name"),
				propertyLoaderObj.getProperty("portal.provider.name").replaceAll(", Dr", ""), accountNumber, amount,
				prepayamount, PracticeConstants.PROCESS_CARD_HOLDER_NAME, cardNumber, cardType);

		logStep("Verify the your Budget payment plan start date text");
		assertTrue(pPayMyBillOnlinePage.getPaymentStartDateText().contains("Your payment plan start date is "
				+ pPayMyBillOnlinePage.getPlanStartDate() + " recurring every other week."));

		logStep("Verify the creditcard last four digit");
		assertTrue(pPayMyBillOnlinePage.getCreditCardLastFourDigits().contains(cardNumber.substring(12)));

		String enddatePlanText = pPayMyBillOnlinePage.getPlanEndDate();

		logStep("click to submit the Budget Payment Plan search");
		pPayMyBillOnlinePage.clickOnSubmitPayment();

		logStep("click on Budget Payment pLan to serach the Budget Payment done ");
		practiceHome.budgetPaymentPlanSearch();

		logStep("Searching of Budget Payment plan with patient firstName and lastName ");
		pPayMyBillOnlinePage.budgetPaymentPlanSearchPatient(firstName, lastName);

		logStep("Verify the BudgetPaymentPlan End Date and card ending");
		assertTrue(pPayMyBillOnlinePage.getplanEndDateBudgetSearch().equals(enddatePlanText));

		logStep("Verify the creditcard last four digit in Budget Payment Plan Search");
		assertTrue(pPayMyBillOnlinePage.getActiveBudgetPaymentCardDigit().contains(cardNumber.substring(12)));

		logStep("Stop the Budget Payment Plan ");
		pPayMyBillOnlinePage.clickOnStopBudgetPayment();
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentOnlineBillPayProcessforSingleGuarantor() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("SingleGuarantorUser");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String practiceId = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Initiate payment data");
		String invalidAccountNumber = "9999999";
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + personId + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + personId + "'");
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		log("Random generated amount: " + amount);

		DecimalFormat df = new DecimalFormat("0.00");
		String actualAmount = df.format(Integer.parseInt(amount));
		log("Amount to be paid " + actualAmount);

		String cardNumber = null, cardType = null;
		if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("Visa")) {
			cardNumber = PracticeConstants.CARD_NUMBER;
			cardType = PracticeConstants.CARD_TYPE_VISA;
		} else if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("MasterCard")) {
			cardNumber = PracticeConstants.CARD_NUM_MASTERCARD;
			cardType = PracticeConstants.CARD_TYPE_MASTERCARD;
		}

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(firstName, lastName);

		String paymentComment = PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString());
		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(propertyLoaderObj.getProperty("portal.location.name"),
				propertyLoaderObj.getProperty("portal.provider.name").replaceAll(", Dr", ""), invalidAccountNumber,
				amount, PracticeConstants.PROCESS_CARD_HOLDER_NAME, cardNumber, cardType, paymentComment);

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PAYMENT_SUCCESSFULL_TEXT + firstName + " " + lastName));

		String personNumber = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_nbr from person where person_id = '" + personId + "'");
		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComment, "BillPayment", personNumber.trim().replace("\t", ""),
				invalidAccountNumber, "POSTED", actualAmount, cardType);

		String sourceIdQuery = "select acct_id from accounts where guar_id ='" + personId + "' and practice_id='"
				+ practiceId.trim() + "'";
		String sourceId = DBUtils.executeQueryOnDB("NGCoreDB", sourceIdQuery);
		CommonFlows.verifyPaymentPostedtoNG(paymentComment, sourceId, personId, "-" + actualAmount,
				"Payment type: BillPayment, Last 4 CC digits: " + cardNumber.substring(12), practiceId);
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentOnlineBillPayProcessforMultipleGuarantors() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");

		logStep("Initiate payment data");
		String invalidAccountNumber = "9999999";
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + personId + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + personId + "'");
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		log("Random generated amount: " + amount);

		DecimalFormat df = new DecimalFormat("0.00");
		String actualAmount = df.format(Integer.parseInt(amount));
		log("Amount to be paid " + actualAmount);

		String cardNumber = null, cardType = null;
		if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("Visa")) {
			cardNumber = PracticeConstants.CARD_NUMBER;
			cardType = PracticeConstants.CARD_TYPE_VISA;
		} else if (propertyLoaderObj.getProperty("PaymentCardType").equalsIgnoreCase("MasterCard")) {
			cardNumber = PracticeConstants.CARD_NUM_MASTERCARD;
			cardType = PracticeConstants.CARD_TYPE_MASTERCARD;
		}

		logStep("Login to Practice Portal");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Click on Make Payment link.");
		PayMyBillOnlinePage pPayMyBillOnlinePage = practiceHome.clickMakePaymentForPatient();

		logStep("Search For Patient");
		pPayMyBillOnlinePage.searchForPatient(firstName, lastName);

		String paymentComment = PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString());
		logStep("Set all the transaction details");
		pPayMyBillOnlinePage.setTransactionsForOnlineBillPayProcess(propertyLoaderObj.getProperty("portal.location.name"),
				propertyLoaderObj.getProperty("portal.provider.name").replaceAll(", Dr", ""), invalidAccountNumber,
				amount, PracticeConstants.PROCESS_CARD_HOLDER_NAME, cardNumber, cardType, paymentComment);

		logStep("Verify the Payment Confirmation text");
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, pPayMyBillOnlinePage.paymentConfirmationText);
		assertEquals(true, pPayMyBillOnlinePage.paymentConfirmationText.getText()
				.contains(PAYMENT_SUCCESSFULL_TEXT + firstName + " " + lastName));

		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComment, "BillPayment", "", invalidAccountNumber, "PENDING",
				actualAmount, cardType);
	}

	@Test(enabled = true, groups = { "Payment" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPaymentVirtualCardSwiperforMultipleGuarantors() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");

		logStep("Initiate payment data");
		String invalidAccountNumber = "9999999";
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where email_address = '" + username + "'");
		String amount = IHGUtil.createRandomNumericString().substring(0, 2);
		String paymentComment = PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString());

		DecimalFormat df = new DecimalFormat("0.00");
		String actualAmount = df.format(Integer.parseInt(amount));
		log("Amount to be paid " + actualAmount);

		logStep("Navigate to Login page");
		PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, propertyLoaderObj.getPortalUrl());
		PracticeHomePage practiceHome = practiceLogin.login(propertyLoaderObj.getDoctorLogin(),
				propertyLoaderObj.getDoctorPassword());

		logStep("Navigate to Virtual Card Swiper page.");
		VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();

		logStep("Verify whether Virtual Card Swiper page is displayed.");
		assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(),
				"Virtual Card Swiper page is not displayed properly.");

		logStep("Add card info and click on 'Click Here To Charge Card' button.");
		virtualCardSwiper.addCreditCardInfo(PracticeConstants.CARD_NAME, PracticeConstants.CARD_NUMBER,
				PracticeConstants.CARD_TYPE_VISA, PracticeConstants.EXP_MONTH, PracticeConstants.EXP_YEAR, amount,
				PracticeConstants.CVV, PracticeConstants.ZIP, invalidAccountNumber, firstName, paymentComment,
				propertyLoaderObj.getProperty("portal.location.name"));

		logStep("Verify whether the payment is completed successfully.");
		assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg()
				.contains(PracticeConstants.PAYMENT_COMPLETED_SUCCESS_MSG), true, "The payment is completed properly.");

		CommonFlows.verifyPaymentReachedtoMFAgent(paymentComment, "VCSPayment", "", invalidAccountNumber, "PENDING",
				actualAmount, PracticeConstants.CARD_TYPE_VISA);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendEHRDocument() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting EHR Document Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String docType = propertyLoaderObj.getProperty("EHRDocumentDocType");
		String docFormat = propertyLoaderObj.getProperty("EHRDocumentFormat");
		String docName = propertyLoaderObj.getProperty("EHRDocumentName");
		String pxpDocType = propertyLoaderObj.getProperty("PXPDocType");

		String sourceFile = System.getProperty("user.dir") + "\\src\\test\\resources\\EHRAttachment\\";
		String content = CommonUtils.fetchTokenValueFromJsonObject(sourceFile, "EHRAttachmentData", "EHRDocument");

		logStep("Inserting EHR Document into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_documents(request_id, document_id, document_type, content, delete_ind, created_by,"
						+ "create_timestamp,modified_by, modify_timestamp)" + "VALUES ('" + requestId + "','"
						+ documentId + "','" + pxpDocType + "', " + "CONVERT(VARBINARY(256),'" + content
						+ "'),'0', '0' , getutcdate(), '0', getutcdate())");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + personId
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_document_history(row_id, emr_doc_id, nx_doc_id, person_id, read_when, read_by, delete_ind, "
						+ "comments,created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz, modify_timestamp_tz)"
						+ " VALUES (newid(), '" + documentId + "', newid(), '" + personId + "', NULL, NULL, 'N'," + "'"
						+ comments + "', '0',getutcdate(), '0', getutcdate(), Null, Null)");

		logStep("Verify EHR Document is inserted into tables");
		CommonFlows.verifyDocumentInsertedIntoTables(requestId, comments);

		logStep("Verify EHR Document status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		String body = comments + "\n\n" + DOCUMENT_RECEIVEDTEXT;
		CommonFlows.verifyPatientDocumentReceivedINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), propertyLoaderObj.getProperty("DocSubject"), body, docName + ".pdf");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendICSImage() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting ICS Image Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String docType = propertyLoaderObj.getProperty("ICSImageDocType");
		String docFormat = propertyLoaderObj.getProperty("ICSImageFormat");
		String docName = propertyLoaderObj.getProperty("ICSImageName");
		String pxpDocType = propertyLoaderObj.getProperty("PXPDocType");

		String sourceFile = System.getProperty("user.dir") + "\\src\\test\\resources\\EHRAttachment\\";
		String content = CommonUtils.fetchTokenValueFromJsonObject(sourceFile, "EHRAttachmentData", "JPGImage");

		logStep("Inserting ICS Image into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_documents(request_id, document_id, document_type, content, delete_ind, created_by,"
						+ "create_timestamp,modified_by, modify_timestamp)" + "VALUES ('" + requestId + "','"
						+ documentId + "','" + pxpDocType + "', " + "CONVERT(VARBINARY(256),'" + content
						+ "'),'0', '0' , getutcdate(), '0', getutcdate())");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + personId
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_document_history(row_id, emr_doc_id, nx_doc_id, person_id, read_when, read_by, delete_ind, "
						+ "comments,created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz, modify_timestamp_tz)"
						+ " VALUES (newid(), '" + documentId + "', newid(), '" + personId + "', NULL, NULL, 'N'," + "'"
						+ comments + "', '0',getutcdate(), '0', getutcdate(), Null, Null)");

		logStep("Verify ICS Image is inserted into tables");
		CommonFlows.verifyDocumentInsertedIntoTables(requestId, comments);

		logStep("Verify ICS Image status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		String body = comments + "\n\n" + DOCUMENT_RECEIVEDTEXT;
		CommonFlows.verifyPatientDocumentReceivedINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), propertyLoaderObj.getProperty("DocSubject"), body,
				docName + "." + docFormat.trim());
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendEHRImage() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting Image Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = propertyLoaderObj.getProperty("EHRImageDocumentId");
		String docType = propertyLoaderObj.getProperty("EHRImageDocType");
		String docFormat = propertyLoaderObj.getProperty("EHRImageFormat");
		String docName = propertyLoaderObj.getProperty("EHRImageName");
		String pxpDocType = propertyLoaderObj.getProperty("PXPDocType");

		String sourceFile = System.getProperty("user.dir") + "\\src\\test\\resources\\EHRAttachment\\";
		String content = CommonUtils.fetchTokenValueFromJsonObject(sourceFile, "EHRAttachmentData", "BMPImage");

		logStep("Inserting image into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_documents(request_id, document_id, document_type, content, delete_ind, created_by,"
						+ "create_timestamp,modified_by, modify_timestamp)" + "VALUES ('" + requestId + "','"
						+ documentId + "','" + pxpDocType + "', " + "CONVERT(VARBINARY(256),'" + content
						+ "'),'0', '0' , getutcdate(), '0', getutcdate())");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + personId
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_document_history(row_id, emr_doc_id, nx_doc_id, person_id, read_when, read_by, delete_ind, "
						+ "comments,created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz, modify_timestamp_tz)"
						+ " VALUES (newid(), '" + documentId + "', newid(), '" + personId + "', NULL, NULL, 'N'," + "'"
						+ comments + "', '0',getutcdate(), '0', getutcdate(), Null, Null)");

		logStep("Verify image is inserted into tables");
		CommonFlows.verifyDocumentInsertedIntoTables(requestId, comments);

		logStep("Verify image status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		String body = comments + "\n\n" + DOCUMENT_RECEIVEDTEXT;
		CommonFlows.verifyPatientDocumentReceivedINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), propertyLoaderObj.getProperty("DocSubject"), body,
				docName + "." + docFormat.trim());
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendReferralLetter() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting Referral Letter Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String docType = propertyLoaderObj.getProperty("EHRReferralLetterDocType");
		String docFormat = propertyLoaderObj.getProperty("EHRReferralLetterFormat");
		String docName = propertyLoaderObj.getProperty("EHRReferralLetterName");
		String pxpDocType = propertyLoaderObj.getProperty("PXPDocType");

		String sourceFile = System.getProperty("user.dir") + "\\src\\test\\resources\\EHRAttachment\\";
		String content = CommonUtils.fetchTokenValueFromJsonObject(sourceFile, "EHRAttachmentData", "ReferralLetter");

		logStep("Inserting Referral Letter into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_documents(request_id, document_id, document_type, content, delete_ind, created_by,"
						+ "create_timestamp,modified_by, modify_timestamp)" + "VALUES ('" + requestId + "','"
						+ documentId + "','" + pxpDocType + "', " + "CONVERT(VARBINARY(256),'" + content
						+ "'),'0', '0' , getutcdate(), '0', getutcdate())");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + personId
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_document_history(row_id, emr_doc_id, nx_doc_id, person_id, read_when, read_by, delete_ind, "
						+ "comments,created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz, modify_timestamp_tz)"
						+ " VALUES (newid(), '" + documentId + "', newid(), '" + personId + "', NULL, NULL, 'N'," + "'"
						+ comments + "', '0',getutcdate(), '0', getutcdate(), Null, Null)");

		logStep("Verify Referral Letter is inserted into tables");
		CommonFlows.verifyDocumentInsertedIntoTables(requestId, comments);

		logStep("Verify Referral Letter status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		String body = comments + "\n\n" + DOCUMENT_RECEIVEDTEXT;
		CommonFlows.verifyPatientDocumentReceivedINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), propertyLoaderObj.getProperty("DocSubject"), body, docName + ".pdf");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendEHRImageWhileReplyingMessage() throws Throwable {
		String questionText = IntegrationConstants.MESSAGE_REPLY;
		String userId = propertyLoaderObj.getProperty("secure.message.user.id");
		String userLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from user_mstr where user_id='" + userId + "'");
		String userProviderName = userLastName + ", Dr";

		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null,
				integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			providerName = propertyLoaderObj.getProperty("ng.e1.p1.provider");
			locationName = propertyLoaderObj.getProperty("ng.e1.p1.location");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			providerName = propertyLoaderObj.getProperty("epm.provider.name");
			locationName = propertyLoaderObj.getProperty("epm.location.name");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		String userLocationName = propertyLoaderObj.getProperty("portal.location.name");

		long timestamp = System.currentTimeMillis();
		logStep("Do a GET and get the read communication");
		Long since = timestamp / 1000L - 60 * 24;

		logStep("Login patient");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(propertyLoaderObj.getProperty("ccda.username"),
				propertyLoaderObj.getPassword());

		logStep("Click Ask A Question tab");
		JalapenoAskAStaffV2Page1 askPage1 = homePage
				.openSpecificAskaQuestion(propertyLoaderObj.getProperty("aska.v2.name"));

		String askaSubject = Long.toString(askPage1.getCreatedTimeStamp());

		logStep("Fill question and continue");
		JalapenoAskAStaffV2Page2 askPage2 = askPage1.NGfillAndContinue(askaSubject, questionText, userProviderName,
				userLocationName);

		assertTrue(askaSubject.equals(askPage2.getSubject()),
				"Expected: " + askaSubject + ", found: " + askPage2.getSubject());
		assertTrue(questionText.equals(askPage2.getQuestion()),
				"Expected: " + questionText + ", found: " + askPage2.getQuestion());

		homePage = askPage2.submit();
		homePage.LogoutfromNGMFPortal();

		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
					propertyLoaderObj.getProperty("oauth.password1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Wait 60 seconds, so the message can be processed");
		Thread.sleep(60000);

		logStep("Do a GET and get the message");
		RestUtils.setupHttpGetRequest(
				propertyLoaderObj.getProperty("get.inbound.message").replaceAll("integrationID", integrationPracticeID)
						+ "?since=" + since + ",0",
				propertyLoaderObj.getResponsePath());

		logStep("Validate message reply");
		String patientFirstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id ='" + personId + "'");
		String patientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id ='" + personId + "'");
		String expectedBody = "Dear " + propertyLoaderObj.getProperty("practice.name") + ",<br/><br/>"
				+ IntegrationConstants.MESSAGE_REPLY + "<br/><br/>Thanks,<br>" + patientFirstName + " "
				+ patientLastName;

		String messageID = RestUtils.isReplyPresentReturnMessageID(propertyLoaderObj.getResponsePath(), askaSubject,
				expectedBody);

		String expectedBodyinInbox = "Dear " + propertyLoaderObj.getProperty("practice.name") + ",\n"
				+ IntegrationConstants.MESSAGE_REPLY + "\nThanks,\n" + patientFirstName + " " + patientLastName;
		Thread.sleep(60000);
		CommonFlows.verifyMessageReceivedAtNGCore(propertyLoaderObj, messageID, askaSubject,
				expectedBodyinInbox.replace("\n", ""), propertyLoaderObj.getProperty("aska.v2.name"));

		String peImageId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select top 1 image_id from patient_images where practice_id ='" + practiceId + "' and person_id ='"
						+ personId + "'");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "ReplyToPortal" + messageID, personId,
				practiceId, userId, providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "",
				"PatientImage", peImageId);

		Thread.sleep(60000);
		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, personId, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageIDAtMF = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		String attachmentName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select orig_image_file from patient_images where image_id ='" + peImageId + "'");
		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
					propertyLoaderObj.getProperty("oauth.password1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageIDAtMF, integrationPracticeID, "messageWithPE", attachmentName);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendEHRImageByCommunication() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String personId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;
		userId = propertyLoaderObj.getProperty("secure.message.user.id");

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			providerName = propertyLoaderObj.getProperty("ng.e1.p1.provider");
			locationName = propertyLoaderObj.getProperty("ng.e1.p1.location");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			providerName = propertyLoaderObj.getProperty("epm.provider.name");
			locationName = propertyLoaderObj.getProperty("epm.location.name");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String peImageId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select top 1 image_id from patient_images where practice_id ='" + practiceId + "' and person_id ='"
						+ personId + "'");

		logStep("Compose Message with EHR Image and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "", personId, practiceId, userId, providerName,
				locationName, "EPM", "DoNotAddToEncounter", "PracticeUser", "", "PatientImage", peImageId);

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, personId, practiceId, comm_id,
				integrationPracticeID, "");

		String messageDeliveryQuery = "select messageid from  message_delivery where message_groupid ='" + comm_id
				+ "'";
		String messageID = DBUtils.executeQueryOnDB("MFAgentDB", messageDeliveryQuery);

		String attachmentName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select orig_image_file from patient_images where image_id ='" + peImageId + "'");
		Thread.sleep(15000);
		logStep("Setup Oauth client" + propertyLoaderObj.getResponsePath());
		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username1"),
					propertyLoaderObj.getProperty("oauth.password1"));
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			RestUtils.oauthSetup(propertyLoaderObj.getOAuthKeyStore(), propertyLoaderObj.getOAuthProperty(),
					propertyLoaderObj.getOAuthAppToken(), propertyLoaderObj.getProperty("oauth.username"),
					propertyLoaderObj.getProperty("oauth.password"));
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}
		CommonFlows.verifyMessageINInbox(propertyLoaderObj, driver, url, username, propertyLoaderObj.getPassword(),
				subject, body, comm_id, messageID, integrationPracticeID, "messageWithPE", attachmentName);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendIMHByCommunication() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + person_id + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + person_id + "'");

		String enterpriseId = null, practiceId = null, providerName = null, locationName = null, userId = null,
				integrationPracticeID = null, url = null;
		userId = propertyLoaderObj.getProperty("secure.message.user.id");

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			providerName = propertyLoaderObj.getProperty("ng.e1.p1.provider");
			locationName = propertyLoaderObj.getProperty("ng.e1.p1.location");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			providerName = propertyLoaderObj.getProperty("epm.provider.name");
			locationName = propertyLoaderObj.getProperty("epm.location.name");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		String attachmentName = propertyLoaderObj.getProperty("IMH");

		logStep("Compose Message with IMH and send it to enrolled patient with “Do not add to chart” option selected in Send & Chart button.");
		NGAPIUtils.updateLoginDefaultTo("EnterpriseGateway", enterpriseId, practiceId);
		String comm_id = NGAPIFlows.postSecureMessage(propertyLoaderObj, "", person_id, practiceId, userId,
				providerName, locationName, "EHR", "DoNotAddToEncounter", "PracticeUser", "", "IMH", attachmentName);

		String subjectQuery = "select subject from ngweb_communications where comm_id ='" + comm_id + "'";
		String bodyQuery = "select body from ngweb_communications where comm_id ='" + comm_id + "'";
		String subject = DBUtils.executeQueryOnDB("NGCoreDB", subjectQuery);
		String body = DBUtils.executeQueryOnDB("NGCoreDB", bodyQuery);

		logStep("Verify the processing status of message");
		CommonFlows.verifyMessageProcessingStatus(propertyLoaderObj, person_id, practiceId, comm_id,
				integrationPracticeID, "");

		logStep("Login to Patient Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Find message in Inbox with message subject " + subject);
		assertTrue(messagesPage.isMessageDisplayed(driver, subject));

		logStep("Verify message content and attachement" + subject);
		messagesPage.verifyMessageContent(driver, subject, body + "\n" + attachmentName);
		messagesPage.verifyMessageAttachment(driver, attachmentName);

		logStep("Filling the IMH Form");
		IMHPage imhPage = new IMHPage(driver);
		imhPage.fillIMH(driver, attachmentName, firstName, lastName, propertyLoaderObj.getProperty("dob.month"),
				propertyLoaderObj.getProperty("dob.day"), propertyLoaderObj.getProperty("dob.year"),
				propertyLoaderObj.getProperty("IMHForm"));

		String deliveryStatusATMF = DBUtils.executeQueryOnDB("MFAgentDB",
				"select status from  message_delivery where message_groupid ='" + comm_id + "'");
		CommonUtils.VerifyTwoValues(deliveryStatusATMF, "equals", "SENT");
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP139SendIMH() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String firstName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select first_name from person where person_id = '" + person_id + "'");
		String lastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where person_id = '" + person_id + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, providerName = null,
				locationName = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
			providerName = propertyLoaderObj.getProperty("ng.e1.p1.provider");
			locationName = propertyLoaderObj.getProperty("ng.e1.p1.location");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
			providerName = propertyLoaderObj.getProperty("epm.provider.name");
			locationName = propertyLoaderObj.getProperty("epm.location.name");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting IMH Form Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String docType = propertyLoaderObj.getProperty("IMHDocType");
		String docFormat = propertyLoaderObj.getProperty("IMHFormat");
		String docName = propertyLoaderObj.getProperty("IMH");

		String nxPracticeId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select nx_practice_id from nxmd_practices where practice_id = '" + practiceId + "'");
		String providerId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select provider_id from provider_mstr where description = '" + providerName + "'");
		String locationId = DBUtils.executeQueryOnDB("NGCoreDB",
				"select location_id from location_mstr where location_name = '" + locationName + "'");

		logStep("Inserting IMH Form into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_imh_question_state(id, patient_id, nx_practice_id, practice_id, "
						+ "provider_id, location_id, complaint, current_state,"
						+ "reminder_notification_sent, delete_ind, modified_by, create_timestamp, modify_timestamp,"
						+ "        		date_completed, created_by)" + "VALUES ('" + documentId + "', '" + person_id
						+ "','" + nxPracticeId + "','" + practiceId + "','" + providerId + "','" + locationId + "','"
						+ docName + "', " + "'1',Null,'N', '0' , getutcdate(), getutcdate(),Null, '0')");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + person_id
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		logStep("Verify IMH Form status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		logStep("Login to Patient Portal");
		NGLoginPage loginPage = new NGLoginPage(driver, url);
		JalapenoHomePage homePage = loginPage.login(username, propertyLoaderObj.getPassword());

		logStep("Click on messages solution");
		JalapenoMessagesPage messagesPage = homePage.showMessages(driver);

		logStep("Find message in Inbox with message subject " + docName);
		assertTrue(messagesPage.isMessageDisplayed(driver, docName));

		logStep("Verify message content and attachement" + docName);
		messagesPage.verifyMessageAttachment(driver, docName);

		logStep("Filling the IMH Form");
		IMHPage imhPage = new IMHPage(driver);
		imhPage.fillIMH(driver, docName, firstName, lastName, propertyLoaderObj.getProperty("dob.month"),
				propertyLoaderObj.getProperty("dob.day"), propertyLoaderObj.getProperty("dob.year"),
				propertyLoaderObj.getProperty("IMHForm"));

		CommonFlows.verifyIMHState(documentId);
	}

	@Test(enabled = true, groups = { "acceptance-INBOX" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP138SendPEDocument() throws Throwable {
		logStep("Getting Existing User");
		String username = propertyLoaderObj.getProperty("ccda.username");
		String person_id = DBUtils.executeQueryOnDB("NGCoreDB",
				"select person_id from person where email_address = '" + username + "'");
		String enterpriseId = null, practiceId = null, integrationPracticeID = null, url = null;

		if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("QAMain")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.enterprise.enrollment.enterprise1");
			practiceId = propertyLoaderObj.getProperty("ng.enterprise1.practice1");
			integrationPracticeID = propertyLoaderObj.getProperty("integration.practice.id.e1.p1");
			url = propertyLoaderObj.getProperty("mf.portal.url.practice1");
		} else if (propertyLoaderObj.getNGAPIexecutionMode().equalsIgnoreCase("SIT")) {
			enterpriseId = propertyLoaderObj.getProperty("ng.main.enterprise.id");
			practiceId = propertyLoaderObj.getProperty("ng.main.practice.id");
			integrationPracticeID = propertyLoaderObj.getProperty("integrationpracticeid.amdc");
			url = propertyLoaderObj.getProperty("url");
		} else {
			Log4jUtil.log("Invalid Execution Mode");
		}

		logStep("Getting Patient Education Document Data");
		String requestId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String documentId = DBUtils.executeQueryOnDB("NGCoreDB", "select newid()");
		String docType = propertyLoaderObj.getProperty("EHRPEDocType");
		String docFormat = propertyLoaderObj.getProperty("EHRPEFormat");
		String docName = propertyLoaderObj.getProperty("EHRPEName");
		String pxpDocType = propertyLoaderObj.getProperty("PXPDocType");

		String sourceFile = System.getProperty("user.dir") + "\\src\\test\\resources\\EHRAttachment\\";
		String content = CommonUtils.fetchTokenValueFromJsonObject(sourceFile, "EHRAttachmentData", "PEContent");

		logStep("Inserting Patient Education Document into tables");
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_documents(request_id, document_id, document_type, content, delete_ind, created_by,"
						+ "create_timestamp,modified_by, modify_timestamp)" + "VALUES ('" + requestId + "','"
						+ documentId + "','" + pxpDocType + "', " + "CONVERT(VARBINARY(256),'" + content
						+ "'),'0', '0' , getutcdate(), '0', getutcdate())");

		String comments = "SendingDocument" + (new Date()).getTime();
		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO pxp_document_requests(request_id, enterprise_id, practice_id, person_id, document_id, document_type, format,"
						+ "name, document_desc, status, created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz,modify_timestamp_tz)"
						+ "VALUES ('" + requestId + "', '" + enterpriseId + "', '" + practiceId + "', '" + person_id
						+ "', '" + documentId + "'," + "'" + docType + "', '" + docFormat + "', '" + docName + "', "
						+ "'" + comments + "', '2','0', getutcdate(),'0', getutcdate(), Null, Null)");

		DBUtils.executeQueryOnDB("NGCoreDB",
				"INSERT INTO ngweb_document_history(row_id, emr_doc_id, nx_doc_id, person_id, read_when, read_by, delete_ind, "
						+ "comments,created_by, create_timestamp, modified_by, modify_timestamp, create_timestamp_tz, modify_timestamp_tz)"
						+ " VALUES (newid(), '" + documentId + "', newid(), '" + person_id + "', NULL, NULL, 'N'," + "'"
						+ comments + "', '0',getutcdate(), '0', getutcdate(), Null, Null)");

		logStep("Verify Patient Education Document is inserted into tables");
		CommonFlows.verifyDocumentInsertedIntoTables(requestId, comments);

		logStep("Verify Patient Education Document status");
		CommonFlows.verifyDocumentProcessingStatus(propertyLoaderObj, requestId, practiceId, integrationPracticeID);

		String body = comments + "\n\n" + DOC_RECEIVED_TEXT;
		CommonFlows.verifyPatientDocumentReceivedINInbox(propertyLoaderObj, driver, url, username,
				propertyLoaderObj.getPassword(), propertyLoaderObj.getProperty("DocSubject"), body,
				docName + "." + docFormat.trim());

		CommonFlows.verifyDocumentReadStatus(requestId);
	}

	@Test(enabled = true, groups = { "acceptance-PAM" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP174PAMSinglePracticeUser() throws Throwable {

		String username = propertyLoaderObj.getProperty("ccda.username");
		logStep("Get access token for user");
		String accessToken = PAMAuthentication.getAccessToken(username, propertyLoaderObj.getPassword());

		logStep("Get the patient details using Get call");
		String routeURL = pamAPIRoutes.valueOf("PAMPatient").getRouteURL();
		String response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of person from DB");
		String actualPatientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where email_address = '" + username + "'");

		logStep("Get last name of person using PAM api");
		String patientLastName = CommonFlows.getPAMLastName(response);

		assertEquals(patientLastName, actualPatientLastName, "The last name doesnot match with DB value.");
	}

	@Test(enabled = true, groups = { "acceptance-PAM" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP174PAMMultiPracticePracticeLevel() throws Throwable {

		String username = propertyLoaderObj.getProperty("multipracticepl.userp1");
		logStep("Get access token for user for Practice 1");
		String accessToken = PAMAuthentication.getAccessToken(username, propertyLoaderObj.getPassword());

		logStep("Get the patient details using Get call for Practice 1");
		String routeURL = pamAPIRoutes.valueOf("PAMPatient").getRouteURL();
		String response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of person from DB");
		String actualPatientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where email_address = '" + username + "'");

		logStep("Get last name of person using PAM api");
		String patientLastName = CommonFlows.getPAMLastName(response);

		assertEquals(patientLastName, actualPatientLastName, "The last name doesnot match with DB value.");

		username = propertyLoaderObj.getProperty("multipracticepl.userp4");
		logStep("Get access token for user for Practice 4 (Practice Level)");
		accessToken = PAMAuthentication.getAccessToken(username, propertyLoaderObj.getPassword());

		logStep("Get the patient details using Get call for Practice 4");
		response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of person Practice 4 using PAM api");
		patientLastName = CommonFlows.getPAMLastName(response);

		assertEquals(patientLastName, actualPatientLastName, "The last name doesnot match with DB value.");
	}

	@Test(enabled = true, groups = { "acceptance-PAM" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP174PAMGuardianUser() throws Throwable {

		String username = propertyLoaderObj.getProperty("guardian.user");
		logStep("Get access token for guardian");
		String accessToken = PAMAuthentication.getAccessToken(username, propertyLoaderObj.getPassword());

		logStep("Get the guardian details using Get call");
		String routeURL = pamAPIRoutes.valueOf("PAMPatient").getRouteURL();
		String response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of guardian from DB");
		String actualPatientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where email_address = '" + username + "'");

		logStep("Get last name of guardian using PAM api");
		String patientLastName = CommonFlows.getPAMLastName(response);

		assertEquals(patientLastName, actualPatientLastName, "The last name doesnot match with DB value.");
	}

	@Test(enabled = true, groups = { "acceptance-PAM" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP174PAMTrustedPatientUser() throws Throwable {

		String username = propertyLoaderObj.getProperty("trustedpatient.user");
		logStep("Get access token for trusted Patient");
		String accessToken = PAMAuthentication.getAccessToken(username, propertyLoaderObj.getPassword());

		logStep("Get the trusted Patient details using Get call");
		String routeURL = pamAPIRoutes.valueOf("PAMPatient").getRouteURL();
		String response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of trusted Patient from DB");
		String actualPatientLastName = DBUtils.executeQueryOnDB("NGCoreDB",
				"select last_name from person where email_address = '" + username + "@yopmail.com" + "'");

		logStep("Get last name of trusted Patient using PAM api");
		String patientLastName = CommonFlows.getPAMLastName(response);

		assertEquals(patientLastName, actualPatientLastName, "The last name doesnot match with DB value.");
	}

	@Test(enabled = true, groups = { "acceptance-PAM" }, retryAnalyzer = RetryAnalyzer.class)
	public void testPP174PAMLegacyPortalUser() throws Throwable {

		String username = propertyLoaderObj.getProperty("legacyportal.user");
		logStep("Get access token for Legacy Portal Patient");
		String accessToken = PAMAuthentication.getAccessToken(username, username);

		logStep("Get the Legacy Portal Patient details using Get call");
		String routeURL = pamAPIRoutes.valueOf("PAMPatient").getRouteURL();
		String response = NGAPIUtils.setupPAMHttpGetRequest(routeURL, accessToken, 200);

		logStep("Get last name of trusted Patient using PAM api");
		String patientLastName = CommonFlows.getPAMLastName(response);

		assertTrue(!patientLastName.isEmpty(), "Legacy Portal Patient is unable to access the token");
	}
}
