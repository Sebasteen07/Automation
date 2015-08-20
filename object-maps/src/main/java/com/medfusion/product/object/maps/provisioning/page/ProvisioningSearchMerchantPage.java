package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningSearchMerchantPage extends BasePageObject {

	public ProvisioningSearchMerchantPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using="//input[@id='merchantSearchField']")
	public WebElement searchInput;
	
	@FindBy(how = How.XPATH, using="//form[@id='merchantSearch']/div/button[@type='submit']")
	public WebElement submitButton;	
	
	public void searchForMerchant(String match){
		searchInput.sendKeys(match);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		submitButton.click();
	}
	
	public String getFirstResultMerchantID() {
		IHGUtil.PrintMethodName();
		String res = driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]")).getText();
		return res;
	}
	
	public String getFirstResultExternalID() {
		IHGUtil.PrintMethodName();
		String res = driver.findElement(By.xpath("//table/tbody/tr[1]/td[2]")).getText();
		return res;
	}
	
	public String getFirstResultMerchantName() {
		IHGUtil.PrintMethodName();
		String res = driver.findElement(By.xpath("//table/tbody/tr[1]/td[3]")).getText();
		return res;
	}
	
	public ProvisioningMerchantDetailPage clickFirstResultMerchantDetail() {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[4]/button")).click();
		return PageFactory.initElements(driver, ProvisioningMerchantDetailPage.class);
	}
	
	public boolean searchVerifyDetails(String match, String mmid, String externalId, String merchantName){
		IHGUtil.PrintMethodName();
		searchForMerchant(match);
		if (mmid.equals(getFirstResultMerchantID()) 
				&& externalId.equals(getFirstResultExternalID()) 
				&& merchantName.equals(getFirstResultMerchantName())) 
			 return true;				
		else return false;
		
	}
	
	
	
	
	
	
}
