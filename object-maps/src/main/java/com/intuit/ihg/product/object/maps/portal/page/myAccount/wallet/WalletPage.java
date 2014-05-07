package com.intuit.ihg.product.object.maps.portal.page.myAccount.wallet;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.object.maps.portal.page.PortalLoginPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class WalletPage extends BasePageObject{
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:nameOnCreditCard")
	private WebElement txtCardholderName;
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:creditCardNumber")
	private WebElement txtCreditCardNumber;
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:creditCardType")
	private WebElement dropDownCreditCardType;
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:expirationMonth")
	private WebElement dropDownExpirationDate_Month;
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:expirationYear")
	private WebElement dropDownExpirationDate_Year;
	
	@FindBy(how= How.NAME,using ="ccpanel:newccdetails:addressZip")
	private WebElement txtZipCode;
	
	@FindBy(how= How.NAME,using ="buttonWrapper:_body:button")
	private WebElement btnAddCard;
	
	@FindBy(how= How.XPATH,using="//table[@class='recent']/tbody/tr[2]/td[6]/a[2]")
	private WebElement lnkDelete;
	
	@FindBy(how= How.CSS,using="span.feedbackPanelINFO")
	private WebElement MsgCreditcardDeleted;
	
	@FindBy(how= How.XPATH,using="//table[@class='recent']/tbody/tr[2]/td[2]")
	private WebElement verifyCreditCardType;
	
	@FindBy(how= How.XPATH,using="//table[@class='recent']/tbody/tr[2]/td[3]")
	private WebElement verifyCardholderName;
	
	@FindBy(how= How.XPATH,using="//table[@class='recent']/tbody/tr[2]/td[5]")
	private WebElement verifyExpDate;
	
	@FindBy(css="a[href*='exit.cfm']")
	private WebElement logout;
	
	@FindBy(how= How.NAME,using ="buttonWrapper:_body:button")
	private WebElement btnUpdate;
	
	
	
	public WalletPage(WebDriver driver)
	{
		super(driver);
	}
	
	public boolean lnkDeleteDisplayed() {
		       try{
		    	   lnkDelete.isDisplayed();
		    	  // driver.findElement(By.xpath("//table[@class='recent']/tbody/tr[2]/td[6]/a[2]"));
                   return true; 
                }catch(org.openqa.selenium.NoSuchElementException ne){
                   	return false;
              }
			}
	
	
	public void removeCreditCard(String sCreditCardType) throws Exception {
		IHGUtil.PrintMethodName();
		boolean y,x;
		
		y=verifyCreditCardType.isDisplayed();
		System.out.println( "=========++yyyyyy+++"+y);
		x=lnkDeleteDisplayed();
		System.out.println( "=========++xxxxxx+++"+x);
		String temp=verifyCreditCardType.getText().trim();
		System.out.println( "=========++temp+++"+temp);
		if((temp.equalsIgnoreCase("Visa"))&&(lnkDeleteDisplayed()))
		{	
			lnkDelete.click();
			driver.switchTo().alert().accept();
			
			}
		else{
			log("no Credit card was added before++++ ");
		}
	}
	
	public void logout(WebDriver driver) throws InterruptedException, IOException {
		IHGUtil.PrintMethodName();
		PortalLoginPage portalLoginPage = new PortalLoginPage(driver);
		portalLoginPage.clickLogout(driver, logout);
		}
	
	
	public void verifyCreditCardDetails(String sCardholderName,String sCreditCardType, String smonth,String sYear) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		String sExpDate=smonth+"/"+sYear;
		Assert.assertEquals(verifyCardholderName.getText(),sCardholderName, "CardholderName is not same");
	    Assert.assertEquals(verifyCreditCardType.getText(),sCreditCardType, "CreditCardType is not same");
	    Assert.assertEquals(verifyExpDate.getText(),sExpDate, "Expiration Date  is not same");
	   }
	
	
	public void addCreditCardDetails(String sCardholderName,String sCreditCardType,String sCreditCardNumber,String smonth,String sYear,String sZipcode) throws Exception {
		IHGUtil.PrintMethodName();
		PortalUtil.setPortalFrame(driver);
		//IHGUtil.waitForElement(driver,60, btnAddCard);
		removeCreditCard(sCreditCardType);
		txtCardholderName.sendKeys(sCardholderName);
		txtCreditCardNumber.sendKeys(sCreditCardNumber);
		Select creditCardType=new Select(dropDownCreditCardType);
		creditCardType.selectByVisibleText(sCreditCardType);
		Select expirationDate_Month=new Select(dropDownExpirationDate_Month);
		expirationDate_Month.selectByVisibleText(smonth);
		Select expirationDate_Year=new Select(dropDownExpirationDate_Year);
		expirationDate_Year.selectByVisibleText(sYear);
		txtZipCode.sendKeys(sZipcode);
		btnAddCard.click();
		Thread.sleep(2000);
		}
	


}
