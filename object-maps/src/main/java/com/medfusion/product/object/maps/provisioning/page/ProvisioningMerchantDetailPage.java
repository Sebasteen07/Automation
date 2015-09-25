package com.medfusion.product.object.maps.provisioning.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class ProvisioningMerchantDetailPage extends BasePageObject {

	public ProvisioningMerchantDetailPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	//Controls
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
	
	//Detail buttons
	@FindBy(how = How.XPATH, using="(//a[@class='btn btn-primary ng-binding'])[1]")
	public WebElement merchantInfoButton;
	@FindBy(how = How.XPATH, using="(//a[@class='btn btn-primary ng-binding'])[2]")
	public WebElement accountsIdsButton;
	@FindBy(how = How.XPATH, using="(//a[@class='btn btn-primary ng-binding'])[3]")
	public WebElement statementOptionsButton;
	@FindBy(how = How.XPATH, using="(//a[@class='btn btn-primary ng-binding'])[4]")
	public WebElement usersRolesButton;
	@FindBy(how = How.XPATH, using="(//a[@class='btn btn-primary ng-binding'])[5]")
	public WebElement ratesContractInfoButton;
	

	
	
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
	public WebElement country;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[1]/fieldset/div[6]/div[2]")
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
	public WebElement remitCountry;	
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset/div[2]/div[2]/fieldset/div[7]/div[2]")
	public WebElement remitState;
	
	//Users and Roles
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset[4]/div[2]/div/div/a")
	public WebElement buttonUsersAndRoles;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset[4]/div[1]")
	public WebElement valueUserId2;
	@FindBy(how = How.XPATH, using="//div[@id='merchantDetail']/fieldset[4]/div[1]/div/span")
	public WebElement valueRoles2;
	
	public boolean verifyExistenceOfUser (String userId){
		return valueUserId2.getText().equals(userId + " - Void/Refund");
	}
	
	public boolean verifyNonexistenceOfUser (){
		return valueRoles2.getText().equals("No roles have been added.");
	}
	
	//Cards
	@FindBy(how = How.XPATH, using="//img[@title='American Express']")
	public WebElement amexImg;
	@FindBy(how = How.XPATH, using="//img[@title='Visa']")
	public WebElement visaImg;
	@FindBy(how = How.XPATH, using="//img[@title='Discover']")
	public WebElement discoverImg;
	@FindBy(how = How.XPATH, using="//img[@title='Care Credit']")
	public WebElement carecredImg;
	
	
	
	
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
	public boolean verifyInfoWithoutMid(String externalId, String merchantName, 
			String vantivLitleId, String elementId, String address1, String address2, 
			String zipcode, String country, String state, String remitMerchantName, 
			String remitAddress1, String remitAddress2, String remitCity, String remitZipcode, 
			String remitCountry, String remitState){
		IHGUtil.PrintMethodName();		
		if (externalId.equals(this.externalId.getText().trim()) 
				&& merchantName.equals(this.merchantName.getText().trim()) 
				&& vantivLitleId.equals(this.vantivLitleId.getText().trim())
				&& elementId.equals(this.elementId.getText().trim())
				&& address1.equals(this.address1.getText().trim())
				&& address2.equals(this.address2.getText().trim())
				&& zipcode.equals(this.zipcode.getText().trim())
				&& country.equals(this.country.getText().trim())
				&& state.equals(this.state.getText().trim())
				&& remitMerchantName.equals(this.remitMerchantName.getText().trim())
				&& remitAddress1.equals(this.remitAddress1.getText().trim())
				&& remitAddress2.equals(this.remitAddress2.getText().trim())
				&& remitCity.equals(this.remitCity.getText().trim())
				&& remitZipcode.equals(this.remitZipcode.getText().trim())
				&& remitCountry.equals(this.remitCountry.getText().trim())
				&& remitState.equals(this.remitState.getText().trim())				
				)
			 return true;
		else return false;	
	}
	
	public void waitTillLoaded() {		
		log("Loading, waiting for merchant ID element");
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@id='merchantDetail']/fieldset/div[1]/div[1]/div[1]/div[2]"))));		
	}
	
	public boolean isVisaOn(){
		try{
			return visaImg.isDisplayed();			
		}
		catch (Exception e){
			return false;
		}
	}
	public boolean isAmexOn(){
		try{
			return amexImg.isDisplayed();			
		}
		catch (Exception e){
			return false;
		}
	}
	public boolean isDiscoverOn(){
		try{
			return discoverImg.isDisplayed();			
		}
		catch (Exception e){
			return false;
		}
	}
	public boolean isCarecredOn(){
		try{
			return carecredImg.isDisplayed();			
		}
		catch (Exception e){
			return false;
		}
	}
	
	public boolean checkCards(boolean amex, boolean visa, boolean discover, boolean carecred){
		IHGUtil.PrintMethodName();
		if(amex) if(!isAmexOn()) return false;
		if(visa) if(!isVisaOn()) return false;
		if(discover) if(!isDiscoverOn()) return false;
		if(carecred) if(!isCarecredOn()) return false;
		return true;
	}
	
	public ProvisioningEditStatementOptionsPage clickStatementOptionsAddOrEdit(){
		IHGUtil.PrintMethodName();
		statementOptionsButton.click();		
		return PageFactory.initElements(driver, ProvisioningEditStatementOptionsPage.class);
	}
	
	public ProvisioningUsersRolesPage clickUsersRolesAddOrEdit(){
		IHGUtil.PrintMethodName();
		buttonUsersAndRoles.click();		
		return PageFactory.initElements(driver, ProvisioningUsersRolesPage.class);
	}

}
