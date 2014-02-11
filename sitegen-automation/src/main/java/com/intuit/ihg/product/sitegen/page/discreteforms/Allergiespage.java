package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class Allergiespage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[7]/a/em")
	private WebElement lnkAllergies;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[1]/span/input")
	public WebElement drugAllergiesEntireGroup;	

	@FindBy(id = "idonot_drug_allergies")
	public WebElement idonotDrugAllergies;

	@FindBy(id = "generalanesthetic_allergy_drug")
	public WebElement generalanestheticAllergyDrug;	

	@FindBy(id = "localanesthetic_allergy_drug")
	public WebElement localanestheticAllergyDrug;	

	@FindBy(id = "carbamazepine_allergy_drug")
	public WebElement carbamazepineAllergyDrug;	

	@FindBy(id = "codeine_allergy_drug")
	public WebElement codeineAllergyDrug;	

	@FindBy(id = "insulin_allergy_drug")
	public WebElement insulinAllergyDrug;	

	@FindBy(id = "iodine_allergy_drug")
	public WebElement iodineAllergyDrug;	

	@FindBy(id = "nsaids_allergy_drug")
	public WebElement nsaidsAllergyDrug;	

	@FindBy(id = "penicillin_allergy_drug")
	public WebElement penicillinAllergyDrug;	

	@FindBy(id = "phenytoin_allergy_drug")
	public WebElement phenytoinAllergyDrug;	

	@FindBy(id = "sulfa_allergy_drug")
	public WebElement sulfaAllergyDrug;	

	@FindBy(id = "tetracycline_allergy_drug")
	public WebElement tetracyclineAllergyDrug;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[2]/span/input")
	public WebElement foodAllergiesEntireGroup;	

	@FindBy(id = "idonot_food_allergies")
	public WebElement idonotFoodAllergies;	

	@FindBy(id = "peanuts_allergy_food")
	public WebElement peanutsAllergyFood;	

	@FindBy(id = "eggs_allergy_food")
	public WebElement eggsAllergyFood;	

	@FindBy(id = "seafood_allergy_food")
	public WebElement seafoodAllergyFood;	

	@FindBy(id = "wheat_allergy_food")
	public WebElement wheatAllergyFood;	

	@FindBy(id = "shellfish_allergy_food")
	public WebElement shellfishAllergyFood;	

	@FindBy(id = "corn_allergy_food")
	public WebElement cornAllergyFood;	

	@FindBy(id = "dairy_allergy_food")
	public WebElement dairyAllergyFood;	

	@FindBy(id = "soy_allergy_food")
	public WebElement soyAllergyFood;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[3]/span/input")
	public WebElement environmentAllergiesEntireGroup;	

	@FindBy(id = "idonot_environmental_allergies")
	public WebElement idonotEnvironmentalAllergies;	

	@FindBy(id = "animals_allergy_environmental")
	public WebElement animalsAllergyEnvironmental;	

	@FindBy(id = "dustmites_allergy_environmental")
	public WebElement dustmitesAllergyEnvironmental;	

	@FindBy(id = "latex_allergy_environmental")
	public WebElement latexAllergyEnvironmental;	

	@FindBy(id = "bees_allergy_environmental")
	public WebElement beesAllergyEnvironmental;	

	@FindBy(id = "mold_allergy_environmental")
	public WebElement moldAllergyEnvironmental;	

	@FindBy(id = "wool_allergy_environmental")
	public WebElement woolAllergyEnvironmental;	

	@FindBy(id = "hayfever_allergy_environmental")
	public WebElement hayfeverAllergyEnvironmental;	

	@FindBy(id = "allergies_other")
	public WebElement allergiesOther;	

	@FindBy(id = "allergies_anythingelse")
	public WebElement allergiesComments;

	public Allergiespage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkAllergies);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Allergies	
	 * @return
	 */
	
	public VaccinationsPage clicklnkAllergies()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkAllergies);
		lnkAllergies.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		// Close the browser window
		return PageFactory.initElements(driver,VaccinationsPage.class);
	}
}
