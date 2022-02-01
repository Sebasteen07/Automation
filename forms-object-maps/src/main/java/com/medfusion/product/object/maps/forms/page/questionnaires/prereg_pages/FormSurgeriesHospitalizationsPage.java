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

public class FormSurgeriesHospitalizationsPage extends PortalFormPage {

	public FormSurgeriesHospitalizationsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "surgeries_other_field_autocomplete")
	WebElement surgeryName;

	@FindBy(xpath = "//div[@id='autocomplete']/ul/li")
	WebElement autoComplete;

	@FindBy(id = "surgeries_other_field_frequency_type")
	WebElement surgeryTimeFrame;

	@FindBy(id = "hospitalizations_other_field_autocomplete")
	WebElement hospitalizationReason;

	@FindBy(id = "hospitalizations_other_field_frequency_type")
	WebElement hospitalizationTimeFrame;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	public void setSurgeryName(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		surgeryName.clear();
		surgeryName.sendKeys(type);
		surgeryName.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	public void setSurgeryName_20(String SurgeryName) throws Exception {
		PortalUtil2.PrintMethodName();
		IHGUtil.waitForElement(driver, 20, surgeryName);
		surgeryName.clear();
		surgeryName.sendKeys(SurgeryName);
		surgeryName.sendKeys(Keys.ARROW_UP);
		autoComplete.click();
	}

	public void setSurgeryTimeFrame(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		Select selector = new Select(surgeryTimeFrame);
		selector.selectByVisibleText(type);
	}

	public void setSurgeryTimeFrame_20(String SurgeryNameFrame) throws Exception {
		PortalUtil2.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, surgeryTimeFrame);
		Select selector = new Select(surgeryTimeFrame);
		selector.selectByVisibleText(SurgeryNameFrame);
	}

	public void setHospitalizationReason(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		hospitalizationReason.clear();
		hospitalizationReason.sendKeys(type);
		hospitalizationReason.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	public void setHospitalizationReason_20(String HospitalizationReason) throws Exception {
		PortalUtil2.PrintMethodName();
		hospitalizationReason.clear();
		hospitalizationReason.sendKeys(HospitalizationReason);
		hospitalizationReason.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	public void setHospitalizationTimeFrame(String type) throws Exception {
		PortalUtil2.PrintMethodName();
		PortalUtil2.setquestionnarieFrame(driver);
		Select selector = new Select(hospitalizationTimeFrame);
		selector.selectByVisibleText(type);
	}

	public void setHospitalizationTimeFrame_20(String HospitalizationReasonframe) throws Exception {
		PortalUtil2.PrintMethodName();
		IHGUtil.waitForElement(driver, 10, hospitalizationTimeFrame);
		Select selector = new Select(hospitalizationTimeFrame);
		selector.selectByVisibleText(HospitalizationReasonframe);
	}

	public FormPreviousExamsPage fillSurgiesForm(String SurgeryName, String SurgeryTimeFrame,
			String HospitalizationReason, String HospitalizationTimeFrame) throws Exception {
		setSurgeryName_20(SurgeryName);
		setSurgeryTimeFrame_20(SurgeryTimeFrame);
		setHospitalizationReason_20(HospitalizationReason);
		setHospitalizationTimeFrame_20(HospitalizationTimeFrame);
		saveAndContinuebtn.click();
		return PageFactory.initElements(driver, FormPreviousExamsPage.class);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Surgeries & Hospitalizations")))
				.isDisplayed();
	}

}
