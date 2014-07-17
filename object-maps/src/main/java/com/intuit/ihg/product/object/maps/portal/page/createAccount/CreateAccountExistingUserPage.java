package com.intuit.ihg.product.object.maps.portal.page.createAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.MyPatientPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class CreateAccountExistingUserPage extends BasePageObject {

	@FindBy(xpath = "//input[@fieldid='username']")
	private WebElement txtUserId;
	
	@FindBy(xpath = "//input[@fieldid='password']")
	private WebElement txtUserPassword;

	@FindBy(name = "buttons:submit")
	private WebElement btnContinue;
	
	@FindBy(xpath = "//div[@id='workflowMessage']/p/span[contains(.,'The information you entered matches an existing account.')]")
	private WebElement titleMessage;

	public CreateAccountExistingUserPage(WebDriver driver) {
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
	
	
	
}
