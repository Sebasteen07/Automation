package com.intuit.ihg.product.object.maps.practice.page.askstaff;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.practice.utils.PracticeUtil;

public class AskAStaffQuestionDetailStep4Page extends BasePageObject {

	public static final String PAGE_NAME = "Ask A Staff Question Detail Step 4 Page";
	
	@FindBy(linkText="Click here to view this patient's chart and print it.")
	private WebElement viewPatientChart;
	
	public AskAStaffQuestionDetailStep4Page(WebDriver driver) {
		super(driver);
	}
	
	public boolean isQuestionDetailPageLoaded() {
		IHGUtil.PrintMethodName();
		PracticeUtil.setPracticeFrame(driver);
		
		boolean result = false;
		try {
			result = viewPatientChart.isDisplayed();
		} catch (Exception e) {
			// Catch any element not found errors
		}
		
		return result;
	}

}
