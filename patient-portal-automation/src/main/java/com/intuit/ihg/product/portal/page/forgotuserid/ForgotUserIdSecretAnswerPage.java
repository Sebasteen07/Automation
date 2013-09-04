package com.intuit.ihg.product.portal.page.forgotuserid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ForgotUserIdSecretAnswerPage extends BasePageObject {
	
	public static final String PAGE_NAME = "Patient Portal Forgot User ID Secret Answer Page";
	
	@FindBy(xpath=".//*[@fieldid='security.secretAnswer']")
	private WebElement securityAnswer;
	
	@FindBy(name="buttons:submit")
	private WebElement btnSendEmail;
	
	public ForgotUserIdSecretAnswerPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
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
			result = securityAnswer.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * Will answer the patient's security question with the supplied answer.
	 * @param answer
	 * @return step 3 in forgot user id process
	 */
	public ForgotUserIdConfirmationPage answerSecurityQuestion(String answer) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		securityAnswer.sendKeys(answer);
		btnSendEmail.click();
		return PageFactory.initElements(driver, ForgotUserIdConfirmationPage.class);
	}

}
