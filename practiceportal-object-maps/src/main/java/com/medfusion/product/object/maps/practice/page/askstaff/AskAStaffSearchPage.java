//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.askstaff;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class AskAStaffSearchPage extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Search Page";

	// Finds all Status radio options
	@FindBy(name = "searchParams:1:input")
	private List<WebElement> status;

	@FindBy(name = "searchParams:2:input")
	private WebElement location;

	@FindBy(name = "searchParams:3:input")
	private WebElement questionType;

	@FindBy(name = "buttons:submit")
	private WebElement getRequests;

	@FindBy(id = "MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;

	@FindBy(xpath = ".//table/tbody/tr/td[6]/span")
	private List<WebElement> searchResultComplaints;


	public AskAStaffSearchPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Searches for questions using default options on the page. Call the getQuestionDetails method to access the search results.
	 */
	public void searchForQuestions() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		searchForQuestions(AskAStaffRequestStatus.NONE, null, null);
	}

	/**
	 * Searches for questions with all available options. Call the getQuestionDetails method to access the search results from this page.
	 * 
	 * @param requestStatus as defined in AskAStaffRequestStatus public fields
	 * @param locationText the visible text option in the select box
	 * @param questionTypeText the visible text option in the select box
	 */
	public void searchForQuestions(int requestStatus, String locationText, String questionTypeText) {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		// Set status if supplied
		if (AskAStaffRequestStatus.NONE != requestStatus) {
			for (WebElement s : status) {
				if (Integer.parseInt(s.getAttribute("value")) == requestStatus) {
					s.click();
				}
			}
		}

		// Set location if supplied
		if (locationText != null) {
			Select locationSelect = new Select(location);
			locationSelect.selectByVisibleText(locationText);
		}

		// Set question type if supplied
		if (questionTypeText != null) {
			Select questionTypeSelect = new Select(questionType);
			questionTypeSelect.selectByVisibleText(questionTypeText);
		}

		getRequests.click();
	}

	/**
	 * Called after the searchForQuestions method to find a specific question to process.
	 * 
	 * @param subjectSubString unique sub-string from the subject of the question to filter on
	 * 
	 * @return the Ask A Staff question detail page if found or null if not found
	 */
	public AskAStaffQuestionDetailStep1Page getQuestionDetails(String subjectSubString) throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Ask A Staff search result table is not found. Ensure a search was completed first.");
		}

		for (WebElement complaint : searchResultComplaints) {
			if (complaint.getText().contains(subjectSubString)) {
				IHGUtil.waitForElement(driver, 15, complaint);
				javascriptClick(complaint);
				return PageFactory.initElements(driver, AskAStaffQuestionDetailStep1Page.class);
			}
		}

		return null;
	}

}
