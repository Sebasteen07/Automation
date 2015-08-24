package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningDashboardPage extends BasePageObject{

	@FindBy(how = How.XPATH, using="//nav[@id='top-nav']//button[contains(@data-ng-click,'logout()')]")
	public WebElement logoutButton;
	
	@FindBy(how = How.XPATH, using="//nav[@id='menu']/ul/li[3]/a")
	public WebElement searchMerchantButton;
	
	@FindBy(how = How.XPATH, using="//nav[@id='menu']/ul/li[4]/a")
	public WebElement addMerchantButton;
	
	@FindBy(how = How.XPATH, using="//nav[@id='menu']/ul/li[6]/a")
	public WebElement searchUserButton;
	
	@FindBy(how = How.XPATH, using="//nav[@id='menu']/ul/li[7]/a")
	public WebElement addUserButton;
	
	public ProvisioningDashboardPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	// TODO Check Search, Check Table With Merchants Content when it is implemented 
	public boolean checkDashboardContent(String name) {
		IHGUtil.PrintMethodName();
		
		return isLoggedAs(name);
	}	
	
	public boolean isLoggedAs(String name) {
		IHGUtil.PrintMethodName();
		
		WebElement loggedAsText;
		try {
			String xpathExpression = "//nav[ @id = 'top-nav' ]//span[text() = ' Welcome " + name + " ' ]";
			loggedAsText = driver.findElement(By.xpath(xpathExpression));
		} catch(Exception e) {
			e.printStackTrace();
			log("ERROR: The name of logged user not found.");
			return false;
		}
		
		if (loggedAsText.isDisplayed()) {
			log("WebElement " + loggedAsText.toString() + " is displayed");
			return true;
		} else {
			log("WebElement " + loggedAsText.toString() + " is NOT displayed");
			return false;
		}
		
	}
	
	public ProvisioningLoginPage logout() {
		IHGUtil.PrintMethodName();
		logoutButton.click();
		return PageFactory.initElements(driver, ProvisioningLoginPage.class);
	}
	
	public ProvisioningAddMerchantPage clickAddMerchant(){
		addMerchantButton.click();
		return PageFactory.initElements(driver, ProvisioningAddMerchantPage.class);
	}
	public ProvisioningSearchMerchantPage clickSearchMerchant(){
		searchMerchantButton.click();
		return PageFactory.initElements(driver, ProvisioningSearchMerchantPage.class);
	}
}
