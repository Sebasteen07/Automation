package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

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

import static com.intuit.ifs.csscat.core.BaseTestSoftAssert.assertTrue;

import java.util.ArrayList;

public class JalapenoAskAStaffV2Page1 extends JalapenoMenu {
	// Navigation
	@FindBy(how = How.ID, using = "historyButton")
	private WebElement historyButton;

	@FindBy(how = How.ID, using = "continueButton")
	private WebElement continueButton;

	@FindBy(how = How.ID, using = "subject")
	private WebElement subjectBox;

	@FindBy(how = How.ID, using = "question")
	private WebElement questionBox;

	@FindBy(how = How.ID, using = "removeCardOkButton")
	private WebElement removeCardOkButton;

	@FindBy(how = How.XPATH, using = "//div[text()=' Payment Details ']")
	private WebElement paymentDetails;

	// Custom component, use click -> sendkeys sequence to manipulate
	@FindBy(how = How.XPATH, using = "//div[@id='locationField']/mf-combobox/div")
	private WebElement locationCombo;

	@FindBy(how = How.XPATH, using = "//div[@id='providerField']/mf-combobox/div")
	private WebElement providerCombo;

	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addNewCardButton;

	@FindBy(how = How.ID, using = "payment_amount")
	private WebElement paymentAmount;

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

	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;

	// Credit card
	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addCard;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement confirmCVV;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement cardCVV;

	@FindBy(how = How.ID, using = "nameOnCard")
	private WebElement nameOnCard;

	@FindBy(how = How.XPATH, using = "//input[@data-ng-maxlength='30']")
	private WebElement subjectLenght;

	@FindBy(how = How.XPATH, using = "//span[text()='Subject has too many characters.']")
	private WebElement errorMessage;

	@FindBy(how = How.XPATH, using = "//span[@class=\"pull-right ng-binding\"]")
	private WebElement subjectCharCount;

	private long createdTS;

	public JalapenoAskAStaffV2Page1(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		createdTS = System.currentTimeMillis();
	}

	public long getCreatedTimeStamp() {
		return createdTS;
	}

	/**
	 * fills the subject and question fields then continues to the next page and
	 * returns its page object null or empty subject param will leave the default (=
	 * the aska label)
	 *
	 * @param subject
	 * @param question
	 * @param subjectbox
	 * @return
	 * @throws InterruptedException
	 */
	public JalapenoAskAStaffV2Page2 fillAndContinue(String subject, String question) throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			subjectBox.clear();
			subjectBox.sendKeys(subject);
			Thread.sleep(10000);
		}
		questionBox.sendKeys(question);
		Thread.sleep(10000);
		continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 fillAndContinue(String invalidLengthText, String question, String validLengthText)
			throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				if (invalidLengthText != null && !invalidLengthText.trim().isEmpty()) {
					subjectBox.clear();
					if (invalidLengthText.length() > 30) {
						subjectBox.sendKeys(invalidLengthText);
						Thread.sleep(100);
						String errorMessageText = errorMessage.getText();
						log("More than 30 character showing error msg: " + errorMessageText);
						assertTrue(errorMessageText.equals("Subject has too many characters."),
								"Expected: " + errorMessageText + ", found: " + "Subject has too many characters.");
						subjectBox.clear();
						continue;
					}
				}
			} else {
				if (validLengthText != null && !validLengthText.trim().isEmpty()) {
					subjectBox.sendKeys(validLengthText);
					String string[] = subjectCharCount.getText().split("/");
					int charCount = Integer.parseInt(string[0]);
					if (validLengthText.length() == charCount) {
						log("The character count in subject  is equals to the Displaying character count in UI");
					} else {
						log("The character count in subject  is Notequals to the Displaying character count in UI");

					}
				}
			}
		}

		questionBox.sendKeys(question);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementList = new ArrayList<WebElement>();
		webElementList.add(subjectBox);
		webElementList.add(questionBox);
		webElementList.add(historyButton);
		webElementList.add(continueButton);
		webElementList.add(paymentDetails);
		// provider, location, and card info are all optional
		return assessPageElements(webElementList);
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

	public JalapenoAskAStaffV2HistoryListPage clickOnHistory() {
		log("Clicking on Ask a Question menu button");
		historyButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2HistoryListPage.class);
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
					Thread.sleep(5000);
				}
			}
		} else {
			log("No previous card is displayed");
		}
	}

	private JalapenoAskAStaffV2Page1 removeCreditCard(WebElement removeButton) {
		removeButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(removeCardOkButton));
		removeCardOkButton.click();
		return this;
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

	private void fillNewCardInformation(CreditCard card) {
		log("Verify all elements of lightbox are visible");
		assertTrue(areAddNewCreditCardLightboxElementsPresent());

		log("Filling info about new credit card");
		log("Name on card: " + card.getName());
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
	}

	public JalapenoAskPayBillsConfirmationPage fillPaymentInfo(String accNumber, CreditCard creditCard) {
		return fillPaymentInfo(accNumber, creditCard, "");
	}

	public JalapenoAskPayBillsConfirmationPage fillPaymentInfo(String accNumber, CreditCard creditCard,
			String location) {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		log("Click on Add New Card");
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		addNewCardButton.sendKeys(Keys.ENTER);
		fillNewCardInformation(creditCard);

		log("Insert CVV code: " + creditCard.getCvvCode());
		wait.until(ExpectedConditions.visibilityOf(confirmCVV));
		confirmCVV.sendKeys(creditCard.getCvvCode());

		log("Click on Continue button");
		// Race condition - sometimes click doesn't work, added explicit wait (didn't
		// help), updated to sendKeys
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		continueButton.sendKeys(Keys.ENTER);

		return PageFactory.initElements(driver, JalapenoAskPayBillsConfirmationPage.class);
	}

	public WebElement getPaymentText(WebElement paymentAmount) {
		return paymentAmount;
	}

	public String getAskaPaymentText() {
		return paymentAmount.getText();

	}

}