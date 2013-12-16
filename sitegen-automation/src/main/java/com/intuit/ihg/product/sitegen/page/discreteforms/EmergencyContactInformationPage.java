package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class EmergencyContactInformationPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[2]/a/em")
	private WebElement lnkEmergencyContactInformation;
	
	@FindBy(name="contactfirstname")               
	private WebElement chckfirstName;
	
	@FindBy(name="contactlastname")               
	private WebElement chckLastname;
	
	@FindBy(name="relation")               
	private WebElement chckRelationToYou;
	
	@FindBy(name="contactprimaryphone")               
	private WebElement chckPrimaryPhone;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	public EmergencyContactInformationPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkEmergencyContactInformation);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	
	/**
	 * Click on link - Emergency Contact Information	
	 * @return
	 */
	
	public HealthInsuranceInformationPage clicklnkEmergencyContactInfo()
	{	
		
		//SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkEmergencyContactInformation);
		lnkEmergencyContactInformation.click();
		
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		// Close the browser window
		return PageFactory.initElements(driver,HealthInsuranceInformationPage.class);
	}
	
	
	

}