package com.medfusion.product.object.maps.practice.page.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep3Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 3 Page";
	
	@FindBy(linkText="Patient Intake")
	private WebElement patientIntakeTab;
	
	@FindBy(linkText="Go Back To Search Page")
	private WebElement goBackToSearchPage;
	
	@FindBy(name="buttons:submit")
	private WebElement btnConfirm;
	
	@FindBy(name="buttons:cancel")
	private WebElement btnMakeChanges;
	
	public AskAStaffQuestionDetailStep3Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives indication if the Ask A Staff Question Detail page loaded.
	 * @return true or false
	 */
	public boolean isQuestionDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = patientIntakeTab.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * Will click the 'Go Back To Search Page' link.
	 * @return the Ask A Staff search page
	 */
	public AskAStaffSearchPage clickGoBackToSearchPage() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		goBackToSearchPage.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}
	
	public AskAStaffQuestionDetailStep4Page confirmProcessedQuestion() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		btnConfirm.click();
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep4Page.class);
	}
}
