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
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoNewPayBillsPage extends BasePageObject {
	
	@FindBy(how = How.ID, using = "payment_amount")
	private WebElement paymentAmount;
	
	@FindBy(how = How.ID, using = "addNewCard")
	private WebElement addNewCardButton;
	
	@FindBy(how = How.ID, using = "pay_history")
	private WebElement payHistoryButton;
	
	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;
	
	@FindBy(how = How.ID, using = "areacode")
	private WebElement phone1;
	
	@FindBy(how = How.ID, using = "prefix")
	private WebElement phone2;
	
	@FindBy(how = How.ID, using = "code")
	private WebElement phone3;
	
	@FindBy(how = How.ID, using = "comment")
	private WebElement comment;
	
	@FindBy(how = How.ID, using = "cvv")
	private WebElement cvv;
	
	@FindBy(how = How.XPATH, using = "//button[.='Continue']")
	private WebElement continueButton;
	
	@FindBy(how = How.ID, using = "nameOnCard")
	private WebElement nameOnCard;
	
	@FindBy(how = How.ID, using = "cardNumber")
	private WebElement cardNumber;
	
	@FindBy(how = How.ID, using = "expiration")
	private WebElement expiration;
	
	@FindBy(how = How.ID, using = "creditCardCVV")
	private WebElement creditCardCVV;
	
	@FindBy(how = How.ID, using = "bill_zipcode")
	private WebElement bill_zipcode;
	
	@FindBy(how = How.ID, using = "addOrEditCardSubmitButton")
	private WebElement submitNewCard;
	
	@FindBy(how = How.ID, using = "makepayment")
	private WebElement submitPayment;
	
	public JalapenoNewPayBillsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);	
	}
	
	private void fillNewCardInformation(String name, String number, String exp, String cvv, String zipCode) {
		log("Filling info about new credit card");
		log("Name on card: " + name);
		nameOnCard.sendKeys(name);
		
		log("Card number: " + number);
		cardNumber.sendKeys(number);
		
		log("Expiration: " + exp);
		expiration.sendKeys(exp);
		
		log("CVV: " + cvv);
		creditCardCVV.sendKeys(cvv);
		
		log("ZipCode: " + zipCode);
		bill_zipcode.sendKeys(zipCode);
		
		submitNewCard.click();
	}
	
	public JalapenoNewPayBillsPage fillPaymentInfo(String amount, String accNumber) {
		String name = "TestPatient CreditCard";
		String number = "4111111111111111";
		String exp = "1020";
		String cvvCode = "001";
		String zipCode = "12345";
		String phoneNumber = "1234567890";
		String commentString = "This is testing payment.";
		
		log("Insert Payment amount: " + amount);
		paymentAmount.sendKeys(amount);
		
		log("Click on Add New Card");
		addNewCardButton.click();
		fillNewCardInformation(name, number, exp, cvvCode, zipCode);
		
		if(driver.findElement(By.xpath("//img[@title='visa']")).isDisplayed()) {
			log("New credit card was added and displayed");
		}
		else {
			log("Unreproducable bug is found");
			return null;
		}
				
		log("Insert account number: " + accNumber);
		accountNumber.sendKeys(accNumber);

		log("Insert phone number: " + phoneNumber);
		phone1.sendKeys(phoneNumber.substring(0, 3));
		phone2.sendKeys(phoneNumber.substring(3, 6));
		phone3.sendKeys(phoneNumber.substring(6, 10));
			
		log("Insert comment: " + commentString);
		comment.sendKeys(commentString);
		
		log("Insert CVV code: " + cvvCode);
		cvv.sendKeys(cvvCode);
		
		log("Click on Continue button");
		continueButton.click();
		
		return this;
	}
	
	public JalapenoHomePage submitPayment() {
		log("Click on Submit Payment button");
		submitPayment.click();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}
	
	//modified assess to see if it will work without waitForElement and moved allElementsDisplayed=true at the end
	public boolean assessNewPayBillsElements() {

		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
	
		webElementsList.add(paymentAmount);
		webElementsList.add(payHistoryButton);
		webElementsList.add(addNewCardButton);
		webElementsList.add(accountNumber);
		webElementsList.add(phone1);
		webElementsList.add(phone2);
		webElementsList.add(phone3);
		webElementsList.add(comment);
		webElementsList.add(continueButton);

		return new IHGUtil(driver).assessAllPageElements(webElementsList, this.getClass());
	}
}
