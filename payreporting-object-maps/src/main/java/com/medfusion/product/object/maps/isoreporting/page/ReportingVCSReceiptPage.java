//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import java.util.Arrays;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportingVCSReceiptPage extends ReportingPOSVCSMenu  {

	public ReportingVCSReceiptPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(how=How.XPATH,using="//*[@id='email']")
	private WebElement email;

	@FindBy(how=How.XPATH,using="//*[@id='emailButton']")
	private WebElement sendEmailButton;

	@FindBy(how=How.XPATH,using="//*[@id='printButton']/button[1]")
	private WebElement printButton;

	@FindBy(how=How.XPATH,using="//*[@id='receiptBody']/div[5]/div/div/div[1]/div")
	private WebElement viewReceipt;

	@FindBy(how=How.XPATH,using="//*[@id='toast-container']/div/button")
	private WebElement toastCloseButton;

	@FindBy(how=How.XPATH,using="//*[@id='toast-container']/div[1]/div[1]")
	private WebElement toastPopUp;
	
	@FindBy(how=How.XPATH,using="//span[@data-ng-if='receiptData.sequenceNumber']")
	private WebElement sequenceNumber;
	
	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[1]/div[2]/div[2]")
	private WebElement receiptAmount;
	
	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[1]/div[2]/div[2]/div[1]")
	private WebElement receiptMerchant;
	
	@FindBy(how=How.XPATH,using="//span[@data-ng-if='receiptData.patientName']")
	private WebElement receiptPatientName;

	@FindBy(how=How.XPATH,using="//span[@data-ng-if='receiptData.accountNumber']")
	private WebElement receiptAccountNumber;
	
	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[1]/div[1]/div[2]/div[2]")
	private WebElement receiptLastFour;

	@FindBy(how=How.XPATH,using="//*[@id='receiptBody']/div[5]/div/div")
	private WebElement receiptModal;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[1]/div[1]/div/div")
	private WebElement paymentSuccessMsg;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[1]/div[1]")
	private WebElement paymentDetails;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]")
	private WebElement emvSection;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[1]/div[1]/span")
	private WebElement applicationLabel;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[1]/div[2]/span")
	private WebElement aidLabel;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[1]/div[3]/span")
	private WebElement cryptogram;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[2]/div[1]/span")
	private WebElement hrmValue;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[2]/div[2]/span")
	private WebElement hrcValue;

	@FindBy(how=How.XPATH,using="//*[@id='receiptMarkup']/div/div[2]/div[3]/div/div[2]/div[3]/span")
	private WebElement cvmValue;

	@FindBy(how=How.XPATH,using="//*[@id='receiptBody']/div[5]/div/div/div[1]/button/span[2]")
	private WebElement closeReceiptModalBtn;

	public String getReceiptStatus(){
		new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='receiptBody']/div[5]/div/div")));
		String paymentstatus=viewReceipt.getText();
		return paymentstatus;
	}
	
	public boolean wasPaymentSuccesful() {
		return getReceiptStatus().equalsIgnoreCase("Payment Successful");
	}

	public String getSequenceNumber() {
		String sequenceNumberText = sequenceNumber.getText();
		// Get only the part after Sequence #: 
		return sequenceNumberText.substring(12).trim();
	}
	
	public String getReceiptAmount() {
		// return amount from receipt without dollar sign
		return receiptAmount.getText().substring(1).trim();
	}
	
	public String getReceiptMerchant() {
		// return without "to "
		return receiptMerchant.getText().substring(3).trim();
	}

	public String getReceiptPatient() {
		// return without "for "
		return receiptPatientName.getText().substring(4).trim();
	}
	
	public String getReceiptAccountNumber() {
		// return without brackets
		return receiptAccountNumber.getText().replaceAll("[()]", "").trim();
	}
	
	public String getReceiptCardLastFour() {
		return receiptLastFour.getText().trim();
	}
	
	public boolean isReceiptEqual(String merchant, String amount, String cardNumber) {
		System.out.println("Merchant: " + getReceiptMerchant());
		System.out.println("Amount: " + getReceiptAmount());
		String lastFour = cardNumber.substring(cardNumber.length() - 4);
		return Arrays.equals(new String[]
				{getReceiptMerchant(), getReceiptAmount(), getReceiptCardLastFour()},
	              new String[] {merchant, amount, lastFour });
	}

	public void getPaymentSuccessorFailureStatus(){
		Assert.assertTrue("System error is seen!!Payment could not be processed",!driver.getPageSource().contains("System Error"));
		Assert.assertTrue("Form error is seen!!The card details provided were incorrect..Payment could not be processed",!driver.getPageSource().contains("Form Error"));
		Assert.assertTrue("Transaction error is seen!!Payment could not be processed",!driver.getPageSource().contains("Transaction Error"));
		Assert.assertEquals("Payment Successful", paymentSuccessMsg.getText());
		Assert.assertTrue("Amount is not displayed in the VCS receipt!",receiptAmount.isDisplayed());
		Assert.assertTrue("Merchant name is not displayed in the VCS receipt!",receiptMerchant.isDisplayed());
		Assert.assertTrue("Payment details are not displayed in the VCS receipt!",paymentDetails.isDisplayed());
		Assert.assertTrue("EMV values are displayed in the VCS receipt!",emvSection.isDisplayed());
		Assert.assertEquals("n/a", applicationLabel.getText());
		Assert.assertEquals("n/a", aidLabel.getText());
		Assert.assertEquals("n/a", cryptogram.getText());
		Assert.assertEquals("Approved", hrmValue.getText());
		Assert.assertEquals("000", hrcValue.getText());
		Assert.assertEquals("n/a", cvmValue.getText());
	}

	public void sendEmail(String emailID){
		email.click();
		email.clear();
		email.sendKeys(emailID);
		sendEmailButton.click();
	}

	public void closeReceiptModal(){
		closeReceiptModalBtn.click();
	}
	
	public boolean wasEmailingSuccessCloseToast() {
		if(toastCloseButton.isDisplayed()&&(toastPopUp.getText().equalsIgnoreCase("Success"))){
			System.out.println("Email succesfully sent.");	
			toastCloseButton.click();
			return true;
		}
		else {
			toastCloseButton.click();
			return false;
		}
	}

	public void printReceipt() {
		printButton.click();
		printButton.sendKeys(Keys.ENTER);
		
	}


	public void clickToast(){
		toastCloseButton.click();
	}

	public String getToastDetails(){
		String toast=toastPopUp.getText();
		return toast;
	}
}
