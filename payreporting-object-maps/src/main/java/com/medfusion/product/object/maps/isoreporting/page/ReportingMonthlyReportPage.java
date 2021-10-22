//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class ReportingMonthlyReportPage extends ReportingNavigationMenu  {

	public ReportingMonthlyReportPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[1]/div")
	private WebElement monthlyReportHeader;
	
	@FindBy(how=How.ID,using="monthlyLink")
	private WebElement returnToReport;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyHeader']/div[2]/div[3]")
	private WebElement customerID;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyReportsTable']/tbody/tr[1]/td[1]")
	private WebElement customerIDValue;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyReportsTable']/tbody/tr[1]/td[2]")
	private WebElement merchantIDValue;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyReportsTable']/tbody/tr[1]/td[3]")
	private WebElement monthValue;
	
	@FindBy(how=How.XPATH,using="//*[@id='monthlyReportsTable']/tbody/tr[1]/td[1]")
	private WebElement publishedDateValue;
	
	@FindBy(how=How.XPATH,using="//*[@id='view_pdf2']")
	private WebElement viewFile;	
	
		
	

    public void assertMonthlyReportElements(String customerIDValue) {
        Assert.assertEquals(customerIDValue, customerID.getText());
		Assert.assertTrue("Unexpected error pop up is seen",!driver.getPageSource().contains("Unexpected Error"));
		Assert.assertTrue("System error pop up is seen",!driver.getPageSource().contains("System Error"));
		Assert.assertEquals("Monthly Reports", monthlyReportHeader.getText());
	}
	
	public int countRows(){
		int rowcount=driver.findElements(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr[*]")).size();
		return rowcount;
	}
	
	public void viewReport() throws InterruptedException{
		
		Actions action1 = new Actions(driver);	
		int rowcount=(driver.findElements(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr[*]/td[1]")).size());
		for(int i=rowcount-1;i<=rowcount;i++) 
		{
		
		String heads[]={"Customer Id","Merchant ID","Report Month","Published Date","View Detail"};
		String customer=driver.findElement(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr["+i+"]/td[1]")).getText();
		System.out.println(""+heads[0]+":"+(customer));
		Assert.assertNotNull(customer,"Customer ID is null");
	
		String merchant=driver.findElement(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr["+i+"]/td[2]")).getText();
   		System.out.println(""+heads[1]+":"+(merchant));
   		Assert.assertNotNull(merchant,"Merchant ID is null");
   		
   		String month=driver.findElement(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr["+i+"]/td[3]")).getText();
   		System.out.println(""+heads[2]+":"+(month));
   		Assert.assertNotNull(month,"Month is null");
		
   		String date=driver.findElement(By.xpath("//*[@id='monthlyReportsTable']/tbody/tr["+i+"]/td[4]")).getText();
   		System.out.println(""+heads[3]+":"+(date));
   		Assert.assertNotNull(date,"Published date is null");
   		
		
		System.out.println("Viewing monthly report for customerID: " +customer+ " for merchant id: " +merchant+ " for month: " +month+ " which was published on: " +date+ "");
		WebElement viewReport=driver.findElement(By.xpath("//html/body/div[2]/div[2]/div/div[3]/div/table/tbody/tr["+i+"]/td[5]/span/a"));
		action1.doubleClick(viewReport).perform();
		Thread.sleep(5000);
		Assert.assertTrue("Monthly report was not present",viewFile.isDisplayed());
		returnToReport();
		Thread.sleep(2000);
		}
	}

	public void returnToReport(){
		Assert.assertTrue("Return to monthly report button is not present", returnToReport.isDisplayed());
		returnToReport.click();
		
	}
}
