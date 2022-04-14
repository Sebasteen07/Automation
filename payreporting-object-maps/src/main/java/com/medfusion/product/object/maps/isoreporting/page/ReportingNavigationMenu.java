//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ReportingNavigationMenu {
	
	protected WebDriver driver;
	public static String downloadPath= "C:/temp";
	protected static String extension= ".csv";
	public ReportingNavigationMenu(WebDriver driver){
		this.driver = driver;
	}
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div[1]/nav/div[2]/ul/li/a/span[1]")
	public WebElement logoutButton;

	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div[1]/nav/minimaliza-menu/div/span")
	public WebElement menuBarButton;
		
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/aside/div/ul/li[1]/a/span")
	public WebElement menuDailyReport;

	@FindBy(how = How.XPATH, using = "/html/body/div[2]/aside/div/ul/li[2]/a/span")
	public WebElement menuFundedReport;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/aside/div/ul/li[3]/a/span")
	public WebElement menuMonthlyReport;
	
	@FindBy(how = How.XPATH, using = "/html/body/div[2]/aside/div/ul/li[4]/a")
	public WebElement menuFeeReport;

	@FindBy(how = How.XPATH, using = "//*[@id='summary']")
	public WebElement menuSummaryReport;
	
	@FindBy(how = How.XPATH, using = "//*[@id='pos']")
	public WebElement menuMakeAPayment;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space()='Configure Terminals']")
	public WebElement configureTerminals;
		
	
	public boolean isMenuOpen(){
		return menuDailyReport.isDisplayed();
	}
	
	public void clickMenu(){
		menuBarButton.click();	
	}
	
	public void openMenuIfNotOpened() throws InterruptedException {
		if (!isMenuOpen()) {
			menuBarButton.click();
			Thread.sleep(5000);
		}
	}
	public void navigateFundedReport(){
		menuBarButton.click();
		menuFundedReport.click();
		
	}
	
	public void clickWebElement(WebElement WebElement){
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", WebElement);
		
	}

	public ReportingDailyReportPage navigateDaily() throws InterruptedException {
		openMenuIfNotOpened();
		menuDailyReport.click();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuDailyReport);
		Thread.sleep(2000);
		return PageFactory.initElements(driver, ReportingDailyReportPage.class);
				
	}

	public ReportingFundedReportPage navigateFunded() throws InterruptedException {
		openMenuIfNotOpened();
		menuFundedReport.click();
		return PageFactory.initElements(driver, ReportingFundedReportPage.class);		
	}

	public ReportingMonthlyReportPage navigateMonthly() throws InterruptedException {
		openMenuIfNotOpened();
		menuMonthlyReport.click();
		return PageFactory.initElements(driver, ReportingMonthlyReportPage.class);		
	}

	public ReportingFeeReportPage navigateFee() throws InterruptedException {
		openMenuIfNotOpened();
		menuFeeReport.click();
		return PageFactory.initElements(driver, ReportingFeeReportPage.class);		
	}

	public ReportingSummaryReportPage navigateSummaryReport() throws InterruptedException {
		openMenuIfNotOpened();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuSummaryReport);
		return PageFactory.initElements(driver, ReportingSummaryReportPage.class);
	}

	public ReportingMakeAPaymentPage navigateMakeAPayment() throws InterruptedException {
		openMenuIfNotOpened();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuMakeAPayment);
		return PageFactory.initElements(driver, ReportingMakeAPaymentPage.class);
	}
	
	public ReportingLoginPage clickLogout(){
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButton);
		return PageFactory.initElements(driver, ReportingLoginPage.class); 
	}

	public ReportingConfigureTerminalsPage navigateConfigureTerminals() throws InterruptedException {
		openMenuIfNotOpened();
		clickWebElement(configureTerminals);
		return PageFactory.initElements(driver, ReportingConfigureTerminalsPage.class); 
	}
	
}
