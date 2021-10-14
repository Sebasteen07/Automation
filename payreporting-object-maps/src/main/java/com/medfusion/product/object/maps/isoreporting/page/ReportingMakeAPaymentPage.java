//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved
package com.medfusion.product.object.maps.isoreporting.page;

import com.medfusion.common.utils.PropertyFileLoader;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class ReportingMakeAPaymentPage extends ReportingPOSVCSMenu {

	public ReportingMakeAPaymentPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(how = How.XPATH, using = "//div[@id='merchantWrapper']/select")
	private WebElement vcsMerchant;

	@FindBy(how = How.ID, using = "amount")
	private WebElement amountToCharge;

	@FindBy(how = How.ID, using = "cardHolderName")
	private WebElement cardHolder;

	@FindBy(how = How.ID, using = "cardNumber")
	private WebElement cardNumber;

	@FindBy(how = How.ID, using = "cardExpiration")
	private WebElement cardExpiry;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement cardCVV;

	@FindBy(how = How.ID, using = "zip")
	private WebElement cardZip;

	@FindBy(how = How.ID, using = "consumerName")
	private WebElement consumer;

	@FindBy(how = How.ID, using = "consumerAccountNumber")
	private WebElement consumerAccount;

	@FindBy(how = How.ID, using = "VCSFromButton")
	private WebElement chargeButton;

	@FindBy(how = How.ID, using = "email")
	private WebElement email;

	@FindBy(how = How.ID, using = "receiptFormButton")
	private WebElement sendEmailButton;

	@FindBy(how = How.XPATH, using = "//*[@id='printButton']/button[1]")
	private WebElement printButton;

	@FindBy(how = How.XPATH, using = "//*[@id='templateHeader']/tbody/tr/td/h1")
	private WebElement viewReceipt;

	@FindBy(how = How.XPATH, using = "//*[@id='toast-container']/div/button")
	private WebElement toastCloseButton;

	@FindBy(how = How.XPATH, using = "//*[@id='toast-container']/div[1]/div[1]")
	private WebElement toastPopUp;

	@FindBy(how = How.XPATH, using = "//div[@class='ng-binding ng-scope']")
	private WebElement declinedErrorToastPopUp;

	@FindBy(how = How.XPATH, using = "//div[contains(text(),'Please correct the highlighted form fields.')]")
	private WebElement formError;

	@FindBy(how = How.XPATH, using = "//*[@id='posWrapper']/div/div/form/p")
	private WebElement vcsInformation;

	@FindBy(how = How.XPATH, using = "//*[@id='templateHeader']/tbody/tr/td/h1")
	private WebElement paymentDetails;

	@FindBy(how = How.XPATH, using = "//*[@id='templateContainer']/tbody/tr[2]/td/span[1]")
	private WebElement amountInReceipt;

	@FindBy(how = How.XPATH, using = "//*[@id='templateContainer']/tbody/tr[2]/td/span[2]")
	private WebElement merchantInReceipt;

	@FindBy(how = How.XPATH, using = "//*[@id='templateContainer']/tbody/tr[2]/td/span[3]")
	private WebElement pateintInReceipt;

	@FindBy(how = How.XPATH, using = "//*[@id='templateContainer']/tbody/tr[2]/td/span[4]")
	private WebElement AccountNumberInReceipt;

	@FindBy(how = How.XPATH, using = "//*[@id='templateColumns']/tbody/tr/td[1]/table/tbody/tr/td")
	private WebElement vcsMerchantDetails;

	@FindBy(how = How.XPATH, using = "//*[@id='templateColumns']/tbody/tr/td[2]/table/tbody/tr/td")
	private WebElement vcsPaymentDetails;

	@FindBy(how = How.XPATH, using = "//*[@id='templateColumns']/tbody/tr/td[2]/table/tbody/tr/td/span[3]")
	private WebElement vcsTransactionID;

	@FindBy(how = How.XPATH, using = "//*[@id='receiptBody']/div[5]/div/div/div[1]/div")
	private WebElement receiptModal;

	public void selectMerchant(String merchant) {
		Select select = new Select(vcsMerchant);
		select.selectByVisibleText(merchant);
	}

	public void enterCardInformation(String vcsAmount, String cardholder, String cardnumber, String cardexpiry,
			String cardcvv, String cardzip, String consumername, String consumeraccountnumber) {
		Assert.assertTrue("Instructions to make a vcs payment is not displayed", vcsInformation.isDisplayed());
		amountToCharge.click();
		amountToCharge.sendKeys(vcsAmount);
		cardHolder.click();
		cardHolder.sendKeys(cardholder);
		cardNumber.click();
		cardNumber.sendKeys(cardnumber);
		cardExpiry.click();
		cardExpiry.sendKeys(cardexpiry);
		cardCVV.click();
		cardCVV.sendKeys(cardcvv);
		cardZip.click();
		cardZip.sendKeys(cardzip);
		consumer.click();
		consumer.sendKeys(consumername);
		consumerAccount.click();
		consumerAccount.sendKeys(consumeraccountnumber);

	}

	public ReportingVCSReceiptPage clickCharge() {
		Assert.assertTrue("Charge button is not present on the payments page", chargeButton.isDisplayed());
		chargeButton.click();
		return PageFactory.initElements(driver, ReportingVCSReceiptPage.class);
	}

	public void IsTransactionisDeclined() {
		Boolean isDeclined = declinedErrorToastPopUp.getText().contains("Partial Approved");
		Assert.assertTrue(isDeclined.equals(true));

	}

	public ReportingVCSReceiptPage waitForReceiptPopup() throws IOException {
		PropertyFileLoader testData = new PropertyFileLoader();
		new WebDriverWait(driver, Integer.parseInt(testData.getProperty("timeout")))
				.until(ExpectedConditions.visibilityOf(receiptModal));
		return PageFactory.initElements(driver, ReportingVCSReceiptPage.class);
	}

	public Boolean IsFormError() {
		Boolean fieldError = formError.getText().contains("Please correct the highlighted form fields.");
		Assert.assertTrue(fieldError.equals(true));
		return fieldError;
	}

}
