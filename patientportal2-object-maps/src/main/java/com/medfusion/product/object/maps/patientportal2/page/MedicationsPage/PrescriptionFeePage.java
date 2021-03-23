//Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.MedicationsPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.patientportal2.pojo.CreditCard;

public class PrescriptionFeePage  extends JalapenoMenu {
	
	@FindBy(how=How.XPATH, using="//button[@id='creditCardAddButton']")
	private WebElement addNewCardBtn;
	
	@FindBy(how=How.XPATH, using="//input[@id='cvv']")
	private WebElement inputCVV;

	@FindBy(how=How.XPATH, using="//a[@class='creditCardEditButton credit-card-action-link ng-binding ng-scope']")
	private WebElement cardEditBtn;
	
	@FindBy(how=How.XPATH, using="//a[@class='creditCardRemoveButton credit-card-action-link ng-binding ng-scope']")
	private WebElement cardRemovebtn;
	
	@FindBy(how=How.XPATH, using="//div[@class='form-buttons ng-scope']/button[@class='btn btn-secondary ng-binding']")
	private WebElement btnBack;
	
	@FindBy(how=How.XPATH, using="//button[@class='btn btn-primary ng-binding']")
	private WebElement useThisCardBtn;
	
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
	
	@FindBy(how = How.ID, using = "removeCardOkButton")
	private WebElement removeCardOkButton;
	
	@FindBy(how = How.ID, using = "cvv")
	private WebElement mainPageCVV;
	
	public PrescriptionFeePage(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean areBasicPageElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();

		webElementsList.add(addNewCardBtn);
		webElementsList.add(btnBack);
		webElementsList.add(useThisCardBtn); 
		
		if(isElementVisible(cardEditBtn, 1))
		{
		webElementsList.add(inputCVV);
		webElementsList.add(cardEditBtn);
		webElementsList.add(cardRemovebtn);
		}
		
		return assessPageElements(webElementsList);
	}

	public void fillRenewalFee(WebDriver driver, CreditCard card) throws InterruptedException {
		removeAllCards();
		addNewCardBtn.click();
		log("Verify all elements of lightbox are visible");
		assertTrue(areAddNewCreditCardLightboxElementsPresent());

		log("Filling info about new credit card");
		log("Name on card: " + card.getName());
		Thread.sleep(1000);
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

		log("Submit new card");
		submitNewCard.click();
		
		wait.until(ExpectedConditions.visibilityOf(mainPageCVV));
		mainPageCVV.sendKeys(card.getCvvCode());
		
		useThisCardBtn.click();
	
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

		return assessPageElements(webElementsList);
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

	private PrescriptionFeePage removeCreditCard(WebElement removeButton) {
		removeButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(removeCardOkButton));
		removeCardOkButton.click();
		return this;
	}
}
