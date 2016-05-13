package com.medfusion.product.object.maps.patientportal1.page.forgotPassword;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal1.page.MyPatientPage;
import com.medfusion.product.patientportal1.utils.PortalUtil;

public class ActivatePasswordChangePage extends BasePageObject{
	
	@FindBy(how=How.NAME,using="inputs:0:input:input")
	private WebElement txtUserName;
		
	@FindBy(how=How.NAME,using="inputs:1:input:input")
	private WebElement txtPassword;
	
	@FindBy(how=How.NAME,using="buttons:submit")
	private WebElement btnSignIn;
	
		
	public ActivatePasswordChangePage(WebDriver driver)
	{
		super(driver);
	}
	
   /**
    * Type the password and login to MyPatientPage
    * @param driver
    * @param userId
    * @param password
    * @return
    * @throws InterruptedException
    */
	
	public MyPatientPage activatePasswordChangePage(WebDriver driver,String userId,String password) throws InterruptedException
	{	
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		log("New password : " + password);
		IHGUtil.waitForElement(driver,6, btnSignIn);
		txtPassword.sendKeys(password);
	    btnSignIn.click();
		return PageFactory.initElements(driver, MyPatientPage.class);
	}

}
