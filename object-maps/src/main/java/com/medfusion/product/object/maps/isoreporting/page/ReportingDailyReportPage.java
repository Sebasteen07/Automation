package com.medfusion.product.object.maps.isoreporting.page;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ReportingDailyReportPage extends BasePageObject {

	@FindBy(how = How.ID, using = "runSearch")
	private WebElement searchButton;
	
	@FindBy(how = How.ID, using = "dailyReportTotalsDownload")
	private WebElement downloadButton;
	
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
	
	public ReportingLoginPage logout(WebDriver driver) {

		IHGUtil.PrintMethodName();
		signoutButton.click();
		return PageFactory.initElements(driver, ReportingLoginPage.class);
	}
	
	public String getLastRowAmount(){
		IHGUtil.PrintMethodName();
		return driver.findElement(By.xpath("//table[@id='dailyTransactionsTable']/tbody/tr[last()]/td[13]")).getText();
	}


}
