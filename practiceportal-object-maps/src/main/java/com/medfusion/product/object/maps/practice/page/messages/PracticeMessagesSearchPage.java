//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.messages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ifs.csscat.core.utils.Log4jUtil;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class PracticeMessagesSearchPage extends BasePageObject {

	public static final String PAGE_NAME = "Practice Messages Search Page";

	@FindBy(name = "searchParams:1:input")
	private List<WebElement> serviceTypeList;

	@FindBy(name = "buttons:submit")
	private WebElement btnSearch;

	@FindBy(name = "searchParams:5:input")
	private WebElement status;

	@FindBy(name = "searchParams:2:input")
	private WebElement firstName;

	@FindBy(name = "searchParams:3:input")
	private WebElement lastName;

	@FindBy(name = "searchParams:4:input")
	private WebElement subject;

	@FindBy(xpath = ".//*[@id='MfAjaxFallbackDefaultDataTable']/tbody/tr/td[2]")
	private List<WebElement> messageList;

	@FindBy(xpath = ".//*[@id='MfAjaxFallbackDefaultDataTable']/tbody/tr/td[1]")
	private WebElement messageLST;

	@FindBy(name = "searchParams:0:input:Date Begin:month")
	private WebElement startMonth;

	@FindBy(name = "searchParams:0:input:Date End:month")
	private WebElement endMonth;

	@FindBy(name = "searchParams:0:input:Date Begin:year")
	private WebElement startYear;

	@FindBy(name = "searchParams:0:input:Date End:year")
	private WebElement endYear;


	public PracticeMessagesSearchPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * Gives an indication if the page is loaded.
	 * 
	 * @return true or false
	 */
	public boolean isPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		boolean result = false;
		try {
			result = firstName.isDisplayed();
		} catch (Exception e) {
			// catch element not found error
		}

		return result;
	}

	/**
	 * Searches for all messages related to Virtual Office Visit.
	 */
	public void searchForVirtualOfficeVisitMessages() throws InterruptedException {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		executeSearch(PracticeMessageServiceType.VIRTUAL_OFFICE_VISIT, null, null, null, null);
	}

	/**
	 * Searches for all messages given the parameters supplied. Call retrieveMessage after searching to find a specific message.
	 * 
	 * @param serviceType use the PracticeMessageServiceType to declare
	 * @param firstNameText the first name of the patient
	 * @param lastNameText the last name of the patient
	 * @param subjectText the subject to search against
	 * @param statusText the visible status in the select box of the message
	 */
	private void executeSearch(String serviceType, String firstNameText, String lastNameText, String subjectText, String statusText) throws InterruptedException {
		IHGUtil.PrintMethodName();

		// Handle the Check All case
		boolean checkAll = false;
		if (PracticeMessageServiceType.CHECK_ALL.equalsIgnoreCase(serviceType)) {
			checkAll = true;
		}

		for (WebElement type : serviceTypeList) {
			if (type.getAttribute("value").equalsIgnoreCase(serviceType) || checkAll) {
				if (!type.isSelected()) {
					type.click();
				}
			}
		}
		Thread.sleep(5000);
		Select endMonthSelect = new Select(endMonth);
		Select startMonthSelect = new Select(startMonth);
		Select endYearSelect = new Select(endYear);
		Select startYearSelect = new Select(startYear);

		String index = endMonthSelect.getFirstSelectedOption().getAttribute("index");
		startMonthSelect.selectByIndex(Integer.parseInt(index));
		Thread.sleep(2000);

		String index3 = endYearSelect.getFirstSelectedOption().getAttribute("index");
		startYearSelect.selectByIndex(Integer.parseInt(index3));
		Thread.sleep(2000);

		if (firstNameText != null && !firstNameText.isEmpty()) {
			firstName.sendKeys(firstNameText);
		}

		if (lastNameText != null && !lastNameText.isEmpty()) {
			lastName.sendKeys(lastNameText);
		}


		if (statusText != null && !statusText.isEmpty()) {
			Select statusSelect = new Select(status);
			statusSelect.selectByVisibleText(statusText);
		}

		btnSearch.click();
	}

	/**
	 * Finds the message from the message search list that contains in the subject the text supplied as an argument.
	 * 
	 * @param subjectText unique subject text to identify the message
	 * 
	 * @return PracticeMessage or null if nothing is found
	 */
	public PracticeMessagePage retrieveMessage(String subjectText) throws InterruptedException {
		IHGUtil.PrintMethodName();
		Thread.sleep(10000);
		IHGUtil.setFrame(driver, "iframebody");
		int count = 1;
		for (WebElement message : messageList) {

			Log4jUtil.log(" Found Practice Vov Message with subject: " + message.getText() + ", Needed Message with subject: " + subjectText);
			String msg = message.getText().trim();
			if (msg.endsWith(subjectText)) {
				driver.findElement(By.xpath(".//*[@id='MfAjaxFallbackDefaultDataTable']/tbody".concat("/tr[" + count + "]/td[1]/span/a"))).click();
				break;
			}
			count++;
		}
		return PageFactory.initElements(driver, PracticeMessagePage.class);
	}
}
