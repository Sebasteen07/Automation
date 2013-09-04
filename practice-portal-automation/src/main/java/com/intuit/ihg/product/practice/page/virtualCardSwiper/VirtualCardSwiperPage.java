package com.intuit.ihg.product.practice.page.virtualCardSwiper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class VirtualCardSwiperPage extends BasePageObject {

	public static final String title = "Virtual Card Swiper";

	@FindBy( xpath = ".//*[@id='pagetitle']/h1" )
	private WebElement pageTitleEle;

	@FindBy( xpath = ".//input[@name='name']")
	private WebElement cardHolderName;

	@FindBy( xpath = ".//select[@name='cctype']")
	private WebElement creditCardType;

	@FindBy( xpath = ".//input[@name='ccnum']")
	private WebElement creditCardNum;

	@FindBy( xpath = ".//select[@name='expMonth']")
	private WebElement expiryMonthDropDwn;

	@FindBy( xpath = ".//select[@name='expYear']")
	private WebElement expiryYearDropDwn;

	@FindBy( xpath = ".//input[@name='amount']")
	private WebElement amountToChargeField;

	@FindBy( xpath = ".//input[@name='cvv']")
	private WebElement cvvField;

	@FindBy( xpath = ".//input[@name='zip']")
	private WebElement zipField;

	@FindBy( xpath = ".//input[@name='paymentComment']")
	private WebElement paymentCommentField;

	@FindBy( xpath = ".//input[@value = 'Click Here to Charge Card']")
	private WebElement clickHereToChargeCard;
	
	@FindBy( xpath = ".//span[@class='feedbackPanelINFO']")
	private WebElement paymentSuccessMsg;
	
	
	public VirtualCardSwiperPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkVirtualCardSwiperPage() {
		IHGUtil.PrintMethodName();
		return pageTitleEle.getText().contains(title);
	}

	public void addCreditCardInfo(String ccName, String ccNum, String cardType, String expMonth, String expYear, String amt, String cvv, String zip, String comment) throws Exception {
		IHGUtil.PrintMethodName();	
		Thread.sleep(4000);
		driver.switchTo().frame("iframe");
		cardHolderName.sendKeys(ccName);
		
		Select sel = new Select(creditCardType);
		sel.selectByVisibleText(cardType);
		
		creditCardNum.sendKeys(ccNum);
		
		Select selMonth = new Select(expiryMonthDropDwn);
		selMonth.selectByValue(expMonth);
		
		Select selYear = new Select(expiryYearDropDwn);
		selYear.selectByVisibleText(expYear);
		
		amountToChargeField.sendKeys(amt);
		cvvField.sendKeys(cvv);
		zipField.sendKeys(zip);
		if(IHGUtil.getEnvironmentType().toString()== "DEV3") {
			paymentCommentField.sendKeys(comment);
		}
			
		clickHereToChargeCard.click();
	}

	public String getPayementCompletedSuccessMsg() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, paymentSuccessMsg);
		return paymentSuccessMsg.getText();
	}
	
	//"Payment completed"
}
