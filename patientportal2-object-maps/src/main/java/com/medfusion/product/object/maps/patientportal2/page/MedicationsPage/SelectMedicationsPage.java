// Copyright 2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import static org.testng.Assert.assertFalse;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	
	@FindBy(how=How.XPATH,using="//div/div/div[.='Choose a medication']/../div[2]/input")
	private WebElement chooseMedicationTextField;
	
	@FindBy(how=How.XPATH,using="//span[.='Acarbose 100 mg Tab']")
	private WebElement chooseFirstMedicationTextField;
	
	@FindBy(how=How.XPATH,using="//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH,using="(//*[@class='checkbox'])[1]")
	private WebElement availablemedicationcheckbx;
	
	@FindBy(how=How.XPATH,using="//div[@class='select-imo-medication']//following::input")
	private WebElement multiSelectDependentMedication;
	
	@FindBy(how=How.ID,using="add-new-medication")
	private WebElement btnAddInactiveMedication;
	
	@FindBy(how=How.XPATH,using="//*[@class='checkbox']")
	private WebElement CheckbxInactiveMedication;
	
	@FindBy(how=How.XPATH,using="//*[text()=' Add ']")
	private WebElement btnAddMedication;
	

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
		try{
  			if(IHGUtil.waitForElement(driver, 5, availablemedicationcheckbx)) {	
			availablemedicationcheckbx.click();
			}
			else {
				log("Acarbose 100 mg Tab is selected from list");	
				driver.switchTo().defaultContent();
				chooseMedicationTextField.sendKeys("Acarbose 100 mg Tab");
				IHGUtil.waitForElement(driver, 5, chooseFirstMedicationTextField); 
				chooseFirstMedicationTextField.click();
			}
  			Actions action=new Actions(driver);
  			action.moveToElement(btnContinue).click().build().perform();
  			IHGUtil.waitForElement(driver, 5, btnContinue); 
			}
		catch (Exception e) {
			log(e.toString());	
		}	
	}
	
	public void selectDependentMedications() throws IOException, InterruptedException {
		PropertyFileLoader testData = new PropertyFileLoader();
		wait.until(ExpectedConditions.visibilityOf(multiSelectDependentMedication));
		Thread.sleep(2000);
		multiSelectDependentMedication.sendKeys(testData.getProperty("med.dep.one"));
		Thread.sleep(2000);
		multiSelectDependentMedication.sendKeys(Keys.ENTER);	
		Thread.sleep(2000);
		btnContinue.click();
	}
	
	public void selectInactiveMedication() throws IOException, InterruptedException {
		IHGUtil.waitForElement(driver, 5, btnAddInactiveMedication);
		btnAddInactiveMedication.click();
		IHGUtil.waitForElement(driver, 5, CheckbxInactiveMedication);
		log("Verifying add button is disabled as medication is not selected");
		IHGUtil.waitForElement(driver, 5, btnAddMedication);
		assertFalse(btnAddMedication.isEnabled(), "Add button is disabled");
		CheckbxInactiveMedication.click();
		btnAddMedication.click();
		IHGUtil.waitForElement(driver, 5, btnContinue);
		btnContinue.click();
	}
	
}
