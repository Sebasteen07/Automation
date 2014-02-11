package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class IllnessesAndConditionsPage extends BasePageObject{
	
	@FindBy(xpath="//form[@id='form_form']/div[2]/div/ul/li[10]/a/em")
	private WebElement lnkIllnessesAndConditions;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_conditions_section_check")
	public WebElement hideConditionsSectionCheck;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[1]/span/input")
	public WebElement rheumatologicEntireGroup;	

	@FindBy(id = "arthritis_condition_rheumatologic")
	public WebElement arthritisConditionRheumatologic;	

	@FindBy(id = "gout_condition_rheumatologic")
	public WebElement goutConditionRheumatologic;	

	@FindBy(id = "osteoporosis_condition_rheumatologic")
	public WebElement osteoporosisConditionRheumatologic;	

	@FindBy(id = "lupus_condition_rheumatologic")
	public WebElement lupusConditionRheumatologic;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[2]/span/input")
	public WebElement neurologicalEntireGroup;	

	@FindBy(id = "alzheimers_condition_neurological")
	public WebElement alzheimersConditionNeurological;	

	@FindBy(id = "migraines_condition_neurological")
	public WebElement migrainesConditionNeurological;	

	@FindBy(id = "multiplesclerosis_condition_neurological")
	public WebElement multiplesclerosisConditionNeurological;	

	@FindBy(id = "parkinsons_condition_neurological")
	public WebElement parkinsonsConditionNeurological;	

	@FindBy(id = "stroke_condition_neurological")
	public WebElement strokeConditionNeurological;	

	@FindBy(id = "seizures_condition_neurological")
	public WebElement seizuresConditionNeurological;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[3]/span/input")
	public WebElement mentalHealthEntireGroup;	

	@FindBy(id = "adhd_condition_mental")
	public WebElement adhdConditionMental;	

	@FindBy(id = "bipolar_condition_mental")
	public WebElement bipolarConditionMental;	

	@FindBy(id = "depression_condition_mental")
	public WebElement depressionConditionMental;	

	@FindBy(id = "anxiety_condition_mental")
	public WebElement anxietyConditionMental;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[4]/span/input")
	public WebElement diabetesEntireGroup;	

	@FindBy(id = "diabetestype1_condition_thyroiddiabetes")
	public WebElement diabetestype1ConditionThyroiddiabetes;	

	@FindBy(id = "diabetestype2_condition_thyroiddiabetes")
	public WebElement diabetestype2ConditionThyroiddiabetes;	

	@FindBy(id = "goiter_condition_thyroiddiabetes")
	public WebElement goiterConditionThyroiddiabetes;	

	@FindBy(id = "graves_condition_thyroiddiabetes")
	public WebElement gravesConditionThyroiddiabetes;	

	@FindBy(id = "hashimoto_condition_thyroiddiabetes")
	public WebElement hashimotoConditionThyroiddiabetes;	

	@FindBy(id = "hypothyroidism_condition_thyroiddiabetes")
	public WebElement hypothyroidismConditionThyroiddiabetes;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[5]/span/input")
	public WebElement cancerEntireGroup;	

	@FindBy(id = "bladder_condition_cancer")
	public WebElement bladderConditionCancer;	

	@FindBy(id = "breast_condition_cancer")
	public WebElement breastConditionCancer;	

	@FindBy(id = "colorectal_condition_cancer")
	public WebElement colorectalConditionCancer;	

	@FindBy(id = "endometrial_condition_cancer")
	public WebElement endometrialConditionCancer;	

	@FindBy(id = "kidney_condition_cancer")
	public WebElement kidneyConditionCancer;	

	@FindBy(id = "leukemia_condition_cancer")
	public WebElement leukemiaConditionCancer;	

	@FindBy(id = "lung_condition_cancer")
	public WebElement lungConditionCancer;	

	@FindBy(id = "skin_condition_cancer")
	public WebElement skinConditionCancer;	

	@FindBy(id = "nonhodgkin_condition_cancer")
	public WebElement nonhodgkinConditionCancer;	

	@FindBy(id = "pancreatic_condition_cancer")
	public WebElement pancreaticConditionCancer;	

	@FindBy(id = "prostate_condition_cancer")
	public WebElement prostateConditionCancer;	

	@FindBy(id = "thyroid_condition_cancer")
	public WebElement thyroidConditionCancer;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[6]/span/input")
	public WebElement lungsEntireGroup;	

	@FindBy(id = "asthma_condition_lungs")
	public WebElement asthmaConditionLungs;	

	@FindBy(id = "copd_condition_lungs")
	public WebElement copdConditionLungs;	

	@FindBy(id = "emphysema_condition_lungs")
	public WebElement emphysemaConditionLungs;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[7]/span/input")
	public WebElement hematologicalEntireGroup;	

	@FindBy(id = "anemia_condition_hematological")
	public WebElement anemiaConditionHematological;	

	@FindBy(id = "bloodclottingdisorder_condition_hematological")
	public WebElement bloodclottingdisorderConditionHematological;	

	@FindBy(id = "sicklecellanemia_condition_hematological")
	public WebElement sicklecellanemiaConditionHematological;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[8]/span/input")
	public WebElement skinEntireGroup;	

	@FindBy(id = "eczema_condition_skin")
	public WebElement eczemaConditionSkin;	

	@FindBy(id = "psoriasis_condition_skin")
	public WebElement psoriasisConditionSkin;	

	@FindBy(id = "rosacea_condition_skin")
	public WebElement rosaceaConditionSkin;	

	@FindBy(id = "shingles_condition_skin")
	public WebElement shinglesConditionSkin;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[9]/span/input")
	public WebElement heartEntireGroup;	

	@FindBy(id = "arrythmias_condition_heart")
	public WebElement arrythmiasConditionHeart;	

	@FindBy(id = "heartattack_condition_heart")
	public WebElement heartattackConditionHeart;	

	@FindBy(id = "heartmurmur_condition_heart")
	public WebElement heartmurmurConditionHeart;	

	@FindBy(id = "highbloodpressure_condition_heart")
	public WebElement highbloodpressureConditionHeart;	

	@FindBy(id = "highcholestrol_condition_heart")
	public WebElement highcholestrolConditionHeart;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[10]/span/input")
	public WebElement gastrointestinalEntireGroup;	

	@FindBy(id = "crohnsdisease_condition_gastrointestinal")
	public WebElement crohnsdiseaseConditionGastrointestinal;	

	@FindBy(id = "colitis_condition_gastrointestinal")
	public WebElement colitisConditionGastrointestinal;	

	@FindBy(id = "gerd_condition_gastrointestinal")
	public WebElement gerdConditionGastrointestinal;	

	@FindBy(id = "heartburn_condition_gastrointestinal")
	public WebElement heartburnConditionGastrointestinal;	

	@FindBy(id = "ibs_condition_gastrointestinal")
	public WebElement ibsConditionGastrointestinal;	

	@FindBy(id = "stomachulcers_condition_gastrointestinal")
	public WebElement stomachulcersConditionGastrointestinal;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[11]/span/input")
	public WebElement liverEntireGroup;	

	@FindBy(id = "hepatitisa_condition_liver")
	public WebElement hepatitisaConditionLiver;	

	@FindBy(id = "hepatitisb_condition_liver")
	public WebElement hepatitisbConditionLiver;	

	@FindBy(id = "hepatitisc_condition_liver")
	public WebElement hepatitiscConditionLiver;	

	@FindBy(id = "cirrhosis_condition_liver")
	public WebElement cirrhosisConditionLiver;	

	@FindBy(id = "fattyliver_condition_liver")
	public WebElement fattyliverConditionLiver;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[12]/span/input")
	public WebElement kidneyEntireGroup;	

	@FindBy(id = "kidneystones_condition_kidneybladder")
	public WebElement kidneystonesConditionKidneybladder;	

	@FindBy(id = "kidneyinfection_condition_kidneybladder")
	public WebElement kidneyinfectionConditionKidneybladder;	

	@FindBy(id = "urinarytractinfection_condition_kidneybladder")
	public WebElement urinarytractinfectionConditionKidneybladder;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[13]/span/input")
	public WebElement otherEntireGroup;	

	@FindBy(id = "alcoholabuse_condition_other")
	public WebElement alcoholabuseConditionOther;	

	@FindBy(id = "brokenbones_condition_other")
	public WebElement brokenbonesConditionOther;	

	@FindBy(id = "chronicpain_condition_other")
	public WebElement chronicpainConditionOther;	

	@FindBy(id = "glaucoma_condition_other")
	public WebElement glaucomaConditionOther;	

	@FindBy(id = "hearingproblems_condition_other")
	public WebElement hearingproblemsConditionOther;	

	@FindBy(id = "hernia_condition_other")
	public WebElement herniaConditionOther;	

	@FindBy(id = "mononucleosis_condition_other")
	public WebElement mononucleosisConditionOther;	

	@FindBy(id = "readingproblems_condition_other")
	public WebElement readingproblemsConditionOther;	

	@FindBy(id = "sexuallytransmitteddiseases_condition_other")
	public WebElement sexuallytransmitteddiseasesConditionOther;	

	@FindBy(id = "tuberculosis_condition_other")
	public WebElement tuberculosisConditionOther;	

	@FindBy(id = "visionproblems_condition_other")
	public WebElement visionproblemsConditionOther;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[14]/span/input")
	public WebElement femaleEntireGroup;	

	@FindBy(id = "howmanypregnancies_condition_femalespecific")
	public WebElement howmanypregnanciesConditionFemalespecific;	

	@FindBy(id = "miscarriages_condition_femalespecific")
	public WebElement miscarriagesConditionFemalespecific;	

	@FindBy(id = "vaginaldeliveries_condition_femalespecific")
	public WebElement vaginaldeliveriesConditionFemalespecific;	

	@FindBy(id = "caesareandeliveries_condition_femalespecific")
	public WebElement caesareandeliveriesConditionFemalespecific;	

	@FindBy(id = "firstpregnancyage_condition_femalespecific")
	public WebElement firstpregnancyageConditionFemalespecific;	

	@FindBy(id = "livingchildren_condition_femalespecific")
	public WebElement livingchildrenConditionFemalespecific;	

	@FindBy(id = "regularperiods_condition_femalespecific")
	public WebElement regularperiodsConditionFemalespecific;	

	@FindBy(id = "conditions_other_line")
	public WebElement conditionsOtherCheck;	

	@FindBy(id = "conditions_anythingelse_line")
	public WebElement conditionsCommentsCheck;
	

	public IllnessesAndConditionsPage(WebDriver driver) {
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
			result = IHGUtil.waitForElement(driver, 6, lnkIllnessesAndConditions);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Illness and Conditions
	 * @return
	 */
	
	public FamilyMedicalHistoryPage clicklnkIllnessesAndConditions()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkIllnessesAndConditions);
		lnkIllnessesAndConditions.click();
		
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		
		// Close the browser window
		return PageFactory.initElements(driver,FamilyMedicalHistoryPage.class);
	}
}


