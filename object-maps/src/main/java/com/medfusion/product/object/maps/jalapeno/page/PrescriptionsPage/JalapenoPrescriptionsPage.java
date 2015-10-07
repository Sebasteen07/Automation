package com.medfusion.product.object.maps.jalapeno.page.PrescriptionsPage;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.object.maps.jalapeno.page.HomePage.JalapenoHomePage;

public class JalapenoPrescriptionsPage extends BasePageObject {
	
	@FindBy(how = How.XPATH, using="//input[@value=\'Continue\']")
	private WebElement continueButton;
	
	@FindBy(how = How.XPATH, using="//div[@id='medForm']/div[1]/div/div[2]/input")
	private WebElement medicationName;
	
	@FindBy(how = How.XPATH, using="//div[@id='medForm']/div[2]/div/div[2]/input")
	private WebElement dosage;
	
	@FindBy(how = How.XPATH, using="//div[@id='medForm']/div[3]/div/div[2]/input")
	private WebElement quantity;

	@FindBy(how = How.XPATH, using="//div[@class='new-pharmacy-container']/div[1]/div/div[2]/input")
	private WebElement pharmacyName;

	@FindBy(how = How.XPATH, using="//div[@class='new-pharmacy-container']/div[2]/div/div[2]/input")
	private WebElement pharmacyPhone;

	@FindBy(how = How.XPATH, using="//input[@value='Submit Request']")
	private WebElement submitButton;

	@FindBy(how = How.XPATH, using="//input[@type='radio']")
	private WebElement radioButton;
	
	@FindBy(how = How.LINK_TEXT, using="Home")
	private WebElement homeButton;
	
	@FindBy(how = How.XPATH, using="//select[@name='locationContainer:locationDD']")
	private WebElement locationDropdown;
	
	@FindBy(how = How.XPATH, using="//select[@name='providerContainer:providerDD']")
	private WebElement providerDropdown;
	
	public JalapenoPrescriptionsPage(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		driver.manage().window().maximize();
		PageFactory.initElements(driver, this);
	}
	
	public void clickContinueButton(WebDriver driver) {
		driver.switchTo().frame("iframebody");
				
		log("Checking if there're location options");
		if(IHGUtil.exists(driver, 2, locationDropdown)) {
			log("Selecting location");
			Select locationSelect = new Select(locationDropdown);
			locationSelect.selectByIndex(1);

			log("Selecting provider");
			try{
			Select providerSelect = new Select(providerDropdown);
			providerSelect.selectByIndex(1);
			} catch(StaleElementReferenceException ex) {
				log("Dont know what's going on here");
			}
			//continueButton.click();
		}
		
		log("Clicking on continue button");
		continueButton.click();
		
		driver.switchTo().defaultContent();
	}
	
	public JalapenoHomePage fillThePrescription(WebDriver driver, String medication, String dosage, int quantity) {
		
		driver.switchTo().frame("iframebody");
		
		log("Insert medication info");
		this.medicationName.sendKeys(medication);
		this.dosage.sendKeys(dosage);
		this.quantity.sendKeys(Integer.toString(quantity));
		
		log("Insert pharmacy information");
		pharmacyName.sendKeys("PharmacyName");
		pharmacyPhone.sendKeys("3216549870");
		radioButton.click();
		
		log("Click on Continue button");
		continueButton.click();
		
		log("Click on Submit button");
		submitButton.click();
		
		log("Return to Home Dashboard");
		homeButton.click();
		
		driver.switchTo().defaultContent();
		
		return PageFactory.initElements(driver, JalapenoHomePage.class);
	}

}
