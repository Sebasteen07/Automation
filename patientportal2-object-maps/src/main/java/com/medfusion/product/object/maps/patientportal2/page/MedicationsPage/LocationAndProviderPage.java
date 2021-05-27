// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

 

import java.io.IOException;

 

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

 

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

 

public class LocationAndProviderPage {
    
    @FindBy(how=How.XPATH,using="//div[@class='ng-select-container ng-has-value']//input")
    private static WebElement locationDropdown;
    
    @FindBy(how=How.XPATH,using="//div[@class='ng-select-container ng-appearance-outline ng-has-value']//input")
    private static WebElement providerDropdown;
    
    @FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
    private static WebElement btnBack;
    
    @FindBy(how=How.XPATH,using="//button[@type='submit']")
    private static WebElement btnContinue;
    
    @FindBy(how=How.XPATH,using="(//*[@class='ng-value-label'])[1]")
    private static WebElement selectedLocation;
    
    @FindBy(how=How.XPATH,using="(//*[@class='ng-value-label'])[2]")
    private static WebElement selectedProvider;
    
    
    		
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
    
	public void chooseLocationAndProviderwithoutFee() throws InterruptedException, IOException {
		PropertyFileLoader testData = new PropertyFileLoader();

		locationDropdown.click();
		locationDropdown.sendKeys(testData.getProperty("providerwfName"));
		providerDropdown.click();
		providerDropdown.sendKeys(testData.getProperty("providerwfLocation"));
		btnContinue.click();

	}

	public  String  getPracticeLocation(WebDriver driver) {
		
		IHGUtil.waitForElement(driver, 10, selectedLocation);
		String location = selectedLocation.getText();
        locationDropdown.click();
        locationDropdown.sendKeys(location);
		return location;
	}
	
	public  String  getPracticeProvider() {
		String provider = selectedProvider.getText();
		providerDropdown.click();
		providerDropdown.sendKeys(provider);
		btnContinue.click();
		return provider;
	}
		
}