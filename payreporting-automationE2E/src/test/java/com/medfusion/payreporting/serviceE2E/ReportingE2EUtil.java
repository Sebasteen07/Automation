//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.serviceE2E;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payreporting.pojo.GWRTransactionLine;
import com.medfusion.payreporting.pojo.TransactionDetailInfo;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingLoginPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingTransactionDetail;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoLoginPage;
import com.medfusion.product.object.maps.patientportal2.page.HomePage.JalapenoHomePage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsConfirmationPage;
import com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage.JalapenoPayBillsMakePaymentPage;
import com.medfusion.product.object.maps.practice.page.PracticeHomePage;
import com.medfusion.product.object.maps.practice.page.PracticeLoginPage;
import com.medfusion.product.object.maps.practice.page.virtualCardSwiper.VirtualCardSwiperPage;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;


public class ReportingE2EUtil {
	
	public final static String EXPIRY_MONTH = "12";
	public final static String EXPIRY_YEAR = "2027";
	public final static String COMMENT = "VCS Automation";
	
	public static String getTwoDigitAmount(){
		Random ra = new Random();
		return new StringBuilder(Integer.toString(((ra.nextInt(9)+ 1)*10 + ra.nextInt(10))) + "." + Integer.toString(ra.nextInt(10)) + Integer.toString(ra.nextInt(10))).toString();
	}

	public static boolean payPracticeVCSProcessGwr(WebDriver driver, PropertyFileLoader testData, String amount,String accountNumber, String merchant, String location,
			boolean voidGWR, boolean refundGWR, boolean detailed, boolean isElement) throws InterruptedException {
		if (voidGWR && refundGWR) throw new IllegalArgumentException("Void and refund are mutually exclusive");
		ReportingDailyReportPage dailyPage;
		TransactionDetailInfo tInfo1 = null;
		TransactionDetailInfo tInfo2 = null;
		String detailTID = "";
		String orderID = "";
		boolean pass = false;
		try
		{
			Log4jUtil.log(" Step 1 :Navigate to Practice portal login page");
			PracticeLoginPage practiceLogin = new PracticeLoginPage(driver, testData.getProperty("practice.url"));
			PracticeHomePage practiceHome ;
			practiceHome = practiceLogin.login(testData.getProperty("doctor.login"), testData.getProperty("doctor.password"));
			assertTrue(practiceHome.isHomePageLoaded(),"Expected to see 'Recent Activity' on home page, but it was not found");

			Log4jUtil.log("Step 2: Navigate to Virtual Card Swiper page.");
			VirtualCardSwiperPage virtualCardSwiper = practiceHome.clickOnVirtualCardSwiper();
			assertTrue(virtualCardSwiper.checkVirtualCardSwiperPage(), "Virtual Card Swiper page is not displayed properly.");

			Log4jUtil.log(" Step 3: Add card info and click on 'Click Here To Charge Card' button.  ");
			virtualCardSwiper.addCreditCardInfo(testData.getFirstName(), testData.getCardNumber(), testData.getCardType(), EXPIRY_MONTH,
					EXPIRY_YEAR, amount, testData.getCardCvv(), testData.getCardZip(), accountNumber,
					(testData.getFirstName()+" "+testData.getLastName()), COMMENT,location);
			
			Log4jUtil.log("Step 4: Verify whether the payment is completed successfully.");
			assertEquals(virtualCardSwiper.getPaymentCompletedSuccessMsg().contains("Payment completed"), true,
					"The payment is completed properly.");
			
			Log4jUtil.log("Payment done, moving to GWR to look for it");
			
			Log4jUtil.log("Step 5: Navigating to Gateway Reporting");
			new ReportingLoginPage(driver, testData.getReportingUrl()).login(testData.getProperty("doctor.login"), testData.getProperty("doctor.password"));		
			dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
			dailyPage.assesDRRPageElements();
			
			Log4jUtil.log("Step 6: Setting up search criteria and looking for the expected payment :" + amount);					
			//each failed check rests for 2 seconds		
			for(int retry = 30; retry > 0; retry--){
				Thread.sleep(2000);
				dailyPage.fillTransactionDateTodayAndMerchant(merchant);
				dailyPage.selectCheckPaymentAmountFilter("$"+amount);
				Thread.sleep(5000);// This is needed here
				if (dailyPage.getLastTransactionPaymentAmount().equals(amount)) pass = true;
				if (pass) {	
					break;
				}
			}	
			if(!pass){
				Thread.sleep(3000);
				Log4jUtil.log("After the maximum of retries, the latest paymentAmount still did not match the submit");
				dailyPage.clickLogout();
				return false;
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
		Log4jUtil.log("Expected amount " + amount + " located, success!");
		if (detailed){
			Log4jUtil.log("Step 7: Performing a detailed comparison of actual and expected transaction line");
			GWRTransactionLine submitted = new GWRTransactionLine("", "", "VCS",location,
					testData.getProperty("doctor.full.name"), (testData.getFirstName()+" "+testData.getLastName()), 
					accountNumber, testData.getCardType(), testData.getFirstName(),
					testData.getCardNumber().substring(testData.getCardNumber().length() - 4), "", "",
					"Pending", "$"+amount, "$0.00");
			if (submitted.equalsSkipLeftEmpties(dailyPage.getLastTransactionInfoArray())){
				Log4jUtil.log("Transaction line compared successfuly");
			}
			else return false;
			
			Log4jUtil.log("Step 8: Verifying agianst transaction detail modal");
			ReportingTransactionDetail detail = dailyPage.openLastTransaction();
			detail.waitForDetailActivityDate();
			tInfo1 = new TransactionDetailInfo(null, null, "VCS", location, testData.getProperty("doctor.full.name"), (testData.getFirstName()+" "+testData.getLastName()), accountNumber,
					testData.getCardType(), testData.getFirstName(), testData.getCardNumber().substring(testData.getCardNumber().length() - 4), null, null, "Payment", "Pending",
					"$" + amount, "$0.00", true, isElement);
			
			if (tInfo1.verifyIgnoreLeftBlanks(detail.getTransactionDetailInfo())){
				Log4jUtil.log("Transaction detail verified successfuly");
				detailTID = detail.getTransactionDetailTransactionId();
				detail.clickCloseButton();
			}
			else {
				Log4jUtil.log("Transaction detail did not match expectations!");
				Log4jUtil.log("Expected: " + tInfo1.toString());
				Log4jUtil.log("Found: " + detail.getTransactionDetailInfo().toString());
				detail.clickCloseButton();
				return false;
			}
			
		}
		Log4jUtil.log("For void/refund");
		if(voidGWR || refundGWR){
			if(voidGWR) {
				Log4jUtil.log("Voiding the payment");
				ReportingTransactionDetail detail = dailyPage.openLastTransaction();
				detail.performVoid();
				detail.getVoidRefundMessage();
				detail.clickCloseButton();
			}
			if(refundGWR) {
				Log4jUtil.log("Refunding the payment");
				ReportingTransactionDetail detail = dailyPage.openLastTransaction();				
				detail.performRefund(amount);				
				detail.getVoidRefundMessage();
				
				detail.clickCloseButton();
			}
			try{
				dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
				dailyPage.assesDRRPageElements();
				Log4jUtil.log("Setting up search criteria and looking for the expected :" + (voidGWR ? "void" : "refund"));
				//check rearm
				pass = false;
				//timeout is implicit (10s)		
				for(int retry = 30; retry > 0; retry--){					
					dailyPage.fillTransactionDateTodayAndMerchant(merchant);					
					dailyPage.selectStatus((voidGWR ? "Void" : "Pending"));										
					if(dailyPage.selectCheckRefundAmountFilter("$"+amount)){
						pass = true;									
						break;
					}												
					Log4jUtil.log("Lookup attempt " + retry + "failed, retrying");
				}
				if(!pass){
					Log4jUtil.log("After the maximum of retries, the latest" + (voidGWR ? "void" : "refund") + "still did not match the submit");
					dailyPage.clickLogout();
					return false;
				}
				else if(detailed){						
					ReportingTransactionDetail detail = dailyPage.openLastTransaction();
					detail.waitForDetailActivityDate();
					if (refundGWR){
						//Location empty!
						tInfo2 = new TransactionDetailInfo(null, null, "VCS", location, testData.getProperty("doctor.full.name"), (testData.getFirstName()+" "+testData.getLastName()),accountNumber,
								null, null,testData.getCardNumber().substring(testData.getCardNumber().length() - 4), null, null, "Refund", "Pending",
								"$0.00", "$"+amount, true, false);
					} else if(voidGWR){
						tInfo2 = new TransactionDetailInfo(null, null, "VCS", location, testData.getProperty("doctor.full.name"),(testData.getFirstName()+" "+testData.getLastName()),accountNumber,
								testData.getCardType(), testData.getFirstName(),testData.getCardNumber().substring(testData.getCardNumber().length() - 4), orderID, detailTID, "Void",
								"Void", "$"+amount, "$"+amount, false, false);
					}					
					
					if (tInfo2.verifyIgnoreLeftBlanks(detail.getTransactionDetailInfo())){
						Log4jUtil.log("Transaction void/refund detail verified successfuly");						
						detail.clickCloseButton();
					}
					else {
						Log4jUtil.log("Transaction void/refund detail did not match expectations!");
						Log4jUtil.log("Expected: " + tInfo1.toString());
						Log4jUtil.log("Found: " + detail.getTransactionDetailInfo().toString());
						detail.clickCloseButton();
						return false;
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		} 		
		return true;
	}

	public static boolean payPortal2FindInGWR(WebDriver driver, PropertyFileLoader testData, String amount, String merchant, String location, String accountNumber,
			 CreditCard creditCard, boolean detailed, boolean isElement) throws Exception {
		
		ReportingDailyReportPage dailyPage;
		TransactionDetailInfo tInfo1;
		boolean pass = false;
		
			Log4jUtil.log("Step 5: Navigating to Gateway Reporting");
			new ReportingLoginPage(driver, testData.getReportingUrl()).login(testData.getProperty("doctor.login"), testData.getProperty("doctor.password"));		
			dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
			dailyPage.assesDRRPageElements();
			
			Log4jUtil.log("Step 6: Setting up search criteria and looking for the expected payment");		
			//each failed check rests for 2 seconds
			
			for(int retry = 30; retry > 0; retry--){
				dailyPage.fillTransactionDateTodayAndMerchant(merchant);
				dailyPage.selectCheckPaymentAmountFilter("$"+amount);
				Thread.sleep(5000);// This is needed here
				if (dailyPage.getLastTransactionPaymentAmount().equals(amount)) pass = true;
				if (pass) {	
					//dailyPage.fillTransactionId();
					String transactionID = dailyPage.getLastTransactionInfoArray().transactionID;
					Log4jUtil.log("Transaction ID: " + transactionID);
					
					dailyPage.fillTransanctionID(transactionID);
					dailyPage.waitFor();
					break;
				}
			}	
			Log4jUtil.log("Step 7: Get the order id and transaction id of the OLBP payment");		
			String transactionId = dailyPage.getTransactionReportTransactionId();
			String orderId = dailyPage.getOrderId();
			Log4jUtil.log("Transaction id:"+transactionId);
			
			if (detailed){
			Log4jUtil.log(" Step 7: Performing a detailed comparison of actual and expected transaction line");
			GWRTransactionLine submitted = new GWRTransactionLine("", "", "OLBP", location,"", testData.getProperty("last.name") + ", " + testData.getProperty("first.name"), accountNumber, creditCard.getType().toString(), testData.getProperty("first.name") + " " + testData.getProperty("last.name"),creditCard.getLastFourDigits(), transactionId,"", "Pending", "$"+amount, "$0.00");
			if (submitted.equalsSkipLeftEmpties(dailyPage.getLastTransactionInfoArray())){
				Log4jUtil.log("Transaction line successfuly compared vs expected projection");
			}
			else return false;
			
			Log4jUtil.log(" Step 8: View the transaction details in the Transaction details modal");
			ReportingTransactionDetail detail = dailyPage.openLastTransaction();
			detail.waitForDetailActivityDate();
			tInfo1 = new TransactionDetailInfo("", "", "OLBP", location, "", testData.getProperty("last.name") + ", " + testData.getProperty("first.name"),
					accountNumber, creditCard.getType().toString(),
					testData.getProperty("first.name") + " " + testData.getProperty("last.name"), creditCard.getLastFourDigits(), orderId, transactionId, "Payment", "Pending",
					"$" + amount, "$0.00", true, isElement);
			if (tInfo1.verifyIgnoreLeftBlanks(detail.getTransactionDetailInfo())){
				Log4jUtil.log("Transaction detail verified successfuly");
				detail.clickCloseButton();
			}
			else {
				Log4jUtil.log("Transaction detail did not match expectations!");
				Log4jUtil.log("Expected: " + tInfo1.toString());
				Log4jUtil.log("Found: " + detail.getTransactionDetailInfo().toString());
				detail.clickCloseButton();
				return false;
			}		
		}
	return true;
	}

	public static boolean payPortalOLBPGwrVoidRefund(WebDriver driver,PropertyFileLoader testData, String amount,String accountNumber,
		String merchant,String location, boolean voidGWR, boolean refundGWR, boolean detailed, boolean isElement) throws Exception {
		
		TransactionDetailInfo tInfo2 = null;
		if (voidGWR && refundGWR) throw new IllegalArgumentException("Void and refund are mutually exclusive");
		
		Log4jUtil.log("Step 1: Login to patient portal and make an OLBP payment");
		JalapenoLoginPage loginPage = new JalapenoLoginPage(driver, testData.getProperty("url.portal2"));
		JalapenoHomePage homePage = loginPage.login(testData.getProperty("user.id"), testData.getProperty("password"));
		
		Log4jUtil.log("Step 2: Navigate tp the pay bills page");
		JalapenoPayBillsMakePaymentPage payBillsPage = homePage.clickOnNewPayBills(driver);
		payBillsPage.removeAllCards();
		Assert.assertFalse(payBillsPage.isAnyCardPresent());
		
		Log4jUtil.log("Step 3: Make an OLBP payment");
		CreditCard creditCard = new CreditCard(CardType.Visa, testData.getProperty("first.name") + " " + testData.getProperty("last.name"));
		JalapenoPayBillsConfirmationPage confirmationPage = payBillsPage.fillPaymentInfo(amount, accountNumber,creditCard);
		Log4jUtil.log("Confirm and Submitting payment");
		assertTrue(confirmationPage.getCreditCardEnding().equals(creditCard.getLastFourDigits()));
		homePage = confirmationPage.commentAndSubmitPayment("Testing payment from number: " + accountNumber);
		Thread.sleep(2000);
		assertTrue(homePage.wasPayBillsSuccessfull());
		
		Log4jUtil.log("Step 4: Check if payment made is shown in GWR");
		if (!payPortal2FindInGWR(driver, testData, amount, merchant, location, accountNumber,creditCard, detailed, isElement)) {
			Log4jUtil.log("Payment failed to process or to be found");
			return false;
		}
		ReportingDailyReportPage dailyPage = new ReportingDailyReportPage(driver);
		Log4jUtil.log("Expected amount " + amount + " located, success!");
		
		Log4jUtil.log("For void or refund step");
		if(voidGWR || refundGWR){
			if(voidGWR) {
				Log4jUtil.log("Voiding the payment");
				ReportingTransactionDetail detail = dailyPage.openLastTransaction();
				detail.performVoid();
				detail.getVoidRefundMessage();
				detail.clickCloseButton();
			}
			if(refundGWR) {
				Log4jUtil.log("Refunding the payment");
				ReportingTransactionDetail detail = dailyPage.openLastTransaction();				
				detail.performRefund(amount);				
				detail.getVoidRefundMessage();
				
				detail.clickCloseButton();
			}
			
			try{
				Log4jUtil.log("Setting up search criteria and looking for the expected :" + (voidGWR ? "void" : "refund"));
				boolean pass = false;
				pass = false;
				//timeout is implicit (10s)
			//	dailyPage.clearTransactionId();
				for(int retry = 30; retry > 0; retry--){					
					dailyPage.fillTransactionDateTodayAndMerchant(merchant);					
					dailyPage.selectStatus((voidGWR ? "Void" : "Pending"));	
					dailyPage.clearTransactionId();
					if(dailyPage.selectCheckRefundAmountFilter("$"+amount)){
						pass = true;									
						break;
					}												
					Log4jUtil.log("Lookup attempt " + retry + "failed, retrying");
				}
				if(!pass){
					Log4jUtil.log("After the maximum of retries, the latest" + (voidGWR ? "void" : "refund") + "still did not match the submit");
					dailyPage.clickLogout();
					return false;
				} else if(detailed){						
					ReportingTransactionDetail detail = dailyPage.openLastTransaction();
					detail.waitForDetailActivityDate();
					if (refundGWR){
						//Location empty!
						tInfo2 = new TransactionDetailInfo(null, null, "OLBP", location, null,  testData.getProperty("last.name") + ", " + testData.getProperty("first.name"), accountNumber, creditCard.getType().toString(), "", creditCard.getLastFourDigits(), null, null, "Refund", "Pending", "$0.00", "$"+amount, true, false);
					} else if(voidGWR){
						tInfo2 = new TransactionDetailInfo(null, null, "OLBP", location, null, testData.getProperty("last.name") + ", " + testData.getProperty("first.name"), accountNumber, creditCard.getType().toString(), testData.getProperty("first.name") + " " + testData.getProperty("last.name"), creditCard.getLastFourDigits(), null, null, "Void", "Void", "$"+amount, "$"+amount, false, false);
					}					
					
					if (tInfo2.verifyIgnoreLeftBlanks(detail.getTransactionDetailInfo())){
						Log4jUtil.log("Transaction void/refund detail verified successfuly");						
						detail.clickCloseButton();
					}
					else {
						Log4jUtil.log("Transaction void/refund detail did not match expectations!");
						Log4jUtil.log("Expected: " + tInfo2.toString());
						Log4jUtil.log("Found: " + detail.getTransactionDetailInfo().toString());
						detail.clickCloseButton();
						return false;
					}
				}				
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		} 		
		return true;
	}
		
}
