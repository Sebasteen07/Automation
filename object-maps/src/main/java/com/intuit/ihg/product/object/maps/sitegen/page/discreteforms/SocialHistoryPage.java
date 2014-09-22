package com.intuit.ihg.product.object.maps.sitegen.page.discreteforms;

import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class SocialHistoryPage extends BasePageObject{
	
	@FindBy(xpath="//li[@data-section='socialhistory_section']/a")
	private WebElement lnkSocialHistory;
	
	@FindBy(id = "save_config_form")              
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
	
	@FindBy(xpath = "//div[@class='configuration_section socialhistory_section']/p[@class='custom']/a")
	private WebElement supplementalQuestion;
	
	@FindBy(xpath = "//li[@class='additional']/a")
	private WebElement newSection;

	@FindBy(xpath = "//div[@class='configuration_section socialhistory_section']/p/a")
	private WebElement newSectionButt;
	
	@FindBy(id = "custom_title_socialhistory_section_0")
	private WebElement customSectionName;
	
	@FindBy(linkText = "Insert An Item")
	private WebElement insertItemButton;
	
	@FindBy(id = "custom_questiontitle_socialhistory_section_0")
	private WebElement questionNameField;
	
	@FindBy(id = "custom_questiontype_socialhistory_section_0")
	private WebElement questionTypeSelect;
	
	@FindBy(id = "custom_questioncheckboxesvalues_socialhistory_section_0")
	private WebElement multiSelectAnswers;
	
	@FindBy(xpath = "//div[@class='notifications']/div[@class='error']/h3[contains(text(), 'Sorry, some required fields are missing.')]")
	private WebElement errorNotification;
	
	public static enum QuestionType {
		shortText, longText, multiSelect, singleSelect
	};
	
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
	 * Unselects the checkbox for hiding the page
	 * Adds a tea-drinking question so that the page does not hide again
	 */
	public void showThisPage() {
		if (hideSocialhistorySectionCheck.isSelected() == true) {
			hideSocialhistorySectionCheck.click();
			teacoffeeHealthhabits.click();
		}
			
	}
	
	/**
	 * Click on save button and close the form editor
	 */
	public void clickSave() {
		btnSave.click();
	}

	/**
	 * Click on button to add a section
	 */
	public void clickAddSection() {
		newSectionButt.click();
	}
	
	public void clickOnNewSection() {
		newSection.click();
	}
	
	/**
	 * Description: Sets the name of the section. Name is passe as parameter 
	 * @param newName: A name to be set
	 *
	 */ 
	 public void setSectionName(String newName) {
		 IHGUtil.PrintMethodName();
		 customSectionName.sendKeys(newName);
	 }

	 /**
      * Description: Sets the name of the section. Name is passe as parameter 
      * @param newName: A name to be set
      *
      */ 
     public void setQuestionName(String newName) {
    	 IHGUtil.PrintMethodName();
    	 questionNameField.sendKeys(newName);
     }
	
     public void setQuestionType(QuestionType type) throws Exception {
    	 Select questionTypeSel = new Select(questionTypeSelect);
    	 
    	 switch (type) {
    	 	case shortText:
    	 		questionTypeSel.selectByIndex(1);
    	 		break;
    	 	case longText:
    	 		questionTypeSel.selectByIndex(2);
    	 		break;
    	 	case multiSelect:
    	 		questionTypeSel.selectByIndex(3);
    	 		break;
    	 	case singleSelect:
    	 		questionTypeSel.selectByIndex(4);
    	 		break;
    	 	default:
    	 		throw new Exception();
    	 }
     }
     
     /**
      * Description: Enters possible answers to the text field. Answers are comma separated values 
      * @param newAnswers Answers as CSV string
      */
     public void setMultiSelectAnswers(String newAnswers) {
    	 multiSelectAnswers.sendKeys(newAnswers);
     }
     
     /**
      * Asserts that error notification message appeared. For example when user tried to save form with incomplete question
      */
     public void errorMessageAppearedTest() {
    	 assertTrue(errorNotification.isDisplayed());
     }
     
     public void clickInsertItemButton() {
    	 insertItemButton.click();
     }
}
