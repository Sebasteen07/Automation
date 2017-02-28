package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

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

	/**
	 * @Description:Set Surgery Name
	 * @return
	 * @throws Exception
	 */
	public void setSurgeryName(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		surgeryName.clear();
		surgeryName.sendKeys(type);
		surgeryName.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	/**
	 * @Description:Set Surgery TimeFrame
	 * @return
	 * @throws Exception
	 */
	public void setSurgeryTimeFrame(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(surgeryTimeFrame);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Set Hospitalization Reason
	 * @return
	 * @throws Exception
	 */
	public void setHospitalizationReason(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		hospitalizationReason.clear();
		hospitalizationReason.sendKeys(type);
		hospitalizationReason.sendKeys(Keys.TAB);
		autoComplete.click();
	}

	/**
	 * @Description:Set Hospitalization TimeFrame
	 * @return
	 * @throws Exception
	 */
	public void setHospitalizationTimeFrame(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(hospitalizationTimeFrame);
		selector.selectByVisibleText(type);
	}

	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Surgeries & Hospitalizations"))).isDisplayed();
	}

}
