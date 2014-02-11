package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SocialHistoryPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[13]/a/em")
	private WebElement lnkSocialHistory;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_socialhistory_section_check")
	public WebElement hideSocialhistorySectionCheck;	

	@FindBy(id = "culturalbelief_field")
	public WebElement culturalbeliefField;	

	@FindBy(id = "occupation_field")
	public WebElement occupationField;	

	@FindBy(id = "education_field")
	public WebElement educationField;	

	@FindBy(id = "smokealarms_personalsafety")
	public WebElement smokealarmsPersonalsafety;	

	@FindBy(id = "firearms_personalsafety")
	public WebElement firearmsPersonalsafety;	

	@FindBy(id = "seatbelt_personalsafety")
	public WebElement seatbeltPersonalsafety;	

	@FindBy(id = "recreationaldrugs_personalsafety")
	public WebElement recreationaldrugsPersonalsafety;	

	@FindBy(id = "violenceconcerns_personalsafety")
	public WebElement violenceconcernsPersonalsafety;	

	@FindBy(id = "traveledoutsidecountry_personalsafety")
	public WebElement traveledoutsidecountryPersonalsafety;	

	@FindBy(id = "livingwith_field")
	public WebElement livingwithField;	

	@FindBy(id = "exercise_healthhabits")
	public WebElement exerciseHealthhabits;	

	@FindBy(id = "alcohol_healthhabits")
	public WebElement alcoholHealthhabits;	

	@FindBy(id = "teacoffee_healthhabits")
	public WebElement teacoffeeHealthhabits;	

	@FindBy(id = "sodaenergydrinks_healthhabits")
	public WebElement sodaenergydrinksHealthhabits;	

	@FindBy(id = "cigarettes_healthhabits")
	public WebElement cigarettesHealthhabits;	

	@FindBy(id = "tobacco_healthhabits")
	public WebElement tobaccoHealthhabits;	

	@FindBy(id = "socialhistory_anythingelse_line")
	public WebElement socialhistoryComments;
	

	public SocialHistoryPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkSocialHistory);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link -Social History	
	 * @return
	 */
	
	public SocialHistoryPage clicklnkSocialHistoryPage()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkSocialHistory);
		lnkSocialHistory.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		// Close the browser window
		return PageFactory.initElements(driver,SocialHistoryPage.class);
	}

}
