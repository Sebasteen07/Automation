package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SocialHistoryPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='socialhistory_section']/a")
	private WebElement lnkSocialHistory;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_socialhistory_section_check")
	private WebElement hideSocialhistorySectionCheck;	

	@FindBy(id = "culturalbelief_field")
	private WebElement culturalbeliefField;	

	@FindBy(id = "occupation_field")
	private WebElement occupationField;	

	@FindBy(id = "education_field")
	private WebElement educationField;	

	@FindBy(id = "smokealarms_personalsafety")
	private WebElement smokealarmsPersonalsafety;	

	@FindBy(id = "firearms_personalsafety")
	private WebElement firearmsPersonalsafety;	

	@FindBy(id = "seatbelt_personalsafety")
	private WebElement seatbeltPersonalsafety;	

	@FindBy(id = "recreationaldrugs_personalsafety")
	private WebElement recreationaldrugsPersonalsafety;	

	@FindBy(id = "violenceconcerns_personalsafety")
	private WebElement violenceconcernsPersonalsafety;	

	@FindBy(id = "traveledoutsidecountry_personalsafety")
	private WebElement traveledoutsidecountryPersonalsafety;	

	@FindBy(id = "livingwith_field")
	private WebElement livingwithField;	

	@FindBy(id = "exercise_healthhabits")
	private WebElement exerciseHealthhabits;	

	@FindBy(id = "alcohol_healthhabits")
	private WebElement alcoholHealthhabits;	

	@FindBy(id = "teacoffee_healthhabits")
	private WebElement teacoffeeHealthhabits;	

	@FindBy(id = "sodaenergydrinks_healthhabits")
	private WebElement sodaenergydrinksHealthhabits;	

	@FindBy(id = "cigarettes_healthhabits")
	private WebElement cigarettesHealthhabits;	

	@FindBy(id = "tobacco_healthhabits")
	private WebElement tobaccoHealthhabits;	

	@FindBy(id = "socialhistory_anythingelse_line")
	private WebElement socialhistoryComments;
	

	public SocialHistoryPage(WebDriver driver) 
	{
		super(driver);
		// TODO Auto-generated constructor stub
	}

	
	
	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() 
	{

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
	 * Click on save button and close the form editor
	 */
	
	public void clickSave() 
	{
		btnSave.click();
	}

}
