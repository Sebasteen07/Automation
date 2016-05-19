package com.medfusion.product.object.maps.practice.page.onlinebillpay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;


public class OnlineBillPayDetailPage extends BasePageObject {

	@FindBy(xpath="//input[@name='voidPayment']")
	private WebElement btnVoidPayment;
	
	@FindBy(xpath="//input[@name='refundPayment']")
	private WebElement btnRefundPayment;
	
	@FindBy(xpath="//input[@name='secureComm:allowPatientReply']")
	private WebElement checkDoNotAllowPatientReply;
	
	@FindBy(xpath="//input[@name='secureComm:sendComm']")
	private WebElement btnSendCommunication;
	
	@FindBy(xpath="//input[@name='secureComm:subject']")
	private WebElement messageSubject;
	
	@FindBy(xpath="//textarea[@name='secureComm:body']")
	private WebElement messageBody;

	
	public OnlineBillPayDetailPage(WebDriver driver) {
		super(driver);
	}
	
	public void setFrame() {
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame("iframebody");
	}
	

	public OnlineBillPayVerifyPage CommunicateBillPay(String sSubject,String sMessage) throws Exception {
		
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);	

		messageSubject.sendKeys(sSubject);
		messageBody.sendKeys(sMessage);
		btnSendCommunication.click();
		
		
		
		Thread.sleep(10000);
		
		return PageFactory.initElements(driver, OnlineBillPayVerifyPage.class);
	}
}
