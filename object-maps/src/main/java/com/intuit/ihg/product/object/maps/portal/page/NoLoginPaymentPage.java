package com.intuit.ihg.product.object.maps.portal.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.product.portal.utils.PortalUtil;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalConstants;


public class NoLoginPaymentPage extends BasePageObject{
	
	@FindBy(name="cardNameWrapper:_body:cardName")
	private WebElement nameOnCard;
	
	@FindBy(name="patientNameWrapper:_body:patientName")
	private WebElement patientName;
	
	@FindBy(name="birthdayWrapper:_body:birthday")
	private WebElement dateOfBirth;
	
	@FindBy(name="cardTypeWrapper:_body:cardType")
	private WebElement cardType;
	
	@FindBy(name="cardNumberWrapper:_body:cardNumber")
	private WebElement cardNumber;
	
	@FindBy(name="cardExpireWrapper:_body:cardExpire:month")
	private WebElement cardExpireMonth;
	
	@FindBy(name="cardExpireWrapper:_body:cardExpire:year")
	private WebElement cardExpireYear;
	
	@FindBy(name="amountWrapper:_body:amount")
	private WebElement amount;
	
	@FindBy(name="cvvCodeWrapper:_body:cvvCode")
	private WebElement cvvCode;
	
	@FindBy(name="cardZipWrapper:_body:cardZip")
	private WebElement cardZip;
	
	@FindBy(name="locationWrapper:_body:location")
	private WebElement location;
	
	@FindBy(name="commentWrapper:_body:comment")
	private WebElement paymentComment;
	
	@FindBy(name="emailAddrWrapper:_body:emailAddr")
	private WebElement emailField;
	
	@FindBy(name="buttons:submit")
	private WebElement btnsubmit;
	
	private String amountPrize;
	
	public NoLoginPaymentPage(WebDriver driver, String baseURL) {
		super(driver);
		IHGUtil.PrintMethodName();
		String sanitizedUrl = baseURL.trim();
		log("URL: " + sanitizedUrl);
		driver.get(sanitizedUrl.replace("home.login", "home.nologinpayment"));
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	

	public void FillNoLoginPaymentPage(String patientFirstName, String patientLastName, String patientZip, String email)
	{
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 10, nameOnCard);
		nameOnCard.sendKeys("Visa");
		patientName.sendKeys(patientFirstName + " " + patientLastName);
		dateOfBirth.sendKeys("01/11/1987");
		
		Select selectstate1 = new Select(cardType);
		selectstate1.selectByVisibleText(PortalConstants.CreditCardType);
		
		cardNumber.sendKeys(PortalConstants.CreditCardNumber);
		
		Select selectstate2 = new Select(cardExpireMonth);
		selectstate2.selectByVisibleText("1");
		Select selectstate3 = new Select(cardExpireYear);
		selectstate3.selectByVisibleText(PortalConstants.Year);
		
		amountPrize = IHGUtil.createRandomNumericString().substring(1, 4);
		
		amount.sendKeys(amountPrize);
		cvvCode.sendKeys("123");
		cardZip.sendKeys(patientZip);
		
		Select selectstate4 = new Select(location);
		selectstate4.selectByVisibleText("Automation-Location-1");
		
		if (driver.getPageSource().contains("Payment Comment"))
		{
		paymentComment.sendKeys("Payment");
		}
		emailField.sendKeys(email);
		btnsubmit.click();	

		
	}
	
	public String GetAmountPrize()
	{
		return amountPrize;
	}
}
