package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningAddMerchantPage extends BasePageObject {

	public ProvisioningAddMerchantPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}

	//General Merchant Info	
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[1]/div[2]/div[1]/input")
	public WebElement merchantName;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[1]/div[3]/div[1]/div[1]/div[1]/input")
	public WebElement externalId;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[1]/div[4]/div[1]/input")
	public WebElement vantivLitleId;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[1]/div[5]/div[1]/input")
	public WebElement elementId;
		
	//Merchant Address
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[1]/div[1]/input")
	public WebElement address1;	
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[2]/div[1]/input")
	public WebElement address2;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[3]/div[1]/input")
	public WebElement city;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[4]/div[1]/div[1]/div[1]/input")
	public WebElement zipcode;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[5]/div[1]/div[1]/div[1]/select")
	public WebElement country;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[2]/div[6]/div[1]/div[1]/div[1]/select")
	public WebElement state;
	
	//Remit-to Address
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/legend/input")
	public WebElement remitToCheckbox;
	
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[1]/div[1]/input")
	public WebElement remitMerchantName;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[2]/div[1]/input")
	public WebElement remitAddress1;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[3]/div[1]/input")
	public WebElement remitAddress2;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[4]/div[1]/input")
	public WebElement remitCity;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[5]/div[1]/div[1]/div[1]/input")
	public WebElement remitZipcode;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[6]/div[1]/div[1]/div[1]/select")
	public WebElement remitCountry;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[3]/div[1]/div[7]/div[1]/div[1]/div[1]/select")
	public WebElement remitState;
		
	// Cards
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[4]/div[1]/div[1]/label[1]/input")
	public WebElement visaCheckbox;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[4]/div[1]/div[1]/label[2]/input")
	public WebElement mastercardCheckbox;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[4]/div[1]/div[1]/label[3]/input")
	public WebElement amexCheckbox;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[4]/div[1]/div[1]/label[4]/input")
	public WebElement discoverCheckbox;
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[4]/div[1]/div[1]/label[5]/input")
	public WebElement carecredCheckbox;
	
	
	
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[5]/div[1]/div[1]/button")
	public WebElement addButton;
	
	
	
	public ProvisioningMerchantDetailPage fillAndSubmit(String merchantName, 
			String externalId, String vantivId, String elementId, String address1, 
			String address2, String city, String zip, String country, String state,
			String remitMerchantName, String remitAddress1, String remitAddress2,
			String remitCity, String remitZip, String remitCountry, String remitState,
			boolean amex, boolean visa, boolean discover, boolean carecred){
		this.merchantName.sendKeys(merchantName);
		this.externalId.sendKeys(externalId);
		vantivLitleId.sendKeys(vantivId);
		this.elementId.sendKeys(elementId);
		this.address1.sendKeys(address1);
		this.address2.sendKeys(address2);
		this.city.sendKeys(city);
		zipcode.sendKeys(zip);
		/*
		Select selCountry= new Select(this.country);
		selCountry.selectByValue(country);
		*/
		this.state.sendKeys(Keys.ARROW_DOWN);
		remitToCheckbox.click();
		this.remitMerchantName.sendKeys(remitMerchantName);
		this.remitAddress1.sendKeys(remitAddress1);
		this.remitAddress2.sendKeys(remitAddress2);
		this.remitCity.sendKeys(remitCity);
		remitZipcode.sendKeys(remitZip);	
		this.remitState.sendKeys(Keys.ARROW_DOWN);
		this.remitState.sendKeys(Keys.ARROW_DOWN);
		tickCards(amex, visa, discover, carecred);
		addButton.click();
		return PageFactory.initElements(driver, ProvisioningMerchantDetailPage.class);		
	}
	
	public void tickCards(boolean amex, boolean visa, boolean discover, boolean carecred){
		if(amex) amexCheckbox.click();
		if(visa) visaCheckbox.click();
		if(discover) discoverCheckbox.click();
		if(carecred) carecredCheckbox.click();
	}
	
	
}

