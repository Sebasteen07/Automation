//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.NewPayBillsPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;

public class JalapenoPayBillsMakePaymentPage extends JalapenoMenu {

	@FindBy(how = How.ID, using = "payment_amount")
	private WebElement paymentAmount;

	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addNewCardButton;

	@FindBy(how = How.ID, using = "removeCardOkButton")
	private WebElement removeCardOkButton;

	@FindBy(xpath = ".//a[contains(text(), 'Payment History') or @id = 'billpayTabPaymentHistoryTab']")
	private WebElement payHistoryButton;

	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement confirmCVV;

	@FindBy(how = How.ID, using = "make_payment_submit")
	private WebElement continueButton;

	@FindBy(how = How.ID, using = "nameOnCard")
	private WebElement nameOnCard;

	@FindBy(how = How.ID, using = "bill_zipcode")
	private WebElement bill_zipcode;

	@FindBy(how = How.ID, using = "cardNumber")
	private WebElement cardNumber;

	@FindBy(how = How.ID, using = "expirationDate_month")
	private WebElement expirationMonth;

	@FindBy(how = How.ID, using = "expirationDate_year")
	private WebElement expirationYear;

	@FindBy(how = How.ID, using = "creditCardCVV")
	private WebElement creditCardCVV;

	@FindBy(how = How.ID, using = "cardSubmitButton")
	private WebElement submitNewCard;

	@FindBy(how = How.ID, using = "amex")
	private WebElement amexCard;

	@FindBy(how = How.ID, using = "discover")
	private WebElement discoverCard;

	@FindBy(how = How.ID, using = "mastercard")
	private WebElement mastercardCard;

	@FindBy(how = How.ID, using = "visa")
	private WebElement visaCard;

	@FindBy(how = How.XPATH, using = "//*[@id=\"balanceDue\"]/span[1]")
	private WebElement statementDetails;

	@FindBy(how = How.XPATH, using = "//*[@id=\"balanceDue\"]/span/strong")
	private WebElement balanceDue;

	@FindBy(how = How.XPATH, using = "//*[@id='headerElements']/following-sibling::span/strong")
	private WebElement outstandingInsuranceBalance;

	@FindBy(how = How.XPATH, using = "//*[@id=\"balanceDue\"]/span")
	private WebElement balanceDueDate;

	@FindBy(how = How.XPATH, using = "//*[contains(@href,'#/payments/history/details')]/preceding-sibling::span[1]")
	private WebElement confirmationNumberMsg;

	@FindBy(how = How.XPATH, using = "(//table[@class='table table-condensed']/tbody/tr/td/a)[1]")
	private WebElement selectFirsttransaction;

	@FindBy(how = How.XPATH, using = "//td[label[contains(text(),'Credit Card')]]/following-sibling::td")
	private WebElement receiptCardDigit;
	
	public JalapenoPayBillsMakePaymentPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		PageFactory.initElements(driver, this);
	}

	private void fillNewCardInformation(CreditCard card) throws InterruptedException {
		log("Verify all elements of lightbox are visible");
		assertTrue(areAddNewCreditCardLightboxElementsPresent());

		log("Filling info about new credit card");
		log("Name on card: " + card.getName());
		Thread.sleep(2000);
		nameOnCard.sendKeys(card.getName());
		log("ZipCode: " + card.getZipCode());
		bill_zipcode.sendKeys(card.getZipCode());

		log("Card number: " + card.getCardNumber());
		cardNumber.sendKeys(card.getCardNumber());

		log("Expiration: " + card.getExpMonth() + "/" + card.getExpYear());

		Select selectMonth = new Select(expirationMonth);
		selectMonth.selectByVisibleText(card.getExpMonth());

		Select selectYear = new Select(expirationYear);
		selectYear.selectByVisibleText(card.getExpYear());
		log("CVV: " + card.getCvvCode());
		creditCardCVV.sendKeys(card.getCvvCode());

		log("Checking if " + card.getType() + " card type is selected");
		assert isCardTypeSelected(card.getType()) : "Wrong card type was selected.";

		log("Submit new card");
		submitNewCard.click();
		Thread.sleep(3000);
	}

	public JalapenoPayBillsConfirmationPage fillPaymentInfo(String amount, String accNumber, CreditCard creditCard)
			throws InterruptedException {
		return fillPaymentInfo(amount, accNumber, creditCard, "");
	}

	public JalapenoPayBillsConfirmationPage fillPaymentInfo(String amount, String accNumber, CreditCard creditCard,
			String location) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		log("Click on Add New Card");
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		addNewCardButton.sendKeys(Keys.ENTER);
		fillNewCardInformation(creditCard);

		log("Insert Payment amount: " + amount);
		paymentAmount.clear();
		paymentAmount.sendKeys(amount);
		
		log("Insert account number: " + accNumber);
		try {
			accountNumber.sendKeys(accNumber);
		}
		catch(Exception e)
		{
			log(e.getMessage());
		}
		
		log("Insert CVV code: " + creditCard.getCvvCode());
		wait.until(ExpectedConditions.visibilityOf(confirmCVV));

		confirmCVV.sendKeys(creditCard.getCvvCode());

		if (!location.equals("")) {
			log("Location not empty, selecting");
			Select selectLoc = new Select(driver.findElement(By.name("location")));
			selectLoc.selectByVisibleText(location);
		}

		log("Click on Continue button");
		// Race condition - sometimes click doesn't work, added explicit wait (didn't
		// help), updated to sendKeys
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		continueButton.sendKeys(Keys.ENTER);
		return PageFactory.initElements(driver, JalapenoPayBillsConfirmationPage.class);
	}

	private ArrayList<WebElement> getCreditCards() {
		return (ArrayList<WebElement>) driver.findElements(By.xpath("//li[contains(@class, 'toggleCheck')]"));
	}

	public boolean isAnyCardPresent() {
		return getCreditCards().size() > 0;
	}

	public void removeAllCards() throws InterruptedException {
		log("Removing of displayed cards");
		ArrayList<WebElement> cards = getCreditCards();

		if (cards.size() > 0) {
			log("Count of displayed cards: " + cards.size());
			int removedCards = 0;

			ArrayList<WebElement> removeButtons = (ArrayList<WebElement>) driver
					.findElements(By.xpath("//a[contains(@class,'creditCardRemoveButton')]"));
			for (int i = 0; i < removeButtons.size(); i++) {
				if (removeButtons.get(i).isDisplayed()) {
					removeCreditCard(removeButtons.get(i));
					log("Card #" + ++removedCards + " removed");
					// need to sleep because of modal disappearing time
					Thread.sleep(2000);
				}
			}
		} else {
			log("No previous card is displayed");
		}
	}

	private JalapenoPayBillsMakePaymentPage removeCreditCard(WebElement removeButton) {
		removeButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(removeCardOkButton));
		removeCardOkButton.click();
		return this;
	}

	public void clickPaymentHistory() {
		wait.until(ExpectedConditions.elementToBeClickable(payHistoryButton));
		payHistoryButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(selectFirsttransaction));
		selectFirsttransaction.click();

	}

	private boolean isCardTypeSelected(CardType type) {
		switch (type) {
		case Visa:
			return visaCard.getAttribute("class").contains("ccselected");
		case Mastercard:
			return mastercardCard.getAttribute("class").contains("ccselected");
		case Discover:
			return discoverCard.getAttribute("class").contains("ccselected");
		case Amex:
			return amexCard.getAttribute("class").contains("ccselected");
		default:
			log("Unknown card type was inserted");
			return false;
		}
	}

	private boolean areAddNewCreditCardLightboxElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(nameOnCard);
		webElementsList.add(bill_zipcode);
		webElementsList.add(cardNumber);
		webElementsList.add(expirationMonth);
		webElementsList.add(expirationYear);
		webElementsList.add(creditCardCVV);
		webElementsList.add(submitNewCard);
		webElementsList.add(amexCard);
		webElementsList.add(discoverCard);
		webElementsList.add(mastercardCard);
		webElementsList.add(visaCard);

		return assessPageElements(webElementsList);
	}

	public void gotoStatementDetail() {
		statementDetails.click();
	}

	public String getAccountNumber() {
		return accountNumber.getAttribute("value");
	}

	public String getBalanceDue() {
		return balanceDue.getText();
	}

	public String getOutstandingInsuranceBalance() {
		return outstandingInsuranceBalance.getText();
	}

	public String getBalanceDueDate() {
		return balanceDueDate.getText();
	}

	public String getReceiptCreditCardDigit() {
		return receiptCardDigit.getText().substring(receiptCardDigit.getText().length() - 4);

	}

	public String readConfirmationNumber() {
		IHGUtil.waitForElement(driver, 50, confirmationNumberMsg);
		String confirmationNumberMsgstring = confirmationNumberMsg.getText();
		String confirmationNumber = confirmationNumberMsgstring.substring(28, 40);
		return confirmationNumber;

	}
	
	public JalapenoPayBillsConfirmationPage fillPaymentInfoWithExistingCards(String amount, String accNumber, String creditCardCsv) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
		log("Insert Payment amount: " + amount);
		paymentAmount.clear();
		paymentAmount.sendKeys(amount);
		
		log("Insert account number: " + accNumber);
		try {
			accountNumber.sendKeys(accNumber);
		}
		catch(Exception e)
		{
			log(e.getMessage());
		}
		wait.until(ExpectedConditions.visibilityOf(confirmCVV));
		confirmCVV.sendKeys(creditCardCsv);
		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		continueButton.sendKeys(Keys.ENTER);
		return PageFactory.initElements(driver, JalapenoPayBillsConfirmationPage.class);
	}
	public JalapenoPayBillsConfirmationPage fillPaymentInfoForDuplicate(String amount, String accNumber, CreditCard creditCard)
			throws InterruptedException {
		return fillPaymentInfoForDuplicate(amount, accNumber, creditCard, "");
	}

	public JalapenoPayBillsConfirmationPage fillPaymentInfoForDuplicate(String amount, String accNumber, CreditCard creditCard,
			String location) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		log("Click on Add New Card");
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		addNewCardButton.sendKeys(Keys.ENTER);
		fillNewCardInformation(creditCard);

		log("Insert Payment amount: " + amount);
		paymentAmount.clear();
		paymentAmount.sendKeys(amount);
		
		log("Insert account number: " + accNumber);
		try {
			accountNumber.sendKeys(accNumber);
		}
		catch(Exception e)
		{
			log(e.getMessage());
		}
		
		log("Insert CVV code: " + creditCard.getCvvCode());
		wait.until(ExpectedConditions.visibilityOf(confirmCVV));

		confirmCVV.sendKeys(creditCard.getCvvCode());
		log("Click on Continue button");
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		continueButton.sendKeys(Keys.ENTER);
		return PageFactory.initElements(driver, JalapenoPayBillsConfirmationPage.class);
	}

	
}
