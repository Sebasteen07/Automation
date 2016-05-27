package com.medfusion.product.object.maps.patientportal1.page.createAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class CreateAccountHealthKeyPage extends BasePageObject {

	@FindBy(xpath = "//input[@fieldid='username']")
	private WebElement txtUserId;
	
	@FindBy(xpath = "//input[@fieldid='password']")
	private WebElement txtUserPassword;

	@FindBy(name = "buttons:submit")
	private WebElement btnContinue;
	
	@FindBy(xpath = "//div[@id='workflowMessage']/p/span[contains(.,'website that is a member of the Healthkey network')]")
	private WebElement titleMessage;

	@FindBy(name="phoneButton")
	private WebElement btnPhoneVerify;
	
	public CreateAccountHealthKeyPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * login to patient portal
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public MyPatientPage logIn(String username, String password) {

		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		
		log("PatientUserId: " + username);
		txtUserId.sendKeys(username);
		log("Password: " + password);
		txtUserPassword.sendKeys(password);
		btnContinue.click();

		return PageFactory.initElements(driver, MyPatientPage.class);
	}
	
	public boolean existingPageVerify(WebDriver driver) throws InterruptedException
	{
		PortalUtil.setPortalFrame(driver);
		return IHGUtil.waitForElement(driver,6, titleMessage);
	}
		
	public CreatePatientVerifyPhonePage clickVerifyPhone(WebDriver driver) {
		
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver,6, btnPhoneVerify);
		
		btnPhoneVerify.click();
		
		return PageFactory.initElements(driver, CreatePatientVerifyPhonePage.class);
	}
	
}
