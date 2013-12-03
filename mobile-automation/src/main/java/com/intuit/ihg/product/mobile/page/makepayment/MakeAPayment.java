package com.intuit.ihg.product.mobile.page.makepayment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;

public class MakeAPayment extends MobileBasePage{
	
	@FindBy(xpath = "//div[@id='listCards']//a[contains(.,'Add new card')]")
	private WebElement lnkAddNewCard;
	
	@FindBy(id = "payAmount" )
	private WebElement txtPayAmount;
	
	@FindBy(id = "payAccount" )
	private WebElement txtpayAccount;
	
	@FindBy(id = "makePaymentSubmit")
	private WebElement btnSubmit;

public MakeAPayment(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

public NewCard clicklnkAddNewCard() throws InterruptedException{
	IHGUtil.PrintMethodName();
	IHGUtil.waitForElement(driver, 10, lnkAddNewCard);
	Thread.sleep(2000);
	lnkAddNewCard.click();
	return PageFactory.initElements(driver, NewCard.class);
}


public void setPaymentAmount(String amount)
{
	IHGUtil.PrintMethodName();
	txtPayAmount.clear();
	txtPayAmount.sendKeys(amount);
}

public void setAccount(String account)
{
	IHGUtil.PrintMethodName();
	txtpayAccount.clear();
	txtpayAccount.sendKeys(account);
}

public void clickbtnSubmit()
{
	IHGUtil.PrintMethodName();
	IHGUtil.waitForElement(driver, 10, btnSubmit);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	btnSubmit.click();
}

public PaymentConfirmationPage makePayment(String amount,String account)
{
	IHGUtil.PrintMethodName();
	setPaymentAmount(amount);
	setAccount(account);
	clickbtnSubmit();
	return PageFactory.initElements(driver, PaymentConfirmationPage.class);
}

/*makePayment.setPaymentAmount("10");
makePayment.setAccount("12345");
Payment has been sent
You will receive an email confirmation shortly. Your confirmation number is 298590.
Thank you
IHGQA Automation NonIntegrated */
}
