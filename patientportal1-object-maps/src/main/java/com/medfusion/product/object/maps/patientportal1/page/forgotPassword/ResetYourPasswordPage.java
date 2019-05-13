package com.medfusion.product.object.maps.patientportal1.page.forgotPassword;

import com.medfusion.portal.utils.PortalConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import static org.testng.Assert.*;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.Mailinator;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ResetYourPasswordPage extends BasePageObject {

	@FindBy(how = How.NAME, using = "inputs:0:input:input")
	private WebElement txtUserID;

	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement btnContinue;

	@FindBy(how = How.NAME, using = "inputs:1:input:input")
	private WebElement txtSecurityAnswer;

	@FindBy(how = How.NAME, using = "inputs:0:input:input")
	private WebElement txtPassword;

	@FindBy(how = How.NAME, using = "inputs:1:input:input")
	private WebElement txtConfirmPassword;

	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement btnSendMail;

	@FindBy(css = "#content > div > div.heading1")
	private WebElement verifyMessage;

	@FindBy(xpath = "//span[@class='feedbackPanelERROR']")
	private WebElement feedBackError;

	public ResetYourPasswordPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Answer all the security questions and ask send password into your gmail Back end Action:-Login your gmail and get the Password reset url Navigate to that
	 * URl
	 * 
	 * @param userId
	 * @param securityAnswer
	 * @param password
	 * @return
	 * @throws Exception
	 */

	public ActivatePasswordChangePage resetYourPasswordPage(String userId, String securityAnswer, String password) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		// type ur user name and submit
		txtUserID.sendKeys(userId);
		btnContinue.click();

		// answer all the security question
		IHGUtil.waitForElement(driver, 60, txtSecurityAnswer);
		txtSecurityAnswer.sendKeys(securityAnswer);
		btnContinue.click();

		IHGUtil.waitForElement(driver, 60, txtConfirmPassword);
		txtPassword.sendKeys(password);
		txtConfirmPassword.sendKeys(password);
		btnSendMail.click();

		String strurl = checkYourMailinator(userId);
		driver.navigate().to(strurl);
		return PageFactory.initElements(driver, ActivatePasswordChangePage.class);

	}

	private String checkYourMailinator(String email) throws Exception {
		Mailinator mail = new Mailinator();
		String[] mailAddress = email.split("@");
		String emailSubject = String.format(PortalConstants.EMAIL_ForgotPassword_SUBJECT.trim(), PortalConstants.PORTAL_TITLE.trim());
		String url = mail.catchNewMessage(mailAddress[0], emailSubject, PortalConstants.TextInForgotPasswordEmailLink, 20);
		assertTrue((url != null), "Reset password email not found.");
		return url;
	}


	public SecretAnswerDoesntMatchPage sendBadAnswerTwice(String userId, String securityAnswer) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);

		// type user name and submit
		txtUserID.sendKeys(userId);
		btnContinue.click();

		// wait for security answer text field
		IHGUtil.waitForElement(driver, 60, txtSecurityAnswer);

		txtSecurityAnswer.sendKeys(securityAnswer);
		javascriptClick(btnContinue);

		IHGUtil.waitForElement(driver, 60, feedBackError);
		assertTrue(feedBackError.getText().contains("Your answer to the secret question doesn't match our records. Please try again."));

		btnContinue.click();

		return PageFactory.initElements(driver, SecretAnswerDoesntMatchPage.class);



	}


}
