package com.medfusion.product.object.maps.practice.page.apptrequest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class ApptRequestDetailStep2Page extends BasePageObject {

	public static final String PAGE_NAME = "Appt Request Detail Step 2 Page";

	@FindBy(name = "buttons:submit")
	private WebElement btnProcessApptRequest;

	public ApptRequestDetailStep2Page(WebDriver driver) {
		super(driver);
	}

	/**
	 * Indicates whether process confirmation page is displayed.
	 * 
	 * @return true or false
	 */
	public boolean isProcessPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		IHGUtil.waitForElement(driver, 20, btnProcessApptRequest);
		boolean result = false;
		try {
			result = btnProcessApptRequest.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Will process the appt request and redirect to the Appt Request search page
	 * 
	 * @return the appt request search page
	 * @throws InterruptedException
	 */
	public ApptRequestSearchPage processApptRequest() throws InterruptedException {
		Thread.sleep(10000);
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		IHGUtil.waitForElement(driver, 60, btnProcessApptRequest);
		javascriptClick(btnProcessApptRequest);
		return PageFactory.initElements(driver, ApptRequestSearchPage.class);
	}

}
