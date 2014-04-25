package com.intuit.ihg.product.portal.page.forgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static org.testng.Assert.*;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalConstants;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class ResetYourPasswordPage extends BasePageObject{
	
	@FindBy(how=How.NAME,using="inputs:0:input:input")
	private WebElement txtUserID;
	
	@FindBy(how=How.NAME,using="buttons:submit")
	private WebElement btnContinue;
	
	@FindBy(how=How.NAME,using="inputs:1:input:input")
	private WebElement txtSecurityAnswer;
	
	@FindBy(how=How.NAME,using="inputs:0:input:input")
	private WebElement txtPassword;
    
	@FindBy(how=How.NAME,using="inputs:1:input:input")
	private WebElement txtConfirmPassword;
	
	@FindBy(how=How.NAME,using="buttons:submit")
	private WebElement btnSendMail;
	
	@FindBy(css ="#content > div > div.heading1")
	private WebElement verifyMessage;
	
	@FindBy(xpath="//span[@class='feedbackPanelERROR']")
	private WebElement feedBackError;
			
	public ResetYourPasswordPage(WebDriver driver)
	{
		super(driver);
	}
	
  /**
   * Answer all the security questions and ask send password into your gmail
   * Back end Action:-Login your gmail and get the Password reset url
   * Navigate to that URl
   * 
   * @param userId
   * @param securityAnswer
   * @param password
   * @return
   * @throws Exception
   */
	
	public ActivatePasswordChangePage resetYourPasswordPage(String userId,String securityAnswer,String password) throws Exception
	{	
		IHGUtil.PrintMethodName();
	    PortalUtil.setPortalFrame(driver);
	    
	    //type ur user name and submit
	    txtUserID.sendKeys(userId);
		btnContinue.click();
		
		//answer all the security question
		IHGUtil.waitForElement(driver,60, txtSecurityAnswer);
		txtSecurityAnswer.sendKeys(securityAnswer);
		btnContinue.click();
		
		IHGUtil.waitForElement(driver,60, txtConfirmPassword);
		txtPassword.sendKeys(password);
		txtConfirmPassword.sendKeys(password);
		btnSendMail.click();
		
		//Validate Check your email 
		String strurl="";
		int count=1;
		do
		{
			Thread.sleep(2000);
			Assert.assertEquals(verifyMessage.getText().trim(),"Check your email", "The Text Check your email is missing");
			Thread.sleep(60000);
			
			//retrive the Url from ur gmail
			PortalUtil pPortalUtil=new PortalUtil(driver);
			String sSubject = String.format(PortalConstants.EMAIL_ForgotPassword_SUBJECT.trim(),PortalConstants.PORTAL_TITLE.trim() );
			String url = pPortalUtil.gmailVerification(userId,password,sSubject,PortalConstants.TextInForgotPasswordEmailLink);
			strurl=url;
			//Navigate to that URL
			if(strurl!="")
			{
				log("++++++URL++++++++"+ url);
				break;
			}
			else
			{
				count++;
			}
		}while(count<=20);	
		if(strurl=="")
		{
		log("+++++++++++++++URL+++++++++NOT FOUND AFTER 10 MIN");
		}
		driver.navigate().to(strurl);
		return PageFactory.initElements(driver,ActivatePasswordChangePage.class);
		
	}
	
	public SecretAnswerDoesntMatchPage sendBadAnswerTwice( String userId, String securityAnswer ) {
		IHGUtil.PrintMethodName();
	    PortalUtil.setPortalFrame(driver);
	    
	    //type  user name and submit
	    txtUserID.sendKeys(userId);
		btnContinue.click();
		
		//wait for security answer text field
		IHGUtil.waitForElement(driver, 60, txtSecurityAnswer);
		
		txtSecurityAnswer.sendKeys(securityAnswer);
		btnContinue.click();
		
		IHGUtil.waitForElement(driver, 60, feedBackError);		
		assertTrue(feedBackError.getText().contains("Your answer to the security question doesn't match our records. Please try again"));
		
		btnContinue.click();
		
		return PageFactory.initElements(driver,  SecretAnswerDoesntMatchPage.class);
		
		
	
	}
	

}
