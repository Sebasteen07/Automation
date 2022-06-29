//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.askstaff;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep1Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 1 Page";

	@FindBy(linkText = "Patient Intake")
	private WebElement patientIntakeTab;

	@FindBy(linkText = "Go Back To Search Page")
	private WebElement goBackToSearchPage;

	@FindBy(name = "action")
	private List<WebElement> actionOptions;

	@FindBy(xpath = ".//input[@value='Submit Choice']")
	private WebElement btnSubmitChoice;

	@FindBy(className = "feedbackpanelERROR")
	private WebElement errorPanel;

	public AskAStaffQuestionDetailStep1Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives indication if the Ask A Staff Question Detail page loaded.
	 * 
	 * @return true or false
	 */
	public boolean isQuestionDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			if (patientIntakeTab.isDisplayed()) {
				if (IHGUtil.exists(driver, errorPanel)) {
					return !(errorPanel.isDisplayed());
				} else
					return true;
			}
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return false;
	}

	/**
	 * Will click the 'Go Back To Search Page' link.
	 * 
	 * @return the Ask A Staff search page
	 */
	public AskAStaffSearchPage clickGoBackToSearchPage() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		goBackToSearchPage.click();
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	/**
	 * Will ensure correct action is selected. It is required that the calling method return the expected page.
	 * 
	 * @param actionOption options defined in AskAStaffActionOption public fields
	 */
	private void chooseActionAndSubmit(int actionOption) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		for (WebElement action : actionOptions) {
			if (actionOption == Integer.parseInt(action.getAttribute("value"))) {
				action.click();
				btnSubmitChoice.click();
				return;
			}
		}
	}

	/**
	 * Will close the question without responding
	 * 
	 * @return the ask a staff search page
	 */
	public AskAStaffSearchPage chooseCloseAndSubmit() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		chooseActionAndSubmit(AskAStaffActionOption.CLOSE);
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	/**
	 * Will set to in-progress the question without responding
	 * 
	 * @return the ask a staff search page
	 */
	public AskAStaffSearchPage chooseInProgressAndSubmit() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		chooseActionAndSubmit(AskAStaffActionOption.SET_TO_INPROGRESS);
		return PageFactory.initElements(driver, AskAStaffSearchPage.class);
	}

	/**
	 * Will set to provide advice and medication and continue to the next page
	 * 
	 * @return the ask a staff detail step 2 page
	 */
	public AskAStaffQuestionDetailStep2Page chooseProvideAdviceAndMedicine() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		chooseActionAndSubmit(AskAStaffActionOption.PROVIDE_ADVICE_AND_MEDICATION);
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep2Page.class);
	}

	/**
	 * Will set to provide advice and continue to the next page
	 * 
	 * @return the ask a staff detail step 2 page
	 */
	public AskAStaffQuestionDetailStep2Page chooseProvideAdviceOnly() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		chooseActionAndSubmit(AskAStaffActionOption.PROVIDE_ADVICE_ONLY);
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep2Page.class);
	}

	/**
	 * Will set to in progress and continue to the next page
	 * 
	 * @return the ask a staff detail step 2 page
	 */
	public AskAStaffQuestionDetailStep2Page chooseCommunicateAndSetToInProgress() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		chooseActionAndSubmit(AskAStaffActionOption.COMMUNICATE_SET_TO_INPROGRESS);
		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep2Page.class);
	}

}
