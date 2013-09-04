package com.intuit.ihg.product.community.page.BillPay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.BaseTestSoftAssert;import com.intuit.ifs.csscat.core.pageobject.BasePageObject;import com.intuit.ihg.common.utils.IHGUtil;import com.intuit.ihg.product.community.page.CommunityHomePage;import com.intuit.ihg.product.community.utils.CommunityConstants;import com.intuit.ihg.product.portal.utils.PortalConstants;import com.intuit.ihg.product.portal.utils.PortalUtil;

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
	public WebElement cardExpirationYear;		@FindBy(how = How.XPATH, using = "//button[@type='submit']")	private WebElement clickSubmitbtn ;
	
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
	}	public CommunityHomePage filloutPaymentDetails( String  sPayment)	{		IHGUtil.PrintMethodName();						accountNumber.clear();		accountNumber.sendKeys(CommunityConstants.PatientAccountNumber);				phoneNumber.clear();		phoneNumber.sendKeys("5555555555");				amount.clear();				amount.sendKeys(sPayment);				try		{			comment.clear();			comment.sendKeys(CommunityConstants.PaymentComment);		}		catch(Exception e)		{			log("Payment Comment Field is not Displayed");		}		creditCard.click();		cardName.sendKeys("Test Card");		cardNumber.sendKeys("4111111111111111");		cardCVV.sendKeys("111");		cardExpirationMonth.sendKeys("12");					cardExpirationYear.sendKeys("2022");		cardAddress1.sendKeys("2698 Marine Way");		cardZip.sendKeys("94043");		btnContinue.click();				IHGUtil.waitForElement(driver,10,clickSubmitbtn);		clickSubmitbtn.click();				return PageFactory.initElements(driver, CommunityHomePage.class);	}}

