// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.phr.page.portaltophr;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.phr.page.PhrHomePage;

public class IntuitAcceptPrivacyPolicy extends BasePageObject {

	@FindBy(name = "touCheckboxValue")
	private WebElement chkIntuitTermsofServiceAndPrivacyPolicy;

	@FindBy(css = "input.custom_text_button")
	private WebElement btnAccept;


	public IntuitAcceptPrivacyPolicy(WebDriver driver) {
		super(driver);
	}

	public PhrHomePage acceptIntuitTermsAndCondition() {
		IHGUtil.PrintMethodName();
		driver.switchTo().defaultContent();
		log("==============" + driver.getCurrentUrl() + "==============");
		log("==============" + driver.getPageSource().toString() + "==============");
		chkIntuitTermsofServiceAndPrivacyPolicy.click();
		btnAccept.click();
		return PageFactory.initElements(driver, PhrHomePage.class);
	}

}
