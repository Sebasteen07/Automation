// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
	private WebElement multiSelectMedicationWithoutRenewalFee;
	
	@FindBy(how=How.XPATH,using="//*[@id=\"iMOMedications\"]/div/div/div[2]/input")
	private WebElement multiSelectMedication;
	 
	@FindBy(how=How.XPATH,using="//ul[@class='list']/li[1]")
	private WebElement apiMedication;
	
	@FindBy(how=How.XPATH,using="//*[contains(text(),'Continue')]")
	private WebElement btnContinue;
	
	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH,using="(//*[@class='list-item']/*[@class='checkbox'])[1]")
	private WebElement availablemedicationcheckbx;
	

	public void selectMedications() throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		wait.until(ExpectedConditions.visibilityOf(multiSelectMedication));
		multiSelectMedication.sendKeys(testData.getProperty("med.one"));
		Thread.sleep(2000);
		multiSelectMedication.sendKeys(Keys.ENTER);	
		Thread.sleep(2000);
		btnContinue.click();
	}
	
	public void selectMedicationsFrmAvailable() throws IOException, InterruptedException {
		IHGUtil.waitForElement(driver, 5, availablemedicationcheckbx);	
		availablemedicationcheckbx.click();
		btnContinue.click();
	}
	
}
