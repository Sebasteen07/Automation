// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.isoreporting.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import java.util.List;

public class ReportingSummaryReportPage extends ReportingNavigationMenu{
    public ReportingSummaryReportPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//*[@id='merchantSelectAll']")
    private WebElement allMerchantsSelect;

    @FindAll({
            @FindBy(how = How.XPATH, using = "//*[@id='merchant.medfusionMerchantId']")
    })
    private List<WebElement> merchantSelect;

    @FindBy(how = How.ID, using = "dateFrom")
    private WebElement dateFrom;

    @FindBy(how = How.ID, using = "dateTo")
    private WebElement dateTo;

    @FindBy(how = How.ID, using = "printReport")
    private WebElement printReport;

    @FindBy(how = How.ID, using = "runSearch")
    private WebElement updateReport;

    @FindBy(how = How.ID, using = "summaryReportDownload")
    private WebElement downloadReport;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[2]")
    private WebElement totalTransactions;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[3]")
    private WebElement totalTransactionAmount;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[4]")
    private WebElement totalVoids;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[5]")
    private WebElement totalVoidAmount;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[6]")
    private WebElement totalRefunds;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[7]")
    private WebElement totalRefundAmount;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[8]")
    private WebElement totalChargebacks;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[9]")
    private WebElement totalChargebackAmount;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[10]")
    private WebElement netSalesTotal;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[11]")
    private WebElement feesTotal;

    @FindBy(how = How.XPATH, using = "//*[@id='summaryReportDetailTable']/tfoot/tr/th[12]")
    private WebElement totalNetFundedAmount;

    public void clickUpdate(){
        updateReport.click();
        try {
            System.out.println("Waiting for search - 5 seconds");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectAllMerchant(){
        allMerchantsSelect.click();
    }

    public void selectSingleMerchant(String merchantName){
        for(WebElement merchant: merchantSelect){
            if(merchant.getText().equalsIgnoreCase(merchantName)){
                merchant.click();
            }
        }
    }

    public void selectMultipleMerchants(String... merchantNames){
        for(String merchant: merchantNames){
                selectSingleMerchant(merchant);
        }
    }

    public void fillFeeReportDates(String fromdate, String todate, String... merchants){
        dateFrom.clear();
        dateFrom.sendKeys(fromdate);
        dateFrom.sendKeys(Keys.ENTER);
        dateTo.clear();
        dateTo.sendKeys(todate);
        dateTo.sendKeys(Keys.ENTER);
        for(String merchant: merchants){
            selectMultipleMerchants(merchant);
        }
    }

    public String getTotalTransactions(){
        String perTrans=totalTransactions.getText();
        return perTrans;
    }

    public String getTotalTransactionAmount(){
        String perTrans=totalTransactionAmount.getText();
        return perTrans;
    }

    public String getTotalVoids(){
        String perTrans=totalVoids.getText();
        return perTrans;
    }

    public String getTotalVoidAmount(){
        String perTrans=totalVoidAmount.getText();
        return perTrans;
    }

    public String getTotalRefunds(){
        String perTrans=totalRefunds.getText();
        return perTrans;
    }

    public String getTotalRefundAmount(){
        String perTrans=totalRefundAmount.getText();
        return perTrans;
    }

    public String getTotalChargebacks(){
        String perTrans=totalChargebacks.getText();
        return perTrans;
    }

    public String getTotalChargebackAmount(){
        String perTrans=totalChargebackAmount.getText();
        return perTrans;
    }

    public String getNetSalesTotal(){
        String perTrans=netSalesTotal.getText();
        return perTrans;
    }

    public String getFeesTotal(){
        String perTrans=feesTotal.getText();
        return perTrans;
    }

    public String gettTotalNetFundedAmount(){
        String perTrans=totalNetFundedAmount.getText();
        return perTrans;
    }
}
