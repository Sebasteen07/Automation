package com.intuit.ihg.product.object.maps.community.page.BillPay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.object.maps.community.page.CommunityHomePage;

public class BillPayPaymentDetailsPage extends BasePageObject {

	@FindBy(how = How.ID, using = "account")
	public WebElement accountNumber;

	@FindBy(how = How.ID, using = "phone")
	public WebElement phoneNumber;

	@FindBy(how = How.ID, using = "amount")
	public WebElement amount;
	
	@FindBy(how = How.ID, using = "comments")
	public WebElement comment;

	// /////////////////////////////////
	// Credit Card
	
	@FindBy(how = How.XPATH, using = "//input[@name='card_choice' and @value='new_card']")
	public WebElement creditCard;

	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	public WebElement btnContinue;

	@FindBy(how = How.ID, using = "full_name")
	public WebElement cardName;

	@FindBy(how = How.ID, using = "card_number")
	public WebElement cardNumber;


	@FindBy(how = How.ID, using = "cvv")
	public WebElement cardCVV;

	@FindBy(how = How.ID, using = "address1")
	public WebElement cardAddress1;

	@FindBy(how = How.ID, using = "zip")
	public WebElement cardZip;
	
	@FindBy(how = How.ID, using = "expiration_month")
	public WebElement cardExpirationMonth;
	
	@FindBy(how = How.ID, using = "expiration_year")
	public WebElement cardExpirationYear;
	
	public BillPayPaymentDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
		
		}
	
	public BillPayConfirmationPage SelectLocation(String sLocation) throws InterruptedException {	
		
		//Selecting Location
		driver.findElement(By.xpath("// * [contains(text(),'" + sLocation + "')]")).click();		
		log("DEBUG: URL After Selecting Location [" + driver.getCurrentUrl() + "]");
		return PageFactory.initElements(driver, BillPayConfirmationPage.class);
	}

	public void setComment(String sComment) {
		
		if (driver.findElements(By.id("comments") ).size() != 0) {
			comment.sendKeys(sComment);
		}
	}
