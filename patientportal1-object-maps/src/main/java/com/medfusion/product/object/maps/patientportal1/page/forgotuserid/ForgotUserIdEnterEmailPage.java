package com.medfusion.product.object.maps.patientportal1.page.forgotuserid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ForgotUserIdEnterEmailPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Patient Portal Forgot User ID Enter Email Page";
	
	@FindBy(xpath=".//*[@fieldid='email']")
	private WebElement email;
	
	@FindBy(name="buttons:submit")
	private WebElement btnContinue;

	public ForgotUserIdEnterEmailPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Indicates whether the page is loaded by checking for a specific element on the screen.
	 * @return
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		boolean result = false;
		try {
			result = email.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * Initiates the process of retrieving the user id by entering the patient's email address.
	 * @param emailAddress patient's email address
	 * @return the next page in this workflow
	 */
	public ForgotUserIdSecretAnswerPage enterEmail(String emailAddress) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		email.sendKeys(emailAddress);
		btnContinue.click();
		
		return PageFactory.initElements(driver, ForgotUserIdSecretAnswerPage.class);
	}
	
	

}
