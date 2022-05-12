//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.services;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingLoginPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingMakeAPaymentPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;
import com.medfusion.product.object.maps.isoreporting.page.ReportingPOSVCSMenu;
import com.medfusion.product.object.maps.isoreporting.page.ReportingTransactionDetail;
import com.medfusion.product.object.maps.isoreporting.page.ReportingVCSReceiptPage;

public class GWRServices extends BaseTestNGWebDriver {

	public void logOut(WebDriver driver) {
		log("Logging out");
		ReportingNavigationMenu page = PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingLoginPage loginPage = page.clickLogout();
		assertTrue(loginPage.assessLoginPageElements());
	}

	public ReportingVCSReceiptPage makeVCSPayment(WebDriver driver, String merchant, String vcsAmount,
			String cardholder, String cardNumber, String cardexpiry, String cardcvv, String cardzip,
			String consumerName, String consumerAccountNumber) throws InterruptedException, IOException {

		log("Payment-step 1:Navigating to make a payment");
		ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingPOSVCSMenu posPage = menu.navigateMakeAPayment();

		log("Payment-step 2:Select virtual card swiper");
		ReportingMakeAPaymentPage paymentPage = posPage.clickVCS();
		log("Successfully selected virtual card swiper");

		log("Payment-step 3:Select merchant to make payment on");
		log("Making a payments on: " + merchant);
		paymentPage.selectMerchant(merchant);

		log("Payment-step4: Enter card details and amount to charge");
		log("Amount : " + vcsAmount);
		log("Cardholder : " + cardholder);
		log("Card number : " + cardNumber);
		log("Card expiry : " + cardexpiry);
		log("CVV Number : " + cardcvv);
		log("Zip : " + cardzip);
		log("Consumer name : " + consumerName);
		log("Consumer account number : " + consumerAccountNumber);
		paymentPage.enterCardInformation(vcsAmount, cardholder, cardNumber, cardexpiry, cardcvv, cardzip, consumerName,
				consumerAccountNumber);
		paymentPage.clickCharge();
		ReportingVCSReceiptPage receiptPage = paymentPage.waitForReceiptPopup();
		return receiptPage;
	}

	public static void storeTransactionId(String transactionID) throws IOException {
		Properties configProperty = new Properties();
		File file = new File("configurations.properties");
		FileInputStream fileIn = new FileInputStream(file);
		configProperty.load(fileIn);
		configProperty.setProperty("transactionid", transactionID);
		FileOutputStream fileOut = new FileOutputStream(file);
		configProperty.store(fileOut, "save transaction id");
		fileOut.close();

	}

	public void makeRefund(WebDriver driver, String transactionID, String refundAmount,
			ReportingDailyReportPage dailyPage) throws InterruptedException {
		log("Open transaction detail");
		ReportingTransactionDetail transactionDetail = dailyPage.openTransactionDetail(transactionID);
		log("Refund from payment");

		log("Refund amount: " + refundAmount);
		transactionDetail.performRefund(refundAmount);
		String refundMessage = transactionDetail.getVoidRefundMessage();
		assertEquals(refundMessage, "Approved", "Refund unsuccessful:" + refundMessage);

		log("step 6.View Refund Receipt");
		transactionDetail.viewReceipt();
		// TODO check the receipt
		transactionDetail.closeTransactionDetail();
		// TODO Do search to see if there is new refund transaction with the correct
		// value
	}

	public void makeVoid(WebDriver driver, String transactionID2, ReportingDailyReportPage dailyPage2)
			throws InterruptedException {
		log("Open transaction detail");
		ReportingTransactionDetail transactionDetail = dailyPage2.openTransactionDetail(transactionID2);

		log("Void Transaction");
		transactionDetail.performVoid();
		transactionDetail.verifyVoid();

		log("View void Receipt");
		String voidtransaction = transactionDetail.viewReceipt();
		// TODO check the receipt
		log("Void transaction ID:" + voidtransaction);

		log("Close transaction detail");
		transactionDetail.closeTransactionDetail();
		// TODO Do search to see of the status changed to void
	}

	/*
	 * Verifies the detail when in DRR page
	 */

	public ReportingDailyReportPage verifyTransactionDetail(WebDriver driver, String merchant, String transactionID,
			boolean qbmsPaypal) throws IOException, InterruptedException {

		log("Verifying the transaction detail");
		ReportingDailyReportPage dailyPage = PageFactory.initElements(driver, ReportingDailyReportPage.class);
		log("Step1: Find the payment in DRR based on TID");
		dailyPage.searchTransactionAssertPending(merchant, transactionID);

		log("step 2.Open transaction and assert page objects on transaction detail page");
		ReportingTransactionDetail transactionDetail = dailyPage.openTransactionDetail(transactionID);
		transactionDetail.assertTransactionDetailObjectsForLitleElement();

		// TODO Add asserting for all parts of payment (e.g. cardnumber, tec.)
		dailyPage = transactionDetail.closeTransactionDetail();
		return dailyPage;
	}

	public HttpClient createHttpClientWithBrowserCookies(WebDriver driver) {

		Set<Cookie> seleniumCookies = driver.manage().getCookies();
		CookieStore cookieStore = new BasicCookieStore();

		for (Cookie seleniumCookie : seleniumCookies) {
			BasicClientCookie basicClientCookie = new BasicClientCookie(seleniumCookie.getName(),
					seleniumCookie.getValue());
			basicClientCookie.setDomain(seleniumCookie.getDomain());
			basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
			basicClientCookie.setPath(seleniumCookie.getPath());
			cookieStore.addCookie(basicClientCookie);
		}

		return HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
	}

	public String makeDeclinedPayment(WebDriver driver, String merchant, String cardholder, String cardNumber,
			String cardexpiry, String cardcvv, String cardzip, String consumerName, String consumerAccountNumber)
			throws InterruptedException {
		log("Payment-step 1:Navigating to make a payment");
		ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingPOSVCSMenu posPage = menu.navigateMakeAPayment();

		log("Payment-step 2:Select virtual card swiper");
		ReportingMakeAPaymentPage paymentPage = posPage.clickVCS();
		log("Successfully selected virtual card swiper");

		log("Payment-step 3:Select merchant to make payment on");
		log("Making a payments on: " + merchant);
		paymentPage.selectMerchant(merchant);

		log("Payment-step4: Enter card details and amount to charge");
		log("Amount : " + "0.10");
		log("Cardholder : " + cardholder);
		log("Card number : " + cardNumber);
		log("Card expiry : " + cardexpiry);
		log("CVV Number : " + cardcvv);
		log("Zip : " + cardzip);
		log("Consumer name : " + consumerName);
		log("Consumer account number : " + consumerAccountNumber);
		paymentPage.enterCardInformation("0.10", cardholder, cardNumber, cardexpiry, cardcvv, cardzip, consumerName,
				consumerAccountNumber);
		paymentPage.clickCharge();
		Thread.sleep(5000);

		log("Verify if transaction is declined");
		paymentPage.IsTransactionisDeclined();
		return merchant;
	}

	public void zeroVCSPayment(WebDriver driver, String merchant, String zero, String cardholder, String cardNumber,
			String cardexpiry, String cardcvv, String cardzip, String consumerName, String consumerAccountNumber)
			throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		log("Payment-step 1:Navigating to make a payment");
		ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingPOSVCSMenu posPage = menu.navigateMakeAPayment();

		log("Payment-step 2:Select virtual card swiper");
		ReportingMakeAPaymentPage paymentPage = posPage.clickVCS();
		log("Successfully selected virtual card swiper");

		log("Payment-step 3:Select merchant to make payment on");
		log("Making a payments on: " + merchant);
		paymentPage.selectMerchant(merchant);

		log("Payment-step4: Enter card details and amount to charge");
		log("Zero Amount :" + zero);
		log("Cardholder : " + cardholder);
		log("Card number : " + cardNumber);
		log("Card expiry : " + cardexpiry);
		log("CVV Number : " + cardcvv);
		log("Zip : " + cardzip);
		log("Consumer name : " + consumerName);
		log("Consumer account number : " + consumerAccountNumber);
		paymentPage.enterCardInformation(zero, cardholder, cardNumber, cardexpiry, cardcvv, cardzip, consumerName,
				consumerAccountNumber);
		paymentPage.clickCharge();
		Thread.sleep(2000);

		log("Verify if transaction is declined");
		log("Error received " + paymentPage.IsFormError());
	}
}
