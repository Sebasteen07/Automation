//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.payreporting.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.io.IOException;
import java.text.NumberFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payreporting.pojo.Total;
import com.medfusion.product.object.maps.isoreporting.page.ReportingDailyReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingFundedReportPage;
import com.medfusion.product.object.maps.isoreporting.page.ReportingNavigationMenu;

public class ReportingFundedReportTests extends ReportingAcceptanceTests {

    @BeforeMethod(enabled = true, groups = { "ReportingAcceptanceTests" })
    public void runFundedReport() throws Exception, IOException {
        // Note: Funded report tests are set to run only on production environment where funded transacions are present.
        log("step 1:Navigating to Funded report");
        ReportingNavigationMenu menu = PageFactory.initElements(driver, ReportingNavigationMenu.class);
        ReportingFundedReportPage fundedReport = menu.navigateFunded();
        log("Successfully navigated to funded report");

        PropertyFileLoader testData = new PropertyFileLoader();
        String merchant = testData.getProperty("merchant.element");
        String fromdate = testData.getProperty("funded.from.date");
        String todate = testData.getProperty("funded.to.date");
        log("Running funded report from" + fromdate + "to" + todate + "on merchant" + merchant + "");
        log("Merchant : " + merchant);

        log("step 2: Set dates, merchant and click search");
        fundedReport.fillFundedReportDates(fromdate, todate, merchant);

        log("step3: Assert presence of header elements");
        fundedReport.assertFundedReportElements(merchant);
    }

    @Test(enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testFundedTotals() throws Exception {
        log("This test will get the totals at the bottom of every funded report column for the given date range and assert that the totals match with the sum of individual columns");

        ReportingFundedReportPage fundedReport = PageFactory.initElements(driver, ReportingFundedReportPage.class);
        int rows = fundedReport.countRows();
        assertFalse(rows == 0, "There are no transactions present for the selected date range");
        log("step1:Get sales count total");
        String sales = fundedReport.getSalesCount();

        log("step2:Verify total");
        int count = fundedReport.getSalesCountSum();
        String salescounttotal = Integer.toString(count);
        log("Sales count sum is:" + salescounttotal);

        log("step3:Assert that the values are same");
        Assert.assertEquals(salescounttotal, sales);
        log("Sales count values match");

        log("step4:Get sales amount total");
        String amount = fundedReport.getSalesAmount();

        log("step5:Verify sales amount total");
        double saleamount = fundedReport.getSalesAmountSum();
        NumberFormat format = NumberFormat.getCurrencyInstance();
        String salesamounttotal = format.format(saleamount);
        log("Sales amount sum is:" + salesamounttotal);

        log("step6:Assert that the values are same");
        Assert.assertEquals(salesamounttotal, amount);
        log("Sales amount values match");

        log("step7:Get refunds and chargeback count");
        String chargebackcount = fundedReport.getRefundsAndChargebackCount();

        log("step8:Verify refunds and chargeback count");
        int refundchargebackcount = fundedReport.getRefundsAndChargebackSum();
        String refundschargebackcount = Integer.toString(refundchargebackcount);
        log("Refunds and chargeback count sum is:" + refundschargebackcount);

        log("step9:Assert that the values are same");
        Assert.assertEquals(refundschargebackcount, chargebackcount);
        log("Refunds and chargeback count values match");

        log("step10:Get refunds and chargeback amount total");
        String chargebackamount = fundedReport.getRefundsAndChargebackAmount();

        log("step11:Verify refunds and chargeback amount total");
        double refundchargebackamount = fundedReport.getRefundsAndChargebackAmountSum();
        String refundsandchargebackamount = format.format(refundchargebackamount);
        log("Refunds and chargeback amount sum is:" + refundsandchargebackamount);

        log("step12:Assert that the values are same");
        Assert.assertEquals(refundsandchargebackamount, chargebackamount);
        log("Refunds and chargeback amount values match");

        log("step13:Get net sales total and assert that the sum of all the values match with the total at the bottom");
        String nettotal = fundedReport.getNetSales();
        double netamount = saleamount - refundchargebackamount;
        String netsales = format.format(netamount);
        log("Net sales amount is:" + netsales);
        Assert.assertEquals(netsales, nettotal);
        log("Net sales amount values match");

        log("step14:Get total fees");
        String feetotal = fundedReport.getFees();

        log("step15:Verify fee total");
        double fee = fundedReport.getFeesSum();
        String fees = format.format(fee);
        log("Fee total is:" + fees);

        log("step16:Assert that the values are same");
        Assert.assertEquals(fees, feetotal);
        log("Fee totals match");

        log("step17:Get net funded amount total and assert that the sum of al the values match with the total at the bottom");
        String netfundedtotal = fundedReport.getNetFundedAmount();
        double netfundedamount = netamount - fee;
        String netfundedamounttotals = format.format(netfundedamount);
        log("Net funded amount total is:" + netfundedamounttotals);
        Assert.assertEquals(netfundedamounttotals, netfundedtotal);
        log("Net funded amount totals match");
        log("Successfully verified column totals");
    }


    @Test(enabled = true, groups = { "ReportingAcceptanceTests" })
    public void testFundedDateLinkToDRR() throws InterruptedException, IOException {
        log("This test verifies if funded report values match its corresponding values in the daily report by clicking on funded date values ");

        ReportingFundedReportPage fundedReport = PageFactory.initElements(driver, ReportingFundedReportPage.class);
        int rows = fundedReport.countRows();
        assertFalse(rows == 0, "There are no transactions present for the selected date range");
        log("There are " + rows + " number of entries for the current funded date range");

        int rowcount = driver.findElements(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[*]")).size();
        log("Fetching values from funded report.. ");
        for (int row = 1; row != rowcount + 1; row++) {
            String date = fundedReport.getDateLinkToDRR(row);
            log(" Verifying for funded date : " + date);
            Total fundedTotal = fundedReport.getTotalsFoRow(row);

            log("Clicking on the link to go to drr");
            fundedReport.clickDateLinkToDRR(row);
            ReportingDailyReportPage dailyPage = PageFactory.initElements(driver, ReportingDailyReportPage.class);
            dailyPage.waitForRowsToLoad();
            // this is necessary because for a second there is previous search result displayed
            Thread.sleep(2000);

            Total drrTotal = dailyPage.getTotals();

            log("Asserting funded report values against drr for the funded date: " + date);
            assertEquals(fundedTotal.paymentsCount, drrTotal.paymentsCount, "Sales count value do not match with DRR ");
            assertEquals(fundedTotal.paymentsAmount, drrTotal.paymentsAmount, "Sales amount values doe match");
            assertEquals(fundedTotal.refundsCount, drrTotal.refundsCount,
                    "Refunds and chargeback count values do not match");
            assertEquals(fundedTotal.refundsAmount, drrTotal.refundsAmount, "Refund amount do not match");
            assertEquals(fundedTotal.netAmount, drrTotal.netAmount, "Netsales values do not match");

            driver.navigate().back();
            fundedReport = PageFactory.initElements(driver, ReportingFundedReportPage.class);
        }

        log("Verified: DRR and funded report values match");
    }
}
