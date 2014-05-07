package com.intuit.ihg.product.object.maps.portal.page.solutions.virtualofficevisit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;

public class VirtualOfficeVisitPharmacyPage extends BasePageObject {

	public static final String PAGE_NAME = "Virtual Office Visit Pharmacy Page";
	
	@FindBy(name="location_pharmacy")
	private WebElement pharmacy;
	
	@FindBy(xpath="//input[@value='Continue']")
	private WebElement btnContinue;
	
	@FindBy(xpath="//input[@value='Agree and Continue']")
	private WebElement btnAgreeAndContinue;
	
	public VirtualOfficeVisitPharmacyPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Will select the supplied pharmacy and click continue. If there is no pharmacy supplied
	 * the last item in the list is selected.
	 * @param pharmacyOption the visible text in the pharmacy select box
	 * @return the VoV questionnaire page
	 */
	public VirtualOfficeVisitQuestionnairePage choosePharmacyAndContinue(String pharmacyOption) {
		IHGUtil.PrintMethodName();
		// PortalUtil.setPortalFrame(driver);
		
		selectPharmacy(pharmacyOption);
		contineToNextPage();
		
		return PageFactory.initElements(driver, VirtualOfficeVisitQuestionnairePage.class);
	}
	
	/**
	 * A practice may turn on a waiver of liability that must be consented.
	 * When this occurs, the button changes from 'Continue' to 'Agree and Continue'.
	 * So this method will look for 'Continue' button, and if it doesn't exist
	 * click on the 'Agree and Continue' button.
	 */
	private void contineToNextPage() {
		IHGUtil.PrintMethodName();
		
		boolean doesContinueExist = false;
		try {
			doesContinueExist = btnContinue.isDisplayed();
		} catch (Exception e) {
			
		}
		
		if (doesContinueExist) {
			btnContinue.click();
		} else {
			btnAgreeAndContinue.click();
		}
	}
	
	private void selectPharmacy(String pharmacyOption) {
		IHGUtil.PrintMethodName();
		
		Select pharmacySelect = new Select(pharmacy);
		
		if (pharmacyOption != null && !pharmacyOption.isEmpty()) {
			pharmacySelect.selectByVisibleText(pharmacyOption);
		} else {
			pharmacySelect.selectByIndex(pharmacySelect.getOptions().size()-1);
		}
	}

	/**
	 * Gives indication if page is loaded
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		
		boolean result = false;
		try {
			result = pharmacy.isDisplayed();
		} catch (Exception e) {

		}
		
		return result;
	}
}
