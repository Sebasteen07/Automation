package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllergiesPage extends ConfiguratorFormPage {

	@FindBy(id = "save_config_form")
	private WebElement btnSave;

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[1]/span/input")
	private WebElement drugAllergiesEntireGroup;

	@FindBy(id = "idonot_drug_allergies")
	private WebElement idonotDrugAllergies;

	@FindBy(id = "generalanesthetic_allergy_drug")
	private WebElement generalanestheticAllergyDrug;

	@FindBy(id = "localanesthetic_allergy_drug")
	private WebElement localanestheticAllergyDrug;

	@FindBy(id = "carbamazepine_allergy_drug")
	private WebElement carbamazepineAllergyDrug;

	@FindBy(id = "codeine_allergy_drug")
	private WebElement codeineAllergyDrug;

	@FindBy(id = "insulin_allergy_drug")
	private WebElement insulinAllergyDrug;

	@FindBy(id = "iodine_allergy_drug")
	private WebElement iodineAllergyDrug;

	@FindBy(id = "nsaids_allergy_drug")
	private WebElement nsaidsAllergyDrug;

	@FindBy(id = "penicillin_allergy_drug")
	private WebElement penicillinAllergyDrug;

	@FindBy(id = "phenytoin_allergy_drug")
	private WebElement phenytoinAllergyDrug;

	@FindBy(id = "sulfa_allergy_drug")
	private WebElement sulfaAllergyDrug;

	@FindBy(id = "tetracycline_allergy_drug")
	private WebElement tetracyclineAllergyDrug;

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[2]/span/input")
	private WebElement foodAllergiesEntireGroup;

	@FindBy(id = "idonot_food_allergies")
	private WebElement idonotFoodAllergies;

	@FindBy(id = "peanuts_allergy_food")
	private WebElement peanutsAllergyFood;

	@FindBy(id = "eggs_allergy_food")
	private WebElement eggsAllergyFood;

	@FindBy(id = "seafood_allergy_food")
	private WebElement seafoodAllergyFood;

	@FindBy(id = "wheat_allergy_food")
	private WebElement wheatAllergyFood;

	@FindBy(id = "shellfish_allergy_food")
	private WebElement shellfishAllergyFood;

	@FindBy(id = "corn_allergy_food")
	private WebElement cornAllergyFood;

	@FindBy(id = "dairy_allergy_food")
	private WebElement dairyAllergyFood;

	@FindBy(id = "soy_allergy_food")
	private WebElement soyAllergyFood;

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[8]/h5[3]/span/input")
	private WebElement environmentAllergiesEntireGroup;

	@FindBy(id = "idonot_environmental_allergies")
	private WebElement idonotEnvironmentalAllergies;

	@FindBy(id = "animals_allergy_environmental")
	private WebElement animalsAllergyEnvironmental;

	@FindBy(id = "dustmites_allergy_environmental")
	private WebElement dustmitesAllergyEnvironmental;

	@FindBy(id = "latex_allergy_environmental")
	private WebElement latexAllergyEnvironmental;

	@FindBy(id = "bees_allergy_environmental")
	private WebElement beesAllergyEnvironmental;

	@FindBy(id = "mold_allergy_environmental")
	private WebElement moldAllergyEnvironmental;

	@FindBy(id = "wool_allergy_environmental")
	private WebElement woolAllergyEnvironmental;

	@FindBy(id = "hayfever_allergy_environmental")
	private WebElement hayfeverAllergyEnvironmental;

	@FindBy(id = "allergies_other")
	private WebElement allergiesOther;

	@FindBy(id = "allergies_anythingelse")
	private WebElement allergiesComments;

	public AllergiesPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
}
