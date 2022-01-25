//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.sitegen.page.MerchantAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.wait.WaitForWEIsDisplayed;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class MerchantAccountPage extends BasePageObject {

	@FindBy(xpath = "//h2[text() = 'Merchant Account List']")
	private WebElement merchantAccountListTitle;

	@FindBy(xpath = ".//div[@id='portal']//span/a[text() ='Merchant Account Setup']")
	private WebElement merchantAccountSetUp;

	@FindBy(xpath = ".//input[@value='Delete']")
	private WebElement deleteButton;

	public MerchantAccountPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkMerchantAccountListPage() {
		IHGUtil.PrintMethodName();
		return merchantAccountListTitle.isDisplayed();
	}

	public MerchantAccountSetUpPage clickOnMerchantAccountSetUp() throws Exception {
		IHGUtil.PrintMethodName();
		waitForElement(merchantAccountSetUp, 30);
		merchantAccountSetUp.click();
		return PageFactory.initElements(driver, MerchantAccountSetUpPage.class);
	}

	public boolean verifyAcctInMerchantAcctList() throws Exception {
		IHGUtil.PrintMethodName();
		Thread.sleep(5000);
		SitegenlUtil sUtil = new SitegenlUtil(driver);
		return (sUtil.isRendered(deleteButton));
	}

	public void deleteExistingMerchantAcct() throws Exception {

		Thread.sleep(4000);
		if (verifyAcctInMerchantAcctList()) {
			waitForElement(deleteButton, 30);
			deleteButton.click();
			driver.switchTo().alert().accept();
		} else {
			log("No Accounts present in the Merchant Account List");
		}

	}

	public boolean waitForElement(WebElement we, int Timeout_Seconds) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Timeout_Seconds);
		return wait.until(new WaitForWEIsDisplayed(we));
	}
}
