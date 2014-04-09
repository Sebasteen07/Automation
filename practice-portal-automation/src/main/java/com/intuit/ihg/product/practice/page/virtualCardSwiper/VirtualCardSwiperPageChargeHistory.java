package com.intuit.ihg.product.practice.page.virtualCardSwiper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.apptrequest.ApptRequestStatus;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class VirtualCardSwiperPageChargeHistory extends BasePageObject {
	
	@FindBy(name="searchParams:1:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name="searchParams:1:input:Date End:month")
	private WebElement endMonth;
	
	@FindBy(name="searchParams:1:input:Date Begin:year")
	private WebElement startYear;
	
	@FindBy(name="searchParams:1:input:Date End:year")
	private WebElement endYear;
	
	@FindBy(name="searchParams:1:input:Date Begin:day")
	private WebElement startDay;
	
	@FindBy(name="searchParams:1:input:Date End:day")
	private WebElement endDay;
	
	@FindBy( xpath = "//td[label[contains(text(), 'Practice Payment')]]/input")
	private WebElement practicePayment;
	
	@FindBy( xpath = "//td[label[contains(text(), 'Practice Refund')]]/input")
	private WebElement practiceRefund;
	
	@FindBy( xpath = "//td[label[contains(text(), 'Practice Void')]]/input")
	private WebElement practiceVoid;
	
	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;
	
	public VirtualCardSwiperPageChargeHistory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void SearchPayment() throws InterruptedException
	{
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 20, practicePayment);
	
		
		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);
		Select endDaySelect = new Select(endDay);
		Select startDaySelect = new Select(startDay);
		
		String index= endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));
		Thread.sleep(2000);
		
		String index2= endDaySelect.getFirstSelectedOption().getAttribute("index");
		startDaySelect.selectByIndex(Integer.parseInt(index2));
		Thread.sleep(2000);
		
		String index3= endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);
		
		practicePayment.click();
		practiceRefund.click();
		practiceVoid.click();
		IHGUtil.waitForElement(driver, 20, btnSubmit);
		btnSubmit.click();	
		
	}

	public boolean VerifyAmount(String amount)
	{
		PracticeUtil.setPracticeFrame(driver);
		return driver.getPageSource().contains(amount);
	}
}
