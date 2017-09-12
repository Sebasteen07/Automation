package com.medfusion.product.object.maps.forms.page.questionnaires.prereg_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.medfusion.portal.utils.PortalUtil;
import com.medfusion.product.object.maps.forms.page.questionnaires.PortalFormPage;

public class FormVaccinePage extends PortalFormPage {

	public FormVaccinePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "tetanusvaccination")
	WebElement tetanusvaccination;

	@FindBy(id = "hpvvaccination")
	WebElement hpvvaccination;

	@FindBy(id = "influeenzavaccination")
	WebElement influeenzavaccination;

	@FindBy(id = "pneumoniavaccination")
	WebElement pneumoniavaccination;

	@FindBy(id = "tdap_immunization")
	WebElement tdapimmunization;

	@FindBy(xpath = "//input[@type='submit' and @value='Save & Continue']")
	private WebElement saveAndContinuebtn;

	/**
	 * @Description:Set Tetanus
	 * @return
	 * @throws Exception
	 */
	public void setTetanus(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(tetanusvaccination);
		selector.selectByVisibleText(type);
	}
	public void setTetanus_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(tetanusvaccination);
		selector.selectByVisibleText(type);
	}
	/**
	 * @Description:Set HPV
	 * @return
	 * @throws Exception
	 */
	public void setHPV(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(hpvvaccination);
		selector.selectByVisibleText(type);
	}
	public void setHPV_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(hpvvaccination);
		selector.selectByVisibleText(type);
	}
	/**
	 * @Description:Set Influenza
	 * @return
	 * @throws Exception
	 */
	public void setInfluenza(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(influeenzavaccination);
		selector.selectByVisibleText(type);
	}
	public void setInfluenza_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(influeenzavaccination);
		selector.selectByVisibleText(type);
	}
	/**
	 * @Description:Set Pneumonia
	 * @return
	 * @throws Exception
	 */
	public void setPneumonia(String type) throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		Select selector = new Select(pneumoniavaccination);
		selector.selectByVisibleText(type);
	}
	public void setPneumonia_20(String type) throws Exception {
		PortalUtil.PrintMethodName();
		Select selector = new Select(pneumoniavaccination);
		selector.selectByVisibleText(type);
	}

	/**
	 * @Description:Click on Tetanus Check Box
	 * @return
	 * @throws Exception
	 */
	public void clickTetanus() throws Exception {
		PortalUtil.PrintMethodName();
		PortalUtil.setquestionnarieFrame(driver);
		tdapimmunization.click();
	}

	public FormSurgeriesHospitalizationsPage SetVaccines(String tetanus, String HPV, String Influenza, String Pneumonia) throws Exception
	{
		setTetanus_20(tetanus);
		setHPV_20(HPV);
		setInfluenza_20(Influenza);
		setPneumonia_20(Pneumonia);
		saveAndContinuebtn.click();
		return  PageFactory.initElements(driver, FormSurgeriesHospitalizationsPage.class);
		
	}
	@Override
	public boolean isPageLoaded() {
		return driver.findElement(By.xpath(String.format(PAGE_LOADED_XPATH_TEMPLATE, "Vaccinations"))).isDisplayed();
	}
}
