package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class BasicInformationAboutYouPage extends BasePageObject{

	public BasicInformationAboutYouPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//li[@data-section='demographics']/a")
	private WebElement lnkBasicInformationAboutYou;

	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id="streetaddr1")
	private WebElement chckAddress;
	
	@FindBy(id="city")
	private WebElement chckCity;
	
	@FindBy(id="state")
	private WebElement chckState;
	
	@FindBy(id="postalcode")
	private WebElement chckZIP;
	
	@FindBy(id="primaryphone")
	private WebElement chckPhoneNumber;
	
	@FindBy(id="gender")
	private WebElement chckGender; // sex
	
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
			result = IHGUtil.waitForElement(driver, 6, lnkBasicInformationAboutYou);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Basic Information About You
	 * @return
	 */
	
	public EmergencyContactInformationPage clicklnkBasicInfoAboutYourPage()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkBasicInformationAboutYou);
		lnkBasicInformationAboutYou.click();
		
		selectBasicInfo();
		btnSave.click();
		
		return PageFactory.initElements(driver,EmergencyContactInformationPage.class);
	}
	
	/**
	 *	Select basic info about the patient to appear
	 *	Address items, phone number, gender
	 */
	
	public void selectBasicInfo() {
		// wait for the element to load
		IHGUtil.waitForElement(driver, 30, chckAddress);
		
		chckAddress.click();
		chckCity.click();
		chckState.click();
		chckZIP.click();
		chckPhoneNumber.click();
		chckGender.click();
	}

}
