package com.medfusion.product.object.maps.practice.page.rxrenewal;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;

public class RxRenewalDetailPageConfirmation extends BasePageObject{

	@FindBy(xpath="//input[@name='confirmAction' and @value='3']")
	private WebElement radioCallInTheRx;
	
	@FindBy(xpath="//input[@name='confirmAction' and @value='2']")
	private WebElement radioSendToCallInQueue;
	
	@FindBy(xpath="//input[@name='confirmAction' and @value='7']")
	private WebElement radioPrintRx;
	
	@FindBy(xpath="//input[@name='submit:submit']")
	private WebElement btnContinue;
	
	@FindBy(xpath="//input[@name='submit:cancel']")
	private WebElement btnCancel;

	public RxRenewalDetailPageConfirmation(WebDriver driver) {
		super(driver);
		
	}
	
public void clickCallInTheRx() {
		
		radioCallInTheRx.click();
	}
	
public RxRenewalConfirmCommunication clickContinue() {
		
		btnContinue.click();
		
		return PageFactory.initElements(driver, RxRenewalConfirmCommunication.class);
	}
	
}