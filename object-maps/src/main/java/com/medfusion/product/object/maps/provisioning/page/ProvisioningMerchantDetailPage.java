package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningMerchantDetailPage extends BasePageObject {

	public ProvisioningMerchantDetailPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	//General Merchant Info
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[1]/div[2]")
	public WebElement merchantId;	
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[2]/div[2]")
	public WebElement externalId;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[3]/div[2]")
	public WebElement merchantName;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[4]/div[2]")
	public WebElement vantivLitleId;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[5]/div[2]")
	public WebElement elementId;
	
	//Merchant Address
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[1]/div[2]")
	public WebElement address1;	
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[2]/div[2]")
	public WebElement address2;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[3]/div[2]")
	public WebElement city;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[4]/div[2]")
	public WebElement zipcode;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[5]/div[2]")
	public WebElement state;
	
	//Remit-to Address
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[1]/div[2]")
	public WebElement remitMerchantName;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[2]/div[2]")
	public WebElement remitAddress1;	
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[3]/div[2]")
	public WebElement remitAddress2;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[4]/div[2]")
	public WebElement remitCity;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[5]/div[2]")
	public WebElement remitZipcode;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[6]/div[2]")
	public WebElement remitState;	
	
	//Edit merchant
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[@class='row actions']/div[1]/a")
	public WebElement editMerchantButton;	
	
	public boolean verifyBasicInfo(String mmid, String externalId, String merchantName){
		IHGUtil.PrintMethodName();
		if (mmid.equals(merchantId.getText().trim()) 
				&& externalId.equals(this.externalId.getText().trim()) 
				&& merchantName.equals(this.merchantName.getText().trim())) 
			 return true;
		else return false;	
	}
	

}
