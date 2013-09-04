package com.intuit.ihg.product.practice.page.apptrequest;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.intuit.ihg.product.practice.page.askstaff.AskAStaffRequestStatus;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class ApptRequestSearchPage extends BasePageObject {
	
	public final static String PAGE_NAME = "Appt Request Search Page";
	
	// Finds all Status radio options
	@FindBy(name="searchParams:1:input")
	private List<WebElement> status;
	
	@FindBy(name="searchParams:3:input")
	private WebElement location;
	
	@FindBy(name="searchParams:2:input")
	private WebElement provider;
	
	@FindBy(name="buttons:submit")
	private WebElement filterApptRequests;
	
	@FindBy(id="MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;
	
	@FindBy(xpath=".//table/tbody/tr/td[3]/span")
	private List<WebElement> searchResultReason;

    @FindBy(name="searchParams:0:input:Date Begin:month")
    private WebElement startMonth;

    @FindBy(name="searchParams:0:input:Date End:month")
    private WebElement endMonth;

	public ApptRequestSearchPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Indicates if the search page is loaded
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = provider.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}
	
	/**
	 * Searches for requests using default options on the page.
	 * Call the getApptDetails method to access the search results.
	 * @throws InterruptedException 
	 */
	public void searchForApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		searchForApptRequests(ApptRequestStatus.OPEN, null, null);
	}

    /**
	 * Searches for requests using default options on the page.
     * Also sets the to and from month value same -
     * Effectively searching only for the day's appointments
	 * Call the getApptDetails method to access the search results.
     * @throws InterruptedException 
	 */
	public void searchForApptRequestsForToday() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

        Select endMonthSelect = new Select(endMonth);
        Select startMonthSelect = new Select(startMonth);

        String index= endMonthSelect.getFirstSelectedOption().getAttribute("index")  ;
        startMonthSelect.selectByIndex(Integer.parseInt(index));

        searchForApptRequests(ApptRequestStatus.OPEN, null, null);
	}

	/**
	 * Searches for requests using default options on the page.
	 * Call the getApptDetails method to access the search results.
	 * 
	 * @param requestStatus as defined in ApptRequestStatus public fields
	 * @param providerText the visible text option in the select box
	 * @param locationText the visible text option in the select box
	 * @throws InterruptedException 
	 */
	public void searchForApptRequests(int requestStatus, String providerText, String locationText) throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		// Set status
		for (WebElement s : status) {
			if (Integer.parseInt(s.getAttribute("value")) == requestStatus) {
				s.click();
			}
		}
		Thread.sleep(5000);
		Select endMonthSelect = new Select(endMonth);
	    Select startMonthSelect = new Select(startMonth);

	    String index= endMonthSelect.getFirstSelectedOption().getAttribute("index")  ;
	    startMonthSelect.selectByIndex(Integer.parseInt(index));
		// Set provider if supplied
		if (providerText != null) {
			Select questionTypeSelect = new Select(provider);
			questionTypeSelect.selectByVisibleText(providerText);
		}
		
		// Set location if supplied
		if (locationText != null) {
			Select locationSelect = new Select(location);
			locationSelect.selectByVisibleText(locationText);
		}		

		filterApptRequests.click();
	}
	
	/**
	 * Called after the searchForApptRequests method to find a specific request to process.
	 * 
	 * @param subjectSubString unique sub-string from the subject of the request to filter on
	 * 
	 * @return the Appt Request detail page if found or null if not found
	 * @see ApptRequestDetailStep1Page
	 * 
	 * @throws Exception
	 */
	public ApptRequestDetailStep1Page getRequestDetails(String subjectSubString) throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Ask A Staff search result table is not found. Ensure a search was completed first.");
		}
		
		for (WebElement complaint : searchResultReason) {
			if (complaint.getText().contains(subjectSubString)) {
				complaint.click();				
				return PageFactory.initElements(driver, ApptRequestDetailStep1Page.class);
			}
		}
		
		return null;		
	}

}
