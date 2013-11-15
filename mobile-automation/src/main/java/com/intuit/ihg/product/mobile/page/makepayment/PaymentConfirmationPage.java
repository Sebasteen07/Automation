package com.intuit.ihg.product.mobile.page.makepayment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.mobile.page.MobileBasePage;
import com.intuit.ihg.product.mobile.page.MobileHomePage;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class PaymentConfirmationPage extends MobileBasePage{
	
	@FindBy(xpath = "//div[@id = 'makePaymentThanks']/div/div[2]/div/h1")
	private WebElement confirmPaymentTitle;
	
	 @FindBy(linkText="Close")
	 private WebElement closeBtn;

	public PaymentConfirmationPage(WebDriver driver) {
		super(driver);
		IHGUtil.waitForElement(driver, 20, closeBtn);
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		boolean result = false;
		try {
			result = closeBtn.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	
	public String getConfirmationTitle()throws InterruptedException
	{
		Thread.sleep(2000);
		IHGUtil.PrintMethodName();
		return confirmPaymentTitle.getText();
	}
	
    public MobileHomePage clickClose() throws InterruptedException {
        Thread.sleep(2000);
        waitForCloseBtn(driver, 20);
        IHGUtil.PrintMethodName();
        closeBtn.click();
        return PageFactory.initElements(driver, MobileHomePage.class);
    }
    
    public void waitForCloseBtn(WebDriver driver, int n) throws InterruptedException {
        IHGUtil.PrintMethodName();
        IHGUtil.waitForElement(driver, n, closeBtn);
    }

}
