package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class IllnessesAndConditionsPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='familymedicalhistory_section']/a")
	private WebElement lnkFamilyHistory;
	
	@FindBy(id="save_config_form")              
	private WebElement btnSave;
	
	@FindBy(id = "hide_conditions_section_check")
	private WebElement hideConditionsSectionCheck;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[1]/span/input")
	private WebElement rheumatologicEntireGroup;	

	@FindBy(id = "arthritis_condition_rheumatologic")
	private WebElement arthritisConditionRheumatologic;	

	@FindBy(id = "gout_condition_rheumatologic")
	private WebElement goutConditionRheumatologic;	

	@FindBy(id = "osteoporosis_condition_rheumatologic")
	private WebElement osteoporosisConditionRheumatologic;	

	@FindBy(id = "lupus_condition_rheumatologic")
	private WebElement lupusConditionRheumatologic;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[2]/span/input")
	private WebElement neurologicalEntireGroup;	

	@FindBy(id = "alzheimers_condition_neurological")
	private WebElement alzheimersConditionNeurological;	

	@FindBy(id = "migraines_condition_neurological")
	private WebElement migrainesConditionNeurological;	

	@FindBy(id = "multiplesclerosis_condition_neurological")
	private WebElement multiplesclerosisConditionNeurological;	

	@FindBy(id = "parkinsons_condition_neurological")
	private WebElement parkinsonsConditionNeurological;	

	@FindBy(id = "stroke_condition_neurological")
	private WebElement strokeConditionNeurological;	

	@FindBy(id = "seizures_condition_neurological")
	private WebElement seizuresConditionNeurological;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[3]/span/input")
	private WebElement mentalHealthEntireGroup;	

	@FindBy(id = "adhd_condition_mental")
	private WebElement adhdConditionMental;	

	@FindBy(id = "bipolar_condition_mental")
	private WebElement bipolarConditionMental;	

	@FindBy(id = "depression_condition_mental")
	private WebElement depressionConditionMental;	

	@FindBy(id = "anxiety_condition_mental")
	private WebElement anxietyConditionMental;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[4]/span/input")
	private WebElement diabetesEntireGroup;	

	@FindBy(id = "diabetestype1_condition_thyroiddiabetes")
	private WebElement diabetestype1ConditionThyroiddiabetes;	

	@FindBy(id = "diabetestype2_condition_thyroiddiabetes")
	private WebElement diabetestype2ConditionThyroiddiabetes;	

	@FindBy(id = "goiter_condition_thyroiddiabetes")
	private WebElement goiterConditionThyroiddiabetes;	

	@FindBy(id = "graves_condition_thyroiddiabetes")
	private WebElement gravesConditionThyroiddiabetes;	

	@FindBy(id = "hashimoto_condition_thyroiddiabetes")
	private WebElement hashimotoConditionThyroiddiabetes;	

	@FindBy(id = "hypothyroidism_condition_thyroiddiabetes")
	private WebElement hypothyroidismConditionThyroiddiabetes;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[5]/span/input")
	private WebElement cancerEntireGroup;	

	@FindBy(id = "bladder_condition_cancer")
	private WebElement bladderConditionCancer;	

	@FindBy(id = "breast_condition_cancer")
	private WebElement breastConditionCancer;	

	@FindBy(id = "colorectal_condition_cancer")
	private WebElement colorectalConditionCancer;	

	@FindBy(id = "endometrial_condition_cancer")
	private WebElement endometrialConditionCancer;	

	@FindBy(id = "kidney_condition_cancer")
	private WebElement kidneyConditionCancer;	

	@FindBy(id = "leukemia_condition_cancer")
	private WebElement leukemiaConditionCancer;	

	@FindBy(id = "lung_condition_cancer")
	private WebElement lungConditionCancer;	

	@FindBy(id = "skin_condition_cancer")
	private WebElement skinConditionCancer;	

	@FindBy(id = "nonhodgkin_condition_cancer")
	private WebElement nonhodgkinConditionCancer;	

	@FindBy(id = "pancreatic_condition_cancer")
	private WebElement pancreaticConditionCancer;	

	@FindBy(id = "prostate_condition_cancer")
	private WebElement prostateConditionCancer;	

	@FindBy(id = "thyroid_condition_cancer")
	private WebElement thyroidConditionCancer;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[6]/span/input")
	private WebElement lungsEntireGroup;	

	@FindBy(id = "asthma_condition_lungs")
	private WebElement asthmaConditionLungs;	

	@FindBy(id = "copd_condition_lungs")
	private WebElement copdConditionLungs;	

	@FindBy(id = "emphysema_condition_lungs")
	private WebElement emphysemaConditionLungs;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[7]/span/input")
	private WebElement hematologicalEntireGroup;	

	@FindBy(id = "anemia_condition_hematological")
	private WebElement anemiaConditionHematological;	

	@FindBy(id = "bloodclottingdisorder_condition_hematological")
	private WebElement bloodclottingdisorderConditionHematological;	

	@FindBy(id = "sicklecellanemia_condition_hematological")
	private WebElement sicklecellanemiaConditionHematological;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[8]/span/input")
	private WebElement skinEntireGroup;	

	@FindBy(id = "eczema_condition_skin")
	private WebElement eczemaConditionSkin;	

	@FindBy(id = "psoriasis_condition_skin")
	private WebElement psoriasisConditionSkin;	

	@FindBy(id = "rosacea_condition_skin")
	private WebElement rosaceaConditionSkin;	

	@FindBy(id = "shingles_condition_skin")
	private WebElement shinglesConditionSkin;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[9]/span/input")
	private WebElement heartEntireGroup;	

	@FindBy(id = "arrythmias_condition_heart")
	private WebElement arrythmiasConditionHeart;	

	@FindBy(id = "heartattack_condition_heart")
	private WebElement heartattackConditionHeart;	

	@FindBy(id = "heartmurmur_condition_heart")
	private WebElement heartmurmurConditionHeart;	

	@FindBy(id = "highbloodpressure_condition_heart")
	private WebElement highbloodpressureConditionHeart;	

	@FindBy(id = "highcholestrol_condition_heart")
	private WebElement highcholestrolConditionHeart;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[10]/span/input")
	private WebElement gastrointestinalEntireGroup;	

	@FindBy(id = "crohnsdisease_condition_gastrointestinal")
	private WebElement crohnsdiseaseConditionGastrointestinal;	

	@FindBy(id = "colitis_condition_gastrointestinal")
	private WebElement colitisConditionGastrointestinal;	

	@FindBy(id = "gerd_condition_gastrointestinal")
	private WebElement gerdConditionGastrointestinal;	

	@FindBy(id = "heartburn_condition_gastrointestinal")
	private WebElement heartburnConditionGastrointestinal;	

	@FindBy(id = "ibs_condition_gastrointestinal")
	private WebElement ibsConditionGastrointestinal;	

	@FindBy(id = "stomachulcers_condition_gastrointestinal")
	private WebElement stomachulcersConditionGastrointestinal;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[11]/span/input")
	private WebElement liverEntireGroup;	

	@FindBy(id = "hepatitisa_condition_liver")
	private WebElement hepatitisaConditionLiver;	

	@FindBy(id = "hepatitisb_condition_liver")
	private WebElement hepatitisbConditionLiver;	

	@FindBy(id = "hepatitisc_condition_liver")
	private WebElement hepatitiscConditionLiver;	

	@FindBy(id = "cirrhosis_condition_liver")
	private WebElement cirrhosisConditionLiver;	

	@FindBy(id = "fattyliver_condition_liver")
	private WebElement fattyliverConditionLiver;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[12]/span/input")
	private WebElement kidneyEntireGroup;	

	@FindBy(id = "kidneystones_condition_kidneybladder")
	private WebElement kidneystonesConditionKidneybladder;	

	@FindBy(id = "kidneyinfection_condition_kidneybladder")
	private WebElement kidneyinfectionConditionKidneybladder;	

	@FindBy(id = "urinarytractinfection_condition_kidneybladder")
	private WebElement urinarytractinfectionConditionKidneybladder;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[13]/span/input")
	private WebElement otherEntireGroup;	

	@FindBy(id = "alcoholabuse_condition_other")
	private WebElement alcoholabuseConditionOther;	

	@FindBy(id = "brokenbones_condition_other")
	private WebElement brokenbonesConditionOther;	

	@FindBy(id = "chronicpain_condition_other")
	private WebElement chronicpainConditionOther;	

	@FindBy(id = "glaucoma_condition_other")
	private WebElement glaucomaConditionOther;	

	@FindBy(id = "hearingproblems_condition_other")
	private WebElement hearingproblemsConditionOther;	

	@FindBy(id = "hernia_condition_other")
	private WebElement herniaConditionOther;	

	@FindBy(id = "mononucleosis_condition_other")
	private WebElement mononucleosisConditionOther;	

	@FindBy(id = "readingproblems_condition_other")
	private WebElement readingproblemsConditionOther;	

	@FindBy(id = "sexuallytransmitteddiseases_condition_other")
	private WebElement sexuallytransmitteddiseasesConditionOther;	

	@FindBy(id = "tuberculosis_condition_other")
	private WebElement tuberculosisConditionOther;	

	@FindBy(id = "visionproblems_condition_other")
	private WebElement visionproblemsConditionOther;	

	@FindBy(xpath = "//*[@id='form_form']/div[2]/div[2]/div[12]/h5[14]/span/input")
	private WebElement femaleEntireGroup;	

	@FindBy(id = "howmanypregnancies_condition_femalespecific")
	private WebElement howmanypregnanciesConditionFemalespecific;	

	@FindBy(id = "miscarriages_condition_femalespecific")
	private WebElement miscarriagesConditionFemalespecific;	

	@FindBy(id = "vaginaldeliveries_condition_femalespecific")
	private WebElement vaginaldeliveriesConditionFemalespecific;	

	@FindBy(id = "caesareandeliveries_condition_femalespecific")
	private WebElement caesareandeliveriesConditionFemalespecific;	

	@FindBy(id = "firstpregnancyage_condition_femalespecific")
	private WebElement firstpregnancyageConditionFemalespecific;	

	@FindBy(id = "livingchildren_condition_femalespecific")
	private WebElement livingchildrenConditionFemalespecific;	

	@FindBy(id = "regularperiods_condition_femalespecific")
	private WebElement regularperiodsConditionFemalespecific;	

	@FindBy(id = "conditions_other_line")
	private WebElement conditionsOtherCheck;	

	@FindBy(id = "conditions_anythingelse_line")
	private WebElement conditionsCommentsCheck;
	

	public IllnessesAndConditionsPage(WebDriver driver)
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
			result = IHGUtil.waitForElement(driver, 6, lnkFamilyHistory);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}
	
	/**
	 * Click on link - Family Medical History
	 * @return
	 */
	
	public FormFamilyHistoryPage clicklnkFamilyHistory()
	{	
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, lnkFamilyHistory);
		lnkFamilyHistory.click();
		return PageFactory.initElements(driver,FormFamilyHistoryPage.class);
	}
}


