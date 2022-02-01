//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.smintegration.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class AppointmentRequestFirstPage extends BasePageObject {


	public AppointmentRequestFirstPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.NAME, using = ":submit")
	private WebElement btnContinue;

	public void clickContinueBtn() {
		IHGUtil.PrintMethodName();
		btnContinue.click();
	}

}
