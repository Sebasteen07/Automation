package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class FamilyMedicalHistoryPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[12]/a/em")
	private WebElement lnkFamilyMedicalHistory;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;

	@FindBy(id = "hide_familymedicalhistory_section_check")
	public WebElement hideFamilymedicalhistorySectionCheck;	

	@FindBy(id = "alcoholism_familymedicalhistory")
	public WebElement alcoholismFamilymedicalhistory;	

	@FindBy(id = "aneurysm_familymedicalhistory")
	public WebElement aneurysmFamilymedicalhistory;	

	@FindBy(id = "arthritis_familymedicalhistory")
	public WebElement arthritisFamilymedicalhistory;	

	@FindBy(id = "asthmacopd_familymedicalhistory")
	public WebElement asthmacopdFamilymedicalhistory;	

	@FindBy(id = "bleedingclotting_familymedicalhistory")
	public WebElement bleedingclottingFamilymedicalhistory;	

	@FindBy(id = "breastcancer_familymedicalhistory")
	public WebElement breastcancerFamilymedicalhistory;	

	@FindBy(id = "othercancer_familymedicalhistory")
	public WebElement othercancerFamilymedicalhistory;	

	@FindBy(id = "coloncancer_familymedicalhistory")
	public WebElement coloncancerFamilymedicalhistory;	

	@FindBy(id = "depression_familymedicalhistory")
	public WebElement depressionFamilymedicalhistory;	

	@FindBy(id = "diabetes1_familymedicalhistory")
	public WebElement diabetes1Familymedicalhistory;	

	@FindBy(id = "diabetes2_familymedicalhistory")
	public WebElement diabetes2Familymedicalhistory;	

	@FindBy(id = "gallstones_familymedicalhistory")
	public WebElement gallstonesFamilymedicalhistory;	

	@FindBy(id = "geneticdisorders_familymedicalhistory")
	public WebElement geneticdisordersFamilymedicalhistory;	

	@FindBy(id = "glaucoma_familymedicalhistory")
	public WebElement glaucomaFamilymedicalhistory;	

	@FindBy(id = "heartdisease_familymedicalhistory")
	public WebElement heartdiseaseFamilymedicalhistory;	

	@FindBy(id = "highbloodpressure_familymedicalhistory")
	public WebElement highbloodpressureFamilymedicalhistory;	

	@FindBy(id = "highcholestrol_familymedicalhistory")
	public WebElement highcholestrolFamilymedicalhistory;	

	@FindBy(id = "kidney_familymedicalhistory")
	public WebElement kidneyFamilymedicalhistory;	

	@FindBy(id = "mentalillness_familymedicalhistory")
	public WebElement mentalillnessFamilymedicalhistory;	

	@FindBy(id = "seizure_familymedicalhistory")
	public WebElement seizureFamilymedicalhistory;	

	@FindBy(id = "stroke_familymedicalhistory")
	public WebElement strokeFamilymedicalhistory;	

	@FindBy(id = "suicide_familymedicalhistory")
	public WebElement suicideFamilymedicalhistory;	

	@FindBy(id = "thyroid_familymedicalhistory")
	public WebElement thyroidFamilymedicalhistory;	

	@FindBy(id = "tuberculosis_familymedicalhistory")
	public WebElement tuberculosisFamilymedicalhistory;	

	@FindBy(id = "familymedicalhistory_other_line")
	public WebElement familymedicalhistoryOther;	

	@FindBy(id = "familymedicalhistory_anythingelse_line")
	public WebElement familymedicalhistoryComments;
	
	
	public FamilyMedicalHistoryPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkFamilyMedicalHistory);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Family Medical History	
	 * @return
	 */
	
	public SocialHistoryPage clicklnkFamilyMedicalHistory()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkFamilyMedicalHistory);
		lnkFamilyMedicalHistory.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		// Close the browser window
		return PageFactory.initElements(driver,SocialHistoryPage.class);
	}
}



