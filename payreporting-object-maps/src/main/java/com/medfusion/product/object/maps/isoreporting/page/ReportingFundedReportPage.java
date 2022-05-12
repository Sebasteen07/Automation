//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;
import java.io.File;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import com.medfusion.payreporting.pojo.Total;


public class ReportingFundedReportPage extends ReportingNavigationMenu  {

	public ReportingFundedReportPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(how = How.ID, using = "merchantSelect")
	private WebElement merchantSelect;
	
	@FindBy(how = How.ID, using = "dateFrom")
	private WebElement dateFrom;
	
	@FindBy(how = How.ID, using = "dateTo")
	private WebElement dateTo;
	
	@FindBy(how = How.ID, using = "printReport")
	private WebElement printReportButton;
	
	@FindBy(how = How.ID, using = "runSearch")
	private WebElement updateReport;
	
	@FindBy(how = How.ID, using = "fundedReportDownload")
	private WebElement downloadButton;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[2]/div[1]")
	private WebElement merchantName;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[2]/div[3]")
	private WebElement customerID;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[1]/div")
	private WebElement fundeReportHeader;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[2]")
	private WebElement salesCount;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[3]")
	private WebElement salesAmount;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[4]")
	private WebElement refundsAndChargeback;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[5]")
	private WebElement refundsAndChargebackAmount;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[6]")
	private WebElement netSales;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[7]")
	private WebElement fees;
	
	@FindBy(how=How.XPATH,using="//*[@id='fundedReportsTable']/tfoot/tr/th[8]")
	private WebElement netFundedAmount;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[1]/div")
	private WebElement fundedReportHeader;
	
	public int countRows(){
		int rowcount=driver.findElements(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[*]")).size();
		return rowcount;
	}
	
	public String getSalesCount(){
		return salesCount.getText();
	}
	
	
	public void downloadCsv() throws InterruptedException{
		downloadButton.click();
		downloadButton.sendKeys(Keys.ENTER);
		Thread.sleep(5000);
		Assert.assertTrue("The csv file could not be downloaded",isFileDownloaded(downloadPath, extension));
	}
	

	private boolean isFileDownloaded(String downloadPath, String extension) {
		boolean flag=false;
	    File dir = new File(downloadPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        flag = false;
	    }
	    
	    for (int i = 1; i < files.length; i++) {
	    	if(files[i].getName().contains(extension)) {
	    		flag=true;
	    	}
	    }
	    return flag;
	}
	
	
	public File getLatestFilefromDir(String downloadPath){
	    File dir = new File(downloadPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }
	
	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
	
	public String getSalesAmount(){
		return salesAmount.getText();
	}
	
	public String getRefundsAndChargebackCount(){
		return refundsAndChargeback.getText();
	}
	
	public String getRefundsAndChargebackAmount(){
		return refundsAndChargebackAmount.getText();
	}
	
	public String getNetSales(){
		return netSales.getText();
	}
	
	public String getFees(){
		return fees.getText();
	}
	
	public String getNetFundedAmount(){
		return netFundedAmount.getText();
	}
	
	
	
	public void clickUpdate(){
		Assert.assertTrue("Update report button is not present", updateReport.isDisplayed());
		updateReport.click();
		try {
			System.out.println("Waiting for search");
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void selectMerchant(String merchant){
		Select sele=new Select(merchantSelect);
		sele.selectByVisibleText(merchant);
	}
	public void waitFor(){
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}
	public void verifyPrintButtonIsPresent() throws InterruptedException{
		Assert.assertTrue("Print report button is not displayed", printReportButton.isDisplayed());
	}
	
	public void verifyDownloadButtonIsPresent() throws InterruptedException{
		Assert.assertTrue("Download report button is not displayed", downloadButton.isDisplayed());
	}
	public void assertFundedReportElements(String merchant){
		Assert.assertNotNull("Merchant name is not present in the funded report", merchantName);
		Assert.assertEquals(merchant, merchantName.getText());
		Assert.assertNotNull("Customer ID is not present in the funded report", customerID);
		Assert.assertTrue("Either the merchant is not selected or date range set was invalid",!driver.getPageSource().contains("Data Error"));
		Assert.assertTrue("Unexpected error pop up is seen",!driver.getPageSource().contains("Unexpected Error"));
		Assert.assertTrue("System error pop up is seen",!driver.getPageSource().contains("System Error"));
		Assert.assertTrue("Download report button is not displayed", downloadButton.isDisplayed());
		Assert.assertTrue("Print report button is not displayed", printReportButton.isDisplayed());
		Assert.assertEquals("Funded Totals Report", fundeReportHeader.getText());
	}
	
	public void fillFundedReportDates(String fromdate, String todate, String merchant){
		dateFrom.clear();
		dateFrom.sendKeys(fromdate);
		dateFrom.sendKeys(Keys.ENTER);
		dateTo.clear();
		dateTo.sendKeys(todate);
		dateTo.sendKeys(Keys.ENTER);
		selectMerchant(merchant);
		clickUpdate();
		Assert.assertFalse("Either the merchant is not selected or date range set was invalid",driver.getPageSource().contains("Data Error"));
		Assert.assertFalse("Funded report could not be viewed.Unexpected error message is seen",driver.getPageSource().contains("Unexpected Error"));
	}
	
	public int getSalesCountSum(){
		int rows=countRows();
		int salescount=0;
		for(int count=1;count<=rows;count++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr["+count+"]/td[2]")).getText();
				System.out.println(sum+ "  (at row: "+count+")"); 
				salescount =salescount+Integer.parseInt(sum);
				} catch (Exception e){
				}
			}
		return salescount;
		}
	
	public double getSalesAmountSum(){
		int rows=countRows();
		double salesamount=0;
		for(int count=1;count<=rows;count++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr["+count+"]/td[3]")).getText();
				System.out.println(sum.substring(1)+ "  (at row: "+count+")"); 
				salesamount =salesamount+ Double.parseDouble(sum.substring(1).trim());
				} catch (Exception e){
				}
			}
		return salesamount;
		}
	
	public int getRefundsAndChargebackSum(){
		int rows=countRows();
		int refundscount=0;
		for(int count=1;count<=rows;count++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr["+count+"]/td[4]")).getText();
				System.out.println(sum+ "  (at row: "+count+")"); 
				refundscount =refundscount+Integer.parseInt(sum);
				} catch (Exception e){
				}
			}
		return refundscount;
		}
	
	public double getRefundsAndChargebackAmountSum(){
		int rows=countRows();
		double refundsamount=0;
		for(int count=1;count<=rows;count++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr["+count+"]/td[5]")).getText();
				System.out.println(sum.substring(1)+ "  (at row: "+count+")"); 
				refundsamount =refundsamount+ Double.parseDouble(sum.substring(1).trim());
				} catch (Exception e){
				}
			}
		return refundsamount;
		}
	
	
	public double getFeesSum(){
		int rows=countRows();
		double fees=0;
		for(int count=1;count<=rows;count++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr["+count+"]/td[7]/a")).getText();
				System.out.println(sum.substring(1)+ "  (at row: "+count+")"); 
				fees =fees+ Double.parseDouble(sum.substring(1).trim());
				} catch (Exception e){
				}
			}
		return fees;
		}
	
    public Total getTotalsFoRow(int row) {
        Total total = new Total();
        WebElement salesCount = driver
                .findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[2]"));
        total.paymentsCount = salesCount.getText();
        System.out.println("Sales count :" + total.paymentsCount);
        WebElement salesAmount = driver
                .findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[3]"));
        total.paymentsAmount = salesAmount.getText();
        System.out.println("Sales amount is:" + total.paymentsAmount);
        WebElement refundsCount = driver
                .findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[4]"));
        total.refundsCount = refundsCount.getText();
        System.out.println("Refunds and chargeback count is:" + total.refundsCount);
        WebElement refundsandchargebackamount = driver
                .findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[5]"));
        total.refundsAmount = refundsandchargebackamount.getText();
        System.out.println("Refunds and chargeback amount is:" + total.refundsAmount);
        WebElement netSalesElement = driver
                .findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[6]/a"));
        total.netAmount = netSalesElement.getText();
        if (total.netAmount.startsWith("(")) {
            total.netAmount = "$-" + total.netAmount.substring(2, total.netAmount.length() - 1);
        }
        System.out.println("Net sales amount is:" + total.netAmount);
        return total;
    }

    public String getDateLinkToDRR(int row) {
        return driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[1]/a")).getText();
    }

    public void clickDateLinkToDRR(int row) {
        driver.findElement(By.xpath("//*[@id='fundedReportsTable']/tbody/tr[" + row + "]/td[1]/a")).click();
    }

	
}
