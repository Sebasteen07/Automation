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
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li/a/em")
	private WebElement lnkBasicInformationAboutYou;

	@FindBy(id = "streetaddr1")
	private WebElement streetaddr1;	

	@FindBy(id = "city")
	private WebElement city;	

	@FindBy(id = "state")
	private WebElement state;	

	@FindBy(id = "postalcode")
	private WebElement postalcode;	

	@FindBy(id = "primaryphone")
	private WebElement primaryphone;	

	@FindBy(id = "altphone")
	private WebElement altphone;	

	@FindBy(id = "gender")
	private WebElement gender;	

	@FindBy(id = "maritalstatus")
	private WebElement maritalstatus;	

	@FindBy(id = "preferredcontact")
	private WebElement preferredcontact;	

	@FindBy(id = "language")
	private WebElement language;	

	@FindBy(id = "race")
	private WebElement race;	

	@FindBy(id = "ethnicity")
	private WebElement ethnicity;	

	@FindBy(id = "formeditor")
	private WebElement formeditor;	
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	
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
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		return PageFactory.initElements(driver,EmergencyContactInformationPage.class);
	}
	
	
	
	

}
