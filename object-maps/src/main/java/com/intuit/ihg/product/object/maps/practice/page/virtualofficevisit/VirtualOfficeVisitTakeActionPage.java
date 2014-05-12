package com.intuit.ihg.product.object.maps.practice.page.virtualofficevisit;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

/**
 * @author rperkinsjr
 *
 */
/**
 * @author rperkinsjr
 *
 */
public class VirtualOfficeVisitTakeActionPage extends BasePageObject {

	public static final String PAGE_NAME = "Virtual Office Visit Take Action Page";
	
	@FindBy(xpath=".//*[@id='CFForm_1']//b[text()='TAKE ACTION']")
	private WebElement takeActionText;
	
	// Find all action options
	@FindBy(name="fuseaction")
	private List<WebElement> actions;
	
	@FindBy(name="templateid")
	private WebElement treatmentPlans;
	
	@FindBy(name="search_now")
	private WebElement btnSubmitChoice;
	
	
	public VirtualOfficeVisitTakeActionPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Gives an indication if the page is loaded
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = takeActionText.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}

	
	/**
	 * Selects the Online visit and prescribe medication option, selects treatment plan (if supplied), and continues to next page
	 * @param treatmentPlanText
	 * @return Virtual Office Visit Online Visit and Medication page
	 * @throws Exception
	 */
	public VirtualOfficeVisitOnlineVisitAndMedicationPage takeActionOfOnlineVisitAndPrescribeMedication(String treatmentPlanText) throws Exception {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		chooseActionOption(VirtualOfficeVisitAction.ONLINE_VISIT_AND_PRESCRIBE_MEDICATION);
		chooseTreatmentPlan(treatmentPlanText);
		btnSubmitChoice.click();
		
		return PageFactory.initElements(driver, VirtualOfficeVisitOnlineVisitAndMedicationPage.class);
	}

	
	/**
	 * Selects the option on the page matching the supplied action option
	 * @param takeAction the action option, leverage the VisualOfficeVisitAction class
	 * @throws Exception
	 */
	private void chooseActionOption(String takeAction) throws Exception {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		boolean actionSelected = false;
		for (WebElement action : actions) {
			if (action.getAttribute("value").contains(takeAction)) {
				action.click();
				actionSelected = true;
			}
		}
		if (!actionSelected) throw new Exception("The no VOV action found with value attribute matching " + takeAction);
	}
	
	/**
	 * Will select the option in the select box where the parameter matches the visible text
	 * @param treatmentPlanText visible text in select box
	 */
	private void chooseTreatmentPlan(String treatmentPlanText) {
		IHGUtil.PrintMethodName();
		//PracticeUtil.setPracticeFrame(driver);
		
		if (treatmentPlanText != null && !treatmentPlanText.isEmpty()) {
			Select selectTreatmentPlan = new Select(treatmentPlans);
			selectTreatmentPlan.selectByVisibleText(treatmentPlanText);
		}
	}
	
}
