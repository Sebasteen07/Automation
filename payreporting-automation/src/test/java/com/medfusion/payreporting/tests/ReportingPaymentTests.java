//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import com.medfusion.common.utils.IHGUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import com.medfusion.payreporting.services.GWRServices;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;
import com.medfusion.product.object.maps.isoreporting.page.ReportingPOSPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingVCSReceiptPage;

public class ReportingPaymentTests extends ReportingAcceptanceTests {


	@Test(enabled = true, groups = { "ReportingPaymentsTests", "Funding" })
	public void testVCSPayEmailElement() throws Exception {

		logStep("Make a payment on element merchant and email receipt ");
		ReportingVCSReceiptPage receiptPage = makeVCSPayment(testData.getProperty("merchant.element"));

		logStep("Verify Receipt Data is populated correctly");
		receiptPage.getPaymentSuccessorFailureStatus();

		logStep("Send Email");
		receiptPage.sendEmail(testData.getProperty("email"));
		assertTrue(receiptPage.wasEmailingSuccessCloseToast(), "Email sending was not successful");
		log("Close Receipt and Navigate to DRR Page");
		receiptPage.closeReceiptModal();
		ReportingDailyReportPage dailyPage = navigateToDRRWait(4000);

		logStep("Select the merchant from the dropdown and set current date");
		dailyPage.fillTransactionDateTodayAndMerchant(testData.getProperty("merchant.element"));
		dailyPage.waitFor();
		
		logStep("Get latest transaction details");
		String transactionID = dailyPage.getLastTransactionInfoArray().transactionID;
		String orderID = dailyPage.getLastTransactionInfoArray().orderId;
		String activityDate = dailyPage.getLastTransactionInfoArray().activityDate;
		String amountForVcs = dailyPage.getLastTransactionInfoArray().paymentAmount;		
		dailyPage.saveFundingInfo(transactionID, orderID, activityDate, amountForVcs);		
		log("Transaction ID: " + transactionID + " orderID " + orderID + " activityDate " + activityDate + " amountForVcs "
				+ amountForVcs);

		logStep("Verify transaction details");
		flows.verifyTransactionDetail(driver, testData.getProperty("merchant.element"), transactionID, false);
		GWRServices.storeTransactionId(transactionID);

	}
	
	@Test(enabled = true, groups = { "ReportingPaymentsTests" })
	public void testPayRefundElement() throws Exception {
		logStep("Make a sale for merchant - " + testData.getProperty("merchant.element"));
		String transactionID = makeVCSPaymentEmailReceipt(testData.getProperty("merchant.element"));
		log("Transaction was successful, transaction ID is " + transactionID);

		logStep("Verify transaction details");
		ReportingDailyReportPage dailyPage = flows.verifyTransactionDetail(driver,
				testData.getProperty("merchant.element"), transactionID, false);

		logStep("Make a refund of the transaction - " + transactionID + " for refund amount "
				+ testData.getProperty("refund.amount"));
		flows.makeRefund(driver, transactionID, IHGUtil.createRandomNumericString(2), dailyPage);
		log("Refund was successful");
	}

	@Test(enabled = true, groups = { "ReportingPaymentsTests" })
	public void testPayVoidElement() throws Exception {
		logStep("Make a sale for merchant - " + testData.getProperty("merchant.element"));
		String transactionID = makeVCSPaymentEmailReceipt(testData.getProperty("merchant.element"));
		log("Transaction was successful, transaction ID is " + transactionID);

		logStep("Verify transaction details");
		ReportingDailyReportPage dailyPage = flows.verifyTransactionDetail(driver,
				testData.getProperty("merchant.element"), transactionID, false);

		logStep("Make a refund of the transaction - " + transactionID + " for refund amount "
				+ testData.getProperty("refund.amount"));
		flows.makeVoid(driver, transactionID, dailyPage);
		log("Refund was successful");
	}

	@Test(enabled = true, groups = { "ReportingPaymentsTests" })
	public void createDeclinedElementTransaction() throws IOException, NullPointerException, InterruptedException {
		logStep("Make a declined payment on element merchant");
		makeDeclinedPayment(testData.getProperty("merchant.element"));
	}

	/*
	 * make VCS payment using the default data
	 * 
	 * @Return TransactionID
	 */
	public ReportingVCSReceiptPage makeVCSPayment(String merchant)
			throws NullPointerException, InterruptedException, IOException {
		return flows.makeVCSPayment(driver, merchant, IHGUtil.createRandomNumericString(3),
				testData.getProperty("card.holder.name"), testData.getProperty("card.number"),
				testData.getProperty("card.expiration"), testData.getProperty("card.cvv"),
				testData.getProperty("card.zip"), testData.getProperty("consumer.name"),
				testData.getProperty("consumer.account.number"));
				
	}

	@Test(enabled = true, groups = { "ReportingPaymentsTests" })
	public void createZeroDollarTransaction() throws IOException, NullPointerException, InterruptedException {
		logStep("Make a zero dollar payment on element merchant");
		flows.zeroVCSPayment(driver, testData.getProperty("merchant.element"), testData.getProperty("zero"),
				testData.getProperty("card.holder.name"), testData.getProperty("card.number"),
				testData.getProperty("card.expiration"), testData.getProperty("card.cvv"),
				testData.getProperty("card.zip"), testData.getProperty("consumer.name"),
				testData.getProperty("consumer.account.number"));

	}

	public String makeVCSPaymentEmailReceipt(String merchant) throws Exception {
		log("Make Payment");
		ReportingVCSReceiptPage receiptPage = makeVCSPayment(merchant);
		log("Close Receipt and Navigate to DRR Page");
		receiptPage.closeReceiptModal();
		log("Navigate to DRR");
		ReportingDailyReportPage dailyPage = navigateToDRRWait(4000);
		log("Input 'From', 'To' date, 'Merchant' and wait for table to populate");
		dailyPage.fillTransactionDateTodayAndMerchant(merchant);
		dailyPage.waitFor();
		log("Get the latest transaction ID");
		String transactionID = dailyPage.getLastTransactionInfoArray().transactionID;
		log("Transaction ID: " + transactionID);
		return transactionID;
	}

	public String makeDeclinedPayment(String merchant) throws NullPointerException, InterruptedException, IOException {
		return flows.makeDeclinedPayment(driver, merchant, testData.getProperty("card.holder.name"),
				testData.getProperty("card.number"), testData.getProperty("card.expiration"),
				testData.getProperty("card.cvv"), testData.getProperty("card.zip"),
				testData.getProperty("consumer.name"), testData.getProperty("consumer.account.number"));
	}

	public ReportingDailyReportPage navigateToDRRWait(long time) throws InterruptedException {
		ReportingNavigationMenu gwrPage = PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingDailyReportPage dailyPage = gwrPage.navigateDaily();
		log("Wait for the transaction to get to DRR");
		Thread.sleep(time);
		return dailyPage;
	}

}
