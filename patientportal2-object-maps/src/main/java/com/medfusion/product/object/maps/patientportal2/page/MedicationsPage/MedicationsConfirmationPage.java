//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;

public class MedicationsConfirmationPage {
	
	public MedicationsConfirmationPage(WebDriver driver) {
		super();
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.ID, using = "additional-comment")
	private WebElement textComment;
	
	@FindBy(how = How.XPATH, using = "//*[@id='confirm-rxRequest-page']/following-sibling::*/button[2]")
	private WebElement btnConfirm;
	
	@FindBy(how = How.XPATH, using = "//div[@class='form-buttons ng-scope']/button[@type='button']")
	private WebElement btnBack;
	
	@FindBy(how = How.XPATH, using = "//div[@class='modal-content']")
	private WebElement confirmPopup;
	
	@FindBy(how = How.XPATH, using = "//*[@id='confirmation']/following-sibling::p")
	private WebElement successMsg;
	
	@FindBy(how = How.XPATH, using = "//a[@id='closebtn']")
	private WebElement btnClose;
	
	@FindBy(how = How.XPATH, using = "//span[text()='Prescription renewal fee']")
	private WebElement prescriptionRenewalFee;
	
	@FindBy(how = How.XPATH, using = "//*[@id='result-pharmacy']/div")
	private WebElement pharamcyDetails;
	
	@FindBy(how = How.XPATH, using = "(//*[@id='result-medications']/div/p)[1]")
	private WebElement medicationdetails;
	
	
	public String confirmMedication(WebDriver driver) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Thread.sleep(3000);
		executor.executeScript("arguments[0].click();", btnConfirm);
		System.out.println("Confirm button is clicked");
		IHGUtil.waitForElement(driver, 15, confirmPopup);
		String successMsgOnPopup = successMsg.getText();
		btnClose.click();
        return successMsgOnPopup;		
	}
	
	public void prescriptionRenewalFee() {
	
		try {
			assertFalse(prescriptionRenewalFee.isDisplayed());
		}
		catch (Exception e) {
			System.out.println("Prescription Renewal fee is  not displayed");
		}
		
	}
	
	public void setAdditionalComments(WebDriver driver, String comment) {
		IHGUtil.waitForElement(driver, 0, textComment);
		textComment.sendKeys(comment);
	}
	
	public String getMedicationdetails(WebDriver driver) throws InterruptedException {
		String confirmMedicationDetails =  medicationdetails.getText() ;
        return confirmMedicationDetails;		
	}

	public String getpharamcyDetails(WebDriver driver) throws InterruptedException {
		String confirmPharamcyDetails =  pharamcyDetails.getText() ;
        return confirmPharamcyDetails;		
	}
}
