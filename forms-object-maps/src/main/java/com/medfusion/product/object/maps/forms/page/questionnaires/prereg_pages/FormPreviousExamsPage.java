//  Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;
import com.medfusion.product.patientportal2.utils.PortalUtil2;

public class FormPreviousExamsPage extends PortalFormPage {

	@FindBy(id = "procedures_other_line_autocomplete")
	WebElement examortestName;

	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id = "procedures_other_line_frequency_type")
	WebElement testTimeFrame;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	public FormPreviousExamsPage(WebDriver driver) {
		super(driver);
	}

	public void setTest(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		examortestName.clear();
		examortestName.sendKeys(type);
		examortestName.sendKeys(Keys.TAB);
		IHGUtil.waitForElement(driver, 100, autoComplete);
		autoComplete.click();
	}

	public void setTest_20(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		examortestName.clear();
		examortestName.sendKeys(type);
		examortestName.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	public void setTestTimeFrame(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}

	public void setTestTimeFrame_20(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		Select selector = new Select(testTimeFrame);
		selector.selectByVisibleText(type);
	}

	public FormPastMedicalHistoryPage fillTestDetails(String Test1, String TestTimeFrame1) throws Exception {
		setTest_20(Test1);
		setTestTimeFrame_20(TestTimeFrame1);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormPastMedicalHistoryPage.class);
	}

	@Override
	public boolean isPageLoaded() {
		return driver
				.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Previous Exams, Tests & Procedures")))
				.isDisplayed();
	}
}
