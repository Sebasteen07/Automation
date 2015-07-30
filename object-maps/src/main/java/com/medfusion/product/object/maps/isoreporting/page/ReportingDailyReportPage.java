package com.medfusion.product.object.maps.isoreporting.page;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeConstants;

public class ReportingDailyReportPage extends BasePageObject {

	@FindBy(how = How.ID, using = "runSearch")
	private WebElement searchButton;
	
	@FindBy(how = How.ID, using = "dailyReportTotalsDownload")
	private WebElement downloadButton;
	
	@FindBy(how = How.ID, using = "locationSelection")
	private WebElement locationSelect;
	
	@FindBy(how = How.ID, using = "staffName")
	private WebElement staffName;
	
	@FindBy(how = How.ID, using = "staffBlank")
	private WebElement staffBlankCheck;
	
	@FindBy(how = How.ID, using = "dateFrom")
	private WebElement dateFrom;
	
	@FindBy(how = How.ID, using = "dateTo")
	private WebElement dateTo;
	
	@FindBy(how = How.ID, using = "statusSelection")
	private WebElement statusSelect;
	
	@FindBy(how = How.ID, using = "transactionId")
	private WebElement transactionId;
	
	@FindBy(how = How.ID, using = "printReport")
	private WebElement printReportButton;
	
	@FindBy(how = How.XPATH, using = "//span[@class='glyphicon glyphicon-log-out']")
	private WebElement signoutButton;

	public ReportingDailyReportPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void clickSearch(){
		IHGUtil.PrintMethodName();
		searchButton.click();
		try {
			log("Waiting for search - 5 seconds");
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clickDownload(){
		IHGUtil.PrintMethodName();
		downloadButton.click();
	}
	
	public void clickPrintReport(){
		IHGUtil.PrintMethodName();
		printReportButton.click();
	}
	
	public void selectLocation(String location){
		IHGUtil.PrintMethodName();
		Select sele=new Select(locationSelect);
		sele.selectByVisibleText(location);
	}
	
	public void fillStaffName(String name){
		IHGUtil.PrintMethodName();
		staffName.sendKeys(name);
	}
	
	public void clickStaffBlank(){
		IHGUtil.PrintMethodName();
		staffBlankCheck.click();
	}
	
	public void fillDateFrom(String date){
		IHGUtil.PrintMethodName();
		dateFrom.sendKeys(date);
	}
	
	public void fillDateTo(String date){
		IHGUtil.PrintMethodName();
		dateTo.sendKeys(date);
	}
	
	public void selectStatus(String status){
		IHGUtil.PrintMethodName();
		Select sele=new Select(statusSelect);
		sele.selectByVisibleText(status);
	}
	
	public void clickPrintReportButton(){
		IHGUtil.PrintMethodName();
		printReportButton.click();
	}
	
	public void fillTransactionId(String transaction){
		IHGUtil.PrintMethodName();
		transactionId.sendKeys(transaction);
	}
	
	public ReportingLoginPage logout(WebDriver driver) {

		IHGUtil.PrintMethodName();
		signoutButton.click();
		return PageFactory.initElements(driver, ReportingLoginPage.class);
	}
	
	public String getLastRowAmount(){
		IHGUtil.PrintMethodName();
		return driver.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[last()]/td[13]")).getText();
	}
	
	public boolean checkTableContents(boolean integrityCheck, boolean compare, String expectedPaySum, String expectedPayCount, String expectedRefSum, String expectedRefCount) {
		IHGUtil.PrintMethodName();
		
		String stringSum = "";
		String paySum = "";
		String refSum = "";
		List<WebElement> payList;
		List<WebElement> refundList;
		WebElement pagePayCount;
		WebElement pagePaySum;
		WebElement pageRefCount;
		WebElement pageRefSum;
		WebElement pageNetSum;
		try{
			payList = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr/td[13]"));
			refundList = driver.findElements(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr/td[14]"));
			pagePayCount = driver.findElement(By.xpath("//table[@id='dailyTotalTable']/tbody/tr/td[1]"));
			pagePaySum = driver.findElement(By.xpath("//table[@id='dailyTotalTable']/tbody/tr/td[2]"));
			pageRefCount = driver.findElement(By.xpath("//table[@id='dailyTotalTable']/tbody/tr/td[3]"));
			pageRefSum = driver.findElement(By.xpath("//table[@id='dailyTotalTable']/tbody/tr/td[4]"));
			pageNetSum = driver.findElement(By.xpath("//table[@id='dailyTotalTable']/tbody/tr/td[5]"));
		}
		catch(Exception e){
			e.printStackTrace();
			log("ERROR finding elements failed, no payments present?");
			return false;
		}
		int payInt = 0;
		int refInt = 0;
		int payCount = 0;
		int refCount = 0;
		boolean result = true;
		boolean tmRes = false;		
		int tmPayment = 0;
		int tmRefund = 0; 
		
		if (compare){
			log("Comparing totals against input");
			tmRes = (pagePaySum.getText().equals(expectedPaySum));
			log("    Payment sum valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pagePayCount.getText().equals(expectedPayCount));
			log("    Payment count valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pageRefSum.getText().equals(expectedRefSum));
			log("    Refund sum valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pageRefCount.getText().equals(expectedRefCount));
			log("    Refund count valid? -- > " + tmRes);
			if (!tmRes) result = false;
			if (!integrityCheck){
				log("Verified totals only!");
				return result;			
			}
		}
		
		for (WebElement elem : payList){
			String[] cut = elem.getText().substring(1).split("\\.");
			payInt = Integer.parseInt(cut[0])*100 + Integer.parseInt(cut[1]);
			tmPayment += payInt;
			if ( payInt > 0) payCount++;					
		}
		paySum = "$" + Integer.toString(tmPayment);
		paySum = new StringBuffer(paySum).insert(paySum.length()-2, ".").toString();
		log("  Payments + " + payCount + " + Sum  --> " + paySum);
		for (WebElement elem : refundList){			
			String[] cut = elem.getText().substring(1).split("\\.");
			refInt = Integer.parseInt(cut[0])*100 + Integer.parseInt(cut[1]);
			tmRefund += refInt;
			if (refInt > 0) refCount++;
			
		}
		
		refSum = "$" + Integer.toString(tmRefund);
		refSum = new StringBuffer(refSum).insert(refSum.length()-2, ".").toString();
		log("  Refunds + " + refCount + " + Sum  --> " + refSum);
		
		stringSum = "$" + Integer.toString(tmPayment - tmRefund);
		stringSum = new StringBuffer(stringSum).insert(stringSum.length()-2, ".").toString();
		log("  Net Sum  --> " + stringSum);
		if (integrityCheck){
			log("  Checking table contents vs totals integrity !");
			tmRes = (pagePaySum.getText().equals(paySum));
			log("    Payment sum valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pagePayCount.getText().equals(Integer.toString(payCount)));
			log("    Payment count valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pageRefSum.getText().equals(refSum));
			log("    Refund sum valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pageRefCount.getText().equals(Integer.toString(refCount)));
			log("    Refund count valid? -- > " + tmRes);
			if (!tmRes) result = false;
			tmRes = (pageNetSum.getText().equals(stringSum));
			log("    Net sum valid? -- > " + tmRes);
			if (!tmRes) result = false;
		}		
		
		if (result) log("Yay!");
		return result;
	}
	public boolean checkTableIntegrityOnly(){
		return checkTableContents(true,false,"","","","");
	}
	public boolean checkTableTotalsOnly(String expectedPaySum, String expectedPayCount, String expectedRefSum, String expectedRefCount){
		return checkTableContents(false,true,expectedPaySum,expectedPayCount,expectedRefSum,expectedRefCount);
	}


}
