//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.virtualCardSwiper;

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class VirtualCardSwiperPageChargeHistory extends BasePageObject {

	@FindBy(name = "searchParams:1:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name = "searchParams:1:input:Date End:month")
	private WebElement endMonth;

	@FindBy(name = "searchParams:1:input:Date Begin:year")
	private WebElement startYear;

	@FindBy(name = "searchParams:1:input:Date End:year")
	private WebElement endYear;

	@FindBy(name = "searchParams:1:input:Date Begin:day")
	private WebElement startDay;

	@FindBy(name = "searchParams:1:input:Date End:day")
	private WebElement endDay;

	@FindBy(xpath = "//td[label[contains(text(), 'Practice Payment')]]/input")
	private WebElement practicePayment;

	@FindBy(xpath = "//td[label[contains(text(), 'Practice Refund')]]/input")
	private WebElement practiceRefund;

	@FindBy(xpath = "//td[label[contains(text(), 'Practice Void')]]/input")
	private WebElement practiceVoid;

	@FindBy(name = "buttons:submit")
	private WebElement btnSubmit;

	@FindBy(name = "searchParams:2:input")
	private List<WebElement> types;

	@FindBy(id = "MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;

	@FindBy(xpath = ".//tbody/tr/td[4]/span")
	private List<WebElement> amount;

	@FindBy(xpath = "//*[@id=\'MfAjaxFallbackDefaultDataTable\']/tfoot/tr/td[4]/span")
	private WebElement totalAmmount;
	
	public VirtualCardSwiperPageChargeHistory(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}


	public void SearchPayment(int value) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 60, practicePayment);


		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);
		Select endDaySelect = new Select(endDay);
		Select startDaySelect = new Select(startDay);

		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));
		Thread.sleep(2000);

		String index2 = endDaySelect.getFirstSelectedOption().getAttribute("index");
		startDaySelect.selectByIndex(Integer.parseInt(index2));
		Thread.sleep(2000);

		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);

		IHGUtil.waitForElement(driver, 20, practicePayment);
		for (int i = 0; i < types.size(); i++) {
			types.get(i).click();

		}
		for (WebElement type : types) {
			if (Integer.parseInt(type.getAttribute("value")) == value) {
				type.click();
			}
		}

		IHGUtil.waitForElement(driver, 90, btnSubmit);
		btnSubmit.click();
	}

	public boolean VerifyAmount(String amount) {
		PracticeUtil.setPracticeFrame(driver);
		
		IHGUtil.waitForElement(driver, 60, totalAmmount);
		return driver.getPageSource().contains("$" + amount + ".00");
	}

	public String getBillDetails(String amont) throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(8000);
		PracticeUtil.setPracticeFrame(driver);

		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Virtual Card Swiper Charge History search result table is not found for particular payment type.");
		}
		int j = 0;
		Thread.sleep(10000);
		for (WebElement amt : amount) {
			j++;
			if (amt.getText().contains(amont)) {
				WebElement post = driver.findElement(By.xpath(".//tbody/tr[" + j + "]/td[4]/span"));
				String title = post.getAttribute("title").toString();
				return title;
			}

		}
		return null;

	}

}
