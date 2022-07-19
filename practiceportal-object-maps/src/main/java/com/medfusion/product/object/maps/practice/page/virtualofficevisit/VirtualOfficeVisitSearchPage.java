//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.virtualofficevisit;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.practice.page.askstaff.AskAStaffQuestionDetailStep1Page;
import com.medfusion.product.object.maps.practice.page.messages.PracticeMessagesSearchPage;

public class VirtualOfficeVisitSearchPage extends BasePageObject {

	// VOV Statuses
	public final static String ALL = "0";
	public final static String OPEN = "1";
	public final static String IN_PROGRESS = "6";
	public final static String COMPLETED = "2";
	public final static String CLOSED = "4";
	public final static String TERMINATED = "5";

	public static final String PAGE_NAME = "Virtual Office Visit Search Page";
	// private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
	private boolean hasSearched = false;

	// Finds all Status radio options
	@FindBy(name = "status")
	private List<WebElement> status;

	// Finds VOV assignment options
	@FindBy(name = "searchforall")
	private List<WebElement> vovAssignments;

	@FindBy(name = "search_now")
	private WebElement btnFilterResults;

	// From date select boxes
	@FindBy(name = "month_1")
	private WebElement fromMonth;

	@FindBy(name = "day_1")
	private WebElement fromDay;

	@FindBy(name = "year_1")
	private WebElement fromYear;

	// To date select boxes
	@FindBy(name = "month_2")
	private WebElement toMonth;

	@FindBy(name = "day_2")
	private WebElement toDay;

	@FindBy(name = "year_2")
	private WebElement toYear;

	// Search Filter text
	@FindBy(xpath = ".//*[@id='searchForm']/fieldset/legend/strong")
	private WebElement searchFilterText;

	// Search Result table's Date/Time column
	@FindBy(xpath = ".//*[@id='table-1']/tbody/tr/td[2]")
	private List<WebElement> submittedDates;

	@FindBy(xpath = "//table[2]/tbody/tr[2]/td[2]/form/strong/strong/table[2]/tbody/tr[1]/td[3]")
	private WebElement submittedDate;

	// My Message link
	@FindBy(xpath = ".//a[contains(@href,'comms')]")
	private WebElement myMessagesLink;

	public VirtualOfficeVisitSearchPage(WebDriver driver) {
		super(driver);
	}

	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		// PracticeUtil.setPracticeFrame(driver);

		boolean result = false;
		try {
			result = searchFilterText.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found exceptions
		}

		return result;
	}

	public void doBasicSearch() {
		IHGUtil.PrintMethodName();
		// PracticeUtil.setPracticeFrame(driver);

		// Set VOV Status to Open
		setVovStatus(OPEN);

		// Set VOV Assignment to ALL
		setVovAssignment(VirtualOfficeVisitAssignment.ALL);

		// Leave From/To dates as is and click Filter
		btnFilterResults.click();
		hasSearched = true;
	}

	public AskAStaffQuestionDetailStep1Page getDetails(Date sentDate) throws ParseException {
		IHGUtil.PrintMethodName();
		// PracticeUtil.setPracticeFrame(driver);

		if (!hasSearched) {
			doBasicSearch();
		}
		submittedDate.click();

		return PageFactory.initElements(driver, AskAStaffQuestionDetailStep1Page.class);
	}

	private void setVovAssignment(String vovAssignment) {
		for (WebElement option : vovAssignments) {
			if (option.getAttribute("value").equalsIgnoreCase(vovAssignment)) {
				option.click();
				break;
			}
		}
	}

	private void setVovStatus(String vovStatus) {
		for (WebElement option : status) {
			if (option.getAttribute("value").equalsIgnoreCase(vovStatus)) {
				option.click();
				break;
			}
		}
	}

	public PracticeMessagesSearchPage goToMyMessages() {
		IHGUtil.PrintMethodName();

		myMessagesLink.click();
		return PageFactory.initElements(driver, PracticeMessagesSearchPage.class);
	}

}
