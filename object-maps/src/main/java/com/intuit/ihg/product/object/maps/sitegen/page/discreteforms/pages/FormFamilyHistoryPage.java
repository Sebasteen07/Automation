package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class FormFamilyHistoryPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='socialhistory_section']/a")
	private WebElement lnkSocialHistory;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;

	@FindBy(id = "hide_familymedicalhistory_section_check")
	private WebElement hideFamilymedicalhistorySectionCheck;	

	@FindBy(id = "alcoholism_familymedicalhistory")
	private WebElement alcoholismFamilymedicalhistory;	

	@FindBy(id = "aneurysm_familymedicalhistory")
	private WebElement aneurysmFamilymedicalhistory;	

	@FindBy(id = "arthritis_familymedicalhistory")
	private WebElement arthritisFamilymedicalhistory;	

	@FindBy(id = "asthmacopd_familymedicalhistory")
	private WebElement asthmacopdFamilymedicalhistory;	

	@FindBy(id = "bleedingclotting_familymedicalhistory")
	private WebElement bleedingclottingFamilymedicalhistory;	

	@FindBy(id = "breastcancer_familymedicalhistory")
	private WebElement breastcancerFamilymedicalhistory;	

	@FindBy(id = "othercancer_familymedicalhistory")
	private WebElement othercancerFamilymedicalhistory;	

	@FindBy(id = "coloncancer_familymedicalhistory")
	private WebElement coloncancerFamilymedicalhistory;	

	@FindBy(id = "depression_familymedicalhistory")
	private WebElement depressionFamilymedicalhistory;	

	@FindBy(id = "diabetes1_familymedicalhistory")
	private WebElement diabetes1Familymedicalhistory;	

	@FindBy(id = "diabetes2_familymedicalhistory")
	private WebElement diabetes2Familymedicalhistory;	

	@FindBy(id = "gallstones_familymedicalhistory")
	private WebElement gallstonesFamilymedicalhistory;	

	@FindBy(id = "geneticdisorders_familymedicalhistory")
	private WebElement geneticdisordersFamilymedicalhistory;	

	@FindBy(id = "glaucoma_familymedicalhistory")
	private WebElement glaucomaFamilymedicalhistory;	

	@FindBy(id = "heartdisease_familymedicalhistory")
	private WebElement heartdiseaseFamilymedicalhistory;	

	@FindBy(id = "highbloodpressure_familymedicalhistory")
	private WebElement highbloodpressureFamilymedicalhistory;	

	@FindBy(id = "highcholestrol_familymedicalhistory")
	private WebElement highcholestrolFamilymedicalhistory;	

	@FindBy(id = "kidney_familymedicalhistory")
	private WebElement kidneyFamilymedicalhistory;	

	@FindBy(id = "mentalillness_familymedicalhistory")
	private WebElement mentalillnessFamilymedicalhistory;	

	@FindBy(id = "seizure_familymedicalhistory")
	private WebElement seizureFamilymedicalhistory;	

	@FindBy(id = "stroke_familymedicalhistory")
	private WebElement strokeFamilymedicalhistory;	

	@FindBy(id = "suicide_familymedicalhistory")
	private WebElement suicideFamilymedicalhistory;	

	@FindBy(id = "thyroid_familymedicalhistory")
	private WebElement thyroidFamilymedicalhistory;	

	@FindBy(id = "tuberculosis_familymedicalhistory")
	private WebElement tuberculosisFamilymedicalhistory;	

	@FindBy(id = "familymedicalhistory_other_line")
	private WebElement familymedicalhistoryOther;	

	@FindBy(id = "familymedicalhistory_anythingelse_line")
	private WebElement familymedicalhistoryComments;
	
	
	public FormFamilyHistoryPage(WebDriver driver) 
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
	 * Click on link - Social History	
	 * @return
	 */
	
	public SocialHistoryPage clicklnkSocialHistory()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkSocialHistory);
		lnkSocialHistory.click();
		return PageFactory.initElements(driver,SocialHistoryPage.class);
	}
}



