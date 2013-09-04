package com.intuit.ihg.product.portal.page.myAccount.familyAccount;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class UnlinkPatientFromFamilyPage extends BasePageObject{


	@FindBy(how=How.NAME, using="inputs:1:input:input")
	private WebElement txtEmail ;
	
	@FindBy(how = How.NAME, using = "buttons:submit")
	private WebElement btnUnLink;
	
		
	public UnlinkPatientFromFamilyPage(WebDriver driver)
	{
		super(driver);
	}
	
	public void waitfortxtEmail(WebDriver driver,int n)
	{   IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,n, txtEmail);
	}
	
	 
	
	public PatientsOnThisAccountPage unLinkFamilyMember(String email) {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitfortxtEmail(driver, 60);
		txtEmail.sendKeys(email);
		btnUnLink.click();
		return PageFactory.initElements(driver,PatientsOnThisAccountPage.class);
	}
	
}
