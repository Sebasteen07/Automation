//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.intuit.ifs.csscat.core.BaseTestNGWebDriver;
import com.intuit.ifs.csscat.core.TestConfig;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payreporting.services.GWRServices;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingLoginPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingTransactionDetail;

@Listeners(com.medfusion.listenerpackage.Listener.class)

public class ReportingAcceptanceTests extends BaseTestNGWebDriver {
	protected PropertyFileLoader testData;
	protected GWRServices flows = new GWRServices();
	

	@BeforeMethod(enabled = true, groups = { "ReportingAcceptanceTests"} )
	public void logIntoGWR() throws IOException, NullPointerException, InterruptedException {
		log("Execution Environment: " + IHGUtil.getEnvironmentType());
		log("Execution Browser: " + TestConfig.getBrowserType());
		log("Getting Test Data");
		testData = new PropertyFileLoader();
		
		logStep("Navigating to Gateway Reporting");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getProperty("reporting.url"));
		assertTrue(loginPage.assessLoginPageElements());
		
		logStep("Log in");	
		loginPage.login(testData.getProperty("doctor.login"), testData.getProperty("doctor.password"));
	}
	
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testLoginLogout() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		dailyPage.assesDRRPageElements();
		flows.logOut(driver);
		logStep("Verify Login Page Comes up after Logout");
		ReportingLoginPage loginPage = new ReportingLoginPage(driver, testData.getProperty("reporting.url"));
		assertTrue(loginPage.assessLoginPageElements());
	}
		
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testStatusAll() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		log("Entering date and merchant details");
		log("Merchant : " + merchant);	
		String transactiondate=dailyPage.getCurrentDate();
		log("Running report for:" + transactiondate);
		dailyPage.fillTransactionDateTodayAndMerchant(merchant);
		Thread.sleep(2000);
		
		logStep("Verify if there are any transactions for today.If there are no transactions, then assert that daily totals are zero");
		int rows=dailyPage.countRows();
		if(rows==0){
			dailyPage.assertThatNoTransactions();
			log("There are no transactions for today.Verified that daily totals are zero for status 'All'");
		}
		else{
			log("There are "+rows+"  transactions for today");
			
		logStep("Get the daily totals report values and assert against the daily transaction report totals");		
		dailyPage.compareDailyTotalsWithDailyTransactions();
		log("Daily totals report matches with daily transaction report for status 'All'");
		}
		
	}
	
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testStatusVoid() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		log("Entering date and merchant details");
		log("Merchant : " + merchant);	
		String transactiondate=dailyPage.getCurrentDate();
		log("Running report for:"+transactiondate);
		dailyPage.fillTransactionDateTodayAndMerchant(merchant);
		Thread.sleep(2000);
		
		logStep("Select status'Void'");
		dailyPage.selectStatusVoid();	
		Thread.sleep(3000);
		
		logStep("Verify if there are any void transactions for today.If there are no void transactions, then assert that daily totals are zero");
		int rows=dailyPage.countRows();
		if(rows==0){
			dailyPage.assertThatNoTransactions();
			log("There are no void transactions for today.Verified that daily totals are zero for status 'Void'");
		}
		else{	
			log("There are "+rows+"  void transactions for today");
			
		logStep("Verifying if transactions are filtered by void status");
		dailyPage.verifyVoidStatus();
			
		logStep("Compare daily totals against daily transaction values for status'Void'");
		dailyPage.compareDailyTotalsWithDailyTransactions();
		log("Daily totals report matches with daily transaction report for status 'Void'");
		
		logStep("Assert that void and refund buttons are not present on void transactions");
		ReportingTransactionDetail transactionDetail = dailyPage.openLastTransaction();
		Thread.sleep(2000);
		transactionDetail.verifyVoidTransaction();
		Thread.sleep(2000);
		log("Void and refund buttons are not present on void transactions");
		transactionDetail.closeTransactionDetail();
		}	
		
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testStatusPending() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		log("Entering date and merchant details");
		log("Merchant : " + merchant);	
		String transactiondate=dailyPage.getCurrentDate();
		log("Running report for:"+transactiondate);
		dailyPage.fillTransactionDateTodayAndMerchant(merchant);
		Thread.sleep(2000);
		
		logStep("Select status'Pending'");
		dailyPage.selectStatusPending();
		Thread.sleep(3000);
		
		logStep("Verify if there are any pending transactions for today.If there are no pending transactions, then assert that daily totals are zero");
		int rows=dailyPage.countRows();
		if(rows==0){
			dailyPage.assertThatNoTransactions();
			log("There are no pending transactions for today.Verified that daily totals are zero for status 'Pending'");
		}
		else{
			log("There are "+rows+"  pending transactions for today");
			
		logStep("Verifying if transactions are filtered by pending status");
		dailyPage.verifyPendingStatus();
		
		logStep("Compare daily totals report values against daily transaction values for status'Pending'");
		dailyPage.compareDailyTotalsWithDailyTransactions();
		log("Daily totals report matches with daily transaction report for status 'Pending'");
		}
		
	}
	
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testActivityDateFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify Activity Date filter is working");
		log("Filter by activity date:"+fromdate);
		dailyPage.selectActivityDate(fromdate);
		int activityrows=dailyPage.countRows();
		log("There is/are "+ activityrows + " transaction(s) with the selected activity date");
		
		logStep("Assert daily totals report values with daily transactions report for the selected activity date ");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected activity date");
				
		logStep("Clear filters");
		dailyPage.clearFilters();
		
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testPaymentSourceFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify Payment source filter is working");
		String paysource = testData.getPaymentSource();
		log("Filter by payment source:"+paysource);
		dailyPage.selectPaymentSource(paysource);
		int paysourcerows=dailyPage.countRows();
		log("There is/are " +paysourcerows + " transaction(s) with the selected payment source");		
		
		logStep("Assert daily totals report values with daily transactions report for the selected payment source");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected payment source");
				
		logStep("Clear filters");
		dailyPage.clearFilters();
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testLocationFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify filter on location is working");
		String location = testData.getLocation();
		log("Filter by location:"+location);
		dailyPage.selectLocation(location);
		
		int locationrows=dailyPage.countRows();
		log("There is/are " +locationrows + " transaction(s) with the selected location");
		
		logStep("Assert daily totals report values with daily transactions report for the selected location");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected location");
				
		logStep("Clear filters");
		dailyPage.clearFilters();
		dailyPage.waitFor();
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testStaffNameFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify filter on staff name is working");
		String staff = testData.getStaffName();
		log("Filtering by staff name:"+staff);
		dailyPage.selectStaffName(staff);
		int staffrows=dailyPage.countRows();
		log("There is/are " +staffrows + " transaction(s) with the selected staff ");
		
		logStep("Assert daily totals report values with daily transactions report for the selected staff name");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected staff");
				
		logStep("Clear filters");
		dailyPage.clearFilters();
		dailyPage.waitFor();
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testCardTypeFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify filter on card type is working");
		String cardtype = testData.getCardType();
		log("Filter by card type:"+cardtype);
		dailyPage.selectCardType(cardtype);
		int cardrows=dailyPage.countRows();
		log("There is/are " +cardrows + " transaction(s) for the selected card type ");
		
		logStep("Assert daily totals report values with daily transactions report for the selected card type");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected card type");
				
		logStep("Clear filters");
		dailyPage.clearFilters();
		dailyPage.waitFor();
	}
	
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testGlobalSearchFilter() throws Exception {
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(5000);
		
		logStep("Verify if Global search filter  is working");
		String searchfor = testData.getGlobalSearch();
		log("Do a global search for:"+searchfor);
		dailyPage.globalSearch(searchfor);
		Thread.sleep(5000);
		int searchrows=dailyPage.countRows();
		log("There is/are " +searchrows + " transaction(s) for the selected date range ");
		
		logStep("Make a global search for a specific string and assert daily totals report values with daily transactions report ");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected card type");
		
		logStep("Clear filters");
		dailyPage.clearFilters();
		dailyPage.waitFor();
	}
	
	@Test(enabled = false, groups = { "ReportingAcceptanceTests" })
	public void testFundedDate() throws Exception {
		log("Test filter on funded date for the selected date range and assert that daily totals report matches daily transaction report");
		//Note: Dev environment does not have any funded transactions.Hence assert will fail
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		String fromdate = testData.getHistoricDateFrom();
		String todate = testData.getHistoricDateTo();
		log("Date from : " + fromdate);
		log("Date to : " + todate);
		log("Merchant : " + merchant);	
		log("Set dates and click search");
		dailyPage.runDRRFor(fromdate, todate, merchant);
		Thread.sleep(2000);
		
		logStep("Verify if funded filter  is working");
		String fundedDate = testData.getFundedDate();
		log("Filter by funded date:"+fundedDate);
		dailyPage.selectFundedDate(fundedDate);
		int fundedrows=dailyPage.countRows();
		log("There is/are " +fundedrows + " transaction(s) for the selected date range  ");
		
		logStep("Assert daily totals report values with daily transactions report for the selected funded date");
		dailyPage.compareDailyTotalsWithTransactionsForFilters();
		log("Daily transactions report matches daily totals report for the selected funded date");
		
		logStep("Clear filters");
		dailyPage.clearFilters();
		dailyPage.waitFor();
	}


	
	@Test(enabled = false, groups = { "ReportingAcceptanceTests" })
	public void testFundedTransactions() throws Exception {
		log("Verify if there are any funded transactions and if so assert that daily totals report matches daily transaction report ");
		//Note: Dev/Demo environments do not have any funded transactions.
		ReportingDailyReportPage dailyPage=PageFactory.initElements(driver, ReportingDailyReportPage.class);
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getReportingMerchant1();
		log("Entering date and merchant details");
		log("Merchant : " + merchant);	
		dailyPage.selectMerchant(merchant);
		String transactiondate=dailyPage.getCurrentDate();
		log("Running report for:"+transactiondate);
		dailyPage.fillTransactionDateTodayAndMerchant(merchant);
		Thread.sleep(2000);
		
		logStep("Click on funded transactions radio button and run report");
		dailyPage.clickFundedRadio();
		Thread.sleep(2000);
		int fundedrows=dailyPage.countRows();
		if(fundedrows==0){
			log("If there are no funded transactions, then assert that daily totals is zero");
			dailyPage.assertThatNoTransactions();
			log("There are no funded transactions for today.");
		}
		else{
			log("There are "+fundedrows+" funded transactions present for today");
			
		logStep("Get the daily totals report values for the funded transactions and assert them against the daily transactions report values");
		dailyPage.compareDailyTotalsWithDailyTransactions();
		log("Daily totals report matches daily transactions report for funded transactions");
		}
	}	
}
	
