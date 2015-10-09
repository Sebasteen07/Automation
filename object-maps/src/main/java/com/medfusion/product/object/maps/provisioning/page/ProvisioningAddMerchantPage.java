package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
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
	@FindBy(how = How.ID, using="merchantName")
	public WebElement merchantName;
	@FindBy(how = How.ID, using="merchantLegalName")
	public WebElement merchantLegalName;
	@FindBy(how = How.ID, using="externalMerchantId")
	public WebElement externalId;
	@FindBy(how = How.ID, using="merchantPhone")
	public WebElement merchantPhone;
	@FindBy(how = How.ID, using="customerServicePhone")
	public WebElement merchantCustomerPhone;
	@FindBy(how = How.ID, using="txLimit")
	public WebElement transactionLimit;
		
	//Merchant Address
	@FindBy(how = How.ID, using="addressLine1")
	public WebElement address1;	
	@FindBy(how = How.ID, using="addressLine2")
	public WebElement address2;
	@FindBy(how = How.ID, using="city")
	public WebElement city;
	@FindBy(how = How.ID, using="zip")
	public WebElement zipcode;
	@FindBy(how = How.ID, using="country")
	public WebElement country;
	@FindBy(how = How.ID, using="state")
	public WebElement state;
	
	//Remit-to Address
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']//input[@name='enableRemitToAddress']")
	public WebElement remitToCheckbox;
	
	@FindBy(how = How.ID, using="remitMerchantName")
	public WebElement remitMerchantName;
	@FindBy(how = How.ID, using="remitAddressLine1")
	public WebElement remitAddress1;
	@FindBy(how = How.ID, using="remitAddressLine2")
	public WebElement remitAddress2;
	@FindBy(how = How.ID, using="remitCity")
	public WebElement remitCity;
	@FindBy(how = How.ID, using="remitZip")
	public WebElement remitZipcode;
	@FindBy(how = How.ID, using="remitCountry")
	public WebElement remitCountry;
	@FindBy(how = How.ID, using="remitState")
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
	
	// IBM Boarding Data
	@FindBy(how = How.ID, using="merchantStatus")
	public WebElement merchantStatus;
	@FindBy(how = How.ID, using="sicMccCode")
	public WebElement sicMccCode;
	@FindBy(how = How.ID, using="averageTicketPrice")
	public WebElement averageTicketPrice;
	@FindBy(how = How.ID, using="voucherFlag")
	public WebElement voucherFlag;
	
	
	@FindBy(how = How.XPATH, using="//form[@name='addMerchant']/fieldset[6]/div[1]/div[1]/button")
	public WebElement addButton;
	
	
	
	public ProvisioningMerchantDetailPage fillAndSubmit(String merchantName, String merchantLegalName,
			String externalId, String merchantPhone, String merchantCustomerPhone, String transactionLimit, String address1, 
			String address2, String city, String zip, String country, String state, String remitMerchantName, 
			String remitAddress1, String remitAddress2, String remitCity, 
			String remitZip, String remitCountry, String remitState,
			boolean amex, boolean visa, boolean discover, boolean mastercard, 
			boolean merchantStatus, String sicMccCode, String averageTicketPrice){
		this.merchantName.sendKeys(merchantName);
		this.merchantLegalName.sendKeys(merchantLegalName);
		this.externalId.sendKeys(externalId);
		this.merchantPhone.sendKeys(merchantPhone);
		this.merchantCustomerPhone.sendKeys(merchantCustomerPhone);
		this.transactionLimit.sendKeys(transactionLimit);
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
		//in future shouldn't be need
		this.remitCountry.sendKeys(Keys.ARROW_DOWN);
		this.remitState.sendKeys(Keys.ARROW_DOWN);
		this.remitState.sendKeys(Keys.ARROW_DOWN);
		tickCards(amex, visa, discover, mastercard);
		if (merchantStatus == false) this.merchantStatus.sendKeys(Keys.ARROW_DOWN);
		this.sicMccCode.sendKeys(sicMccCode);
		this.averageTicketPrice.sendKeys(averageTicketPrice);
		addButton.click();
		return PageFactory.initElements(driver, ProvisioningMerchantDetailPage.class);		
	}
	
	public void tickCards(boolean amex, boolean visa, boolean discover, boolean mastercard){
		if(amex) amexCheckbox.click();
		if(visa) visaCheckbox.click();
		if(discover) discoverCheckbox.click();
		if(mastercard) mastercardCheckbox.click();
	}
	
	
}

