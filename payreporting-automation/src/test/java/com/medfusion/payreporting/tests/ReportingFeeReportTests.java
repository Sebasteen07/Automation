//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.object.maps.isoreporting.page.ReportingFeeReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;

public class ReportingFeeReportTests extends ReportingAcceptanceTests {
	
	@BeforeMethod(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void runFeeReport() throws IOException, InterruptedException{
		//Note:Fee report tests can be only run on production environment
		log("step 1:Navigating to Fee report");
		ReportingNavigationMenu menu=PageFactory.initElements(driver, ReportingNavigationMenu.class);
		ReportingFeeReportPage feeReport= menu.navigateFee();
		log("Successfully navigated to Fee report");
		
		PropertyFileLoader testData = new PropertyFileLoader();
		String merchant = testData.getProperty("fee.report.merchant");
		String fromdate = testData.getProperty("fee.from.date");
		String todate = testData.getProperty("fee.to.date");
		log("Running fee report from" +fromdate+ "to"+ todate+ "on merchant"+merchant+ "");
		log("Merchant : " + merchant);	
		
		log("step 2: Set dates, merchant and click search");
		feeReport.fillFeeReportDates(fromdate, todate, merchant);
		feeReport.clickUpdate();
		
		log("step3: Assert presence of header elements");
		feeReport.assertFeeReportElements(merchant);
	}
	
	@Test(enabled = true, groups = { "ReportingAcceptanceTests" })
	public void testFeeTotals() throws InterruptedException  {
		log("This test will verify calculations and totals within the fee report");
		ReportingFeeReportPage feeReport=PageFactory.initElements(driver, ReportingFeeReportPage.class);
		int rows=feeReport.countRows();
		assertFalse(rows==0, "No data for current search criteria");
		feeReport.verifyFeeTotals();
		feeReport.verifyColumnTotals();
		log("Successfully verified fee report calculation");
	}
	
}
