//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.virtualCardSwiper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;

public class VirtualCardSwiperPage extends BasePageObject {

	public static final String title = "Virtual Card Swiper";

	@FindBy(xpath = "//*[@id='submenu']/ul/li[3]/a")
	private WebElement chargeHistory;

	@FindBy(xpath = ".//*[@id='pagetitle']/h1")
	private WebElement pageTitleEle;

	@FindBy(xpath = ".//input[@name='name']")
	private WebElement cardHolderName;

	@FindBy(xpath = ".//select[@name='cctype']")
	private WebElement creditCardType;

	@FindBy(xpath = ".//input[@name='ccnum']")
	private WebElement creditCardNum;

	@FindBy(xpath = ".//select[@name='expMonth']")
	private WebElement expiryMonthDropDwn;

	@FindBy(xpath = ".//select[@name='expYear']")
	private WebElement expiryYearDropDwn;

	@FindBy(xpath = ".//input[@name='amount']")
	private WebElement amountToChargeField;

	@FindBy(xpath = ".//input[@name='cvv']")
	private WebElement cvvField;

	@FindBy(xpath = ".//input[@name='zip']")
	private WebElement zipField;

	@FindBy(xpath = ".//input[@name='accountnum']")
	private WebElement patientaccountField;

	@FindBy(xpath = ".//input[@name='patientName']")
	private WebElement patientNameField;

	@FindBy(xpath = ".//input[@name='paymentComment']")
	private WebElement paymentCommentField;

	@FindBy(xpath = ".//input[@value = 'Click Here to Charge Card']")
	private WebElement clickHereToChargeCard;

	@FindBy(xpath = ".//span[@class='feedbackPanelINFO']")
	private WebElement paymentSuccessMsg;

	@FindBy(xpath = "//a[contains(text(), 'Charge History')]")
	private WebElement lnkChargeHistory;

	public VirtualCardSwiperPageChargeHistory lnkChargeHistoryclick(WebDriver driver) {
		IHGUtil.waitForElement(driver, 20, lnkChargeHistory);
		lnkChargeHistory.click();
		return PageFactory.initElements(driver, VirtualCardSwiperPageChargeHistory.class);
	}

	public VirtualCardSwiperPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public boolean checkVirtualCardSwiperPage() {
		IHGUtil.PrintMethodName();
		return pageTitleEle.getText().contains(title);
	}

	/**
	 * Use this one for single location practices (there won't be a selector
	 * 
	 * @param ccName
	 * @param ccNum
	 * @param cardType
	 * @param expMonth
	 * @param expYear
	 * @param amt
	 * @param cvv
	 * @param zip
	 * @param PAccount
	 * @param PName
	 * @param comment
	 * @throws Exception
	 */
	public void addCreditCardInfo(String ccName, String ccNum, String cardType, String expMonth, String expYear, String amt, String cvv, String zip,
			String PAccount, String PName, String comment) {
		IHGUtil.PrintMethodName();
		// Thread.sleep(4000);
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

		patientaccountField.sendKeys(PAccount);

		patientNameField.sendKeys(PName);

		try {
			paymentCommentField.sendKeys(comment);
		}
		catch (Exception e) {
			log(e.getMessage());
		}

		clickHereToChargeCard.click();
	}

	/**
	 * Use this one if there's a location selector
	 * 
	 * @param ccName
	 * @param ccNum
	 * @param cardType
	 * @param expMonth
	 * @param expYear
	 * @param amt
	 * @param cvv
	 * @param zip
	 * @param PAccount
	 * @param PName
	 * @param comment
	 * @param location
	 * @throws Exception
	 */
	public void addCreditCardInfo(String ccName, String ccNum, String cardType, String expMonth, String expYear, String amt, String cvv, String zip,
			String PAccount, String PName, String comment, String location) {
		IHGUtil.PrintMethodName();
		driver.switchTo().frame("iframe");
		WebElement locationDropDwn = driver.findElement(By.xpath(".//select[@name='locationSelected']"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Select selLoc = new Select(locationDropDwn);
		selLoc.selectByVisibleText(location);

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

		patientaccountField.sendKeys(PAccount);

		patientNameField.sendKeys(PName);

		paymentCommentField.sendKeys(comment);

		/*
		 * if((IHGUtil.getEnvironmentType().toString()== "DEV3") || (IHGUtil.getEnvironmentType().toString()== "QA1")) { paymentCommentField.sendKeys(comment); }
		 */

		clickHereToChargeCard.click();
	}

	public void addCreditCardMandatoryInfo(String ccName, String ccNum, String cardType, String expMonth, String expYear, String amt, String zip, String swipe, String PAccount,String PName) {
		IHGUtil.PrintMethodName();
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
		
		zipField.sendKeys(zip);
       
		patientaccountField.sendKeys(PAccount);

		patientNameField.sendKeys(PName);
		
		clickHereToChargeCard.click();
	}

	public String getPaymentCompletedSuccessMsg() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, paymentSuccessMsg);
		return paymentSuccessMsg.getText();
	}

	// "Payment completed"
}
