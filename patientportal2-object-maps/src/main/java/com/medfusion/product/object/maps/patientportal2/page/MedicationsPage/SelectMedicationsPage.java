// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;

public class SelectMedicationsPage  extends BasePageObject {
	
	public SelectMedicationsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}

	@FindBy(how=How.XPATH,using="//div[@class='ng-input']/input")
	private WebElement multiSelectMedication;
	 
	@FindBy(how=How.XPATH,using="//ul[@class='list']/li[1]")
	private WebElement apiMedication;
	
	@FindBy(how=How.XPATH,using="//button[@class='btn btn-primary ng-binding']")
	private WebElement btnContinue;
	
	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;

	public void selectMedications() throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		multiSelectMedication.sendKeys(testData.getProperty("medOne"));
		Thread.sleep(2000);
		multiSelectMedication.sendKeys(Keys.ENTER);	
		Thread.sleep(2000);
		btnContinue.click();
		
	}
}
