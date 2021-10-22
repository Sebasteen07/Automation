//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class ReportingPOSVCSMenu extends ReportingNavigationMenu {

	public ReportingPOSVCSMenu(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(how=How.LINK_TEXT,using="Virtual Card Swiper")
	private WebElement vcsTab;

	public ReportingMakeAPaymentPage clickVCS(){
		Assert.assertTrue("VCS link is present",vcsTab.isDisplayed());
		vcsTab.click();
		vcsTab.sendKeys(Keys.ENTER);
		return PageFactory.initElements(driver, ReportingMakeAPaymentPage.class);
	}


}
