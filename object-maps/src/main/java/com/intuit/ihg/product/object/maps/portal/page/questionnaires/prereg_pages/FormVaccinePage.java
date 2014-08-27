package com.intuit.ihg.product.object.maps.portal.page.questionnaires.prereg_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ihg.product.object.maps.portal.page.questionnaires.PortalFormPage;
import com.intuit.ihg.product.portal.utils.PortalUtil;

public class FormVaccinePage extends PortalFormPage {

	public FormVaccinePage(WebDriver driver)  {
		super(driver);
	}


	@FindBy(id="tetanusvaccination")
	WebElement tetanusvaccination;

	@FindBy(id="hpvvaccination")
	WebElement hpvvaccination;

	@FindBy(id="influeenzavaccination")
	WebElement influeenzavaccination;

	@FindBy(id="pneumoniavaccination")
	WebElement pneumoniavaccination;

	@FindBy(id="tdap_immunization")
	WebElement tdapimmunization;

	@FindBy(xpath="//input[@type='submit' and @value='Save & Continue']")
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

}
