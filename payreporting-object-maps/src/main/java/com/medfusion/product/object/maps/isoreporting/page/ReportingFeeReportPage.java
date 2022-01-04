//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.text.NumberFormat;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;


public class ReportingFeeReportPage extends ReportingNavigationMenu {
public ReportingFeeReportPage(WebDriver driver) {
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
	private WebElement printReport;
	
	@FindBy(how = How.ID, using = "runSearch")
	private WebElement updateReport;
	
	@FindBy(how = How.ID, using = "fundedReportDownload")
	private WebElement downloadReport;
	
	@FindBy(how = How.XPATH, using = "//*[@id='feeHeader']/div[1]/div[1]")
	private WebElement feeReportHeader;
	
	@FindBy(how = How.ID, using = "headerDateRange")
	private WebElement feeReportDateRange;
	
	@FindBy(how = How.ID, using = "headerRunAt")
	private WebElement feeReportRunAtHeader;
	
	@FindBy(how = How.XPATH, using = "//*[@id='feeHeader']/div[2]/div[1]")
	private WebElement merchantName;
	
	@FindBy(how = How.XPATH, using = "//*[@id='feeHeader']/div[2]/div[3]")
	private WebElement customerID;
	
	@FindBy(how = How.XPATH, using = "//*[@id='feeDetailTable']/tbody/tr[1]/td[2]")
	private WebElement perTransTotal;
	
	@FindBy(how = How.XPATH, using = "//*[@id='feeDetailTable']/tbody/tr[1]/td[3]")
	private WebElement percentFeesTotal;
	
	
	
	public void clickUpdate(){
		updateReport.click();
		try {
			System.out.println("Waiting for search - 5 seconds");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public String getPerTransTotal(){
		String perTrans=perTransTotal.getText();
		return perTrans;
	}
	
	public String getPercentFeesTotal(){
		String percentFees=percentFeesTotal.getText();
		return percentFees;
			}
	
	public void clickDownload(){
		downloadReport.click();
	}
	
	public void selectMerchant(String merchant){
		Select select=new Select(merchantSelect);
		select.selectByVisibleText(merchant);
	}
	
	public void clickPrintReport(){
		printReport.click();
	}
	
	public int countRows(){
		int rowcount=driver.findElements(By.xpath("//*[@id='feeDetailTable']/tbody/tr[*]")).size();
		return rowcount;
	}
	
	public void fillFeeReportDates(String fromdate, String todate, String merchant){
		dateFrom.clear();
		dateFrom.sendKeys(fromdate);
		dateFrom.sendKeys(Keys.ENTER);
		dateTo.clear();
		dateTo.sendKeys(todate);
		dateTo.sendKeys(Keys.ENTER);
		selectMerchant(merchant);
	}
	
	public void assertFeeReportElements(String merchant){
		Assert.assertNotNull("Merchant name is not present in the fee report", merchantName);
		Assert.assertEquals(merchant, merchantName.getText());
		Assert.assertNotNull("Customer ID is not present in the funded report", customerID);
		Assert.assertTrue("Either the merchant is not selected or date range set was invalid",!driver.getPageSource().contains("Data Error"));
		Assert.assertTrue("Unexpected error pop up is seen",!driver.getPageSource().contains("Unexpected Error"));
		Assert.assertTrue("System error pop up is seen",!driver.getPageSource().contains("System Error"));
	}
	
	public void verifyFeeTotals(){
		
		int rows=countRows();
		for(int i=3;i<rows;i++) 
		{
			String breakdown=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[1]")).getText();	
			System.out.println("Verifying:" +breakdown);
			String column[]={"Count","Amount Processed","Per Trans Rate","Percent Rate","Per Trans Fees Total","Percent Fees Total"};
	   	   
			String count=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[2]")).getText();
			System.out.println(""+column[0]+":"+(count));
			String amount=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[3]")).getText().substring(1).trim();
			System.out.println(""+column[1]+":"+amount);
			String transrate=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[4]")).getText().substring(0).trim();
			System.out.println(""+column[2]+":"+Double.parseDouble(transrate));
			String percentrate=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[5]")).getText().substring(0, 3).trim();
			System.out.println(""+column[3]+":"+percentrate);
			String pertransfees=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[6]")).getText();
			System.out.println(""+column[4]+":"+pertransfees.substring(1).trim());
			String percentfee=driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[7]")).getText();
			System.out.println(""+column[5]+":"+percentfee.substring(1));
			
			System.out.println("Verifying per trans fees calculation");
			double feestotal=Double.parseDouble(count.trim())* Double.parseDouble(transrate.trim());
			NumberFormat formatted = NumberFormat.getCurrencyInstance();
			String pertransfeetotal = formatted.format(feestotal);
			System.out.println("Calculating Per Trans Fee Total:"+pertransfeetotal);
			Assert.assertEquals(pertransfees, pertransfeetotal);
			System.out.println(" Per Trans fees Total is accurate ");
	    
			double percent=Double.parseDouble(percentrate);
			double amountprocessed=Double.parseDouble(amount);
			double percentage=(double)((percent*amountprocessed)/100);
		
			System.out.println("Verifying percent fees calculation");
			System.out.println("Percentage:"+percentage);
			//DecimalFormat formatter = new DecimalFormat("0.00");
			String percentfeestotal = formatted.format(percentage);
			System.out.println("Calculating Percent Fees Total:"+percentfeestotal);
			Assert.assertEquals(percentfee,percentfeestotal);
			System.out.println(" Percent  fees Total is accurate ");
			System.out.println("");
		}
	}
			public void verifyColumnTotals() throws InterruptedException{	
			NumberFormat format = NumberFormat.getCurrencyInstance();
			String pertranstotal=getPerTransTotal();
			String percentfees=getPercentFeesTotal();
			double feetotal=sumOfPerTransFees();
			String finalfeetotal=format.format(feetotal);
			System.out.println("Verifying Per trans fees total");
			Assert.assertEquals(pertranstotal, finalfeetotal);
			System.out.println("Per trans fees total is accurate");
			Thread.sleep(2000);
			
			double percentfeetotal=sumOfPercentFees();
			String finalpercentfeetotal=format.format(percentfeetotal);
			System.out.println("Verifying Pecent fees total");
			Assert.assertEquals(percentfees, finalpercentfeetotal);
			System.out.println("Percent fees total is accurate");
	}
	
	public double sumOfPerTransFees(){
		int rowscount=countRows();
		double feestotal=0;
		for(int i=3;i<rowscount;i++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[6]")).getText();
				System.out.println(sum.substring(1)+ "  (at row: "+i+")"); 
				feestotal =feestotal+ Double.parseDouble(sum.substring(1).trim());
				} catch (Exception e){
				}
			}
		return feestotal;
	}
	
	public double sumOfPercentFees(){
		int rowscount=countRows();
		double percentfeestotal=0;
		for(int i=3;i<rowscount;i++) {
			try{
				String sum = driver.findElement(By.xpath("//*[@id='feeDetailTable']/tbody/tr["+i+"]/td[7]")).getText();
				System.out.println(sum.substring(1)+ "  (at row: "+i+")"); 
				percentfeestotal =percentfeestotal+ Double.parseDouble(sum.substring(1).trim());
				} catch (Exception e){
				}
			}
		return percentfeestotal;
	}
}
