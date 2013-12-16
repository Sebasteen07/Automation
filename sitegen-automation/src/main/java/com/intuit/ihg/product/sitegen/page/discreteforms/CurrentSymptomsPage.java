package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class CurrentSymptomsPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[5]/a/em")
	private WebElement lnkCurrentSymptoms;
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div[2]/div[5]/h5/span[2]/input")               
	private WebElement chckGeneralHealth;
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div[2]/div[5]/h5[2]/span[2]/input")              
	private WebElement chckBlood;
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div[2]/div[5]/h5[3]/span[2]/input")               
	private WebElement chckEyesEarsNoseThroat;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;

	public CurrentSymptomsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setSiteGenFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 6, lnkCurrentSymptoms);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link -  Current Symptoms
	 * @return
	 * @throws InterruptedException 
	 */
	
	public MedicationsPage clicklnkCurrentSymptoms() throws InterruptedException
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkCurrentSymptoms);
		lnkCurrentSymptoms.click();
				
		log("Check General Health");
		chckGeneralHealth.click();
		
		Thread.sleep(2000);
		log("Check Blood");
		chckBlood.click();
		
		Thread.sleep(2000);
		log("Check Eyes, Ears,Nose and Throat");
		chckEyesEarsNoseThroat.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		// Close the browser window
		return PageFactory.initElements(driver,MedicationsPage.class);
	}
	
	}
