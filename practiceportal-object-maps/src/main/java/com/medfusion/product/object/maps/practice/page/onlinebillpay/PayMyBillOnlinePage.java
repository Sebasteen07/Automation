package com.medfusion.product.object.maps.practice.page.onlinebillpay;

import java.util.Calendar;
import java.util.List;

import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeConstants;

public class PayMyBillOnlinePage extends BasePageObject {

	public PayMyBillOnlinePage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='searchParams:0:input']")
	private WebElement firstName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@name='searchParams:1:input']")
	private WebElement lastName;

	@FindBy(xpath = "//table[@class='searchForm']//input[@value='Search']")
	private WebElement searchForPatients;

	@FindBy(xpath = "//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(),'automation, ihgqa')]")
	private WebElement searchResult;

	@FindBy(name = "locationList")
	private WebElement location;

	@FindBy(xpath = "//select[@name='providerList']")
	private WebElement provider;

	@FindBy(xpath = "//input[@name='patientAccountNumber']")
	private WebElement patientAccountNumber;

	@FindBy(xpath = "//input[@name='paymentAmount']")
	private WebElement paymentAmount;

	@FindBy(xpath = "//input[@name='paymentComment']")
	private WebElement paymentComment;

	@FindBy(xpath = "//*/tr/td[2][contains(.,'t save card post transaction')]/preceding-sibling::td/input[@name='creditCardPanel:existingccdetailscontainer:ccRadioGroup']")
	private WebElement donotSaveTransaction;

	@FindBy(xpath = "//*/tr/td[2][contains(.,'Pay with New Card')]/preceding-sibling::td/input[@name='creditCardPanel:existingccdetailscontainer:ccRadioGroup']")
	private WebElement newCardPay;

	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:nameOnCreditCard']")
	private WebElement cardHolder;

	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:creditCardNumber']")
	private WebElement cardNumber;

	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:creditCardType']")
	private WebElement cardType;

	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:expirationMonth']")
	private WebElement expirationMonth;

	@FindBy(xpath = "//select[@name='creditCardPanel:newccdetails:expirationYear']")
	private WebElement expirationYear;

	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:newccdetailscvv:cvvCode']")
	private WebElement cvvCode;

	@FindBy(xpath = "//input[@name='creditCardPanel:newccdetails:addressZip']")
	private WebElement zipCode;

	@FindBy(xpath = "//input[@name='payBillButton']")
	private WebElement payBillButton;

	@FindBy(xpath = "//input[@name=':submit']")
	private WebElement submitPaymentButton;

	@FindBy(xpath = "//div[@id='content']/div[2]/strong/div/p")
	public WebElement paymentConfirmationText;

	@FindBy(xpath = "//input[@name='voidPayment']")
	private WebElement voidPaymentButton;

	@FindBy(name = "comment")
	private WebElement commentForVoid;

	@FindBy(xpath = ".//input[@value='Void']")
	private WebElement voidButton;

	// @FindBy( xpath = ".//*[@id='_wicket_window_2']/a")
	@FindBy(xpath = ".//*[@id='_wicket_window_1']//div[@class='w_caption']/a")
	private WebElement closeVoidPopUp;

	@FindBy(xpath = ".//input[@name = 'refundPayment' and @value='Refund Payment']")
	private WebElement refundPayment;

	@FindBy(xpath = "//input[@name ='amount']")
	private WebElement amountToRefundField;

	@FindBy(xpath = "//textarea[@name ='comment']")
	private WebElement commentForRefund;

	@FindBy(xpath = "//input[@value='Refund']")
	private WebElement refundButton;

	@FindBy(xpath = ".//*[@id='_wicket_window_15']/a")
	private WebElement closeRefundPopUp;

	@FindBy(xpath = "//iframe[contains(@id,'_wicket_window_')]")
	private WebElement iFrameRefundWindow;

	@FindBy(id = "table-1")
	private WebElement transactionsList;

	/**
	 * @Description:Set First name
	 */
	public void setFirstName() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		firstName.clear();
		firstName.sendKeys(PracticeConstants.PATIENT_FIRST_NAME);
	}

	/**
	 * @Description:Set Last Name
	 */
	public void setLastName() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		lastName.clear();
		lastName.sendKeys(PracticeConstants.PATIENT_LAST_NAME);
	}

	/**
	 * @Description:Set Location
	 */
	public void setLocation() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(location);
		try {
			sel.selectByVisibleText(PracticeConstants.LOCATION);
		} catch (Exception e) {
			sel.selectByVisibleText(PracticeConstants.LOCATION2);
		}

	}

	public void setLocation(String name) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(location);
		try {
			sel.selectByVisibleText(name);
		} catch (Exception e) {
			log("Exception caught! Retrying");
			sel.selectByVisibleText(name);
		}

	}

	/**
	 * @Description:Choose the provider name from Drop Down
	 * @param pProvider
	 */
	public void chooseProvider(String pProvider) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 20, provider);
		String providerXpath = "//option//text()[contains(.,'" + pProvider + "')]/..";
		WebDriverWait wait = new WebDriverWait(driver, 20);
		driver.findElement(By.xpath(providerXpath)).click();
		wait.until(ExpectedConditions.elementToBeSelected(By.xpath(providerXpath)));
	}

	/**
	 * @Description:Set Account Number
	 * @param accountNumber
	 */
	public void setPatientAccountNumber(String accountNumber) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		patientAccountNumber.clear();
		patientAccountNumber.sendKeys(accountNumber);
	}

	/**
	 * @Description:Set Payment Amount
	 * @param amount
	 */
	public void setPaymentAmount(String amount) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		paymentAmount.clear();
		paymentAmount.sendKeys(amount);
	}

	/**
	 * @Description:Set Payment Comment
	 * @param comment
	 */
	public void setPaymentComment(String comment) {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		paymentComment.clear();
		paymentComment.sendKeys(comment);
	}

	/**
	 * @Description:Set Don't Save Transaction radio button
	 * @throws Exception
	 */
	public void clickDonotSaveTransaction() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 30, donotSaveTransaction);
		donotSaveTransaction.click();
		Thread.sleep(4000);
	}

	/**
	 * @Description:Set Card Holder Name
	 * @throws Exception
	 */
	public void setCardholderName() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 30, cardHolder);
		cardHolder.clear();
		cardHolder.sendKeys(PracticeConstants.CARD_HOLDER_NAME);
		Thread.sleep(4000);

	}

	/**
	 * @Description:Set Card Number
	 * @throws Exception
	 */
	public void setCardNumber() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 30, cardNumber);
		cardNumber.clear();
		cardNumber.sendKeys(PracticeConstants.CARD_NUMBER);
		Thread.sleep(4000);

	}

	/**
	 * @Description:Set Credit Card Type
	 */
	public void setCardType() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(cardType);
		sel.selectByVisibleText(PracticeConstants.CARD_TYPE_VISA);

	}

	/**
	 * @throws Exception
	 * @Description:Set Expiration date
	 */
	public void setExpirationDate() throws Exception

	{
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(expirationMonth);
		sel.selectByVisibleText(PracticeConstants.EXPIRATION_MONTH);
		Select sele = new Select(expirationYear);
		List<WebElement> dropdown = sele.getOptions();
		for (int i = 0; i < dropdown.size(); i++) {
			String drop_down_values = dropdown.get(i).getText();
			String current_date = IHGUtil.getCurrentDate();
			String Current_year = current_date.substring(0, 4);
			int ExpirationYear = Integer.valueOf(Current_year);
			String yearInString = String.valueOf(ExpirationYear + 2);
			if (yearInString.equals(drop_down_values)) {
				sele.selectByVisibleText(yearInString);
				log("The" + yearInString + "and" + drop_down_values + " Matched");
			}

			else {

				log("The" + yearInString + "and" + drop_down_values + "didnt Matched");

			}
		}
	}

	/**
	 * @Description:Set CCV Code
	 */
	public void setCCVCode() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		cvvCode.clear();
		cvvCode.sendKeys(PracticeConstants.CVV);

	}

	/**
	 * @Description:Set Zip Code
	 */
	public void setZipCode() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		zipCode.clear();
		zipCode.sendKeys(PracticeConstants.ZIP_CODE);

	}

	/**
	 * @Description:Search For Patient
	 * @throws Exception
	 */

	public void searchForPatient() throws Exception {
		IHGUtil.PrintMethodName();
		setFirstName();
		setLastName();
		searchForPatients.click();
		IHGUtil.waitForElement(driver, 30, searchResult);
		searchResult.click();
		Thread.sleep(10000);
	}

	/**
	 * @Description:Set Patient Transaction Fields
	 * @throws Exception
	 */
	public void setPatientTransactionFields() throws Exception {
		IHGUtil.PrintMethodName();
		setLocation();
		setPatientAccountNumber(IHGUtil.createRandomNumericString().substring(0, 5));
		setPaymentAmount(IHGUtil.createRandomNumericString().substring(0, 2));
		try {
			setPaymentComment(PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString()));
		} catch (Exception e) {
			log("Payment Comment Field is not displayed");
		}

		clickDonotSaveTransaction();

		try {
			setCardholderName();
			setCardNumber();
			setCardType();
			setExpirationDate();
			setCCVCode();
			setZipCode();

		} catch (Exception e) {
			log("Card details is already added");
		}
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		chooseProvider(PracticeConstants.PROVIDER);
		payBillButton.click();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		submitPaymentButton.click();

	}

	public void searchForPatient(String fName, String lName) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		firstName.clear();
		firstName.sendKeys(fName);
		lastName.clear();
		lastName.sendKeys(lName);

		searchForPatients.click();
		IHGUtil.waitForElement(driver, 30, searchResult);

		driver.findElement(By.xpath(
				"//table[@id='MfAjaxFallbackDefaultDataTable']//span[contains(text(),'" + lName + ", " + fName + "')]"))
				.click();
		Thread.sleep(10000);

	}

	public void setTransactionsForOnlineBillPayProcess(String location, String provider, String acctNum, String amount,
			String cardHolderName, String cardNum, String cardTyp) throws Exception {
		IHGUtil.PrintMethodName();
		setLocation(location);
		setPatientAccountNumber(acctNum);
		setPaymentAmount(amount);

		try {
			setPaymentComment(PracticeConstants.PAYMENT_COMMENT.concat(IHGUtil.createRandomNumericString()));
		} catch (Exception e) {
			log("Payment Comment Field is not displayed");
		}

		clickDonotSaveTransaction();

		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		IHGUtil.waitForElement(driver, 30, cardHolder);
		cardHolder.clear();
		cardHolder.sendKeys(cardHolderName);
		Thread.sleep(4000);

		IHGUtil.waitForElement(driver, 30, cardNumber);
		cardNumber.clear();
		cardNumber.sendKeys(cardNum);
		Thread.sleep(4000);

		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select sel = new Select(cardType);
		sel.selectByVisibleText(cardTyp);

		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		Select selexp = new Select(expirationMonth);
		selexp.selectByVisibleText(PracticeConstants.EXPIRATION_MONTH);
		Select sele = new Select(expirationYear);
		List<WebElement> dropdown = sele.getOptions();
		for (int i = 0; i < dropdown.size(); i++) {
			String drop_down_values = dropdown.get(i).getText();
			String current_date = IHGUtil.getCurrentDate();
			String Current_year = current_date.substring(0, 4);
			int ExpirationYear = Integer.valueOf(Current_year);
			String yearInString = String.valueOf(ExpirationYear + 2);
			if (yearInString.equals(drop_down_values)) {
				sele.selectByVisibleText(yearInString);
				log("The" + yearInString + "and" + drop_down_values + " Matched");
			}

			else {

				log("The" + yearInString + "and" + drop_down_values + "didnt Matched");

			}
		}
		cvvCode.clear();
		cvvCode.sendKeys(PracticeConstants.CVV);

		zipCode.clear();
		zipCode.sendKeys(PracticeConstants.ZIP_CODE);

		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		chooseProvider(provider);
		payBillButton.click();
		IHGUtil.setFrame(driver, PracticeConstants.FRAME_NAME);
		submitPaymentButton.click();
		Thread.sleep(5000);

	}

	public void voidPayment(String voidComment) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframe");
		IHGUtil.waitForElement(driver, 50, voidPaymentButton);
		voidPaymentButton.click();
		System.out.println("THE VOID ELEMENT GOT CLICKED");
		Thread.sleep(8000);
		driver.switchTo().activeElement();
		IHGUtil.waitForElement(driver, 50, iFrameRefundWindow);
		driver.switchTo().frame(iFrameRefundWindow);
		commentForVoid.sendKeys(voidComment);
		voidButton.click();
	}

	public void refundPayment(String refundAmount, String refundComment) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframe");
		refundPayment.click();
		driver.switchTo().activeElement();
		driver.switchTo().frame(iFrameRefundWindow);
		amountToRefundField.sendKeys(refundAmount);
		commentForRefund.sendKeys(refundComment);
		refundButton.click();
	}

	public boolean isVoidTransactionPresent() {
		IHGUtil.PrintMethodName();
		IHGUtil.setFrame(driver, "iframe");
		if (new IHGUtil(driver).isRendered(transactionsList)) {
			IHGUtil.waitForElement(driver, 30, transactionsList);
			return transactionsList.getText().contains("Void") && transactionsList.getText().contains("$0.00");
		} else
			return false;

	}

}
