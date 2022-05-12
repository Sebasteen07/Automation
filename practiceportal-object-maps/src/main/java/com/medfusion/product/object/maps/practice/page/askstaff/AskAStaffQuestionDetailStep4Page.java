// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.practice.page.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.practice.api.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep4Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 4 Page";

	@FindBy(linkText = "Click here to view this patient's chart and print it.")
	private WebElement viewPatientChart;
	
	@FindBy(xpath = "//*[@id=\'content\']/div[2]/p[1]/span")
	private WebElement confirmMsg;
	

	public AskAStaffQuestionDetailStep4Page(WebDriver driver) {
		super(driver);
	}

	public boolean isQuestionDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);

		IHGUtil.waitForElement(driver, 40, viewPatientChart);
		boolean result = false;
		try {
			result = viewPatientChart.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	public boolean isVovSubmitted() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		IHGUtil.waitForElement(driver, 20, confirmMsg);
		
		return  confirmMsg.getText().contains("Your prescription and communication have been posted and the visit has been closed.");
	}
	
}
