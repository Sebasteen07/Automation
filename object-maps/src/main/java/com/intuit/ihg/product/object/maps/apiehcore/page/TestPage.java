//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.intuit.ihg.product.object.maps.apiehcore.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class TestPage extends BasePageObject {


	public static final String PAGE_NAME = "";
	public static final String pageUrl = "";

	@FindBy(name = "username")
	private WebElement txtElement;


	public TestPage(WebDriver driver) {
		super(driver);
	}

	public TestPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		log("URL: " + baseURL);
		driver.get(baseURL);
		maxWindow();
		PageFactory.initElements(driver, this);
	}


}
