package com.medfusion.product.object.maps.jalapeno.page.NewPayBillsPage;

import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class JalapenoPayBillsMakePaymentPage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "payment_amount")
	private WebElement paymentAmount;
	
	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addNewCardButton;
	
	@FindBy(how = How.ID, using = "pay_history")
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
	
	@FindBy(how = How.ID, using = "expiration")
	private WebElement expiration;
	
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
	
	public JalapenoPayBillsMakePaymentPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	private void fillNewCardInformation(String name, String number, String exp, String cvv, String zipCode) {
		log("Verify all elements of lightbox are visible");
		assessAddNewCrediCardLightboxElements();
		
		log("Filling info about new credit card");
		log("Name on card: " + name);
		nameOnCard.sendKeys(name);
		
		log("ZipCode: " + zipCode);
		bill_zipcode.sendKeys(zipCode);
		
		log("Card number: " + number);
		cardNumber.sendKeys(number);
		
		log("Expiration: " + exp);
		expiration.sendKeys(exp);
		
		log("CVV: " + cvv);
		creditCardCVV.sendKeys(cvv);
		
		log("Is card selected? Result: " + visaCard.getAttribute("class").contains("ccselected"));
		
		log("Submit new card");
		submitNewCard.click();
	}
	
	public JalapenoPayBillsConfirmationPage fillPaymentInfo(String amount, String accNumber) {
		//TODO: extract these information somewhere to property file and make it customizable
		String name = "TestPatient CreditCard";
		String number = "4111111111111111";
		String exp = "1020";
		String cvvCode = "001";
		String zipCode = "12345";
		
		log("Click on Add New Card");
		addNewCardButton.click();
		fillNewCardInformation(name, number, exp, cvvCode, zipCode);
		
		log("Insert Payment amount: " + amount);
		paymentAmount.sendKeys(amount);		
		
		log("Insert account number: " + accNumber);
		accountNumber.sendKeys(accNumber);
		
		log("Insert CVV code: " + cvvCode);
		confirmCVV.sendKeys(cvvCode);
		
		log("Click on Continue button");
		//for some reason it's not reacting when there's only one click on Continue button
		continueButton.click();
		continueButton.click();
		
		return PageFactory.initElements(driver, JalapenoPayBillsConfirmationPage.class);
	}
	
	public void removePreviousCardsIfPresent() {
		log("Removing of displayed cards");
		ArrayList<WebElement> cards = (ArrayList<WebElement>) driver.findElements(By.xpath("//li[contains(@class, 'toggleCheck')]"));
		int removedCards = 0;
		
		if(cards.size() > 0) {
			log("Count of displayed cards: " + cards.size());
			ArrayList<WebElement> removeButtons = (ArrayList<WebElement>) driver.findElements(By.xpath("//a[contains(@class,'creditCardRemoveButton')]"));
			//remove this logging after Lidka's fix -> there should be cards.size == removeButtons.size
			log("Count of removeButtons: " + removeButtons.size());
			for(int i = 0; i < removeButtons.size(); i++) {
				if (removeButtons.get(i).isDisplayed()) {
					removeCreditCard(removeButtons.get(i));
					removedCards++;
				}
			}
		}
		
		log("Count of successfully removed cards: " + removedCards);	
	}
	
	private JalapenoPayBillsMakePaymentPage removeCreditCard(WebElement removeButton) {
		removeButton.click();
		driver.findElement(By.id("removeCardOkButton")).click();
		
		return this;
	}
	
	//modified assess to see if it will work without waitForElement and moved allElementsDisplayed=true at the end
	public boolean assessPayBillsMakePaymentPageElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(paymentAmount);
		webElementsList.add(payHistoryButton);
		webElementsList.add(addNewCardButton);
		webElementsList.add(accountNumber);
		webElementsList.add(continueButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
	
	private boolean assessAddNewCrediCardLightboxElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(nameOnCard);
		webElementsList.add(bill_zipcode);
		webElementsList.add(cardNumber);
		webElementsList.add(expiration);
		webElementsList.add(creditCardCVV);
		webElementsList.add(submitNewCard);
		webElementsList.add(amexCard);
		webElementsList.add(discoverCard);
		webElementsList.add(mastercardCard);
		webElementsList.add(visaCard);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
