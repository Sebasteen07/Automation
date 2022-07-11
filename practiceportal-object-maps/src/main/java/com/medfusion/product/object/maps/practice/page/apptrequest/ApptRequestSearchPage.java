//Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.apptrequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class ApptRequestSearchPage extends BasePageObject {

	public final static String PAGE_NAME = "Appt Request Search Page";

	// Finds all Status radio options
	@FindBy(name = "searchParams:1:input")
	private List<WebElement> status;

	@FindBy(name = "searchParams:3:input")
	private WebElement location;

	@FindBy(name = "searchParams:2:input")
	private WebElement provider;

	@FindBy(name = "buttons:submit")
	private WebElement filterApptRequests;

	@FindBy(id = "MfAjaxFallbackDefaultDataTable")
	private WebElement searchResults;

	@FindBy(xpath = ".//table/tbody/tr/td[3]/span")
	private List<WebElement> searchResultReason;

	@FindBy(name = "searchParams:0:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name = "searchParams:0:input:Date End:month")
	private WebElement endMonth;

	@FindBy(xpath = ".//select[@name = 'searchParams:0:input:Date End:day']")
	public WebElement endDateDropDwn;

	@FindBy(xpath = "searchParams:0:input:Date Begin:month")
	public WebElement startMonthDropDwn;

	@FindBy(xpath = ".//select[@name= 'searchParams:0:input:Date Begin:day']")
	public WebElement startDateDropDwn;

	@FindBy(name = "searchParams:0:input:Date Begin:year")
	private WebElement startYear;

	@FindBy(name = "searchParams:0:input:Date End:year")
	private WebElement endYear;

	@FindBy(xpath = ".//span/a[@title='Go to last page']")
	public WebElement gotoLastPage;

	public ApptRequestSearchPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		boolean result = false;
		
		
		for(int i = 0; i < 4; i++){
			   try {
			    PracticeUtil.setPracticeFrame(driver);
			    result = provider.isDisplayed();
			    break;    
			   } catch (Exception e) {
			    log("search page loaded? failed attempt:" + (i+1) + " out of 4");
			    // Catch any element not found errors
			   }
			   try {
			    Thread.sleep(3000);
			   } catch (InterruptedException e) {
			    e.printStackTrace();
			   }
			  }

		return result;
	}

	/**
	 * Searches for requests using default options on the page. Call the getApptDetails method to access the search results.
	 */
	public void searchForApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		searchForApptRequests(ApptRequestStatus.OPEN, null, null);
	}
	
	public void searchForApprovedApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		searchForApptRequests(ApptRequestStatus.APPROVED, null, null);
	}

	public void searchForCancelledApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		searchForApptRequests(ApptRequestStatus.CANCELLED, null, null);
	}
	
	public void searchForExternalProcessApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		searchForApptRequests(ApptRequestStatus.PROCESSED_EXTERNALLY, null, null);
	}
	
	public void searchForPendingApptRequests() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		searchForApptRequests(ApptRequestStatus.PENDING, null, null);
	}

	/**
	 * Searches for requests using default options on the page. Also sets the to and from month value same - Effectively searching only for the day's appointments
	 * Call the getApptDetails method to access the search results.
	 */
	public void searchForApptRequestsForToday() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);

		IHGUtil.waitForElement(driver, 6, startMonth);
		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));

		IHGUtil.waitForElement(driver, 6, startYear);
		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));

		searchForApptRequests(ApptRequestStatus.OPEN, null, null);
	}

	/**
	 * Searches for requests using default options on the page. Call the getApptDetails method to access the search results.
	 * 
	 * @param requestStatus as defined in ApptRequestStatus public fields
	 * @param providerText the visible text option in the select box
	 * @param locationText the visible text option in the select box
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

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		String date = strDate.substring(0, 2);
		if (date.startsWith("0")) {
			date = strDate.substring(1, 2);
		}

		String month = strDate.substring(3, 5);
		int startMnthValue = Integer.parseInt(month) - 1;

		log("Select the Start Month in search filter options");
		IHGUtil.waitForElement(driver, 6, startMonth);
		Select startMonthSelect = new Select(startMonth);
		startMonthSelect.selectByValue(String.valueOf(startMnthValue));

		log("Select the Start date in search filter options");
		int i = 1;
		do {
			try {
				IHGUtil.waitForElement(driver, 6, startDateDropDwn);
				Select startDate = new Select(startDateDropDwn);
				startDate.selectByValue(date);
				break;
			} catch ( Exception e) {				
				if (e instanceof StaleElementReferenceException) {
					e.printStackTrace();
					log("Stale element encountered, attempt #" + i + " out of 3");
					i++;
					continue;
				}
				log("Unexpected exception caught and test failed, printing and rethrowing.");
				e.printStackTrace();
				throw new InterruptedException("Unexpected exception interrupted test on appointment start date search filter");
			}
		} while (i < 3);

		log("Select end date in search filter options");
		IHGUtil.waitForElement(driver, 6, endDateDropDwn);
		Select endDate = new Select(endDateDropDwn);
		endDate.selectByValue(date);

		log("Select end Month in search filter options");
		IHGUtil.waitForElement(driver, 6, endMonth);
		Select endMonthSelect = new Select(endMonth);
		endMonthSelect.getFirstSelectedOption().getAttribute("index");

		log("Select end Year in search filter options");
		IHGUtil.waitForElement(driver, 6, startYear);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);
		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));

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
		IHGUtil.waitForElement(driver, 10, searchResults);
	}

	/**
	 * Called after the searchForApptRequests method to find a specific request to process.
	 * 
	 * @param subjectSubString unique sub-string from the subject of the request to filter on
	 * 
	 * @return the Appt Request detail page if found or null if not found 
	 */
	public ApptRequestDetailStep1Page getRequestDetails(String subjectSubString) throws Exception {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		try {
			searchResults.isDisplayed();
		} catch (Exception e) {
			throw new Exception("Ask A Staff search result table is not found. Ensure a search was completed first.");
		}
		int i = 0;
		if (!IHGUtil.exists(driver, gotoLastPage)) {
			for (WebElement complaint : searchResultReason) {
				i++;
				if (complaint.getText().contains(subjectSubString)) {
					complaint.click();
					log("Value :" + i);
					return PageFactory.initElements(driver, ApptRequestDetailStep1Page.class);
				}
			}
		} else {

			PracticeUtil.setPracticeFrame(driver);
			driver.findElement(By.xpath(".//span/a[@title='Go to last page']")).click();

			PracticeUtil.setPracticeFrame(driver);
			Thread.sleep(3000);
			List<WebElement> lastPageElements = driver.findElements(By.xpath(".//table/tbody/tr/td[3]/span"));
			log("Size :" + lastPageElements.size());
			for (WebElement complaint1 : lastPageElements) {
				if (complaint1.getText().contains(subjectSubString)) {
					complaint1.click();
					return PageFactory.initElements(driver, ApptRequestDetailStep1Page.class);
				}
			}
		}
		return null;
	}


}
