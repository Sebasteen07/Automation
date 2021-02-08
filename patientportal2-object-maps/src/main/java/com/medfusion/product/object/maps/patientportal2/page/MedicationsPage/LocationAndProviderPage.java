// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class LocationAndProviderPage {
	
	@FindBy(how=How.XPATH,using="//div[@class='ng-select-container ng-has-value']//input")
	private WebElement locationDropdown;
	
	@FindBy(how=How.XPATH,using="//div[@class='ng-select-container ng-appearance-outline ng-has-value']//input")
    private WebElement providerDropdown;
	
	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH,using="//button[@type='submit']")
	private WebElement btnContinue;
	
	public LocationAndProviderPage(WebDriver driver) {
		super();
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}

	public void chooseLocationAndProvider() throws InterruptedException, IOException {
		PropertyFileLoader testData = new PropertyFileLoader();
		locationDropdown.click();
		locationDropdown.sendKeys(testData.getProperty("providerName"));
		providerDropdown.click();
		providerDropdown.sendKeys(testData.getProperty("providerLocation"));
		btnContinue.click();	
	}
	
	
}
