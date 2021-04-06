//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.medfusion.common.utils.IHGUtil;

public class MedicationsConfirmationPage {
	
	public MedicationsConfirmationPage(WebDriver driver) {
		super();
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "additional-comment")
	private WebElement textComment;
	
	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-primary ng-binding']")
	private WebElement btnConfirm;
	
	@FindBy(how = How.XPATH, using = "//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-content']")
	private WebElement confirmPopup;
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-header ng-scope']//p")
	private WebElement successMsg;
	
	@FindBy(how = How.XPATH, using = "//a[@id='closebtn']")
	private WebElement btnClose;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Prescription renewal fee']")
	private WebElement prescriptionRenewalFee;

	public String confirmMedication(WebDriver driver) throws InterruptedException {
		JavascriptExecutor ex = (JavascriptExecutor)driver;
	    ex.executeScript("arguments[0].click();", btnConfirm);
		System.out.println("Confirm button is clicked");
		IHGUtil.waitForElement(driver, 10, confirmPopup);
        return successMsg.getText();		
	}
	
	public void prescriptionRenewalFee() {
	
		try {
			assertFalse(prescriptionRenewalFee.isDisplayed());
		}
		catch (Exception e) {
			System.out.println("Prescription Renewal fee is  not displayed");
		}
		
		
		
	}

}
