//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.medfusion.product.object.maps.isoreporting.page.ReportingMonthlyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;

public class ReportingMonthlyReportTests extends ReportingAcceptanceTests {

    @Test(enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testMonthlyReport() throws InterruptedException {
        log("This test will navigate to monthly report,view a report and assert that report is displayed");
        log("step 1:Navigating to monthly report");
        ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
        ReportingMonthlyReportPage monthlyReport = menu.navigateMonthly();
        log("Successfully navigated to monthly report");

        log("step2: Assert presence of header elements");
        monthlyReport.assertMonthlyReportElements(testData.getProperty("customer.id"));

        log("step3: View monthly report");
        int rows = monthlyReport.countRows();
        assertFalse(rows == 0, "There are no reports to view!");
        monthlyReport.viewReport();
        log("Successfully viewed monthly report");
    }
}