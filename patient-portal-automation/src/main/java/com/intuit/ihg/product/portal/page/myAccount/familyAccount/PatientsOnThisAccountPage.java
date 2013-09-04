package com.intuit.ihg.product.portal.page.myAccount.familyAccount;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.portal.utils.PortalUtil;

/**
 * 
 * @author bkrishnankutty
 *
 */
public class PatientsOnThisAccountPage extends BasePageObject{
	
	
	@FindBy(xpath = ".//*[@id='iframecontent']/div/div[3]/a")
	private WebElement lnkAddaFamilyMember ;
	
	
	@FindBy(xpath = "//a[contains(@href, 'unlink::ILinkListener::')]")
	private WebElement lnkUnlinkDependent ;
	
	@FindBy(css = ".firefinder-match")
	private WebElement textName;
	
	@FindBy(css = ".feedbackPanelINFO>span")
	private WebElement textunlinkedDependentMessage;
	
	@FindBy(css="a[href*='exit.cfm']")
	private WebElement logout;
	
	public PatientsOnThisAccountPage(WebDriver driver)
	{
		super(driver);
	}
	
	public void waitforElement(WebDriver driver,int n)
	{   IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver,n, lnkAddaFamilyMember);
	}
	
	public CreatefamilymemberPage clickAddaFamilyMemberLink() throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		IHGUtil.waitForElement(driver, 10, lnkAddaFamilyMember);
		try
		{
			this.clickUnlinkDependentLink();
			lnkAddaFamilyMember.click();
		}
		catch(Exception ex)
		{
		  lnkAddaFamilyMember.click();
		}
		
		return PageFactory.initElements(driver,CreatefamilymemberPage.class);
	}
	
	public void verifyName(String FirstName,String LastName) {
		IHGUtil.PrintMethodName();
		String Name =FirstName+" "+LastName;
		Assert.assertEquals(textName.getText().trim(), Name);
		
	}
	
	public void verifyunlinkedDependentMessage(String FirstName,String LastName) {
		IHGUtil.PrintMethodName();
		String Name =FirstName+" "+LastName+" has been unlinked from your account";
		Assert.assertEquals(textunlinkedDependentMessage.getText(), Name);
		}
	
	public void logout(WebDriver driver) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		PortalUtil pPortalUtil=new PortalUtil(driver);
		pPortalUtil.clickLogout(driver, logout);
		}
	
	
	public UnlinkPatientFromFamilyPage clickUnlinkDependentLink() throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		waitforElement(driver, 60);
		lnkUnlinkDependent.click();
		driver.switchTo().alert().accept();
		return PageFactory.initElements(driver,UnlinkPatientFromFamilyPage.class);
	}
	
	
	
}
